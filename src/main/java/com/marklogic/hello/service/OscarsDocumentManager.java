package com.marklogic.hello.service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marklogic.client.document.XMLDocumentManager;
import com.marklogic.client.io.JAXBHandle;
import com.marklogic.wikipedia.Nominee;

@Service
public class OscarsDocumentManager{
	@Autowired
	private XMLDocumentManager xmlManager;

	public Nominee getNomineeByUri(String uri){
		
		JAXBContext context = null;
		try {
			context = JAXBContext.newInstance(Nominee.class);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JAXBHandle readHandle = new JAXBHandle(context);
		this.xmlManager.read(uri, readHandle);
		
		return (Nominee) readHandle.get();
	}
}
