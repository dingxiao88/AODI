package AODI_QC;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;
import org.jdom.Content;




/**AODI策略配置文件管理
 *
 * @author Administrator
 *
 */
public class PudModel {

	//@1-默认配置文件名
	public static String Def_PudModel_FileName = new String("./AODI_PudModel.config");

	//@2-产品型号总数
	public static int PudModel_Num=1;

	//@3-
	public static int PudModel_MaxCount = 1000;

	//@4-产品型号缓存-5000种型号
	public static PudModel_Set[] AODI_PudModel = new PudModel_Set[PudModel_MaxCount];



    /**
    *
    */
   private void AODI_PudModel_Init()
   {
	   	for(int i=0;i<PudModel_MaxCount;i++)
	   	{
	   		AODI_PudModel[i] = new PudModel_Set();
	   	}
   }

	/**配置文件构造
	 *
	 */
	public PudModel()
	{
		AODI_PudModel_Init();

		//@1-检查默认Config文件
		if(!(HaveFile(Def_PudModel_FileName)))
		{
			//@2-丢失默认Config文件需创建
			CreateSave_Config(Def_PudModel_FileName,false);   //创建初始config
		}

		//@-加载默认Config文件
		Load_Config(Def_PudModel_FileName);
	}

	/**Mode 1:创建默认配置文件 2:创建用户自定义文件
	 * 创建初始xml文件
	 */
	public int CreateSave_Config(String FileName,boolean isload)
	{
		int Have_File;   //1:文件存在  2:文件不存在  3:文件名出错

		if(HaveFile(FileName)==true)
		{
			Have_File=1;
		}
		else
		{
			Have_File=2;
		}

		//System.out.println(""+Have_File);

		//@文件不存在
		if((Have_File==2)||(isload==true))
		{
			//@1-创建根节点 ROOT;
	        Element root = new Element("PudModel");
	        //@2-根节点添加到文档中；
	        Document Doc = new Document(root);

	        //@3-创建节点 Main_Set;
	        Element PudModel_Set = new Element("PudModel_Set");

	        //@4-创建PudModel_Set子节点“产品型号总数”并赋值
	        PudModel_Set.addContent(new Element("PudModel_Num").setText("1"));

	        //@4-创建PudModel_Set子节点“型号-2415”并赋值
	        PudModel_Set.addContent(new Element("PudModel1_Name").setText("2415"));

	        //@4-创建PudModel_Set子节点“型号-2415”-电压限制并赋值
	        PudModel_Set.addContent(new Element("PudModel1_VoltageLimit").setText("29"));
	        //@4-创建PudModel_Set子节点“型号-2415”-电压限制范围并赋值
	        PudModel_Set.addContent(new Element("PudModel1_VoltageLimit_Range").setText("1"));

	        //@4-创建PudModel_Set子节点“型号-2415”-电流限制并赋值
	        PudModel_Set.addContent(new Element("PudModel1_CurrentLimit").setText("14.5"));
	        //@4-创建PudModel_Set子节点“型号-2415”-电流限制范围并赋值
	        PudModel_Set.addContent(new Element("PudModel1_CurrentLimit_Range").setText("0.5"));

	        //@4-创建PudModel_Set子节点“型号-2415”-温度1限制并赋值
	        PudModel_Set.addContent(new Element("PudModel1_Temperature1Limit").setText("60"));
	        //@4-创建PudModel_Set子节点“型号-2415”-温度1限制范围并赋值
	        PudModel_Set.addContent(new Element("PudModel1_Temperature1Limit_Range").setText("10"));

	        //@4-创建PudModel_Set子节点“型号-2415”-温度2限制并赋值
	        PudModel_Set.addContent(new Element("PudModel1_Temperature2Limit").setText("60"));
	        //@4-创建PudModel_Set子节点“型号-2415”-温度2限制范围并赋值
	        PudModel_Set.addContent(new Element("PudModel1_Temperature2Limit_Range").setText("10"));

	        //@4-创建PudModel_Set子节点“型号-2415”-温度3限制并赋
	        PudModel_Set.addContent(new Element("PudModel1_Temperature3Limit").setText("70"));
	        //@4-创建PudModel_Set子节点“型号-2415”-温度3限制范围并赋值
	        PudModel_Set.addContent(new Element("PudModel1_Temperature3Limit_Range").setText("10"));

	        //@10-子节点添加到根节点上;
	        root.addContent(PudModel_Set);

	        //@12-创建目标配置文件
	        XMLOutputter XMLOut = new XMLOutputter();
		    //@13-输出 xml 文件；
		    try {
				XMLOut.output(Doc, new FileOutputStream(FileName));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				Have_File=3;
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Have_File=3;
			}
		}

		return Have_File;

	}

