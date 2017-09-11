

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
public class AODI_Config {
	
	//@1-默认配置文件名
	public static String Def_Config_FileName = new String("./AODI_Def.aodi");
	//@7-策略准则文件名
	public static String Def_Strategy_FileName;
		
	//@2-策略准则区域电压1选择标志
	public static boolean Def_VoltageArea1_Sel=true;
	//@3-策略准则区域电压2选择标志
	public static boolean Def_VoltageArea2_Sel=false;
	//@4-策略准则区域电压3选择标志
	public static boolean Def_VoltageArea3_Sel=false;
	//@5-策略准则区域电压4选择标志
	public static boolean Def_VoltageArea4_Sel=false;
	
	//@8-策略准则-起始电压
	public static float  Def_VoltageStart;
	//@9-策略准则-终止电压
	public static float  Def_VoltageEnd;
	//@9-策略准则-终止电压延时
	public static float  Def_VoltageEnd_Delay;
	//@10-策略准则-步进电压
	public static float  Def_VoltageStep;
	
	//@10-策略准则-区域电压1
	public static float  Def_VoltageArea1;
	//@10-策略准则-区域电压1-Low
	public static float  Def_VoltageArea1_Low;
	//@10-策略准则-区域电压1-Hight
	public static float  Def_VoltageArea1_High;
	//@10-策略准则-区域电压2
	public static float  Def_VoltageArea2;
	//@10-策略准则-区域电压2-Low
	public static float  Def_VoltageArea2_Low;
	//@10-策略准则-区域电压2-Hight
	public static float  Def_VoltageArea2_High;
	//@10-策略准则-区域电压3
	public static float  Def_VoltageArea3;
	//@10-策略准则-区域电压3-Low
	public static float  Def_VoltageArea3_Low;
	//@10-策略准则-区域电压3-Hight
	public static float  Def_VoltageArea3_High;
	//@10-策略准则-区域电压4
	public static float  Def_VoltageArea4;
	//@10-策略准则-区域电压4-Low
	public static float  Def_VoltageArea4_Low;
	//@10-策略准则-区域电压4-Hight
	public static float  Def_VoltageArea4_High;
	
	//@10-策略准则-预充电流
	public static float  Def_Current1;
	//@10-策略准则-预充电流-Low
	public static float  Def_Current1_Low;
	//@10-策略准则-预充电流-Hight
	public static float  Def_Current1_High;
	//@10-策略准则-恒电流1
	public static float  Def_Current2;
	//@10-策略准则-恒电流1-Low
	public static float  Def_Current2_Low;
	//@10-策略准则-恒电流1-Hight
	public static float  Def_Current2_High;
	//@10-策略准则-恒电流2
	public static float  Def_Current3;
	//@10-策略准则-恒电流2-Low
	public static float  Def_Current3_Low;
	//@10-策略准则-恒电流2-Hight
	public static float  Def_Current3_High;
	//@10-策略准则-浮充电流
	public static float  Def_Current4;
	//@10-策略准则-浮充电流-Low
	public static float  Def_Current4_Low;
	//@10-策略准则-浮充电流-Hight
	public static float  Def_Current4_High;
	
	
	/**配置文件构造
	 * 
	 */
	public AODI_Config()
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
	        
	        //@3-创建节点 Voltage;
	        Element VoltageDef = new Element("Voltage");
	        //@4-创建Voltage子节点“起始电压”并赋值
	        VoltageDef.addContent(new Element("Def_VoltageStart").setText("50"));
	        //@5-创建Voltage子节点“终止电压”并赋值
	        VoltageDef.addContent(new Element("Def_VoltageEnd").setText("80"));
	        //@5-创建Voltage子节点“终止电压延时”并赋值
	        VoltageDef.addContent(new Element("Def_VoltageEnd_Delay").setText("10"));
	        //@6-创建Voltage子节点“步进电压”并赋值
	        VoltageDef.addContent(new Element("Def_VoltageStep").setText("0.1"));
	        
