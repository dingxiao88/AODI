package AODI_QC;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.SocketException;
import java.util.Arrays;
import java.util.Calendar;
import javafx.application.Platform;
import javax.swing.Timer;




/**
 *
 * @author Jack Ding
 *
 */
public class Net_Main_Receive extends  Timer implements ActionListener,Runnable{

	//@1-网络接收接口、缓存
	public  Thread Network_Receive_thread=new Thread(this);      //网络接收线程
    private static byte[] receive_buff_Udp = new byte[128];      //网络接收缓存128Byte
    private static byte[] receive_buff_Cast = new byte[128];     //网络接收缓存128Byte

    //@2-网络接口
    private int cPort_Udp;
    private int cPort_Cast;
    public  long temp;  //接收数据包计数
    private String ClientIP_Udp=null;
    private String ClientIP_Cast=null;
    private String ClientPort=null;
    private String ClientData=null;
    private int Recv_Len_Udp;   //网络接收数据包的长度
    private int Recv_Len_Cast;  //网络接收数据包的长度

    //@3-协议解包
    public static long Net_Recv_PCB_Count=0;          //PCB接收计数
    public static long Net_Recv_PCB_Count_Copy=0;     //PCB Copy接收计数
    public static boolean Net_PCB_State=true;        //PCB状态

    //@4-网络数据段
    public static int    Net_Recv_CMD=0;
    public static int    Net_Recv_CMD_Copy=0;
    public static byte[] Net_Recv_Data = new byte[19];
    public static byte   Net_Recv_Key=0x00;
    public static boolean  Net_Recv_KeyLock=false;
    public static int Net_Recv_RecordCMD_Count=0;
    public static String  Net_Recv_CMD_Str;

    //@5-CAN数据
    public static byte   Net_Recv_CAN_Start;
    public static byte[] Net_Recv_CAN_ID = new byte[4];
    public static byte   Net_Recv_CAN_CMD;
    public static byte   Net_Recv_CAN_Config_ID;
    public static byte   Net_Recv_CAN_Config_State;
    public static byte[] Net_Recv_CAN_Data = new byte[7];
    public static byte[] Net_Recv_CAN_ID_Error = new byte[2];
    public static byte   Net_Recv_CAN_ErrorID = 0x00;

    //@6-接收的物理量
    public static float  Net_Recv_Voltage;
    public static float  Net_Recv_Current;
    public static float  Net_Recv_Temperature1;
    public static float  Net_Recv_Temperature2;
    public static float  Net_Recv_Temperature3;

	//@7-数据保存对象
	public static Save_Data Save_Obj = new Save_Data();
	//@7-数据保存标志
	public static boolean Save_Flag=false;

    //@1-需要保存数据的Group位置
	public static  int Save_Data_Group;
    //@1-需要保存数据的Group位置中的Node位置
	public static  int Save_Data_Node;

    //@1-序号
	public static String  Save_Data_Num;
    //@2-产品ID
	public static String  Save_Data_PudID;
	//@3-产品型号
	public static String  Save_Data_PudModel;
    //@4-产品老化起始时间
	public static String  Save_Data_TestStartTime;
    //@5-老化产品采样-电压
	public static String  Save_Data_Voltage;
    //@6-老化产品采样-电流
	public static String  Save_Data_Current;
    //@7-老化产品采样-温度1
	public static String  Save_Data_Temperature1;
    //@8-老化产品采样-温度2
	public static String  Save_Data_Temperature2;
    //@9-老化产品采样-温度3
	public static String  Save_Data_Temperature3;
    //@10-老化产品采样-采样时间
	public static String  Save_Data_TestScanTime;
    //@11-老化产品-故障码
	public static String  Save_Data_ErrorID;
    //@12-老化产品-状态
	public static String  Save_Data_TestStatus;

	//@-数据保存文件名
//	public static String Save_Data_FileName = new String("AODI_4567_Group1Node1.aodi_save");
	public static String Save_Data_FileName;


