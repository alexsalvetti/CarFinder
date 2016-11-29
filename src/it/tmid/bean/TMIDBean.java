package it.tmid.bean;

import java.util.LinkedList;

/* 
 * Classe TMIDBean. Simula il dispositivo GPS TMID; sceglie in modo casuale un percorso casuale e lo invia
 * al database. Lo stesso verrà poi caricato e visualizzato sul file "Mappa.jsp".
 */

public class TMIDBean {
	
	/* 
	 * Due LinkediList per caricare un tot di posizioni di partenza e di arrivo che verranno poi scelte
	 * in modo casuale; due String, initposition e finalposition, dove viene salvato il percorso effettivo
	 * uscito dalla scelta randomizzata; un'altra variabile String per salvare la targa del veicolo,
	 * un float speed, dove verrà salvata una velocità scelta in modo casuale da passare poi alla 
	 * Mappa.jsp; un ultimo String urlvideo, dove verrà salvato il percorso dove è posizionato il file
	 * del video (simulazione di eventuale ladro alla guida che sta rubando la nostra vettura).
	 */
	
	private LinkedList <String> listinit = new LinkedList<String>();
	private LinkedList <String> listfinal = new LinkedList<String>();
	private String initposition;
	private String finalposition;
	private String targa;
	private float speed;
	private String urlvideo = "video/video.mp4";

	/*
	 *  Costruttore della classe TMIDBean, senza alcun parametro, in quanto simula un dispositivo che 
	 *  effettua tutte le operazioni descritte in alto in modo automatico.
	 */

	public TMIDBean(){

		// Setto dei percorsi
		
		listinit.add("Strada le grazie, Verona");                   //1
		listinit.add("Via Armando Diaz, 8, Verona");                //2
		listinit.add("Via Pigna, 2, Verona");                       //3
		listinit.add("Via Mazzini, 4, Verona");                     //4
		listinit.add("Corso Porta Nuova, Verona");                  //5
		
		listfinal.add("Via Daniele Manin, 8, Verona");              //1
		listfinal.add("Via San Francesco, 5, Verona");              //2
		listfinal.add("Via Colonnello Fasoli, 43, Verona");         //3
		listfinal.add("Aeroporto di Verona, Caselle");              //4
		listfinal.add("Via dei Fante, 2, Castelnuovo del Garda");   //5
		
		// Genero un indice per scegliere un percorso random
		
		int randomNumber = 0 + (int)(Math.random()*(listinit.size()-1));
		System.out.println("Indice generato: "+randomNumber);
		
		// Setto il percorso random
	
		initposition = listinit.get(randomNumber);
		finalposition = listfinal.get(randomNumber);
		
		// Genero una velocità random
		
		speed = (float) (20 + Math.random() * 100);
		System.out.println("VELOCITA: "+speed);
		
	}

	// Get delle variabili
	
	public String getInitposition() {
		return initposition;
	}

	public String getFinalposition() {
		return finalposition;
	}

	public float getSpeed() {
		return speed;
	}

	public String getUrlvideo() {
		return urlvideo;
	}

	public String getTarga() {
		return targa;
	}

	public void setTarga(String targa) {
		this.targa = targa;
	}
	
}
