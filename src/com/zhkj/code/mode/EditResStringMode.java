package com.zhkj.code.mode;

import java.util.List;

import com.zhkj.code.bean.ResFile;

/**
 * �޸���Դ�ļ� 
 * @author Administrator
 *
 */
public interface EditResStringMode extends Mode {
	/**
	 * 
	 * @param strBf
	 * @param resFile
	 * @param resFileS
	 * @return
	 */
	public StringBuffer editResString(StringBuffer strBf,ResFile resFile,
			List<ResFile> resFileS);

}
