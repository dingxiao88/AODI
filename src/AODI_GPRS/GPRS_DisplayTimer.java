package AODI_GPRS;




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


/**
 * 
 * @author Jack Ding
 * @date 2011-01-13
 */
public class GPRS_DisplayTimer implements Runnable{
		
	//@1-系统计时
	private double system_tick=0;
	//@2-1秒计时
	private double second_1s_tick=0;
	//@3-1秒定时值
	private int    second_1s_const=0;
	
	private int    second_2s_const=0;
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
	public static String Time_Str1=new String("----");
	
	private int    second_curver1s_const=0;
	private int    second_curver2s_const=0;
	
	private int    NET_Send_TimeCount=0;
	
	//@2-1秒计时
	private static double second_3s_tick=0;
	private static boolean lock_3s=false;
	public static boolean lock_start_main=false;
	private static double second_5s_tick=0;
	private static boolean check_5s=false;

	
	/*------使用非FX原生task方法-----------*/
	public static Task<Integer> task_dis; 
    private Thread Display_Thread;
    
    public static double input_angel=0;
    
    private int tick_count=0;
    
    public static boolean change_flag=false;
    public static boolean flash_flag=false;
    public static boolean change_flag1=false;
    public static boolean change_flag2=false;
    
    
	public  Thread Tcp_Thread=new Thread(this);      //网络接收线程
	private Socket client;
	
//	private String send_data=new String("DENG_LU|电控北京测试端|820715|33ffde05474b363818581843||||#");
	private String send_data1=new String("POP|#");
//	private byte[] data1 = send_data.getBytes();
	private byte[] data2 = send_data1.getBytes();
	private byte[] data3 = new byte[1024];
	
	public static int Tcp_RecvCount=0;

	public static int Tcp_TestCount=0;
	
	public static int pop_flag=1;
	
	/**构建时间定时器
	 * 
	 * @param delayTime1
	 */
	public GPRS_DisplayTimer(int delayTime){
		    
		//@1-数据精度格式
		formater_value.setMaximumIntegerDigits(2);
		formater_value.setMinimumIntegerDigits(2);
		
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
		
		
		//@2-1秒计时
		if(second_1s_tick==tick_count)   //1s
		{
			second_1s_tick=0;
			
			//@6-刷新时间
	    	local_time = Calendar.getInstance();
			
			//系统时间
	    	Time_Str = new String(""+local_time.get(Calendar.YEAR)+"/"
					+formater_value.format(local_time.get(Calendar.MONTH)+1)+"/"
					+formater_value.format(local_time.get(Calendar.DATE))+"  "
					+formater_value.format(local_time.get(Calendar.HOUR_OF_DAY))+":"
					+formater_value.format(local_time.get(Calendar.MINUTE))+":"
					+formater_value.format(local_time.get(Calendar.SECOND)));
	    	
	    	Time_Str1 = new String(""+formater_value.format(local_time.get(Calendar.HOUR_OF_DAY))+":"
									+formater_value.format(local_time.get(Calendar.MINUTE))+":"
									+formater_value.format(local_time.get(Calendar.SECOND)));
	    	

			if(flash_flag==false)
			flash_flag=true;
			else if(flash_flag==true)
			flash_flag=false;
			GPRSController.DataProperty_Main.setValue(""+flash_flag);
	    	
	    	
			//TCP发送心跳包
			second_5s_tick=second_5s_tick+1;
			if(second_5s_tick==5)
			{
				check_5s=true;
				second_5s_tick=0;
			}
						
		}
	} 
	
	
	
	private void Read_Tcp_Data(InputStream in)
	{
					

		
        
	}
	
	
	public void receive_on_off(boolean in)
	{
		if(in==true)
		{
			//定时计数清零
			second_5s_tick=0;
			
			Tcp_Thread=new Thread(this);  //网络接收线程
			//Tcp_Thread.setDaemon(true);   //设置为后台线程
			Tcp_Thread.setName("TCP");
			Tcp_Thread.setPriority(9);    //设置优先级别6  
			Tcp_Thread.start();
		}
		else 
			Tcp_Thread=null;
	}


