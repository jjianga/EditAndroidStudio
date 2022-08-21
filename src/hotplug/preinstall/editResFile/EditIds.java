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
 * 所有引用 ids.xml/ 下的文件名称(Id)全部修改成新的
 * @author Administrator
 *
 */
public class EditIds implements EditResStringMode{

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
		if(!"ids".equals(xmlFile.getXmlName()))return strBf;
		xmlFile.setFileEditName("ids");
		Pattern pattern = Pattern.compile("<item\\s+name=\"([\\w_]+)");
		Matcher matcher = pattern.matcher(strBf);
		StringBuffer strBfNew = new StringBuffer();
		//<item name="iv_main_tab_icon" type="id">false</item>
		while (matcher.find()) {
			String str = matcher.group(1);
			List<XmlTager> xmlTagers = xmlFile.getXmlTagger();
			for (XmlTager xmlTager : xmlTagers) {
				if(str.equals(xmlTager.getNameParamValue())){
					matcher.appendReplacement(strBfNew,
							"<item name=\"" + xmlTager.getNameParamValueEdit());
					break;
				}
			}
		}
		matcher.appendTail(strBfNew);
		return strBfNew;
	}
	
}
