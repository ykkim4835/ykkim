package com.meta.xmlparsing;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;

// 실행 클래스
public class MainClass {

	public static void main(String[] args)
			throws XPathExpressionException, IOException, SAXException, ParserConfigurationException {
		XMLParser xmlParser = new XMLParser();
//		xmlParser.execute(); // 실행
		xmlParser.checkTimeAndMemory(); // 시간, 메모리 사용량 체크
	}

}
