package com.zhkj.code.replaceSours;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.zhkj.code.mode.ReplaceFileStringMode;

public class ReplaceForRegular implements ReplaceFileStringMode {
	private StringBuffer fileBuf = null;
	private String value = "Ìæ»»ºóµÄ";
	private String regularValue = "myName";

	public ReplaceForRegular(String value, StringBuffer fileBuf,String regularValue) {
		super();
		this.fileBuf = fileBuf;
		this.value = value;
		this.regularValue = regularValue;
	}

	@Override
	public void lineExecute(String Line) {
		//(<color name="myName">).*?(</color>)=black,desc_text,background_user
		String string = Line.substring(0,Line.lastIndexOf("="));
		String[] regularList = Line.substring(Line.lastIndexOf("=")+1, Line.length()).split(",");
		String str = null;
		for (int i = 0; i < regularList.length; i++) {
			str = fileBuf.toString();
			fileBuf.delete(0, fileBuf.length());
			String strl = string.replace(regularValue, regularList[i]);
			Pattern pattern = Pattern.compile(strl);
			Matcher matcher = pattern.matcher(str);
			while (matcher.find()) {
				String strc = matcher.group(1)+value + matcher.group(2)
						.replace("\\", "\\\\").replace("$", "\\$");
				matcher.appendReplacement(fileBuf, strc);
			}
			matcher.appendTail(fileBuf);
		}
	}

	@Override
	public void init(HashMap<String, Object> map) {
		// TODO Auto-generated method stub
		
	}

}
