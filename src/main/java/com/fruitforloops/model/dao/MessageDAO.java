package com.fruitforloops.model.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.fruitforloops.HibernateUtil;
import com.fruitforloops.model.Message;

public class MessageDAO implements IDAO<Message> {
	@Override
	public boolean save(Message postMessage) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();

			session.save(postMessage);

			transaction.commit();
		} catch (Exception e) {
			System.err.println("An error occured when trying to save a Message-.\n" + e.getMessage());

			if (transaction != null)
				transaction.rollback();

			return false;
		}

		return true;
	}

	@Override
	public List<Message> getAll() {
		List<Message> messageList = new ArrayList<Message>();
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			messageList = session.createQuery("from Message", Message.class).list();
		} catch (IOException e) {
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
	public ArrayList<Message> getMessages(Date fDate, Date tDate, List<String> authors, List<String> hashtags) {

		ArrayList<Message> messagesList = new ArrayList<Message>();

		String query = "FROM Message WHERE created_date >= :fDate AND last_modified_date <= :tDate";
			
		if(authors != null && authors.size() > 0)
		{
			query += " AND author IN (:authors)";
		}

		if (hashtags != null && hashtags.size() > 0) {
			query += createMultiHashTagsSql(hashtags);
		}

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			messagesList = (ArrayList<Message>) session.createQuery(query, Message.class)
					.setParameterList("authors", authors).setParameter("fDate", fDate).setParameter("tDate", tDate)
					.list();
		} catch (IOException e) {
			System.err.println("Unable to get list of messages.\n" + e.getMessage());
		}
		return messagesList;
	}


	//Private method to create OR hashtag statements
	private String createMultiHashTagsSql(List<String> hashtags) {

		StringBuilder str = new StringBuilder();

		str.append(" AND (");

		for (String s : hashtags) {

			str.append("message_text LIKE '%" + s + "%' OR ");

		}
		String finalStr = str.toString();
		finalStr = (String) finalStr.subSequence(0, finalStr.length() - 3);
		return finalStr += ")";

	}
	
	@Override
	public Message get(long id) {
		//
		return null;
	}

	@Override
	public boolean update(Message message) {
		
		if(message == null) return false;
		
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
		
			session.beginTransaction();
			session.update(message);
			session.getTransaction().commit();
			
		} catch (IOException e) {
			System.err.println("Unable to update.\n" + e.getMessage());
			return false;
		}
		return true;
	}

	public boolean delete_2(long id) {
		
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
		
			Message message = (Message)session.get(Message.class,id);
			
			if(message == null) return false;
			
			session.beginTransaction();
		    session.delete(message);
			session.getTransaction().commit();
		 
		} catch (IOException e) {
			System.err.println("Unable to delete.\n" + e.getMessage());
			return false;
		}
		return true;
	}

	@Override
	public boolean delete(long id) 
	{
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) 
		{
			transaction = session.beginTransaction();

			session.createQuery("delete " + Message.class.getSimpleName() + " where id = :id")
				.setParameter("id", id).executeUpdate();

			transaction.commit();
		} 
		catch (Exception e) 
		{
			System.err.println("An error occured when trying to delete a Message.\n" + e.getMessage());

			if (transaction != null)
				transaction.rollback();

			return false;
		}

		return true;
	}
}
