<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Modify User Profile</title>
</head>
<body>
<div class="logo"><h1><a href="<c:url value='/userController?action=refreshPage&id=USER.lastname' />">Mavs Catering System</a></h1></div>
<a href="<c:url value='/userController?action=logout' />"><span>Logout</span></a>
<h2>Modify User Profile</h2>
<input name="errMsg"  value="<c:out value='${errorMsgs.errorMsgs}'/>" type="text"  style ="background-color: white; color: red; border: none; width:800px" disabled="disabled">
<table>
  <tr>
   <td>
    <form name="registrationForm" action="<c:url value='/userController?action=changeRole&id="${item.username}"' />" method="post">
    <table style="width: 1200px; ">
    
    
    <tr>
    <td> Username (*): </td>
    <td> <input name="username" value="<c:out value='${USER.username}'/>" type="text" >  </td>
  	<td> <input name="usernameError"  value="<c:out value='${errorMsgs.usernameError}'/>" type="text"  style ="background-color: white; color: red; border: none; width: 800px"   disabled="disabled"> </td>
    </tr>
    
    <tr>
    <td> Role: (*): </td>
    <td> <input name="role" value="<c:out value='${USER.role}'/>" type="text" >  </td>
  	<td> <input name="roleError"  value="<c:out value='${errorMsgs.roleError}'/>" type="text"  style ="background-color: white; color: red; border: none; width: 800px"   disabled="disabled" > </td>
    </tr>
    
    <tr>
    <td colspan="2"><i>(*) Mandatory field</i></td>
    </tr>
    </table>
    <input type="submit" value="Modify" onclick="if (confirm('Modify role?')){return true;}else{event.stopPropagation(); event.preventDefault();};"><!-- Javascript Popup Confirmation -->
    </form>
</td>
</tr>
</table>
</body>
</html>