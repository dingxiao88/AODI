package AODI_QC;


import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;



public class Save_Data{

	private static String SaveData_Format ="\t\t";
	private static String LeaveData_Format ="\t\t";

	private int LeaveData_Num_copy;

	private byte read_flag=0x01;  //读取数据库文件正常标志 0x00:没有读取到  0x01:读取到数据库文件

	private float read_total_Line=0;  //读取文件总行数
	private float Pro_Line_Count=0;   //正在处理数据的行数
	private float Per_Pro_Line=0;     //正在处理数据的百分比

	public static String Rec_Save_FileName = new String("aodi.save");
	public static String Rec_Save_RecFileName;


	public static boolean runflag=true;


	/**
	 *
	 */
	public void Sava_Data()
	{

	}


	//20121108-新方法是用list
	//@1-生成并保存 SaveData 对象  生成记录文件SaveDb.txt--------------------
    public void createAndSave() throws IOException {

        List<AODI_Data> persons = createSaveData();

        //使用当前系统时间为文件名进行数据记录
//        if(Net_Main_Receive.Save_NewName_Flag==true)
//        {
//        	//新文件名
//        	Rec_Save_RecFileName= new String(""+Rec_DisplayTimer.Time_Str_Save);
//        	Rec_Save_FileName=new String("/home/pi/Rec/Save/"+Rec_Save_RecFileName);
//
//        	//权限关闭
//        	Net_Main_Receive.Save_NewName_Flag=false;
//        }


//        //@-判断存储文件是否存在
//		File f = new File(Net_Main_Receive.Save_Data_FileName);
//		if((f.exists())==false)
//		{
//
//		}

        savedata(persons);
    }


    //@2-创建要保存的 SaveData 对象
    private  List<AODI_Data> createSaveData() {

        List<AODI_Data> result = new ArrayList<AODI_Data>();

        result.add(new AODI_Data(

//        		@param Data1-序号
//                * @param Data2-产品ID
//                * @param Data3-老化起始时间
//                * @param Data4-老化采样-电压
//                * @param Data5-老化采样-电流
//                * @param Data6-老化采样-温度1
//                * @param Data7-老化采样-温度2
//                * @param Data8-老化采样-温度3
//                * @param Data9-老化采样-采样时间
//                * @param Data10-老化产品-故障码
//                * @param Data11-老化产品-状态

        		Net_Main_Receive.Save_Data_Num,
        		Net_Main_Receive.Save_Data_PudID,
        		Net_Main_Receive.Save_Data_TestStartTime,
        		Net_Main_Receive.Save_Data_Voltage,
        		Net_Main_Receive.Save_Data_Current,
        		Net_Main_Receive.Save_Data_Temperature1,
        		Net_Main_Receive.Save_Data_Temperature2,
        		Net_Main_Receive.Save_Data_Temperature3,
        		Net_Main_Receive.Save_Data_TestScanTime,
        		Net_Main_Receive.Save_Data_ErrorID,
        		Net_Main_Receive.Save_Data_TestStatus

        		));

         return result;
    }

    //@3-保存 SaveData 对象到文件。保存格式为：
    // 1、每个 SaveData 一行
    // 2、每行依次存放 name、birthday、male 三个属性值，用 tab 隔开
    // 3、birthday 属性保存的是毫秒数，male 属性保存的是字符串
    private  void savedata(List<AODI_Data> indata) throws IOException {

        // 生成文件内容
        String data = "";
        for (AODI_Data SaveData1 : indata) {
            data += getSaveDataString(SaveData1) + "\n";
        }


        //@-判断存储文件是否存在
		File f = new File(ScreensFramework.MAIN_SaveData_TodayPath+Net_Main_Receive.Save_Data_FileName);
		//System.out.println(""+f.getName());
        FileWriter writer;
		try {
			writer = new FileWriter(f,true);
	        writer.write(data);
	        writer.flush();
	        writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//        // 保存文件内容  (filewriter)
//        FileWriter writer = new FileWriter(Net_Main_Receive.Save_Data_FileName,true);
//        writer.write(data);
//        writer.flush();
//        writer.close();

        // 保存文件内容  (BufferedWriter)
//        try {
//        	 BufferedWriter out = new BufferedWriter(new FileWriter(Rec_Save_FileName,true),64);
//             out.write(data);
//             out.flush();
//             out.close();
//        } catch (IOException e) {
//        }


        //保存文件内容  (NIO)
//		byte [] inputBytes = data.getBytes();
//		ByteBuffer buffer = ByteBuffer.wrap(inputBytes);
//		FileOutputStream fos = new FileOutputStream(Rec_Save_FileName,true);
//		FileChannel fileChannel = fos.getChannel();
//		fileChannel.write(buffer);
//		fileChannel.close();
//		fos.close();

    }




    /**
     *
     * @param InData
     * @return
     */
    private  String getSaveDataString(AODI_Data InData) {
//        return (
//        		//FY
//        		InData.get_FY_InputAngel() + SaveData_Format + InData.get_FY_RealAngel()+ SaveData_Format + InData.get_FY_ErrorAngel()+ SaveData_Format + InData.get_FY_VMot()
//        		+ SaveData_Format + InData.get_FY_IMot()+ SaveData_Format + InData.get_FY_VField()+ SaveData_Format + InData.get_FY_IField()+ SaveData_Format + InData.get_FY_VDCBus()
//        				+ SaveData_Format +
//                //XH
//        		InData.get_XH_InputAngel() + SaveData_Format + InData.get_XH_RealAngel()+ SaveData_Format + InData.get_XH_ErrorAngel()+ SaveData_Format + InData.get_XH_VMot()
//        		+ SaveData_Format + InData.get_XH_IMot()+ SaveData_Format + InData.get_XH_VField()+ SaveData_Format + InData.get_XH_IField()+ SaveData_Format + InData.get_XH_VDCBus()
//        );


      return (
        InData.get_Data_Num_Str() + SaveData_Format + InData.get_Data_PudID_Str() + SaveData_Format +
        InData.get_Data_TestStartTime_Str() + SaveData_Format + InData.get_Data_Voltage_Str() + SaveData_Format + InData.get_Data_Current_Str() + SaveData_Format +
        InData.get_Data_Temperature1_Str() + SaveData_Format + InData.get_Data_Temperature2_Str() + SaveData_Format + InData.get_Data_Temperature3_Str() + SaveData_Format +
        InData.get_Data_TestScanTime_Str() + SaveData_Format + InData.get_Data_ErrorID_Str()+ SaveData_Format + InData.get_Data_TestStatus_Str());

    }
    //----------Save End----------------------


    /**
     * 采用BufferedInputStream方式读取文件行数
     *
     * @param filename
     * @return
     * @throws IOException
     */
    public int total_line_count(String filename) throws IOException {
        InputStream is = new BufferedInputStream(new FileInputStream(filename));
        byte[] c = new byte[1024];
        int count = 0;
        int readChars = 0;
        while ((readChars = is.read(c)) != -1) {
            for (int i = 0; i < readChars; ++i) {
                if (c[i] == '\n')
                    ++count;
            }
        }
        is.close();
        return count;
    }



}
