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

        //@1-���
        private String  Data_Num_s;

        //@2-��ƷID
        private String  Data_PudID_s;

        //@3-��Ʒ�ϻ���ʼʱ��
        private String  Data_TestStartTime_s;
        //@4-�ϻ���Ʒ����-��ѹ
        private String  Data_Voltage_s;
        //@5-�ϻ���Ʒ����-����
        private String  Data_Current_s;

        //@6-�ϻ���Ʒ����-�¶�1
        private String  Data_Temperature1_s;
        //@7-�ϻ���Ʒ����-�¶�2
        private String  Data_Temperature2_s;
        //@8-�ϻ���Ʒ����-�¶�3
        private String  Data_Temperature3_s;

        //@9-�ϻ���Ʒ����-����ʱ��
        private String  Data_TestScanTime_s;

        //@10-�ϻ���Ʒ-������
        private String  Data_ErrorID_s;

        //@11-�ϻ���Ʒ-״̬
        private String  Data_TestStatus_s;


        /**
         *
         * @param Data1-���
         * @param Data2-��ƷID
         * @param Data3-�ϻ���ʼʱ��
         * @param Data4-�ϻ�����-��ѹ
         * @param Data5-�ϻ�����-����
         * @param Data6-�ϻ�����-�¶�1
         * @param Data7-�ϻ�����-�¶�2
         * @param Data8-�ϻ�����-�¶�3
         * @param Data9-�ϻ�����-����ʱ��
         * @param Data10-�ϻ���Ʒ-������
         * @param Data11-�ϻ���Ʒ-״̬
         */
    	public  AODI_Data(String Data1,String Data2, String Data3, String Data4, String Data5, String Data6,String Data7,String Data8,String Data9,String Data10,String Data11)
    	{
    		//@1-���
    		this.Data_Num_s=new String(Data1);
    		//@2-��ƷID
    		this.Data_PudID_s=new String(Data2);

    		//@3-�ϻ���ʼʱ��
    		this.Data_TestStartTime_s=new String(Data3);

    		//@4-�ϻ���Ʒ����-��ѹ
    		this.Data_Voltage_s=new String(Data4);
    		//@5-�ϻ���Ʒ����-����
    		this.Data_Current_s=new String(Data5);
    		//@6-�ϻ���Ʒ����-�¶�1
    		this.Data_Temperature1_s=new String(Data6);
    		//@7-�ϻ���Ʒ����-�¶�2
    		this.Data_Temperature2_s=new String(Data7);
    		//@8-�ϻ���Ʒ����-�¶�3
    		this.Data_Temperature3_s=new String(Data8);

    		//@9-�ϻ���Ʒ����-����ʱ��
    		this.Data_TestScanTime_s=new String(Data9);
    		//@10-�ϻ���Ʒ-����ID
    		this.Data_ErrorID_s=new String(Data10);
    		//@11-�ϻ���Ʒ״̬
    		this.Data_TestStatus_s=new String(Data11);
    	}

    	//@1-���
    	public String get_Data_Num_Str()
    	{
    		return Data_Num_s;
    	}

    	//@2-��ƷID
    	public String get_Data_PudID_Str()
    	{
    		return Data_PudID_s;
    	}

    	//@3-�ϻ���ʼʱ��
    	public String get_Data_TestStartTime_Str()
    	{
    		return Data_TestStartTime_s;
    	}

    	//@4-�ϻ���Ʒ����-��ѹ
    	public String get_Data_Voltage_Str()
    	{
    		return Data_Voltage_s;
    	}
    	//@5-�ϻ���Ʒ����-����
    	public String get_Data_Current_Str()
    	{
    		return Data_Current_s;
    	}
    	//@6-�ϻ���Ʒ����-�¶�1
    	public String get_Data_Temperature1_Str()
    	{
    		return Data_Temperature1_s;
    	}
    	//@7-�ϻ���Ʒ����-�¶�2
    	public String get_Data_Temperature2_Str()
    	{
    		return Data_Temperature2_s;
    	}
    	//@8-�ϻ���Ʒ����-�¶�3
    	public String get_Data_Temperature3_Str()
    	{
    		return Data_Temperature3_s;
    	}

    	//@9-�ϻ���Ʒ����-����ʱ��
    	public String get_Data_TestScanTime_Str()
    	{
    		return Data_TestScanTime_s;
    	}
    	//@10-�ϻ���Ʒ-������
    	public String get_Data_ErrorID_Str()
    	{
    		return Data_ErrorID_s;
    	}
    	//@11-�ϻ���Ʒ-״̬
    	public String get_Data_TestStatus_Str()
    	{
    		return Data_TestStatus_s;
    	}

}
