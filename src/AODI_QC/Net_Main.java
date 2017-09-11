package AODI_QC;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Enumeration;

import javafx.application.Platform;


/** ������
 *
 * @author Jack Ding
 *
 *
 */
public class Net_Main implements Runnable{

	public  Thread Network_Send_thread=new Thread(this);

	private	InetAddress Local_inadd;                           //���������ַ
	private	InetAddress Remote_inadd;                          //Զ�������ַ
	private int Local_port;                                    //���������ַport��
	private int Remote_port;                                   //Զ�������ַport��
	private	InetAddress Multicast_inadd;                       //�鲥�����ַ
	private int Multicast_port;                                //�鲥�����ַport��
	public static DatagramSocket UDP_socket;                   //����UDP socket
	public static MulticastSocket Multicast_socket;            //�����鲥socket
    private byte[] receive_cast_buff = new byte[128];          //������ջ���128Byte
	public static DatagramPacket UDP_Packet,Multicast_Packet;  //����������ݰ�
    private byte[] receive_udp_buff = new byte[128];           //������ջ���128Byte

	public static boolean isOpen = false;                      //����򿪱�־
	public static boolean isClose = true;                      //����رձ�־
	public static int isError = 0;                             //�����־     0:û�д���  1:��ñ�����ַ����
	public static int sendmode=0;                              //���緢�ͷ�ʽ  0:�㲥   1:�鲥

    private Net_Main_Receive Net_Receive;                      //�����������

    //@1-Э���
    private DatagramPacket Send_Packet;
    //@2-���緢��Э�����ݰ�
	public static byte[]  Send_Buff= new byte[13];
	//@3-���緢�ʹ�����־
	public static boolean  Send_Flag=false;
    //@4-���緢�ͼ���
    public static long Net_Send_Count=0;


	//@5-ȡ�ñ���Ip��ַ
	public static InetAddress address;
	//@6-����IP����ģʽ
	public static int NetLink_Mode=1;  //1:����Ӳ��������  2:���������Ӳ���ip��ȷ 3:���������ӵ�ip����ȷ
	//@7-����IP����ģʽCopy
	public static int NetLink_Mode_Copy=0;
	//@8-����IP����ģʽˢ��
	public static boolean NetLink_Mode_Flash=false;
	//@9-����IP��ַ
	public static String Local_IP;
	//@10-�趨Ip��ַ
	//public static String Set_IP = new String("192.168.1.101");
	//@11-�趨Port
	public static int Set_Port = 6000;
	//@10-�趨�鲥Ip��ַ
	public static String Set_Mcast_IP = new String("224.100.23.180");
	//@10-�趨PCB Ip��ַ
	public static String Set_PCB_IP = new String("192.168.1.18");

