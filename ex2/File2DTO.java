package com.meta.logparsing;

//file2의 데이터를 저장하기위한 DTO 클래스
public class File2DTO {

	private String time;
	private int countProcess;
	private int avgTime;
	private int minTime;
	private int maxTime;
	private int avgSize;
	private int minSize;
	private int maxSize;

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getCountProcess() {
		return countProcess;
	}

	public void setCountProcess(int countProcess) {
		this.countProcess = countProcess;
	}

	public int getAvgTime() {
		return avgTime;
	}

	public void setAvgTime(int avgTime) {
		this.avgTime = avgTime;
	}

	public int getMinTime() {
		return minTime;
	}

	public void setMinTime(int minTime) {
		this.minTime = minTime;
	}

	public int getMaxTime() {
		return maxTime;
	}

	public void setMaxTime(int maxTime) {
		this.maxTime = maxTime;
	}

	public int getAvgSize() {
		return avgSize;
	}

	public void setAvgSize(int avgSize) {
		this.avgSize = avgSize;
	}

	public int getMinSize() {
		return minSize;
	}

	public void setMinSize(int minSize) {
		this.minSize = minSize;
	}

	public int getMaxSize() {
		return maxSize;
	}

	public void setMaxSize(int maxSize) {
		this.maxSize = maxSize;
	}

}
