package it.tmid.model;

import it.tmid.bean.UserBean;

//DESIGN PATTERN CREAZIONALE ABSTRACT FACTORY. Interfaccia della UserDAO.

public interface UserDAOInterface {
	
	public UserBean login(UserBean bean);
	public UserBean record(UserBean bean);
	public UserBean changepass(UserBean bean);
	public void sendEmail(String nome,String to);

}
