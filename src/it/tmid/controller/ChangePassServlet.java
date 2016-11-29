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
 * Implementazione della servlet ChangePassServlet, che permette di poter modificare la password utente;
 * una volta impostata la nuova password si viene reindirizzati di nuovo alla pagine Utente.jsp, dove
 * comparirà un messaggio dinamicamente che confermerà l'effettivo salvataggio in caso positivo oppure
 * l'errore riscontrato in caso negativo.
 */

@WebServlet("/ChangePassServlet")

public class ChangePassServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, java.io.IOException {
		
		try
		{	    
			
			/* 
			 * Prendo i due valori di password passati nel form e li salvo nell'oggetto istanziato di
			 * tipo UserBean. Salvo anche la targa dell'utente, gia salvata in precedenza nella sessione
			 * 
			 */
			
			/*
			 * Inizializzo session di tipo HttpSession per aver accesso agli attributi salvati sulla 
			 * sessione attiva, inizializzo un oggetto user di tipo UserBean e tramite i suoi metodi set 
			 * gli setto le due password, quella vecchia e quelle nuova, prendendole dal form e la targa 
			 * prendendola dalla sessione attiva.
			 */
			
			HttpSession session = request.getSession(true);
			UserBean user = new UserBean();
			user.setPassword(request.getParameter("pw"));
			user.setPasswordNuova(request.getParameter("pw2"));			
			user.setTarga((String)session.getAttribute("targa"));
			
			/*
			 * Viene usato il designer pattern "Abstract Factory" che fornisce un'interfaccia per creare 
			 * famiglie di oggetti connessi o dipendenti tra loro, in modo che non ci sia necessità 
			 * di specificare i nomi delle classi concrete all'interno del proprio codice.
			 * Creo il fattorizzatore che attraverso l'interfaccia UserDAOInterface mi crea l'oggetto
			 * contenente i metodi di UserDAO, da cui chiamerò la funzione changepass, passandogli come
			 * parametro l'oggetto user di tipo UserBean.
			 */
			
	        DAOFactory factory = DAOFactory.getDAOFactory();
	        UserDAOInterface userDAO = factory.getUserDAO();
			user = userDAO.changepass(user);
			
			/*
			 * Se l'utente ha messo una password vecchia valida avrà il valore valid settato a true;
			 * quindi in questo caso setto un valore numerico con alias di riferimento cp che permette
			 * tramite il file dynamic.js di far apparire sulla pagina dinamicamente un messaggio nel
			 * quale viene specificato che tutto è andato a buon fine. In caso contrario il valore cp
			 * settato sarà diverso, in modo da far apparire una scritta nella quale viene indicato un
			 * problema nella password vecchia inserita.
			 * 
			 */
			
			if (user.isValid()){
				session.setAttribute("cp", 2);
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/Utente.jsp");
				rd.include(request, response);
				session.setAttribute("cp", 0);
			}
			else{
				session.setAttribute("cp", 1);
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/Utente.jsp");
				rd.include(request, response);
				session.setAttribute("cp", 0);
			}
		
		}
		
		catch (Throwable theException) 	    
		{
			System.out.println(theException); 
		}
	}
}