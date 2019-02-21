package com.meta.xmlparsing;

// F_idx_TB.xml������ <ROW> �±��� �ڽ��±��� ���� �����ϱ� ���� DTO Ŭ����
public class FDTO {
	
	private boolean overFifteen; // similar_rate / 100 > 15 ���� Ȯ�� ����
	private boolean equalP_id; // fP_id == pP_id ���� Ȯ�� ����
	private String rowId;
	private String volume;
	private String file_name;
	private String release_name;
	private String similar_rate;
	private String file_path;
	private String p_id;
	private String exclusion;
	private String comment;
	
	public FDTO() {
		this.overFifteen = false;
		this.equalP_id = false;
	}
	public boolean isEqualP_id() {
		return equalP_id;
	}
	public void setEqualP_id(boolean equalP_id) {
		this.equalP_id = equalP_id;
	}
	public boolean isOverFifteen() {
		return overFifteen;
	}
	public void setOverFifteen(boolean overFifteen) {
		this.overFifteen = overFifteen;
	}
	public String getRowId() {
		return rowId;
	}
	public void setRowId(String rowId) {
		this.rowId = rowId;
	}
	public String getVolume() {
		return volume;
	}
	public void setVolume(String volume) {
		this.volume = volume;
	}
	public String getFile_name() {
		return file_name;
	}
	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}
	public String getRelease_name() {
		return release_name;
	}
	public void setRelease_name(String release_name) {
		this.release_name = release_name;
	}
	public String getSimilar_rate() {
		return similar_rate;
	}
	public void setSimilar_rate(String similar_rate) {
		this.similar_rate = similar_rate;
	}
	public String getFile_path() {
		return file_path;
	}
	public void setFile_path(String file_path) {
		this.file_path = file_path;
	}
	public String getP_id() {
		return p_id;
	}
	public void setP_id(String p_id) {
		this.p_id = p_id;
	}
	public String getExclusion() {
		return exclusion;
	}
	public void setExclusion(String exclusion) {
		this.exclusion = exclusion;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
}
