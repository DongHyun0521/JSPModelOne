// JSPModelOne - com.mbc.pro.db - DBConnection.java
package com.mbc.pro.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	public static void initConnection() {
		try {
			Class.forName("org.postgresql.Driver");
			System.out.println("[ 드라이버 불러오기 성공 ]");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/mydb", "postgres", "1234");
			System.out.println("[ PostgreSQL 연결 성공 ]");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
}
