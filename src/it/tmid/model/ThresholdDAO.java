package it.tmid.model;

import it.tmid.bean.UserBean;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;


/*
 * Classe che contiene le query e i controlli per settare le soglie dalla pagina Soglie.jsp
 */

public class ThresholdDAO implements ThresholdDAOInterface  {

	static Connection currentCon = null;
	static ResultSet rs = null; 


	public UserBean soglie(UserBean user) throws InterruptedException {

		Statement stmt = null;
		String soglia1 = user.getSoglia1();    
		String soglia2 = user.getSoglia2();
		String targa = user.getTarga();
		System.out.println("Soglia 1 "+soglia1);
		System.out.println("Soglia 2 "+soglia2);
		int c=0;

		// Controllo che le soglie siano composte solo da caratteri numerici

		for (int i=0;i<soglia1.length();i++){
			if ((soglia1.charAt(i)<'0' || soglia1.charAt(i)>'9') && soglia1.charAt(i)!='.'){

				c+=1;
			}
		}
		if (c>=1){
			user.thresholdsetValid(false);
			return user;
		}
		c=0;
		for (int i=0;i<soglia2.length();i++){
			if ((soglia2.charAt(i)<'0' || soglia2.charAt(i)>'9') && soglia2.charAt(i)!='.')
				c+=1;
		}
		if (c>=1){
			user.thresholdsetValid(false);
			return user;
		}
		if ((Float.parseFloat(soglia1)>Float.parseFloat(soglia2)) || ((Float.parseFloat(soglia1)<0) || (Float.parseFloat(soglia2)<0))){
			System.out.println("soglie errate");
			user.thresholdsetValid(false);
			return user;
		}

		String querySoglie = "update members set soglia1 = "+soglia1+" , soglia2 = "+soglia2+" where targa = '"+targa+"'";
		try 
		{
			currentCon = ConnectionManager.getConnection();
			stmt=currentCon.createStatement();
			stmt.executeUpdate(querySoglie);
			user.setSoglia1(Float.toString(Float.parseFloat(soglia1)));
			user.setSoglia2(Float.toString(Float.parseFloat(soglia2)));

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
		user.thresholdsetValid(true);
		return user;

	}
}