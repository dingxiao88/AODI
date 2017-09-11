

import java.awt.Toolkit;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.util.Duration;


/**
 * 
 * @author Jack Ding
 * @date 2011-01-13
 */
public class AODI_DisplayTimer{
		
	//@1-ϵͳ��ʱ
	private double system_tick=0;
	//@2-1���ʱ
	private double second_1s_tick=0;
	//@-1����˸flag
	public static boolean second_1s_flash=false;
	//@3-������ʾ��ʱ��1
	private int    DisplayUpdata_Timer1=0;
	//@3-������ʾ��ʱ��2
	private int    DisplayUpdata_Timer2=0;
	//@3-���ܼ�ⶨʱ��3
	private int    DisplayUpdata_Timer3=0;
	//@3-PCB����ⶨʱ��4
	private int    DisplayUpdata_Timer4=0;
	//@3-�����ʱ��ʱ��5
	public static  int    DisplayUpdata_Timer5=0;
	//@3-���Զ�ʱ��6
	private int    DisplayUpdata_Timer6=0;
	//@3-ʱ�䶨ʱ��
    private static Timer My_timer;    
    //@4-ʱ�䶨ʱ����ʱֵ
    private int My_Time_Value=0;
    //@5-ϵͳʱ���ȡ�ӿ�
	private static Calendar local_time;
	//@6-��ʾ���ݸ�ʽ
	public static java.text.NumberFormat  formater_value  =  java.text.DecimalFormat.getInstance();  //��ʾС����ʽ��
	//@7-��ʱ�����б�־
	public static int Timer_RunFlag=1;  //1:run  2:close
	
	public static String Time_Str=new String("----");
	public static String Time_Str_Info=new String("----");
	
	private int i=0;
	
	//@2-1���ʱ
	private static double second_3s_tick=0;
	private static boolean lock_3s=false;
	public static boolean lock_start_main=false;

	
	/*------ʹ�÷�FXԭ��task����-----------*/
	public static Task<Integer> task_dis; 
    private Thread Display_Thread;
        
    private int tick_count=0;
    
    
    //@-��Ϣ��Ϣ����
    public static List<String> Data_InfoMessage = Collections.synchronizedList(new ArrayList<String>());
    //@-��Ϣ��Ϣ����
    public static List<Integer> Data_InfoType = Collections.synchronizedList(new ArrayList<Integer>());
    //@-��Ϣ��Ϣ
    public static String  InfoMessage;
    //@-��Ϣ��Ϣ�������
    public static int MessagePut_Count=0;
    //@-��Ϣ��Ϣ����
    public static int Message_Type=0;  //1:ok  2:error 3:warning 

	
	



   	
	
