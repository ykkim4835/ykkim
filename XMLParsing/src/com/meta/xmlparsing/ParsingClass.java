package com.meta.xmlparsing;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class ParsingClass {

	ArrayList<BaseDTO> baseList = null;
	ArrayList<FDTO> fList = null;
	ArrayList<PDTO> pList = null;

	public ParsingClass() {
		this.baseList = new ArrayList<>();
		this.fList = new ArrayList<>();
		this.pList = new ArrayList<>();
	}

	public void execute() throws XPathExpressionException, IOException, SAXException, ParserConfigurationException {
		long start = 0;
		long end = 0;

		start = System.currentTimeMillis();
		saveBaseXML();

		for (int i = 0; i < baseList.size(); i++) {
			String file_id = baseList.get(i).getFile_id();
			saveFXML(file_id);
			savePXML(file_id);
			overFifty();
			equalP_id();
			license_id();
			makeXML(file_id);
			init();
		}
		end = System.currentTimeMillis();
		System.out.println("실행 시간 : " + (end - start) + "ms");
	}

	public NodeList setNodeList(BufferedReader in)
			throws IOException, SAXException, ParserConfigurationException, XPathExpressionException {
		String str = null;
		StringBuilder sb = new StringBuilder();

		while ((str = in.readLine()) != null) {
			str = str.trim();
			sb.append(str);
		}
		String xml = sb.toString();
		in.close();

		// XML Document 객체 생성
		InputSource is = new InputSource(new StringReader(xml));
		Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(is);
		// Document document =
		// DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(path);

		// xpath 생성
		XPath xpath = XPathFactory.newInstance().newXPath();

		// NodeList 가져오기 : row 아래에 있는 모든 col 을 선택
		NodeList rowList = (NodeList) xpath.evaluate("//ROWS/ROW", document, XPathConstants.NODESET);

		return rowList;
	}

	public void saveBaseXML() throws IOException, SAXException, ParserConfigurationException, XPathExpressionException {
		BufferedReader in = new BufferedReader(new FileReader("./xmlFile/base/T_BASEFILE_TB.xml"));
		// String path = "./xmlFile/base/T_BASEFILE_TB.xml";
		NodeList rowList = setNodeList(in);

		BaseDTO baseDTO = null;
		ArrayList<String> tempList = null;
		NodeList childList = null;

		for (int i = 0; i < rowList.getLength(); i++) {
			tempList = new ArrayList<>();
			childList = rowList.item(i).getChildNodes();
			if (childList.getLength() > 0) {
				for (int j = 0; j < childList.getLength(); j++) {
					if (!(childList.item(j).getNodeName().equals("#text"))) {
						tempList.add(childList.item(j).getTextContent());
					}
				}
			}
			baseDTO = new BaseDTO();
			baseDTO.setFile_id(tempList.get(0));
			baseDTO.setFile_name(tempList.get(1));
			baseDTO.setFile_ext(tempList.get(2));
			baseDTO.setFile_path(tempList.get(3));
			baseDTO.setReal_path(tempList.get(4));
			baseDTO.setCopy_path(tempList.get(5));
			baseDTO.setStatus(tempList.get(6));
			baseDTO.setStart_time(tempList.get(7));
			baseDTO.setEnd_time(tempList.get(8));
			baseDTO.setTarget_file_count(tempList.get(9));
			baseDTO.setExclusion_count(tempList.get(10));

			baseList.add(baseDTO);
		}
	}

	public void saveFXML(String idx)
			throws IOException, XPathExpressionException, SAXException, ParserConfigurationException {
		BufferedReader in = new BufferedReader(new FileReader("./xmlFile/f/F_" + idx + "_TB.xml"));
		// String path = "./xmlFile/f/F_" + idx + "_TB.xml";
		NodeList rowList = setNodeList(in);

		FDTO fDTO = null;
		ArrayList<String> tempList = null;
		NodeList childList = null;

		for (int i = 0; i < rowList.getLength(); i++) {
			tempList = new ArrayList<>();
			childList = rowList.item(i).getChildNodes();
			if (childList.getLength() > 0) {
				for (int j = 0; j < childList.getLength(); j++) {
					if (childList.item(j).getNodeName().equals("#text") == false) {
						tempList.add(childList.item(j).getTextContent());
					}
				}
			}
			fDTO = new FDTO();
			fDTO.setRowId(tempList.get(0));
			fDTO.setVolume(tempList.get(1));
			fDTO.setFile_name(tempList.get(2));
			fDTO.setRelease_name(tempList.get(3));
			fDTO.setSimilar_rate(tempList.get(4));
			fDTO.setFile_path(tempList.get(5));
			fDTO.setP_id(tempList.get(6));
			fDTO.setExclusion(tempList.get(7));
			fDTO.setComment(tempList.get(8));

			fList.add(fDTO);
		}
	}

	public void savePXML(String idx)
			throws IOException, XPathExpressionException, SAXException, ParserConfigurationException {
		BufferedReader in = new BufferedReader(new FileReader("./xmlFile/p/P_" + idx + "_TB.xml"));
		// String path = "./xmlFile/p/P_" + idx + "_TB.xml";
		NodeList rowList = setNodeList(in);

		PDTO pDTO = null;
		ArrayList<String> tempList = null;
		NodeList childList = null;

		for (int i = 0; i < rowList.getLength(); i++) {
			tempList = new ArrayList<>();
			childList = rowList.item(i).getChildNodes();
			if (childList.getLength() > 0) {
				for (int j = 0; j < childList.getLength(); j++) {
					if (childList.item(j).getNodeName().equals("#text") == false) {
						tempList.add(childList.item(j).getTextContent());
					}
				}
			}
			pDTO = new PDTO();
			pDTO.setP_id(tempList.get(0));
			pDTO.setProject_name(tempList.get(1));
			pDTO.setLicense_id(tempList.get(2));
			pDTO.setLicense_name(tempList.get(3));
			pDTO.setLicense_display(tempList.get(4));
			pDTO.setSize(tempList.get(5));
			pDTO.setTopic_cd(tempList.get(6));
			pDTO.setTopic_name(tempList.get(7));

			pList.add(pDTO);
		}
	}

	public void overFifty() {
		for (int i = 0; i < fList.size(); i++) {
			int intSR = Integer.parseInt(fList.get(i).getSimilar_rate());
			int result = intSR / 100;
			// 조건에 만족하면 overFity변수에 true를 저장
			// 조건: similar_rate / 100 > 15
			if (result > 15) {
				fList.get(i).setOverFifty(true);
			}
		}
	}

	public void equalP_id() {
		for (int i = 0; i < fList.size(); i++) {
			// 조건에 만족하면(overFity변수가 true면) 실행
			// 실제 조건: similar_rate / 100 > 15
			if (fList.get(i).isOverFifty()) {
				String fP_id = fList.get(i).getP_id();
				for (int j = 0; j < pList.size(); j++) {
					String pP_id = pList.get(j).getP_id();
					// fFile의 p_id와 pFile의 p_id값을 비교하여 같은것이 있으면 equalP_id에 true를
					// 저장
					if (fP_id.equals(pP_id)) {
						fList.get(i).setEqualP_id(true);
					}
				}
			}
		}
	}

	public void license_id() {
		for (int i = 0; i < fList.size(); i++) {
			// overFifty와 equalP_id 변수가 둘다 true일때 실행
			// 실제 조건: similar_rate / 100 > 15 이고 fFile의 p_id 와 pFile의 p_id가 같은것이
			// 있으면 실행
			if (fList.get(i).isOverFifty() && fList.get(i).isEqualP_id()) {
				for (int j = 0; j < pList.size(); j++) {
					String lincense_id = pList.get(j).getLicense_id();
					// fFile과 pFile의 p_id 비교 후 같으면 comment에 lincense_id를 넣어준다.
					if (fList.get(i).getP_id().equals(pList.get(j).getP_id())) {
						fList.get(i).setComment(lincense_id);
					}
				}
			}
		}
	}

	public void makeXML(String fileName) {
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			Document doc = docBuilder.newDocument();
			doc.setXmlStandalone(true);

			Element table = doc.createElement("TABLE");
			doc.appendChild(table);

			Element metadata = doc.createElement("METADATA");
			table.appendChild(metadata);

			Element name = doc.createElement("NAME");
			name.appendChild(doc.createTextNode("T_" + fileName + "_TB"));
			metadata.appendChild(name);

			Element col = doc.createElement("COL");
			col.appendChild(doc.createTextNode("ROWID"));
			metadata.appendChild(col);

			col = doc.createElement("COL");
			col.appendChild(doc.createTextNode("VOLUME"));
			metadata.appendChild(col);

			col = doc.createElement("COL");
			col.appendChild(doc.createTextNode("FILE_NAME"));
			metadata.appendChild(col);

			col = doc.createElement("COL");
			col.appendChild(doc.createTextNode("RELEASE_NAME"));
			metadata.appendChild(col);

			col = doc.createElement("COL");
			col.appendChild(doc.createTextNode("SIMILAR_RATE"));
			metadata.appendChild(col);

			col = doc.createElement("COL");
			col.appendChild(doc.createTextNode("FILE_PATH"));
			metadata.appendChild(col);

			col = doc.createElement("COL");
			col.appendChild(doc.createTextNode("P_ID"));
			metadata.appendChild(col);

			col = doc.createElement("COL");
			col.appendChild(doc.createTextNode("EXCLUSION"));
			metadata.appendChild(col);

			col = doc.createElement("COL");
			col.appendChild(doc.createTextNode("COMMENT"));
			metadata.appendChild(col);

			Element rows = doc.createElement("ROWS");
			table.appendChild(rows);

			Element row = null;
			Element rowId = null;
			Element volume = null;
			Element file_name = null;
			Element release_name = null;
			Element similar_rate = null;
			Element file_path = null;
			Element p_id = null;
			Element exclusion = null;
			Element comment = null;

			// element에 저장
			for (int i = 0; i < fList.size(); i++) {
				row = doc.createElement("ROW");
				rows.appendChild(row);

				rowId = doc.createElement("ROWID");
				rowId.appendChild(doc.createTextNode(fList.get(i).getRowId()));
				row.appendChild(rowId);

				volume = doc.createElement("VOLUME");
				volume.appendChild(doc.createTextNode(fList.get(i).getVolume()));
				row.appendChild(volume);

				file_name = doc.createElement("FILE_NAME");
				file_name.appendChild(doc.createTextNode(fList.get(i).getFile_name()));
				row.appendChild(file_name);

				release_name = doc.createElement("RELEASE_NAME");
				release_name.appendChild(doc.createTextNode(fList.get(i).getRelease_name()));
				row.appendChild(release_name);

				similar_rate = doc.createElement("SIMILAR_RATE");
				similar_rate.appendChild(doc.createTextNode(fList.get(i).getSimilar_rate()));
				row.appendChild(similar_rate);

				file_path = doc.createElement("FILE_PATH");
				file_path.appendChild(doc.createTextNode(fList.get(i).getFile_path()));
				row.appendChild(file_path);

				p_id = doc.createElement("P_ID");
				p_id.appendChild(doc.createTextNode(fList.get(i).getP_id()));
				row.appendChild(p_id);

				exclusion = doc.createElement("EXCLUSION");
				exclusion.appendChild(doc.createTextNode(fList.get(i).getExclusion()));
				row.appendChild(exclusion);

				comment = doc.createElement("COMMENT");
				comment.appendChild(doc.createTextNode(fList.get(i).getComment()));
				row.appendChild(comment);
			}

			// XML 파일로 쓰기
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();

			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(
					new BufferedWriter(new FileWriter("./xmlFile/t/T_" + fileName + "_TB.xml")));
			transformer.transform(source, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ArrayList 재사용을 위한 초기화
	public void init() {
		fList.clear();
		pList.clear();
	}

	public void memoryCheck() throws XPathExpressionException, IOException, SAXException, ParserConfigurationException {
		System.gc();
		long preUseMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		execute();
		System.gc();
		long aftUseMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		long useMemory = (preUseMemory - aftUseMemory);
		System.out.println("메모리 사용량: " + useMemory);
	}

}
