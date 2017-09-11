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


/** 网络类
 *
 * @author Jack Ding
 *
 *
 */
public class Net_Main implements Runnable{

	public  Thread Network_Send_thread=new Thread(this);

	private	InetAddress Local_inadd;                           //本地网络地址
	private	InetAddress Remote_inadd;                          //远端网络地址
	private int Local_port;                                    //本地网络地址port口
	private int Remote_port;                                   //远端网络地址port口
	private	InetAddress Multicast_inadd;                       //组播网络地址
	private int Multicast_port;                                //组播网络地址port口
	public static DatagramSocket UDP_socket;                   //创建UDP socket
	public static MulticastSocket Multicast_socket;            //创建组播socket
    private byte[] receive_cast_buff = new byte[128];          //网络接收缓存128Byte
	public static DatagramPacket UDP_Packet,Multicast_Packet;  //网络接收数据包
    private byte[] receive_udp_buff = new byte[128];           //网络接收缓存128Byte

	public static boolean isOpen = false;                      //网络打开标志
	public static boolean isClose = true;                      //网络关闭标志
	public static int isError = 0;                             //错误标志     0:没有错误  1:获得本机地址出错
	public static int sendmode=0;                              //网络发送方式  0:点播   1:组播

    private Net_Main_Receive Net_Receive;                      //网络接收类标号

    //@1-协议包
    private DatagramPacket Send_Packet;
    //@2-网络发送协议数据包
	public static byte[]  Send_Buff= new byte[13];
	//@3-网络发送触发标志
	public static boolean  Send_Flag=false;
    //@4-网络发送计数
    public static long Net_Send_Count=0;


	//@5-取得本地Ip地址
	public static InetAddress address;
	//@6-本地IP连接模式
	public static int NetLink_Mode=1;  //1:网络硬件无连接  2:网络已连接并且ip正确 3:网络已连接但ip不正确
	//@7-本地IP连接模式Copy
	public static int NetLink_Mode_Copy=0;
	//@8-本地IP连接模式刷新
	public static boolean NetLink_Mode_Flash=false;
	//@9-本地IP地址
	public static String Local_IP;
	//@10-设定Ip地址
	//public static String Set_IP = new String("192.168.1.101");
	//@11-设定Port
	public static int Set_Port = 6000;
	//@10-设定组播Ip地址
	public static String Set_Mcast_IP = new String("224.100.23.180");
	//@10-设定PCB Ip地址
	public static String Set_PCB_IP = new String("192.168.1.18");

