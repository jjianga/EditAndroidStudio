package com.zhkj.code.replaceSours;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import com.zhkj.code.mode.ReplaceFileStringMode;
import com.zhkj.code.utils.FileUtils;
import com.zhkj.log.Log;

public class ReplaceImage implements ReplaceFileStringMode{
	private String[] relativePathS;//="相对路径集合"
	private String imgPath;//="图片目录"
	private String projectPath;//="项目目录"

	public ReplaceImage(String relativePath,String imgPath, String projectPath) {
		super();
		this.relativePathS = relativePath.split(",");
		this.imgPath = imgPath + File.separator;
		this.projectPath = projectPath;
	}

	@Override
	public void lineExecute(String Line) {
		for (int index = 0; index < relativePathS.length; index++) {
			//res/drawable-hdpi/,res/drawable-hdpi/=guide1.jpg,guide2.jpg,guide3.jpg
			String[] string = Line.split("=");
			String[] rootPath = string[0].split(",");
			String[] imgList = string[1].split(",");
			for (int k = 0; k < rootPath.length; k++) {
				for (int i = 0; i < imgList.length; i++) {
					//检查图片是否存在
					File file = new File(imgPath + rootPath[k] + imgList[i]);
					File fileProject = new File(projectPath + 
							relativePathS[index] + rootPath[k] + imgList[i]);
					if(file.exists()){
						try {
							FileUtils.copyFolder(file, fileProject);
						} catch (IOException e) {
							Log.appendErr("ReplaceImage 图片copy失败：" + rootPath[k] + imgList[i]);
						}
					}else{
						Log.appendErr("ReplaceImage 图片不存在：" + rootPath[k] + imgList[i]);
					}
				}
			}
		}
	}

	@Override
	public void init(HashMap<String, Object> map) {
		// TODO Auto-generated method stub
		
	}

}
