package hotplug.preinstall.editResFile;

import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.zhkj.code.bean.ClazzObjectTrait;
import com.zhkj.code.bean.ResFile;
import com.zhkj.code.bean.XmlFile;
import com.zhkj.code.bean.XmlTager;
import com.zhkj.code.mode.EditResStringMode;
import com.zhkj.code.utils.ResUtli;
/**
 * 所有引用 layout 目录下的 layout目录下的xml 名称改成新的
 * @author Administrator
 *
 */
public class EditLayout implements EditResStringMode{
	List<ClazzObjectTrait> ClazzObjctTraitS;
	@Override
	public void init(HashMap<String, Object> map) {
		
	}
	@Override
	public StringBuffer editResString(StringBuffer strBf, ResFile resFile, 
			List<ResFile> resFileS) {
		XmlFile xmlFile = null;
		List<XmlFile> xmlFileS = ResUtli.getResFileIsType(resFileS, 100, 200);
		if(resFile.getType() == 100){
			xmlFile = (XmlFile)resFile;
		}
		if(!"layout".equals(xmlFile.getParentPath()))return strBf;
		//layout  引用  layout
		StringBuffer strBfNew = new StringBuffer();
		Pattern pattern = Pattern.compile("@layout/([\\w_]+)");
		Matcher matcher = pattern.matcher(strBf);
		while (matcher.find()) {
			String str = matcher.group(1);
			for (XmlFile xmlFilef : xmlFileS) {
				if("layout".equals(xmlFilef.getParentPath())
						&&str.equals(xmlFilef.getXmlName())){
					matcher.appendReplacement(strBfNew, "@layout/" + xmlFilef.getXmlNameEdit());
					break;
				}
			}
		}
		matcher.appendTail(strBfNew);
		
		//layout 引用 id
		StringBuffer strBfNewt = new StringBuffer();
		pattern = Pattern.compile("@\\+?id/([\\w_]+)");
		matcher = pattern.matcher(strBfNew);
		while (matcher.find()) {
			String str = matcher.group(1);
			for (XmlFile xmlFilef : xmlFileS) {
				if("ids".equals(xmlFilef.getXmlName())){
				List<XmlTager> xmlTagerS = xmlFilef.getXmlTagger();
					for (XmlTager xmlTager : xmlTagerS) {
						if("item".equals(xmlTager.getName())
							&&str.equals(xmlTager.getNameParamValue())){
							matcher.appendReplacement(strBfNewt, 
								"@id/" + xmlTager.getNameParamValueEdit());
							break;
						}
					}
				}
			}
			
		}
		matcher.appendTail(strBfNewt);


		//layout 引用 id
		strBfNew.delete(0, strBfNew.length());
		pattern = Pattern.compile("app:([\\w_]+)");
		matcher = pattern.matcher(strBfNewt);
		while (matcher.find()) {
			String str = matcher.group(1);
			for (XmlFile xmlFilef : xmlFileS) {
				if("attrs".equals(xmlFilef.getXmlName())){
					List<XmlTager> xmlTagerS = xmlFilef.getXmlTagger();
					for (XmlTager xmlTager : xmlTagerS) {
						if("attr".equals(xmlTager.getName())
								&&str.equals(xmlTager.getNameParamValue())){
							matcher.appendReplacement(strBfNew, 
									"app:" + xmlTager.getNameParamValueEdit());
							break;
						}
					}
				}
			}
		}
		matcher.appendTail(strBfNew);
		
		pattern = Pattern.compile("</?\\s*([\\w\\._]+)\\b");
		matcher = pattern.matcher(strBfNew);
		strBfNewt.delete(0, strBfNewt.length());
		while (matcher.find()) {
			for (ClazzObjectTrait clazzObjctTrait : ClazzObjctTraitS) {
				String str = clazzObjctTrait.getPackageName()+"."+clazzObjctTrait.getClazzName();
				String grp = matcher.group(1);
				if(str.equals(grp)){
					str = matcher.group().replace(grp,
					clazzObjctTrait.getPackageEditName()+"."+clazzObjctTrait.getClazzEditName());
					matcher.appendReplacement(strBfNewt, str);
				}
			}
		}
		matcher.appendTail(strBfNewt);
		
		return strBfNewt;
	}
}
