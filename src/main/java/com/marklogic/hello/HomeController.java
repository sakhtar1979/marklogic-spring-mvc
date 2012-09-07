package com.marklogic.hello;

import java.util.Comparator;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.marklogic.hello.service.OscarsSearch;
import com.marklogic.hello.service.OscarsDocumentManager;
import com.marklogic.wikipedia.Nominee;


/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	@Autowired
	OscarsDocumentManager manager;
	
	@Autowired
	OscarsSearch search;
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! the client locale is "+ locale.toString());
		
		Nominee nom = manager.getNomineeByUri("/oscars/10417445992776905175.xml");
		
		model.addAttribute("snippet", nom.getFilm().getTitle() );
		
		return "home";
	}
	
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public String search(@RequestParam("q") String q, Model model){
		
		model.addAttribute("snippet", search.getResultCount(q) );
		
		model.addAttribute("nomineeList", search.getNomineeList(q) );
		model.addAttribute("personList", search.getPersonList(q) );
		model.addAttribute("filmList", search.getFilmList(q) );
		
		return "search";
	}
}
