package hotplug.preinstall.trimming;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.zhkj.code.bean.ClazzObjectTrait;
import com.zhkj.code.bean.ResFile;
import com.zhkj.code.mode.TrimmingMode;
import com.zhkj.code.utils.FileUtils;
import com.zhkj.code.utils.RandomStringUtil;
import com.zhkj.log.Log;

public class AndroidManifestFile  implements TrimmingMode{
	private String androidManifest_;	//androidManifest
	@Override
	public void init(HashMap<String, Object> map) {
	}
	@Override
	public void trimming(List<ClazzObjectTrait> clazzObjectTraitS, List<ResFile> resFileS, String encoding) {
		Log.appendInfo("修改AndroidManifestFile相关...");
		//替换 androidManifestFile 图标    activity 
		File androidManifestFile = new File(androidManifest_);
		StringBuffer androidManifestf =FileUtils.readString(androidManifestFile, encoding);
		Pattern pattern = Pattern.compile("android:icon=\"@drawable/(.*?)\"");
		Matcher matcher = pattern.matcher(androidManifestf.toString());
		androidManifestf = new StringBuffer();
		while (matcher.find()) {
			String str = matcher.group().replace(matcher.group(1), 
					RandomStringUtil.autoImitateStringToLowerCase(matcher.group(1)));
			matcher.appendReplacement(androidManifestf, str);
		}
		matcher.appendTail(androidManifestf);
		

		pattern = Pattern.compile("android:name=\\s*\"\\s*([\\w\\._]+)\\s*\"");
		matcher = pattern.matcher(androidManifestf.toString());
		androidManifestf = new StringBuffer();
		while (matcher.find()) {
			for (ClazzObjectTrait clazzObjctTrait : clazzObjectTraitS) {
				String str = clazzObjctTrait.getPackageName()+"."+clazzObjctTrait.getClazzName();
				String grp = matcher.group(1);
				if(str.equals(grp)){
					str = matcher.group().replace(grp,
					clazzObjctTrait.getPackageEditName()+"."+clazzObjctTrait.getClazzEditName());
					matcher.appendReplacement(androidManifestf, str);
				}
			}
		}
		matcher.appendTail(androidManifestf);
		FileUtils.saveFile(androidManifestFile, androidManifestf, encoding);
	}
}
