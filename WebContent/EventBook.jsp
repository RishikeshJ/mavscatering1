<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<h2>Event request form</h2>
<a href="<c:url value='/userController?action=logout' />"><span>Logout</span></a>
<table>
  <tr>
   <td>
    <form name="EventbookingForm" action="<c:url value='/eventController?action=Book_Event' />" method="post">
    <table style="width: 1200px; ">
    
    <tr>
    <td> Last Name: </td>
    <td> <input name="lastName" value="<c:out value='${lname}'/>" type="text" disabled="disabled"> </td>
    </tr>
    
	<tr>
    <td> First Name: </td>
    <td> <input name="firstName" value="<c:out value='${fname}'/>" type="text" disabled="disabled"> </td>
    </tr>
    
    <tr>
		<td>Date: </td>
			<td>
				<input type="date" id="iddate" name="iddate"  disabled="disabled" value="<c:out value='${date}'/>" >
				
			</td>
			<td><input name="selecteddateerror"  value="<c:out value='${errorMsgs.pastdateError}'/>" type="text"  style ="color: red; border: none; width: 300px;"></td>
			
	</tr>
	
	<tr>
		<td>Time: </td>
			<td>
				<input type="time" id="startTime" name="idtime" disabled="disabled" value="<c:out value='${time}'/>" >
			</td>
	</tr>
    

    <tr>
    <td> Duration: </td>
    <td><select name="duration" >
      <option id="2" value="2">2</option>
      <option id="3" value="3">3</option>
      <option id="4" value="4">4</option>
      <option id="5" value="5">5</option>
      <option id="6" value="6">6</option>
      <option id="7" value="7">7</option>
      <option id="8" value="8">8</option>
    </select>
    <td> <input name="durationmsg"  value="<c:out value='${errorMsgs.durationError}'/>" type="text"  style ="background-color: white; color: red; border: none; width: 800px"  disabled="disabled" > </td>
   	</tr>
    
    <tr>
    <td> Hall Name: </td>
    <td><select name="hallName" >
      <option id="Maverick" value="Maverick">Maverick</option>
      <option id="KC" value="KC">KC</option>
      <option id="Arlington" value="Arlington">Arlington</option>
      <option id="Shard" value="Shard">Shard</option>
      <option id="Liberty" value="Liberty">Liberty</option>
    </select>
    </td>
    <td> <input name="duplicateResMsg"  value="<c:out value='${errorMsgs.duplicateResMsg}'/>" type="text"  style ="background-color: white; color: red; border: none; width: 800px"  disabled="disabled" > </td>
    
   	</tr>
   	
   	<tr>
    <td> Estimated Attendees: </td>
    <td> <input name="estAttendees" value="<c:out value='${Event.estAttendees}'/>" type="text">  </td>
    <td> <input name="capacityError"  value="<c:out value='${errorMsgs.capacityError}'/>" type="text"  style ="background-color: white; color: red; border: none; width: 800px"  disabled="disabled"> </td>
  
    </tr>
   	
   	<tr>
    <td> Event Name: </td>
    <td> <input name="eventName" value="<c:out value='${Event.eventName}'/>" type="text">  </td>
    <td> <input name="eventNameError"  value="<c:out value='${errorMsgs.eventNameError}'/>" type="text"  style ="background-color: white; color: red; border: none; width: 800px"  disabled="disabled"> </td>
    </tr>
    
    <tr>
    <td> Food Type: </td>
    <td><select name="foodType" >
      <option id="American" value="American">American</option>
      <option id="Chinese" value="Chinese">Chinese</option>
      <option id="French" value="French">French</option>
      <option id="Greek" value="Greek">Greek</option>
      <option id="Indian" value="Indian">Indian</option>
      <option id="Italian" value="Italian">Italian</option>
      <option id="Japanese" value="Japanese">Japanese</option>
      <option id="Mexican" value="Mexican">Mexican</option>
      <option id="Pizza" value="Pizza">Pizza</option>
    </select>
   	</tr>
   	
   	<tr>
    <td> Meal: </td>
    <td><select name="meal" >
      <option id="Breakfast" value="Breakfast">Breakfast</option>
      <option id="Lunch" value="Lunch">Lunch</option>
      <option id="Supper" value="Supper">Supper</option>
    </select>
   	</tr>
   	
   	<tr>
    <td> Meal Formality: </td>
    <td><select name="mealFormality" >
      <option id="Formal" value="Formal">Formal</option>
      <option id="Informal" value="Informal">Informal</option>
    </select>
   	</tr>
   	
   	<tr>
    <td> Drink Type: </td>
    <td><select name="drinkType" >
      <option id="Standard" value="Standard">Non-Alcohol</option>
      <option id="Alcohol" value="Alcohol" selected="selected">Alcohol</option>
    </select>
   	</tr>
   	
   	<tr>
    <td> Entertainment items: </td>
    <td><select name="entertainmentItems" >
      <option id="Music" value="Music">Music</option>
      <option id="Non-Music" value="Non-Music">Non-Music</option>
    </select>
   	</tr>
   	
    </table>
    <input type="submit" value="Book Event">
    </form>
    <tr>
	<td><input name="selecteddateerror2"  value="<c:out value='${errorMsgs.samedayReserveError}'/>" type="text"  style ="color: red; border: none; width: 300px;"></td>
	</tr>
	<tr>
		<td><input name="selecteddateerror3"  value="<c:out value='${errorMsgs.sameweekReserverError}'/>" type="text"  style ="color: red; border: none; width: 400px;"></td>
	</tr>
	<tr>
		<td><input name="selecteddateerror4"  value="<c:out value='${errorMsgs.timeerror}'/>" type="text"  style ="color: red; border: none; width: 400px;"></td>
	</tr>
	
    
</td>
</tr>
</table>
</body>
</html>