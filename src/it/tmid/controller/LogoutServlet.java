package it.tmid.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

// Servlet invocata per effettuare il Logout; invalida la sessione attiva in quel momento.

@WebServlet("/LogoutServlet")

public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		
		/*
		 * Vengono cercati i cookie di sessione dell'utente salvati dal browser e se presenti ne viene
		 * stampato l'id.
		 */
		
		Cookie[] cookies = request.getCookies();
		if(cookies != null){
			for(Cookie cookie : cookies){
				if(cookie.getName().equals("JSESSIONID")){
					System.out.println("JSESSIONID="+cookie.getValue());
					break;
				}
			}
		}
		
		/*
		 * Viene settata la sessione attiva a false e successivamente invalidata; dopo questo si viene
		 * reindirizzati alla pagina LoginPage.jsp
		 */

		HttpSession session = request.getSession(false);
		session.setAttribute("login", 0);
		if(session != null){
			session.invalidate();
		}
		response.sendRedirect("LoginPage.jsp");
	}

}
