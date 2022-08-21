package com.zhkj.code.mode;

import java.io.File;

import com.zhkj.code.bean.ResFile;

public interface ReadResFileMode extends Mode {
	ResFile resResFile(File file, String encoding);
	
}
