package com.zhkj.code.utils;

import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
/**
 * ��װ element ����Ԫ�ز���
 * @author Administrator
 *
 */
public class ElementUtil {
	private ElementUtil elementUtil;
	private Element element = null;
	private ElementUtil(){}
	public Element getElement() {
		return element;
	}
	/**
	 * ��ʼ������
	 * @param element
	 * @return
	 */
	public static ElementUtil init(Element element) {
		ElementUtil elementUtil = new ElementUtil();
		elementUtil.elementUtil = elementUtil;
		elementUtil.element = element;
		return elementUtil;
	}
	/**
	 * ��ʼ������
	 * @param document
	 * @param elementName
	 * @return
	 */
	public static ElementUtil init(Document document,String elementName){
		ElementUtil elementUtil = new ElementUtil();
		elementUtil.elementUtil = elementUtil;
		elementUtil.element = document.createElement(elementName);
		return elementUtil;
	}
	/**
	 * �������
	 * @param name
	 * @param value
	 * @return
	 */
	public ElementUtil setAttribute(String name,String value){
		element.setAttribute(name, value);
		return elementUtil;
	}
	/**
	 * ����ӽڵ�
	 * @param newChild
	 * @return
	 */
	public ElementUtil appendChild(Element newChild){
		element.appendChild(newChild);
		return elementUtil;
	}
	public ElementUtil appendChild(ElementUtil newChild){
		element.appendChild(newChild.getElement());
		return elementUtil;
	}
	/**
	 * �ڽڵ�ǰ����ӽڵ�
	 * @param newChild
	 * @return
	 */
	public ElementUtil appendChildPrecedingBrother(Node newChild){
		Node node = element.getParentNode();
		node.insertBefore(newChild, element);
		return elementUtil;
	}
	/**
	 * �ڽڵ��������ӽڵ�
	 * @param newChild
	 * @return
	 */
	public ElementUtil appendChildBehindBrother(Node newChild){
		Node node = element.getParentNode();
		node.insertBefore(newChild, element.getNextSibling());
		return elementUtil;
	}

	/**
	 * �������ֵܽڵ�
	 * @param newChild
	 * @return
	 */
	public ElementUtil appendChildRandomBrother(Node newChild){
		Node node = element.getParentNode();
		NodeList  nodeList = node.getChildNodes();
		int index = RandomStringUtil.
				random.nextInt(nodeList.getLength()+1);
		node.insertBefore(newChild, nodeList.item(index));
		return elementUtil;
	}

	/**
	 * �����ӽڵ�
	 * @param newChild
	 * @return
	 */
	public ElementUtil appendChildRandom(Node newChild){
		NodeList  nodeList = element.getChildNodes();
		int index = RandomStringUtil.
				random.nextInt(nodeList.getLength()+1);
		element.insertBefore(newChild, nodeList.item(index));
		return elementUtil;
	}
	public ElementUtil appendChildRandom(ElementUtil newChild){
		elementUtil.appendChildRandom(newChild.getElement());
		return elementUtil;
	}
	/**
	 * �� doc ��ȡ Element
	 * @param doc
	 * @param elementName
	 * @param attributes
	 * @return
	 */
	public static Element getNewElement(Document doc,String elementName,
			Map<String, String> attributes){
		Element ele = null;
		NodeList links = doc.getElementsByTagName(elementName);
		for (int i = 0; i < links.getLength(); i++) {
			if(attributes == null){
				return (Element)links.item(i);
			}
			int max = attributes.size();
			for (String key : attributes.keySet()) {
				Node nd = links.item(i);
				ele = (Element) nd;
				String value = ele.getAttribute(key);
				if (value.equals(attributes.get(key))&&--max == 0) {
					return ele;
				}
			}
		}
		return ele;
	}
	/**
	 * ��ȡһ��Ԫ�ر�ǩ��ͨ�������Լ����Լ�ֵ��
	 * @param elementName
	 * @param attributes
	 * @return
	 */
	public ElementUtil getNewElementUtil(String elementName,
			Map<String, String> attributes){
		Element ele = null;
		NodeList links = element.getElementsByTagName(elementName);
		for (int i = 0; i < links.getLength(); i++) {
			if(attributes == null){
				ElementUtil elementUtil = ElementUtil.init((Element)links.item(i));
				return elementUtil;
			}
			int max = attributes.size();
			for (String key : attributes.keySet()) {
				Node nd = links.item(i);
				ele = (Element) nd;
				String value = ele.getAttribute(key);
				if (value.equals(attributes.get(key))&&--max == 0) {
					ElementUtil elementUtil = ElementUtil.init(ele);
					return elementUtil;
				}
			}
		}
		return null;
	}
	/**
	 * ��ȡԪ�ر�ǩ����
	 * @return
	 */
	public String getNewElementName(){
		return element.getNodeName();
	}
	
}
