/*
 * Copyright (c) 2008, 2013 Oracle and/or its affiliates.
 * All rights reserved. Use is subject to license terms.
 *
 * This file is available and licensed under the following license:
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  - Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *  - Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the distribution.
 *  - Neither the name of Oracle Corporation nor the names of its
 *    contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */



import gnu.io.NoSuchPortException;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import javax.xml.stream.EventFilter;

import com.sun.javafx.perf.PerformanceTracker;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.CacheHint;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/**
 * Login Controller.
 */
public class LoginController1 implements Initializable, ControlledScreen {

   //@1-ϵͳ�˳���ť
   @FXML
   private ImageView System_Exit;

   //@2-ϵͳʱ����ʾ
   @FXML
   private Label     System_Time;

   //@3-�ѳ��ʱ��
   @FXML
   private Label     Charging_RunTime;
   //@4-�ѳ��ʱ��Rect
   @FXML
   private Rectangle Charging_RunTime_Rect;

   //@5-�����ѹ
   @FXML
   private Label       Charging_OutputVoltage;
   //@5-�����ѹ-MAX
   @FXML
   private Label       Charging_OutputVoltage_MAX;
   //@5-�����ѹ-MIN
   @FXML
   private Label       Charging_OutputVoltage_MIN;
   //@5-�����ѹ-UpLimit
   @FXML
   private Label       Charging_OutputVoltage_UpLimit;
   //@5-�����ѹ-DownLimit
   @FXML
   private Label       Charging_OutputVoltage_DownLimit;
   //@6-�����ѹRect
   @FXML
   private Rectangle   Charging_OutputVoltage_Rect;
   //@7-�����ѹ����ѡ��
   @FXML
   private CheckBox Charging_OutputVoltageCurve_Sel;

   //@8-�������
   @FXML
   private Label       Charging_OutputCurrent;
   //@5-�������-MAX
   @FXML
   private Label       Charging_OutputCurrent_MAX;
   //@5-�������-MIN
   @FXML
   private Label       Charging_OutputCurrent_MIN;
   //@5-�����ѹ-UpLimit
   @FXML
   private Label       Charging_OutputCurrent_UpLimit;
   //@5-�����ѹ-DownLimit
   @FXML
   private Label       Charging_OutputCurrent_DownLimit;
   //@9-�������Rect
   @FXML
   private Rectangle   Charging_OutputCurrent_Rect;
   //@10-�����ѹ����ѡ��
   @FXML
   private CheckBox Charging_OutputCurrentCurve_Sel;

   //@11-ĸ�ߵ�ѹ
   @FXML
   private Label       Charging_MainVoltage;
   //@5-ĸ�ߵ�ѹ-MAX
   @FXML
   private Label       Charging_MainVoltage_MAX;
   //@5-ĸ�ߵ�ѹ-MIN
   @FXML
   private Label       Charging_MainVoltage_MIN;
   //@5-�����ѹ-UpLimit
   @FXML
   private Label       Charging_MainVoltage_UpLimit;
   //@5-�����ѹ-DownLimit
   @FXML
   private Label       Charging_MainVoltage_DownLimit;
   //@12-ĸ�ߵ�ѹRect
   @FXML
   private Rectangle   Charging_MainVoltage_Rect;
   //@13-ĸ�ߵ�ѹ����ѡ��
   @FXML
   private CheckBox Charging_MainVoltageCurve_Sel;

   //@14-����¶�
   @FXML
   private Label       Charging_Temperature;
   //@5-����¶�-MAX
   @FXML
   private Label       Charging_Temperature_MAX;
   //@5-����¶�-MIN
   @FXML
   private Label       Charging_Temperature_MIN;
   //@5-�����ѹ-UpLimit
   @FXML
   private Label       Charging_Temperature_UpLimit;
   //@5-�����ѹ-DownLimit
   @FXML
   private Label       Charging_Temperature_DownLimit;
   //@15-����¶�Rect
   @FXML
   private Rectangle   Charging_Temperature_Rect;
   //@16-����¶�����ѡ��
   @FXML
   private CheckBox Charging_TemperatureCurve_Sel;

   //@17-����
   @FXML
   private LineChart  Chart;
   //@18-����X��
   @FXML
   private NumberAxis Chart_Axis_X;
   //@19-����Y��
   @FXML
   private NumberAxis Chart_Axis_Y;
   //@20-������ͣ��ť
   @FXML
   private Button     ChartRun_Button;

   //@21-�����ָͣʾ
   @FXML
   private ImageView  Charging_StatusLamp;
   //@22-�����ͣ��ť
   @FXML
   private Button     ChargingControl_Button;


   //@23-ϵͳ��Ϣ���
   @FXML
   private TextArea   SystemInfo_TextArea;
   //@24-ϵͳ��Ϣ��հ�ť
   @FXML
   private Button     SystemInfo_Clean_Button;

   //@25-ϵͳ������-�����ѹ��ѹ
   @FXML
   private Label      SystemError_OutputVoltage_Over;
   //@26-ϵͳ������-�����ѹǷѹ
   @FXML
   private Label      SystemError_OutputVoltage_Under;
   //@27-ϵͳ������-�����������
   @FXML
   private Label      SystemError_OutputCurrent_Over;
   //@28-ϵͳ������-�����������
   @FXML
   private Label      SystemError_OutputCurrent_Under;
   //@29-ϵͳ������-ĸ�ߵ�ѹ��ѹ
   @FXML
   private Label      SystemError_MainVoltage_Over;
   //@30-ϵͳ������-ĸ�ߵ�ѹǷѹ
   @FXML
   private Label      SystemError_MainVoltage_Under;
   //@31-ϵͳ������-����¶ȹ���
   @FXML
   private Label      SystemError_Temperature_Over;
   //@32-ϵͳ������-����¶ȹ���
   @FXML
   private Label      SystemError_Temperature_Under;
   //@33-ϵͳ������-���ư�ͨѶ����
   @FXML
   private Label      SystemError_PCBConnection_Error;

   //@34-ͨѶ����ָʾ
   @FXML
   private ImageView  Connection_StatusLamp;
   //@35-ͨѶ���Ӱ�ť
   @FXML
   private Button     ConnectionControl_Button;


   //@35-��������-�����ļ�-�����ļ���
   @FXML
   private TextField   OptionSet_StrategyLoadFile;
   //@35-��������-�����ļ�-���ذ�ť
   @FXML
   private Button     OptionSet_StrategyLoad_Button;
   //@35-��������-�����ļ�-�½���ť
   @FXML
   private Button     OptionSet_StrategyNew_Button;
   //@1-��������-��������״̬-״ָ̬ʾ
   @FXML
   private ImageView  OptionSet_StrategySet_Status;


   //@35-��������-��ѹ��������-�����ѹ��ѹ
   @FXML
   private TextField   OptionSet_OutputVoltage_Over;
   //@35-��������-��ѹ��������-�����ѹ��ѹ���ܱ�־
   @FXML
   private RadioButton OptionSet_OutputVoltage_Over_Do;
   //@35-��������-��ѹ��������-�����ѹǷѹ
   @FXML
   private TextField   OptionSet_OutputVoltage_Under;
   //@35-��������-��ѹ��������-�����ѹǷѹ���ܱ�־
   @FXML
   private RadioButton OptionSet_OutputVoltage_Under_Do;
   //@35-��������-��ѹ��������-ĸ�ߵ�ѹ��ѹ
   @FXML
   private TextField   OptionSet_MainVoltage_Over;
   //@35-��������-��ѹ��������-ĸ�ߵ�ѹ��ѹ���ܱ�־
   @FXML
   private RadioButton OptionSet_MainVoltage_Over_Do;
   //@35-��������-��ѹ��������-ĸ�ߵ�ѹǷѹ
   @FXML
   private TextField   OptionSet_MainVoltage_Under;
   //@35-��������-��ѹ��������-ĸ�ߵ�ѹǷѹ���ܱ�־
   @FXML
   private RadioButton OptionSet_MainVoltage_Under_Do;

   //@35-��������-������������-�����������
   @FXML
   private TextField   OptionSet_OutputCurrent_Over;
   //@35-��������-������������-��������������ܱ�־
   @FXML
   private RadioButton OptionSet_OutputCurrent_Over_Do;
   //@35-��������-������������-�����������
   @FXML
   private TextField   OptionSet_OutputCurrent_Under;
   //@35-��������-������������-����������͹��ܱ�־
   @FXML
   private RadioButton OptionSet_OutputCurrent_Under_Do;

   //@35-��������-�¶ȱ�������-����¶ȹ���
   @FXML
   private TextField   OptionSet_Temperature_Over;
   //@35-��������-�¶ȱ�������-����¶ȹ��߹��ܱ�־
   @FXML
   private RadioButton OptionSet_Temperature_Over_Do;
   //@35-��������-�¶ȱ�������-����¶ȹ���
   @FXML
   private TextField   OptionSet_Temperature_Under;
   //@35-��������-�¶ȱ�������-����¶ȹ��͹��ܱ�־
   @FXML
   private RadioButton OptionSet_Temperature_Under_Do;


   //@14-���Ƶ�ѹ
   @FXML
   private Label       Charging_ControlVoltage;
   //@7-���Ƶ�ѹ����ѡ��
   @FXML
   private CheckBox    Charging_ControlVoltageCurve_Sel;


   //@12-��Դ��ѹѡ��-110v
   @FXML
   private Rectangle   Charging_Power110_Rect;
   //@14-���Ƶ�ѹ
   @FXML
   private Label       Charging_Power110_Label;
   //@12-��Դ��ѹѡ��-220v
   @FXML
   private Rectangle   Charging_Power220_Rect;
   //@14-���Ƶ�ѹ
   @FXML
   private Label       Charging_Power220_Label;
   //@12-��Դ��ѹѡ��-260v
   @FXML
   private Rectangle   Charging_Power260_Rect;
   //@14-���Ƶ�ѹ
   @FXML
   private Label       Charging_Power260_Label;
   //@35-��Դ��ѹѡ��-ȷ�ϰ�ť
   @FXML
   private Button     Charging_PowerSet_Button;
   //@1-��Դ��ѹѡ��-״ָ̬ʾ
   @FXML
   private ImageView  Charging_PowerStatus_Lamp;


   //@35-��������-��������-�˿�
   @FXML
   private ChoiceBox  OptionSet_SeriseSet_Port;
   //@35-��������-��������-������
   @FXML
   private ChoiceBox  OptionSet_SeriseSet_Bound;


   //@Debug-DA����΢��
   //@35-DA����΢��+
   @FXML
   private Button     DA_Up;
   //@35-DA����΢��-
   @FXML
   private Button     DA_Down;

//----------------------------------------------------------------------
	//@1-������Ӧ�ó���ӿ�
	private ScreensController myController;

	//@-ͼƬ��Դ
    private Image Status_Unknow_Pic;
    private Image Status_OK_Pic;
    private Image Status_Error_Pic;
    private Image System_Close_Pic;
    private Image Status_SmallUnknow_Pic;
    private Image Status_SmallOK_Pic;
    private Image Status_SmallError_Pic;

    //@-���������ʾ����
    private static final int MAX_DATA_POINTS = 400;

    //@-��������״̬
    public static boolean   CurveRun_Status=false;

    //@-����������ʾ��־
    public static boolean   Curve_OutputVoltage_Dis = true;
    public static boolean   Curve_OutputCurrent_Dis = true;
    public static boolean   Curve_MainVoltage_Dis = true;
    public static boolean   Curve_Temperature_Dis = true;
    public static boolean   Curve_ControlVoltage_Dis = false;

    //@-���������Ƴ���־
    public static boolean   Curve_OutputVoltage_Remove = false;
    public static boolean   Curve_OutputCurrent_Remove = false;
    public static boolean   Curve_MainVoltage_Remove =  false;
    public static boolean   Curve_Temperature_Remove =  false;
    public static boolean   Curve_ControlVoltage_Remove =  true;

    //@-������ʾ����
    public static int       Curve_Display_Count=4;

    //@-��׼���ݸ��±�־
    private boolean Curve_UpData=true;

    //@-��׼����
    private int     Curve_UpData_DataBase=1;   //1:�����ѹ  2:�������   3:ĸ�ߵ�ѹ  4:����¶�  5:���Ƶ�ѹ

    //@-��׼�������߼���
    private int xSeriesData = 0;
    private int xSeriesData_copy = 0;

    //@-��������
    private LineChart.Series<Number, Number>  ChartData_OutputVoltage;
    private LineChart.Series<Number, Number>  ChartData_OutputCurrent;
    private LineChart.Series<Number, Number>  ChartData_MainVoltage;
    private LineChart.Series<Number, Number>  ChartData_Temperature;
    private LineChart.Series<Number, Number>  ChartData_ControlVoltage;

