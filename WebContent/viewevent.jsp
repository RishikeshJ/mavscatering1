<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Selected Event  </title>
</head>
<body>
     
    <div align="center">
        <table border="1" cellpadding="5">
            <caption><h2>Event summary</h2></caption>
           
                    <tr><td>Event ID:</td>            
                    <td><c:out value="${eventsummary.eventID}" /></td></tr>
            		
            		<tr><td>Last Name Name:</td>                    
                    <td><c:out value="${eventsummary.lastName}" /></td></tr>
                    
                    <tr><td>Fist Name:</td>                    
                    <td><c:out value="${eventsummary.firstName}" /></td></tr>
                    
                    <tr><td>Event Date:</td>                    
                    <td><c:out value="${eventsummary.date}" /></td></tr>
                    
                    <tr><td>Start time:</td>                    
                    <td><c:out value="${eventsummary.startTime}" /></td></tr>
                    
                    <tr><td>Duration:</td>                    
                    <td><c:out value="${eventsummary.duration}" /></td></tr>                    
                    
                    <tr><td>Hallname:</td>                    
                    <td><c:out value="${eventsummary.hallName}" /></td></tr>
                    
                    
                    <tr><td>Estimated Attendees:</td>
                    <td><c:out value="${eventsummary.estAttendees}" /></td></tr>                    
                    
                    
                    <tr><td>Event Name:</td>                    
                    <td><c:out value="${eventsummary.eventName}" /></td></tr>
                    
                    <tr><td>Food Type:</td>                    
                    <td><c:out value="${eventsummary.foodType}" /></td></tr>                    
                    
                    <tr><td>Meal:</td>                    
                    <td><c:out value="${eventsummary.meal}" /></td></tr>
                    
                    
                    <tr><td>Meal Formality:</td>                    
                    <td><c:out value="${eventsummary.mealFormality}" /></td></tr>                    
                    
                    <tr><td>Drink Type:</td>                    
                    <td><c:out value="${eventsummary.drinkType}" /></td></tr>
                    
                    
                    <tr><td>Entertainment Items:</td>
                    <td><c:out value="${eventsummary.entertainmentItems}" /></td></tr>                    
                    
        </table>
        
    </div>
</body>
</html>