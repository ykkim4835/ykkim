package com.meta.logparsing;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

// file1�� ����ϱ� ���� Ŭ����(�α׺м�)
public class LogParser1 {

	ArrayList<File1DTO> file1List;
	File1DTO file1DTO;
	
	
	public LogParser1() {
		this.file1List = new ArrayList<>();
		this.file1DTO = null;
	}
	
	// ���� ���� �޼ҵ�
	public void execute() throws IOException {
		BufferedReader in = new BufferedReader(new FileReader("galileo.log"));
		BufferedWriter out = new BufferedWriter(new FileWriter("logParser1.log"));
		String str = "";
		
		// �α׸� ���پ� �о�´�.
		while ((str = in.readLine()) != null) {
			str = str.trim();
			
			// �ش� ���ڿ��� ���ԵǾ� ������ ������ �޼ҵ带 �����Ѵ�.
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
		
		// file1DTO�� �����Ϳ� �Ѱ��� �ΰ��� ����Ǿ������� file1List���� �����Ѵ�.
		removeNull();
		
		// ���Ϸ� ����Ѵ�.
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
	
	// ���� �ð��� �������� �޼ҵ�
	private void getStartTime(String str) {
		String threadId = getThreadId(str);  
		String startTime = getTime(str); 

		file1DTO = new File1DTO();	// ���� �޼ҵ� ���� �� ������ file1DTO�� �����Ѵ�.
		file1DTO.setThreadId(threadId);
		file1DTO.setStartTime(startTime);
		file1List.add(file1DTO);
	}
	
	// esb ���̵� �������� �޼ҵ�
	private void getEsbId(String str) {
		String threadId = getThreadId(str); 
		String esbId = getData(str, "ESB_TRAN_ID : ");
		
		// �ڿ��� ���� �о���� ������ �׻� �ֱ� ������ ��ü�� �����Ѵ�.
		for (int i = file1List.size(); i > 0; i--) { 
			String tempThreadId = file1List.get(i - 1).getThreadId();
			// ������ ���� ���� ��쿡�� �����͸� �־��ش�.
			if (threadId.equals(tempThreadId)) {
				file1List.get(i - 1).setEsbId(esbId);
				break;
			}
		}
	}
	
	// ����� �������� �޼ҵ�
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
	
	// ��Ÿ���� �������� �޼ҵ�
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
	
	// �� �ҿ�ð��� �������� �޼ҵ�
	private void getRunningTime(String str, BufferedReader in) throws IOException {
		String threadId = getThreadId(str);
		
		// ������ �������� �о� ���δ�.
		in.readLine();
		in.readLine();
		in.readLine();
		
		String beforeMarshalling = in.readLine().substring(0, 5);
		String marshalling = in.readLine().substring(0, 5);
		String invoking = in.readLine().substring(0, 5);
		String unmarshalling = in.readLine().substring(0, 5);
		
		// �Ѱ��� ���ڷ� ��ȯ �� �� ���ٸ� ���� �Ѵ�.
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
	
	// ���� �ð��� �������� �޼ҵ�
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
	
	// file1DTO�� �����Ϳ� �Ѱ��� �ΰ��� ����Ǿ������� file1List���� �����ϴ� �޼ҵ�
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
			
			// file1DTO�� �ʵ� ����� �ϳ��� �ΰ��� ������ �����Ѵ�.
			if (startTime == null || endTime == null || esbId == null || contentLen == null || callTime == null
					|| beforeMarshalling == null || marshalling == null || invoking == null || unmarshalling == null) {
				iter.remove();
			}
		}
	}
	
	// ���ڿ��� ���ڷ� �̷���� �ִ��� Ȯ���ϴ� �޼ҵ�
	private boolean isNumber(String str) {
		boolean result = false;

		try {
			Integer.parseInt(str);
			result = true;
		} catch (Exception e) {
		}

		return result;
	}
	
	// �α׿��� ���ڿ��� �߶� ������ ���� �������� �޼ҵ�
	private String getThreadId(String str) {
		String threadId = str.substring(58, 66);
		
		return threadId;
	}
	
	// �α׿��� ���ڿ��� �߶� ���۽ð��� �������� �޼ҵ�
	private String getTime(String str) {
		String time = str.substring(1, 18);
		
		return time;
	}
	
	// �α׿��� ���ڿ��� �߶� �����͸� �����ϴ� �޼ҵ�
	private String getData(String str, String keyword) {
		int keywordIdx = str.indexOf(keyword);
		int keywordLen = keyword.length();
		String data = str.substring(keywordIdx + keywordLen);
		
		return data;
	}

}
