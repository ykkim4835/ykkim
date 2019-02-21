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

// file12�� ����ϱ� ���� Ŭ����(������ ����)
public class LogParser2 {

	ArrayList<File1DTO> file1List;
	ArrayList<File2DTO> file2List;
	File2DTO file2DTO;
	int idx; // �� �ð� �׷��� ù��° �������� �ε���

	// �м��� �α׷� ���� �����͸� �������� �޼ҵ�(�ʱ�ȭ)
	public void init() throws IOException {
		LogParser1 logParser1 = new LogParser1();
		logParser1.execute();

		file1List = logParser1.file1List;
		file2List = new ArrayList<>();
		file2DTO = null;
		idx = 0;
	}

	// ���� ���� �޼ҵ�
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
		
		// idx�� �ѹ��� �� �׷쳻�� ��� �� ��ŭ �����Ѵ�.
		// while���� �׷��� �� ��ŭ �ݺ��Ѵ�.
		while (!(idx == file1List.size())) {
			time = getTime();
			countProcess = getCountProcess(idx, time);
			avgTime = getAvgTime(idx, time);
			minTime = getMinTime(idx, time);
			maxTime = getMaxTime(idx, time);
			minSize = getMinSize(idx, time);
			maxSize = getMaxSize(idx, time);
			avgSize = getAvgSize(idx, time);
			setIdx(countProcess); // idx�� ���� �ð� �׷��� ù��° �����ͷ� �̵��Ѵ�.

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

		// ������ ����Ѵ�.
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

	// �� �ð� �׷��� ù��° �������� �ε��� ���� �����ִ� �޼ҵ�
	public void setIdx(int countProcess) {
		idx += countProcess;
	}

	// ���� �ð��� �ʸ� �� �κ��� �߶� �������� �޼ҵ�(��.��.�� ��:��)
	public String getTime() {
		String time = file1List.get(idx).getStartTime().substring(0, 14);

		return time;
	}

	// �������� �ð� �׷���� ��� ���� ī��Ʈ�ϴ� �޼ҵ�(ó���Ǽ�)
	public int getCountProcess(int idx, String time) {
		int countProcess = 0;
		for (int i = idx; i < file1List.size(); i++) { // i = idx (i�� idx�� ����)
			String startTime = file1List.get(i).getStartTime();
			if (startTime.contains(time)) {
				countProcess++;
			} else {
				break;
			}
		}

		return countProcess;
	}

	// ��� �ҿ�ð��� ���ϴ� �޼ҵ�
	public int getAvgTime(int idx, String time) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yy.MM.dd HH:mm:ss");
		ArrayList<Long> tempList = new ArrayList<>();
		Date start = null;
		Date end = null;
		int sum = 0;
		int avg = 0;

		// startTime(��.��.�� ��:��:��)�� �Ķ���ͷ� ���� time ����(��.��.�� ��:��)�� �����ϸ�
		// ���۽ð��� ����ð��� ���� ���Ѵ�.
		// ���� ���� tempList�� �ִ´�.
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

		// tempList�� ����Ǿ� �ִ� ������ ����� ���Ѵ�.
		for (int i = 0; i < tempList.size(); i++) {
			sum += tempList.get(i);
		}

		avg = sum / tempList.size();

		return avg;
	}

	// �ּ� �ð��� ���ϴ� �޼ҵ�
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

		// tempList�� ����Ǿ� �ִ� ������ �ּҰ��� ���Ѵ�.
		min = tempList.get(0);
		for (int i = 1; i < tempList.size(); i++) {
			if (tempList.get(i) < min) {
				min = tempList.get(i);
			}
		}

		return (int) min;
	}

	// �ִ� �ð��� ���ϴ� �޼ҵ�
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

		// tempList�� ����Ǿ� �ִ� ������ �ִ밪�� ���Ѵ�.
		max = tempList.get(0);
		for (int i = 1; i < tempList.size(); i++) {
			if (tempList.get(i) > max) {
				max = tempList.get(i);
			}
		}

		return (int) max;
	}

	// ��� ����� ���ϴ� �޼ҵ�
	public int getAvgSize(int idx, String time) {
		ArrayList<Integer> tempList = new ArrayList<>();
		int sum = 0;
		int avg = 0;

		// startTime(��.��.�� ��:��:��)�� �Ķ���ͷ� ���� time ����(��.��.�� ��:��)�� �����ϸ�
		// ����� tempList�� �ִ´�.
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

		// tempList�� ����Ǿ� �ִ� ������ ��հ��� ���Ѵ�.
		for (int i = 0; i < tempList.size(); i++) {
			sum += tempList.get(i);
		}

		avg = sum / tempList.size();

		return avg;
	}

	// �ּ� ����� ���ϴ� �޼ҵ�
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

		// tempList�� ����Ǿ� �ִ� ������ �ּҰ��� ���Ѵ�.
		min = tempList.get(0);
		for (int i = 1; i < tempList.size(); i++) {
			if (tempList.get(i) < min) {
				min = tempList.get(i);
			}
		}
		return min;
	}

	// �ִ� ����� ���ϴ� �޼ҵ�
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

		// tempList�� ����Ǿ� �ִ� ������ �ִ밪�� ���Ѵ�.
		max = tempList.get(0);
		for (int i = 1; i < tempList.size(); i++) {
			if (tempList.get(i) > max) {
				max = tempList.get(i);
			}
		}
		return max;
	}

	// �ð��� �޸� ��뷮 üũ�� ���� �޼ҵ�
	public void checkTimeAndMemory()
			throws XPathExpressionException, IOException, SAXException, ParserConfigurationException, ParseException {
		System.gc();
		// ���� �޸�
		long preUseMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

		long start = System.currentTimeMillis(); // ���� �ð�
		execute(); // ���� ���� �޼ҵ�
		long end = System.currentTimeMillis(); // ���� �ð�
		System.out.println("���� �ð� : " + (end - start) + "ms");

		System.gc();
		// ���� �� �޸�
		long aftUseMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		long useMemory = (preUseMemory - aftUseMemory);
		System.out.println("�޸� ��뷮: " + useMemory);
	}

}
