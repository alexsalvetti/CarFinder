package it.tmid.model;

import it.tmid.bean.TMIDBean;

/* 
 * Classe contente il metodo senddata, dove faccio una query per inserire il percorso generato casualmente
 * dal TMID.
 */

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;


public class TMIDDAO implements TMIDDAOInterface {

	static Connection currentCon = null;
	static ResultSet rs = null; 


	public TMIDBean senddata(TMIDBean tmid) throws InterruptedException{

		Statement stmt = null;
		String targa = tmid.getTarga();
		String startpos = tmid.getInitposition();
		String endpos = tmid.getFinalposition();
		String speed = Float.toString(tmid.getSpeed());
		String urlvideo = tmid.getUrlvideo();

		try 
		{
			currentCon = ConnectionManager.getConnection();
			stmt=currentCon.createStatement();

			String insertQuery =
					"insert into tmid(targa,startpos, endpos, speed, urlvideo) VALUES ('"
							+ targa
							+ "','"
							+ startpos
							+ "','"
							+ endpos
							+ "','"
							+ speed
							+ "','"
							+ urlvideo
							+ "')";
			String insertInPos = "insert into positions(targa,startpos, endpos) VALUES ('"
					+ targa
					+ "','"
					+ startpos
					+ "','"
					+ endpos
					+ "')";
			String searchQuery = "select * from tmid where targa='"+targa+"'";
			String deleteQuery = "delete from tmid";
			rs = stmt.executeQuery(searchQuery);
			boolean more = rs.next();

			if (more) 
			{
				stmt.executeUpdate(deleteQuery);
				stmt.executeUpdate(insertQuery);
				stmt.executeUpdate(insertInPos);

			} 

			// Se l'utente non esiste setto isValid a true
			else if (!more) 
			{    	
				stmt.executeUpdate(insertQuery);
				stmt.executeUpdate(insertInPos);

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

		return tmid;

	}
}