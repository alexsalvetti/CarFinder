package it.tmid.controller;

import it.tmid.bean.PositionBean;
import it.tmid.model.DAOFactory;
import it.tmid.model.PositionDAOInterface;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/* 
 * Servlet PositionServlet utilizzata per prendere le due posizioni inviate dall'oggetto TMID al database,
 * in modo da settare un percorso sulla mappa per simulare la percorrenza di esso tramite un veicolo.
 */

@WebServlet("/PositionServlet")

public class PositionServlet extends HttpServlet {
	
  
		private static final long serialVersionUID = 1L;

		public void doGet(HttpServletRequest request, HttpServletResponse response) 
				throws ServletException, java.io.IOException {
			try
			{	    
				
				/* 
				 * Viene creato un oggetto positions di tipo PositionBean dove verranno settate le due
				 * posizioni una volta recuperate
				 */
				
				PositionBean positions = new PositionBean();
				
				/*
				 * Viene usato il designer pattern "Abstract Factory" che fornisce un'interfaccia per creare 
				 * famiglie di oggetti connessi o dipendenti tra loro, in modo che non ci sia necessità 
				 * di specificare i nomi delle classi concrete all'interno del proprio codice.
				 * Creo il fattorizzatore che attraverso l'interfaccia positionDAOInterface mi crea 
				 * l'oggetto contenente i metodi di positionDAO, da cui chiamerò la funzione positions, 
				 * passandogli come parametro l'oggetto positions di tipo PositionBean. La DAO contiene
				 * tutti i metodi per effettuare le query sul db e salvarne il risultato.
				 */
				
		        DAOFactory factory = DAOFactory.getDAOFactory();
		        PositionDAOInterface positionDAO = factory.getPositionDAO();
				positions = positionDAO.positions(positions);
				
				/* 
				 * Una volta prese e settate le due posizioni sull'oggetto le devo settare anche sulla
				 * sessione attiva per poterle utilizzare in altri punti del codice, nel nostro caso nel
				 * file Mappa.jsp. I valori di esse verranno salvati con alias initposition e finalposition.
				 */
				
				HttpSession session = request.getSession(true);
				session.setAttribute("initposition",positions.getInitposition());
				session.setAttribute("finalposition",positions.getFinalposition());
				positions = null;
				response.sendRedirect("Mappa.jsp");
				
			} 
			catch (Throwable theException) 	    
			{
				System.out.println(theException); 
			}
		}
}
	
