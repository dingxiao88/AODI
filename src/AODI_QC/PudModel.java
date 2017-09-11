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
public class PudModel {

	//@1-Ĭ�������ļ���
	public static String Def_PudModel_FileName = new String("./AODI_PudModel.config");

	//@2-��Ʒ�ͺ�����
	public static int PudModel_Num=1;

	//@3-
	public static int PudModel_MaxCount = 1000;

	//@4-��Ʒ�ͺŻ���-5000���ͺ�
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

	/**�����ļ�����
	 *
	 */
	public PudModel()
	{
		AODI_PudModel_Init();

		//@1-���Ĭ��Config�ļ�
		if(!(HaveFile(Def_PudModel_FileName)))
		{
			//@2-��ʧĬ��Config�ļ��贴��
			CreateSave_Config(Def_PudModel_FileName,false);   //������ʼconfig
		}

		//@-����Ĭ��Config�ļ�
		Load_Config(Def_PudModel_FileName);
	}

	/**Mode 1:����Ĭ�������ļ� 2:�����û��Զ����ļ�
	 * ������ʼxml�ļ�
	 */
	public int CreateSave_Config(String FileName,boolean isload)
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
	        Element root = new Element("PudModel");
	        //@2-���ڵ���ӵ��ĵ��У�
	        Document Doc = new Document(root);

	        //@3-�����ڵ� Main_Set;
	        Element PudModel_Set = new Element("PudModel_Set");

	        //@4-����PudModel_Set�ӽڵ㡰��Ʒ�ͺ�����������ֵ
	        PudModel_Set.addContent(new Element("PudModel_Num").setText("1"));

	        //@4-����PudModel_Set�ӽڵ㡰�ͺ�-2415������ֵ
	        PudModel_Set.addContent(new Element("PudModel1_Name").setText("2415"));

	        //@4-����PudModel_Set�ӽڵ㡰�ͺ�-2415��-��ѹ���Ʋ���ֵ
	        PudModel_Set.addContent(new Element("PudModel1_VoltageLimit").setText("29"));
	        //@4-����PudModel_Set�ӽڵ㡰�ͺ�-2415��-��ѹ���Ʒ�Χ����ֵ
	        PudModel_Set.addContent(new Element("PudModel1_VoltageLimit_Range").setText("1"));

	        //@4-����PudModel_Set�ӽڵ㡰�ͺ�-2415��-�������Ʋ���ֵ
	        PudModel_Set.addContent(new Element("PudModel1_CurrentLimit").setText("14.5"));
	        //@4-����PudModel_Set�ӽڵ㡰�ͺ�-2415��-�������Ʒ�Χ����ֵ
	        PudModel_Set.addContent(new Element("PudModel1_CurrentLimit_Range").setText("0.5"));

	        //@4-����PudModel_Set�ӽڵ㡰�ͺ�-2415��-�¶�1���Ʋ���ֵ
	        PudModel_Set.addContent(new Element("PudModel1_Temperature1Limit").setText("60"));
	        //@4-����PudModel_Set�ӽڵ㡰�ͺ�-2415��-�¶�1���Ʒ�Χ����ֵ
	        PudModel_Set.addContent(new Element("PudModel1_Temperature1Limit_Range").setText("10"));

	        //@4-����PudModel_Set�ӽڵ㡰�ͺ�-2415��-�¶�2���Ʋ���ֵ
	        PudModel_Set.addContent(new Element("PudModel1_Temperature2Limit").setText("60"));
	        //@4-����PudModel_Set�ӽڵ㡰�ͺ�-2415��-�¶�2���Ʒ�Χ����ֵ
	        PudModel_Set.addContent(new Element("PudModel1_Temperature2Limit_Range").setText("10"));

