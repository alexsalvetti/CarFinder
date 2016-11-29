package it.tmid.bean;

import java.util.LinkedList;

/* 
 * Classe PositionBean delle Posizioni. Viene utilizzata in due casi. Nel primo come Bean per la 
 * PositionServlet, invocata dal form presente nel file "Mappa.jsp", in modo da prelevare le posizioni
 * del percorso salvate sulla session attiva. Nel secondo come Bean per la ReportServlet, per salvare 
 * gli ultimi 5 percorsi effettuati da quell'utente.
 */

public class PositionBean {
	
	// Due LinkediList per salvare le 5 ultime posizioni di partenza e di arrivo dell'utente
	
	private LinkedList <String> listinit = new LinkedList<String>();
	private LinkedList <String> listfinal = new LinkedList<String>();
	
	/* 
	 * Tre varibili di tipo String per salvare la posizione iniziale e finale del percorso che sta 
	 * effettuando; nella terza invece viene salvata la targa del veicolo.
	 */
	
	private String initposition;
	private String finalposition;
	private String targa;
	
	// Metodi Set-Get-Add per le varie variabili

	public LinkedList<String> getListinit() {

		return listinit;
	}

	public void addListinit(String valore){
		this.listinit.add(valore);
	}

	public void addListfinal(String valore){
		this.listfinal.add(valore);
	}

	public void setListinit(LinkedList<String> listinit) {
		this.listinit = listinit;
	}
	
	public LinkedList<String> getListfinal() {
		return listfinal;
	}

	public void setListfinal(LinkedList<String> listfinal) {
		this.listfinal = listfinal;
	}

	public String getInitposition() {
		return initposition;
	}

	public void setInitposition(String initposition) {
		this.initposition = initposition;
	}

	public String getFinalposition() {
		return finalposition;
	}

	public void setFinalposition(String finalposition) {
		this.finalposition = finalposition;
	}

	public String getTarga() {
		return targa;
	}

	public void setTarga(String targa) {
		this.targa = targa;
	}

}
