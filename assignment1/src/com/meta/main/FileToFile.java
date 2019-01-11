package com.meta.main;

import com.meta.component.CountWord;
import com.meta.component.FileInput;
import com.meta.component.FileOutput;
import com.meta.component.StrInput;
import com.meta.component.StrOutput;

public class FileToFile {
	
	public static void main(String[] args) throws Exception {
		StrInput input = new FileInput();
		String str = input.execute();
		
		CountWord countWord = new CountWord();
		int strLength = countWord.execute(str);
		
		StrOutput output = new FileOutput();
		output.execute(strLength);
	}
	
}