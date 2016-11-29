package it.tmid.controller;

import it.tmid.bean.TMIDBean;
import it.tmid.model.DAOFactory;
import it.tmid.model.TMIDDAOInterface;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/* 
 * Servlet che simula il dispositivo TMID, il quale manda un percorso random che verrà poi visualizzato nel
 * file Mappa.jsp e il link di riferimento del video del presunto ladro che verrà mostrato nella pagina
 * Video.jsp 
 */

@WebServlet("/TMIDServlet")

public class TMIDServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, java.io.IOException {
		try
		{	    
			
			/*
			 * Inizializzo un oggetto session di tipo HttpSession contenente la sessione attiva; poi creo
			 * un oggetto tmid di tipo TMIDBean a cui dovrò settare, prelevandola dalla sessione, la targa
			 * del dispositivo con cui sono loggato.
			 */
			
			HttpSession session = request.getSession(true);
			TMIDBean tmid = new TMIDBean();
			tmid.setTarga((String) session.getAttribute("targa"));
			
			/*
			 * Viene usato il designer pattern "Abstract Factory" che fornisce un'interfaccia per creare 
			 * famiglie di oggetti connessi o dipendenti tra loro, in modo che non ci sia necessità 
			 * di specificare i nomi delle classi concrete all'interno del proprio codice.
			 * Creo il fattorizzatore che attraverso l'interfaccia TMIDDAOInterface mi crea 
			 * l'oggetto contenente i metodi di TMIDDAO, da cui chiamerò la funzione senddata, 
			 * passandogli come parametro l'oggetto positions di tipo TMIDBean.
			 */
			
	        DAOFactory factory = DAOFactory.getDAOFactory();
	        TMIDDAOInterface tmidDAO = factory.getTMIDDAO();
			tmid = tmidDAO.senddata(tmid);
			
			/*
			 * Dopo aver inviato i dati sul database mi salvo i valori di speed e dell'url del video sulla
			 * sessione attiva.
			 */
			
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/TMID.jsp");
			session.setAttribute("speed",tmid.getSpeed());
			session.setAttribute("video",tmid.getUrlvideo());
			rd.include(request, response);
			tmid = null;
			
		} 
		catch (Throwable theException) 	    
		{
			System.out.println(theException); 
		}
	}
	
}