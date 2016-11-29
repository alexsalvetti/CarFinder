package it.tmid.model;

/* 
 * DESIGN PATTERN CREAZIONALE ABSTRACT FACTORY. E' il fattorizzatore che permettere di accedere alle
 * interfacce delle classi DAO per accedere ai loro metodi.
 */

public class DAOFactoryImplement extends DAOFactory {
	
	public PositionDAOInterface getPositionDAO(){
		return new PositionDAO();
	}
	
	public ThresholdDAOInterface getThresholdDAO(){
		return new ThresholdDAO();
	}
	
	public TMIDDAOInterface getTMIDDAO(){
		return new TMIDDAO();
	}
	
	public UserDAOInterface getUserDAO(){
		return new UserDAO();
	}

}
