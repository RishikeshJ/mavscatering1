package controller;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
//import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import data.UserDAO;
import model.User;
import model.Event;
import model.UserErrorMsgs;
import model.EventErrorMsgs;
import data.EventDAO;

/**
 * Servlet implementation class userController
 */
@WebServlet("/userController")
public class userController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public userController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	private void getUserParam (HttpServletRequest request, User user) {
		user.setUser(request.getParameter("username"),request.getParameter("password"),request.getParameter("lastname"),request.getParameter("firstname"),
				request.getParameter("role"),request.getParameter("utaid"),request.getParameter("phone"),request.getParameter("email"),
				request.getParameter("streetnumber"),request.getParameter("streetname"),request.getParameter("city"),request.getParameter("state"),
				request.getParameter("zipcode"));  
	}
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		String action = request.getParameter("action");
		ArrayList<User> userDB = new ArrayList<User>();
		User selectedUser = new User();
		if (action.equalsIgnoreCase("listSpecificUser")) {
			userDB = UserDAO.searchUser(request.getParameter("id"));
			System.out.println("UTA ID : " + userDB.get(0).getRole());
			selectedUser.setUser(	userDB.get(0).getUsername(),userDB.get(0).getPassword(),userDB.get(0).getLastname(),userDB.get(0).getFirstname(),
									userDB.get(0).getRole(),userDB.get(0).getUtaid(),userDB.get(0).getPhone(),userDB.get(0).getEmail(),
									userDB.get(0).getStreetnumber(),userDB.get(0).getStreetname(),userDB.get(0).getCity(),userDB.get(0).getState(),
									userDB.get(0).getZipcode());
			session.setAttribute("USER", selectedUser);
			getServletContext().getRequestDispatcher("/modifyUserProfile.jsp").forward(request, response);
		}
		else if(action.equalsIgnoreCase("EventRequest")) {
			 DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");  
			  LocalDateTime now = LocalDateTime.now();  
			   
			session.setAttribute("CurrentTime", dtf.format(now));
			session.setAttribute("CurrentDate", java.time.LocalDate.now());
			//2020-02-12
			//url = "/EventRequest.jsp";
			getServletContext().getRequestDispatcher("/EventRequest.jsp").forward(request, response);
		}
		else // redirect all other gets to post
			doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getParameter("action"), url="";
		HttpSession session = request.getSession();
		System.out.println(session.getAttributeNames());
		User user = new User();
		UserErrorMsgs uerrorMsgs = new UserErrorMsgs();
		session.removeAttribute("errorMsgs");
		
		if(action.equalsIgnoreCase("searchUser")) {
			session.removeAttribute("errorMsgs");
			String lastname = request.getParameter("lastname");
			session.setAttribute("User", user);
			user.setLastname(lastname);
			user.validateUser(action,user,uerrorMsgs);
			uerrorMsgs.setErrorMsgs();
			ArrayList<User> userInDB = new ArrayList<User>();
			if(!uerrorMsgs.getErrorMsgs().equals("")) {

				session.setAttribute("User", user);
				session.setAttribute("errorMsgs", uerrorMsgs);
				url="/searchUser.jsp";

			}
			else {
				userInDB = UserDAO.searchUsers(user.getLastname());
				session.setAttribute("USERS", userInDB);
				session.removeAttribute("User");
				url="/userSearchResults.jsp";	
			}
		}
		else if(action.equalsIgnoreCase("registerUser")) {
			getUserParam(request,user);
			user.validateUser(action,user,uerrorMsgs);
			uerrorMsgs.setErrorMsgs();
			session.setAttribute("User", user);
			if (!uerrorMsgs.getErrorMsgs().equals("")) {// if error messages
				getUserParam(request,user);
				session.setAttribute("errorMsgs", uerrorMsgs);
				url="/register.jsp";
			}
			else {
				
				UserDAO.registerUser(user);
				url="/login.jsp";
			}

		}
		else if(action.equalsIgnoreCase("modifyUserProfile")) {
			
			session.setAttribute("USER", user);
			getUserParam(request,user);
			User user1 = (User)session.getAttribute("currentUser");
			User userProfile = UserDAO.getUser(user1.getUsername());
			System.out.println("Usenrmae: "+user1.getUsername()+" "+user.getUsername());
			if(user1.getUsername().equals(user.getUsername())) {
				System.out.println("In admin0:");
				if(userProfile.getRole().equals("Admin") || userProfile.getRole().equals("Caterer Manager")){
					System.out.println("In admin:");
					user.validateUser("modifyUserProfile",user,uerrorMsgs);
				}
				else {
					user.validateUser("userProfile",user,uerrorMsgs);
					System.out.println("In register:");
				}
			}
			else {
				user.validateUser("userProfile",user,uerrorMsgs);
				System.out.println("In register:");
			}
			uerrorMsgs.setErrorMsgs();
			if (!uerrorMsgs.getErrorMsgs().equals("")) {// if error messages
				getUserParam(request,user);
				session.setAttribute("errorMsgs", uerrorMsgs);
				url="/modifyUserProfile.jsp";
			}
			else {

				System.out.println(user.getRole());
				System.out.println(user.getUsername());
				System.out.println(user1.getUsername());



				if(user1.getUsername().equals(user.getUsername()) && user.getRole().equals("Admin")) {
					UserDAO.modifyUserProfile(user);
					url="/adminHomePage.jsp";			
				}
				else if(user1.getUsername().equals(user.getUsername()) && user.getRole().equals("Caterer Manager")) {
					UserDAO.modifyUserProfile(user);
					url="/ManagerHome.jsp";			
				}
				else if(user1.getUsername().equals(user.getUsername()) && user.getRole().equals("User")) {
					UserDAO.modifyUserProfile(user);
					//userProfile = UserDAO.getUser(user1.getUsername());
					url="/UserHome.jsp";			
				}
				
				else if(user1.getUsername().equals(user.getUsername()) && user.getRole().equals("Caterer Staff")) {
					UserDAO.modifyUserProfile(user);
					url="/HomePage.jsp";			
				}
				else {
					UserDAO.modifyUserProfile(user);
					url="/userController?action=refreshPage&id="+user.getLastname();			
				}
				//UserDAO.modifyUser(user.getUsername(), user.getRole());
				//url="/userController?action=refreshPage&id="+user.getLastname();			

			}
		}
		else if(action.equalsIgnoreCase("refreshPage")) {
			String lastname = request.getParameter("id");
			System.out.println("Lastname : "+lastname);
			ArrayList<User> userInDB = new ArrayList<User>();
			userInDB = UserDAO.searchUsers(lastname);
			session.setAttribute("USERS", userInDB);
			session.removeAttribute("User");
			url="/userSearchResults.jsp";
		}
		else if(action.equalsIgnoreCase("viewProfile")) {
			User user1 = (User)session.getAttribute("currentUser");
			User userProfile = UserDAO.getUser(user1.getUsername());
			session.setAttribute("USER", userProfile);
			url="/viewMyProfile.jsp";
		}
		else if(action.equalsIgnoreCase("logout")) {
			if(session != null)
			    session.invalidate();
			//request.getRequestDispatcher("/login.jsp").forward(request,response);
			url="/login.jsp";
		}
		else if(action.equalsIgnoreCase("bookEvent") ) {
			if (request.getParameter("NextBtn")!=null) 
			{
				Event event = new Event();
				User user1 = (User)session.getAttribute("currentUser");
				String selectedDate = request.getParameter("iddate");
				String selectedTime = request.getParameter("idtime");
				EventErrorMsgs EerrorMsgs = new EventErrorMsgs();
				event.validateSelectedDateTime(selectedDate, selectedTime, EerrorMsgs);
					try {
						event.validateselectedDate(selectedDate, EerrorMsgs);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				session.setAttribute("TIMEERROR", EerrorMsgs);
				if (EerrorMsgs.getErrorMsg().equals("")) {
					session.removeAttribute("errorMsgs");
					String firstname = EventDAO.getfirstname(user1.getUsername());
					String lastname = EventDAO.getlastname(user1.getUsername());
					session.setAttribute("fname", firstname);
					session.setAttribute("lname", lastname);
					session.setAttribute("date", selectedDate);
					session.setAttribute("time", selectedTime);
					session.setAttribute("Event", event);
					url = "/EventBook.jsp";
				}
				else {
					url = "/EventRequest.jsp";
				}
				
				//getServletContext().getRequestDispatcher(url).forward(request, response);

			}

		}
		else if(action.equalsIgnoreCase("deleteSpecificUser")) {
			String username = request.getParameter("id");
			String lastname = request.getParameter("id1");
			UserDAO.deleteUser(username);
			url="/userController?action=refreshPage&id="+lastname;
		}


		getServletContext().getRequestDispatcher(url).forward(request, response);
	}

}

