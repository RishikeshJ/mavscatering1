package controller;

import java.io.IOException;
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
import model.UserErrorMsgs;

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
			
			user.validateUser(action,user,uerrorMsgs);
			uerrorMsgs.setErrorMsgs();
			
			if (!uerrorMsgs.getErrorMsgs().equals("")) {// if error messages
				getUserParam(request,user);
				session.setAttribute("errorMsgs", uerrorMsgs);
				url="/modifyUserProfile.jsp";
			}
			else {
				UserDAO.modifyUser(user.getUsername(), user.getRole());
				url="/userController?action=refreshPage&id="+user.getLastname();			}
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
			System.out.println("Current User : "+user1.getUsername());
			User userProfile = UserDAO.getUser(user1.getUsername());
			session.setAttribute("USER", userProfile);
			url="/viewMyProfile.jsp";
		}

		getServletContext().getRequestDispatcher(url).forward(request, response);
	}

}
