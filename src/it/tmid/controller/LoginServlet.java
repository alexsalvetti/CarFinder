package it.tmid.controller;


import it.tmid.bean.UserBean;
import it.tmid.model.DAOFactory;
import it.tmid.model.UserDAOInterface;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.Cookie;


/* 
 * Servlet del Login che viene invocata per effettuare il login di un utente, la registrazione di un utente,
 * il cambio password di un utente. Una volta loggati si avrà accesso al menu principale, altrimenti si
 * verrà reindirizzati nuovamente sulla stessa pagina e sarà necessario ritentare l'autenticazione.
 */

@WebServlet("/LoginServlet")

public class LoginServlet extends HttpServlet  {
	private static final long serialVersionUID = 1L;



	public void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, java.io.IOException {

		try
		{	    
			
			/* 
			 * Prendo i due valori di targa e password passati nel form e li salvo nell'oggetto istanziato
			 * di tipo UserBean. Inoltre salvo anche il valore di targa su una variabile String di nome
			 * usern che mi servirà successivamente per la creazione del cookie.
			 * 
			 */
			
			UserBean user = new UserBean();
			user.setTarga(request.getParameter("targa"));
			user.setPassword(request.getParameter("pw"));
			String usern = user.getTarga();
			
			/*
			 * Viene usato il designer pattern "Abstract Factory" che fornisce un'interfaccia per creare 
			 * famiglie di oggetti connessi o dipendenti tra loro, in modo che non ci sia necessità 
			 * di specificare i nomi delle classi concrete all'interno del proprio codice.
			 * Creo il fattorizzatore che attraverso l'interfaccia UserDAOInterface mi crea l'oggetto
			 * contenente i metodi di UserDAO, da cui chiamerò la funzione login, passandogli come
			 * parametro l'oggetto user di tipo UserBean. La DAO contiene tutti i metodi per effettuare
			 * le query sul db e salvarne il risultato.
			 */
			
			DAOFactory factory = DAOFactory.getDAOFactory();
			UserDAOInterface userDAO = factory.getUserDAO();
			user = userDAO.login(user);
			
			/* Se l'utente è valido setto tutti i valori interni dell'utente registrato, quindi targa, nome,
			 * cognome, soglia1, soglia2, email, sulla sessione attiva, in modo che siano reperibili in
			 * qualsiasi momento mediante un identificativo. Dopo questo si verrà reindirizzati sulla pagina
			 * index.jsp, vale a dire il menu principale della web-app. Se invece l'utente non è valido,
			 * per causa targa o password errari, si verrà rendirizzati di nuovo alla stessa LoginPage.jsp.
			 * Quando ci troviamo in questo caso setto un valore numerico con alias di riferimento login
			 * che permette tramite il file dynamic.js di far apparire sulla pagina dinamicamente un 
			 * messaggio nel quale viene specificato che c'è stato un problema.
			 */
			
			if (user.isValid())
			{

				HttpSession session = request.getSession(true);
				session.setAttribute("targa",usern);
				session.setAttribute("nome",user.getFirstName());
				session.setAttribute("cognome",user.getLastName());
				session.setAttribute("soglia1",user.getSoglia1());
				session.setAttribute("soglia2",user.getSoglia2());
				session.setAttribute("email",user.getEmail());
				
				// Setto il cookie per avere il tempo di sessione a 30 minuti.
				
				session.setMaxInactiveInterval(30*60); 
				Cookie Targa = new Cookie("targa", usern);
				Targa.setMaxAge(30*60);
				response.addCookie(Targa);
				response.sendRedirect("index.jsp");

			}

			else {
				HttpSession session= request.getSession(true);
				session.setAttribute("login", 1);
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/LoginPage.jsp");
				rd.include(request, response);
				session.setAttribute("login", 0);
			}
		} 

		catch (Throwable theException) 	    
		{
			System.out.println(theException); 
		}
	}
}