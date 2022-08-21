package com.zhkj.code.replaceSours;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.zhkj.code.mode.ReplaceFileStringMode;

public class ReplaceRegular implements ReplaceFileStringMode{
	String string = "Ìæ»»ºóµÄÖµ";
	StringBuffer fileBuf = null;

	public void setString(String string) {
		this.string = string;
	}

	public ReplaceRegular(String string, StringBuffer fileBuf) {
		super();
		this.string = string;
		this.fileBuf = fileBuf;
	}

	@Override
	public void lineExecute(String Line) {
		String fileString = fileBuf.toString();
		fileBuf.delete(0,fileBuf.length());
		Pattern pattern = Pattern.compile(Line);
		Matcher matcher = pattern.matcher(fileString);
		while (matcher.find()) {
			String str = matcher.group().replace(matcher.group(1), string)
					.replace("\\", "\\\\").replace("$", "\\$");
			matcher.appendReplacement(fileBuf, str);
		}
		matcher.appendTail(fileBuf);
	}

	@Override
	public void init(HashMap<String, Object> map) {
		// TODO Auto-generated method stub
		
	}
	
}