	        //@7-创建Voltage子节点“区域电压1”并赋值
	        VoltageDef.addContent(new Element("Def_VoltageArea1").setText("60"));  
	        //@8-创建Voltage子节点“区域电压1-Low”并赋值
	        VoltageDef.addContent(new Element("Def_VoltageArea1_Low").setText("0"));  
	        //@9-创建Voltage子节点“区域电压1-Hight”并赋值
	        VoltageDef.addContent(new Element("Def_VoltageArea1_High").setText("0"));  
	        //@10-创建Voltage子节点“区域电压2”并赋值
	        VoltageDef.addContent(new Element("Def_VoltageArea2").setText("70"));  
	        //@11-创建Voltage子节点“区域电压2-Low”并赋值
	        VoltageDef.addContent(new Element("Def_VoltageArea2_Low").setText("0"));  
	        //@12-创建Voltage子节点“区域电压2-Hight”并赋值
	        VoltageDef.addContent(new Element("Def_VoltageArea2_High").setText("0"));  
	        //@13-创建Voltage子节点“区域电压3”并赋值
	        VoltageDef.addContent(new Element("Def_VoltageArea3").setText("75"));  
	        //@14-创建Voltage子节点“区域电压3-Low”并赋值
	        VoltageDef.addContent(new Element("Def_VoltageArea3_Low").setText("0"));  
	        //@15-创建Voltage子节点“区域电压3-Hight”并赋值
	        VoltageDef.addContent(new Element("Def_VoltageArea3_High").setText("0"));  
	        //@16-创建Voltage子节点“区域电压4”并赋值
	        VoltageDef.addContent(new Element("Def_VoltageArea4").setText("80"));  
	        //@17-创建Voltage子节点“区域电压4-Low”并赋值
	        VoltageDef.addContent(new Element("Def_VoltageArea4_Low").setText("0"));  
	        //@18-创建Voltage子节点“区域电压4-Hight”并赋值
	        VoltageDef.addContent(new Element("Def_VoltageArea4_High").setText("0"));  
	        	        
	        //@19-创建节点 Current;
	        Element CurrentDef = new Element("Current");
	        //@20-创建Current子节点“预充电流”并赋值
	        CurrentDef.addContent(new Element("Def_Current1").setText("2"));  
	        //@21-创建Current子节点“预充电流-Low”并赋值
	        CurrentDef.addContent(new Element("Def_Current1_Low").setText("0"));  
	        //@22-创建Current子节点“预充电流-Hight”并赋值
	        CurrentDef.addContent(new Element("Def_Current1_High").setText("0"));  
	        //@23-创建Current子节点“恒电流1”并赋值
	        CurrentDef.addContent(new Element("Def_Current2").setText("16"));  
	        //@24-创建Current子节点“恒电流1-Low”并赋值
	        CurrentDef.addContent(new Element("Def_Current2_Low").setText("0"));  
	        //@25-创建Current子节点“恒电流1-Hight”并赋值
	        CurrentDef.addContent(new Element("Def_Current2_High").setText("0"));  
	        //@26-创建Current子节点“恒电流2”并赋值
	        CurrentDef.addContent(new Element("Def_Current3").setText("11"));  
	        //@27-创建Current子节点“恒电流2-Low”并赋值
	        CurrentDef.addContent(new Element("Def_Current3_Low").setText("0"));  
	        //@28-创建Current子节点“恒电流2-Hight”并赋值
	        CurrentDef.addContent(new Element("Def_Current3_High").setText("0"));  
	        //@29-创建Current子节点“浮充电流”并赋值
	        CurrentDef.addContent(new Element("Def_Current4").setText("2"));  
	        //@30-创建Current子节点“浮充电流-Low”并赋值
	        CurrentDef.addContent(new Element("Def_Current4_Low").setText("0"));  
	        //@31-创建Current子节点“浮充电流-Hight”并赋值
	        CurrentDef.addContent(new Element("Def_Current4_High").setText("0"));  
	        
	        //@32-创建节点 Strategy;
	        Element StrategyDef = new Element("Strategy");
	        //@33-创建Strategy子节点“策略文件名”并赋值
	        StrategyDef.addContent(new Element("Def_Strategy_FileName").setText("AODI_Def.aodi"));
	        //@34-创建Strategy子节点“策略阶段1选择”并赋值
	        StrategyDef.addContent(new Element("Def_VoltageArea1_Sel").setText("true"));
	        //@35-创建Strategy子节点“策略阶段2选择”并赋值
	        StrategyDef.addContent(new Element("Def_VoltageArea2_Sel").setText("false"));
	        //@36-创建Strategy子节点“策略阶段3选择”并赋值
	        StrategyDef.addContent(new Element("Def_VoltageArea3_Sel").setText("false"));
	        //@37-创建Strategy子节点“策略阶段4选择”并赋值
	        StrategyDef.addContent(new Element("Def_VoltageArea4_Sel").setText("false"));
	                        
