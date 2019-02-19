package test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class LogParser {

	ArrayList<File1DTO> file1List = null;
	File1DTO file1DTO = null;
	
	public LogParser() {
		file1List = new ArrayList<>();
	}

	public void getStart(String str) {
		String threadId = str.substring(58, 66);
		String startTime = str.substring(1, 18);

		file1DTO = new File1DTO();
		file1DTO.setThreadId(threadId);
		file1DTO.setStartTime(startTime);
		file1List.add(file1DTO);
	}

	public void getEsbId(String str) {
		String threadId = str.substring(58, 66);
		int keywordIdx = str.indexOf("ESB_TRAN_ID : ");
		int keywordLen = "ESB_TRAN_ID : ".length();
		String esbId = str.substring(keywordIdx + keywordLen);

		for (int i = 0; i < file1List.size(); i++) {
			String tempThreadId = file1List.get(i).getThreadId();
			if (threadId.equals(tempThreadId)) {
				if (file1List.get(i).getEsbId() == null) {
					file1List.get(i).setEsbId(esbId);
					break;
				}
			}
		}
	}

	public void getContentLen(String str) {
		String threadId = str.substring(58, 66);
		int keywordIdx = str.indexOf("Content-Length:");
		int keywordLen = "Content-Length:".length();
		String contentLen = str.substring(keywordIdx + keywordLen);

		for (int i = 0; i < file1List.size(); i++) {
			String tempThreadId = file1List.get(i).getThreadId();
			if (threadId.equals(tempThreadId)) {
				if (file1List.get(i).getContentLen() == null) {
					file1List.get(i).setContentLen(contentLen);
					break;
				}
			}
		}
	}

	public void getCallTime(String str) {
		String threadId = str.substring(58, 66);
		int keywordIdx = str.indexOf("#galileo call time:");
		int keywordLen = "#galileo call time:".length();
		String callTime = str.substring(keywordIdx + keywordLen);
		callTime = callTime.replace(" ms", "");

		for (int i = 0; i < file1List.size(); i++) {
			String tempThreadId = file1List.get(i).getThreadId();
			if (threadId.equals(tempThreadId)) {
				if (file1List.get(i).getCallTime() == null) {
					file1List.get(i).setCallTime(callTime);
					break;
				}
			}
		}
	}

	public void getRunningTime(String str, BufferedReader in) throws IOException {
		String threadId = str.substring(58, 66);
		int keywordIdx = str.indexOf("StopWatch");
		int keywordLen = "StopWatch".length();

		in.readLine();
		in.readLine();
		in.readLine();
		String beforeMarshalling = in.readLine().substring(0, 5);
		String marshalling = in.readLine().substring(0, 5);
		String invoking = in.readLine().substring(0, 5);
		String unmarshalling = in.readLine().substring(0, 5);

		if (!isNumber(beforeMarshalling) || !isNumber(marshalling) || !isNumber(invoking)
				|| !isNumber(unmarshalling)) {
			beforeMarshalling = null;
			marshalling = null;
			invoking = null;
			unmarshalling = null;
		}

		for (int i = 0; i < file1List.size(); i++) {
			String tempThreadId = file1List.get(i).getThreadId();
			if (threadId.equals(tempThreadId)) {
				if (file1List.get(i).getBeforeMarshalling() == null && file1List.get(i).getMarshalling() == null
						&& file1List.get(i).getInvoking() == null && file1List.get(i).getUnmarshalling() == null) {
					file1List.get(i).setBeforeMarshalling(beforeMarshalling);
					file1List.get(i).setMarshalling(marshalling);
					file1List.get(i).setInvoking(invoking);
					file1List.get(i).setUnmarshalling(unmarshalling);
					break;
				}
			}
		}
	}

	public void getEndTime(String str) {
		String threadId = str.substring(58, 66);
		String endTime = str.substring(1, 18);

		for (int i = 0; i < file1List.size(); i++) {
			String tempThreadId = file1List.get(i).getThreadId();
			if (threadId.equals(tempThreadId)) {
				if (file1List.get(i).getEndTime() == null) {
					file1List.get(i).setEndTime(endTime);
					break;
				}
			}
		}
	}

	public void removeNull() {
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

			if (startTime == null || endTime == null || esbId == null || contentLen == null || callTime == null
					|| beforeMarshalling == null || marshalling == null || invoking == null || unmarshalling == null) {
				iter.remove();
			}
		}
	}

	public static boolean isNumber(String str) {
		boolean result = false;

		try {
			Integer.parseInt(str);
			result = true;
		} catch (Exception e) {
		}

		return result;
	}

	public void execute() throws IOException {
		long start = System.currentTimeMillis();

		BufferedReader in = new BufferedReader(new FileReader("galileo.log"));
		BufferedWriter out = new BufferedWriter(new FileWriter("out.txt"));
		String str = "";

		while ((str = in.readLine()) != null) {
			str = str.trim();

			if (str.contains("##galileo_bean start")) {
				getStart(str);
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

		removeNull();
		System.out.println("count: " + file1List.size());

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

			/*
			 * System.out.println(startTime); System.out.println(endTime);
			 * System.out.println(esbId); System.out.println(contentLen);
			 * System.out.println(callTime);
			 * System.out.println(beforeMarshalling);
			 * System.out.println(marshalling); System.out.println(invoking);
			 * System.out.println(unmarshalling);
			 */

			out.write(startTime + ", " + endTime + ", " + esbId + ", " + contentLen + ", " + callTime + ", "
					+ beforeMarshalling + ", " + marshalling + ", " + invoking + ", " + unmarshalling + "\r\n");
		}

		in.close();
		out.close();

		long end = System.currentTimeMillis();
		System.out.println(end - start);
	}

}
