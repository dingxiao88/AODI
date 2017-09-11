package AODI_QC;


import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.util.Duration;



//@DX 为软件后期需要优化处

/**
 *
 * @author Jack Ding
 * @date 2011-01-13
 */
public class QC_DisplayTimer{

	//@1-非FX原生定时方法(task)
	public static Task<Integer> task_dis;
	//@2-显示线程
    private Thread Display_Thread;

	//@3-系统计时
	private double system_tick=0;
	//@4-系统tick计算值
    private int tick_count=0;
	//@5-1秒计时
	private int second_1s_tick=0;
	//@5-5秒计时
	private int second_5s_tick=0;
	//@5-采样等级定时可变计时
	private int second_net_send_tick=0;
	//@5-主界面定时可变计时
	private int display_tick=0;

    //@6-系统时间获取接口
	private static Calendar local_time;
	//@7-显示数据格式
	public static java.text.NumberFormat  formater_value  =  java.text.DecimalFormat.getInstance();  //显示小数格式化
	//@8-时间信息显示
	public static String Time_Str=new String("----");
	public static String Time_Str1=new String("----");

    //@9-主界面刷新标志
    public static boolean flash_flag=false;

    //@10-检测NET_CAN板计时(1Min检测一次)
	private static int Second_PCB_Tick = 0;