	        //@38-子节点添加到根节点上;
	        root.addContent(VoltageDef);
	        root.addContent(CurrentDef);
	        root.addContent(StrategyDef);
	        
	        //@39-创建用户自定义配置文件
	        if(Mode==2)
	        {
	        	Save_Config(root);
	        }
	
	        //@40-创建目标配置文件
	        XMLOutputter XMLOut = new XMLOutputter();
		    //@41-输出 xml 文件；
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
        
        //@5-获得策略配置-起始电压
        Def_VoltageStart = Float.parseFloat(root.getChild("Voltage").getChild("Def_VoltageStart").getText());
        //@6-获得策略配置-终止电压
        Def_VoltageEnd = Float.parseFloat(root.getChild("Voltage").getChild("Def_VoltageEnd").getText());
        //@6-获得策略配置-终止电压延时
        Def_VoltageEnd_Delay = Float.parseFloat(root.getChild("Voltage").getChild("Def_VoltageEnd_Delay").getText());
        //@7-获得策略配置-步进电压
        Def_VoltageStep = Float.parseFloat(root.getChild("Voltage").getChild("Def_VoltageStep").getText());
        
        //@8-获得策略配置-区域电压1
        Def_VoltageArea1 = Float.parseFloat(root.getChild("Voltage").getChild("Def_VoltageArea1").getText());
        //@9-获得策略配置-区域电压1-Low
        Def_VoltageArea1_Low = Float.parseFloat(root.getChild("Voltage").getChild("Def_VoltageArea1_Low").getText());
        //@10-获得策略配置-区域电压1-Hight
        Def_VoltageArea1_High = Float.parseFloat(root.getChild("Voltage").getChild("Def_VoltageArea1_High").getText());
        //@8-获得策略配置-区域电压2
        Def_VoltageArea2 = Float.parseFloat(root.getChild("Voltage").getChild("Def_VoltageArea2").getText());
        //@9-获得策略配置-区域电压2-Low
        Def_VoltageArea2_Low = Float.parseFloat(root.getChild("Voltage").getChild("Def_VoltageArea2_Low").getText());
        //@10-获得策略配置-区域电压2-Hight
        Def_VoltageArea2_High = Float.parseFloat(root.getChild("Voltage").getChild("Def_VoltageArea2_High").getText());
        //@8-获得策略配置-区域电压3
        Def_VoltageArea3 = Float.parseFloat(root.getChild("Voltage").getChild("Def_VoltageArea3").getText());
        //@9-获得策略配置-区域电压3-Low
        Def_VoltageArea3_Low = Float.parseFloat(root.getChild("Voltage").getChild("Def_VoltageArea3_Low").getText());
        //@10-获得策略配置-区域电压3-Hight
        Def_VoltageArea3_High = Float.parseFloat(root.getChild("Voltage").getChild("Def_VoltageArea3_High").getText());
        //@8-获得策略配置-区域电压4
        Def_VoltageArea4 = Float.parseFloat(root.getChild("Voltage").getChild("Def_VoltageArea4").getText());
        //@9-获得策略配置-区域电压4-Low
        Def_VoltageArea4_Low = Float.parseFloat(root.getChild("Voltage").getChild("Def_VoltageArea4_Low").getText());
        //@10-获得策略配置-区域电压4-Hight
        Def_VoltageArea4_High = Float.parseFloat(root.getChild("Voltage").getChild("Def_VoltageArea4_High").getText());
        
