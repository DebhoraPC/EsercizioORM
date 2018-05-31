package it.polito.tdp.esercizioorm.model;

import java.util.HashMap;
import java.util.Map;

public class CorsoIdMap {

	private Map<String, Corso> map;
	
	public CorsoIdMap() {
		map = new HashMap<>();
	}
	
	// ESTENDO LE FUNZIONI ORIGINARIE DELL'Identity Map
	public Corso get(String codins) {
		return map.get(codins);
	}
	
	public Corso get(Corso corso) {
		Corso old = map.get(corso.getCodIns());
		if (old == null) {
			// nella mappa non c'è questo corso
			map.put(corso.getCodIns(), corso);
			return corso;
		}
		return old;
	}
	
	public void put(String codins, Corso corso) {
		map.put(codins, corso);
	}
}
