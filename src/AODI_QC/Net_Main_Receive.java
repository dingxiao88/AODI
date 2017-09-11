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

	//@1-������սӿڡ�����
	public  Thread Network_Receive_thread=new Thread(this);      //��������߳�
    private static byte[] receive_buff_Udp = new byte[128];      //������ջ���128Byte
    private static byte[] receive_buff_Cast = new byte[128];     //������ջ���128Byte

    //@2-����ӿ�
    private int cPort_Udp;
    private int cPort_Cast;
    public  long temp;  //�������ݰ�����
    private String ClientIP_Udp=null;
    private String ClientIP_Cast=null;
    private String ClientPort=null;
    private String ClientData=null;
    private int Recv_Len_Udp;   //����������ݰ��ĳ���
    private int Recv_Len_Cast;  //����������ݰ��ĳ���

    //@3-Э����
    public static long Net_Recv_PCB_Count=0;          //PCB���ռ���
    public static long Net_Recv_PCB_Count_Copy=0;     //PCB Copy���ռ���
    public static boolean Net_PCB_State=true;        //PCB״̬

    //@4-�������ݶ�
    public static int    Net_Recv_CMD=0;
    public static int    Net_Recv_CMD_Copy=0;
    public static byte[] Net_Recv_Data = new byte[19];
    public static byte   Net_Recv_Key=0x00;
    public static boolean  Net_Recv_KeyLock=false;
    public static int Net_Recv_RecordCMD_Count=0;
    public static String  Net_Recv_CMD_Str;

    //@5-CAN����
    public static byte   Net_Recv_CAN_Start;
    public static byte[] Net_Recv_CAN_ID = new byte[4];
    public static byte   Net_Recv_CAN_CMD;
    public static byte   Net_Recv_CAN_Config_ID;
    public static byte   Net_Recv_CAN_Config_State;
    public static byte[] Net_Recv_CAN_Data = new byte[7];
    public static byte[] Net_Recv_CAN_ID_Error = new byte[2];
    public static byte   Net_Recv_CAN_ErrorID = 0x00;

    //@6-���յ�������
    public static float  Net_Recv_Voltage;
    public static float  Net_Recv_Current;
    public static float  Net_Recv_Temperature1;
    public static float  Net_Recv_Temperature2;
    public static float  Net_Recv_Temperature3;

	//@7-���ݱ������
	public static Save_Data Save_Obj = new Save_Data();
	//@7-���ݱ����־
	public static boolean Save_Flag=false;

    //@1-��Ҫ�������ݵ�Groupλ��
	public static  int Save_Data_Group;
    //@1-��Ҫ�������ݵ�Groupλ���е�Nodeλ��
	public static  int Save_Data_Node;

    //@1-���
	public static String  Save_Data_Num;
    //@2-��ƷID
	public static String  Save_Data_PudID;
	//@3-��Ʒ�ͺ�
	public static String  Save_Data_PudModel;
    //@4-��Ʒ�ϻ���ʼʱ��
	public static String  Save_Data_TestStartTime;
    //@5-�ϻ���Ʒ����-��ѹ
	public static String  Save_Data_Voltage;
    //@6-�ϻ���Ʒ����-����
	public static String  Save_Data_Current;
    //@7-�ϻ���Ʒ����-�¶�1
	public static String  Save_Data_Temperature1;
    //@8-�ϻ���Ʒ����-�¶�2
	public static String  Save_Data_Temperature2;
    //@9-�ϻ���Ʒ����-�¶�3
	public static String  Save_Data_Temperature3;
    //@10-�ϻ���Ʒ����-����ʱ��
	public static String  Save_Data_TestScanTime;
    //@11-�ϻ���Ʒ-������
	public static String  Save_Data_ErrorID;
    //@12-�ϻ���Ʒ-״̬
	public static String  Save_Data_TestStatus;

	//@-���ݱ����ļ���
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

		//����UDP Socket��ʱ
		try {
			Net_Main.UDP_socket.setSoTimeout(100);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}

	/** ���տ���
	 *
	 */
	public void receive_on_off(boolean on_off){

		if(on_off==true)
		{
		 Network_Receive_thread=new Thread(this);  //��������߳�
		 //Network_Receive_thread.setDaemon(true);   //����Ϊ��̨�߳�
		 Network_Receive_thread.setName("Net-Receive");
		 Network_Receive_thread.setPriority(7);    //�������ȼ���6
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
		if(Net_Main.sendmode==0)  //�㲥
		{
			if(Net_Main.isOpen==true)  //������������
			{
				while(Net_Main.isClose==false)  //ֻҪû��Ҫ��رգ�һֱ����
				{


						/**
						 *   add Action
						 */

				}
			}
		}
		else if(Net_Main.sendmode==1)  //�鲥
		{
			while(Net_Main.isOpen==true)  //������������
			{
				//@1-�鲥����
				try {

					Net_Main.Multicast_socket.receive(Net_Main.Multicast_Packet);

					ClientIP_Cast=Net_Main.Multicast_Packet.getAddress().getHostAddress();
					cPort_Cast=Net_Main.Multicast_Packet.getPort();
					Recv_Len_Cast=Net_Main.Multicast_Packet.getLength();
					receive_buff_Cast=Net_Main.Multicast_Packet.getData();

					//System.out.println("HB2"+ClientIP_Cast);
					//@2-����PCB����Ϣ
					if(ClientIP_Cast.equals(Net_Main.Set_PCB_IP))
					{
						if(cPort_Cast==Net_Main.Set_Port)
						{
							if(Recv_Len_Cast==13)  //Э��������ݳ���
							{
								//@3-���ռ�����
								Net_Recv_PCB_Count=Net_Recv_PCB_Count+1;

								//System.out.println(""+Net_Recv_PCB_Count);

								//@4-PCB״̬�������Ž��
								if(Net_PCB_State==true)
								{
									//@-CAN���ݶ�ͷ
									Net_Recv_CAN_Start = receive_buff_Cast[0];
									//@-CAN���ݶ�ID
									Net_Recv_CAN_ID[0] = receive_buff_Cast[1];
									Net_Recv_CAN_ID[1] = receive_buff_Cast[2];
									Net_Recv_CAN_ID[2] = receive_buff_Cast[3];
									Net_Recv_CAN_ID[3] = receive_buff_Cast[4];

									//@-CAN���ݶ�����
									Net_Recv_CAN_CMD = receive_buff_Cast[5];

									//@-CAN���ݶ�����
									Net_Recv_CAN_Data[0] = receive_buff_Cast[6];
									Net_Recv_CAN_Data[1] = receive_buff_Cast[7];
									Net_Recv_CAN_Data[2] = receive_buff_Cast[8];
									Net_Recv_CAN_Data[3] = receive_buff_Cast[9];
									Net_Recv_CAN_Data[4] = receive_buff_Cast[10];
									Net_Recv_CAN_Data[5] = receive_buff_Cast[11];
									Net_Recv_CAN_Data[6] = receive_buff_Cast[12];


									//@-�������ñ���-ID&State(����CAN���ݶ�����)
									Net_Recv_CAN_Config_ID =  receive_buff_Cast[5];
									Net_Recv_CAN_Config_State =  receive_buff_Cast[6];

									//@-���չ��ϱ���-����CANID(����CAN���ݶ�����+����)
									Net_Recv_CAN_ID_Error[0] = receive_buff_Cast[5];
									Net_Recv_CAN_ID_Error[1] = receive_buff_Cast[6];
									Net_Recv_CAN_ErrorID = receive_buff_Cast[7];


									//System.out.println("["+Net_Recv_CAN_ID[0]+"]"+"["+Net_Recv_CAN_ID[1]+"]"+"["+Net_Recv_CAN_ID[2]+"]"+"["+Net_Recv_CAN_ID[3]+"]");


									//@5-���ݶν��
									if(Net_Recv_CAN_Start==(byte)0xD2)
									{

										//@DX1-����Ҫ�뷢��ͬ���жϣ�CAN_SendCMD_Count

										//@-�ж��ѿ�ʼɨ��CAN_NodeMap�������еĽڵ�
										if(QC_DisplayTimer.CAN_NodeMap_Have==true)
										{

											//System.out.println("["+QC_DisplayTimer.CAN_Syn_ID[0]+"]"+"["+QC_DisplayTimer.CAN_Syn_ID[1]+"]"+"["+QC_DisplayTimer.CAN_Syn_ID[2]+"]"+"["+QC_DisplayTimer.CAN_Syn_ID[3]+"]");

											//@-ͬ��CAN����ID
											if((Net_Recv_CAN_ID[0]==QC_DisplayTimer.CAN_Syn_ID[0])&&(Net_Recv_CAN_ID[1]==QC_DisplayTimer.CAN_Syn_ID[1])&&
											   (Net_Recv_CAN_ID[2]==QC_DisplayTimer.CAN_Syn_ID[2])&&(Net_Recv_CAN_ID[3]==QC_DisplayTimer.CAN_Syn_ID[3]))
											{
												//@-�������ݴ����Group��Nodeλ��
								                Save_Data_Group = QC_DisplayTimer.CAN_Syn_ID[2] - 1;
								        		Save_Data_Node =  QC_DisplayTimer.CAN_Syn_ID[3] - 1;

												//@-��ѹ����������
												if(Net_Recv_CAN_CMD==0x02)
												{
													//@-��ѹ
													int temp=(int)(((Net_Recv_CAN_Data[0]&0xff)*256)+(Net_Recv_CAN_Data[1]&0xff));
													//System.out.println("AD0:"+temp);
													Net_Recv_Voltage = (float)(temp/(91)*5);  //Group1Node5
													//Net_Recv_Voltage = (float)(temp/(91));  //Group1Node6
													//@-����
									            	temp=(int)(((Net_Recv_CAN_Data[2]&0xff)*256)+(Net_Recv_CAN_Data[3]&0xff));
													Net_Recv_Current = (float)((2500-(temp*0.4))*0.03899999);

													//@-���յ���ѹ����������-�л��������¶�����
													QC_DisplayTimer.CAN_SendCMD_Num=3;
													//@-���ͼ�������
													QC_DisplayTimer.CAN_SendCMD_Count=0;

													//System.out.println("CMD==3");

													//System.out.println("["+Net_Recv_CAN_Data[0]+"]"+"["+Net_Recv_CAN_Data[1]+"]"+"["+Net_Recv_CAN_Data[2]+"]"+"["+Net_Recv_CAN_Data[3]+"]");
												}
												//@-�¶�����
												else if(Net_Recv_CAN_CMD==0x03)
												{
													//@-�¶�1
													Net_Recv_Temperature1 = (float)((((Net_Recv_CAN_Data[0]&0xff)*256)+(Net_Recv_CAN_Data[1]&0xff))*0.1);
													//@-�¶�2
													Net_Recv_Temperature2 = (float)((((Net_Recv_CAN_Data[2]&0xff)*256)+(Net_Recv_CAN_Data[3]&0xff))*0.1);
													//@-�¶�3
													Net_Recv_Temperature3 = (float)((((Net_Recv_CAN_Data[4]&0xff)*256)+(Net_Recv_CAN_Data[5]&0xff))*0.1);

													//@-��������
													Save_Flag = true;
												}

												//@-��������
												if(Save_Flag==true)
												{

													//@-�ýڵ��յ��ɼ����ݼ�����1
													ScreensFramework.DZFZ_Group[Save_Data_Group].Group_DZFZ[Save_Data_Node].Set_TestData_Count_Add();

									                //@-��òɼ��������
													Save_Data_Num = new String(""+ScreensFramework.DZFZ_Group[Save_Data_Group].Group_DZFZ[Save_Data_Node].Get_TestData_Count());
									                //@-��ò�ƷID
									        		Save_Data_PudID = ScreensFramework.DZFZ_Group[Save_Data_Group].Group_DZFZ[Save_Data_Node].Get_Pud_ID();
									        		//@-��ò�Ʒ�ͺ�
									        		Save_Data_PudModel  = ScreensFramework.DZFZ_Group[Save_Data_Group].Group_DZFZ[Save_Data_Node].Get_Pud_Test_PudModel();
									                //@-��ò�Ʒ�ϻ���ʼʱ��
									        		Save_Data_TestStartTime = ScreensFramework.DZFZ_Group[Save_Data_Group].Group_DZFZ[Save_Data_Node].Get_Pud_Test_Start_Time();
									                //@-��ò�Ʒ����-��ѹ
									        		Save_Data_Voltage = new String(""+Net_Recv_Voltage);
									                //@-��ò�Ʒ����-����
									        		Save_Data_Current = new String(""+Net_Recv_Current);
									                //@-��ò�Ʒ����-�¶�1
									        		Save_Data_Temperature1 = new String(""+Net_Recv_Temperature1);
									                //@-��ò�Ʒ����-�¶�2
									        		Save_Data_Temperature2 = new String(""+Net_Recv_Temperature2);
									                //@-��ò�Ʒ����-�¶�3
									        		Save_Data_Temperature3 = new String(""+Net_Recv_Temperature3);
									                //@-��ò�Ʒ����-����ʱ��
									        		Save_Data_TestScanTime = new String(QC_DisplayTimer.Time_Str1);
									                //@-��ò�Ʒ����-������(����ԭ��)
									        		Save_Data_ErrorID = new String(""+ScreensFramework.DZFZ_Group[Save_Data_Group].Group_DZFZ[Save_Data_Node].Get_Pud_LeaveReason());
									                //@-��ò�Ʒ����-״̬
									        		Save_Data_TestStatus = new String(""+ScreensFramework.DZFZ_Group[Save_Data_Group].Group_DZFZ[Save_Data_Node].Get_Pud_Test_Status());

									        		//@-�������ݵ��ڵ�-��ѹ
									        		ScreensFramework.DZFZ_Group[Save_Data_Group].Group_DZFZ[Save_Data_Node].Set_Pud_Test_ScanVoltage(Net_Recv_Voltage);
									        		//@-�������ݵ��ڵ�-����
									        		ScreensFramework.DZFZ_Group[Save_Data_Group].Group_DZFZ[Save_Data_Node].Set_Pud_Test_ScanCurrent(Net_Recv_Current);
									        		//@-�������ݵ��ڵ�-�¶�1
									        		ScreensFramework.DZFZ_Group[Save_Data_Group].Group_DZFZ[Save_Data_Node].Set_Pud_Test_ScanTemputer1(Net_Recv_Temperature1);
									        		//@-�������ݵ��ڵ�-�¶�2
									        		ScreensFramework.DZFZ_Group[Save_Data_Group].Group_DZFZ[Save_Data_Node].Set_Pud_Test_ScanTemputer2(Net_Recv_Temperature2);
									        		//@-�������ݵ��ڵ�-�¶�3
									        		ScreensFramework.DZFZ_Group[Save_Data_Group].Group_DZFZ[Save_Data_Node].Set_Pud_Test_ScanTemputer3(Net_Recv_Temperature3);
									        		//@-�������ݵ��ڵ�-����ʱ��
									        		ScreensFramework.DZFZ_Group[Save_Data_Group].Group_DZFZ[Save_Data_Node].Set_Pud_Test_Scan_Time(Save_Data_TestScanTime);

													//@DX1-�������ݵ��ļ���-ÿ����Ʒ�������ݴ洢�ļ�-|AODI_��ƷID_��Ʒ�ͺ�_Group(Save_Data_Group)Node(Save_Data_Node).aodi_save|
													Save_Data_FileName = new String("AODI_"+Save_Data_PudID+"_"+Save_Data_PudModel+"_Group"+(Save_Data_Group+1)+"Node"+(Save_Data_Node+1)+".aodi_save");

													//System.out.println(""+Save_Data_FileName);

													//@-��������
										    	    try {
										    			Save_Obj.createAndSave();
										    		} catch (IOException e) {
										    			// TODO Auto-generated catch block
										    			e.printStackTrace();
										    		}

													//@-CAN_ID�л��¸��ڵ�
													QC_DisplayTimer.CAN_ID_Switch=true;
													//@-���ʹ�������
													QC_DisplayTimer.CAN_SendCMD_Count=0;
													//@-����CAN�����л���ʼֵ
													QC_DisplayTimer.CAN_SendCMD_Num=2;

													//System.out.println("time:"+QC_DisplayTimer.Time_Str1);

										    	    Save_Flag = false;
												}

											}

										}


										//@-�ж����ûظ���������CAN_ID(0000aaaa)-���ûظ�ȷ��
										if((Net_Recv_CAN_ID[0]==(byte)0x00)&&(Net_Recv_CAN_ID[1]==(byte)0x00)&&
										   (Net_Recv_CAN_ID[2]==(byte)0xaa)&&(Net_Recv_CAN_ID[3]==(byte)0xaa))
										{
											//System.out.println("device config!");
											//@-���յ����ñ��Ļ���-�¶�
											if(Net_Recv_CAN_Config_State==(byte)0xaa)
											{
												//@-���͵�ѹ���������ñ���
												QC_DisplayTimer.CAN_Config_CMD = 2;
												//@-���ñ��ļ�������
												QC_DisplayTimer.CAN_SendConfig_Count = 0;
											}
											//@-���յ����ñ��Ļ���-��ѹ������
											else if(Net_Recv_CAN_Config_State==(byte)0xbb)
											{
												//@-���ýڵ��������
												ScreensFramework.DZFZ_Group[(byte)(((Net_Recv_CAN_Config_ID&0xf0)>>4)-1)].Group_DZFZ[(byte)((Net_Recv_CAN_Config_ID&0x0f)-1)].Set_Pud_Test_Status(1);
												//@-�����¶����ñ���
												QC_DisplayTimer.CAN_Config_CMD = 1;
												//@-���ñ��ļ�������
												QC_DisplayTimer.CAN_SendConfig_Count = 0;
											}
											//@-���յ����ñ��Ļ���-��ѯ״̬�Ѹ���
											else if(Net_Recv_CAN_Config_State==(byte)0xcc)
											{

											}

										}
										//@-�жϹ��ϸ澯��������CAN_ID(0000bbbb)-���ϸ澯
										else if((Net_Recv_CAN_ID[0]==(byte)0x00)&&(Net_Recv_CAN_ID[1]==(byte)0x00)&&
										   (Net_Recv_CAN_ID[2]==(byte)0xbb)&&(Net_Recv_CAN_ID[3]==(byte)0xbb))
										{
											System.out.println("Error-Group:"+Net_Recv_CAN_ID_Error[0]+" Error-Node:"+Net_Recv_CAN_ID_Error[1]);

											System.out.println("ErrorID:"+Net_Recv_CAN_ErrorID);

											//Net_Recv_CAN_ErrorID
										}

										//@-�ж�������������CAN_ID(0d0d0d0d)-PCB����
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

