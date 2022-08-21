package com.zhkj.code.utils;

import java.util.ArrayList;
import java.util.List;

import com.zhkj.code.bean.ResFile;

public class ResUtli {
	public static <T extends ResFile> List<T> getResFileIsType(List<ResFile> resFileS,int starType,int endType){
		List<T> newResFileS = new ArrayList<T>();
		for (int i = 0; i < resFileS.size(); i++) {
			@SuppressWarnings("unchecked")
			T resFile = (T)resFileS.get(i);
			if(resFile.getType() >= starType && resFile.getType() < endType){
				newResFileS.add(resFile);
			}
		}
		return newResFileS;
	}

	public static <T extends ResFile> List<T> getResFileIsSuffix(List<ResFile> resFileS,String suffix){
		List<T> newResFileS = new ArrayList<T>();
		for (int i = 0; i < resFileS.size(); i++) {
			@SuppressWarnings("unchecked")
			T resFile = (T)resFileS.get(i);
			if(suffix.equals(resFile.getSuffix())){
				newResFileS.add(resFile);
			}
		}
		return newResFileS;
	}
}
