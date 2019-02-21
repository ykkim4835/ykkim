package com.meta.logparsing;

import java.io.IOException;
import java.text.ParseException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;

public class MainClass {

	public static void main(String[] args)
			throws IOException, ParseException, XPathExpressionException, SAXException, ParserConfigurationException {
		LogParser2 logParser2 = new LogParser2();
//		logParser2.execute(); // 실행
		logParser2.checkTimeAndMemory(); // 시간, 메모리 사용량 체크
	}

}
