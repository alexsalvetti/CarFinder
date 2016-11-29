package it.tmid.model;

import it.tmid.bean.TMIDBean;

//DESIGN PATTERN CREAZIONALE ABSTRACT FACTORY. Interfaccia della TMIDDAO.

public interface TMIDDAOInterface {
	
	public TMIDBean senddata(TMIDBean tmid) throws InterruptedException;

}
