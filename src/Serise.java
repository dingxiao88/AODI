


import java.io.*;
import java.util.Arrays;

import gnu.io.*;


/**������
 * 
 * @author Jack Ding
 * @since  2010/03/09   2014/06/23 updata
 * @version 1.1
 */
public class Serise implements SerialPortEventListener {
	
	//@1-ϵͳ����ID��
    private CommPortIdentifier portId;  
    //@2-�����Ĵ��ڱ�ʶ
    private SerialPort serialPort;     
    //@3-��������������
	private InputStream inputStream; 
	//@4-�������������
	private OutputStream outputStream;  
    //@5-�������ݽ����ֽ��������Σ�
    private int numBytes; 
    //@6-�������ݽ��ջ�����128Bytes
    public byte[] readBuffer = new byte[128]; 
    //@7-����������ʱ������64Bytes
    public static byte[] tempBuffer = new byte[64]; 
    //@8-���ڻ������ݽ��ջ�����1024Bytes
    public static Serise_Buffer serise_buff=new Serise_Buffer();
    
    //@9-����Э���������
    public static Serise_Protocol  AODI_Serise_Protocol = new Serise_Protocol();
    
    //@10-��ʱ������
    private int Temp_Count;
    
    //@11-������ȷ������
    public static int Recv_FrameOK_Count=0;
    //@11-������ȷ����������
    public static int Recv_FrameOK_Count_Copy=0;
    //@12-���մ��������
    public static int Recv_FrameError_Count=0;
    //@13-���ͼ�����
    public static int Send_Frame_Count=0;
    
    //@14-�����ֽ���������
    public static int Recv_Bytes_Count=0;
    //@15-�����ֽ���������
    public static int Send_Bytes_Count=0;

