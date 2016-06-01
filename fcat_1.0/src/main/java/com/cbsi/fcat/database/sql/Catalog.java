package com.cbsi.fcat.database.sql;

import java.util.Date;

public class Catalog {

	private long id;
	private int active;
	private String catalog_name;
	private Date created;
	private String description;
	private long item_count;
	private Date last_modified;
	private int version;
	private long catalog_import_type;
	private long catalog_type;
	private long content_type;
	private long party;
	private String code;
	private long mapped_count;
	private String modified_by;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int getActive() {
		return active;
	}
	public void setActive(int active) {
		this.active = active;
	}
	public String getCatalog_name() {
		return catalog_name;
	}
	public void setCatalog_name(String catalog_name) {
		this.catalog_name = catalog_name;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public long getItem_count() {
		return item_count;
	}
	public void setItem_count(long item_count) {
		this.item_count = item_count;
	}
	public Date getLast_modified() {
		return last_modified;
	}
	public void setLast_modified(Date last_modified) {
		this.last_modified = last_modified;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public long getCatalog_import_type() {
		return catalog_import_type;
	}
	public void setCatalog_import_type(long catalog_import_type) {
		this.catalog_import_type = catalog_import_type;
	}
	public long getCatalog_type() {
		return catalog_type;
	}
	public void setCatalog_type(long catalog_type) {
		this.catalog_type = catalog_type;
	}
	public long getContent_type() {
		return content_type;
	}
	public void setContent_type(long content_type) {
		this.content_type = content_type;
	}
	public long getParty() {
		return party;
	}
	public void setParty(long party) {
		this.party = party;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public long getMapped_count() {
		return mapped_count;
	}
	public void setMapped_count(long mapped_count) {
		this.mapped_count = mapped_count;
	}
	public String getModified_by() {
		return modified_by;
	}
	public void setModified_by(String modified_by) {
		this.modified_by = modified_by;
	}

	
}
