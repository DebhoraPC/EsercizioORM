package it.polito.tdp.esercizioorm.dao;

import it.polito.tdp.esercizioorm.model.Corso;
import it.polito.tdp.esercizioorm.model.CorsoIdMap;
import it.polito.tdp.esercizioorm.model.Studente;
import it.polito.tdp.esercizioorm.model.StudenteIdMap;

public class TestDAO {

	public static void main(String[] args) {
		
		CorsoIdMap corsomap = new CorsoIdMap();
		StudenteIdMap studentemap = new StudenteIdMap();
		
		CorsoDAO cdao = new CorsoDAO();
		StudenteDAO sdao = new StudenteDAO();
		
		for (Corso c : cdao.getTuttiCorsi(corsomap))
			System.out.println(c);
		
		for (Studente s : sdao.getTuttiStudenti(studentemap)) 
			System.out.println(s);
		
	}
	
}
