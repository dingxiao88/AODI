package AODI_INFO;




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
public class INFO_DisplayTimer implements Runnable{
		
	//@1-ϵͳ��ʱ
	private double system_tick=0;
	//@2-1���ʱ
	private double second_1s_tick=0;
	//@3-1�붨ʱֵ
	private int    second_1s_const=0;
	
	private int    second_2s_const=0;
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
	public static String Time_Str1=new String("----");
	
	private int    second_curver1s_const=0;
	private int    second_curver2s_const=0;
	
	private int    NET_Send_TimeCount=0;
	
	//@2-1���ʱ
	private static double second_3s_tick=0;
	private static boolean lock_3s=false;
	public static boolean lock_start_main=false;
	private static double second_5s_tick=0;
	private static boolean check_5s=false;

	
	/*------ʹ�÷�FXԭ��task����-----------*/
	public static Task<Integer> task_dis; 
    private Thread Display_Thread;
    
    public static double input_angel=0;
    
    private int tick_count=0;
    
    public static boolean change_flag=false;
    public static boolean flash_flag=false;
    public static boolean change_flag1=false;
    public static boolean change_flag2=false;
    
    
	public  Thread Tcp_Thread=new Thread(this);      //��������߳�
	private Socket client;
	
//	private String send_data=new String("DENG_LU|��ر������Զ�|820715|33ffde05474b363818581843||||#");
	private String send_data1=new String("POP|#");
//	private byte[] data1 = send_data.getBytes();
	private byte[] data2 = send_data1.getBytes();
	private byte[] data3 = new byte[1024];
	
	public static int Tcp_RecvCount=0;

	public static int Tcp_TestCount=0;
	
	public static int pop_flag=1;
	
	/**����ʱ�䶨ʱ��
	 * 
	 * @param delayTime1
	 */
	public INFO_DisplayTimer(int delayTime){
		    
		//@1-���ݾ��ȸ�ʽ
		formater_value.setMaximumIntegerDigits(2);
		formater_value.setMinimumIntegerDigits(2);
		
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
		
		
		//@2-1���ʱ
		if(second_1s_tick==tick_count)   //1s
		{
			second_1s_tick=0;
			
			//@6-ˢ��ʱ��
	    	local_time = Calendar.getInstance();
			
			//ϵͳʱ��
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
			INFOController.DataProperty_Main.setValue(""+flash_flag);
	    	
	    	
			//TCP����������
			second_5s_tick=second_5s_tick+1;
			if(second_5s_tick==5)
			{
				check_5s=true;
				second_5s_tick=0;
			}
						
		}
	} 
	



	@Override
	public void run() {
		
		
	}
	
	
}
                    
