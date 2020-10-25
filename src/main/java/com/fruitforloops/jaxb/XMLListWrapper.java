package com.fruitforloops.jaxb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class XMLListWrapper<T> implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private List<T> list;
	
	public XMLListWrapper()
	{
		list = new ArrayList<T>();
	}
	
	public XMLListWrapper(ArrayList<T> other)
	{
		list = other;
	}
	
	@XmlElementWrapper
	@XmlElement(name="element")
	public List<T> getList()
	{
		return list;
	}
	
	public void setList(ArrayList<T> list)
	{
		this.list = list;
	}
}