	/**����ʱ�䶨ʱ��
	 * 
	 * @param delayTime1
	 */
	public AODI_DisplayTimer(int delayTime1){
		
		//@1-���ն�ʱ����ʱʱ��
    	My_Time_Value = delayTime1;
    	//@2-����1�붨ʱֵ
    	tick_count = 1000/My_Time_Value;
    	
 /*----------------------------ʹ�÷�FXԭ��task����---------------------------*/   	
//    	//@3-������ʱ��ʵ��
//    	My_timer = new Timer();
//    	//@4-������ʱ�����ȶ�ʱ����
//    	My_timer.schedule(new UpDisplay_Task(), My_Time_Value);
    	
		formater_value.setMaximumIntegerDigits(2);
		formater_value.setMinimumIntegerDigits(2);
		
		
		
	    /*-------------ʹ��FXԭ��task����---------------------*/
	    task_dis = new Task<Integer>() {
	        @Override protected Integer call() throws Exception {
	            int iterations;
	            //int i;
	            
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
	                    Thread.sleep(My_Time_Value);
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
		
		
		if(ScreensFramework.Main_Falg==true)
		{			
	    	//@1-����������������
	    	if(ScreensFramework.App_Page==1)
	    	{	
	    		DisplayUpdata_Timer1=DisplayUpdata_Timer1+1; //20times/s
	    		
	    		DisplayUpdata_Timer2=DisplayUpdata_Timer2+1; //?times/s
	    		
	    		DisplayUpdata_Timer3=DisplayUpdata_Timer3+1; //?times/s
	    		
	    		DisplayUpdata_Timer4=DisplayUpdata_Timer4+1; //?times/s
	    		
	    		DisplayUpdata_Timer6=DisplayUpdata_Timer6+1; //?times/s
	    		
	    		//@-����ˢ��20times/s
				if(DisplayUpdata_Timer1==tick_count/10)
				{
					DisplayUpdata_Timer1=0;
					
					//@-�����Ƶ�ѹ���
					ChargingControl_VoltageOut();
					
					//@-debug
//					LoginController1.OutputVoltage_Current=(float)Math.random();
//					LoginController1.OutputCurrent_Current=new Float(Math.sin(40*2*3.14*system_tick)*1);
//					LoginController1.MainVoltage_Current=(float)Math.random();
//					LoginController1.Temperature_Current=(float)Math.random();
					
					//@-������������
					LoginController1.OutputVoltage_Current=LoginController1.Serise_Main.Recv_Data_OutputVoltage;
					LoginController1.OutputCurrent_Current=LoginController1.Serise_Main.Recv_Data_OutputCurrent;
					LoginController1.MainVoltage_Current=LoginController1.Serise_Main.Recv_Data_MainVoltage;
					LoginController1.Temperature_Current=LoginController1.Serise_Main.Recv_Data_Temperature;
					
					//@-�������б�־
					if(LoginController1.CurveRun_Status==true)
					{
						synchronized(this)
						{
							if(LoginController1.Curve_OutputVoltage_Dis==true)
							LoginController1.Data_OutputVoltage.add(LoginController1.OutputVoltage_Current);
							
							if(LoginController1.Curve_OutputCurrent_Dis==true)
							LoginController1.Data_OutputCurrent.add(LoginController1.OutputCurrent_Current);
							
							if(LoginController1.Curve_MainVoltage_Dis==true)
							LoginController1.Data_MainVoltage.add(LoginController1.MainVoltage_Current);
							
							if(LoginController1.Curve_Temperature_Dis==true)
							LoginController1.Data_Temperature.add(LoginController1.Temperature_Current);
							
							if(LoginController1.Curve_ControlVoltage_Dis==true)
							LoginController1.Data_ControlVoltage.add(LoginController1.ControlVoltage_Current);
						}
				    }
				}
	    		//@-����ˢ��&��ֵ�Ƚ�&���ޱȽ�5times/s
				if(DisplayUpdata_Timer2==tick_count/5)
				{
					DisplayUpdata_Timer2=0;
					
					//@-��ʾ���±�־
					if(LoginController1.DisplayUpdata_Flag==false)
					LoginController1.DisplayUpdata_Flag=true;
					else if(LoginController1.DisplayUpdata_Flag==true)
					LoginController1.DisplayUpdata_Flag=false;
					
					//@-��ֵ�Ƚ�&���ޱȽ�
					Check_Num();
					
					LoginController1.DisplayUpdataProperty_Main.setValue(""+LoginController1.DisplayUpdata_Flag);
				}
				//@-����Э�鷢��10times/s
				if(DisplayUpdata_Timer3==tick_count/10)
				{
					DisplayUpdata_Timer3=0;
										
					//@-�жϴ����Ѵ�&�������
					if((LoginController1.Serise_Main.isOpen==true))
					{						
						//@-�̵�������
						if(LoginController1.Charging_PowerSend_Flag==true)
						{
							LoginController1.Charging_PowerSend_Flag=false;
							//@-����Э��ͷ
							LoginController1.Serise_Main.AODI_Serise_Protocol.Serise_Send_Protocol_RecvBuff[0]=LoginController1.Serise_Main.AODI_Serise_Protocol.Serise_SendFrame_StartByte;
							//@-CAN-ID(4Bytes)-�̵�������
							LoginController1.Serise_Main.AODI_Serise_Protocol.Serise_Send_Protocol_RecvBuff[1]=0x00;
							LoginController1.Serise_Main.AODI_Serise_Protocol.Serise_Send_Protocol_RecvBuff[2]=0x00;
							LoginController1.Serise_Main.AODI_Serise_Protocol.Serise_Send_Protocol_RecvBuff[3]=0x01;
							LoginController1.Serise_Main.AODI_Serise_Protocol.Serise_Send_Protocol_RecvBuff[4]=0x19;
							
							switch(LoginController1.Charging_Power_Sel)
							{
							    //@-���м̵�����
								case 0:
										//@-CAN-DATA(8Bytes)-�̵���1
										LoginController1.Serise_Main.AODI_Serise_Protocol.Serise_Send_Protocol_RecvBuff[5]=0x00;
										LoginController1.Serise_Main.AODI_Serise_Protocol.Serise_Send_Protocol_RecvBuff[6]=0x00;
										//@-CAN-DATA(8Bytes)-�̵���2
										LoginController1.Serise_Main.AODI_Serise_Protocol.Serise_Send_Protocol_RecvBuff[7]=0x00;
										LoginController1.Serise_Main.AODI_Serise_Protocol.Serise_Send_Protocol_RecvBuff[8]=0x00;
										//@-CAN-DATA(8Bytes)-�̵���3
										LoginController1.Serise_Main.AODI_Serise_Protocol.Serise_Send_Protocol_RecvBuff[9]=0x00;
										LoginController1.Serise_Main.AODI_Serise_Protocol.Serise_Send_Protocol_RecvBuff[10]=0x00;
										//@-CAN-DATA(8Bytes)-�̵���4
										LoginController1.Serise_Main.AODI_Serise_Protocol.Serise_Send_Protocol_RecvBuff[11]=0x00;
										LoginController1.Serise_Main.AODI_Serise_Protocol.Serise_Send_Protocol_RecvBuff[12]=0x00;
										break;
							    //@-110V
								case 1:
										//@-CAN-DATA(8Bytes)-�̵���1
										LoginController1.Serise_Main.AODI_Serise_Protocol.Serise_Send_Protocol_RecvBuff[5]=0x00;
										LoginController1.Serise_Main.AODI_Serise_Protocol.Serise_Send_Protocol_RecvBuff[6]=0x04;
										//@-CAN-DATA(8Bytes)-�̵���2
										LoginController1.Serise_Main.AODI_Serise_Protocol.Serise_Send_Protocol_RecvBuff[7]=0x00;
										LoginController1.Serise_Main.AODI_Serise_Protocol.Serise_Send_Protocol_RecvBuff[8]=0x00;
										//@-CAN-DATA(8Bytes)-�̵���3
										LoginController1.Serise_Main.AODI_Serise_Protocol.Serise_Send_Protocol_RecvBuff[9]=0x00;
										LoginController1.Serise_Main.AODI_Serise_Protocol.Serise_Send_Protocol_RecvBuff[10]=0x00;
										//@-CAN-DATA(8Bytes)-�̵���4
										LoginController1.Serise_Main.AODI_Serise_Protocol.Serise_Send_Protocol_RecvBuff[11]=0x00;
										LoginController1.Serise_Main.AODI_Serise_Protocol.Serise_Send_Protocol_RecvBuff[12]=0x00;
										break;
							    //@-220V
								case 2:
										//@-CAN-DATA(8Bytes)-�̵���1
										LoginController1.Serise_Main.AODI_Serise_Protocol.Serise_Send_Protocol_RecvBuff[5]=0x00;
										LoginController1.Serise_Main.AODI_Serise_Protocol.Serise_Send_Protocol_RecvBuff[6]=0x06;
										//@-CAN-DATA(8Bytes)-�̵���2
										LoginController1.Serise_Main.AODI_Serise_Protocol.Serise_Send_Protocol_RecvBuff[7]=0x00;
										LoginController1.Serise_Main.AODI_Serise_Protocol.Serise_Send_Protocol_RecvBuff[8]=0x00;
										//@-CAN-DATA(8Bytes)-�̵���3
										LoginController1.Serise_Main.AODI_Serise_Protocol.Serise_Send_Protocol_RecvBuff[9]=0x00;
										LoginController1.Serise_Main.AODI_Serise_Protocol.Serise_Send_Protocol_RecvBuff[10]=0x00;
										//@-CAN-DATA(8Bytes)-�̵���4
										LoginController1.Serise_Main.AODI_Serise_Protocol.Serise_Send_Protocol_RecvBuff[11]=0x00;
										LoginController1.Serise_Main.AODI_Serise_Protocol.Serise_Send_Protocol_RecvBuff[12]=0x00;
										break;
							    //@-260V
								case 3:
										//@-CAN-DATA(8Bytes)-�̵���1
										LoginController1.Serise_Main.AODI_Serise_Protocol.Serise_Send_Protocol_RecvBuff[5]=0x00;
										LoginController1.Serise_Main.AODI_Serise_Protocol.Serise_Send_Protocol_RecvBuff[6]=0x07;
										//@-CAN-DATA(8Bytes)-�̵���2
										LoginController1.Serise_Main.AODI_Serise_Protocol.Serise_Send_Protocol_RecvBuff[7]=0x00;
										LoginController1.Serise_Main.AODI_Serise_Protocol.Serise_Send_Protocol_RecvBuff[8]=0x00;
										//@-CAN-DATA(8Bytes)-�̵���3
										LoginController1.Serise_Main.AODI_Serise_Protocol.Serise_Send_Protocol_RecvBuff[9]=0x00;
										LoginController1.Serise_Main.AODI_Serise_Protocol.Serise_Send_Protocol_RecvBuff[10]=0x00;
										//@-CAN-DATA(8Bytes)-�̵���4
										LoginController1.Serise_Main.AODI_Serise_Protocol.Serise_Send_Protocol_RecvBuff[11]=0x00;
										LoginController1.Serise_Main.AODI_Serise_Protocol.Serise_Send_Protocol_RecvBuff[12]=0x00;
										break;
										
								default :break;
							}
							
							System.out.println("send power switch");
							
							//@-���ڷ�������
							LoginController1.Serise_Main.SerWrite_Hex(LoginController1.Serise_Main.AODI_Serise_Protocol.Serise_Send_Protocol_RecvBuff);
						}
						else if(LoginController1.Charging_PowerSend_Flag==false)
						{
							//if(LoginController1.ChargeVoltage_StartFlag==true)
							//{
								//@-����Э��ͷ
								LoginController1.Serise_Main.AODI_Serise_Protocol.Serise_Send_Protocol_RecvBuff[0]=LoginController1.Serise_Main.AODI_Serise_Protocol.Serise_SendFrame_StartByte;
								//@-CAN-ID(4Bytes)-DA���
								LoginController1.Serise_Main.AODI_Serise_Protocol.Serise_Send_Protocol_RecvBuff[1]=0x00;
								LoginController1.Serise_Main.AODI_Serise_Protocol.Serise_Send_Protocol_RecvBuff[2]=0x00;
								LoginController1.Serise_Main.AODI_Serise_Protocol.Serise_Send_Protocol_RecvBuff[3]=0x01;
								LoginController1.Serise_Main.AODI_Serise_Protocol.Serise_Send_Protocol_RecvBuff[4]=0x20;
								//@-CAN-DATA(8Bytes)-DA0
								float DA = (float)((LoginController1.ControlVoltage_Current/0.024606299212598425196850393700787));
								int DA1 = (int)DA;
								LoginController1.Serise_Main.AODI_Serise_Protocol.Serise_Send_Protocol_RecvBuff[5]=(byte)(((DA1&0xffff)&0xff00)>>8);
								LoginController1.Serise_Main.AODI_Serise_Protocol.Serise_Send_Protocol_RecvBuff[6]=(byte)(((DA1&0xffff)&0x00ff));

//								System.out.println("DA:"+DA);
//
//								System.out.println("[5]:"+LoginController1.Serise_Main.AODI_Serise_Protocol.Serise_Send_Protocol_RecvBuff[5]
//										          +"[6]:"+LoginController1.Serise_Main.AODI_Serise_Protocol.Serise_Send_Protocol_RecvBuff[6]);
//							
								//@-���ڷ�������
								LoginController1.Serise_Main.SerWrite_Hex(LoginController1.Serise_Main.AODI_Serise_Protocol.Serise_Send_Protocol_RecvBuff);
							//}
						}
	
					}
				}
				//@-PCB�����&���ʱ���ʱ1times/s
				if(DisplayUpdata_Timer4==tick_count/1)
				{
					DisplayUpdata_Timer4=0;
					
					//@-PCB�����
					if(LoginController1.Serise_Main.Recv_FrameOK_Count_Copy!=LoginController1.Serise_Main.Recv_FrameOK_Count)
					{
						LoginController1.Serise_Main.Recv_FrameOK_Count_Copy=LoginController1.Serise_Main.Recv_FrameOK_Count;
						
						LoginController1.PCB_Status=true;
					}
					else
					{						
						LoginController1.PCB_Status=false;  
					}
					
					//@-���ʱ���ʱ
					if(LoginController1.ChargeVoltage_StartFlag==true)
					{
						LoginController1.ChargeVoltage_RunTimeSecond=LoginController1.ChargeVoltage_RunTimeSecond+1;
						if(LoginController1.ChargeVoltage_RunTimeSecond==60)
						{
							LoginController1.ChargeVoltage_RunTimeSecond=0;
							LoginController1.ChargeVoltage_RunTimeMin=LoginController1.ChargeVoltage_RunTimeMin+1;
							if(LoginController1.ChargeVoltage_RunTimeMin==60)
							{
								LoginController1.ChargeVoltage_RunTimeMin=0;
								LoginController1.ChargeVoltage_RunTimeHour=LoginController1.ChargeVoltage_RunTimeHour+1;
							}
						}
					}
					else if(LoginController1.ChargeVoltage_StartFlag==false)
					{
						LoginController1.ChargeVoltage_RunTimeSecond=0;
						LoginController1.ChargeVoltage_RunTimeMin=0;
						LoginController1.ChargeVoltage_RunTimeHour=0;
					}
				}
				//@-������ʱ���ʱ10times/1s
				if(DisplayUpdata_Timer6==(tick_count/10))
				{
					DisplayUpdata_Timer6 = 0;
					
					//System.out.println("ll");

					//@-������
					Charging_Strategic();
					
				}
				
			}
	    	//@2-����������������
	    	else if(ScreensFramework.App_Page==2)
	    	{
	
	    	}

		}
		
		

		//@4-1���ʱ
		if(second_1s_tick==tick_count)   //1s
		{
			second_1s_tick=0;
			
			//ϵͳ����
			//Toolkit.getDefaultToolkit().beep();
			
			//@-1����˸flag
			if(second_1s_flash==false)
			second_1s_flash=true;
			else if(second_1s_flash==true)
			second_1s_flash=false;
			
			
			//@6-ˢ��ʱ��
	    	local_time = Calendar.getInstance();
			
			//@-ϵͳʱ��
	    	Time_Str = new String(""+local_time.get(Calendar.YEAR)+"/"
					+formater_value.format(local_time.get(Calendar.MONTH)+1)+"/"
					+formater_value.format(local_time.get(Calendar.DATE))+"  "
					+formater_value.format(local_time.get(Calendar.HOUR_OF_DAY))+":"
					+formater_value.format(local_time.get(Calendar.MINUTE))+":"
					+formater_value.format(local_time.get(Calendar.SECOND)));
	    	
	    	//@-��Ϣ���ʱ��
	    	Time_Str_Info = new String(""+formater_value.format(local_time.get(Calendar.HOUR_OF_DAY))+":"
											+formater_value.format(local_time.get(Calendar.MINUTE))+":"
											+formater_value.format(local_time.get(Calendar.SECOND)));

			//@-����������ʾʱ�����
			if((lock_3s==false)||(lock_start_main==true))
			second_3s_tick=second_3s_tick+1;
			if((second_3s_tick==3)&&(lock_3s==false)||(second_3s_tick==3)&&(lock_start_main==true))
			{
				//@-������Ч
				if(lock_3s==false)
				{
					lock_3s=true;
					LoginController.RecCountProperty_Main.set("change");
				}
				
				//@-����������
				if(lock_start_main==true)
				{
					lock_start_main=false;
					ScreensFramework.PageChange.set("main");
				}
				
				second_3s_tick=0;
			}

			//@-��������ʱ��ʱ�ж�
			if((LoginController1.ControlVoltage_StageLimt_Flag==true)&&(LoginController1.ControlVoltage_Timeout_Flag==false))
			{
				//@-�ж��Ƿ�ʱ
				if(DisplayUpdata_Timer5>=LoginController1.VoltageEnd_Delay)
				{
					LoginController1.ControlVoltage_Timeout_Flag=true;
					System.out.println("time out!");
				}
				else
				{
					DisplayUpdata_Timer5 = DisplayUpdata_Timer5+1;
				}
			}
						
		}
	}
	
	/**�����Ƶ�ѹ���
	 * 
	 */
	private void ChargingControl_VoltageOut()
	{
		//@-������б�־&&PCB����
		if((LoginController1.ChargeVoltage_StartFlag==true)&&(LoginController1.PCB_Status_Copy==true))
		{
			//@1-�����Ƶ�ѹ�׶����ޱ�־
			if(LoginController1.ControlVoltage_StageLimt_Flag==false)
			{
				//@-�����Ƶ�ѹ<�׶ο��Ƶ�ѹ���ޣ����Ƶ�ѹ��������
				if((new Float(LoginController1.ControlVoltage_Current_Copy).compareTo(new Float(LoginController1.VoltageEnd))<=0))
				{
					LoginController1.ControlVoltage_Current_Copy=(float)((LoginController1.ControlVoltage_Current_Copy+(float)(LoginController1.VoltageStep/10)));
					LoginController1.ControlVoltage_Current=LoginController1.ControlVoltage_Current_Copy;
				}
				else
				{
					//@-�����Ϣ
					InfoPut(3,"���Ƶ�ѹ�Դﵽ�׶�����");
					LoginController1.ControlVoltage_StageLimt_Flag=true;
				}
			}
		}
	}
	
	/**������
	 * 
	 * @param
	 */
	private void Charging_Strategic()
	{
		
		//@-������б�־&&PCB����
		if((LoginController1.ChargeVoltage_StartFlag==true)&&(LoginController1.PCB_Status_Copy==true))
		{			
			//@-û�г�ʱ�������ж�
			if(LoginController1.ControlVoltage_Timeout_Flag==false)
			{
				//@-����ѹ���������ѹ
				if((new Float(LoginController1.ControlVoltage_Current).compareTo(new Float(LoginController1.Stage_Voltage_Start))>=0)
				 &&(new Float(LoginController1.ControlVoltage_Current).compareTo(new Float(LoginController1.Stage_Voltage_End))<=0))
				{
				
					//@-�ж��״ν�������
					if(LoginController1.Stage_InFlag==false)
					{
						LoginController1.Stage_InFlag=true;
						InfoPut(1,"����׶�"+LoginController1.Strategy_Stage);
					}
					
					//@-�������Ƿ��ڵ���������
					if((new Float(LoginController1.OutputCurrent_Current_Copy).compareTo(new Float(LoginController1.Stage_Current_Start))>=0)
					 &&(new Float(LoginController1.OutputCurrent_Current_Copy).compareTo(new Float(LoginController1.Stage_Current_End))<=0))
					{
						LoginController1.Stage_Succeed_Count=LoginController1.Stage_Succeed_Count+1;
						//System.out.println("S");
					}
					else
					{
						LoginController1.Stage_Fail_Count=LoginController1.Stage_Fail_Count+1;
						//System.out.println("F");
					}
				}
				else if((new Float(LoginController1.ControlVoltage_Current).compareTo(new Float(LoginController1.Stage_Voltage_End))>0))
				{
					//@-�ж��״��뿪����
					if(LoginController1.Stage_OutFlag==false)
					{
						LoginController1.Stage_OutFlag=true;
						InfoPut(1,"�뿪�׶�"+LoginController1.Strategy_Stage);
					}
					
					//@-�жϽ׶��Ƿ�ɹ�
					Check_Percent();
				}	
				
			}
			//@-�ѳ�ʱ���澯
			else if(LoginController1.ControlVoltage_Timeout_Flag==true)
			{
				//@-�澯&&&&&&&&&&&&&&&&&&&&&&&
				InfoPut(2,"��ⳬʱ");
				LoginController1.StrategyStage_Main.setValue("Strategy_Error");
			}			
		}

		//@-���µ���ֵ
		LoginController1.OutputCurrent_Current_Copy=LoginController1.OutputCurrent_Current;
		
	}
	
	/**����ɹ��ٷֱ�
	 * 
	 */
	private void Check_Percent()
	{
		int total;
		
		total=LoginController1.Stage_Succeed_Count+LoginController1.Stage_Fail_Count;
		
		if(total>0)
		{
			//@-����׶γɹ��ٷֱ�
			LoginController1.Stage_Succeed=(LoginController1.Stage_Succeed_Count/(total)*100);
			//@-�жϽ׶��Ƿ�ɹ�
			if((new Float(LoginController1.Stage_Succeed).compareTo(new Float(60.0))>=0))
			{
				if(LoginController1.Strategy_Stage==1)
				{
					LoginController1.Strategy_Stage=LoginController1.Strategy_Stage+1;
			    	if(LoginController1.VoltageArea2_Sel==true)
			    	{
			    		LoginController1.StrategyStage_Main.setValue("Stage2");
			    		InfoPut(1,"�л����׶�2");
			    	}
			    	else
			    	{
						LoginController1.Strategy_Stage=LoginController1.Strategy_Stage+1;
				    	if(LoginController1.VoltageArea3_Sel==true)
				    	{
				    		LoginController1.StrategyStage_Main.setValue("Stage3");
				    		InfoPut(1,"�л����׶�3");
				    	}
				    	else
				    	{
							LoginController1.Strategy_Stage=LoginController1.Strategy_Stage+1;
					    	if(LoginController1.VoltageArea4_Sel==true)
					    	{
					    		LoginController1.StrategyStage_Main.setValue("Stage4");
					    		InfoPut(1,"�л����׶�4");
					    	}
					    	else 
					    	{
					    		LoginController1.StrategyStage_Main.setValue("Strategy_OK");
					    		InfoPut(1,"�����������");
					    	}
				    	}
			    	}
				}
				else if(LoginController1.Strategy_Stage==2)
				{
					LoginController1.Strategy_Stage=LoginController1.Strategy_Stage+1;
			    	if(LoginController1.VoltageArea3_Sel==true)
			    	{
			    		LoginController1.StrategyStage_Main.setValue("Stage3");
			    		InfoPut(1,"�л����׶�3");
			    	}
			    	else
			    	{
						LoginController1.Strategy_Stage=LoginController1.Strategy_Stage+1;
				    	if(LoginController1.VoltageArea4_Sel==true)
				    	{
				    		LoginController1.StrategyStage_Main.setValue("Stage4");
				    		InfoPut(1,"�л����׶�4");
				    	}
				    	else 
				    	{
				    		LoginController1.StrategyStage_Main.setValue("Strategy_OK");
				    		InfoPut(1,"�����������");
				    	}
			    	}
				}
				else if(LoginController1.Strategy_Stage==3)
				{
					LoginController1.Strategy_Stage=LoginController1.Strategy_Stage+1;
			    	if(LoginController1.VoltageArea4_Sel==true)
			    	{
			    		LoginController1.StrategyStage_Main.setValue("Stage4");
			    		InfoPut(1,"�л����׶�4");
			    	}
			    	else
			    	{
			    		LoginController1.StrategyStage_Main.setValue("Strategy_OK");
			    		InfoPut(1,"�����������");
			    	}
				}
				else if(LoginController1.Strategy_Stage==4)
				{
			    	LoginController1.StrategyStage_Main.setValue("Strategy_OK");
			    	InfoPut(1,"�����������");
				}
			}
			else
			{
				//�ȴ���4�׶γ�ʱ
				if(LoginController1.Strategy_Stage==4)
				{
					if(LoginController1.ControlVoltage_Timeout_Flag==true)
					{
						LoginController1.StrategyStage_Main.setValue("Strategy_Error");
						InfoPut(3,"���ʧ��");
					}
					System.out.println("out check!");
				}	
				else
				{
					LoginController1.StrategyStage_Main.setValue("Strategy_Error");
					InfoPut(3,"���ʧ��");
				}
			}
		}
		else
		{
			InfoPut(3,"����"+LoginController1.Strategy_Stage+"���õ�����̫С�����ܼ��");
		}
		
		//System.out.println("Stage_Succeed:"+LoginController1.Stage_Succeed+"%");
	}
	
	/**
	 * 
	 * @param Mode Message
	 */
	public static void InfoPut(int Mode,String Message)
	{

		//@-��Ϣ����
		Message_Type=Mode;
		
		//@-��Ϣ���������1
		MessagePut_Count=MessagePut_Count+1;
		//@-��ʽ����Ϣ��Ϣ
		InfoMessage=new String("@"+MessagePut_Count+"-"+Message+"-"+Time_Str_Info+"\n");	
		//@-����������
		synchronized(Data_InfoType)
		{
			Data_InfoType.add(Message_Type);
		}
		synchronized(Data_InfoMessage)
		{
			Data_InfoMessage.add(InfoMessage);
		}
	}
	
	
	/**��ֵ�Ƚ�&���ޱȽ�
	 * 
	 */
    private void Check_Num()
    {
    	  //----------------------------------------------��ֵ-----------------------------------------------------------
	      //@1-�����ѹ���ֵ
		  if((new Float(LoginController1.OutputVoltage_Current).compareTo(new Float(LoginController1.OutputVoltage_MAX))>0))
		  {
	  		LoginController1.OutputVoltage_MAX=LoginController1.OutputVoltage_Current;  
		  }
    	  //@2-�����ѹ��Сֵ
    	  if((new Float(LoginController1.OutputVoltage_Current).compareTo(new Float(LoginController1.OutputVoltage_MIN))<0))
    	  {
    		LoginController1.OutputVoltage_MIN=LoginController1.OutputVoltage_Current;
    	  }
    	
	      //@3-ĸ�ߵ�ѹ���ֵ
		  if((new Float(LoginController1.MainVoltage_Current).compareTo(new Float(LoginController1.MainVoltage_MAX))>0))
		  {
	  		LoginController1.MainVoltage_MAX=LoginController1.MainVoltage_Current;  
		  }
    	  //@4-ĸ�ߵ�ѹ��Сֵ
    	  if((new Float(LoginController1.MainVoltage_Current).compareTo(new Float(LoginController1.MainVoltage_MIN))<0))
    	  {
    		LoginController1.MainVoltage_MIN=LoginController1.MainVoltage_Current;
    	  }
    	
	      //@5-����������ֵ
		  if((new Float(LoginController1.OutputCurrent_Current).compareTo(new Float(LoginController1.OutputCurrent_MAX))>0))
		  {
	  		LoginController1.OutputCurrent_MAX=LoginController1.OutputCurrent_Current;  
		  }
    	  //@6-���������Сֵ
    	  if((new Float(LoginController1.OutputCurrent_Current).compareTo(new Float(LoginController1.OutputCurrent_MIN))<0))
    	  {
    		LoginController1.OutputCurrent_MIN=LoginController1.OutputCurrent_Current;
    	  }
    	  
	      //@7-����¶����ֵ
		  if((new Float(LoginController1.Temperature_Current).compareTo(new Float(LoginController1.Temperature_MAX))>0))
		  {
	  		LoginController1.Temperature_MAX=LoginController1.Temperature_Current;  
		  }
    	  //@8-����¶���Сֵ
    	  if((new Float(LoginController1.Temperature_Current).compareTo(new Float(LoginController1.Temperature_MIN))<0))
    	  {
    		LoginController1.Temperature_MIN=LoginController1.Temperature_Current;
    	  }
    	
    	//----------------------------------------------������-----------------------------------------------------------
    	  //@9-�����ѹ���޼��
    	  if(LoginController1.OptionSet_OutputVoltage_UpLimit_Flag==true)
    	  {
    		  //@��ǰֵС���ϼ���-����
    		  if((new Float(LoginController1.OutputVoltage_Current).compareTo(new Float(LoginController1.OptionSet_OutputVoltage_UpLimit))<0))
    		  {
    			  LoginController1.OutputVoltage_Up_LimitFlag=0;    			  
    		  }
    		  //@��ǰֵ�����ϼ���-����
    		  else if((new Float(LoginController1.OutputVoltage_Current).compareTo(new Float(LoginController1.OptionSet_OutputVoltage_UpLimit))>0))
    		  {
    			  LoginController1.OutputVoltage_Up_LimitFlag=1;
    		  }
    		  //@��ǰֵ�����ϼ���-�ٽ�
    		  else if((new Float(LoginController1.OutputVoltage_Current).compareTo(new Float(LoginController1.OptionSet_OutputVoltage_UpLimit))==0))
    		  {
    			  LoginController1.OutputVoltage_Up_LimitFlag=2;
    		  }
    	  }
    	  //@10-�����ѹ���޼��
    	  if(LoginController1.OptionSet_OutputVoltage_DownLimit_Flag==true)
    	  {
    		  //@��ǰֵ�����¼���-����
    		  if((new Float(LoginController1.OutputVoltage_Current).compareTo(new Float(LoginController1.OptionSet_OutputVoltage_DownLimit))>0))
    		  {
    			  LoginController1.OutputVoltage_Down_LimitFlag=0;
    		  }
    		  //@��ǰֵС���¼���-����
    		  else if((new Float(LoginController1.OutputVoltage_Current).compareTo(new Float(LoginController1.OptionSet_OutputVoltage_DownLimit))<0))
    		  {
    			  LoginController1.OutputVoltage_Down_LimitFlag=1;
    		  }
    		  //@��ǰֵ�����¼���-�ٽ�
    		  else if((new Float(LoginController1.OutputVoltage_Current).compareTo(new Float(LoginController1.OptionSet_OutputVoltage_DownLimit))==0))
    		  {
    			  LoginController1.OutputVoltage_Down_LimitFlag=2;
    		  }
    	  }
    	  
    	  //@11-ĸ�ߵ�ѹ���޼��
    	  if(LoginController1.OptionSet_MainVoltage_UpLimit_Flag==true)
    	  {
    		  //@��ǰֵС���ϼ���-����
    		  if((new Float(LoginController1.MainVoltage_Current).compareTo(new Float(LoginController1.OptionSet_MainVoltage_UpLimit))<0))
    		  {
    			  LoginController1.MainVoltage_Up_LimitFlag=0;    			  
    		  }
    		  //@��ǰֵ�����ϼ���-����
    		  else if((new Float(LoginController1.MainVoltage_Current).compareTo(new Float(LoginController1.OptionSet_MainVoltage_UpLimit))>0))
    		  {
    			  LoginController1.MainVoltage_Up_LimitFlag=1;
    		  }
    		  //@��ǰֵ�����ϼ���-�ٽ�
    		  else if((new Float(LoginController1.MainVoltage_Current).compareTo(new Float(LoginController1.OptionSet_MainVoltage_UpLimit))==0))
    		  {
    			  LoginController1.MainVoltage_Up_LimitFlag=2;
    		  }
    	  }
    	  //@12-ĸ�ߵ�ѹ���޼��
    	  if(LoginController1.OptionSet_MainVoltage_DownLimit_Flag==true)
    	  {
    		  //@��ǰֵ�����¼���-����
    		  if((new Float(LoginController1.MainVoltage_Current).compareTo(new Float(LoginController1.OptionSet_MainVoltage_DownLimit))>0))
    		  {
    			  LoginController1.MainVoltage_Down_LimitFlag=0;
    		  }
    		  //@��ǰֵС���¼���-����
    		  else if((new Float(LoginController1.MainVoltage_Current).compareTo(new Float(LoginController1.OptionSet_MainVoltage_DownLimit))<0))
    		  {
    			  LoginController1.MainVoltage_Down_LimitFlag=1;
    		  }
    		  //@��ǰֵ�����¼���-�ٽ�
    		  else if((new Float(LoginController1.MainVoltage_Current).compareTo(new Float(LoginController1.OptionSet_MainVoltage_DownLimit))==0))
    		  {
    			  LoginController1.MainVoltage_Down_LimitFlag=2;
    		  }
    	  }
    	  
    	  //@13-����������޼��
    	  if(LoginController1.OptionSet_OutputCurrent_UpLimit_Flag==true)
    	  {
    		  //@��ǰֵС���ϼ���-����
    		  if((new Float(LoginController1.OutputCurrent_Current).compareTo(new Float(LoginController1.OptionSet_OutputCurrent_UpLimit))<0))
    		  {
    			  LoginController1.OutputCurrent_Up_LimitFlag=0;    			  
    		  }
    		  //@��ǰֵ�����ϼ���-����
    		  else if((new Float(LoginController1.OutputCurrent_Current).compareTo(new Float(LoginController1.OptionSet_OutputCurrent_UpLimit))>0))
    		  {
    			  LoginController1.OutputCurrent_Up_LimitFlag=1;
    		  }
    		  //@��ǰֵ�����ϼ���-�ٽ�
    		  else if((new Float(LoginController1.OutputCurrent_Current).compareTo(new Float(LoginController1.OptionSet_OutputCurrent_UpLimit))==0))
    		  {
    			  LoginController1.OutputCurrent_Up_LimitFlag=2;
    		  }
    		  
    		  //System.out.println(""+LoginController1.OptionSet_OutputCurrent_UpLimit);
    	  }
    	  //@14-����������޼��
    	  if(LoginController1.OptionSet_OutputCurrent_DownLimit_Flag==true)
    	  {
    		  //@��ǰֵ�����¼���-����
    		  if((new Float(LoginController1.OutputCurrent_Current).compareTo(new Float(LoginController1.OptionSet_OutputCurrent_DownLimit))>0))
    		  {
    			  LoginController1.OutputCurrent_Down_LimitFlag=0;
    		  }
    		  //@��ǰֵС���¼���-����
    		  else if((new Float(LoginController1.OutputCurrent_Current).compareTo(new Float(LoginController1.OptionSet_OutputCurrent_DownLimit))<0))
    		  {
    			  LoginController1.OutputCurrent_Down_LimitFlag=1;
    		  }
    		  //@��ǰֵ�����¼���-�ٽ�
    		  else if((new Float(LoginController1.OutputCurrent_Current).compareTo(new Float(LoginController1.OptionSet_OutputCurrent_DownLimit))==0))
    		  {
    			  LoginController1.OutputCurrent_Down_LimitFlag=2;
    		  }
    	  }
    	  
    	  //@15-����¶����޼��
    	  if(LoginController1.OptionSet_Temperature_UpLimit_Flag==true)
    	  {
    		  //@��ǰֵС���ϼ���-����
    		  if((new Float(LoginController1.Temperature_Current).compareTo(new Float(LoginController1.OptionSet_Temperature_UpLimit))<0))
    		  {
    			  LoginController1.Temperature_Up_LimitFlag=0;    			  
    		  }
    		  //@��ǰֵ�����ϼ���-����
    		  else if((new Float(LoginController1.Temperature_Current).compareTo(new Float(LoginController1.OptionSet_Temperature_UpLimit))>0))
    		  {
    			  LoginController1.Temperature_Up_LimitFlag=1;
    		  }
    		  //@��ǰֵ�����ϼ���-�ٽ�
    		  else if((new Float(LoginController1.Temperature_Current).compareTo(new Float(LoginController1.OptionSet_Temperature_UpLimit))==0))
    		  {
    			  LoginController1.Temperature_Up_LimitFlag=2;
    		  }
    	  }
    	  //@16-����¶����޼��
    	  if(LoginController1.OptionSet_Temperature_DownLimit_Flag==true)
    	  {
    		  //@��ǰֵ�����¼���-����
    		  if((new Float(LoginController1.Temperature_Current).compareTo(new Float(LoginController1.OptionSet_Temperature_DownLimit))>0))
    		  {
    			  LoginController1.Temperature_Down_LimitFlag=0;
    		  }
    		  //@��ǰֵС���¼���-����
    		  else if((new Float(LoginController1.Temperature_Current).compareTo(new Float(LoginController1.OptionSet_Temperature_DownLimit))<0))
    		  {
    			  LoginController1.Temperature_Down_LimitFlag=1;
    		  }
    		  //@��ǰֵ�����¼���-�ٽ�
    		  else if((new Float(LoginController1.Temperature_Current).compareTo(new Float(LoginController1.OptionSet_Temperature_DownLimit))==0))
    		  {
    			  LoginController1.Temperature_Down_LimitFlag=2;
    		  }
    	  }
    	  
    	
    }
	
	    
}
                    
