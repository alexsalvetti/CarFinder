package it.tmid.bean;

/* 
 * Classe UserBean che viene instanziata quando viene invocata la LoginServlet, oppure la 
 * ChangePassServlet o ancora la RegistrationServlet. Contiene tutti gli attributi che fanno parte
 * di un utente, settati in fase di registrazione, tranne per le due soglie che vengono settate di
 * default nel database al valore 0.0, e poi sono modificabili dall'utente mediante la pagina
 * Soglie.jsp
 */

public class UserBean {

	/*
	 * Varibili di tipo String che contengono i valori degli attributi dell'utente; inoltre sono presenti
	 * due varibili di tipo boolean: la prima per verificare se l'utente, in fase di registrazione,
	 * può effettuarla (quindi se non ci sono altri utenti gia registrati con quella targa) e in fase di 
	 * login per permettere il reindirizzamento al Menu.jsp. La seconda invece serve per verificare che
	 * le soglie inserite, nella pagina Soglie.jsp, sono corrette.
	 * 
	 */
	
	private String username;
	private String password;
	private String passwordnuova;
	private String firstName;
	private String lastName;
	private String email;
	private String targa;
	private String soglia1;
	private String soglia2;
	private boolean valid;
	private boolean threshold;
	
	// Metodi Get-Set delle variabili

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String newFirstName) {
		firstName = newFirstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String newLastName) {
		lastName = newLastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String newPassword) {
		password = newPassword;
	}
	
	public String getPasswordNuova(){
		return passwordnuova;
	}

	public void setPasswordNuova(String newPassword){
		passwordnuova = newPassword;
	}

	public String getUsername() {
		return username;
	}

	public void setUserName(String newUsername) {
		username = newUsername;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String newEmail) {
		email = newEmail;
	}

	public String getTarga() {
		return targa;
	}

	public void setTarga(String newTarga) {
		targa = newTarga;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean newValid) {
		valid = newValid;
	}

	public String getSoglia1() {
		return soglia1;
	}

	public void setSoglia1(String soglia1) {
		this.soglia1 = soglia1;
	}

	public String getSoglia2() {
		return soglia2;
	}

	public void setSoglia2(String soglia2) {
		this.soglia2 = soglia2;
	}
	
	public boolean thresholdisValid() {
		return threshold;
	}

	public void thresholdsetValid(boolean newValid) {
		threshold = newValid;
	}
	
}
