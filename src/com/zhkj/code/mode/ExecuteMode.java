package com.zhkj.code.mode;

import java.util.HashMap;

import com.zhkj.log.Log;

public abstract class ExecuteMode implements Mode,Comparable<ExecuteMode> {
	public int rankIndex;		//排序函数 保证执行顺序
	public abstract void execute(String projectPath);
	public void init(HashMap<String, Object> map) {
		try {
			rankIndex = Integer.parseInt((String)map.get("rankIndex"));
		} catch (Exception e) {
			Log.appendErr(this.getClass().getTypeName()+"【rankIndex 执行顺序初始化错误！");
		}
	}
	
}
