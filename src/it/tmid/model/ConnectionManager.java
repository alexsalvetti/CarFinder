package it.tmid.model;

import java.sql.*;

public class ConnectionManager {

	private static Connection con;
	private static String url;

	/* 
	 * DESING PATTERN CREAZIONALE SINGLETON utilizzato per creare un unico oggetto di tipo ConnectionManager
	 * che contiene sempre gli stessi valori, senza ricreare un nuovo oggetto uguale ad ogni connessione
	 * al Database. Viene dichiarata una variabile privata e statica del tipo della classe, nel nostro caso
	 * appunto ConnectionManager, si crea un costruttore privato e successivamente un metodo publico,
	 * genericamente chiamato getInstance(), il quale, se l'oggetto ConnectionManager esiste gia lo 
	 * restituisce senza ricrearlo, altrimenti lo crea, ma quella sarà la sua unica volta.
	 */

	private static ConnectionManager instance;

	private ConnectionManager(){}

	public static ConnectionManager getInstance(){
		if (instance==null)
			instance = new ConnectionManager();
		return instance;
	}

	public static Connection getConnection()
	{
		// Connessione al Database

		try
		{
			
			url = "jdbc:postgresql://ec2-107-20-178-83.compute-1.amazonaws.com:5432/dfukin326rbi01?user=scfscrouebjfrz&password=qtrw1F-37VvBkLESZ00y93ri-k&ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory"; 
			Class.forName("org.postgresql.Driver");

			try
			{      
				
				con = DriverManager.getConnection(url,"scfscrouebjfrz","qtrw1F-37VvBkLESZ00y93ri-k"); 

			}

			catch (SQLException ex)
			{
				
				ex.printStackTrace();
				
			}
		}

		catch(ClassNotFoundException e)
		{
			System.out.println(e);
		}

		return con;
	}
}
