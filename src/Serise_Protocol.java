

/**����Э����
 * 
 * @author Administrator
 *
 */
public class Serise_Protocol {
	

	//@1-���ݽ��յ�֡����
	public static byte[] Serise_Recv_Protocol_RecvBuff;
	//@2-���ݽ��յ�֡�ֽ���
	public static int Serise_RecvFrame_Bytes=14;
	//@3-���ݽ�����ʼ���ֽ�-1
	public static byte Serise_RecvFrame_Heard1=0x00;
	//@3-���ݽ�����ʼ���ֽ�-2
	public static byte Serise_RecvFrame_Heard2=0x00;
	//@3-���ݽ�����ʼ���ֽڶ���-1
	public static byte Serise_RecvFrame_StartByte1=(byte)0xE2;
	//@3-���ݽ�����ʼ���ֽڶ���-2
	public static byte Serise_RecvFrame_StartByte2=(byte)0x08;
	//@4-�������ݽ���У���
	public static byte Serise_RecvCheck_Sum=0x00;
	
	//@5-���ݷ��͵�֡����
	public static byte[] Serise_Send_Protocol_RecvBuff;
	//@6-���ݷ��͵�֡�ֽ���
	public static int Serise_SendFrame_Bytes=13;
	//@7-���ݷ�����ʼ���ֽ�
	public static byte Serise_SendFrame_Heard=0x00;
	//@8-���ݷ�����ʼ���ֽڶ���
	public static byte Serise_SendFrame_StartByte=(byte)0xD2;
	//@9-�������ݽ���У���
	public static byte Serise_SendCheck_Sum=0x00;


	
	/**����Э�鹹��
	 * 
	 */
	public Serise_Protocol()
	{
		//@1-��ʼ�����ݽ��յ�֡����
		Serise_Recv_Protocol_RecvBuff=new byte[Serise_RecvFrame_Bytes];   
		//@2-��ʼ�����ݷ��͵�֡����
		Serise_Send_Protocol_RecvBuff=new byte[Serise_SendFrame_Bytes];  
	}
	
}
