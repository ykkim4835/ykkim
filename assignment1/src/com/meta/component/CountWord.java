package com.meta.component;

import java.util.StringTokenizer;

public class CountWord {
	
	// ������ ����
/*	public int execute(String str) {
		str = removeSpace(str);		
		int strLength = str.length();
		
		return strLength;
	}
	
	public String removeSpace(String str) {
		str = str.trim();
		str = str.replaceAll(" ", "");
		str = str.replaceAll("\r\n|\n|\n\r", "");
		
		return str;
	}*/
	
	// �ܾ��� ����
	public int execute(String str) {
		str = str.trim();
		StringTokenizer st = new StringTokenizer(str);
		int strLength = st.countTokens();
		return strLength;
	}

}
 