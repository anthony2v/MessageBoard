package com.fruitforloops.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import com.fruitforloops.jaxb.XMLListWrapper;

@XmlRootElement
@XmlSeeAlso({XMLListWrapper.class})
public class ResponseBean implements java.io.Serializable
{
	private static final long serialVersionUID = 1L;

	private int status;
	private String message;
	private Object data;

	public ResponseBean()
	{
	}

	public ResponseBean(int status, String message, Object data)
	{
		this.status = status;
		this.message = message;
		this.data = data;
	}

	@XmlAttribute
	public int getStatus()
	{
		return status;
	}

	public void setStatus(int status)
	{
		this.status = status;
	}

	@XmlElement
	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	@XmlElement
	public Object getData()
	{
		return data;
	}

	public void setData(Object data)
	{
		this.data = data;
	}
}
