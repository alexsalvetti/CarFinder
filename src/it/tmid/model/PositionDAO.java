package it.tmid.model;

import it.tmid.bean.PositionBean;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/*
 * Classe che contiene Query per il recupero del percorso e per il recupero dei 5 ultimi percorsi
 * effettuati dall'utente
 */

public class PositionDAO implements PositionDAOInterface {

	static Connection currentCon = null;
	static ResultSet rs = null; 

	// Metodo contenente le query e controlli per recuperare il percorso mandato sul db dal TMID.

	public PositionBean positions(PositionBean pos){

		Statement stmt = null;
		String queryPos = "select startpos,endpos from tmid";

		try 
		{
			currentCon = ConnectionManager.getConnection();
			stmt=currentCon.createStatement();
			rs = stmt.executeQuery(queryPos);
			rs.next();
			pos.setInitposition(rs.getString("startpos"));
			pos.setFinalposition(rs.getString("endpos"));

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

		return pos;

	}

	// Metodo contenente le Query e controlli per recuperare gli ultimi 5 percorsi effettuati dall'utente

	public PositionBean lastfivepositions(PositionBean pos){

		Statement stmt = null;
		String targa = pos.getTarga();
		String queryPos = "select startpos,endpos from positions where targa='"+targa+"' order by id desc;";

		try 
		{
			currentCon = ConnectionManager.getConnection();
			stmt=currentCon.createStatement();
			rs = stmt.executeQuery(queryPos);
			int c=0;

			// While per scorrere massimo 5 percorsi presenti sul db

			while (rs.next() && c<5) {
				pos.addListinit(rs.getString("startpos"));
				pos.addListfinal(rs.getString("endpos"));
				c+=1;
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

		return pos;

	}
}