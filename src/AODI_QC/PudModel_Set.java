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

        //@1-��Ʒ�ͺ�����
        private String  PudModel_Name;

        //@2-��ѹ����ֵ
        private float  PudModel_VoltageLimit;
        //@3-��ѹ����ֵ-��Χ
        private float  PudModel_VoltageLimit_Range;

        //@4-��������ֵ
        private float  PudModel_CurrentLimit;
        //@5-��������ֵ-��Χ
        private float  PudModel_CurrentLimit_Range;

        //@6-�¶�1����ֵ
        private float  PudModel_Temperature1Limit;
        //@7-�¶�1����ֵ-��Χ
        private float  PudModel_Temperature1Limit_Range;

        //@8-�¶�2����ֵ
        private float  PudModel_Temperature2Limit;
        //@9-�¶�2����ֵ-��Χ
        private float  PudModel_Temperature2Limit_Range;

        //@10-�¶�3����ֵ
        private float  PudModel_Temperature3Limit;
        //@11-�¶�3����ֵ-��Χ
        private float  PudModel_Temperature3Limit_Range;



        /**
         *
         */
        public  PudModel_Set()
        {

        }

        /**
         *
         * @param Data1-�ͺ�����
         * @param Data2-��ѹ����ֵ
         * @param Data3-��ѹ����ֵ��Χ
         * @param Data4-��������ֵ
         * @param Data5-��������ֵ��Χ
         * @param Data6-�¶�1����ֵ
         * @param Data7-�¶�1����ֵ��Χ
         * @param Data8-�¶�2����ֵ
         * @param Data9-�¶�2����ֵ��Χ
         * @param Data10-�¶�3����ֵ
         * @param Data11-�¶�3����ֵ��Χ
         */
    	public  PudModel_Set(String Data1,float Data2, float Data3, float Data4, float Data5, float Data6,float Data7,float Data8,float Data9,float Data10,float Data11)
    	{
    		//@1-��Ʒ�ͺ�����
    		PudModel_Name = new String(Data1);

    		//@2-��ѹ����ֵ
    		PudModel_VoltageLimit = Data2;
    		//@3-��ѹ����ֵ��Χ
    		PudModel_VoltageLimit_Range = Data3;

    		//@4-��������ֵ
    		PudModel_CurrentLimit = Data4;
    		//@5-��������ֵ��Χ
    		PudModel_CurrentLimit_Range = Data5;

    		//@6-�¶�1����ֵ
    		PudModel_Temperature1Limit = Data6;
    		//@7-�¶�1����ֵ��Χ
    		PudModel_Temperature1Limit_Range = Data7;

    		//@8-�¶�2����ֵ
    		PudModel_Temperature2Limit = Data8;
    		//@9-�¶�2����ֵ��Χ
    		PudModel_Temperature2Limit_Range = Data9;

    		//@10-�¶�3����ֵ
    		PudModel_Temperature3Limit = Data10;
    		//@11-�¶�3����ֵ��Χ
    		PudModel_Temperature3Limit_Range = Data11;
    	}

    	/**����/��ȡ��Ʒ�ͺ���
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

    	/**����/��ȡ��Ʒ�ͺŵ�ѹ����ֵ
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

    	/**����/��ȡ��Ʒ�ͺŵ�ѹ����ֵ��Χ
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


    	/**����/��ȡ��Ʒ�ͺŵ�������ֵ
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

	   	/**����/��ȡ��Ʒ�ͺŵ�������ֵ��Χ
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


    	/**����/��ȡ��Ʒ�ͺ��¶�1����ֵ
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

	   	/**����/��ȡ��Ʒ�ͺ��¶�1����ֵ��Χ
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

    	/**����/��ȡ��Ʒ�ͺ��¶�2����ֵ
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

	   	/**����/��ȡ��Ʒ�ͺ��¶�2����ֵ��Χ
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

    	/**����/��ȡ��Ʒ�ͺ��¶�3����ֵ
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

	   	/**����/��ȡ��Ʒ�ͺ��¶�3����ֵ��Χ
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