	public static boolean Net_Interface_Set = false;





/**
 *
 * @param local_add_ip        回馈显示本地主机ip地址
 * @param local_add_port      输入本地主机监听的port口
 * @param Remote_add_ip       输入远端主机ip地址
 * @param Remote_add_port     输入远端主机Port口
 * @param Multicast_add_ip    输入组播ip地址
 * @param Multicast_add_port  输入组播port口
 * @param Send_Mode           输入发送方式 点播或者组播   0 or 1
 */
	public Net_Main(String local_add_ip,int local_add_port,String Remote_add_ip,int Remote_add_port,String Multicast_add_ip,int Multicast_add_port,int Send_Mode){

		Network_Send_thread.setPriority(9);

		sendmode=Send_Mode;  //存储发送方式  点播或组播
		Multicast_port=Multicast_add_port;
		Remote_port=Remote_add_port;
		Local_port=local_add_port;


		//---------创建点播-----------------------
		try {
			Local_inadd=InetAddress.getByName(local_add_ip);   //for windows
			//Local_inadd=SWJ_NewLogin_Ctl.address_use.getByName(local_add_ip);  //for liunx

		} catch (UnknownHostException e) {
		}

		//获取制定远端地址----------------------
		try {
			 Remote_inadd=InetAddress.getByName(Remote_add_ip);  //for windows
			//Remote_inadd=SWJ_NewLogin_Ctl.address_use.getByName(Remote_add_ip);  //for liunx
		} catch (UnknownHostException e1) {
		}

		if(Send_Mode==0)  //点播发送
		{
			try {
				UDP_socket=new DatagramSocket(Local_port,Local_inadd);
			} catch (SocketException e) {
			}

			UDP_Packet = new DatagramPacket(receive_udp_buff,receive_udp_buff.length,Local_inadd,Local_port);  //创建UDP数据接收包
			//--------------end----------------

			//---------------------------------点播------------------------------------
			//获取制定本机地址----------------------
			try {
				Local_inadd=InetAddress.getByName(local_add_ip);  //for windows
				//Local_inadd=SWJ_NewLogin_Ctl.address_use.getByName(local_add_ip);  //for liunx

			} catch (UnknownHostException e) {
				isError=1;  //获得制定IP地址出错
			}

			//获取制定远端地址----------------------
			try {
				 Remote_inadd=InetAddress.getByName(Remote_add_ip);  //for windows
				 //Remote_inadd=SWJ_NewLogin_Ctl.address_use.getByName(Remote_add_ip);  //for liunx
			} catch (UnknownHostException e1) {
				isError=2;  //获得制定IP地址出错
			}

			//创建UDP连接Socket---------------------
			if(isError==0)  //获得制定本机地址没有错误
			{
				try {
					UDP_socket=new DatagramSocket(local_add_port,Local_inadd);
					UDP_socket.setSoTimeout(1000);

				} catch (SocketException e) {
					isError=2;  //创建socket出错
				}

			}
			UDP_Packet = new DatagramPacket(receive_udp_buff,receive_udp_buff.length,Local_inadd,Local_port);  //创建UDP数据接收包

		}

		else if (Send_Mode==1)  //组播发送
		{
			//---------------------------------组播------------------------------------
			//获取制定组播IP地址----------------------
			try {
				Multicast_inadd = InetAddress.getByName(Multicast_add_ip);
				//Multicast_inadd=SWJ_NewLogin_Ctl.address_use.getByName(Multicast_add_ip);
			} catch (UnknownHostException e1) {
				isError=3;  //获得制定组播IP地址出错
			}


			//创建组播连接socket-------------------------
			try {
				Multicast_socket = new MulticastSocket(Multicast_port); //创建组播Socket
				Multicast_socket.joinGroup(Multicast_inadd);  //加入组播地址IP
				Multicast_socket.setSoTimeout(10);  //设置接收超时时间
			} catch (IOException e1) {
				isError=4;  //创建组播socket出错
				e1.printStackTrace();
			}
			Multicast_Packet = new DatagramPacket(receive_cast_buff,receive_cast_buff.length,Local_inadd,Local_port);  //创建组播数据接收包


		    //创建发送协议数据包
			Send_Packet=new DatagramPacket(Send_Buff,Send_Buff.length, Multicast_inadd,Multicast_port);


		}
		//-----------------------------------------------

		if(isError==0)  //确认在网络创建过程中没有错误发生
		{
			isOpen=true;
			isClose=false;

			if(Send_Mode==0)   //点播
			{
				//打印信息至主界面
			}
			else if(Send_Mode==1)  //组播
			{
				//打印信息至主界面
			}
		}
		else
		{
			//打印信息至主界面
		}

		Net_Receive =new Net_Main_Receive(20);  //组播网络接收20/s  --接收采用定时触发接收 0.2s
		Net_Receive.receive_on_off(true);
	}

	/** 网络关闭
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

		isError=0;     //错误标志复位
		isOpen=false;  //改变标志状态
		isClose=true;  //改变标志状态

	}
	/**
	 *
	 */
	public void Thread_alive_agin()
	{
		Network_Send_thread=new Thread(this);
		Network_Send_thread.setName("Net-Send");
		Network_Send_thread.setDaemon(true);
		Network_Send_thread.setPriority(3);    //设置优先级别3
	}

    /** 网络发送
     *
     *
     */
	@Override
	public void run() {

		//增加网络发送
		if(isOpen==true)  //网络已打开
		{

			    //@-CAN数据组包
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

				//@-网络发送计数
				Net_Send_Count = Net_Send_Count + 1;

				//@-发送完毕清标志
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


            //@-根据具体计算机硬件配置进行修改
            //if(networkInterface.getDisplayName().equals("eth0"))
            //if(networkInterface.getDisplayName().equals("Software Loopback Interface 1"))
            if(networkInterface.getDisplayName().equals(Config.NetCard_Name))
            {
            	try {
            		//网络硬件连接
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
					//网络硬件未连接
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
            //回环地址
            else if(networkInterface.getDisplayName().equals("lo"))
            {
            	continue;
            }

            //System.out.println("ip:" + Local_IP);
        }
	}

}
