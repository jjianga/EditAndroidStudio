package hotplug.preinstall.editResFile;

import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.zhkj.code.bean.ResFile;
import com.zhkj.code.bean.XmlFile;
import com.zhkj.code.bean.XmlTager;
import com.zhkj.code.mode.EditResStringMode;
/**
 * 所有引用 attrs.xml/ 下的文件名称全部修改成新的
 * @author Administrator
 *
 */
public class EditAttrs implements EditResStringMode{

	@Override
	public void init(HashMap<String, Object> map) {
	}

	@Override
	public StringBuffer editResString(StringBuffer strBf, ResFile resFile, 
			List<ResFile> resFileS) {
		XmlFile xmlFile = null;
		if(resFile.getType() == 100){
			xmlFile = (XmlFile)resFile;
		}
		if(!"attrs".equals(xmlFile.getXmlName()))return strBf;
		xmlFile.setXmlNameEdit("attrs");
		Pattern pattern = Pattern.compile("<declare-styleable name=\"([\\w_]+)");
		Matcher matcher = pattern.matcher(strBf);
		StringBuffer strBfNew = new StringBuffer();
		while (matcher.find()) {
			String str = matcher.group(1);
			List<XmlTager> xmlTagers = xmlFile.getXmlTagger();
			for (XmlTager xmlTager : xmlTagers) {
				if(str.equals(xmlTager.getNameParamValue())){
					matcher.appendReplacement(strBfNew,
							"<declare-styleable name=\"" + xmlTager.getNameParamValueEdit());
					break;
				}
			}
		}
		matcher.appendTail(strBfNew);

		StringBuffer strBfNewt = new StringBuffer();
		pattern = Pattern.compile("<attr name=\"([\\w_]+)");
		matcher = pattern.matcher(strBfNew);
		while (matcher.find()) {
			String str = matcher.group(1);
			List<XmlTager> xmlTagers = xmlFile.getXmlTagger();
			for (XmlTager xmlTager : xmlTagers) {
				if(str.equals(xmlTager.getNameParamValue())){
					matcher.appendReplacement(strBfNewt,
							"<attr name=\"" + xmlTager.getNameParamValueEdit());
					break;
				}
			}
		}
		matcher.appendTail(strBfNewt);
		return strBfNewt;
	}
}