    //@-�������ݹ������
    public static List<Float> Data_OutputVoltage = Collections.synchronizedList(new ArrayList<Float>());
    public static List<Float> Data_OutputCurrent = Collections.synchronizedList(new ArrayList<Float>());
    public static List<Float> Data_MainVoltage =  Collections.synchronizedList(new ArrayList<Float>());
    public static List<Float> Data_Temperature =  Collections.synchronizedList(new ArrayList<Float>());
    public static List<Float> Data_ControlVoltage =  Collections.synchronizedList(new ArrayList<Float>());

    //@-����ʱ����
    private Timeline Curve_Timeline;
    //@-���߶�����ʱ��
    private SequentialTransition Curve_Animation;

    //@-�������ͬ��
	public static SimpleStringProperty DisplayUpdataProperty_Main = new SimpleStringProperty();
    //@-����������ͬ��
	public static SimpleStringProperty StrategyProperty_Main = new SimpleStringProperty();
    //@-�����Խ׶�ͬ��
	public static SimpleStringProperty StrategyStage_Main = new SimpleStringProperty();

	//@-������±�־
	public static boolean DisplayUpdata_Flag=false;

    //@34-change������
	private ChangeListener changelisten1;
	private ChangeListener changelisten2;
	private ChangeListener changelisten3;

	//@6-��ʾ���ݸ�ʽ
	private java.text.NumberFormat  formater_value  =  java.text.DecimalFormat.getInstance();  //��ʾС����ʽ��
	private java.text.NumberFormat  formater_value1  =  java.text.DecimalFormat.getInstance();  //��ʾС����ʽ��

	//����
	public static Serise Serise_Main;    //���ڶ���
	//����״̬
	public static int Serise_Status=0;   //0:���ڹر�  1:���ڴ�  2:�����ڴ��ڶ˿�
	//���ڹ�������
	public static boolean Serise_Error_Flag=false;

    //@35-��������-��ѹ����-�����ѹ����
	public static Float OptionSet_OutputVoltage_UpLimit=(float)0;
    //@35-��������-��ѹ����-�����ѹ����
	public static Float OptionSet_OutputVoltage_DownLimit=(float)0;
    //@35-��������-��ѹ����-�����ѹ�����Ʊ�����־   0:����    1:��    2:�ٽ�
	public static int OutputVoltage_Up_LimitFlag=0;
    //@35-��������-��ѹ����-�����ѹ�����Ʊ�����־   0:����    1:��    2:�ٽ�
	public static int OutputVoltage_Down_LimitFlag=0;
    //@35-��������-��ѹ����-�����ѹ�������ñ�־
	public static boolean OptionSet_OutputVoltage_UpLimit_Flag=false;
    //@35-��������-��ѹ����-�����ѹ�������ñ�־
	public static boolean OptionSet_OutputVoltage_DownLimit_Flag=false;
    //@35-�����ѹ���ֵ
	public static Float OutputVoltage_MAX=(float)0;
    //@35-�����ѹ��Сֵ
	public static Float OutputVoltage_MIN=(float)0;
    //@35-�����ѹ��ǰֵ
	public static Float OutputVoltage_Current=(float)0;

    //@35-��������-��������-�����������
	public static Float OptionSet_OutputCurrent_UpLimit=(float)0;
    //@35-��������-��������-�����������
	public static Float OptionSet_OutputCurrent_DownLimit=(float)0;
    //@35-��������-��ѹ����-������������Ʊ�����־   0:����    1:��    2:�ٽ�
	public static int OutputCurrent_Up_LimitFlag=0;
    //@35-��������-��ѹ����-������������Ʊ�����־   0:����    1:��    2:�ٽ�
	public static int OutputCurrent_Down_LimitFlag=0;
    //@35-��������-��������-��������������ñ�־
	public static boolean OptionSet_OutputCurrent_UpLimit_Flag=false;
    //@35-��������-��������-��������������ñ�־
	public static boolean OptionSet_OutputCurrent_DownLimit_Flag=false;
    //@35-����������ֵ
	public static Float OutputCurrent_MAX=(float)0;
    //@35-���������Сֵ
	public static Float OutputCurrent_MIN=(float)0;
    //@35-���������ǰֵ
	public static Float OutputCurrent_Current=(float)0;
    //@35-���������ǰֵӰ��
	public static Float OutputCurrent_Current_Copy=(float)0;

    //@35-��������-��ѹ����-ĸ�ߵ�ѹ����
	public static Float OptionSet_MainVoltage_UpLimit=(float)0;
    //@35-��������-��ѹ����-ĸ�ߵ�ѹ����
	public static Float OptionSet_MainVoltage_DownLimit=(float)0;
    //@35-��������-��ѹ����-ĸ�ߵ��������Ʊ�����־   0:����    1:��    2:�ٽ�
	public static int MainVoltage_Up_LimitFlag=0;
    //@35-��������-��ѹ����-ĸ�ߵ��������Ʊ�����־   0:����    1:��    2:�ٽ�
	public static int MainVoltage_Down_LimitFlag=0;
    //@35-��������-��ѹ����-ĸ�ߵ�ѹ�������ñ�־
	public static boolean OptionSet_MainVoltage_UpLimit_Flag=false;
    //@35-��������-��ѹ����-ĸ�ߵ�ѹ�������ñ�־
	public static boolean OptionSet_MainVoltage_DownLimit_Flag=false;
    //@35-ĸ�ߵ�ѹ���ֵ
	public static Float MainVoltage_MAX=(float)0;
    //@35-ĸ�ߵ�ѹ��Сֵ
	public static Float MainVoltage_MIN=(float)0;
    //@35-ĸ�ߵ�ѹ��ǰֵ
	public static Float MainVoltage_Current=(float)0;

    //@35-��������-�¶ȱ���-����¶�����
	public static Float OptionSet_Temperature_UpLimit=(float)0;
    //@35-��������-�¶ȱ���-����¶�����
	public static Float OptionSet_Temperature_DownLimit=(float)0;
    //@35-��������-��ѹ����-����¶������Ʊ�����־   0:����    1:��    2:�ٽ�
	public static int Temperature_Up_LimitFlag=0;
    //@35-��������-��ѹ����-����¶������Ʊ�����־   0:����    1:��    2:�ٽ�
	public static int Temperature_Down_LimitFlag=0;
    //@35-��������-�¶ȱ���-����¶��������ñ�־
	public static boolean OptionSet_Temperature_UpLimit_Flag=false;
    //@35-��������-�¶ȱ���-����¶��������ñ�־
	public static boolean OptionSet_Temperature_DownLimit_Flag=false;
    //@35-����¶����ֵ
	public static Float Temperature_MAX=(float)0;
    //@35-����¶���Сֵ
	public static Float Temperature_MIN=(float)0;
    //@35-����¶ȵ�ǰֵ
	public static Float Temperature_Current=(float)0;


    //@35-���Ƶ�ѹ��ǰֵ
	public static Float ControlVoltage_Current=(float)0;
	public static Float ControlVoltage_Current_Copy=(float)0;


	//@-���������ñ�־
	public static boolean OptionSet_StrategySet_Flag=false;
	//@-��糬ʱ��־
	public static boolean ControlVoltage_Timeout_Flag=false;
	//@-���Ƶ�ѹ�ﵽ���ޱ�־
	public static boolean ControlVoltage_StageLimt_Flag=false;
    //@-���Բ���-�������н׶γɹ���־
    public static boolean Strategy_Succeed_Flag=false;
    //@-���Բ���-���Խ׶�
    public static int     Strategy_Stage=0;   //1:Stage1 2:Stage2 3:Stage3 4:Stage4

    //@-�����ж�-�����ѹ��ʼֵ
    public static float Stage_Voltage_Start=0;
    //@-�����ж�-�����ѹ��ֵֹ
    public static float Stage_Voltage_End=0;
    //@-�����ж�-�����ѹ��ֵֹ��ʱʱ��
    public static float Stage_Voltage_EndDelay=0;
    //@-�����ж�-���������ʼֵ
    public static float Stage_Current_Start=0;
    //@-�����ж�-���������ֵֹ
    public static float Stage_Current_End=0;
    //@-�����ж�-������ɹ�����
    public static int Stage_Succeed_Count=0;
    //@-�����ж�-������ʧ�ܼ���
    public static int Stage_Fail_Count=0;
    //@-�����ж�-������ɹ��ٷֱ�
    public static float Stage_Succeed=0;
    //@-�����ж�-��������־
    public static boolean Stage_InFlag=false;
    //@-�����ж�-�����뿪��־
    public static boolean Stage_OutFlag=false;

    //@2-����׼�������ѹ1ѡ���־
  	public static boolean VoltageArea1_Sel=true;
  	//@3-����׼�������ѹ2ѡ���־
  	public static boolean VoltageArea2_Sel=false;
  	//@4-����׼�������ѹ3ѡ���־
  	public static boolean VoltageArea3_Sel=false;
  	//@5-����׼�������ѹ4ѡ���־
  	public static boolean VoltageArea4_Sel=false;

  	//@8-����׼��-��ʼ��ѹ
  	public static float  VoltageStart;
  	//@9-����׼��-��ֹ��ѹ
  	public static float  VoltageEnd;
  	//@9-����׼��-��ֹ��ѹ��ʱʱ��
  	public static float  VoltageEnd_Delay;
  	//@10-����׼��-������ѹ
  	public static float  VoltageStep;

  	//@10-����׼��-�����ѹ1
  	public static float  VoltageArea1;
  	//@10-����׼��-�����ѹ1-Low
  	public static float  VoltageArea1_Low;
  	//@10-����׼��-�����ѹ1-Hight
  	public static float  VoltageArea1_High;
  	//@10-����׼��-�����ѹ2
  	public static float  VoltageArea2;
  	//@10-����׼��-�����ѹ2-Low
  	public static float  VoltageArea2_Low;
  	//@10-����׼��-�����ѹ2-Hight
  	public static float  VoltageArea2_High;
  	//@10-����׼��-�����ѹ3
  	public static float  VoltageArea3;
  	//@10-����׼��-�����ѹ3-Low
  	public static float  VoltageArea3_Low;
  	//@10-����׼��-�����ѹ3-Hight
  	public static float  VoltageArea3_High;
  	//@10-����׼��-�����ѹ4
  	public static float  VoltageArea4;
  	//@10-����׼��-�����ѹ4-Low
  	public static float  VoltageArea4_Low;
  	//@10-����׼��-�����ѹ4-Hight
  	public static float  VoltageArea4_High;

  	//@10-����׼��-Ԥ�����
  	public static float  Current1;
  	//@10-����׼��-Ԥ�����-Low
  	public static float  Current1_Low;
  	//@10-����׼��-Ԥ�����-Hight
  	public static float  Current1_High;
  	//@10-����׼��-�����1
  	public static float  Current2;
  	//@10-����׼��-�����1-Low
  	public static float  Current2_Low;
  	//@10-����׼��-�����1-Hight
  	public static float  Current2_High;
  	//@10-����׼��-�����2
  	public static float  Current3;
  	//@10-����׼��-�����2-Low
  	public static float  Current3_Low;
  	//@10-����׼��-�����2-Hight
  	public static float  Current3_High;
  	//@10-����׼��-�������
  	public static float  Current4;
  	//@10-����׼��-�������-Low
  	public static float  Current4_Low;
  	//@10-����׼��-�������-Hight
  	public static float  Current4_High;



	//@-���ʱ��-ʱ
	public static int   ChargeVoltage_Time=0;
	//@-�������ʱ��-ʱ
	public static int   ChargeVoltage_RunTimeHour=0;
	//@-�������ʱ��-��
	public static int   ChargeVoltage_RunTimeMin=0;
	//@-�������ʱ��-��
	public static int   ChargeVoltage_RunTimeSecond=0;
	//@-����ѹ���б�־
	public static boolean ChargeVoltage_StartFlag=false;


	//@-PCB����־
	public static boolean PCB_Status=false;
	//@-PCB����־����
	public static boolean PCB_Status_Copy=false;

	//@-����ѹѡ��
	public static int Charging_Power_Sel=0;    //0:û��ѡ��  1:110v 2:220v 3:260v
	//@-����Դ��ѹ״̬��־
	public static boolean Charging_PowerStarus=false;
	//@-����Դ��ѹCAN���ͱ�־
	public static boolean Charging_PowerSend_Flag=false;

	//@-�����ļ�·��
	public static String StrategyLoadFile_Path=null;
	//@-�����ļ���
	public static String StrategyLoadFile_Name=null;
	//@-�����ļ����ر�־
	public static boolean StrategyChooseFile_Flag=false;
	//@-�����ļ����ط�ʽ  1:�½�  2:����
	public static int StrategyLoadFile_Mode=1;


	//@-Debug-DA΢��
	public static float Send_DA_Range =0;



