package com.meta.main;

import com.meta.component.CommonInput;
import com.meta.component.CommonOutput;
import com.meta.component.CountWord;

public class FileToFile {
	
	public static void main(String[] args) throws Exception {
		CommonInput commonInput = new CommonInput();
		String str = commonInput.file();
		
		CountWord countWord = new CountWord();
		int strLength = countWord.execute(str);
		
		CommonOutput commonOutput = new CommonOutput();
		commonOutput.file(strLength);
	}
	
}
