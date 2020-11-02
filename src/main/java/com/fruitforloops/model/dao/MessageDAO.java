package com.fruitforloops.model.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.fruitforloops.HibernateUtil;
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
		catch(Exception e)
		{
			System.err.println("An error occured when trying to save a Message-.\n" + e.getMessage());
			
			if (transaction != null)
				transaction.rollback();
			
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
		catch (IOException e)
		{
			System.err.println("Unable to get list of messages.\n" + e.getMessage());
		}
		
		return messageList;
	}

	@Override
	public Message get(long id)
	{
		//
		return null;
	}

	@Override
	public void update(Message object)
	{
		// TODO
	}

	@Override
	public void delete(Message object)
	{
		//
	}
}
