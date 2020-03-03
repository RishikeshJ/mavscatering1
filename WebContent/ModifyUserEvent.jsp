<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="myStyle.css" rel="stylesheet" type="text/css" />
<title>Update event</title>
</head>
<body>
<div class="logo"><h1><a href="<c:url value='/eventsummary.jsp' />">Mavs Catering System</a></h1></div>
<a href="<c:url value='/userController?action=logout' />"><span>Logout</span></a>
<h2>Modify event</h2>
<input name="errMsg"  value="<c:out value='${EventErrorMsgs.EventErrorMsgs}'/>" type="text"  style ="background-color: white; color: red; border: none; width:800px" disabled="disabled">

<table>
<tr>
   <td>
    <form name="Modifyevent" action="<c:url value='/eventController?action=Modify_Event_Details' />" method="post">
    <table style="width: 1200px; ">
    
     
   <tr>
    <td> Event ID (*): </td>
    <td> <input name="eventID" value="<c:out value='${EVENT.eventID}'/>" type="text">  </td>
    </tr>
   <tr>
    <td> Last Name (*): </td>
    <td> <input name="lastname" value="<c:out value='${EVENT.lastName}'/>" type="text">  </td>
    </tr>
    
    
	<tr>
	<td> first name: </td>
	<td> <input name="firstname" value="<c:out value='${EVENT.firstName}'/>" type="text">  </td>
   	</tr>
   	
   		
	<tr>
	<td> date: </td>
    <td> <input name="date" value="<c:out value='${EVENT.date}'/>" type="text">  </td>
 	</tr> 	
   	
	<tr>
	<td>start time: </td>
	<td> <input name="startTime" value="<c:out value='${EVENT.startTime}'/>" type="text">  </td>
  	</tr>
	<tr>
	<td>duration: </td>
	<td> <input name="duration" value="<c:out value='${EVENT.duration}'/>" type="text">  </td>
   	</tr>
	<tr>
	<td>hall name: </td>
	<td> <input name="hallName" value="<c:out value='${EVENT.hallName}'/>" type="text">  </td>  	
   	</tr>
	<tr>
	<td>estimated attendees: </td>
	<td> <input name="est" value="<c:out value='${EVENT.estAttendees}'/>" type="text">  </td>
   	</tr>
	<tr>
	<td>event name: </td>
	<td> <input name="eventName" value="<c:out value='${EVENT.eventName}'/>" type="text">  </td>
 	</tr>
 
	<tr>
	<td> food type: </td>
	<td> <input name="foodType" value="<c:out value='${EVENT.foodType}'/>" type="text">  </td>
    </tr>
	<tr>
	<td> meal: </td>
	<td> <input name="meal" value="<c:out value='${EVENT.meal}'/>" type="text">  </td>
   	</tr>
	<tr>
	<td> meal formality: </td>
	<td> <input name="mealFormality" value="<c:out value='${EVENT.mealFormality}'/>" type="text">  </td>
   	</tr>
	<tr>
	<td> drink type: </td>
	<td> <input name="drinkType" value="<c:out value='${EVENT.drinkType}'/>" type="text">  </td>
   	</tr>
	<tr>
	<td> entertainment items: </td>
	<td> <input name="entertainmentItems" value="<c:out value='${EVENT.entertainmentItems}'/>" type="text">  </td>
  	</tr>
  <%-- 	<tr>
	<td>event Status: </td>
	<td> <input name="eventStatus" value="<c:out value='${EVENT.eventStatus}'/>" type="text">  </td>
 	<td> <input name="lastNameError"  value="<c:out value='${errorMsgs.lastNameError}'/>" type="text"  style ="background-color: white; color: red; border: none; width: 800px"   disabled="disabled"> </td>
 	</tr> --%>
	
  
    <tr>
    <td colspan="2"><i>(*) Mandatory field</i></td>
    </tr>
    </table>
    <input type="submit" value="Modify" onclick="return (confirm('Modify selected Event?'))"><!-- Javascript Popup Confirmation -->
    </form>
</td>
</tr>
</table>
</body>
</html>