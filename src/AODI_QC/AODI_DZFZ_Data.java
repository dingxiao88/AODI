package AODI_QC;

public class AODI_DZFZ_Data {

	 //-------------创建DZFZ就需确定的参数-------------------------
	//@1-电子负载唯一CAN地址识别ID
	public byte[] CAN_ID = new byte[4];

	//@2-电子负载所属Group号
	public int Group_ID;

	//@3-电子负载所属Group中的位置号
	public int Group_Location_ID;

	 //-------------创建DZFZ后可变参数-------------------------
	//@4-电子负载-老化产品唯一识别码
	public String Pud_ID;

	//@7-电子负载-老化产品运行标志
	public int Pud_RunFlag;     //0:停止  1:运行   2:故障

	//@4-电子负载-老化采集数据计数
	public int Pud_TestData_Count;

	//@4-电子负载-老化运行时间
	public int Pud_RunTime;

	//@7-电子负载-老化定时时间(默认2h=7200s)
	public int Pud_Timing = 7200;

	//@5-电子负载-老化产品起始时间标志
	public boolean Pud_Test_Start_Time_Flag;

	//@5-电子负载-老化产品起始时间
	public String Pud_Test_Start_Time;

	//@6-电子负载-老化产品采样时间
	public String Pud_Test_Scan_Time;

	//@7-电子负载-老化产品结束标志
	public boolean Pud_Test_FinshFlag;

	//@8-电子负载-老化产品-采样参数-电压
	public float Pud_Test_ScanVoltage;

	//@9-电子负载-老化产品-采样参数-电流
	public float Pud_Test_ScanCurrent;

	//@10-电子负载-老化产品-采样参数-温度1
	public float Pud_Test_ScanTemputer1;
	//@10-电子负载-老化产品-采样参数-温度2
	public float Pud_Test_ScanTemputer2;
	//@10-电子负载-老化产品-采样参数-温度3
	public float Pud_Test_ScanTemputer3;

	//@11-电子负载-老化产品-老化状态
	public int Pud_Test_ConfigStatus = 0;   //0:节点未配置(config_no)  1:节点已配置(config_yes)

	//@11-电子负载-老化产品-老化状态
	public int Pud_Test_Status = 0;    //0:节点未配置(config_no)  1:节点已配置(config_yes) 2:节点老化停止(status_warning)  3:节点老化正常(status_ok)  4:节点老化故障(status_error)  5:节点老化结束(status_end)

	//@11-电子负载-老化产品-离组原因
	public int Pud_Test_LeaveReason = 0;   //0:没有离组  1:故障  2:人为检测

	//@-电子负载-老化产品-型号
	public String Pud_Test_PudModel;


	/**电子负载构造
	 *
	 */
	public AODI_DZFZ_Data(int Group, int Group_Location)
	{
		//@设置CAN唯一ID
		CAN_ID[0] = 0x00;
		CAN_ID[1] = 0x00;
		CAN_ID[2] = (byte)(Group+1);
		CAN_ID[3] = (byte)(Group_Location+1);
		//@-设置Group号
		Group_ID = Group+1;
		//@-设置Group位置号
		Group_Location_ID = Group_Location+1;

		//@-其他参数初始化
		DZFZ_OtherData_Init();

	}

	/**运行参数初始化
	 *
	 */
	public void DZFZ_RuningData_Init()
	{
		//@4-电子负载-老化采集数据计数
		Pud_TestData_Count = 0;

		//@7-电子负载-老化产品运行标志
		Pud_RunFlag = 0;

		//@3-电子负载-老化运行时间
		Pud_RunTime = 0;

		//@7-电子负载-老化定时时间(默认2h=7200s)
		//Pud_Timing = 7200;

		//@4-电子负载-老化产品起始时间标志
		Pud_Test_Start_Time_Flag = false;

		//@4-电子负载-老化产品起始时间
		Pud_Test_Start_Time = new String("00:00:00");

		//@5-电子负载-老化产品采样时间
		Pud_Test_Scan_Time = new String("00:00:00");

		//@6-电子负载-老化产品结束标志
		Pud_Test_FinshFlag = false;

		//@7-电子负载-老化产品-采样参数-电压
		Pud_Test_ScanVoltage = 0;

		//@8-电子负载-老化产品-采样参数-电流
		Pud_Test_ScanCurrent = 0;

		//@8-电子负载-老化产品-采样参数-温度
		Pud_Test_ScanTemputer1 = 0;
		Pud_Test_ScanTemputer2 = 0;
		Pud_Test_ScanTemputer3 = 0;
	}


