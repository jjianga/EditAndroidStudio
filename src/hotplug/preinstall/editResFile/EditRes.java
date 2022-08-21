package hotplug.preinstall.editResFile;

import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.zhkj.code.bean.ResFile;
import com.zhkj.code.bean.XmlFile;
import com.zhkj.code.bean.XmlTager;
import com.zhkj.code.mode.EditResStringMode;
import com.zhkj.code.utils.ResUtli;
/**
 * 修改所有引用到资源的xml
 * @author Administrator
 *
 */
public class EditRes implements EditResStringMode{
	@Override
	public void init(HashMap<String, Object> map) {
	}
	@Override
	public StringBuffer editResString(StringBuffer strBf, ResFile resFile, 
			List<ResFile> resFileS) {
		for (ResFile resFile2 : resFileS) {
			if(resFile2.getParentPath().equals("values")){	
				resFile2.setFileEditName(resFile2.getFileName());
			}
			if(resFile2.getParentPath().equals("xml")){
				resFile2.setFileEditName(resFile2.getFileName());
			}
		}
		List<XmlFile> xmlFileS = ResUtli.getResFileIsType(resFileS, 100, 200);
		
		// 所有引用  anim/ 目录下的文件都修改成新的引用
		StringBuffer strBfNew = new StringBuffer();
		Pattern pattern = Pattern.compile("@anim/([\\w_]+)");
		Matcher matcher = pattern.matcher(strBf);
		while (matcher.find()) {
			String str = matcher.group(1);
			for (XmlFile xmlFilef : xmlFileS) {
				if(str.equals(xmlFilef.getXmlName())){
					matcher.appendReplacement(strBfNew, "@anim/" + xmlFilef.getXmlNameEdit());
					break;
				}
			}
		}
		matcher.appendTail(strBfNew);
		
		// 所有引用 drawable/ 下的文件名称全部修改成新的引用
		StringBuffer strBfNewt = new StringBuffer();
		pattern = Pattern.compile("@drawable/([\\w_]+)");
		matcher = pattern.matcher(strBfNew);
		while (matcher.find()) {
			String str = matcher.group(1);
			for (ResFile resFilef : resFileS) {
				if(str.equals(resFilef.getFileName())){
					matcher.appendReplacement(strBfNewt, "@drawable/" + resFilef.getFileEditName());
					break;
				}
			}
		}
		matcher.appendTail(strBfNewt);
		
		// 所有引用 color.xml  下的标签全部修改成新的引用
		strBfNew.delete(0, strBfNew.length());
		pattern = Pattern.compile("@color/([\\w_]+)");
		matcher = pattern.matcher(strBfNewt);
		while(matcher.find()){
			boolean isfile = false;
			String str = matcher.group(1);
			for (XmlFile xmlFilef : xmlFileS) {
				if("color".equals(xmlFilef.getParentPath())){
					if(xmlFilef.getFileName().equals(str)){
						matcher.appendReplacement(strBfNew, 
								"@color/" + xmlFilef.getFileEditName());
						isfile = true;
					}
				}
			}
			for (XmlFile xmlFilef : xmlFileS) {
				if(isfile)break;
				if("colors".equals(xmlFilef.getXmlName())){
					List<XmlTager> xmlTagerS = xmlFilef.getXmlTagger();
					for (XmlTager xmlTager : xmlTagerS) {
						if("color".equals(xmlTager.getName())
								&&str.equals(xmlTager.getNameParamValue())){
							matcher.appendReplacement(strBfNew, 
									"@color/" + xmlTager.getNameParamValueEdit());
							break;
						}
					}
				}
			}
		}
		matcher.appendTail(strBfNew);
		
		//所有引用 @dimen   
		strBfNewt.delete(0, strBfNewt.length());
		pattern = Pattern.compile("@dimen/([\\w_]+)");
		matcher = pattern.matcher(strBfNew);
		while(matcher.find()){
			String str = matcher.group(1);
			for (XmlFile xmlFilef : xmlFileS) {
				boolean f = false;// 多个dimen 存在时  找到即返回
				if("dimens".equals(xmlFilef.getXmlName())){
					List<XmlTager> xmlTagerS = xmlFilef.getXmlTagger();
					for (XmlTager xmlTager : xmlTagerS) {
						if("dimen".equals(xmlTager.getName())
								&&str.equals(xmlTager.getNameParamValue())){
							matcher.appendReplacement(strBfNewt, 
									"@dimen/" + xmlTager.getNameParamValueEdit());
							f = true;
							break;
						}
					}
					if(f)break;
				}
			}
		}
		matcher.appendTail(strBfNewt);
		return strBfNewt;
	}


}
