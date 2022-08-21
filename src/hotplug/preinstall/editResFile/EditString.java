package hotplug.preinstall.editResFile;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.zhkj.code.bean.ResFile;
import com.zhkj.code.bean.XmlFile;
import com.zhkj.code.mode.EditResStringMode;
import com.zhkj.code.utils.EncryptionUtil;
import com.zhkj.code.utils.FileUtils;
/**
 * �������� ids.xml/ �µ��ļ�����ȫ���޸ĳ��µ�
 * @author Administrator
 *
 */
public class EditString implements EditResStringMode{
	String _assetsPath;
	String fileString;
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
		if(!"strings".equals(xmlFile.getXmlName()))return strBf;
		xmlFile.setFileEditName("strings");
		//��������ǩ
		Pattern pattern = Pattern.compile("<string\\s+name=\"app_name.*?/string>");
		Matcher matcher = pattern.matcher(strBf);
		StringBuffer strBfNew = new StringBuffer();
		//<item name="iv_main_tab_icon" type="id">false</item>
		if(matcher.find()) {
			strBfNew.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
			strBfNew.append("<resources>\n");
			strBfNew.append(matcher.group()+"\n");
			strBfNew.append("</resources>\n");
		}
		//���������ļ�
		StringBuffer strBfNewF = new StringBuffer();
		pattern = Pattern.compile(".*");
		matcher = pattern.matcher(strBf);
		while (matcher.find()) {
			String len = matcher.group();
			if(!"".equals(len)){
				strBfNewF.append(EncryptionUtil.encode(len)).append("\n");
			}
		}
		//��׿ studio ��Դ·����һ��
		FileUtils.saveFile(new File(_assetsPath+ fileString+".xml"), strBfNewF, "UTF-8");
		return strBfNew;
	}
	
}
