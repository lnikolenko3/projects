package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;

public class Connector {
	private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DB_URL = "jdbc:mysql://academic-mysql.cc.gatech.edu/cs4400_Team_13";
	private static final String USER = "cs4400_Team_13";
	private static final String PASS = "UmwyG6KP";

	private Connection conn;
	private ResultSet rs;
	private Statement stmt;

	public Connector() {
		conn = null;
		rs = null;
		stmt = null;
	}

	public void connect() {
		try {
			Class.forName(JDBC_DRIVER).newInstance();
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
		} catch (Exception e) {
			System.out.println("Sorry, could not connect to the database");
		}
	}

	public ResultSet execute(String statement) throws Exception {
		if (conn == null || statement == null) {
			return null;
		}
		Statement stmt = conn.createStatement();
		return stmt.executeQuery(statement);
	}

	public void update(String statement) throws Exception {
		if (conn == null || statement == null) {
			return;
		}
		Statement stmt = conn.createStatement();
		stmt.executeUpdate(statement);
	}

	public void close() {
		try {
			if (stmt != null) {
				stmt.close();
			}
			if (rs != null) {
				rs.close();
			}
			if (conn != null) {
				conn.close();
			}
			System.out.println("Connection successfully closed.");
		} catch (Exception e) {
			System.out.println("Sorry, something went wrong");
		}
	}
}
