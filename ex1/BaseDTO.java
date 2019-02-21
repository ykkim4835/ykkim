package com.meta.xmlparsing;

//T_BASEFILE_TB.xml파일의 <ROW> 태그의 자식태그의 값을 저장하기 위한 DTO 클래스
public class BaseDTO {
	
	private String file_id;
	private String file_name;
	private String file_ext;
	private String file_path;
	private String real_path;
	private String copy_path;
	private String status;
	private String start_time;
	private String end_time;
	private String target_file_count;
	private String exclusion_count;
	
	public String getFile_id() {
		return file_id;
	}
	public void setFile_id(String file_id) {
		this.file_id = file_id;
	}
	public String getFile_name() {
		return file_name;
	}
	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}
	public String getFile_ext() {
		return file_ext;
	}
	public void setFile_ext(String file_ext) {
		this.file_ext = file_ext;
	}
	public String getFile_path() {
		return file_path;
	}
	public void setFile_path(String file_path) {
		this.file_path = file_path;
	}
	public String getReal_path() {
		return real_path;
	}
	public void setReal_path(String real_path) {
		this.real_path = real_path;
	}
	public String getCopy_path() {
		return copy_path;
	}
	public void setCopy_path(String copy_path) {
		this.copy_path = copy_path;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	public String getEnd_time() {
		return end_time;
	}
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
	public String getTarget_file_count() {
		return target_file_count;
	}
	public void setTarget_file_count(String target_file_count) {
		this.target_file_count = target_file_count;
	}
	public String getExclusion_count() {
		return exclusion_count;
	}
	public void setExclusion_count(String exclusion_count) {
		this.exclusion_count = exclusion_count;
	}
	
}
