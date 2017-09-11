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



//@DX Ϊ���������Ҫ�Ż���

/**
 *
 * @author Jack Ding
 * @date 2011-01-13
 */
public class QC_DisplayTimer{

	//@1-��FXԭ����ʱ����(task)
	public static Task<Integer> task_dis;
	//@2-��ʾ�߳�
    private Thread Display_Thread;

	//@3-ϵͳ��ʱ
	private double system_tick=0;
	//@4-ϵͳtick����ֵ
    private int tick_count=0;
	//@5-1���ʱ
	private int second_1s_tick=0;
	//@5-5���ʱ
	private int second_5s_tick=0;
	//@5-�����ȼ���ʱ�ɱ��ʱ
	private int second_net_send_tick=0;
	//@5-�����涨ʱ�ɱ��ʱ
	private int display_tick=0;

    //@6-ϵͳʱ���ȡ�ӿ�
	private static Calendar local_time;
	//@7-��ʾ���ݸ�ʽ
	public static java.text.NumberFormat  formater_value  =  java.text.DecimalFormat.getInstance();  //��ʾС����ʽ��
	//@8-ʱ����Ϣ��ʾ
	public static String Time_Str=new String("----");
	public static String Time_Str1=new String("----");

    //@9-������ˢ�±�־
    public static boolean flash_flag=false;

    //@10-���NET_CAN���ʱ(1Min���һ��)
	private static int Second_PCB_Tick = 0;


