package AODI_QC;
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



//import eu.hansolo.enzo.common.Section;
//import eu.hansolo.enzo.gauge.SimpleGauge;
//import eu.hansolo.enzo.gauge.SimpleGaugeBuilder;


import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.sun.javafx.perf.PerformanceTracker;
import javafx.animation.Animation;
import javafx.animation.Animation.Status;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.CacheHint;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.SwipeEvent;
import javafx.scene.input.TouchEvent;
import javafx.scene.input.TouchPoint;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;




/**
 *
 * @author DX
 *
 */
public class NodeView_Controller implements Initializable, ControlledScreen {

	//@1-������Ӧ�ó���ӿ�
	private ScreensController myController;

	//@-Ӧ�ò���root
	@FXML
	private AnchorPane Root;

    //@17-����
    @FXML
    private LineChart  Chart;
    //@18-����X��
    @FXML
    private NumberAxis Chart_Axis_X;
    //@19-����Y��
    @FXML
    private NumberAxis Chart_Axis_Y;

    //@13-������ѹ����ѡ��
    @FXML
    private CheckBox ScanVoltage_CurveSel;
    //@13-������������ѡ��
    @FXML
    private CheckBox ScanCurrent_CurveSel;
    //@13-�����¶�1����ѡ��
    @FXML
    private CheckBox ScanTemputer1_CurveSel;
    //@13-�����¶�2����ѡ��
    @FXML
    private CheckBox ScanTemputer2_CurveSel;
    //@13-�����¶�3����ѡ��
    @FXML
    private CheckBox ScanTemputer3_CurveSel;

	//@-�ڵ�����-����
	@FXML
	private Label NodeView_Titel;
	//@-�ڵ�����-��Ʒ���
	@FXML
	private Label NodeView_Pud_ID;
	//@-�ڵ�����-��Ʒ�ϻ�����ʱ��
	@FXML
	private Label NodeView_Pud_RunTime;
	//@-�ڵ�����-�ϻ���Ʒ����ʱ��
	@FXML
	private Label NodeView_Pud_Test_Scan_Time;
	//@-�ڵ�����-�ϻ���Ʒ-��������-��ѹ
	@FXML
	private Label NodeView_Pud_Test_ScanVoltage;
	//@-�ڵ�����-�ϻ���Ʒ-��������-����
	@FXML
	private Label NodeView_Pud_Test_ScanCurrent;
	//@-�ڵ�����-�ϻ���Ʒ-��������-�¶�1
	@FXML
	private Label NodeView_Pud_Test_ScanTemputer1;
	//@-�ڵ�����-�ϻ���Ʒ-��������-�¶�2
	@FXML
	private Label NodeView_Pud_Test_ScanTemputer2;
	//@-�ڵ�����-�ϻ���Ʒ-��������-�¶�3
	@FXML
	private Label NodeView_Pud_Test_ScanTemputer3;

	//@-�ڵ�����-�ϻ���Ʒ�ͺ�
	@FXML
	private Label NodeView_PudModel;

	//@-�ڵ�����-Ŀǰ�ɼ�����
	@FXML
	private Label NodeView_DataCount;


	//@-PCB����״̬
	@FXML
	private ImageView System_PCB_Status_ImageView;
	//@-ϵͳ��������״̬
	@FXML
	private ImageView System_NetWork_Status_ImageView;


	//@-����������
	@FXML
	private Button Back_Button;

	//@-�ֶ���ѯ
	@FXML
	private Button HandelCheck_Button;


    //-----------------------------------------------------

	//@26-ͼƬ��Դ-û������
	private Image Image_config_no;
	//@26-ͼƬ��Դ-������
	private Image Image_config_yes;
	//@26-ͼƬ��Դ-LED��
	private Image Image_state_ok;
	//@26-ͼƬ��Դ-LED��
	private Image Image_state_error;
	//@26-ͼƬ��Դ-LED��
	private Image Image_state_warning;
	//@26-ͼƬ��Դ-LED��
	private Image Image_state_ok_fang;
	//@26-ͼƬ��Դ-LED��
	private Image Image_state_error_fang;
	//@26-ͼƬ��Դ-LED��
	private Image Image_state_warning_fang;
	//@26-ͼƬ��Դ-target
	private Image Image_target;

	//@6-��ʾ���ݸ�ʽ
	public static java.text.NumberFormat  formater_data  =  java.text.DecimalFormat.getInstance();  //��ʾС����ʽ��

    //@-������ʾˢ��
	public static SimpleStringProperty DisplayProperty_Main = new SimpleStringProperty();
	//@-����仯Change1������
	private ChangeListener Changelisten1;
    //@-��������
	public static SimpleStringProperty DataProperty_Main = new SimpleStringProperty();
	//@-����仯Change2������
	private ChangeListener Changelisten2;

