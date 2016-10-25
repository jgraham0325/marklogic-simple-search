package com.marklogic.example;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.marklogic.example.SearchService.SearchResultObject;
 

@Controller
public class Search {
 
	@Autowired
	SearchService searchService;
	
	@RequestMapping("/search")
	public ModelAndView search(@RequestParam(value = "searchString", defaultValue="") String searchString) {
		
		System.out.println("SearchString = "+searchString);
		
		SearchResultObject results = searchService.search(searchString);

		return new ModelAndView("search", "results", results);
	}
}