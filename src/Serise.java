


import java.io.*;
import java.util.Arrays;

import gnu.io.*;


/**串口类
 * 
 * @author Jack Ding
 * @since  2010/03/09   2014/06/23 updata
 * @version 1.1
 */
public class Serise implements SerialPortEventListener {
	
	//@1-系统串口ID号
    private CommPortIdentifier portId;  
    //@2-创建的串口标识
    private SerialPort serialPort;     
    //@3-串口输入数据流
	private InputStream inputStream; 
	//@4-串口输出数据流
	private OutputStream outputStream;  
    //@5-串口数据接收字节数（单次）
    private int numBytes; 
    //@6-串口数据接收缓冲区128Bytes
    public byte[] readBuffer = new byte[128]; 
    //@7-串口数据临时缓冲区64Bytes
    public static byte[] tempBuffer = new byte[64]; 
    //@8-串口环形数据接收缓冲区1024Bytes
    public static Serise_Buffer serise_buff=new Serise_Buffer();
    
    //@9-串口协议接收数据
    public static Serise_Protocol  AODI_Serise_Protocol = new Serise_Protocol();
    
    //@10-临时计数器
    private int Temp_Count;
    
    //@11-接收正确计数器
    public static int Recv_FrameOK_Count=0;
    //@11-接收正确计数器镜像
    public static int Recv_FrameOK_Count_Copy=0;
    //@12-接收错误计数器
    public static int Recv_FrameError_Count=0;
    //@13-发送计数器
    public static int Send_Frame_Count=0;
    
    //@14-接收字节数计数器
    public static int Recv_Bytes_Count=0;
    //@15-发送字节数计数器
    public static int Send_Bytes_Count=0;

	//@11-串口打开标志
    public static boolean isOpen = false;   
    //@12-串口工作标志
    public static boolean isStart = false; 
    
    
    //@-串口接收数据-输出电压
    public static float Recv_Data_OutputVoltage;
    //@-串口接收数据-输出电流
    public static float Recv_Data_OutputCurrent;
    //@-串口接收数据-母线电压
    public static float Recv_Data_MainVoltage;
    //@-串口接收数据-检测温度
    public static float Recv_Data_Temperature;
    

    
    /** 串口类构造方法
     * @param port
     * @param baudrate
     * @param parity
     * @param text_receive
     * @throws NoSuchPortException
     * @throws Exception
     * @author Jack Ding
     * @version 1.0
     */
    public Serise(String port,int baudrate,String parity) throws NoSuchPortException, Exception{
    	
    	int ser_parity = SerialPort.PARITY_NONE; //串口的默认校验方式为没有校验
    	
	    //获得系统串口服务
	    try{
	    	  	
	    	portId = CommPortIdentifier.getPortIdentifier(port);  //创建CommPortIdentifier对象服务
	    
	    	serialPort = (SerialPort) portId.open("Serise", 2000);  //使用portId对象服务打开串口，并获得串口对象  
	    	
	    	//判断选择的串口校验方式
            if(parity == "None")
            {
            	ser_parity=SerialPort.PARITY_NONE;
            }
            else if(parity == "Odd")
            {
            	ser_parity=SerialPort.PARITY_ODD;
            }
            else if(parity == "Even")
            {
            	ser_parity=SerialPort.PARITY_EVEN;
            }
            //设置串口工作模式
	    	serialPort.setSerialPortParams(baudrate, SerialPort.DATABITS_8, SerialPort.STOPBITS_1,ser_parity); //配置串口参数
	    	
	    	//设置当串口有数据响应
	    	serialPort.notifyOnDataAvailable(true);

	    	//设置串口超时时间
	    	try {
	    		serialPort.enableReceiveTimeout(30);
	    	} catch (UnsupportedCommOperationException e) {
	    	}
		    
			 //通过串口对象获得读串口流对象  
			inputStream = serialPort.getInputStream();
			  
			//通过串口对象获得写串口流对象  
			outputStream = serialPort.getOutputStream();  
	
			//为串口添加事件监听器
			serialPort.addEventListener(this);
			
			isOpen=true;  //串口打开
			isStart=true; //串口开始工作
			
		
	    }catch(NoSuchPortException ex){
	    	throw new Exception(ex.toString());  
	    }
	    catch(Exception ex){
	    	throw new Exception(ex.toString());  
	    }

    }
    
    
    