	/**
	 * 加载配置项目数据
	 */
	public void Load_Config(String LoadName)
	{
        //@2-创建文件解析器
        SAXBuilder sb=new SAXBuilder();//建立构造器
        //@3-读入指定文件
        Document doc = null;
		try {
			doc = sb.build(new FileInputStream(LoadName));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (JDOMException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//@4-获得文件根节点
        Element root=doc.getRootElement();

        //@5-获得产品型号总数
        PudModel_Num = Integer.valueOf(root.getChild("PudModel_Set").getChild("PudModel_Num").getText());

        //@6-根据产品型号总数加载产品型号参数表
        for(int i=0;i<PudModel_Num;i++)
        {
        	//@-获取产品型号名PudModel1_Name
        	AODI_PudModel[i].Set_PudModel_Name(root.getChild("PudModel_Set").getChild("PudModel"+(i+1)+"_Name").getText());

        	//@-获取产品型号-电压限制值
        	AODI_PudModel[i].Set_PudModel_VoltageLimit(Float.valueOf(root.getChild("PudModel_Set").getChild("PudModel"+(i+1)+"_VoltageLimit").getText()));
        	//@-获取产品型号-电压限制值范围
        	AODI_PudModel[i].Set_PudModel_VoltageLimit_Range(Float.valueOf(root.getChild("PudModel_Set").getChild("PudModel"+(i+1)+"_VoltageLimit_Range").getText()));

        	//@-获取产品型号-电流限制值
        	AODI_PudModel[i].Set_PudModel_CurrentLimit(Float.valueOf(root.getChild("PudModel_Set").getChild("PudModel"+(i+1)+"_CurrentLimit").getText()));
        	//@-获取产品型号-电流限制值范围
        	AODI_PudModel[i].Set_PudModel_CurrentLimit_Range(Float.valueOf(root.getChild("PudModel_Set").getChild("PudModel"+(i+1)+"_CurrentLimit_Range").getText()));

        	//@-获取产品型号-温度1限制值
        	AODI_PudModel[i].Set_PudModel_Temperature1Limit(Float.valueOf(root.getChild("PudModel_Set").getChild("PudModel"+(i+1)+"_Temperature1Limit").getText()));
        	//@-获取产品型号-温度1限制值范围
        	AODI_PudModel[i].Set_PudModel_Temperature1Limit_Range(Float.valueOf(root.getChild("PudModel_Set").getChild("PudModel"+(i+1)+"_Temperature1Limit_Range").getText()));

        	//@-获取产品型号-温度2限制值
        	AODI_PudModel[i].Set_PudModel_Temperature2Limit(Float.valueOf(root.getChild("PudModel_Set").getChild("PudModel"+(i+1)+"_Temperature2Limit").getText()));
        	//@-获取产品型号-温度2限制值范围
        	AODI_PudModel[i].Set_PudModel_Temperature2Limit_Range(Float.valueOf(root.getChild("PudModel_Set").getChild("PudModel"+(i+1)+"_Temperature2Limit_Range").getText()));

        	//@-获取产品型号-温度3限制值
        	AODI_PudModel[i].Set_PudModel_Temperature3Limit(Float.valueOf(root.getChild("PudModel_Set").getChild("PudModel"+(i+1)+"_Temperature3Limit").getText()));
        	//@-获取产品型号-温度3限制值范围
        	AODI_PudModel[i].Set_PudModel_Temperature3Limit_Range(Float.valueOf(root.getChild("PudModel_Set").getChild("PudModel"+(i+1)+"_Temperature3Limit_Range").getText()));
        }

//        System.out.println("t1"+AODI_PudModel[0].Get_PudModel_Temperature2Limit());

	}

	/**
	 * 保存配置项目数据
	 */
	public boolean Save_Config()
	{
		boolean flag = false;

		//@1-创建根节点 ROOT;
        Element root = new Element("PudModel");
        //@2-根节点添加到文档中；
        Document Doc = new Document(root);

        //@3-创建节点 Main_Set;
        Element PudModel_Set = new Element("PudModel_Set");

        int i = ScreensFramework.AODI_PubModel.PudModel_Num;

        //@4-创建PudModel_Set子节点“产品型号总数”并赋值
        PudModel_Set.addContent(new Element("PudModel_Num").setText(""+i));

        //@根据产品型号数量写入配置文件
        for(int temp=0;temp<i;temp++)
        {
			//@4-创建PudModel_Set子节点“型号”并赋值
			PudModel_Set.addContent(new Element("PudModel"+(temp+1)+"_Name").setText(""+ScreensFramework.AODI_PubModel.AODI_PudModel[temp].Get_PudModel_Name()));

			//@4-创建PudModel_Set子节点“型号”-电压限制并赋值
			PudModel_Set.addContent(new Element("PudModel"+(temp+1)+"_VoltageLimit").setText(""+ScreensFramework.AODI_PubModel.AODI_PudModel[temp].Get_PudModel_VoltageLimit()));
			//@4-创建PudModel_Set子节点“型号”-电压限制范围并赋值
			PudModel_Set.addContent(new Element("PudModel"+(temp+1)+"_VoltageLimit_Range").setText(""+ScreensFramework.AODI_PubModel.AODI_PudModel[temp].Get_PudModel_VoltageLimit_Range()));

			//@4-创建PudModel_Set子节点“型号”-电流限制并赋值
			PudModel_Set.addContent(new Element("PudModel"+(temp+1)+"_CurrentLimit").setText(""+ScreensFramework.AODI_PubModel.AODI_PudModel[temp].Get_PudModel_CurrentLimit()));
			//@4-创建PudModel_Set子节点“型号”-电流限制范围并赋值
			PudModel_Set.addContent(new Element("PudModel"+(temp+1)+"_CurrentLimit_Range").setText(""+ScreensFramework.AODI_PubModel.AODI_PudModel[temp].Get_PudModel_CurrentLimit_Range()));

			//@4-创建PudModel_Set子节点“型号”-温度1限制并赋值
			PudModel_Set.addContent(new Element("PudModel"+(temp+1)+"_Temperature1Limit").setText(""+ScreensFramework.AODI_PubModel.AODI_PudModel[temp].Get_PudModel_Temperature1Limit()));
			//@4-创建PudModel_Set子节点“型号”-温度1限制范围并赋值
			PudModel_Set.addContent(new Element("PudModel"+(temp+1)+"_Temperature1Limit_Range").setText(""+ScreensFramework.AODI_PubModel.AODI_PudModel[temp].Get_PudModel_Temperature1Limit_Range()));

			//@4-创建PudModel_Set子节点“型号”-温度2限制并赋值
			PudModel_Set.addContent(new Element("PudModel"+(temp+1)+"_Temperature2Limit").setText(""+ScreensFramework.AODI_PubModel.AODI_PudModel[temp].Get_PudModel_Temperature2Limit()));
			//@4-创建PudModel_Set子节点“型号”-温度2限制范围并赋值
			PudModel_Set.addContent(new Element("PudModel"+(temp+1)+"_Temperature2Limit_Range").setText(""+ScreensFramework.AODI_PubModel.AODI_PudModel[temp].Get_PudModel_Temperature2Limit_Range()));

			//@4-创建PudModel_Set子节点“型号”-温度3限制并赋
			PudModel_Set.addContent(new Element("PudModel"+(temp+1)+"_Temperature3Limit").setText(""+ScreensFramework.AODI_PubModel.AODI_PudModel[temp].Get_PudModel_Temperature3Limit()));
			//@4-创建PudModel_Set子节点“型号”-温度3限制范围并赋值
			PudModel_Set.addContent(new Element("PudModel"+(temp+1)+"_Temperature3Limit_Range").setText(""+ScreensFramework.AODI_PubModel.AODI_PudModel[temp].Get_PudModel_Temperature3Limit_Range()));

        }



//        //@4-创建PudModel_Set子节点“型号-2415”并赋值
//        PudModel_Set.addContent(new Element("PudModel1_Name").setText("2415"));
//
//        //@4-创建PudModel_Set子节点“型号-2415”-电压限制并赋值
//        PudModel_Set.addContent(new Element("PudModel1_VoltageLimit").setText("29"));
//        //@4-创建PudModel_Set子节点“型号-2415”-电压限制范围并赋值
//        PudModel_Set.addContent(new Element("PudModel1_VoltageLimit_Range").setText("1"));
//
//        //@4-创建PudModel_Set子节点“型号-2415”-电流限制并赋值
//        PudModel_Set.addContent(new Element("PudModel1_CurrentLimit").setText("14.5"));
//        //@4-创建PudModel_Set子节点“型号-2415”-电流限制范围并赋值
//        PudModel_Set.addContent(new Element("PudModel1_CurrentLimit_Range").setText("0.5"));
//
//        //@4-创建PudModel_Set子节点“型号-2415”-温度1限制并赋值
//        PudModel_Set.addContent(new Element("PudModel1_Temperature1Limit").setText("60"));
//        //@4-创建PudModel_Set子节点“型号-2415”-温度1限制范围并赋值
//        PudModel_Set.addContent(new Element("PudModel1_Temperature1Limit_Range").setText("10"));
//
//        //@4-创建PudModel_Set子节点“型号-2415”-温度2限制并赋值
//        PudModel_Set.addContent(new Element("PudModel1_Temperature2Limit").setText("60"));
//        //@4-创建PudModel_Set子节点“型号-2415”-温度2限制范围并赋值
//        PudModel_Set.addContent(new Element("PudModel1_Temperature2Limit_Range").setText("10"));
//
//        //@4-创建PudModel_Set子节点“型号-2415”-温度3限制并赋
//        PudModel_Set.addContent(new Element("PudModel1_Temperature3Limit").setText("70"));
//        //@4-创建PudModel_Set子节点“型号-2415”-温度3限制范围并赋值
//        PudModel_Set.addContent(new Element("PudModel1_Temperature3Limit_Range").setText("10"));

        //@10-子节点添加到根节点上;
        root.addContent(PudModel_Set);

        //@12-创建目标配置文件
        XMLOutputter XMLOut = new XMLOutputter();
	    //@13-输出 xml 文件；
	    try {
			XMLOut.output(Doc, new FileOutputStream(Def_PudModel_FileName));
			flag=true;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			flag=false;
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			flag=false;
		}

	    return flag;
	}

	 /**
	  * Convert char to byte
	  * @param c char
	  * @return byte
	  */
	  private static byte charToByte(char c) {
	     return (byte) "0123456789ABCDEF".indexOf(c);
	 }

	 /**
	  * Convert hex string to byte[]
	  * @param hexString the hex string
	  * @return byte[]
	  */
	 public static byte hexStringToBytes(String hexString) {

	     char[] hexChars = hexString.toCharArray();
	     byte d=0x00;

	     if(hexChars.length==1)
	     d = (byte)(charToByte(hexChars[0]));
	     else if(hexChars.length==2)
	     d = (byte)(charToByte(hexChars[0]) << 4 | charToByte(hexChars[1]));

	     return d;
	 }


	/**
	 *
	 * @return
	 */
	private boolean HaveFile(String File){

		File f = new File(File);
		if(f.exists()){
			return true;
		}else{
			return false;
		}
	}


}
