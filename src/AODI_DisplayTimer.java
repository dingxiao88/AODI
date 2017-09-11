

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
		
	//@1-系统计时
	private double system_tick=0;
	//@2-1秒计时
	private double second_1s_tick=0;
	//@-1秒闪烁flag
	public static boolean second_1s_flash=false;
	//@3-界面显示定时器1
	private int    DisplayUpdata_Timer1=0;
	//@3-界面显示定时器2
	private int    DisplayUpdata_Timer2=0;
	//@3-功能监测定时器3
	private int    DisplayUpdata_Timer3=0;
	//@3-PCB存活监测定时器4
	private int    DisplayUpdata_Timer4=0;
	//@3-充电延时定时器5
	public static  int    DisplayUpdata_Timer5=0;
	//@3-策略定时器6
	private int    DisplayUpdata_Timer6=0;
	//@3-时间定时器
    private static Timer My_timer;    
    //@4-时间定时器定时值
    private int My_Time_Value=0;
    //@5-系统时间获取接口
	private static Calendar local_time;
	//@6-显示数据格式
	public static java.text.NumberFormat  formater_value  =  java.text.DecimalFormat.getInstance();  //显示小数格式化
	//@7-定时器运行标志
	public static int Timer_RunFlag=1;  //1:run  2:close
	
	public static String Time_Str=new String("----");
	public static String Time_Str_Info=new String("----");
	
	private int i=0;
	
	//@2-1秒计时
	private static double second_3s_tick=0;
	private static boolean lock_3s=false;
	public static boolean lock_start_main=false;

	
	/*------使用非FX原生task方法-----------*/
	public static Task<Integer> task_dis; 
    private Thread Display_Thread;
        
    private int tick_count=0;
    
    
    //@-信息消息队列
    public static List<String> Data_InfoMessage = Collections.synchronizedList(new ArrayList<String>());
    //@-信息消息队列
    public static List<Integer> Data_InfoType = Collections.synchronizedList(new ArrayList<Integer>());
    //@-信息消息
    public static String  InfoMessage;
    //@-信息消息输出计数
    public static int MessagePut_Count=0;
    //@-信息消息类型
    public static int Message_Type=0;  //1:ok  2:error 3:warning 

	
	



   	
	
	/**构建时间定时器
	 * 
	 * @param delayTime1
	 */
	public AODI_DisplayTimer(int delayTime1){
		
		//@1-接收定时器定时时间
    	My_Time_Value = delayTime1;
    	//@2-换算1秒定时值
    	tick_count = 1000/My_Time_Value;
    	
 /*----------------------------使用非FX原生task方法---------------------------*/   	
//    	//@3-创建定时器实例
//    	My_timer = new Timer();
//    	//@4-启动定时器调度定时任务
//    	My_timer.schedule(new UpDisplay_Task(), My_Time_Value);
    	
		formater_value.setMaximumIntegerDigits(2);
		formater_value.setMinimumIntegerDigits(2);
		
		
		
	    /*-------------使用FX原生task方法---------------------*/
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
		
		
		if(ScreensFramework.Main_Falg==true)
		{			
	    	//@1-程序运行在主界面
	    	if(ScreensFramework.App_Page==1)
	    	{	
	    		DisplayUpdata_Timer1=DisplayUpdata_Timer1+1; //20times/s
	    		
	    		DisplayUpdata_Timer2=DisplayUpdata_Timer2+1; //?times/s
	    		
	    		DisplayUpdata_Timer3=DisplayUpdata_Timer3+1; //?times/s
	    		
	    		DisplayUpdata_Timer4=DisplayUpdata_Timer4+1; //?times/s
	    		
	    		DisplayUpdata_Timer6=DisplayUpdata_Timer6+1; //?times/s
	    		
	    		//@-界面刷新20times/s
				if(DisplayUpdata_Timer1==tick_count/10)
				{
					DisplayUpdata_Timer1=0;
					
					//@-充电控制电压输出
					ChargingControl_VoltageOut();
					
					//@-debug
//					LoginController1.OutputVoltage_Current=(float)Math.random();
//					LoginController1.OutputCurrent_Current=new Float(Math.sin(40*2*3.14*system_tick)*1);
//					LoginController1.MainVoltage_Current=(float)Math.random();
//					LoginController1.Temperature_Current=(float)Math.random();
					
					//@-解算数据填入
					LoginController1.OutputVoltage_Current=LoginController1.Serise_Main.Recv_Data_OutputVoltage;
					LoginController1.OutputCurrent_Current=LoginController1.Serise_Main.Recv_Data_OutputCurrent;
					LoginController1.MainVoltage_Current=LoginController1.Serise_Main.Recv_Data_MainVoltage;
					LoginController1.Temperature_Current=LoginController1.Serise_Main.Recv_Data_Temperature;
					
					//@-曲线运行标志
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
	    		//@-界面刷新&最值比较&极限比较5times/s
				if(DisplayUpdata_Timer2==tick_count/5)
				{
					DisplayUpdata_Timer2=0;
					
					//@-显示更新标志
					if(LoginController1.DisplayUpdata_Flag==false)
					LoginController1.DisplayUpdata_Flag=true;
					else if(LoginController1.DisplayUpdata_Flag==true)
					LoginController1.DisplayUpdata_Flag=false;
					
					//@-最值比较&极限比较
					Check_Num();
					
					LoginController1.DisplayUpdataProperty_Main.setValue(""+LoginController1.DisplayUpdata_Flag);
				}
				//@-串口协议发送10times/s
				if(DisplayUpdata_Timer3==tick_count/10)
				{
					DisplayUpdata_Timer3=0;
										
					//@-判断串口已打开&充电启动
					if((LoginController1.Serise_Main.isOpen==true))
					{						
						//@-继电器控制
						if(LoginController1.Charging_PowerSend_Flag==true)
						{
							LoginController1.Charging_PowerSend_Flag=false;
							//@-串口协议头
							LoginController1.Serise_Main.AODI_Serise_Protocol.Serise_Send_Protocol_RecvBuff[0]=LoginController1.Serise_Main.AODI_Serise_Protocol.Serise_SendFrame_StartByte;
							//@-CAN-ID(4Bytes)-继电器控制
							LoginController1.Serise_Main.AODI_Serise_Protocol.Serise_Send_Protocol_RecvBuff[1]=0x00;
							LoginController1.Serise_Main.AODI_Serise_Protocol.Serise_Send_Protocol_RecvBuff[2]=0x00;
							LoginController1.Serise_Main.AODI_Serise_Protocol.Serise_Send_Protocol_RecvBuff[3]=0x01;
							LoginController1.Serise_Main.AODI_Serise_Protocol.Serise_Send_Protocol_RecvBuff[4]=0x19;
							
							switch(LoginController1.Charging_Power_Sel)
							{
							    //@-所有继电器关
								case 0:
										//@-CAN-DATA(8Bytes)-继电器1
										LoginController1.Serise_Main.AODI_Serise_Protocol.Serise_Send_Protocol_RecvBuff[5]=0x00;
										LoginController1.Serise_Main.AODI_Serise_Protocol.Serise_Send_Protocol_RecvBuff[6]=0x00;
										//@-CAN-DATA(8Bytes)-继电器2
										LoginController1.Serise_Main.AODI_Serise_Protocol.Serise_Send_Protocol_RecvBuff[7]=0x00;
										LoginController1.Serise_Main.AODI_Serise_Protocol.Serise_Send_Protocol_RecvBuff[8]=0x00;
										//@-CAN-DATA(8Bytes)-继电器3
										LoginController1.Serise_Main.AODI_Serise_Protocol.Serise_Send_Protocol_RecvBuff[9]=0x00;
										LoginController1.Serise_Main.AODI_Serise_Protocol.Serise_Send_Protocol_RecvBuff[10]=0x00;
										//@-CAN-DATA(8Bytes)-继电器4
										LoginController1.Serise_Main.AODI_Serise_Protocol.Serise_Send_Protocol_RecvBuff[11]=0x00;
										LoginController1.Serise_Main.AODI_Serise_Protocol.Serise_Send_Protocol_RecvBuff[12]=0x00;
										break;
							    //@-110V
								case 1:
										//@-CAN-DATA(8Bytes)-继电器1
										LoginController1.Serise_Main.AODI_Serise_Protocol.Serise_Send_Protocol_RecvBuff[5]=0x00;
										LoginController1.Serise_Main.AODI_Serise_Protocol.Serise_Send_Protocol_RecvBuff[6]=0x04;
										//@-CAN-DATA(8Bytes)-继电器2
										LoginController1.Serise_Main.AODI_Serise_Protocol.Serise_Send_Protocol_RecvBuff[7]=0x00;
										LoginController1.Serise_Main.AODI_Serise_Protocol.Serise_Send_Protocol_RecvBuff[8]=0x00;
										//@-CAN-DATA(8Bytes)-继电器3
										LoginController1.Serise_Main.AODI_Serise_Protocol.Serise_Send_Protocol_RecvBuff[9]=0x00;
										LoginController1.Serise_Main.AODI_Serise_Protocol.Serise_Send_Protocol_RecvBuff[10]=0x00;
										//@-CAN-DATA(8Bytes)-继电器4
										LoginController1.Serise_Main.AODI_Serise_Protocol.Serise_Send_Protocol_RecvBuff[11]=0x00;
										LoginController1.Serise_Main.AODI_Serise_Protocol.Serise_Send_Protocol_RecvBuff[12]=0x00;
										break;
							    //@-220V
								case 2:
										//@-CAN-DATA(8Bytes)-继电器1
										LoginController1.Serise_Main.AODI_Serise_Protocol.Serise_Send_Protocol_RecvBuff[5]=0x00;
										LoginController1.Serise_Main.AODI_Serise_Protocol.Serise_Send_Protocol_RecvBuff[6]=0x06;
										//@-CAN-DATA(8Bytes)-继电器2
										LoginController1.Serise_Main.AODI_Serise_Protocol.Serise_Send_Protocol_RecvBuff[7]=0x00;
										LoginController1.Serise_Main.AODI_Serise_Protocol.Serise_Send_Protocol_RecvBuff[8]=0x00;
										//@-CAN-DATA(8Bytes)-继电器3
										LoginController1.Serise_Main.AODI_Serise_Protocol.Serise_Send_Protocol_RecvBuff[9]=0x00;
										LoginController1.Serise_Main.AODI_Serise_Protocol.Serise_Send_Protocol_RecvBuff[10]=0x00;
										//@-CAN-DATA(8Bytes)-继电器4
										LoginController1.Serise_Main.AODI_Serise_Protocol.Serise_Send_Protocol_RecvBuff[11]=0x00;
										LoginController1.Serise_Main.AODI_Serise_Protocol.Serise_Send_Protocol_RecvBuff[12]=0x00;
										break;
							    //@-260V
								case 3:
										//@-CAN-DATA(8Bytes)-继电器1
										LoginController1.Serise_Main.AODI_Serise_Protocol.Serise_Send_Protocol_RecvBuff[5]=0x00;
										LoginController1.Serise_Main.AODI_Serise_Protocol.Serise_Send_Protocol_RecvBuff[6]=0x07;
										//@-CAN-DATA(8Bytes)-继电器2
										LoginController1.Serise_Main.AODI_Serise_Protocol.Serise_Send_Protocol_RecvBuff[7]=0x00;
										LoginController1.Serise_Main.AODI_Serise_Protocol.Serise_Send_Protocol_RecvBuff[8]=0x00;
										//@-CAN-DATA(8Bytes)-继电器3
										LoginController1.Serise_Main.AODI_Serise_Protocol.Serise_Send_Protocol_RecvBuff[9]=0x00;
										LoginController1.Serise_Main.AODI_Serise_Protocol.Serise_Send_Protocol_RecvBuff[10]=0x00;
										//@-CAN-DATA(8Bytes)-继电器4
										LoginController1.Serise_Main.AODI_Serise_Protocol.Serise_Send_Protocol_RecvBuff[11]=0x00;
										LoginController1.Serise_Main.AODI_Serise_Protocol.Serise_Send_Protocol_RecvBuff[12]=0x00;
										break;
										
								default :break;
							}
							
							System.out.println("send power switch");
							
							//@-串口发送数据
							LoginController1.Serise_Main.SerWrite_Hex(LoginController1.Serise_Main.AODI_Serise_Protocol.Serise_Send_Protocol_RecvBuff);
						}
						else if(LoginController1.Charging_PowerSend_Flag==false)
						{
							//if(LoginController1.ChargeVoltage_StartFlag==true)
							//{
								//@-串口协议头
								LoginController1.Serise_Main.AODI_Serise_Protocol.Serise_Send_Protocol_RecvBuff[0]=LoginController1.Serise_Main.AODI_Serise_Protocol.Serise_SendFrame_StartByte;
								//@-CAN-ID(4Bytes)-DA输出
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
								//@-串口发送数据
								LoginController1.Serise_Main.SerWrite_Hex(LoginController1.Serise_Main.AODI_Serise_Protocol.Serise_Send_Protocol_RecvBuff);
							//}
						}
	
					}
				}
				//@-PCB存活检测&充电时间计时1times/s
				if(DisplayUpdata_Timer4==tick_count/1)
				{
					DisplayUpdata_Timer4=0;
					
					//@-PCB存活检测
					if(LoginController1.Serise_Main.Recv_FrameOK_Count_Copy!=LoginController1.Serise_Main.Recv_FrameOK_Count)
					{
						LoginController1.Serise_Main.Recv_FrameOK_Count_Copy=LoginController1.Serise_Main.Recv_FrameOK_Count;
						
						LoginController1.PCB_Status=true;
					}
					else
					{						
						LoginController1.PCB_Status=false;  
					}
					
					//@-充电时间计时
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
				//@-充电策略时间计时10times/1s
				if(DisplayUpdata_Timer6==(tick_count/10))
				{
					DisplayUpdata_Timer6 = 0;
					
					//System.out.println("ll");

					//@-充电策略
					Charging_Strategic();
					
				}
				
			}
	    	//@2-程序运行在主界面
	    	else if(ScreensFramework.App_Page==2)
	    	{
	
	    	}

		}
		
		

		//@4-1秒计时
		if(second_1s_tick==tick_count)   //1s
		{
			second_1s_tick=0;
			
			//系统声音
			//Toolkit.getDefaultToolkit().beep();
			
			//@-1秒闪烁flag
			if(second_1s_flash==false)
			second_1s_flash=true;
			else if(second_1s_flash==true)
			second_1s_flash=false;
			
			
			//@6-刷新时间
	    	local_time = Calendar.getInstance();
			
			//@-系统时间
	    	Time_Str = new String(""+local_time.get(Calendar.YEAR)+"/"
					+formater_value.format(local_time.get(Calendar.MONTH)+1)+"/"
					+formater_value.format(local_time.get(Calendar.DATE))+"  "
					+formater_value.format(local_time.get(Calendar.HOUR_OF_DAY))+":"
					+formater_value.format(local_time.get(Calendar.MINUTE))+":"
					+formater_value.format(local_time.get(Calendar.SECOND)));
	    	
	    	//@-信息输出时间
	    	Time_Str_Info = new String(""+formater_value.format(local_time.get(Calendar.HOUR_OF_DAY))+":"
											+formater_value.format(local_time.get(Calendar.MINUTE))+":"
											+formater_value.format(local_time.get(Calendar.SECOND)));

			//@-启动界面显示时间控制
			if((lock_3s==false)||(lock_start_main==true))
			second_3s_tick=second_3s_tick+1;
			if((second_3s_tick==3)&&(lock_3s==false)||(second_3s_tick==3)&&(lock_start_main==true))
			{
				//@-启动特效
				if(lock_3s==false)
				{
					lock_3s=true;
					LoginController.RecCountProperty_Main.set("change");
				}
				
				//@-启动主界面
				if(lock_start_main==true)
				{
					lock_start_main=false;
					ScreensFramework.PageChange.set("main");
				}
				
				second_3s_tick=0;
			}

			//@-充电策略延时超时判断
			if((LoginController1.ControlVoltage_StageLimt_Flag==true)&&(LoginController1.ControlVoltage_Timeout_Flag==false))
			{
				//@-判断是否超时
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
	
	/**充电控制电压输出
	 * 
	 */
	private void ChargingControl_VoltageOut()
	{
		//@-充电运行标志&&PCB需存活
		if((LoginController1.ChargeVoltage_StartFlag==true)&&(LoginController1.PCB_Status_Copy==true))
		{
			//@1-充电控制电压阶段上限标志
			if(LoginController1.ControlVoltage_StageLimt_Flag==false)
			{
				//@-充电控制电压<阶段控制电压上限，控制电压步进增加
				if((new Float(LoginController1.ControlVoltage_Current_Copy).compareTo(new Float(LoginController1.VoltageEnd))<=0))
				{
					LoginController1.ControlVoltage_Current_Copy=(float)((LoginController1.ControlVoltage_Current_Copy+(float)(LoginController1.VoltageStep/10)));
					LoginController1.ControlVoltage_Current=LoginController1.ControlVoltage_Current_Copy;
				}
				else
				{
					//@-输出信息
					InfoPut(3,"控制电压以达到阶段上限");
					LoginController1.ControlVoltage_StageLimt_Flag=true;
				}
			}
		}
	}
	
	/**充电策略
	 * 
	 * @param
	 */
	private void Charging_Strategic()
	{
		
		//@-充电运行标志&&PCB需存活
		if((LoginController1.ChargeVoltage_StartFlag==true)&&(LoginController1.PCB_Status_Copy==true))
		{			
			//@-没有超时，继续判断
			if(LoginController1.ControlVoltage_Timeout_Flag==false)
			{
				//@-充电电压进入区域电压
				if((new Float(LoginController1.ControlVoltage_Current).compareTo(new Float(LoginController1.Stage_Voltage_Start))>=0)
				 &&(new Float(LoginController1.ControlVoltage_Current).compareTo(new Float(LoginController1.Stage_Voltage_End))<=0))
				{
				
					//@-判断首次进入区域
					if(LoginController1.Stage_InFlag==false)
					{
						LoginController1.Stage_InFlag=true;
						InfoPut(1,"进入阶段"+LoginController1.Strategy_Stage);
					}
					
					//@-检测电流是否在电流区域内
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
					//@-判断首次离开区域
					if(LoginController1.Stage_OutFlag==false)
					{
						LoginController1.Stage_OutFlag=true;
						InfoPut(1,"离开阶段"+LoginController1.Strategy_Stage);
					}
					
					//@-判断阶段是否成功
					Check_Percent();
				}	
				
			}
			//@-已超时，告警
			else if(LoginController1.ControlVoltage_Timeout_Flag==true)
			{
				//@-告警&&&&&&&&&&&&&&&&&&&&&&&
				InfoPut(2,"检测超时");
				LoginController1.StrategyStage_Main.setValue("Strategy_Error");
			}			
		}

		//@-更新电流值
		LoginController1.OutputCurrent_Current_Copy=LoginController1.OutputCurrent_Current;
		
	}
	
	/**计算成功百分比
	 * 
	 */
	private void Check_Percent()
	{
		int total;
		
		total=LoginController1.Stage_Succeed_Count+LoginController1.Stage_Fail_Count;
		
		if(total>0)
		{
			//@-计算阶段成功百分比
			LoginController1.Stage_Succeed=(LoginController1.Stage_Succeed_Count/(total)*100);
			//@-判断阶段是否成功
			if((new Float(LoginController1.Stage_Succeed).compareTo(new Float(60.0))>=0))
			{
				if(LoginController1.Strategy_Stage==1)
				{
					LoginController1.Strategy_Stage=LoginController1.Strategy_Stage+1;
			    	if(LoginController1.VoltageArea2_Sel==true)
			    	{
			    		LoginController1.StrategyStage_Main.setValue("Stage2");
			    		InfoPut(1,"切换至阶段2");
			    	}
			    	else
			    	{
						LoginController1.Strategy_Stage=LoginController1.Strategy_Stage+1;
				    	if(LoginController1.VoltageArea3_Sel==true)
				    	{
				    		LoginController1.StrategyStage_Main.setValue("Stage3");
				    		InfoPut(1,"切换至阶段3");
				    	}
				    	else
				    	{
							LoginController1.Strategy_Stage=LoginController1.Strategy_Stage+1;
					    	if(LoginController1.VoltageArea4_Sel==true)
					    	{
					    		LoginController1.StrategyStage_Main.setValue("Stage4");
					    		InfoPut(1,"切换至阶段4");
					    	}
					    	else 
					    	{
					    		LoginController1.StrategyStage_Main.setValue("Strategy_OK");
					    		InfoPut(1,"检测正常结束");
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
			    		InfoPut(1,"切换至阶段3");
			    	}
			    	else
			    	{
						LoginController1.Strategy_Stage=LoginController1.Strategy_Stage+1;
				    	if(LoginController1.VoltageArea4_Sel==true)
				    	{
				    		LoginController1.StrategyStage_Main.setValue("Stage4");
				    		InfoPut(1,"切换至阶段4");
				    	}
				    	else 
				    	{
				    		LoginController1.StrategyStage_Main.setValue("Strategy_OK");
				    		InfoPut(1,"检测正常结束");
				    	}
			    	}
				}
				else if(LoginController1.Strategy_Stage==3)
				{
					LoginController1.Strategy_Stage=LoginController1.Strategy_Stage+1;
			    	if(LoginController1.VoltageArea4_Sel==true)
			    	{
			    		LoginController1.StrategyStage_Main.setValue("Stage4");
			    		InfoPut(1,"切换至阶段4");
			    	}
			    	else
			    	{
			    		LoginController1.StrategyStage_Main.setValue("Strategy_OK");
			    		InfoPut(1,"检测正常结束");
			    	}
				}
				else if(LoginController1.Strategy_Stage==4)
				{
			    	LoginController1.StrategyStage_Main.setValue("Strategy_OK");
			    	InfoPut(1,"检测正常结束");
				}
			}
			else
			{
				//等待第4阶段超时
				if(LoginController1.Strategy_Stage==4)
				{
					if(LoginController1.ControlVoltage_Timeout_Flag==true)
					{
						LoginController1.StrategyStage_Main.setValue("Strategy_Error");
						InfoPut(3,"检测失败");
					}
					System.out.println("out check!");
				}	
				else
				{
					LoginController1.StrategyStage_Main.setValue("Strategy_Error");
					InfoPut(3,"检测失败");
				}
			}
		}
		else
		{
			InfoPut(3,"区域"+LoginController1.Strategy_Stage+"设置的区间太小，不能检测");
		}
		
		//System.out.println("Stage_Succeed:"+LoginController1.Stage_Succeed+"%");
	}
	
	/**
	 * 
	 * @param Mode Message
	 */
	public static void InfoPut(int Mode,String Message)
	{

		//@-信息类型
		Message_Type=Mode;
		
		//@-信息输出计数增1
		MessagePut_Count=MessagePut_Count+1;
		//@-格式化信息消息
		InfoMessage=new String("@"+MessagePut_Count+"-"+Message+"-"+Time_Str_Info+"\n");	
		//@-发送至队列
		synchronized(Data_InfoType)
		{
			Data_InfoType.add(Message_Type);
		}
		synchronized(Data_InfoMessage)
		{
			Data_InfoMessage.add(InfoMessage);
		}
	}
	
	
	/**最值比较&极限比较
	 * 
	 */
    private void Check_Num()
    {
    	  //----------------------------------------------最值-----------------------------------------------------------
	      //@1-输出电压最大值
		  if((new Float(LoginController1.OutputVoltage_Current).compareTo(new Float(LoginController1.OutputVoltage_MAX))>0))
		  {
	  		LoginController1.OutputVoltage_MAX=LoginController1.OutputVoltage_Current;  
		  }
    	  //@2-输出电压最小值
    	  if((new Float(LoginController1.OutputVoltage_Current).compareTo(new Float(LoginController1.OutputVoltage_MIN))<0))
    	  {
    		LoginController1.OutputVoltage_MIN=LoginController1.OutputVoltage_Current;
    	  }
    	
	      //@3-母线电压最大值
		  if((new Float(LoginController1.MainVoltage_Current).compareTo(new Float(LoginController1.MainVoltage_MAX))>0))
		  {
	  		LoginController1.MainVoltage_MAX=LoginController1.MainVoltage_Current;  
		  }
    	  //@4-母线电压最小值
    	  if((new Float(LoginController1.MainVoltage_Current).compareTo(new Float(LoginController1.MainVoltage_MIN))<0))
    	  {
    		LoginController1.MainVoltage_MIN=LoginController1.MainVoltage_Current;
    	  }
    	
	      //@5-输出电流最大值
		  if((new Float(LoginController1.OutputCurrent_Current).compareTo(new Float(LoginController1.OutputCurrent_MAX))>0))
		  {
	  		LoginController1.OutputCurrent_MAX=LoginController1.OutputCurrent_Current;  
		  }
    	  //@6-输出电流最小值
    	  if((new Float(LoginController1.OutputCurrent_Current).compareTo(new Float(LoginController1.OutputCurrent_MIN))<0))
    	  {
    		LoginController1.OutputCurrent_MIN=LoginController1.OutputCurrent_Current;
    	  }
    	  
	      //@7-检测温度最大值
		  if((new Float(LoginController1.Temperature_Current).compareTo(new Float(LoginController1.Temperature_MAX))>0))
		  {
	  		LoginController1.Temperature_MAX=LoginController1.Temperature_Current;  
		  }
    	  //@8-检测温度最小值
    	  if((new Float(LoginController1.Temperature_Current).compareTo(new Float(LoginController1.Temperature_MIN))<0))
    	  {
    		LoginController1.Temperature_MIN=LoginController1.Temperature_Current;
    	  }
    	
    	//----------------------------------------------上下限-----------------------------------------------------------
    	  //@9-输出电压上限监测
    	  if(LoginController1.OptionSet_OutputVoltage_UpLimit_Flag==true)
    	  {
    		  //@当前值小于上极限-正常
    		  if((new Float(LoginController1.OutputVoltage_Current).compareTo(new Float(LoginController1.OptionSet_OutputVoltage_UpLimit))<0))
    		  {
    			  LoginController1.OutputVoltage_Up_LimitFlag=0;    			  
    		  }
    		  //@当前值大于上极限-报错
    		  else if((new Float(LoginController1.OutputVoltage_Current).compareTo(new Float(LoginController1.OptionSet_OutputVoltage_UpLimit))>0))
    		  {
    			  LoginController1.OutputVoltage_Up_LimitFlag=1;
    		  }
    		  //@当前值等于上极限-临界
    		  else if((new Float(LoginController1.OutputVoltage_Current).compareTo(new Float(LoginController1.OptionSet_OutputVoltage_UpLimit))==0))
    		  {
    			  LoginController1.OutputVoltage_Up_LimitFlag=2;
    		  }
    	  }
    	  //@10-输出电压下限监测
    	  if(LoginController1.OptionSet_OutputVoltage_DownLimit_Flag==true)
    	  {
    		  //@当前值大于下极限-正常
    		  if((new Float(LoginController1.OutputVoltage_Current).compareTo(new Float(LoginController1.OptionSet_OutputVoltage_DownLimit))>0))
    		  {
    			  LoginController1.OutputVoltage_Down_LimitFlag=0;
    		  }
    		  //@当前值小于下极限-报警
    		  else if((new Float(LoginController1.OutputVoltage_Current).compareTo(new Float(LoginController1.OptionSet_OutputVoltage_DownLimit))<0))
    		  {
    			  LoginController1.OutputVoltage_Down_LimitFlag=1;
    		  }
    		  //@当前值等于下极限-临界
    		  else if((new Float(LoginController1.OutputVoltage_Current).compareTo(new Float(LoginController1.OptionSet_OutputVoltage_DownLimit))==0))
    		  {
    			  LoginController1.OutputVoltage_Down_LimitFlag=2;
    		  }
    	  }
    	  
    	  //@11-母线电压上限监测
    	  if(LoginController1.OptionSet_MainVoltage_UpLimit_Flag==true)
    	  {
    		  //@当前值小于上极限-正常
    		  if((new Float(LoginController1.MainVoltage_Current).compareTo(new Float(LoginController1.OptionSet_MainVoltage_UpLimit))<0))
    		  {
    			  LoginController1.MainVoltage_Up_LimitFlag=0;    			  
    		  }
    		  //@当前值大于上极限-报错
    		  else if((new Float(LoginController1.MainVoltage_Current).compareTo(new Float(LoginController1.OptionSet_MainVoltage_UpLimit))>0))
    		  {
    			  LoginController1.MainVoltage_Up_LimitFlag=1;
    		  }
    		  //@当前值等于上极限-临界
    		  else if((new Float(LoginController1.MainVoltage_Current).compareTo(new Float(LoginController1.OptionSet_MainVoltage_UpLimit))==0))
    		  {
    			  LoginController1.MainVoltage_Up_LimitFlag=2;
    		  }
    	  }
    	  //@12-母线电压下限监测
    	  if(LoginController1.OptionSet_MainVoltage_DownLimit_Flag==true)
    	  {
    		  //@当前值大于下极限-正常
    		  if((new Float(LoginController1.MainVoltage_Current).compareTo(new Float(LoginController1.OptionSet_MainVoltage_DownLimit))>0))
    		  {
    			  LoginController1.MainVoltage_Down_LimitFlag=0;
    		  }
    		  //@当前值小于下极限-报警
    		  else if((new Float(LoginController1.MainVoltage_Current).compareTo(new Float(LoginController1.OptionSet_MainVoltage_DownLimit))<0))
    		  {
    			  LoginController1.MainVoltage_Down_LimitFlag=1;
    		  }
    		  //@当前值等于下极限-临界
    		  else if((new Float(LoginController1.MainVoltage_Current).compareTo(new Float(LoginController1.OptionSet_MainVoltage_DownLimit))==0))
    		  {
    			  LoginController1.MainVoltage_Down_LimitFlag=2;
    		  }
    	  }
    	  
    	  //@13-输出电流上限监测
    	  if(LoginController1.OptionSet_OutputCurrent_UpLimit_Flag==true)
    	  {
    		  //@当前值小于上极限-正常
    		  if((new Float(LoginController1.OutputCurrent_Current).compareTo(new Float(LoginController1.OptionSet_OutputCurrent_UpLimit))<0))
    		  {
    			  LoginController1.OutputCurrent_Up_LimitFlag=0;    			  
    		  }
    		  //@当前值大于上极限-报错
    		  else if((new Float(LoginController1.OutputCurrent_Current).compareTo(new Float(LoginController1.OptionSet_OutputCurrent_UpLimit))>0))
    		  {
    			  LoginController1.OutputCurrent_Up_LimitFlag=1;
    		  }
    		  //@当前值等于上极限-临界
    		  else if((new Float(LoginController1.OutputCurrent_Current).compareTo(new Float(LoginController1.OptionSet_OutputCurrent_UpLimit))==0))
    		  {
    			  LoginController1.OutputCurrent_Up_LimitFlag=2;
    		  }
    		  
    		  //System.out.println(""+LoginController1.OptionSet_OutputCurrent_UpLimit);
    	  }
    	  //@14-输出电流下限监测
    	  if(LoginController1.OptionSet_OutputCurrent_DownLimit_Flag==true)
    	  {
    		  //@当前值大于下极限-正常
    		  if((new Float(LoginController1.OutputCurrent_Current).compareTo(new Float(LoginController1.OptionSet_OutputCurrent_DownLimit))>0))
    		  {
    			  LoginController1.OutputCurrent_Down_LimitFlag=0;
    		  }
    		  //@当前值小于下极限-报警
    		  else if((new Float(LoginController1.OutputCurrent_Current).compareTo(new Float(LoginController1.OptionSet_OutputCurrent_DownLimit))<0))
    		  {
    			  LoginController1.OutputCurrent_Down_LimitFlag=1;
    		  }
    		  //@当前值等于下极限-临界
    		  else if((new Float(LoginController1.OutputCurrent_Current).compareTo(new Float(LoginController1.OptionSet_OutputCurrent_DownLimit))==0))
    		  {
    			  LoginController1.OutputCurrent_Down_LimitFlag=2;
    		  }
    	  }
    	  
    	  //@15-检测温度上限监测
    	  if(LoginController1.OptionSet_Temperature_UpLimit_Flag==true)
    	  {
    		  //@当前值小于上极限-正常
    		  if((new Float(LoginController1.Temperature_Current).compareTo(new Float(LoginController1.OptionSet_Temperature_UpLimit))<0))
    		  {
    			  LoginController1.Temperature_Up_LimitFlag=0;    			  
    		  }
    		  //@当前值大于上极限-报错
    		  else if((new Float(LoginController1.Temperature_Current).compareTo(new Float(LoginController1.OptionSet_Temperature_UpLimit))>0))
    		  {
    			  LoginController1.Temperature_Up_LimitFlag=1;
    		  }
    		  //@当前值等于上极限-临界
    		  else if((new Float(LoginController1.Temperature_Current).compareTo(new Float(LoginController1.OptionSet_Temperature_UpLimit))==0))
    		  {
    			  LoginController1.Temperature_Up_LimitFlag=2;
    		  }
    	  }
    	  //@16-检测温度下限监测
    	  if(LoginController1.OptionSet_Temperature_DownLimit_Flag==true)
    	  {
    		  //@当前值大于下极限-正常
    		  if((new Float(LoginController1.Temperature_Current).compareTo(new Float(LoginController1.OptionSet_Temperature_DownLimit))>0))
    		  {
    			  LoginController1.Temperature_Down_LimitFlag=0;
    		  }
    		  //@当前值小于下极限-报警
    		  else if((new Float(LoginController1.Temperature_Current).compareTo(new Float(LoginController1.OptionSet_Temperature_DownLimit))<0))
    		  {
    			  LoginController1.Temperature_Down_LimitFlag=1;
    		  }
    		  //@当前值等于下极限-临界
    		  else if((new Float(LoginController1.Temperature_Current).compareTo(new Float(LoginController1.OptionSet_Temperature_DownLimit))==0))
    		  {
    			  LoginController1.Temperature_Down_LimitFlag=2;
    		  }
    	  }
    	  
    	
    }
	
	    
}
                    
