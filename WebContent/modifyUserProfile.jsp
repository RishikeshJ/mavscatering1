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
    <form name="registrationForm" action="<c:url value='/userController?action=modifyUserProfile' />" method="post">
    <table style="width: 1200px; ">
    
    
    <tr>
    <td> Username (*): </td>
    <td> <input name="username" value="<c:out value='${USER.username}'/>" type="text">  </td>
  	<td> <input name="usernameError"  value="<c:out value='${errorMsgs.usernameError}'/>" type="text"  style ="background-color: white; color: red; border: none; width: 800px"   disabled="disabled"> </td>
    </tr>
    
    <tr>
    <td> Role: (*): </td>
    <td> <input name="role" value="<c:out value='${USER.role}'/>" type="text" >  </td>
  	<td> <input name="roleError"  value="<c:out value='${errorMsgs.roleError}'/>" type="text"  style ="background-color: white; color: red; border: none; width: 800px"   disabled="disabled" > </td>
    </tr>
    
   	<tr>
    <td> UTA ID (*): </td>
    <td> <input name="utaid" value="<c:out value='${USER.utaid}'/>" type="text" >  </td>
  	<td> <input name="utaIdError"  value="<c:out value='${errorMsgs.utaIdError}'/>" type="text"  style ="background-color: white; color: red; border: none; width: 800px"   disabled="disabled" > </td>
    </tr>
    
    <tr>
    <td> First Name (*): </td>
    <td> <input name="firstname" value="<c:out value='${USER.firstname}'/>" type="text" > </td>
  	<td> <input name="firstNameError"  value="<c:out value='${errorMsgs.firstNameError}'/>" type="text"  style ="background-color: white; color: red; border: none; width: 800px"  disabled="disabled" > </td>
    </tr>

    <tr>
    <td> Last Name (*): </td>
    <td> <input name="lastname" value="<c:out value='${USER.lastname}'/>" type="text" > </td>
  	<td> <input name="lastNameError"  value="<c:out value='${errorMsgs.lastNameError}'/>" type="text"  style ="background-color: white; color: red; border: none; width: 800px"  disabled="disabled" > </td>
    </tr>
    
    <tr>
    <td> Phone : </td>
    <td> <input name="phone" value="<c:out value='${USER.phone}'/>" type="text" > </td>
  	<td> <input name="phoneError"  value="<c:out value='${errorMsgs.phoneError}'/>" type="text"  style ="background-color: white; color: red; border: none; width: 800px"  disabled="disabled" > </td>
    </tr>
  
    <tr>
    <td> Email : </td>
    <td> <input name="email" value="<c:out value='${USER.email}'/>" type="text" >  </td>
  	<td> <input name="emailError"  value="<c:out value='${errorMsgs.emailError}'/>" type="text"  style ="background-color: white; color: red; border: none; width: 800px"   disabled="disabled" > </td>
    </tr>
    
    <tr>
    <td> Street Number : </td>
    <td> <input name="streetnumber" value="<c:out value='${USER.streetnumber}'/>" type="text" >  </td>
  	<td> <input name="streetNumberError"  value="<c:out value='${errorMsgs.streetNumberError}'/>" type="text"  style ="background-color: white; color: red; border: none; width: 800px"   disabled="disabled" > </td>
    </tr>
    
    <tr>
    <td> Street Name : </td>
    <td> <input name="streetname" value="<c:out value='${USER.streetname}'/>" type="text" >  </td>
  	<td> <input name="streetNameError"  value="<c:out value='${errorMsgs.streetNameError}'/>" type="text"  style ="background-color: white; color: red; border: none; width: 800px"   disabled="disabled" > </td>
    </tr>
    
    <tr>
    <td> City : </td>
    <td> <input name="city" value="<c:out value='${USER.city}'/>" type="text" >  </td>
  	<td> <input name="cityError"  value="<c:out value='${errorMsgs.cityError}'/>" type="text"  style ="background-color: white; color: red; border: none; width: 800px"   disabled="disabled" > </td>
    </tr>
    
    <tr>
    <td> State : </td>
    <td> <input name="state" value="<c:out value='${USER.state}'/>" type="text" >  </td>
  	<td> <input name="stateError"  value="<c:out value='${errorMsgs.stateError}'/>" type="text"  style ="background-color: white; color: red; border: none; width: 800px"   disabled="disabled" > </td>
    </tr>
    
    <tr>
    <td> Zipcode : </td>
    <td> <input name="zipcode" value="<c:out value='${USER.zipcode}'/>" type="text" >  </td>
  	<td> <input name="zipcodeError"  value="<c:out value='${errorMsgs.zipcodeError}'/>" type="text"  style ="background-color: white; color: red; border: none; width: 800px"   disabled="disabled" > </td>
    </tr>
    
    
    <tr>
    <td colspan="2"><i>(*) Mandatory field</i></td>
    </tr>
    </table>
    <input type="submit" value="Modify" onclick="if (confirm('Modify Profile?')){return true;}else{event.stopPropagation(); event.preventDefault();};"><!-- Javascript Popup Confirmation -->
    </form>
</td>
</tr>
</table>
</body>
</html>