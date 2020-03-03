<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="myStyle.css" rel="stylesheet" type="text/css" />
<title>Event Details</title>
</head>
<body>
<nav>  
    <li><a href="<c:url value='/HomePage.jsp'/>" >Back</a></li>
</nav>
<a href="<c:url value='/userController?action=logout' />"><span>Logout</span></a>
<table>
<tr>
	<td> last name: </td>
	<td><c:out value="${EVENTS.lastName}" /></td>
</tr>
	<tr>
	<td> first name: </td>
	<td><c:out value="${EVENTS.firstName}" /></td>
	</tr>
	<tr>
	<td> date: </td>
	<td><c:out value="${EVENTS.date}" /></td>
	</tr>
	<tr>
	<td>start time: </td>
	<td><c:out value="${EVENTS.startTime}" /></td>
	</tr>
	<tr>
	<td>duration: </td>
	<td><c:out value="${EVENTS.duration}" /></td>
	</tr>
	<tr>
	<td>hall name: </td>
	<td><c:out value="${EVENTS.hallName}" /></td>
	</tr>
	<tr>
	<td>est attendees: </td>
	<td><c:out value="${EVENTS.estAttendees}" /></td>
	</tr>
	<tr>
	<td>event name: </td>
	<td><c:out value="${EVENTS.eventName}" /></td>
	</tr>
	<tr>
	<td> food type: </td>
	<td><c:out value="${EVENTS.foodType}" /></td>
	</tr>
	<tr>
	<td> meal: </td>
	<td><c:out value="${EVENTS.meal}" /></td>
	</tr>
	<tr>
	<td> meal formality: </td>
	<td><c:out value="${EVENTS.mealFormality}" /></td>
	</tr>
	<tr>
	<td> drink type: </td>
	<td><c:out value="${EVENTS.drinkType}" /></td>
	</tr>
	<tr>
	<td> entertainment items: </td>
	<td><c:out value="${EVENTS.entertainmentItems}" /></td>
	</tr>
	<tr>
</tr>
</table>
</body>
</html>