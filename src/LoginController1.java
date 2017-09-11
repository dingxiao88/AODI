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

   //@1-系统退出按钮
   @FXML
   private ImageView System_Exit;

   //@2-系统时间显示
   @FXML
   private Label     System_Time;

   //@3-已充电时间
   @FXML
   private Label     Charging_RunTime;
   //@4-已充电时间Rect
   @FXML
   private Rectangle Charging_RunTime_Rect;

   //@5-输出电压
   @FXML
   private Label       Charging_OutputVoltage;
   //@5-输出电压-MAX
   @FXML
   private Label       Charging_OutputVoltage_MAX;
   //@5-输出电压-MIN
   @FXML
   private Label       Charging_OutputVoltage_MIN;
   //@5-输出电压-UpLimit
   @FXML
   private Label       Charging_OutputVoltage_UpLimit;
   //@5-输出电压-DownLimit
   @FXML
   private Label       Charging_OutputVoltage_DownLimit;
   //@6-输出电压Rect
   @FXML
   private Rectangle   Charging_OutputVoltage_Rect;
   //@7-输出电压曲线选择
   @FXML
   private CheckBox Charging_OutputVoltageCurve_Sel;

   //@8-输出电流
   @FXML
   private Label       Charging_OutputCurrent;
   //@5-输出电流-MAX
   @FXML
   private Label       Charging_OutputCurrent_MAX;
   //@5-输出电流-MIN
   @FXML
   private Label       Charging_OutputCurrent_MIN;
   //@5-输出电压-UpLimit
   @FXML
   private Label       Charging_OutputCurrent_UpLimit;
   //@5-输出电压-DownLimit
   @FXML
   private Label       Charging_OutputCurrent_DownLimit;
   //@9-输出电流Rect
   @FXML
   private Rectangle   Charging_OutputCurrent_Rect;
   //@10-输出电压曲线选择
   @FXML
   private CheckBox Charging_OutputCurrentCurve_Sel;

   //@11-母线电压
   @FXML
   private Label       Charging_MainVoltage;
   //@5-母线电压-MAX
   @FXML
   private Label       Charging_MainVoltage_MAX;
   //@5-母线电压-MIN
   @FXML
   private Label       Charging_MainVoltage_MIN;
   //@5-输出电压-UpLimit
   @FXML
   private Label       Charging_MainVoltage_UpLimit;
   //@5-输出电压-DownLimit
   @FXML
   private Label       Charging_MainVoltage_DownLimit;
   //@12-母线电压Rect
   @FXML
   private Rectangle   Charging_MainVoltage_Rect;
   //@13-母线电压曲线选择
   @FXML
   private CheckBox Charging_MainVoltageCurve_Sel;

   //@14-检测温度
   @FXML
   private Label       Charging_Temperature;
   //@5-检测温度-MAX
   @FXML
   private Label       Charging_Temperature_MAX;
   //@5-检测温度-MIN
   @FXML
   private Label       Charging_Temperature_MIN;
   //@5-输出电压-UpLimit
   @FXML
   private Label       Charging_Temperature_UpLimit;
   //@5-输出电压-DownLimit
   @FXML
   private Label       Charging_Temperature_DownLimit;
   //@15-检测温度Rect
   @FXML
   private Rectangle   Charging_Temperature_Rect;
   //@16-检测温度曲线选择
   @FXML
   private CheckBox Charging_TemperatureCurve_Sel;

   //@17-曲线
   @FXML
   private LineChart  Chart;
   //@18-曲线X轴
   @FXML
   private NumberAxis Chart_Axis_X;
   //@19-曲线Y轴
   @FXML
   private NumberAxis Chart_Axis_Y;
   //@20-曲线启停按钮
   @FXML
   private Button     ChartRun_Button;

   //@21-充电启停指示
   @FXML
   private ImageView  Charging_StatusLamp;
   //@22-充电启停按钮
   @FXML
   private Button     ChargingControl_Button;


   //@23-系统信息输出
   @FXML
   private TextArea   SystemInfo_TextArea;
   //@24-系统信息清空按钮
   @FXML
   private Button     SystemInfo_Clean_Button;

   //@25-系统错误监控-输出电压过压
   @FXML
   private Label      SystemError_OutputVoltage_Over;
   //@26-系统错误监控-输出电压欠压
   @FXML
   private Label      SystemError_OutputVoltage_Under;
   //@27-系统错误监控-输出电流过流
   @FXML
   private Label      SystemError_OutputCurrent_Over;
   //@28-系统错误监控-输出电流过低
   @FXML
   private Label      SystemError_OutputCurrent_Under;
   //@29-系统错误监控-母线电压过压
   @FXML
   private Label      SystemError_MainVoltage_Over;
   //@30-系统错误监控-母线电压欠压
   @FXML
   private Label      SystemError_MainVoltage_Under;
   //@31-系统错误监控-检测温度过高
   @FXML
   private Label      SystemError_Temperature_Over;
   //@32-系统错误监控-检测温度过低
   @FXML
   private Label      SystemError_Temperature_Under;
   //@33-系统错误监控-控制板通讯故障
   @FXML
   private Label      SystemError_PCBConnection_Error;

   //@34-通讯连接指示
   @FXML
   private ImageView  Connection_StatusLamp;
   //@35-通讯连接按钮
   @FXML
   private Button     ConnectionControl_Button;


   //@35-功能设置-策略文件-加载文件名
   @FXML
   private TextField   OptionSet_StrategyLoadFile;
   //@35-功能设置-策略文件-加载按钮
   @FXML
   private Button     OptionSet_StrategyLoad_Button;
   //@35-功能设置-策略文件-新建按钮
   @FXML
   private Button     OptionSet_StrategyNew_Button;
   //@1-功能设置-策略配置状态-状态指示
   @FXML
   private ImageView  OptionSet_StrategySet_Status;


   //@35-功能设置-电压报警设置-输出电压过压
   @FXML
   private TextField   OptionSet_OutputVoltage_Over;
   //@35-功能设置-电压报警设置-输出电压过压功能标志
   @FXML
   private RadioButton OptionSet_OutputVoltage_Over_Do;
   //@35-功能设置-电压报警设置-输出电压欠压
   @FXML
   private TextField   OptionSet_OutputVoltage_Under;
   //@35-功能设置-电压报警设置-输出电压欠压功能标志
   @FXML
   private RadioButton OptionSet_OutputVoltage_Under_Do;
   //@35-功能设置-电压报警设置-母线电压过压
   @FXML
   private TextField   OptionSet_MainVoltage_Over;
   //@35-功能设置-电压报警设置-母线电压过压功能标志
   @FXML
   private RadioButton OptionSet_MainVoltage_Over_Do;
   //@35-功能设置-电压报警设置-母线电压欠压
   @FXML
   private TextField   OptionSet_MainVoltage_Under;
   //@35-功能设置-电压报警设置-母线电压欠压功能标志
   @FXML
   private RadioButton OptionSet_MainVoltage_Under_Do;

   //@35-功能设置-电流报警设置-输出电流过流
   @FXML
   private TextField   OptionSet_OutputCurrent_Over;
   //@35-功能设置-电流报警设置-输出电流过流功能标志
   @FXML
   private RadioButton OptionSet_OutputCurrent_Over_Do;
   //@35-功能设置-电流报警设置-输出电流过低
   @FXML
   private TextField   OptionSet_OutputCurrent_Under;
   //@35-功能设置-电流报警设置-输出电流过低功能标志
   @FXML
   private RadioButton OptionSet_OutputCurrent_Under_Do;

   //@35-功能设置-温度报警设置-检测温度过高
   @FXML
   private TextField   OptionSet_Temperature_Over;
   //@35-功能设置-温度报警设置-检测温度过高功能标志
   @FXML
   private RadioButton OptionSet_Temperature_Over_Do;
   //@35-功能设置-温度报警设置-检测温度过低
   @FXML
   private TextField   OptionSet_Temperature_Under;
   //@35-功能设置-温度报警设置-检测温度过低功能标志
   @FXML
   private RadioButton OptionSet_Temperature_Under_Do;


   //@14-控制电压
   @FXML
   private Label       Charging_ControlVoltage;
   //@7-控制电压曲线选择
   @FXML
   private CheckBox    Charging_ControlVoltageCurve_Sel;


   //@12-电源电压选择-110v
   @FXML
   private Rectangle   Charging_Power110_Rect;
   //@14-控制电压
   @FXML
   private Label       Charging_Power110_Label;
   //@12-电源电压选择-220v
   @FXML
   private Rectangle   Charging_Power220_Rect;
   //@14-控制电压
   @FXML
   private Label       Charging_Power220_Label;
   //@12-电源电压选择-260v
   @FXML
   private Rectangle   Charging_Power260_Rect;
   //@14-控制电压
   @FXML
   private Label       Charging_Power260_Label;
   //@35-电源电压选择-确认按钮
   @FXML
   private Button     Charging_PowerSet_Button;
   //@1-电源电压选择-状态指示
   @FXML
   private ImageView  Charging_PowerStatus_Lamp;


   //@35-功能设置-串口设置-端口
   @FXML
   private ChoiceBox  OptionSet_SeriseSet_Port;
   //@35-功能设置-串口设置-波特率
   @FXML
   private ChoiceBox  OptionSet_SeriseSet_Bound;


   //@Debug-DA参数微调
   //@35-DA参数微调+
   @FXML
   private Button     DA_Up;
   //@35-DA参数微调-
   @FXML
   private Button     DA_Down;

