package AODI_QC;

public class AODI_DZFZ_Data {

	 //-------------����DZFZ����ȷ���Ĳ���-------------------------
	//@1-���Ӹ���ΨһCAN��ַʶ��ID
	public byte[] CAN_ID = new byte[4];

	//@2-���Ӹ�������Group��
	public int Group_ID;

	//@3-���Ӹ�������Group�е�λ�ú�
	public int Group_Location_ID;

	 //-------------����DZFZ��ɱ����-------------------------
	//@4-���Ӹ���-�ϻ���ƷΨһʶ����
	public String Pud_ID;

	//@7-���Ӹ���-�ϻ���Ʒ���б�־
	public int Pud_RunFlag;     //0:ֹͣ  1:����   2:����

	//@4-���Ӹ���-�ϻ��ɼ����ݼ���
	public int Pud_TestData_Count;

	//@4-���Ӹ���-�ϻ�����ʱ��
	public int Pud_RunTime;

	//@7-���Ӹ���-�ϻ���ʱʱ��(Ĭ��2h=7200s)
	public int Pud_Timing = 7200;

	//@5-���Ӹ���-�ϻ���Ʒ��ʼʱ���־
	public boolean Pud_Test_Start_Time_Flag;

	//@5-���Ӹ���-�ϻ���Ʒ��ʼʱ��
	public String Pud_Test_Start_Time;

	//@6-���Ӹ���-�ϻ���Ʒ����ʱ��
	public String Pud_Test_Scan_Time;

	//@7-���Ӹ���-�ϻ���Ʒ������־
	public boolean Pud_Test_FinshFlag;

	//@8-���Ӹ���-�ϻ���Ʒ-��������-��ѹ
	public float Pud_Test_ScanVoltage;

	//@9-���Ӹ���-�ϻ���Ʒ-��������-����
	public float Pud_Test_ScanCurrent;

	//@10-���Ӹ���-�ϻ���Ʒ-��������-�¶�1
	public float Pud_Test_ScanTemputer1;
	//@10-���Ӹ���-�ϻ���Ʒ-��������-�¶�2
	public float Pud_Test_ScanTemputer2;
	//@10-���Ӹ���-�ϻ���Ʒ-��������-�¶�3
	public float Pud_Test_ScanTemputer3;

	//@11-���Ӹ���-�ϻ���Ʒ-�ϻ�״̬
	public int Pud_Test_ConfigStatus = 0;   //0:�ڵ�δ����(config_no)  1:�ڵ�������(config_yes)

	//@11-���Ӹ���-�ϻ���Ʒ-�ϻ�״̬
	public int Pud_Test_Status = 0;    //0:�ڵ�δ����(config_no)  1:�ڵ�������(config_yes) 2:�ڵ��ϻ�ֹͣ(status_warning)  3:�ڵ��ϻ�����(status_ok)  4:�ڵ��ϻ�����(status_error)  5:�ڵ��ϻ�����(status_end)

	//@11-���Ӹ���-�ϻ���Ʒ-����ԭ��
	public int Pud_Test_LeaveReason = 0;   //0:û������  1:����  2:��Ϊ���

	//@-���Ӹ���-�ϻ���Ʒ-�ͺ�
	public String Pud_Test_PudModel;


	/**���Ӹ��ع���
	 *
	 */
	public AODI_DZFZ_Data(int Group, int Group_Location)
	{
		//@����CANΨһID
		CAN_ID[0] = 0x00;
		CAN_ID[1] = 0x00;
		CAN_ID[2] = (byte)(Group+1);
		CAN_ID[3] = (byte)(Group_Location+1);
		//@-����Group��
		Group_ID = Group+1;
		//@-����Groupλ�ú�
		Group_Location_ID = Group_Location+1;

		//@-����������ʼ��
		DZFZ_OtherData_Init();

	}

