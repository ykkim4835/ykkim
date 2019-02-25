package com.meta.logparsing;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

// file1을 출력하기 위한 클래스(로그분석)
public class LogParser1 {

	ArrayList<File1DTO> file1List;
	File1DTO file1DTO;
	
	
	public LogParser1() {
		this.file1List = new ArrayList<>();
		this.file1DTO = null;
	}
	
	// 최종 실행 메소드
	public void execute() throws IOException {
		BufferedReader in = new BufferedReader(new FileReader("galileo.log"));
		BufferedWriter out = new BufferedWriter(new FileWriter("logParser1.log"));
		String str = "";
		
		// 로그를 한줄씩 읽어온다.
		while ((str = in.readLine()) != null) {
			str = str.trim();
			
			// 해당 문자열이 포함되어 있으면 각각의 메소드를 실행한다.
			if (str.contains("##galileo_bean start")) {
				getStartTime(str);
			} else if (str.contains("ESB_TRAN_ID : ")) {
				getEsbId(str);
			} else if (str.contains("Content-Length:")) {
				getContentLen(str);
			} else if (str.contains("#galileo call time:")) {
				getCallTime(str);
			} else if (str.contains("StopWatch")) {
				getRunningTime(str, in);
			} else if (str.contains("##galileo_bean end")) {
				getEndTime(str);
			}
		}
		
		// file1DTO의 데이터에 한개라도 널값이 저장되어있으면 file1List에서 제거한다.
		removeNull();
		
		// 파일로 출력한다.
		for (int i = 0; i < file1List.size(); i++) {
			String startTime = file1List.get(i).getStartTime();
			String endTime = file1List.get(i).getEndTime();
			String esbId = file1List.get(i).getEsbId();
			String contentLen = file1List.get(i).getContentLen();
			String callTime = file1List.get(i).getCallTime();
			String beforeMarshalling = file1List.get(i).getBeforeMarshalling();
			String marshalling = file1List.get(i).getMarshalling();
			String invoking = file1List.get(i).getInvoking();
			String unmarshalling = file1List.get(i).getUnmarshalling();

			out.write(startTime + ", " + endTime + ", " + esbId + ", " + contentLen + ", " + callTime + ", "
					+ beforeMarshalling + ", " + marshalling + ", " + invoking + ", " + unmarshalling + "\r\n");
		}

		in.close();
		out.close();
	}
	
	// 시작 시간을 가져오는 메소드
	private void getStartTime(String str) {
		String threadId = getThreadId(str);  
		String startTime = getTime(str); 

		file1DTO = new File1DTO();	// 현재 메소드 실행 시 무조건 file1DTO를 생성한다.
		file1DTO.setThreadId(threadId);
		file1DTO.setStartTime(startTime);
		file1List.add(file1DTO);
	}
	
	// esb 아이디를 가져오는 메소드
	private void getEsbId(String str) {
		String threadId = getThreadId(str); 
		String esbId = getData(str, "ESB_TRAN_ID : ");
		
		// 뒤에서 부터 읽어오기 때문에 항상 최근 생성된 객체를 참조한다.
		for (int i = file1List.size(); i > 0; i--) { 
			String tempThreadId = file1List.get(i - 1).getThreadId();
			// 쓰레드 명이 같을 경우에만 데이터를 넣어준다.
			if (threadId.equals(tempThreadId)) {
				file1List.get(i - 1).setEsbId(esbId);
				break;
			}
		}
	}
	
	// 사이즈를 가져오는 메소드
	private void getContentLen(String str) {
		String threadId = getThreadId(str); 
		String contentLen = getData(str, "Content-Length:");

		for (int i = file1List.size(); i > 0; i--) {
			String tempThreadId = file1List.get(i - 1).getThreadId();
			if (threadId.equals(tempThreadId)) {
				file1List.get(i - 1).setContentLen(contentLen);
				break;
			}
		}
	}
	