	/**��¼�����ʼ��
	 *
	 */
    @SuppressWarnings("unchecked")
	@Override
    public void initialize(URL location, ResourceBundle resources)
    {
    	//@1-��ʽ������
		formater_value.setMaximumFractionDigits(2);
		formater_value.setMinimumFractionDigits(2);
		formater_value1.setMaximumIntegerDigits(2);
		formater_value1.setMinimumIntegerDigits(2);

    	//@2-ͼƬ��Դ��ʼ��
    	Status_Unknow_Pic= new Image(LoginController1.class.getResourceAsStream("statusbar_message_light_orange.png"));
    	Status_OK_Pic= new Image(LoginController1.class.getResourceAsStream("statusbar_message_light_green.png"));
    	Status_Error_Pic= new Image(LoginController1.class.getResourceAsStream("statusbar_message_light_red.png"));
    	System_Close_Pic= new Image(LoginController1.class.getResourceAsStream("power.png"));
    	Status_SmallUnknow_Pic= new Image(LoginController1.class.getResourceAsStream("status_yellow.png"));
    	Status_SmallOK_Pic= new Image(LoginController1.class.getResourceAsStream("status_green.png"));
    	Status_SmallError_Pic= new Image(LoginController1.class.getResourceAsStream("status_red.png"));

    	//@3-����ͼƬ��Դ
    	System_Exit.setImage(System_Close_Pic);
    	Charging_StatusLamp.setImage(Status_Unknow_Pic);
    	Connection_StatusLamp.setImage(Status_Unknow_Pic);
    	OptionSet_StrategySet_Status.setImage(Status_Unknow_Pic);
		Charging_StatusLamp.setImage(Status_Unknow_Pic);
		Charging_PowerStatus_Lamp.setImage(Status_SmallUnknow_Pic);

    	//@4-�������������ݹ�����
    	TextField_CharFilter(OptionSet_OutputVoltage_Over);
    	TextField_CharFilter(OptionSet_OutputVoltage_Under);
    	TextField_CharFilter(OptionSet_MainVoltage_Over);
    	TextField_CharFilter(OptionSet_MainVoltage_Under);
    	TextField_CharFilter(OptionSet_OutputCurrent_Over);
    	TextField_CharFilter(OptionSet_OutputCurrent_Under);
    	TextField_CharFilter(OptionSet_Temperature_Over);
    	TextField_CharFilter(OptionSet_Temperature_Under);

    	//@5-���߻�������
    	Chart.setCreateSymbols(false);
    	Chart.setAnimated(false);
    	Chart.setLegendVisible(true);
    	Chart.setCache(true);
    	//Chart.setCacheShape(true);
    	Chart.cacheProperty();
    	Chart.cacheHintProperty();
    	Chart.setCacheHint(CacheHint.SPEED);

    	Chart_Axis_Y.setAutoRanging(true);
    	Chart_Axis_X.setTickLabelFill(Color.CHOCOLATE);
    	Chart_Axis_Y.setTickLabelFill(Color.CHOCOLATE);

    	Chart_Axis_X.setCache(true);
    	//Chart_Axis_X.setCacheShape(true);
    	Chart_Axis_X.cacheProperty();
    	Chart_Axis_X.cacheHintProperty();
    	Chart_Axis_X.setCacheHint(CacheHint.SPEED);

    	Chart_Axis_Y.setCache(true);
    	//Chart_Axis_Y.setCacheShape(true);
    	Chart_Axis_Y.cacheProperty();
    	Chart_Axis_Y.cacheHintProperty();
    	Chart_Axis_Y.setCacheHint(CacheHint.SPEED);

		//@6-������������
    	ChartData_OutputVoltage = new LineChart.Series<Number, Number>();
    	ChartData_OutputVoltage.setName("�����ѹ");
    	ChartData_OutputCurrent = new LineChart.Series<Number, Number>();
    	ChartData_OutputCurrent.setName("�������");
    	ChartData_MainVoltage =  new LineChart.Series<Number, Number>();
    	ChartData_MainVoltage.setName("ĸ�ߵ�ѹ");
    	ChartData_Temperature =  new LineChart.Series<Number, Number>();
    	ChartData_Temperature.setName("����¶�");
    	ChartData_ControlVoltage =  new LineChart.Series<Number, Number>();
    	ChartData_ControlVoltage.setName("���Ƶ�ѹ");

    	//@7-��ʼ����������-��������-�˿�
    	OptionSet_SeriseSet_Port.getItems().clear();
    	OptionSet_SeriseSet_Port.getItems().addAll("COM1", "COM2", "COM3", "COM4", "COM5");
    	OptionSet_SeriseSet_Port.getSelectionModel().selectFirst();
    	//@8-��ʼ����������-��������-������
    	OptionSet_SeriseSet_Bound.getItems().clear();
    	OptionSet_SeriseSet_Bound.getItems().addAll("9600", "19200", "115200");
    	OptionSet_SeriseSet_Bound.getSelectionModel().select(1);

		//@9-��������������
		Chart.getData().addAll(ChartData_OutputVoltage,ChartData_OutputCurrent,ChartData_MainVoltage,ChartData_Temperature);

		//@10-�������ͬ��
		DisplayUpdataProperty_Main.addListener(changelisten1=new ChangeListener(){
			@Override
			public void changed(ObservableValue arg0, Object oldval, Object newval) {
				// TODO Auto-generated method stub
				Thread t1 = new Thread(new Runnable() {
					@Override
					public void run() {
							Platform.runLater(new Runnable() {
								@Override
								public void run() {

									//@-ϵͳʱ����ʾ
									System_Time.setText(""+AODI_DisplayTimer.Time_Str);

									//@-�����ѹ��ֵ-��ǰֵ
									Charging_OutputVoltage.setText(""+formater_value.format(OutputVoltage_Current)+"V");
									//@-�����ѹ��ֵ-���ֵ
									Charging_OutputVoltage_MAX.setText("MAX:"+formater_value.format(OutputVoltage_MAX)+"V");
									//@-�����ѹ��ֵ-��Сֵ
									Charging_OutputVoltage_MIN.setText("MIN:"+formater_value.format(OutputVoltage_MIN)+"V");

									//@-���������ֵ
									Charging_OutputCurrent.setText(""+formater_value.format(OutputCurrent_Current)+"A");
									//@-���������ֵ-���ֵ
									Charging_OutputCurrent_MAX.setText("MAX:"+formater_value.format(OutputCurrent_MAX)+"A");
									//@-���������ֵ-��Сֵ
									Charging_OutputCurrent_MIN.setText("MIN:"+formater_value.format(OutputCurrent_MIN)+"A");

									//@-ĸ�ߵ�ѹ��ֵ
									Charging_MainVoltage.setText(""+formater_value.format(MainVoltage_Current)+"V");
									//@-ĸ�ߵ�ѹ��ֵ-���ֵ
									Charging_MainVoltage_MAX.setText("MAX:"+formater_value.format(MainVoltage_MAX)+"V");
									//@-ĸ�ߵ�ѹ��ֵ-��Сֵ
									Charging_MainVoltage_MIN.setText("MIN:"+formater_value.format(MainVoltage_MIN)+"V");

									//@-����¶���ֵ
									Charging_Temperature.setText(""+formater_value.format(Temperature_Current)+"��");
									//@-����¶���ֵ-���ֵ
									Charging_Temperature_MAX.setText("MAX:"+formater_value.format(Temperature_MAX)+"��");
									//@-����¶���ֵ-��Сֵ
									Charging_Temperature_MIN.setText("MIN:"+formater_value.format(Temperature_MIN)+"��");

									//@���Ƶ�ѹ
									Charging_ControlVoltage.setText(""+formater_value.format(ControlVoltage_Current)+"V");

									//@-����״̬��
									if(Serise_Status==0)
									Connection_StatusLamp.setImage(Status_Unknow_Pic);
									else if(Serise_Status==1)
									Connection_StatusLamp.setImage(Status_OK_Pic);
									else if(Serise_Status==2)
									Connection_StatusLamp.setImage(Status_Error_Pic);

									//@��鼫��
									Check_Limit();

									//@-PCB״̬
									if(PCB_Status_Copy!=PCB_Status)
									{
										PCB_Status_Copy=PCB_Status;
										if(PCB_Status_Copy==true)
										{
											SystemError_PCBConnection_Error.setId("T1");
											ScreensFramework.Show_Noti("Success", "PCB���ӳɹ�!");
										}
										else if(PCB_Status_Copy==false)
										{
											SystemError_PCBConnection_Error.setId("T44");
											ScreensFramework.Show_Noti("Error", "PCB����!");

											//@-PCB״̬������ֹͣ���
											ChargeVoltage_StartFlag=false;
											Charging_StatusLamp.setImage(Status_Unknow_Pic);
										}
									}


									//@-�������ʱ��
									if(ChargeVoltage_StartFlag==true)
									{
										Charging_RunTime.setText(""+formater_value1.format(ChargeVoltage_RunTimeHour)+
																":"+formater_value1.format(ChargeVoltage_RunTimeMin)+
																":"+formater_value1.format(ChargeVoltage_RunTimeSecond));
									}

									//@-��Ϣ���
									if((AODI_DisplayTimer.Data_InfoMessage.size())>0)
									{
						        		synchronized (AODI_DisplayTimer.Data_InfoType)
						        		{
						        			int tpye = AODI_DisplayTimer.Data_InfoType.remove(0);

						        			//@-OK
						        			if(tpye==1)
						        			SystemInfo_TextArea.setId("INFO_OK");
						        			//@-OK
						        			else if(tpye==2)
						        			SystemInfo_TextArea.setId("INFO_Error");
						        			//@-OK
						        			else if(tpye==1)
						        			SystemInfo_TextArea.setId("INFO_Warning");
						        		}

						        		synchronized (AODI_DisplayTimer.Data_InfoMessage)
						        		{
						        			SystemInfo_TextArea.appendText(AODI_DisplayTimer.Data_InfoMessage.remove(0));
						        		}
									}

								}
							});
					}
				});
				t1.setName("MainDisplayUpdate");
				t1.setDaemon(true);
				t1.start();
			}
    	});

		//@-������ͬ��
		StrategyProperty_Main.addListener(changelisten2=new ChangeListener(){
			@Override
			public void changed(ObservableValue arg0, Object oldval, Object newval) {
				// TODO Auto-generated method stub
				if(newval.toString().equals(new String("Set")))          //�������óɹ�
				{
					//@-��������״̬
					OptionSet_StrategySet_Status.setImage(Status_OK_Pic);
					//@-�رղ����ļ�
					OptionSet_StrategyLoadFile.setDisable(true);
	    			//@-�ɹ�ѡ������ļ�
	    			OptionSet_StrategyLoadFile.setText(StrategyControllerNew.Strategy_FileName);
					//@-���ذ�ť��Ϊȡ��
					OptionSet_StrategyLoad_Button.setText("ȡ��");
					//@-�����½���ť
					OptionSet_StrategyNew_Button.setVisible(false);
					//@-�������óɹ���־
					OptionSet_StrategySet_Flag=true;
					//@-���ز�������
					Load_StrategyData();
				}
				else if(newval.toString().equals(new String("Cancel")))  //û�н��в�������
				{
					//@-��������״̬
					OptionSet_StrategySet_Status.setImage(Status_Unknow_Pic);
					//@-�رղ����ļ�
					OptionSet_StrategyLoadFile.setDisable(false);
	    			//@-�ɹ�ѡ������ļ�
	    			OptionSet_StrategyLoadFile.setText("----");
					//@-���ذ�ť��Ϊȡ��
					OptionSet_StrategyLoad_Button.setText("����");
					//@-�����½���ť
					OptionSet_StrategyNew_Button.setVisible(true);
					//@-�������óɹ���־
					OptionSet_StrategySet_Flag=false;
				}
				StrategyProperty_Main.setValue("None");
			}
    	});


		//@-�����Խ׶�ͬ��
		StrategyStage_Main.addListener(changelisten3=new ChangeListener(){
			@Override
			public void changed(ObservableValue arg0, Object oldval, Object newval) {
				// TODO Auto-generated method stub
				if(newval.toString().equals(new String("Stage1")))       //���Խ׶�1
				{
					Strategy_Stage=1;
					Stage_Voltage_Start=VoltageArea1-VoltageArea1_Low;
					Stage_Voltage_End=VoltageArea1+VoltageArea1_High;
					Stage_Current_Start=Current1-Current1_Low;
					Stage_Current_End=Current1+Current1_High;

				    Stage_Succeed_Count=0;
				    Stage_Fail_Count=0;
				    Stage_Succeed=0;

				    Stage_InFlag=false;
				    Stage_OutFlag=false;
				}
				else if(newval.toString().equals(new String("Stage2")))  //���Խ׶�2
				{
					Strategy_Stage=2;
					Stage_Voltage_Start=VoltageArea2-VoltageArea2_Low;
					Stage_Voltage_End=VoltageArea2+VoltageArea2_High;
					Stage_Current_Start=Current2-Current2_Low;
					Stage_Current_End=Current2+Current2_High;

				    Stage_Succeed_Count=0;
				    Stage_Fail_Count=0;
				    Stage_Succeed=0;

				    Stage_InFlag=false;
				    Stage_OutFlag=false;
				}
				else if(newval.toString().equals(new String("Stage3")))  //���Խ׶�3
				{
					Strategy_Stage=3;
					Stage_Voltage_Start=VoltageArea3-VoltageArea3_Low;
					Stage_Voltage_End=VoltageArea3+VoltageArea3_High;
					Stage_Current_Start=Current3-Current3_Low;
					Stage_Current_End=Current3+Current3_High;

				    Stage_Succeed_Count=0;
				    Stage_Fail_Count=0;
				    Stage_Succeed=0;

				    Stage_InFlag=false;
				    Stage_OutFlag=false;
				}
				else if(newval.toString().equals(new String("Stage4")))  //���Խ׶�4
				{
					Strategy_Stage=4;
					Stage_Voltage_Start=VoltageArea4-VoltageArea4_Low;
					Stage_Voltage_End=VoltageArea4+VoltageArea4_High;
					Stage_Current_Start=Current4-Current4_Low;
					Stage_Current_End=Current4+Current4_High;

				    Stage_Succeed_Count=0;
				    Stage_Fail_Count=0;
				    Stage_Succeed=0;

				    Stage_InFlag=false;
				    Stage_OutFlag=false;
				}
				else if(newval.toString().equals(new String("Strategy_OK")))  //���OK
				{
					Platform.runLater(new Runnable() {
					@Override
					public void run() {

						    //@-��ʱ��־����
							ControlVoltage_Timeout_Flag=false;
							ControlVoltage_StageLimt_Flag=false;

							//@-���������־����
						    Stage_InFlag=false;
						    Stage_OutFlag=false;

					    	//@-�Ͽ����Ƴ��
		    				ChargeVoltage_StartFlag=false;
		    				Charging_StatusLamp.setImage(Status_Unknow_Pic);

							//@-���ز���
							StrategyProperty_Main.setValue("Set");

					    	//@-��ʾ���ɹ�
					    	ScreensFramework.Show_Noti("Success", "���ɹ�!");
						}
					});
				}
				else if(newval.toString().equals(new String("Strategy_Error")))  //���Error
				{
					Platform.runLater(new Runnable() {
					@Override
					public void run() {

						    //@-��ʱ��־����
							ControlVoltage_Timeout_Flag=false;
							ControlVoltage_StageLimt_Flag=false;

							//@-���������־����
						    Stage_InFlag=false;
						    Stage_OutFlag=false;

					    	//@-�Ͽ����Ƴ��
		    				ChargeVoltage_StartFlag=false;
		    				Charging_StatusLamp.setImage(Status_Unknow_Pic);

							//@-���ز���
							StrategyProperty_Main.setValue("Set");

					    	//@-��ʾ������
					    	ScreensFramework.Show_Noti("Error", "������!");
						}
					});
				}
				StrategyStage_Main.setValue("None");
			}
    	});

    	//@11-��������ˢ��
    	prepareTimeline();

    }