	/**其他参数初始化
	 *
	 */
	public void DZFZ_OtherData_Init()
	{
		//@3-电子负载-老化产品唯一识别码
		Pud_ID = new String("--------");

		//@3-电子负载-老化产品运行标志
		Pud_RunFlag = 0;

		//@4-电子负载-老化采集数据计数
		Pud_TestData_Count = 0;

		//@3-电子负载-老化运行时间
		Pud_RunTime = 0;

		//@7-电子负载-老化定时时间(默认2h=7200s)
		Pud_Timing = 7200;

		//@4-电子负载-老化产品起始时间标志
		Pud_Test_Start_Time_Flag = false;

		//@4-电子负载-老化产品起始时间
		Pud_Test_Start_Time = new String("00:00:00");

		//@5-电子负载-老化产品采样时间
		Pud_Test_Scan_Time = new String("00:00:00");

		//@6-电子负载-老化产品结束标志
		Pud_Test_FinshFlag = false;

		//@7-电子负载-老化产品-采样参数-电压
		Pud_Test_ScanVoltage = 0;

		//@8-电子负载-老化产品-采样参数-电流
		Pud_Test_ScanCurrent = 0;

		//@8-电子负载-老化产品-采样参数-温度
		Pud_Test_ScanTemputer1 = 0;
		Pud_Test_ScanTemputer2 = 0;
		Pud_Test_ScanTemputer3 = 0;

		//@8-电子负载-老化产品-未配置
		Pud_Test_Status = 0;

		//@11-电子负载-老化产品-离组原因
		Pud_Test_LeaveReason = 0;
	}



	/**获取CAN地址
	 *
	 * @return
	 */
	public byte[] Get_CAN_ID()
	{
		return CAN_ID;
	}

	/**获取Group号
	 *
	 * @return
	 */
	public int Get_Group_ID()
	{
		return Group_ID;
	}

	/**获取Group位置号
	 *
	 * @return
	 */
	public int Get_Group_Location_ID()
	{
		return Group_Location_ID;
	}


	/**设置或获取老化产品唯一识别码
	 *
	 * @param ID
	 */
	public void Set_Pud_ID(String ID)
	{
		Pud_ID = new String(ID);
	}
	public String Get_Pud_ID()
	{
		return Pud_ID;
	}


	/**设置或获取老化产品运行标志
	 *
	 * @param Flag
	 */
	public void Set_Pud_RunFlag(int Flag)
	{
		Pud_RunFlag = Flag;
	}
	public int Get_Pud_RunFlag()
	{
		return Pud_RunFlag;
	}


	/**设置或获取老化采集数据计数
	 *
	 * @param Count
	 */
	public void Set_TestData_Count(int Count)
	{
		Pud_TestData_Count = Count;
	}
	public void Set_TestData_Count_Add()
	{
		Pud_TestData_Count = Pud_TestData_Count + 1;
	}
	public int Get_TestData_Count()
	{
		return Pud_TestData_Count;
	}


	/**设置或获取老化运行时间
	 *
	 * @param Time
	 */
	public void Set_Pud_RunTime(int Time)
	{
		Pud_RunTime = Time;
	}
	public int Get_Pud_RunTime()
	{
		return Pud_RunTime;
	}

	/**设置或获取老化定时时间
	 *
	 * @param Time
	 */
	public void Set_Pud_Timing(int Time)
	{
		Pud_Timing = Time;
	}
	public int Get_Pud_Timing()
	{
		return Pud_Timing;
	}

	/**设置或获取老化产品唯一识别码
	 *
	 * @param Flag
	 */
	public void Set_Pud_Test_Start_Time_Flag(boolean Flag)
	{
		Pud_Test_Start_Time_Flag = Flag;
	}
	public boolean Get_Pud_Test_Start_Time_Flag()
	{
		return Pud_Test_Start_Time_Flag;
	}

