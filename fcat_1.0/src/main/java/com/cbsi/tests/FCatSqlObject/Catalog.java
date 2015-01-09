package com.cbsi.tests.FCatSqlObject;

import java.math.BigDecimal;
import java.sql.Date;

public class Catalog {
	public Catalog(){
		
	}
	
	private String catalog_name;
	private Date created;
	private BigDecimal party;
	private BigDecimal itemCount;
	private String modifiedBy;
	private String id;
	
	public String getCatalog_name(){
		return catalog_name;
	}
	
	public void setCatalog_name(String catalog_name){
		this.catalog_name = catalog_name;
	}
	
	public Date getCreated(){
		return created;
	}
	
	public void setCreated(Date date){
		this.created = date;
	}
	
	public BigDecimal getParty(){
		return party;
	}
	
	public void setParty(BigDecimal party){
		this.party = party;
	}
	
	public BigDecimal getItemCount(){
		return itemCount;
	}
	
	public void setItemCount(BigDecimal bigDecimal){
		this.itemCount = bigDecimal;
	}
	
	public String getModifiedBy(){
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy){
		this.modifiedBy = modifiedBy;
	}
	
	public String getId(){
		return id;
	}
	
	public void setId(String id){
		this.id = id;
	}
	
	@Override
	public boolean equals(Object other){
		if(!(other instanceof Catalog)) return false;
		
		Catalog that= (Catalog) other;
		
		//System.out.println("using custome equal method");
		
		return (this.catalog_name==null?this.catalog_name==that.catalog_name:this.catalog_name.equals(that.catalog_name))
				&& (this.created==null?this.created==that.created:this.created.equals(that.created))
				&& (this.itemCount==null?this.itemCount==that.itemCount: this.itemCount.equals(that.itemCount))
				&& (this.modifiedBy==null?this.modifiedBy==that.modifiedBy: this.modifiedBy.equals(that.modifiedBy))
				&& (this.party==null?this.party==that.party: this.party.equals(that.party));
		
	}
	
	@Override 
	public String toString(){
		return "name: " + getCatalog_name() + "\t modified: " + getModifiedBy() + "\tparty: " + getParty() 
				+ "\titemCount: " + getItemCount() + "\tcatalog_id: " + getId();
	}
	
}
