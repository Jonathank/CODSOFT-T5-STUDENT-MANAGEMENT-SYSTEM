package database.conn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

	private static Connection conn = null;
	static {
		String URL ="jdbc:mysql://localhost/StudentManagementSystem";
		String USER = "root";
		String PASS = "Jnana_2002";
		
		try {
			
			conn = DriverManager.getConnection(URL, USER, PASS);
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public static Connection getConnection() {
		return conn;
	}
}