	//@11-���ڴ򿪱�־
    public static boolean isOpen = false;   
    //@12-���ڹ�����־
    public static boolean isStart = false; 
    
    
    //@-���ڽ�������-�����ѹ
    public static float Recv_Data_OutputVoltage;
    //@-���ڽ�������-�������
    public static float Recv_Data_OutputCurrent;
    //@-���ڽ�������-ĸ�ߵ�ѹ
    public static float Recv_Data_MainVoltage;
    //@-���ڽ�������-����¶�
    public static float Recv_Data_Temperature;
    

    
    /** �����๹�췽��
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
    	
    	int ser_parity = SerialPort.PARITY_NONE; //���ڵ�Ĭ��У�鷽ʽΪû��У��
    	
	    //���ϵͳ���ڷ���
	    try{
	    	  	
	    	portId = CommPortIdentifier.getPortIdentifier(port);  //����CommPortIdentifier�������
	    
	    	serialPort = (SerialPort) portId.open("Serise", 2000);  //ʹ��portId�������򿪴��ڣ�����ô��ڶ���  
	    	
	    	//�ж�ѡ��Ĵ���У�鷽ʽ
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
            //���ô��ڹ���ģʽ
	    	serialPort.setSerialPortParams(baudrate, SerialPort.DATABITS_8, SerialPort.STOPBITS_1,ser_parity); //���ô��ڲ���
	    	
	    	//���õ�������������Ӧ
	    	serialPort.notifyOnDataAvailable(true);

	    	//���ô��ڳ�ʱʱ��
	    	try {
	    		serialPort.enableReceiveTimeout(30);
	    	} catch (UnsupportedCommOperationException e) {
	    	}
		    
			 //ͨ�����ڶ����ö�����������  
			inputStream = serialPort.getInputStream();
			  
			//ͨ�����ڶ�����д����������  
			outputStream = serialPort.getOutputStream();  
	
			//Ϊ��������¼�������
			serialPort.addEventListener(this);
			
			isOpen=true;  //���ڴ�
			isStart=true; //���ڿ�ʼ����
			
		
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
			case SerialPortEvent.DATA_AVAILABLE:  //���յ�����
						
//				Thread Serial_Thread_In = new Thread(new Runnable() {
//					
//					@Override
//					public void run() {   
						
						//�жϴ����Ƿ�����������
				        try {
				            while (inputStream.available() > 0) {
				                numBytes = inputStream.read(readBuffer,0, inputStream.available());
				            }
				            
				            //�����ֽڼ���������
				            Recv_Bytes_Count=Recv_Bytes_Count+numBytes;
				            					            
				            //@-���յ������ݴ��뻺��
				            for(Temp_Count=0;Temp_Count<numBytes;Temp_Count++)
				            serise_buff.put(readBuffer[Temp_Count]);
				           				            
						    //@-�����������Ƿ����һ֡������
							while(serise_buff.check_buffer_size()>(AODI_Serise_Protocol.Serise_RecvFrame_Bytes+1))
							{
								//@-��ȡЭ��ͷ�ֽ�1
								AODI_Serise_Protocol.Serise_RecvFrame_Heard1=serise_buff.get();
								//@-��ȡЭ��ͷ�ֽ�2
								AODI_Serise_Protocol.Serise_RecvFrame_Heard2=serise_buff.get();
								
					            //System.out.println("H1:"+Serise_Protocol.Serise_RecvFrame_Heard1+"  H2:"+Serise_Protocol.Serise_RecvFrame_Heard2);

								//@-Э��ͷ���
								if(((byte)AODI_Serise_Protocol.Serise_RecvFrame_Heard1==(byte)AODI_Serise_Protocol.Serise_RecvFrame_StartByte1)
								 &&((byte)AODI_Serise_Protocol.Serise_RecvFrame_Heard2==(byte)AODI_Serise_Protocol.Serise_RecvFrame_StartByte2))
								{

									tempBuffer[0]=AODI_Serise_Protocol.Serise_RecvFrame_Heard1;
									tempBuffer[1]=AODI_Serise_Protocol.Serise_RecvFrame_Heard1;
									
									//@-��ȡʣ����������
						            for(int i=2;i<AODI_Serise_Protocol.Serise_RecvFrame_Bytes;i++)
						            {
						            	tempBuffer[i]=serise_buff.get();	
						            }
										
						            //System.out.println("--ID:[0]"+tempBuffer[1]+"-[1]"+tempBuffer[2]+"-[2]"+tempBuffer[3]+"-[3]"+tempBuffer[4]);
						            
						            //@-�ж�ID=0x00000121
						            if((tempBuffer[2]==0x00)&&(tempBuffer[3]==0x00)&&(tempBuffer[4]==0x01)&&(tempBuffer[5]==0x21))
						            {
						            	//System.out.println("ok");
						            	
						            	//@-���յ�֡�ɹ�����������
						            	Recv_FrameOK_Count=Recv_FrameOK_Count+1;	
						            			
						            	int temp;
							            //@-���ݽ��-�����ѹ
						            	temp=(int)(((tempBuffer[6]&0xff)*256)+(tempBuffer[7]&0xff));
							            Recv_Data_OutputVoltage=(float)(temp*(0.0266));
							            //@-���ݽ��-ĸ�ߵ�ѹ
//						            	/float range=(float)(0.001+LoginController1.Send_DA_Range);
						            	temp=(int)(((tempBuffer[8]&0xff)*256)+(tempBuffer[9]&0xff));
						            	Recv_Data_MainVoltage=(float)(temp*0.027000003);
						            	//System.out.println("r:"+range);
							            //@-���ݽ��-�������
						            	temp=(int)(((tempBuffer[10]&0xff)*256)+(tempBuffer[11]&0xff));
						            	Recv_Data_OutputCurrent=(float)((2500-(temp*0.4))*0.03899999);
							            //@-���ݽ��-����¶�
						            	temp=(int)(((tempBuffer[12]&0xff)*256)+(tempBuffer[13]&0xff));
						            	Recv_Data_Temperature=(float)(temp*0.01);
						            }
								}
							}
							/*end 2011-04-13 ringbuff*/
							Arrays.fill(readBuffer,(byte)0); //��ս�����
							
				        } catch (IOException e) {
				        	
				        }
//					}
//				});
//				Serial_Thread_In.setDaemon(true);
//				Serial_Thread_In.setName("Serial_In");
//				Serial_Thread_In.setPriority(Thread.NORM_PRIORITY);    //�������ȼ���2 
//				Serial_Thread_In.start();

			break;   
		}  
		
		
	}
    
    
    /** ����ֹͣ����
     * 
     */
    public void stop()  {  
    	
         if (isStart)  //�жϴ����Ƿ�����
        {  
            serialPort.notifyOnDataAvailable(false);  
            serialPort.removeEventListener();  
  
            isStart = false;  
       }  
         
    }  
    
   /** ���ڹر�
    * 
    */
    public void close(){  
    	
       stop();  //����ֹͣ����  

       if (isOpen)  //�ж������Ƿ��
       {  
          serialPort.close();  
          isOpen = false;  
       }
 
     }  
    
    /** �����ַ���д����
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
	 
	 /** ����hexд����
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
