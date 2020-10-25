package com.fruitforloops.jaxb;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import com.fruitforloops.Constants;

/**
 * Adapter for jaxb xml binding.
 * Note: you need to add the formatter to the property of the object you wish to apply it to using the following annotation: 
 * @XmlJavaTypeAdapter(DateAdapter.class) 
 */
public class XMLDateAdapter extends XmlAdapter<String, Date>
{
	private static final SimpleDateFormat dateFormat = Constants.DATE_FORMAT_yyyy_MM_dd_HH_mm_ss_SSS;

	@Override
	public Date unmarshal(String v) throws Exception
	{
		return dateFormat.parse(v);
	}

	@Override
	public String marshal(Date v) throws Exception
	{
		return dateFormat.format(v);
	}
}
