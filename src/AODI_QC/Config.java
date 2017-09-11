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
public class Config {

	//@1-默认配置文件名
	public static String Def_Config_FileName = new String("./AODI_LH.config");

	//@2-超级用户名
	public static String Super_User;
	//@3-超级用户密码
	public static String Super_User_Password;

	//@4-普通用户名
	public static String User;
	//@5-普通用户密码
	public static String User_Password;

	//@6-网卡名
	public static String NetCard_Name = new String("Realtek PCIe GBE Family Controller");

	//@6-网卡IP
	public static String Config_NetIP = new String("192.168.0.103");



	/**配置文件构造
	 *
	 */
	public Config()
	{
		//@1-检查默认Config文件
		if(!(HaveFile(Def_Config_FileName)))
		{
			//@2-丢失默认Config文件需创建
			CreateSave_Config(1,Def_Config_FileName,false);   //创建初始config
		}

		//@-加载默认Config文件
		Load_Config(Def_Config_FileName);
	}

	/**Mode 1:创建默认配置文件 2:创建用户自定义文件
	 * 创建初始xml文件
	 */
	public int CreateSave_Config(int Mode,String FileName,boolean isload)
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
	        Element root = new Element("AODI_Config");
	        //@2-根节点添加到文档中；
	        Document Doc = new Document(root);

	        //@3-创建节点 Main_Set;
	        Element Main_Set = new Element("Main_Set");

	        //@4-创建Main_Set子节点“超级用户”并赋值
	        Main_Set.addContent(new Element("Super_User").setText("admin"));
	        //@5-创建Main_Set子节点“超级用户密码”并赋值
	        Main_Set.addContent(new Element("Super_User_Password").setText("12345678"));

	        //@6-创建Main_Set子节点“普通用户”并赋值
	        Main_Set.addContent(new Element("User").setText("aodi"));
	        //@7-创建Main_Set子节点“普通用户密码”并赋值
	        Main_Set.addContent(new Element("User_Password").setText("aodi"));

	        //@8-创建Main_Set子节点“网卡名”并赋值
	        Main_Set.addContent(new Element("NetCard_Name").setText("Realtek PCIe GBE Family Controller"));

	        //@9-创建Main_Set子节点“IP地址”并赋值
	        Main_Set.addContent(new Element("IP_Add1").setText("192"));
	        Main_Set.addContent(new Element("IP_Add2").setText("168"));
	        Main_Set.addContent(new Element("IP_Add3").setText("0"));
	        Main_Set.addContent(new Element("IP_Add4").setText("103"));

	        //@10-子节点添加到根节点上;
	        root.addContent(Main_Set);

	        //@11-创建用户自定义配置文件
	        if(Mode==2)
	        {
	        	Save_Config(root);
	        }

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

        //@5-获得超级用户名
        Super_User = root.getChild("Main_Set").getChild("Super_User").getText();
        //@6-获得超级用户名密码
        Super_User_Password = root.getChild("Main_Set").getChild("Super_User_Password").getText();

        //@7-获得普通用户名
        Super_User = root.getChild("Main_Set").getChild("User").getText();
        //@8-获得普通用户名密码
        Super_User_Password = root.getChild("Main_Set").getChild("User_Password").getText();

        //@9-获得网卡名
        NetCard_Name = root.getChild("Main_Set").getChild("NetCard_Name").getText();

        //@10-获得网卡名
        Config_NetIP = new String (root.getChild("Main_Set").getChild("IP_Add1").getText()+"."+
        						   root.getChild("Main_Set").getChild("IP_Add2").getText()+"."+
        						   root.getChild("Main_Set").getChild("IP_Add3").getText()+"."+
        						   root.getChild("Main_Set").getChild("IP_Add4").getText());

	}

	/**
	 * 保存配置项目数据
	 */
	private void Save_Config(Element root)
	{
//        //@4-保存Voltage子节点“起始电压”
//        root.getChild("Voltage").getChild("Def_VoltageStart").setText(""+StrategyControllerNew.Temp_VoltageStart);
//        //@5-保存Voltage子节点“终止电压”
//        root.getChild("Voltage").getChild("Def_VoltageEnd").setText(""+StrategyControllerNew.Temp_VoltageEnd);
//        //@5-保存Voltage子节点“终止电压延时”
//        root.getChild("Voltage").getChild("Def_VoltageEnd_Delay").setText(""+StrategyControllerNew.Temp_VoltageEnd_Delay);
//        //@6-保存Voltage子节点“步进电压”
//        root.getChild("Voltage").getChild("Def_VoltageStep").setText(""+StrategyControllerNew.Temp_VoltageStep);
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