	public static boolean Net_Interface_Set = false;





/**
 *
 * @param local_add_ip        ������ʾ��������ip��ַ
 * @param local_add_port      ���뱾������������port��
 * @param Remote_add_ip       ����Զ������ip��ַ
 * @param Remote_add_port     ����Զ������Port��
 * @param Multicast_add_ip    �����鲥ip��ַ
 * @param Multicast_add_port  �����鲥port��
 * @param Send_Mode           ���뷢�ͷ�ʽ �㲥�����鲥   0 or 1
 */
	public Net_Main(String local_add_ip,int local_add_port,String Remote_add_ip,int Remote_add_port,String Multicast_add_ip,int Multicast_add_port,int Send_Mode){

		Network_Send_thread.setPriority(9);

		sendmode=Send_Mode;  //�洢���ͷ�ʽ  �㲥���鲥
		Multicast_port=Multicast_add_port;
		Remote_port=Remote_add_port;
		Local_port=local_add_port;


		//---------�����㲥-----------------------
		try {
			Local_inadd=InetAddress.getByName(local_add_ip);   //for windows
			//Local_inadd=SWJ_NewLogin_Ctl.address_use.getByName(local_add_ip);  //for liunx

		} catch (UnknownHostException e) {
		}

		//��ȡ�ƶ�Զ�˵�ַ----------------------
		try {
			 Remote_inadd=InetAddress.getByName(Remote_add_ip);  //for windows
			//Remote_inadd=SWJ_NewLogin_Ctl.address_use.getByName(Remote_add_ip);  //for liunx
		} catch (UnknownHostException e1) {
		}

		if(Send_Mode==0)  //�㲥����
		{
			try {
				UDP_socket=new DatagramSocket(Local_port,Local_inadd);
			} catch (SocketException e) {
			}

			UDP_Packet = new DatagramPacket(receive_udp_buff,receive_udp_buff.length,Local_inadd,Local_port);  //����UDP���ݽ��հ�
			//--------------end----------------

			//---------------------------------�㲥------------------------------------
			//��ȡ�ƶ�������ַ----------------------
			try {
				Local_inadd=InetAddress.getByName(local_add_ip);  //for windows
				//Local_inadd=SWJ_NewLogin_Ctl.address_use.getByName(local_add_ip);  //for liunx

			} catch (UnknownHostException e) {
				isError=1;  //����ƶ�IP��ַ����
			}

			//��ȡ�ƶ�Զ�˵�ַ----------------------
			try {
				 Remote_inadd=InetAddress.getByName(Remote_add_ip);  //for windows
				 //Remote_inadd=SWJ_NewLogin_Ctl.address_use.getByName(Remote_add_ip);  //for liunx
			} catch (UnknownHostException e1) {
				isError=2;  //����ƶ�IP��ַ����
			}

			//����UDP����Socket---------------------
			if(isError==0)  //����ƶ�������ַû�д���
			{
				try {
					UDP_socket=new DatagramSocket(local_add_port,Local_inadd);
					UDP_socket.setSoTimeout(1000);

				} catch (SocketException e) {
					isError=2;  //����socket����
				}

			}
			UDP_Packet = new DatagramPacket(receive_udp_buff,receive_udp_buff.length,Local_inadd,Local_port);  //����UDP���ݽ��հ�

		}

		else if (Send_Mode==1)  //�鲥����
		{
			//---------------------------------�鲥------------------------------------
			//��ȡ�ƶ��鲥IP��ַ----------------------
			try {
				Multicast_inadd = InetAddress.getByName(Multicast_add_ip);
				//Multicast_inadd=SWJ_NewLogin_Ctl.address_use.getByName(Multicast_add_ip);
			} catch (UnknownHostException e1) {
				isError=3;  //����ƶ��鲥IP��ַ����
			}


			//�����鲥����socket-------------------------
			try {
				Multicast_socket = new MulticastSocket(Multicast_port); //�����鲥Socket
				Multicast_socket.joinGroup(Multicast_inadd);  //�����鲥��ַIP
				Multicast_socket.setSoTimeout(10);  //���ý��ճ�ʱʱ��
			} catch (IOException e1) {
				isError=4;  //�����鲥socket����
				e1.printStackTrace();
			}
			Multicast_Packet = new DatagramPacket(receive_cast_buff,receive_cast_buff.length,Local_inadd,Local_port);  //�����鲥���ݽ��հ�


		    //��������Э�����ݰ�
			Send_Packet=new DatagramPacket(Send_Buff,Send_Buff.length, Multicast_inadd,Multicast_port);


		}
		//-----------------------------------------------

		if(isError==0)  //ȷ�������紴��������û�д�����
		{
			isOpen=true;
			isClose=false;

			if(Send_Mode==0)   //�㲥
			{
				//��ӡ��Ϣ��������
			}
			else if(Send_Mode==1)  //�鲥
			{
				//��ӡ��Ϣ��������
			}
		}
		else
		{
			//��ӡ��Ϣ��������
		}

		Net_Receive =new Net_Main_Receive(20);  //�鲥�������20/s  --���ղ��ö�ʱ�������� 0.2s
		Net_Receive.receive_on_off(true);
	}

