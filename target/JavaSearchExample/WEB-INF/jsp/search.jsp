<%@ page import="com.marklogic.example.SearchService.SearchResultObject"%>
<%@ page import="com.marklogic.example.SearchService.SearchPojo"%>
<html>
<head>
<title>Search in JSP Demo</title>
<link rel="stylesheet" type="text/css" href="css/bootstrap.css" />
<style type="text/css">
.top-buffer {
	margin-top: 20px
}
</style>
</head>
<body>
	<div class="container">
		<br>` <br>
		<div class="row">
			<div class="col-xs-6 col-xs-offset-1" style="padding-left: 15px">
				<h2>JSP Search Demo</h2>
			</div>
		</div>

		<div class="top-buffer row">
			<form class="form-inline" method="GET" action="search.html">
				<div class="form-group col-xs-10 col-xs-offset-1">
					<label class="control-label" for="search">Search : </label> <input
						class="form-control" id="search" type="text" name="searchString" />
					<input class="form-control" type="submit" value="Search" />
				</div>
			</form>
		</div>
		<%
			SearchResultObject results = (SearchResultObject) request.getAttribute("results");
		%>
		<div class="top-buffer row">
			<div class="col-xs-6 col-xs-offset-1">
				Results :
				<%=results.getSearchCount()%>
			</div>
		</div>

		<%
			for (SearchPojo result : results.getResults()) {
		%>
		<div class="row top-buffer">
			<div class="col-xs-6 col-xs-offset-1">
				<a href="<%=result.getURI()%>"><%=result.getURI()%></a>

				<div class="top-buffer"><%=result.getSummary()%></div>
			</div>

		</div>
		<%
			}
		%>
	</div>
</body>
</html>