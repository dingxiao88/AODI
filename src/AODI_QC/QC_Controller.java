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
import javafx.scene.control.ComboBox;
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
 * Login Controller.
 */

//@DX1-后续需要优化定位点

public class QC_Controller implements Initializable, ControlledScreen {

	//@1-传递主应用程序接口
	private ScreensController myController;

	//@-应用布局root
	@FXML
	private AnchorPane Root;

	//--------------------------组1---------------------------
	//@-老化对象1名字
	@FXML
	private Label Test_Target1_Name_Label;

	//@-老化对象1时间标题
	@FXML
	private Label Test_Target1_TimeName_Label;

	//@-老化对象1运行时间-Sub
	@FXML
	private Button Test_Target1_RunTime_Sub_Button;
	//@-老化对象1运行时间-Add
	@FXML
	private Button Test_Target1_RunTime_Add_Button;

	//@-老化对象1运行时间
	@FXML
	private Label Test_Target1_RunTime_Label;

	//@-老化对象组1pane
	@FXML
	private GridPane Test_Group1_Pane;

	//@-老化对象组1状态指示图
	@FXML
	private ImageView Test_Group1_ImageView;
	//@-老化对象组1状态指示图1
	@FXML
	private ImageView Test_Group1_ImageView1;
	//@-老化对象组1状态指示图2
	@FXML
	private ImageView Test_Group1_ImageView2;
	//@-老化对象组1状态指示图3
	@FXML
	private ImageView Test_Group1_ImageView3;
	//@-老化对象组1状态指示图4
	@FXML
	private ImageView Test_Group1_ImageView4;
	//@-老化对象组1状态指示图5
	@FXML
	private ImageView Test_Group1_ImageView5;
	//@-老化对象组1状态指示图6
	@FXML
	private ImageView Test_Group1_ImageView6;
	//@-老化对象组1状态指示图7
	@FXML
	private ImageView Test_Group1_ImageView7;
	//@-老化对象组1状态指示图8
	@FXML
	private ImageView Test_Group1_ImageView8;
	//@-老化对象组1状态指示图9
	@FXML
	private ImageView Test_Group1_ImageView9;
	//@-老化对象组1状态指示图10
	@FXML
	private ImageView Test_Group1_ImageView10;

	//@-老化对象“启动/停止”按钮-不能在Node页面下自行启动，必须在Group页面下联动“老化开始”按钮
	@FXML
	private Button  Test_Target1_TestRun_Button;
	//@-老化对象离组原因文字
	@FXML
	private Label     Test_Target1_LeaveReason_Label;
	//@-老化对象离组原因
	@FXML
	private ChoiceBox Test_Target1_LeaveReason_ChoiceBox;

	//@-老化对象1按钮1
	@FXML
	private Button  Test_Target1_Button1;
	//@-老化对象1按钮2
	@FXML
	private Button  Test_Target1_Button2;

	//@-老化对象1或组1背景Rectangle
	@FXML
	private Rectangle  Test_Target1_Rectangle;
	//---------------------------------------------------------

	//--------------------------组2---------------------------
	//@-老化对象2名字
	@FXML
	private Label Test_Target2_Name_Label;

	//@-老化对象2时间标题
	@FXML
	private Label Test_Target2_TimeName_Label;

	//@-老化对象2运行时间-Sub
	@FXML
	private Button Test_Target2_RunTime_Sub_Button;
	//@-老化对象2运行时间-Add
	@FXML
	private Button Test_Target2_RunTime_Add_Button;

	//@-老化对象2运行时间
	@FXML
	private Label Test_Target2_RunTime_Label;

	//@-老化对象组2pane
	@FXML
	private GridPane Test_Group2_Pane;

	//@-老化对象组2状态指示图
	@FXML
	private ImageView Test_Group2_ImageView;
	//@-老化对象组2状态指示图1
	@FXML
	private ImageView Test_Group2_ImageView1;
	//@-老化对象组2状态指示图2
	@FXML
	private ImageView Test_Group2_ImageView2;
	//@-老化对象组2状态指示图3
	@FXML
	private ImageView Test_Group2_ImageView3;
	//@-老化对象组2状态指示图4
	@FXML
	private ImageView Test_Group2_ImageView4;
	//@-老化对象组2状态指示图5
	@FXML
	private ImageView Test_Group2_ImageView5;
	//@-老化对象组2状态指示图6
	@FXML
	private ImageView Test_Group2_ImageView6;
	//@-老化对象组2状态指示图7
	@FXML
	private ImageView Test_Group2_ImageView7;
	//@-老化对象组2状态指示图8
	@FXML
	private ImageView Test_Group2_ImageView8;
	//@-老化对象组2状态指示图9
	@FXML
	private ImageView Test_Group2_ImageView9;
	//@-老化对象组2状态指示图10
	@FXML
	private ImageView Test_Group2_ImageView10;

	//@-老化对象“启动/停止”按钮-不能在Node页面下自行启动，必须在Group页面下联动“老化开始”按钮
	@FXML
	private Button  Test_Target2_TestRun_Button;
	//@-老化对象离组原因文字
	@FXML
	private Label     Test_Target2_LeaveReason_Label;
	//@-老化对象离组原因
	@FXML
	private ChoiceBox Test_Target2_LeaveReason_ChoiceBox;

	//@-老化对象2或组2老化运行按钮
	@FXML
	private Button  Test_Target2_Button1;
	//@-老化对象2或组2老化编辑按钮
	@FXML
	private Button  Test_Target2_Button2;

	//@-老化对象2或组2背景Rectangle
	@FXML
	private Rectangle  Test_Target2_Rectangle;
	//---------------------------------------------------------

	//--------------------------组3---------------------------
	//@-老化对象3名字
	@FXML
	private Label Test_Target3_Name_Label;

	//@-老化对象3时间标题
	@FXML
	private Label Test_Target3_TimeName_Label;

	//@-老化对象3运行时间-Sub
	@FXML
	private Button Test_Target3_RunTime_Sub_Button;
	//@-老化对象3运行时间-Add
	@FXML
	private Button Test_Target3_RunTime_Add_Button;

	//@-老化对象3运行时间
	@FXML
	private Label Test_Target3_RunTime_Label;

	//@-老化对象组3pane
	@FXML
	private GridPane Test_Group3_Pane;

	//@-老化对象组3状态指示图
	@FXML
	private ImageView Test_Group3_ImageView;
	//@-老化对象组3状态指示图1
	@FXML
	private ImageView Test_Group3_ImageView1;
	//@-老化对象组3状态指示图2
	@FXML
	private ImageView Test_Group3_ImageView2;
	//@-老化对象组3状态指示图3
	@FXML
	private ImageView Test_Group3_ImageView3;
	//@-老化对象组3状态指示图4
	@FXML
	private ImageView Test_Group3_ImageView4;
	//@-老化对象组3状态指示图5
	@FXML
	private ImageView Test_Group3_ImageView5;
	//@-老化对象组3状态指示图6
	@FXML
	private ImageView Test_Group3_ImageView6;
	//@-老化对象组3状态指示图7
	@FXML
	private ImageView Test_Group3_ImageView7;
	//@-老化对象组3状态指示图8
	@FXML
	private ImageView Test_Group3_ImageView8;
	//@-老化对象组3状态指示图9
	@FXML
	private ImageView Test_Group3_ImageView9;
	//@-老化对象组3状态指示图10
	@FXML
	private ImageView Test_Group3_ImageView10;

	//@-老化对象“启动/停止”按钮-不能在Node页面下自行启动，必须在Group页面下联动“老化开始”按钮
	@FXML
	private Button  Test_Target3_TestRun_Button;
	//@-老化对象离组原因文字
	@FXML
	private Label     Test_Target3_LeaveReason_Label;
	//@-老化对象离组原因
	@FXML
	private ChoiceBox Test_Target3_LeaveReason_ChoiceBox;

	//@-老化对象3或组3老化运行按钮
	@FXML
	private Button  Test_Target3_Button1;
	//@-老化对象3或组3老化编辑按钮
	@FXML
	private Button  Test_Target3_Button2;

	//@-老化对象3或组3背景Rectangle
	@FXML
	private Rectangle  Test_Target3_Rectangle;
	//---------------------------------------------------------

	//--------------------------组4---------------------------
	//@-老化对象4名字
	@FXML
	private Label Test_Target4_Name_Label;

	//@-老化对象4时间标题
	@FXML
	private Label Test_Target4_TimeName_Label;

	//@-老化对象4运行时间-Sub
	@FXML
	private Button Test_Target4_RunTime_Sub_Button;
	//@-老化对象4运行时间-Add
	@FXML
	private Button Test_Target4_RunTime_Add_Button;

	//@-老化对象4运行时间
	@FXML
	private Label Test_Target4_RunTime_Label;

	//@-老化对象组4pane
	@FXML
	private GridPane Test_Group4_Pane;

	//@-老化对象组4状态指示图
	@FXML
	private ImageView Test_Group4_ImageView;
	//@-老化对象组4状态指示图1
	@FXML
	private ImageView Test_Group4_ImageView1;
	//@-老化对象组4状态指示图2
	@FXML
	private ImageView Test_Group4_ImageView2;
	//@-老化对象组4状态指示图3
	@FXML
	private ImageView Test_Group4_ImageView3;
	//@-老化对象组4状态指示图4
	@FXML
	private ImageView Test_Group4_ImageView4;
	//@-老化对象组4状态指示图5
	@FXML
	private ImageView Test_Group4_ImageView5;
	//@-老化对象组4状态指示图6
	@FXML
	private ImageView Test_Group4_ImageView6;
	//@-老化对象组4状态指示图7
	@FXML
	private ImageView Test_Group4_ImageView7;
	//@-老化对象组4状态指示图8
	@FXML
	private ImageView Test_Group4_ImageView8;
	//@-老化对象组4状态指示图9
	@FXML
	private ImageView Test_Group4_ImageView9;
	//@-老化对象组4状态指示图10
	@FXML
	private ImageView Test_Group4_ImageView10;

	//@-老化对象“启动/停止”按钮-不能在Node页面下自行启动，必须在Group页面下联动“老化开始”按钮
	@FXML
	private Button  Test_Target4_TestRun_Button;
	//@-老化对象离组原因文字
	@FXML
	private Label     Test_Target4_LeaveReason_Label;
	//@-老化对象离组原因
	@FXML
	private ChoiceBox Test_Target4_LeaveReason_ChoiceBox;

	//@-老化对象4或组4老化运行按钮
	@FXML
	private Button  Test_Target4_Button1;
	//@-老化对象4或组4老化编辑按钮
	@FXML
	private Button  Test_Target4_Button2;

	//@-老化对象4或组4背景Rectangle
	@FXML
	private Rectangle  Test_Target4_Rectangle;
	//---------------------------------------------------------

	//--------------------------组5---------------------------
	//@-老化对象5名字
	@FXML
	private Label Test_Target5_Name_Label;

	//@-老化对象5时间标题
	@FXML
	private Label Test_Target5_TimeName_Label;

	//@-老化对象5运行时间-Sub
	@FXML
	private Button Test_Target5_RunTime_Sub_Button;
	//@-老化对象5运行时间-Add
	@FXML
	private Button Test_Target5_RunTime_Add_Button;

	//@-老化对象5运行时间
	@FXML
	private Label Test_Target5_RunTime_Label;

	//@-老化对象组5pane
	@FXML
	private GridPane Test_Group5_Pane;

	//@-老化对象组5状态指示图
	@FXML
	private ImageView Test_Group5_ImageView;
	//@-老化对象组5状态指示图1
	@FXML
	private ImageView Test_Group5_ImageView1;
	//@-老化对象组5状态指示图2
	@FXML
	private ImageView Test_Group5_ImageView2;
	//@-老化对象组5状态指示图3
	@FXML
	private ImageView Test_Group5_ImageView3;
	//@-老化对象组5状态指示图4
	@FXML
	private ImageView Test_Group5_ImageView4;
	//@-老化对象组5状态指示图5
	@FXML
	private ImageView Test_Group5_ImageView5;
	//@-老化对象组5状态指示图6
	@FXML
	private ImageView Test_Group5_ImageView6;
	//@-老化对象组5状态指示图7
	@FXML
	private ImageView Test_Group5_ImageView7;
	//@-老化对象组5状态指示图8
	@FXML
	private ImageView Test_Group5_ImageView8;
	//@-老化对象组5状态指示图9
	@FXML
	private ImageView Test_Group5_ImageView9;
	//@-老化对象组5状态指示图10
	@FXML
	private ImageView Test_Group5_ImageView10;

	//@-老化对象“启动/停止”按钮-不能在Node页面下自行启动，必须在Group页面下联动“老化开始”按钮
	@FXML
	private Button  Test_Target5_TestRun_Button;
	//@-老化对象离组原因文字
	@FXML
	private Label     Test_Target5_LeaveReason_Label;
	//@-老化对象离组原因
	@FXML
	private ChoiceBox Test_Target5_LeaveReason_ChoiceBox;

	//@-老化对象5或组5老化运行按钮
	@FXML
	private Button  Test_Target5_Button1;
	//@-老化对象5或组5老化编辑按钮
	@FXML
	private Button  Test_Target5_Button2;

	//@-老化对象5或组5背景Rectangle
	@FXML
	private Rectangle  Test_Target5_Rectangle;
	//---------------------------------------------------------

	//--------------------------组6---------------------------
	//@-老化对象6名字
	@FXML
	private Label Test_Target6_Name_Label;

	//@-老化对象6时间标题
	@FXML
	private Label Test_Target6_TimeName_Label;

	//@-老化对象6运行时间-Sub
	@FXML
	private Button Test_Target6_RunTime_Sub_Button;
	//@-老化对象6运行时间-Add
	@FXML
	private Button Test_Target6_RunTime_Add_Button;

	//@-老化对象6运行时间
	@FXML
	private Label Test_Target6_RunTime_Label;

	//@-老化对象组6pane
	@FXML
	private GridPane Test_Group6_Pane;

	//@-老化对象组6状态指示图
	@FXML
	private ImageView Test_Group6_ImageView;
	//@-老化对象组6状态指示图1
	@FXML
	private ImageView Test_Group6_ImageView1;
	//@-老化对象组6状态指示图2
	@FXML
	private ImageView Test_Group6_ImageView2;
	//@-老化对象组6状态指示图3
	@FXML
	private ImageView Test_Group6_ImageView3;
	//@-老化对象组6状态指示图4
	@FXML
	private ImageView Test_Group6_ImageView4;
	//@-老化对象组6状态指示图5
	@FXML
	private ImageView Test_Group6_ImageView5;
	//@-老化对象组6状态指示图6
	@FXML
	private ImageView Test_Group6_ImageView6;
	//@-老化对象组6状态指示图7
	@FXML
	private ImageView Test_Group6_ImageView7;
	//@-老化对象组6状态指示图8
	@FXML
	private ImageView Test_Group6_ImageView8;
	//@-老化对象组6状态指示图9
	@FXML
	private ImageView Test_Group6_ImageView9;
	//@-老化对象组6状态指示图10
	@FXML
	private ImageView Test_Group6_ImageView10;

	//@-老化对象“启动/停止”按钮-不能在Node页面下自行启动，必须在Group页面下联动“老化开始”按钮
	@FXML
	private Button  Test_Target6_TestRun_Button;
	//@-老化对象离组原因文字
	@FXML
	private Label     Test_Target6_LeaveReason_Label;
	//@-老化对象离组原因
	@FXML
	private ChoiceBox Test_Target6_LeaveReason_ChoiceBox;

	//@-老化对象6或组6老化运行按钮
	@FXML
	private Button  Test_Target6_Button1;
	//@-老化对象6或组6老化编辑按钮
	@FXML
	private Button  Test_Target6_Button2;

	//@-老化对象6或组6背景Rectangle
	@FXML
	private Rectangle  Test_Target6_Rectangle;
	//---------------------------------------------------------

	//--------------------------组7---------------------------
	//@-老化对象7名字
	@FXML
	private Label Test_Target7_Name_Label;

	//@-老化对象7时间标题
	@FXML
	private Label Test_Target7_TimeName_Label;

	//@-老化对象7运行时间-Sub
	@FXML
	private Button Test_Target7_RunTime_Sub_Button;
	//@-老化对象7运行时间-Add
	@FXML
	private Button Test_Target7_RunTime_Add_Button;

	//@-老化对象7运行时间
	@FXML
	private Label Test_Target7_RunTime_Label;

	//@-老化对象组7pane
	@FXML
	private GridPane Test_Group7_Pane;

	//@-老化对象组7状态指示图
	@FXML
	private ImageView Test_Group7_ImageView;
	//@-老化对象组7状态指示图1
	@FXML
	private ImageView Test_Group7_ImageView1;
	//@-老化对象组7状态指示图2
	@FXML
	private ImageView Test_Group7_ImageView2;
	//@-老化对象组7状态指示图3
	@FXML
	private ImageView Test_Group7_ImageView3;
	//@-老化对象组7状态指示图4
	@FXML
	private ImageView Test_Group7_ImageView4;
	//@-老化对象组7状态指示图5
	@FXML
	private ImageView Test_Group7_ImageView5;
	//@-老化对象组7状态指示图6
	@FXML
	private ImageView Test_Group7_ImageView6;
	//@-老化对象组7状态指示图7
	@FXML
	private ImageView Test_Group7_ImageView7;
	//@-老化对象组7状态指示图8
	@FXML
	private ImageView Test_Group7_ImageView8;
	//@-老化对象组7状态指示图9
	@FXML
	private ImageView Test_Group7_ImageView9;
	//@-老化对象组7状态指示图10
	@FXML
	private ImageView Test_Group7_ImageView10;