//----------------------------------------------------------------------
	//@1-传递主应用程序接口
	private ScreensController myController;

	//@-图片资源
    private Image Status_Unknow_Pic;
    private Image Status_OK_Pic;
    private Image Status_Error_Pic;
    private Image System_Close_Pic;
    private Image Status_SmallUnknow_Pic;
    private Image Status_SmallOK_Pic;
    private Image Status_SmallError_Pic;

    //@-曲线最大显示点数
    private static final int MAX_DATA_POINTS = 400;

    //@-曲线运行状态
    public static boolean   CurveRun_Status=false;

    //@-各条曲线显示标志
    public static boolean   Curve_OutputVoltage_Dis = true;
    public static boolean   Curve_OutputCurrent_Dis = true;
    public static boolean   Curve_MainVoltage_Dis = true;
    public static boolean   Curve_Temperature_Dis = true;
    public static boolean   Curve_ControlVoltage_Dis = false;

    //@-各条曲线移除标志
    public static boolean   Curve_OutputVoltage_Remove = false;
    public static boolean   Curve_OutputCurrent_Remove = false;
    public static boolean   Curve_MainVoltage_Remove =  false;
    public static boolean   Curve_Temperature_Remove =  false;
    public static boolean   Curve_ControlVoltage_Remove =  true;

    //@-曲线显示计数
    public static int       Curve_Display_Count=4;

    //@-基准数据更新标志
    private boolean Curve_UpData=true;

    //@-基准数据
    private int     Curve_UpData_DataBase=1;   //1:输出电压  2:输出电流   3:母线电压  4:检测温度  5:控制电压

    //@-基准数据曲线计数
    private int xSeriesData = 0;
    private int xSeriesData_copy = 0;

    //@-曲线数据
    private LineChart.Series<Number, Number>  ChartData_OutputVoltage;
    private LineChart.Series<Number, Number>  ChartData_OutputCurrent;
    private LineChart.Series<Number, Number>  ChartData_MainVoltage;
    private LineChart.Series<Number, Number>  ChartData_Temperature;
    private LineChart.Series<Number, Number>  ChartData_ControlVoltage;

    //@-曲线数据共享队列
    public static List<Float> Data_OutputVoltage = Collections.synchronizedList(new ArrayList<Float>());
    public static List<Float> Data_OutputCurrent = Collections.synchronizedList(new ArrayList<Float>());
    public static List<Float> Data_MainVoltage =  Collections.synchronizedList(new ArrayList<Float>());
    public static List<Float> Data_Temperature =  Collections.synchronizedList(new ArrayList<Float>());
    public static List<Float> Data_ControlVoltage =  Collections.synchronizedList(new ArrayList<Float>());

    //@-曲线时间线
    private Timeline Curve_Timeline;
    //@-曲线动画定时器
    private SequentialTransition Curve_Animation;

    //@-界面更新同步
	public static SimpleStringProperty DisplayUpdataProperty_Main = new SimpleStringProperty();
    //@-充电策略配置同步
	public static SimpleStringProperty StrategyProperty_Main = new SimpleStringProperty();
    //@-充电策略阶段同步
	public static SimpleStringProperty StrategyStage_Main = new SimpleStringProperty();

	//@-界面更新标志
	public static boolean DisplayUpdata_Flag=false;

    //@34-change监听器
	private ChangeListener changelisten1;
	private ChangeListener changelisten2;
	private ChangeListener changelisten3;

	//@6-显示数据格式
	private java.text.NumberFormat  formater_value  =  java.text.DecimalFormat.getInstance();  //显示小数格式化
	private java.text.NumberFormat  formater_value1  =  java.text.DecimalFormat.getInstance();  //显示小数格式化

	//串口
	public static Serise Serise_Main;    //串口对象
	//串口状态
	public static int Serise_Status=0;   //0:串口关闭  1:串口打开  2:不存在串口端口
	//串口故障锁定
	public static boolean Serise_Error_Flag=false;

    //@35-功能设置-电压报警-输出电压上限
	public static Float OptionSet_OutputVoltage_UpLimit=(float)0;
    //@35-功能设置-电压报警-输出电压下限
	public static Float OptionSet_OutputVoltage_DownLimit=(float)0;
    //@35-功能设置-电压报警-输出电压上限制报警标志   0:正常    1:超    2:临界
	public static int OutputVoltage_Up_LimitFlag=0;
    //@35-功能设置-电压报警-输出电压下限制报警标志   0:正常    1:超    2:临界
	public static int OutputVoltage_Down_LimitFlag=0;
    //@35-功能设置-电压报警-输出电压上限设置标志
	public static boolean OptionSet_OutputVoltage_UpLimit_Flag=false;
    //@35-功能设置-电压报警-输出电压下限设置标志
	public static boolean OptionSet_OutputVoltage_DownLimit_Flag=false;
    //@35-输出电压最大值
	public static Float OutputVoltage_MAX=(float)0;
    //@35-输出电压最小值
	public static Float OutputVoltage_MIN=(float)0;
    //@35-输出电压当前值
	public static Float OutputVoltage_Current=(float)0;

    //@35-功能设置-电流报警-输出电流上限
	public static Float OptionSet_OutputCurrent_UpLimit=(float)0;
    //@35-功能设置-电流报警-输出电流下限
	public static Float OptionSet_OutputCurrent_DownLimit=(float)0;
    //@35-功能设置-电压报警-输出电流上限制报警标志   0:正常    1:超    2:临界
	public static int OutputCurrent_Up_LimitFlag=0;
    //@35-功能设置-电压报警-输出电流下限制报警标志   0:正常    1:超    2:临界
	public static int OutputCurrent_Down_LimitFlag=0;
    //@35-功能设置-电流报警-输出电流上限设置标志
	public static boolean OptionSet_OutputCurrent_UpLimit_Flag=false;
    //@35-功能设置-电流报警-输出电流下限设置标志
	public static boolean OptionSet_OutputCurrent_DownLimit_Flag=false;
    //@35-输出电流最大值
	public static Float OutputCurrent_MAX=(float)0;
    //@35-输出电流最小值
	public static Float OutputCurrent_MIN=(float)0;
    //@35-输出电流当前值
	public static Float OutputCurrent_Current=(float)0;
    //@35-输出电流当前值影子
	public static Float OutputCurrent_Current_Copy=(float)0;

    //@35-功能设置-电压报警-母线电压上限
	public static Float OptionSet_MainVoltage_UpLimit=(float)0;
    //@35-功能设置-电压报警-母线电压下限
	public static Float OptionSet_MainVoltage_DownLimit=(float)0;
    //@35-功能设置-电压报警-母线电流上限制报警标志   0:正常    1:超    2:临界
	public static int MainVoltage_Up_LimitFlag=0;
    //@35-功能设置-电压报警-母线电流下限制报警标志   0:正常    1:超    2:临界
	public static int MainVoltage_Down_LimitFlag=0;
    //@35-功能设置-电压报警-母线电压上限设置标志
	public static boolean OptionSet_MainVoltage_UpLimit_Flag=false;
    //@35-功能设置-电压报警-母线电压下限设置标志
	public static boolean OptionSet_MainVoltage_DownLimit_Flag=false;
    //@35-母线电压最大值
	public static Float MainVoltage_MAX=(float)0;
    //@35-母线电压最小值
	public static Float MainVoltage_MIN=(float)0;
    //@35-母线电压当前值
	public static Float MainVoltage_Current=(float)0;

    //@35-功能设置-温度报警-检测温度上限
	public static Float OptionSet_Temperature_UpLimit=(float)0;
    //@35-功能设置-温度报警-检测温度下限
	public static Float OptionSet_Temperature_DownLimit=(float)0;
    //@35-功能设置-电压报警-检测温度上限制报警标志   0:正常    1:超    2:临界
	public static int Temperature_Up_LimitFlag=0;
    //@35-功能设置-电压报警-检测温度下限制报警标志   0:正常    1:超    2:临界
	public static int Temperature_Down_LimitFlag=0;
    //@35-功能设置-温度报警-检测温度上限设置标志
	public static boolean OptionSet_Temperature_UpLimit_Flag=false;
    //@35-功能设置-温度报警-检测温度下限设置标志
	public static boolean OptionSet_Temperature_DownLimit_Flag=false;
    //@35-检测温度最大值
	public static Float Temperature_MAX=(float)0;
    //@35-检测温度最小值
	public static Float Temperature_MIN=(float)0;
    //@35-检测温度当前值
	public static Float Temperature_Current=(float)0;


    //@35-控制电压当前值
	public static Float ControlVoltage_Current=(float)0;
	public static Float ControlVoltage_Current_Copy=(float)0;


	//@-充电策略配置标志
	public static boolean OptionSet_StrategySet_Flag=false;
	//@-充电超时标志
	public static boolean ControlVoltage_Timeout_Flag=false;
	//@-控制电压达到上限标志
	public static boolean ControlVoltage_StageLimt_Flag=false;
    //@-策略参数-策略所有阶段成功标志
    public static boolean Strategy_Succeed_Flag=false;
    //@-策略参数-策略阶段
    public static int     Strategy_Stage=0;   //1:Stage1 2:Stage2 3:Stage3 4:Stage4

    //@-策略判断-区域电压起始值
    public static float Stage_Voltage_Start=0;
    //@-策略判断-区域电压终止值
    public static float Stage_Voltage_End=0;
    //@-策略判断-区域电压终止值延时时间
    public static float Stage_Voltage_EndDelay=0;
    //@-策略判断-区域电流起始值
    public static float Stage_Current_Start=0;
    //@-策略判断-区域电流终止值
    public static float Stage_Current_End=0;
    //@-策略判断-区域检测成功计数
    public static int Stage_Succeed_Count=0;
    //@-策略判断-区域检测失败计数
    public static int Stage_Fail_Count=0;
    //@-策略判断-区域检测成功百分比
    public static float Stage_Succeed=0;
    //@-策略判断-区域进入标志
    public static boolean Stage_InFlag=false;
    //@-策略判断-区域离开标志
    public static boolean Stage_OutFlag=false;

    //@2-策略准则区域电压1选择标志
  	public static boolean VoltageArea1_Sel=true;
  	//@3-策略准则区域电压2选择标志
  	public static boolean VoltageArea2_Sel=false;
  	//@4-策略准则区域电压3选择标志
  	public static boolean VoltageArea3_Sel=false;
  	//@5-策略准则区域电压4选择标志
  	public static boolean VoltageArea4_Sel=false;

  	//@8-策略准则-起始电压
  	public static float  VoltageStart;
  	//@9-策略准则-终止电压
  	public static float  VoltageEnd;
  	//@9-策略准则-终止电压延时时间
  	public static float  VoltageEnd_Delay;
  	//@10-策略准则-步进电压
  	public static float  VoltageStep;

  	//@10-策略准则-区域电压1
  	public static float  VoltageArea1;
  	//@10-策略准则-区域电压1-Low
  	public static float  VoltageArea1_Low;
  	//@10-策略准则-区域电压1-Hight
  	public static float  VoltageArea1_High;
  	//@10-策略准则-区域电压2
  	public static float  VoltageArea2;
  	//@10-策略准则-区域电压2-Low
  	public static float  VoltageArea2_Low;
  	//@10-策略准则-区域电压2-Hight
  	public static float  VoltageArea2_High;
  	//@10-策略准则-区域电压3
  	public static float  VoltageArea3;
  	//@10-策略准则-区域电压3-Low
  	public static float  VoltageArea3_Low;
  	//@10-策略准则-区域电压3-Hight
  	public static float  VoltageArea3_High;
  	//@10-策略准则-区域电压4
  	public static float  VoltageArea4;
  	//@10-策略准则-区域电压4-Low
  	public static float  VoltageArea4_Low;
  	//@10-策略准则-区域电压4-Hight
  	public static float  VoltageArea4_High;

  	//@10-策略准则-预充电流
  	public static float  Current1;
  	//@10-策略准则-预充电流-Low
  	public static float  Current1_Low;
  	//@10-策略准则-预充电流-Hight
  	public static float  Current1_High;
  	//@10-策略准则-恒电流1
  	public static float  Current2;
  	//@10-策略准则-恒电流1-Low
  	public static float  Current2_Low;
  	//@10-策略准则-恒电流1-Hight
  	public static float  Current2_High;
  	//@10-策略准则-恒电流2
  	public static float  Current3;
  	//@10-策略准则-恒电流2-Low
  	public static float  Current3_Low;
  	//@10-策略准则-恒电流2-Hight
  	public static float  Current3_High;
  	//@10-策略准则-浮充电流
  	public static float  Current4;
  	//@10-策略准则-浮充电流-Low
  	public static float  Current4_Low;
  	//@10-策略准则-浮充电流-Hight
  	public static float  Current4_High;



	//@-充电时间-时
	public static int   ChargeVoltage_Time=0;
	//@-充电运行时间-时
	public static int   ChargeVoltage_RunTimeHour=0;
	//@-充电运行时间-分
	public static int   ChargeVoltage_RunTimeMin=0;
	//@-充电运行时间-秒
	public static int   ChargeVoltage_RunTimeSecond=0;
	//@-充电电压运行标志
	public static boolean ChargeVoltage_StartFlag=false;


	//@-PCB存活标志
	public static boolean PCB_Status=false;
	//@-PCB存活标志镜像
	public static boolean PCB_Status_Copy=false;

	//@-充电电压选择
	public static int Charging_Power_Sel=0;    //0:没有选择  1:110v 2:220v 3:260v
	//@-充电电源电压状态标志
	public static boolean Charging_PowerStarus=false;
	//@-充电电源电压CAN发送标志
	public static boolean Charging_PowerSend_Flag=false;

	//@-策略文件路径
	public static String StrategyLoadFile_Path=null;
	//@-策略文件名
	public static String StrategyLoadFile_Name=null;
	//@-策略文件加载标志
	public static boolean StrategyChooseFile_Flag=false;
	//@-策略文件加载方式  1:新建  2:加载
	public static int StrategyLoadFile_Mode=1;


	//@-Debug-DA微调
	public static float Send_DA_Range =0;



	/**登录界面初始化
	 *
	 */
    @SuppressWarnings("unchecked")
	@Override
    public void initialize(URL location, ResourceBundle resources)
    {
    	//@1-格式化设置
		formater_value.setMaximumFractionDigits(2);
		formater_value.setMinimumFractionDigits(2);
		formater_value1.setMaximumIntegerDigits(2);
		formater_value1.setMinimumIntegerDigits(2);

    	//@2-图片资源初始化
    	Status_Unknow_Pic= new Image(LoginController1.class.getResourceAsStream("statusbar_message_light_orange.png"));
    	Status_OK_Pic= new Image(LoginController1.class.getResourceAsStream("statusbar_message_light_green.png"));
    	Status_Error_Pic= new Image(LoginController1.class.getResourceAsStream("statusbar_message_light_red.png"));
    	System_Close_Pic= new Image(LoginController1.class.getResourceAsStream("power.png"));
    	Status_SmallUnknow_Pic= new Image(LoginController1.class.getResourceAsStream("status_yellow.png"));
    	Status_SmallOK_Pic= new Image(LoginController1.class.getResourceAsStream("status_green.png"));
    	Status_SmallError_Pic= new Image(LoginController1.class.getResourceAsStream("status_red.png"));

    	//@3-链接图片资源
    	System_Exit.setImage(System_Close_Pic);
    	Charging_StatusLamp.setImage(Status_Unknow_Pic);
    	Connection_StatusLamp.setImage(Status_Unknow_Pic);
    	OptionSet_StrategySet_Status.setImage(Status_Unknow_Pic);
		Charging_StatusLamp.setImage(Status_Unknow_Pic);
		Charging_PowerStatus_Lamp.setImage(Status_SmallUnknow_Pic);

    	//@4-功能配置项数据过滤器
    	TextField_CharFilter(OptionSet_OutputVoltage_Over);
    	TextField_CharFilter(OptionSet_OutputVoltage_Under);
    	TextField_CharFilter(OptionSet_MainVoltage_Over);
    	TextField_CharFilter(OptionSet_MainVoltage_Under);
    	TextField_CharFilter(OptionSet_OutputCurrent_Over);
    	TextField_CharFilter(OptionSet_OutputCurrent_Under);
    	TextField_CharFilter(OptionSet_Temperature_Over);
    	TextField_CharFilter(OptionSet_Temperature_Under);

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

		//@6-创建曲线数据
    	ChartData_OutputVoltage = new LineChart.Series<Number, Number>();
    	ChartData_OutputVoltage.setName("输出电压");
    	ChartData_OutputCurrent = new LineChart.Series<Number, Number>();
    	ChartData_OutputCurrent.setName("输出电流");
    	ChartData_MainVoltage =  new LineChart.Series<Number, Number>();
    	ChartData_MainVoltage.setName("母线电压");
    	ChartData_Temperature =  new LineChart.Series<Number, Number>();
    	ChartData_Temperature.setName("检测温度");
    	ChartData_ControlVoltage =  new LineChart.Series<Number, Number>();
    	ChartData_ControlVoltage.setName("控制电压");

    	//@7-初始化设置数据-串口配置-端口
    	OptionSet_SeriseSet_Port.getItems().clear();
    	OptionSet_SeriseSet_Port.getItems().addAll("COM1", "COM2", "COM3", "COM4", "COM5");
    	OptionSet_SeriseSet_Port.getSelectionModel().selectFirst();
    	//@8-初始化设置数据-串口配置-波特率
    	OptionSet_SeriseSet_Bound.getItems().clear();
    	OptionSet_SeriseSet_Bound.getItems().addAll("9600", "19200", "115200");
    	OptionSet_SeriseSet_Bound.getSelectionModel().select(1);

		//@9-数据链加入曲线
		Chart.getData().addAll(ChartData_OutputVoltage,ChartData_OutputCurrent,ChartData_MainVoltage,ChartData_Temperature);

		//@10-界面更新同步
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

									//@-系统时间显示
									System_Time.setText(""+AODI_DisplayTimer.Time_Str);

									//@-输出电压数值-当前值
									Charging_OutputVoltage.setText(""+formater_value.format(OutputVoltage_Current)+"V");
									//@-输出电压数值-最大值
									Charging_OutputVoltage_MAX.setText("MAX:"+formater_value.format(OutputVoltage_MAX)+"V");
									//@-输出电压数值-最小值
									Charging_OutputVoltage_MIN.setText("MIN:"+formater_value.format(OutputVoltage_MIN)+"V");

									//@-输出电流数值
									Charging_OutputCurrent.setText(""+formater_value.format(OutputCurrent_Current)+"A");
									//@-输出电流数值-最大值
									Charging_OutputCurrent_MAX.setText("MAX:"+formater_value.format(OutputCurrent_MAX)+"A");
									//@-输出电流数值-最小值
									Charging_OutputCurrent_MIN.setText("MIN:"+formater_value.format(OutputCurrent_MIN)+"A");

									//@-母线电压数值
									Charging_MainVoltage.setText(""+formater_value.format(MainVoltage_Current)+"V");
									//@-母线电压数值-最大值
									Charging_MainVoltage_MAX.setText("MAX:"+formater_value.format(MainVoltage_MAX)+"V");
									//@-母线电压数值-最小值
									Charging_MainVoltage_MIN.setText("MIN:"+formater_value.format(MainVoltage_MIN)+"V");

									//@-检测温度数值
									Charging_Temperature.setText(""+formater_value.format(Temperature_Current)+"度");
									//@-检测温度数值-最大值
									Charging_Temperature_MAX.setText("MAX:"+formater_value.format(Temperature_MAX)+"度");
									//@-检测温度数值-最小值
									Charging_Temperature_MIN.setText("MIN:"+formater_value.format(Temperature_MIN)+"度");

									//@控制电压
									Charging_ControlVoltage.setText(""+formater_value.format(ControlVoltage_Current)+"V");

									//@-串口状态灯
									if(Serise_Status==0)
									Connection_StatusLamp.setImage(Status_Unknow_Pic);
									else if(Serise_Status==1)
									Connection_StatusLamp.setImage(Status_OK_Pic);
									else if(Serise_Status==2)
									Connection_StatusLamp.setImage(Status_Error_Pic);

									//@检查极限
									Check_Limit();

									//@-PCB状态
									if(PCB_Status_Copy!=PCB_Status)
									{
										PCB_Status_Copy=PCB_Status;
										if(PCB_Status_Copy==true)
										{
											SystemError_PCBConnection_Error.setId("T1");
											ScreensFramework.Show_Noti("Success", "PCB连接成功!");
										}
										else if(PCB_Status_Copy==false)
										{
											SystemError_PCBConnection_Error.setId("T44");
											ScreensFramework.Show_Noti("Error", "PCB不明!");

											//@-PCB状态不明，停止充电
											ChargeVoltage_StartFlag=false;
											Charging_StatusLamp.setImage(Status_Unknow_Pic);
										}
									}


									//@-充电运行时间
									if(ChargeVoltage_StartFlag==true)
									{
										Charging_RunTime.setText(""+formater_value1.format(ChargeVoltage_RunTimeHour)+
																":"+formater_value1.format(ChargeVoltage_RunTimeMin)+
																":"+formater_value1.format(ChargeVoltage_RunTimeSecond));
									}

									//@-信息输出
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

		//@-充电策略同步
		StrategyProperty_Main.addListener(changelisten2=new ChangeListener(){
			@Override
			public void changed(ObservableValue arg0, Object oldval, Object newval) {
				// TODO Auto-generated method stub
				if(newval.toString().equals(new String("Set")))          //策略配置成功
				{
					//@-策略配置状态
					OptionSet_StrategySet_Status.setImage(Status_OK_Pic);
					//@-关闭策略文件
					OptionSet_StrategyLoadFile.setDisable(true);
	    			//@-成功选择策略文件
	    			OptionSet_StrategyLoadFile.setText(StrategyControllerNew.Strategy_FileName);
					//@-加载按钮变为取消
					OptionSet_StrategyLoad_Button.setText("取消");
					//@-隐藏新建按钮
					OptionSet_StrategyNew_Button.setVisible(false);
					//@-策略配置成功标志
					OptionSet_StrategySet_Flag=true;
					//@-加载策略数据
					Load_StrategyData();
				}
				else if(newval.toString().equals(new String("Cancel")))  //没有进行策略配置
				{
					//@-策略配置状态
					OptionSet_StrategySet_Status.setImage(Status_Unknow_Pic);
					//@-关闭策略文件
					OptionSet_StrategyLoadFile.setDisable(false);
	    			//@-成功选择策略文件
	    			OptionSet_StrategyLoadFile.setText("----");
					//@-加载按钮变为取消
					OptionSet_StrategyLoad_Button.setText("加载");
					//@-隐藏新建按钮
					OptionSet_StrategyNew_Button.setVisible(true);
					//@-策略配置成功标志
					OptionSet_StrategySet_Flag=false;
				}
				StrategyProperty_Main.setValue("None");
			}
    	});


		//@-充电策略阶段同步
		StrategyStage_Main.addListener(changelisten3=new ChangeListener(){
			@Override
			public void changed(ObservableValue arg0, Object oldval, Object newval) {
				// TODO Auto-generated method stub
				if(newval.toString().equals(new String("Stage1")))       //策略阶段1
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
				else if(newval.toString().equals(new String("Stage2")))  //策略阶段2
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
				else if(newval.toString().equals(new String("Stage3")))  //策略阶段3
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
				else if(newval.toString().equals(new String("Stage4")))  //策略阶段4
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
				else if(newval.toString().equals(new String("Strategy_OK")))  //检测OK
				{
					Platform.runLater(new Runnable() {
					@Override
					public void run() {

						    //@-超时标志清零
							ControlVoltage_Timeout_Flag=false;
							ControlVoltage_StageLimt_Flag=false;

							//@-区域进出标志清零
						    Stage_InFlag=false;
						    Stage_OutFlag=false;

					    	//@-断开控制充电
		    				ChargeVoltage_StartFlag=false;
		    				Charging_StatusLamp.setImage(Status_Unknow_Pic);

							//@-重载策略
							StrategyProperty_Main.setValue("Set");

					    	//@-显示检测成功
					    	ScreensFramework.Show_Noti("Success", "检测成功!");
						}
					});
				}
				else if(newval.toString().equals(new String("Strategy_Error")))  //检测Error
				{
					Platform.runLater(new Runnable() {
					@Override
					public void run() {

						    //@-超时标志清零
							ControlVoltage_Timeout_Flag=false;
							ControlVoltage_StageLimt_Flag=false;

							//@-区域进出标志清零
						    Stage_InFlag=false;
						    Stage_OutFlag=false;

					    	//@-断开控制充电
		    				ChargeVoltage_StartFlag=false;
		    				Charging_StatusLamp.setImage(Status_Unknow_Pic);

							//@-重载策略
							StrategyProperty_Main.setValue("Set");

					    	//@-显示检测错误
					    	ScreensFramework.Show_Noti("Error", "检测错误!");
						}
					});
				}
				StrategyStage_Main.setValue("None");
			}
    	});

    	//@11-启动曲线刷新
    	prepareTimeline();

    }

    /**加载策略数据
     *
     */
    private void Load_StrategyData()
    {
    	//@-加载区域电压1阶段标志
    	VoltageArea1_Sel = ScreensFramework.Main_Config.Def_VoltageArea1_Sel;
    	//@-加载区域电压2阶段标志
    	VoltageArea2_Sel = ScreensFramework.Main_Config.Def_VoltageArea2_Sel;
    	//@-加载区域电压3阶段标志
    	VoltageArea3_Sel = ScreensFramework.Main_Config.Def_VoltageArea3_Sel;
    	//@-加载区域电压4阶段标志
    	VoltageArea4_Sel = ScreensFramework.Main_Config.Def_VoltageArea4_Sel;

    	//@-加载起始电压
    	VoltageStart = ScreensFramework.Main_Config.Def_VoltageStart;
    	//@-加载终止电压
    	VoltageEnd = ScreensFramework.Main_Config.Def_VoltageEnd;
    	//@-加载终止电压延时时间
    	VoltageEnd_Delay = ScreensFramework.Main_Config.Def_VoltageEnd_Delay;
    	//@-加载步进电压
    	VoltageStep = ScreensFramework.Main_Config.Def_VoltageStep;

    	//@-加载策略点区域电压1
    	VoltageArea1 = ScreensFramework.Main_Config.Def_VoltageArea1;
    	//@-加载策略点区域电压1-Low
    	VoltageArea1_Low = ScreensFramework.Main_Config.Def_VoltageArea1_Low;
    	//@-加载策略点区域电压1-High
    	VoltageArea1_High = ScreensFramework.Main_Config.Def_VoltageArea1_High;
    	//@-加载策略点区域电压2
    	VoltageArea2 = ScreensFramework.Main_Config.Def_VoltageArea2;
    	//@-加载策略点区域电压2-Low
    	VoltageArea2_Low = ScreensFramework.Main_Config.Def_VoltageArea2_Low;
    	//@-加载策略点区域电压2-High
    	VoltageArea2_High = ScreensFramework.Main_Config.Def_VoltageArea2_High;
    	//@-加载策略点区域电压3
    	VoltageArea3 = ScreensFramework.Main_Config.Def_VoltageArea3;
    	//@-加载策略点区域电压3-Low
    	VoltageArea3_Low = ScreensFramework.Main_Config.Def_VoltageArea3_Low;
    	//@-加载策略点区域电压3-High
    	VoltageArea3_High = ScreensFramework.Main_Config.Def_VoltageArea3_High;
    	//@-加载策略点区域电压4
    	VoltageArea4 = ScreensFramework.Main_Config.Def_VoltageArea4;
    	//@-加载策略点区域电压4-Low
    	VoltageArea4_Low = ScreensFramework.Main_Config.Def_VoltageArea4_Low;
    	//@-加载策略点区域电压4-High
    	VoltageArea4_High = ScreensFramework.Main_Config.Def_VoltageArea4_High;

    	//@-加载策略点预充电流
    	Current1 = ScreensFramework.Main_Config.Def_Current1;
    	//@-加载策略点区域电压1-Low
    	Current1_Low = ScreensFramework.Main_Config.Def_Current1_Low;
    	//@-加载策略点区域电压1-High
    	Current1_High = ScreensFramework.Main_Config.Def_Current1_High;
    	//@-加载策略点区域电压2
    	Current2 = ScreensFramework.Main_Config.Def_Current2;
    	//@-加载策略点区域电压2-Low
    	Current2_Low = ScreensFramework.Main_Config.Def_Current2_Low;
    	//@-加载策略点区域电压2-High
    	Current2_High = ScreensFramework.Main_Config.Def_Current2_High;
    	//@-加载策略点区域电压3
    	Current3 = ScreensFramework.Main_Config.Def_Current3;
    	//@-加载策略点区域电压3-Low
    	Current3_Low = ScreensFramework.Main_Config.Def_Current3_Low;
    	//@-加载策略点区域电压3-High
    	Current3_High = ScreensFramework.Main_Config.Def_Current3_High;
    	//@-加载策略点区域电压4
    	Current4 = ScreensFramework.Main_Config.Def_Current4;
    	//@-加载策略点区域电压4-Low
    	Current4_Low = ScreensFramework.Main_Config.Def_Current4_Low;
    	//@-加载策略点区域电压4-High
    	Current4_High = ScreensFramework.Main_Config.Def_Current4_High;

    	//@-充电电压起始值
		ControlVoltage_Current_Copy=VoltageStart;

    	//@-充电策略启动
    	if(VoltageArea1_Sel==true)
    	{
    		StrategyStage_Main.setValue("Stage1");
    		AODI_DisplayTimer.InfoPut(1,"切换至阶段1");
    	}
    	else if(VoltageArea2_Sel==true)
    	{
    		StrategyStage_Main.setValue("Stage2");
    		AODI_DisplayTimer.InfoPut(1,"切换至阶段2");
    	}
    	else if(VoltageArea3_Sel==true)
    	{
    		StrategyStage_Main.setValue("Stage3");
    		AODI_DisplayTimer.InfoPut(1,"切换至阶段3");
    	}
    	else if(VoltageArea4_Sel==true)
    	{
    		StrategyStage_Main.setValue("Stage4");
    		AODI_DisplayTimer.InfoPut(1,"切换至阶段4");
    	}
    }


    /**检查极限
     *
     */
    private void Check_Limit()
    {
    	//@1-限制报警-输出电压上限
		switch(OutputVoltage_Up_LimitFlag)
		{
			//@输出电压上限正常
			case 0:
				   //@-启用上限制？
				   if(OptionSet_OutputVoltage_UpLimit_Flag==true)
				   {
					   SystemError_OutputVoltage_Over.setId("T1");
				   }
				   else if(OptionSet_OutputVoltage_UpLimit_Flag==false)
				   {
					   SystemError_OutputVoltage_Over.setId("T33");
				   }
				   break;
			//@输出电压上限超
			case 1:
				   //@-启用上限制？
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
			//@输出电压等于上限
			case 2:
				   //@-启用上限制？
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
		//@2-限制报警-输出电压下限
		switch(OutputVoltage_Down_LimitFlag)
		{
			//@输出电压下限正常
			case 0:
				   //@-启用上限制？
				   if(OptionSet_OutputVoltage_DownLimit_Flag==true)
				   {
					   SystemError_OutputVoltage_Under.setId("T1");
				   }
				   else if(OptionSet_OutputVoltage_DownLimit_Flag==false)
				   {
					   SystemError_OutputVoltage_Under.setId("T33");
				   }
				   break;
			//@输出电压下限超
			case 1:
				   //@-启用上限制？
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
			//@输出电压等于下限
			case 2:
				   //@-启用上限制？
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

		//@3-限制报警-输出电流上限
		switch(OutputCurrent_Up_LimitFlag)
		{
			//@输出电流上限正常
			case 0:
				   //@-启用上限制？
				   if(OptionSet_OutputCurrent_UpLimit_Flag==true)
				   {
					   SystemError_OutputCurrent_Over.setId("T1");
				   }
				   else if(OptionSet_OutputCurrent_UpLimit_Flag==false)
				   {
					   SystemError_OutputCurrent_Over.setId("T33");
				   }
				   break;
			//@输出电流上限超
			case 1:
				   //@-启用上限制？
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
			//@输出电流等于上限
			case 2:
				   //@-启用上限制？
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
		//@4-限制报警-输出电流下限
		switch(OutputCurrent_Down_LimitFlag)
		{
			//@输出电流下限正常
			case 0:
				   //@-启用下限制？
				   if(OptionSet_OutputCurrent_DownLimit_Flag==true)
				   {
					   SystemError_OutputCurrent_Under.setId("T1");
				   }
				   else if(OptionSet_OutputCurrent_DownLimit_Flag==false)
				   {
					   SystemError_OutputCurrent_Under.setId("T33");
				   }
				   break;
			//@输出电流下限超
			case 1:
				   //@-启用下限制？
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
			//@输出电流等于下限
			case 2:
				   //@-启用下限制？
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

		//@5-限制报警-母线电压上限
		switch(MainVoltage_Up_LimitFlag)
		{
			//@母线电压上限正常
			case 0:
				   //@-启用上限制？
				   if(OptionSet_MainVoltage_UpLimit_Flag==true)
				   {
					   SystemError_MainVoltage_Over.setId("T1");
				   }
				   else if(OptionSet_MainVoltage_UpLimit_Flag==false)
				   {
					   SystemError_MainVoltage_Over.setId("T33");
				   }
				   break;
			//@母线电压上限超
			case 1:
				   //@-启用上限制？
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
			//@母线电压等于上限
			case 2:
				   //@-启用上限制？
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
		//@6-限制报警-母线电压下限
		switch(MainVoltage_Down_LimitFlag)
		{
			//@母线电压下限正常
			case 0:
				   //@-启用上限制？
				   if(OptionSet_MainVoltage_DownLimit_Flag==true)
				   {
					   SystemError_MainVoltage_Under.setId("T1");
				   }
				   else if(OptionSet_MainVoltage_DownLimit_Flag==false)
				   {
					   SystemError_MainVoltage_Under.setId("T33");
				   }
				   break;
			//@母线电压下限超
			case 1:
				   //@-启用上限制？
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
			//@母线电压等于下限
			case 2:
				   //@-启用上限制？
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

		//@7-限制报警-检测温度上限
		switch(Temperature_Up_LimitFlag)
		{
			//@检测温度上限正常
			case 0:
				   //@-启用上限制？
				   if(OptionSet_Temperature_UpLimit_Flag==true)
				   {
					   SystemError_Temperature_Over.setId("T1");
				   }
				   else if(OptionSet_Temperature_UpLimit_Flag==false)
				   {
					   SystemError_Temperature_Over.setId("T33");
				   }
				   break;
			//@检测温度上限超
			case 1:
				   //@-启用上限制？
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
			//@检测温度等于上限
			case 2:
				   //@-启用上限制？
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
		//@8-限制报警-检测温度下限
		switch(Temperature_Down_LimitFlag)
		{
			//@检测温度下限正常
			case 0:
				   //@-启用上限制？
				   if(OptionSet_Temperature_DownLimit_Flag==true)
				   {
					   SystemError_Temperature_Under.setId("T1");
				   }
				   else if(OptionSet_Temperature_DownLimit_Flag==false)
				   {
					   SystemError_Temperature_Under.setId("T33");
				   }
				   break;
			//@检测温度下限超
			case 1:
				   //@-启用上限制？
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
			//@检测温度等于下限
			case 2:
				   //@-启用上限制？
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


    /**TextField限制监听器
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
                new KeyFrame(Duration.millis(30), new EventHandler<ActionEvent>() {
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
        curve_updata_switch();

    }

    /**曲线更新开始
     *
     */
    public void curve_updata_switch()
    {
    	if(CurveRun_Status==false)
    	{
    		CurveRun_Status=true;
    		ChartRun_Button.setText("停止");
    		Curve_Animation.play();
    	}
    	else if(CurveRun_Status==true)
    	{
    		CurveRun_Status=false;
    		ChartRun_Button.setText("运行");
    		Curve_Animation.stop();
    	}
    }

    /**向曲线添加新数据
     *
     */
    private void addDataToSeries()
    {
    	//@1-同步多线程
    	synchronized(this)
    	{

    		//@2-判断基准数据源-输出电压
    		if(Curve_OutputVoltage_Dis==true)
    		{
        		//@-同步数据源
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
    		//@3-判断基准数据源-输出电流
    		else if(Curve_OutputCurrent_Dis==true)
    		{
        		//@-同步数据源
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
    		//@4-判断基准数据源-母线电压
    		else if(Curve_MainVoltage_Dis==true)
    		{
        		//@-同步数据源
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
    		//@5-判断基准数据源-检测温度
    		else if(Curve_Temperature_Dis==true)
    		{
        		//@-同步数据源
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
    		//@6-判断基准数据源-控制电压
    		else if(Curve_ControlVoltage_Dis==true)
    		{
        		//@-同步数据源
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

    		//@7-基准数据源是否更新
	    	if(xSeriesData_copy!=xSeriesData)
	    	{
	    		xSeriesData_copy=xSeriesData;
	    		Curve_UpData=true;
	    	}
	    	else
	    	{
	    		Curve_UpData=false;
	    	}

	    	//@8-基准数据源更新
	    	if(Curve_UpData==true)
	    	{
	    		//@9-数据基准源非输出电压
    		    if(Curve_UpData_DataBase!=1)
    		    {
    	    		//@10-输出电压是否显示
    	    		if(Curve_OutputVoltage_Dis==true)
    	    		{
    	        		//@-同步数据源
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

	    		//@11-数据基准源非输出电流
    		    if(Curve_UpData_DataBase!=2)
    		    {
    	    		//@12-输出电流是否显示
    	    		if(Curve_OutputCurrent_Dis==true)
    	    		{
    	        		//@-同步数据源
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

	    		//@13-数据基准源非母线电压
    		    if(Curve_UpData_DataBase!=3)
    		    {
    	    		//@14-母线电压是否显示
    	    		if(Curve_MainVoltage_Dis==true)
    	    		{
    	        		//@-同步数据源
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

	    		//@15-数据基准源非检测温度
    		    if(Curve_UpData_DataBase!=4)
    		    {
    	    		//@16-检测温度是否显示
    	    		if(Curve_Temperature_Dis==true)
    	    		{
    	        		//@-同步数据源
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

	    		//@17-数据基准源非控制电压
    		    if(Curve_UpData_DataBase!=5)
    		    {
    	    		//@18-控制电压是否显示
    	    		if(Curve_ControlVoltage_Dis==true)
    	    		{
    	        		//@-同步数据源
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


		     //@18-曲线超出最大显示数
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

	        //@19-曲线更新边界显示
	        Chart_Axis_X.setLowerBound(xSeriesData-MAX_DATA_POINTS);
	        Chart_Axis_X.setUpperBound(xSeriesData-1);
        }

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
    	    //@1-输出电压
    		case 1:
    			   if(Charging_OutputVoltageCurve_Sel.isSelected()==true)
    			   {
    				   Curve_Display_Count=Curve_Display_Count+1;
    				   Curve_OutputVoltage_Dis=true;
    				   //@-根据移除标识判断是否加入
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
        				   //@-根据移除标识判断是否加入
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
    					   //@移除该曲线
    					   Curve_OutputVoltage_Remove=true;
    					   Chart.getData().remove(ChartData_OutputVoltage);
    				   }
    			   }
    			   break;
    	    //@2-输出电流
    		case 2:
    			   if(Charging_OutputCurrentCurve_Sel.isSelected()==true)
    			   {
    				   Curve_Display_Count=Curve_Display_Count+1;
    				   Curve_OutputCurrent_Dis=true;
    				   //@-根据移除标识判断是否加入
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
        				   //@-根据移除标识判断是否加入
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
    					   //@移除该曲线
    					   Curve_OutputCurrent_Remove=true;
    					   Chart.getData().remove(ChartData_OutputCurrent);
    				   }
    			   }
    			   break;
    	    //@3-母线电压
    		case 3:
    			   if(Charging_MainVoltageCurve_Sel.isSelected()==true)
    			   {
    				   Curve_Display_Count=Curve_Display_Count+1;
    				   Curve_MainVoltage_Dis=true;
    				   //@-根据移除标识判断是否加入
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
        				   //@-根据移除标识判断是否加入
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
    					   //@移除该曲线
    					   Curve_MainVoltage_Remove=true;
    					   Chart.getData().remove(ChartData_MainVoltage);
    				   }
    			   }
    			   break;
    	    //@4-检测温度
    		case 4:
    			   if(Charging_TemperatureCurve_Sel.isSelected()==true)
    			   {
    				   Curve_Display_Count=Curve_Display_Count+1;
    				   Curve_Temperature_Dis=true;
    				   //@-根据移除标识判断是否加入
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
        				   //@-根据移除标识判断是否加入
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
    					   //@移除该曲线
    					   Curve_Temperature_Remove=true;
    					   Chart.getData().remove(ChartData_Temperature);
    				   }
    			   }
    			   break;
    	    //@4-控制电压
    		case 5:
	    			if(Charging_ControlVoltageCurve_Sel.isSelected()==true)
	 			   {
	 				   Curve_Display_Count=Curve_Display_Count+1;
	 				   Curve_ControlVoltage_Dis=true;
	 				   //@-根据移除标识判断是否加入
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
	     				   //@-根据移除标识判断是否加入
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
	 					   //@移除该曲线
	 					   Curve_ControlVoltage_Remove=true;
	 					   Chart.getData().remove(ChartData_ControlVoltage);
	 				   }
	 			   }
	 			   break;

    	     default: break;

    	}
    }


    /**检查曲线显示条数
     *
     * @return
     */
    private boolean Curve_Display_Chech()
    {
    	//必须存在一条曲线显示
    	if(Curve_Display_Count>1)
    	{
    		return true;
    	}
    	else
    	{
    		return false;
    	}
    }


    /**按键监听器
     *
     * @param event
     */
    @FXML
    public void Button_Pro(ActionEvent event)
    {
    	//@1-曲线显示选择处理
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
    	//@2-曲线启停处理
    	else if((event.getSource()==ChartRun_Button))
    	{
    		curve_updata_switch();
    	}
    	//@3-串口启停处理
    	else if((event.getSource()==ConnectionControl_Button))
    	{
    		Serise_Pro();
    	}

    	//@4-功能配置-输出电压过压启停
    	else if((event.getSource()==OptionSet_OutputVoltage_Over_Do))
    	{
    		//@输出电压上限监测
    		if(OptionSet_OutputVoltage_Over_Do.isSelected()==true)
    		{
    			//@标志
    			OptionSet_OutputVoltage_UpLimit_Flag=true;
    			//@显示
    			Charging_OutputVoltage_UpLimit.setTextFill(Color.BLACK);
    			//@数值锁定
    			OptionSet_OutputVoltage_Over.setDisable(true);
    			OptionSet_OutputVoltage_Over.setId("T2");
    			//@更新数值
    			OptionSet_OutputVoltage_UpLimit=Float.parseFloat(OptionSet_OutputVoltage_Over.getText());
    			//System.out.println(""+OptionSet_OutputVoltage_UpLimit);
    		}
    		else if(OptionSet_OutputVoltage_Over_Do.isSelected()==false)
    		{
    			//@标志
    			OptionSet_OutputVoltage_UpLimit_Flag=false;
    			//@显示
    			Charging_OutputVoltage_UpLimit.setTextFill(Color.rgb(0xbf, 0xbf, 0xbf));
    			//@数值开放
    			OptionSet_OutputVoltage_Over.setDisable(false);
    			OptionSet_OutputVoltage_Over.setText("0");
    			OptionSet_OutputVoltage_Over.setId("T1");
    		}
    	}
    	//@5-功能配置-输出电压欠压启停
    	else if((event.getSource()==OptionSet_OutputVoltage_Under_Do))
    	{
    		//@输出电压下限监测
    		if(OptionSet_OutputVoltage_Under_Do.isSelected()==true)
    		{
    			//@标志
    			OptionSet_OutputVoltage_DownLimit_Flag=true;
    			//@显示
    			Charging_OutputVoltage_DownLimit.setTextFill(Color.BLACK);
    			//@数值锁定
    			OptionSet_OutputVoltage_Under.setDisable(true);
    			OptionSet_OutputVoltage_Under.setId("T2");
    			//@更新数值
    			OptionSet_OutputVoltage_DownLimit=Float.parseFloat(OptionSet_OutputVoltage_Under.getText());
    		}
    		else if(OptionSet_OutputVoltage_Under_Do.isSelected()==false)
    		{
    			//@标志
    			OptionSet_OutputVoltage_DownLimit_Flag=false;
    			//@显示
    			Charging_OutputVoltage_DownLimit.setTextFill(Color.rgb(0xbf, 0xbf, 0xbf));
    			//@数值开放
    			OptionSet_OutputVoltage_Under.setDisable(false);
    			OptionSet_OutputVoltage_Under.setText("0");
    			OptionSet_OutputVoltage_Under.setId("T1");
    		}
    	}
    	//@6-功能配置-母线电压过压启停
    	else if((event.getSource()==OptionSet_MainVoltage_Over_Do))
    	{
    		//@母线电压上限监测
    		if(OptionSet_MainVoltage_Over_Do.isSelected()==true)
    		{
    			//@标志
    			OptionSet_MainVoltage_UpLimit_Flag=true;
    			//@显示
    			Charging_MainVoltage_UpLimit.setTextFill(Color.BLACK);
    			//@数值锁定
    			OptionSet_MainVoltage_Over.setDisable(true);
    			OptionSet_MainVoltage_Over.setId("T2");
    			//@更新数值
    			OptionSet_MainVoltage_UpLimit=Float.parseFloat(OptionSet_MainVoltage_Over.getText());
    		}
    		else if(OptionSet_MainVoltage_Over_Do.isSelected()==false)
    		{
    			//@标志
    			OptionSet_MainVoltage_UpLimit_Flag=false;
    			//@显示
    			Charging_MainVoltage_UpLimit.setTextFill(Color.rgb(0xbf, 0xbf, 0xbf));
    			//@数值开放
    			OptionSet_MainVoltage_Over.setDisable(false);
    			OptionSet_MainVoltage_Over.setText("0");
    			OptionSet_MainVoltage_Over.setId("T1");
    		}
    	}
    	//@7-功能配置-母线电压欠压启停
    	else if((event.getSource()==OptionSet_MainVoltage_Under_Do))
    	{
    		//@母线电压下限监测
    		if(OptionSet_MainVoltage_Under_Do.isSelected()==true)
    		{
    			//@标志
    			OptionSet_MainVoltage_DownLimit_Flag=true;
    			//@显示
    			Charging_MainVoltage_DownLimit.setTextFill(Color.BLACK);
    			//@数值锁定
    			OptionSet_MainVoltage_Under.setDisable(true);
    			OptionSet_MainVoltage_Under.setId("T2");
    			//@更新数值
    			OptionSet_MainVoltage_DownLimit=Float.parseFloat(OptionSet_MainVoltage_Under.getText());
    		}
    		else if(OptionSet_MainVoltage_Under_Do.isSelected()==false)
    		{
    			//@标志
    			OptionSet_MainVoltage_DownLimit_Flag=false;
    			//@显示
    			Charging_MainVoltage_DownLimit.setTextFill(Color.rgb(0xbf, 0xbf, 0xbf));
    			//@数值开放
    			OptionSet_MainVoltage_Under.setDisable(false);
    			OptionSet_MainVoltage_Under.setText("0");
    			OptionSet_MainVoltage_Under.setId("T1");
    		}
    	}

    	//@8-功能配置-输出电流过流启停
    	else if((event.getSource()==OptionSet_OutputCurrent_Over_Do))
    	{
    		//@输出电流上限监测
    		if(OptionSet_OutputCurrent_Over_Do.isSelected()==true)
    		{
    			//@标志
    			OptionSet_OutputCurrent_UpLimit_Flag=true;
    			//@显示
    			Charging_OutputCurrent_UpLimit.setTextFill(Color.BLACK);
    			//@数值锁定
    			OptionSet_OutputCurrent_Over.setDisable(true);
    			OptionSet_OutputCurrent_Over.setId("T2");
    			//@更新数值
    			OptionSet_OutputCurrent_UpLimit=Float.parseFloat(OptionSet_OutputCurrent_Over.getText());
    		}
    		else if(OptionSet_OutputCurrent_Over_Do.isSelected()==false)
    		{
    			//@标志
    			OptionSet_OutputCurrent_UpLimit_Flag=false;
    			//@显示
    			Charging_OutputCurrent_UpLimit.setTextFill(Color.rgb(0xbf, 0xbf, 0xbf));
    			//@数值开放
    			OptionSet_OutputCurrent_Over.setDisable(false);
    			OptionSet_OutputCurrent_Over.setText("0");
    			OptionSet_OutputCurrent_Over.setId("T1");
    		}
    	}
    	//@9-功能配置-输出电流过低启停
    	else if((event.getSource()==OptionSet_OutputCurrent_Under_Do))
    	{
    		//@输出电流下限监测
    		if(OptionSet_OutputCurrent_Under_Do.isSelected()==true)
    		{
    			//@标志
    			OptionSet_OutputCurrent_DownLimit_Flag=true;
    			//@显示
    			Charging_OutputCurrent_DownLimit.setTextFill(Color.BLACK);
    			//@数值锁定
    			OptionSet_OutputCurrent_Under.setDisable(true);
    			OptionSet_OutputCurrent_Under.setId("T2");
    			//@更新数值
    			OptionSet_OutputCurrent_DownLimit=Float.parseFloat(OptionSet_OutputCurrent_Under.getText());
    		}
    		else if(OptionSet_OutputCurrent_Under_Do.isSelected()==false)
    		{
    			//@标志
    			OptionSet_OutputCurrent_DownLimit_Flag=false;
    			//@显示
    			Charging_OutputCurrent_DownLimit.setTextFill(Color.rgb(0xbf, 0xbf, 0xbf));
    			//@数值开放
    			OptionSet_OutputCurrent_Under.setDisable(false);
    			OptionSet_OutputCurrent_Under.setText("0");
    			OptionSet_OutputCurrent_Under.setId("T1");
    		}
    	}

    	//@10-功能配置-检测温度过高启停
    	else if((event.getSource()==OptionSet_Temperature_Over_Do))
    	{
    		//@检测温度上限监测
    		if(OptionSet_Temperature_Over_Do.isSelected()==true)
    		{
    			//@标志
    			OptionSet_Temperature_UpLimit_Flag=true;
    			//@显示
    			Charging_Temperature_UpLimit.setTextFill(Color.BLACK);
    			//@数值锁定
    			OptionSet_Temperature_Over.setDisable(true);
    			OptionSet_Temperature_Over.setId("T2");
    			//@更新数值
    			OptionSet_Temperature_UpLimit=Float.parseFloat(OptionSet_Temperature_Over.getText());
    		}
    		else if(OptionSet_Temperature_Over_Do.isSelected()==false)
    		{
    			//@标志
    			OptionSet_Temperature_UpLimit_Flag=false;
    			//@显示
    			Charging_Temperature_UpLimit.setTextFill(Color.rgb(0xbf, 0xbf, 0xbf));
    			//@数值开放
    			OptionSet_Temperature_Over.setDisable(false);
    			OptionSet_Temperature_Over.setText("0");
    			OptionSet_Temperature_Over.setId("T1");
    		}
    	}
    	//@11-功能配置-检测温度过低启停
    	else if((event.getSource()==OptionSet_Temperature_Under_Do))
    	{
    		//@检测温度下限监测
    		if(OptionSet_Temperature_Under_Do.isSelected()==true)
    		{
    			//@标志
    			OptionSet_Temperature_DownLimit_Flag=true;
    			//@显示
    			Charging_Temperature_DownLimit.setTextFill(Color.BLACK);
    			//@数值锁定
    			OptionSet_Temperature_Under.setDisable(true);
    			OptionSet_Temperature_Under.setId("T2");
    			//@更新数值
    			OptionSet_Temperature_DownLimit=Float.parseFloat(OptionSet_Temperature_Under.getText());
    		}
    		else if(OptionSet_Temperature_Under_Do.isSelected()==false)
    		{
    			//@标志
    			OptionSet_Temperature_DownLimit_Flag=false;
    			//@显示
    			Charging_Temperature_DownLimit.setTextFill(Color.rgb(0xbf, 0xbf, 0xbf));
    			//@数值开放
    			OptionSet_Temperature_Under.setDisable(false);
    			OptionSet_Temperature_Under.setText("0");
    			OptionSet_Temperature_Under.setId("T1");
    		}
    	}

    	//@12-功能配置-策略文件-加载
    	else if((event.getSource()==OptionSet_StrategyLoad_Button))
    	{
    		//@-充电电压运行标志
    		if(ChargeVoltage_StartFlag==false)
    		{
	    		//@-没有配置充电策略
	    		if(OptionSet_StrategySet_Flag==false)
	    		{
		    		//@-启动策略文件浏览器
		    		StrategyChooseFile_Flag=ScreensFramework.File_Chooser();

		    		if(StrategyChooseFile_Flag==true)
		    		{
		    			//@-成功选择策略文件
		    			OptionSet_StrategyLoadFile.setText(StrategyLoadFile_Name);
		        		//@-策略文件模式
		        		StrategyLoadFile_Mode=2;
		    			//@-将已选择的策略文件显示
		    			ScreensFramework.PageChange.set("strategy");
		    		}
		    		else if(StrategyChooseFile_Flag=false)
		    		{
		    			ScreensFramework.Show_Noti("Warning", "没有选择策略文件!");
		    		}
	    		}
	    		else if(OptionSet_StrategySet_Flag==true)
	    		{
	    			OptionSet_StrategySet_Flag=false;
	    			ScreensFramework.Show_Noti("Success", "充电策略重置!");
	    			StrategyProperty_Main.setValue("Cancel");
	    		}
    		}
    		else if(ChargeVoltage_StartFlag==true)
    		{
    			ScreensFramework.Show_Noti("Warning", "充电进行中，请停止!");
    		}
    	}

    	//@12-功能配置-策略文件-新建
    	else if((event.getSource()==OptionSet_StrategyNew_Button))
    	{
    		//@-没有配置充电策略
    		if(OptionSet_StrategySet_Flag==false)
    		{
	    		//@-策略文件模式
	    		StrategyLoadFile_Mode=1;
	    		//@-新建策略文件
	    		ScreensFramework.PageChange.set("strategy");
    		}
    	}
    	//@13-充电运行按钮
    	else if((event.getSource()==ChargingControl_Button))
    	{
    		//@-需配置充电电源
    		if(Charging_PowerStarus==true)
    		{
	    		//@-充电策略需已配置
	    		if(OptionSet_StrategySet_Flag==true)
	    		{
	    			//@-PCB必须存活
	    			if(PCB_Status_Copy==true)
	    			{
		    			//@-更新标志
		    			if(ChargeVoltage_StartFlag==false)
		    			{
		    				ChargeVoltage_StartFlag=true;
		    				Charging_StatusLamp.setImage(Status_OK_Pic);

		    		    	//@-充电电压起始值-20160716
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
	    				ScreensFramework.Show_Noti("Warning", "PCB没有连接，不能进行充电!");
	        		}

	    		}
	    		else if(OptionSet_StrategySet_Flag==false)
	    		{
	    			ScreensFramework.Show_Noti("Error", "没有配置充电策略!");
	    		}
    		}
    		else if(Charging_PowerStarus==false)
    		{
    			ScreensFramework.Show_Noti("Error", "电源电压闭合!");
    		}
    	}
    	//@14-电源电压选择确定按钮
    	else if((event.getSource()==Charging_PowerSet_Button))
    	{
    		//@-串口需打开
    		if(Serise_Main.isOpen==true)
    		{
	    		if(Charging_PowerStarus==false)
	    		{
	    			//@-已选择电源电压
	    			if(Charging_Power_Sel!=0)
	    			{
	    				if(Charging_Power_Sel==1)
	    				ScreensFramework.Show_Noti("Success", "电源电压选择为110V!");
	    				else if(Charging_Power_Sel==2)
	    				ScreensFramework.Show_Noti("Success", "电源电压选择为220V!");
	    				if(Charging_Power_Sel==3)
	    				ScreensFramework.Show_Noti("Success", "电源电压选择为260V!");

		    			Charging_PowerStarus=true;
		    			Charging_PowerStatus_Lamp.setImage(Status_SmallOK_Pic);

						//Send CAN ID:0x00000119
						Charging_PowerSend_Flag=true;
	    			}
	    			else
	    			{
	    				ScreensFramework.Show_Noti("Warning", "请选择电源电压!");
	    			}
	    		}
	    		else if(Charging_PowerStarus==true)
	    		{
	    			//@-充电未运行
	    			if(ChargeVoltage_StartFlag==false)
	    			{
		    			//@-已选择电源电压清零
		    			Charging_Power_Sel=0;
		    			//@-颜色归零
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
	    				ScreensFramework.Show_Noti("Warning", "充电已启动，请停止!");
	    			}
	    		}
	    	}
    		else if(Serise_Main.isOpen==false)
    		{
    			ScreensFramework.Show_Noti("Error", "串口未打开!");
    		}
    	}
    	//@-系统信息清空
    	else if((event.getSource()==SystemInfo_Clean_Button))
    	{
    		AODI_DisplayTimer.MessagePut_Count=0;
    		SystemInfo_TextArea.setText("");
    	}

    	//@-Debug DA微调+
    	else if((event.getSource()==DA_Up))
    	{
    		Send_DA_Range =(float)(Send_DA_Range+0.001);
    	}
    	//@-Debug DA微调-
    	else if((event.getSource()==DA_Down))
    	{
    		if(Send_DA_Range<0)
    		Send_DA_Range=0;
    		else
    		Send_DA_Range =(float)(Send_DA_Range-0.001);
    	}

    }



    /**串口处理
     *
     */
    private void Serise_Pro()
    {
    	int    get_index;
    	String Serise_Port;
    	int    Serise_Bound=19200;

    	//@1-串口故障标志复位
    	Serise_Error_Flag=false;

    	//@2-获得串口端口号
    	get_index=OptionSet_SeriseSet_Port.getSelectionModel().getSelectedIndex();
    	Serise_Port=new String("COM"+(get_index+1));
    	//@3-获得串口波特率
    	get_index=OptionSet_SeriseSet_Bound.getSelectionModel().getSelectedIndex();
    	switch(get_index)
    	{
    		case 0: Serise_Bound=9600; break;
    		case 1: Serise_Bound=19200; break;
    		case 2: Serise_Bound=115200; break;
    		default: break;
    	}

    	//System.out.println("p:"+Serise_Port+" b:"+Serise_Bound);

    	//@4-判断串口未打开-打开
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
				ScreensFramework.Show_Noti("Success", "串口打开!");
				ConnectionControl_Button.setText("通讯已连接");
			}
			else if(Serise_Error_Flag==true)
			{
				Serise_Status=2;
				ScreensFramework.Show_Noti("Error", "不存在串口设备或端口不对!\n请重启软件并插入USB串口电缆!");
				ConnectionControl_Button.setText("通讯故障");
			}
    	}
    	//@5-判断串口已打开-关闭
    	else if(Serise_Main.isOpen==true)
    	{
    		Serise_Status=0;
    		Serise_Main.close();
    		ScreensFramework.Show_Noti("Info", "串口关闭!");
    		ConnectionControl_Button.setText("通讯未连接");
    	}
    }


    /**电源电压选择监听器
     *
     * @param event
     */
    @FXML
    public void PowerSel_Pro(MouseEvent event)
    {
    	//@-电源电压没有设定
    	if(Charging_PowerStarus==false)
    	{
	    	//@-鼠标进入
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
	        //@-鼠标离开
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
	        //@-鼠标点击
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


    /**应用软件退出
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
