package com.zhkj.code.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XmlUtils{
	static DocumentBuilderFactory factory = null;
	static DocumentBuilder builder = null;
	static{
		factory = DocumentBuilderFactory.newInstance();
		try {
			builder = XmlUtils.factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 将XML文件输出到指定的路径
	 * 
	 * @param doc
	 * @param fileName
	 * @throws Exception
	 */
	public static void outputXml(Document doc, String fileName)
			throws Exception {
		doc.setXmlStandalone(true);
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer = tf.newTransformer();
		transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
//		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, isXmlHand?"no":"yes");
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2"); 
		transformer.setOutputProperty(OutputKeys.METHOD, "xml");
		FileOutputStream fos = new FileOutputStream(fileName);
		OutputStreamWriter isr = new OutputStreamWriter(fos, "utf-8");
		PrintWriter pw = new PrintWriter(isr);
		StreamResult result = new StreamResult(pw);
		transformer.transform(new DOMSource(doc), result);
//		System.out.println("生成XML文件成功!");
		//输出
//		ByteArrayOutputStream  bos  =  new  ByteArrayOutputStream();
//		OutputStreamWriter isr = new OutputStreamWriter(bos, "utf-8");
//		transformer.transform(new DOMSource(doc), new StreamResult(isr));
//		String xmlStr = bos.toString();
//		System.out.println(xmlStr);
//		isr.close();
//		bos.close();
		pw.close();
		fos.close();
	}

	/**
	 * 生成Document
	 *
	 * @return
	 */
	public static Document generateDocument() {
		Document doc = null;
		doc = builder.newDocument();
		return doc;
	}
	
	/**
	 * 读取XML
	 * @param filename
	 * @return
	 */
	public static Document readXml(String filename){
		DocumentBuilder builder = null;
		Document doc = null;
		try {
			builder = factory.newDocumentBuilder();
			FileInputStream fis = new FileInputStream(filename);
			InputStreamReader isr = new InputStreamReader(fis,"utf-8");
			BufferedReader br= new BufferedReader(isr);
			InputSource is = new InputSource(br);
			doc = builder.parse(is);
			br.close();
			isr.close();
			fis.close();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return doc;
	}

	/**
	 * Xml节点
	 * @param doc
	 * @param ip
	 * @throws FileNotFoundException
	 * @throws TransformerException
	 */
	public static void remove(Document doc, String ip)
			throws FileNotFoundException, TransformerException {
		NodeList links = doc.getElementsByTagName("errorDevice");
		if (links.getLength() > 0) {
			for (int i = 0; i < links.getLength(); i++) {
				Node nd = links.item(i);
				Node catParent = nd.getParentNode();
				Element ele = (Element) nd;
				String url = ele.getAttribute("ip");
				if (url.equals(ip)) {
					catParent.removeChild(nd);
				}
			}
		}
	}
	/**
	 * 生成XML
	 * 
	 * @param doc
	 * @return
	 */
	public static ElementUtil generateXmlRootRelativeLayout(Document doc) {
		ElementUtil root = ElementUtil.init(doc, "RelativeLayout")
				.setAttribute("xmlns:android", "http://schemas.android.com/apk/res/android")
				.setAttribute("xmlns:tools", "http://schemas.android.com/tools")
				.setAttribute("android:layout_width", "match_parent")
				.setAttribute("android:layout_height", "match_parent");
		return root;
	}
	/**
	 * 插入 Layout 的代码
	 * @param doc
	 * @return
	 */
	public static ElementUtil generateXmlInsertRootFrameLayout(Document doc) {
		ElementUtil root = ElementUtil.init(doc, "RelativeLayout")
				.setAttribute("android:layout_width", "match_parent")
				.setAttribute("android:layout_height", "match_parent")
				.setAttribute("android:visibility", "gone");
		return root;
	}
	/**
	 * 创建 TextView
	 * @param document
	 * @return
	 */
	public static ElementUtil generateXmlTextView(Document document) {
		ElementUtil elementUtil = ElementUtil.init(document, "TextView");
		elementUtil.setAttribute("android:layout_width", "wrap_content");
		elementUtil.setAttribute("android:layout_height", "wrap_content");
		elementUtil.setAttribute("layout_marginTop", RandomStringUtil.random.nextInt(100) + "dp");
		int top = RandomStringUtil.random.nextInt(100);
		if(top == 0)elementUtil.setAttribute("android:layout_marginLeft", top + "dp");
		//可有可无
		String[] stringS = new String[]{
				"?android:attr/textAppearanceLarge",
				"?android:attr/textAppearanceSmall",null
		};
		String str = RandomStringUtil.randomSting(stringS);
		if(null != str)elementUtil.setAttribute("android:textAppearance", str);
		return elementUtil;
	}

	/**
	 * 创建 Button
	 * @param document
	 * @return
	 */
	public static ElementUtil generateXmlButton(Document document) {
		ElementUtil elementUtil = ElementUtil.init(document, "Button");
		//可有可无
		if(RandomStringUtil.randomSpecie())
			elementUtil.setAttribute("style", "?android:attr/buttonStyleSmall");
		elementUtil.setAttribute("android:layout_width", "wrap_content");
		elementUtil.setAttribute("android:layout_height", "wrap_content");
		elementUtil.setAttribute("layout_marginTop", RandomStringUtil.random.nextInt(100) + "dp");
		return elementUtil;
	}
	/**
	 * 创建 TextView
	 * @param document
	 * @return
	 */
	public static ElementUtil generateXmlCommon(Document document) {
		//可有可无
		String[] stringS = new String[]{
				"AnalogClock","CheckBox","CheckedTextView","DigitalClock","ToggleButton"
		};
		String rootName = RandomStringUtil.randomSting(stringS);
		ElementUtil elementUtil = ElementUtil.init(document, rootName);
		elementUtil.setAttribute("android:layout_width", "wrap_content");
		elementUtil.setAttribute("android:layout_height", "wrap_content");
		elementUtil.setAttribute("android:layout_marginTop", RandomStringUtil.random.nextInt(100) + "dp");
		int top = RandomStringUtil.random.nextInt(100);
		if(top == 0)elementUtil.setAttribute("android:layout_marginLeft", top + "dp");
		return elementUtil;
	} 
	/**
	 * RadioGroup
	 * @param document
	 * @return
	 */
	public static ElementUtil generateXmlRadioGroup(Document document) {
		ElementUtil elementUtil = ElementUtil.init(document, "RadioGroup");
		elementUtil.setAttribute("android:layout_width", "wrap_content");
		elementUtil.setAttribute("android:layout_height", "wrap_content");
		elementUtil.setAttribute("android:layout_marginTop", RandomStringUtil.random.nextInt(100) + "dp");
		int top = RandomStringUtil.random.nextInt(3);
		//第一次
		ElementUtil radioButton = ElementUtil.init(document, "RadioButton");
		radioButton.setAttribute("android:layout_width", "wrap_content");
		radioButton.setAttribute("android:layout_height", "wrap_content");
		radioButton.setAttribute("android:checked", "true");
		elementUtil.appendChildRandom(radioButton);
		//余下未选中
		for (int i = -1; i < top; i++) {radioButton = ElementUtil.init(document, "RadioButton");
		radioButton.setAttribute("android:layout_width", "wrap_content");
		radioButton.setAttribute("android:layout_height", "wrap_content");
			elementUtil.appendChildRandom(radioButton);
		}
		return elementUtil;
	}  
	/**
	 * ScrollView
	 * @param document
	 * @return
	 */
	public static ElementUtil generateXmlScrollView(Document document) {
		ElementUtil elementUtil = ElementUtil.init(document, "ScrollView");
		elementUtil.setAttribute("android:layout_width", "match_parent");
		elementUtil.setAttribute("android:layout_height", "wrap_content");
		//第一次
		ElementUtil radioButton = ElementUtil.init(document, "LinearLayout");
		radioButton.setAttribute("android:layout_width", "match_parent");
		radioButton.setAttribute("android:layout_height", "wrap_content");
		radioButton.setAttribute("android:orientation", "vertical");
		elementUtil.appendChildRandom(radioButton);
		return elementUtil;
	}	
	public static ElementUtil generateXmlHorizontalScrollView(Document document) {
		ElementUtil elementUtil = ElementUtil.init(document, "HorizontalScrollView");
		elementUtil.setAttribute("android:layout_width", "match_parent");
		elementUtil.setAttribute("android:layout_height", "wrap_content");
		//子节点
		ElementUtil radioButton = ElementUtil.init(document, "LinearLayout");
		radioButton.setAttribute("android:layout_width", "match_parent");
		radioButton.setAttribute("android:layout_height", "wrap_content");
		radioButton.setAttribute("android:orientation", "horizontal");
		elementUtil.appendChildRandom(radioButton);
		return elementUtil;
	}	
	public static ElementUtil generateXmlLinearLayout(Document document) {
		ElementUtil elementUtil = ElementUtil.init(document, "LinearLayout");
		elementUtil.setAttribute("android:layout_width", "wrap_content");
		elementUtil.setAttribute("android:layout_height", "wrap_content");
		elementUtil.setAttribute("android:layout_marginTop", RandomStringUtil.random.nextInt(100) + "dp");
		if(RandomStringUtil.randomSpecie())
			elementUtil.setAttribute("android:orientation", "vertical");
		return elementUtil;
	}	
	public static ElementUtil generateXmlGridView(Document document) {
		ElementUtil elementUtil = ElementUtil.init(document, "GridView")
				.setAttribute("android:layout_width", "match_parent")
				.setAttribute("android:layout_height", "wrap_content")
				.setAttribute("android:numColumns", "3");
		return elementUtil;
	}
	
	/**
	 * 创建ProgressBar
	 * @param document
	 * @return
	 */
	public static ElementUtil generateXmlProgressBar(Document document){
		//初始化 设置标签
		ElementUtil elementUtil=ElementUtil.init(document, "ProgressBar");
		//设置公共的属性
		elementUtil.setAttribute("android:layout_width", "wrap_content");
		elementUtil.setAttribute("android:layout_height", "wrap_content");
		elementUtil.setAttribute("android:layout_marginTop", RandomStringUtil.random.nextInt(100) + "dp");
		//设置不共有的  style="?android:attr/progressBarStyleLarge"   android:layout_marginLeft="96dp"
		int top= RandomStringUtil.random.nextInt(100);
		if(top>49){
			elementUtil.setAttribute("style", "?android:attr/progressBarStyleLarge");
			elementUtil.setAttribute("android:layout_marginLeft", top+"dp");
		}		
		return elementUtil;
	}
	
	/**
	 * RatingBar
        ImageSwitcher
    	ImageView
        RadioButton
        Spinner
	 * @param document
	 */
	public static ElementUtil generateXmlTwoCommon(Document document){
		String[] stringS = new String[]{
			"RatingBar","ImageSwitcher","ImageView","RadioButton","Spinner"
		};
		String rootName= RandomStringUtil.randomSting(stringS);
		//初始化
		ElementUtil elementUtil=ElementUtil.init(document,rootName);
		//设置属性
		elementUtil.setAttribute("android:layout_width", "wrap_content");
		elementUtil.setAttribute("android:layout_height", "wrap_content");
		elementUtil.setAttribute("android:layout_marginTop", RandomStringUtil.random.nextInt(100) + "dp");
		return elementUtil;
	}
	
	/**
	 * 创建 SeekBar
	 * @param document
	 * @return
	 */
	public static ElementUtil generateXmlSeekBar(Document document){
		//初始化
		ElementUtil elementUtil=ElementUtil.init(document,"SeekBar");
		//设置属性
		elementUtil.setAttribute("android:layout_width", "match_parent");
		elementUtil.setAttribute("android:layout_height", "wrap_content");
		elementUtil.setAttribute("android:layout_marginTop", RandomStringUtil.random.nextInt(100) + "dp");
		return elementUtil;
	}
	
	/**
	FrameLayout
    RelativeLayout
    QuickContactBadge
	 * @param document
	 */
	public static ElementUtil generateXmlThreeCommon(Document document){
		String[] stringS = new String[]{
			"FrameLayout","RelativeLayout","QuickContactBadge"
		};
		String rootName= RandomStringUtil.randomSting(stringS);
		//初始化
		ElementUtil elementUtil=ElementUtil.init(document,rootName);
		//设置属性
		elementUtil.setAttribute("android:layout_width", "wrap_content");
		elementUtil.setAttribute("android:layout_height", "wrap_content");
		elementUtil.setAttribute("android:layout_marginTop", RandomStringUtil.random.nextInt(100) + "dp");
		elementUtil.setAttribute("android:layout_marginLeft", RandomStringUtil.random.nextInt(100) + "dp");
		return elementUtil;
	}
	
	/**
	 	ImageButton
       SurfaceView
       TimePicker
       ViewFlipper
       ZoomControls
	 * @param document
	 */
	public static ElementUtil generateXmlFourCommon(Document document){
		String[] stringS = new String[]{
				"ExpandableListView","ListView","Chronometer","ImageButton",
				"SurfaceView","TimePicker","ViewFlipper","ZoomControls"
			};
			String rootName= RandomStringUtil.randomSting(stringS);
		//初始化
		ElementUtil elementUtil=ElementUtil.init(document,rootName);	
		//设置属性
		String[] stringSs = new String[]{"match_parent","wrap_content"};
		elementUtil.setAttribute("android:layout_width", 
				RandomStringUtil.randomSting(stringSs));
		elementUtil.setAttribute("android:layout_height", 
				RandomStringUtil.randomSting(stringSs));
		return elementUtil;
	}
	
	/**
		VideoView
        WebView
	 * @param document
	 */
	public static ElementUtil generateXmlSixCommon(Document document){
		String[] stringS = new String[]{
				"VideoView","WebView"
		};
		String rootName= RandomStringUtil.randomSting(stringS);
		//初始化
		ElementUtil elementUtil=ElementUtil.init(document,rootName);	
		//设置属性
		elementUtil.setAttribute("android:layout_width", "match_parent");
		elementUtil.setAttribute("android:layout_height", "match_parent");		
		return elementUtil;
	}
	/**
	 * 生成 无用 layout 文件
	 * @param path
	 */
	public static void generateXml(String path) {
		Document doc = XmlUtils.generateDocument();
		ElementUtil root = generateXmlRootRelativeLayout(doc);
		while (RandomStringUtil.random.nextInt(10)>0) {
			switch(RandomStringUtil.random.nextInt(27)){
				case 0:
					root.appendChild(generateXmlTextView(doc));
					break;
				case 1:
					root.appendChild(generateXmlButton(doc));
					break;
				case 2:
					root.appendChild(generateXmlRadioGroup(doc));
					break;
				case 3:
					root.appendChild(generateXmlScrollView(doc));
					break;
				case 4:
					root.appendChild(generateXmlHorizontalScrollView(doc));
					break;
				case 5:
					root.appendChild(generateXmlLinearLayout(doc));
					break;
				case 6:
					root.appendChild(generateXmlGridView(doc));
					break;
				case 7:
					root.appendChild(generateXmlProgressBar(doc));
					break;
				case 8:
					root.appendChild(generateXmlSeekBar(doc));
					break;
				case 9:;case 10:;case 11:;case 12:
					root.appendChild(generateXmlCommon(doc));
					break;
				case 13:;case 14:
					root.appendChild(generateXmlSixCommon(doc));
					break;
				case 15:;case 16:
					root.appendChild(generateXmlThreeCommon(doc));
					break;
				case 17:;case 18:;case 19:;case 20:
					root.appendChild(generateXmlTwoCommon(doc));
					break;
				case 21:;case 22:;case 24:;case 25:;case 26:
					root.appendChild(generateXmlFourCommon(doc));
					break;
			}
		}
		XmlUtils.add(doc, root.getElement());
		try {
			XmlUtils.outputXml(doc,path);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 往 xml 插入 无用 layout
	 * @param path
	 */
	public static void insertXml(String path) {
		Document doc = XmlUtils.readXml(path);
		ElementUtil root = ElementUtil.init(doc.getDocumentElement());
		ElementUtil inserRoot = generateXmlInsertRootFrameLayout(doc);
		if(root.getNewElementName().equals("android.support.v4.widget.SwipeRefreshLayout")){
			root = root.getNewElementUtil("LinearLayout", null);
		}
		int num = RandomStringUtil.random.nextInt(3);
		while (num++ < 3) {
			switch(RandomStringUtil.random.nextInt(27)){
				case 0:
					inserRoot.appendChild(generateXmlTextView(doc));
					break;
				case 1:
					inserRoot.appendChild(generateXmlButton(doc));
					break;
				case 2:
					inserRoot.appendChild(generateXmlRadioGroup(doc));
					break;
				case 3:
					inserRoot.appendChild(generateXmlScrollView(doc));
					break;
				case 4:
					inserRoot.appendChild(generateXmlHorizontalScrollView(doc));
					break;
				case 5:
					inserRoot.appendChild(generateXmlLinearLayout(doc));
					break;
				case 6:
					inserRoot.appendChild(generateXmlGridView(doc));
					break;
				case 7:
					inserRoot.appendChild(generateXmlProgressBar(doc));
					break;
				case 8:
					inserRoot.appendChild(generateXmlSeekBar(doc));
					break;
				case 9:;case 10:;case 11:;case 12:
					inserRoot.appendChild(generateXmlCommon(doc));
					break;
				case 13:;case 14:
					inserRoot.appendChild(generateXmlSixCommon(doc));
					break;
				case 15:;case 16:
					inserRoot.appendChild(generateXmlThreeCommon(doc));
					break;
				case 17:;case 18:;case 19:;case 20:
					inserRoot.appendChild(generateXmlTwoCommon(doc));
					break;
				case 21:;case 22:;case 24:;case 25:;case 26:
					inserRoot.appendChild(generateXmlFourCommon(doc));
					break;
			}
		}
		root.appendChildRandom(inserRoot);
		try {
			XmlUtils.outputXml(doc,path);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private static void add(Document doc, Element root) {
		doc.appendChild(root);
	}
		
	/**
	 * Drawable xml
	 * selector根节点
	 */
	public static ElementUtil generateDrawableXmlSelector(Document document){
		//item子节点
		ElementUtil itemChild=ElementUtil.init(document,"item").setAttribute("android:drawable", "@android:color/transparent");
		//selector根节点
		ElementUtil elementUtil = ElementUtil.init(document, "selector")
				.setAttribute("xmlns:android", "http://schemas.android.com/apk/res/android")
					.setAttribute("android:exitFadeDuration", "@android:integer/config_shortAnimTime")
					.appendChild(itemChild);	
		return elementUtil;
	}
	
	/**
	 * Drawable xml
	 * shape根节点
	 * @param document
	 * @return
	 */
	public static ElementUtil generateDrawableXmlShape(Document document){
		//根节点
		ElementUtil elementUtil = ElementUtil.init(document, "shape");
		elementUtil.setAttribute("xmlns:android", "http://schemas.android.com/apk/res/android");
		int judgeNum= RandomStringUtil.random.nextInt(15);
		//System.out.println(judgeNum);
		switch(judgeNum){
			case 11 :;case 12:;case 13:			
				elementUtil.setAttribute("android:shape", "rectangle");
				/*
				 * padding子节点
				 */
				int paddingSize = RandomStringUtil.random.nextInt(50);
				ElementUtil paddingChild=ElementUtil.init(document, "padding");
				paddingChild.setAttribute("android:bottom",paddingSize+"dp");
				paddingChild.setAttribute("android:left", paddingSize+"dp");
				paddingChild.setAttribute("android:right", paddingSize+"dp");
				paddingChild.setAttribute("android:top", paddingSize+"dp");
				//将子节点加到根节点后面
				elementUtil.appendChild(paddingChild);			
				/*
				 * corners 子节点
				 */
				int cornersNum= RandomStringUtil.random.nextInt(100);
				if(cornersNum>49){
					int cornersSize = RandomStringUtil.random.nextInt(200);
					ElementUtil cornersChild=ElementUtil.init(document, "corners");
					cornersChild.setAttribute("android:radius",cornersSize+"dp");
					int num6= RandomStringUtil.random.nextInt(100);
					if(num6>49){
						cornersChild.setAttribute("android:bottomLeftRadius",cornersSize+"dp");
						cornersChild.setAttribute("android:bottomRightRadius",cornersSize+"dp");
						cornersChild.setAttribute("android:topLeftRadius",cornersSize+"dp");
						cornersChild.setAttribute("android:topRightRadius",cornersSize+"dp");
					}
					elementUtil.appendChild(cornersChild);
				}				
				/*
				 * stroke子节点
				 */
				int strokeNum= RandomStringUtil.random.nextInt(100);
				if(strokeNum>49){
					int strokeSize = RandomStringUtil.random.nextInt(20);
					ElementUtil strokeChild=ElementUtil.init(document, "stroke");
					strokeChild.setAttribute("android:width", strokeSize+"dp");
					strokeChild.setAttribute("android:color", "@android:color/holo_blue_dark");
					int num4= RandomStringUtil.random.nextInt(100);
					if(num4>49){
						strokeChild.setAttribute("android:dashGap", strokeSize+"dp");
						strokeChild.setAttribute("android:dashWidth", strokeSize+"dp");
					}	
					elementUtil.appendChild(strokeChild);
				}			
				//solid节点
				int solidNum= RandomStringUtil.random.nextInt(100);
				if(solidNum>49){
					ElementUtil solidChild=ElementUtil.init(document, "solid");
					solidChild.setAttribute("android:color", "#2F90BD");
					elementUtil.appendChild(solidChild);
				}
				//gradient 节点
				int gradientNum= RandomStringUtil.random.nextInt(100);
				if(gradientNum>49){
					ElementUtil gradientChild=ElementUtil.init(document, "gradient");
					gradientChild.setAttribute("android:angle", gradientNum+"");
					gradientChild.setAttribute("android:endColor", "#CC2F90BD");
					gradientChild.setAttribute("android:startColor", "#222F90BD");
					elementUtil.appendChild(gradientChild);
				}
				break;
			case 1:;case 2:;case 14:
				elementUtil.setAttribute("android:shape", "line");
				/*
				 * stroke子节点
				 */
				int strokeSize2= RandomStringUtil.random.nextInt(20);
				ElementUtil strokeChild2=ElementUtil.init(document, "stroke");
				strokeChild2.setAttribute("android:width", strokeSize2+"dp");
				strokeChild2.setAttribute("android:color", "@android:color/holo_blue_dark");
				int num= RandomStringUtil.random.nextInt(100);
				if(num>49){
					strokeChild2.setAttribute("android:dashGap", strokeSize2+"dp");
					strokeChild2.setAttribute("android:dashWidth", strokeSize2+"dp");
				}				
				elementUtil.appendChild(strokeChild2);
				/*
				 * size子节点
				 */
				int sizeSize= RandomStringUtil.random.nextInt(30);
				ElementUtil sizeChild=ElementUtil.init(document, "size");
				sizeChild.setAttribute("android:height", sizeSize+"dp");
				elementUtil.appendChild(sizeChild);
				break;
			case 3:;case 0:;case 4:;case 15:
				elementUtil.setAttribute("android:shape", "oval");
				/*
				 * padding子节点
				 */
				int paddingSize2 = RandomStringUtil.random.nextInt(50);
				ElementUtil paddingChild2=ElementUtil.init(document, "padding");
				paddingChild2.setAttribute("android:bottom",paddingSize2+"dp");
				paddingChild2.setAttribute("android:left", paddingSize2+"dp");
				paddingChild2.setAttribute("android:right", paddingSize2+"dp");
				paddingChild2.setAttribute("android:top", paddingSize2+"dp");
				elementUtil.appendChild(paddingChild2);
				/*
				 * 随机
				 */
				// size 子节点
				int num2= RandomStringUtil.random.nextInt(100);
				if(num2>49){
					int sizeSize2= RandomStringUtil.random.nextInt(50);
					ElementUtil sizeChild2=ElementUtil.init(document, "size");
					sizeChild2.setAttribute("android:width", sizeSize2+"dp");
					sizeChild2.setAttribute("android:height", sizeSize2+"dp");
					elementUtil.appendChild(sizeChild2);
				}
				//solid节点				
				int num3= RandomStringUtil.random.nextInt(100);
				if(num3>49){
					ElementUtil solidChild=ElementUtil.init(document, "solid");
					solidChild.setAttribute("android:color", "#E4007F");
					elementUtil.appendChild(solidChild);
				}
				//stroke节点
				int num5= RandomStringUtil.random.nextInt(100);
				if(num5>49){
					ElementUtil strokeChild3=ElementUtil.init(document, "stroke");
					strokeChild3.setAttribute("android:width", paddingSize2+"dp");
					strokeChild3.setAttribute("android:color", "@android:color/darker_gray");
					elementUtil.appendChild(strokeChild3);
				}
				break;
			case 5:;case 6:
				int num6= RandomStringUtil.random.nextInt(10);
				elementUtil.setAttribute("android:innerRadiusRatio",num6+"");
				elementUtil.setAttribute("android:shape", "ring");
				elementUtil.setAttribute("android:thicknessRatio", num6+"");
				elementUtil.setAttribute("android:useLevel", "false");
				/*
				 * gradient节点
				 */
				ElementUtil gradientChild=ElementUtil.init(document, "gradient");
				gradientChild.setAttribute("android:endColor", "#2F90BD");
				gradientChild.setAttribute("android:startColor", "#FFFFFF");
				gradientChild.setAttribute("android:type", "sweep");
				elementUtil.appendChild(gradientChild);
				/*
				 * stroke 节点
				 */
				ElementUtil strokeChild=ElementUtil.init(document, "stroke");
				strokeChild.setAttribute("android:width", num6+"dp");
				strokeChild.setAttribute("android:color", "@android:color/black");
				elementUtil.appendChild(strokeChild);
				break;
			case 7:;case 8:
				//solid
				ElementUtil solidChild=ElementUtil.init(document, "solid");
				solidChild.setAttribute("android:color", "#E4007F");
				elementUtil.appendChild(solidChild);
				//padding
				int paddingSize3 = RandomStringUtil.random.nextInt(50);
				ElementUtil paddingChild3=ElementUtil.init(document, "padding");
				paddingChild3.setAttribute("android:bottom",paddingSize3+"dp");
				paddingChild3.setAttribute("android:left", paddingSize3+"dp");
				paddingChild3.setAttribute("android:right", paddingSize3+"dp");
				paddingChild3.setAttribute("android:top", paddingSize3+"dp");
				elementUtil.appendChild(paddingChild3);
				//corners
				int cornersSize = RandomStringUtil.random.nextInt(50);
				ElementUtil cornersChild=ElementUtil.init(document, "corners");
				cornersChild.setAttribute("android:radius",cornersSize+"dp");
				elementUtil.appendChild(cornersChild);
				break;
			case 9:;case 10:
				elementUtil.setAttribute("android:shape","oval");
				elementUtil.setAttribute("android:useLevel", "true");
				// solid
				ElementUtil solidChild2=ElementUtil.init(document, "solid");
				solidChild2.setAttribute("android:color", "#2F90BD");
				elementUtil.appendChild(solidChild2);
				//size
				int sizeSize2= RandomStringUtil.random.nextInt(20);
				ElementUtil sizeChild2=ElementUtil.init(document, "size");
				sizeChild2.setAttribute("android:width", sizeSize2+"dp");
				sizeChild2.setAttribute("android:height", sizeSize2+"dp");
				elementUtil.appendChild(sizeChild2);
				break;
		}					
		return elementUtil;
	}
	
	/**
	 * Drawable xml
	 * rotate 根节点
	 */
	public static ElementUtil generateDrawableXmlRotate(Document document){
		ElementUtil elementUtil = ElementUtil.init(document, "rotate");
		elementUtil.setAttribute("xmlns:android", "http://schemas.android.com/apk/res/android");
		elementUtil.setAttribute("android:fromDegrees", "0");
		elementUtil.setAttribute("android:pivotX", "50%");
		elementUtil.setAttribute("android:pivotY", "50%");
		elementUtil.setAttribute("android:toDegrees", "1080.0");
		//添加shape子节点
		int num= RandomStringUtil.random.nextInt(10);
		ElementUtil shapeChild = ElementUtil.init(document, "shape");
		shapeChild.setAttribute("android:innerRadiusRatio",num+"");
		shapeChild.setAttribute("android:shape", "ring");
		shapeChild.setAttribute("android:thicknessRatio", num+"");
		shapeChild.setAttribute("android:useLevel", "false");
		//shape下的gradient子节点
		ElementUtil gradientChild = ElementUtil.init(document, "gradient");
		gradientChild.setAttribute("android:endColor", "#2F90BD");
		gradientChild.setAttribute("android:startColor", "#FFFFFF");
		gradientChild.setAttribute("android:type", "sweep");
		shapeChild.appendChild(gradientChild);
		elementUtil.appendChild(shapeChild);
			
		return elementUtil;
	}
	
	/**
	 * 生成xml文件 到Drawable目录
	 * @param path
	 */
	public static void generateDrawableXml(String path) {
		Document doc = XmlUtils.generateDocument();
		ElementUtil elementUtil=null;
		switch(RandomStringUtil.random.nextInt(10)){
			case 10 :;case 0 :;case 1:
				elementUtil=generateDrawableXmlSelector(doc);
				break;
			case 2:;case 3:;case 4:;case 5:;case 6:;case 7:;case 8:
				elementUtil=generateDrawableXmlShape(doc);
				break;
			case 9:
				elementUtil=generateDrawableXmlRotate(doc);
				break;
		}
		XmlUtils.add(doc, elementUtil.getElement());
		try {
			XmlUtils.outputXml(doc,path);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	// 
	public static void main(String[] args) {
//		generateXml("my.xml");
		//insertXml("my.xml");
		System.out.println(ElementUtil.init(readXml("my.xml").getDocumentElement()).getNewElementName());
		//XmlUtils.generateDrawableXml("C:\\Users\\wanglei\\Desktop\\111.xml");
	}
}