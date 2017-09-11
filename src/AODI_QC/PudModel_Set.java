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

public class PudModel_Set {

        //@1-产品型号名称
        private String  PudModel_Name;

        //@2-电压限制值
        private float  PudModel_VoltageLimit;
        //@3-电压限制值-范围
        private float  PudModel_VoltageLimit_Range;

        //@4-电流限制值
        private float  PudModel_CurrentLimit;
        //@5-电流限制值-范围
        private float  PudModel_CurrentLimit_Range;

        //@6-温度1限制值
        private float  PudModel_Temperature1Limit;
        //@7-温度1限制值-范围
        private float  PudModel_Temperature1Limit_Range;

        //@8-温度2限制值
        private float  PudModel_Temperature2Limit;
        //@9-温度2限制值-范围
        private float  PudModel_Temperature2Limit_Range;

        //@10-温度3限制值
        private float  PudModel_Temperature3Limit;
        //@11-温度3限制值-范围
        private float  PudModel_Temperature3Limit_Range;



        /**
         *
         */
        public  PudModel_Set()
        {

        }

        /**
         *
         * @param Data1-型号名称
         * @param Data2-电压限制值
         * @param Data3-电压限制值范围
         * @param Data4-电流限制值
         * @param Data5-电流限制值范围
         * @param Data6-温度1限制值
         * @param Data7-温度1限制值范围
         * @param Data8-温度2限制值
         * @param Data9-温度2限制值范围
         * @param Data10-温度3限制值
         * @param Data11-温度3限制值范围
         */
    	public  PudModel_Set(String Data1,float Data2, float Data3, float Data4, float Data5, float Data6,float Data7,float Data8,float Data9,float Data10,float Data11)
    	{
    		//@1-产品型号名称
    		PudModel_Name = new String(Data1);

    		//@2-电压限制值
    		PudModel_VoltageLimit = Data2;
    		//@3-电压限制值范围
    		PudModel_VoltageLimit_Range = Data3;

    		//@4-电流限制值
    		PudModel_CurrentLimit = Data4;
    		//@5-电流限制值范围
    		PudModel_CurrentLimit_Range = Data5;

    		//@6-温度1限制值
    		PudModel_Temperature1Limit = Data6;
    		//@7-温度1限制值范围
    		PudModel_Temperature1Limit_Range = Data7;

    		//@8-温度2限制值
    		PudModel_Temperature2Limit = Data8;
    		//@9-温度2限制值范围
    		PudModel_Temperature2Limit_Range = Data9;

    		//@10-温度3限制值
    		PudModel_Temperature3Limit = Data10;
    		//@11-温度3限制值范围
    		PudModel_Temperature3Limit_Range = Data11;
    	}

    	/**设置/获取产品型号名
    	 *
    	 * @param name
    	 */
    	public void Set_PudModel_Name(String name)
    	{
    		PudModel_Name = new String(name);
    	}
    	public String Get_PudModel_Name()
    	{
    		return(PudModel_Name);
    	}

    	/**设置/获取产品型号电压限制值
	   	 *
	   	 * @param value
	   	 */
	   	public void Set_PudModel_VoltageLimit(float value)
	   	{
	   		PudModel_VoltageLimit = value;
	   	}
	   	public float Get_PudModel_VoltageLimit()
	   	{
	   		return(PudModel_VoltageLimit);
	   	}

    	/**设置/获取产品型号电压限制值范围
	   	 *
	   	 * @param value
	   	 */
	   	public void Set_PudModel_VoltageLimit_Range(float value)
	   	{
	   		PudModel_VoltageLimit_Range = value;
	   	}
	   	public float Get_PudModel_VoltageLimit_Range()
	   	{
	   		return(PudModel_VoltageLimit_Range);
	   	}


    	/**设置/获取产品型号电流限制值
	   	 *
	   	 * @param value
	   	 */
	   	public void Set_PudModel_CurrentLimit(float value)
	   	{
	   		PudModel_CurrentLimit = value;
	   	}
	   	public float Get_PudModel_CurrentLimit()
	   	{
	   		return(PudModel_CurrentLimit);
	   	}

	   	/**设置/获取产品型号电流限制值范围
	   	 *
	   	 * @param value
	   	 */
	   	public void Set_PudModel_CurrentLimit_Range(float value)
	   	{
	   		PudModel_CurrentLimit_Range = value;
	   	}
	   	public float Get_PudModel_CurrentLimit_Range()
	   	{
	   		return(PudModel_CurrentLimit_Range);
	   	}


    	/**设置/获取产品型号温度1限制值
	   	 *
	   	 * @param value
	   	 */
	   	public void Set_PudModel_Temperature1Limit(float value)
	   	{
	   		PudModel_Temperature1Limit = value;
	   	}
	   	public float Get_PudModel_Temperature1Limit()
	   	{
	   		return(PudModel_Temperature1Limit);
	   	}

	   	/**设置/获取产品型号温度1限制值范围
	   	 *
	   	 * @param value
	   	 */
	   	public void Set_PudModel_Temperature1Limit_Range(float value)
	   	{
	   		PudModel_Temperature1Limit_Range = value;
	   	}
	   	public float Get_PudModel_Temperature1Limit_Range()
	   	{
	   		return(PudModel_Temperature1Limit_Range);
	   	}

    	/**设置/获取产品型号温度2限制值
	   	 *
	   	 * @param value
	   	 */
	   	public void Set_PudModel_Temperature2Limit(float value)
	   	{
	   		PudModel_Temperature2Limit = value;
	   	}
	   	public float Get_PudModel_Temperature2Limit()
	   	{
	   		return(PudModel_Temperature2Limit);
	   	}

	   	/**设置/获取产品型号温度2限制值范围
	   	 *
	   	 * @param value
	   	 */
	   	public void Set_PudModel_Temperature2Limit_Range(float value)
	   	{
	   		PudModel_Temperature2Limit_Range = value;
	   	}
	   	public float Get_PudModel_Temperature2Limit_Range()
	   	{
	   		return(PudModel_Temperature2Limit_Range);
	   	}

    	/**设置/获取产品型号温度3限制值
	   	 *
	   	 * @param value
	   	 */
	   	public void Set_PudModel_Temperature3Limit(float value)
	   	{
	   		PudModel_Temperature3Limit = value;
	   	}
	   	public float Get_PudModel_Temperature3Limit()
	   	{
	   		return(PudModel_Temperature3Limit);
	   	}

	   	/**设置/获取产品型号温度3限制值范围
	   	 *
	   	 * @param value
	   	 */
	   	public void Set_PudModel_Temperature3Limit_Range(float value)
	   	{
	   		PudModel_Temperature3Limit_Range = value;
	   	}
	   	public float Get_PudModel_Temperature3Limit_Range()
	   	{
	   		return(PudModel_Temperature3Limit_Range);
	   	}


}
