//Servlet per la Registrazione dell'Utente

package it.tmid.controller;


import it.tmid.bean.UserBean;
import it.tmid.model.DAOFactory;
import it.tmid.model.UserDAOInterface;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/* 
 * Servlet Registration invocata per la registrazione di un nuovo utente. Una volta effettuata la
 * registrazione si verrà o reindirizzati sulla LoginPage.jsp con un messaggio dinamico di avvenuta
 * operazione, oppure reindirizzati sulla stessa RegistPage.jsp sempre con un messaggio dinamico ma 
 * stavolta contentente un messaggio di errore che mostra il problema riscontrato.
 * 
 */

@WebServlet("/RegistrationServlet")

public class RegistrationServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, java.io.IOException {
		try
		{	    
			
			/* 
			 * Prendo i valori di targa, nome, cognome, email, password, passati nel form e li salvo 
			 * nell'oggetto istanziato di tipo UserBean.
			 */
			
			UserBean user = new UserBean();
			user.setFirstName(request.getParameter("fname"));
			user.setLastName(request.getParameter("lname"));
			user.setEmail(request.getParameter("email"));
			user.setTarga(request.getParameter("targa"));
			user.setPassword(request.getParameter("pass"));
			
			/*
			 * Viene usato il designer pattern "Abstract Factory" che fornisce un'interfaccia per creare 
			 * famiglie di oggetti connessi o dipendenti tra loro, in modo che non ci sia necessità 
			 * di specificare i nomi delle classi concrete all'interno del proprio codice.
			 * Creo il fattorizzatore che attraverso l'interfaccia UserDAOInterface mi crea l'oggetto
			 * contenente i metodi di UserDAO, da cui chiamerò la funzione record, passandogli come
			 * parametro l'oggetto user di tipo UserBean.
			 */
			
	        DAOFactory factory = DAOFactory.getDAOFactory();
	        UserDAOInterface userDAO = factory.getUserDAO();
			user = userDAO.record(user);
			
			/* Se l'utente è valido si verrà reindirizzati sulla pagina LoginPage.jsp, vale a dire la pagina
			 * di login della web-app, con un messaggio dinamico positivo. Se invece l'utente non è valido,
			 * per causa utente gia creato oppure dati inseriti incompatibili, si verrà rendirizzati di 
			 * nuovo alla stessa RegistPage.jsp con un messaggio dinamico di errore.
			 * Queso messaggio dinamico viene settato con alias di riferimento login se la registrazione
			 * è andata a buon fine, altrimenti con alias reg che permette tramite il file dynamic.js 
			 * di far apparire sulla pagina dinamicamente il messaggio citato.
			 */
			
			if (user.isValid())
			{
				HttpSession session= request.getSession(true);
				System.out.println("Registrazione andata a buon fine");
				session.setAttribute("login", 2);
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/LoginPage.jsp");
				rd.include(request, response);
				session.setAttribute("login", 0);
			} 
			else {
				HttpSession session= request.getSession(true);
				session.setAttribute("reg", 1);
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/RegistPage.jsp");
				rd.include(request, response);
				session.setAttribute("reg", 0);
			}
		} 
		catch (Throwable theException) 	    
		{
			System.out.println(theException); 
		}
	}
}
