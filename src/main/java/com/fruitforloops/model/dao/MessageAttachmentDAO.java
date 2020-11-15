package com.fruitforloops.model.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.fruitforloops.HibernateUtil;
import com.fruitforloops.model.MessageAttachment;

public class MessageAttachmentDAO implements IDAO<MessageAttachment>
{

	@Override
	public MessageAttachment get(long id)
	{
		MessageAttachment attachment = null;
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();

			attachment = session.get(MessageAttachment.class, id);

			transaction.commit();
		} 
		catch (Exception e) 
		{
			System.err.println("An error occured when trying to save a Message.\n" + e.getMessage());

			if (transaction != null)
				transaction.rollback();

			return attachment;
		}

		return attachment;
	}

	@Override
	public List<MessageAttachment> getAll()
	{
		//
		return null;
	}

	@Override
	public boolean save(MessageAttachment object)
	{
		//
		return false;
	}

	@Override
	public boolean update(MessageAttachment object)
	{
		//
		return false;
	}

	@Override
	public boolean delete(long id)
	{
		//
		return false;
	}

}
