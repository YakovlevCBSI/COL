package com.cbsi.fcat.database.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cbsi.fcat.database.sql.LogoContentDao;
import com.cbsi.fcat.util.PropertyUtil;

public class SqlServerConnector {

	public final static Logger logger = LoggerFactory.getLogger(SqlServerConnector.class);

	private static String host = PropertyUtil.get("v3.server");
	private static String dbname = PropertyUtil.get("v3.db");
	private static String username = PropertyUtil.get("v3.username");
	private static String password = PropertyUtil.get("v3.password");
	
	public static final String driverClassName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	public static Connection con;

	public static void connect(){
		String url = "jdbc:sqlserver://" +host + 
				";databaseName" + dbname + 
				";user=" + username + ";password" + password;
		
		try {
			Class.forName(driverClassName);
			con = DriverManager.getConnection(url);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
