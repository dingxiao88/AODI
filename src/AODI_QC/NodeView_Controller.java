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

	//@1-传递主应用程序接口
	private ScreensController myController;

	//@-应用布局root
	@FXML
	private AnchorPane Root;

    //@17-曲线
    @FXML
    private LineChart  Chart;
    //@18-曲线X轴
    @FXML
    private NumberAxis Chart_Axis_X;
    //@19-曲线Y轴
    @FXML
    private NumberAxis Chart_Axis_Y;

    //@13-采样电压曲线选择
    @FXML
    private CheckBox ScanVoltage_CurveSel;
    //@13-采样电流曲线选择
    @FXML
    private CheckBox ScanCurrent_CurveSel;
    //@13-采样温度1曲线选择
    @FXML
    private CheckBox ScanTemputer1_CurveSel;
    //@13-采样温度2曲线选择
    @FXML
    private CheckBox ScanTemputer2_CurveSel;
    //@13-采样温度3曲线选择
    @FXML
    private CheckBox ScanTemputer3_CurveSel;

	//@-节点详情-标题
	@FXML
	private Label NodeView_Titel;
	//@-节点详情-产品编号
	@FXML
	private Label NodeView_Pud_ID;
	//@-节点详情-产品老化运行时间
	@FXML
	private Label NodeView_Pud_RunTime;
	//@-节点详情-老化产品采样时间
	@FXML
	private Label NodeView_Pud_Test_Scan_Time;
	//@-节点详情-老化产品-采样参数-电压
	@FXML
	private Label NodeView_Pud_Test_ScanVoltage;
	//@-节点详情-老化产品-采样参数-电流
	@FXML
	private Label NodeView_Pud_Test_ScanCurrent;
	//@-节点详情-老化产品-采样参数-温度1
	@FXML
	private Label NodeView_Pud_Test_ScanTemputer1;
	//@-节点详情-老化产品-采样参数-温度2
	@FXML
	private Label NodeView_Pud_Test_ScanTemputer2;
	//@-节点详情-老化产品-采样参数-温度3
	@FXML
	private Label NodeView_Pud_Test_ScanTemputer3;

	//@-节点详情-老化产品型号
	@FXML
	private Label NodeView_PudModel;

	//@-节点详情-目前采集点数
	@FXML
	private Label NodeView_DataCount;


	//@-PCB连接状态
	@FXML
	private ImageView System_PCB_Status_ImageView;
	//@-系统网络连接状态
	@FXML
	private ImageView System_NetWork_Status_ImageView;


	//@-返回主界面
	@FXML
	private Button Back_Button;

	//@-手动查询
	@FXML
	private Button HandelCheck_Button;


    //-----------------------------------------------------

	//@26-图片资源-没有配置
	private Image Image_config_no;
	//@26-图片资源-已配置
	private Image Image_config_yes;
	//@26-图片资源-LED绿
	private Image Image_state_ok;
	//@26-图片资源-LED红
	private Image Image_state_error;
	//@26-图片资源-LED黄
	private Image Image_state_warning;
	//@26-图片资源-LED绿
	private Image Image_state_ok_fang;
	//@26-图片资源-LED红
	private Image Image_state_error_fang;
	//@26-图片资源-LED黄
	private Image Image_state_warning_fang;
	//@26-图片资源-target
	private Image Image_target;

	//@6-显示数据格式
	public static java.text.NumberFormat  formater_data  =  java.text.DecimalFormat.getInstance();  //显示小数格式化

    //@-界面显示刷新
	public static SimpleStringProperty DisplayProperty_Main = new SimpleStringProperty();
	//@-界面变化Change1监听器
	private ChangeListener Changelisten1;
    //@-数据属性
	public static SimpleStringProperty DataProperty_Main = new SimpleStringProperty();
	//@-界面变化Change2监听器
	private ChangeListener Changelisten2;

	//@-显示用-采样电压
	private float Display_ScanVoltage=0;
	private float Display_ScanVoltage_Copy=1;
	private boolean setId1 = false;
	//@-显示用-采样电流
	private float Display_ScanCurrent=0;
	private float Display_ScanCurrent_Copy=1;
	private boolean setId2 = false;
	//@-显示用-采样温度1
	private float Display_ScanTemputer1=0;
	private float Display_ScanTemputer1_Copy=1;
	private boolean setId3 = false;
	//@-显示用-采样温度2
	private float Display_ScanTemputer2=0;
	private float Display_ScanTemputer2_Copy=1;
	private boolean setId4 = false;
	//@-显示用-采样温度3
	private float Display_ScanTemputer3=0;
	private float Display_ScanTemputer3_Copy=1;
	private boolean setId5 = false;

    //@-曲线时间线
    private Timeline Curve_Timeline;
    //@-曲线动画定时器
    private SequentialTransition Curve_Animation;

    //@-曲线最大显示点数
    private static final int MAX_DATA_POINTS = 200;

    //@-曲线运行状态
    public static boolean   CurveRun_Status=false;

    //@-曲线数据
    private LineChart.Series<Number, Number>  ChartData_ScanVoltage;
    private LineChart.Series<Number, Number>  ChartData_ScanCurrent;
    private LineChart.Series<Number, Number>  ChartData_ScanTemperature1;
    private LineChart.Series<Number, Number>  ChartData_ScanTemperature2;
    private LineChart.Series<Number, Number>  ChartData_ScanTemperature3;

    //@-曲线显示数
    private int Curve_Num=5;

    //@-各条曲线显示标志
    public static boolean   Curve_ScanVoltage_Dis = true;
    public static boolean   Curve_ScanCurrent_Dis = true;
    public static boolean   Curve_ScanTemperature1_Dis = true;
    public static boolean   Curve_ScanTemperature2_Dis = true;
    public static boolean   Curve_ScanTemperature3_Dis = false;

    //@-各条曲线移除标志
    public static boolean   Curve_ScanVoltage_Remove = false;
    public static boolean   Curve_ScanCurrent_Remove = false;
    public static boolean   Curve_ScanTemperature1_Remove =  false;
    public static boolean   Curve_ScanTemperature2_Remove =  false;
    public static boolean   Curve_ScanTemperature3_Remove =  true;

	//@-曲线所有数据项
	public static AODI_Data[] All_AODI_Data = new AODI_Data[100000];   //1000条数据
	//@-存储文件数据总存储数
	public static int Data_TotalLine=0;
	public static int Data_TotalLine_Copy=0;
	//@-存储文件数据当前记录数
	public static int Data_CurrentLine=0;
	//@-存储数据读取缓冲
	private static BufferedReader reader = null;
	//@-存储数据一行
	private static String line;
	//@-格式符
	private static String SaveData_Format ="\t\t";      //存储文件中数据段之间的分隔

	//@-节点详情初始化标志
	public static boolean Init_Flag = false;

	//@-页面已初始化标志
	public static boolean Init_Run_Flag = false;






	/**登录界面初始化
	 *
	 */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    	Init_Run_Flag = true;

    	//@-数据精度格式
		formater_data.setMaximumIntegerDigits(2);
		formater_data.setMinimumIntegerDigits(2);

    	//@1-图片资源初始化
        Image_config_no = new Image(NodeView_Controller.class.getResourceAsStream("warning.png"));
        Image_config_yes = new Image(NodeView_Controller.class.getResourceAsStream("success.png"));
		Image_state_ok = new Image(NodeView_Controller.class.getResourceAsStream("status_green.png"));
		Image_state_error = new Image(NodeView_Controller.class.getResourceAsStream("status_red.png"));
		Image_state_warning = new Image(NodeView_Controller.class.getResourceAsStream("status_yellow.png"));

		Image_state_ok_fang = new Image(NodeView_Controller.class.getResourceAsStream("statusbar_message_light_green.png"));
		Image_state_error_fang = new Image(NodeView_Controller.class.getResourceAsStream("statusbar_message_light_red.png"));
		Image_state_warning_fang = new Image(NodeView_Controller.class.getResourceAsStream("statusbar_message_light_orange.png"));

		Image_target = new Image(NodeView_Controller.class.getResourceAsStream("target.png"));


    	//@5-曲线基础设置
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

		//@6-创建曲线数据
    	ChartData_ScanVoltage = new LineChart.Series<Number, Number>();
    	ChartData_ScanVoltage.setName("采样电压");
    	ChartData_ScanCurrent = new LineChart.Series<Number, Number>();
    	ChartData_ScanCurrent.setName("采样电流");
    	ChartData_ScanTemperature1 =  new LineChart.Series<Number, Number>();
    	ChartData_ScanTemperature1.setName("采样温度1");
    	ChartData_ScanTemperature2 =  new LineChart.Series<Number, Number>();
    	ChartData_ScanTemperature2.setName("采样温度2");
    	ChartData_ScanTemperature3 =  new LineChart.Series<Number, Number>();
    	ChartData_ScanTemperature3.setName("采样温度3");

		//@9-数据链加入曲线
		Chart.getData().addAll(ChartData_ScanVoltage,ChartData_ScanCurrent,ChartData_ScanTemperature1,ChartData_ScanTemperature2,ChartData_ScanTemperature3);


    	//@3-界面显示刷新
    	DisplayProperty_Main.addListener(Changelisten1 = new ChangeListener(){
			@Override
			public void changed(ObservableValue arg0, Object oldval, Object newval) {
				// TODO Auto-generated method stub

				Platform.runLater(new Runnable() {
					@Override
					public void run() {

						//@-显示系统网络连接
						if(Net_Main.NetLink_Mode==1)
						System_NetWork_Status_ImageView.setImage(Image_state_error_fang);
						else if(Net_Main.NetLink_Mode==2)
						System_NetWork_Status_ImageView.setImage(Image_state_ok_fang);
						else if(Net_Main.NetLink_Mode==3)
						System_NetWork_Status_ImageView.setImage(Image_state_warning_fang);

						//@-显示PCB连接状态
						if(Net_Main_Receive.Net_PCB_State==true)
						{
							System_PCB_Status_ImageView.setImage(Image_state_ok_fang);
						}
						else if(Net_Main_Receive.Net_PCB_State==false)
						{
							System_PCB_Status_ImageView.setImage(Image_state_error_fang);
						}

						//@-NodeView-标题
						NodeView_Titel.setText("老化组"+(QC_Controller.View_Group+1)+"-节点"+QC_Controller.View_Node+"-详情");
						//@-NodeView-产品编号
						NodeView_Pud_ID.setText("产品编号:"+ScreensFramework.DZFZ_Group[QC_Controller.View_Group].Group_DZFZ[(QC_Controller.View_Node-1)].Get_Pud_ID());

						//@-NodeView-老化运行时间
						int time = ScreensFramework.DZFZ_Group[QC_Controller.View_Group].Group_DZFZ[(QC_Controller.View_Node-1)].Get_Pud_RunTime();
						int hours = time / 3600;
						int minutes = (time % 3600) / 60;
						int seconds = (time % 3600) % 60;
						NodeView_Pud_RunTime.setText("老化运行时间:"+formater_data.format(hours)+":"+formater_data.format(minutes)+":"+formater_data.format(seconds));

						//@-NodeView-老化采样时间
						NodeView_Pud_Test_Scan_Time.setText("采集时间:"+ScreensFramework.DZFZ_Group[QC_Controller.View_Group].Group_DZFZ[(QC_Controller.View_Node-1)].Get_Pud_Test_Scan_Time());

						//@-NodeView-目前采集点数
						NodeView_DataCount.setText("目前采集点数:"+Data_TotalLine_Copy);

						//@-NodeView-产品型号
						NodeView_PudModel.setText("产品型号:"+ScreensFramework.DZFZ_Group[QC_Controller.View_Group].Group_DZFZ[(QC_Controller.View_Node-1)].Get_Pud_Test_PudModel());

						//@-NodeView-老化采样-电压
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
							NodeView_Pud_Test_ScanVoltage.setText("采集点电压:"+Display_ScanVoltage_Copy);
						}

						//@-NodeView-老化采样-电流
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
							NodeView_Pud_Test_ScanCurrent.setText("采集点电流:"+Display_ScanCurrent_Copy);
						}

						//@-NodeView-老化采样-温度1
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
							NodeView_Pud_Test_ScanTemputer1.setText("采集点温度1:"+Display_ScanTemputer1_Copy);
						}
						//@-NodeView-老化采样-温度2
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
							NodeView_Pud_Test_ScanTemputer2.setText("采集点温度2:"+Display_ScanTemputer2_Copy);
						}
						//@-NodeView-老化采样-温度3
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
							NodeView_Pud_Test_ScanTemputer3.setText("采集点温度3:"+Display_ScanTemputer3_Copy);
						}

					}
				});

			}
    	});

    	//@4-数据信息监听器
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

    	//@11-启动曲线刷新
    	prepareTimeline();

    }

    /**
     *
     */
    public void Init_Parameter()
    {
    	//@-数据记录数初始化
		Data_TotalLine = 0;
		Data_TotalLine_Copy = 0;

		//@-存储文件数据当前记录数
		Data_CurrentLine=0;

		//@-曲线数据清空
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
//		//@9-数据链加入曲线
//    	Chart.getData().add(ChartData_ScanVoltage);
//    	Chart.getData().add(ChartData_ScanCurrent);
//    	Chart.getData().add(ChartData_ScanTemperature1);
//    	Chart.getData().add(ChartData_ScanTemperature2);
//    	Chart.getData().add(ChartData_ScanTemperature3);

        Chart_Axis_X.setLowerBound(0);
        Chart_Axis_X.setUpperBound(200);

        //@7-设置曲线运行状态
        curve_updata_switch();

    }



    /**曲线更新定时时间线
     *
     */
    private void prepareTimeline()
    {
        //@1-创建时间线
    	Curve_Timeline = new Timeline();

        //@2-设置时间线为无限循环
    	Curve_Timeline.setCycleCount(Animation.INDEFINITE);

        //@3-时间线增加定时事件
    	Curve_Timeline.getKeyFrames().add(
                new KeyFrame(Duration.millis(50), new EventHandler<ActionEvent>() {
                    @Override public void handle(ActionEvent actionEvent) {

                    	//@4-建立平台输出线程
						Platform.runLater(new Runnable() {
							@Override
							public void run() {

								//@5-添加新数据至曲线
					        	addDataToSeries();
							}
						});
                    }
                })
        );

        //@6-将事件线连接至动画定时器启动
    	Curve_Animation = new SequentialTransition();
    	Curve_Animation.getChildren().addAll(Curve_Timeline);

        //@7-设置曲线运行状态
        //curve_updata_switch();

    }

    /**曲线更新开始
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



    /**向曲线添加新数据
     *
     */
    synchronized private void addDataToSeries()
    {


    	//System.out.println("Thread:"+Thread.currentThread().getName());


    	//@-获取节点数据源
		//@DX1-保存数据的文件名-每个产品独立数据存储文件-|AODI_产品ID_Group(Save_Data_Group)Node(Save_Data_Node).aodi_save|
    	String Save_Data_FileName = new String("AODI_"+ScreensFramework.DZFZ_Group[QC_Controller.View_Group].Group_DZFZ[(QC_Controller.View_Node-1)].Get_Pud_ID()+"_"+
    											ScreensFramework.DZFZ_Group[QC_Controller.View_Group].Group_DZFZ[(QC_Controller.View_Node-1)].Get_Pud_Test_PudModel()+
    										   "_Group"+(QC_Controller.View_Group+1)+"Node"+(QC_Controller.View_Node)+".aodi_save");

        //@-判断存储文件是否存在
		File f = new File(ScreensFramework.MAIN_SaveData_TodayPath+Save_Data_FileName);
		if(f.exists())
		{
			//@-存在存储文件,读取文件-获取数据总存储数
	    	try {
				Data_TotalLine=total_line_count(ScreensFramework.MAIN_SaveData_TodayPath+Save_Data_FileName);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	    	//@-总数据存储数发生变化
	    	if(Data_TotalLine_Copy!=Data_TotalLine)
	    	{
		    	//@1-加载记录数据
				try {
					reader = new BufferedReader(new FileReader(ScreensFramework.MAIN_SaveData_TodayPath+Save_Data_FileName));
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				//@2-过滤原有数据
				for(int  i=0;i<Data_TotalLine_Copy;i++)
				{
					try {
						line = reader.readLine();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

	    		//@3-更新存储数
	    		Data_TotalLine_Copy=Data_TotalLine;

				//@4-读取每行记录
				//Data_CurrentLine=0;
		    	while(Data_CurrentLine<Data_TotalLine)
		    	{
		    		if(CurveRun_Status==true)
		    		{
			    		try {
							line = reader.readLine();

							All_AODI_Data[Data_CurrentLine]=(AODI_Data)getRecordDataFromString(line);

					    	//@-采样电压
					    	ChartData_ScanVoltage.getData().add(new LineChart.Data(Data_CurrentLine, Float.valueOf(All_AODI_Data[Data_CurrentLine].get_Data_Voltage_Str())));
					    	//@-采样电流
					    	ChartData_ScanCurrent.getData().add(new LineChart.Data(Data_CurrentLine, Float.valueOf(All_AODI_Data[Data_CurrentLine].get_Data_Current_Str())));
					    	//@-采样温度1
					    	ChartData_ScanTemperature1.getData().add(new LineChart.Data(Data_CurrentLine, Float.valueOf(All_AODI_Data[Data_CurrentLine].get_Data_Temperature1_Str())));
					    	//@-采样温度2
					    	ChartData_ScanTemperature2.getData().add(new LineChart.Data(Data_CurrentLine, Float.valueOf(All_AODI_Data[Data_CurrentLine].get_Data_Temperature2_Str())));
					    	//@-采样温度3
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

					        //@19-曲线更新边界显示
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
//			    	//@-生成曲线
//			    	for(int i=0;i<Data_TotalLine;i++)
//			    	{
//				    	//@-采样电压
//				    	ChartData_ScanVoltage.getData().add(new LineChart.Data(i, Float.valueOf(All_AODI_Data[i].get_Data_Voltage_Str())));
//				    	//@-采样电流
//				    	ChartData_ScanCurrent.getData().add(new LineChart.Data(i, Float.valueOf(All_AODI_Data[i].get_Data_Current_Str())));
//				    	//@-采样温度1
//				    	ChartData_ScanTemperature1.getData().add(new LineChart.Data(i, Float.valueOf(All_AODI_Data[i].get_Data_Temperature1_Str())));
//				    	//@-采样温度2
//				    	ChartData_ScanTemperature2.getData().add(new LineChart.Data(i, Float.valueOf(All_AODI_Data[i].get_Data_Temperature2_Str())));
//				    	//@-采样温度3
//				    	ChartData_ScanTemperature3.getData().add(new LineChart.Data(i, Float.valueOf(All_AODI_Data[i].get_Data_Temperature3_Str())));
//			    	}
//		    	}

	    	}

		}


    	//System.out.println(""+Data_TotalLine);

    	//@-采样电压
    	//ChartData_ScanVoltage.getData().add(new LineChart.Data(xSeriesData++, 0));
    }


    /**
     * 通过一行文件内容生成一个 AODI_Data 对象
     * @param line
     * @return
     */
    private AODI_Data getRecordDataFromString(String line) {


        String[] parts = line.split(SaveData_Format);  // 获取被分隔的三个部分

        return new AODI_Data(new String(parts[0]), new String(parts[1]), new String(parts[2]), new String(parts[3]),new String(parts[4]),
        	   new String(parts[5]),new String(parts[6]),new String(parts[7]),new String(parts[8]),new String(parts[9]),new String(parts[10]));

    }


    /**
     * 采用BufferedInputStream方式读取文件行数
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



    /**按键监听器
     *
     * @param event
     */
    @FXML
    public void Button_Pro(ActionEvent event)
    {
		//@1-“返回”按钮
		if(event.getSource()==Back_Button)
		{
	        //@7-设置曲线运行状态
	        curve_updata_switch();
			ScreensFramework.PageChange.set("main");
		}
		//@2-"手动查询"按钮
		else if(event.getSource()==HandelCheck_Button)
		{
//			Display_ScanTemputer3 = ScreensFramework.DZFZ_Group[QC_Controller.View_Group].Group_DZFZ[(QC_Controller.View_Node-1)].Get_Pud_Test_ScanTemputer3();
			QC_DisplayTimer.CAN_ID_Index_Copy = (QC_Controller.View_Group*10)+QC_Controller.View_Node-1;
		}
		//@3-采集电压曲线
		else if(event.getSource()==ScanVoltage_CurveSel)
		{
			Curve_Display_Pro(1);
		}
		//@4-采集电流曲线
		else if(event.getSource()==ScanCurrent_CurveSel)
		{
			Curve_Display_Pro(2);
		}
		//@5-采集温度1曲线
		else if(event.getSource()==ScanTemputer1_CurveSel)
		{
			Curve_Display_Pro(3);
		}
		//@6-采集温度2曲线
		else if(event.getSource()==ScanTemputer2_CurveSel)
		{
			Curve_Display_Pro(4);
		}
		//@7-采集温度3曲线
		else if(event.getSource()==ScanTemputer3_CurveSel)
		{
			Curve_Display_Pro(5);
		}
    }

    /**各条曲线显示选择处理
     *
     * @param Display_Num
     */
    private void Curve_Display_Pro(int Display_Num)
    {
    	switch(Display_Num)
    	{
    	    //@1-采样电压
    		case 1:
    			   if(ScanVoltage_CurveSel.isSelected()==true)
    			   {
    				   Curve_Num=Curve_Num+1;
    				   if(Curve_Num>5)
    				   Curve_Num=5;
    				   Curve_ScanVoltage_Dis=true;
    				   //@-根据移除标识判断是否加入
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
        				   //@-根据移除标识判断是否加入
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
    					   //@移除该曲线
    					   Curve_ScanVoltage_Remove=true;
    					   Chart.getData().remove(ChartData_ScanVoltage);
    				   }
    			   }
    			   break;
    		//@2-采样电流
    		case 2:
    			   if(ScanCurrent_CurveSel.isSelected()==true)
    			   {
    				   Curve_Num=Curve_Num+1;
    				   Curve_ScanCurrent_Dis=true;
    				   //@-根据移除标识判断是否加入
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
        				   //@-根据移除标识判断是否加入
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
    					   //@移除该曲线
    					   Curve_ScanCurrent_Remove=true;
    					   Chart.getData().remove(ChartData_ScanCurrent);
    				   }
    			   }
    			   break;
    	    //@3-采样温度1
    		case 3:
    			   if(ScanTemputer1_CurveSel.isSelected()==true)
    			   {
    				   Curve_Num=Curve_Num+1;
    				   Curve_ScanTemperature1_Dis=true;
    				   //@-根据移除标识判断是否加入
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
        				   //@-根据移除标识判断是否加入
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
    					   //@移除该曲线
    					   Curve_ScanTemperature1_Remove=true;
    					   Chart.getData().remove(ChartData_ScanTemperature1);
    				   }
    			   }
    			   break;
    	    //@4-采样温度2
    		case 4:
    			   if(ScanTemputer2_CurveSel.isSelected()==true)
    			   {
    				   Curve_Num=Curve_Num+1;
    				   Curve_ScanTemperature2_Dis=true;
    				   //@-根据移除标识判断是否加入
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
        				   //@-根据移除标识判断是否加入
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
    					   //@移除该曲线
    					   Curve_ScanTemperature2_Remove=true;
    					   Chart.getData().remove(ChartData_ScanTemperature2);
    				   }
    			   }
    			   break;
    	    //@5-采样温度3
    		case 5:
    			   if(ScanTemputer3_CurveSel.isSelected()==true)
    			   {
    				   Curve_Num=Curve_Num+1;
    				   Curve_ScanTemperature3_Dis=true;
    				   //@-根据移除标识判断是否加入
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
        				   //@-根据移除标识判断是否加入
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
    					   //@移除该曲线
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


    /**检查曲线显示条数
     *
     * @return
     */
    private boolean Curve_Display_Check()
    {
    	//必须存在一条曲线显示
    	if(Curve_Num>1)
    	{
    		return true;
    	}
    	else
    	{
    		return false;
    	}
    }


    /**TextField过滤器
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

    /**应用软件退出
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


    /**传递主应用程序接口
     *
     */
	@Override
	public void setScreenParent(ScreensController screenPage) {
		// TODO Auto-generated method stub
		myController = screenPage;
	}

}
