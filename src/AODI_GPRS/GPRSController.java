package AODI_GPRS;
/*
 * Copyright (c) 2008, 2013 Oracle and/or its affiliates.
 * All rights reserved. Use is subject to license terms.
 *
 * This file is available and licensed under the following license:
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  - Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *  - Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the distribution.
 *  - Neither the name of Oracle Corporation nor the names of its
 *    contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */



//import eu.hansolo.enzo.common.Section;
//import eu.hansolo.enzo.gauge.SimpleGauge;
//import eu.hansolo.enzo.gauge.SimpleGaugeBuilder;


import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.logging.Level;
import java.util.logging.Logger;

import AODI_GPRS.ControlledScreen;
import AODI_GPRS.ScreensController;
import AODI_GPRS.ScreensFramework;

import com.sun.javafx.perf.PerformanceTracker;

import javafx.animation.Animation;
import javafx.animation.Animation.Status;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.CacheHint;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.SwipeEvent;
import javafx.scene.input.TouchEvent;
import javafx.scene.input.TouchPoint;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Line;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;


/**
 * Login Controller.
 */
public class GPRSController implements Initializable, ControlledScreen {

	//@1-传递主应用程序接口
	private ScreensController myController;
	
	@FXML
	private AnchorPane Root;
	
	//@1-软件退出
    @FXML
    private ImageView App_Exit;
    
	//@-服务器IP
	@FXML
	private TextField  TextField_ServeIP;
	//@-服务器Port
	@FXML
	private TextField  TextField_ServePort;
	//@-TCP接收计数
	@FXML
	private TextField  TextField_TcpCount;
	
	//@-时间
	@FXML
	private Label  Label_Time;
	
	//@-设备ID
	@FXML
	private TextField  TextField_DeviceID;
	//@-车辆识别号
	@FXML
	private TextField  TextField_CarNumber;
	//@-密码
	@FXML
	private TextField  TextField_Pwd;
	
	//@-密码
	@FXML
	private TextArea  TextAre_RecText;
	
	//@-网络连接按钮
	@FXML
	private Button  Button_Connect;
	
	//@-TCP连接指示灯
	@FXML
	private ImageView ImageView_TcpConnect;
	
	//@-网络连接按钮
	@FXML
	private Button  Button_CleanText;
	
	
	//@26-图片资源-LED绿
	private Image Image_state_ok;
	//@26-图片资源-LED红
	private Image Image_state_error;
	//@26-图片资源-LED黄
	private Image Image_state_warning;
	
//	private Socket client;
//	private int Send_flag=1;
//	String send_data=new String("DENG_LU|电控北京测试端|820715|33ffde05474b363818581843||||#");
//	String send_data1=new String("POP|#");
//	byte[] data1 = send_data.getBytes();
//	byte[] data2 = send_data1.getBytes();
//	byte[] data3 = new byte[1024];
//	int flag=1;
//	Socket socket;
	
	//TCP运行标志
	public static boolean TCP_Run_Flag=false;
	//TCP登录或心跳数据包发送切换
	public static boolean TCP_Send_Data=false;
	//TCP连接状态
	public static int TCP_State=0;  //1:已连接  2:ip或port不存在  3:断开
	
    //数据属性
	public static SimpleStringProperty DataProperty_Main = new SimpleStringProperty();
	//change监听器
	private ChangeListener changelisten1;
	
	public static String  Main_Data_Sting=null;
	public static boolean Main_Data_flag=false;
	
	public static boolean Noti_Send_Flag=false;
	
	public static String Target_IP;
	public static int    Target_Port;
	public static String Target_DeviceID;
	public static String Target_CarNumber;
	public static String Target_Pwd;
	
	private boolean id_flag=true;
	

