package com.meta.main;

import java.io.IOException;

import com.meta.component.CommonInput;
import com.meta.component.CommonOutput;
import com.meta.component.CountWord;

public class KeyboardToFile {
	
	public static void main(String[] args) throws IOException {
		CommonInput commonInput = new CommonInput();
		String str = commonInput.keyboard();
		
		CountWord countWord = new CountWord();
		int strLength = countWord.execute(str);
		
		CommonOutput commonOutput = new CommonOutput();
		commonOutput.file(strLength);
	}

}
