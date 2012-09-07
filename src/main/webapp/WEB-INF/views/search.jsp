<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<html>
<head>
<title>Search</title>
</head>
<body>
	<h1>Search Results</h1>

	<p>Result: ${snippet}.</p>
	<h4>Nominees:</h4>
	<table>
		<tr><th>Name</th><th>Award</th><th>Film Title</th><th>Winner</th><th>Year</th></tr>
		<c:forEach var="nominee" items="${nomineeList}">
			<tr>
				<td>${nominee.person.name}</td>
				<td>${nominee.award}</td>
				<td>${nominee.film.title}</td>
				<td>${nominee.isWinner().toString()}</td>
				<td>${nominee.year}</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>
