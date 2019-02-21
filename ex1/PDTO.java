package com.meta.xmlparsing;

//P_idx_TB.xml파일의 <ROW> 태그의 자식태그의 값을 저장하기 위한 DTO 클래스
public class PDTO {
	
	private String p_id;
	private String project_name;
	private String license_id;
	private String license_name;
	private String license_display;
	private String size;
	private String topic_cd;
	private String topic_name;
	
	public String getP_id() {
		return p_id;
	}
	public void setP_id(String p_id) {
		this.p_id = p_id;
	}
	public String getProject_name() {
		return project_name;
	}
	public void setProject_name(String project_name) {
		this.project_name = project_name;
	}
	public String getLicense_id() {
		return license_id;
	}
	public void setLicense_id(String license_id) {
		this.license_id = license_id;
	}
	public String getLicense_name() {
		return license_name;
	}
	public void setLicense_name(String license_name) {
		this.license_name = license_name;
	}
	public String getLicense_display() {
		return license_display;
	}
	public void setLicense_display(String license_display) {
		this.license_display = license_display;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getTopic_cd() {
		return topic_cd;
	}
	public void setTopic_cd(String topic_cd) {
		this.topic_cd = topic_cd;
	}
	public String getTopic_name() {
		return topic_name;
	}
	public void setTopic_name(String topic_name) {
		this.topic_name = topic_name;
	}
	
}
