package com.meta.logparsing;

// file1의 데이터를 저장하기위한 DTO 클래스
public class File1DTO {

	private String threadId;
	private String startTime;
	private String esbId;
	private String contentLen;
	private String callTime;
	private String beforeMarshalling;
	private String marshalling;
	private String invoking;
	private String unmarshalling;
	private String endTime;

	public String getThreadId() {
		return threadId;
	}

	public void setThreadId(String threadId) {
		this.threadId = threadId;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEsbId() {
		return esbId;
	}

	public void setEsbId(String esbId) {
		this.esbId = esbId;
	}

	public String getContentLen() {
		return contentLen;
	}

	public void setContentLen(String contentLen) {
		this.contentLen = contentLen;
	}

	public String getCallTime() {
		return callTime;
	}

	public void setCallTime(String callTime) {
		this.callTime = callTime;
	}

	public String getBeforeMarshalling() {
		return beforeMarshalling;
	}

	public void setBeforeMarshalling(String beforeMarshalling) {
		this.beforeMarshalling = beforeMarshalling;
	}

	public String getMarshalling() {
		return marshalling;
	}

	public void setMarshalling(String marshalling) {
		this.marshalling = marshalling;
	}

	public String getInvoking() {
		return invoking;
	}

	public void setInvoking(String invoking) {
		this.invoking = invoking;
	}

	public String getUnmarshalling() {
		return unmarshalling;
	}

	public void setUnmarshalling(String unmarshalling) {
		this.unmarshalling = unmarshalling;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

}