	//@-��ʾ��-������ѹ
	private float Display_ScanVoltage=0;
	private float Display_ScanVoltage_Copy=1;
	private boolean setId1 = false;
	//@-��ʾ��-��������
	private float Display_ScanCurrent=0;
	private float Display_ScanCurrent_Copy=1;
	private boolean setId2 = false;
	//@-��ʾ��-�����¶�1
	private float Display_ScanTemputer1=0;
	private float Display_ScanTemputer1_Copy=1;
	private boolean setId3 = false;
	//@-��ʾ��-�����¶�2
	private float Display_ScanTemputer2=0;
	private float Display_ScanTemputer2_Copy=1;
	private boolean setId4 = false;
	//@-��ʾ��-�����¶�3
	private float Display_ScanTemputer3=0;
	private float Display_ScanTemputer3_Copy=1;
	private boolean setId5 = false;

    //@-����ʱ����
    private Timeline Curve_Timeline;
    //@-���߶�����ʱ��
    private SequentialTransition Curve_Animation;

    //@-���������ʾ����
    private static final int MAX_DATA_POINTS = 200;

    //@-��������״̬
    public static boolean   CurveRun_Status=false;

    //@-��������
    private LineChart.Series<Number, Number>  ChartData_ScanVoltage;
    private LineChart.Series<Number, Number>  ChartData_ScanCurrent;
    private LineChart.Series<Number, Number>  ChartData_ScanTemperature1;
    private LineChart.Series<Number, Number>  ChartData_ScanTemperature2;
    private LineChart.Series<Number, Number>  ChartData_ScanTemperature3;

    //@-������ʾ��
    private int Curve_Num=5;

    //@-����������ʾ��־
    public static boolean   Curve_ScanVoltage_Dis = true;
    public static boolean   Curve_ScanCurrent_Dis = true;
    public static boolean   Curve_ScanTemperature1_Dis = true;
    public static boolean   Curve_ScanTemperature2_Dis = true;
    public static boolean   Curve_ScanTemperature3_Dis = false;

    //@-���������Ƴ���־
    public static boolean   Curve_ScanVoltage_Remove = false;
    public static boolean   Curve_ScanCurrent_Remove = false;
    public static boolean   Curve_ScanTemperature1_Remove =  false;
    public static boolean   Curve_ScanTemperature2_Remove =  false;
    public static boolean   Curve_ScanTemperature3_Remove =  true;

	//@-��������������
	public static AODI_Data[] All_AODI_Data = new AODI_Data[100000];   //1000������
	//@-�洢�ļ������ܴ洢��
	public static int Data_TotalLine=0;
	public static int Data_TotalLine_Copy=0;
	//@-�洢�ļ����ݵ�ǰ��¼��
	public static int Data_CurrentLine=0;
	//@-�洢���ݶ�ȡ����
	private static BufferedReader reader = null;
	//@-�洢����һ��
	private static String line;
	//@-��ʽ��
	private static String SaveData_Format ="\t\t";      //�洢�ļ������ݶ�֮��ķָ�

	//@-�ڵ������ʼ����־
	public static boolean Init_Flag = false;

	//@-ҳ���ѳ�ʼ����־
	public static boolean Init_Run_Flag = false;






	/**��¼�����ʼ��
	 *
	 */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    	Init_Run_Flag = true;

    	//@-���ݾ��ȸ�ʽ
		formater_data.setMaximumIntegerDigits(2);
		formater_data.setMinimumIntegerDigits(2);

    	//@1-ͼƬ��Դ��ʼ��
        Image_config_no = new Image(NodeView_Controller.class.getResourceAsStream("warning.png"));
        Image_config_yes = new Image(NodeView_Controller.class.getResourceAsStream("success.png"));
		Image_state_ok = new Image(NodeView_Controller.class.getResourceAsStream("status_green.png"));
		Image_state_error = new Image(NodeView_Controller.class.getResourceAsStream("status_red.png"));
		Image_state_warning = new Image(NodeView_Controller.class.getResourceAsStream("status_yellow.png"));

		Image_state_ok_fang = new Image(NodeView_Controller.class.getResourceAsStream("statusbar_message_light_green.png"));
		Image_state_error_fang = new Image(NodeView_Controller.class.getResourceAsStream("statusbar_message_light_red.png"));
		Image_state_warning_fang = new Image(NodeView_Controller.class.getResourceAsStream("statusbar_message_light_orange.png"));

		Image_target = new Image(NodeView_Controller.class.getResourceAsStream("target.png"));


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
//    	Chart_Axis_X.setTickLabelFill(Color.CHOCOLATE);
//    	Chart_Axis_Y.setTickLabelFill(Color.CHOCOLATE);

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
    	ChartData_ScanVoltage = new LineChart.Series<Number, Number>();
    	ChartData_ScanVoltage.setName("������ѹ");
    	ChartData_ScanCurrent = new LineChart.Series<Number, Number>();
    	ChartData_ScanCurrent.setName("��������");
    	ChartData_ScanTemperature1 =  new LineChart.Series<Number, Number>();
    	ChartData_ScanTemperature1.setName("�����¶�1");
    	ChartData_ScanTemperature2 =  new LineChart.Series<Number, Number>();
    	ChartData_ScanTemperature2.setName("�����¶�2");
    	ChartData_ScanTemperature3 =  new LineChart.Series<Number, Number>();
    	ChartData_ScanTemperature3.setName("�����¶�3");

