package com.meta.logparsing;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;

// file12를 출력하기 위한 클래스(데이터 정리)
public class LogParser2 {

	ArrayList<File1DTO> file1List;
	ArrayList<File2DTO> file2List;
	File2DTO file2DTO;
	int idx; // 각 시간 그룹의 첫번째 데이터의 인덱스

	// 분석된 로그로 부터 데이터를 가져오는 메소드(초기화)
	public void init() throws IOException {
		LogParser1 logParser1 = new LogParser1();
		logParser1.execute();

		file1List = logParser1.file1List;
		file2List = new ArrayList<>();
		file2DTO = null;
		idx = 0;
	}

	// 최종 실행 메소드
	public void execute() throws ParseException, IOException {
		init();

		BufferedWriter out = new BufferedWriter(new FileWriter("logParser2.log"));
		String time = null;
		int countProcess = 0;
		int avgTime = 0;
		int minTime = 0;
		int maxTime = 0;
		int minSize = 0;
		int maxSize = 0;
		int avgSize = 0;
		
		// idx가 한번에 각 그룹내의 멤버 수 만큼 증가한다.
		// while문은 그룹의 수 만큼 반복한다.
		while (!(idx == file1List.size())) {
			time = getTime();
			countProcess = getCountProcess(idx, time);
			avgTime = getAvgTime(idx, time);
			minTime = getMinTime(idx, time);
			maxTime = getMaxTime(idx, time);
			minSize = getMinSize(idx, time);
			maxSize = getMaxSize(idx, time);
			avgSize = getAvgSize(idx, time);
			setIdx(countProcess); // idx가 다음 시간 그룹의 첫번째 데이터로 이동한다.

			file2DTO = new File2DTO();
			file2DTO.setTime(time);
			file2DTO.setCountProcess(countProcess);
			file2DTO.setAvgTime(avgTime);
			file2DTO.setMinTime(minTime);
			file2DTO.setMaxTime(maxTime);
			file2DTO.setMinSize(minSize);
			file2DTO.setMaxSize(maxSize);
			file2DTO.setAvgSize(avgSize);

			file2List.add(file2DTO);
		}

		// 파일을 출력한다.
		for (int i = 0; i < file2List.size(); i++) {
			String tempTime = file2List.get(i).getTime();
			int tmepCountProcess = file2List.get(i).getCountProcess();
			int tmepAvgTime = file2List.get(i).getAvgTime();
			int tmepMinTime = file2List.get(i).getMinTime();
			int tmepMaxTime = file2List.get(i).getMaxTime();
			int tmepAvgSize = file2List.get(i).getAvgSize();
			int tmepMinSize = file2List.get(i).getMinSize();
			int tmepMaxSize = file2List.get(i).getMaxSize();

			out.write(tempTime + "," + tmepCountProcess + "," + tmepAvgTime + "," + tmepMinTime + "," + tmepMaxTime
					+ "," + tmepAvgSize + "," + tmepMinSize + "," + tmepMaxSize + "\r\n");
		}

		out.close();
	}

	// 각 시간 그룹의 첫번째 데이터의 인덱스 값을 정해주는 메소드
	public void setIdx(int countProcess) {
		idx += countProcess;
	}

	// 시작 시간의 초를 뺀 부분을 잘라서 가져오는 메소드(년.월.일 시:분)
	public String getTime() {
		String time = file1List.get(idx).getStartTime().substring(0, 14);

		return time;
	}

	// 나누어지 시간 그룹안의 멤버 수를 카운트하는 메소드(처리건수)
	public int getCountProcess(int idx, String time) {
		int countProcess = 0;
		for (int i = idx; i < file1List.size(); i++) { // i = idx (i에 idx를 대입)
			String startTime = file1List.get(i).getStartTime();
			if (startTime.contains(time)) {
				countProcess++;
			} else {
				break;
			}
		}

		return countProcess;
	}

	// 평균 소요시간을 구하는 메소드
	public int getAvgTime(int idx, String time) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yy.MM.dd HH:mm:ss");
		ArrayList<Long> tempList = new ArrayList<>();
		Date start = null;
		Date end = null;
		int sum = 0;
		int avg = 0;

		// startTime(년.월.일 시:분:초)이 파라미터로 들어온 time 변수(년.월.일 시:분)를 포함하면
		// 시작시간과 종료시간의 차를 구한다.
		// 구한 값을 tempList에 넣는다.
		for (int i = idx; i < file1List.size(); i++) {
			String startTime = file1List.get(i).getStartTime();
			if (startTime.contains(time)) {
				String endTime = file1List.get(i).getEndTime();
				start = format.parse(startTime);
				end = format.parse(endTime);
				long result = end.getTime() - start.getTime();
				tempList.add(result);
			} else {
				break;
			}
		}

		// tempList에 저장되어 있는 값들의 평균을 구한다.
		for (int i = 0; i < tempList.size(); i++) {
			sum += tempList.get(i);
		}

		avg = sum / tempList.size();