    /**���ز�������
     *
     */
    private void Load_StrategyData()
    {
    	//@-���������ѹ1�׶α�־
    	VoltageArea1_Sel = ScreensFramework.Main_Config.Def_VoltageArea1_Sel;
    	//@-���������ѹ2�׶α�־
    	VoltageArea2_Sel = ScreensFramework.Main_Config.Def_VoltageArea2_Sel;
    	//@-���������ѹ3�׶α�־
    	VoltageArea3_Sel = ScreensFramework.Main_Config.Def_VoltageArea3_Sel;
    	//@-���������ѹ4�׶α�־
    	VoltageArea4_Sel = ScreensFramework.Main_Config.Def_VoltageArea4_Sel;

    	//@-������ʼ��ѹ
    	VoltageStart = ScreensFramework.Main_Config.Def_VoltageStart;
    	//@-������ֹ��ѹ
    	VoltageEnd = ScreensFramework.Main_Config.Def_VoltageEnd;
    	//@-������ֹ��ѹ��ʱʱ��
    	VoltageEnd_Delay = ScreensFramework.Main_Config.Def_VoltageEnd_Delay;
    	//@-���ز�����ѹ
    	VoltageStep = ScreensFramework.Main_Config.Def_VoltageStep;

    	//@-���ز��Ե������ѹ1
    	VoltageArea1 = ScreensFramework.Main_Config.Def_VoltageArea1;
    	//@-���ز��Ե������ѹ1-Low
    	VoltageArea1_Low = ScreensFramework.Main_Config.Def_VoltageArea1_Low;
    	//@-���ز��Ե������ѹ1-High
    	VoltageArea1_High = ScreensFramework.Main_Config.Def_VoltageArea1_High;
    	//@-���ز��Ե������ѹ2
    	VoltageArea2 = ScreensFramework.Main_Config.Def_VoltageArea2;
    	//@-���ز��Ե������ѹ2-Low
    	VoltageArea2_Low = ScreensFramework.Main_Config.Def_VoltageArea2_Low;
    	//@-���ز��Ե������ѹ2-High
    	VoltageArea2_High = ScreensFramework.Main_Config.Def_VoltageArea2_High;
    	//@-���ز��Ե������ѹ3
    	VoltageArea3 = ScreensFramework.Main_Config.Def_VoltageArea3;
    	//@-���ز��Ե������ѹ3-Low
    	VoltageArea3_Low = ScreensFramework.Main_Config.Def_VoltageArea3_Low;
    	//@-���ز��Ե������ѹ3-High
    	VoltageArea3_High = ScreensFramework.Main_Config.Def_VoltageArea3_High;
    	//@-���ز��Ե������ѹ4
    	VoltageArea4 = ScreensFramework.Main_Config.Def_VoltageArea4;
    	//@-���ز��Ե������ѹ4-Low
    	VoltageArea4_Low = ScreensFramework.Main_Config.Def_VoltageArea4_Low;
    	//@-���ز��Ե������ѹ4-High
    	VoltageArea4_High = ScreensFramework.Main_Config.Def_VoltageArea4_High;

    	//@-���ز��Ե�Ԥ�����
    	Current1 = ScreensFramework.Main_Config.Def_Current1;
    	//@-���ز��Ե������ѹ1-Low
    	Current1_Low = ScreensFramework.Main_Config.Def_Current1_Low;
    	//@-���ز��Ե������ѹ1-High
    	Current1_High = ScreensFramework.Main_Config.Def_Current1_High;
    	//@-���ز��Ե������ѹ2
    	Current2 = ScreensFramework.Main_Config.Def_Current2;
    	//@-���ز��Ե������ѹ2-Low
    	Current2_Low = ScreensFramework.Main_Config.Def_Current2_Low;
    	//@-���ز��Ե������ѹ2-High
    	Current2_High = ScreensFramework.Main_Config.Def_Current2_High;
    	//@-���ز��Ե������ѹ3
    	Current3 = ScreensFramework.Main_Config.Def_Current3;
    	//@-���ز��Ե������ѹ3-Low
    	Current3_Low = ScreensFramework.Main_Config.Def_Current3_Low;
    	//@-���ز��Ե������ѹ3-High
    	Current3_High = ScreensFramework.Main_Config.Def_Current3_High;
    	//@-���ز��Ե������ѹ4
    	Current4 = ScreensFramework.Main_Config.Def_Current4;
    	//@-���ز��Ե������ѹ4-Low
    	Current4_Low = ScreensFramework.Main_Config.Def_Current4_Low;
    	//@-���ز��Ե������ѹ4-High
    	Current4_High = ScreensFramework.Main_Config.Def_Current4_High;

    	//@-����ѹ��ʼֵ
		ControlVoltage_Current_Copy=VoltageStart;

    	//@-����������
    	if(VoltageArea1_Sel==true)
    	{
    		StrategyStage_Main.setValue("Stage1");
    		AODI_DisplayTimer.InfoPut(1,"�л����׶�1");
    	}
    	else if(VoltageArea2_Sel==true)
    	{
    		StrategyStage_Main.setValue("Stage2");
    		AODI_DisplayTimer.InfoPut(1,"�л����׶�2");
    	}
    	else if(VoltageArea3_Sel==true)
    	{
    		StrategyStage_Main.setValue("Stage3");
    		AODI_DisplayTimer.InfoPut(1,"�л����׶�3");
    	}
    	else if(VoltageArea4_Sel==true)
    	{
    		StrategyStage_Main.setValue("Stage4");
    		AODI_DisplayTimer.InfoPut(1,"�л����׶�4");
    	}
    }