    //-------------------CAN���------------------------
	//@-CAN_Debug
	private int CAN_Debug = 1;
    //@-CAN_CMD
	public static byte   CAN_Start = (byte)0xD2;
	public static byte[] CAN_ID = {(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00};
	public static byte[] CAN_Syn_ID = {(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00};
	public static byte   CAN_CMD = (byte)0x00;
	public static byte[] CAN_Data = {(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00};
	//@-CAN��̬���б�
	public static byte[] CAN_NodeMap = new byte[100];  //0x00:����������  0x01:��������
	//@-CAN_ID�л���һ���ڵ㿪��
	public static boolean CAN_ID_Switch = true;
	//@-CAN_NodeMap�������еĽڵ�
	public static boolean CAN_NodeMap_Have = false;  //true:Map�������еĽڵ�   false:Map��û�����еĽڵ�
	//@-CAN_ID�����Index
	public static int CAN_ID_Index = 0;
	//@-CAN_ID�����Index_Copy
	public static int CAN_ID_Index_Copy = 0;
	//@-CAN_ID�������������ط�������3
	public static int CAN_SendCMD_CountMax = 3;
	//@-CAN_ID��������ǰ����
	public static int CAN_SendCMD_Count = 0;
	//@-CAN_ID�����������
	public static int CAN_SendCMD_Num = 2;  //1:������Ϣ  2:������ӦCAN�ڵ��ѹ����������   3:������ӦCAN�ڵ��¶Ȳ���  4:��������һ��CAN�ڵ�
	//@-CAN_ID�������������
	public static int CAN_SendHB_Count = 0;

	//@-CAN��ǰɨ��ڵ�Group
	public static int CAN_Scan_Group = 1;
	//@-CAN��ǰɨ��ڵ�Group��Node
	public static int CAN_Scan_Group_Node = 1;

	//@-CAN_����Group
	public static int CAN_Config_Group = 0;
	//@-CAN_����Node
	public static int CAN_Config_Node = 0;
	//@-CAN_��������
	public static int CAN_Config_CMD = 1;  //1:�����¶�   2:��ѹ������
	//@-CAN�������ñ��ļ���
	public static int CAN_SendConfig_Count = 0;
	//@-CAN�������ñ���(��һNode)���ֵ
	public static int CAN_SendConfig_MaxCount = 2;

	//@-�����Ϣ������
	public static int Info_Num = 0;  //0:û�д�����Ϣ
	//@-�����Ϣ������Copy
	public static int Info_Num_Copy = 0;   //0:û�д�����Ϣ

	//@-CAN_����Ӧ����������Group
	public static int CAN_AlarmPro_Group = 0;
	//@-CAN_����Ӧ����������Node
	public static int CAN_AlarmPro_Node = 0;
	//@-CAN���͹���Ӧ�������ı�־
	public static boolean CAN_Send_AlarmPro_Flag = false;

	//@-CAN����Group�ϻ���ʼ�����־
	public static boolean[] CAN_Send_Group_TestPro_Flag = {false,false,false,false,false,false,false,false,false,false};
	//@-�ϻ���ʼ�����־-D/A����׶�
	public static boolean TestPro_DA_Flag = true;
	//@-�ϻ���ʼ�����־-��ѯ�׶�
	public static boolean TestPro_Check_Flag = false;
	//@-�ϻ���ʼ����Node
	public static int TestPro_Node = 1;
	//@-�ϻ���ʼ/ֹͣ-D/A���
	public static int TestPro_Stage = 0;  //0:�޲���  1:�ϻ������׶�  2:�ϻ��رս׶�




	/**����ʱ�䶨ʱ��
	 *
	 * @param delayTime1
	 */
	public QC_DisplayTimer(int delayTime){

		//@1-���ݾ��ȸ�ʽ
		formater_value.setMaximumIntegerDigits(2);
		formater_value.setMinimumIntegerDigits(2);

		//@-CAN��̬Map��ʼ��
		for(int i=0;i<100;i++)
		CAN_NodeMap[i]=0x00;

		tick_count=1000/delayTime;

	    /*-------------ʹ��FXԭ��task����---------------------*/
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
	    Display_Thread.setPriority(Thread.NORM_PRIORITY);    //�������ȼ���8
	    Display_Thread.start();

	}


	/**��ʾˢ��
	 *
	 */
	private void data_put()
	{
	    //@1-ϵͳ��ʱ�ۼ�
		system_tick=system_tick+1;
		//@2-1���ʱ�ۼ�
		second_1s_tick=second_1s_tick+1;
		//@3-�����ȼ���ʱ�ۼ�
		second_net_send_tick = second_net_send_tick +1;
		//@3-��������ʾ��ʱ�ۼ�
		display_tick = display_tick +1;

		//@-�����̨���ʹ���-���ݲ����ȼ�ʱ��ɱ�-�ݶ�0.25s
		if(second_net_send_tick==(tick_count/4))
		{
			//@-�����ȼ�ʱ��ɱ�
			second_net_send_tick = 0;

			//@-��̬ɨ��CAN_NodeMap
			if(QC_Controller.Aodi_DataSample_Mode==0)
			{
				//@-�㶨�������ģʽ
				Scan_CAN_NodeMap_All();
			}
			else if(QC_Controller.Aodi_DataSample_Mode==1)
			{
				//@-�ɱ����ģʽ
				Scan_CAN_NodeMap();
			}

			//@-�ж�CAN_NodeMap�������нڵ�-��
			if(CAN_NodeMap_Have==true)
			{
				//@-�ڵ�ɨ��ɼ�
				Node_Scan();
			}

			//@-���͹���Ӧ������
			if(CAN_Send_AlarmPro_Flag==true)
			{
				CAN_Send_AlarmPro_Flag = false;

				Node_AlarmPro();
			}

			//@-�����ϻ���������-���Ʊ���&���ò�ѯ����(��Group˳��ִ��)-������CAN�������󣬷���DA��ѹֵ�������Ƿ���״̬
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

		//@-������ʾ-�ݶ�0.5s
		if(display_tick==(tick_count/2))
		{
			//@-������ʾ
			display_tick = 0;

			if(ScreensFramework.App_Page==1)
			{
		    	//@-������ˢ��
				if(flash_flag==false)
				flash_flag=true;
				else if(flash_flag==true)
				flash_flag=false;
				QC_Controller.DisplayProperty_Main.setValue(""+flash_flag);
				//System.out.println("time");
			}
			else if(ScreensFramework.App_Page==2)
			{
				//@-����Ƿ���Ҫ������ʼ��
				if(NodeView_Controller.Init_Flag==true)
				{
					NodeView_Controller.Init_Flag=false;

					NodeView_Controller.DataProperty_Main.setValue("init");
				}

		    	//@-������ˢ��
				if(flash_flag==false)
				flash_flag=true;
				else if(flash_flag==true)
				flash_flag=false;
				NodeView_Controller.DisplayProperty_Main.setValue(""+flash_flag);
			}

		}

		//@2-1���ʱ
		if(second_1s_tick==tick_count)   //1s
		{
			//@-1s����ʱ
			second_1s_tick=0;

			//@-5s����ʱ
			second_5s_tick = second_5s_tick + 1;

			//@-NET_CAN�����ʱ
			Second_PCB_Tick = Second_PCB_Tick + 1;

			//@-���ͽڵ��ز�������
			Node_Config();

			//@-��Ϣ������
			Info_Out_Check();

        	//@-�������Ӳ������
			ScreensFramework.Net_Main_Connnect.Check_Net_Link();

			//@-���Group��ʱ�Ƿ����������ۼ�����ʱ��
			Check_Group_Timer_Run();

			//@-�ж�CAN_NodeMap�������нڵ�-��
			if(CAN_NodeMap_Have==false)
			{
				//@-����PCB�������ģ�ά��ͨѶ
				Node_HeartBeat();
				//System.out.println("Send H");
			}

			//@6-ˢ��ʱ��
	    	local_time = Calendar.getInstance();

			//@-ϵͳʱ��
	    	Time_Str = new String(""+local_time.get(Calendar.YEAR)+"/"
					+formater_value.format(local_time.get(Calendar.MONTH)+1)+"/"
					+formater_value.format(local_time.get(Calendar.DATE))+"  "
					+formater_value.format(local_time.get(Calendar.HOUR_OF_DAY))+":"
					+formater_value.format(local_time.get(Calendar.MINUTE))+":"
					+formater_value.format(local_time.get(Calendar.SECOND)));

	    	Time_Str1 = new String(""+formater_value.format(local_time.get(Calendar.HOUR_OF_DAY))+":"
									+formater_value.format(local_time.get(Calendar.MINUTE))+":"
									+formater_value.format(local_time.get(Calendar.SECOND)));

			//@-�������Ӳ���ӿ�
			if(second_5s_tick==5)
			{
				second_5s_tick=0;
				Check_NetWork();
			}

			//@-NET_CAN��250s���
			if(Second_PCB_Tick==250)
			{
				Second_PCB_Tick=0;
				Check_NetCAN_PCB();
			}

		}
	}

	/**�����Ϣ���
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

	/**�ڵ����Ӧ������
	 *
	 */
	private void Node_AlarmPro()
	{
		//@-���ñ�������CAN-ID(0x0000aaaa)
		CAN_ID[0] = 0x00;
		CAN_ID[1] = 0x00;
		CAN_ID[2] = (byte)0xcc;
		CAN_ID[3] = (byte)0xcc;

		//@-����Ӧ������ڵ�Group
		CAN_CMD = (byte)(CAN_AlarmPro_Group+1);

		//@-����Ӧ������ڵ�Node
		CAN_Data[0] = (byte)(CAN_AlarmPro_Node+1);

		CAN_Data[1] = (byte)(0x01);
		CAN_Data[2] = (byte)(0x01);

		//@-���緢�ͱ���
    	if((ScreensFramework.Net_Main_Connnect.isOpen==true)&&(ScreensFramework.Net_Main_Connnect.isError==0))
    	{
    		Net_Main.Send_Flag=true;
    		ScreensFramework.Net_Main_Connnect.Thread_alive_agin();
    		ScreensFramework.Net_Main_Connnect.Network_Send_thread.start();
    	}
	}


	/** ���Ʊ���&���ò�ѯ����
	 *
	 * @param Group_Num
	 */
	private void Group_TestPro(int Group_Num)
	{
		//@-���Ϳ��Ʊ���(D/A�����ѹ)
		if(TestPro_DA_Flag==true)
		{
			Group_TestPro_DA(Group_Num);
		}

		//@-���Ͳ�ѯ���ñ���
		if(TestPro_Check_Flag==true)
		{
			Group_TestPro_Check(Group_Num);
		}

		//@-�������ý׶����Group��λ
		if((TestPro_DA_Flag==false)&&(TestPro_Check_Flag==false))
		{
			//@-�ϻ���ʼ�����־-D/A����׶�
			TestPro_DA_Flag = true;
			//@-��Ӧ��Group��־��λ
			CAN_Send_Group_TestPro_Flag[Group_Num] = false;
			//@-�ϻ���ʼ/ֹͣ-D/A���
			TestPro_Stage = 0;
		}

	}

	/**���Ϳ��Ʊ���(D/A���)
	 *
	 * @param Group_Num
	 */
	private void Group_TestPro_DA(int Group_Num)
	{
		//@-�ж��Ƿ��������ýڵ�
		if((ScreensFramework.DZFZ_Group[Group_Num].Group_DZFZ[TestPro_Node-1].Get_Pud_Test_Status())==1)
		{

			System.out.println("IN");

			//@-���ñ�������CAN-ID
			CAN_ID[0] = 0x00;
			CAN_ID[1] = 0x00;
			CAN_ID[2] = (byte)(Group_Num+1);
			CAN_ID[3] = (byte)TestPro_Node;

			//@-����CMD
			CAN_CMD = (byte)0x01;

			//@-���ü̵���
			CAN_Data[0] = (byte)0x00;

			//@-�ϻ������׶�
			if(TestPro_Stage == 1)
			{
				//@-�ͺŲ�Ʒ���
				int PudNum = 0;
				//@-��ýڵ��Ʒ�ͺ�
				String PudName = ScreensFramework.DZFZ_Group[Group_Num].Group_DZFZ[TestPro_Node-1].Get_Pud_Test_PudModel();
				//@-��ѯ�ͺű��
				for(int i=0;i<ScreensFramework.AODI_PubModel.PudModel_Num;i++)
				{
					if(PudName.endsWith(ScreensFramework.AODI_PubModel.AODI_PudModel[i].Get_PudModel_Name()))
					{
						PudNum = i;
						break;
					}
				}
				//@-����ͺ�D/A���õ�ѹ
				float Model_Voltage = ScreensFramework.AODI_PubModel.AODI_PudModel[PudNum].Get_PudModel_VoltageLimit();
				float DA1 = (float)(Model_Voltage/0.024606299212598425196850393700787);
				int DA = (int)(DA1);
				//@-����D/A�����ѹ
				CAN_Data[1] = (byte)(((DA&0xffff)&0xff00)>>8);
				CAN_Data[2] = (byte)(((DA&0xffff)&0x00ff));

				System.out.println("DA1");
			}
			//@-�ϻ�ֹͣ�׶�
			else if(TestPro_Stage == 2)
			{
				//@-����D/A�����ѹΪ0
				CAN_Data[1] = (byte)(0x00);
				CAN_Data[2] = (byte)(0x00);

				System.out.println("DA2");
			}

			//@-���緢�ͱ���
	    	if((ScreensFramework.Net_Main_Connnect.isOpen==true)&&(ScreensFramework.Net_Main_Connnect.isError==0))
	    	{
	    		Net_Main.Send_Flag=true;
	    		ScreensFramework.Net_Main_Connnect.Thread_alive_agin();
	    		ScreensFramework.Net_Main_Connnect.Network_Send_thread.start();
	    	}

			//System.out.println("SendTestPro_DA");

	    	//@-Node����
	    	TestPro_Node = TestPro_Node + 1;

	    	if(TestPro_Node>10)
	    	{
	    		TestPro_Node = 1;
	    		//@-D/A�׶ν���
	    		TestPro_DA_Flag = false;
	    		//@-��ѯ�׶ο�ʼ
	    		TestPro_Check_Flag = true;
	    	}
		}
		else
		{
	    	//@-Node����
	    	TestPro_Node = TestPro_Node + 1;

	    	if(TestPro_Node>10)
	    	{
	    		TestPro_Node = 1;
	    		//@-D/A�׶ν���
	    		TestPro_DA_Flag = false;
	    		//@-��ѯ�׶ο�ʼ
	    		TestPro_Check_Flag = true;
	    	}
		}
	}

	/**���Ͳ�ѯ���ñ���
	 *
	 * @param Group_Num
	 */
	private void Group_TestPro_Check(int Group_Num)
	{
		//@-�ж��Ƿ��������ýڵ�
		if((ScreensFramework.DZFZ_Group[Group_Num].Group_DZFZ[TestPro_Node-1].Get_Pud_Test_Status())==1)
		{
			//@-���ñ�������CAN-ID
			CAN_ID[0] = 0x00;
			CAN_ID[1] = 0x00;
			CAN_ID[2] = (byte)(0xaa);
			CAN_ID[3] = (byte)(0xaa);

			//@-���ýڵ�Group��Node
			CAN_CMD = (byte)(((Group_Num+1)<<4)|(TestPro_Node));

			//@-���ýڵ���Ϣ(��ѯ����)
			CAN_Data[0] = (byte)(0x03);

			//@-�ϻ������׶�
			if(TestPro_Stage == 1)
			{
				//@-���ò�ѯ״̬-��
				CAN_Data[1] = (byte)(0x01);
			}
			//@-�ϻ�ֹͣ�׶�
			else if(TestPro_Stage == 2)
			{
				//@-���ò�ѯ״̬-��
				CAN_Data[1] = (byte)(0x00);
			}

			//@-���緢�ͱ���
	    	if((ScreensFramework.Net_Main_Connnect.isOpen==true)&&(ScreensFramework.Net_Main_Connnect.isError==0))
	    	{
	    		Net_Main.Send_Flag=true;
	    		ScreensFramework.Net_Main_Connnect.Thread_alive_agin();
	    		ScreensFramework.Net_Main_Connnect.Network_Send_thread.start();
	    	}

	    	//@-Node����
	    	TestPro_Node = TestPro_Node + 1;

	    	if(TestPro_Node>10)
	    	{
	    		TestPro_Node = 1;
	    		//@-��ѯ�׶ν���
	    		TestPro_Check_Flag = false;
	    	}
		}
		else
		{
	    	//@-Node����
	    	TestPro_Node = TestPro_Node + 1;

	    	if(TestPro_Node>10)
	    	{
	    		TestPro_Node = 1;
	    		//@-��ѯ�׶ν���
	    		TestPro_Check_Flag = false;
	    	}
		}

	}


	/**�ڵ����ñ�������
	 *
	 */
	private void Node_Config()
	{
		int Group = 0;
		int Node  = 0;
		boolean Have_Config = false;

		//@-ɨ��10��Group����Ҫ����Node
		for(Group=CAN_Config_Group;Group<=9;Group++)
		{
			for(Node=CAN_Config_Node;Node<=9;Node++)
			{
				//@-Node�������ͺ�&��û�����ò���
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

		//@-ɨ���Զ���λ
		if((Have_Config==false)&&(Group>9)&&(Node>9))
		{
			CAN_Config_Group = 0;
			CAN_Config_Node = 0;
			CAN_SendConfig_Count = 0;
			//System.out.println("Reset");
		}

		//System.out.println("G1:"+Group+" N1:"+Node+" Flag:"+Have_Config);

		//System.out.println("Flag:"+Have_Config);

		//@-������Ҫ���õ�Node
		if(Have_Config==true)
		{
			//@-���ñ�������CAN-ID(0x0000aaaa)
			CAN_ID[0] = 0x00;
			CAN_ID[1] = 0x00;
			CAN_ID[2] = (byte)0xaa;
			CAN_ID[3] = (byte)0xaa;

			//@-���ýڵ�Group��Node
			CAN_CMD = (byte)(((CAN_Config_Group+1)<<4)|(CAN_Config_Node+1));

			//@-���ýڵ���Ϣ(�¶ȡ���ѹ������)
			CAN_Data[0] = (byte)CAN_Config_CMD;

			//@-�����¶ȡ�������Ӧ(�¶������޷�ֵ)
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
			//@-���õ�ѹ������
			if(CAN_Config_CMD==2)
			{
				CAN_Data[1] = 0x00;
				CAN_Data[2] = 0x00;
				CAN_Data[3] = 0x00;
				CAN_Data[4] = 0x00;
				CAN_Data[5] = 0x00;
				CAN_Data[6] = 0x00;
			}

			//@-���緢�ͱ���
	    	if((ScreensFramework.Net_Main_Connnect.isOpen==true)&&(ScreensFramework.Net_Main_Connnect.isError==0))
	    	{
	    		Net_Main.Send_Flag=true;
	    		ScreensFramework.Net_Main_Connnect.Thread_alive_agin();
	    		ScreensFramework.Net_Main_Connnect.Network_Send_thread.start();
	    	}

	    	//@-�����ñ��Ľ��м���
	    	CAN_SendConfig_Count = CAN_SendConfig_Count + 1;

	    	//@-�������ñ��ĳ���
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

	    		//@�����Ϣ
	    		Info_Num = 2;
	    	}

		}


	}



	/**��ⶨʱ������
	 *
	 */
	private void Check_Group_Timer_Run()
	{
		//@-ɨ������Group����Flag
		for(int Group=0; Group<10; Group++)
		{
			//@-Group��ʱ��
			if((ScreensFramework.DZFZ_Group[Group].Get_Group_TimerRunFlag())==1)
			{
				//Group��ʱ����ʱ
				ScreensFramework.DZFZ_Group[Group].Group_Timer_RunTime = ScreensFramework.DZFZ_Group[Group].Group_Timer_RunTime +1;

				//@-Group�м�����Node��ʱ
				for(int Node=0; Node<10; Node++)
				{
					//@-Group��Node����Group���У�Node�ϻ�ʱ����Group�ϻ�ʱ��ͬ��
					if(ScreensFramework.DZFZ_Group[Group].Group_DZFZ[Node].Get_Pud_RunFlag()==1)
					{
						//@-Group�������û���ϵĽڵ���Groupʱ��ͬ��
						if((ScreensFramework.DZFZ_Group[Group].Group_DZFZ[Node].Get_Pud_Test_Status()==1)||(ScreensFramework.DZFZ_Group[Group].Group_DZFZ[Node].Get_Pud_Test_Status()==4))
						ScreensFramework.DZFZ_Group[Group].Group_DZFZ[Node].Set_Pud_RunTime(ScreensFramework.DZFZ_Group[Group].Group_Timer_RunTime);

						//@-��¼�ڵ��ϻ���ʼʱ��
						ScreensFramework.DZFZ_Group[Group].Group_DZFZ[Node].Set_Pud_Test_Start_Time(Time_Str);

						//@DX1-�ж����нڵ��Ƿ��ѵ���ʱʱ��
						int t1 = ScreensFramework.DZFZ_Group[Group].Group_DZFZ[Node].Get_Pud_RunTime();
						int t2 = ScreensFramework.DZFZ_Group[Group].Group_DZFZ[Node].Get_Pud_Timing();
						if(t1>=t2)
						{
							//@-�ϻ�ֹͣ
							ScreensFramework.DZFZ_Group[Group].Group_DZFZ[Node].Set_Pud_RunFlag(0);
							//@-�ϻ�����
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

		//@-���緢�ͱ���
    	if((ScreensFramework.Net_Main_Connnect.isOpen==true)&&(ScreensFramework.Net_Main_Connnect.isError==0))
    	{
    		Net_Main.Send_Flag=true;
    		ScreensFramework.Net_Main_Connnect.Thread_alive_agin();
    		ScreensFramework.Net_Main_Connnect.Network_Send_thread.start();
    	}
    	//@-���ʹ�����1
    	CAN_SendHB_Count = CAN_SendHB_Count + 1;
	}

	/**��λ�ڵ�ɨ������
	 *
	 */
	private void Node_Scan()
	{
		//@-��ȡCAN_NodeMap�������CAN_ID
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
				//@-Map��Index����
				if(i==99)
				{
					CAN_ID_Index_Copy = 0;
				}
			}
		}

		//System.out.println("I:"+CAN_ID_Index);

		if(CAN_ID_Switch==false)
		{
			//@-��CAN_ID_Indexת����CAN_ID
			int Group=0, Node=0;
			Group = (CAN_ID_Index/10)+1;
			Node =  (CAN_ID_Index%10)+1;

			CAN_ID[0] = 0x00;
			CAN_ID[1] = 0x00;
			CAN_ID[2] = (byte)Group;
			CAN_ID[3] = (byte)Node;

			//@-CAN��ǰɨ��ڵ�Group
			CAN_Scan_Group = Group;
			CAN_Scan_Group_Node = Node;

			//System.out.println("index:"+CAN_ID_Index);
			//System.out.println("G:"+Group+" N:"+Node);

			//@-CAN_IDͬ������
			CAN_Syn_ID[0] = CAN_ID[0];
			CAN_Syn_ID[1] = CAN_ID[1];
			CAN_Syn_ID[2] = CAN_ID[2];
			CAN_Syn_ID[3] = CAN_ID[3];


			//@-��ѯ��ǰCAN_ID���ʹ���С���������ֵ
			if(CAN_SendCMD_Count<=CAN_SendCMD_CountMax)
			{

				//System.out.println("index:"+CAN_ID_Index);

				//@-����CAN�����ŷ�����Ӧ��������-��ѹ����
				if(CAN_SendCMD_Num==2)
				{
					CAN_CMD = 0x02;
				}
				//@-����CAN�����ŷ�����Ӧ��������-�¶�
				else if(CAN_SendCMD_Num==3)
				{
					CAN_CMD = 0x03;
				}

				//@-���͵�ѹ���������¶���������
				if((CAN_SendCMD_Num==2)||(CAN_SendCMD_Num==3))
				{
					//@-���緢�ͱ���
			    	if((ScreensFramework.Net_Main_Connnect.isOpen==true)&&(ScreensFramework.Net_Main_Connnect.isError==0))
			    	{
			    		Net_Main.Send_Flag=true;
			    		ScreensFramework.Net_Main_Connnect.Thread_alive_agin();
			    		ScreensFramework.Net_Main_Connnect.Network_Send_thread.start();
			    	}
			    	//@-���ʹ�����1
			    	CAN_SendCMD_Count = CAN_SendCMD_Count + 1;

			    	//System.out.println("C:"+CAN_SendCMD_Count);
				}
			}
			else
			{
				//@DX1-�ڵ����

				//@-CAN_ID�л��¸��ڵ�
				CAN_ID_Switch=true;
				//@-���ʹ�������
				CAN_SendCMD_Count=0;
				//@-����CAN�����л���ʼֵ
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
		//@-Map�������нڵ��־����
		CAN_NodeMap_Have = false;

		//@-��ѯ�������Ƿ���Group����ʼ�ϻ�������-���ɶ�̬CAN����ID��-0x00000101----->0x00000a0a
		for(int Group=0; Group<10; Group++)
		{
			//@-�ж�Group��ʱ���Ƿ�����
			if((ScreensFramework.DZFZ_Group[Group].Get_Group_TimerRunFlag())==1)
			{
				//@-Group�м������Node
				for(int Node=0; Node<10; Node++)
				{
					//@-Group��Node����
					if(ScreensFramework.DZFZ_Group[Group].Group_DZFZ[Node].Get_Pud_RunFlag()==1)
					{
						//@-Group�������õĽڵ�-���ɶ�̬CAN��-��λ�����
						if((ScreensFramework.DZFZ_Group[Group].Group_DZFZ[Node].Get_Pud_Test_Status()==1)||(ScreensFramework.DZFZ_Group[Group].Group_DZFZ[Node].Get_Pud_Test_Status()==4))
						{
							//@-��ӦCANMapλ��λ
							CAN_NodeMap_Pro(Group,Node,1);
							//@-Map�������нڵ�
							CAN_NodeMap_Have = true;
						}
					}
				}
			}
		}
		//@-ֻҪ�нڵ����У����нڵ��ɨ��
		if(CAN_NodeMap_Have==true)
		{
			//@-CAN���нڵ���λ
			for(int i=0;i<100;i++)
			CAN_NodeMap[i]=0x01;
		}
	}

	/**
	 *
	 */
	private void Scan_CAN_NodeMap()
	{
		//@-Map�������нڵ��־����
		CAN_NodeMap_Have = false;

		//@-��ѯ�������Ƿ���Group����ʼ�ϻ�������-���ɶ�̬CAN����ID��-0x00000101----->0x00000a0a
		for(int Group=0; Group<10; Group++)
		{
			//@-�ж�Group��ʱ���Ƿ�����
			if((ScreensFramework.DZFZ_Group[Group].Get_Group_TimerRunFlag())==1)
			{
				//@-Group�м������Node
				for(int Node=0; Node<10; Node++)
				{
					//@-Group��Node����
					if(ScreensFramework.DZFZ_Group[Group].Group_DZFZ[Node].Get_Pud_RunFlag()==1)
					{
						//@-Group�������õĽڵ�-���ɶ�̬CAN��-��λ�����
						if((ScreensFramework.DZFZ_Group[Group].Group_DZFZ[Node].Get_Pud_Test_Status()==1)||(ScreensFramework.DZFZ_Group[Group].Group_DZFZ[Node].Get_Pud_Test_Status()==4))
						{
							//@-��ӦCANMapλ��λ
							CAN_NodeMap_Pro(Group,Node,1);
							//@-Map�������нڵ�
							CAN_NodeMap_Have = true;
						}
						//@-Group�������õĽڵ�-���ɶ�̬CAN��-����
						else
						{
							//@-��ӦCANMapλ����
							CAN_NodeMap_Pro(Group,Node,0);
						}
					}
					//@-Group��Nodeû�����л����
					else if((ScreensFramework.DZFZ_Group[Group].Group_DZFZ[Node].Get_Pud_RunFlag()==0)||(ScreensFramework.DZFZ_Group[Group].Group_DZFZ[Node].Get_Pud_RunFlag()==2))
					{
						//@-��ӦCANMapλ����
						CAN_NodeMap_Pro(Group,Node,0);
					}
				}
			}
			//@-��Group�е�Node��ӦCANMapλ����
			else if((ScreensFramework.DZFZ_Group[Group].Get_Group_TimerRunFlag())==0)
			{
				for(int Node=0;Node<10;Node++)
				{
					//@-��ӦCANMapλ����
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

		//@-CAN_NodeMap��iλ������
		if(flag==0)
		{
			CAN_NodeMap[i] = 0x00;
		}
		//@-CAN_NodeMap��iλ����λ
		else if(flag==1)
		{
			CAN_NodeMap[i] = 0x01;
		}
	}



	/**NET_CAN������
	 *
	 */
	private void Check_NetCAN_PCB()
	{
		//System.out.println(""+Net_Main_Receive.Net_Recv_PCB_Count+"-"+Net_Main_Receive.Net_Recv_PCB_Count_Copy);

		//@-���PCB�Ƿ����
		if(Net_Main_Receive.Net_Recv_PCB_Count_Copy!=Net_Main_Receive.Net_Recv_PCB_Count)
		{
			Net_Main_Receive.Net_Recv_PCB_Count_Copy=Net_Main_Receive.Net_Recv_PCB_Count;
			Net_Main_Receive.Net_PCB_State=true;
			//MainRunController.CommunicationLEDProperty.set("ok");  //�����Ѵ�,���յ�PCB����
		}
		else if(Net_Main_Receive.Net_Recv_PCB_Count_Copy==Net_Main_Receive.Net_Recv_PCB_Count)
		{
			Net_Main_Receive.Net_Recv_CMD=2;
			Net_Main_Receive.Net_PCB_State=false;
			//MainRunController.CommunicationLEDProperty.set("warnning");  //�����Ѵ�,��û�յ�PCB����
		}

	}


	/**�������Ӳ���ӿ�
	 *
	 */
	private void Check_NetWork()
	{
		if(ScreensFramework.Main_Falg==true)
		{
			//@1-��ѯӲ����������
			if((ScreensFramework.Net_Main_Connnect.NetLink_Mode_Copy!=ScreensFramework.Net_Main_Connnect.NetLink_Mode)||(ScreensFramework.Net_Main_Connnect.NetLink_Mode_Flash==true))
			{
				//����copyֵ
				ScreensFramework.Net_Main_Connnect.NetLink_Mode_Copy=ScreensFramework.Net_Main_Connnect.NetLink_Mode;

				//����Ӳ��û������
				if(ScreensFramework.Net_Main_Connnect.NetLink_Mode_Copy==1)
				{
					//MainRunController.HardwareInfoProperty.set("unknow");

					//@-Noti�����Ϣ
					Platform.runLater(new Runnable(){
						@Override
						public void run()
						{
							ScreensFramework.Show_Noti("Error", "�豸����!");
						}
					});

					if(ScreensFramework.Net_Main_Connnect.isOpen==true)
					{
						ScreensFramework.Net_Main_Connnect.close();
						Net_Main.Net_Interface_Set=false;
						Net_Main_Receive.Net_Recv_CMD=2;
						//MainRunController.CommunicationLEDProperty.set("error");  //����Ͽ�
					}
				}
				//����Ӳ�����ӣ�IP��ȷ
				else if(ScreensFramework.Net_Main_Connnect.NetLink_Mode_Copy==2)
				{
					//MainRunController.HardwareInfoProperty.set("ok");

					//@-Noti�����Ϣ
					Platform.runLater(new Runnable(){
						@Override
						public void run()
						{
							ScreensFramework.Show_Noti("Success", "�豸����!");
						}
					});

					if(Net_Main.Net_Interface_Set==false)
					{
						Net_Main.Net_Interface_Set=true;
						//@DX1
						//ScreensFramework.Net_Main_Connnect= new Net_Main(Net_Main.Set_IP,6000,Net_Main.Set_IP,21664,"224.100.23.180",6000,1);	//�鲥
						ScreensFramework.Net_Main_Connnect= new Net_Main(Config.Config_NetIP,Net_Main.Set_Port,Config.Config_NetIP,Net_Main.Set_Port,Net_Main.Set_Mcast_IP,Net_Main.Set_Port,1);	//�鲥

						//System.out.println("e:"+ScreensFramework.Net_Main_Connnect.isError);

						if((ScreensFramework.Net_Main_Connnect.isOpen==true)&&(ScreensFramework.Net_Main_Connnect.isError==0))
						{

							//MainRunController.CommunicationLEDProperty.set("warnning");  //�����Ѵ�
						}
						else
						{
							Net_Main_Receive.Net_Recv_CMD=2;
							//MainRunController.CommunicationLEDProperty.set("error");     //����򿪳���
						}

					}
				}
				//����Ӳ�����ӣ�IP����ȷ
				else if(ScreensFramework.Net_Main_Connnect.NetLink_Mode_Copy==3)
				{
					//MainRunController.HardwareInfoProperty.set("warnning");

					//@-Noti�����Ϣ
					Platform.runLater(new Runnable(){
						@Override
						public void run()
						{
							ScreensFramework.Show_Noti("Warning", "�豸�����ַ����!");
						}
					});

					if(ScreensFramework.Net_Main_Connnect.isOpen==true)
					{
						Net_Main_Receive.Net_Recv_CMD=2;
						ScreensFramework.Net_Main_Connnect.close();
						Net_Main.Net_Interface_Set=false;
						//MainRunController.CommunicationLEDProperty.set("error");  //����Ͽ�
					}
				}

				//�ر�״̬ˢ��
				if(ScreensFramework.Net_Main_Connnect.NetLink_Mode_Flash==true)
				ScreensFramework.Net_Main_Connnect.NetLink_Mode_Flash=false;

			}

			//@3-�������ý����ж�
			//Rec_LoadConfig_Check();
		}
	}

}