		return avg;
	}

	// 최소 시간을 구하는 메소드
	public int getMinTime(int idx, String time) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yy.MM.dd HH:mm:ss");
		ArrayList<Long> tempList = new ArrayList<>();
		Date start = null;
		Date end = null;
		long min = 0;

		for (int i = idx; i < file1List.size(); i++) {
			String startTime = file1List.get(i).getStartTime();
			if (startTime.contains(time)) {
				String endTime = file1List.get(i).getEndTime();
				start = format.parse(startTime);
				end = format.parse(endTime);
				long result = end.getTime() - start.getTime();
				tempList.add(result);
			} else {
				break;
			}
		}

		// tempList에 저장되어 있는 값들의 최소값을 구한다.
		min = tempList.get(0);
		for (int i = 1; i < tempList.size(); i++) {
			if (tempList.get(i) < min) {
				min = tempList.get(i);
			}
		}

		return (int) min;
	}

	// 최대 시간을 구하는 메소드
	public int getMaxTime(int idx, String time) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yy.MM.dd HH:mm:ss");
		ArrayList<Long> tempList = new ArrayList<>();
		Date start = null;
		Date end = null;
		long max = 0;

		for (int i = idx; i < file1List.size(); i++) {
			String startTime = file1List.get(i).getStartTime();
			if (startTime.contains(time)) {
				String endTime = file1List.get(i).getEndTime();
				start = format.parse(startTime);
				end = format.parse(endTime);
				long result = end.getTime() - start.getTime();
				tempList.add(result);
			} else {
				break;
			}
		}

		// tempList에 저장되어 있는 값들의 최대값을 구한다.
		max = tempList.get(0);
		for (int i = 1; i < tempList.size(); i++) {
			if (tempList.get(i) > max) {
				max = tempList.get(i);
			}
		}

		return (int) max;
	}

	// 평균 사이즈를 구하는 메소드
	public int getAvgSize(int idx, String time) {
		ArrayList<Integer> tempList = new ArrayList<>();
		int sum = 0;
		int avg = 0;

		// startTime(년.월.일 시:분:초)이 파라미터로 들어온 time 변수(년.월.일 시:분)를 포함하면
		// 사이즈를 tempList에 넣는다.
		for (int i = idx; i < file1List.size(); i++) {
			String startTime = file1List.get(i).getStartTime();
			if (startTime.contains(time)) {
				String contentLen = file1List.get(i).getContentLen();
				int intContentLen = Integer.parseInt(contentLen);
				tempList.add(intContentLen);
			} else {
				break;
			}
		}

		// tempList에 저장되어 있는 값들의 평균값을 구한다.
		for (int i = 0; i < tempList.size(); i++) {
			sum += tempList.get(i);
		}

		avg = sum / tempList.size();

		return avg;
	}

	// 최소 사이즈를 구하는 메소드
	public int getMinSize(int idx, String time) {
		ArrayList<Integer> tempList = new ArrayList<>();
		int min = 0;

		for (int i = idx; i < file1List.size(); i++) {
			String startTime = file1List.get(i).getStartTime();
			if (startTime.contains(time)) {
				String contentLen = file1List.get(i).getContentLen();
				int intContentLen = Integer.parseInt(contentLen);
				tempList.add(intContentLen);
			} else {
				break;
			}
		}

		// tempList에 저장되어 있는 값들의 최소값을 구한다.
		min = tempList.get(0);
		for (int i = 1; i < tempList.size(); i++) {
			if (tempList.get(i) < min) {
				min = tempList.get(i);
			}
		}
		return min;
	}

	// 최대 사이즈를 구하는 메소드
	public int getMaxSize(int idx, String time) {
		ArrayList<Integer> tempList = new ArrayList<>();
		int max = 0;

		for (int i = idx; i < file1List.size(); i++) {
			String startTime = file1List.get(i).getStartTime();
			if (startTime.contains(time)) {
				String contentLen = file1List.get(i).getContentLen();
				int intContentLen = Integer.parseInt(contentLen);
				tempList.add(intContentLen);
			} else {
				break;
			}
		}

		// tempList에 저장되어 있는 값들의 최대값을 구한다.
		max = tempList.get(0);
		for (int i = 1; i < tempList.size(); i++) {
			if (tempList.get(i) > max) {
				max = tempList.get(i);
			}
		}
		return max;
	}

	// 시간과 메모리 사용량 체크를 위한 메소드
	public void checkTimeAndMemory()
			throws XPathExpressionException, IOException, SAXException, ParserConfigurationException, ParseException {
		System.gc();
		// 시작 메모리
		long preUseMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

		long start = System.currentTimeMillis(); // 시작 시간
		execute(); // 실제 실행 메소드
		long end = System.currentTimeMillis(); // 종료 시간
		System.out.println("실행 시간 : " + (end - start) + "ms");

		System.gc();
		// 종료 후 메모리
		long aftUseMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		long useMemory = (preUseMemory - aftUseMemory);
		System.out.println("메모리 사용량: " + useMemory);
	}

}
