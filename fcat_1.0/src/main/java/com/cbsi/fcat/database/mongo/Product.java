package com.cbsi.fcat.database.mongo;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonSubTypes;
import org.codehaus.jackson.annotate.JsonTypeInfo;

/**
 * Uses Jackson lib to parse Json data from mongo.
 * @author alpark
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Product {

	
	private int map;
	//private String itemIds;
	private String id;
	private String mfpn;
	private String mf;
	
	//--------optional variables------//
	private String locale="";
	private String contentType="";
	
	private String msrp="";
	private String price="";
	private String productUrl="";
	private String inventory="";
	private String cnetSkuId="";
	private String upcEan="";
	
	public Product(){
		
	}
	
	public Product(int map, String id, String mfpn, String mf, String locale, String contentType, String msrp, String price, String inventory, String cnetSkuId, String upcEan, String productUrl){
		this.map = map;
		this.id = id;
		this.mfpn = mfpn;
		this.mf = mf;
		this.locale = locale;
		this.contentType = contentType;
		this.msrp = msrp;
		this.price = price;
		this.inventory = inventory;
		this.cnetSkuId = cnetSkuId;
		this.upcEan = upcEan;
		this.productUrl = productUrl;
	}
	
	public String getId(){
		return id;
	}

	public void setId(String id){
		this.id = id;
	}
	
	public String getMfpn(){
		return mfpn;
	}
	
	public void setMfpn(String mfpn){
		this.mfpn = mfpn;
	}
	
	public String getMf(){
		return mf;
	}
	
	public void setMf(String mf){
		this.mf = mf;
	}
	
	public String getUpcEan(){
		return upcEan;
	}
	
	public void setUpcEan(String upcEan){
		this.upcEan = upcEan;
	}
	
	public int getMap(){
		return map;
	}
	
	public void setMap(int map){
		this.map = map;
	}
	
	public String getLocale(){
		return locale;
	}
	
	public void setLocale(String locale){
		this.locale = locale;
	}
	
	public String getContentType(){
		return contentType;
	}
	
	public void setContentType(String contentType){
		this.contentType = contentType;
	}
	
	public String getMsrp(){
		return msrp;
	}
	
	public void setMsrp(String msrp){
		this.msrp = msrp;
	}
	
	public String getPrice(){
		return price;
	}
	
	public void setPrice(String price){
		this.price = price;
	}
	
	public String getProductUrl(){
		return productUrl;
	}
	
	public void setProductUrl(String productUrl){
		this.productUrl = productUrl;
	}
	
	public String getInevntory(){
		return inventory;
	}
	
	public void setInventory(String inventory){
		this.inventory = inventory;
	}
	
	public String getCnetSkuId(){
		return cnetSkuId;
	}
	
	public void setCentSkuId(String cnetSkuId){
		this.cnetSkuId = cnetSkuId;
	}

	@Override
	public boolean equals(Object other){
		if(!(other instanceof Product)) return false;
		
		Product that= (Product) other;
		/**
		//System.out.println("using custome equal method");
		System.out.println("=====================>");
		System.out.println("map:"+ (this.map == that.map));
		System.out.println(this.id.equals(that.id));
		System.out.println( this.mfpn.equals(that.mfpn));
		System.out.println(this.mf.equals(that.mf));
		System.out.println(this.locale.equals(that.locale));
		System.out.println(this.msrp.equals(that.msrp));
		System.out.println(this.price.equals(that.price));
		System.out.println( this.inventory.equals(that.inventory));
		System.out.println(this.productUrl.equals(that.productUrl));
		System.out.println(this.cnetSkuId.equals(that.cnetSkuId));
		System.out.println(this.upcEan.equals(that.upcEan));
*/

		
		//System.out.println(this.locale + "/ " + that.locale);
		return (this.id.equals(that.id)
				&& this.map == (that.map)
				&& this.mfpn.equals(that.mfpn)
				&& this.mf.equals(that.mf)
				&& this.locale.equals (that.locale)
				&& this.msrp.equals(that.msrp)
				&& this.price.equals(that.price)
				&& this.inventory.equals(that.inventory)
				&& this.productUrl.equals(that.productUrl)
				&& this.cnetSkuId.equals(that.cnetSkuId)
				&& this.upcEan.equals(that.upcEan));
		
	}
	
	@Override
	public String toString(){
		return "map: " + map + "\tmfpn: " + mfpn + "\tupcEan: " + upcEan + "\tmf: " + mf + "\tid: " + id + "\tlocale: " + locale + 
				"\tcontentType: " + contentType + "\tmsrp: " + msrp + "\tprice: "+ price +
				"\tproductUrl: " + productUrl + "\tinventory: " + inventory + "\tcnetSkuId: " + cnetSkuId;
	}
}
