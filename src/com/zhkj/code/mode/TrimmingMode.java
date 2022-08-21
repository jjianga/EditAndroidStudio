package com.zhkj.code.mode;

import java.util.List;

import com.zhkj.code.bean.ClazzObjectTrait;
import com.zhkj.code.bean.ResFile;
/**
 * 代码整理   插入无用代码，整理细节等
 * @author Administrator
 *
 */
public interface TrimmingMode extends Mode {

	void trimming(List<ClazzObjectTrait> clazzObjectTraitS,
				List<ResFile> resFileS, String encoding) throws Exception;
	
}
