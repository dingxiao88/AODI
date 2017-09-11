package AODI_QC;

public class AODI_DZFZ_Group {

	//@1-每个Group有10台电子负载
	public AODI_DZFZ_Data[] Group_DZFZ = new AODI_DZFZ_Data[10];
	//@2-Group名
	public String Group_Name;
	//@3-Group定时器运行标志(区别与单一设备，单一设备只有参与运行标志)
	public int Group_Timer_RunFlag = 0;   //0:Group老化没有进行   1:Group老化进行中   2:Group老化进行中有DZFZ不正常
	//@4-Group定时器运行时间单位s
	public int Group_Timer_RunTime = 0;
	//@5-Group内已识别的DZFZ数量
	public int Group_DZFZ_Count = 0;
	//@6-Group内已识别的DZFZ位置是否有DZFZ
	public int[] Group_DZFZ_Locatin =  new int[10];  //0:没有DZFZ  1:有DZFZ
	//@7-Group老化定时时间(默认2h=7200s)
	public int Group_Timing = 7200;
	//@8Group老化产品起始时间
	public String Group_Test_StartTime;
	//@9Group老化产品终止时间
	public String Group_Test_EndTime;
	//@10-Group老化产品型号
	public String Group_Test_PudModel;




	/**电子负载Group构造
	 *
	 */
	public AODI_DZFZ_Group(String Name)
	{
		int Group, Group_Location;

		//@-初始化10组Group
		for(Group=0;Group<10;Group++)
		{
			//@-初始化每组Group内的10台DZFZ
			for(Group_Location=0;Group_Location<10;Group_Location++)
			{
				Group_DZFZ[Group_Location] = new AODI_DZFZ_Data(Group,Group_Location);
			}

			//@-初始化Group名
			Group_Name = Name;

			//@-初始化Group内已识别的DZFZ数量
			Group_DZFZ_Locatin[Group] = 0;
		}

	}

	/**对组内10个节点运行参数进行初始化
	 *
	 */
	public void Set_Group_Node_Init()
	{
		for(int i=0;i<10;i++)
		Group_DZFZ[i].DZFZ_RuningData_Init();
	}



	/**设置或获取Group老化组名
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


	/**设置或获取Group老化组定时器开关
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


	/**赋值或获取Group老化组定时时间(单位s)
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

	/**赋值或获取Group老化定时时间(单位s)
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

	/**赋值或获取Group内已识别的DZFZ数量
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


	/**赋值或获取Group内已识别的DZFZ位置是否有DZFZ
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

	/**赋值或获取Group老化起始时间
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

	/**赋值或获取Group老化终止时间
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

	/**赋值或获取Group老化产品型号
	 *
	 *
	 */
	public void Set_Group_Test_PudModel(int Group_Num,String Model)
	{
		//@-设置Group产品
		Group_Test_PudModel = new String(Model);

		//@-设置该Group下10个产品型号
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
