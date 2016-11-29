package it.tmid.model;

import it.tmid.bean.PositionBean;

// DESIGN PATTERN CREAZIONALE ABSTRACT FACTORY. Interfaccia della PositionDAO.

public interface PositionDAOInterface {
	
	public PositionBean positions(PositionBean pos);
	public PositionBean lastfivepositions(PositionBean pos);

}
