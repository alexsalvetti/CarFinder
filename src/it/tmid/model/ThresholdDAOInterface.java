package it.tmid.model;

import it.tmid.bean.UserBean;

//DESIGN PATTERN CREAZIONALE ABSTRACT FACTORY. Interfaccia della ThresholdDAO.

public interface ThresholdDAOInterface {
	
	public UserBean soglie(UserBean user) throws InterruptedException;

}
