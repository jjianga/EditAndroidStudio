package com.zhkj.code.mode;

import java.util.List;

import com.zhkj.code.bean.ClazzObjectTrait;
import com.zhkj.code.bean.ResFile;
/**
 * ��������   �������ô��룬����ϸ�ڵ�
 * @author Administrator
 *
 */
public interface TrimmingMode extends Mode {

	void trimming(List<ClazzObjectTrait> clazzObjectTraitS,
				List<ResFile> resFileS, String encoding) throws Exception;
	
}
