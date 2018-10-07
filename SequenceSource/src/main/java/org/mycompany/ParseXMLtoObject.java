package org.mycompany;

import java.io.StringReader;

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

	

public class ParseXMLtoObject implements Processor{


	/**
	 * @param arg0
	 * @return
	 * @throws Exception
	 */
	public void process(Exchange arg0) throws Exception {
		/**
		 * @author Paul Vergilis
		 * TODO implement jaxb for com.isone.cim when on network
		 */
		
		String cimObject  = arg0.getIn().getBody(String.class);
		
		//Object o = xmlToObject(cimObject);
		//String cimMessage = objectToXML(o);
		arg0.getIn().setBody(cimObject);
	}

	/**
	 * 
	 * @param xmlString
	 * @return
	 * @throws JAXBException
	 */
	public static Object xmlToObject(String xmlString) throws JAXBException 
	{	
//		JAXBContext context  = JAXBContext.newInstance(com.isone.cim.ObjectFactory.class);
//		Unmarshaller unmarshaller = context.createUnmarshaller();		
//		Object o = (Object) unmarshaller.unmarshal(
//			new StreamSource(new StringReader(xmlString)));

		return null;
	}

	/**
	 * 
	 * @param o
	 * @return
	 * @throws JAXBException
	 */
	public static String objectToXML(Object o) throws JAXBException {
//		JAXBContext context = JAXBContext.newInstance(com.isone.cim.ObjectFactory.class);
//		Marshaller marshaller = context.createMarshaller();
//		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//
//		StringWriter writer = new StringWriter();
//		marshaller.marshal(o, writer);
//		return writer.toString();
		return null;
	}
	
	
	
}
