package com.fruitforloops.model.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.fruitforloops.HibernateUtil;
import com.fruitforloops.model.HashTag;
import com.fruitforloops.model.Message;

public class MessageDAO implements IDAO<Message>
{
	@Override
	public boolean save(Message postMessage)
	{
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession())
		{
			transaction = session.beginTransaction();

			session.save(postMessage);

			transaction.commit();
		}
		catch (Exception e)
		{
			System.err.println("An error occured when trying to save a Message-.\n" + e.getMessage());

			if (transaction != null) transaction.rollback();

			return false;
		}

		return true;
	}

	@Override
	public List<Message> getAll()
	{
		List<Message> messageList = new ArrayList<Message>();
		try (Session session = HibernateUtil.getSessionFactory().openSession())
		{
			messageList = session.createQuery("from Message", Message.class).list();
		}
		catch (Exception e)
		{
			System.err.println("Unable to get list of messages.\n" + e.getMessage());
		}

		return messageList;
	}

	/**
	 * 
	 * @param fDate
	 * @param tDate
	 * @param authors
	 * @param hashtags
	 * @return
	 */
	public ArrayList<Message> getMessages(Date fDate, Date tDate, List<String> authors, List<String> hashtags,
			int limit)
	{

		ArrayList<Message> messageList = new ArrayList<Message>();

		String query = "FROM Message";
		List<String> whereList = new ArrayList<String>();

		if (fDate != null) whereList.add("created_date >= :fDate");

		if (tDate != null) whereList.add("created_date <= :tDate");

		if (authors != null && authors.size() > 0) whereList.add("author IN (:authors)");

		if (hashtags != null && hashtags.size() > 0) whereList.add(createMultiHashTagsSql(hashtags));

		if (whereList.size() > 0) query += " WHERE " + whereList.get(0);

		for (int i = 1; i < whereList.size(); ++i)
			query += " AND " + whereList.get(i);

		query += " ORDER BY created_date DESC";

		try (Session session = HibernateUtil.getSessionFactory().openSession())
		{

			Query<Message> queryObj = session.createQuery(query, Message.class).setMaxResults(limit);

			if (fDate != null) queryObj.setParameter("fDate", fDate);

			if (tDate != null) queryObj.setParameter("tDate", tDate);

			if (authors != null && authors.size() > 0)
			{
				queryObj.setParameterList("authors", authors);
			}

			messageList = (ArrayList<Message>) queryObj.list();

		}
		catch (Exception e)
		{
			System.err.println("Unable to get list of messages.\n" + e.getMessage());
		}
		return messageList;
	}

	// Private method to create OR hashtag statements
	private String createMultiHashTagsSql(List<String> hashtags)
	{

		StringBuilder str = new StringBuilder();

		str.append("(");

		for (String s : hashtags)
			str.append("message_text LIKE '%" + s + "%' OR ");
		
		String finalStr = str.toString();
		finalStr = (String) finalStr.subSequence(0, finalStr.length() - 3);
		return finalStr += ")";

	}

	@Override
	public Message get(long id)
	{
		//
		return null;
	}

	@Override
	public boolean update(Message message)
	{

		if (message == null) return false;

		Message originalMessage = null;

		try (Session session = HibernateUtil.getSessionFactory().openSession())
		{

			originalMessage = (Message) session .get(Message.class, message.getId());

		}
		catch (IOException e)
		{
			System.err.println("Unable to retrieve.\n" + e.getMessage());
		}

		try (Session session = HibernateUtil.getSessionFactory().openSession())
		{

			// If message to update does not exist
			if (originalMessage == null)
			{
				return false;
			}

			// get original HashTags in the message
			List<String> originalHashtags = getHashTags(originalMessage.getMessageText()).stream().distinct()
					.collect(Collectors.toList());

			// get new/updated hash tags. Note: We will treat updated as new hash tags
			List<String> incomingHashtags = getHashTags(message.getMessageText()).stream().distinct()
					.collect(Collectors.toList());

			// Finding deleted hashTags
			List<String> deletedHashtags = originalHashtags.stream().filter(f -> !incomingHashtags.contains(f))
					.collect(Collectors.toList());

			// Finding new hashTags
			List<String> newHashtags = incomingHashtags.stream().filter(f -> !originalHashtags.contains(f))
					.collect(Collectors.toList());

			Set<HashTag> newHashSet = new HashSet<HashTag>();

			// Deal with deleted Hash tags
			processDeletedHashtags(session, message, deletedHashtags);

			// Deal with new Hash tags
			processNewHashtags(session, message, newHashtags);

			// Now that we have inserted all new hash tags with generated IDs, we can now
			// add them to message

			for (String tag : incomingHashtags) {

				String messageTag = "#" + tag;

				Long hashTagId = getHashTagId(messageTag);

				newHashSet.add(new HashTag(hashTagId, messageTag));

			}

			message.setHashtags(newHashSet);

			transaction = session.beginTransaction();
			session.update(message);

			transaction.commit();


		} catch (Exception e) {
			System.out.println(e.getStackTrace());
			System.err.println("An error occured when trying to update a Message-.\n" + e.getMessage());

			if (transaction != null)
				transaction.rollback();
			
			//TODO: Delete newly added hash tags??

			return false;
		}
		
		return true;
	}

	/**
	 * This private method processes deleted hash tags
	 * 
	 * @param session
	 * @param message
	 * @param deletedHashtags
	 * @throws IOException
	 */
	private void processDeletedHashtags(Session session, Message message, List<String> deletedHashtags) throws IOException
	{
		for (String tag : deletedHashtags)
		{
			String messageTag = "#" + tag;

			Long hashTagId = getHashTagId(session, messageTag);
			if (hashTagId != null)
			{
				session.createNativeQuery("delete from message_hashtag where message_id = :message_id and hashtag_id = :hashtag_id")
						.setParameter("message_id", message.getId())
						.setParameter("hashtag_id", hashTagId)
						.executeUpdate();
				
				Long count = (Long) session.createNativeQuery("select Count(*) from message_hashtag where hashtag_id = :hashtag_id")
					.setParameter("hashtag_id", hashTagId)
					.getSingleResult();
				
				if (count == 0)
				{
					session.createQuery("delete from " + hashTagId.getClass().getSimpleName() + " where id = :hashtag_id")
						.setParameter("hashtag_id", hashTagId);
				}
			}
		}
	}

	/**
	 * This private method processes new hast tags
	 * 
	 * @param session
	 * @param message
	 * @param newHashtags
	 * @throws IOException
	 */

	private ArrayList<HashTag> processNewHashtags(Session session, Message message, List<String> newHashtags)
			throws IOException
	{

		ArrayList<HashTag> newInsertedHashTags = new ArrayList<HashTag>();
		/*
		 * (1) Add entries to [hashtag] if they don't already exist (2) Add entries to
		 * [message_hashtag]
		 */

		for (String tag : newHashtags)
		{

			String messageTag = "#" + tag;

			Long hashTagId = getHashTagId(messageTag);

			// Entirely new hash tag
			if (hashTagId == null)
			{

				// Insert new entry into [hashtag]
				HashTag hashTag = new HashTag();
				hashTag.setId(null);
				hashTag.setTag(messageTag);

				/*
				 * IMPORTANT: Doing it in a different session since I will not know the new hash
				 * tag ID before commit is called. Due to this, in case of roll back, need to
				 * delete these newly added hash tag entries from [hashtag]
				 * 
				 */
				saveHashTag(hashTag);

				// Inserting the to the list to be sent back
				newInsertedHashTags.add(hashTag);

				// Get generated hash Tag
				// hashTagId = hashTag.getId();

			}

			/*
			 * EntityManager eniEntityManager =
			 * session.getEntityManagerFactory().createEntityManager();
			 * 
			 * eniEntityManager.
			 * createNativeQuery("INSERT INTO message_hashtag (message_id, hashtag_id) VALUES (?, ?)"
			 * ) .setParameter(1, message.getId()).setParameter(2,
			 * hashTagId).executeUpdate();
			 */

			// MessageHashtag messageHashtag = new MessageHashtag(message.getId(),hashTagId
			// );

			// Insert them into [message_hashtag
			// session.save(new MessageHashtag(message.getId(),hashTagId));

		}

		return newInsertedHashTags;
	}

	public boolean saveHashTag(HashTag hashTag)
	{
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession())
		{
			transaction = session.beginTransaction();

			session.save(hashTag);

			transaction.commit();
		}
		catch (Exception e)
		{
			System.err.println("An error occured when trying to save a HashTag Message-.\n" + e.getMessage());

			if (transaction != null) transaction.rollback();

			return false;
		}

		return true;
	}

	/**
	 * Private method to get the hash tag id, given an actual hash tag
	 * 
	 * @param session
	 * @param messageTag
	 * @return
	 * @throws IOException
	 */
	private Long getHashTagId(Session session, String messageTag) throws IOException
	{

		List<HashTag> hashTagList = null;

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			// Should always return size 1
			hashTagList = session.createQuery("FROM HashTag WHERE tag = :tag", HashTag.class)
					.setParameter("tag", messageTag).list();

		} catch (Exception e) {
			System.err.println("An error occured when trying to retrieve hashtag id-.\n" + e.getMessage());

		}
		return (hashTagList != null && hashTagList.size() == 1) ? hashTagList.get(0).getId() : null;
	}

	/**
	 * Private method to get all the has tags in a message text
	 * 
	 * @param message
	 * @return
	 */
	private ArrayList<String> getHashTags(String message)
	{

		Pattern MY_PATTERN = Pattern.compile("#(\\S+)");
		Matcher mat = MY_PATTERN.matcher(message);

		ArrayList<String> hashTagList = new ArrayList<String>();

		while (mat.find())
		{
			System.out.println(mat.group(1));
			hashTagList.add(mat.group(1));
		}
		return hashTagList;
	}

	@Override
	public boolean delete(long id)
	{
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession())
		{
			transaction = session.beginTransaction();

			session.createQuery("delete " + Message.class.getSimpleName() + " where id = :id").setParameter("id", id)
					.executeUpdate();

			transaction.commit();
		}
		catch (Exception e)
		{
			System.err.println("An error occured when trying to delete a Message.\n" + e.getMessage());

			if (transaction != null) transaction.rollback();

			return false;
		}

		return true;
	}
}