    /**
     *
     * @param arg0
     * @param arg1
     */
	public Net_Main_Receive(int interval) {
		super(interval,  null);
	}

	/**
	 *
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

		//设置UDP Socket超时
		try {
			Net_Main.UDP_socket.setSoTimeout(100);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}

	/** 接收开关
	 *
	 */
	public void receive_on_off(boolean on_off){

		if(on_off==true)
		{
		 Network_Receive_thread=new Thread(this);  //网络接收线程
		 //Network_Receive_thread.setDaemon(true);   //设置为后台线程
		 Network_Receive_thread.setName("Net-Receive");
		 Network_Receive_thread.setPriority(7);    //设置优先级别6
		 Network_Receive_thread.start();
		}
		else
		 Network_Receive_thread=null;
	}

	/**
	 *
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		if(Net_Main.sendmode==0)  //点播
		{
			if(Net_Main.isOpen==true)  //网络连接正常
			{
				while(Net_Main.isClose==false)  //只要没有要求关闭，一直接收
				{


						/**
						 *   add Action
						 */

				}
			}
		}
		else if(Net_Main.sendmode==1)  //组播
		{
			while(Net_Main.isOpen==true)  //网络连接正常
			{
				//@1-组播接收
				try {

					Net_Main.Multicast_socket.receive(Net_Main.Multicast_Packet);

					ClientIP_Cast=Net_Main.Multicast_Packet.getAddress().getHostAddress();
					cPort_Cast=Net_Main.Multicast_Packet.getPort();
					Recv_Len_Cast=Net_Main.Multicast_Packet.getLength();
					receive_buff_Cast=Net_Main.Multicast_Packet.getData();

					//System.out.println("HB2"+ClientIP_Cast);
					//@2-接收PCB板信息
					if(ClientIP_Cast.equals(Net_Main.Set_PCB_IP))
					{
						if(cPort_Cast==Net_Main.Set_Port)
						{
							if(Recv_Len_Cast==13)  //协议接收数据长度
							{
								//@3-接收计数增
								Net_Recv_PCB_Count=Net_Recv_PCB_Count+1;

								//System.out.println(""+Net_Recv_PCB_Count);

								//@4-PCB状态正常，才解包
								if(Net_PCB_State==true)
								{
									//@-CAN数据段头
									Net_Recv_CAN_Start = receive_buff_Cast[0];
									//@-CAN数据段ID
									Net_Recv_CAN_ID[0] = receive_buff_Cast[1];
									Net_Recv_CAN_ID[1] = receive_buff_Cast[2];
									Net_Recv_CAN_ID[2] = receive_buff_Cast[3];
									Net_Recv_CAN_ID[3] = receive_buff_Cast[4];

									//@-CAN数据段命令
									Net_Recv_CAN_CMD = receive_buff_Cast[5];

									//@-CAN数据段数据
									Net_Recv_CAN_Data[0] = receive_buff_Cast[6];
									Net_Recv_CAN_Data[1] = receive_buff_Cast[7];
									Net_Recv_CAN_Data[2] = receive_buff_Cast[8];
									Net_Recv_CAN_Data[3] = receive_buff_Cast[9];
									Net_Recv_CAN_Data[4] = receive_buff_Cast[10];
									Net_Recv_CAN_Data[5] = receive_buff_Cast[11];
									Net_Recv_CAN_Data[6] = receive_buff_Cast[12];


									//@-接收配置报文-ID&State(复用CAN数据段命令)
									Net_Recv_CAN_Config_ID =  receive_buff_Cast[5];
									Net_Recv_CAN_Config_State =  receive_buff_Cast[6];

									//@-接收故障报文-故障CANID(复用CAN数据段命令+数据)
									Net_Recv_CAN_ID_Error[0] = receive_buff_Cast[5];
									Net_Recv_CAN_ID_Error[1] = receive_buff_Cast[6];
									Net_Recv_CAN_ErrorID = receive_buff_Cast[7];


									//System.out.println("["+Net_Recv_CAN_ID[0]+"]"+"["+Net_Recv_CAN_ID[1]+"]"+"["+Net_Recv_CAN_ID[2]+"]"+"["+Net_Recv_CAN_ID[3]+"]");


									//@5-数据段解包
									if(Net_Recv_CAN_Start==(byte)0xD2)
									{

										//@DX1-接收要与发送同步判断，CAN_SendCMD_Count

										//@-判断已开始扫描CAN_NodeMap中有运行的节点
										if(QC_DisplayTimer.CAN_NodeMap_Have==true)
										{

											//System.out.println("["+QC_DisplayTimer.CAN_Syn_ID[0]+"]"+"["+QC_DisplayTimer.CAN_Syn_ID[1]+"]"+"["+QC_DisplayTimer.CAN_Syn_ID[2]+"]"+"["+QC_DisplayTimer.CAN_Syn_ID[3]+"]");

											//@-同步CAN发送ID
											if((Net_Recv_CAN_ID[0]==QC_DisplayTimer.CAN_Syn_ID[0])&&(Net_Recv_CAN_ID[1]==QC_DisplayTimer.CAN_Syn_ID[1])&&
											   (Net_Recv_CAN_ID[2]==QC_DisplayTimer.CAN_Syn_ID[2])&&(Net_Recv_CAN_ID[3]==QC_DisplayTimer.CAN_Syn_ID[3]))
											{
												//@-保存数据储存的Group和Node位置
								                Save_Data_Group = QC_DisplayTimer.CAN_Syn_ID[2] - 1;
								        		Save_Data_Node =  QC_DisplayTimer.CAN_Syn_ID[3] - 1;

												//@-电压、电流数据
												if(Net_Recv_CAN_CMD==0x02)
												{
													//@-电压
													int temp=(int)(((Net_Recv_CAN_Data[0]&0xff)*256)+(Net_Recv_CAN_Data[1]&0xff));
													//System.out.println("AD0:"+temp);
													Net_Recv_Voltage = (float)(temp/(91)*5);  //Group1Node5
													//Net_Recv_Voltage = (float)(temp/(91));  //Group1Node6
													//@-电流
									            	temp=(int)(((Net_Recv_CAN_Data[2]&0xff)*256)+(Net_Recv_CAN_Data[3]&0xff));
													Net_Recv_Current = (float)((2500-(temp*0.4))*0.03899999);

													//@-接收到电压、电流数据-切换到请求温度命令
													QC_DisplayTimer.CAN_SendCMD_Num=3;
													//@-发送计数清零
													QC_DisplayTimer.CAN_SendCMD_Count=0;

													//System.out.println("CMD==3");

													//System.out.println("["+Net_Recv_CAN_Data[0]+"]"+"["+Net_Recv_CAN_Data[1]+"]"+"["+Net_Recv_CAN_Data[2]+"]"+"["+Net_Recv_CAN_Data[3]+"]");
												}
												//@-温度数据
												else if(Net_Recv_CAN_CMD==0x03)
												{
													//@-温度1
													Net_Recv_Temperature1 = (float)((((Net_Recv_CAN_Data[0]&0xff)*256)+(Net_Recv_CAN_Data[1]&0xff))*0.1);
													//@-温度2
													Net_Recv_Temperature2 = (float)((((Net_Recv_CAN_Data[2]&0xff)*256)+(Net_Recv_CAN_Data[3]&0xff))*0.1);
													//@-温度3
													Net_Recv_Temperature3 = (float)((((Net_Recv_CAN_Data[4]&0xff)*256)+(Net_Recv_CAN_Data[5]&0xff))*0.1);

													//@-保存数据
													Save_Flag = true;
												}

												//@-保存数据
												if(Save_Flag==true)
												{

													//@-该节点收到采集数据计数增1
													ScreensFramework.DZFZ_Group[Save_Data_Group].Group_DZFZ[Save_Data_Node].Set_TestData_Count_Add();

									                //@-获得采集数据序号
													Save_Data_Num = new String(""+ScreensFramework.DZFZ_Group[Save_Data_Group].Group_DZFZ[Save_Data_Node].Get_TestData_Count());
									                //@-获得产品ID
									        		Save_Data_PudID = ScreensFramework.DZFZ_Group[Save_Data_Group].Group_DZFZ[Save_Data_Node].Get_Pud_ID();
									        		//@-获得产品型号
									        		Save_Data_PudModel  = ScreensFramework.DZFZ_Group[Save_Data_Group].Group_DZFZ[Save_Data_Node].Get_Pud_Test_PudModel();
									                //@-获得产品老化起始时间
									        		Save_Data_TestStartTime = ScreensFramework.DZFZ_Group[Save_Data_Group].Group_DZFZ[Save_Data_Node].Get_Pud_Test_Start_Time();
									                //@-获得产品采样-电压
									        		Save_Data_Voltage = new String(""+Net_Recv_Voltage);
									                //@-获得产品采样-电流
									        		Save_Data_Current = new String(""+Net_Recv_Current);
									                //@-获得产品采样-温度1
									        		Save_Data_Temperature1 = new String(""+Net_Recv_Temperature1);
									                //@-获得产品采样-温度2
									        		Save_Data_Temperature2 = new String(""+Net_Recv_Temperature2);
									                //@-获得产品采样-温度3
									        		Save_Data_Temperature3 = new String(""+Net_Recv_Temperature3);
									                //@-获得产品采样-采样时间
									        		Save_Data_TestScanTime = new String(QC_DisplayTimer.Time_Str1);
									                //@-获得产品采样-故障码(离组原因)
									        		Save_Data_ErrorID = new String(""+ScreensFramework.DZFZ_Group[Save_Data_Group].Group_DZFZ[Save_Data_Node].Get_Pud_LeaveReason());
									                //@-获得产品采样-状态
									        		Save_Data_TestStatus = new String(""+ScreensFramework.DZFZ_Group[Save_Data_Group].Group_DZFZ[Save_Data_Node].Get_Pud_Test_Status());

									        		//@-保存数据到节点-电压
									        		ScreensFramework.DZFZ_Group[Save_Data_Group].Group_DZFZ[Save_Data_Node].Set_Pud_Test_ScanVoltage(Net_Recv_Voltage);
									        		//@-保存数据到节点-电流
									        		ScreensFramework.DZFZ_Group[Save_Data_Group].Group_DZFZ[Save_Data_Node].Set_Pud_Test_ScanCurrent(Net_Recv_Current);
									        		//@-保存数据到节点-温度1
									        		ScreensFramework.DZFZ_Group[Save_Data_Group].Group_DZFZ[Save_Data_Node].Set_Pud_Test_ScanTemputer1(Net_Recv_Temperature1);
									        		//@-保存数据到节点-温度2
									        		ScreensFramework.DZFZ_Group[Save_Data_Group].Group_DZFZ[Save_Data_Node].Set_Pud_Test_ScanTemputer2(Net_Recv_Temperature2);
									        		//@-保存数据到节点-温度3
									        		ScreensFramework.DZFZ_Group[Save_Data_Group].Group_DZFZ[Save_Data_Node].Set_Pud_Test_ScanTemputer3(Net_Recv_Temperature3);
									        		//@-保存数据到节点-采样时间
									        		ScreensFramework.DZFZ_Group[Save_Data_Group].Group_DZFZ[Save_Data_Node].Set_Pud_Test_Scan_Time(Save_Data_TestScanTime);

													//@DX1-保存数据的文件名-每个产品独立数据存储文件-|AODI_产品ID_产品型号_Group(Save_Data_Group)Node(Save_Data_Node).aodi_save|
													Save_Data_FileName = new String("AODI_"+Save_Data_PudID+"_"+Save_Data_PudModel+"_Group"+(Save_Data_Group+1)+"Node"+(Save_Data_Node+1)+".aodi_save");

													//System.out.println(""+Save_Data_FileName);

													//@-保存数据
										    	    try {
										    			Save_Obj.createAndSave();
										    		} catch (IOException e) {
										    			// TODO Auto-generated catch block
										    			e.printStackTrace();
										    		}

													//@-CAN_ID切换下个节点
													QC_DisplayTimer.CAN_ID_Switch=true;
													//@-发送次数清零
													QC_DisplayTimer.CAN_SendCMD_Count=0;
													//@-发送CAN命令切换初始值
													QC_DisplayTimer.CAN_SendCMD_Num=2;

													//System.out.println("time:"+QC_DisplayTimer.Time_Str1);

										    	    Save_Flag = false;
												}

											}

										}


										//@-判断配置回复报文特殊CAN_ID(0000aaaa)-配置回复确认
										if((Net_Recv_CAN_ID[0]==(byte)0x00)&&(Net_Recv_CAN_ID[1]==(byte)0x00)&&
										   (Net_Recv_CAN_ID[2]==(byte)0xaa)&&(Net_Recv_CAN_ID[3]==(byte)0xaa))
										{
											//System.out.println("device config!");
											//@-接收到配置报文回馈-温度
											if(Net_Recv_CAN_Config_State==(byte)0xaa)
											{
												//@-发送电压、电流配置报文
												QC_DisplayTimer.CAN_Config_CMD = 2;
												//@-配置报文计数清零
												QC_DisplayTimer.CAN_SendConfig_Count = 0;
											}
											//@-接收到配置报文回馈-电压、电流
											else if(Net_Recv_CAN_Config_State==(byte)0xbb)
											{
												//@-配置节点完成配置
												ScreensFramework.DZFZ_Group[(byte)(((Net_Recv_CAN_Config_ID&0xf0)>>4)-1)].Group_DZFZ[(byte)((Net_Recv_CAN_Config_ID&0x0f)-1)].Set_Pud_Test_Status(1);
												//@-发送温度配置报文
												QC_DisplayTimer.CAN_Config_CMD = 1;
												//@-配置报文计数清零
												QC_DisplayTimer.CAN_SendConfig_Count = 0;
											}
											//@-接收到配置报文回馈-查询状态已更新
											else if(Net_Recv_CAN_Config_State==(byte)0xcc)
											{

											}

										}
										//@-判断故障告警报文特殊CAN_ID(0000bbbb)-故障告警
										else if((Net_Recv_CAN_ID[0]==(byte)0x00)&&(Net_Recv_CAN_ID[1]==(byte)0x00)&&
										   (Net_Recv_CAN_ID[2]==(byte)0xbb)&&(Net_Recv_CAN_ID[3]==(byte)0xbb))
										{
											System.out.println("Error-Group:"+Net_Recv_CAN_ID_Error[0]+" Error-Node:"+Net_Recv_CAN_ID_Error[1]);

											System.out.println("ErrorID:"+Net_Recv_CAN_ErrorID);

											//Net_Recv_CAN_ErrorID
										}

										//@-判断心跳报文特殊CAN_ID(0d0d0d0d)-PCB心跳
										else if((Net_Recv_CAN_ID[0]==(byte)0x0d)&&(Net_Recv_CAN_ID[1]==(byte)0x0d)&&
										   (Net_Recv_CAN_ID[2]==(byte)0x0d)&&(Net_Recv_CAN_ID[3]==(byte)0x0d))
										{
											//System.out.println("Recv H");
										}


									}

								}


							}
						}
					}

					//System.out.println("receive ok!"+ClientIP+cPort+"/n");

				} catch (IOException e) {

				}
			}
		}
	}






}

