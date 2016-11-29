package it.tmid.controller;

import it.tmid.bean.UserBean;
import it.tmid.model.DAOFactory;

import it.tmid.model.ThresholdDAOInterface;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/* Servlet utilizzata per settare le soglie di velocità dell'utente: in fase di registrazione esse vengono
 * settate a 0.0 di default dal Database.
 */

@WebServlet("/ThresholdServlet")

public class ThresholdServlet extends HttpServlet {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, java.io.IOException {
		try
		{	    
			
			/* 
			 * Creo un nuovo oggetto session di tipo HttpSession per recuperare gli attributi salvati 
			 * sulla sessione attiva. Dopo instanzio un oggetto user di tipo UserBean ed ad esso vengono
			 * settate le due soglie dell'utente prelevate dal form.
			 */
			
			HttpSession session = request.getSession(true);
			UserBean user = new UserBean();
			user.setSoglia1(request.getParameter("soglia1"));
			user.setSoglia2(request.getParameter("soglia2"));
			
			/*
			 * Viene usato il designer pattern "Abstract Factory" che fornisce un'interfaccia per creare 
			 * famiglie di oggetti connessi o dipendenti tra loro, in modo che non ci sia necessità 
			 * di specificare i nomi delle classi concrete all'interno del proprio codice.
			 * Creo il fattorizzatore che attraverso l'interfaccia ThresholdDAOInterface mi crea 
			 * l'oggetto contenente i metodi di ThresholdDAO, da cui chiamerò la funzione soglie, 
			 * passandogli come parametro l'oggetto positions di tipo UserBean. La DAO contiene tutti i
			 * metodi per effettuare le query sul db e salvarne il risultato.
			 */
			
	        DAOFactory factory = DAOFactory.getDAOFactory();
	        ThresholdDAOInterface threDAO = factory.getThresholdDAO();
			user = threDAO.soglie(user);
			if (user.thresholdisValid()==true){
				System.out.println("Settaggio soglie andato a buon fine");
				session.setAttribute("soglia1",user.getSoglia1());
				session.setAttribute("soglia2",user.getSoglia2());
				session.setAttribute("so", 2);
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/Soglie.jsp");
				rd.include(request, response);
				session.setAttribute("so", 0);
			}
			else{
				session.setAttribute("so", 1);
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/Soglie.jsp");
				rd.include(request, response);
				session.setAttribute("so", 0);
			}
		} 
		catch (Throwable theException) 	    
		{
			System.out.println(theException); 
		}
	}
}