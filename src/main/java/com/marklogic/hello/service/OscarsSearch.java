package com.marklogic.hello.service;

import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marklogic.client.admin.QueryOptionsManager;
import com.marklogic.client.admin.config.QueryOptionsBuilder;
import com.marklogic.client.admin.config.support.QueryOptionsConfiguration;
import com.marklogic.client.io.QueryOptionsHandle;
import com.marklogic.client.io.SearchHandle;
import com.marklogic.client.query.MatchDocumentSummary;
import com.marklogic.client.query.QueryManager;
import com.marklogic.client.query.StringQueryDefinition;
import com.marklogic.wikipedia.Nominee;
import com.marklogic.wikipedia.Nominee.Film;
import com.marklogic.wikipedia.Nominee.Person;

@Service
public class OscarsSearch {
	@Autowired
	private QueryManager queryManager;
	
	@Autowired
	private QueryOptionsManager queryOptionsManager;
	
	@Autowired
	OscarsDocumentManager manager;
	
	private static final String DEF = "OSCAR";
	
	private boolean initialized = false;
	
	private void init(){
		QueryOptionsBuilder qob = new QueryOptionsBuilder();
		QueryOptionsHandle queryOptions = new QueryOptionsHandle().withConstraints(
			qob.constraint("person-name", qob.value(qob.elementTermIndex(new QName("http://marklogic.com/wikipedia","name")))),
			qob.constraint("person-bday", qob.value(qob.elementTermIndex(new QName("http://marklogic.com/wikipedia","name")))),
			qob.constraint("nominee-award", qob.value(qob.elementAttributeTermIndex(new QName("http://marklogic.com/wikipedia","nominee"), new QName("award")))),
			qob.constraint("nominee-year", qob.value(qob.elementAttributeTermIndex(new QName("http://marklogic.com/wikipedia","nominee"), new QName("year")))),
			qob.constraint("oscar-award", qob.value(qob.elementAttributeTermIndex(new QName("http://marklogic.com/wikipedia","oscar"), new QName("award")))),
			qob.constraint("oscar-year", qob.value(qob.elementAttributeTermIndex(new QName("http://marklogic.com/wikipedia","oscar"), new QName("year")))),
			qob.constraint("oscar-winner", qob.value(qob.elementAttributeTermIndex(new QName("http://marklogic.com/wikipedia","oscar"), new QName("winner")))),
			qob.constraint("oscar-file-title", qob.value(qob.elementAttributeTermIndex(new QName("http://marklogic.com/wikipedia","oscar"), new QName("film-title"))))
		).withConfiguration(new QueryOptionsConfiguration().debug(false));
		
		queryOptionsManager.writeOptions(DEF, queryOptions);
		
		this.initialized = true;
	}
	
	public int getResultCount(String q){
		if(!initialized){
			this.init();
		}
		
		StringQueryDefinition def = queryManager.newStringDefinition(DEF);
		def.setCriteria(q);
		
		SearchHandle resultHandle = new SearchHandle();
		
		queryManager.search(def, resultHandle);
		
		return (int) resultHandle.getTotalResults();
	}
	
	public List<Nominee> getNomineeList(String q){
		if(!initialized){
			this.init();
		}
		
		StringQueryDefinition def = queryManager.newStringDefinition(DEF);
		def.setCriteria(q);
		
		SearchHandle resultHandle = new SearchHandle();
		
		queryManager.search(def, resultHandle);
		
		List<Nominee> result = new ArrayList<Nominee>();
		for(MatchDocumentSummary s : resultHandle.getMatchResults()){
			result.add(manager.getNomineeByUri(s.getUri()));
		}
		
		return result;
	}
	
	public List<Person> getPersonList(String q){
		if(!initialized){
			this.init();
		}
		
		StringQueryDefinition def = queryManager.newStringDefinition(DEF);
		def.setCriteria(q);
		
		SearchHandle resultHandle = new SearchHandle();
		
		queryManager.search(def, resultHandle);
		
		List<Person> result = new ArrayList<Person>();
		for(MatchDocumentSummary s : resultHandle.getMatchResults()){
			result.add(manager.getNomineeByUri(s.getUri()).getPerson());
		}
		
		return result;
	}
	
	public List<Film> getFilmList(String q){
		if(!initialized){
			this.init();
		}
		
		StringQueryDefinition def = queryManager.newStringDefinition(DEF);
		def.setCriteria(q);
		
		SearchHandle resultHandle = new SearchHandle();
		
		queryManager.search(def, resultHandle);
		
		List<Film> result = new ArrayList<Film>();
		for(MatchDocumentSummary s : resultHandle.getMatchResults()){
			result.add(manager.getNomineeByUri(s.getUri()).getFilm());
		}
		
		return result;
	}
}