	/**设置或获取老化产品起始时间
	 *
	 * @param Time
	 */
	public void Set_Pud_Test_Start_Time(String Time)
	{
		if(Pud_Test_Start_Time_Flag==false)
		{
			Pud_Test_Start_Time_Flag = true;
			Pud_Test_Start_Time = new String(Time);
		}
	}
	public String Get_Pud_Test_Start_Time()
	{
		return Pud_Test_Start_Time;
	}


	/**设置或获取老化产品采样时间
	 *
	 * @param Time
	 */
	public void Set_Pud_Test_Scan_Time(String Time)
	{
		Pud_Test_Scan_Time = new String(Time);
	}
	public String Get_Pud_Test_Scan_Time()
	{
		return Pud_Test_Scan_Time;
	}


	/**设置或获取老化产品结束标志
	 *
	 * @param Flag
	 */
	public void Set_Pud_Test_FinshFlag(boolean Flag)
	{
		Pud_Test_FinshFlag = Flag;
	}
	public boolean Get_Pud_Test_FinshFlag()
	{
		return Pud_Test_FinshFlag;
	}


	/**设置或获取老化产品-采样参数-电压
	 *
	 * @param Value
	 */
	public void Set_Pud_Test_ScanVoltage(float Value)
	{
		Pud_Test_ScanVoltage = Value;
	}
	public float Get_Pud_Test_ScanVoltage()
	{
		return Pud_Test_ScanVoltage;
	}


	/**设置或获取老化产品-采样参数-电流
	 *
	 * @param Value
	 */
	public void Set_Pud_Test_ScanCurrent(float Value)
	{
		Pud_Test_ScanCurrent = Value;
	}
	public float Get_Pud_Test_ScanCurrent()
	{
		return Pud_Test_ScanCurrent;
	}


	/**设置或获取老化产品-采样参数-温度1
	 *
	 * @param Value
	 */
	public void Set_Pud_Test_ScanTemputer1(float Value)
	{
		Pud_Test_ScanTemputer1 = Value;
	}
	public float Get_Pud_Test_ScanTemputer1()
	{
		return Pud_Test_ScanTemputer1;
	}

	/**设置或获取老化产品-采样参数-温度2
	 *
	 * @param Value
	 */
	public void Set_Pud_Test_ScanTemputer2(float Value)
	{
		Pud_Test_ScanTemputer2 = Value;
	}
	public float Get_Pud_Test_ScanTemputer2()
	{
		return Pud_Test_ScanTemputer2;
	}

	/**设置或获取老化产品-采样参数-温度3
	 *
	 * @param Value
	 */
	public void Set_Pud_Test_ScanTemputer3(float Value)
	{
		Pud_Test_ScanTemputer3 = Value;
	}
	public float Get_Pud_Test_ScanTemputer3()
	{
		return Pud_Test_ScanTemputer3;
	}


	/**设置或获取老化产品-配置状态
	 *
	 * @param Status
	 */
	public void Set_Pud_Test_ConfigStatus(int Status)
	{
		Pud_Test_ConfigStatus = Status;
	}
	public int Get_Pud_Test_ConfigStatus()
	{
		return Pud_Test_ConfigStatus;
	}

	/**设置或获取老化产品-老化状态
	 *
	 * @param Status
	 */
	public void Set_Pud_Test_Status(int Status)
	{
		Pud_Test_Status = Status;
	}
	public int Get_Pud_Test_Status()
	{
		return Pud_Test_Status;
	}


	/**设置或获取老化产品-离组原因
	 *
	 * @param Reason
	 */
	public void Set_Pud_LeaveReason(int Reason)
	{
		Pud_Test_LeaveReason = Reason;
	}
	public int Get_Pud_LeaveReason()
	{
		return Pud_Test_LeaveReason;
	}


	/**设置或获取老化产品-产品型号
	 *
	 * @param Model
	 */
	public void Set_Pud_Test_PudModel(String Model)
	{
		Pud_Test_PudModel = new String(Model);
	}
	public String Get_Pud_Test_PudModel()
	{
		return Pud_Test_PudModel;
	}

}