	//@-老化对象“启动/停止”按钮-不能在Node页面下自行启动，必须在Group页面下联动“老化开始”按钮
	@FXML
	private Button  Test_Target7_TestRun_Button;
	//@-老化对象离组原因文字
	@FXML
	private Label     Test_Target7_LeaveReason_Label;
	//@-老化对象离组原因
	@FXML
	private ChoiceBox Test_Target7_LeaveReason_ChoiceBox;

	//@-老化对象7或组7老化运行按钮
	@FXML
	private Button  Test_Target7_Button1;
	//@-老化对象7或组7老化编辑按钮
	@FXML
	private Button  Test_Target7_Button2;

	//@-老化对象7或组7背景Rectangle
	@FXML
	private Rectangle  Test_Target7_Rectangle;
	//---------------------------------------------------------

	//--------------------------组8---------------------------
	//@-老化对象8名字
	@FXML
	private Label Test_Target8_Name_Label;

	//@-老化对象8时间标题
	@FXML
	private Label Test_Target8_TimeName_Label;

	//@-老化对象8运行时间-Sub
	@FXML
	private Button Test_Target8_RunTime_Sub_Button;
	//@-老化对象8运行时间-Add
	@FXML
	private Button Test_Target8_RunTime_Add_Button;

	//@-老化对象8运行时间
	@FXML
	private Label Test_Target8_RunTime_Label;

	//@-老化对象组8pane
	@FXML
	private GridPane Test_Group8_Pane;

	//@-老化对象组8状态指示图
	@FXML
	private ImageView Test_Group8_ImageView;
	//@-老化对象组8状态指示图1
	@FXML
	private ImageView Test_Group8_ImageView1;
	//@-老化对象组8状态指示图2
	@FXML
	private ImageView Test_Group8_ImageView2;
	//@-老化对象组8状态指示图3
	@FXML
	private ImageView Test_Group8_ImageView3;
	//@-老化对象组8状态指示图4
	@FXML
	private ImageView Test_Group8_ImageView4;
	//@-老化对象组8状态指示图5
	@FXML
	private ImageView Test_Group8_ImageView5;
	//@-老化对象组8状态指示图6
	@FXML
	private ImageView Test_Group8_ImageView6;
	//@-老化对象组8状态指示图7
	@FXML
	private ImageView Test_Group8_ImageView7;
	//@-老化对象组8状态指示图8
	@FXML
	private ImageView Test_Group8_ImageView8;
	//@-老化对象组8状态指示图9
	@FXML
	private ImageView Test_Group8_ImageView9;
	//@-老化对象组8状态指示图10
	@FXML
	private ImageView Test_Group8_ImageView10;

	//@-老化对象“启动/停止”按钮-不能在Node页面下自行启动，必须在Group页面下联动“老化开始”按钮
	@FXML
	private Button  Test_Target8_TestRun_Button;
	//@-老化对象离组原因文字
	@FXML
	private Label     Test_Target8_LeaveReason_Label;
	//@-老化对象离组原因
	@FXML
	private ChoiceBox Test_Target8_LeaveReason_ChoiceBox;

	//@-老化对象8或组8老化运行按钮
	@FXML
	private Button  Test_Target8_Button1;
	//@-老化对象8或组8老化编辑按钮
	@FXML
	private Button  Test_Target8_Button2;

	//@-老化对象8或组8背景Rectangle
	@FXML
	private Rectangle  Test_Target8_Rectangle;
	//---------------------------------------------------------

	//--------------------------组9---------------------------
	//@-老化对象9名字
	@FXML
	private Label Test_Target9_Name_Label;

	//@-老化对象9时间标题
	@FXML
	private Label Test_Target9_TimeName_Label;

	//@-老化对象9运行时间-Sub
	@FXML
	private Button Test_Target9_RunTime_Sub_Button;
	//@-老化对象9运行时间-Add
	@FXML
	private Button Test_Target9_RunTime_Add_Button;

	//@-老化对象9运行时间
	@FXML
	private Label Test_Target9_RunTime_Label;

	//@-老化对象组9pane
	@FXML
	private GridPane Test_Group9_Pane;

	//@-老化对象组9状态指示图
	@FXML
	private ImageView Test_Group9_ImageView;
	//@-老化对象组9状态指示图1
	@FXML
	private ImageView Test_Group9_ImageView1;
	//@-老化对象组9状态指示图2
	@FXML
	private ImageView Test_Group9_ImageView2;
	//@-老化对象组9状态指示图3
	@FXML
	private ImageView Test_Group9_ImageView3;
	//@-老化对象组9状态指示图4
	@FXML
	private ImageView Test_Group9_ImageView4;
	//@-老化对象组9状态指示图5
	@FXML
	private ImageView Test_Group9_ImageView5;
	//@-老化对象组9状态指示图6
	@FXML
	private ImageView Test_Group9_ImageView6;
	//@-老化对象组9状态指示图7
	@FXML
	private ImageView Test_Group9_ImageView7;
	//@-老化对象组9状态指示图8
	@FXML
	private ImageView Test_Group9_ImageView8;
	//@-老化对象组9状态指示图9
	@FXML
	private ImageView Test_Group9_ImageView9;
	//@-老化对象组9状态指示图10
	@FXML
	private ImageView Test_Group9_ImageView10;

	//@-老化对象“启动/停止”按钮-不能在Node页面下自行启动，必须在Group页面下联动“老化开始”按钮
	@FXML
	private Button  Test_Target9_TestRun_Button;
	//@-老化对象离组原因文字
	@FXML
	private Label     Test_Target9_LeaveReason_Label;
	//@-老化对象离组原因
	@FXML
	private ChoiceBox Test_Target9_LeaveReason_ChoiceBox;

	//@-老化对象9或组9老化运行按钮
	@FXML
	private Button  Test_Target9_Button1;
	//@-老化对象9或组9老化编辑按钮
	@FXML
	private Button  Test_Target9_Button2;

	//@-老化对象9或组9背景Rectangle
	@FXML
	private Rectangle  Test_Target9_Rectangle;
	//---------------------------------------------------------

	//--------------------------组10---------------------------
	//@-老化对象10名字
	@FXML
	private Label Test_Target10_Name_Label;

	//@-老化对象10时间标题
	@FXML
	private Label Test_Target10_TimeName_Label;

	//@-老化对象10运行时间-Sub
	@FXML
	private Button Test_Target10_RunTime_Sub_Button;
	//@-老化对象10运行时间-Add
	@FXML
	private Button Test_Target10_RunTime_Add_Button;

	//@-老化对象10运行时间
	@FXML
	private Label Test_Target10_RunTime_Label;

	//@-老化对象组10pane
	@FXML
	private GridPane Test_Group10_Pane;

	//@-老化对象组10状态指示图
	@FXML
	private ImageView Test_Group10_ImageView;
	//@-老化对象组10状态指示图1
	@FXML
	private ImageView Test_Group10_ImageView1;
	//@-老化对象组10状态指示图2
	@FXML
	private ImageView Test_Group10_ImageView2;
	//@-老化对象组10状态指示图3
	@FXML
	private ImageView Test_Group10_ImageView3;
	//@-老化对象组10状态指示图4
	@FXML
	private ImageView Test_Group10_ImageView4;
	//@-老化对象组10状态指示图5
	@FXML
	private ImageView Test_Group10_ImageView5;
	//@-老化对象组10状态指示图6
	@FXML
	private ImageView Test_Group10_ImageView6;
	//@-老化对象组10状态指示图7
	@FXML
	private ImageView Test_Group10_ImageView7;
	//@-老化对象组10状态指示图8
	@FXML
	private ImageView Test_Group10_ImageView8;
	//@-老化对象组10状态指示图9
	@FXML
	private ImageView Test_Group10_ImageView9;
	//@-老化对象组10状态指示图10
	@FXML
	private ImageView Test_Group10_ImageView10;

	//@-老化对象“启动/停止”按钮-不能在Node页面下自行启动，必须在Group页面下联动“老化开始”按钮
	@FXML
	private Button  Test_Target10_TestRun_Button;
	//@-老化对象离组原因文字
	@FXML
	private Label     Test_Target10_LeaveReason_Label;
	//@-老化对象离组原因
	@FXML
	private ChoiceBox Test_Target10_LeaveReason_ChoiceBox;

	//@-老化对象10或组10老化运行按钮
	@FXML
	private Button  Test_Target10_Button1;
	//@-老化对象10或组10老化编辑按钮
	@FXML
	private Button  Test_Target10_Button2;

	//@-老化对象10或组10背景Rectangle
	@FXML
	private Rectangle  Test_Target10_Rectangle;
	//---------------------------------------------------------

    //@-老化操作人员
    @FXML
    private Label TestOpt_Name;

    //@-时间
    @FXML
    private Label System_Time;

	//@-PCB连接状态
	@FXML
	private ImageView System_PCB_Status_ImageView;
	//@-系统网络连接状态
	@FXML
	private ImageView System_NetWork_Status_ImageView;

	//@1-软件退出
    @FXML
    private ImageView App_Exit;

    //----------------------------------------------------------

    //@-老化产品组名
    @FXML
    private Label Test_Tab1_Group_Name_Label;

    //@-老化产品节点1编号录入
    @FXML
    private TextField Test_Tab1_Node1_Name_TextField;
    //@-老化产品节点2编号录入
    @FXML
    private TextField Test_Tab1_Node2_Name_TextField;
    //@-老化产品节点3编号录入
    @FXML
    private TextField Test_Tab1_Node3_Name_TextField;
    //@-老化产品节点4编号录入
    @FXML
    private TextField Test_Tab1_Node4_Name_TextField;
    //@-老化产品节点5编号录入
    @FXML
    private TextField Test_Tab1_Node5_Name_TextField;
    //@-老化产品节点6编号录入
    @FXML
    private TextField Test_Tab1_Node6_Name_TextField;
    //@-老化产品节点7编号录入
    @FXML
    private TextField Test_Tab1_Node7_Name_TextField;
    //@-老化产品节点8编号录入
    @FXML
    private TextField Test_Tab1_Node8_Name_TextField;
    //@-老化产品节点9编号录入
    @FXML
    private TextField Test_Tab1_Node9_Name_TextField;
    //@-老化产品节点10编号录入
    @FXML
    private TextField Test_Tab1_Node10_Name_TextField;

    //@-老化产品型号选择
    @FXML
    private ComboBox Test_PudModel_Sel_ComboBox;

    //@-老化产品节点编号录入按钮
    @FXML
    private Button Test_Tab1_InputName_Button;

    //---------------------------产品型号------------------------------

    //@-产品型号选择
    @FXML
    private ComboBox Aodi_PudModel_Sel_ComboBox;

    //@-电压监测值
    @FXML
    private TextField Aodi_PudModel_VoltageLimit_TextField;
    //@-电压监测值范围
    @FXML
    private TextField Aodi_PudModel_VoltageLimit_Range_TextField;

    //@-电流监测值
    @FXML
    private TextField Aodi_PudModel_CurrentLimit_TextField;
    //@-电流监测值范围
    @FXML
    private TextField Aodi_PudModel_CurrentLimit_Range_TextField;

    //@-温度1监测值
    @FXML
    private TextField Aodi_PudModel_Temperature1Limit_TextField;
    //@-温度1监测值范围
    @FXML
    private TextField Aodi_PudModel_Temperature1Limit_Range_TextField;

    //@-温度2监测值
    @FXML
    private TextField Aodi_PudModel_Temperature2Limit_TextField;
    //@-温度2监测值范围
    @FXML
    private TextField Aodi_PudModel_Temperature2Limit_Range_TextField;

    //@-温度3监测值
    @FXML
    private TextField Aodi_PudModel_Temperature3Limit_TextField;
    //@-温度3监测值范围
    @FXML
    private TextField Aodi_PudModel_Temperature3Limit_Range_TextField;

    //@-型号编辑按钮
    @FXML
    private Button Aodi_PudModel_Editor_Button;

    //@-型号保存按钮
    @FXML
    private Button Aodi_PudModel_Save_Button;

    //@-新建型号按钮
    @FXML
    private Button Aodi_PudModel_New_Button;
    //@-新建型号名
    @FXML
    private TextField Aodi_PudModel_New_TextField;

    //-----------------------基本设置管理------------------------------

    //@-定时单位切换-小时
    @FXML
    private RadioButton TimingSwitch_Hour_RadioButton;
    //@-定时单位切换-分钟
    @FXML
    private RadioButton TimingSwitch_Minute_RadioButton;

    //@-软件信息输出
    @FXML
    private TextArea Aodi_InfoPut_TextArea;
    //@-软件信息输出清空按钮
    @FXML
    private Button Aodi_InfoClean_Button;

    //------------------------数据采集功能配置-----------------------------
    //@-数据采集模式选择
    @FXML
    private ComboBox Aodi_DataSample_Mode_ComboBox;


    //------------------------预警处理配置-----------------------------
    //@-预警处理模式选择
    @FXML
    private ComboBox Aodi_AlarmMode_ComboBox;

    //------------------------故障应急处理-----------------------------
    //@-故障应急处理Group选择
    @FXML
    private ComboBox Aodi_AlarmPro_Group_ComboBox;
    //@-故障应急处理Node选择
    @FXML
    private ComboBox Aodi_AlarmPro_Node_ComboBox;

    //@-故障应急处理按钮
    @FXML
    private Button Aodi_AlarmPro_Button;

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
	//@26-图片资源-老化结束
	private Image Image_state_end;
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
    //@-信息输出
	public static SimpleStringProperty InfoProperty_Main = new SimpleStringProperty();
	//@-界面变化Change3监听器
	private ChangeListener Changelisten3;

    //@-界面单元-背景HashMap
	private HashMap<String,Rectangle> Map_Rectangle = new HashMap<String, Rectangle>();
	//@-界面单元-Group指示灯HashMap
	private HashMap<String,ImageView> Map_GroupImageView = new HashMap<String, ImageView>();
	//@-界面单元-老化对象名HashMap
	private HashMap<String,Label> Map_Name = new HashMap<String, Label>();
	//@-界面单元-老化对象时间标题名HashMap
	private HashMap<String,Label> Map_TimeName = new HashMap<String, Label>();
	//@-界面单元-老化运行时间HashMap
	private HashMap<String,Label> Map_RunTime = new HashMap<String, Label>();
	//@-界面单元-Group指示灯装载容器HashMap
	private HashMap<String,GridPane> Map_GroupPane = new HashMap<String, GridPane>();
	//@-界面单元-Group内10台DZFZ指示灯HashMap
	private HashMap<String,ImageView> Map_GroupPane_ImageView = new HashMap<String, ImageView>();

	//@-界面单元-Group按钮1 HashMap
	private HashMap<String,Button> Map_Button1 = new HashMap<String, Button>();
	//@-界面单元-Group按钮2 HashMap
	private HashMap<String,Button> Map_Button2 = new HashMap<String, Button>();

	//@-界面单元-Group按钮+及- HashMap
	private HashMap<String,Button> Map_AddSub_Button = new HashMap<String, Button>();


	//@-界面单元-Node老化对象“启动/停止”按钮 HashMap
	private HashMap<String,Button> Map_TestRun_Button = new HashMap<String, Button>();
	//@-界面单元-Node“离组原因”选择框 HashMap
	private HashMap<String,ChoiceBox> Map_LeaveReason_ChoiceBox = new HashMap<String, ChoiceBox>();
	//@-界面单元-Node“离组原因”文字 HashMap
	private HashMap<String,Label> Map_LeaveReason_Label = new HashMap<String, Label>();

	//@-界面单位-Tab1-节点编号输入框 HashMap
	private HashMap<String,TextField> Map_Tab1_NodeName_TextField = new HashMap<String, TextField>();


	//@-界面Group运行层
	public static int GroupRunLevel = 1;  //1:层显示为最顶层100台节点  2:层显示任一Group内10台节点内容
	//@-运行在Node层显示那个Group信息-默认Group1
	public static int RunLevel2_GroupNum = 1;

	//@-Tab1-"节点编号录入"按钮功能切换
	public static int Tab1_InputName_Button_Flag = 0; //0:激活10个节点编号输入框  1:确认录入节点编号
	//@-Tab1-"节点编号录入"Index指示
	public static int Tab1_InputName_Index = 1; //1-10个TextField节点编号输入框

	//@-节点详情-Group
	public static int View_Group = 1;
	//@-节点详情-Node
	public static int View_Node = 1;

	//@-定时时间操作级别  1:小时  2:分
	public static int TimerPro_Level = 1;

	//@-数据采集模式选择
	public static int Aodi_DataSample_Mode = 0;  //0:恒定不变采样模式   1:可变采样模式

	//@-故障预警应急处理模式
	public static int Aodi_AlarmPro_Mode = 2;




