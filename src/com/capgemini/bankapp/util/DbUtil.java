package com.capgemini.bankapp.util;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;



public class DbUtil {

	private static String dburl;
	private static String username;
	private static String password;
	
	public static Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");

			 connection = DriverManager.getConnection(dburl, username, password);
		}catch (ClassNotFoundException e) {
	
			System.out.println("Driver Class not found");
		}catch (SQLException e){
			e.printStackTrace();
		}
		
		return connection;
	}
	
	static {
		try {
			File propertiesFile = new File("dbConfig.properties");
			FileReader reader = new FileReader(propertiesFile);
			
			Properties properties = new Properties();
			properties.load(reader);
			reader.close();
			
			dburl = properties.getProperty("dburl");
			username = properties.getProperty("username");
			password = properties.getProperty("password");
		}catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
