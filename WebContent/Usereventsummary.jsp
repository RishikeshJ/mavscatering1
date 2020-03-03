<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>List</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="myStyle.css" rel="stylesheet" type="text/css" />
<body>
    <div class="header_resize">
      <div class="logo"><h1><a href="<c:url value='/adminHomePage.jsp' />">Catering Management Application</a></h1></div>
      <a href="<c:url value='/userController?action=logout' />"><span>Logout</span></a>
  </div>
 
      
 <form  method="post">         
         
       <table border="1" class="myTable"> 
			<tr class="myTableRow"> 
				<th class="myTable35">Event ID</th> 
				<th class="myTable20">Event Name</th>
				<th class="myTable30">Duration</th> 
				<th class="myTable35">First Name</th> 
				<th class="myTable20">Last Name</th>
				<th class="myTable30">Starttime</th> 
				<th class="myTable30">Hall Name</th> 
				<th class="myTable30">Event Date</th> 
				<th class="myTable30">Est Atendees</th> 
				
				
			</tr>

 		<c:forEach items="${Event}" var="item" varStatus="status">
			<tr class="myTableRow">
			<td class="myTable20 "><c:out value="${item.eventID}" /></td>
			<td class="myTable35 "><c:out value="${item.eventName}" /></td>
			<td class="myTable20 "><c:out value="${item.duration}" /></td>
			<td class="myTable30 "><c:out value="${item.firstName}" /></td>
			<td class="myTable20 "><c:out value="${item.lastName}" /></td>
			<td class="myTable30 "><c:out value="${item.startTime}" /></td>
			<td class="myTable30 "><c:out value="${item.hallName}" /></td>
			<td class="myTable30 "><c:out value="${item.date}" /></td>
			<td class="myTable30 "><c:out value="${item.estAttendees}" /></td>
            <td> <a href="<c:url value='/eventController?action=viewSpecificEvent&id=${item.eventID}' />">View</a></td>
            <td> <a href="<c:url value='/eventController?action=goupdateeventUsr&id=${item.eventID}' />">Modify</a></td>
			
			</tr>
		</c:forEach>
 </table>
 </form>
 </body>
</html>