	/**登录界面初始化
	 *
	 */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	
    	//@7-图片资源初始化
		Image_state_ok = new Image(GPRSController.class.getResourceAsStream("status_green.png"));
		Image_state_error = new Image(GPRSController.class.getResourceAsStream("status_red.png"));
		Image_state_warning = new Image(GPRSController.class.getResourceAsStream("status_yellow.png"));
		ImageView_TcpConnect.setImage(Image_state_error);
    	

    	
    	//@1-数据同步
    	DataProperty_Main.addListener(changelisten1 = new ChangeListener(){
			@Override
			public void changed(ObservableValue arg0, Object oldval, Object newval) {
				// TODO Auto-generated method stub

				Platform.runLater(new Runnable() {
					@Override
					public void run() {

						//@1-接收数据及接收计数
						if(Main_Data_flag==true)
						{
							Main_Data_flag=false;
							TextAre_RecText.setStyle(""
							        //+ "-fx-font-size: 30px;"
							        //+ "-fx-font-style: italic;"
							        //+ "-fx-font-weight: bold;"
							        + "-fx-font-family: STSONG;"
							        + "-fx-text-fill: blue;"
							        + "-fx-background-color: aqua");
							
							TextAre_RecText.appendText(Main_Data_Sting);

							
							if(id_flag==true)
							{
								id_flag=false;
								TextField_TcpCount.setId("T11");
							}
							else if(id_flag==false)
							{
								id_flag=true;
								TextField_TcpCount.setId("T44");
							}
							
							TextField_TcpCount.setText(""+GPRS_DisplayTimer.Tcp_RecvCount);
						}
						
						//@2-TCP连接成功信息
						if(Noti_Send_Flag==true)
						{
							Noti_Send_Flag=false;
							
							//@-Noti输出信息
							if(TCP_State==1)
							{
								ScreensFramework.Show_Noti("Success", "TCP连接成功!");
								ImageView_TcpConnect.setImage(Image_state_ok);
								Button_Connect.setText("TCP断开");
							}
							else if(TCP_State==2)
							{
								ScreensFramework.Show_Noti("Warning", "TCP连接失败!");
								ImageView_TcpConnect.setImage(Image_state_warning);
								Button_Connect.setText("TCP连接");
							}
							else if(TCP_State==3)
							{
								ScreensFramework.Show_Noti("Error", "TCP连接断开!");
								ImageView_TcpConnect.setImage(Image_state_error);
								Button_Connect.setText("TCP连接");
							}
	
						}
						
						//@3-时间信息
						Label_Time.setText("时间:"+GPRS_DisplayTimer.Time_Str);
					}
				});					

			}
    	});
    	
    	
    }
    
    /**
     * 
     */
    private void TCP_Connect()
    {         
    	//@1	
//		try {
//			Local_inadd=InetAddress.getByName("112.114.144.58");   //for windows
//			//Local_inadd=SWJ_NewLogin_Ctl.address_use.getByName(local_add_ip);  //for liunx
//	    	
//		} catch (UnknownHostException e) {
//		}
//    	
//         // Trying to connect to a socket and initialize an output stream
//         try {
//             lpSocket = new Socket(Local_inadd, 40000); // host and port
//             out = new PrintWriter(lpSocket.getOutputStream(), true);
//         } catch (UnknownHostException e) {
//               System.out.println("Unknown host: ");
//               System.exit(1);
//         } catch (IOException e) {
//             System.out.println("Couldn't get I/O to "  + " connection");
//             System.exit(1);
//         }
//         
//         System.out.println("Connected to server!");
         
         
         
    	//@2
//	        boolean flag = true;  
//	        try {
//				client = new Socket("122.114.114.58", 40000);
//			} catch (UnknownHostException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//	       
//	        while(flag)
//	        {  
//	    try {
//			
//	        //client.setSoTimeout(1000);  
//
//	        //获取Socket的输出流，用来发送数据到服务端    
//	        PrintStream out = new PrintStream(client.getOutputStream());  
//	        //获取Socket的输入流，用来接收从服务端发送过来的数据    
//	        BufferedReader buf =  new BufferedReader(new InputStreamReader(client.getInputStream()));  
//
//	        
//	        System.out.println("in");
//	        
////	            System.out.print("输入信息：");  
////	            String str = input.readLine();  
//	        	if(Send_flag==1)
//	        	{
//	        		Send_flag =0;
//		            //发送数据到服务端    
//		            out.println("DENG_LU|电控北京测试端|820715|33ffde05474b363818581843||||#");  
//		            
//	        	}
//	        	
//                char[] buffer = new char[2048];
//                int charsRead = 0;
//                BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
//                if ((charsRead = in.read(buffer)) != -1)
//                {
//                    String message = new String(buffer).substring(0, charsRead);
//                    System.out.println("msg :"+message);
//                }
//	            
//                System.out.println("out");
//                
////                String echo = buf.readLine();  
////                System.out.println(echo);
//                
//
//
//                //client.close();
//                
//                try {
//                    Thread.sleep(100000);                 //1000 milliseconds is one second.
//                } catch(InterruptedException ex) {
//                    
//                }
//                
//                
//	        }  catch (UnknownHostException e1) {
//				// TODO Auto-generated catch block
//	        	System.out.println("e1");
//				e1.printStackTrace();
//			} catch (IOException e1) {
//				// TODO Auto-generated catch block
//				System.out.println("e2");
//				e1.printStackTrace();
//			}
//			
//		}   
	        
	        
	        
	    //@3
//    	try{
//			 //@1-建立连接
//	         // 根据服务器地址和端口号实例化一个Socket实例
//	         socket = new Socket("122.114.114.58", 40000);
//	         //System.out.println("Connected to server...sending echo string");
//	         // 返回此套接字的输入流，即从服务器接受的数据对象
//	         InputStream in = socket.getInputStream();
//	         // 返回此套接字的输出流，即向服务器发送的数据对象
//	         OutputStream out = socket.getOutputStream();
//	         
//	         while(true)
//	         {
//		         //@2-向服务器写温度数据
//		         // 向服务器发送从控制台接收的数据
//		         if(flag==1)
//		         {
//		        	 flag=0;
//		        	 out.write(data1);
//		         }
//		         else if(flag==0)
//		         {
//		        	
//		        	 out.write(data2);
//		         }
//		        
//		         // 接收数据的计数器，将写入数据的初始偏移量
//		         int totalBytesRcvd = 0;
//		         // 初始化接收数据的总字节数 
//		         int bytesRcvd;
//		         //while (totalBytesRcvd < data3.length) {
//		             // 服务器关闭连接，则返回 -1,read方法返回接收数据的总字节数
//		             if ((bytesRcvd = in.read(data3, totalBytesRcvd, data3.length
//		                     - totalBytesRcvd)) == -1)
//		            	 
//		                 throw new SocketException("与服务器的连接已关闭");
//		             //totalBytesRcvd += bytesRcvd;
//		         //}
//		         
//		         // 打印服务器发送来的数据
//		         System.out.println("DATA:"+new String(data3));
//		         
//	           try {
//	               Thread.sleep(20000);                 //1000 milliseconds is one second.
//	           } catch(InterruptedException ex) {
//	               
//	           }
//	         }
//		        
//		  }catch (Exception e){
//		   e.printStackTrace();
//		  }
    	
    	
    	//@4
    	//获得IP,Port等数据
    	
    	if(TCP_Run_Flag==false)
    	{
    		//@1-获取IP
    		Target_IP = TextField_ServeIP.getText();
    		//@2-获取Port
    		Target_Port =Integer.parseInt(TextField_ServePort.getText());
       		//@3-获取DeviceID
    		Target_DeviceID = TextField_DeviceID.getText();
       		//@4-获取CarNumber
    		Target_CarNumber = TextField_CarNumber.getText();
       		//@5-获取Pwd
    		Target_Pwd = TextField_Pwd.getText();

    		
	    	TCP_Run_Flag=true;
	    	TCP_Send_Data=false;	
	    	//启动TCP线程
	    	ScreensFramework.GPRS_Displsy.receive_on_off(true);		
    	}
    	else if(TCP_Run_Flag==true)
    	{
    		TCP_Run_Flag=false;
    		GPRSController.TCP_State=3;
    		Noti_Send_Flag=true;
	    	//启动TCP线程
	    	ScreensFramework.GPRS_Displsy.receive_on_off(false);	
    	}
  
    }
    
    
    /**按键监听器
     * 
     * @param event
     */
    @FXML
    public void Button_Pro(ActionEvent event)
    {
    	//@1-连接网络
    	if(event.getSource()==Button_Connect)
    	{
    		TCP_Connect();
    	}
    	//@2-接收Text清空
    	else if(event.getSource()==Button_CleanText)
    	{
    		TextAre_RecText.setText("");
    	}
    	
    }
    
   
    
    /**应用软件退出
     * 
     * @param event
     */
    @FXML
    public void AppExit_Pro(MouseEvent event)
    {
        if(event.getEventType()==MouseEvent.MOUSE_CLICKED)
        {
        	ScreensFramework.cleanAndQuit();
        }
    }
    

    /**传递主应用程序接口
     * 
     */
	@Override
	public void setScreenParent(ScreensController screenPage) {
		// TODO Auto-generated method stub
		myController = screenPage;
	}
    
}
