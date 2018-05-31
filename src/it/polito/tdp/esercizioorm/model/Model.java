package it.polito.tdp.esercizioorm.model;

import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.esercizioorm.dao.CorsoDAO;
import it.polito.tdp.esercizioorm.dao.StudenteDAO;

public class Model {

	private CorsoDAO cdao;
	private StudenteDAO sdao;
	
	private List<Corso> corsi;
	private List<Studente> studenti;
	
	private CorsoIdMap corsomap;
	private StudenteIdMap studentemap;
	
	public Model() {
		
		cdao = new CorsoDAO();
		sdao = new StudenteDAO();
		
		corsomap = new CorsoIdMap();
		studentemap = new StudenteIdMap();
		
		corsi = cdao.getTuttiCorsi(corsomap);
		System.out.println("Totale corsi: " + corsi.size());
		
		studenti = sdao.getTuttiStudenti(studentemap);
		System.out.println("Totale studenti: " + studenti.size());
		
		// METODO PER RIEMPIRE LA LISTA DI CORSI PER OGNI STUDENTE
		for (Studente s : studenti) {
			cdao.getCorsiFromStudente(s, corsomap);
		}
		
		// METODO PERRIEMPIRE LA LISTA DI STUDENTI PER OGNI CORSO
		for (Corso c : corsi) {
			sdao.getStudentiFromCorso(c, studentemap);
		}
		
	}
	
	public List<Studente> getStudentiFromCorso(String codins) {
		
		// itero sulla lista di corsi oppure sfrutto Identity Map 
		Corso c = corsomap.get(codins);
		if (c == null) 
			return new ArrayList<Studente>(); // meglio ritornare una lista vuota che null
		
		return c.getStudenti();
		
	}
	
	public List<Corso> getCorsiFromStudente(int matricola) {
		
		// itero sulla lista di studenti oppure sfrutto Identity Map 
		Studente s = studentemap.get(matricola);
		if (s == null)
			return new ArrayList<Corso>(); // meglio ritornare una lista vuota che null
		
		return s.getCorsi(); // NullPointerException
		
	}
	
	public int getTotCreditiFromStudente(int matricola) {
		
		// non dobbiamo più fare query perché è tutto in memoria
		
		int sum = 0;
		
		for (Studente s : studenti) {
			if (s.getMatricola() == matricola) {
				for (Corso c : s.getCorsi())
					sum += c.getCrediti();
				return sum;
			}
		}
		return -1; // se non troviamo lo studente
		
	}
	
	public boolean iscriviStudenteACorso(int matricola, String codins) {
		
		// Il Model deve sempre passare il riferimento agli oggetti al DAO e non solo matricola e codins
		Studente studente = studentemap.get(matricola);
		Corso corso = corsomap.get(codins);
		
		// Se uno dei due oggetti non esiste il metodo fallisce
		if (studente == null || corso == null)
			return false; 
		
		// AGGIORNAMENTO DB
		
		boolean result = sdao.iscriviStudenteACorso(studente, corso);
		
		if (result) { // aggiornamento DB effettuato con successo
			
			// AGGIORNO IN MEMORIA 
			
			// occhio a non aggiungere duplicati perché abbiamo delle List non dei Set
			if (!studente.getCorsi().contains(corso)) {
				studente.getCorsi().add(corso);
			}
			if (!corso.getStudenti().contains(studente)) {
				corso.getStudenti().add(studente);
			}
			return true; 
		}
		
		return false;  // inserimento all'interno del DAO ha datto false
		
	}
	
	// QUESTO METODO VIENE USATO SOLO PER TESTARE LE PERFORMANCE DI ConnectDBCP.
	public void testCP() {
		
		double avgTime = 0;
		for (int i = 0; i < 10; i++) {
			long start = System.nanoTime();
			List<Studente> studenti = sdao.getTuttiStudenti(new StudenteIdMap());
			for (Studente s : studenti) {
				// la query viene ripetuta: 10 * studenti.size()
				sdao.studenteIscrittoACorso(s.getMatricola(), "01NBAPG");
			}
			double tt = (System.nanoTime() - start) / 10e9;
			System.out.println(tt);
			avgTime += tt;
		}
		System.out.println("AvgTime (mean on 10 loops): " + avgTime/10);
		
	}

}
