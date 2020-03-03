<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<a href="<c:url value='/userController?action=logout' />"><span>Logout</span></a>
<body>
<table>
  <tr>
   <td>
    <form name="EventRequestForm" action="userController?action=bookEvent" method="post">
    <table>
    	<tr>
		<td>Date: </td>
			<td>
				<input type="date" id="iddate" value="<c:out value='${CurrentDate}' />"  name="iddate">
				<td> <input name="selecteddatetimeerror"  value="<c:out value='${TIMEERROR.timeerror}'/>" type="text"  style ="color: red; border: none; width: 300px;"> </td>
	</tr>
	<tr>
		<td>Time: </td>
			<td>
				<input type="time" id="idtime" value="<c:out value='${CurrentTime}' />" name="idtime">
			</td>
	</tr>
    <tr>
	<td><input name="selecteddateerror"  value="<c:out value='${TIMEERROR.pastdateError}'/>" type="text"  style ="color: red; border: none; width: 300px;"></td>
	
	</tr>
    
    </table>
    
    <input name="NextBtn" type="submit" value="Next">
    </form>
</td>
</tr>
</table>
</body>
</html>