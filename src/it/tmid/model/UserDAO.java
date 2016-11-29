package it.tmid.model;

import it.tmid.bean.UserBean;

import java.sql.*;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/* 
 * Classe contenente 4 metodi: login, con le query e i controlli per il login dell'utente; record, con le
 * query e controlli per la registrazione dell'utente; changepass, con le query e i controlli per effettuare
 * il cambio password nella pagina Utente.jsp; infine sendMail, per effettuare l'invio di una mail dopo 
 * aver effettuato la registrazione.
 */

public class UserDAO implements UserDAOInterface 	
{
	static Connection currentCon = null;
	static ResultSet rs = null;  

	/*
	 * Metodo che controlla sul database se i valori inseriti nella pagina LoginPage.jsp, quali targa e 
	 * password sono presenti nel database
	 * @see it.tmid.model.UserDAOInterface#login(it.tmid.bean.UserBean)
	 */

	public UserBean login(UserBean bean) {

		String targa = bean.getTarga();
		String pass = bean.getPassword();
		Statement stmt = null;     
		String searchQuery =
				"select * from members where targa='"
						+ targa
						+ "'"
						+"and pass='"
						+ pass
						+"'"; 
		System.out.println("La tua targa è " + targa);          
		System.out.println("La tua password è " + pass);
		System.out.println("Query effettuata: "+searchQuery);

		try 
		{

			currentCon = ConnectionManager.getConnection();
			stmt=currentCon.createStatement();
			rs = stmt.executeQuery(searchQuery);	        
			boolean more = rs.next();

			// Se l'utente non esiste setta isValid a false

			if (!more) 
			{
				System.out.println("Sorry, you are not a registered user! Please sign up first");
				bean.setValid(false);
			} 

			//se l'utente esiste setta isValid a true

			else if (more) 
			{

				// Setto tutti i dati reperiti dalla query nell'oggetto bean

				System.out.println("Welcome " + rs.getString("first_name"));
				bean.setFirstName(rs.getString("first_name"));
				bean.setLastName(rs.getString("last_name"));
				bean.setEmail(rs.getString("email"));
				bean.setSoglia1(rs.getString("soglia1"));
				bean.setSoglia2(rs.getString("soglia2"));
				bean.setValid(true);
			}
		} 

		catch (Exception ex) 
		{
			System.out.println("Log In failed: An Exception has occurred! " + ex);
		} 

		finally 
		{
			if (rs != null)	{
				try {
					rs.close();
				} catch (Exception e) {}
				rs = null;
			}

			if (stmt != null) {
				try {
					stmt.close();
				} catch (Exception e) {}
				stmt = null;
			}

			if (currentCon != null) {
				try {
					currentCon.close();
				} catch (Exception e) {
				}

				currentCon = null;
			}
		}

		return bean;

	}

	public UserBean record(UserBean bean) {

		Statement stmt = null;   

		String firstName = bean.getFirstName();    
		String lastName = bean.getLastName(); 
		String email = bean.getEmail();
		String targa = bean.getTarga();
		String password = bean.getPassword();

		// Controllo che i campi non siano vuoti

		if (firstName.length()==0 || lastName.length()==0 || email.length()==0 || targa.length()==0 || password.length()==0){
			bean.setValid(false);
			return bean;
		}
		int c=0;

		// Controllo che la mail abbia almeno una @ ed un .

		for(int i=0;i<bean.getEmail().length();i++){
			if (email.charAt(i)=='.' || email.charAt(i)=='@'){
				c+=1;
			}

		}
		if(c<2 || email.equals("@.") || email.equals(".@")){
			bean.setValid(false);

			return bean;
		}
		String insertQuery =
				"insert into members(first_name, last_name, email, pass, targa) VALUES ('"
						+ firstName
						+ "','"
						+ lastName
						+ "','"
						+ email
						+ "','"
						+ password
						+ "','"
						+ targa
						+ "')";
		String searchQuery =
				"select * from members where targa='"
						+ targa
						+ "'";
		System.out.println("Il tuo nome è " + firstName);
		System.out.println("Il tuo cognome è " + lastName);
		System.out.println("La tua mail è " + email);
		System.out.println("La tua targa è " + targa);
		System.out.println("La tua password è " + password);
		System.out.println("Query effettuata: "+insertQuery);

		try 
		{

			currentCon = ConnectionManager.getConnection();
			stmt=currentCon.createStatement();	  
			rs = stmt.executeQuery(searchQuery);
			boolean more = rs.next();

			// Se l'utente esiste setto isValid a false

			if (more) 
			{
				System.out.println("Sei già registrato, effettua il login");
				bean.setValid(false);
			} 

			// Se l'utente non esiste setto isValid a true

			else if (!more) 
			{    	
				stmt.executeUpdate(insertQuery);
				bean.setValid(true);

				// Invio la mail all'utente che si è appena registrato

				sendEmail(firstName,email);
			}
		} 

		catch (Exception ex) 
		{
			System.out.println("Registrazione fallita: Si è verificato un problema! " + ex);
		} 

		finally 
		{
			if (rs != null)	{
				try {
					rs.close();
				} catch (Exception e) {}
				rs = null;
			}

			if (stmt != null) {
				try {
					stmt.close();
				} catch (Exception e) {}
				stmt = null;
			}

			if (currentCon != null) {
				try {
					currentCon.close();
				} catch (Exception e) {
				}

				currentCon = null;
			}
		}

		return bean;

	}

	public UserBean changepass(UserBean bean) {

		String targa = bean.getTarga();
		String pass = bean.getPassword();
		String nuovapass = bean.getPasswordNuova();
		Statement stmt = null;
		String queryPass = "update members set pass = '"+nuovapass+"' where targa = '"+targa+"'";
		String searchUser =
				"select * from members where targa='"
						+ targa
						+ "' and pass='"+pass+"'";
		System.out.println("Query di ricerca in esecuzione: "+searchUser);
		System.out.println("Query di settaggio in esecuzione: "+queryPass);

		try 
		{
			currentCon = ConnectionManager.getConnection();
			stmt=currentCon.createStatement();
			rs = stmt.executeQuery(searchUser);
			boolean more = rs.next();

			if (more) 
			{
				stmt.executeUpdate(queryPass);
				System.out.println("Cambio password andato a buon fine");
				bean.setValid(true);

			} 

			else if (!more) 
			{    	
				System.out.println("Password vecchia errata, riprova");
				bean.setValid(false);
			}


		} 

		catch (Exception ex) 
		{

			System.out.println("Si è verificato un problema! " + ex);
		} 


		finally 
		{
			if (rs != null)	{
				try {
					rs.close();
				} catch (Exception e) {}
				rs = null;
			}


			if (stmt != null) {
				try {
					stmt.close();
				} catch (Exception e) {}
				stmt = null;
			}

			if (currentCon != null) {
				try {
					currentCon.close();
				} catch (Exception e) {
				}

				currentCon = null;
			}
		}

		return bean;

	}

	public void sendEmail(String nome,String to)
	{
		final String username = "alfadaprogetti@gmail.com";
		final String password = "CIAOciao";
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("alfadaprogetti@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(to));
			message.setSubject("ALFADA PROGETTI - Registrazione Utente");
			message.setText("Salve "+nome+";"
					+ "\n\n La registrazione è andata a buon fine!");
			Transport.send(message);
			System.out.println("Done");

		} 

		catch (MessagingException e) 
		{

			System.out.println("Username o Password della casella mail non sono corretti !");
		}
	}
}