	/** ����ر�
	 *
	 */
	public void close(){

//		if(isError!=2&&sendmode==0)
//	    UDP_socket.close();

		if(isError!=4&&sendmode==1)
		{
			Net_Receive.stop();
			Net_Receive=null;
			//Multicast_socket.close();
		}

		isError=0;     //�����־��λ
		isOpen=false;  //�ı��־״̬
		isClose=true;  //�ı��־״̬

	}
	/**
	 *
	 */
	public void Thread_alive_agin()
	{
		Network_Send_thread=new Thread(this);
		Network_Send_thread.setName("Net-Send");
		Network_Send_thread.setDaemon(true);
		Network_Send_thread.setPriority(3);    //�������ȼ���3
	}

    /** ���緢��
     *
     *
     */
	@Override
	public void run() {

		//�������緢��
		if(isOpen==true)  //�����Ѵ�
		{

			    //@-CAN�������
				Send_Buff[0] = QC_DisplayTimer.CAN_Start;

				Send_Buff[1] = QC_DisplayTimer.CAN_ID[0];
				Send_Buff[2] = QC_DisplayTimer.CAN_ID[1];
				Send_Buff[3] = QC_DisplayTimer.CAN_ID[2];
				Send_Buff[4] = QC_DisplayTimer.CAN_ID[3];


				Send_Buff[5] = QC_DisplayTimer.CAN_CMD;

				Send_Buff[6] =  QC_DisplayTimer.CAN_Data[0];
				Send_Buff[7] =  QC_DisplayTimer.CAN_Data[1];
				Send_Buff[8] =  QC_DisplayTimer.CAN_Data[2];
				Send_Buff[9] =  QC_DisplayTimer.CAN_Data[3];
				Send_Buff[10] = QC_DisplayTimer.CAN_Data[4];
				Send_Buff[11] = QC_DisplayTimer.CAN_Data[5];
				Send_Buff[12] = QC_DisplayTimer.CAN_Data[6];


				Send_Packet=new DatagramPacket(Send_Buff,Send_Buff.length, Multicast_inadd,Multicast_port);
				try {
					Multicast_socket.send(Send_Packet);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				//@-���緢�ͼ���
				Net_Send_Count = Net_Send_Count + 1;

				//@-����������־
				Send_Flag=false;
		}
	}

	/**
	 *
	 */
	public static void Check_Net_Link()
	{
		Enumeration<NetworkInterface> interfaces = null;
		try {
			interfaces = NetworkInterface.getNetworkInterfaces();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        while (interfaces.hasMoreElements())
        {
            NetworkInterface networkInterface = interfaces.nextElement();
            //System.out.println("Network Interface Name : [" + networkInterface.getDisplayName() + "]");

            //ScreensFramework.Debug_String = new String("Network Interface Name : [" + networkInterface.getDisplayName() + "]\n");


            //@-���ݾ�������Ӳ�����ý����޸�
            //if(networkInterface.getDisplayName().equals("eth0"))
            //if(networkInterface.getDisplayName().equals("Software Loopback Interface 1"))
            if(networkInterface.getDisplayName().equals(Config.NetCard_Name))
            {
            	try {
            		//����Ӳ������
					if(networkInterface.isUp()==true)
					{
						//System.out.println("isup");

			            for (InterfaceAddress i : networkInterface.getInterfaceAddresses())
			            {
			            	String ip = i.getAddress().getHostAddress();
			            	//System.out.println("ip:" + ip.length());
			            	if(ip.length()<15)
			            	{
			            		//System.out.println("ip:" + ip);
				                if(ip.equals(Config.Config_NetIP))
				                {
									Local_IP=new String(i.getAddress().getHostAddress());
									NetLink_Mode=2;
				                }
				                else
				                {
									Local_IP=new String(i.getAddress().getHostAddress());
									NetLink_Mode=3;
				                	continue;
				                }
			            	}
			            }
					}
					//����Ӳ��δ����
					else if(networkInterface.isUp()==false)
					{
						//System.out.println("out");
						NetLink_Mode=1;
					}

				} catch (SocketException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

            }
            //�ػ���ַ
            else if(networkInterface.getDisplayName().equals("lo"))
            {
            	continue;
            }

            //System.out.println("ip:" + Local_IP);
        }
	}

}
