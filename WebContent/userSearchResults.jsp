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
      <div class="menu_nav">
      </div>
  </div>
  <input name="errMsg"  value="<c:out value='${errorMsgs}'/>" class="errorPane">
     <div class="mainbar"><div class="submb"></div></div>
      
 <form action="<c:url value='userController?action=listSpecificUser' />" method="post">         
         
       <table border="1" class="myTable"> 
			<tr class="myTableRow"> 
				<th class="myTable20">Last Name</th>
				<th class="myTable35">First Name</th> 
				<th class="myTable20">User Name</th>
				<th class="myTable30">Role</th> 
			</tr>

 		<c:forEach items="${USERS}" var="item" varStatus="status">
			<tr class="myTableRow">
			<td class="myTable20 "><c:out value="${item.lastname}" /></td>
			<td class="myTable35 "><c:out value="${item.firstname}" /></td>
			<td class="myTable20 "><c:out value="${item.username}" /></td>
			<td class="myTable30 "><c:out value="${item.role}" /></td>
            <td> <a href="<c:url value='/userController?action=listSpecificUser&id=${item.username}' />">View</a></td>
            <td> <a href="<c:url value='/userController?action=changeRole1&id=${item.username}'/>">Modify Role</a></td>
            <td> <a href="<c:url value='/userController?action=deleteSpecificUser&id=${item.username}&id1=${item.lastname}'/>" onclick="if (confirm('Delete selected item?')){return true;}else{event.stopPropagation(); event.preventDefault();};">
            Delete</a></td><!-- Javascript Popup Confirmation -->
			</tr>
		</c:forEach>
 </table>
 </form>
 </body>
</html>