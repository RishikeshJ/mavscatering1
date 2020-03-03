<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="myStyle.css" rel="stylesheet" type="text/css" />
<title>Assigned Events</title>
</head>
<body>
<nav>  
    <li><a href="<c:url value='/ManagerHome.jsp' />">Home</a></li>
</nav>
<a href="<c:url value='/userController?action=logout' />"><span>Logout</span></a>

<form > 		
	<table border="1" class="myTable">
		<tr class="myTableRow">
			<th class="myTable20"> event name </th>
			<th class="myTable20"> date </th>
			<th class="myTable20">start time</th>
			<th class="myTable20">duration</th>
			<th class="myTable20">hall name</th>
			<th class="myTable20">user last name</th>
			<th class="myTable20">user first name</th>
			<th class="myTable20">View Details</th>
		</tr>

	<c:forEach items="${EVENTS}" var="item" varStatus="status">
		<tr class="myTableRow">
			<td class="myTable20 "><c:out value="${item.eventName}" /></td>
			<td class="myTable20 "><c:out value="${item.date}" /></td>
			<td class="myTable20 "><c:out value="${item.startTime}" /></td>
			<td class="myTable30 "><c:out value="${item.duration}" /></td>
			<td class="myTable30 "><c:out value="${item.hallName}" /></td>
			<td class="myTable30 "><c:out value="${item.lastName}" /></td>
			<td class="myTable30 "><c:out value="${item.firstName}" /></td>
            <td> <a href="<c:url value='/eventController?action=viewSpecificEvent&id=${item.eventID}' />">View</a></td>
            <td> <a href="<c:url value='/eventController?action=goassignStaff&id=${item.eventID}' />"> Assign </a></td>
            <td> <a href="<c:url value='/eventController?action=goupdateevent&id=${item.eventID}' />">Modify</a></td>
			
		</tr>
	</c:forEach>

</table>
</form>
</body>
</html>