    //-------------------CAN相关------------------------
	//@-CAN_Debug
	private int CAN_Debug = 1;
    //@-CAN_CMD
	public static byte   CAN_Start = (byte)0xD2;
	public static byte[] CAN_ID = {(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00};
	public static byte[] CAN_Syn_ID = {(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00};
	public static byte   CAN_CMD = (byte)0x00;
	public static byte[] CAN_Data = {(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00};
	//@-CAN动态运行表
	public static byte[] CAN_NodeMap = new byte[100];  //0x00:不参与运行  0x01:参与运行
	//@-CAN_ID切换下一个节点开关
	public static boolean CAN_ID_Switch = true;
	//@-CAN_NodeMap中有运行的节点
	public static boolean CAN_NodeMap_Have = false;  //true:Map中有运行的节点   false:Map中没有运行的节点
	//@-CAN_ID命令发送Index
	public static int CAN_ID_Index = 0;
	//@-CAN_ID命令发送Index_Copy
	public static int CAN_ID_Index_Copy = 0;
	//@-CAN_ID命令发送命令最大重发数次数3
	public static int CAN_SendCMD_CountMax = 3;
	//@-CAN_ID命令发送命令当前次数
	public static int CAN_SendCMD_Count = 0;
	//@-CAN_ID命令发送命令编号
	public static int CAN_SendCMD_Num = 2;  //1:控制信息  2:请求相应CAN节点电压、电流参数   3:请求相应CAN节点温度参数  4:请求发送下一个CAN节点
	//@-CAN_ID命令发送心跳计数
	public static int CAN_SendHB_Count = 0;

	//@-CAN当前扫描节点Group
	public static int CAN_Scan_Group = 1;
	//@-CAN当前扫描节点Group的Node
	public static int CAN_Scan_Group_Node = 1;

	//@-CAN_配置Group
	public static int CAN_Config_Group = 0;
	//@-CAN_配置Node
	public static int CAN_Config_Node = 0;
	//@-CAN_配置命令
	public static int CAN_Config_CMD = 1;  //1:配置温度   2:电压、电流
	//@-CAN发送配置报文计数
	public static int CAN_SendConfig_Count = 0;
	//@-CAN发送配置报文(单一Node)最大值
	public static int CAN_SendConfig_MaxCount = 2;

	//@-软件信息输出编号
	public static int Info_Num = 0;  //0:没有错误信息
	//@-软件信息输出编号Copy
	public static int Info_Num_Copy = 0;   //0:没有错误信息

	//@-CAN_故障应急处理配置Group
	public static int CAN_AlarmPro_Group = 0;
	//@-CAN_故障应急处理配置Node
	public static int CAN_AlarmPro_Node = 0;
	//@-CAN发送故障应急处理报文标志
	public static boolean CAN_Send_AlarmPro_Flag = false;

	//@-CAN发送Group老化开始处理标志
	public static boolean[] CAN_Send_Group_TestPro_Flag = {false,false,false,false,false,false,false,false,false,false};
	//@-老化开始处理标志-D/A输出阶段
	public static boolean TestPro_DA_Flag = true;
	//@-老化开始处理标志-查询阶段
	public static boolean TestPro_Check_Flag = false;
	//@-老化开始处理Node
	public static int TestPro_Node = 1;
	//@-老化开始/停止-D/A输出
	public static int TestPro_Stage = 0;  //0:无操作  1:老化启动阶段  2:老化关闭阶段




	/**构建时间定时器
	 *
	 * @param delayTime1
	 */
	public QC_DisplayTimer(int delayTime){

		//@1-数据精度格式
		formater_value.setMaximumIntegerDigits(2);
		formater_value.setMinimumIntegerDigits(2);

		//@-CAN动态Map初始化
		for(int i=0;i<100;i++)
		CAN_NodeMap[i]=0x00;

		tick_count=1000/delayTime;

	    /*-------------使用FX原生task方法---------------------*/
	    task_dis = new Task<Integer>() {
	        @Override protected Integer call() throws Exception {
	            int iterations;

	            while (true)
	            {
	            	iterations=1;

	                if (isCancelled()) {
	                    updateMessage("Cancelled");
	                    break;
	                }

	                data_put();

	                //Block the thread for a short time, but be sure
	                //to check the InterruptedException for cancellation
	                try {
	                    Thread.sleep(delayTime);
	                } catch (InterruptedException interrupted) {
	                    if (isCancelled()) {
	                        updateMessage("Cancelled");
	                        break;
	                    }
	                }
	            }
	            return iterations;
	        }
	    };
	    Display_Thread=new Thread(task_dis);
	    Display_Thread.setName("display");
	    Display_Thread.setDaemon(true);
	    Display_Thread.setPriority(Thread.NORM_PRIORITY);    //设置优先级别8
	    Display_Thread.start();

	}


	/**显示刷新
	 *
	 */
	private void data_put()
	{
	    //@1-系统计时累加
		system_tick=system_tick+1;
		//@2-1秒计时累加
		second_1s_tick=second_1s_tick+1;
		//@3-采样等级计时累加
		second_net_send_tick = second_net_send_tick +1;
		//@3-主界面显示计时累加
		display_tick = display_tick +1;

		//@-网络后台发送触发-根据采样等级时间可变-暂定0.25s
		if(second_net_send_tick==(tick_count/4))
		{
			//@-采样等级时间可变
			second_net_send_tick = 0;

			//@-动态扫描CAN_NodeMap
			if(QC_Controller.Aodi_DataSample_Mode==0)
			{
				//@-恒定不变采样模式
				Scan_CAN_NodeMap_All();
			}
			else if(QC_Controller.Aodi_DataSample_Mode==1)
			{
				//@-可变采样模式
				Scan_CAN_NodeMap();
			}

			//@-判断CAN_NodeMap有无运行节点-有
			if(CAN_NodeMap_Have==true)
			{
				//@-节点扫描采集
				Node_Scan();
			}

			//@-发送故障应急处理
			if(CAN_Send_AlarmPro_Flag==true)
			{
				CAN_Send_AlarmPro_Flag = false;

				Node_AlarmPro();
			}

			//@-发送老化开启动作-控制报文&配置查询开启(按Group顺序执行)-独立于CAN数据请求，发送DA电压值及报警是否开启状态
			for(int i=0;i<9;i++)
			{
				if(CAN_Send_Group_TestPro_Flag[i]==true)
				{
					System.out.println("TestPro Group"+i+"TestPro_Node"+TestPro_Node);
					Group_TestPro(i);
					break;
				}
			}
		}

		//@-界面显示-暂定0.5s
		if(display_tick==(tick_count/2))
		{
			//@-界面显示
			display_tick = 0;

			if(ScreensFramework.App_Page==1)
			{
		    	//@-主界面刷新
				if(flash_flag==false)
				flash_flag=true;
				else if(flash_flag==true)
				flash_flag=false;
				QC_Controller.DisplayProperty_Main.setValue(""+flash_flag);
				//System.out.println("time");
			}
			else if(ScreensFramework.App_Page==2)
			{
				//@-检测是否需要参数初始化
				if(NodeView_Controller.Init_Flag==true)
				{
					NodeView_Controller.Init_Flag=false;

					NodeView_Controller.DataProperty_Main.setValue("init");
				}

		    	//@-主界面刷新
				if(flash_flag==false)
				flash_flag=true;
				else if(flash_flag==true)
				flash_flag=false;
				NodeView_Controller.DisplayProperty_Main.setValue(""+flash_flag);
			}

		}

		//@2-1秒计时
		if(second_1s_tick==tick_count)   //1s
		{
			//@-1s检测计时
			second_1s_tick=0;

			//@-5s检测计时
			second_5s_tick = second_5s_tick + 1;

			//@-NET_CAN板检测计时
			Second_PCB_Tick = Second_PCB_Tick + 1;

			//@-发送节点监控参数配置
			Node_Config();

			//@-信息输出监控
			Info_Out_Check();

        	//@-检测网络硬件连接
			ScreensFramework.Net_Main_Connnect.Check_Net_Link();

			//@-检测Group定时是否启动，并累计运行时间
			Check_Group_Timer_Run();

			//@-判断CAN_NodeMap有无运行节点-无
			if(CAN_NodeMap_Have==false)
			{
				//@-发送PCB心跳报文，维持通讯
				Node_HeartBeat();
				//System.out.println("Send H");
			}

			//@6-刷新时间
	    	local_time = Calendar.getInstance();

			//@-系统时间
	    	Time_Str = new String(""+local_time.get(Calendar.YEAR)+"/"
					+formater_value.format(local_time.get(Calendar.MONTH)+1)+"/"
					+formater_value.format(local_time.get(Calendar.DATE))+"  "
					+formater_value.format(local_time.get(Calendar.HOUR_OF_DAY))+":"
					+formater_value.format(local_time.get(Calendar.MINUTE))+":"
					+formater_value.format(local_time.get(Calendar.SECOND)));

	    	Time_Str1 = new String(""+formater_value.format(local_time.get(Calendar.HOUR_OF_DAY))+":"
									+formater_value.format(local_time.get(Calendar.MINUTE))+":"
									+formater_value.format(local_time.get(Calendar.SECOND)));

			//@-检测网络硬件接口
			if(second_5s_tick==5)
			{
				second_5s_tick=0;
				Check_NetWork();
			}

			//@-NET_CAN板250s检测
			if(Second_PCB_Tick==250)
			{
				Second_PCB_Tick=0;
				Check_NetCAN_PCB();
			}

		}
	}

	/**软件信息监测
	 *
	 */
	private void Info_Out_Check()
	{
//		System.out.println("C:"+Info_Num_Copy+" N:"+Info_Num);

		if(Info_Num_Copy!=Info_Num)
		{
			Info_Num_Copy = Info_Num;
			QC_Controller.InfoProperty_Main.set(""+Info_Num);
		}
	}

	/**节点故障应急处理
	 *
	 */
	private void Node_AlarmPro()
	{
		//@-配置报文特殊CAN-ID(0x0000aaaa)
		CAN_ID[0] = 0x00;
		CAN_ID[1] = 0x00;
		CAN_ID[2] = (byte)0xcc;
		CAN_ID[3] = (byte)0xcc;

		//@-故障应急处理节点Group
		CAN_CMD = (byte)(CAN_AlarmPro_Group+1);

		//@-故障应急处理节点Node
		CAN_Data[0] = (byte)(CAN_AlarmPro_Node+1);

		CAN_Data[1] = (byte)(0x01);
		CAN_Data[2] = (byte)(0x01);

		//@-网络发送报文
    	if((ScreensFramework.Net_Main_Connnect.isOpen==true)&&(ScreensFramework.Net_Main_Connnect.isError==0))
    	{
    		Net_Main.Send_Flag=true;
    		ScreensFramework.Net_Main_Connnect.Thread_alive_agin();
    		ScreensFramework.Net_Main_Connnect.Network_Send_thread.start();
    	}
	}


	/** 控制报文&配置查询处理
	 *
	 * @param Group_Num
	 */
	private void Group_TestPro(int Group_Num)
	{
		//@-发送控制报文(D/A输出电压)
		if(TestPro_DA_Flag==true)
		{
			Group_TestPro_DA(Group_Num);
		}

		//@-发送查询设置报文
		if(TestPro_Check_Flag==true)
		{
			Group_TestPro_Check(Group_Num);
		}

		//@-所有配置阶段完成Group置位
		if((TestPro_DA_Flag==false)&&(TestPro_Check_Flag==false))
		{
			//@-老化开始处理标志-D/A输出阶段
			TestPro_DA_Flag = true;
			//@-相应的Group标志置位
			CAN_Send_Group_TestPro_Flag[Group_Num] = false;
			//@-老化开始/停止-D/A输出
			TestPro_Stage = 0;
		}

	}

	/**发送控制报文(D/A输出)
	 *
	 * @param Group_Num
	 */
	private void Group_TestPro_DA(int Group_Num)
	{
		//@-判断是否是已配置节点
		if((ScreensFramework.DZFZ_Group[Group_Num].Group_DZFZ[TestPro_Node-1].Get_Pud_Test_Status())==1)
		{

			System.out.println("IN");

			//@-配置报文特殊CAN-ID
			CAN_ID[0] = 0x00;
			CAN_ID[1] = 0x00;
			CAN_ID[2] = (byte)(Group_Num+1);
			CAN_ID[3] = (byte)TestPro_Node;

			//@-配置CMD
			CAN_CMD = (byte)0x01;

			//@-配置继电器
			CAN_Data[0] = (byte)0x00;

			//@-老化启动阶段
			if(TestPro_Stage == 1)
			{
				//@-型号产品标号
				int PudNum = 0;
				//@-获得节点产品型号
				String PudName = ScreensFramework.DZFZ_Group[Group_Num].Group_DZFZ[TestPro_Node-1].Get_Pud_Test_PudModel();
				//@-查询型号标号
				for(int i=0;i<ScreensFramework.AODI_PubModel.PudModel_Num;i++)
				{
					if(PudName.endsWith(ScreensFramework.AODI_PubModel.AODI_PudModel[i].Get_PudModel_Name()))
					{
						PudNum = i;
						break;
					}
				}
				//@-获得型号D/A配置电压
				float Model_Voltage = ScreensFramework.AODI_PubModel.AODI_PudModel[PudNum].Get_PudModel_VoltageLimit();
				float DA1 = (float)(Model_Voltage/0.024606299212598425196850393700787);
				int DA = (int)(DA1);
				//@-配置D/A输出电压
				CAN_Data[1] = (byte)(((DA&0xffff)&0xff00)>>8);
				CAN_Data[2] = (byte)(((DA&0xffff)&0x00ff));

				System.out.println("DA1");
			}
			//@-老化停止阶段
			else if(TestPro_Stage == 2)
			{
				//@-配置D/A输出电压为0
				CAN_Data[1] = (byte)(0x00);
				CAN_Data[2] = (byte)(0x00);

				System.out.println("DA2");
			}

			//@-网络发送报文
	    	if((ScreensFramework.Net_Main_Connnect.isOpen==true)&&(ScreensFramework.Net_Main_Connnect.isError==0))
	    	{
	    		Net_Main.Send_Flag=true;
	    		ScreensFramework.Net_Main_Connnect.Thread_alive_agin();
	    		ScreensFramework.Net_Main_Connnect.Network_Send_thread.start();
	    	}

			//System.out.println("SendTestPro_DA");

	    	//@-Node自增
	    	TestPro_Node = TestPro_Node + 1;

	    	if(TestPro_Node>10)
	    	{
	    		TestPro_Node = 1;
	    		//@-D/A阶段结束
	    		TestPro_DA_Flag = false;
	    		//@-查询阶段开始
	    		TestPro_Check_Flag = true;
	    	}
		}
		else
		{
	    	//@-Node自增
	    	TestPro_Node = TestPro_Node + 1;

	    	if(TestPro_Node>10)
	    	{
	    		TestPro_Node = 1;
	    		//@-D/A阶段结束
	    		TestPro_DA_Flag = false;
	    		//@-查询阶段开始
	    		TestPro_Check_Flag = true;
	    	}
		}
	}

	/**发送查询设置报文
	 *
	 * @param Group_Num
	 */
	private void Group_TestPro_Check(int Group_Num)
	{
		//@-判断是否是已配置节点
		if((ScreensFramework.DZFZ_Group[Group_Num].Group_DZFZ[TestPro_Node-1].Get_Pud_Test_Status())==1)
		{
			//@-配置报文特殊CAN-ID
			CAN_ID[0] = 0x00;
			CAN_ID[1] = 0x00;
			CAN_ID[2] = (byte)(0xaa);
			CAN_ID[3] = (byte)(0xaa);

			//@-配置节点Group及Node
			CAN_CMD = (byte)(((Group_Num+1)<<4)|(TestPro_Node));

			//@-配置节点信息(查询配置)
			CAN_Data[0] = (byte)(0x03);

			//@-老化启动阶段
			if(TestPro_Stage == 1)
			{
				//@-配置查询状态-开
				CAN_Data[1] = (byte)(0x01);
			}
			//@-老化停止阶段
			else if(TestPro_Stage == 2)
			{
				//@-配置查询状态-关
				CAN_Data[1] = (byte)(0x00);
			}

			//@-网络发送报文
	    	if((ScreensFramework.Net_Main_Connnect.isOpen==true)&&(ScreensFramework.Net_Main_Connnect.isError==0))
	    	{
	    		Net_Main.Send_Flag=true;
	    		ScreensFramework.Net_Main_Connnect.Thread_alive_agin();
	    		ScreensFramework.Net_Main_Connnect.Network_Send_thread.start();
	    	}

	    	//@-Node自增
	    	TestPro_Node = TestPro_Node + 1;

	    	if(TestPro_Node>10)
	    	{
	    		TestPro_Node = 1;
	    		//@-查询阶段结束
	    		TestPro_Check_Flag = false;
	    	}
		}
		else
		{
	    	//@-Node自增
	    	TestPro_Node = TestPro_Node + 1;

	    	if(TestPro_Node>10)
	    	{
	    		TestPro_Node = 1;
	    		//@-查询阶段结束
	    		TestPro_Check_Flag = false;
	    	}
		}

	}


	/**节点配置报文设置
	 *
	 */
	private void Node_Config()
	{
		int Group = 0;
		int Node  = 0;
		boolean Have_Config = false;

		//@-扫描10组Group中需要配置Node
		for(Group=CAN_Config_Group;Group<=9;Group++)
		{
			for(Node=CAN_Config_Node;Node<=9;Node++)
			{
				//@-Node已配置型号&但没有配置参数
				if((ScreensFramework.DZFZ_Group[Group].Group_DZFZ[Node].Get_Pud_Test_ConfigStatus()==1)&&(ScreensFramework.DZFZ_Group[Group].Group_DZFZ[Node].Get_Pud_Test_Status()!=1))
				{
					CAN_Config_Group = Group;
					CAN_Config_Node =  Node;
					Have_Config = true;
					//System.out.println("G:"+CAN_Config_Group+" N:"+CAN_Config_Node);
					break;
				}
			}
			if(Have_Config==true)
			break;
		}

		//@-扫描自动复位
		if((Have_Config==false)&&(Group>9)&&(Node>9))
		{
			CAN_Config_Group = 0;
			CAN_Config_Node = 0;
			CAN_SendConfig_Count = 0;
			//System.out.println("Reset");
		}

		//System.out.println("G1:"+Group+" N1:"+Node+" Flag:"+Have_Config);

		//System.out.println("Flag:"+Have_Config);

		//@-存在需要配置的Node
		if(Have_Config==true)
		{
			//@-配置报文特殊CAN-ID(0x0000aaaa)
			CAN_ID[0] = 0x00;
			CAN_ID[1] = 0x00;
			CAN_ID[2] = (byte)0xaa;
			CAN_ID[3] = (byte)0xaa;

			//@-配置节点Group及Node
			CAN_CMD = (byte)(((CAN_Config_Group+1)<<4)|(CAN_Config_Node+1));

			//@-配置节点信息(温度、电压及电流)
			CAN_Data[0] = (byte)CAN_Config_CMD;

			//@-配置温度、故障响应(温度配置无幅值)
			if(CAN_Config_CMD==1)
			{
				int PudNum = 0;
				String PudName = ScreensFramework.DZFZ_Group[Group].Group_DZFZ[Node].Get_Pud_Test_PudModel();

				for(int i=0;i<ScreensFramework.AODI_PubModel.PudModel_Num;i++)
				{
					if(PudName.endsWith(ScreensFramework.AODI_PubModel.AODI_PudModel[i].Get_PudModel_Name()))
					{
						PudNum = i;
						break;
					}
				}

				CAN_Data[1] = (byte)(ScreensFramework.AODI_PubModel.AODI_PudModel[PudNum].Get_PudModel_Temperature1Limit());
				CAN_Data[2] = (byte)(ScreensFramework.AODI_PubModel.AODI_PudModel[PudNum].Get_PudModel_Temperature2Limit());
				CAN_Data[3] = (byte)(ScreensFramework.AODI_PubModel.AODI_PudModel[PudNum].Get_PudModel_Temperature3Limit());
				CAN_Data[4] = (byte)(QC_Controller.Aodi_AlarmPro_Mode+1);

				CAN_Data[5] = 0x00;
				CAN_Data[6] = 0x00;

				//System.out.println("t:"+CAN_Data[1]);
			}
			//@-配置电压、电流
			if(CAN_Config_CMD==2)
			{
				CAN_Data[1] = 0x00;
				CAN_Data[2] = 0x00;
				CAN_Data[3] = 0x00;
				CAN_Data[4] = 0x00;
				CAN_Data[5] = 0x00;
				CAN_Data[6] = 0x00;
			}

			//@-网络发送报文
	    	if((ScreensFramework.Net_Main_Connnect.isOpen==true)&&(ScreensFramework.Net_Main_Connnect.isError==0))
	    	{
	    		Net_Main.Send_Flag=true;
	    		ScreensFramework.Net_Main_Connnect.Thread_alive_agin();
	    		ScreensFramework.Net_Main_Connnect.Network_Send_thread.start();
	    	}

	    	//@-对配置报文进行计数
	    	CAN_SendConfig_Count = CAN_SendConfig_Count + 1;

	    	//@-发送配置报文超限
	    	if(CAN_SendConfig_Count>CAN_SendConfig_MaxCount)
	    	{
	    		CAN_SendConfig_Count = 0;
	    		CAN_Config_Node = CAN_Config_Node + 1;
	    		if(CAN_Config_Node>9)
	    		{
	    			CAN_Config_Node = 0;
	    			CAN_Config_Group = CAN_Config_Group + 1;
	    			if(CAN_Config_Group>9)
	    			{
	    				CAN_Config_Group = 0;
	    			}
	    		}

	    		//@输出信息
	    		Info_Num = 2;
	    	}

		}


	}



	/**检测定时器运行
	 *
	 */
	private void Check_Group_Timer_Run()
	{
		//@-扫描所有Group运行Flag
		for(int Group=0; Group<10; Group++)
		{
			//@-Group定时器
			if((ScreensFramework.DZFZ_Group[Group].Get_Group_TimerRunFlag())==1)
			{
				//Group定时器计时
				ScreensFramework.DZFZ_Group[Group].Group_Timer_RunTime = ScreensFramework.DZFZ_Group[Group].Group_Timer_RunTime +1;

				//@-Group中加入组Node计时
				for(int Node=0; Node<10; Node++)
				{
					//@-Group中Node加入Group运行，Node老化时间与Group老化时间同步
					if(ScreensFramework.DZFZ_Group[Group].Group_DZFZ[Node].Get_Pud_RunFlag()==1)
					{
						//@-Group中已配置或故障的节点与Group时间同步
						if((ScreensFramework.DZFZ_Group[Group].Group_DZFZ[Node].Get_Pud_Test_Status()==1)||(ScreensFramework.DZFZ_Group[Group].Group_DZFZ[Node].Get_Pud_Test_Status()==4))
						ScreensFramework.DZFZ_Group[Group].Group_DZFZ[Node].Set_Pud_RunTime(ScreensFramework.DZFZ_Group[Group].Group_Timer_RunTime);

						//@-记录节点老化起始时间
						ScreensFramework.DZFZ_Group[Group].Group_DZFZ[Node].Set_Pud_Test_Start_Time(Time_Str);

						//@DX1-判断运行节点是否已到定时时间
						int t1 = ScreensFramework.DZFZ_Group[Group].Group_DZFZ[Node].Get_Pud_RunTime();
						int t2 = ScreensFramework.DZFZ_Group[Group].Group_DZFZ[Node].Get_Pud_Timing();
						if(t1>=t2)
						{
							//@-老化停止
							ScreensFramework.DZFZ_Group[Group].Group_DZFZ[Node].Set_Pud_RunFlag(0);
							//@-老化结束
							ScreensFramework.DZFZ_Group[Group].Group_DZFZ[Node].Set_Pud_Test_Status(5);
						}

					}
				}
			}
		}
	}

	/**
	 *
	 */
	private void Node_HeartBeat()
	{
		CAN_ID[0] = 0x0d;
		CAN_ID[1] = 0x0d;
		CAN_ID[2] = 0x0d;
		CAN_ID[3] = 0x0d;

		CAN_CMD = 0x04;

		CAN_Data[0] = 0x00;
		CAN_Data[1] = 0x00;
		CAN_Data[2] = 0x00;
		CAN_Data[3] = 0x00;
		CAN_Data[4] = 0x00;
		CAN_Data[5] = 0x00;
		CAN_Data[6] = 0x00;

		//@-网络发送报文
    	if((ScreensFramework.Net_Main_Connnect.isOpen==true)&&(ScreensFramework.Net_Main_Connnect.isError==0))
    	{
    		Net_Main.Send_Flag=true;
    		ScreensFramework.Net_Main_Connnect.Thread_alive_agin();
    		ScreensFramework.Net_Main_Connnect.Network_Send_thread.start();
    	}
    	//@-发送次数增1
    	CAN_SendHB_Count = CAN_SendHB_Count + 1;
	}

	/**置位节点扫描运行
	 *
	 */
	private void Node_Scan()
	{
		//@-获取CAN_NodeMap中最近的CAN_ID
		if(CAN_ID_Switch==true)
		{
			CAN_ID_Index = CAN_ID_Index_Copy;

			for(int i=CAN_ID_Index; i<100; i++)
			{
				if(CAN_NodeMap[i]==0x01)
				{
					CAN_ID_Index=i;
					if(CAN_ID_Index == 99)
					{
						CAN_ID_Index_Copy = 0;
					}
					else
					{
						CAN_ID_Index_Copy = CAN_ID_Index + 1;
					}
					CAN_ID_Switch=false;
					break;
				}
				//@-Map的Index清零
				if(i==99)
				{
					CAN_ID_Index_Copy = 0;
				}
			}
		}

		//System.out.println("I:"+CAN_ID_Index);

		if(CAN_ID_Switch==false)
		{
			//@-将CAN_ID_Index转换成CAN_ID
			int Group=0, Node=0;
			Group = (CAN_ID_Index/10)+1;
			Node =  (CAN_ID_Index%10)+1;

			CAN_ID[0] = 0x00;
			CAN_ID[1] = 0x00;
			CAN_ID[2] = (byte)Group;
			CAN_ID[3] = (byte)Node;

			//@-CAN当前扫描节点Group
			CAN_Scan_Group = Group;
			CAN_Scan_Group_Node = Node;

			//System.out.println("index:"+CAN_ID_Index);
			//System.out.println("G:"+Group+" N:"+Node);

			//@-CAN_ID同步接收
			CAN_Syn_ID[0] = CAN_ID[0];
			CAN_Syn_ID[1] = CAN_ID[1];
			CAN_Syn_ID[2] = CAN_ID[2];
			CAN_Syn_ID[3] = CAN_ID[3];


			//@-查询当前CAN_ID发送次数小于最大限制值
			if(CAN_SendCMD_Count<=CAN_SendCMD_CountMax)
			{

				//System.out.println("index:"+CAN_ID_Index);

				//@-根据CAN命令编号发送相应请求命令-电压电流
				if(CAN_SendCMD_Num==2)
				{
					CAN_CMD = 0x02;
				}
				//@-根据CAN命令编号发送相应请求命令-温度
				else if(CAN_SendCMD_Num==3)
				{
					CAN_CMD = 0x03;
				}

				//@-发送电压、电流和温度请求命令
				if((CAN_SendCMD_Num==2)||(CAN_SendCMD_Num==3))
				{
					//@-网络发送报文
			    	if((ScreensFramework.Net_Main_Connnect.isOpen==true)&&(ScreensFramework.Net_Main_Connnect.isError==0))
			    	{
			    		Net_Main.Send_Flag=true;
			    		ScreensFramework.Net_Main_Connnect.Thread_alive_agin();
			    		ScreensFramework.Net_Main_Connnect.Network_Send_thread.start();
			    	}
			    	//@-发送次数增1
			    	CAN_SendCMD_Count = CAN_SendCMD_Count + 1;

			    	//System.out.println("C:"+CAN_SendCMD_Count);
				}
			}
			else
			{
				//@DX1-节点故障

				//@-CAN_ID切换下个节点
				CAN_ID_Switch=true;
				//@-发送次数清零
				CAN_SendCMD_Count=0;
				//@-发送CAN命令切换初始值
				CAN_SendCMD_Num=2;

				if(ScreensFramework.DZFZ_Group[Group-1].Group_DZFZ[Node-1].Get_Pud_Test_ConfigStatus()==1)
				ScreensFramework.DZFZ_Group[Group-1].Group_DZFZ[Node-1].Set_Pud_Test_Status(4);
				//System.out.println("G:"+Group+" N:"+Node+"is'down!");
			}
		}

	}


	/**
	 *
	 */
	private void Scan_CAN_NodeMap_All()
	{
		//@-Map中有运行节点标志清零
		CAN_NodeMap_Have = false;

		//@-查询主界面是否有Group“开始老化”按下-生成动态CAN总线ID表-0x00000101----->0x00000a0a
		for(int Group=0; Group<10; Group++)
		{
			//@-判断Group定时器是否运行
			if((ScreensFramework.DZFZ_Group[Group].Get_Group_TimerRunFlag())==1)
			{
				//@-Group中加入组的Node
				for(int Node=0; Node<10; Node++)
				{
					//@-Group中Node运行
					if(ScreensFramework.DZFZ_Group[Group].Group_DZFZ[Node].Get_Pud_RunFlag()==1)
					{
						//@-Group中已配置的节点-生成动态CAN表-置位或故障
						if((ScreensFramework.DZFZ_Group[Group].Group_DZFZ[Node].Get_Pud_Test_Status()==1)||(ScreensFramework.DZFZ_Group[Group].Group_DZFZ[Node].Get_Pud_Test_Status()==4))
						{
							//@-相应CANMap位置位
							CAN_NodeMap_Pro(Group,Node,1);
							//@-Map中有运行节点
							CAN_NodeMap_Have = true;
						}
					}
				}
			}
		}
		//@-只要有节点运行，所有节点均扫描
		if(CAN_NodeMap_Have==true)
		{
			//@-CAN所有节点置位
			for(int i=0;i<100;i++)
			CAN_NodeMap[i]=0x01;
		}
	}

	/**
	 *
	 */
	private void Scan_CAN_NodeMap()
	{
		//@-Map中有运行节点标志清零
		CAN_NodeMap_Have = false;

		//@-查询主界面是否有Group“开始老化”按下-生成动态CAN总线ID表-0x00000101----->0x00000a0a
		for(int Group=0; Group<10; Group++)
		{
			//@-判断Group定时器是否运行
			if((ScreensFramework.DZFZ_Group[Group].Get_Group_TimerRunFlag())==1)
			{
				//@-Group中加入组的Node
				for(int Node=0; Node<10; Node++)
				{
					//@-Group中Node运行
					if(ScreensFramework.DZFZ_Group[Group].Group_DZFZ[Node].Get_Pud_RunFlag()==1)
					{
						//@-Group中已配置的节点-生成动态CAN表-置位或故障
						if((ScreensFramework.DZFZ_Group[Group].Group_DZFZ[Node].Get_Pud_Test_Status()==1)||(ScreensFramework.DZFZ_Group[Group].Group_DZFZ[Node].Get_Pud_Test_Status()==4))
						{
							//@-相应CANMap位置位
							CAN_NodeMap_Pro(Group,Node,1);
							//@-Map中有运行节点
							CAN_NodeMap_Have = true;
						}
						//@-Group中已配置的节点-生成动态CAN表-清零
						else
						{
							//@-相应CANMap位清零
							CAN_NodeMap_Pro(Group,Node,0);
						}
					}
					//@-Group中Node没有运行或故障
					else if((ScreensFramework.DZFZ_Group[Group].Group_DZFZ[Node].Get_Pud_RunFlag()==0)||(ScreensFramework.DZFZ_Group[Group].Group_DZFZ[Node].Get_Pud_RunFlag()==2))
					{
						//@-相应CANMap位清零
						CAN_NodeMap_Pro(Group,Node,0);
					}
				}
			}
			//@-该Group中的Node相应CANMap位清零
			else if((ScreensFramework.DZFZ_Group[Group].Get_Group_TimerRunFlag())==0)
			{
				for(int Node=0;Node<10;Node++)
				{
					//@-相应CANMap位清零
					CAN_NodeMap_Pro(Group,Node,0);
				}
			}
		}
	}


	/**
	 *
	 * @param Group
	 * @param Node
	 * @param flag
	 */
	private void CAN_NodeMap_Pro(int Group, int Node, int flag)
	{
		int i;

		i = ((Group*10)+Node);

		//@-CAN_NodeMap的i位置清零
		if(flag==0)
		{
			CAN_NodeMap[i] = 0x00;
		}
		//@-CAN_NodeMap的i位置置位
		else if(flag==1)
		{
			CAN_NodeMap[i] = 0x01;
		}
	}



	/**NET_CAN板存活检测
	 *
	 */
	private void Check_NetCAN_PCB()
	{
		//System.out.println(""+Net_Main_Receive.Net_Recv_PCB_Count+"-"+Net_Main_Receive.Net_Recv_PCB_Count_Copy);

		//@-检测PCB是否存在
		if(Net_Main_Receive.Net_Recv_PCB_Count_Copy!=Net_Main_Receive.Net_Recv_PCB_Count)
		{
			Net_Main_Receive.Net_Recv_PCB_Count_Copy=Net_Main_Receive.Net_Recv_PCB_Count;
			Net_Main_Receive.Net_PCB_State=true;
			//MainRunController.CommunicationLEDProperty.set("ok");  //网络已打开,并收到PCB数据
		}
		else if(Net_Main_Receive.Net_Recv_PCB_Count_Copy==Net_Main_Receive.Net_Recv_PCB_Count)
		{
			Net_Main_Receive.Net_Recv_CMD=2;
			Net_Main_Receive.Net_PCB_State=false;
			//MainRunController.CommunicationLEDProperty.set("warnning");  //网络已打开,但没收到PCB数据
		}

	}


	/**检测网络硬件接口
	 *
	 */
	private void Check_NetWork()
	{
		if(ScreensFramework.Main_Falg==true)
		{
			//@1-查询硬件网络连接
			if((ScreensFramework.Net_Main_Connnect.NetLink_Mode_Copy!=ScreensFramework.Net_Main_Connnect.NetLink_Mode)||(ScreensFramework.Net_Main_Connnect.NetLink_Mode_Flash==true))
			{
				//更新copy值
				ScreensFramework.Net_Main_Connnect.NetLink_Mode_Copy=ScreensFramework.Net_Main_Connnect.NetLink_Mode;

				//网络硬件没有连接
				if(ScreensFramework.Net_Main_Connnect.NetLink_Mode_Copy==1)
				{
					//MainRunController.HardwareInfoProperty.set("unknow");

					//@-Noti输出信息
					Platform.runLater(new Runnable(){
						@Override
						public void run()
						{
							ScreensFramework.Show_Noti("Error", "设备故障!");
						}
					});

					if(ScreensFramework.Net_Main_Connnect.isOpen==true)
					{
						ScreensFramework.Net_Main_Connnect.close();
						Net_Main.Net_Interface_Set=false;
						Net_Main_Receive.Net_Recv_CMD=2;
						//MainRunController.CommunicationLEDProperty.set("error");  //网络断开
					}
				}
				//网络硬件连接，IP正确
				else if(ScreensFramework.Net_Main_Connnect.NetLink_Mode_Copy==2)
				{
					//MainRunController.HardwareInfoProperty.set("ok");

					//@-Noti输出信息
					Platform.runLater(new Runnable(){
						@Override
						public void run()
						{
							ScreensFramework.Show_Noti("Success", "设备正常!");
						}
					});

					if(Net_Main.Net_Interface_Set==false)
					{
						Net_Main.Net_Interface_Set=true;
						//@DX1
						//ScreensFramework.Net_Main_Connnect= new Net_Main(Net_Main.Set_IP,6000,Net_Main.Set_IP,21664,"224.100.23.180",6000,1);	//组播
						ScreensFramework.Net_Main_Connnect= new Net_Main(Config.Config_NetIP,Net_Main.Set_Port,Config.Config_NetIP,Net_Main.Set_Port,Net_Main.Set_Mcast_IP,Net_Main.Set_Port,1);	//组播

						//System.out.println("e:"+ScreensFramework.Net_Main_Connnect.isError);

						if((ScreensFramework.Net_Main_Connnect.isOpen==true)&&(ScreensFramework.Net_Main_Connnect.isError==0))
						{

							//MainRunController.CommunicationLEDProperty.set("warnning");  //网络已打开
						}
						else
						{
							Net_Main_Receive.Net_Recv_CMD=2;
							//MainRunController.CommunicationLEDProperty.set("error");     //网络打开出错
						}

					}
				}
				//网络硬件连接，IP不正确
				else if(ScreensFramework.Net_Main_Connnect.NetLink_Mode_Copy==3)
				{
					//MainRunController.HardwareInfoProperty.set("warnning");

					//@-Noti输出信息
					Platform.runLater(new Runnable(){
						@Override
						public void run()
						{
							ScreensFramework.Show_Noti("Warning", "设备网络地址出错!");
						}
					});

					if(ScreensFramework.Net_Main_Connnect.isOpen==true)
					{
						Net_Main_Receive.Net_Recv_CMD=2;
						ScreensFramework.Net_Main_Connnect.close();
						Net_Main.Net_Interface_Set=false;
						//MainRunController.CommunicationLEDProperty.set("error");  //网络断开
					}
				}

				//关闭状态刷新
				if(ScreensFramework.Net_Main_Connnect.NetLink_Mode_Flash==true)
				ScreensFramework.Net_Main_Connnect.NetLink_Mode_Flash=false;

			}

			//@3-根据配置进行判断
			//Rec_LoadConfig_Check();
		}
	}

}

