<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Manager Home Page</title>
</head>
<body >
<div class="logo"><h1><a href="<c:url value='/' />">Campus Event Catering System</a></h1></div>
<a href="<c:url value='/userController?action=logout' />"><span>Logout</span></a>
<h1>Manager Home page</h1>
  <ul>
  	<li><a href="<c:url value='/eventController?action=eventsummary' />">View Caterer Event Summary</a></li>
  	<li><a href="<c:url value='/userController?action=viewProfile' />">View/Modify Profile</a></li>
  </ul>
<form action="<c:url value='/eventController?action=getDateforevent' />" method="post" >
<table>
<tr>
<td colspan=5 width=350px>Select a date range to view your events:</td>
</tr>
<tr>
	<td>Date: </td>
	<td><input type="date" id="iddate" name="iddate" value="<c:out value='${date}'/>" ></td>
	<td>Time: </td>
	<td><input type="time" id="startTime" name="idtime" value="<c:out value='${time}'/>" ></td>
  	<td><input type="submit" value="Submit"></td>
</tr>
</table>
</form>
  
</body>
</html>