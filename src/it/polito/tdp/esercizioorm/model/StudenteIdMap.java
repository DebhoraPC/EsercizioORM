package it.polito.tdp.esercizioorm.model;

import java.util.HashMap;
import java.util.Map;

public class StudenteIdMap {
	
	private Map<Integer, Studente> map; 

	public StudenteIdMap() {
		map = new HashMap<>();
	}
	
	// ESTENDO LE FUNZIONI ORIGINARIE DELL'Identity Map
	public Studente get(int matricola) {
		return map.get(matricola);
	}
	
	public Studente get(Studente studente) {
		Studente old = map.get(studente.getMatricola());
		if (old == null) {
			// nella mappa non c'è questo studente
			map.put(studente.getMatricola(), studente);
			return studente;
		}
		return old;
	}
	
	public void put(Integer matricola, Studente studente) {
		map.put(matricola, studente);
	}



}