    /**��鼫��
     *
     */
    private void Check_Limit()
    {
    	//@1-���Ʊ���-�����ѹ����
		switch(OutputVoltage_Up_LimitFlag)
		{
			//@�����ѹ��������
			case 0:
				   //@-���������ƣ�
				   if(OptionSet_OutputVoltage_UpLimit_Flag==true)
				   {
					   SystemError_OutputVoltage_Over.setId("T1");
				   }
				   else if(OptionSet_OutputVoltage_UpLimit_Flag==false)
				   {
					   SystemError_OutputVoltage_Over.setId("T33");
				   }
				   break;
			//@�����ѹ���޳�
			case 1:
				   //@-���������ƣ�
				   if(OptionSet_OutputVoltage_UpLimit_Flag==true)
				   {
					   if(AODI_DisplayTimer.second_1s_flash==true)
					   SystemError_OutputVoltage_Over.setId("T44");
					   else if(AODI_DisplayTimer.second_1s_flash==false)
					   SystemError_OutputVoltage_Over.setId("T55");
				   }
				   else if(OptionSet_OutputVoltage_UpLimit_Flag==false)
				   {
					   SystemError_OutputVoltage_Over.setId("T33");
				   }
				   break;
			//@�����ѹ��������
			case 2:
				   //@-���������ƣ�
				   if(OptionSet_OutputVoltage_UpLimit_Flag==true)
				   {
					   SystemError_OutputVoltage_Over.setId("T22");
				   }
				   else if(OptionSet_OutputVoltage_UpLimit_Flag==false)
				   {
					   SystemError_OutputVoltage_Over.setId("T33");
				   }
				   break;
		    default: break;
		}
		//@2-���Ʊ���-�����ѹ����
		switch(OutputVoltage_Down_LimitFlag)
		{
			//@�����ѹ��������
			case 0:
				   //@-���������ƣ�
				   if(OptionSet_OutputVoltage_DownLimit_Flag==true)
				   {
					   SystemError_OutputVoltage_Under.setId("T1");
				   }
				   else if(OptionSet_OutputVoltage_DownLimit_Flag==false)
				   {
					   SystemError_OutputVoltage_Under.setId("T33");
				   }
				   break;
			//@�����ѹ���޳�
			case 1:
				   //@-���������ƣ�
				   if(OptionSet_OutputVoltage_DownLimit_Flag==true)
				   {
					   if(AODI_DisplayTimer.second_1s_flash==true)
					   SystemError_OutputVoltage_Under.setId("T44");
					   else if(AODI_DisplayTimer.second_1s_flash==false)
					   SystemError_OutputVoltage_Under.setId("T55");
				   }
				   else if(OptionSet_OutputVoltage_DownLimit_Flag==false)
				   {
					   SystemError_OutputVoltage_Under.setId("T33");
				   }
				   break;
			//@�����ѹ��������
			case 2:
				   //@-���������ƣ�
				   if(OptionSet_OutputVoltage_DownLimit_Flag==true)
				   {
					   SystemError_OutputVoltage_Under.setId("T22");
				   }
				   else if(OptionSet_OutputVoltage_DownLimit_Flag==false)
				   {
					   SystemError_OutputVoltage_Under.setId("T33");
				   }
				   break;
		    default: break;
		}

		//@3-���Ʊ���-�����������
		switch(OutputCurrent_Up_LimitFlag)
		{
			//@���������������
			case 0:
				   //@-���������ƣ�
				   if(OptionSet_OutputCurrent_UpLimit_Flag==true)
				   {
					   SystemError_OutputCurrent_Over.setId("T1");
				   }
				   else if(OptionSet_OutputCurrent_UpLimit_Flag==false)
				   {
					   SystemError_OutputCurrent_Over.setId("T33");
				   }
				   break;
			//@����������޳�
			case 1:
				   //@-���������ƣ�
				   if(OptionSet_OutputCurrent_UpLimit_Flag==true)
				   {
					   if(AODI_DisplayTimer.second_1s_flash==true)
					   SystemError_OutputCurrent_Over.setId("T44");
					   else if(AODI_DisplayTimer.second_1s_flash==false)
					   SystemError_OutputCurrent_Over.setId("T55");
				   }
				   else if(OptionSet_OutputCurrent_UpLimit_Flag==false)
				   {
					   SystemError_OutputCurrent_Over.setId("T33");
				   }
				   break;
			//@���������������
			case 2:
				   //@-���������ƣ�
				   if(OptionSet_OutputCurrent_UpLimit_Flag==true)
				   {
					   SystemError_OutputCurrent_Over.setId("T22");
				   }
				   else if(OptionSet_OutputCurrent_UpLimit_Flag==false)
				   {
					   SystemError_OutputCurrent_Over.setId("T33");
				   }
				   break;
		    default: break;
		}
		//@4-���Ʊ���-�����������
		switch(OutputCurrent_Down_LimitFlag)
		{
			//@���������������
			case 0:
				   //@-���������ƣ�
				   if(OptionSet_OutputCurrent_DownLimit_Flag==true)
				   {
					   SystemError_OutputCurrent_Under.setId("T1");
				   }
				   else if(OptionSet_OutputCurrent_DownLimit_Flag==false)
				   {
					   SystemError_OutputCurrent_Under.setId("T33");
				   }
				   break;
			//@����������޳�
			case 1:
				   //@-���������ƣ�
				   if(OptionSet_OutputCurrent_DownLimit_Flag==true)
				   {
					   if(AODI_DisplayTimer.second_1s_flash==true)
					   SystemError_OutputCurrent_Under.setId("T44");
					   else if(AODI_DisplayTimer.second_1s_flash==false)
					   SystemError_OutputCurrent_Under.setId("T55");
				   }
				   else if(OptionSet_OutputCurrent_DownLimit_Flag==false)
				   {
					   SystemError_OutputCurrent_Under.setId("T33");
				   }
				   break;
			//@���������������
			case 2:
				   //@-���������ƣ�
				   if(OptionSet_OutputCurrent_DownLimit_Flag==true)
				   {
					   SystemError_OutputCurrent_Under.setId("T22");
				   }
				   else if(OptionSet_OutputCurrent_DownLimit_Flag==false)
				   {
					   SystemError_OutputCurrent_Under.setId("T33");
				   }
				   break;
		    default: break;
		}

		//@5-���Ʊ���-ĸ�ߵ�ѹ����
		switch(MainVoltage_Up_LimitFlag)
		{
			//@ĸ�ߵ�ѹ��������
			case 0:
				   //@-���������ƣ�
				   if(OptionSet_MainVoltage_UpLimit_Flag==true)
				   {
					   SystemError_MainVoltage_Over.setId("T1");
				   }
				   else if(OptionSet_MainVoltage_UpLimit_Flag==false)
				   {
					   SystemError_MainVoltage_Over.setId("T33");
				   }
				   break;
			//@ĸ�ߵ�ѹ���޳�
			case 1:
				   //@-���������ƣ�
				   if(OptionSet_MainVoltage_UpLimit_Flag==true)
				   {
					   if(AODI_DisplayTimer.second_1s_flash==true)
					   SystemError_MainVoltage_Over.setId("T44");
					   else if(AODI_DisplayTimer.second_1s_flash==false)
					   SystemError_MainVoltage_Over.setId("T55");
				   }
				   else if(OptionSet_MainVoltage_UpLimit_Flag==false)
				   {
					   SystemError_MainVoltage_Over.setId("T33");
				   }
				   break;
			//@ĸ�ߵ�ѹ��������
			case 2:
				   //@-���������ƣ�
				   if(OptionSet_MainVoltage_UpLimit_Flag==true)
				   {
					   SystemError_MainVoltage_Over.setId("T22");
				   }
				   else if(OptionSet_MainVoltage_UpLimit_Flag==false)
				   {
					   SystemError_MainVoltage_Over.setId("T33");
				   }
				   break;
		    default: break;
		}
		//@6-���Ʊ���-ĸ�ߵ�ѹ����
		switch(MainVoltage_Down_LimitFlag)
		{
			//@ĸ�ߵ�ѹ��������
			case 0:
				   //@-���������ƣ�
				   if(OptionSet_MainVoltage_DownLimit_Flag==true)
				   {
					   SystemError_MainVoltage_Under.setId("T1");
				   }
				   else if(OptionSet_MainVoltage_DownLimit_Flag==false)
				   {
					   SystemError_MainVoltage_Under.setId("T33");
				   }
				   break;
			//@ĸ�ߵ�ѹ���޳�
			case 1:
				   //@-���������ƣ�
				   if(OptionSet_MainVoltage_DownLimit_Flag==true)
				   {
					   if(AODI_DisplayTimer.second_1s_flash==true)
					   SystemError_MainVoltage_Under.setId("T44");
					   else if(AODI_DisplayTimer.second_1s_flash==false)
					   SystemError_MainVoltage_Under.setId("T55");
				   }
				   else if(OptionSet_MainVoltage_DownLimit_Flag==false)
				   {
					   SystemError_MainVoltage_Under.setId("T33");
				   }
				   break;
			//@ĸ�ߵ�ѹ��������
			case 2:
				   //@-���������ƣ�
				   if(OptionSet_MainVoltage_DownLimit_Flag==true)
				   {
					   SystemError_MainVoltage_Under.setId("T22");
				   }
				   else if(OptionSet_MainVoltage_DownLimit_Flag==false)
				   {
					   SystemError_MainVoltage_Under.setId("T33");
				   }
				   break;
		    default: break;
		}

		//@7-���Ʊ���-����¶�����
		switch(Temperature_Up_LimitFlag)
		{
			//@����¶���������
			case 0:
				   //@-���������ƣ�
				   if(OptionSet_Temperature_UpLimit_Flag==true)
				   {
					   SystemError_Temperature_Over.setId("T1");
				   }
				   else if(OptionSet_Temperature_UpLimit_Flag==false)
				   {
					   SystemError_Temperature_Over.setId("T33");
				   }
				   break;
			//@����¶����޳�
			case 1:
				   //@-���������ƣ�
				   if(OptionSet_Temperature_UpLimit_Flag==true)
				   {
					   if(AODI_DisplayTimer.second_1s_flash==true)
					   SystemError_Temperature_Over.setId("T44");
					   else if(AODI_DisplayTimer.second_1s_flash==false)
					   SystemError_Temperature_Over.setId("T55");
				   }
				   else if(OptionSet_Temperature_UpLimit_Flag==false)
				   {
					   SystemError_Temperature_Over.setId("T33");
				   }
				   break;
			//@����¶ȵ�������
			case 2:
				   //@-���������ƣ�
				   if(OptionSet_Temperature_UpLimit_Flag==true)
				   {
					   SystemError_Temperature_Over.setId("T22");
				   }
				   else if(OptionSet_Temperature_UpLimit_Flag==false)
				   {
					   SystemError_Temperature_Over.setId("T33");
				   }
				   break;
		    default: break;
		}
		//@8-���Ʊ���-����¶�����
		switch(Temperature_Down_LimitFlag)
		{
			//@����¶���������
			case 0:
				   //@-���������ƣ�
				   if(OptionSet_Temperature_DownLimit_Flag==true)
				   {
					   SystemError_Temperature_Under.setId("T1");
				   }
				   else if(OptionSet_Temperature_DownLimit_Flag==false)
				   {
					   SystemError_Temperature_Under.setId("T33");
				   }
				   break;
			//@����¶����޳�
			case 1:
				   //@-���������ƣ�
				   if(OptionSet_Temperature_DownLimit_Flag==true)
				   {
					   if(AODI_DisplayTimer.second_1s_flash==true)
					   SystemError_Temperature_Under.setId("T44");
					   else if(AODI_DisplayTimer.second_1s_flash==false)
					   SystemError_Temperature_Under.setId("T55");
				   }
				   else if(OptionSet_Temperature_DownLimit_Flag==false)
				   {
					   SystemError_Temperature_Under.setId("T33");
				   }
				   break;
			//@����¶ȵ�������
			case 2:
				   //@-���������ƣ�
				   if(OptionSet_Temperature_DownLimit_Flag==true)
				   {
					   SystemError_Temperature_Under.setId("T22");
				   }
				   else if(OptionSet_Temperature_DownLimit_Flag==false)
				   {
					   SystemError_Temperature_Under.setId("T33");
				   }
				   break;
		    default: break;
		}
    }