	        //@4-����PudModel_Set�ӽڵ㡰�ͺ�-2415��-�¶�3���Ʋ���
	        PudModel_Set.addContent(new Element("PudModel1_Temperature3Limit").setText("70"));
	        //@4-����PudModel_Set�ӽڵ㡰�ͺ�-2415��-�¶�3���Ʒ�Χ����ֵ
	        PudModel_Set.addContent(new Element("PudModel1_Temperature3Limit_Range").setText("10"));

	        //@10-�ӽڵ���ӵ����ڵ���;
	        root.addContent(PudModel_Set);

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

        //@5-��ò�Ʒ�ͺ�����
        PudModel_Num = Integer.valueOf(root.getChild("PudModel_Set").getChild("PudModel_Num").getText());

        //@6-���ݲ�Ʒ�ͺ��������ز�Ʒ�ͺŲ�����
        for(int i=0;i<PudModel_Num;i++)
        {
        	//@-��ȡ��Ʒ�ͺ���PudModel1_Name
        	AODI_PudModel[i].Set_PudModel_Name(root.getChild("PudModel_Set").getChild("PudModel"+(i+1)+"_Name").getText());

        	//@-��ȡ��Ʒ�ͺ�-��ѹ����ֵ
        	AODI_PudModel[i].Set_PudModel_VoltageLimit(Float.valueOf(root.getChild("PudModel_Set").getChild("PudModel"+(i+1)+"_VoltageLimit").getText()));
        	//@-��ȡ��Ʒ�ͺ�-��ѹ����ֵ��Χ
        	AODI_PudModel[i].Set_PudModel_VoltageLimit_Range(Float.valueOf(root.getChild("PudModel_Set").getChild("PudModel"+(i+1)+"_VoltageLimit_Range").getText()));

        	//@-��ȡ��Ʒ�ͺ�-��������ֵ
        	AODI_PudModel[i].Set_PudModel_CurrentLimit(Float.valueOf(root.getChild("PudModel_Set").getChild("PudModel"+(i+1)+"_CurrentLimit").getText()));
        	//@-��ȡ��Ʒ�ͺ�-��������ֵ��Χ
        	AODI_PudModel[i].Set_PudModel_CurrentLimit_Range(Float.valueOf(root.getChild("PudModel_Set").getChild("PudModel"+(i+1)+"_CurrentLimit_Range").getText()));

        	//@-��ȡ��Ʒ�ͺ�-�¶�1����ֵ
        	AODI_PudModel[i].Set_PudModel_Temperature1Limit(Float.valueOf(root.getChild("PudModel_Set").getChild("PudModel"+(i+1)+"_Temperature1Limit").getText()));
        	//@-��ȡ��Ʒ�ͺ�-�¶�1����ֵ��Χ
        	AODI_PudModel[i].Set_PudModel_Temperature1Limit_Range(Float.valueOf(root.getChild("PudModel_Set").getChild("PudModel"+(i+1)+"_Temperature1Limit_Range").getText()));

        	//@-��ȡ��Ʒ�ͺ�-�¶�2����ֵ
        	AODI_PudModel[i].Set_PudModel_Temperature2Limit(Float.valueOf(root.getChild("PudModel_Set").getChild("PudModel"+(i+1)+"_Temperature2Limit").getText()));
        	//@-��ȡ��Ʒ�ͺ�-�¶�2����ֵ��Χ
        	AODI_PudModel[i].Set_PudModel_Temperature2Limit_Range(Float.valueOf(root.getChild("PudModel_Set").getChild("PudModel"+(i+1)+"_Temperature2Limit_Range").getText()));

        	//@-��ȡ��Ʒ�ͺ�-�¶�3����ֵ
        	AODI_PudModel[i].Set_PudModel_Temperature3Limit(Float.valueOf(root.getChild("PudModel_Set").getChild("PudModel"+(i+1)+"_Temperature3Limit").getText()));
        	//@-��ȡ��Ʒ�ͺ�-�¶�3����ֵ��Χ
        	AODI_PudModel[i].Set_PudModel_Temperature3Limit_Range(Float.valueOf(root.getChild("PudModel_Set").getChild("PudModel"+(i+1)+"_Temperature3Limit_Range").getText()));
        }

//        System.out.println("t1"+AODI_PudModel[0].Get_PudModel_Temperature2Limit());

	}

	/**
	 * ����������Ŀ����
	 */
	public boolean Save_Config()
	{
		boolean flag = false;

		//@1-�������ڵ� ROOT;
        Element root = new Element("PudModel");
        //@2-���ڵ���ӵ��ĵ��У�
        Document Doc = new Document(root);

        //@3-�����ڵ� Main_Set;
        Element PudModel_Set = new Element("PudModel_Set");

        int i = ScreensFramework.AODI_PubModel.PudModel_Num;

        //@4-����PudModel_Set�ӽڵ㡰��Ʒ�ͺ�����������ֵ
        PudModel_Set.addContent(new Element("PudModel_Num").setText(""+i));

        //@���ݲ�Ʒ�ͺ�����д�������ļ�
        for(int temp=0;temp<i;temp++)
        {
			//@4-����PudModel_Set�ӽڵ㡰�ͺš�����ֵ
			PudModel_Set.addContent(new Element("PudModel"+(temp+1)+"_Name").setText(""+ScreensFramework.AODI_PubModel.AODI_PudModel[temp].Get_PudModel_Name()));

			//@4-����PudModel_Set�ӽڵ㡰�ͺš�-��ѹ���Ʋ���ֵ
			PudModel_Set.addContent(new Element("PudModel"+(temp+1)+"_VoltageLimit").setText(""+ScreensFramework.AODI_PubModel.AODI_PudModel[temp].Get_PudModel_VoltageLimit()));
			//@4-����PudModel_Set�ӽڵ㡰�ͺš�-��ѹ���Ʒ�Χ����ֵ
			PudModel_Set.addContent(new Element("PudModel"+(temp+1)+"_VoltageLimit_Range").setText(""+ScreensFramework.AODI_PubModel.AODI_PudModel[temp].Get_PudModel_VoltageLimit_Range()));

			//@4-����PudModel_Set�ӽڵ㡰�ͺš�-�������Ʋ���ֵ
			PudModel_Set.addContent(new Element("PudModel"+(temp+1)+"_CurrentLimit").setText(""+ScreensFramework.AODI_PubModel.AODI_PudModel[temp].Get_PudModel_CurrentLimit()));
			//@4-����PudModel_Set�ӽڵ㡰�ͺš�-�������Ʒ�Χ����ֵ
			PudModel_Set.addContent(new Element("PudModel"+(temp+1)+"_CurrentLimit_Range").setText(""+ScreensFramework.AODI_PubModel.AODI_PudModel[temp].Get_PudModel_CurrentLimit_Range()));

			//@4-����PudModel_Set�ӽڵ㡰�ͺš�-�¶�1���Ʋ���ֵ
			PudModel_Set.addContent(new Element("PudModel"+(temp+1)+"_Temperature1Limit").setText(""+ScreensFramework.AODI_PubModel.AODI_PudModel[temp].Get_PudModel_Temperature1Limit()));
			//@4-����PudModel_Set�ӽڵ㡰�ͺš�-�¶�1���Ʒ�Χ����ֵ
			PudModel_Set.addContent(new Element("PudModel"+(temp+1)+"_Temperature1Limit_Range").setText(""+ScreensFramework.AODI_PubModel.AODI_PudModel[temp].Get_PudModel_Temperature1Limit_Range()));

			//@4-����PudModel_Set�ӽڵ㡰�ͺš�-�¶�2���Ʋ���ֵ
			PudModel_Set.addContent(new Element("PudModel"+(temp+1)+"_Temperature2Limit").setText(""+ScreensFramework.AODI_PubModel.AODI_PudModel[temp].Get_PudModel_Temperature2Limit()));
			//@4-����PudModel_Set�ӽڵ㡰�ͺš�-�¶�2���Ʒ�Χ����ֵ
			PudModel_Set.addContent(new Element("PudModel"+(temp+1)+"_Temperature2Limit_Range").setText(""+ScreensFramework.AODI_PubModel.AODI_PudModel[temp].Get_PudModel_Temperature2Limit_Range()));

			//@4-����PudModel_Set�ӽڵ㡰�ͺš�-�¶�3���Ʋ���
			PudModel_Set.addContent(new Element("PudModel"+(temp+1)+"_Temperature3Limit").setText(""+ScreensFramework.AODI_PubModel.AODI_PudModel[temp].Get_PudModel_Temperature3Limit()));
			//@4-����PudModel_Set�ӽڵ㡰�ͺš�-�¶�3���Ʒ�Χ����ֵ
			PudModel_Set.addContent(new Element("PudModel"+(temp+1)+"_Temperature3Limit_Range").setText(""+ScreensFramework.AODI_PubModel.AODI_PudModel[temp].Get_PudModel_Temperature3Limit_Range()));

        }



//        //@4-����PudModel_Set�ӽڵ㡰�ͺ�-2415������ֵ
//        PudModel_Set.addContent(new Element("PudModel1_Name").setText("2415"));
//
//        //@4-����PudModel_Set�ӽڵ㡰�ͺ�-2415��-��ѹ���Ʋ���ֵ
//        PudModel_Set.addContent(new Element("PudModel1_VoltageLimit").setText("29"));
//        //@4-����PudModel_Set�ӽڵ㡰�ͺ�-2415��-��ѹ���Ʒ�Χ����ֵ
//        PudModel_Set.addContent(new Element("PudModel1_VoltageLimit_Range").setText("1"));
//
//        //@4-����PudModel_Set�ӽڵ㡰�ͺ�-2415��-�������Ʋ���ֵ
//        PudModel_Set.addContent(new Element("PudModel1_CurrentLimit").setText("14.5"));
//        //@4-����PudModel_Set�ӽڵ㡰�ͺ�-2415��-�������Ʒ�Χ����ֵ
//        PudModel_Set.addContent(new Element("PudModel1_CurrentLimit_Range").setText("0.5"));
//
//        //@4-����PudModel_Set�ӽڵ㡰�ͺ�-2415��-�¶�1���Ʋ���ֵ
//        PudModel_Set.addContent(new Element("PudModel1_Temperature1Limit").setText("60"));
//        //@4-����PudModel_Set�ӽڵ㡰�ͺ�-2415��-�¶�1���Ʒ�Χ����ֵ
//        PudModel_Set.addContent(new Element("PudModel1_Temperature1Limit_Range").setText("10"));
//
//        //@4-����PudModel_Set�ӽڵ㡰�ͺ�-2415��-�¶�2���Ʋ���ֵ
//        PudModel_Set.addContent(new Element("PudModel1_Temperature2Limit").setText("60"));
//        //@4-����PudModel_Set�ӽڵ㡰�ͺ�-2415��-�¶�2���Ʒ�Χ����ֵ
//        PudModel_Set.addContent(new Element("PudModel1_Temperature2Limit_Range").setText("10"));
//
//        //@4-����PudModel_Set�ӽڵ㡰�ͺ�-2415��-�¶�3���Ʋ���
//        PudModel_Set.addContent(new Element("PudModel1_Temperature3Limit").setText("70"));
//        //@4-����PudModel_Set�ӽڵ㡰�ͺ�-2415��-�¶�3���Ʒ�Χ����ֵ
//        PudModel_Set.addContent(new Element("PudModel1_Temperature3Limit_Range").setText("10"));

        //@10-�ӽڵ���ӵ����ڵ���;
        root.addContent(PudModel_Set);

        //@12-����Ŀ�������ļ�
        XMLOutputter XMLOut = new XMLOutputter();
	    //@13-��� xml �ļ���
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
