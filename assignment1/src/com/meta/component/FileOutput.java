package com.meta.component;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FileOutput implements WordOutput {
	
	@Override
	public void execute(int strLength) throws IOException{
		BufferedWriter out = new BufferedWriter(new FileWriter("output.txt"));
		out.write(String.valueOf(strLength));
		
		out.close();
	}

}
