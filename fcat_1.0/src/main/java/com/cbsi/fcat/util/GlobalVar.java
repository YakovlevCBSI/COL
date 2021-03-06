package com.cbsi.fcat.util;

public class GlobalVar {
	public static final String JENKINS = "jenkins";
	public static boolean isGrid = System.getProperty("useGrid", "false").equals("true") ;
	
	public static boolean isDev = false;
	public static boolean isProd = false;
	
//	public static boolean isGrid = true;
	
	public static String BFPId = "";
	public static String BFPPw= "";
	
	public static String LocalId ="";
	public static String LocalPw= "";
	public static String ProdPw = "";
	
	public static String stageServer = "",
			   devServer = "",
			   BFPServer = "", //need to login using their credentials.
			   prodServer = "";

	public static String embedPath ="manageCatalog/embed-test";
	
	public static String BSId = "";
	public static String BSAccessKey = "";
	
	public static String dbURL = "";
	public static String dbUserName="";
	public static String dbPassword="";
	
	
	public static String ftpURL= "";
	public static String ftpUserName = "";
	public static String ftpPassword = "";
	
	public static String sftpURL = "sftp://sftp.cnetcontentsolutions.com/download/fcat/";
	
	public static String MongoHost="";
	public static String MongoUsername="";
	public static String MongoPassword="";
	
	public  boolean isJenkins(){
		String userName=System.getProperty("user.name");
		if(userName.equals("jenkins") || userName.contains("slave") )
			return true;
		
		return false;
	}
	public void setBFPId(String BFPID){
		this.BFPId = BFPID;
	}
	
	public void  setBFPPw(String BFPPw){
		this.BFPPw = BFPPw;
	}

	public void setLocalID(String LocalID){
		this.LocalId = LocalID;
	}
	
	public void setLocalPw(String LocalPw){
		this.LocalPw = LocalPw;
	}
	
	public void setStageServer(String stageServer){
		this.stageServer = stageServer;
	}
	
	public void setDevServer(String devServer){
		this.devServer = devServer;
	}
	
	public void setBFPServer(String BFPServer){
		this.BFPServer = BFPServer;
	}
	
	public void setProdServer(String prodServer){
		this.prodServer = prodServer;
	}
	
	public void setBSId(String BSId){
		this.BSId = BSId;
	}
	
	public void setBSAccessKey(String BSAccessKey){
		this.BSAccessKey = BSAccessKey;
	}
	
	public void setDbURL(String dbURL){
		this.dbURL = dbURL;
	}
	public void setDbUserName(String dbUserName){
		this.dbUserName = dbUserName;
	}
	public void setDbPassword(String dbPassword){
		this.dbPassword = dbPassword;
	}
	
	public void setFtpUrl(String ftpURL){
		this.ftpURL = ftpURL;
	}
	
	public void setFtpUserName(String ftpUserName){
		this.ftpUserName = ftpUserName;
	}
	
	public void setFtpPassword(String ftpPassword){
		this.ftpPassword = ftpPassword;
	}
	
	public enum Env{
		ALL,
		EMBED,
		FORM,
		STAGE,
		ALLSECURE
		
		
	}
}