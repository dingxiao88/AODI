package AODI_QC;

public class AODI_DZFZ_Group {

	//@1-ÿ��Group��10̨���Ӹ���
	public AODI_DZFZ_Data[] Group_DZFZ = new AODI_DZFZ_Data[10];
	//@2-Group��
	public String Group_Name;
	//@3-Group��ʱ�����б�־(�����뵥һ�豸����һ�豸ֻ�в������б�־)
	public int Group_Timer_RunFlag = 0;   //0:Group�ϻ�û�н���   1:Group�ϻ�������   2:Group�ϻ���������DZFZ������
	//@4-Group��ʱ������ʱ�䵥λs
	public int Group_Timer_RunTime = 0;
	//@5-Group����ʶ���DZFZ����
	public int Group_DZFZ_Count = 0;
	//@6-Group����ʶ���DZFZλ���Ƿ���DZFZ
	public int[] Group_DZFZ_Locatin =  new int[10];  //0:û��DZFZ  1:��DZFZ
	//@7-Group�ϻ���ʱʱ��(Ĭ��2h=7200s)
	public int Group_Timing = 7200;
	//@8Group�ϻ���Ʒ��ʼʱ��
	public String Group_Test_StartTime;
	//@9Group�ϻ���Ʒ��ֹʱ��
	public String Group_Test_EndTime;
	//@10-Group�ϻ���Ʒ�ͺ�
	public String Group_Test_PudModel;




	/**���Ӹ���Group����
	 *
	 */
	public AODI_DZFZ_Group(String Name)
	{
		int Group, Group_Location;

		//@-��ʼ��10��Group
		for(Group=0;Group<10;Group++)
		{
			//@-��ʼ��ÿ��Group�ڵ�10̨DZFZ
			for(Group_Location=0;Group_Location<10;Group_Location++)
			{
				Group_DZFZ[Group_Location] = new AODI_DZFZ_Data(Group,Group_Location);
			}

			//@-��ʼ��Group��
			Group_Name = Name;

			//@-��ʼ��Group����ʶ���DZFZ����
			Group_DZFZ_Locatin[Group] = 0;
		}

	}

	/**������10���ڵ����в������г�ʼ��
	 *
	 */
	public void Set_Group_Node_Init()
	{
		for(int i=0;i<10;i++)
		Group_DZFZ[i].DZFZ_RuningData_Init();
	}



	/**���û��ȡGroup�ϻ�����
	 *
	 * @param Name
	 */
	public void Set_Group_Name(String Name)
	{
		Group_Name = new String(Name);
	}
	public String Get_Group_Name()
	{
		return Group_Name;
	}


	/**���û��ȡGroup�ϻ��鶨ʱ������
	 *
	 * @param RunFlag
	 */
	public void Set_Group_TimerRunFlag(int RunFlag)
	{
		if(RunFlag==0)
		{
			Group_Timer_RunFlag = 0;
		}
		else if(RunFlag==1)
		{
			Group_Timer_RunFlag = 1;
		}
		else if(RunFlag==2)
		{
			Group_Timer_RunFlag = 2;
		}
	}
	public int Get_Group_TimerRunFlag()
	{
		return Group_Timer_RunFlag;
	}


	/**��ֵ���ȡGroup�ϻ��鶨ʱʱ��(��λs)
	 *
	 *
	 */
	public void Set_Group_RunTime(int time)
	{
		Group_Timer_RunTime = time;
	}
	public int Get_Group_RunTime()
	{
		return Group_Timer_RunTime;
	}
	public void Set_Group_RunTime_Add()
	{
		Group_Timer_RunTime = Group_Timer_RunTime + 1;
	}

	/**��ֵ���ȡGroup�ϻ���ʱʱ��(��λs)
	 *
	 *
	 */
	public void Set_Group_Timing(int time)
	{
		Group_Timing = time;
	}
	public int Get_Group_Timing()
	{
		return Group_Timing;
	}

	/**��ֵ���ȡGroup����ʶ���DZFZ����
	 *
	 *
	 */
	public void Set_Group_DZFZ_Count(int Count)
	{
		Group_DZFZ_Count = Count;
	}
	public int Get_Group_DZFZ_Count()
	{
		return Group_DZFZ_Count;
	}


	/**��ֵ���ȡGroup����ʶ���DZFZλ���Ƿ���DZFZ
	 *
	 *
	 */
	public void Set_Group_DZFZ_Locatin(int Location, int flag)
	{
		Group_DZFZ_Locatin[Location] = flag;
	}
	public int Get_Group_DZFZ_Locatin(int Location)
	{
		return Group_DZFZ_Locatin[Location];
	}

	/**��ֵ���ȡGroup�ϻ���ʼʱ��
	 *
	 *
	 */
	public void Set_Group_Test_StartTime(String Time)
	{
		Group_Test_StartTime = Time;
	}
	public String Get_Group_Test_StartTime(int Location)
	{
		return Group_Test_StartTime;
	}

	/**��ֵ���ȡGroup�ϻ���ֹʱ��
	 *
	 *
	 */
	public void Set_Group_Test_EndTime(String Time)
	{
		Group_Test_EndTime = Time;
	}
	public String Get_Group_Test_EndTime()
	{
		return Group_Test_EndTime;
	}

	/**��ֵ���ȡGroup�ϻ���Ʒ�ͺ�
	 *
	 *
	 */
	public void Set_Group_Test_PudModel(int Group_Num,String Model)
	{
		//@-����Group��Ʒ
		Group_Test_PudModel = new String(Model);

		//@-���ø�Group��10����Ʒ�ͺ�
		for(int i=0;i<10;i++)
		{
			ScreensFramework.DZFZ_Group[Group_Num].Group_DZFZ[i].Set_Pud_Test_PudModel(Model);
		}

	}
	public String Get_Group_Test_PudModel()
	{
		return Group_Test_PudModel;
	}


}
