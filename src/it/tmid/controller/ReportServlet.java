package it.tmid.controller;


import it.tmid.bean.PositionBean;
import it.tmid.model.DAOFactory;
import it.tmid.model.PositionDAOInterface;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// Servlet Report invocata per prelevare gli ultimi 5 percorsi effettuati dall'utente

@WebServlet("/ReportServlet")

public class ReportServlet extends HttpServlet {
	
       
		private static final long serialVersionUID = 1L;

		public void doGet(HttpServletRequest request, HttpServletResponse response) 
				throws ServletException, java.io.IOException {
			try
			{	    
				
				/* Inizializzo l'oggetto di tipo HttpSession per recuperare gli attributi salvati sulla
				 * sessione attiva; poi creo un oggetto positions di tipo PositionBean e gli setto la
				 * targa del veicolo, presa dalla sessione e quindi gia salvata in precedenza.
				 */
				
				HttpSession session = request.getSession(true);
				PositionBean positions = new PositionBean();
				positions.setTarga((String) session.getAttribute("targa"));
				
				/*
				 * Viene usato il designer pattern "Abstract Factory" che fornisce un'interfaccia per creare 
				 * famiglie di oggetti connessi o dipendenti tra loro, in modo che non ci sia necessità 
				 * di specificare i nomi delle classi concrete all'interno del proprio codice.
				 * Creo il fattorizzatore che attraverso l'interfaccia positionDAOInterface mi crea 
				 * l'oggetto contenente i metodi di positionDAO, da cui chiamerò la funzione 
				 * lastfivepositions, passandogli come parametro l'oggetto positions di tipo PositionBean.
				 */
				
		        DAOFactory factory = DAOFactory.getDAOFactory();
		        PositionDAOInterface positionDAO = factory.getPositionDAO();
				positions = positionDAO.lastfivepositions(positions);
				
				/* 
				 * Se i percorsi sono meno di 5, aggiungo tot valori vuoti alla lista in modo che nel
				 * passaggio dei valori recuperati al file Report.jsp non ci siano celle della tabella
				 * con valori NULL.
				 */
				
				if (positions.getListinit().size()<5){
					int diff = 5-positions.getListinit().size();
					for(int rip=0;rip<diff;rip++){
						positions.addListinit("");
						positions.addListfinal("");
					}
				}
				
				/* 
				 * Salvo gli ultimi 5 percorsi dell'utente sulla sessione, in modo da poterli recuperare
				 * nel file Report.jsp, dove verranno visualizzati.
				 */
			
				
				for (int i=0;i<positions.getListinit().size();i++){
					session.setAttribute("init"+(i+1),positions.getListinit().get(i));
					session.setAttribute("finale"+(i+1),positions.getListfinal().get(i));
				}
				
				
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/Report.jsp");
				rd.include(request, response);
				
				positions = null;
				response.sendRedirect("Report.jsp");
				
			}
			catch (Throwable theException) 	    
			{
				System.out.println(theException); 
			}
		}
}
	