	// 콜타임을 가져오는 메소드
	private void getCallTime(String str) {
		String threadId = getThreadId(str); 
		String callTime = getData(str, "#galileo call time:");
		callTime = callTime.replace(" ms", "");

		for (int i = file1List.size(); i > 0; i--) {
			String tempThreadId = file1List.get(i - 1).getThreadId();
			if (threadId.equals(tempThreadId)) {
				file1List.get(i - 1).setCallTime(callTime);
				break;
			}
		}
	}
	
	// 각 소요시간을 가져오는 메소드
	private void getRunningTime(String str, BufferedReader in) throws IOException {
		String threadId = getThreadId(str);
		
		// 세줄을 강제적을 읽어 들인다.
		in.readLine();
		in.readLine();
		in.readLine();
		
		String beforeMarshalling = in.readLine().substring(0, 5);
		String marshalling = in.readLine().substring(0, 5);
		String invoking = in.readLine().substring(0, 5);
		String unmarshalling = in.readLine().substring(0, 5);
		
		// 한개라도 숫자로 변환 할 수 없다면 실행 한다.
		if (!isNumber(beforeMarshalling) || !isNumber(marshalling) || !isNumber(invoking) || !isNumber(unmarshalling)) {
			beforeMarshalling = null;
			marshalling = null;
			invoking = null;
			unmarshalling = null;
		}

		for (int i = file1List.size(); i > 0; i--) {
			String tempThreadId = file1List.get(i - 1).getThreadId();
			if (threadId.equals(tempThreadId)) {
				file1List.get(i - 1).setBeforeMarshalling(beforeMarshalling);
				file1List.get(i - 1).setMarshalling(marshalling);
				file1List.get(i - 1).setInvoking(invoking);
				file1List.get(i - 1).setUnmarshalling(unmarshalling);
				break;
			}
		}
	}
	
	// 종료 시간을 가져오는 메소드
	private void getEndTime(String str) {
		String threadId = getThreadId(str);
		String endTime = getTime(str);

		for (int i = file1List.size(); i > 0; i--) {
			String tempThreadId = file1List.get(i - 1).getThreadId();
			if (threadId.equals(tempThreadId)) {
				file1List.get(i - 1).setEndTime(endTime);
				break;
			}
		}
	}
	
	// file1DTO의 데이터에 한개라도 널값이 저장되어있으면 file1List에서 제거하는 메소드
	private void removeNull() {
		Iterator<File1DTO> iter = file1List.iterator();
		while (iter.hasNext()) {
			File1DTO file1DTO = iter.next();
			String startTime = file1DTO.getStartTime();
			String endTime = file1DTO.getEndTime();
			String esbId = file1DTO.getEsbId();
			String contentLen = file1DTO.getContentLen();
			String callTime = file1DTO.getCallTime();
			String beforeMarshalling = file1DTO.getBeforeMarshalling();
			String marshalling = file1DTO.getMarshalling();
			String invoking = file1DTO.getInvoking();
			String unmarshalling = file1DTO.getUnmarshalling();
			
			// file1DTO의 필드 멤버가 하나라도 널값이 있으면 실행한다.
			if (startTime == null || endTime == null || esbId == null || contentLen == null || callTime == null
					|| beforeMarshalling == null || marshalling == null || invoking == null || unmarshalling == null) {
				iter.remove();
			}
		}
	}
	
	// 문자열이 숫자로 이루어져 있는지 확인하는 메소드
	private boolean isNumber(String str) {
		boolean result = false;

		try {
			Integer.parseInt(str);
			result = true;
		} catch (Exception e) {
		}

		return result;
	}
	
	// 로그에서 문자열을 잘라 쓰레드 명을 가져오는 메소드
	private String getThreadId(String str) {
		String threadId = str.substring(58, 66);
		
		return threadId;
	}
	
	// 로그에서 문자열을 잘라 시작시간을 가져오는 메소드
	private String getTime(String str) {
		String time = str.substring(1, 18);
		
		return time;
	}
	
	// 로그에서 문자열을 잘라 데이터를 추출하는 메소드
	private String getData(String str, String keyword) {
		int keywordIdx = str.indexOf(keyword);
		int keywordLen = keyword.length();
		String data = str.substring(keywordIdx + keywordLen);
		
		return data;
	}

}
