package com.cbsi.fcat.database.util;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cbsi.fcat.database.sql.Catalog;
import com.cbsi.fcat.database.sql.LogoContentDao;
import com.cbsi.fcat.util.GlobalVar;

public class MySQLConnector {
	
	//ALL CREDITINAL SHOULD GO ON FCATCredit.txt
//	static String dbURL = GlobalVar.dbURL;
//	static String userName = GlobalVar.dbUserName;
//	static String password = GlobalVar.dbPassword;
	public final static Logger logger = LoggerFactory.getLogger(MySQLConnector.class);

	static String dbURL = "jdbc:mysql://fcat-engine.cloudapp.net:3306";
	static String userName = "greenbox";
	static String password = "greenbox";
	
	//#####################################################################//
	
	static String dbClass = "com.mysql.jdbc.Driver";
	
	private static Connection con;
	
	public MySQLConnector connectToFcatDB(){
		
		try {
			Class.forName(dbClass).newInstance();
			con = DriverManager.getConnection(dbURL, userName, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
		}
		
		return this;
	}
	
	
	public <T> List<T> runQuery(String query, Class<T> c1, boolean mongoInUse){
		List<T> objectList = null;
		try{

			Statement stmt = (Statement) con.createStatement();
			stmt.execute("use fcat;");
			PreparedStatement prs= con.prepareStatement(query);
			ResultSet result = (ResultSet) prs.executeQuery();
			
			objectList = new ArrayList<T>();
			
			while(result.next()){
				T inst = c1.newInstance();
//				if(inst instanceof Catalog){
//					Catalog catalog = new Catalog();
//					catalog.setCatalog_name(result.getString("catalog_name"));
//					//catalog.setCreated(result.getDate("created"));
//					//catalog.setParty(result.getBigDecimal("party"));
//					catalog.setItemCount(result.getBigDecimal("item_count"));
//					catalog.setModifiedBy(result.getString("modified_by"));
//					
//					//This will also set catId and partyID;
//					if(mongoInUse){
//						catalog.setId(result.getBigDecimal("id")+"");
//						catalog.setParty(result.getBigDecimal("party"));
//					}
//					
//					objectList.add((T) catalog);
//				}
//				result.get
			}
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return objectList;
	}
	
	public List<String> runQuery(String query){
		List<String> list = new ArrayList<String>();
		try{

			Statement stmt = (Statement) con.createStatement();
			PreparedStatement prs= con.prepareStatement(query);
			ResultSet result = (ResultSet) prs.executeQuery();
			
			int count = 0;
			while(result.next()){
				count++;
				list.add(result.getString("party_name"));
			}
			
			System.out.println("RunQuery count: " + count);
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return list;
	}
	
	public void quit(){
		if(con != null){
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	//--------------------------------------Debug--------------------------------------------//
	
	public static void main(String[] args) throws InterruptedException{
		String query = "select *from fcat.catalog"
				+ " where fcat.catalog.party = 1 and not active =0" 
				+ " order by catalog_name";
		
		List<Catalog> list = new MySQLConnector().connectToFcatDB().runQuery(query, Catalog.class, false);
		
		for(Catalog c: list){
			System.out.println(c.getCatalog_name());
		}
	}
}