		//@9-��������������
		Chart.getData().addAll(ChartData_ScanVoltage,ChartData_ScanCurrent,ChartData_ScanTemperature1,ChartData_ScanTemperature2,ChartData_ScanTemperature3);


    	//@3-������ʾˢ��
    	DisplayProperty_Main.addListener(Changelisten1 = new ChangeListener(){
			@Override
			public void changed(ObservableValue arg0, Object oldval, Object newval) {
				// TODO Auto-generated method stub

				Platform.runLater(new Runnable() {
					@Override
					public void run() {

						//@-��ʾϵͳ��������
						if(Net_Main.NetLink_Mode==1)
						System_NetWork_Status_ImageView.setImage(Image_state_error_fang);
						else if(Net_Main.NetLink_Mode==2)
						System_NetWork_Status_ImageView.setImage(Image_state_ok_fang);
						else if(Net_Main.NetLink_Mode==3)
						System_NetWork_Status_ImageView.setImage(Image_state_warning_fang);

						//@-��ʾPCB����״̬
						if(Net_Main_Receive.Net_PCB_State==true)
						{
							System_PCB_Status_ImageView.setImage(Image_state_ok_fang);
						}
						else if(Net_Main_Receive.Net_PCB_State==false)
						{
							System_PCB_Status_ImageView.setImage(Image_state_error_fang);
						}

						//@-NodeView-����
						NodeView_Titel.setText("�ϻ���"+(QC_Controller.View_Group+1)+"-�ڵ�"+QC_Controller.View_Node+"-����");
						//@-NodeView-��Ʒ���
						NodeView_Pud_ID.setText("��Ʒ���:"+ScreensFramework.DZFZ_Group[QC_Controller.View_Group].Group_DZFZ[(QC_Controller.View_Node-1)].Get_Pud_ID());

						//@-NodeView-�ϻ�����ʱ��
						int time = ScreensFramework.DZFZ_Group[QC_Controller.View_Group].Group_DZFZ[(QC_Controller.View_Node-1)].Get_Pud_RunTime();
						int hours = time / 3600;
						int minutes = (time % 3600) / 60;
						int seconds = (time % 3600) % 60;
						NodeView_Pud_RunTime.setText("�ϻ�����ʱ��:"+formater_data.format(hours)+":"+formater_data.format(minutes)+":"+formater_data.format(seconds));

						//@-NodeView-�ϻ�����ʱ��
						NodeView_Pud_Test_Scan_Time.setText("�ɼ�ʱ��:"+ScreensFramework.DZFZ_Group[QC_Controller.View_Group].Group_DZFZ[(QC_Controller.View_Node-1)].Get_Pud_Test_Scan_Time());

						//@-NodeView-Ŀǰ�ɼ�����
						NodeView_DataCount.setText("Ŀǰ�ɼ�����:"+Data_TotalLine_Copy);

						//@-NodeView-��Ʒ�ͺ�
						NodeView_PudModel.setText("��Ʒ�ͺ�:"+ScreensFramework.DZFZ_Group[QC_Controller.View_Group].Group_DZFZ[(QC_Controller.View_Node-1)].Get_Pud_Test_PudModel());

						//@-NodeView-�ϻ�����-��ѹ
						Display_ScanVoltage = ScreensFramework.DZFZ_Group[QC_Controller.View_Group].Group_DZFZ[(QC_Controller.View_Node-1)].Get_Pud_Test_ScanVoltage();
						if(Float.compare(Display_ScanVoltage_Copy, Display_ScanVoltage)!=0)
						{
							Display_ScanVoltage_Copy = Display_ScanVoltage;
							if(setId1==false)
							{
								setId1 = true;
								NodeView_Pud_Test_ScanVoltage.setId("INFO_White");
							}
							else if(setId1==true)
							{
								setId1 = false;
								NodeView_Pud_Test_ScanVoltage.setId("INFO_OK");
							}
							NodeView_Pud_Test_ScanVoltage.setText("�ɼ����ѹ:"+Display_ScanVoltage_Copy);
						}

						//@-NodeView-�ϻ�����-����
						Display_ScanCurrent = ScreensFramework.DZFZ_Group[QC_Controller.View_Group].Group_DZFZ[(QC_Controller.View_Node-1)].Get_Pud_Test_ScanCurrent();
						if(Float.compare(Display_ScanCurrent_Copy, Display_ScanCurrent)!=0)
						{
							Display_ScanCurrent_Copy = Display_ScanCurrent;
							if(setId2==false)
							{
								setId2 = true;
								NodeView_Pud_Test_ScanCurrent.setId("INFO_White");
							}
							else if(setId2==true)
							{
								setId2 = false;
								NodeView_Pud_Test_ScanCurrent.setId("INFO_OK");
							}
							NodeView_Pud_Test_ScanCurrent.setText("�ɼ������:"+Display_ScanCurrent_Copy);
						}

						//@-NodeView-�ϻ�����-�¶�1
						Display_ScanTemputer1 = ScreensFramework.DZFZ_Group[QC_Controller.View_Group].Group_DZFZ[(QC_Controller.View_Node-1)].Get_Pud_Test_ScanTemputer1();
						if(Float.compare(Display_ScanTemputer1_Copy, Display_ScanTemputer1)!=0)
						{
							Display_ScanTemputer1_Copy = Display_ScanTemputer1;
							if(setId3==false)
							{
								setId3 = true;
								NodeView_Pud_Test_ScanTemputer1.setId("INFO_White");
							}
							else if(setId3==true)
							{
								setId3 = false;
								NodeView_Pud_Test_ScanTemputer1.setId("INFO_OK");
							}
							NodeView_Pud_Test_ScanTemputer1.setText("�ɼ����¶�1:"+Display_ScanTemputer1_Copy);
						}
						//@-NodeView-�ϻ�����-�¶�2
						Display_ScanTemputer2 = ScreensFramework.DZFZ_Group[QC_Controller.View_Group].Group_DZFZ[(QC_Controller.View_Node-1)].Get_Pud_Test_ScanTemputer2();
						if(Float.compare(Display_ScanTemputer2_Copy, Display_ScanTemputer2)!=0)
						{
							Display_ScanTemputer2_Copy = Display_ScanTemputer2;
							if(setId4==false)
							{
								setId4 = true;
								NodeView_Pud_Test_ScanTemputer2.setId("INFO_White");
							}
							else if(setId4==true)
							{
								setId4 = false;
								NodeView_Pud_Test_ScanTemputer2.setId("INFO_OK");
							}
							NodeView_Pud_Test_ScanTemputer2.setText("�ɼ����¶�2:"+Display_ScanTemputer2_Copy);
						}
						//@-NodeView-�ϻ�����-�¶�3
						Display_ScanTemputer3 = ScreensFramework.DZFZ_Group[QC_Controller.View_Group].Group_DZFZ[(QC_Controller.View_Node-1)].Get_Pud_Test_ScanTemputer3();
						if(Float.compare(Display_ScanTemputer3_Copy, Display_ScanTemputer3)!=0)
						{
							Display_ScanTemputer3_Copy = Display_ScanTemputer3;
							if(setId5==false)
							{
								setId5 = true;
								NodeView_Pud_Test_ScanTemputer3.setId("INFO_White");
							}
							else if(setId5==true)
							{
								setId5 = false;
								NodeView_Pud_Test_ScanTemputer3.setId("INFO_OK");
							}
							NodeView_Pud_Test_ScanTemputer3.setText("�ɼ����¶�3:"+Display_ScanTemputer3_Copy);
						}

					}
				});

			}
    	});

