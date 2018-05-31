package it.polito.tdp.esercizioorm.dao;

import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class ConnectDBCP {

	private static final String jdbcURL = "jdbc:mysql://localhost/iscritticorsi";
	private static HikariDataSource ds;
	
	public static Connection getConnection() {
		
		if (ds == null) {
			
			HikariConfig config = new HikariConfig();
			
			config.setJdbcUrl(jdbcURL);
			config.setUsername("root");
			config.setPassword("root");
			
			// configurazione MySQL: non siamo obbligati a farla
			config.addDataSourceProperty("cachePrepStmts", "true");
			config.addDataSourceProperty("prepStmtCacheSize", "250");
			config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
			
			ds = new HikariDataSource(config);
			
		}
		
		try {
			
			return ds.getConnection();

		} catch (SQLException e) {
			System.err.println("Errore connessione al DB");
			throw new RuntimeException(e);
		}
		
	}

}
