package com.zhkj.code.mode;

import java.util.HashMap;

import com.zhkj.log.Log;

public abstract class ExecuteMode implements Mode,Comparable<ExecuteMode> {
	public int rankIndex;		//������ ��ִ֤��˳��
	public abstract void execute(String projectPath);
	public void init(HashMap<String, Object> map) {
		try {
			rankIndex = Integer.parseInt((String)map.get("rankIndex"));
		} catch (Exception e) {
			Log.appendErr(this.getClass().getTypeName()+"��rankIndex ִ��˳���ʼ������");
		}
	}
	
}