	@Override
	public void serialEvent(SerialPortEvent event)
	{
		// TODO Auto-generated method stub
		switch (event.getEventType())  
		{  
			case SerialPortEvent.BI:  
			case SerialPortEvent.OE:  
			case SerialPortEvent.FE:  
			case SerialPortEvent.PE:  
			case SerialPortEvent.CD:  
			case SerialPortEvent.CTS:  
			case SerialPortEvent.DSR:  
			case SerialPortEvent.RI:  
			case SerialPortEvent.OUTPUT_BUFFER_EMPTY:  
			break;  
			case SerialPortEvent.DATA_AVAILABLE:  //接收到数据
						
//				Thread Serial_Thread_In = new Thread(new Runnable() {
//					
//					@Override
//					public void run() {   
						
						//判断串口是否有数据输入
				        try {
				            while (inputStream.available() > 0) {
				                numBytes = inputStream.read(readBuffer,0, inputStream.available());
				            }
				            
				            //接收字节计数器更新
				            Recv_Bytes_Count=Recv_Bytes_Count+numBytes;
				            					            
				            //@-接收到的数据存入缓存
				            for(Temp_Count=0;Temp_Count<numBytes;Temp_Count++)
				            serise_buff.put(readBuffer[Temp_Count]);
				           				            
						    //@-缓存中数据是否大于一帧数据量
							while(serise_buff.check_buffer_size()>(AODI_Serise_Protocol.Serise_RecvFrame_Bytes+1))
							{
								//@-获取协议头字节1
								AODI_Serise_Protocol.Serise_RecvFrame_Heard1=serise_buff.get();
								//@-获取协议头字节2
								AODI_Serise_Protocol.Serise_RecvFrame_Heard2=serise_buff.get();
								
					            //System.out.println("H1:"+Serise_Protocol.Serise_RecvFrame_Heard1+"  H2:"+Serise_Protocol.Serise_RecvFrame_Heard2);

								//@-协议头检查
								if(((byte)AODI_Serise_Protocol.Serise_RecvFrame_Heard1==(byte)AODI_Serise_Protocol.Serise_RecvFrame_StartByte1)
								 &&((byte)AODI_Serise_Protocol.Serise_RecvFrame_Heard2==(byte)AODI_Serise_Protocol.Serise_RecvFrame_StartByte2))
								{

									tempBuffer[0]=AODI_Serise_Protocol.Serise_RecvFrame_Heard1;
									tempBuffer[1]=AODI_Serise_Protocol.Serise_RecvFrame_Heard1;
									
									//@-获取剩余所有数据
						            for(int i=2;i<AODI_Serise_Protocol.Serise_RecvFrame_Bytes;i++)
						            {
						            	tempBuffer[i]=serise_buff.get();	
						            }
										
						            //System.out.println("--ID:[0]"+tempBuffer[1]+"-[1]"+tempBuffer[2]+"-[2]"+tempBuffer[3]+"-[3]"+tempBuffer[4]);
						            
						            //@-判断ID=0x00000121
						            if((tempBuffer[2]==0x00)&&(tempBuffer[3]==0x00)&&(tempBuffer[4]==0x01)&&(tempBuffer[5]==0x21))
						            {
						            	//System.out.println("ok");
						            	
						            	//@-接收单帧成功计数器更新
						            	Recv_FrameOK_Count=Recv_FrameOK_Count+1;	
						            			
						            	int temp;
							            //@-数据解包-输入电压
						            	temp=(int)(((tempBuffer[6]&0xff)*256)+(tempBuffer[7]&0xff));
							            Recv_Data_OutputVoltage=(float)(temp*(0.0266));
							            //@-数据解包-母线电压
//						            	/float range=(float)(0.001+LoginController1.Send_DA_Range);
						            	temp=(int)(((tempBuffer[8]&0xff)*256)+(tempBuffer[9]&0xff));
						            	Recv_Data_MainVoltage=(float)(temp*0.027000003);
						            	//System.out.println("r:"+range);
							            //@-数据解包-输入电流
						            	temp=(int)(((tempBuffer[10]&0xff)*256)+(tempBuffer[11]&0xff));
						            	Recv_Data_OutputCurrent=(float)((2500-(temp*0.4))*0.03899999);
							            //@-数据解包-检测温度
						            	temp=(int)(((tempBuffer[12]&0xff)*256)+(tempBuffer[13]&0xff));
						            	Recv_Data_Temperature=(float)(temp*0.01);
						            }
								}
							}
							/*end 2011-04-13 ringbuff*/
							Arrays.fill(readBuffer,(byte)0); //清空接收区
							
				        } catch (IOException e) {
				        	
				        }
//					}
//				});
//				Serial_Thread_In.setDaemon(true);
//				Serial_Thread_In.setName("Serial_In");
//				Serial_Thread_In.setPriority(Thread.NORM_PRIORITY);    //设置优先级别2 
//				Serial_Thread_In.start();

			break;   
		}  
		
		
	}
    
    
    /** 串口停止工作
     * 
     */
    public void stop()  {  
    	
         if (isStart)  //判断串口是否运行
        {  
            serialPort.notifyOnDataAvailable(false);  
            serialPort.removeEventListener();  
  
            isStart = false;  
       }  
         
    }  
    
   /** 串口关闭
    * 
    */
    public void close(){  
    	
       stop();  //串口停止运行  

       if (isOpen)  //判读串口是否打开
       {  
          serialPort.close();  
          isOpen = false;  
       }
 
     }  
    
    /** 串口字符串写操作
     * 
     * @param outString
     * @return -1 or outbyte
     */
	 public int SerWrite_Str(String outString)  
	{  
		int outbyte = 0;
		
		try  
		{  
			outputStream.write(outString.getBytes()); 
			outbyte=outString.length();
			return(outbyte);
			
		} catch (IOException ex)  
		{  
			return(-1);
		}
	}  
	 
	 /** 串口hex写操作
	  * 
	  * @param outbyte
	  */
	 public int SerWrite_Hex(byte[] outbyte)
	 {
		 int outbyte1 = 0;
		 
			try  
			{  
				outputStream.write(outbyte); 
				outbyte1=outbyte.length;
				return(outbyte1);
				
			} catch (IOException ex)  
			{  
				return(-1);
			}
		 
	 }
	 	 
}