    	//@4-������Ϣ������
    	DataProperty_Main.addListener(Changelisten2=new ChangeListener(){
			@Override
			public void changed(ObservableValue arg0, Object oldval, Object newval) {
				// TODO Auto-generated method stub
				if(newval.toString().equals(new String("init")))
				{
					Init_Parameter();
				}
				else if(newval.toString().equals(new String("ok")))
				{

				}
				DataProperty_Main.set("Change");
			}
    	});

    	//@11-��������ˢ��
    	prepareTimeline();

    }

    /**
     *
     */
    public void Init_Parameter()
    {
    	//@-���ݼ�¼����ʼ��
		Data_TotalLine = 0;
		Data_TotalLine_Copy = 0;

		//@-�洢�ļ����ݵ�ǰ��¼��
		Data_CurrentLine=0;

		//@-�����������
		ChartData_ScanVoltage.getData().clear();
    	ChartData_ScanCurrent.getData().clear();
    	ChartData_ScanTemperature1.getData().clear();
    	ChartData_ScanTemperature2.getData().clear();
    	ChartData_ScanTemperature3.getData().clear();

//    	Curve_Num=5;
//    	Curve_ScanVoltage_Dis=true;
//    	Curve_ScanCurrent_Dis=true;
//    	Curve_ScanTemperature1_Dis=true;
//    	Curve_ScanTemperature2_Dis=true;
//    	Curve_ScanTemperature3_Dis=true;
//
//    	Curve_ScanVoltage_Remove=false;
//    	Curve_ScanCurrent_Remove=false;
//    	Curve_ScanTemperature1_Remove=false;
//    	Curve_ScanTemperature2_Remove=false;
//    	Curve_ScanTemperature3_Remove=false;
//
//    	ScanVoltage_CurveSel.setSelected(true);
//    	ScanCurrent_CurveSel.setSelected(true);
//    	ScanTemputer1_CurveSel.setSelected(true);
//    	ScanTemputer2_CurveSel.setSelected(true);
//    	ScanTemputer3_CurveSel.setSelected(true);
//
//		//@9-��������������
//    	Chart.getData().add(ChartData_ScanVoltage);
//    	Chart.getData().add(ChartData_ScanCurrent);
//    	Chart.getData().add(ChartData_ScanTemperature1);
//    	Chart.getData().add(ChartData_ScanTemperature2);
//    	Chart.getData().add(ChartData_ScanTemperature3);

        Chart_Axis_X.setLowerBound(0);
        Chart_Axis_X.setUpperBound(200);

        //@7-������������״̬
        curve_updata_switch();

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
                new KeyFrame(Duration.millis(50), new EventHandler<ActionEvent>() {
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
        //curve_updata_switch();

    }

    /**���߸��¿�ʼ
     *
     */
    public void curve_updata_switch()
    {
    	if(CurveRun_Status==false)
    	{
    		CurveRun_Status=true;
    		Curve_Animation.play();
    	}
    	else if(CurveRun_Status==true)
    	{
    		CurveRun_Status=false;
    		Curve_Animation.stop();
    	}
    }



    /**���������������
     *
     */
    synchronized private void addDataToSeries()
    {


    	//System.out.println("Thread:"+Thread.currentThread().getName());


    	//@-��ȡ�ڵ�����Դ
		//@DX1-�������ݵ��ļ���-ÿ����Ʒ�������ݴ洢�ļ�-|AODI_��ƷID_Group(Save_Data_Group)Node(Save_Data_Node).aodi_save|
    	String Save_Data_FileName = new String("AODI_"+ScreensFramework.DZFZ_Group[QC_Controller.View_Group].Group_DZFZ[(QC_Controller.View_Node-1)].Get_Pud_ID()+"_"+
    											ScreensFramework.DZFZ_Group[QC_Controller.View_Group].Group_DZFZ[(QC_Controller.View_Node-1)].Get_Pud_Test_PudModel()+
    										   "_Group"+(QC_Controller.View_Group+1)+"Node"+(QC_Controller.View_Node)+".aodi_save");

        //@-�жϴ洢�ļ��Ƿ����
		File f = new File(ScreensFramework.MAIN_SaveData_TodayPath+Save_Data_FileName);
		if(f.exists())
		{
			//@-���ڴ洢�ļ�,��ȡ�ļ�-��ȡ�����ܴ洢��
	    	try {
				Data_TotalLine=total_line_count(ScreensFramework.MAIN_SaveData_TodayPath+Save_Data_FileName);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	    	//@-�����ݴ洢�������仯
	    	if(Data_TotalLine_Copy!=Data_TotalLine)
	    	{
		    	//@1-���ؼ�¼����
				try {
					reader = new BufferedReader(new FileReader(ScreensFramework.MAIN_SaveData_TodayPath+Save_Data_FileName));
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				//@2-����ԭ������
				for(int  i=0;i<Data_TotalLine_Copy;i++)
				{
					try {
						line = reader.readLine();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

	    		//@3-���´洢��
	    		Data_TotalLine_Copy=Data_TotalLine;

				//@4-��ȡÿ�м�¼
				//Data_CurrentLine=0;
		    	while(Data_CurrentLine<Data_TotalLine)
		    	{
		    		if(CurveRun_Status==true)
		    		{
			    		try {
							line = reader.readLine();

							All_AODI_Data[Data_CurrentLine]=(AODI_Data)getRecordDataFromString(line);

					    	//@-������ѹ
					    	ChartData_ScanVoltage.getData().add(new LineChart.Data(Data_CurrentLine, Float.valueOf(All_AODI_Data[Data_CurrentLine].get_Data_Voltage_Str())));
					    	//@-��������
					    	ChartData_ScanCurrent.getData().add(new LineChart.Data(Data_CurrentLine, Float.valueOf(All_AODI_Data[Data_CurrentLine].get_Data_Current_Str())));
					    	//@-�����¶�1
					    	ChartData_ScanTemperature1.getData().add(new LineChart.Data(Data_CurrentLine, Float.valueOf(All_AODI_Data[Data_CurrentLine].get_Data_Temperature1_Str())));
					    	//@-�����¶�2
					    	ChartData_ScanTemperature2.getData().add(new LineChart.Data(Data_CurrentLine, Float.valueOf(All_AODI_Data[Data_CurrentLine].get_Data_Temperature2_Str())));
					    	//@-�����¶�3
					    	ChartData_ScanTemperature3.getData().add(new LineChart.Data(Data_CurrentLine, Float.valueOf(All_AODI_Data[Data_CurrentLine].get_Data_Temperature3_Str())));

				    		//System.out.println("flash!"+All_AODI_Data[Data_CurrentLine].get_Data_Current_Str());

						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			    		Data_CurrentLine=Data_CurrentLine+1;

			    		if(Data_CurrentLine > MAX_DATA_POINTS)
			    		{
			    			ChartData_ScanVoltage.getData().remove(0);
			    			ChartData_ScanCurrent.getData().remove(0);
			    			ChartData_ScanTemperature1.getData().remove(0);
			    			ChartData_ScanTemperature2.getData().remove(0);
			    			ChartData_ScanTemperature3.getData().remove(0);

					        //@19-���߸��±߽���ʾ
					        Chart_Axis_X.setLowerBound(Data_CurrentLine-MAX_DATA_POINTS);
					        Chart_Axis_X.setUpperBound(Data_CurrentLine-1);
			    		}
		    		}
		    		else if(CurveRun_Status==false)
		    		{
		    			break;
		    		}
		    	}


//		    	if(Data_TotalLine<200)
//		    	{
//			    	//@-��������
//			    	for(int i=0;i<Data_TotalLine;i++)
//			    	{
//				    	//@-������ѹ
//				    	ChartData_ScanVoltage.getData().add(new LineChart.Data(i, Float.valueOf(All_AODI_Data[i].get_Data_Voltage_Str())));
//				    	//@-��������
//				    	ChartData_ScanCurrent.getData().add(new LineChart.Data(i, Float.valueOf(All_AODI_Data[i].get_Data_Current_Str())));
//				    	//@-�����¶�1
//				    	ChartData_ScanTemperature1.getData().add(new LineChart.Data(i, Float.valueOf(All_AODI_Data[i].get_Data_Temperature1_Str())));
//				    	//@-�����¶�2
//				    	ChartData_ScanTemperature2.getData().add(new LineChart.Data(i, Float.valueOf(All_AODI_Data[i].get_Data_Temperature2_Str())));
//				    	//@-�����¶�3
//				    	ChartData_ScanTemperature3.getData().add(new LineChart.Data(i, Float.valueOf(All_AODI_Data[i].get_Data_Temperature3_Str())));
//			    	}
//		    	}

	    	}

		}


    	//System.out.println(""+Data_TotalLine);

    	//@-������ѹ
    	//ChartData_ScanVoltage.getData().add(new LineChart.Data(xSeriesData++, 0));
    }


    /**
     * ͨ��һ���ļ���������һ�� AODI_Data ����
     * @param line
     * @return
     */
    private AODI_Data getRecordDataFromString(String line) {


        String[] parts = line.split(SaveData_Format);  // ��ȡ���ָ�����������

        return new AODI_Data(new String(parts[0]), new String(parts[1]), new String(parts[2]), new String(parts[3]),new String(parts[4]),
        	   new String(parts[5]),new String(parts[6]),new String(parts[7]),new String(parts[8]),new String(parts[9]),new String(parts[10]));

    }


    /**
     * ����BufferedInputStream��ʽ��ȡ�ļ�����
     *
     * @param filename
     * @return
     * @throws IOException
     */
    public int total_line_count(String filename) throws IOException {
        InputStream is = new BufferedInputStream(new FileInputStream(filename));
        byte[] c = new byte[1024];
        int count = 0;
        int readChars = 0;
        while ((readChars = is.read(c)) != -1) {
            for (int i = 0; i < readChars; ++i) {
                if (c[i] == '\n')
                    ++count;
            }
        }
        is.close();
        return count;
    }



    /**����������
     *
     * @param event
     */
    @FXML
    public void Button_Pro(ActionEvent event)
    {
		//@1-�����ء���ť
		if(event.getSource()==Back_Button)
		{
	        //@7-������������״̬
	        curve_updata_switch();
			ScreensFramework.PageChange.set("main");
		}
		//@2-"�ֶ���ѯ"��ť
		else if(event.getSource()==HandelCheck_Button)
		{
//			Display_ScanTemputer3 = ScreensFramework.DZFZ_Group[QC_Controller.View_Group].Group_DZFZ[(QC_Controller.View_Node-1)].Get_Pud_Test_ScanTemputer3();
			QC_DisplayTimer.CAN_ID_Index_Copy = (QC_Controller.View_Group*10)+QC_Controller.View_Node-1;
		}
		//@3-�ɼ���ѹ����
		else if(event.getSource()==ScanVoltage_CurveSel)
		{
			Curve_Display_Pro(1);
		}
		//@4-�ɼ���������
		else if(event.getSource()==ScanCurrent_CurveSel)
		{
			Curve_Display_Pro(2);
		}
		//@5-�ɼ��¶�1����
		else if(event.getSource()==ScanTemputer1_CurveSel)
		{
			Curve_Display_Pro(3);
		}
		//@6-�ɼ��¶�2����
		else if(event.getSource()==ScanTemputer2_CurveSel)
		{
			Curve_Display_Pro(4);
		}
		//@7-�ɼ��¶�3����
		else if(event.getSource()==ScanTemputer3_CurveSel)
		{
			Curve_Display_Pro(5);
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
    	    //@1-������ѹ
    		case 1:
    			   if(ScanVoltage_CurveSel.isSelected()==true)
    			   {
    				   Curve_Num=Curve_Num+1;
    				   if(Curve_Num>5)
    				   Curve_Num=5;
    				   Curve_ScanVoltage_Dis=true;
    				   //@-�����Ƴ���ʶ�ж��Ƿ����
    				   if(Curve_ScanVoltage_Remove==true)
    				   {
    					   Curve_ScanVoltage_Remove=false;
    					   Chart.getData().add(ChartData_ScanVoltage);
    				   }
    			   }
    			   else if(ScanVoltage_CurveSel.isSelected()==false)
    			   {
    				   if(Curve_Display_Check()==false)
    				   {
    					   ScanVoltage_CurveSel.setSelected(true);
    					   Curve_ScanVoltage_Dis=true;
        				   //@-�����Ƴ���ʶ�ж��Ƿ����
        				   if(Curve_ScanVoltage_Remove==true)
        				   {
        					   Curve_ScanVoltage_Remove=false;
        					   Chart.getData().add(ChartData_ScanVoltage);
        				   }

    				   }
    				   else if(Curve_Display_Check()==true)
    				   {
    					   ScanVoltage_CurveSel.setSelected(false);
    					   Curve_ScanVoltage_Dis=false;
    					   Curve_Num=Curve_Num-1;
    					   //@�Ƴ�������
    					   Curve_ScanVoltage_Remove=true;
    					   Chart.getData().remove(ChartData_ScanVoltage);
    				   }
    			   }
    			   break;
    		//@2-��������
    		case 2:
    			   if(ScanCurrent_CurveSel.isSelected()==true)
    			   {
    				   Curve_Num=Curve_Num+1;
    				   Curve_ScanCurrent_Dis=true;
    				   //@-�����Ƴ���ʶ�ж��Ƿ����
    				   if(Curve_ScanCurrent_Remove==true)
    				   {
    					   Curve_ScanCurrent_Remove=false;
    					   Chart.getData().add(ChartData_ScanCurrent);
    				   }
    			   }
    			   else if(ScanCurrent_CurveSel.isSelected()==false)
    			   {
    				   if(Curve_Display_Check()==false)
    				   {
    					   ScanCurrent_CurveSel.setSelected(true);
    					   Curve_ScanCurrent_Dis=true;
        				   //@-�����Ƴ���ʶ�ж��Ƿ����
        				   if(Curve_ScanCurrent_Remove==true)
        				   {
        					   Curve_ScanCurrent_Remove=false;
        					   Chart.getData().add(ChartData_ScanCurrent);
        				   }

    				   }
    				   else if(Curve_Display_Check()==true)
    				   {
    					   ScanCurrent_CurveSel.setSelected(false);
    					   Curve_ScanCurrent_Dis=false;
    					   Curve_Num=Curve_Num-1;
    					   //@�Ƴ�������
    					   Curve_ScanCurrent_Remove=true;
    					   Chart.getData().remove(ChartData_ScanCurrent);
    				   }
    			   }
    			   break;
    	    //@3-�����¶�1
    		case 3:
    			   if(ScanTemputer1_CurveSel.isSelected()==true)
    			   {
    				   Curve_Num=Curve_Num+1;
    				   Curve_ScanTemperature1_Dis=true;
    				   //@-�����Ƴ���ʶ�ж��Ƿ����
    				   if(Curve_ScanTemperature1_Remove==true)
    				   {
    					   Curve_ScanTemperature1_Remove=false;
    					   Chart.getData().add(ChartData_ScanTemperature1);
    				   }
    			   }
    			   else if(ScanTemputer1_CurveSel.isSelected()==false)
    			   {
    				   if(Curve_Display_Check()==false)
    				   {
    					   ScanTemputer1_CurveSel.setSelected(true);
    					   Curve_ScanTemperature1_Dis=true;
        				   //@-�����Ƴ���ʶ�ж��Ƿ����
        				   if(Curve_ScanTemperature1_Remove==true)
        				   {
        					   Curve_ScanTemperature1_Remove=false;
        					   Chart.getData().add(ChartData_ScanTemperature1);
        				   }

    				   }
    				   else if(Curve_Display_Check()==true)
    				   {
    					   ScanTemputer1_CurveSel.setSelected(false);
    					   Curve_ScanTemperature1_Dis=false;
    					   Curve_Num=Curve_Num-1;
    					   //@�Ƴ�������
    					   Curve_ScanTemperature1_Remove=true;
    					   Chart.getData().remove(ChartData_ScanTemperature1);
    				   }
    			   }
    			   break;
    	    //@4-�����¶�2
    		case 4:
    			   if(ScanTemputer2_CurveSel.isSelected()==true)
    			   {
    				   Curve_Num=Curve_Num+1;
    				   Curve_ScanTemperature2_Dis=true;
    				   //@-�����Ƴ���ʶ�ж��Ƿ����
    				   if(Curve_ScanTemperature2_Remove==true)
    				   {
    					   Curve_ScanTemperature2_Remove=false;
    					   Chart.getData().add(ChartData_ScanTemperature2);
    				   }
    			   }
    			   else if(ScanTemputer2_CurveSel.isSelected()==false)
    			   {
    				   if(Curve_Display_Check()==false)
    				   {
    					   ScanTemputer2_CurveSel.setSelected(true);
    					   Curve_ScanTemperature2_Dis=true;
        				   //@-�����Ƴ���ʶ�ж��Ƿ����
        				   if(Curve_ScanTemperature2_Remove==true)
        				   {
        					   Curve_ScanTemperature2_Remove=false;
        					   Chart.getData().add(ChartData_ScanTemperature2);
        				   }

    				   }
    				   else if(Curve_Display_Check()==true)
    				   {
    					   ScanTemputer2_CurveSel.setSelected(false);
    					   Curve_ScanTemperature2_Dis=false;
    					   Curve_Num=Curve_Num-1;
    					   //@�Ƴ�������
    					   Curve_ScanTemperature2_Remove=true;
    					   Chart.getData().remove(ChartData_ScanTemperature2);
    				   }
    			   }
    			   break;
    	    //@5-�����¶�3
    		case 5:
    			   if(ScanTemputer3_CurveSel.isSelected()==true)
    			   {
    				   Curve_Num=Curve_Num+1;
    				   Curve_ScanTemperature3_Dis=true;
    				   //@-�����Ƴ���ʶ�ж��Ƿ����
    				   if(Curve_ScanTemperature3_Remove==true)
    				   {
    					   Curve_ScanTemperature3_Remove=false;
    					   Chart.getData().add(ChartData_ScanTemperature3);
    				   }
    			   }
    			   else if(ScanTemputer3_CurveSel.isSelected()==false)
    			   {
    				   if(Curve_Display_Check()==false)
    				   {
    					   ScanTemputer3_CurveSel.setSelected(true);
    					   Curve_ScanTemperature3_Dis=true;
        				   //@-�����Ƴ���ʶ�ж��Ƿ����
        				   if(Curve_ScanTemperature3_Remove==true)
        				   {
        					   Curve_ScanTemperature3_Remove=false;
        					   Chart.getData().add(ChartData_ScanTemperature3);
        				   }

    				   }
    				   else if(Curve_Display_Check()==true)
    				   {
    					   ScanTemputer3_CurveSel.setSelected(false);
    					   Curve_ScanTemperature3_Dis=false;
    					   Curve_Num=Curve_Num-1;
    					   //@�Ƴ�������
    					   Curve_ScanTemperature3_Remove=true;
    					   Chart.getData().remove(ChartData_ScanTemperature3);
    				   }
    			   }
    			   break;
    	     default: break;

    	}

    	//@-Debug
    	//System.out.println("C:"+Curve_Num);
    }


    /**���������ʾ����
     *
     * @return
     */
    private boolean Curve_Display_Check()
    {
    	//�������һ��������ʾ
    	if(Curve_Num>1)
    	{
    		return true;
    	}
    	else
    	{
    		return false;
    	}
    }


    /**TextField������
     *
     * @param max_Lengh
     * @return
     */
    private EventHandler<KeyEvent> numeric_Validation(final Integer max_Lengh)
    {
        return new EventHandler<KeyEvent>()
        {
            @Override
            public void handle(KeyEvent e)
            {
                TextField txt_TextField = (TextField) e.getSource();

                //System.out.println("key");

                if (txt_TextField.getText().length() >= max_Lengh)
                {
                    e.consume();
                }
                if(e.getCharacter().matches("[0-9]"))
                {
                    if(txt_TextField.getText().contains(".") && e.getCharacter().matches("[.]"))
                    {
                        e.consume();
                    }
                    else if(txt_TextField.getText().length() == 0 && e.getCharacter().matches("[.]"))
                    {
                        e.consume();
                    }
                }
                else
                {
                    e.consume();
                }
            }
        };
    }

    /**Ӧ������˳�
     *
     * @param event
     */
    @FXML
    public void AppExit_Pro(MouseEvent event)
    {
        if(event.getEventType()==MouseEvent.MOUSE_CLICKED)
        {
        	ScreensFramework.cleanAndQuit();
        }
    }


    /**������Ӧ�ó���ӿ�
     *
     */
	@Override
	public void setScreenParent(ScreensController screenPage) {
		// TODO Auto-generated method stub
		myController = screenPage;
	}

}
