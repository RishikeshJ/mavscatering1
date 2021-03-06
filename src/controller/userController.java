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
//			System.out.println("UTA ID : " + userDB.get(0).getRole());
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
		else if(action.equals("changeRole1")) {
			userDB = UserDAO.searchUser(request.getParameter("id"));
			selectedUser.setUser(	userDB.get(0).getUsername(),userDB.get(0).getPassword(),userDB.get(0).getLastname(),userDB.get(0).getFirstname(),
									userDB.get(0).getRole(),userDB.get(0).getUtaid(),userDB.get(0).getPhone(),userDB.get(0).getEmail(),
									userDB.get(0).getStreetnumber(),userDB.get(0).getStreetname(),userDB.get(0).getCity(),userDB.get(0).getState(),
									userDB.get(0).getZipcode());
			session.setAttribute("USER", selectedUser);
			session.setAttribute("lastnamer", userDB.get(0).getLastname());
			getServletContext().getRequestDispatcher("/changeRole.jsp").forward(request, response);
			
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
			if(userProfile.getRole().equals("Admin") || userProfile.getRole().equals("Caterer Manager")){
				user.validateUser("modifyUserProfile",user,uerrorMsgs);
			}
			else {
				user.validateUser("userProfile",user,uerrorMsgs);
			}
			uerrorMsgs.setErrorMsgs();
			if (!uerrorMsgs.getErrorMsgs().equals("")) {// if error messages
				getUserParam(request,user);
				session.setAttribute("errorMsgs", uerrorMsgs);
				url="/viewMyProfile.jsp";
			}
			else {
				if(user.getRole().equals("Admin")) {
					UserDAO.modifyUserProfile(user);
					url="/adminHomePage.jsp";			
				}
				else if(user.getRole().equals("Caterer Manager")) {
					UserDAO.modifyUserProfile(user);
					url="/ManagerHome.jsp";			
				}
				else if(user.getRole().equals("User")) {
					UserDAO.modifyUserProfile(user);
					url="/UserHome.jsp";			
				}
				else {
					UserDAO.modifyUserProfile(user);
					url="/HomePage.jsp";			
				}
			}
		}
		else if(action.equalsIgnoreCase("refreshPage")) {
			String lastname = request.getParameter("id");
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
				String firstname = EventDAO.getfirstname(user1.getUsername());
				String lastname = EventDAO.getlastname(user1.getUsername());
				session.setAttribute("fname", firstname);
				session.setAttribute("lname", lastname);
				session.setAttribute("date", selectedDate);
				session.setAttribute("time", selectedTime);
				session.setAttribute("Event", event);
				url = "/EventBook.jsp";
			}

		}
		else if(action.equalsIgnoreCase("deleteSpecificUser")) {
			String username = request.getParameter("id");
			String lastname = request.getParameter("id1");
			UserDAO.deleteUser(username);
			url="/userController?action=refreshPage&id="+lastname;
		}
		else {
	
			User newUser = new User();

			newUser.setUsername(request.getParameter("username"));
			newUser.setRole(request.getParameter("role"));
		
			System.out.println("Username: "+newUser.getUsername());
			System.out.println("Role: "+newUser.getRole());

			newUser.validateUser(action, newUser, uerrorMsgs);
			uerrorMsgs.setErrorMsgs();
			if (!uerrorMsgs.getErrorMsgs().equals("")) {// if error messages
				getUserParam(request,user);
				session.setAttribute("errorMsgs", uerrorMsgs);
				url="/changeRole.jsp";
			}
			else {
				UserDAO.modifyUser(newUser.getUsername(), newUser.getRole());
				url="/userController?action=refreshPage&id="+session.getAttribute("lastnamer");
			}
		}


		getServletContext().getRequestDispatcher(url).forward(request, response);
	}

}

