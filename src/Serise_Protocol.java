

/**串口协议类
 * 
 * @author Administrator
 *
 */
public class Serise_Protocol {
	

	//@1-数据接收单帧缓存
	public static byte[] Serise_Recv_Protocol_RecvBuff;
	//@2-数据接收单帧字节数
	public static int Serise_RecvFrame_Bytes=14;
	//@3-数据接收起始首字节-1
	public static byte Serise_RecvFrame_Heard1=0x00;
	//@3-数据接收起始首字节-2
	public static byte Serise_RecvFrame_Heard2=0x00;
	//@3-数据接收起始首字节定义-1
	public static byte Serise_RecvFrame_StartByte1=(byte)0xE2;
	//@3-数据接收起始首字节定义-2
	public static byte Serise_RecvFrame_StartByte2=(byte)0x08;
	//@4-接收数据接收校验和
	public static byte Serise_RecvCheck_Sum=0x00;
	
	//@5-数据发送单帧缓存
	public static byte[] Serise_Send_Protocol_RecvBuff;
	//@6-数据发送单帧字节数
	public static int Serise_SendFrame_Bytes=13;
	//@7-数据发送起始首字节
	public static byte Serise_SendFrame_Heard=0x00;
	//@8-数据发送起始首字节定义
	public static byte Serise_SendFrame_StartByte=(byte)0xD2;
	//@9-发送数据接收校验和
	public static byte Serise_SendCheck_Sum=0x00;


	
	/**串口协议构造
	 * 
	 */
	public Serise_Protocol()
	{
		//@1-初始化数据接收单帧缓存
		Serise_Recv_Protocol_RecvBuff=new byte[Serise_RecvFrame_Bytes];   
		//@2-初始化数据发送单帧缓存
		Serise_Send_Protocol_RecvBuff=new byte[Serise_SendFrame_Bytes];  
	}
	
}
