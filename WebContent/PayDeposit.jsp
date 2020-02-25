<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>


</head>
<body>
<p>Deposit Amount is : <c:out value="${DepositValue}" /></p>
<a href="<c:url value='/userController?action=logout' />"><span>Logout</span></a>
  <form action="/mav_catering/eventController?action=PayDeposit" method="post">          
       <table border="1" class="myTable"> 
			<tr>
			<th class="myTableHead" style="padding-right: 35px; text-align: left">First Name</th>
			<td class="myTableCell" style="padding-right: 20px; "><c:out value="${EVENT.firstName}" /></td>
			</tr>
			
			<tr>
			<th class="myTableHead" style="padding-right: 20px; text-align: left">Last Name</th>
			<td class="myTableCell" style="padding-right: 20px; "><c:out value="${EVENT.lastName}" /></td>
			</tr>
			
			<tr>
			<th class="myTableHead" style="padding-right: 20px; text-align: left">Date</th>
			<td class="myTableCell" style="padding-right: 20px; "><c:out value="${EVENT.date}" /></td>
			</tr>
			
			<tr>
			<th class="myTableHead" style="padding-right: 30px; text-align: left">Start Time</th>
			<td class="myTableCell" style="padding-right: 20px; "><c:out value="${EVENT.startTime}" /></td>
			</tr>
			
			<tr>
			<th class="myTableHead" style="padding-right: 30px; text-align: left">Duration</th>
			<td class="myTableCell" style="padding-right: 20px; "><c:out value="${EVENT.duration}" /></td>
			</tr>
			
			<tr>
			<th class="myTableHead" style="padding-right: 30px; text-align: left">Hall Name</th>
			<td class="myTableCell" style="padding-right: 20px; "><c:out value="${EVENT.hallName}" /></td>
			</tr>
			
			<tr>
			<th class="myTableHead" style="padding-right: 30px; text-align: left">Attendees</th>
			<td class="myTableCell" style="padding-right: 20px; "><c:out value="${EVENT.estAttendees}" /></td>
			</tr>
			
			<tr>
			<th class="myTableHead" style="padding-right: 30px; text-align: left">Event Name</th>
			<td class="myTableCell" style="padding-right: 20px; "><c:out value="${EVENT.eventName}" /></td>
			</tr>
			
			<tr>
			<th class="myTableHead" style="padding-right: 30px; text-align: left">Food Type</th>
			<td class="myTableCell" style="padding-right: 20px; "><c:out value="${EVENT.foodType}" /></td>
			</tr>
			
			<tr>
			<th class="myTableHead" style="padding-right: 30px; text-align: left">Meal Formality</th>
			<td class="myTableCell" style="padding-right: 20px; "><c:out value="${EVENT.meal}" /></td>
			</tr>
			
			<tr>
			<th class="myTableHead" style="padding-right: 30px; text-align: left">Meal</th>
			<td class="myTableCell" style="padding-right: 20px; "><c:out value="${EVENT.mealFormality}" /></td>
			</tr>
			
			<tr>
			<th class="myTableHead" style="padding-right: 30px; text-align: left">Drink Type</th>
			<td class="myTableCell" style="padding-right: 20px; "><c:out value="${EVENT.drinkType}" /></td>
			</tr>
			
			<tr>
			<th class="myTableHead" style="padding-right: 30px; text-align: left">Entertainment Items</th>
			<td class="myTableCell" style="padding-right: 20px; "><c:out value="${EVENT.entertainmentItems}" /></td>
			</tr>
			
				<%-- <tr><td class="myTableCell" style="padding-right: 20px; "><c:out value="${EVENT.eventID}" /></td></tr> --%>
				
 </table>
 </form>
 
 <form name="EventDepositForm" action="eventController?action=payDeposit" method="post">
    <table>
 <tr>
	<td> Card Number : </td>
	<td><input type="text" id="idccNum" name="idccNum"></td>
	<td style="padding:0px"><input name="invalidCCNum"  value="<c:out value='${CardErrors.invalidCCNum}'/>" type="text"  style ="border: none;width:800px"></td>	        
</tr>
<tr>
	<td> Security Code : </td>
	<td><input type="text" id="idinvalidpin" name="idinvalidpin"></td>
	<td style="padding:0px"><input name="invalidpin"  value="<c:out value='${CardErrors.invalidpin}'/>" type="text"  style ="border: none;width:800px"></td>	        
</tr>
<tr>
	<td> Exp Date : </td>
	<td><input type="date" id="idexpDate" name="idexpDate"></td>
	<td style="padding:0px"><input name="invalidExpDate"  value="<c:out value='${CardErrors.invalidExpDate}'/>" type="text"  style =" border: none;width:800px"></td>		        
</tr>
   
    </table>
      <input name="NextBtn" type="submit" value="PayDeposit">
 </form>

 
</body>
</html>