        //@16-获得策略配置-预充电流
        Def_Current1 = Float.parseFloat(root.getChild("Current").getChild("Def_Current1").getText());
        //@17-获得策略配置-预充电流-Low
        Def_Current1_Low = Float.parseFloat(root.getChild("Current").getChild("Def_Current1_Low").getText());
        //@18-获得策略配置-预充电流-Hight
        Def_Current1_High = Float.parseFloat(root.getChild("Current").getChild("Def_Current1_High").getText());
        //@16-获得策略配置-恒电流1
        Def_Current2 = Float.parseFloat(root.getChild("Current").getChild("Def_Current2").getText());
        //@17-获得策略配置-恒电流1-Low
        Def_Current2_Low = Float.parseFloat(root.getChild("Current").getChild("Def_Current2_Low").getText());
        //@18-获得策略配置-恒电流1-Hight
        Def_Current2_High = Float.parseFloat(root.getChild("Current").getChild("Def_Current2_High").getText());
        //@16-获得策略配置-恒电流2
        Def_Current3 = Float.parseFloat(root.getChild("Current").getChild("Def_Current3").getText());
        //@17-获得策略配置-恒电流2-Low
        Def_Current3_Low = Float.parseFloat(root.getChild("Current").getChild("Def_Current3_Low").getText());
        //@18-获得策略配置-恒电流2-Hight
        Def_Current3_High = Float.parseFloat(root.getChild("Current").getChild("Def_Current3_High").getText());
        //@16-获得策略配置-浮充电流
        Def_Current4 = Float.parseFloat(root.getChild("Current").getChild("Def_Current4").getText());
        //@17-获得策略配置-浮充电流-Low
        Def_Current4_Low = Float.parseFloat(root.getChild("Current").getChild("Def_Current4_Low").getText());
        //@18-获得策略配置-浮充电流-Hight
        Def_Current4_High = Float.parseFloat(root.getChild("Current").getChild("Def_Current4_High").getText());
        
