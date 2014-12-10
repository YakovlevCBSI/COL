package com.cbsi.tests.util;

import java.io.IOException;

public class GlobalVar {
	public static String BFPId = "";
	public static String BFPPw= "";
	
	public static String LocalId ="";
	public static String LocalPw= "";
	
	public static String stageServer = "",
			   devServer = "",
			   BFPServer = ""; //need to login using their credentials.

	public static String embedPath ="manageCatalog/embed-test";
	
	public static String BSId = "";
	public static String BSAccessKey = "";
	
	

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
	
	public void setBSId(String BSId){
		this.BSId = BSId;
	}
	
	public void setBSAccessKey(String BSAccessKey){
		this.BSAccessKey = BSAccessKey;
	}
}