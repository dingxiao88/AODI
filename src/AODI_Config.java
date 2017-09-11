

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




/**AODI���������ļ�����
 * 
 * @author Administrator
 *
 */
public class AODI_Config {
	
	//@1-Ĭ�������ļ���
	public static String Def_Config_FileName = new String("./AODI_Def.aodi");
	//@7-����׼���ļ���
	public static String Def_Strategy_FileName;
		
	//@2-����׼�������ѹ1ѡ���־
	public static boolean Def_VoltageArea1_Sel=true;
	//@3-����׼�������ѹ2ѡ���־
	public static boolean Def_VoltageArea2_Sel=false;
	//@4-����׼�������ѹ3ѡ���־
	public static boolean Def_VoltageArea3_Sel=false;
	//@5-����׼�������ѹ4ѡ���־
	public static boolean Def_VoltageArea4_Sel=false;
	
	//@8-����׼��-��ʼ��ѹ
	public static float  Def_VoltageStart;
	//@9-����׼��-��ֹ��ѹ
	public static float  Def_VoltageEnd;
	//@9-����׼��-��ֹ��ѹ��ʱ
	public static float  Def_VoltageEnd_Delay;
	//@10-����׼��-������ѹ
	public static float  Def_VoltageStep;
	
	//@10-����׼��-�����ѹ1
	public static float  Def_VoltageArea1;
	//@10-����׼��-�����ѹ1-Low
	public static float  Def_VoltageArea1_Low;
	//@10-����׼��-�����ѹ1-Hight
	public static float  Def_VoltageArea1_High;
	//@10-����׼��-�����ѹ2
	public static float  Def_VoltageArea2;
	//@10-����׼��-�����ѹ2-Low
	public static float  Def_VoltageArea2_Low;
	//@10-����׼��-�����ѹ2-Hight
	public static float  Def_VoltageArea2_High;
	//@10-����׼��-�����ѹ3
	public static float  Def_VoltageArea3;
	//@10-����׼��-�����ѹ3-Low
	public static float  Def_VoltageArea3_Low;
	//@10-����׼��-�����ѹ3-Hight
	public static float  Def_VoltageArea3_High;
	//@10-����׼��-�����ѹ4
	public static float  Def_VoltageArea4;
	//@10-����׼��-�����ѹ4-Low
	public static float  Def_VoltageArea4_Low;
	//@10-����׼��-�����ѹ4-Hight
	public static float  Def_VoltageArea4_High;
	
	//@10-����׼��-Ԥ�����
	public static float  Def_Current1;
	//@10-����׼��-Ԥ�����-Low
	public static float  Def_Current1_Low;
	//@10-����׼��-Ԥ�����-Hight
	public static float  Def_Current1_High;
	//@10-����׼��-�����1
	public static float  Def_Current2;
	//@10-����׼��-�����1-Low
	public static float  Def_Current2_Low;
	//@10-����׼��-�����1-Hight
	public static float  Def_Current2_High;
	//@10-����׼��-�����2
	public static float  Def_Current3;
	//@10-����׼��-�����2-Low
	public static float  Def_Current3_Low;
	//@10-����׼��-�����2-Hight
	public static float  Def_Current3_High;
	//@10-����׼��-�������
	public static float  Def_Current4;
	//@10-����׼��-�������-Low
	public static float  Def_Current4_Low;
	//@10-����׼��-�������-Hight
	public static float  Def_Current4_High;
	
	
	/**�����ļ�����
	 * 
	 */
	public AODI_Config()
	{		
		//@1-���Ĭ��Config�ļ�
		if(!(HaveFile(Def_Config_FileName)))
		{
			//@2-��ʧĬ��Config�ļ��贴��
			CreateSave_Config(1,Def_Config_FileName,false);   //������ʼconfig	
		}
	    
		//@-����Ĭ��Config�ļ�
		Load_Config(Def_Config_FileName);
	}
	
