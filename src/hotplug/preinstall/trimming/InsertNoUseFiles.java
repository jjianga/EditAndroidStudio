package hotplug.preinstall.trimming;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.zhkj.code.bean.ClazzObjectTrait;
import com.zhkj.code.bean.ResFile;
import com.zhkj.code.mode.TrimmingMode;
import com.zhkj.code.utils.CreatFIleUril;
import com.zhkj.code.utils.ImageGenerate;
import com.zhkj.code.utils.RandomStringUtil;
import com.zhkj.code.utils.XmlUtils;
import com.zhkj.log.Log;

public class InsertNoUseFiles implements TrimmingMode{
	String _srcPath;
	String _resPath;
	@Override
	public void init(HashMap<String, Object> map) {
	}
	@Override
	public void trimming(List<ClazzObjectTrait> clazzObjectTraitS, List<ResFile> resFileS, String encoding) {
		Log.appendInfo("�������ô���...");
		String layoutPath = _resPath + "layout/";
		//drawableĿ¼
		String drawablePath=_resPath+"drawable";
		for (ClazzObjectTrait clazzObjctTrait : clazzObjectTraitS) {
			//������� ��������   
			int nub = 2 + RandomStringUtil.random.nextInt(8);
			String packeage = null;
			String ClazzName = RandomStringUtil.getRandomAbc(nub);
			//���ѡȡ����
			switch (RandomStringUtil.random.nextInt(10)) {
			case 0:
				packeage = generatePackeage(0);
				break;
			case 1:
				packeage = generatePackeage(1);
				break;
			case 2:
				packeage = generatePackeage(2);
				break;
			case 3:
				packeage = generatePackeage(3);
				break;
			case 4:
				packeage = generatePackeage(4);
				break;
			case 5:
				packeage = generatePackeage(5);
				break;
			case 6:
				packeage = generatePackeage(6);
				break;
			default:
				packeage = clazzObjctTrait.getPackageEditName();
			}
			//����ʵ����
			CreatFIleUril.counterfeitJava(packeage, ClazzName, _srcPath);
		}
		//�������ͼƬ  layoutPath  ��������       
		for (ResFile resFile : resFileS) {
			if(".png".equals(resFile.getSuffix()) && RandomStringUtil.randomSpecie()){
				try{
					ImageGenerate.paintRandomPNG(_resPath+resFile.getParentPath());
				}catch (Exception e){
					Log.appendErr("����ͼƬ����" + _resPath+resFile.getParentPath());
				}
			}
			if("layout".equals(resFile.getParentPath())){
				if(RandomStringUtil.randomSpecie()){
					StringBuffer stb = new StringBuffer();
					stb.append(layoutPath).append(File.separator).
					append(RandomStringUtil.randomLowSting(RandomStringUtil.random.nextInt(5)+3)).
					append(".xml");
					XmlUtils.generateXml(stb.toString());
				}
				String filePath = _resPath+resFile.getParentPath()+File.separator 
						+resFile.getFileEditName() + resFile.getSuffix() ;
				while(RandomStringUtil.randomSpecie()){
					XmlUtils.insertXml(filePath);
				}
			}
			//��ȡ drawable Ŀ¼
			if("drawable".equals(resFile.getParentPath())){
				if(RandomStringUtil.randomSpecie()){
					StringBuffer sb=new StringBuffer();
					int num=8+(int)(Math.random()*7);
					String name= RandomStringUtil.randomLowSting(num);
					sb.append(drawablePath).append(File.separator ).append(name).append(".xml");
					XmlUtils.generateDrawableXml(sb.toString());
				}
			}
		}
	}
	List<String> arr =new ArrayList<>();
	public String generatePackeage(int index){
		if(arr.size() > 6){return arr.get(index);}
		StringBuffer strBf = new StringBuffer();
		int len = RandomStringUtil.random.nextInt(6);
		for (int i = -1; i < len; i++) {
			strBf.append(
					RandomStringUtil.randomLowSting(RandomStringUtil.random.nextInt(5)+3));
			strBf.append(".");
		}
		strBf.append(RandomStringUtil.randomLowSting(RandomStringUtil.random.nextInt(5)+3));
		arr.add(strBf.toString());
		return strBf.toString();
	}
}
