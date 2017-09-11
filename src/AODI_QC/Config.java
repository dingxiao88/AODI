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




/**AODI���������ļ�����
 *
 * @author Administrator
 *
 */
public class Config {

	//@1-Ĭ�������ļ���
	public static String Def_Config_FileName = new String("./AODI_LH.config");

	//@2-�����û���
	public static String Super_User;
	//@3-�����û�����
	public static String Super_User_Password;

	//@4-��ͨ�û���
	public static String User;
	//@5-��ͨ�û�����
	public static String User_Password;

	//@6-������
	public static String NetCard_Name = new String("Realtek PCIe GBE Family Controller");

	//@6-����IP
	public static String Config_NetIP = new String("192.168.0.103");



	/**�����ļ�����
	 *
	 */
	public Config()
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

	        //@3-�����ڵ� Main_Set;
	        Element Main_Set = new Element("Main_Set");

	        //@4-����Main_Set�ӽڵ㡰�����û�������ֵ
	        Main_Set.addContent(new Element("Super_User").setText("admin"));
	        //@5-����Main_Set�ӽڵ㡰�����û����롱����ֵ
	        Main_Set.addContent(new Element("Super_User_Password").setText("12345678"));

	        //@6-����Main_Set�ӽڵ㡰��ͨ�û�������ֵ
	        Main_Set.addContent(new Element("User").setText("aodi"));
	        //@7-����Main_Set�ӽڵ㡰��ͨ�û����롱����ֵ
	        Main_Set.addContent(new Element("User_Password").setText("aodi"));

	        //@8-����Main_Set�ӽڵ㡰������������ֵ
	        Main_Set.addContent(new Element("NetCard_Name").setText("Realtek PCIe GBE Family Controller"));

	        //@9-����Main_Set�ӽڵ㡰IP��ַ������ֵ
	        Main_Set.addContent(new Element("IP_Add1").setText("192"));
	        Main_Set.addContent(new Element("IP_Add2").setText("168"));
	        Main_Set.addContent(new Element("IP_Add3").setText("0"));
	        Main_Set.addContent(new Element("IP_Add4").setText("103"));

	        //@10-�ӽڵ���ӵ����ڵ���;
	        root.addContent(Main_Set);

	        //@11-�����û��Զ��������ļ�
	        if(Mode==2)
	        {
	        	Save_Config(root);
	        }

	        //@12-����Ŀ�������ļ�
	        XMLOutputter XMLOut = new XMLOutputter();
		    //@13-��� xml �ļ���
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

        //@5-��ó����û���
        Super_User = root.getChild("Main_Set").getChild("Super_User").getText();
        //@6-��ó����û�������
        Super_User_Password = root.getChild("Main_Set").getChild("Super_User_Password").getText();

        //@7-�����ͨ�û���
        Super_User = root.getChild("Main_Set").getChild("User").getText();
        //@8-�����ͨ�û�������
        Super_User_Password = root.getChild("Main_Set").getChild("User_Password").getText();

        //@9-���������
        NetCard_Name = root.getChild("Main_Set").getChild("NetCard_Name").getText();

        //@10-���������
        Config_NetIP = new String (root.getChild("Main_Set").getChild("IP_Add1").getText()+"."+
        						   root.getChild("Main_Set").getChild("IP_Add2").getText()+"."+
        						   root.getChild("Main_Set").getChild("IP_Add3").getText()+"."+
        						   root.getChild("Main_Set").getChild("IP_Add4").getText());

	}

	/**
	 * ����������Ŀ����
	 */
	private void Save_Config(Element root)
	{
//        //@4-����Voltage�ӽڵ㡰��ʼ��ѹ��
//        root.getChild("Voltage").getChild("Def_VoltageStart").setText(""+StrategyControllerNew.Temp_VoltageStart);
//        //@5-����Voltage�ӽڵ㡰��ֹ��ѹ��
//        root.getChild("Voltage").getChild("Def_VoltageEnd").setText(""+StrategyControllerNew.Temp_VoltageEnd);
//        //@5-����Voltage�ӽڵ㡰��ֹ��ѹ��ʱ��
//        root.getChild("Voltage").getChild("Def_VoltageEnd_Delay").setText(""+StrategyControllerNew.Temp_VoltageEnd_Delay);
//        //@6-����Voltage�ӽڵ㡰������ѹ��
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