	/**登录界面初始化
	 *
	 */
    @SuppressWarnings("unchecked")
	@Override
    public void initialize(URL location, ResourceBundle resources) {

    	//@1-初始化界面HashMap
    	HashMap_Parameter_Init();

    	//@2-GUI界面参数初始化
    	GUI_Parameter_Init();

    	//@3-加载产品型号
    	PudModel_Init();

    	//@3-界面显示刷新
    	DisplayProperty_Main.addListener(Changelisten1 = new ChangeListener(){
			@Override
			public void changed(ObservableValue arg0, Object oldval, Object newval) {
				// TODO Auto-generated method stub

				Platform.runLater(new Runnable() {
					@Override
					public void run() {


						//@-debug
						//Aodi_InfoPut_TextArea.appendText(""+ScreensFramework.Debug_String);

						//@-显示系统时间
						System_Time.setText(QC_DisplayTimer.Time_Str);

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

						//@-根据键盘处理-Group层
						if(QC_Controller.GroupRunLevel==1)
						{
							//@-所有Group轮询
							for(int Group=0;Group<10;Group++)
							{
						    	//@-所有Group运行标志
						    	if((ScreensFramework.DZFZ_Group[Group].Get_Group_TimerRunFlag())==1)
						    	{
						    		//@-Group运行指示灯
									String TargetName = new String("Test_Group"+(Group+1)+"_ImageView");
						    		Map_GroupImageView.get(TargetName).setImage(Image_state_ok);

						    		//@-Group背景色块
									TargetName = new String("Test_Target"+(Group+1)+"_Rectangle");
									Map_Rectangle.get(TargetName).setId("Rectangle_Color_Green");

						    		//@-Group时间标题名-运行时间
									TargetName = new String("Test_Target"+(Group+1)+"_TimeName_Label");
									Map_TimeName.get(TargetName).setText("运行时间");

									//@-Group隐藏定时+及-按钮
									TargetName = new String("Test_Target"+(Group+1)+"_RunTime_Add_Button");
									Map_AddSub_Button.get(TargetName).setVisible(false);
									TargetName = new String("Test_Target"+(Group+1)+"_RunTime_Sub_Button");
									Map_AddSub_Button.get(TargetName).setVisible(false);

									//@-显示Group老化运行时间
									int hours = ScreensFramework.DZFZ_Group[Group].Group_Timer_RunTime / 3600;
									int minutes = (ScreensFramework.DZFZ_Group[Group].Group_Timer_RunTime % 3600) / 60;
									int seconds = (ScreensFramework.DZFZ_Group[Group].Group_Timer_RunTime % 3600) % 60;
							    	TargetName = new String("Test_Target"+(Group+1)+"_RunTime_Label");
							    	Map_RunTime.get(TargetName).setText(""+formater_data.format(hours)+":"+formater_data.format(minutes)+":"+formater_data.format(seconds));
						    	}
						    	else if((ScreensFramework.DZFZ_Group[Group].Get_Group_TimerRunFlag())==0)
						    	{
						    		//@-Group运行指示灯
									String TargetName = new String("Test_Group"+(Group+1)+"_ImageView");
						    		Map_GroupImageView.get(TargetName).setImage(Image_state_warning);

						    		//@-Group背景色块
									TargetName = new String("Test_Target"+(Group+1)+"_Rectangle");
									Map_Rectangle.get(TargetName).setId("Rectangle_Color_Yellow");

						    		//@-Group时间标题名-定时时间
									TargetName = new String("Test_Target"+(Group+1)+"_TimeName_Label");
									Map_TimeName.get(TargetName).setText("定时时间");

									//@-Group显示定时+及-按钮
									TargetName = new String("Test_Target"+(Group+1)+"_RunTime_Add_Button");
									Map_AddSub_Button.get(TargetName).setVisible(true);
									TargetName = new String("Test_Target"+(Group+1)+"_RunTime_Sub_Button");
									Map_AddSub_Button.get(TargetName).setVisible(true);

									//@-显示Group老化定时时间
									int hours = ScreensFramework.DZFZ_Group[Group].Group_Timing / 3600;
									int minutes = (ScreensFramework.DZFZ_Group[Group].Group_Timing % 3600) / 60;
									int seconds = (ScreensFramework.DZFZ_Group[Group].Group_Timing % 3600) % 60;
							    	TargetName = new String("Test_Target"+(Group+1)+"_RunTime_Label");
							    	Map_RunTime.get(TargetName).setText(""+formater_data.format(hours)+":"+formater_data.format(minutes)+":"+formater_data.format(seconds));

						    	}

								//@-所有Group内DZFZ运行标志
								for(int Node=0;Node<10;Node++)
								{
									//Test_Group1_ImageView1
							    	String TargetName = new String("Test_Group"+(Group+1)+"_ImageView"+(Node+1));
							    	if((ScreensFramework.DZFZ_Group[Group].Group_DZFZ[Node].Get_Pud_Test_Status())==0)
							    	Map_GroupPane_ImageView.get(TargetName).setImage(Image_config_no);
							    	if((ScreensFramework.DZFZ_Group[Group].Group_DZFZ[Node].Get_Pud_Test_Status())==1)
							    	Map_GroupPane_ImageView.get(TargetName).setImage(Image_config_yes);
							    	else if((ScreensFramework.DZFZ_Group[Group].Group_DZFZ[Node].Get_Pud_Test_Status())==2)
							    	Map_GroupPane_ImageView.get(TargetName).setImage(Image_state_warning);
							    	else if((ScreensFramework.DZFZ_Group[Group].Group_DZFZ[Node].Get_Pud_Test_Status())==3)
							    	Map_GroupPane_ImageView.get(TargetName).setImage(Image_state_ok);
							    	else if((ScreensFramework.DZFZ_Group[Group].Group_DZFZ[Node].Get_Pud_Test_Status())==4)
							    	Map_GroupPane_ImageView.get(TargetName).setImage(Image_state_error);
							    	else if((ScreensFramework.DZFZ_Group[Group].Group_DZFZ[Node].Get_Pud_Test_Status())==5)
							    	Map_GroupPane_ImageView.get(TargetName).setImage(Image_state_end);

								}
							}
						}
						//@-根据键盘处理-Node层
						else if(QC_Controller.GroupRunLevel==2)
						{
							//@-对特定Group内的所有Node轮询
							for(int Node=0;Node<10;Node++)
							{
						    	//@-所有Group运行标志
						    	if((ScreensFramework.DZFZ_Group[RunLevel2_GroupNum].Group_DZFZ[Node].Get_Pud_RunFlag()==1)||(ScreensFramework.DZFZ_Group[RunLevel2_GroupNum].Group_DZFZ[Node].Get_Pud_RunFlag()==2))
						    	{
						    		//@-Group背景色块
						    		String TargetName = new String("Test_Target"+(Node+1)+"_Rectangle");
						    		Map_Rectangle.get(TargetName).setId("Rectangle_Color_Green");

						    		//@-Node时间标题名-运行时间
						    		TargetName = new String("Test_Target"+(Node+1)+"_TimeName_Label");
									Map_TimeName.get(TargetName).setText("运行时间");

									//@-Node老化运行时间
									int hours = ScreensFramework.DZFZ_Group[RunLevel2_GroupNum].Group_DZFZ[Node].Pud_RunTime / 3600;
									int minutes = (ScreensFramework.DZFZ_Group[RunLevel2_GroupNum].Group_DZFZ[Node].Pud_RunTime % 3600) / 60;
									int seconds = (ScreensFramework.DZFZ_Group[RunLevel2_GroupNum].Group_DZFZ[Node].Pud_RunTime % 3600) % 60;
							    	TargetName = new String("Test_Target"+(Node+1)+"_RunTime_Label");
							    	Map_RunTime.get(TargetName).setText(""+formater_data.format(hours)+":"+formater_data.format(minutes)+":"+formater_data.format(seconds));

									//@-Group隐藏定时+及-按钮
									TargetName = new String("Test_Target"+(Node+1)+"_RunTime_Add_Button");
									Map_AddSub_Button.get(TargetName).setVisible(false);
									TargetName = new String("Test_Target"+(Node+1)+"_RunTime_Sub_Button");
									Map_AddSub_Button.get(TargetName).setVisible(false);
						    	}
						    	else if((ScreensFramework.DZFZ_Group[RunLevel2_GroupNum].Group_DZFZ[Node].Get_Pud_RunFlag()==0))
						    	{
						    		//@-Group背景色块
						    		String TargetName = new String("Test_Target"+(Node+1)+"_Rectangle");
						    		Map_Rectangle.get(TargetName).setId("Rectangle_Color_Yellow");

						    		//@-Node时间标题名-定时时间
						    		TargetName = new String("Test_Target"+(Node+1)+"_TimeName_Label");
									Map_TimeName.get(TargetName).setText("定时时间");

									//@-Node老化运行时间
									int hours = ScreensFramework.DZFZ_Group[RunLevel2_GroupNum].Group_DZFZ[Node].Pud_Timing / 3600;
									int minutes = (ScreensFramework.DZFZ_Group[RunLevel2_GroupNum].Group_DZFZ[Node].Pud_Timing % 3600) / 60;
									int seconds = (ScreensFramework.DZFZ_Group[RunLevel2_GroupNum].Group_DZFZ[Node].Pud_Timing % 3600) % 60;
							    	TargetName = new String("Test_Target"+(Node+1)+"_RunTime_Label");
							    	Map_RunTime.get(TargetName).setText(""+formater_data.format(hours)+":"+formater_data.format(minutes)+":"+formater_data.format(seconds));

									//@-Group显示定时+及-按钮
									TargetName = new String("Test_Target"+(Node+1)+"_RunTime_Add_Button");
									Map_AddSub_Button.get(TargetName).setVisible(true);
									TargetName = new String("Test_Target"+(Node+1)+"_RunTime_Sub_Button");
									Map_AddSub_Button.get(TargetName).setVisible(true);

						    	}

						    	//@-Group内DZFZ运行标志
						    	String TargetName = new String("Test_Group"+(Node+1)+"_ImageView");
						    	if((ScreensFramework.DZFZ_Group[RunLevel2_GroupNum].Group_DZFZ[Node].Get_Pud_Test_Status())==0)
						    	Map_GroupImageView.get(TargetName).setImage(Image_config_no);
						    	else if((ScreensFramework.DZFZ_Group[RunLevel2_GroupNum].Group_DZFZ[Node].Get_Pud_Test_Status())==1)
						    	Map_GroupImageView.get(TargetName).setImage(Image_config_yes);
						    	else if((ScreensFramework.DZFZ_Group[RunLevel2_GroupNum].Group_DZFZ[Node].Get_Pud_Test_Status())==2)
						    	Map_GroupImageView.get(TargetName).setImage(Image_state_warning);
						    	else if((ScreensFramework.DZFZ_Group[RunLevel2_GroupNum].Group_DZFZ[Node].Get_Pud_Test_Status())==3)
						    	Map_GroupImageView.get(TargetName).setImage(Image_state_ok);
						    	else if((ScreensFramework.DZFZ_Group[RunLevel2_GroupNum].Group_DZFZ[Node].Get_Pud_Test_Status())==4)
						    	Map_GroupImageView.get(TargetName).setImage(Image_state_error);
						    	else if((ScreensFramework.DZFZ_Group[RunLevel2_GroupNum].Group_DZFZ[Node].Get_Pud_Test_Status())==5)
						    	Map_GroupImageView.get(TargetName).setImage(Image_state_end);
							}
						}

						//@显示扫描target
						boolean target = false;
						for(int i=0;i<10; i++)
						{
					  		if((ScreensFramework.DZFZ_Group[i].Get_Group_TimerRunFlag())==1)
					  		{
					  			target = true;
					  		}
						}
						if(target==true)
						{

//					    	String TargetName = new String("Test_Group"+QC_DisplayTimer.CAN_Scan_Group+"_ImageView"+QC_DisplayTimer.CAN_Scan_Group_Node);
							String TargetName = new String("Test_Group"+QC_DisplayTimer.CAN_Scan_Group+"_ImageView"+QC_DisplayTimer.TestPro_Node);
					    	Map_GroupPane_ImageView.get(TargetName).setImage(Image_target);
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
				if(newval.toString().equals(new String("NodeName_Focus")))
				{
					NodeName_RequestFocus();
				}
				else if(newval.toString().equals(new String("ok")))
				{

				}
				DataProperty_Main.set("Change");
			}
    	});
    	//@5-信息监听器
    	InfoProperty_Main.addListener(Changelisten3=new ChangeListener(){
			@Override
			public void changed(ObservableValue arg0, Object oldval, Object newval) {
				// TODO Auto-generated method stub
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						Aodi_Info_Put(newval.toString());
					}
				});
			}
    	});
    }


    /**产品型号初始化
     *
     */
    private void PudModel_Init()
    {

    	//@-选择项清空
    	Aodi_PudModel_Sel_ComboBox.getItems().clear();
    	Test_PudModel_Sel_ComboBox.getItems().clear();

    	//@-加载选项项
    	for(int i=0;i<ScreensFramework.AODI_PubModel.PudModel_Num;i++)
    	{
    		Test_PudModel_Sel_ComboBox.getItems().add(""+ScreensFramework.AODI_PubModel.AODI_PudModel[i].Get_PudModel_Name());
    		Aodi_PudModel_Sel_ComboBox.getItems().add(""+ScreensFramework.AODI_PubModel.AODI_PudModel[i].Get_PudModel_Name());
    	}

    }


    /**HashMap初始化
     *
     */
    private void HashMap_Parameter_Init()
    {
    	//@-数据精度格式
		formater_data.setMaximumIntegerDigits(2);
		formater_data.setMinimumIntegerDigits(2);

    	//@-背景资源添加至HashMap
    	Map_Rectangle.put("Test_Target1_Rectangle",Test_Target1_Rectangle);
    	Map_Rectangle.put("Test_Target2_Rectangle",Test_Target2_Rectangle);
    	Map_Rectangle.put("Test_Target3_Rectangle",Test_Target3_Rectangle);
    	Map_Rectangle.put("Test_Target4_Rectangle",Test_Target4_Rectangle);
    	Map_Rectangle.put("Test_Target5_Rectangle",Test_Target5_Rectangle);
    	Map_Rectangle.put("Test_Target6_Rectangle",Test_Target6_Rectangle);
    	Map_Rectangle.put("Test_Target7_Rectangle",Test_Target7_Rectangle);
    	Map_Rectangle.put("Test_Target8_Rectangle",Test_Target8_Rectangle);
    	Map_Rectangle.put("Test_Target9_Rectangle",Test_Target9_Rectangle);
    	Map_Rectangle.put("Test_Target10_Rectangle",Test_Target10_Rectangle);
    	//@-Group指示灯资源添加至HashMap
    	Map_GroupImageView.put("Test_Group1_ImageView", Test_Group1_ImageView);
       	Map_GroupImageView.put("Test_Group2_ImageView", Test_Group2_ImageView);
       	Map_GroupImageView.put("Test_Group3_ImageView", Test_Group3_ImageView);
       	Map_GroupImageView.put("Test_Group4_ImageView", Test_Group4_ImageView);
       	Map_GroupImageView.put("Test_Group5_ImageView", Test_Group5_ImageView);
       	Map_GroupImageView.put("Test_Group6_ImageView", Test_Group6_ImageView);
       	Map_GroupImageView.put("Test_Group7_ImageView", Test_Group7_ImageView);
       	Map_GroupImageView.put("Test_Group8_ImageView", Test_Group8_ImageView);
       	Map_GroupImageView.put("Test_Group9_ImageView", Test_Group9_ImageView);
       	Map_GroupImageView.put("Test_Group10_ImageView", Test_Group10_ImageView);
    	//@-老化对象名资源添加至HashMap
       	Map_Name.put("Test_Target1_Name_Label", Test_Target1_Name_Label);
       	Map_Name.put("Test_Target2_Name_Label", Test_Target2_Name_Label);
       	Map_Name.put("Test_Target3_Name_Label", Test_Target3_Name_Label);
       	Map_Name.put("Test_Target4_Name_Label", Test_Target4_Name_Label);
       	Map_Name.put("Test_Target5_Name_Label", Test_Target5_Name_Label);
       	Map_Name.put("Test_Target6_Name_Label", Test_Target6_Name_Label);
       	Map_Name.put("Test_Target7_Name_Label", Test_Target7_Name_Label);
       	Map_Name.put("Test_Target8_Name_Label", Test_Target8_Name_Label);
       	Map_Name.put("Test_Target9_Name_Label", Test_Target9_Name_Label);
       	Map_Name.put("Test_Target10_Name_Label", Test_Target10_Name_Label);
    	//@-老化对象时间标题名HashMap
       	Map_TimeName.put("Test_Target1_TimeName_Label", Test_Target1_TimeName_Label);
       	Map_TimeName.put("Test_Target2_TimeName_Label", Test_Target2_TimeName_Label);
       	Map_TimeName.put("Test_Target3_TimeName_Label", Test_Target3_TimeName_Label);
       	Map_TimeName.put("Test_Target4_TimeName_Label", Test_Target4_TimeName_Label);
       	Map_TimeName.put("Test_Target5_TimeName_Label", Test_Target5_TimeName_Label);
       	Map_TimeName.put("Test_Target6_TimeName_Label", Test_Target6_TimeName_Label);
       	Map_TimeName.put("Test_Target7_TimeName_Label", Test_Target7_TimeName_Label);
       	Map_TimeName.put("Test_Target8_TimeName_Label", Test_Target8_TimeName_Label);
       	Map_TimeName.put("Test_Target9_TimeName_Label", Test_Target9_TimeName_Label);
       	Map_TimeName.put("Test_Target10_TimeName_Label", Test_Target10_TimeName_Label);
    	//@-老化运行时间资源添加至HashMap
       	Map_RunTime.put("Test_Target1_RunTime_Label", Test_Target1_RunTime_Label);
       	Map_RunTime.put("Test_Target2_RunTime_Label", Test_Target2_RunTime_Label);
       	Map_RunTime.put("Test_Target3_RunTime_Label", Test_Target3_RunTime_Label);
       	Map_RunTime.put("Test_Target4_RunTime_Label", Test_Target4_RunTime_Label);
       	Map_RunTime.put("Test_Target5_RunTime_Label", Test_Target5_RunTime_Label);
       	Map_RunTime.put("Test_Target6_RunTime_Label", Test_Target6_RunTime_Label);
       	Map_RunTime.put("Test_Target7_RunTime_Label", Test_Target7_RunTime_Label);
       	Map_RunTime.put("Test_Target8_RunTime_Label", Test_Target8_RunTime_Label);
       	Map_RunTime.put("Test_Target9_RunTime_Label", Test_Target9_RunTime_Label);
       	Map_RunTime.put("Test_Target10_RunTime_Label", Test_Target10_RunTime_Label);
    	//@-界面单元-Group按钮+及- HashMap
       	Map_AddSub_Button.put("Test_Target1_RunTime_Add_Button", Test_Target1_RunTime_Add_Button);
       	Map_AddSub_Button.put("Test_Target1_RunTime_Sub_Button", Test_Target1_RunTime_Sub_Button);
       	Map_AddSub_Button.put("Test_Target2_RunTime_Add_Button", Test_Target2_RunTime_Add_Button);
       	Map_AddSub_Button.put("Test_Target2_RunTime_Sub_Button", Test_Target2_RunTime_Sub_Button);
       	Map_AddSub_Button.put("Test_Target3_RunTime_Add_Button", Test_Target3_RunTime_Add_Button);
       	Map_AddSub_Button.put("Test_Target3_RunTime_Sub_Button", Test_Target3_RunTime_Sub_Button);
       	Map_AddSub_Button.put("Test_Target4_RunTime_Add_Button", Test_Target4_RunTime_Add_Button);
       	Map_AddSub_Button.put("Test_Target4_RunTime_Sub_Button", Test_Target4_RunTime_Sub_Button);
       	Map_AddSub_Button.put("Test_Target5_RunTime_Add_Button", Test_Target5_RunTime_Add_Button);
       	Map_AddSub_Button.put("Test_Target5_RunTime_Sub_Button", Test_Target5_RunTime_Sub_Button);
       	Map_AddSub_Button.put("Test_Target6_RunTime_Add_Button", Test_Target6_RunTime_Add_Button);
       	Map_AddSub_Button.put("Test_Target6_RunTime_Sub_Button", Test_Target6_RunTime_Sub_Button);
       	Map_AddSub_Button.put("Test_Target7_RunTime_Add_Button", Test_Target7_RunTime_Add_Button);
       	Map_AddSub_Button.put("Test_Target7_RunTime_Sub_Button", Test_Target7_RunTime_Sub_Button);
       	Map_AddSub_Button.put("Test_Target8_RunTime_Add_Button", Test_Target8_RunTime_Add_Button);
       	Map_AddSub_Button.put("Test_Target8_RunTime_Sub_Button", Test_Target8_RunTime_Sub_Button);
       	Map_AddSub_Button.put("Test_Target9_RunTime_Add_Button", Test_Target9_RunTime_Add_Button);
       	Map_AddSub_Button.put("Test_Target9_RunTime_Sub_Button", Test_Target9_RunTime_Sub_Button);
       	Map_AddSub_Button.put("Test_Target10_RunTime_Add_Button", Test_Target10_RunTime_Add_Button);
       	Map_AddSub_Button.put("Test_Target10_RunTime_Sub_Button", Test_Target10_RunTime_Sub_Button);
    	//@-Group指示灯装载容器资源添加至HashMap
       	Map_GroupPane.put("Test_Group1_Pane", Test_Group1_Pane);
       	Map_GroupPane.put("Test_Group2_Pane", Test_Group2_Pane);
       	Map_GroupPane.put("Test_Group3_Pane", Test_Group3_Pane);
       	Map_GroupPane.put("Test_Group4_Pane", Test_Group4_Pane);
       	Map_GroupPane.put("Test_Group5_Pane", Test_Group5_Pane);
       	Map_GroupPane.put("Test_Group6_Pane", Test_Group6_Pane);
       	Map_GroupPane.put("Test_Group7_Pane", Test_Group7_Pane);
       	Map_GroupPane.put("Test_Group8_Pane", Test_Group8_Pane);
       	Map_GroupPane.put("Test_Group9_Pane", Test_Group9_Pane);
       	Map_GroupPane.put("Test_Group10_Pane", Test_Group10_Pane);
       	//@-Group"Button1"按钮资源添加至HashMap
       	Map_Button1.put("Test_Target1_Button1", Test_Target1_Button1);
       	Map_Button1.put("Test_Target2_Button1", Test_Target2_Button1);
       	Map_Button1.put("Test_Target3_Button1", Test_Target3_Button1);
       	Map_Button1.put("Test_Target4_Button1", Test_Target4_Button1);
       	Map_Button1.put("Test_Target5_Button1", Test_Target5_Button1);
       	Map_Button1.put("Test_Target6_Button1", Test_Target6_Button1);
       	Map_Button1.put("Test_Target7_Button1", Test_Target7_Button1);
       	Map_Button1.put("Test_Target8_Button1", Test_Target8_Button1);
       	Map_Button1.put("Test_Target9_Button1", Test_Target9_Button1);
       	Map_Button1.put("Test_Target10_Button1", Test_Target10_Button1);
       	//@-Group"Button2"按钮资源添加至HashMap
       	Map_Button2.put("Test_Target1_Button2", Test_Target1_Button2);
       	Map_Button2.put("Test_Target2_Button2", Test_Target2_Button2);
       	Map_Button2.put("Test_Target3_Button2", Test_Target3_Button2);
       	Map_Button2.put("Test_Target4_Button2", Test_Target4_Button2);
       	Map_Button2.put("Test_Target5_Button2", Test_Target5_Button2);
       	Map_Button2.put("Test_Target6_Button2", Test_Target6_Button2);
       	Map_Button2.put("Test_Target7_Button2", Test_Target7_Button2);
       	Map_Button2.put("Test_Target8_Button2", Test_Target8_Button2);
       	Map_Button2.put("Test_Target9_Button2", Test_Target9_Button2);
       	Map_Button2.put("Test_Target10_Button2", Test_Target10_Button2);
       	//@-Node老化对象“启动/停止”按钮 HashMap
       	Map_TestRun_Button.put("Test_Target1_TestRun_Button", Test_Target1_TestRun_Button);
       	Map_TestRun_Button.put("Test_Target2_TestRun_Button", Test_Target2_TestRun_Button);
       	Map_TestRun_Button.put("Test_Target3_TestRun_Button", Test_Target3_TestRun_Button);
       	Map_TestRun_Button.put("Test_Target4_TestRun_Button", Test_Target4_TestRun_Button);
       	Map_TestRun_Button.put("Test_Target5_TestRun_Button", Test_Target5_TestRun_Button);
       	Map_TestRun_Button.put("Test_Target6_TestRun_Button", Test_Target6_TestRun_Button);
       	Map_TestRun_Button.put("Test_Target7_TestRun_Button", Test_Target7_TestRun_Button);
       	Map_TestRun_Button.put("Test_Target8_TestRun_Button", Test_Target8_TestRun_Button);
       	Map_TestRun_Button.put("Test_Target9_TestRun_Button", Test_Target9_TestRun_Button);
       	Map_TestRun_Button.put("Test_Target10_TestRun_Button", Test_Target10_TestRun_Button);
       	//@-Node“离组原因”选择框资源添加至HashMap
    	Map_LeaveReason_ChoiceBox.put("Test_Target1_LeaveReason_ChoiceBox", Test_Target1_LeaveReason_ChoiceBox);
    	Map_LeaveReason_ChoiceBox.put("Test_Target2_LeaveReason_ChoiceBox", Test_Target2_LeaveReason_ChoiceBox);
    	Map_LeaveReason_ChoiceBox.put("Test_Target3_LeaveReason_ChoiceBox", Test_Target3_LeaveReason_ChoiceBox);
    	Map_LeaveReason_ChoiceBox.put("Test_Target4_LeaveReason_ChoiceBox", Test_Target4_LeaveReason_ChoiceBox);
    	Map_LeaveReason_ChoiceBox.put("Test_Target5_LeaveReason_ChoiceBox", Test_Target5_LeaveReason_ChoiceBox);
    	Map_LeaveReason_ChoiceBox.put("Test_Target6_LeaveReason_ChoiceBox", Test_Target6_LeaveReason_ChoiceBox);
    	Map_LeaveReason_ChoiceBox.put("Test_Target7_LeaveReason_ChoiceBox", Test_Target7_LeaveReason_ChoiceBox);
    	Map_LeaveReason_ChoiceBox.put("Test_Target8_LeaveReason_ChoiceBox", Test_Target8_LeaveReason_ChoiceBox);
    	Map_LeaveReason_ChoiceBox.put("Test_Target9_LeaveReason_ChoiceBox", Test_Target9_LeaveReason_ChoiceBox);
    	Map_LeaveReason_ChoiceBox.put("Test_Target10_LeaveReason_ChoiceBox", Test_Target10_LeaveReason_ChoiceBox);
       	//@-Node“离组原因”文字资源添加至HashMap
    	Map_LeaveReason_Label.put("Test_Target1_LeaveReason_Label", Test_Target1_LeaveReason_Label);
    	Map_LeaveReason_Label.put("Test_Target2_LeaveReason_Label", Test_Target2_LeaveReason_Label);
    	Map_LeaveReason_Label.put("Test_Target3_LeaveReason_Label", Test_Target3_LeaveReason_Label);
    	Map_LeaveReason_Label.put("Test_Target4_LeaveReason_Label", Test_Target4_LeaveReason_Label);
    	Map_LeaveReason_Label.put("Test_Target5_LeaveReason_Label", Test_Target5_LeaveReason_Label);
    	Map_LeaveReason_Label.put("Test_Target6_LeaveReason_Label", Test_Target6_LeaveReason_Label);
    	Map_LeaveReason_Label.put("Test_Target7_LeaveReason_Label", Test_Target7_LeaveReason_Label);
    	Map_LeaveReason_Label.put("Test_Target8_LeaveReason_Label", Test_Target8_LeaveReason_Label);
    	Map_LeaveReason_Label.put("Test_Target9_LeaveReason_Label", Test_Target9_LeaveReason_Label);
    	Map_LeaveReason_Label.put("Test_Target10_LeaveReason_Label", Test_Target10_LeaveReason_Label);

    	//@-Group1指示灯资源添加至HashMap
       	Map_GroupPane_ImageView.put("Test_Group1_ImageView1", Test_Group1_ImageView1);
       	Map_GroupPane_ImageView.put("Test_Group1_ImageView2", Test_Group1_ImageView2);
       	Map_GroupPane_ImageView.put("Test_Group1_ImageView3", Test_Group1_ImageView3);
       	Map_GroupPane_ImageView.put("Test_Group1_ImageView4", Test_Group1_ImageView4);
       	Map_GroupPane_ImageView.put("Test_Group1_ImageView5", Test_Group1_ImageView5);
       	Map_GroupPane_ImageView.put("Test_Group1_ImageView6", Test_Group1_ImageView6);
       	Map_GroupPane_ImageView.put("Test_Group1_ImageView7", Test_Group1_ImageView7);
       	Map_GroupPane_ImageView.put("Test_Group1_ImageView8", Test_Group1_ImageView8);
       	Map_GroupPane_ImageView.put("Test_Group1_ImageView9", Test_Group1_ImageView9);
       	Map_GroupPane_ImageView.put("Test_Group1_ImageView10", Test_Group1_ImageView10);
    	//@-Group2指示灯资源添加至HashMap
       	Map_GroupPane_ImageView.put("Test_Group2_ImageView1", Test_Group2_ImageView1);
       	Map_GroupPane_ImageView.put("Test_Group2_ImageView2", Test_Group2_ImageView2);
       	Map_GroupPane_ImageView.put("Test_Group2_ImageView3", Test_Group2_ImageView3);
       	Map_GroupPane_ImageView.put("Test_Group2_ImageView4", Test_Group2_ImageView4);
       	Map_GroupPane_ImageView.put("Test_Group2_ImageView5", Test_Group2_ImageView5);
       	Map_GroupPane_ImageView.put("Test_Group2_ImageView6", Test_Group2_ImageView6);
       	Map_GroupPane_ImageView.put("Test_Group2_ImageView7", Test_Group2_ImageView7);
       	Map_GroupPane_ImageView.put("Test_Group2_ImageView8", Test_Group2_ImageView8);
       	Map_GroupPane_ImageView.put("Test_Group2_ImageView9", Test_Group2_ImageView9);
       	Map_GroupPane_ImageView.put("Test_Group2_ImageView10", Test_Group2_ImageView10);
    	//@-Group3指示灯资源添加至HashMap
       	Map_GroupPane_ImageView.put("Test_Group3_ImageView1", Test_Group3_ImageView1);
       	Map_GroupPane_ImageView.put("Test_Group3_ImageView2", Test_Group3_ImageView2);
       	Map_GroupPane_ImageView.put("Test_Group3_ImageView3", Test_Group3_ImageView3);
       	Map_GroupPane_ImageView.put("Test_Group3_ImageView4", Test_Group3_ImageView4);
       	Map_GroupPane_ImageView.put("Test_Group3_ImageView5", Test_Group3_ImageView5);
       	Map_GroupPane_ImageView.put("Test_Group3_ImageView6", Test_Group3_ImageView6);
       	Map_GroupPane_ImageView.put("Test_Group3_ImageView7", Test_Group3_ImageView7);
       	Map_GroupPane_ImageView.put("Test_Group3_ImageView8", Test_Group3_ImageView8);
       	Map_GroupPane_ImageView.put("Test_Group3_ImageView9", Test_Group3_ImageView9);
       	Map_GroupPane_ImageView.put("Test_Group3_ImageView10", Test_Group3_ImageView10);
    	//@-Group4指示灯资源添加至HashMap
       	Map_GroupPane_ImageView.put("Test_Group4_ImageView1", Test_Group4_ImageView1);
       	Map_GroupPane_ImageView.put("Test_Group4_ImageView2", Test_Group4_ImageView2);
       	Map_GroupPane_ImageView.put("Test_Group4_ImageView3", Test_Group4_ImageView3);
       	Map_GroupPane_ImageView.put("Test_Group4_ImageView4", Test_Group4_ImageView4);
       	Map_GroupPane_ImageView.put("Test_Group4_ImageView5", Test_Group4_ImageView5);
       	Map_GroupPane_ImageView.put("Test_Group4_ImageView6", Test_Group4_ImageView6);
       	Map_GroupPane_ImageView.put("Test_Group4_ImageView7", Test_Group4_ImageView7);
       	Map_GroupPane_ImageView.put("Test_Group4_ImageView8", Test_Group4_ImageView8);
       	Map_GroupPane_ImageView.put("Test_Group4_ImageView9", Test_Group4_ImageView9);
       	Map_GroupPane_ImageView.put("Test_Group4_ImageView10", Test_Group4_ImageView10);
    	//@-Group5指示灯资源添加至HashMap
       	Map_GroupPane_ImageView.put("Test_Group5_ImageView1", Test_Group5_ImageView1);
       	Map_GroupPane_ImageView.put("Test_Group5_ImageView2", Test_Group5_ImageView2);
       	Map_GroupPane_ImageView.put("Test_Group5_ImageView3", Test_Group5_ImageView3);
       	Map_GroupPane_ImageView.put("Test_Group5_ImageView4", Test_Group5_ImageView4);
       	Map_GroupPane_ImageView.put("Test_Group5_ImageView5", Test_Group5_ImageView5);
       	Map_GroupPane_ImageView.put("Test_Group5_ImageView6", Test_Group5_ImageView6);
       	Map_GroupPane_ImageView.put("Test_Group5_ImageView7", Test_Group5_ImageView7);
       	Map_GroupPane_ImageView.put("Test_Group5_ImageView8", Test_Group5_ImageView8);
       	Map_GroupPane_ImageView.put("Test_Group5_ImageView9", Test_Group5_ImageView9);
       	Map_GroupPane_ImageView.put("Test_Group5_ImageView10", Test_Group5_ImageView10);
    	//@-Group6指示灯资源添加至HashMap
       	Map_GroupPane_ImageView.put("Test_Group6_ImageView1", Test_Group6_ImageView1);
       	Map_GroupPane_ImageView.put("Test_Group6_ImageView2", Test_Group6_ImageView2);
       	Map_GroupPane_ImageView.put("Test_Group6_ImageView3", Test_Group6_ImageView3);
       	Map_GroupPane_ImageView.put("Test_Group6_ImageView4", Test_Group6_ImageView4);
       	Map_GroupPane_ImageView.put("Test_Group6_ImageView5", Test_Group6_ImageView5);
       	Map_GroupPane_ImageView.put("Test_Group6_ImageView6", Test_Group6_ImageView6);
       	Map_GroupPane_ImageView.put("Test_Group6_ImageView7", Test_Group6_ImageView7);
       	Map_GroupPane_ImageView.put("Test_Group6_ImageView8", Test_Group6_ImageView8);
       	Map_GroupPane_ImageView.put("Test_Group6_ImageView9", Test_Group6_ImageView9);
       	Map_GroupPane_ImageView.put("Test_Group6_ImageView10", Test_Group6_ImageView10);
    	//@-Group7指示灯资源添加至HashMap
       	Map_GroupPane_ImageView.put("Test_Group7_ImageView1", Test_Group7_ImageView1);
       	Map_GroupPane_ImageView.put("Test_Group7_ImageView2", Test_Group7_ImageView2);
       	Map_GroupPane_ImageView.put("Test_Group7_ImageView3", Test_Group7_ImageView3);
       	Map_GroupPane_ImageView.put("Test_Group7_ImageView4", Test_Group7_ImageView4);
       	Map_GroupPane_ImageView.put("Test_Group7_ImageView5", Test_Group7_ImageView5);
       	Map_GroupPane_ImageView.put("Test_Group7_ImageView6", Test_Group7_ImageView6);
       	Map_GroupPane_ImageView.put("Test_Group7_ImageView7", Test_Group7_ImageView7);
       	Map_GroupPane_ImageView.put("Test_Group7_ImageView8", Test_Group7_ImageView8);
       	Map_GroupPane_ImageView.put("Test_Group7_ImageView9", Test_Group7_ImageView9);
       	Map_GroupPane_ImageView.put("Test_Group7_ImageView10", Test_Group7_ImageView10);
    	//@-Group8指示灯资源添加至HashMap
       	Map_GroupPane_ImageView.put("Test_Group8_ImageView1", Test_Group8_ImageView1);
       	Map_GroupPane_ImageView.put("Test_Group8_ImageView2", Test_Group8_ImageView2);
       	Map_GroupPane_ImageView.put("Test_Group8_ImageView3", Test_Group8_ImageView3);
       	Map_GroupPane_ImageView.put("Test_Group8_ImageView4", Test_Group8_ImageView4);
       	Map_GroupPane_ImageView.put("Test_Group8_ImageView5", Test_Group8_ImageView5);
       	Map_GroupPane_ImageView.put("Test_Group8_ImageView6", Test_Group8_ImageView6);
       	Map_GroupPane_ImageView.put("Test_Group8_ImageView7", Test_Group8_ImageView7);
       	Map_GroupPane_ImageView.put("Test_Group8_ImageView8", Test_Group8_ImageView8);
       	Map_GroupPane_ImageView.put("Test_Group8_ImageView9", Test_Group8_ImageView9);
       	Map_GroupPane_ImageView.put("Test_Group8_ImageView10", Test_Group8_ImageView10);
    	//@-Group9指示灯资源添加至HashMap
       	Map_GroupPane_ImageView.put("Test_Group9_ImageView1", Test_Group9_ImageView1);
       	Map_GroupPane_ImageView.put("Test_Group9_ImageView2", Test_Group9_ImageView2);
       	Map_GroupPane_ImageView.put("Test_Group9_ImageView3", Test_Group9_ImageView3);
       	Map_GroupPane_ImageView.put("Test_Group9_ImageView4", Test_Group9_ImageView4);
       	Map_GroupPane_ImageView.put("Test_Group9_ImageView5", Test_Group9_ImageView5);
       	Map_GroupPane_ImageView.put("Test_Group9_ImageView6", Test_Group9_ImageView6);
       	Map_GroupPane_ImageView.put("Test_Group9_ImageView7", Test_Group9_ImageView7);
       	Map_GroupPane_ImageView.put("Test_Group9_ImageView8", Test_Group9_ImageView8);
       	Map_GroupPane_ImageView.put("Test_Group9_ImageView9", Test_Group9_ImageView9);
       	Map_GroupPane_ImageView.put("Test_Group9_ImageView10", Test_Group9_ImageView10);
    	//@-Group10指示灯资源添加至HashMap
       	Map_GroupPane_ImageView.put("Test_Group10_ImageView1", Test_Group10_ImageView1);
       	Map_GroupPane_ImageView.put("Test_Group10_ImageView2", Test_Group10_ImageView2);
       	Map_GroupPane_ImageView.put("Test_Group10_ImageView3", Test_Group10_ImageView3);
       	Map_GroupPane_ImageView.put("Test_Group10_ImageView4", Test_Group10_ImageView4);
       	Map_GroupPane_ImageView.put("Test_Group10_ImageView5", Test_Group10_ImageView5);
       	Map_GroupPane_ImageView.put("Test_Group10_ImageView6", Test_Group10_ImageView6);
       	Map_GroupPane_ImageView.put("Test_Group10_ImageView7", Test_Group10_ImageView7);
       	Map_GroupPane_ImageView.put("Test_Group10_ImageView8", Test_Group10_ImageView8);
       	Map_GroupPane_ImageView.put("Test_Group10_ImageView9", Test_Group10_ImageView9);
       	Map_GroupPane_ImageView.put("Test_Group10_ImageView10", Test_Group10_ImageView10);

       	//------------------------------------------------------------------------------------------
    	//@-Tab1-节点编号输入框资源添加至HashMap
       	Map_Tab1_NodeName_TextField.put("Test_Tab1_Node1_Name_TextField", Test_Tab1_Node1_Name_TextField);
       	Map_Tab1_NodeName_TextField.put("Test_Tab1_Node2_Name_TextField", Test_Tab1_Node2_Name_TextField);
       	Map_Tab1_NodeName_TextField.put("Test_Tab1_Node3_Name_TextField", Test_Tab1_Node3_Name_TextField);
       	Map_Tab1_NodeName_TextField.put("Test_Tab1_Node4_Name_TextField", Test_Tab1_Node4_Name_TextField);
       	Map_Tab1_NodeName_TextField.put("Test_Tab1_Node5_Name_TextField", Test_Tab1_Node5_Name_TextField);
       	Map_Tab1_NodeName_TextField.put("Test_Tab1_Node6_Name_TextField", Test_Tab1_Node6_Name_TextField);
       	Map_Tab1_NodeName_TextField.put("Test_Tab1_Node7_Name_TextField", Test_Tab1_Node7_Name_TextField);
       	Map_Tab1_NodeName_TextField.put("Test_Tab1_Node8_Name_TextField", Test_Tab1_Node8_Name_TextField);
       	Map_Tab1_NodeName_TextField.put("Test_Tab1_Node9_Name_TextField", Test_Tab1_Node9_Name_TextField);
       	Map_Tab1_NodeName_TextField.put("Test_Tab1_Node10_Name_TextField", Test_Tab1_Node10_Name_TextField);


    }

    /**
     *
     */
    private void GUI_Parameter_Init()
    {
        int Group;
        int Index;
        String TargetName;

    	//@1-图片资源初始化
        Image_config_no = new Image(QC_Controller.class.getResourceAsStream("warning.png"));
        Image_config_yes = new Image(QC_Controller.class.getResourceAsStream("info.png"));
		Image_state_ok = new Image(QC_Controller.class.getResourceAsStream("status_green.png"));
		Image_state_error = new Image(QC_Controller.class.getResourceAsStream("status_red.png"));
		Image_state_warning = new Image(QC_Controller.class.getResourceAsStream("status_yellow.png"));
		Image_state_end  = new Image(QC_Controller.class.getResourceAsStream("success.png"));

		Image_state_ok_fang = new Image(QC_Controller.class.getResourceAsStream("statusbar_message_light_green.png"));
		Image_state_error_fang = new Image(QC_Controller.class.getResourceAsStream("statusbar_message_light_red.png"));
		Image_state_warning_fang = new Image(QC_Controller.class.getResourceAsStream("statusbar_message_light_orange.png"));

		Image_target = new Image(QC_Controller.class.getResourceAsStream("target.png"));


		//@-所有组背景初始化
		for(Group=1; Group<=10; Group++)
		{
	    	TargetName = new String("Test_Target"+Group+"_Rectangle");
	        Map_Rectangle.get(TargetName).setId("Rectangle_Color_Yellow");
		}
		//@-Group指示灯初始化
		for(Group=1; Group<=10; Group++)
		{
	    	TargetName = new String("Test_Group"+Group+"_ImageView");
	    	Map_GroupImageView.get(TargetName).setImage(Image_state_warning);
		}
		//@-老化对象名灯初始化
		for(Group=1; Group<=10; Group++)
		{
	    	TargetName = new String("Test_Target"+Group+"_Name_Label");
	    	Map_Name.get(TargetName).setText("老化组"+Group);
		}
		//@-老化运行时间初始化
		for(Group=1; Group<=10; Group++)
		{
	    	TargetName = new String("Test_Target"+Group+"_RunTime_Label");
	    	Map_RunTime.get(TargetName).setText("00:00:00");
		}
		//@-Group指示灯装载容器初始化
		for(Group=1; Group<=10; Group++)
		{
	    	TargetName = new String("Test_Group"+Group+"_Pane");
	    	Map_GroupPane.get(TargetName).setVisible(true);
		}
		//@-Group指示灯初始化
		for(Group=1; Group<=10; Group++)
		{
			for(Index=1;Index<=10;Index++)
			{
		    	TargetName = new String("Test_Group"+Group+"_ImageView"+Index);
		    	Map_GroupPane_ImageView.get(TargetName).setImage(Image_config_no);
			}
		}
		//----------------------------------------
		//@-Tab1-节点编号录入按钮-不激活
		Test_Tab1_InputName_Button.setDisable(true);
		//@-Tab1-型号选择-不激活
		Test_PudModel_Sel_ComboBox.setDisable(true);

		//@-Tab1-节点编号输入框-不激活&挂载监听器
		for(Index=1;Index<=10;Index++)
		{
			//Test_Tab1_Node7_Name_TextField
	    	TargetName = new String("Test_Tab1_Node"+Index+"_Name_TextField");
	    	Map_Tab1_NodeName_TextField.get(TargetName).addEventFilter(KeyEvent.KEY_TYPED , numeric_Validation(20));
	    	Map_Tab1_NodeName_TextField.get(TargetName).setDisable(true);
		}
		//----------------------------------------
		//@-数据采集模式选择初始化
		Aodi_DataSample_Mode_ComboBox.getItems().clear();
		Aodi_DataSample_Mode_ComboBox.getItems().add("恒定采样模式");
		Aodi_DataSample_Mode_ComboBox.getItems().add("可变采样模式");
		Aodi_DataSample_Mode_ComboBox.getSelectionModel().selectFirst();
		//---------------------------------------------
		//@-预警处理模式选择初始化
		Aodi_AlarmMode_ComboBox.getItems().clear();
		Aodi_AlarmMode_ComboBox.getItems().add("仅故障指示灯指示");
		Aodi_AlarmMode_ComboBox.getItems().add("仅鸣音器告警");
		Aodi_AlarmMode_ComboBox.getItems().add("故障指示+鸣音器告警");
		Aodi_AlarmMode_ComboBox.getSelectionModel().select(2);
		//---------------------------------------------
		//@-故障应急处理Group选择初始化
		Aodi_AlarmPro_Group_ComboBox.getItems().clear();
		for(int i=1;i<11;i++)
		{
			Aodi_AlarmPro_Group_ComboBox.getItems().add("组"+(i));
		}
		Aodi_AlarmPro_Group_ComboBox.getSelectionModel().selectFirst();
		//@-故障应急处理Node选择初始化
		Aodi_AlarmPro_Node_ComboBox.getItems().clear();
		for(int i=1;i<11;i++)
		{
			Aodi_AlarmPro_Node_ComboBox.getItems().add("节点"+(i));
		}
		Aodi_AlarmPro_Node_ComboBox.getSelectionModel().selectFirst();
    }

    /**Group与Node之间切换
     *
     * @param Mode
     * @param ID Group编号
     */
    private void Group_Node_Switch(int Mode,int ID)
    {
    	int Index;
        String TargetName;
        String Str1;   //--------

        //@-Group--->Node切换
        if(Mode==1)
        {
			//@-切换显示层
			GroupRunLevel=2;

			//@-老化对象名初始化
			for(Index=1; Index<=10; Index++)
			{
		    	TargetName = new String("Test_Target"+Index+"_Name_Label");
		    	//@获得节点DZFZ名字
		    	Str1 = new String(ScreensFramework.DZFZ_Group[RunLevel2_GroupNum].Group_DZFZ[Index-1].Get_Pud_ID());
		    	Map_Name.get(TargetName).setText(Str1);
			}
			//@-Group指示灯装载容器
			for(Index=1; Index<=10; Index++)
			{
		    	TargetName = new String("Test_Group"+Index+"_Pane");
		    	Map_GroupPane.get(TargetName).setVisible(false);
			}
			//@-Button1改名
			for(Index=1; Index<=10; Index++)
			{
		    	TargetName = new String("Test_Target"+Index+"_Button1");
		    	Map_Button1.get(TargetName).setText("节点详情");
			}
			//@-Button2改名
			for(Index=1; Index<=10; Index++)
			{
		    	TargetName = new String("Test_Target"+Index+"_Button2");
		    	Map_Button2.get(TargetName).setText("返回");
			}
			//@-显示Node老化对象“启动/停止”按钮
			for(Index=1; Index<=10; Index++)
			{
		    	TargetName = new String("Test_Target"+Index+"_TestRun_Button");
		    	Map_TestRun_Button.get(TargetName).setVisible(true);
			}
			//@-显示“离组原因”选择框
			for(Index=1; Index<=10; Index++)
			{
		    	TargetName = new String("Test_Target"+Index+"_LeaveReason_ChoiceBox");
		    	Map_LeaveReason_ChoiceBox.get(TargetName).setVisible(true);
			}
			//@-显示“离组原因”文字
			for(Index=1; Index<=10; Index++)
			{
		    	TargetName = new String("Test_Target"+Index+"_LeaveReason_Label");
		    	Map_LeaveReason_Label.get(TargetName).setVisible(true);
			}
			//------------------------------------------------------------
			//@-Tab1-Group名显示
			Test_Tab1_Group_Name_Label.setText("老化组"+(ID+1));
			//@-Tab1-节点编号录入按钮-激活
			Test_Tab1_InputName_Button.setDisable(false);
			//@-Tab1-节点编号录入按钮功能切换初始态
			Tab1_InputName_Button_Flag = 0;
			//@-节点编号输入框内容与相应的Group中的Node名同步
			for(Index=1; Index<=10; Index++)
			{
		    	TargetName = new String("Test_Tab1_Node"+Index+"_Name_TextField");
		    	Map_Tab1_NodeName_TextField.get(TargetName).setText(ScreensFramework.DZFZ_Group[ID].Group_DZFZ[(Index-1)].Get_Pud_ID());
			}
        }
        //@-Node--->Group切换
        else if(Mode==2)
        {
			//@-切换显示层
			GroupRunLevel=1;

			//@-老化组名灯初始化
			for(Index=1; Index<=10; Index++)
			{
		    	TargetName = new String("Test_Target"+Index+"_Name_Label");
		    	Map_Name.get(TargetName).setText("老化组"+Index);
			}
			//@-Group指示灯装载容器
			for(Index=1; Index<=10; Index++)
			{
		    	TargetName = new String("Test_Group"+Index+"_Pane");
		    	Map_GroupPane.get(TargetName).setVisible(true);
			}
			//@-Button1改名
			for(Index=1; Index<=10; Index++)
			{
		    	TargetName = new String("Test_Target"+Index+"_Button1");
		    	if((ScreensFramework.DZFZ_Group[(Index-1)].Get_Group_TimerRunFlag())==0)
		    	Map_Button1.get(TargetName).setText("开始老化");
		    	else if((ScreensFramework.DZFZ_Group[(Index-1)].Get_Group_TimerRunFlag())==1)
		    	Map_Button1.get(TargetName).setText("停止老化");
			}
			//@-Button2改名
			for(Index=1; Index<=10; Index++)
			{
		    	TargetName = new String("Test_Target"+Index+"_Button2");
		    	Map_Button2.get(TargetName).setText("编辑组");
			}
			//@-隐藏Node老化对象“启动/停止”按钮
			for(Index=1; Index<=10; Index++)
			{
		    	TargetName = new String("Test_Target"+Index+"_TestRun_Button");
		    	Map_TestRun_Button.get(TargetName).setVisible(false);
			}
			//@-隐藏“离组原因”选择框
			for(Index=1; Index<=10; Index++)
			{
		    	TargetName = new String("Test_Target"+Index+"_LeaveReason_ChoiceBox");
		    	Map_LeaveReason_ChoiceBox.get(TargetName).setVisible(false);
			}
			//@-隐藏“离组原因”文字
			for(Index=1; Index<=10; Index++)
			{
		    	TargetName = new String("Test_Target"+Index+"_LeaveReason_Label");
		    	Map_LeaveReason_Label.get(TargetName).setVisible(false);
			}
			//-------------------------------------------------------------
			//@-Tab1-Group名消隐
			Test_Tab1_Group_Name_Label.setText("----------");
			//@-Tab1-产品型号选择-不激活
			Test_PudModel_Sel_ComboBox.setDisable(true);
			//@-Tab1-节点编号录入按钮-不激活
			Test_Tab1_InputName_Button.setDisable(true);
			//@-Tab1-节点编号录入按钮名消隐
			Test_Tab1_InputName_Button.setText("录入");
			//@-Tab1-节点编号录入按钮功能切换初始态
			Tab1_InputName_Button_Flag = 0;
			//@-不激活节点编号输入框
			for(Index=1; Index<=10; Index++)
			{
		    	TargetName = new String("Test_Tab1_Node"+Index+"_Name_TextField");
		    	Map_Tab1_NodeName_TextField.get(TargetName).setText("-----");
		    	Map_Tab1_NodeName_TextField.get(TargetName).setDisable(true);
			}
        }

    }



    /**
     *
     * @param ID
     */
    private void Group_TimerStart(int ID)
    {
    	//@DX1-需判断网络连接&操作人员录入
    	//@1-Group中已有DZFZ
    	if((ScreensFramework.DZFZ_Group[ID].Get_Group_DZFZ_Count()!=0))
    	{
	    	//@2-Group“开始老化”按钮
	  		if((ScreensFramework.DZFZ_Group[ID].Get_Group_TimerRunFlag())==1)
	  		{
	  			//@-设置Group定时运行标志
	  			ScreensFramework.DZFZ_Group[ID].Set_Group_TimerRunFlag(0);
				//@-所属Group中的Node运行标志
				for(int i=0;i<10;i++)
				{
					ScreensFramework.DZFZ_Group[ID].Group_DZFZ[i].Set_Pud_RunFlag(0);
				}

				//@DX-增加老化开始其他动作-控制报文&配置查询开启
				QC_DisplayTimer.CAN_Send_Group_TestPro_Flag[ID] = true;
				//@-老化停止阶段
				QC_DisplayTimer.TestPro_Stage = 2;
				//@-老化停止阶段-Node重新开始
				//QC_DisplayTimer.TestPro_Node = 1;


	  			//@-"开始老化"按钮文字切换
		    	String TargetName = new String("Test_Target"+(ID+1)+"_Button1");
		    	Map_Button1.get(TargetName).setText("开始老化");

	  		}
			else if((ScreensFramework.DZFZ_Group[ID].Get_Group_TimerRunFlag())==0)
			{
				//@DX1-if(节点监控参数配置是否结束)
				//{

				//@DX1-初始化Group中10个节点参数
				ScreensFramework.DZFZ_Group[ID].Set_Group_Node_Init();
				ScreensFramework.DZFZ_Group[ID].Group_Timer_RunTime = 0;

				//@-设置Group定时运行标志
				ScreensFramework.DZFZ_Group[ID].Set_Group_TimerRunFlag(1);

				//@-所属Group中的Node运行标志
				for(int i=0;i<10;i++)
				{
					//@DX1
					//@-已配置的Node
					if((ScreensFramework.DZFZ_Group[ID].Group_DZFZ[i].Get_Pud_Test_ConfigStatus())==1)
					ScreensFramework.DZFZ_Group[ID].Group_DZFZ[i].Set_Pud_RunFlag(1);
				}

				//@DX-增加老化开始其他动作-控制报文&配置查询开启
				QC_DisplayTimer.CAN_Send_Group_TestPro_Flag[ID] = true;
				//@-老化启动阶段
				QC_DisplayTimer.TestPro_Stage = 1;
				//@-老化启动阶段-Node重新开始
				//QC_DisplayTimer.TestPro_Node = 1;

	  			//@-"开始老化"按钮文字切换
		    	String TargetName = new String("Test_Target"+(ID+1)+"_Button1");
		    	Map_Button1.get(TargetName).setText("停止老化");

			}
	    }
    	//@2-Group中无DZFZ
    	else if(ScreensFramework.DZFZ_Group[ID].Get_Group_DZFZ_Count()==0)
    	{
			//@-Noti输出信息
			ScreensFramework.Show_Noti("Warning", "Group"+(ID+1)+"中没有连接任何电子负载设备！");
    	}

  		//System.out.println("ID"+ID+"_Flag:"+ScreensFramework.DZFZ_Group[ID].Get_Group_TimerRunFlag());
    }



    /**按键监听器
     *
     * @param event
     */
    @FXML
    public void Button_Pro(ActionEvent event)
    {
    	//@-键盘处理在Group层
    	if(GroupRunLevel==1)
    	{
	    	Button_Pro_Group(event);
    	}
    	//@-键盘处理在节点层
    	else if(GroupRunLevel==2)
    	{
    		Button_Pro_Node(event);
    	}

    	//@-产品型号选择
		if(event.getSource()==Aodi_PudModel_Sel_ComboBox)
		{
			//@-获取Index
			int Index = Aodi_PudModel_Sel_ComboBox.getSelectionModel().getSelectedIndex();

			//@-显示电压限制值
			Aodi_PudModel_VoltageLimit_TextField.setText(""+ScreensFramework.AODI_PubModel.AODI_PudModel[Index].Get_PudModel_VoltageLimit());
			//@-显示电压限制值范围
			Aodi_PudModel_VoltageLimit_Range_TextField.setText(""+ScreensFramework.AODI_PubModel.AODI_PudModel[Index].Get_PudModel_VoltageLimit_Range());

			//@-显示电流限制值
			Aodi_PudModel_CurrentLimit_TextField.setText(""+ScreensFramework.AODI_PubModel.AODI_PudModel[Index].Get_PudModel_CurrentLimit());
			//@-显示电流限制值范围
			Aodi_PudModel_CurrentLimit_Range_TextField.setText(""+ScreensFramework.AODI_PubModel.AODI_PudModel[Index].Get_PudModel_CurrentLimit_Range());

			//@-显示温度1限制值
			Aodi_PudModel_Temperature1Limit_TextField.setText(""+ScreensFramework.AODI_PubModel.AODI_PudModel[Index].Get_PudModel_Temperature1Limit());
			//@-显示温度1限制值范围
			Aodi_PudModel_Temperature1Limit_Range_TextField.setText(""+ScreensFramework.AODI_PubModel.AODI_PudModel[Index].Get_PudModel_Temperature1Limit_Range());
			//@-显示温度2限制值
			Aodi_PudModel_Temperature2Limit_TextField.setText(""+ScreensFramework.AODI_PubModel.AODI_PudModel[Index].Get_PudModel_Temperature2Limit());
			//@-显示温度2限制值范围
			Aodi_PudModel_Temperature2Limit_Range_TextField.setText(""+ScreensFramework.AODI_PubModel.AODI_PudModel[Index].Get_PudModel_Temperature2Limit_Range());
			//@-显示温度3限制值
			Aodi_PudModel_Temperature3Limit_TextField.setText(""+ScreensFramework.AODI_PubModel.AODI_PudModel[Index].Get_PudModel_Temperature3Limit());
			//@-显示温度3限制值范围
			Aodi_PudModel_Temperature3Limit_Range_TextField.setText(""+ScreensFramework.AODI_PubModel.AODI_PudModel[Index].Get_PudModel_Temperature3Limit_Range());
		}
		//@-产品型号编辑
		else if(event.getSource()==Aodi_PudModel_Editor_Button)
		{
			float i = Aodi_PudModel_Sel_ComboBox.getSelectionModel().getSelectedIndex();

			//@-已选中编辑目标
			if(i>=0)
			{
				//@-显示电压限制值
				Aodi_PudModel_VoltageLimit_TextField.setDisable(false);
				//@-显示电压限制值范围
				Aodi_PudModel_VoltageLimit_Range_TextField.setDisable(false);

				//@-显示电流限制值
				Aodi_PudModel_CurrentLimit_TextField.setDisable(false);
				//@-显示电流限制值范围
				Aodi_PudModel_CurrentLimit_Range_TextField.setDisable(false);

				//@-显示温度1限制值
				Aodi_PudModel_Temperature1Limit_TextField.setDisable(false);
				//@-显示温度1限制值范围
				Aodi_PudModel_Temperature1Limit_Range_TextField.setDisable(false);
				//@-显示温度2限制值
				Aodi_PudModel_Temperature2Limit_TextField.setDisable(false);
				//@-显示温度2限制值范围
				Aodi_PudModel_Temperature2Limit_Range_TextField.setDisable(false);
				//@-显示温度3限制值
				Aodi_PudModel_Temperature3Limit_TextField.setDisable(false);
				//@-显示温度3限制值范围
				Aodi_PudModel_Temperature3Limit_Range_TextField.setDisable(false);
			}
			//@-没有选择选择目标
			else
			{
    			//@-Noti输出信息
    			ScreensFramework.Show_Noti("Warning", "请选择需要编辑的产品型号！");
			}
		}
		//@-产品型号保存
		else if(event.getSource()==Aodi_PudModel_Save_Button)
		{
			int i = Aodi_PudModel_Sel_ComboBox.getSelectionModel().getSelectedIndex();

			//@-已选中编辑目标
			if(i>=0)
			{
				//@-获得电压限制值
				float temp1 = (Float.valueOf((Aodi_PudModel_VoltageLimit_TextField.getText())));
				//@-获得电压限制值范围
				float temp2 = (Float.valueOf((Aodi_PudModel_VoltageLimit_Range_TextField.getText())));

				//@-获得电流限制值
				float temp3 = (Float.valueOf((Aodi_PudModel_CurrentLimit_TextField.getText())));
				//@-获得电流限制值范围
				float temp4 = (Float.valueOf((Aodi_PudModel_CurrentLimit_Range_TextField.getText())));

				//@-获得温度1限制值
				float temp5 = (Float.valueOf((Aodi_PudModel_Temperature1Limit_TextField.getText())));
				//@-获得温度1限制值范围
				float temp6 = (Float.valueOf((Aodi_PudModel_Temperature1Limit_Range_TextField.getText())));
				//@-获得温度2限制值
				float temp7 = (Float.valueOf((Aodi_PudModel_Temperature2Limit_TextField.getText())));
				//@-获得温度2限制值范围
				float temp8 = (Float.valueOf((Aodi_PudModel_Temperature2Limit_Range_TextField.getText())));
				//@-获得温度3限制值
				float temp9 = (Float.valueOf((Aodi_PudModel_Temperature3Limit_TextField.getText())));
				//@-获得温度3限制值范围
				float temp10 = (Float.valueOf((Aodi_PudModel_Temperature3Limit_Range_TextField.getText())));

				//@-更新电压限制值
				ScreensFramework.AODI_PubModel.AODI_PudModel[i].Set_PudModel_VoltageLimit(temp1);
				//@-更新电压限制值范围
				ScreensFramework.AODI_PubModel.AODI_PudModel[i].Set_PudModel_VoltageLimit_Range(temp2);

				//@-更新电流限制值
				ScreensFramework.AODI_PubModel.AODI_PudModel[i].Set_PudModel_CurrentLimit(temp3);
				//@-更新电流限制值范围
				ScreensFramework.AODI_PubModel.AODI_PudModel[i].Set_PudModel_CurrentLimit_Range(temp4);

				//@-更新温度1限制值
				ScreensFramework.AODI_PubModel.AODI_PudModel[i].Set_PudModel_Temperature1Limit(temp5);
				//@-更新温度1限制值范围
				ScreensFramework.AODI_PubModel.AODI_PudModel[i].Set_PudModel_Temperature1Limit_Range(temp6);
				//@-更新温度2限制值
				ScreensFramework.AODI_PubModel.AODI_PudModel[i].Set_PudModel_Temperature2Limit(temp7);
				//@-更新温度2限制值范围
				ScreensFramework.AODI_PubModel.AODI_PudModel[i].Set_PudModel_Temperature2Limit_Range(temp8);
				//@-更新温度3限制值
				ScreensFramework.AODI_PubModel.AODI_PudModel[i].Set_PudModel_Temperature3Limit(temp9);
				//@-更新温度3限制值范围
				ScreensFramework.AODI_PubModel.AODI_PudModel[i].Set_PudModel_Temperature3Limit_Range(temp10);

				//@保存产品型号配置文件
				ScreensFramework.AODI_PubModel.Save_Config();

    			//@-Noti输出信息
    			ScreensFramework.Show_Noti("Success", "产品型号保存完毕！");

			}
			//@-没有选择选择目标
			else
			{
    			//@-Noti输出信息
    			ScreensFramework.Show_Noti("Warning", "无法保存产品型号！");
			}
		}
		//@-新建产品型号
		else if(event.getSource()==Aodi_PudModel_New_Button)
		{
			//@-获得产品型号名
			String temp = new String(Aodi_PudModel_New_TextField.getText());

			boolean rename_flag = false;
			//@-产品型号名判断
			if(temp.isEmpty()==false)
			{
				//@-产品型号名排除已存在的可能
				for(int i=0;i<ScreensFramework.AODI_PubModel.PudModel_Num;i++)
				{
					if(temp.endsWith(ScreensFramework.AODI_PubModel.AODI_PudModel[i].Get_PudModel_Name())==true)
					{
						rename_flag = true;
						break;
					}
					else
					{
						continue;
					}
				}

				//@-根据rename标志判断是否新建型号
				if(rename_flag==false)
				{
					//@-型号数量增1
					ScreensFramework.AODI_PubModel.PudModel_Num = ScreensFramework.AODI_PubModel.PudModel_Num + 1;

					//@-保存新增型号名
					ScreensFramework.AODI_PubModel.AODI_PudModel[ScreensFramework.AODI_PubModel.PudModel_Num-1].Set_PudModel_Name(temp);

					//@-保存新增型号-电压限制值
					ScreensFramework.AODI_PubModel.AODI_PudModel[ScreensFramework.AODI_PubModel.PudModel_Num-1].Set_PudModel_VoltageLimit(29);
					//@-保存新增型号-电压限制值范围
					ScreensFramework.AODI_PubModel.AODI_PudModel[ScreensFramework.AODI_PubModel.PudModel_Num-1].Set_PudModel_VoltageLimit_Range(1);

					//@-保存新增型号-电流限制值
					ScreensFramework.AODI_PubModel.AODI_PudModel[ScreensFramework.AODI_PubModel.PudModel_Num-1].Set_PudModel_CurrentLimit((float)14.5);
					//@-保存新增型号-电流限制值范围
					ScreensFramework.AODI_PubModel.AODI_PudModel[ScreensFramework.AODI_PubModel.PudModel_Num-1].Set_PudModel_CurrentLimit_Range((float)0.5);

					//@-保存新增型号-温度1限制值
					ScreensFramework.AODI_PubModel.AODI_PudModel[ScreensFramework.AODI_PubModel.PudModel_Num-1].Set_PudModel_Temperature1Limit((float)60);
					//@-保存新增型号-温度1限制值范围
					ScreensFramework.AODI_PubModel.AODI_PudModel[ScreensFramework.AODI_PubModel.PudModel_Num-1].Set_PudModel_Temperature1Limit_Range((float)10);
					//@-保存新增型号-温度2限制值
					ScreensFramework.AODI_PubModel.AODI_PudModel[ScreensFramework.AODI_PubModel.PudModel_Num-1].Set_PudModel_Temperature2Limit((float)60);
					//@-保存新增型号-温度2限制值范围
					ScreensFramework.AODI_PubModel.AODI_PudModel[ScreensFramework.AODI_PubModel.PudModel_Num-1].Set_PudModel_Temperature2Limit_Range((float)10);
					//@-保存新增型号-温度3限制值
					ScreensFramework.AODI_PubModel.AODI_PudModel[ScreensFramework.AODI_PubModel.PudModel_Num-1].Set_PudModel_Temperature3Limit((float)70);
					//@-保存新增型号-温度3限制值范围
					ScreensFramework.AODI_PubModel.AODI_PudModel[ScreensFramework.AODI_PubModel.PudModel_Num-1].Set_PudModel_Temperature3Limit_Range((float)10);

					//@保存产品型号配置文件
					ScreensFramework.AODI_PubModel.Save_Config();

					//@-更新型号名选择表
					Aodi_PudModel_Sel_ComboBox.getItems().add(""+ScreensFramework.AODI_PubModel.AODI_PudModel[ScreensFramework.AODI_PubModel.PudModel_Num-1].Get_PudModel_Name());
					Test_PudModel_Sel_ComboBox.getItems().add(""+ScreensFramework.AODI_PubModel.AODI_PudModel[ScreensFramework.AODI_PubModel.PudModel_Num-1].Get_PudModel_Name());

	    			//@-Noti输出信息
	    			ScreensFramework.Show_Noti("Success", "新产品型号创建成功,请编辑参数！");

	    			//@-新建型号名清空
	    			Aodi_PudModel_New_TextField.setText("");
				}
				else if(rename_flag==true)
				{
	    			//@-Noti输出信息
	    			ScreensFramework.Show_Noti("Warning", "该产品型号已存在！");
				}

			}
			else
			{
    			//@-Noti输出信息
    			ScreensFramework.Show_Noti("Warning", "产品型号名不能为空！");
			}

		}
		//@-定时切换-小时
		else if(event.getSource()==TimingSwitch_Hour_RadioButton)
		{
			boolean flag = TimingSwitch_Hour_RadioButton.isSelected();

			if(flag==true)
			{
				TimingSwitch_Minute_RadioButton.setSelected(false);
				TimerPro_Level = 1;
			}
			else if(flag==false)
			{
				TimingSwitch_Minute_RadioButton.setSelected(true);
				TimerPro_Level = 2;
			}
		}
		//@-定时切换-分钟
		else if(event.getSource()==TimingSwitch_Minute_RadioButton)
		{
			boolean flag = TimingSwitch_Minute_RadioButton.isSelected();

			if(flag==true)
			{
				TimingSwitch_Hour_RadioButton.setSelected(false);
				TimerPro_Level = 2;
			}
			else if(flag==false)
			{
				TimingSwitch_Hour_RadioButton.setSelected(true);
				TimerPro_Level = 1;
			}
		}
		//@-软件信息输出框清空
		else if(event.getSource()==Aodi_InfoClean_Button)
		{
			Aodi_InfoPut_TextArea.clear();
			QC_DisplayTimer.Info_Num = 0;
		}
		//@-数据采集模式选择
		else if(event.getSource()==Aodi_DataSample_Mode_ComboBox)
		{
			Aodi_DataSample_Mode = Aodi_DataSample_Mode_ComboBox.getSelectionModel().getSelectedIndex();
			//System.out.println("m:"+Aodi_DataSample_Mode);
		}
		//@-预警处理模式选择
		else if(event.getSource()==Aodi_AlarmMode_ComboBox)
		{
			Aodi_AlarmPro_Mode = Aodi_AlarmMode_ComboBox.getSelectionModel().getSelectedIndex();
		}
		//@-故障应急处理按钮
		else if(event.getSource()==Aodi_AlarmPro_Button)
		{
			AlarmPro();
		}

    }

    /**键盘处理-Node
     *
     * @param event
     */
	private void Button_Pro_Node(ActionEvent event)
	{
		//@1-Node1“节点详情”按钮
		if(event.getSource()==Test_Target1_Button1)
		{
			Node_View(RunLevel2_GroupNum,1);
		}
		//@2-Node2“节点详情”按钮
		else if(event.getSource()==Test_Target2_Button1)
		{
			Node_View(RunLevel2_GroupNum,2);
		}
		//@3-Node3“节点详情”按钮
		else if(event.getSource()==Test_Target3_Button1)
		{
			Node_View(RunLevel2_GroupNum,3);
		}
		//@4-Node4“节点详情”按钮
		else if(event.getSource()==Test_Target4_Button1)
		{
			Node_View(RunLevel2_GroupNum,4);
		}
		//@5-Node5“节点详情”按钮
		else if(event.getSource()==Test_Target5_Button1)
		{
			Node_View(RunLevel2_GroupNum,5);
		}
		//@6-Node6“节点详情”按钮
		else if(event.getSource()==Test_Target6_Button1)
		{
			Node_View(RunLevel2_GroupNum,6);
		}
		//@7-Node7“节点详情”按钮
		else if(event.getSource()==Test_Target7_Button1)
		{
			Node_View(RunLevel2_GroupNum,7);
		}
		//@8-Node8“节点详情”按钮
		else if(event.getSource()==Test_Target8_Button1)
		{
			Node_View(RunLevel2_GroupNum,8);
		}
		//@9-Node9“节点详情”按钮
		else if(event.getSource()==Test_Target9_Button1)
		{
			Node_View(RunLevel2_GroupNum,9);
		}
		//@10-Node10“节点详情”按钮
		else if(event.getSource()==Test_Target10_Button1)
		{
			Node_View(RunLevel2_GroupNum,10);
		}
 //-------------------------------------------------------------------------------------
		//@11-所有Node“返回”按钮
		else if((event.getSource()==Test_Target1_Button2)||(event.getSource()==Test_Target2_Button2)
			  ||(event.getSource()==Test_Target3_Button2)||(event.getSource()==Test_Target4_Button2)
			  ||(event.getSource()==Test_Target5_Button2)||(event.getSource()==Test_Target6_Button2)
			  ||(event.getSource()==Test_Target7_Button2)||(event.getSource()==Test_Target8_Button2)
			  ||(event.getSource()==Test_Target9_Button2)||(event.getSource()==Test_Target10_Button2))
		{
			//@-判断Group内最大的Node定时值，并将该值赋给Group定时值
			int time=0,time_max=0;
			int unconfig_count=0;
			for(int i=0;i<10;i++)
			{
				//@-节点已配置
				if(ScreensFramework.DZFZ_Group[RunLevel2_GroupNum].Group_DZFZ[i].Get_Pud_Test_ConfigStatus()==1)
				{
					time = ScreensFramework.DZFZ_Group[RunLevel2_GroupNum].Group_DZFZ[i].Get_Pud_Timing();
					if(time>time_max)
				    time_max = time;
				}
				else
				{
					unconfig_count = unconfig_count + 1;
				}
			}
			//@-Group中存在已配置的节点
			if(unconfig_count!=10)
			ScreensFramework.DZFZ_Group[RunLevel2_GroupNum].Set_Group_Timing(time_max);

			//@-Node----->Group
			Group_Node_Switch(2,0);
		}
//----------------------------------------------------------------------------------------
    	//@-Tab1-"编号录入"按钮
    	else if(event.getSource()==Test_Tab1_InputName_Button)
    	{
    		//@-该组老化产品编号录入时，该组未进行老化
    		if((ScreensFramework.DZFZ_Group[RunLevel2_GroupNum].Get_Group_TimerRunFlag()==0))
    		{
	    		//@-激活节点编号输入框
	    		if(Tab1_InputName_Button_Flag==0)
	    		{
	    			//@-切换按钮功能
	    			Tab1_InputName_Button_Flag=1;
	    			//@-激活节点编号输入框
	    			for(int Index=1; Index<=10; Index++)
	    			{
	    				//@-节点编号输入清空
	    		    	String TargetName = new String("Test_Tab1_Node"+Index+"_Name_TextField");
	    		    	Map_Tab1_NodeName_TextField.get(TargetName).setText("");
	    		    	Map_Tab1_NodeName_TextField.get(TargetName).setDisable(false);

	    		    	//@-节点配置清空-节点未配置
    		    		ScreensFramework.DZFZ_Group[RunLevel2_GroupNum].Group_DZFZ[(Index-1)].Set_Pud_Test_ConfigStatus(0);
        		    	ScreensFramework.DZFZ_Group[RunLevel2_GroupNum].Group_DZFZ[(Index-1)].Set_Pud_Test_Status(0);
	    			}
	    			//@-型号选择-激活
	    			Test_PudModel_Sel_ComboBox.setDisable(false);
	    			//@-切换按钮名
	    			Test_Tab1_InputName_Button.setText("确认");
	    			//@-切换界面焦点
	    			Tab1_InputName_Index = 1;
	    			Test_Tab1_Node1_Name_TextField.requestFocus();
	    		}
	    		//@-节点编号输入框内容确认
	    		else if(Tab1_InputName_Button_Flag==1)
	    		{
	    			//@-老化产品编号缓存
					String[] Str_Buff = new String[10];
					//@-老化产品编号重名标志
					boolean  SameName_Flag = false;
					//@-老化产品已录入编号的个数
					int   Node_Count=0;
					//@-老化产品型号是否选择标志
					boolean PudModel_Flag = false;

	    			//@-10个节点编号是否有重名
	    			for(int Index=1; Index<=10; Index++)
	    			{
	    		    	String TargetName = new String("Test_Tab1_Node"+Index+"_Name_TextField");

	    		    	//@-判断节点编号是否已输入
	    		    	if((Map_Tab1_NodeName_TextField.get(TargetName).getText()).equals("")&&((Map_Tab1_NodeName_TextField.get(TargetName).getLength())==0))
	    		    	{

	    		    	}
	    		    	else
	    		    	{
	    		    		//@-判断临时buff中的编号是否有重名
	    		    		for(int i=0; i<10; i++)
	    		    		{
			    				if(Map_Tab1_NodeName_TextField.get(TargetName).getText().equals(Str_Buff[i])==true)
			    				{
			    					SameName_Flag = true;
			    				}
	    		    		}

		    		    	//@-临时存储节点编号
		    		    	Str_Buff[(Index-1)] = new String(Map_Tab1_NodeName_TextField.get(TargetName).getText());
	    		    	}
	    			}

	    			//@-判断产品型号是否选择
	    			if(Test_PudModel_Sel_ComboBox.getSelectionModel().getSelectedIndex()>=0)
	    			{
	    				PudModel_Flag = true;
	    			}
	    			else
	    			{
	    				PudModel_Flag = false;
	    			}

	    			//System.out.println("f:"+SameName_Flag);

	    			//@-根据标志判断是否执行存储并且产品型号已选择
	    			if((SameName_Flag==false)&&(PudModel_Flag==true))
	    			{
		    			//@-保存10个节点编号及根据编号内容更新节点状态
		    			for(int Index=1; Index<=10; Index++)
		    			{
		    		    	String TargetName = new String("Test_Tab1_Node"+Index+"_Name_TextField");
		    		    	//@-保存节点编号
		    		    	ScreensFramework.DZFZ_Group[RunLevel2_GroupNum].Group_DZFZ[(Index-1)].Set_Pud_ID(Map_Tab1_NodeName_TextField.get(TargetName).getText());

		    		    	if(ScreensFramework.DZFZ_Group[RunLevel2_GroupNum].Group_DZFZ[(Index-1)].Get_Pud_ID().equals("")==false)
		    		    	{
			    		    	//@-更新节点状态-节点已配置
		    		    		ScreensFramework.DZFZ_Group[RunLevel2_GroupNum].Group_DZFZ[(Index-1)].Set_Pud_Test_ConfigStatus(1);
			    		    	//ScreensFramework.DZFZ_Group[RunLevel2_GroupNum].Group_DZFZ[(Index-1)].Set_Pud_Test_Status(1);
			    		    	//@-已节点配置个数计数
			    		    	Node_Count = Node_Count + 1;
			    		    	//System.out.println("set");
		    		    	}
		    		    	else if(ScreensFramework.DZFZ_Group[RunLevel2_GroupNum].Group_DZFZ[(Index-1)].Get_Pud_ID().equals("")==true)
		    		    	{
		        		    	//@-更新节点状态-节点未配置
		    		    		ScreensFramework.DZFZ_Group[RunLevel2_GroupNum].Group_DZFZ[(Index-1)].Set_Pud_Test_ConfigStatus(0);
		        		    	ScreensFramework.DZFZ_Group[RunLevel2_GroupNum].Group_DZFZ[(Index-1)].Set_Pud_Test_Status(0);
		        		    	//System.out.println("none");
		    		    	}
		    			}
		    			//@-保存节点配置个数计数
		    			ScreensFramework.DZFZ_Group[RunLevel2_GroupNum].Set_Group_DZFZ_Count(Node_Count);

		    			//@-获取产品型号
		    			int temp = Test_PudModel_Sel_ComboBox.getSelectionModel().getSelectedIndex();
		    			//@-保存产品型号-Group
		    			ScreensFramework.DZFZ_Group[RunLevel2_GroupNum].Set_Group_Test_PudModel(RunLevel2_GroupNum,ScreensFramework.AODI_PubModel.AODI_PudModel[temp].Get_PudModel_Name());

		    			//@-切换按钮功能
		    			Tab1_InputName_Button_Flag=0;
		    			//@-不激活节点编号输入框
		    			for(int Index=1; Index<=10; Index++)
		    			{
		    		    	String TargetName = new String("Test_Tab1_Node"+Index+"_Name_TextField");
		    		    	Map_Tab1_NodeName_TextField.get(TargetName).setDisable(true);
		    			}
		    			//@-型号选择-不激活
		    			Test_PudModel_Sel_ComboBox.setDisable(true);
		    			//@-切换按钮名
		    			Test_Tab1_InputName_Button.setText("录入");
		    			//@-老化对象名更新
		    			for(int Index=1; Index<=10; Index++)
		    			{
		    				String TargetName = new String("Test_Target"+Index+"_Name_Label");
		    		    	//@获得节点DZFZ名字
		    		    	Map_Name.get(TargetName).setText(ScreensFramework.DZFZ_Group[RunLevel2_GroupNum].Group_DZFZ[Index-1].Get_Pud_ID());
		    			}

		    			//@DX-发送产品型号-节点监控参数配置报文
		    			//QC_DisplayTimer.CAN_NodeConfig_Flag[RunLevel2_GroupNum] = true;


	    			}
	    			//@-输入的节点编号有重名
	    			else if(SameName_Flag==true)
	    			{
	    				//@-Noti输出信息
	    				ScreensFramework.Show_Noti("Error", "老化产品编号有重名！");
	    			}
	    			//@-产品型号未选择
	    			else if(PudModel_Flag==false)
	    			{
	    				//@-Noti输出信息
	    				ScreensFramework.Show_Noti("Error", "老化产品型号未选择！");
	    			}

	    		}
    		}
    		//@-该组老化产品编号录入时，该组正在进行老化，不能进行编号录入
    		else
    		{
    			//@-Noti输出信息
    			ScreensFramework.Show_Noti("Warning", "Group"+(RunLevel2_GroupNum+1)+"正在进行老化,不能录入编号！");
    		}
    	}
//----------------------------------------------------------------------------------------
		//@21-Node1“定时时间+和-”按钮
		else if((event.getSource()==Test_Target1_RunTime_Sub_Button)||(event.getSource()==Test_Target1_RunTime_Add_Button))
		{
			//@-定时-
			if(event.getSource()==Test_Target1_RunTime_Sub_Button)
			{
				Timer_RunTime_Pro(1,false);
			}
			//@-定时+
			else if(event.getSource()==Test_Target1_RunTime_Add_Button)
			{
				Timer_RunTime_Pro(1,true);
			}
		}
		//@22-Node2“定时时间+和-”按钮
		else if((event.getSource()==Test_Target2_RunTime_Sub_Button)||(event.getSource()==Test_Target2_RunTime_Add_Button))
		{
			//@-定时-
			if(event.getSource()==Test_Target2_RunTime_Sub_Button)
			{
				Timer_RunTime_Pro(2,false);
			}
			//@-定时+
			else if(event.getSource()==Test_Target2_RunTime_Add_Button)
			{
				Timer_RunTime_Pro(2,true);
			}
		}
		//@23-Node3“定时时间+和-”按钮
		else if((event.getSource()==Test_Target3_RunTime_Sub_Button)||(event.getSource()==Test_Target3_RunTime_Add_Button))
		{
			//@-定时-
			if(event.getSource()==Test_Target3_RunTime_Sub_Button)
			{
				Timer_RunTime_Pro(3,false);
			}
			//@-定时+
			else if(event.getSource()==Test_Target3_RunTime_Add_Button)
			{
				Timer_RunTime_Pro(3,true);
			}
		}
		//@24-Node4“定时时间+和-”按钮
		else if((event.getSource()==Test_Target4_RunTime_Sub_Button)||(event.getSource()==Test_Target4_RunTime_Add_Button))
		{
			//@-定时-
			if(event.getSource()==Test_Target4_RunTime_Sub_Button)
			{
				Timer_RunTime_Pro(4,false);
			}
			//@-定时+
			else if(event.getSource()==Test_Target4_RunTime_Add_Button)
			{
				Timer_RunTime_Pro(4,true);
			}
		}
		//@25-Node5“定时时间+和-”按钮
		else if((event.getSource()==Test_Target5_RunTime_Sub_Button)||(event.getSource()==Test_Target5_RunTime_Add_Button))
		{
			//@-定时-
			if(event.getSource()==Test_Target5_RunTime_Sub_Button)
			{
				Timer_RunTime_Pro(5,false);
			}
			//@-定时+
			else if(event.getSource()==Test_Target5_RunTime_Add_Button)
			{
				Timer_RunTime_Pro(5,true);
			}
		}
		//@26-Node6“定时时间+和-”按钮
		else if((event.getSource()==Test_Target6_RunTime_Sub_Button)||(event.getSource()==Test_Target6_RunTime_Add_Button))
		{
			//@-定时-
			if(event.getSource()==Test_Target6_RunTime_Sub_Button)
			{
				Timer_RunTime_Pro(6,false);
			}
			//@-定时+
			else if(event.getSource()==Test_Target6_RunTime_Add_Button)
			{
				Timer_RunTime_Pro(6,true);
			}
		}
		//@27-Node7“定时时间+和-”按钮
		else if((event.getSource()==Test_Target7_RunTime_Sub_Button)||(event.getSource()==Test_Target7_RunTime_Add_Button))
		{
			//@-定时-
			if(event.getSource()==Test_Target7_RunTime_Sub_Button)
			{
				Timer_RunTime_Pro(7,false);
			}
			//@-定时+
			else if(event.getSource()==Test_Target7_RunTime_Add_Button)
			{
				Timer_RunTime_Pro(7,true);
			}
		}
		//@28-Node8“定时时间+和-”按钮
		else if((event.getSource()==Test_Target8_RunTime_Sub_Button)||(event.getSource()==Test_Target8_RunTime_Add_Button))
		{
			//@-定时-
			if(event.getSource()==Test_Target8_RunTime_Sub_Button)
			{
				Timer_RunTime_Pro(8,false);
			}
			//@-定时+
			else if(event.getSource()==Test_Target8_RunTime_Add_Button)
			{
				Timer_RunTime_Pro(8,true);
			}
		}
		//@29-Node9“定时时间+和-”按钮
		else if((event.getSource()==Test_Target9_RunTime_Sub_Button)||(event.getSource()==Test_Target9_RunTime_Add_Button))
		{
			//@-定时-
			if(event.getSource()==Test_Target9_RunTime_Sub_Button)
			{
				Timer_RunTime_Pro(9,false);
			}
			//@-定时+
			else if(event.getSource()==Test_Target9_RunTime_Add_Button)
			{
				Timer_RunTime_Pro(9,true);
			}
		}
		//@30-Node10“定时时间+和-”按钮
		else if((event.getSource()==Test_Target10_RunTime_Sub_Button)||(event.getSource()==Test_Target10_RunTime_Add_Button))
		{
			//@-定时-
			if(event.getSource()==Test_Target10_RunTime_Sub_Button)
			{
				Timer_RunTime_Pro(10,false);
			}
			//@-定时+
			else if(event.getSource()==Test_Target10_RunTime_Add_Button)
			{
				Timer_RunTime_Pro(10,true);
			}
		}

	}

	/**查看节点详情
	 *
	 * @param Group
	 * @param Node
	 */
	private void Node_View(int Group, int Node)
	{
		//@-判断节点是否已配置
		if((ScreensFramework.DZFZ_Group[Group].Group_DZFZ[Node-1].Get_Pud_Test_Status())!=0)
		{
			//@-节点详情赋Group和Node
			View_Group = Group;
			View_Node  = Node;

			if(NodeView_Controller.Init_Run_Flag==true)
			{
				//@-节点详情数据记录数初始化
				NodeView_Controller.Init_Flag = true;
			}

			//@-切换到节点详情页面
			ScreensFramework.PageChange.set("node_view");
		}
		else
		{
			//@-Noti输出信息
			ScreensFramework.Show_Noti("Warning", "Group"+(Group+1)+"中Node"+Node+"为空,不能查看详情！");
		}
	}


    /**键盘处理-Group
     *
     * @param event
     */
	private void Button_Pro_Group(ActionEvent event)
	{
		//@1-Group1“开始老化”按钮
		if(event.getSource()==Test_Target1_Button1)
		{
			Group_TimerStart(0);
		}
		//@2-Group2“开始老化”按钮
		else if(event.getSource()==Test_Target2_Button1)
		{
			Group_TimerStart(1);
		}
		//@3-Group3“开始老化”按钮
		else if(event.getSource()==Test_Target3_Button1)
		{
			Group_TimerStart(2);
		}
		//@4-Group4“开始老化”按钮
		else if(event.getSource()==Test_Target4_Button1)
		{
			Group_TimerStart(3);
		}
		//@5-Group5“开始老化”按钮
		else if(event.getSource()==Test_Target5_Button1)
		{
			Group_TimerStart(4);
		}
		//@6-Group6“开始老化”按钮
		else if(event.getSource()==Test_Target6_Button1)
		{
			Group_TimerStart(5);
		}
		//@7-Group7“开始老化”按钮
		else if(event.getSource()==Test_Target7_Button1)
		{
			Group_TimerStart(6);
		}
		//@8-Group8“开始老化”按钮
		else if(event.getSource()==Test_Target8_Button1)
		{
			Group_TimerStart(7);
		}
		//@9-Group9“开始老化”按钮
		else if(event.getSource()==Test_Target9_Button1)
		{
			Group_TimerStart(8);
		}
		//@10-Group10“开始老化”按钮
		else if(event.getSource()==Test_Target10_Button1)
		{
			Group_TimerStart(9);
		}
 //-------------------------------------------------------------------------------------
		//@11-Group1“编辑组”按钮
		else if(event.getSource()==Test_Target1_Button2)
		{
			//@-传递运行在Node层的Group信息
			RunLevel2_GroupNum = 0;
			//@-Group----->Node
			Group_Node_Switch(1,RunLevel2_GroupNum);
		}
		//@12-Group2“编辑组”按钮
		else if(event.getSource()==Test_Target2_Button2)
		{
			//@-传递运行在Node层的Group信息
			RunLevel2_GroupNum = 1;
			//@-Group----->Node
			Group_Node_Switch(1,RunLevel2_GroupNum);
		}
		//@13-Group3“编辑组”按钮
		else if(event.getSource()==Test_Target3_Button2)
		{
			//@-传递运行在Node层的Group信息
			RunLevel2_GroupNum = 2;
			//@-Group----->Node
			Group_Node_Switch(1,RunLevel2_GroupNum);
		}
		//@14-Group4“编辑组”按钮
		else if(event.getSource()==Test_Target4_Button2)
		{
			//@-传递运行在Node层的Group信息
			RunLevel2_GroupNum = 3;
			//@-Group----->Node
			Group_Node_Switch(1,RunLevel2_GroupNum);
		}
		//@15-Group5“编辑组”按钮
		else if(event.getSource()==Test_Target5_Button2)
		{
			//@-传递运行在Node层的Group信息
			RunLevel2_GroupNum = 4;
			//@-Group----->Node
			Group_Node_Switch(1,RunLevel2_GroupNum);
		}
		//@16-Group6“编辑组”按钮
		else if(event.getSource()==Test_Target6_Button2)
		{
			//@-传递运行在Node层的Group信息
			RunLevel2_GroupNum = 5;
			//@-Group----->Node
			Group_Node_Switch(1,RunLevel2_GroupNum);
		}
		//@17-Group7“编辑组”按钮
		else if(event.getSource()==Test_Target7_Button2)
		{
			//@-传递运行在Node层的Group信息
			RunLevel2_GroupNum = 6;
			//@-Group----->Node
			Group_Node_Switch(1,RunLevel2_GroupNum);
		}
		//@18-Group8“编辑组”按钮
		else if(event.getSource()==Test_Target8_Button2)
		{
			//@-传递运行在Node层的Group信息
			RunLevel2_GroupNum = 7;
			//@-Group----->Node
			Group_Node_Switch(1,RunLevel2_GroupNum);
		}
		//@19-Group8“编辑组”按钮
		else if(event.getSource()==Test_Target9_Button2)
		{
			//@-传递运行在Node层的Group信息
			RunLevel2_GroupNum = 8;
			//@-Group----->Node
			Group_Node_Switch(1,RunLevel2_GroupNum);
		}
		//@20-Group9“编辑组”按钮
		else if(event.getSource()==Test_Target10_Button2)
		{
			//@-传递运行在Node层的Group信息
			RunLevel2_GroupNum = 9;
			//@-Group----->Node
			Group_Node_Switch(1,RunLevel2_GroupNum);
		}
//-------------------------------------------------------------------------------------
		//@21-Group1“定时时间+和-”按钮
		else if((event.getSource()==Test_Target1_RunTime_Sub_Button)||(event.getSource()==Test_Target1_RunTime_Add_Button))
		{
			//@-定时-
			if(event.getSource()==Test_Target1_RunTime_Sub_Button)
			{
				Timer_RunTime_Pro(1,false);
			}
			//@-定时+
			else if(event.getSource()==Test_Target1_RunTime_Add_Button)
			{
				Timer_RunTime_Pro(1,true);
			}
		}
		//@22-Group2“定时时间+和-”按钮
		else if((event.getSource()==Test_Target2_RunTime_Sub_Button)||(event.getSource()==Test_Target2_RunTime_Add_Button))
		{
			//@-定时-
			if(event.getSource()==Test_Target2_RunTime_Sub_Button)
			{
				Timer_RunTime_Pro(2,false);
			}
			//@-定时+
			else if(event.getSource()==Test_Target2_RunTime_Add_Button)
			{
				Timer_RunTime_Pro(2,true);
			}
		}
		//@23-Group3“定时时间+和-”按钮
		else if((event.getSource()==Test_Target3_RunTime_Sub_Button)||(event.getSource()==Test_Target3_RunTime_Add_Button))
		{
			//@-定时-
			if(event.getSource()==Test_Target3_RunTime_Sub_Button)
			{
				Timer_RunTime_Pro(3,false);
			}
			//@-定时+
			else if(event.getSource()==Test_Target3_RunTime_Add_Button)
			{
				Timer_RunTime_Pro(3,true);
			}
		}
		//@24-Group4“定时时间+和-”按钮
		else if((event.getSource()==Test_Target4_RunTime_Sub_Button)||(event.getSource()==Test_Target4_RunTime_Add_Button))
		{
			//@-定时-
			if(event.getSource()==Test_Target4_RunTime_Sub_Button)
			{
				Timer_RunTime_Pro(4,false);
			}
			//@-定时+
			else if(event.getSource()==Test_Target4_RunTime_Add_Button)
			{
				Timer_RunTime_Pro(4,true);
			}
		}
		//@25-Group5“定时时间+和-”按钮
		else if((event.getSource()==Test_Target5_RunTime_Sub_Button)||(event.getSource()==Test_Target5_RunTime_Add_Button))
		{
			//@-定时-
			if(event.getSource()==Test_Target5_RunTime_Sub_Button)
			{
				Timer_RunTime_Pro(5,false);
			}
			//@-定时+
			else if(event.getSource()==Test_Target5_RunTime_Add_Button)
			{
				Timer_RunTime_Pro(5,true);
			}
		}
		//@26-Group6“定时时间+和-”按钮
		else if((event.getSource()==Test_Target6_RunTime_Sub_Button)||(event.getSource()==Test_Target6_RunTime_Add_Button))
		{
			//@-定时-
			if(event.getSource()==Test_Target6_RunTime_Sub_Button)
			{
				Timer_RunTime_Pro(6,false);
			}
			//@-定时+
			else if(event.getSource()==Test_Target6_RunTime_Add_Button)
			{
				Timer_RunTime_Pro(6,true);
			}
		}
		//@27-Group7“定时时间+和-”按钮
		else if((event.getSource()==Test_Target7_RunTime_Sub_Button)||(event.getSource()==Test_Target7_RunTime_Add_Button))
		{
			//@-定时-
			if(event.getSource()==Test_Target7_RunTime_Sub_Button)
			{
				Timer_RunTime_Pro(7,false);
			}
			//@-定时+
			else if(event.getSource()==Test_Target7_RunTime_Add_Button)
			{
				Timer_RunTime_Pro(7,true);
			}
		}
		//@28-Group8“定时时间+和-”按钮
		else if((event.getSource()==Test_Target8_RunTime_Sub_Button)||(event.getSource()==Test_Target8_RunTime_Add_Button))
		{
			//@-定时-
			if(event.getSource()==Test_Target8_RunTime_Sub_Button)
			{
				Timer_RunTime_Pro(8,false);
			}
			//@-定时+
			else if(event.getSource()==Test_Target8_RunTime_Add_Button)
			{
				Timer_RunTime_Pro(8,true);
			}
		}
		//@29-Group9“定时时间+和-”按钮
		else if((event.getSource()==Test_Target9_RunTime_Sub_Button)||(event.getSource()==Test_Target9_RunTime_Add_Button))
		{
			//@-定时-
			if(event.getSource()==Test_Target9_RunTime_Sub_Button)
			{
				Timer_RunTime_Pro(9,false);
			}
			//@-定时+
			else if(event.getSource()==Test_Target9_RunTime_Add_Button)
			{
				Timer_RunTime_Pro(9,true);
			}
		}
		//@30-Group10“定时时间+和-”按钮
		else if((event.getSource()==Test_Target10_RunTime_Sub_Button)||(event.getSource()==Test_Target10_RunTime_Add_Button))
		{
			//@-定时-
			if(event.getSource()==Test_Target10_RunTime_Sub_Button)
			{
				Timer_RunTime_Pro(10,false);
			}
			//@-定时+
			else if(event.getSource()==Test_Target10_RunTime_Add_Button)
			{
				Timer_RunTime_Pro(10,true);
			}
		}

	}

	/**定时器定时时间处理
	 *
	 * @param Target
	 * @param Dir  false:Sub   true:Add
	 */
	private void Timer_RunTime_Pro(int Target, boolean Dir)
	{
		//@-Group层
		if(GroupRunLevel==1)
		{
			//@-Add
			if(Dir==true)
			{
				int time = 0;

				//@-判断时间级别
				if(TimerPro_Level==1)
				{
					time = ScreensFramework.DZFZ_Group[Target-1].Get_Group_Timing();
					time = time +3600;
					ScreensFramework.DZFZ_Group[Target-1].Set_Group_Timing(time);
				}
				else if(TimerPro_Level==2)
				{
					time = ScreensFramework.DZFZ_Group[Target-1].Get_Group_Timing();
					time = time +60;
					ScreensFramework.DZFZ_Group[Target-1].Set_Group_Timing(time);
				}

				//@-修改Group内所有Node值
				for(int i=0;i<10;i++)
				ScreensFramework.DZFZ_Group[Target-1].Group_DZFZ[i].Set_Pud_Timing(time);
			}
			//@-Sub
			else if(Dir==false)
			{
				//@-判断时间级别
				if(TimerPro_Level==1)
				{
					int time = ScreensFramework.DZFZ_Group[Target-1].Get_Group_Timing();
					time = time -3600;
					if(time>=0)
					{
						ScreensFramework.DZFZ_Group[Target-1].Set_Group_Timing(time);
						//@-修改Group内所有Node值
						for(int i=0;i<10;i++)
						ScreensFramework.DZFZ_Group[Target-1].Group_DZFZ[i].Set_Pud_Timing(time);
					}
				}
				else if(TimerPro_Level==2)
				{
					int time = ScreensFramework.DZFZ_Group[Target-1].Get_Group_Timing();
					time = time -60;
					if(time>=0)
					{
						ScreensFramework.DZFZ_Group[Target-1].Set_Group_Timing(time);
						//@-修改Group内所有Node值
						for(int i=0;i<10;i++)
						ScreensFramework.DZFZ_Group[Target-1].Group_DZFZ[i].Set_Pud_Timing(time);
					}
				}


			}
		}
		//@-Node层
		else if(GroupRunLevel==2)
		{
			//@-Add
			if(Dir==true)
			{
				//@-判断时间级别
				if(TimerPro_Level==1)
				{
					int time = ScreensFramework.DZFZ_Group[RunLevel2_GroupNum].Group_DZFZ[Target-1].Get_Pud_Timing();
					ScreensFramework.DZFZ_Group[RunLevel2_GroupNum].Group_DZFZ[Target-1].Set_Pud_Timing((time+3600));
				}
				else if(TimerPro_Level==2)
				{
					int time = ScreensFramework.DZFZ_Group[RunLevel2_GroupNum].Group_DZFZ[Target-1].Get_Pud_Timing();
					ScreensFramework.DZFZ_Group[RunLevel2_GroupNum].Group_DZFZ[Target-1].Set_Pud_Timing((time+60));
				}
			}
			//@-Sub
			else if(Dir==false)
			{
				//@-判断时间级别
				if(TimerPro_Level==1)
				{
					int time = ScreensFramework.DZFZ_Group[RunLevel2_GroupNum].Group_DZFZ[Target-1].Get_Pud_Timing();
					time = time -3600;
					if(time>=0)
					ScreensFramework.DZFZ_Group[RunLevel2_GroupNum].Group_DZFZ[Target-1].Set_Pud_Timing(time);
				}
				else if(TimerPro_Level==2)
				{
					int time = ScreensFramework.DZFZ_Group[RunLevel2_GroupNum].Group_DZFZ[Target-1].Get_Pud_Timing();
					time = time -60;
					if(time>=0)
					ScreensFramework.DZFZ_Group[RunLevel2_GroupNum].Group_DZFZ[Target-1].Set_Pud_Timing(time);
				}
			}
		}
	}






	/**老化产品编号输入焦点切换
	 *
	 *
	 */
	private void NodeName_RequestFocus()
	{
		Tab1_InputName_Index = Tab1_InputName_Index + 1;
		if(Tab1_InputName_Index>=11)
		Tab1_InputName_Index = 1;

    	String TargetName = new String("Test_Tab1_Node"+Tab1_InputName_Index+"_Name_TextField");
    	Map_Tab1_NodeName_TextField.get(TargetName).requestFocus();
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



    /**故障应急处理
     *
     */
    private void AlarmPro()
    {
    	//@-获得故障应急处理Group
    	QC_DisplayTimer.CAN_AlarmPro_Group=Aodi_AlarmPro_Group_ComboBox.getSelectionModel().getSelectedIndex();
    	//@-获得故障应急处理Node
    	QC_DisplayTimer.CAN_AlarmPro_Node=Aodi_AlarmPro_Node_ComboBox.getSelectionModel().getSelectedIndex();

    	QC_DisplayTimer.CAN_Send_AlarmPro_Flag = true;
    }


    /**软件信息输出
     *
     * @param Info
     */
   synchronized private void Aodi_Info_Put(String Info)
    {
	   Aodi_InfoPut_TextArea.appendText("@"+QC_DisplayTimer.Time_Str1+"-"+Info);
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
