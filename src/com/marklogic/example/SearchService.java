package com.marklogic.example;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.DatabaseClientFactory.Authentication;
import com.marklogic.client.io.SearchHandle;
import com.marklogic.client.query.MatchDocumentSummary;
import com.marklogic.client.query.MatchLocation;
import com.marklogic.client.query.MatchSnippet;
import com.marklogic.client.query.QueryManager;
import com.marklogic.client.query.StringQueryDefinition;

@Component
public class SearchService {

	private static String ML_HOST = "localhost";
	private static int ML_PORT = 8011;
	private static String ML_USER = "admin";
	private static String ML_PASSWORD = "admin";

	private DatabaseClient client;

	public SearchService() {
		client = DatabaseClientFactory.newClient(ML_HOST, ML_PORT, ML_USER, ML_PASSWORD, Authentication.DIGEST);
	}

	public SearchResultObject search(String searchString) {
		QueryManager queryMgr = client.newQueryManager();
		StringQueryDefinition qd = queryMgr.newStringDefinition();
		qd.setCriteria(searchString);
		SearchHandle resultsHandle = queryMgr.search(qd, new SearchHandle());
		MatchDocumentSummary[] matches = resultsHandle.getMatchResults();
		ArrayList<SearchPojo> results = new ArrayList<SearchPojo>();
		for (MatchDocumentSummary match : matches) {
			MatchLocation m = match.getMatchLocations()[0];
			String snippet = "";
			for (MatchSnippet ms : m.getSnippets()) {
				if (ms.isHighlighted())
					snippet += "<b>" + ms.getText() + "</b>";
				else
					snippet += ms.getText();
			}
			results.add(new SearchPojo(match.getUri(), snippet));
		}
		return new SearchResultObject(
				results.toArray(new SearchPojo[matches.length]),
				resultsHandle.getTotalResults()
				);
	}

	public static class SearchResultObject{
		long searchCount;
		SearchPojo[] results;
		
		SearchResultObject(SearchPojo[] results, long searchCount){
			this.results = results;
			this.searchCount = searchCount;
		}
		
		public long getSearchCount(){return searchCount;}
		
		public SearchPojo[] getResults(){return results;}
		
		
	}
	public static class SearchPojo {
		private String URI;
		private String summary;

		public SearchPojo(String URI, String summary) {
			this.URI = URI;
			this.summary = summary;
		}

		public String getURI() {
			return URI;
		}

		public String getSummary() {
			return summary;
		}
		
		@Override
		public String toString() {
			return "SearchPojo [URI=" + URI + ", summary=" + summary + "]";
		}		
	}
	
	public static void main(String[] args){
		SearchService service = new SearchService();
		SearchPojo[] results = service.search("shoulder").getResults();
		for(SearchPojo result : results){
			System.out.println(result);
		}
		
	}

}
