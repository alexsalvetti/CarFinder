package it.tmid.model;

/*
 * DESIGN PATTERN CREAZIONALE ABSTRACT FACTORY. E' una classe astratta che permette di implementare il 
 * fattorizzatore DAOFactory, attraverso il quale riesco ad accedere ad ogni classe DAO non direttamente
 * ma attraverso l'interfaccia di ognuna.
 */

public abstract class DAOFactory {
	public abstract PositionDAOInterface getPositionDAO();
	public abstract ThresholdDAOInterface getThresholdDAO();
	public abstract TMIDDAOInterface getTMIDDAO();
	public abstract UserDAOInterface getUserDAO();
	
	public static DAOFactory getDAOFactory(){
		return new DAOFactoryImplement();
	}

}
