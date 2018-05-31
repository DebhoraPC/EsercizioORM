package it.polito.tdp.esercizioorm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.esercizioorm.model.Corso;
import it.polito.tdp.esercizioorm.model.Studente;
import it.polito.tdp.esercizioorm.model.StudenteIdMap;

public class StudenteDAO {

	public List<Studente> getTuttiStudenti(StudenteIdMap studentimap)
	{

		String sql = "SELECT matricola, nome, cognome, cds FROM studente";

		List<Studente> result = new ArrayList<Studente>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				Studente s = new Studente(res.getInt("matricola"), res.getString("nome"), res.getString("cognome"),
						res.getString("cds"));
				result.add(studentimap.get(s));
			}

			conn.close();

		} catch (SQLException e) {
			e.printStackTrace(); System.err.println("Errore Db in getTuttiStudenti");
			throw new RuntimeException(e);
		}

		return result;
		
	}
	
	public void getStudentiFromCorso(Corso c, StudenteIdMap studentemap) {
		
		String sql = "SELECT s.matricola, s.nome, s.cognome, s.cds "
					+ "FROM studente AS s, iscrizione AS i "
					+ "WHERE s.matricola = i.matricola AND i.codins = ?";
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, c.getCodIns());
			
			ResultSet res = st.executeQuery();

			while (res.next()) {
				Studente s = new Studente(res.getInt("matricola"), res.getString("nome"), res.getString("cognome"),
						res.getString("cds"));
						c.getStudenti().add(studentemap.get(s));
			}

			conn.close();

		} catch (SQLException e) {
			e.printStackTrace(); System.err.println("Errore Db in getStudentiFromCorso");
			throw new RuntimeException(e);
		}
		
	}
	
	public boolean iscriviStudenteACorso(Studente studente, Corso corso) {
		
		// PROVARE A FARE GIRARE QUESTA QUERY, COMUNQUE IGNORE VUOL DIRE CHE SE PER CASO PROVI AD INSERIRE UNA RIGA
		// NEL DB CHE C'E' GIA' SEMPLICEMENTE LA IGNORA E NON LA INSERISCE
		String sql = "INSERT IGNORE INTO iscrizione (matricola, codins) VALUES (?, ?)";
		boolean returnValue = false;
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setInt(1, studente.getMatricola());
			st.setString(2, corso.getCodIns());
			
			int res = st.executeUpdate(); 
			
			if (res == 1)
				returnValue = true;

			conn.close();

		} catch (SQLException e) {
			e.printStackTrace(); System.err.println("Errore Db in iscriviStudenteACorso");
			throw new RuntimeException(e);
		}
		
		return returnValue;
		
	}
	
	// QUESTO METODO VIENE UTILIZZATO SOLO PER TESTARE LE PERFORMANCE DI ConnectDBCP.
	public boolean studenteIscrittoACorso(int matricola, String codins) {
		
		String sql = "SELECT matricola, codins FROM iscrizione WHERE matricola = ? and codins = ?";
		boolean result = false;
		
		try {
			
			Connection conn = ConnectDBCP.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setInt(1, matricola);
			st.setString(2, codins);
			
			ResultSet res = st.executeQuery();

			if (res.next()) {
				result = true;
			}

			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace(); System.err.println("Errore Db in studenteIscrittoACorso");
			throw new RuntimeException(e);
		}
		
	}

}