        //@26-获得策略配置-策略文件名
        Def_Strategy_FileName = root.getChild("Strategy").getChild("Def_Strategy_FileName").getText();
        //@27-获得策略配置-区域电压1选择标志
        Def_VoltageArea1_Sel = Boolean.parseBoolean(root.getChild("Strategy").getChild("Def_VoltageArea1_Sel").getText());
        //@28-获得策略配置-区域电压2选择标志
        Def_VoltageArea2_Sel = Boolean.parseBoolean(root.getChild("Strategy").getChild("Def_VoltageArea2_Sel").getText());
        //@29-获得策略配置-区域电压3选择标志
        Def_VoltageArea3_Sel = Boolean.parseBoolean(root.getChild("Strategy").getChild("Def_VoltageArea3_Sel").getText());
        //@30-获得策略配置-区域电压4选择标志
        Def_VoltageArea4_Sel = Boolean.parseBoolean(root.getChild("Strategy").getChild("Def_VoltageArea4_Sel").getText());
        
	}
	
	/**
	 * 保存配置项目数据
	 */
	private void Save_Config(Element root)
	{
        //@4-保存Voltage子节点“起始电压”
        root.getChild("Voltage").getChild("Def_VoltageStart").setText(""+StrategyControllerNew.Temp_VoltageStart);
        //@5-保存Voltage子节点“终止电压” 
        root.getChild("Voltage").getChild("Def_VoltageEnd").setText(""+StrategyControllerNew.Temp_VoltageEnd);
        //@5-保存Voltage子节点“终止电压延时” 
        root.getChild("Voltage").getChild("Def_VoltageEnd_Delay").setText(""+StrategyControllerNew.Temp_VoltageEnd_Delay);
        //@6-保存Voltage子节点“步进电压” 
        root.getChild("Voltage").getChild("Def_VoltageStep").setText(""+StrategyControllerNew.Temp_VoltageStep);
        
        //@7-保存Voltage子节点“区域电压1” 
        root.getChild("Voltage").getChild("Def_VoltageArea1").setText(""+StrategyControllerNew.Temp_VoltageArea1);
        //@8-保存Voltage子节点“区域电压1-Low” 
        root.getChild("Voltage").getChild("Def_VoltageArea1_Low").setText(""+StrategyControllerNew.Temp_VoltageArea1_Low);
        //@9-保存Voltage子节点“区域电压1-High” 
        root.getChild("Voltage").getChild("Def_VoltageArea1_High").setText(""+StrategyControllerNew.Temp_VoltageArea1_High);
        //@7-保存Voltage子节点“区域电压2” 
        root.getChild("Voltage").getChild("Def_VoltageArea2").setText(""+StrategyControllerNew.Temp_VoltageArea2);
        //@8-保存Voltage子节点“区域电压2-Low” 
        root.getChild("Voltage").getChild("Def_VoltageArea2_Low").setText(""+StrategyControllerNew.Temp_VoltageArea2_Low);
        //@9-保存Voltage子节点“区域电压2-High” 
        root.getChild("Voltage").getChild("Def_VoltageArea2_High").setText(""+StrategyControllerNew.Temp_VoltageArea2_High);
        //@7-保存Voltage子节点“区域电压3” 
        root.getChild("Voltage").getChild("Def_VoltageArea3").setText(""+StrategyControllerNew.Temp_VoltageArea3);
        //@8-保存Voltage子节点“区域电压3-Low” 
        root.getChild("Voltage").getChild("Def_VoltageArea3_Low").setText(""+StrategyControllerNew.Temp_VoltageArea3_Low);
        //@9-保存Voltage子节点“区域电压3-High” 
        root.getChild("Voltage").getChild("Def_VoltageArea3_High").setText(""+StrategyControllerNew.Temp_VoltageArea3_High);
        //@7-保存Voltage子节点“区域电压4” 
        root.getChild("Voltage").getChild("Def_VoltageArea4").setText(""+StrategyControllerNew.Temp_VoltageArea4);
        //@8-保存Voltage子节点“区域电压4-Low” 
        root.getChild("Voltage").getChild("Def_VoltageArea4_Low").setText(""+StrategyControllerNew.Temp_VoltageArea4_Low);
        //@9-保存Voltage子节点“区域电压4-High” 
        root.getChild("Voltage").getChild("Def_VoltageArea4_High").setText(""+StrategyControllerNew.Temp_VoltageArea4_High);

        //@15-保存Current子节点“预充电流” 
        root.getChild("Current").getChild("Def_Current1").setText(""+StrategyControllerNew.Temp_Current1);
        //@15-保存Current子节点“预充电流-Low” 
        root.getChild("Current").getChild("Def_Current1_Low").setText(""+StrategyControllerNew.Temp_Current1_Low);
        //@15-保存Current子节点“预充电流-High” 
        root.getChild("Current").getChild("Def_Current1_High").setText(""+StrategyControllerNew.Temp_Current1_High);
        //@15-保存Current子节点“恒电流1” 
        root.getChild("Current").getChild("Def_Current2").setText(""+StrategyControllerNew.Temp_Current2);
        //@15-保存Current子节点“恒电流1-Low” 
        root.getChild("Current").getChild("Def_Current2_Low").setText(""+StrategyControllerNew.Temp_Current2_Low);
        //@15-保存Current子节点“恒电流1-High” 
        root.getChild("Current").getChild("Def_Current2_High").setText(""+StrategyControllerNew.Temp_Current2_High);
        //@15-保存Current子节点“恒电流2” 
        root.getChild("Current").getChild("Def_Current3").setText(""+StrategyControllerNew.Temp_Current3);
        //@15-保存Current子节点“恒电流2-Low” 
        root.getChild("Current").getChild("Def_Current3_Low").setText(""+StrategyControllerNew.Temp_Current3_Low);
        //@15-保存Current子节点“恒电流2-High” 
        root.getChild("Current").getChild("Def_Current3_High").setText(""+StrategyControllerNew.Temp_Current3_High);
        //@15-保存Current子节点“浮充电流” 
        root.getChild("Current").getChild("Def_Current4").setText(""+StrategyControllerNew.Temp_Current4);
        //@15-保存Current子节点“浮充电流-Low” 
        root.getChild("Current").getChild("Def_Current4_Low").setText(""+StrategyControllerNew.Temp_Current4_Low);
        //@15-保存Current子节点“浮充电流-High” 
        root.getChild("Current").getChild("Def_Current4_High").setText(""+StrategyControllerNew.Temp_Current4_High);

        //@25-保存Strategy子节点“策略文件名” 
        root.getChild("Strategy").getChild("Def_Strategy_FileName").setText(""+StrategyControllerNew.Strategy_FileName);	       
        //@26-保存Strategy子节点“区域电压1选择” 
        root.getChild("Strategy").getChild("Def_VoltageArea1_Sel").setText(""+StrategyControllerNew.VoltageArea1_Sel_Flag);	   
        //@27-保存Strategy子节点“区域电压2选择” 
        root.getChild("Strategy").getChild("Def_VoltageArea2_Sel").setText(""+StrategyControllerNew.VoltageArea2_Sel_Flag);
        //@28-保存Strategy子节点“区域电压3选择” 
        root.getChild("Strategy").getChild("Def_VoltageArea3_Sel").setText(""+StrategyControllerNew.VoltageArea3_Sel_Flag);
        //@29-保存Strategy子节点“区域电压4选择” 
        root.getChild("Strategy").getChild("Def_VoltageArea4_Sel").setText(""+StrategyControllerNew.VoltageArea4_Sel_Flag);
        
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
