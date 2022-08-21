package hotplug.preinstall.readResFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.zhkj.code.bean.ImageFile;
import com.zhkj.code.bean.ResFile;
import com.zhkj.code.bean.XmlFile;
import com.zhkj.code.bean.XmlTager;
import com.zhkj.code.mode.ReadResFileMode;

public class ReadResFile implements ReadResFileMode {
	@Override
	public void init(HashMap<String, Object> map) {
	}
	@Override
	public ResFile resResFile(File file, String encoding) {
		ResFile resFile =null;
		String fildeName = file.getName();
		//xml文件 读出来
		String suffix = fildeName.substring(fildeName.indexOf("."), fildeName.length());
		if(".XML".equals(suffix.toUpperCase())){
			String parentPath = file.getParent();
			parentPath = parentPath.substring(
					parentPath.lastIndexOf(File.separator)+1, parentPath.length());
			fildeName = fildeName.substring(0,fildeName.length()-suffix.length());
			XmlFile xmlFile = new XmlFile(parentPath, fildeName,suffix);
			resFile = redXmlFile(file, xmlFile, encoding);
		}else if(suffix!=null && suffix.toUpperCase().contains(".PNG")){
			//读取png  .9.png 和 .png
			String parentPath = file.getParent();
			parentPath = parentPath.substring(
					parentPath.lastIndexOf(File.separator)+1, parentPath.length());
			fildeName = fildeName.substring(0,fildeName.length()-suffix.length());
			resFile = new ImageFile(parentPath, fildeName, suffix);
		}else if(".JPG".equals(suffix.toUpperCase())){
			String parentPath = file.getParent();
			parentPath = parentPath.substring(
					parentPath.lastIndexOf(File.separator)+1, parentPath.length());
			fildeName = fildeName.substring(0,fildeName.length()-suffix.length());
			resFile = new ImageFile(parentPath, fildeName, suffix);
		}
		return resFile;
	}
	/**
	 * 阅读xml文件  返回类的描述信息 
	 * @param xmlFile				//xml文件
	 * @param encoding				//xml 编码 UTF-8 GBK 
	 * @return
	 */
	public XmlFile redXmlFile(File file,XmlFile xmlFile,String encoding) {
        BufferedReader reader = null;
        StringBuffer sb = new StringBuffer();
        try {		
            InputStreamReader read = 
            		new InputStreamReader(new FileInputStream(file),encoding);      
            reader = new BufferedReader(read);
            String xmlCode = null;
            // 一次读入一行，直到读入null为文件结束
            while ((xmlCode = reader.readLine()) != null) {
            	sb.append(xmlCode);
            }
            read.close();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        readerTager(sb, xmlFile);
		return xmlFile;
	}
	
	public static void readerTager(StringBuffer sb, XmlFile xmlFile){
		Pattern pattern = Pattern.compile("<\\s*[^/!?].*?>");
		Pattern patternName = Pattern.compile("<([\\w_-]+)\\s+");
		Pattern patternValue = Pattern.compile("\\s+name\\s*=\\s*\"([\\w_]+)");
		Pattern patternId = Pattern.compile("id/([a-zA-Z0-9_]*)");
		Matcher matcher = pattern.matcher(sb);
		while (matcher.find()) {
			String tagerStr = matcher.group(0);
			String name = null;
			String nameParamValue = null;
			String idParamValue = null;
			Matcher matcher2 = patternName.matcher(tagerStr);
			if(matcher2.find()){
				name = matcher2.group(1);
			}
			matcher2 = patternValue.matcher(tagerStr);
			if(matcher2.find()){
				nameParamValue = matcher2.group(1);
			}
			matcher2 = patternId.matcher(tagerStr);
			if(matcher2.find()){
				idParamValue = matcher2.group(1);
			}
			XmlTager XmlTager = new XmlTager(name, nameParamValue, idParamValue);
			xmlFile.getXmlTagger().add(XmlTager);
		}
		
	}
}
