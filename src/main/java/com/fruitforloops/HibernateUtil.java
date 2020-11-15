package com.fruitforloops;

import java.io.IOException;
import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.cfg.Configuration;

import com.fruitforloops.model.HashTag;
import com.fruitforloops.model.Message;
import com.fruitforloops.model.MessageAttachment;

public final class HibernateUtil
{
	private static SessionFactory sessionFactory;
	
	private HibernateUtil()
	{
		// do not instantiate
	}
	
	public static SessionFactory getSessionFactory() throws IOException
	{
		if (sessionFactory == null)
		{
			StandardServiceRegistry registry = null;
			try
			{
				Configuration configuration = new Configuration();
				
				Properties hibernateConfig = new Properties();
				hibernateConfig.load(HibernateUtil.class.getClassLoader().getResourceAsStream("/WEB-INF/hibernate.cfg.properties"));
				configuration.setProperties(hibernateConfig);
				
				// mappings for annotated classes
				configuration.addAnnotatedClass(Message.class);
				configuration.addAnnotatedClass(MessageAttachment.class);
				configuration.addAnnotatedClass(HashTag.class);
				
				registry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
				sessionFactory = configuration.buildSessionFactory(registry);
			}
			catch (Exception e)
			{
				System.err.println("An error occured when trying to create hibernate session factory.\n" + e.getMessage());
				
				if (registry != null)
					StandardServiceRegistryBuilder.destroy(registry);
			}
		}
		
		return sessionFactory;
	}
}