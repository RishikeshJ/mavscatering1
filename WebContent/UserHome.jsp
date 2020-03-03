<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>User Home Page</title>

</head>
<body>
<header>
<a href="<c:url value='/userController?action=logout' />"><span>Logout</span></a>
        <h1>User Home Page</h1>
        <p>Welcome <c:out value="${userid}" /></p>
    </header>
    <table class="center">
    <tr>
				<td><a href="<c:url value='/userController?action=EventRequest' />" target="_top" style="margin-left: 118px;"> Request an event </a></td>
    </tr>
     <tr>
				<td><a href="<c:url value='/eventController?action=usereventsummary' />" target="_top" style="margin-left: 118px;"> View my Event Summary </a></td>
    </tr>
    <tr>
				<td><a href="<c:url value='/userController?action=viewProfile' />" target="_top" style="margin-left: 118px;"> Update Profile</a></td>
    </tr>
    </table>
</body>
</html>