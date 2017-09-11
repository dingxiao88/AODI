package AODI_QC;


import javafx.beans.property.DoubleProperty;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class AODI_Data {

        //@1-序号
        private String  Data_Num_s;

        //@2-产品ID
        private String  Data_PudID_s;

        //@3-产品老化起始时间
        private String  Data_TestStartTime_s;
        //@4-老化产品采样-电压
        private String  Data_Voltage_s;
        //@5-老化产品采样-电流
        private String  Data_Current_s;

        //@6-老化产品采样-温度1
        private String  Data_Temperature1_s;
        //@7-老化产品采样-温度2
        private String  Data_Temperature2_s;
        //@8-老化产品采样-温度3
        private String  Data_Temperature3_s;

        //@9-老化产品采样-采样时间
        private String  Data_TestScanTime_s;

        //@10-老化产品-故障码
        private String  Data_ErrorID_s;

        //@11-老化产品-状态
        private String  Data_TestStatus_s;


        /**
         *
         * @param Data1-序号
         * @param Data2-产品ID
         * @param Data3-老化起始时间
         * @param Data4-老化采样-电压
         * @param Data5-老化采样-电流
         * @param Data6-老化采样-温度1
         * @param Data7-老化采样-温度2
         * @param Data8-老化采样-温度3
         * @param Data9-老化采样-采样时间
         * @param Data10-老化产品-故障码
         * @param Data11-老化产品-状态
         */
    	public  AODI_Data(String Data1,String Data2, String Data3, String Data4, String Data5, String Data6,String Data7,String Data8,String Data9,String Data10,String Data11)
    	{
    		//@1-序号
    		this.Data_Num_s=new String(Data1);
    		//@2-产品ID
    		this.Data_PudID_s=new String(Data2);

    		//@3-老化起始时间
    		this.Data_TestStartTime_s=new String(Data3);

    		//@4-老化产品采样-电压
    		this.Data_Voltage_s=new String(Data4);
    		//@5-老化产品采样-电流
    		this.Data_Current_s=new String(Data5);
    		//@6-老化产品采样-温度1
    		this.Data_Temperature1_s=new String(Data6);
    		//@7-老化产品采样-温度2
    		this.Data_Temperature2_s=new String(Data7);
    		//@8-老化产品采样-温度3
    		this.Data_Temperature3_s=new String(Data8);

    		//@9-老化产品采样-采样时间
    		this.Data_TestScanTime_s=new String(Data9);
    		//@10-老化产品-故障ID
    		this.Data_ErrorID_s=new String(Data10);
    		//@11-老化产品状态
    		this.Data_TestStatus_s=new String(Data11);
    	}

    	//@1-序号
    	public String get_Data_Num_Str()
    	{
    		return Data_Num_s;
    	}

    	//@2-产品ID
    	public String get_Data_PudID_Str()
    	{
    		return Data_PudID_s;
    	}

    	//@3-老化起始时间
    	public String get_Data_TestStartTime_Str()
    	{
    		return Data_TestStartTime_s;
    	}

    	//@4-老化产品采样-电压
    	public String get_Data_Voltage_Str()
    	{
    		return Data_Voltage_s;
    	}
    	//@5-老化产品采样-电流
    	public String get_Data_Current_Str()
    	{
    		return Data_Current_s;
    	}
    	//@6-老化产品采样-温度1
    	public String get_Data_Temperature1_Str()
    	{
    		return Data_Temperature1_s;
    	}
    	//@7-老化产品采样-温度2
    	public String get_Data_Temperature2_Str()
    	{
    		return Data_Temperature2_s;
    	}
    	//@8-老化产品采样-温度3
    	public String get_Data_Temperature3_Str()
    	{
    		return Data_Temperature3_s;
    	}

    	//@9-老化产品采样-采样时间
    	public String get_Data_TestScanTime_Str()
    	{
    		return Data_TestScanTime_s;
    	}
    	//@10-老化产品-故障码
    	public String get_Data_ErrorID_Str()
    	{
    		return Data_ErrorID_s;
    	}
    	//@11-老化产品-状态
    	public String get_Data_TestStatus_Str()
    	{
    		return Data_TestStatus_s;
    	}

}