	/**���в�����ʼ��
	 *
	 */
	public void DZFZ_RuningData_Init()
	{
		//@4-���Ӹ���-�ϻ��ɼ����ݼ���
		Pud_TestData_Count = 0;

		//@7-���Ӹ���-�ϻ���Ʒ���б�־
		Pud_RunFlag = 0;

		//@3-���Ӹ���-�ϻ�����ʱ��
		Pud_RunTime = 0;

		//@7-���Ӹ���-�ϻ���ʱʱ��(Ĭ��2h=7200s)
		//Pud_Timing = 7200;

		//@4-���Ӹ���-�ϻ���Ʒ��ʼʱ���־
		Pud_Test_Start_Time_Flag = false;

		//@4-���Ӹ���-�ϻ���Ʒ��ʼʱ��
		Pud_Test_Start_Time = new String("00:00:00");

		//@5-���Ӹ���-�ϻ���Ʒ����ʱ��
		Pud_Test_Scan_Time = new String("00:00:00");

		//@6-���Ӹ���-�ϻ���Ʒ������־
		Pud_Test_FinshFlag = false;

		//@7-���Ӹ���-�ϻ���Ʒ-��������-��ѹ
		Pud_Test_ScanVoltage = 0;

		//@8-���Ӹ���-�ϻ���Ʒ-��������-����
		Pud_Test_ScanCurrent = 0;

		//@8-���Ӹ���-�ϻ���Ʒ-��������-�¶�
		Pud_Test_ScanTemputer1 = 0;
		Pud_Test_ScanTemputer2 = 0;
		Pud_Test_ScanTemputer3 = 0;
	}


	/**����������ʼ��
	 *
	 */
	public void DZFZ_OtherData_Init()
	{
		//@3-���Ӹ���-�ϻ���ƷΨһʶ����
		Pud_ID = new String("--------");

		//@3-���Ӹ���-�ϻ���Ʒ���б�־
		Pud_RunFlag = 0;

		//@4-���Ӹ���-�ϻ��ɼ����ݼ���
		Pud_TestData_Count = 0;

		//@3-���Ӹ���-�ϻ�����ʱ��
		Pud_RunTime = 0;

		//@7-���Ӹ���-�ϻ���ʱʱ��(Ĭ��2h=7200s)
		Pud_Timing = 7200;

		//@4-���Ӹ���-�ϻ���Ʒ��ʼʱ���־
		Pud_Test_Start_Time_Flag = false;

		//@4-���Ӹ���-�ϻ���Ʒ��ʼʱ��
		Pud_Test_Start_Time = new String("00:00:00");

		//@5-���Ӹ���-�ϻ���Ʒ����ʱ��
		Pud_Test_Scan_Time = new String("00:00:00");

		//@6-���Ӹ���-�ϻ���Ʒ������־
		Pud_Test_FinshFlag = false;

		//@7-���Ӹ���-�ϻ���Ʒ-��������-��ѹ
		Pud_Test_ScanVoltage = 0;

		//@8-���Ӹ���-�ϻ���Ʒ-��������-����
		Pud_Test_ScanCurrent = 0;

		//@8-���Ӹ���-�ϻ���Ʒ-��������-�¶�
		Pud_Test_ScanTemputer1 = 0;
		Pud_Test_ScanTemputer2 = 0;
		Pud_Test_ScanTemputer3 = 0;

		//@8-���Ӹ���-�ϻ���Ʒ-δ����
		Pud_Test_Status = 0;

		//@11-���Ӹ���-�ϻ���Ʒ-����ԭ��
		Pud_Test_LeaveReason = 0;
	}



	/**��ȡCAN��ַ
	 *
	 * @return
	 */
	public byte[] Get_CAN_ID()
	{
		return CAN_ID;
	}

	/**��ȡGroup��
	 *
	 * @return
	 */
	public int Get_Group_ID()
	{
		return Group_ID;
	}

	/**��ȡGroupλ�ú�
	 *
	 * @return
	 */
	public int Get_Group_Location_ID()
	{
		return Group_Location_ID;
	}


	/**���û��ȡ�ϻ���ƷΨһʶ����
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


	/**���û��ȡ�ϻ���Ʒ���б�־
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


	/**���û��ȡ�ϻ��ɼ����ݼ���
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


	/**���û��ȡ�ϻ�����ʱ��
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

	/**���û��ȡ�ϻ���ʱʱ��
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

	/**���û��ȡ�ϻ���ƷΨһʶ����
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

	/**���û��ȡ�ϻ���Ʒ��ʼʱ��
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


	/**���û��ȡ�ϻ���Ʒ����ʱ��
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


	/**���û��ȡ�ϻ���Ʒ������־
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


	/**���û��ȡ�ϻ���Ʒ-��������-��ѹ
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


	/**���û��ȡ�ϻ���Ʒ-��������-����
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


	/**���û��ȡ�ϻ���Ʒ-��������-�¶�1
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

	/**���û��ȡ�ϻ���Ʒ-��������-�¶�2
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

	/**���û��ȡ�ϻ���Ʒ-��������-�¶�3
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


	/**���û��ȡ�ϻ���Ʒ-����״̬
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

	/**���û��ȡ�ϻ���Ʒ-�ϻ�״̬
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


	/**���û��ȡ�ϻ���Ʒ-����ԭ��
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


	/**���û��ȡ�ϻ���Ʒ-��Ʒ�ͺ�
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
