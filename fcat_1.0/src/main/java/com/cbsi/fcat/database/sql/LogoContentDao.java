package com.cbsi.fcat.database.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cbsi.fcat.util.PropertyUtil;

public class LogoContentDao {
	
	public final static Logger logger = LoggerFactory.getLogger(LogoContentDao.class);
	
	private static String host = PropertyUtil.get("v3.server");
	private static String dbname = PropertyUtil.get("v3.db");
	private static String username = PropertyUtil.get("v3.username");
	private static String password = PropertyUtil.get("v3.password");
	
	public static final String driverClassName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	public static Connection con;
    public PreparedStatement stmt;
    public ResultSet rs;

	public static void connect(){
		String url = "jdbc:sqlserver://" +host + 
				";databaseName=" + dbname + 
				";user=" + username + ";password=" + password;
		logger.debug(url);
		
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
	
	public static final String LogoNumber = "110";
	public List<LogoContent> getLogoCoverage(String ctype, Long chunkNumber){
		List<LogoContent> logoContents = new ArrayList<LogoContent>();
		
		connect();
		try {
			String sql = "SELECT TOP (?) "  + 
	        		 "PT.ManufacturerId" +
	        		 ",PT.ClearPn" +
	        		 ",UE.ContentTypeId" +
	        		 ",UE.ContentLocaleId" +
	        		 ",UE.ContentSourceId" + 
	        		 ",UPB.Timestamp" +
//	        		 ",v.ValueData" +
	        		 ",UPT.MasterId " +
	        		 "FROM Usage.Entry UE " + 
	        		 "INNER JOIN V3.Usage.Point UPT ON (UPT.PointId = UE.PointId) " + 
	        		 "INNER JOIN V3.Usage.Publish UPB ON (UPB.EntryId = UE.EntryId) " +  
	        		 "INNER JOIN V3.Product.Tag PT ON (PT.MasterId = UPT.MasterId) " + 
	        		 "INNER JOIN V3.Content.MetaDataInteger CMDI ON (CMDI.MetaKey = 3) " +
	        		 "AND (CMDI.ValueId = UPB.PublishedValue) " + 
	        		 "WHERE UE.ContentTypeId = (?) AND " +
	        		 "CMDI.MetaValue = (?)";
			  
			
				stmt = con.prepareStatement(sql);
				stmt.setLong(1,  chunkNumber);
				stmt.setString(2, ctype.split("-")[0]);
				stmt.setString(3, ctype.split("-")[1]);
				
				rs = stmt.executeQuery();
				while(rs.next()){
					logoContents.add( buildLogoContent(rs));
				}
				
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				con.close();
				logger.info("connectino closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return logoContents;
	}

	public LogoContent buildLogoContent(ResultSet rs) throws SQLException{
		LogoContent logoContent = new LogoContent();
		logoContent.setMfId(rs.getString(1));
		logoContent.setMfPn(rs.getString(2));
		logoContent.setCtype(rs.getString(3));
		logoContent.setMasterId(rs.getString(7));
		
//		System.out.println("mfId: " + logoContent.getMfId() + " | mfPn: " + logoContent.getMfPn());
		return logoContent;
	}
	
	public static void main(String[] args){
		int size = new LogoContentDao().getLogoCoverage("110-16", 3500L).size();
		System.out.println(size);
	}
	
}
