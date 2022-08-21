package hotplug.preinstall.editJavaFile;

import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.zhkj.code.bean.ClazzObjectTrait;
import com.zhkj.code.bean.ResFile;
import com.zhkj.code.bean.XmlFile;
import com.zhkj.code.bean.XmlTager;
import com.zhkj.code.mode.EditJavaFileMode;
import com.zhkj.code.utils.ResUtli;
import com.zhkj.log.Log;
/**
 * �޸ĺ�xml ��ص�����
 * @author Administrator
 *
 */
public class EditJavaClazzRecRes implements EditJavaFileMode{
	private List<ResFile> ResFileS;
	@Override
	public void init(HashMap<String, Object> map) {
	}

	@Override
	public String editJavaCode(String javaCode, ClazzObjectTrait clazzObjectTraitThis,
			List<ClazzObjectTrait> clazzObjectTraitS) {
		if(ResFileS == null){Log.appendErr("û�ж�ȡ����ResFileS��������������Դ�ļ��ļ��ϣ�");return javaCode;}
		List<XmlFile> xmlFileS = ResUtli.getResFileIsType(ResFileS, 100, 200);

		StringBuffer javaCodeB = new StringBuffer();
		//��������
		Pattern pattern = Pattern.compile("(^|[^\\w\\.])R\\.\\s*([\\w_]+)\\s*\\.\\s*([\\w_]+)");
		Matcher matcher = pattern.matcher(javaCode);
		while (matcher.find()){
			String paramOne = matcher.group(2);
			String paramTwo = matcher.group(3);
			StringBuffer code = new StringBuffer(matcher.group(1)).append("R.").append(paramOne);
			switch (paramOne) {
			case "layout":
				code.append(".").append(getValeByXmlParentPathFileName(paramOne, xmlFileS, paramTwo));
				matcher.appendReplacement(javaCodeB, code.toString());
				//Log.appendTest("layout��" + code.toString());
				break;
			case "drawable":
				code.append(".").append(getValeByResParentPathFileName(paramOne, ResFileS, paramTwo));
				matcher.appendReplacement(javaCodeB, code.toString());
				//Log.appendTest("drawable��" + code.toString());
				break;
			case "xml":
				code.append(".").append(getValeByXmlParentPathFileName(paramOne, xmlFileS, paramTwo));
				matcher.appendReplacement(javaCodeB, code.toString());
				//Log.appendTest("xml��" + code.toString());
				break;
			case "id":
				code.append(".").append(getValeByXmlTagerNameParam("ids", xmlFileS, paramTwo));
				matcher.appendReplacement(javaCodeB, code.toString());
				//Log.appendTest("id��" + code.toString());
				break;
			case "anim":
				code.append(".").append(getValeByXmlParentPathFileName(paramOne, xmlFileS, paramTwo));
				matcher.appendReplacement(javaCodeB, code.toString());
				//Log.appendTest("anim��" + code.toString());
				break;
			case "array":
				code.append(".").append(getValeByXmlTagerNameParam(paramOne, xmlFileS, paramTwo));
				matcher.appendReplacement(javaCodeB, code.toString());
				//Log.appendTest("array��" + code.toString());
				break;
			case "color":
				String str = getValeByXmlParentPathFileName(paramOne, xmlFileS, paramTwo);
				if(str.equals(paramTwo)){
					str = getValeByXmlTagerNameParam("colors", xmlFileS, paramTwo);
				}
				matcher.appendReplacement(javaCodeB, code.append(".").append(str).toString());
				//Log.appendTest("color��" + code.toString());
				break;
			case "dimen":
				code.append(".").append(getValeByXmlTagerNameParam("dimens", xmlFileS, paramTwo));
				matcher.appendReplacement(javaCodeB, code.toString());
//				//Log.appendTest("dimen��" + code.toString());
				break;
//			case "style":
//				code.append(".").append(getValeByXmlTagerNameParam("styles", xmlFileS, paramTwo));
//				matcher.appendReplacement(javaCodeB, code.toString());
//				Log.append2("style��" + code.toString());
//				break;
			case "styleable":
				//��ƴ�ӱȽ�   �����ȷ��ʾ �Ϸ�
				paramTwo = getValeByXmlTagerNameJointTagerName(
					"attrs", xmlFileS, paramTwo,"declare-styleable","_","attr");
				//���ƴ�Ӳ��Ϸ�  �ٵ����Ƚ�
				code.append(".").append(getValeByXmlTagerNameParam("attrs", xmlFileS, paramTwo));
				matcher.appendReplacement(javaCodeB, code.toString());
				//Log.appendTest("styleable��" + code.toString());
				break;
			case "attr":
				code.append(".").append(getValeByXmlTagerNameParam("attrs", xmlFileS, paramTwo));
				matcher.appendReplacement(javaCodeB, code.toString());
				//Log.appendTest("attr��" + code.toString());
				break;
				
				default:
					//Log.appendTest("���˵ģ�"+matcher.group());
					break;
			}
		}matcher.appendTail(javaCodeB);
		
		return javaCodeB.toString();
	}
	

	public String getValeByResParentPathFileName(String parentPath,
			List<ResFile> resFileS,String fileName){
		for (ResFile resFile : resFileS) {
			if(resFile.getParentPath().contains(parentPath)
					&&fileName.equals(resFile.getFileName())){
				return resFile.getFileEditName();
			}
		}
		return fileName;
	}
	/**
	 * 
	 * @param fileName  �ļ�������
	 * @param xmlFileS	�ļ�����
	 * @param fileName	ԭ�����ļ���
	 * @return �µ��ļ���
	 */
	public String getValeByXmlParentPathFileName(String parentPath,
			List<XmlFile> xmlFileS,String fileName){
		for (XmlFile xmlFile : xmlFileS) {
			if(parentPath.equals(xmlFile.getParentPath())
					&&fileName.equals(xmlFile.getFileName())){
				return xmlFile.getFileEditName();
			}
		}
		return fileName;
	}
	
	public String getValeByXmlTagerNameParam(String fileName,
			List<XmlFile> xmlFileS,String nameParamValue){
		for (XmlFile xmlFile : xmlFileS) {
			if(fileName.equals(xmlFile.getFileName())){
				List<XmlTager> tagers = xmlFile.getXmlTagger();
				for (XmlTager xmlTager : tagers) {
					if(nameParamValue.equals(xmlTager.getNameParamValue())){
						return xmlTager.getNameParamValueEdit();
					}
				}
			}
		}
		return nameParamValue;
	}
	

	
	public String getValeByXmlTagerNameJointTagerName(String fileName,
			List<XmlFile> xmlFileS,String nameParamValue,
			String tagerName,String joint,String jointTagerName){
		String str = null;
		String strEdit = null;
		for (XmlFile xmlFile : xmlFileS) {
			if(fileName.equals(xmlFile.getFileName())){//�ҵ��ļ�
				List<XmlTager> tagers = xmlFile.getXmlTagger();
				for (XmlTager xmlTager : tagers) {
					if(tagerName.equals(xmlTager.getName())){
						str = xmlTager.getNameParamValue();			//��¼��һ��tager��ֵ
						strEdit = xmlTager.getNameParamValueEdit();
					}else if(jointTagerName.equals(xmlTager.getName())){
						if(nameParamValue.equals(					//ȷ���ǲ�������
								str+joint+xmlTager.getNameParamValue())){
							return strEdit + joint + xmlTager.getNameParamValueEdit();
						}
					}
				}
			}
		}
		return nameParamValue;
	}

}
