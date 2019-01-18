package com.meta.component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileInput implements StrInput {

	@Override
	public String execute() throws IOException {
		BufferedReader in = new BufferedReader(new FileReader("input.txt"));
		String str = null;
		String tempStr = "";
		
		while(true) {
			str = in.readLine();
			
			if(str == null) {
				break;
			}
			
			tempStr += str + "\n";
		}

		in.close();

		return tempStr;
	}

}