	/**Mode 1:����Ĭ�������ļ� 2:�����û��Զ����ļ�
	 * ������ʼxml�ļ�
	 */
	public int CreateSave_Config(int Mode,String FileName,boolean isload)
	{
		int Have_File;   //1:�ļ�����  2:�ļ�������  3:�ļ�������
		
		if(HaveFile(FileName)==true)
		{
			Have_File=1;
		}
		else
		{
			Have_File=2;
		}
		
		//System.out.println(""+Have_File);
		
		//@�ļ�������
		if((Have_File==2)||(isload==true))
		{
			//@1-�������ڵ� ROOT;
	        Element root = new Element("AODI_Config");
	        //@2-���ڵ���ӵ��ĵ��У�
	        Document Doc = new Document(root);
	        
	        //@3-�����ڵ� Voltage;
	        Element VoltageDef = new Element("Voltage");
	        //@4-����Voltage�ӽڵ㡰��ʼ��ѹ������ֵ
	        VoltageDef.addContent(new Element("Def_VoltageStart").setText("50"));
	        //@5-����Voltage�ӽڵ㡰��ֹ��ѹ������ֵ
	        VoltageDef.addContent(new Element("Def_VoltageEnd").setText("80"));
	        //@5-����Voltage�ӽڵ㡰��ֹ��ѹ��ʱ������ֵ
	        VoltageDef.addContent(new Element("Def_VoltageEnd_Delay").setText("10"));
	        //@6-����Voltage�ӽڵ㡰������ѹ������ֵ
	        VoltageDef.addContent(new Element("Def_VoltageStep").setText("0.1"));
	        
	        //@7-����Voltage�ӽڵ㡰�����ѹ1������ֵ
	        VoltageDef.addContent(new Element("Def_VoltageArea1").setText("60"));  
	        //@8-����Voltage�ӽڵ㡰�����ѹ1-Low������ֵ
	        VoltageDef.addContent(new Element("Def_VoltageArea1_Low").setText("0"));  
	        //@9-����Voltage�ӽڵ㡰�����ѹ1-Hight������ֵ
	        VoltageDef.addContent(new Element("Def_VoltageArea1_High").setText("0"));  
	        //@10-����Voltage�ӽڵ㡰�����ѹ2������ֵ
	        VoltageDef.addContent(new Element("Def_VoltageArea2").setText("70"));  
	        //@11-����Voltage�ӽڵ㡰�����ѹ2-Low������ֵ
	        VoltageDef.addContent(new Element("Def_VoltageArea2_Low").setText("0"));  
	        //@12-����Voltage�ӽڵ㡰�����ѹ2-Hight������ֵ
	        VoltageDef.addContent(new Element("Def_VoltageArea2_High").setText("0"));  
	        //@13-����Voltage�ӽڵ㡰�����ѹ3������ֵ
	        VoltageDef.addContent(new Element("Def_VoltageArea3").setText("75"));  
	        //@14-����Voltage�ӽڵ㡰�����ѹ3-Low������ֵ
	        VoltageDef.addContent(new Element("Def_VoltageArea3_Low").setText("0"));  
	        //@15-����Voltage�ӽڵ㡰�����ѹ3-Hight������ֵ
	        VoltageDef.addContent(new Element("Def_VoltageArea3_High").setText("0"));  
	        //@16-����Voltage�ӽڵ㡰�����ѹ4������ֵ
	        VoltageDef.addContent(new Element("Def_VoltageArea4").setText("80"));  
	        //@17-����Voltage�ӽڵ㡰�����ѹ4-Low������ֵ
	        VoltageDef.addContent(new Element("Def_VoltageArea4_Low").setText("0"));  
	        //@18-����Voltage�ӽڵ㡰�����ѹ4-Hight������ֵ
	        VoltageDef.addContent(new Element("Def_VoltageArea4_High").setText("0"));  
	        	        
	        //@19-�����ڵ� Current;
	        Element CurrentDef = new Element("Current");
	        //@20-����Current�ӽڵ㡰Ԥ�����������ֵ
	        CurrentDef.addContent(new Element("Def_Current1").setText("2"));  
	        //@21-����Current�ӽڵ㡰Ԥ�����-Low������ֵ
	        CurrentDef.addContent(new Element("Def_Current1_Low").setText("0"));  
	        //@22-����Current�ӽڵ㡰Ԥ�����-Hight������ֵ
	        CurrentDef.addContent(new Element("Def_Current1_High").setText("0"));  
	        //@23-����Current�ӽڵ㡰�����1������ֵ
	        CurrentDef.addContent(new Element("Def_Current2").setText("16"));  
	        //@24-����Current�ӽڵ㡰�����1-Low������ֵ
	        CurrentDef.addContent(new Element("Def_Current2_Low").setText("0"));  
	        //@25-����Current�ӽڵ㡰�����1-Hight������ֵ
	        CurrentDef.addContent(new Element("Def_Current2_High").setText("0"));  
	        //@26-����Current�ӽڵ㡰�����2������ֵ
	        CurrentDef.addContent(new Element("Def_Current3").setText("11"));  
	        //@27-����Current�ӽڵ㡰�����2-Low������ֵ
	        CurrentDef.addContent(new Element("Def_Current3_Low").setText("0"));  
	        //@28-����Current�ӽڵ㡰�����2-Hight������ֵ
	        CurrentDef.addContent(new Element("Def_Current3_High").setText("0"));  
	        //@29-����Current�ӽڵ㡰�������������ֵ
	        CurrentDef.addContent(new Element("Def_Current4").setText("2"));  
	        //@30-����Current�ӽڵ㡰�������-Low������ֵ
	        CurrentDef.addContent(new Element("Def_Current4_Low").setText("0"));  
	        //@31-����Current�ӽڵ㡰�������-Hight������ֵ
	        CurrentDef.addContent(new Element("Def_Current4_High").setText("0"));  
	        
	        //@32-�����ڵ� Strategy;
	        Element StrategyDef = new Element("Strategy");
	        //@33-����Strategy�ӽڵ㡰�����ļ���������ֵ
	        StrategyDef.addContent(new Element("Def_Strategy_FileName").setText("AODI_Def.aodi"));
	        //@34-����Strategy�ӽڵ㡰���Խ׶�1ѡ�񡱲���ֵ
	        StrategyDef.addContent(new Element("Def_VoltageArea1_Sel").setText("true"));
	        //@35-����Strategy�ӽڵ㡰���Խ׶�2ѡ�񡱲���ֵ
	        StrategyDef.addContent(new Element("Def_VoltageArea2_Sel").setText("false"));
	        //@36-����Strategy�ӽڵ㡰���Խ׶�3ѡ�񡱲���ֵ
	        StrategyDef.addContent(new Element("Def_VoltageArea3_Sel").setText("false"));
	        //@37-����Strategy�ӽڵ㡰���Խ׶�4ѡ�񡱲���ֵ
	        StrategyDef.addContent(new Element("Def_VoltageArea4_Sel").setText("false"));
	                        
	        //@38-�ӽڵ���ӵ����ڵ���;
	        root.addContent(VoltageDef);
	        root.addContent(CurrentDef);
	        root.addContent(StrategyDef);
	        
	        //@39-�����û��Զ��������ļ�
	        if(Mode==2)
	        {
	        	Save_Config(root);
	        }
	
	        //@40-����Ŀ�������ļ�
	        XMLOutputter XMLOut = new XMLOutputter();
		    //@41-��� xml �ļ���
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
	 * ����������Ŀ����
	 */
	public void Load_Config(String LoadName)
	{
        //@2-�����ļ�������
        SAXBuilder sb=new SAXBuilder();//����������
        //@3-����ָ���ļ�
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
		//@4-����ļ����ڵ�
        Element root=doc.getRootElement();
        
        //@5-��ò�������-��ʼ��ѹ
        Def_VoltageStart = Float.parseFloat(root.getChild("Voltage").getChild("Def_VoltageStart").getText());
        //@6-��ò�������-��ֹ��ѹ
        Def_VoltageEnd = Float.parseFloat(root.getChild("Voltage").getChild("Def_VoltageEnd").getText());
        //@6-��ò�������-��ֹ��ѹ��ʱ
        Def_VoltageEnd_Delay = Float.parseFloat(root.getChild("Voltage").getChild("Def_VoltageEnd_Delay").getText());
        //@7-��ò�������-������ѹ
        Def_VoltageStep = Float.parseFloat(root.getChild("Voltage").getChild("Def_VoltageStep").getText());
        
        //@8-��ò�������-�����ѹ1
        Def_VoltageArea1 = Float.parseFloat(root.getChild("Voltage").getChild("Def_VoltageArea1").getText());
        //@9-��ò�������-�����ѹ1-Low
        Def_VoltageArea1_Low = Float.parseFloat(root.getChild("Voltage").getChild("Def_VoltageArea1_Low").getText());
        //@10-��ò�������-�����ѹ1-Hight
        Def_VoltageArea1_High = Float.parseFloat(root.getChild("Voltage").getChild("Def_VoltageArea1_High").getText());
        //@8-��ò�������-�����ѹ2
        Def_VoltageArea2 = Float.parseFloat(root.getChild("Voltage").getChild("Def_VoltageArea2").getText());
        //@9-��ò�������-�����ѹ2-Low
        Def_VoltageArea2_Low = Float.parseFloat(root.getChild("Voltage").getChild("Def_VoltageArea2_Low").getText());
        //@10-��ò�������-�����ѹ2-Hight
        Def_VoltageArea2_High = Float.parseFloat(root.getChild("Voltage").getChild("Def_VoltageArea2_High").getText());
        //@8-��ò�������-�����ѹ3
        Def_VoltageArea3 = Float.parseFloat(root.getChild("Voltage").getChild("Def_VoltageArea3").getText());
        //@9-��ò�������-�����ѹ3-Low
        Def_VoltageArea3_Low = Float.parseFloat(root.getChild("Voltage").getChild("Def_VoltageArea3_Low").getText());
        //@10-��ò�������-�����ѹ3-Hight
        Def_VoltageArea3_High = Float.parseFloat(root.getChild("Voltage").getChild("Def_VoltageArea3_High").getText());
        //@8-��ò�������-�����ѹ4
        Def_VoltageArea4 = Float.parseFloat(root.getChild("Voltage").getChild("Def_VoltageArea4").getText());
        //@9-��ò�������-�����ѹ4-Low
        Def_VoltageArea4_Low = Float.parseFloat(root.getChild("Voltage").getChild("Def_VoltageArea4_Low").getText());
        //@10-��ò�������-�����ѹ4-Hight
        Def_VoltageArea4_High = Float.parseFloat(root.getChild("Voltage").getChild("Def_VoltageArea4_High").getText());
        
        //@16-��ò�������-Ԥ�����
        Def_Current1 = Float.parseFloat(root.getChild("Current").getChild("Def_Current1").getText());
        //@17-��ò�������-Ԥ�����-Low
        Def_Current1_Low = Float.parseFloat(root.getChild("Current").getChild("Def_Current1_Low").getText());
        //@18-��ò�������-Ԥ�����-Hight
        Def_Current1_High = Float.parseFloat(root.getChild("Current").getChild("Def_Current1_High").getText());
        //@16-��ò�������-�����1
        Def_Current2 = Float.parseFloat(root.getChild("Current").getChild("Def_Current2").getText());
        //@17-��ò�������-�����1-Low
        Def_Current2_Low = Float.parseFloat(root.getChild("Current").getChild("Def_Current2_Low").getText());
        //@18-��ò�������-�����1-Hight
        Def_Current2_High = Float.parseFloat(root.getChild("Current").getChild("Def_Current2_High").getText());
        //@16-��ò�������-�����2
        Def_Current3 = Float.parseFloat(root.getChild("Current").getChild("Def_Current3").getText());
        //@17-��ò�������-�����2-Low
        Def_Current3_Low = Float.parseFloat(root.getChild("Current").getChild("Def_Current3_Low").getText());
        //@18-��ò�������-�����2-Hight
        Def_Current3_High = Float.parseFloat(root.getChild("Current").getChild("Def_Current3_High").getText());
        //@16-��ò�������-�������
        Def_Current4 = Float.parseFloat(root.getChild("Current").getChild("Def_Current4").getText());
        //@17-��ò�������-�������-Low
        Def_Current4_Low = Float.parseFloat(root.getChild("Current").getChild("Def_Current4_Low").getText());
        //@18-��ò�������-�������-Hight
        Def_Current4_High = Float.parseFloat(root.getChild("Current").getChild("Def_Current4_High").getText());
        
        //@26-��ò�������-�����ļ���
        Def_Strategy_FileName = root.getChild("Strategy").getChild("Def_Strategy_FileName").getText();
        //@27-��ò�������-�����ѹ1ѡ���־
        Def_VoltageArea1_Sel = Boolean.parseBoolean(root.getChild("Strategy").getChild("Def_VoltageArea1_Sel").getText());
        //@28-��ò�������-�����ѹ2ѡ���־
        Def_VoltageArea2_Sel = Boolean.parseBoolean(root.getChild("Strategy").getChild("Def_VoltageArea2_Sel").getText());
        //@29-��ò�������-�����ѹ3ѡ���־
        Def_VoltageArea3_Sel = Boolean.parseBoolean(root.getChild("Strategy").getChild("Def_VoltageArea3_Sel").getText());
        //@30-��ò�������-�����ѹ4ѡ���־
        Def_VoltageArea4_Sel = Boolean.parseBoolean(root.getChild("Strategy").getChild("Def_VoltageArea4_Sel").getText());
        
	}
	
	/**
	 * ����������Ŀ����
	 */
	private void Save_Config(Element root)
	{
        //@4-����Voltage�ӽڵ㡰��ʼ��ѹ��
        root.getChild("Voltage").getChild("Def_VoltageStart").setText(""+StrategyControllerNew.Temp_VoltageStart);
        //@5-����Voltage�ӽڵ㡰��ֹ��ѹ�� 
        root.getChild("Voltage").getChild("Def_VoltageEnd").setText(""+StrategyControllerNew.Temp_VoltageEnd);
        //@5-����Voltage�ӽڵ㡰��ֹ��ѹ��ʱ�� 
        root.getChild("Voltage").getChild("Def_VoltageEnd_Delay").setText(""+StrategyControllerNew.Temp_VoltageEnd_Delay);
        //@6-����Voltage�ӽڵ㡰������ѹ�� 
        root.getChild("Voltage").getChild("Def_VoltageStep").setText(""+StrategyControllerNew.Temp_VoltageStep);
        
        //@7-����Voltage�ӽڵ㡰�����ѹ1�� 
        root.getChild("Voltage").getChild("Def_VoltageArea1").setText(""+StrategyControllerNew.Temp_VoltageArea1);
        //@8-����Voltage�ӽڵ㡰�����ѹ1-Low�� 
        root.getChild("Voltage").getChild("Def_VoltageArea1_Low").setText(""+StrategyControllerNew.Temp_VoltageArea1_Low);
        //@9-����Voltage�ӽڵ㡰�����ѹ1-High�� 
        root.getChild("Voltage").getChild("Def_VoltageArea1_High").setText(""+StrategyControllerNew.Temp_VoltageArea1_High);
        //@7-����Voltage�ӽڵ㡰�����ѹ2�� 
        root.getChild("Voltage").getChild("Def_VoltageArea2").setText(""+StrategyControllerNew.Temp_VoltageArea2);
        //@8-����Voltage�ӽڵ㡰�����ѹ2-Low�� 
        root.getChild("Voltage").getChild("Def_VoltageArea2_Low").setText(""+StrategyControllerNew.Temp_VoltageArea2_Low);
        //@9-����Voltage�ӽڵ㡰�����ѹ2-High�� 
        root.getChild("Voltage").getChild("Def_VoltageArea2_High").setText(""+StrategyControllerNew.Temp_VoltageArea2_High);
        //@7-����Voltage�ӽڵ㡰�����ѹ3�� 
        root.getChild("Voltage").getChild("Def_VoltageArea3").setText(""+StrategyControllerNew.Temp_VoltageArea3);
        //@8-����Voltage�ӽڵ㡰�����ѹ3-Low�� 
        root.getChild("Voltage").getChild("Def_VoltageArea3_Low").setText(""+StrategyControllerNew.Temp_VoltageArea3_Low);
        //@9-����Voltage�ӽڵ㡰�����ѹ3-High�� 
        root.getChild("Voltage").getChild("Def_VoltageArea3_High").setText(""+StrategyControllerNew.Temp_VoltageArea3_High);
        //@7-����Voltage�ӽڵ㡰�����ѹ4�� 
        root.getChild("Voltage").getChild("Def_VoltageArea4").setText(""+StrategyControllerNew.Temp_VoltageArea4);
        //@8-����Voltage�ӽڵ㡰�����ѹ4-Low�� 
        root.getChild("Voltage").getChild("Def_VoltageArea4_Low").setText(""+StrategyControllerNew.Temp_VoltageArea4_Low);
        //@9-����Voltage�ӽڵ㡰�����ѹ4-High�� 
        root.getChild("Voltage").getChild("Def_VoltageArea4_High").setText(""+StrategyControllerNew.Temp_VoltageArea4_High);

        //@15-����Current�ӽڵ㡰Ԥ������� 
        root.getChild("Current").getChild("Def_Current1").setText(""+StrategyControllerNew.Temp_Current1);
        //@15-����Current�ӽڵ㡰Ԥ�����-Low�� 
        root.getChild("Current").getChild("Def_Current1_Low").setText(""+StrategyControllerNew.Temp_Current1_Low);
        //@15-����Current�ӽڵ㡰Ԥ�����-High�� 
        root.getChild("Current").getChild("Def_Current1_High").setText(""+StrategyControllerNew.Temp_Current1_High);
        //@15-����Current�ӽڵ㡰�����1�� 
        root.getChild("Current").getChild("Def_Current2").setText(""+StrategyControllerNew.Temp_Current2);
        //@15-����Current�ӽڵ㡰�����1-Low�� 
        root.getChild("Current").getChild("Def_Current2_Low").setText(""+StrategyControllerNew.Temp_Current2_Low);
        //@15-����Current�ӽڵ㡰�����1-High�� 
        root.getChild("Current").getChild("Def_Current2_High").setText(""+StrategyControllerNew.Temp_Current2_High);
        //@15-����Current�ӽڵ㡰�����2�� 
        root.getChild("Current").getChild("Def_Current3").setText(""+StrategyControllerNew.Temp_Current3);
        //@15-����Current�ӽڵ㡰�����2-Low�� 
        root.getChild("Current").getChild("Def_Current3_Low").setText(""+StrategyControllerNew.Temp_Current3_Low);
        //@15-����Current�ӽڵ㡰�����2-High�� 
        root.getChild("Current").getChild("Def_Current3_High").setText(""+StrategyControllerNew.Temp_Current3_High);
        //@15-����Current�ӽڵ㡰��������� 
        root.getChild("Current").getChild("Def_Current4").setText(""+StrategyControllerNew.Temp_Current4);
        //@15-����Current�ӽڵ㡰�������-Low�� 
        root.getChild("Current").getChild("Def_Current4_Low").setText(""+StrategyControllerNew.Temp_Current4_Low);
        //@15-����Current�ӽڵ㡰�������-High�� 
        root.getChild("Current").getChild("Def_Current4_High").setText(""+StrategyControllerNew.Temp_Current4_High);

        //@25-����Strategy�ӽڵ㡰�����ļ����� 
        root.getChild("Strategy").getChild("Def_Strategy_FileName").setText(""+StrategyControllerNew.Strategy_FileName);	       
        //@26-����Strategy�ӽڵ㡰�����ѹ1ѡ�� 
        root.getChild("Strategy").getChild("Def_VoltageArea1_Sel").setText(""+StrategyControllerNew.VoltageArea1_Sel_Flag);	   
        //@27-����Strategy�ӽڵ㡰�����ѹ2ѡ�� 
        root.getChild("Strategy").getChild("Def_VoltageArea2_Sel").setText(""+StrategyControllerNew.VoltageArea2_Sel_Flag);
        //@28-����Strategy�ӽڵ㡰�����ѹ3ѡ�� 
        root.getChild("Strategy").getChild("Def_VoltageArea3_Sel").setText(""+StrategyControllerNew.VoltageArea3_Sel_Flag);
        //@29-����Strategy�ӽڵ㡰�����ѹ4ѡ�� 
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