    /**TextField���Ƽ�����
     *
     * @param Text
     */
    private void TextField_CharFilter(final TextField Text)
    {
    	Text.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
            @Override  public void handle(KeyEvent inputevent) {
                  if ((inputevent.getCharacter().matches("[a-z]"))||(inputevent.getCharacter().matches("[A-Z]")))
                  {
                       inputevent.consume();
                  }
                }
            });

    	Text.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
                if (Text.getText().length() > 6) {
                    String s = Text.getText().substring(0, 6);
                    Text.setText(s);
                }
            }
        });
    }


    /**���߸��¶�ʱʱ����
     *
     */
    private void prepareTimeline()
    {
        //@1-����ʱ����
    	Curve_Timeline = new Timeline();

        //@2-����ʱ����Ϊ����ѭ��
    	Curve_Timeline.setCycleCount(Animation.INDEFINITE);

        //@3-ʱ�������Ӷ�ʱ�¼�
    	Curve_Timeline.getKeyFrames().add(
                new KeyFrame(Duration.millis(30), new EventHandler<ActionEvent>() {
                    @Override public void handle(ActionEvent actionEvent) {

                    	//@4-����ƽ̨����߳�
						Platform.runLater(new Runnable() {
							@Override
							public void run() {

								//@5-���������������
					        	addDataToSeries();
							}
						});
                    }
                })
        );

        //@6-���¼���������������ʱ������
    	Curve_Animation = new SequentialTransition();
    	Curve_Animation.getChildren().addAll(Curve_Timeline);

        //@7-������������״̬
        curve_updata_switch();

    }

    /**���߸��¿�ʼ
     *
     */
    public void curve_updata_switch()
    {
    	if(CurveRun_Status==false)
    	{
    		CurveRun_Status=true;
    		ChartRun_Button.setText("ֹͣ");
    		Curve_Animation.play();
    	}
    	else if(CurveRun_Status==true)
    	{
    		CurveRun_Status=false;
    		ChartRun_Button.setText("����");
    		Curve_Animation.stop();
    	}
    }

    /**���������������
     *
     */
    private void addDataToSeries()
    {
    	//@1-ͬ�����߳�
    	synchronized(this)
    	{

    		//@2-�жϻ�׼����Դ-�����ѹ
    		if(Curve_OutputVoltage_Dis==true)
    		{
        		//@-ͬ������Դ
        		synchronized (Data_OutputVoltage)
        		{
    	    	    if(Data_OutputVoltage.isEmpty()==false)
    	    	    {
    	    	    	OutputVoltage_Current=Data_OutputVoltage.remove(0);
    	    	    	ChartData_OutputVoltage.getData().add(new LineChart.Data(xSeriesData++, OutputVoltage_Current));
    	    	    	Curve_UpData_DataBase=1;
    	    	    }
        		}
    		}
    		//@3-�жϻ�׼����Դ-�������
    		else if(Curve_OutputCurrent_Dis==true)
    		{
        		//@-ͬ������Դ
        		synchronized (Data_OutputCurrent)
        		{
    	    	    if(Data_OutputCurrent.isEmpty()==false)
    	    	    {
    	    	    	OutputCurrent_Current=Data_OutputCurrent.remove(0);
    	    	    	ChartData_OutputCurrent.getData().add(new LineChart.Data(xSeriesData++, OutputCurrent_Current));
    	    	    	Curve_UpData_DataBase=2;
    	    	    }
        		}
    		}
    		//@4-�жϻ�׼����Դ-ĸ�ߵ�ѹ
    		else if(Curve_MainVoltage_Dis==true)
    		{
        		//@-ͬ������Դ
        		synchronized (Data_MainVoltage)
        		{
    	    	    if(Data_MainVoltage.isEmpty()==false)
    	    	    {
    	    	    	MainVoltage_Current=Data_MainVoltage.remove(0);
    	    	    	ChartData_MainVoltage.getData().add(new LineChart.Data(xSeriesData++, MainVoltage_Current));
    	    	    	Curve_UpData_DataBase=3;
    	    	    }
        		}
    		}
    		//@5-�жϻ�׼����Դ-����¶�
    		else if(Curve_Temperature_Dis==true)
    		{
        		//@-ͬ������Դ
        		synchronized (Data_Temperature)
        		{
    	    	    if(Data_Temperature.isEmpty()==false)
    	    	    {
    	    	    	Temperature_Current=Data_Temperature.remove(0);
    	    	    	ChartData_Temperature.getData().add(new LineChart.Data(xSeriesData++, Temperature_Current));
    	    	    	Curve_UpData_DataBase=4;
    	    	    }
        		}
    		}
    		//@6-�жϻ�׼����Դ-���Ƶ�ѹ
    		else if(Curve_ControlVoltage_Dis==true)
    		{
        		//@-ͬ������Դ
        		synchronized (Data_ControlVoltage)
        		{
    	    	    if(Data_ControlVoltage.isEmpty()==false)
    	    	    {
    	    	    	ControlVoltage_Current=Data_ControlVoltage.remove(0);
    	    	    	ChartData_ControlVoltage.getData().add(new LineChart.Data(xSeriesData++, ControlVoltage_Current));
    	    	    	Curve_UpData_DataBase=5;
    	    	    }
        		}
    		}

    		//@7-��׼����Դ�Ƿ����
	    	if(xSeriesData_copy!=xSeriesData)
	    	{
	    		xSeriesData_copy=xSeriesData;
	    		Curve_UpData=true;
	    	}
	    	else
	    	{
	    		Curve_UpData=false;
	    	}

	    	//@8-��׼����Դ����
	    	if(Curve_UpData==true)
	    	{
	    		//@9-���ݻ�׼Դ�������ѹ
    		    if(Curve_UpData_DataBase!=1)
    		    {
    	    		//@10-�����ѹ�Ƿ���ʾ
    	    		if(Curve_OutputVoltage_Dis==true)
    	    		{
    	        		//@-ͬ������Դ
    	        		synchronized (Data_OutputVoltage)
    	        		{
    	    	    	    if(Data_OutputVoltage.isEmpty()==false)
    	    	    	    {
    	    	    	    	OutputVoltage_Current=Data_OutputVoltage.remove(0);
    	    	    	    	ChartData_OutputVoltage.getData().add(new LineChart.Data(xSeriesData, OutputVoltage_Current));
    	    	    	    }
    	        		}
    	    		}
    		    }

	    		//@11-���ݻ�׼Դ���������
    		    if(Curve_UpData_DataBase!=2)
    		    {
    	    		//@12-��������Ƿ���ʾ
    	    		if(Curve_OutputCurrent_Dis==true)
    	    		{
    	        		//@-ͬ������Դ
    	        		synchronized (Data_OutputCurrent)
    	        		{
    	    	    	    if(Data_OutputCurrent.isEmpty()==false)
    	    	    	    {
    	    	    	    	OutputCurrent_Current=Data_OutputCurrent.remove(0);
    	    	    	    	ChartData_OutputCurrent.getData().add(new LineChart.Data(xSeriesData, OutputCurrent_Current));
    	    	    	    }
    	        		}
    	    		}
    		    }

	    		//@13-���ݻ�׼Դ��ĸ�ߵ�ѹ
    		    if(Curve_UpData_DataBase!=3)
    		    {
    	    		//@14-ĸ�ߵ�ѹ�Ƿ���ʾ
    	    		if(Curve_MainVoltage_Dis==true)
    	    		{
    	        		//@-ͬ������Դ
    	        		synchronized (Data_MainVoltage)
    	        		{
    	    	    	    if(Data_MainVoltage.isEmpty()==false)
    	    	    	    {
    	    	    	    	MainVoltage_Current=Data_MainVoltage.remove(0);
    	    	    	    	ChartData_MainVoltage.getData().add(new LineChart.Data(xSeriesData, MainVoltage_Current));
    	    	    	    }
    	        		}
    	    		}
    		    }

	    		//@15-���ݻ�׼Դ�Ǽ���¶�
    		    if(Curve_UpData_DataBase!=4)
    		    {
    	    		//@16-����¶��Ƿ���ʾ
    	    		if(Curve_Temperature_Dis==true)
    	    		{
    	        		//@-ͬ������Դ
    	        		synchronized (Data_Temperature)
    	        		{
    	    	    	    if(Data_Temperature.isEmpty()==false)
    	    	    	    {
    	    	    	    	Temperature_Current=Data_Temperature.remove(0);
    	    	    	    	ChartData_Temperature.getData().add(new LineChart.Data(xSeriesData, Temperature_Current));
    	    	    	    }
    	        		}
    	    		}
    		    }

	    		//@17-���ݻ�׼Դ�ǿ��Ƶ�ѹ
    		    if(Curve_UpData_DataBase!=5)
    		    {
    	    		//@18-���Ƶ�ѹ�Ƿ���ʾ
    	    		if(Curve_ControlVoltage_Dis==true)
    	    		{
    	        		//@-ͬ������Դ
    	        		synchronized (Data_ControlVoltage)
    	        		{
    	    	    	    if(Data_ControlVoltage.isEmpty()==false)
    	    	    	    {
    	    	    	    	ControlVoltage_Current=Data_ControlVoltage.remove(0);
    	    	    	    	ChartData_ControlVoltage.getData().add(new LineChart.Data(xSeriesData, ControlVoltage_Current));
    	    	    	    }
    	        		}
    	    		}
    		    }


		     //@18-���߳��������ʾ��
	        if (ChartData_OutputVoltage.getData().size() > MAX_DATA_POINTS)
	        {
	    		if(Curve_OutputVoltage_Dis==true)
	    		{
	    			ChartData_OutputVoltage.getData().remove(0);
	    		}

	        }
	        if(ChartData_OutputCurrent.getData().size() > MAX_DATA_POINTS)
	        {
	        	if(Curve_OutputCurrent_Dis==true)
	        	{
	        		ChartData_OutputCurrent.getData().remove(0);
	        	}
	        }
	        if(ChartData_MainVoltage.getData().size() > MAX_DATA_POINTS)
	        {
	        	if(Curve_MainVoltage_Dis==true)
	        	{
	        		ChartData_MainVoltage.getData().remove(0);
	        	}
	        }
	        if(ChartData_Temperature.getData().size() > MAX_DATA_POINTS)
	        {
	        	if(Curve_Temperature_Dis==true)
	        	{
	        		ChartData_Temperature.getData().remove(0);
	        	}
	        }
	        if(ChartData_ControlVoltage.getData().size() > MAX_DATA_POINTS)
	        {
	        	if(Curve_ControlVoltage_Dis==true)
	        	{
	        		ChartData_ControlVoltage.getData().remove(0);
	        	}
	        }

	        //@19-���߸��±߽���ʾ
	        Chart_Axis_X.setLowerBound(xSeriesData-MAX_DATA_POINTS);
	        Chart_Axis_X.setUpperBound(xSeriesData-1);
        }

    }

  }

    /**����������ʾѡ����
     *
     * @param Display_Num
     */
    private void Curve_Display_Pro(int Display_Num)
    {
    	switch(Display_Num)
    	{
    	    //@1-�����ѹ
    		case 1:
    			   if(Charging_OutputVoltageCurve_Sel.isSelected()==true)
    			   {
    				   Curve_Display_Count=Curve_Display_Count+1;
    				   Curve_OutputVoltage_Dis=true;
    				   //@-�����Ƴ���ʶ�ж��Ƿ����
    				   if(Curve_OutputVoltage_Remove==true)
    				   {
    					   Curve_OutputVoltage_Remove=false;
    					   Chart.getData().add(ChartData_OutputVoltage);
    				   }
    			   }
    			   else if(Charging_OutputVoltageCurve_Sel.isSelected()==false)
    			   {
    				   if(Curve_Display_Chech()==false)
    				   {
    					   Charging_OutputVoltageCurve_Sel.setSelected(true);
    					   Curve_OutputVoltage_Dis=true;
        				   //@-�����Ƴ���ʶ�ж��Ƿ����
        				   if(Curve_OutputVoltage_Remove==true)
        				   {
        					   Curve_OutputVoltage_Remove=false;
        					   Chart.getData().add(ChartData_OutputVoltage);
        				   }

    				   }
    				   else if(Curve_Display_Chech()==true)
    				   {
    					   Charging_OutputVoltageCurve_Sel.setSelected(false);
    					   Curve_OutputVoltage_Dis=false;
    					   Curve_Display_Count=Curve_Display_Count-1;
    					   //@�Ƴ�������
    					   Curve_OutputVoltage_Remove=true;
    					   Chart.getData().remove(ChartData_OutputVoltage);
    				   }
    			   }
    			   break;
    	    //@2-�������
    		case 2:
    			   if(Charging_OutputCurrentCurve_Sel.isSelected()==true)
    			   {
    				   Curve_Display_Count=Curve_Display_Count+1;
    				   Curve_OutputCurrent_Dis=true;
    				   //@-�����Ƴ���ʶ�ж��Ƿ����
    				   if(Curve_OutputCurrent_Remove==true)
    				   {
    					   Curve_OutputCurrent_Remove=false;
    					   Chart.getData().add(ChartData_OutputCurrent);
    				   }
    			   }
    			   else if(Charging_OutputCurrentCurve_Sel.isSelected()==false)
    			   {
    				   if(Curve_Display_Chech()==false)
    				   {
    					   Charging_OutputCurrentCurve_Sel.setSelected(true);
        				   Curve_OutputCurrent_Dis=true;
        				   //@-�����Ƴ���ʶ�ж��Ƿ����
        				   if(Curve_OutputCurrent_Remove==true)
        				   {
        					   Curve_OutputCurrent_Remove=false;
        					   Chart.getData().add(ChartData_OutputCurrent);
        				   }
    				   }
    				   else if(Curve_Display_Chech()==true)
    				   {
    					   Charging_OutputCurrentCurve_Sel.setSelected(false);
        				   Curve_OutputCurrent_Dis=false;
    					   Curve_Display_Count=Curve_Display_Count-1;
    					   //@�Ƴ�������
    					   Curve_OutputCurrent_Remove=true;
    					   Chart.getData().remove(ChartData_OutputCurrent);
    				   }
    			   }
    			   break;
    	    //@3-ĸ�ߵ�ѹ
    		case 3:
    			   if(Charging_MainVoltageCurve_Sel.isSelected()==true)
    			   {
    				   Curve_Display_Count=Curve_Display_Count+1;
    				   Curve_MainVoltage_Dis=true;
    				   //@-�����Ƴ���ʶ�ж��Ƿ����
    				   if(Curve_MainVoltage_Remove==true)
    				   {
    					   Curve_MainVoltage_Remove=false;
    					   Chart.getData().add(ChartData_MainVoltage);
    				   }
    			   }
    			   else if(Charging_MainVoltageCurve_Sel.isSelected()==false)
    			   {
    				   if(Curve_Display_Chech()==false)
    				   {
    					   Charging_MainVoltageCurve_Sel.setSelected(true);
        				   Curve_MainVoltage_Dis=true;
        				   //@-�����Ƴ���ʶ�ж��Ƿ����
        				   if(Curve_MainVoltage_Remove==true)
        				   {
        					   Curve_MainVoltage_Remove=false;
        					   Chart.getData().add(ChartData_MainVoltage);
        				   }
    				   }
    				   else if(Curve_Display_Chech()==true)
    				   {
    					   Charging_MainVoltageCurve_Sel.setSelected(false);
        				   Curve_MainVoltage_Dis=false;
    					   Curve_Display_Count=Curve_Display_Count-1;
    					   //@�Ƴ�������
    					   Curve_MainVoltage_Remove=true;
    					   Chart.getData().remove(ChartData_MainVoltage);
    				   }
    			   }
    			   break;
    	    //@4-����¶�
    		case 4:
    			   if(Charging_TemperatureCurve_Sel.isSelected()==true)
    			   {
    				   Curve_Display_Count=Curve_Display_Count+1;
    				   Curve_Temperature_Dis=true;
    				   //@-�����Ƴ���ʶ�ж��Ƿ����
    				   if(Curve_Temperature_Remove==true)
    				   {
    					   Curve_Temperature_Remove=false;
    					   Chart.getData().add(ChartData_Temperature);
    				   }
    			   }
    			   else if(Charging_TemperatureCurve_Sel.isSelected()==false)
    			   {
    				   if(Curve_Display_Chech()==false)
    				   {
    					   Charging_TemperatureCurve_Sel.setSelected(true);
        				   Curve_Temperature_Dis=true;
        				   //@-�����Ƴ���ʶ�ж��Ƿ����
        				   if(Curve_Temperature_Remove==true)
        				   {
        					   Curve_Temperature_Remove=false;
        					   Chart.getData().add(ChartData_Temperature);
        				   }
    				   }
    				   else if(Curve_Display_Chech()==true)
    				   {
    					   Charging_TemperatureCurve_Sel.setSelected(false);
        				   Curve_Temperature_Dis=false;
    					   Curve_Display_Count=Curve_Display_Count-1;
    					   //@�Ƴ�������
    					   Curve_Temperature_Remove=true;
    					   Chart.getData().remove(ChartData_Temperature);
    				   }
    			   }
    			   break;
    	    //@4-���Ƶ�ѹ
    		case 5:
	    			if(Charging_ControlVoltageCurve_Sel.isSelected()==true)
	 			   {
	 				   Curve_Display_Count=Curve_Display_Count+1;
	 				   Curve_ControlVoltage_Dis=true;
	 				   //@-�����Ƴ���ʶ�ж��Ƿ����
	 				   if(Curve_ControlVoltage_Remove==true)
	 				   {
	 					   Curve_ControlVoltage_Remove=false;
	 					   Chart.getData().add(ChartData_ControlVoltage);
	 				   }
	 			   }
	 			   else if(Charging_ControlVoltageCurve_Sel.isSelected()==false)
	 			   {
	 				   if(Curve_Display_Chech()==false)
	 				   {
	 					   Charging_ControlVoltageCurve_Sel.setSelected(true);
	 					   Curve_ControlVoltage_Dis=true;
	     				   //@-�����Ƴ���ʶ�ж��Ƿ����
	     				   if(Curve_ControlVoltage_Remove==true)
	     				   {
	     					   Curve_ControlVoltage_Remove=false;
	     					   Chart.getData().add(ChartData_ControlVoltage);
	     				   }

	 				   }
	 				   else if(Curve_Display_Chech()==true)
	 				   {
	 					   Charging_ControlVoltageCurve_Sel.setSelected(false);
	 					   Curve_ControlVoltage_Dis=false;
	 					   Curve_Display_Count=Curve_Display_Count-1;
	 					   //@�Ƴ�������
	 					   Curve_ControlVoltage_Remove=true;
	 					   Chart.getData().remove(ChartData_ControlVoltage);
	 				   }
	 			   }
	 			   break;

    	     default: break;

    	}
    }


    /**���������ʾ����
     *
     * @return
     */
    private boolean Curve_Display_Chech()
    {
    	//�������һ��������ʾ
    	if(Curve_Display_Count>1)
    	{
    		return true;
    	}
    	else
    	{
    		return false;
    	}
    }


    /**����������
     *
     * @param event
     */
    @FXML
    public void Button_Pro(ActionEvent event)
    {
    	//@1-������ʾѡ����
    	if((event.getSource()==Charging_OutputVoltageCurve_Sel))
    	{
    		Curve_Display_Pro(1);
    	}
    	else if((event.getSource()==Charging_OutputCurrentCurve_Sel))
    	{
    		Curve_Display_Pro(2);
    	}
    	else if((event.getSource()==Charging_MainVoltageCurve_Sel))
    	{
    		Curve_Display_Pro(3);
    	}
    	else if((event.getSource()==Charging_TemperatureCurve_Sel))
    	{
    		Curve_Display_Pro(4);
    	}
    	else if((event.getSource()==Charging_ControlVoltageCurve_Sel))
    	{
    		Curve_Display_Pro(5);
    	}
    	//@2-������ͣ����
    	else if((event.getSource()==ChartRun_Button))
    	{
    		curve_updata_switch();
    	}
    	//@3-������ͣ����
    	else if((event.getSource()==ConnectionControl_Button))
    	{
    		Serise_Pro();
    	}

    	//@4-��������-�����ѹ��ѹ��ͣ
    	else if((event.getSource()==OptionSet_OutputVoltage_Over_Do))
    	{
    		//@�����ѹ���޼��
    		if(OptionSet_OutputVoltage_Over_Do.isSelected()==true)
    		{
    			//@��־
    			OptionSet_OutputVoltage_UpLimit_Flag=true;
    			//@��ʾ
    			Charging_OutputVoltage_UpLimit.setTextFill(Color.BLACK);
    			//@��ֵ����
    			OptionSet_OutputVoltage_Over.setDisable(true);
    			OptionSet_OutputVoltage_Over.setId("T2");
    			//@������ֵ
    			OptionSet_OutputVoltage_UpLimit=Float.parseFloat(OptionSet_OutputVoltage_Over.getText());
    			//System.out.println(""+OptionSet_OutputVoltage_UpLimit);
    		}
    		else if(OptionSet_OutputVoltage_Over_Do.isSelected()==false)
    		{
    			//@��־
    			OptionSet_OutputVoltage_UpLimit_Flag=false;
    			//@��ʾ
    			Charging_OutputVoltage_UpLimit.setTextFill(Color.rgb(0xbf, 0xbf, 0xbf));
    			//@��ֵ����
    			OptionSet_OutputVoltage_Over.setDisable(false);
    			OptionSet_OutputVoltage_Over.setText("0");
    			OptionSet_OutputVoltage_Over.setId("T1");
    		}
    	}
    	//@5-��������-�����ѹǷѹ��ͣ
    	else if((event.getSource()==OptionSet_OutputVoltage_Under_Do))
    	{
    		//@�����ѹ���޼��
    		if(OptionSet_OutputVoltage_Under_Do.isSelected()==true)
    		{
    			//@��־
    			OptionSet_OutputVoltage_DownLimit_Flag=true;
    			//@��ʾ
    			Charging_OutputVoltage_DownLimit.setTextFill(Color.BLACK);
    			//@��ֵ����
    			OptionSet_OutputVoltage_Under.setDisable(true);
    			OptionSet_OutputVoltage_Under.setId("T2");
    			//@������ֵ
    			OptionSet_OutputVoltage_DownLimit=Float.parseFloat(OptionSet_OutputVoltage_Under.getText());
    		}
    		else if(OptionSet_OutputVoltage_Under_Do.isSelected()==false)
    		{
    			//@��־
    			OptionSet_OutputVoltage_DownLimit_Flag=false;
    			//@��ʾ
    			Charging_OutputVoltage_DownLimit.setTextFill(Color.rgb(0xbf, 0xbf, 0xbf));
    			//@��ֵ����
    			OptionSet_OutputVoltage_Under.setDisable(false);
    			OptionSet_OutputVoltage_Under.setText("0");
    			OptionSet_OutputVoltage_Under.setId("T1");
    		}
    	}
    	//@6-��������-ĸ�ߵ�ѹ��ѹ��ͣ
    	else if((event.getSource()==OptionSet_MainVoltage_Over_Do))
    	{
    		//@ĸ�ߵ�ѹ���޼��
    		if(OptionSet_MainVoltage_Over_Do.isSelected()==true)
    		{
    			//@��־
    			OptionSet_MainVoltage_UpLimit_Flag=true;
    			//@��ʾ
    			Charging_MainVoltage_UpLimit.setTextFill(Color.BLACK);
    			//@��ֵ����
    			OptionSet_MainVoltage_Over.setDisable(true);
    			OptionSet_MainVoltage_Over.setId("T2");
    			//@������ֵ
    			OptionSet_MainVoltage_UpLimit=Float.parseFloat(OptionSet_MainVoltage_Over.getText());
    		}
    		else if(OptionSet_MainVoltage_Over_Do.isSelected()==false)
    		{
    			//@��־
    			OptionSet_MainVoltage_UpLimit_Flag=false;
    			//@��ʾ
    			Charging_MainVoltage_UpLimit.setTextFill(Color.rgb(0xbf, 0xbf, 0xbf));
    			//@��ֵ����
    			OptionSet_MainVoltage_Over.setDisable(false);
    			OptionSet_MainVoltage_Over.setText("0");
    			OptionSet_MainVoltage_Over.setId("T1");
    		}
    	}
    	//@7-��������-ĸ�ߵ�ѹǷѹ��ͣ
    	else if((event.getSource()==OptionSet_MainVoltage_Under_Do))
    	{
    		//@ĸ�ߵ�ѹ���޼��
    		if(OptionSet_MainVoltage_Under_Do.isSelected()==true)
    		{
    			//@��־
    			OptionSet_MainVoltage_DownLimit_Flag=true;
    			//@��ʾ
    			Charging_MainVoltage_DownLimit.setTextFill(Color.BLACK);
    			//@��ֵ����
    			OptionSet_MainVoltage_Under.setDisable(true);
    			OptionSet_MainVoltage_Under.setId("T2");
    			//@������ֵ
    			OptionSet_MainVoltage_DownLimit=Float.parseFloat(OptionSet_MainVoltage_Under.getText());
    		}
    		else if(OptionSet_MainVoltage_Under_Do.isSelected()==false)
    		{
    			//@��־
    			OptionSet_MainVoltage_DownLimit_Flag=false;
    			//@��ʾ
    			Charging_MainVoltage_DownLimit.setTextFill(Color.rgb(0xbf, 0xbf, 0xbf));
    			//@��ֵ����
    			OptionSet_MainVoltage_Under.setDisable(false);
    			OptionSet_MainVoltage_Under.setText("0");
    			OptionSet_MainVoltage_Under.setId("T1");
    		}
    	}

    	//@8-��������-�������������ͣ
    	else if((event.getSource()==OptionSet_OutputCurrent_Over_Do))
    	{
    		//@����������޼��
    		if(OptionSet_OutputCurrent_Over_Do.isSelected()==true)
    		{
    			//@��־
    			OptionSet_OutputCurrent_UpLimit_Flag=true;
    			//@��ʾ
    			Charging_OutputCurrent_UpLimit.setTextFill(Color.BLACK);
    			//@��ֵ����
    			OptionSet_OutputCurrent_Over.setDisable(true);
    			OptionSet_OutputCurrent_Over.setId("T2");
    			//@������ֵ
    			OptionSet_OutputCurrent_UpLimit=Float.parseFloat(OptionSet_OutputCurrent_Over.getText());
    		}
    		else if(OptionSet_OutputCurrent_Over_Do.isSelected()==false)
    		{
    			//@��־
    			OptionSet_OutputCurrent_UpLimit_Flag=false;
    			//@��ʾ
    			Charging_OutputCurrent_UpLimit.setTextFill(Color.rgb(0xbf, 0xbf, 0xbf));
    			//@��ֵ����
    			OptionSet_OutputCurrent_Over.setDisable(false);
    			OptionSet_OutputCurrent_Over.setText("0");
    			OptionSet_OutputCurrent_Over.setId("T1");
    		}
    	}
    	//@9-��������-�������������ͣ
    	else if((event.getSource()==OptionSet_OutputCurrent_Under_Do))
    	{
    		//@����������޼��
    		if(OptionSet_OutputCurrent_Under_Do.isSelected()==true)
    		{
    			//@��־
    			OptionSet_OutputCurrent_DownLimit_Flag=true;
    			//@��ʾ
    			Charging_OutputCurrent_DownLimit.setTextFill(Color.BLACK);
    			//@��ֵ����
    			OptionSet_OutputCurrent_Under.setDisable(true);
    			OptionSet_OutputCurrent_Under.setId("T2");
    			//@������ֵ
    			OptionSet_OutputCurrent_DownLimit=Float.parseFloat(OptionSet_OutputCurrent_Under.getText());
    		}
    		else if(OptionSet_OutputCurrent_Under_Do.isSelected()==false)
    		{
    			//@��־
    			OptionSet_OutputCurrent_DownLimit_Flag=false;
    			//@��ʾ
    			Charging_OutputCurrent_DownLimit.setTextFill(Color.rgb(0xbf, 0xbf, 0xbf));
    			//@��ֵ����
    			OptionSet_OutputCurrent_Under.setDisable(false);
    			OptionSet_OutputCurrent_Under.setText("0");
    			OptionSet_OutputCurrent_Under.setId("T1");
    		}
    	}

    	//@10-��������-����¶ȹ�����ͣ
    	else if((event.getSource()==OptionSet_Temperature_Over_Do))
    	{
    		//@����¶����޼��
    		if(OptionSet_Temperature_Over_Do.isSelected()==true)
    		{
    			//@��־
    			OptionSet_Temperature_UpLimit_Flag=true;
    			//@��ʾ
    			Charging_Temperature_UpLimit.setTextFill(Color.BLACK);
    			//@��ֵ����
    			OptionSet_Temperature_Over.setDisable(true);
    			OptionSet_Temperature_Over.setId("T2");
    			//@������ֵ
    			OptionSet_Temperature_UpLimit=Float.parseFloat(OptionSet_Temperature_Over.getText());
    		}
    		else if(OptionSet_Temperature_Over_Do.isSelected()==false)
    		{
    			//@��־
    			OptionSet_Temperature_UpLimit_Flag=false;
    			//@��ʾ
    			Charging_Temperature_UpLimit.setTextFill(Color.rgb(0xbf, 0xbf, 0xbf));
    			//@��ֵ����
    			OptionSet_Temperature_Over.setDisable(false);
    			OptionSet_Temperature_Over.setText("0");
    			OptionSet_Temperature_Over.setId("T1");
    		}
    	}
    	//@11-��������-����¶ȹ�����ͣ
    	else if((event.getSource()==OptionSet_Temperature_Under_Do))
    	{
    		//@����¶����޼��
    		if(OptionSet_Temperature_Under_Do.isSelected()==true)
    		{
    			//@��־
    			OptionSet_Temperature_DownLimit_Flag=true;
    			//@��ʾ
    			Charging_Temperature_DownLimit.setTextFill(Color.BLACK);
    			//@��ֵ����
    			OptionSet_Temperature_Under.setDisable(true);
    			OptionSet_Temperature_Under.setId("T2");
    			//@������ֵ
    			OptionSet_Temperature_DownLimit=Float.parseFloat(OptionSet_Temperature_Under.getText());
    		}
    		else if(OptionSet_Temperature_Under_Do.isSelected()==false)
    		{
    			//@��־
    			OptionSet_Temperature_DownLimit_Flag=false;
    			//@��ʾ
    			Charging_Temperature_DownLimit.setTextFill(Color.rgb(0xbf, 0xbf, 0xbf));
    			//@��ֵ����
    			OptionSet_Temperature_Under.setDisable(false);
    			OptionSet_Temperature_Under.setText("0");
    			OptionSet_Temperature_Under.setId("T1");
    		}
    	}

    	//@12-��������-�����ļ�-����
    	else if((event.getSource()==OptionSet_StrategyLoad_Button))
    	{
    		//@-����ѹ���б�־
    		if(ChargeVoltage_StartFlag==false)
    		{
	    		//@-û�����ó�����
	    		if(OptionSet_StrategySet_Flag==false)
	    		{
		    		//@-���������ļ������
		    		StrategyChooseFile_Flag=ScreensFramework.File_Chooser();

		    		if(StrategyChooseFile_Flag==true)
		    		{
		    			//@-�ɹ�ѡ������ļ�
		    			OptionSet_StrategyLoadFile.setText(StrategyLoadFile_Name);
		        		//@-�����ļ�ģʽ
		        		StrategyLoadFile_Mode=2;
		    			//@-����ѡ��Ĳ����ļ���ʾ
		    			ScreensFramework.PageChange.set("strategy");
		    		}
		    		else if(StrategyChooseFile_Flag=false)
		    		{
		    			ScreensFramework.Show_Noti("Warning", "û��ѡ������ļ�!");
		    		}
	    		}
	    		else if(OptionSet_StrategySet_Flag==true)
	    		{
	    			OptionSet_StrategySet_Flag=false;
	    			ScreensFramework.Show_Noti("Success", "����������!");
	    			StrategyProperty_Main.setValue("Cancel");
	    		}
    		}
    		else if(ChargeVoltage_StartFlag==true)
    		{
    			ScreensFramework.Show_Noti("Warning", "�������У���ֹͣ!");
    		}
    	}

    	//@12-��������-�����ļ�-�½�
    	else if((event.getSource()==OptionSet_StrategyNew_Button))
    	{
    		//@-û�����ó�����
    		if(OptionSet_StrategySet_Flag==false)
    		{
	    		//@-�����ļ�ģʽ
	    		StrategyLoadFile_Mode=1;
	    		//@-�½������ļ�
	    		ScreensFramework.PageChange.set("strategy");
    		}
    	}
    	//@13-������а�ť
    	else if((event.getSource()==ChargingControl_Button))
    	{
    		//@-�����ó���Դ
    		if(Charging_PowerStarus==true)
    		{
	    		//@-��������������
	    		if(OptionSet_StrategySet_Flag==true)
	    		{
	    			//@-PCB������
	    			if(PCB_Status_Copy==true)
	    			{
		    			//@-���±�־
		    			if(ChargeVoltage_StartFlag==false)
		    			{
		    				ChargeVoltage_StartFlag=true;
		    				Charging_StatusLamp.setImage(Status_OK_Pic);

		    		    	//@-����ѹ��ʼֵ-20160716
		    				ControlVoltage_Current_Copy=VoltageStart;
		    			}
		    			else if(ChargeVoltage_StartFlag==true)
		    			{
		    				ChargeVoltage_StartFlag=false;
		    				Charging_StatusLamp.setImage(Status_Unknow_Pic);
		    			}
	    			}
	    			else if(PCB_Status_Copy==false)
	        		{
	    				ScreensFramework.Show_Noti("Warning", "PCBû�����ӣ����ܽ��г��!");
	        		}

	    		}
	    		else if(OptionSet_StrategySet_Flag==false)
	    		{
	    			ScreensFramework.Show_Noti("Error", "û�����ó�����!");
	    		}
    		}
    		else if(Charging_PowerStarus==false)
    		{
    			ScreensFramework.Show_Noti("Error", "��Դ��ѹ�պ�!");
    		}
    	}
    	//@14-��Դ��ѹѡ��ȷ����ť
    	else if((event.getSource()==Charging_PowerSet_Button))
    	{
    		//@-�������
    		if(Serise_Main.isOpen==true)
    		{
	    		if(Charging_PowerStarus==false)
	    		{
	    			//@-��ѡ���Դ��ѹ
	    			if(Charging_Power_Sel!=0)
	    			{
	    				if(Charging_Power_Sel==1)
	    				ScreensFramework.Show_Noti("Success", "��Դ��ѹѡ��Ϊ110V!");
	    				else if(Charging_Power_Sel==2)
	    				ScreensFramework.Show_Noti("Success", "��Դ��ѹѡ��Ϊ220V!");
	    				if(Charging_Power_Sel==3)
	    				ScreensFramework.Show_Noti("Success", "��Դ��ѹѡ��Ϊ260V!");

		    			Charging_PowerStarus=true;
		    			Charging_PowerStatus_Lamp.setImage(Status_SmallOK_Pic);

						//Send CAN ID:0x00000119
						Charging_PowerSend_Flag=true;
	    			}
	    			else
	    			{
	    				ScreensFramework.Show_Noti("Warning", "��ѡ���Դ��ѹ!");
	    			}
	    		}
	    		else if(Charging_PowerStarus==true)
	    		{
	    			//@-���δ����
	    			if(ChargeVoltage_StartFlag==false)
	    			{
		    			//@-��ѡ���Դ��ѹ����
		    			Charging_Power_Sel=0;
		    			//@-��ɫ����
		        		Charging_Power110_Rect.setFill(Color.rgb(0xcc, 0x62, 0x00));
		            	Charging_Power220_Rect.setFill(Color.rgb(0xcc, 0x62, 0x00));
		            	Charging_Power260_Rect.setFill(Color.rgb(0xcc, 0x62, 0x00));

		    			Charging_PowerStarus=false;
		    			Charging_PowerStatus_Lamp.setImage(Status_SmallUnknow_Pic);

						//Send CAN ID:0x00000119
						Charging_PowerSend_Flag=true;
	    			}
	    			else if(ChargeVoltage_StartFlag==true)
	    			{
	    				ScreensFramework.Show_Noti("Warning", "�������������ֹͣ!");
	    			}
	    		}
	    	}
    		else if(Serise_Main.isOpen==false)
    		{
    			ScreensFramework.Show_Noti("Error", "����δ��!");
    		}
    	}
    	//@-ϵͳ��Ϣ���
    	else if((event.getSource()==SystemInfo_Clean_Button))
    	{
    		AODI_DisplayTimer.MessagePut_Count=0;
    		SystemInfo_TextArea.setText("");
    	}

    	//@-Debug DA΢��+
    	else if((event.getSource()==DA_Up))
    	{
    		Send_DA_Range =(float)(Send_DA_Range+0.001);
    	}
    	//@-Debug DA΢��-
    	else if((event.getSource()==DA_Down))
    	{
    		if(Send_DA_Range<0)
    		Send_DA_Range=0;
    		else
    		Send_DA_Range =(float)(Send_DA_Range-0.001);
    	}

    }



    /**���ڴ���
     *
     */
    private void Serise_Pro()
    {
    	int    get_index;
    	String Serise_Port;
    	int    Serise_Bound=19200;

    	//@1-���ڹ��ϱ�־��λ
    	Serise_Error_Flag=false;

    	//@2-��ô��ڶ˿ں�
    	get_index=OptionSet_SeriseSet_Port.getSelectionModel().getSelectedIndex();
    	Serise_Port=new String("COM"+(get_index+1));
    	//@3-��ô��ڲ�����
    	get_index=OptionSet_SeriseSet_Bound.getSelectionModel().getSelectedIndex();
    	switch(get_index)
    	{
    		case 0: Serise_Bound=9600; break;
    		case 1: Serise_Bound=19200; break;
    		case 2: Serise_Bound=115200; break;
    		default: break;
    	}

    	//System.out.println("p:"+Serise_Port+" b:"+Serise_Bound);

    	//@4-�жϴ���δ��-��
    	if(Serise_Main.isOpen==false)
    	{
			try {
				Serise_Main = new Serise(Serise_Port, Serise_Bound, "None");
			}catch (Exception e) {
				// TODO Auto-generated catch block
				Serise_Error_Flag=true;
				//e.printStackTrace();
			}
			if(Serise_Error_Flag==false)
			{
				Serise_Status=1;
				ScreensFramework.Show_Noti("Success", "���ڴ�!");
				ConnectionControl_Button.setText("ͨѶ������");
			}
			else if(Serise_Error_Flag==true)
			{
				Serise_Status=2;
				ScreensFramework.Show_Noti("Error", "�����ڴ����豸��˿ڲ���!\n���������������USB���ڵ���!");
				ConnectionControl_Button.setText("ͨѶ����");
			}
    	}
    	//@5-�жϴ����Ѵ�-�ر�
    	else if(Serise_Main.isOpen==true)
    	{
    		Serise_Status=0;
    		Serise_Main.close();
    		ScreensFramework.Show_Noti("Info", "���ڹر�!");
    		ConnectionControl_Button.setText("ͨѶδ����");
    	}
    }


    /**��Դ��ѹѡ�������
     *
     * @param event
     */
    @FXML
    public void PowerSel_Pro(MouseEvent event)
    {
    	//@-��Դ��ѹû���趨
    	if(Charging_PowerStarus==false)
    	{
	    	//@-������
	        if(event.getEventType()==MouseEvent.MOUSE_ENTERED)
	        {
	        	if((event.getSource()==Charging_Power110_Rect)||(event.getSource()==Charging_Power110_Label))
	        	{
	        		if(Charging_Power_Sel!=1)
	        		Charging_Power110_Rect.setFill(Color.rgb(0xff, 0x95, 0x33));
	        	}
	        	else if((event.getSource()==Charging_Power220_Rect)||(event.getSource()==Charging_Power220_Label))
	        	{
	        		if(Charging_Power_Sel!=2)
	        		Charging_Power220_Rect.setFill(Color.rgb(0xff, 0x95, 0x33));
	        	}
	        	else if((event.getSource()==Charging_Power260_Rect)||(event.getSource()==Charging_Power260_Label))
	        	{
	        		if(Charging_Power_Sel!=3)
	        		Charging_Power260_Rect.setFill(Color.rgb(0xff, 0x95, 0x33));
	        	}
	        }
	        //@-����뿪
	        else if(event.getEventType()==MouseEvent.MOUSE_EXITED)
	        {
	        	if((event.getSource()==Charging_Power110_Rect)||(event.getSource()==Charging_Power110_Label))
	        	{
	        		if(Charging_Power_Sel!=1)
	        		Charging_Power110_Rect.setFill(Color.rgb(0xcc, 0x62, 0x00));
	        	}
	        	else if((event.getSource()==Charging_Power220_Rect)||(event.getSource()==Charging_Power220_Label))
	        	{
	        		if(Charging_Power_Sel!=2)
	        		Charging_Power220_Rect.setFill(Color.rgb(0xcc, 0x62, 0x00));
	        	}
	        	else if((event.getSource()==Charging_Power260_Rect)||(event.getSource()==Charging_Power260_Label))
	        	{
	        		if(Charging_Power_Sel!=3)
	        		Charging_Power260_Rect.setFill(Color.rgb(0xcc, 0x62, 0x00));
	        	}
	        }
	        //@-�����
	        else if(event.getEventType()==MouseEvent.MOUSE_CLICKED)
	        {
	        	if((event.getSource()==Charging_Power110_Rect)||(event.getSource()==Charging_Power110_Label))
	        	{
	        		Charging_Power_Sel=1;
	        	}
	        	else if((event.getSource()==Charging_Power220_Rect)||(event.getSource()==Charging_Power220_Label))
	        	{
	        		Charging_Power_Sel=2;
	        	}
	        	else if((event.getSource()==Charging_Power260_Rect)||(event.getSource()==Charging_Power260_Label))
	        	{
	        		Charging_Power_Sel=3;
	        	}

	        	if(Charging_Power_Sel==1)
	        	{
	        		Charging_Power110_Rect.setFill(Color.rgb(0xa5, 0xff, 0x33));
	            	Charging_Power220_Rect.setFill(Color.rgb(0xcc, 0x62, 0x00));
	            	Charging_Power260_Rect.setFill(Color.rgb(0xcc, 0x62, 0x00));
	        	}
	        	else if(Charging_Power_Sel==2)
	        	{
	        		Charging_Power220_Rect.setFill(Color.rgb(0xa5, 0xff, 0x33));
	            	Charging_Power110_Rect.setFill(Color.rgb(0xcc, 0x62, 0x00));
	            	Charging_Power260_Rect.setFill(Color.rgb(0xcc, 0x62, 0x00));
	        	}
	        	else if(Charging_Power_Sel==3)
	        	{
	        		Charging_Power260_Rect.setFill(Color.rgb(0xa5, 0xff, 0x33));
	            	Charging_Power110_Rect.setFill(Color.rgb(0xcc, 0x62, 0x00));
	            	Charging_Power220_Rect.setFill(Color.rgb(0xcc, 0x62, 0x00));
	        	}
	        }
    	}
    }


    /**Ӧ������˳�
     *
     * @param event
     */
    @FXML
    public void SystemExit_Pro(MouseEvent event)
    {
        if(event.getEventType()==MouseEvent.MOUSE_CLICKED)
        {
        	ScreensFramework.cleanAndQuit();
        }
    }




	@Override
	public void setScreenParent(ScreensController screenPage) {
		// TODO Auto-generated method stub
		myController = screenPage;
	}

}
