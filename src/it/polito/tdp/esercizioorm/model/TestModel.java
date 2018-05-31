package it.polito.tdp.esercizioorm.model;

import java.util.List;

public class TestModel {

	public static void main(String[] args) {

		Model m = new Model();
		
		int matricola = 146101;
		int result = m.getTotCreditiFromStudente(matricola);
		if (result == -1) {
			System.out.println("Non esiste questo studente\n");
		}
		System.out.println("Tot crediti: " + result + "\n");
		
		System.out.println("Studenti iscritti a 01NBAPG");
		List<Studente> resultstudenti = m.getStudentiFromCorso("01NBAPG");
		for (Studente s : resultstudenti) {
			System.out.println(s);
		}
		System.out.println("\n");
		
		System.out.println("Corsi a cui è iscritto: " + matricola);
		List<Corso> resultcorsi = m.getCorsiFromStudente(matricola);
		for (Corso c : resultcorsi) {
			System.out.println(c);
		}
		System.out.println("\n");
		
		// Test the connection Pooling perfomances: 
		m.testCP();
		
		// Result: 
		
		/* Without CP
		* 0.1249689767
		* 0.1248598453
		* 0.0947944966
		* 0.1375450558
		* 0.0954319473
		* 0.0886381945
		* 0.1563275739
		* 0.0942817106
		* 0.0960478704
		* 0.0925078412
		* AvgTime (mean on 10 loops): 0.11054035123000001
		*/
		
		/* With CP
		* 0.0258318698
		* 0.0210406035
		* 0.0170516183
		* 0.021747395
		* 0.017576221
		* 0.0154856773
		* 0.012929591
		* 0.0177840052
		* 0.0170433854
		* 0.0149857197
		* AvgTime (mean on 10 loops): 0.018147608620000002
		*/
		
	}

}

