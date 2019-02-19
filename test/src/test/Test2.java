package test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Test2 {
	
	static ArrayList<File1DTO> file1List = null;
	
	public static void main(String[] args) throws IOException {
		LogParser logParser = new LogParser();
		logParser.execute();
		
		file1List = logParser.file1List;
				
		for(int i = 0; i < file1List.size(); i++) {
			String startTime = file1List.get(i).getStartTime().substring(0, 14);
			System.out.println(startTime);
		}
	}

}