	@Override
	public void run() {
		// TODO Auto-generated method stub

		try{
			 //@1-建立连接
	         // 根据服务器地址和端口号实例化一个Socket实例
			//client = new Socket("122.114.114.58", 40000);
			client = new Socket(GPRSController.Target_IP, GPRSController.Target_Port);
			client.setSoTimeout(50000);
	         //System.out.println("Connected to server...sending echo string");
	         // 返回此套接字的输入流，即从服务器接受的数据对象
	         InputStream in = client.getInputStream();
	         // 返回此套接字的输出流，即向服务器发送的数据对象
	         OutputStream out = client.getOutputStream();
	         
	         while(GPRSController.TCP_Run_Flag==true)
	         {
	        	 
	        	 if(GPRSController.TCP_Send_Data==false)
	        	 {
	        		 GPRSController.TCP_Send_Data=true;
	        		 //System.out.println("TCP");
	        		 
        			String send_data=new String("DENG_LU|"+GPRSController.Target_CarNumber+"|"+GPRSController.Target_Pwd+"|"+GPRSController.Target_DeviceID+"||||#");
        			byte[] data1 = send_data.getBytes();
	        		 
	        		 out.write(data1);
	        		 
			         // 接收数据的计数器，将写入数据的初始偏移量
	        		 // 接收数据的计数器，将写入数据的初始偏移量
	        	        int totalBytesRcvd = 0;
	        	        // 初始化接收数据的总字节数 
	        	        int bytesRcvd;

	        	        //接收数据的总字节数
	        	        try {
	        				if ((bytesRcvd = in.read(data3, totalBytesRcvd, data3.length
	        				        - totalBytesRcvd)) != -1)
	        				{
	        				 //System.out.println("yes");
	        				}
	        				else
	        				{
	        				    throw new SocketException("与服务器的连接已关闭");
	        				}
	        			} catch (SocketException e) {
	        				// TODO Auto-generated catch block
	        				e.printStackTrace();
	        			} catch (IOException e) {
	        				// TODO Auto-generated catch block
	        				e.printStackTrace();
	        			}
	        	        
	        	        // 打印服务器发送来的数据
	        	        //System.out.println("DATA:"+new String(data3));
	        	        
	        	        Tcp_RecvCount =Tcp_RecvCount+1;

	        	        GPRSController.Main_Data_flag=true;
	        	        GPRSController.Main_Data_Sting =new String("@"+Tcp_RecvCount+":"+Time_Str1+"  "+new String(data3));

	        	        
	        	        //TCP连接状态-已连接
        			    GPRSController.TCP_State=1;
        			    GPRSController.Noti_Send_Flag=true;
	        	 }
	        	 else if(GPRSController.TCP_Send_Data==true)
	        	 {
	        		 //发送频率是否置位
	        		 if(check_5s==true)
	        		 {
	        			 check_5s=false;
	        			 //System.out.println("Data");
	        			 
	        			 if(pop_flag==1)
	        			 {
	        				 pop_flag=0;
	        				 out.write(data2);
	        			 }
	        			 
	        			 // 接收数据的计数器，将写入数据的初始偏移量
	        		        int totalBytesRcvd = 0;
	        		        // 初始化接收数据的总字节数 
	        		        int bytesRcvd;

	        		        //接收数据的总字节数
	        		        try {
	        					if ((bytesRcvd = in.read(data3, totalBytesRcvd, data3.length
	        					        - totalBytesRcvd)) != -1)
	        					{
	        					 //System.out.println("yes");
	        					}
	        					else
	        					{
	        					    throw new SocketException("与服务器的连接已关闭");
	        					}
	        				} catch (SocketException e) {
	        					// TODO Auto-generated catch block
	        					e.printStackTrace();
	        				} catch (IOException e) {
	        					// TODO Auto-generated catch block
	        					e.printStackTrace();
	        				}
	        		        
	        		        // 打印服务器发送来的数据
	        		        //System.out.println("DATA:"+new String(data3));
	        		        
	        		        Tcp_RecvCount =Tcp_RecvCount+1;

	        		        GPRSController.Main_Data_flag=true;
		        	        GPRSController.Main_Data_Sting =new String("@"+Tcp_RecvCount+":"+Time_Str1+"  "+new String(data3));

	        		 }

	        	 }
	        	 
	        	 Tcp_TestCount =Tcp_TestCount+1;	       
	         }
		        
		  }
		catch (UnknownHostException e) {
			GPRSController.TCP_Run_Flag=false;
			GPRSController.TCP_Send_Data=false;
	        //TCP连接状态-IP或Port不存在
		    GPRSController.TCP_State=2;
		    GPRSController.Noti_Send_Flag=true;
        }
		catch (ConnectException e) {
			GPRSController.TCP_Run_Flag=false;
			GPRSController.TCP_Send_Data=false;
	        //TCP连接状态-IP或Port不存在
		    GPRSController.TCP_State=2;
		    GPRSController.Noti_Send_Flag=true;
        }
		catch (Exception e){
		   e.printStackTrace();
		  }
		
	
	}
	
	
}
                    
