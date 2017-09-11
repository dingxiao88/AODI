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

//@DX1-������Ҫ�Ż���λ��

public class QC_Controller implements Initializable, ControlledScreen {

	//@1-������Ӧ�ó���ӿ�
	private ScreensController myController;

	//@-Ӧ�ò���root
	@FXML
	private AnchorPane Root;

	//--------------------------��1---------------------------
	//@-�ϻ�����1����
	@FXML
	private Label Test_Target1_Name_Label;

	//@-�ϻ�����1ʱ�����
	@FXML
	private Label Test_Target1_TimeName_Label;

	//@-�ϻ�����1����ʱ��-Sub
	@FXML
	private Button Test_Target1_RunTime_Sub_Button;
	//@-�ϻ�����1����ʱ��-Add
	@FXML
	private Button Test_Target1_RunTime_Add_Button;

	//@-�ϻ�����1����ʱ��
	@FXML
	private Label Test_Target1_RunTime_Label;

	//@-�ϻ�������1pane
	@FXML
	private GridPane Test_Group1_Pane;

	//@-�ϻ�������1״ָ̬ʾͼ
	@FXML
	private ImageView Test_Group1_ImageView;
	//@-�ϻ�������1״ָ̬ʾͼ1
	@FXML
	private ImageView Test_Group1_ImageView1;
	//@-�ϻ�������1״ָ̬ʾͼ2
	@FXML
	private ImageView Test_Group1_ImageView2;
	//@-�ϻ�������1״ָ̬ʾͼ3
	@FXML
	private ImageView Test_Group1_ImageView3;
	//@-�ϻ�������1״ָ̬ʾͼ4
	@FXML
	private ImageView Test_Group1_ImageView4;
	//@-�ϻ�������1״ָ̬ʾͼ5
	@FXML
	private ImageView Test_Group1_ImageView5;
	//@-�ϻ�������1״ָ̬ʾͼ6
	@FXML
	private ImageView Test_Group1_ImageView6;
	//@-�ϻ�������1״ָ̬ʾͼ7
	@FXML
	private ImageView Test_Group1_ImageView7;
	//@-�ϻ�������1״ָ̬ʾͼ8
	@FXML
	private ImageView Test_Group1_ImageView8;
	//@-�ϻ�������1״ָ̬ʾͼ9
	@FXML
	private ImageView Test_Group1_ImageView9;
	//@-�ϻ�������1״ָ̬ʾͼ10
	@FXML
	private ImageView Test_Group1_ImageView10;

	//@-�ϻ���������/ֹͣ����ť-������Nodeҳ��������������������Groupҳ�����������ϻ���ʼ����ť
	@FXML
	private Button  Test_Target1_TestRun_Button;
	//@-�ϻ���������ԭ������
	@FXML
	private Label     Test_Target1_LeaveReason_Label;
	//@-�ϻ���������ԭ��
	@FXML
	private ChoiceBox Test_Target1_LeaveReason_ChoiceBox;

	//@-�ϻ�����1��ť1
	@FXML
	private Button  Test_Target1_Button1;
	//@-�ϻ�����1��ť2
	@FXML
	private Button  Test_Target1_Button2;

	//@-�ϻ�����1����1����Rectangle
	@FXML
	private Rectangle  Test_Target1_Rectangle;
	//---------------------------------------------------------

	//--------------------------��2---------------------------
	//@-�ϻ�����2����
	@FXML
	private Label Test_Target2_Name_Label;

	//@-�ϻ�����2ʱ�����
	@FXML
	private Label Test_Target2_TimeName_Label;

	//@-�ϻ�����2����ʱ��-Sub
	@FXML
	private Button Test_Target2_RunTime_Sub_Button;
	//@-�ϻ�����2����ʱ��-Add
	@FXML
	private Button Test_Target2_RunTime_Add_Button;

	//@-�ϻ�����2����ʱ��
	@FXML
	private Label Test_Target2_RunTime_Label;

	//@-�ϻ�������2pane
	@FXML
	private GridPane Test_Group2_Pane;

	//@-�ϻ�������2״ָ̬ʾͼ
	@FXML
	private ImageView Test_Group2_ImageView;
	//@-�ϻ�������2״ָ̬ʾͼ1
	@FXML
	private ImageView Test_Group2_ImageView1;
	//@-�ϻ�������2״ָ̬ʾͼ2
	@FXML
	private ImageView Test_Group2_ImageView2;
	//@-�ϻ�������2״ָ̬ʾͼ3
	@FXML
	private ImageView Test_Group2_ImageView3;
	//@-�ϻ�������2״ָ̬ʾͼ4
	@FXML
	private ImageView Test_Group2_ImageView4;
	//@-�ϻ�������2״ָ̬ʾͼ5
	@FXML
	private ImageView Test_Group2_ImageView5;
	//@-�ϻ�������2״ָ̬ʾͼ6
	@FXML
	private ImageView Test_Group2_ImageView6;
	//@-�ϻ�������2״ָ̬ʾͼ7
	@FXML
	private ImageView Test_Group2_ImageView7;
	//@-�ϻ�������2״ָ̬ʾͼ8
	@FXML
	private ImageView Test_Group2_ImageView8;
	//@-�ϻ�������2״ָ̬ʾͼ9
	@FXML
	private ImageView Test_Group2_ImageView9;
	//@-�ϻ�������2״ָ̬ʾͼ10
	@FXML
	private ImageView Test_Group2_ImageView10;

	//@-�ϻ���������/ֹͣ����ť-������Nodeҳ��������������������Groupҳ�����������ϻ���ʼ����ť
	@FXML
	private Button  Test_Target2_TestRun_Button;
	//@-�ϻ���������ԭ������
	@FXML
	private Label     Test_Target2_LeaveReason_Label;
	//@-�ϻ���������ԭ��
	@FXML
	private ChoiceBox Test_Target2_LeaveReason_ChoiceBox;

	//@-�ϻ�����2����2�ϻ����а�ť
	@FXML
	private Button  Test_Target2_Button1;
	//@-�ϻ�����2����2�ϻ��༭��ť
	@FXML
	private Button  Test_Target2_Button2;

	//@-�ϻ�����2����2����Rectangle
	@FXML
	private Rectangle  Test_Target2_Rectangle;
	//---------------------------------------------------------

	//--------------------------��3---------------------------
	//@-�ϻ�����3����
	@FXML
	private Label Test_Target3_Name_Label;

	//@-�ϻ�����3ʱ�����
	@FXML
	private Label Test_Target3_TimeName_Label;

	//@-�ϻ�����3����ʱ��-Sub
	@FXML
	private Button Test_Target3_RunTime_Sub_Button;
	//@-�ϻ�����3����ʱ��-Add
	@FXML
	private Button Test_Target3_RunTime_Add_Button;

	//@-�ϻ�����3����ʱ��
	@FXML
	private Label Test_Target3_RunTime_Label;

	//@-�ϻ�������3pane
	@FXML
	private GridPane Test_Group3_Pane;

	//@-�ϻ�������3״ָ̬ʾͼ
	@FXML
	private ImageView Test_Group3_ImageView;
	//@-�ϻ�������3״ָ̬ʾͼ1
	@FXML
	private ImageView Test_Group3_ImageView1;
	//@-�ϻ�������3״ָ̬ʾͼ2
	@FXML
	private ImageView Test_Group3_ImageView2;
	//@-�ϻ�������3״ָ̬ʾͼ3
	@FXML
	private ImageView Test_Group3_ImageView3;
	//@-�ϻ�������3״ָ̬ʾͼ4
	@FXML
	private ImageView Test_Group3_ImageView4;
	//@-�ϻ�������3״ָ̬ʾͼ5
	@FXML
	private ImageView Test_Group3_ImageView5;
	//@-�ϻ�������3״ָ̬ʾͼ6
	@FXML
	private ImageView Test_Group3_ImageView6;
	//@-�ϻ�������3״ָ̬ʾͼ7
	@FXML
	private ImageView Test_Group3_ImageView7;
	//@-�ϻ�������3״ָ̬ʾͼ8
	@FXML
	private ImageView Test_Group3_ImageView8;
	//@-�ϻ�������3״ָ̬ʾͼ9
	@FXML
	private ImageView Test_Group3_ImageView9;
	//@-�ϻ�������3״ָ̬ʾͼ10
	@FXML
	private ImageView Test_Group3_ImageView10;

	//@-�ϻ���������/ֹͣ����ť-������Nodeҳ��������������������Groupҳ�����������ϻ���ʼ����ť
	@FXML
	private Button  Test_Target3_TestRun_Button;
	//@-�ϻ���������ԭ������
	@FXML
	private Label     Test_Target3_LeaveReason_Label;
	//@-�ϻ���������ԭ��
	@FXML
	private ChoiceBox Test_Target3_LeaveReason_ChoiceBox;

	//@-�ϻ�����3����3�ϻ����а�ť
	@FXML
	private Button  Test_Target3_Button1;
	//@-�ϻ�����3����3�ϻ��༭��ť
	@FXML
	private Button  Test_Target3_Button2;

	//@-�ϻ�����3����3����Rectangle
	@FXML
	private Rectangle  Test_Target3_Rectangle;
	//---------------------------------------------------------

	//--------------------------��4---------------------------
	//@-�ϻ�����4����
	@FXML
	private Label Test_Target4_Name_Label;

	//@-�ϻ�����4ʱ�����
	@FXML
	private Label Test_Target4_TimeName_Label;

	//@-�ϻ�����4����ʱ��-Sub
	@FXML
	private Button Test_Target4_RunTime_Sub_Button;
	//@-�ϻ�����4����ʱ��-Add
	@FXML
	private Button Test_Target4_RunTime_Add_Button;

	//@-�ϻ�����4����ʱ��
	@FXML
	private Label Test_Target4_RunTime_Label;

	//@-�ϻ�������4pane
	@FXML
	private GridPane Test_Group4_Pane;

	//@-�ϻ�������4״ָ̬ʾͼ
	@FXML
	private ImageView Test_Group4_ImageView;
	//@-�ϻ�������4״ָ̬ʾͼ1
	@FXML
	private ImageView Test_Group4_ImageView1;
	//@-�ϻ�������4״ָ̬ʾͼ2
	@FXML
	private ImageView Test_Group4_ImageView2;
	//@-�ϻ�������4״ָ̬ʾͼ3
	@FXML
	private ImageView Test_Group4_ImageView3;
	//@-�ϻ�������4״ָ̬ʾͼ4
	@FXML
	private ImageView Test_Group4_ImageView4;
	//@-�ϻ�������4״ָ̬ʾͼ5
	@FXML
	private ImageView Test_Group4_ImageView5;
	//@-�ϻ�������4״ָ̬ʾͼ6
	@FXML
	private ImageView Test_Group4_ImageView6;
	//@-�ϻ�������4״ָ̬ʾͼ7
	@FXML
	private ImageView Test_Group4_ImageView7;
	//@-�ϻ�������4״ָ̬ʾͼ8
	@FXML
	private ImageView Test_Group4_ImageView8;
	//@-�ϻ�������4״ָ̬ʾͼ9
	@FXML
	private ImageView Test_Group4_ImageView9;
	//@-�ϻ�������4״ָ̬ʾͼ10
	@FXML
	private ImageView Test_Group4_ImageView10;

	//@-�ϻ���������/ֹͣ����ť-������Nodeҳ��������������������Groupҳ�����������ϻ���ʼ����ť
	@FXML
	private Button  Test_Target4_TestRun_Button;
	//@-�ϻ���������ԭ������
	@FXML
	private Label     Test_Target4_LeaveReason_Label;
	//@-�ϻ���������ԭ��
	@FXML
	private ChoiceBox Test_Target4_LeaveReason_ChoiceBox;

	//@-�ϻ�����4����4�ϻ����а�ť
	@FXML
	private Button  Test_Target4_Button1;
	//@-�ϻ�����4����4�ϻ��༭��ť
	@FXML
	private Button  Test_Target4_Button2;

	//@-�ϻ�����4����4����Rectangle
	@FXML
	private Rectangle  Test_Target4_Rectangle;
	//---------------------------------------------------------

	//--------------------------��5---------------------------
	//@-�ϻ�����5����
	@FXML
	private Label Test_Target5_Name_Label;

	//@-�ϻ�����5ʱ�����
	@FXML
	private Label Test_Target5_TimeName_Label;

	//@-�ϻ�����5����ʱ��-Sub
	@FXML
	private Button Test_Target5_RunTime_Sub_Button;
	//@-�ϻ�����5����ʱ��-Add
	@FXML
	private Button Test_Target5_RunTime_Add_Button;

	//@-�ϻ�����5����ʱ��
	@FXML
	private Label Test_Target5_RunTime_Label;

	//@-�ϻ�������5pane
	@FXML
	private GridPane Test_Group5_Pane;

	//@-�ϻ�������5״ָ̬ʾͼ
	@FXML
	private ImageView Test_Group5_ImageView;
	//@-�ϻ�������5״ָ̬ʾͼ1
	@FXML
	private ImageView Test_Group5_ImageView1;
	//@-�ϻ�������5״ָ̬ʾͼ2
	@FXML
	private ImageView Test_Group5_ImageView2;
	//@-�ϻ�������5״ָ̬ʾͼ3
	@FXML
	private ImageView Test_Group5_ImageView3;
	//@-�ϻ�������5״ָ̬ʾͼ4
	@FXML
	private ImageView Test_Group5_ImageView4;
	//@-�ϻ�������5״ָ̬ʾͼ5
	@FXML
	private ImageView Test_Group5_ImageView5;
	//@-�ϻ�������5״ָ̬ʾͼ6
	@FXML
	private ImageView Test_Group5_ImageView6;
	//@-�ϻ�������5״ָ̬ʾͼ7
	@FXML
	private ImageView Test_Group5_ImageView7;
	//@-�ϻ�������5״ָ̬ʾͼ8
	@FXML
	private ImageView Test_Group5_ImageView8;
	//@-�ϻ�������5״ָ̬ʾͼ9
	@FXML
	private ImageView Test_Group5_ImageView9;
	//@-�ϻ�������5״ָ̬ʾͼ10
	@FXML
	private ImageView Test_Group5_ImageView10;

	//@-�ϻ���������/ֹͣ����ť-������Nodeҳ��������������������Groupҳ�����������ϻ���ʼ����ť
	@FXML
	private Button  Test_Target5_TestRun_Button;
	//@-�ϻ���������ԭ������
	@FXML
	private Label     Test_Target5_LeaveReason_Label;
	//@-�ϻ���������ԭ��
	@FXML
	private ChoiceBox Test_Target5_LeaveReason_ChoiceBox;

	//@-�ϻ�����5����5�ϻ����а�ť
	@FXML
	private Button  Test_Target5_Button1;
	//@-�ϻ�����5����5�ϻ��༭��ť
	@FXML
	private Button  Test_Target5_Button2;

	//@-�ϻ�����5����5����Rectangle
	@FXML
	private Rectangle  Test_Target5_Rectangle;
	//---------------------------------------------------------

	//--------------------------��6---------------------------
	//@-�ϻ�����6����
	@FXML
	private Label Test_Target6_Name_Label;

	//@-�ϻ�����6ʱ�����
	@FXML
	private Label Test_Target6_TimeName_Label;

	//@-�ϻ�����6����ʱ��-Sub
	@FXML
	private Button Test_Target6_RunTime_Sub_Button;
	//@-�ϻ�����6����ʱ��-Add
	@FXML
	private Button Test_Target6_RunTime_Add_Button;

	//@-�ϻ�����6����ʱ��
	@FXML
	private Label Test_Target6_RunTime_Label;

	//@-�ϻ�������6pane
	@FXML
	private GridPane Test_Group6_Pane;

	//@-�ϻ�������6״ָ̬ʾͼ
	@FXML
	private ImageView Test_Group6_ImageView;
	//@-�ϻ�������6״ָ̬ʾͼ1
	@FXML
	private ImageView Test_Group6_ImageView1;
	//@-�ϻ�������6״ָ̬ʾͼ2
	@FXML
	private ImageView Test_Group6_ImageView2;
	//@-�ϻ�������6״ָ̬ʾͼ3
	@FXML
	private ImageView Test_Group6_ImageView3;
	//@-�ϻ�������6״ָ̬ʾͼ4
	@FXML
	private ImageView Test_Group6_ImageView4;
	//@-�ϻ�������6״ָ̬ʾͼ5
	@FXML
	private ImageView Test_Group6_ImageView5;
	//@-�ϻ�������6״ָ̬ʾͼ6
	@FXML
	private ImageView Test_Group6_ImageView6;
	//@-�ϻ�������6״ָ̬ʾͼ7
	@FXML
	private ImageView Test_Group6_ImageView7;
	//@-�ϻ�������6״ָ̬ʾͼ8
	@FXML
	private ImageView Test_Group6_ImageView8;
	//@-�ϻ�������6״ָ̬ʾͼ9
	@FXML
	private ImageView Test_Group6_ImageView9;
	//@-�ϻ�������6״ָ̬ʾͼ10
	@FXML
	private ImageView Test_Group6_ImageView10;

	//@-�ϻ���������/ֹͣ����ť-������Nodeҳ��������������������Groupҳ�����������ϻ���ʼ����ť
	@FXML
	private Button  Test_Target6_TestRun_Button;
	//@-�ϻ���������ԭ������
	@FXML
	private Label     Test_Target6_LeaveReason_Label;
	//@-�ϻ���������ԭ��
	@FXML
	private ChoiceBox Test_Target6_LeaveReason_ChoiceBox;

	//@-�ϻ�����6����6�ϻ����а�ť
	@FXML
	private Button  Test_Target6_Button1;
	//@-�ϻ�����6����6�ϻ��༭��ť
	@FXML
	private Button  Test_Target6_Button2;

	//@-�ϻ�����6����6����Rectangle
	@FXML
	private Rectangle  Test_Target6_Rectangle;
	//---------------------------------------------------------

	//--------------------------��7---------------------------
	//@-�ϻ�����7����
	@FXML
	private Label Test_Target7_Name_Label;

	//@-�ϻ�����7ʱ�����
	@FXML
	private Label Test_Target7_TimeName_Label;

	//@-�ϻ�����7����ʱ��-Sub
	@FXML
	private Button Test_Target7_RunTime_Sub_Button;
	//@-�ϻ�����7����ʱ��-Add
	@FXML
	private Button Test_Target7_RunTime_Add_Button;

	//@-�ϻ�����7����ʱ��
	@FXML
	private Label Test_Target7_RunTime_Label;

	//@-�ϻ�������7pane
	@FXML
	private GridPane Test_Group7_Pane;

	//@-�ϻ�������7״ָ̬ʾͼ
	@FXML
	private ImageView Test_Group7_ImageView;
	//@-�ϻ�������7״ָ̬ʾͼ1
	@FXML
	private ImageView Test_Group7_ImageView1;
	//@-�ϻ�������7״ָ̬ʾͼ2
	@FXML
	private ImageView Test_Group7_ImageView2;
	//@-�ϻ�������7״ָ̬ʾͼ3
	@FXML
	private ImageView Test_Group7_ImageView3;
	//@-�ϻ�������7״ָ̬ʾͼ4
	@FXML
	private ImageView Test_Group7_ImageView4;
	//@-�ϻ�������7״ָ̬ʾͼ5
	@FXML
	private ImageView Test_Group7_ImageView5;
	//@-�ϻ�������7״ָ̬ʾͼ6
	@FXML
	private ImageView Test_Group7_ImageView6;
	//@-�ϻ�������7״ָ̬ʾͼ7
	@FXML
	private ImageView Test_Group7_ImageView7;
	//@-�ϻ�������7״ָ̬ʾͼ8
	@FXML
	private ImageView Test_Group7_ImageView8;
	//@-�ϻ�������7״ָ̬ʾͼ9
	@FXML
	private ImageView Test_Group7_ImageView9;
	//@-�ϻ�������7״ָ̬ʾͼ10
	@FXML
	private ImageView Test_Group7_ImageView10;

	//@-�ϻ���������/ֹͣ����ť-������Nodeҳ��������������������Groupҳ�����������ϻ���ʼ����ť
	@FXML
	private Button  Test_Target7_TestRun_Button;
	//@-�ϻ���������ԭ������
	@FXML
	private Label     Test_Target7_LeaveReason_Label;
	//@-�ϻ���������ԭ��
	@FXML
	private ChoiceBox Test_Target7_LeaveReason_ChoiceBox;

	//@-�ϻ�����7����7�ϻ����а�ť
	@FXML
	private Button  Test_Target7_Button1;
	//@-�ϻ�����7����7�ϻ��༭��ť
	@FXML
	private Button  Test_Target7_Button2;

	//@-�ϻ�����7����7����Rectangle
	@FXML
	private Rectangle  Test_Target7_Rectangle;
	//---------------------------------------------------------

	//--------------------------��8---------------------------
	//@-�ϻ�����8����
	@FXML
	private Label Test_Target8_Name_Label;

	//@-�ϻ�����8ʱ�����
	@FXML
	private Label Test_Target8_TimeName_Label;

	//@-�ϻ�����8����ʱ��-Sub
	@FXML
	private Button Test_Target8_RunTime_Sub_Button;
	//@-�ϻ�����8����ʱ��-Add
	@FXML
	private Button Test_Target8_RunTime_Add_Button;

	//@-�ϻ�����8����ʱ��
	@FXML
	private Label Test_Target8_RunTime_Label;

	//@-�ϻ�������8pane
	@FXML
	private GridPane Test_Group8_Pane;

	//@-�ϻ�������8״ָ̬ʾͼ
	@FXML
	private ImageView Test_Group8_ImageView;
	//@-�ϻ�������8״ָ̬ʾͼ1
	@FXML
	private ImageView Test_Group8_ImageView1;
	//@-�ϻ�������8״ָ̬ʾͼ2
	@FXML
	private ImageView Test_Group8_ImageView2;
	//@-�ϻ�������8״ָ̬ʾͼ3
	@FXML
	private ImageView Test_Group8_ImageView3;
	//@-�ϻ�������8״ָ̬ʾͼ4
	@FXML
	private ImageView Test_Group8_ImageView4;
	//@-�ϻ�������8״ָ̬ʾͼ5
	@FXML
	private ImageView Test_Group8_ImageView5;
	//@-�ϻ�������8״ָ̬ʾͼ6
	@FXML
	private ImageView Test_Group8_ImageView6;
	//@-�ϻ�������8״ָ̬ʾͼ7
	@FXML
	private ImageView Test_Group8_ImageView7;
	//@-�ϻ�������8״ָ̬ʾͼ8
	@FXML
	private ImageView Test_Group8_ImageView8;
	//@-�ϻ�������8״ָ̬ʾͼ9
	@FXML
	private ImageView Test_Group8_ImageView9;
	//@-�ϻ�������8״ָ̬ʾͼ10
	@FXML
	private ImageView Test_Group8_ImageView10;

	//@-�ϻ���������/ֹͣ����ť-������Nodeҳ��������������������Groupҳ�����������ϻ���ʼ����ť
	@FXML
	private Button  Test_Target8_TestRun_Button;
	//@-�ϻ���������ԭ������
	@FXML
	private Label     Test_Target8_LeaveReason_Label;
	//@-�ϻ���������ԭ��
	@FXML
	private ChoiceBox Test_Target8_LeaveReason_ChoiceBox;

	//@-�ϻ�����8����8�ϻ����а�ť
	@FXML
	private Button  Test_Target8_Button1;
	//@-�ϻ�����8����8�ϻ��༭��ť
	@FXML
	private Button  Test_Target8_Button2;

	//@-�ϻ�����8����8����Rectangle
	@FXML
	private Rectangle  Test_Target8_Rectangle;
	//---------------------------------------------------------

	//--------------------------��9---------------------------
	//@-�ϻ�����9����
	@FXML
	private Label Test_Target9_Name_Label;

	//@-�ϻ�����9ʱ�����
	@FXML
	private Label Test_Target9_TimeName_Label;

	//@-�ϻ�����9����ʱ��-Sub
	@FXML
	private Button Test_Target9_RunTime_Sub_Button;
	//@-�ϻ�����9����ʱ��-Add
	@FXML
	private Button Test_Target9_RunTime_Add_Button;

	//@-�ϻ�����9����ʱ��
	@FXML
	private Label Test_Target9_RunTime_Label;

	//@-�ϻ�������9pane
	@FXML
	private GridPane Test_Group9_Pane;

	//@-�ϻ�������9״ָ̬ʾͼ
	@FXML
	private ImageView Test_Group9_ImageView;
	//@-�ϻ�������9״ָ̬ʾͼ1
	@FXML
	private ImageView Test_Group9_ImageView1;
	//@-�ϻ�������9״ָ̬ʾͼ2
	@FXML
	private ImageView Test_Group9_ImageView2;
	//@-�ϻ�������9״ָ̬ʾͼ3
	@FXML
	private ImageView Test_Group9_ImageView3;
	//@-�ϻ�������9״ָ̬ʾͼ4
	@FXML
	private ImageView Test_Group9_ImageView4;
	//@-�ϻ�������9״ָ̬ʾͼ5
	@FXML
	private ImageView Test_Group9_ImageView5;
	//@-�ϻ�������9״ָ̬ʾͼ6
	@FXML
	private ImageView Test_Group9_ImageView6;
	//@-�ϻ�������9״ָ̬ʾͼ7
	@FXML
	private ImageView Test_Group9_ImageView7;
	//@-�ϻ�������9״ָ̬ʾͼ8
	@FXML
	private ImageView Test_Group9_ImageView8;
	//@-�ϻ�������9״ָ̬ʾͼ9
	@FXML
	private ImageView Test_Group9_ImageView9;
	//@-�ϻ�������9״ָ̬ʾͼ10
	@FXML
	private ImageView Test_Group9_ImageView10;

	//@-�ϻ���������/ֹͣ����ť-������Nodeҳ��������������������Groupҳ�����������ϻ���ʼ����ť
	@FXML
	private Button  Test_Target9_TestRun_Button;
	//@-�ϻ���������ԭ������
	@FXML
	private Label     Test_Target9_LeaveReason_Label;
	//@-�ϻ���������ԭ��
	@FXML
	private ChoiceBox Test_Target9_LeaveReason_ChoiceBox;

	//@-�ϻ�����9����9�ϻ����а�ť
	@FXML
	private Button  Test_Target9_Button1;
	//@-�ϻ�����9����9�ϻ��༭��ť
	@FXML
	private Button  Test_Target9_Button2;

	//@-�ϻ�����9����9����Rectangle
	@FXML
	private Rectangle  Test_Target9_Rectangle;
	//---------------------------------------------------------

	//--------------------------��10---------------------------
	//@-�ϻ�����10����
	@FXML
	private Label Test_Target10_Name_Label;

	//@-�ϻ�����10ʱ�����
	@FXML
	private Label Test_Target10_TimeName_Label;

	//@-�ϻ�����10����ʱ��-Sub
	@FXML
	private Button Test_Target10_RunTime_Sub_Button;
	//@-�ϻ�����10����ʱ��-Add
	@FXML
	private Button Test_Target10_RunTime_Add_Button;

	//@-�ϻ�����10����ʱ��
	@FXML
	private Label Test_Target10_RunTime_Label;

	//@-�ϻ�������10pane
	@FXML
	private GridPane Test_Group10_Pane;

	//@-�ϻ�������10״ָ̬ʾͼ
	@FXML
	private ImageView Test_Group10_ImageView;
	//@-�ϻ�������10״ָ̬ʾͼ1
	@FXML
	private ImageView Test_Group10_ImageView1;
	//@-�ϻ�������10״ָ̬ʾͼ2
	@FXML
	private ImageView Test_Group10_ImageView2;
	//@-�ϻ�������10״ָ̬ʾͼ3
	@FXML
	private ImageView Test_Group10_ImageView3;
	//@-�ϻ�������10״ָ̬ʾͼ4
	@FXML
	private ImageView Test_Group10_ImageView4;
	//@-�ϻ�������10״ָ̬ʾͼ5
	@FXML
	private ImageView Test_Group10_ImageView5;
	//@-�ϻ�������10״ָ̬ʾͼ6
	@FXML
	private ImageView Test_Group10_ImageView6;
	//@-�ϻ�������10״ָ̬ʾͼ7
	@FXML
	private ImageView Test_Group10_ImageView7;
	//@-�ϻ�������10״ָ̬ʾͼ8
	@FXML
	private ImageView Test_Group10_ImageView8;
	//@-�ϻ�������10״ָ̬ʾͼ9
	@FXML
	private ImageView Test_Group10_ImageView9;
	//@-�ϻ�������10״ָ̬ʾͼ10
	@FXML
	private ImageView Test_Group10_ImageView10;

	//@-�ϻ���������/ֹͣ����ť-������Nodeҳ��������������������Groupҳ�����������ϻ���ʼ����ť
	@FXML
	private Button  Test_Target10_TestRun_Button;
	//@-�ϻ���������ԭ������
	@FXML
	private Label     Test_Target10_LeaveReason_Label;
	//@-�ϻ���������ԭ��
	@FXML
	private ChoiceBox Test_Target10_LeaveReason_ChoiceBox;

	//@-�ϻ�����10����10�ϻ����а�ť
	@FXML
	private Button  Test_Target10_Button1;
	//@-�ϻ�����10����10�ϻ��༭��ť
	@FXML
	private Button  Test_Target10_Button2;

	//@-�ϻ�����10����10����Rectangle
	@FXML
	private Rectangle  Test_Target10_Rectangle;
	//---------------------------------------------------------

    //@-�ϻ�������Ա
    @FXML
    private Label TestOpt_Name;

    //@-ʱ��
    @FXML
    private Label System_Time;

	//@-PCB����״̬
	@FXML
	private ImageView System_PCB_Status_ImageView;
	//@-ϵͳ��������״̬
	@FXML
	private ImageView System_NetWork_Status_ImageView;

	//@1-����˳�
    @FXML
    private ImageView App_Exit;

    //----------------------------------------------------------

    //@-�ϻ���Ʒ����
    @FXML
    private Label Test_Tab1_Group_Name_Label;

    //@-�ϻ���Ʒ�ڵ�1���¼��
    @FXML
    private TextField Test_Tab1_Node1_Name_TextField;
    //@-�ϻ���Ʒ�ڵ�2���¼��
    @FXML
    private TextField Test_Tab1_Node2_Name_TextField;
    //@-�ϻ���Ʒ�ڵ�3���¼��
    @FXML
    private TextField Test_Tab1_Node3_Name_TextField;
    //@-�ϻ���Ʒ�ڵ�4���¼��
    @FXML
    private TextField Test_Tab1_Node4_Name_TextField;
    //@-�ϻ���Ʒ�ڵ�5���¼��
    @FXML
    private TextField Test_Tab1_Node5_Name_TextField;
    //@-�ϻ���Ʒ�ڵ�6���¼��
    @FXML
    private TextField Test_Tab1_Node6_Name_TextField;
    //@-�ϻ���Ʒ�ڵ�7���¼��
    @FXML
    private TextField Test_Tab1_Node7_Name_TextField;
    //@-�ϻ���Ʒ�ڵ�8���¼��
    @FXML
    private TextField Test_Tab1_Node8_Name_TextField;
    //@-�ϻ���Ʒ�ڵ�9���¼��
    @FXML
    private TextField Test_Tab1_Node9_Name_TextField;
    //@-�ϻ���Ʒ�ڵ�10���¼��
    @FXML
    private TextField Test_Tab1_Node10_Name_TextField;

    //@-�ϻ���Ʒ�ͺ�ѡ��
    @FXML
    private ComboBox Test_PudModel_Sel_ComboBox;

    //@-�ϻ���Ʒ�ڵ���¼�밴ť
    @FXML
    private Button Test_Tab1_InputName_Button;

    //---------------------------��Ʒ�ͺ�------------------------------

    //@-��Ʒ�ͺ�ѡ��
    @FXML
    private ComboBox Aodi_PudModel_Sel_ComboBox;

    //@-��ѹ���ֵ
    @FXML
    private TextField Aodi_PudModel_VoltageLimit_TextField;
    //@-��ѹ���ֵ��Χ
    @FXML
    private TextField Aodi_PudModel_VoltageLimit_Range_TextField;

    //@-�������ֵ
    @FXML
    private TextField Aodi_PudModel_CurrentLimit_TextField;
    //@-�������ֵ��Χ
    @FXML
    private TextField Aodi_PudModel_CurrentLimit_Range_TextField;

    //@-�¶�1���ֵ
    @FXML
    private TextField Aodi_PudModel_Temperature1Limit_TextField;
    //@-�¶�1���ֵ��Χ
    @FXML
    private TextField Aodi_PudModel_Temperature1Limit_Range_TextField;

    //@-�¶�2���ֵ
    @FXML
    private TextField Aodi_PudModel_Temperature2Limit_TextField;
    //@-�¶�2���ֵ��Χ
    @FXML
    private TextField Aodi_PudModel_Temperature2Limit_Range_TextField;

    //@-�¶�3���ֵ
    @FXML
    private TextField Aodi_PudModel_Temperature3Limit_TextField;
    //@-�¶�3���ֵ��Χ
    @FXML
    private TextField Aodi_PudModel_Temperature3Limit_Range_TextField;

    //@-�ͺű༭��ť
    @FXML
    private Button Aodi_PudModel_Editor_Button;

    //@-�ͺű��水ť
    @FXML
    private Button Aodi_PudModel_Save_Button;

    //@-�½��ͺŰ�ť
    @FXML
    private Button Aodi_PudModel_New_Button;
    //@-�½��ͺ���
    @FXML
    private TextField Aodi_PudModel_New_TextField;

    //-----------------------�������ù���------------------------------

    //@-��ʱ��λ�л�-Сʱ
    @FXML
    private RadioButton TimingSwitch_Hour_RadioButton;
    //@-��ʱ��λ�л�-����
    @FXML
    private RadioButton TimingSwitch_Minute_RadioButton;

    //@-�����Ϣ���
    @FXML
    private TextArea Aodi_InfoPut_TextArea;
    //@-�����Ϣ�����հ�ť
    @FXML
    private Button Aodi_InfoClean_Button;

    //------------------------���ݲɼ���������-----------------------------
    //@-���ݲɼ�ģʽѡ��
    @FXML
    private ComboBox Aodi_DataSample_Mode_ComboBox;


    //------------------------Ԥ����������-----------------------------
    //@-Ԥ������ģʽѡ��
    @FXML
    private ComboBox Aodi_AlarmMode_ComboBox;

    //------------------------����Ӧ������-----------------------------
    //@-����Ӧ������Groupѡ��
    @FXML
    private ComboBox Aodi_AlarmPro_Group_ComboBox;
    //@-����Ӧ������Nodeѡ��
    @FXML
    private ComboBox Aodi_AlarmPro_Node_ComboBox;

    //@-����Ӧ������ť
    @FXML
    private Button Aodi_AlarmPro_Button;

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
	//@26-ͼƬ��Դ-�ϻ�����
	private Image Image_state_end;
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
    //@-��Ϣ���
	public static SimpleStringProperty InfoProperty_Main = new SimpleStringProperty();
	//@-����仯Change3������
	private ChangeListener Changelisten3;

    //@-���浥Ԫ-����HashMap
	private HashMap<String,Rectangle> Map_Rectangle = new HashMap<String, Rectangle>();
	//@-���浥Ԫ-Groupָʾ��HashMap
	private HashMap<String,ImageView> Map_GroupImageView = new HashMap<String, ImageView>();
	//@-���浥Ԫ-�ϻ�������HashMap
	private HashMap<String,Label> Map_Name = new HashMap<String, Label>();
	//@-���浥Ԫ-�ϻ�����ʱ�������HashMap
	private HashMap<String,Label> Map_TimeName = new HashMap<String, Label>();
	//@-���浥Ԫ-�ϻ�����ʱ��HashMap
	private HashMap<String,Label> Map_RunTime = new HashMap<String, Label>();
	//@-���浥Ԫ-Groupָʾ��װ������HashMap
	private HashMap<String,GridPane> Map_GroupPane = new HashMap<String, GridPane>();
	//@-���浥Ԫ-Group��10̨DZFZָʾ��HashMap
	private HashMap<String,ImageView> Map_GroupPane_ImageView = new HashMap<String, ImageView>();

	//@-���浥Ԫ-Group��ť1 HashMap
	private HashMap<String,Button> Map_Button1 = new HashMap<String, Button>();
	//@-���浥Ԫ-Group��ť2 HashMap
	private HashMap<String,Button> Map_Button2 = new HashMap<String, Button>();

	//@-���浥Ԫ-Group��ť+��- HashMap
	private HashMap<String,Button> Map_AddSub_Button = new HashMap<String, Button>();


	//@-���浥Ԫ-Node�ϻ���������/ֹͣ����ť HashMap
	private HashMap<String,Button> Map_TestRun_Button = new HashMap<String, Button>();
	//@-���浥Ԫ-Node������ԭ��ѡ��� HashMap
	private HashMap<String,ChoiceBox> Map_LeaveReason_ChoiceBox = new HashMap<String, ChoiceBox>();
	//@-���浥Ԫ-Node������ԭ������ HashMap
	private HashMap<String,Label> Map_LeaveReason_Label = new HashMap<String, Label>();

	//@-���浥λ-Tab1-�ڵ�������� HashMap
	private HashMap<String,TextField> Map_Tab1_NodeName_TextField = new HashMap<String, TextField>();


	//@-����Group���в�
	public static int GroupRunLevel = 1;  //1:����ʾΪ���100̨�ڵ�  2:����ʾ��һGroup��10̨�ڵ�����
	//@-������Node����ʾ�Ǹ�Group��Ϣ-Ĭ��Group1
	public static int RunLevel2_GroupNum = 1;

	//@-Tab1-"�ڵ���¼��"��ť�����л�
	public static int Tab1_InputName_Button_Flag = 0; //0:����10���ڵ��������  1:ȷ��¼��ڵ���
	//@-Tab1-"�ڵ���¼��"Indexָʾ
	public static int Tab1_InputName_Index = 1; //1-10��TextField�ڵ��������

	//@-�ڵ�����-Group
	public static int View_Group = 1;
	//@-�ڵ�����-Node
	public static int View_Node = 1;

	//@-��ʱʱ���������  1:Сʱ  2:��
	public static int TimerPro_Level = 1;

	//@-���ݲɼ�ģʽѡ��
	public static int Aodi_DataSample_Mode = 0;  //0:�㶨�������ģʽ   1:�ɱ����ģʽ

	//@-����Ԥ��Ӧ������ģʽ
	public static int Aodi_AlarmPro_Mode = 2;




	/**��¼�����ʼ��
	 *
	 */
    @SuppressWarnings("unchecked")
	@Override
    public void initialize(URL location, ResourceBundle resources) {

    	//@1-��ʼ������HashMap
    	HashMap_Parameter_Init();

    	//@2-GUI���������ʼ��
    	GUI_Parameter_Init();

    	//@3-���ز�Ʒ�ͺ�
    	PudModel_Init();

    	//@3-������ʾˢ��
    	DisplayProperty_Main.addListener(Changelisten1 = new ChangeListener(){
			@Override
			public void changed(ObservableValue arg0, Object oldval, Object newval) {
				// TODO Auto-generated method stub

				Platform.runLater(new Runnable() {
					@Override
					public void run() {


						//@-debug
						//Aodi_InfoPut_TextArea.appendText(""+ScreensFramework.Debug_String);

						//@-��ʾϵͳʱ��
						System_Time.setText(QC_DisplayTimer.Time_Str);

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

						//@-���ݼ��̴���-Group��
						if(QC_Controller.GroupRunLevel==1)
						{
							//@-����Group��ѯ
							for(int Group=0;Group<10;Group++)
							{
						    	//@-����Group���б�־
						    	if((ScreensFramework.DZFZ_Group[Group].Get_Group_TimerRunFlag())==1)
						    	{
						    		//@-Group����ָʾ��
									String TargetName = new String("Test_Group"+(Group+1)+"_ImageView");
						    		Map_GroupImageView.get(TargetName).setImage(Image_state_ok);

						    		//@-Group����ɫ��
									TargetName = new String("Test_Target"+(Group+1)+"_Rectangle");
									Map_Rectangle.get(TargetName).setId("Rectangle_Color_Green");

						    		//@-Groupʱ�������-����ʱ��
									TargetName = new String("Test_Target"+(Group+1)+"_TimeName_Label");
									Map_TimeName.get(TargetName).setText("����ʱ��");

									//@-Group���ض�ʱ+��-��ť
									TargetName = new String("Test_Target"+(Group+1)+"_RunTime_Add_Button");
									Map_AddSub_Button.get(TargetName).setVisible(false);
									TargetName = new String("Test_Target"+(Group+1)+"_RunTime_Sub_Button");
									Map_AddSub_Button.get(TargetName).setVisible(false);

									//@-��ʾGroup�ϻ�����ʱ��
									int hours = ScreensFramework.DZFZ_Group[Group].Group_Timer_RunTime / 3600;
									int minutes = (ScreensFramework.DZFZ_Group[Group].Group_Timer_RunTime % 3600) / 60;
									int seconds = (ScreensFramework.DZFZ_Group[Group].Group_Timer_RunTime % 3600) % 60;
							    	TargetName = new String("Test_Target"+(Group+1)+"_RunTime_Label");
							    	Map_RunTime.get(TargetName).setText(""+formater_data.format(hours)+":"+formater_data.format(minutes)+":"+formater_data.format(seconds));
						    	}
						    	else if((ScreensFramework.DZFZ_Group[Group].Get_Group_TimerRunFlag())==0)
						    	{
						    		//@-Group����ָʾ��
									String TargetName = new String("Test_Group"+(Group+1)+"_ImageView");
						    		Map_GroupImageView.get(TargetName).setImage(Image_state_warning);

						    		//@-Group����ɫ��
									TargetName = new String("Test_Target"+(Group+1)+"_Rectangle");
									Map_Rectangle.get(TargetName).setId("Rectangle_Color_Yellow");

						    		//@-Groupʱ�������-��ʱʱ��
									TargetName = new String("Test_Target"+(Group+1)+"_TimeName_Label");
									Map_TimeName.get(TargetName).setText("��ʱʱ��");

									//@-Group��ʾ��ʱ+��-��ť
									TargetName = new String("Test_Target"+(Group+1)+"_RunTime_Add_Button");
									Map_AddSub_Button.get(TargetName).setVisible(true);
									TargetName = new String("Test_Target"+(Group+1)+"_RunTime_Sub_Button");
									Map_AddSub_Button.get(TargetName).setVisible(true);

									//@-��ʾGroup�ϻ���ʱʱ��
									int hours = ScreensFramework.DZFZ_Group[Group].Group_Timing / 3600;
									int minutes = (ScreensFramework.DZFZ_Group[Group].Group_Timing % 3600) / 60;
									int seconds = (ScreensFramework.DZFZ_Group[Group].Group_Timing % 3600) % 60;
							    	TargetName = new String("Test_Target"+(Group+1)+"_RunTime_Label");
							    	Map_RunTime.get(TargetName).setText(""+formater_data.format(hours)+":"+formater_data.format(minutes)+":"+formater_data.format(seconds));

						    	}

								//@-����Group��DZFZ���б�־
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
						//@-���ݼ��̴���-Node��
						else if(QC_Controller.GroupRunLevel==2)
						{
							//@-���ض�Group�ڵ�����Node��ѯ
							for(int Node=0;Node<10;Node++)
							{
						    	//@-����Group���б�־
						    	if((ScreensFramework.DZFZ_Group[RunLevel2_GroupNum].Group_DZFZ[Node].Get_Pud_RunFlag()==1)||(ScreensFramework.DZFZ_Group[RunLevel2_GroupNum].Group_DZFZ[Node].Get_Pud_RunFlag()==2))
						    	{
						    		//@-Group����ɫ��
						    		String TargetName = new String("Test_Target"+(Node+1)+"_Rectangle");
						    		Map_Rectangle.get(TargetName).setId("Rectangle_Color_Green");

						    		//@-Nodeʱ�������-����ʱ��
						    		TargetName = new String("Test_Target"+(Node+1)+"_TimeName_Label");
									Map_TimeName.get(TargetName).setText("����ʱ��");

									//@-Node�ϻ�����ʱ��
									int hours = ScreensFramework.DZFZ_Group[RunLevel2_GroupNum].Group_DZFZ[Node].Pud_RunTime / 3600;
									int minutes = (ScreensFramework.DZFZ_Group[RunLevel2_GroupNum].Group_DZFZ[Node].Pud_RunTime % 3600) / 60;
									int seconds = (ScreensFramework.DZFZ_Group[RunLevel2_GroupNum].Group_DZFZ[Node].Pud_RunTime % 3600) % 60;
							    	TargetName = new String("Test_Target"+(Node+1)+"_RunTime_Label");
							    	Map_RunTime.get(TargetName).setText(""+formater_data.format(hours)+":"+formater_data.format(minutes)+":"+formater_data.format(seconds));

									//@-Group���ض�ʱ+��-��ť
									TargetName = new String("Test_Target"+(Node+1)+"_RunTime_Add_Button");
									Map_AddSub_Button.get(TargetName).setVisible(false);
									TargetName = new String("Test_Target"+(Node+1)+"_RunTime_Sub_Button");
									Map_AddSub_Button.get(TargetName).setVisible(false);
						    	}
						    	else if((ScreensFramework.DZFZ_Group[RunLevel2_GroupNum].Group_DZFZ[Node].Get_Pud_RunFlag()==0))
						    	{
						    		//@-Group����ɫ��
						    		String TargetName = new String("Test_Target"+(Node+1)+"_Rectangle");
						    		Map_Rectangle.get(TargetName).setId("Rectangle_Color_Yellow");

						    		//@-Nodeʱ�������-��ʱʱ��
						    		TargetName = new String("Test_Target"+(Node+1)+"_TimeName_Label");
									Map_TimeName.get(TargetName).setText("��ʱʱ��");

									//@-Node�ϻ�����ʱ��
									int hours = ScreensFramework.DZFZ_Group[RunLevel2_GroupNum].Group_DZFZ[Node].Pud_Timing / 3600;
									int minutes = (ScreensFramework.DZFZ_Group[RunLevel2_GroupNum].Group_DZFZ[Node].Pud_Timing % 3600) / 60;
									int seconds = (ScreensFramework.DZFZ_Group[RunLevel2_GroupNum].Group_DZFZ[Node].Pud_Timing % 3600) % 60;
							    	TargetName = new String("Test_Target"+(Node+1)+"_RunTime_Label");
							    	Map_RunTime.get(TargetName).setText(""+formater_data.format(hours)+":"+formater_data.format(minutes)+":"+formater_data.format(seconds));

									//@-Group��ʾ��ʱ+��-��ť
									TargetName = new String("Test_Target"+(Node+1)+"_RunTime_Add_Button");
									Map_AddSub_Button.get(TargetName).setVisible(true);
									TargetName = new String("Test_Target"+(Node+1)+"_RunTime_Sub_Button");
									Map_AddSub_Button.get(TargetName).setVisible(true);

						    	}

						    	//@-Group��DZFZ���б�־
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

						//@��ʾɨ��target
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

    	//@4-������Ϣ������
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
    	//@5-��Ϣ������
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


    /**��Ʒ�ͺų�ʼ��
     *
     */
    private void PudModel_Init()
    {

    	//@-ѡ�������
    	Aodi_PudModel_Sel_ComboBox.getItems().clear();
    	Test_PudModel_Sel_ComboBox.getItems().clear();

    	//@-����ѡ����
    	for(int i=0;i<ScreensFramework.AODI_PubModel.PudModel_Num;i++)
    	{
    		Test_PudModel_Sel_ComboBox.getItems().add(""+ScreensFramework.AODI_PubModel.AODI_PudModel[i].Get_PudModel_Name());
    		Aodi_PudModel_Sel_ComboBox.getItems().add(""+ScreensFramework.AODI_PubModel.AODI_PudModel[i].Get_PudModel_Name());
    	}

    }


    /**HashMap��ʼ��
     *
     */
    private void HashMap_Parameter_Init()
    {
    	//@-���ݾ��ȸ�ʽ
		formater_data.setMaximumIntegerDigits(2);
		formater_data.setMinimumIntegerDigits(2);

    	//@-������Դ�����HashMap
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
    	//@-Groupָʾ����Դ�����HashMap
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
    	//@-�ϻ���������Դ�����HashMap
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
    	//@-�ϻ�����ʱ�������HashMap
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
    	//@-�ϻ�����ʱ����Դ�����HashMap
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
    	//@-���浥Ԫ-Group��ť+��- HashMap
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
    	//@-Groupָʾ��װ��������Դ�����HashMap
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
       	//@-Group"Button1"��ť��Դ�����HashMap
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
       	//@-Group"Button2"��ť��Դ�����HashMap
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
       	//@-Node�ϻ���������/ֹͣ����ť HashMap
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
       	//@-Node������ԭ��ѡ�����Դ�����HashMap
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
       	//@-Node������ԭ��������Դ�����HashMap
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

    	//@-Group1ָʾ����Դ�����HashMap
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
    	//@-Group2ָʾ����Դ�����HashMap
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
    	//@-Group3ָʾ����Դ�����HashMap
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
    	//@-Group4ָʾ����Դ�����HashMap
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
    	//@-Group5ָʾ����Դ�����HashMap
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
    	//@-Group6ָʾ����Դ�����HashMap
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
    	//@-Group7ָʾ����Դ�����HashMap
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
    	//@-Group8ָʾ����Դ�����HashMap
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
    	//@-Group9ָʾ����Դ�����HashMap
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
    	//@-Group10ָʾ����Դ�����HashMap
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
    	//@-Tab1-�ڵ����������Դ�����HashMap
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

    	//@1-ͼƬ��Դ��ʼ��
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


		//@-�����鱳����ʼ��
		for(Group=1; Group<=10; Group++)
		{
	    	TargetName = new String("Test_Target"+Group+"_Rectangle");
	        Map_Rectangle.get(TargetName).setId("Rectangle_Color_Yellow");
		}
		//@-Groupָʾ�Ƴ�ʼ��
		for(Group=1; Group<=10; Group++)
		{
	    	TargetName = new String("Test_Group"+Group+"_ImageView");
	    	Map_GroupImageView.get(TargetName).setImage(Image_state_warning);
		}
		//@-�ϻ��������Ƴ�ʼ��
		for(Group=1; Group<=10; Group++)
		{
	    	TargetName = new String("Test_Target"+Group+"_Name_Label");
	    	Map_Name.get(TargetName).setText("�ϻ���"+Group);
		}
		//@-�ϻ�����ʱ���ʼ��
		for(Group=1; Group<=10; Group++)
		{
	    	TargetName = new String("Test_Target"+Group+"_RunTime_Label");
	    	Map_RunTime.get(TargetName).setText("00:00:00");
		}
		//@-Groupָʾ��װ��������ʼ��
		for(Group=1; Group<=10; Group++)
		{
	    	TargetName = new String("Test_Group"+Group+"_Pane");
	    	Map_GroupPane.get(TargetName).setVisible(true);
		}
		//@-Groupָʾ�Ƴ�ʼ��
		for(Group=1; Group<=10; Group++)
		{
			for(Index=1;Index<=10;Index++)
			{
		    	TargetName = new String("Test_Group"+Group+"_ImageView"+Index);
		    	Map_GroupPane_ImageView.get(TargetName).setImage(Image_config_no);
			}
		}
		//----------------------------------------
		//@-Tab1-�ڵ���¼�밴ť-������
		Test_Tab1_InputName_Button.setDisable(true);
		//@-Tab1-�ͺ�ѡ��-������
		Test_PudModel_Sel_ComboBox.setDisable(true);

		//@-Tab1-�ڵ��������-������&���ؼ�����
		for(Index=1;Index<=10;Index++)
		{
			//Test_Tab1_Node7_Name_TextField
	    	TargetName = new String("Test_Tab1_Node"+Index+"_Name_TextField");
	    	Map_Tab1_NodeName_TextField.get(TargetName).addEventFilter(KeyEvent.KEY_TYPED , numeric_Validation(20));
	    	Map_Tab1_NodeName_TextField.get(TargetName).setDisable(true);
		}
		//----------------------------------------
		//@-���ݲɼ�ģʽѡ���ʼ��
		Aodi_DataSample_Mode_ComboBox.getItems().clear();
		Aodi_DataSample_Mode_ComboBox.getItems().add("�㶨����ģʽ");
		Aodi_DataSample_Mode_ComboBox.getItems().add("�ɱ����ģʽ");
		Aodi_DataSample_Mode_ComboBox.getSelectionModel().selectFirst();
		//---------------------------------------------
		//@-Ԥ������ģʽѡ���ʼ��
		Aodi_AlarmMode_ComboBox.getItems().clear();
		Aodi_AlarmMode_ComboBox.getItems().add("������ָʾ��ָʾ");
		Aodi_AlarmMode_ComboBox.getItems().add("���������澯");
		Aodi_AlarmMode_ComboBox.getItems().add("����ָʾ+�������澯");
		Aodi_AlarmMode_ComboBox.getSelectionModel().select(2);
		//---------------------------------------------
		//@-����Ӧ������Groupѡ���ʼ��
		Aodi_AlarmPro_Group_ComboBox.getItems().clear();
		for(int i=1;i<11;i++)
		{
			Aodi_AlarmPro_Group_ComboBox.getItems().add("��"+(i));
		}
		Aodi_AlarmPro_Group_ComboBox.getSelectionModel().selectFirst();
		//@-����Ӧ������Nodeѡ���ʼ��
		Aodi_AlarmPro_Node_ComboBox.getItems().clear();
		for(int i=1;i<11;i++)
		{
			Aodi_AlarmPro_Node_ComboBox.getItems().add("�ڵ�"+(i));
		}
		Aodi_AlarmPro_Node_ComboBox.getSelectionModel().selectFirst();
    }

    /**Group��Node֮���л�
     *
     * @param Mode
     * @param ID Group���
     */
    private void Group_Node_Switch(int Mode,int ID)
    {
    	int Index;
        String TargetName;
        String Str1;   //--------

        //@-Group--->Node�л�
        if(Mode==1)
        {
			//@-�л���ʾ��
			GroupRunLevel=2;

			//@-�ϻ���������ʼ��
			for(Index=1; Index<=10; Index++)
			{
		    	TargetName = new String("Test_Target"+Index+"_Name_Label");
		    	//@��ýڵ�DZFZ����
		    	Str1 = new String(ScreensFramework.DZFZ_Group[RunLevel2_GroupNum].Group_DZFZ[Index-1].Get_Pud_ID());
		    	Map_Name.get(TargetName).setText(Str1);
			}
			//@-Groupָʾ��װ������
			for(Index=1; Index<=10; Index++)
			{
		    	TargetName = new String("Test_Group"+Index+"_Pane");
		    	Map_GroupPane.get(TargetName).setVisible(false);
			}
			//@-Button1����
			for(Index=1; Index<=10; Index++)
			{
		    	TargetName = new String("Test_Target"+Index+"_Button1");
		    	Map_Button1.get(TargetName).setText("�ڵ�����");
			}
			//@-Button2����
			for(Index=1; Index<=10; Index++)
			{
		    	TargetName = new String("Test_Target"+Index+"_Button2");
		    	Map_Button2.get(TargetName).setText("����");
			}
			//@-��ʾNode�ϻ���������/ֹͣ����ť
			for(Index=1; Index<=10; Index++)
			{
		    	TargetName = new String("Test_Target"+Index+"_TestRun_Button");
		    	Map_TestRun_Button.get(TargetName).setVisible(true);
			}
			//@-��ʾ������ԭ��ѡ���
			for(Index=1; Index<=10; Index++)
			{
		    	TargetName = new String("Test_Target"+Index+"_LeaveReason_ChoiceBox");
		    	Map_LeaveReason_ChoiceBox.get(TargetName).setVisible(true);
			}
			//@-��ʾ������ԭ������
			for(Index=1; Index<=10; Index++)
			{
		    	TargetName = new String("Test_Target"+Index+"_LeaveReason_Label");
		    	Map_LeaveReason_Label.get(TargetName).setVisible(true);
			}
			//------------------------------------------------------------
			//@-Tab1-Group����ʾ
			Test_Tab1_Group_Name_Label.setText("�ϻ���"+(ID+1));
			//@-Tab1-�ڵ���¼�밴ť-����
			Test_Tab1_InputName_Button.setDisable(false);
			//@-Tab1-�ڵ���¼�밴ť�����л���ʼ̬
			Tab1_InputName_Button_Flag = 0;
			//@-�ڵ����������������Ӧ��Group�е�Node��ͬ��
			for(Index=1; Index<=10; Index++)
			{
		    	TargetName = new String("Test_Tab1_Node"+Index+"_Name_TextField");
		    	Map_Tab1_NodeName_TextField.get(TargetName).setText(ScreensFramework.DZFZ_Group[ID].Group_DZFZ[(Index-1)].Get_Pud_ID());
			}
        }
        //@-Node--->Group�л�
        else if(Mode==2)
        {
			//@-�л���ʾ��
			GroupRunLevel=1;

			//@-�ϻ������Ƴ�ʼ��
			for(Index=1; Index<=10; Index++)
			{
		    	TargetName = new String("Test_Target"+Index+"_Name_Label");
		    	Map_Name.get(TargetName).setText("�ϻ���"+Index);
			}
			//@-Groupָʾ��װ������
			for(Index=1; Index<=10; Index++)
			{
		    	TargetName = new String("Test_Group"+Index+"_Pane");
		    	Map_GroupPane.get(TargetName).setVisible(true);
			}
			//@-Button1����
			for(Index=1; Index<=10; Index++)
			{
		    	TargetName = new String("Test_Target"+Index+"_Button1");
		    	if((ScreensFramework.DZFZ_Group[(Index-1)].Get_Group_TimerRunFlag())==0)
		    	Map_Button1.get(TargetName).setText("��ʼ�ϻ�");
		    	else if((ScreensFramework.DZFZ_Group[(Index-1)].Get_Group_TimerRunFlag())==1)
		    	Map_Button1.get(TargetName).setText("ֹͣ�ϻ�");
			}
			//@-Button2����
			for(Index=1; Index<=10; Index++)
			{
		    	TargetName = new String("Test_Target"+Index+"_Button2");
		    	Map_Button2.get(TargetName).setText("�༭��");
			}
			//@-����Node�ϻ���������/ֹͣ����ť
			for(Index=1; Index<=10; Index++)
			{
		    	TargetName = new String("Test_Target"+Index+"_TestRun_Button");
		    	Map_TestRun_Button.get(TargetName).setVisible(false);
			}
			//@-���ء�����ԭ��ѡ���
			for(Index=1; Index<=10; Index++)
			{
		    	TargetName = new String("Test_Target"+Index+"_LeaveReason_ChoiceBox");
		    	Map_LeaveReason_ChoiceBox.get(TargetName).setVisible(false);
			}
			//@-���ء�����ԭ������
			for(Index=1; Index<=10; Index++)
			{
		    	TargetName = new String("Test_Target"+Index+"_LeaveReason_Label");
		    	Map_LeaveReason_Label.get(TargetName).setVisible(false);
			}
			//-------------------------------------------------------------
			//@-Tab1-Group������
			Test_Tab1_Group_Name_Label.setText("----------");
			//@-Tab1-��Ʒ�ͺ�ѡ��-������
			Test_PudModel_Sel_ComboBox.setDisable(true);
			//@-Tab1-�ڵ���¼�밴ť-������
			Test_Tab1_InputName_Button.setDisable(true);
			//@-Tab1-�ڵ���¼�밴ť������
			Test_Tab1_InputName_Button.setText("¼��");
			//@-Tab1-�ڵ���¼�밴ť�����л���ʼ̬
			Tab1_InputName_Button_Flag = 0;
			//@-������ڵ��������
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
    	//@DX1-���ж���������&������Ա¼��
    	//@1-Group������DZFZ
    	if((ScreensFramework.DZFZ_Group[ID].Get_Group_DZFZ_Count()!=0))
    	{
	    	//@2-Group����ʼ�ϻ�����ť
	  		if((ScreensFramework.DZFZ_Group[ID].Get_Group_TimerRunFlag())==1)
	  		{
	  			//@-����Group��ʱ���б�־
	  			ScreensFramework.DZFZ_Group[ID].Set_Group_TimerRunFlag(0);
				//@-����Group�е�Node���б�־
				for(int i=0;i<10;i++)
				{
					ScreensFramework.DZFZ_Group[ID].Group_DZFZ[i].Set_Pud_RunFlag(0);
				}

				//@DX-�����ϻ���ʼ��������-���Ʊ���&���ò�ѯ����
				QC_DisplayTimer.CAN_Send_Group_TestPro_Flag[ID] = true;
				//@-�ϻ�ֹͣ�׶�
				QC_DisplayTimer.TestPro_Stage = 2;
				//@-�ϻ�ֹͣ�׶�-Node���¿�ʼ
				//QC_DisplayTimer.TestPro_Node = 1;


	  			//@-"��ʼ�ϻ�"��ť�����л�
		    	String TargetName = new String("Test_Target"+(ID+1)+"_Button1");
		    	Map_Button1.get(TargetName).setText("��ʼ�ϻ�");

	  		}
			else if((ScreensFramework.DZFZ_Group[ID].Get_Group_TimerRunFlag())==0)
			{
				//@DX1-if(�ڵ��ز��������Ƿ����)
				//{

				//@DX1-��ʼ��Group��10���ڵ����
				ScreensFramework.DZFZ_Group[ID].Set_Group_Node_Init();
				ScreensFramework.DZFZ_Group[ID].Group_Timer_RunTime = 0;

				//@-����Group��ʱ���б�־
				ScreensFramework.DZFZ_Group[ID].Set_Group_TimerRunFlag(1);

				//@-����Group�е�Node���б�־
				for(int i=0;i<10;i++)
				{
					//@DX1
					//@-�����õ�Node
					if((ScreensFramework.DZFZ_Group[ID].Group_DZFZ[i].Get_Pud_Test_ConfigStatus())==1)
					ScreensFramework.DZFZ_Group[ID].Group_DZFZ[i].Set_Pud_RunFlag(1);
				}

				//@DX-�����ϻ���ʼ��������-���Ʊ���&���ò�ѯ����
				QC_DisplayTimer.CAN_Send_Group_TestPro_Flag[ID] = true;
				//@-�ϻ������׶�
				QC_DisplayTimer.TestPro_Stage = 1;
				//@-�ϻ������׶�-Node���¿�ʼ
				//QC_DisplayTimer.TestPro_Node = 1;

	  			//@-"��ʼ�ϻ�"��ť�����л�
		    	String TargetName = new String("Test_Target"+(ID+1)+"_Button1");
		    	Map_Button1.get(TargetName).setText("ֹͣ�ϻ�");

			}
	    }
    	//@2-Group����DZFZ
    	else if(ScreensFramework.DZFZ_Group[ID].Get_Group_DZFZ_Count()==0)
    	{
			//@-Noti�����Ϣ
			ScreensFramework.Show_Noti("Warning", "Group"+(ID+1)+"��û�������κε��Ӹ����豸��");
    	}

  		//System.out.println("ID"+ID+"_Flag:"+ScreensFramework.DZFZ_Group[ID].Get_Group_TimerRunFlag());
    }



    /**����������
     *
     * @param event
     */
    @FXML
    public void Button_Pro(ActionEvent event)
    {
    	//@-���̴�����Group��
    	if(GroupRunLevel==1)
    	{
	    	Button_Pro_Group(event);
    	}
    	//@-���̴����ڽڵ��
    	else if(GroupRunLevel==2)
    	{
    		Button_Pro_Node(event);
    	}

    	//@-��Ʒ�ͺ�ѡ��
		if(event.getSource()==Aodi_PudModel_Sel_ComboBox)
		{
			//@-��ȡIndex
			int Index = Aodi_PudModel_Sel_ComboBox.getSelectionModel().getSelectedIndex();

			//@-��ʾ��ѹ����ֵ
			Aodi_PudModel_VoltageLimit_TextField.setText(""+ScreensFramework.AODI_PubModel.AODI_PudModel[Index].Get_PudModel_VoltageLimit());
			//@-��ʾ��ѹ����ֵ��Χ
			Aodi_PudModel_VoltageLimit_Range_TextField.setText(""+ScreensFramework.AODI_PubModel.AODI_PudModel[Index].Get_PudModel_VoltageLimit_Range());

			//@-��ʾ��������ֵ
			Aodi_PudModel_CurrentLimit_TextField.setText(""+ScreensFramework.AODI_PubModel.AODI_PudModel[Index].Get_PudModel_CurrentLimit());
			//@-��ʾ��������ֵ��Χ
			Aodi_PudModel_CurrentLimit_Range_TextField.setText(""+ScreensFramework.AODI_PubModel.AODI_PudModel[Index].Get_PudModel_CurrentLimit_Range());

			//@-��ʾ�¶�1����ֵ
			Aodi_PudModel_Temperature1Limit_TextField.setText(""+ScreensFramework.AODI_PubModel.AODI_PudModel[Index].Get_PudModel_Temperature1Limit());
			//@-��ʾ�¶�1����ֵ��Χ
			Aodi_PudModel_Temperature1Limit_Range_TextField.setText(""+ScreensFramework.AODI_PubModel.AODI_PudModel[Index].Get_PudModel_Temperature1Limit_Range());
			//@-��ʾ�¶�2����ֵ
			Aodi_PudModel_Temperature2Limit_TextField.setText(""+ScreensFramework.AODI_PubModel.AODI_PudModel[Index].Get_PudModel_Temperature2Limit());
			//@-��ʾ�¶�2����ֵ��Χ
			Aodi_PudModel_Temperature2Limit_Range_TextField.setText(""+ScreensFramework.AODI_PubModel.AODI_PudModel[Index].Get_PudModel_Temperature2Limit_Range());
			//@-��ʾ�¶�3����ֵ
			Aodi_PudModel_Temperature3Limit_TextField.setText(""+ScreensFramework.AODI_PubModel.AODI_PudModel[Index].Get_PudModel_Temperature3Limit());
			//@-��ʾ�¶�3����ֵ��Χ
			Aodi_PudModel_Temperature3Limit_Range_TextField.setText(""+ScreensFramework.AODI_PubModel.AODI_PudModel[Index].Get_PudModel_Temperature3Limit_Range());
		}
		//@-��Ʒ�ͺű༭
		else if(event.getSource()==Aodi_PudModel_Editor_Button)
		{
			float i = Aodi_PudModel_Sel_ComboBox.getSelectionModel().getSelectedIndex();

			//@-��ѡ�б༭Ŀ��
			if(i>=0)
			{
				//@-��ʾ��ѹ����ֵ
				Aodi_PudModel_VoltageLimit_TextField.setDisable(false);
				//@-��ʾ��ѹ����ֵ��Χ
				Aodi_PudModel_VoltageLimit_Range_TextField.setDisable(false);

				//@-��ʾ��������ֵ
				Aodi_PudModel_CurrentLimit_TextField.setDisable(false);
				//@-��ʾ��������ֵ��Χ
				Aodi_PudModel_CurrentLimit_Range_TextField.setDisable(false);

				//@-��ʾ�¶�1����ֵ
				Aodi_PudModel_Temperature1Limit_TextField.setDisable(false);
				//@-��ʾ�¶�1����ֵ��Χ
				Aodi_PudModel_Temperature1Limit_Range_TextField.setDisable(false);
				//@-��ʾ�¶�2����ֵ
				Aodi_PudModel_Temperature2Limit_TextField.setDisable(false);
				//@-��ʾ�¶�2����ֵ��Χ
				Aodi_PudModel_Temperature2Limit_Range_TextField.setDisable(false);
				//@-��ʾ�¶�3����ֵ
				Aodi_PudModel_Temperature3Limit_TextField.setDisable(false);
				//@-��ʾ�¶�3����ֵ��Χ
				Aodi_PudModel_Temperature3Limit_Range_TextField.setDisable(false);
			}
			//@-û��ѡ��ѡ��Ŀ��
			else
			{
    			//@-Noti�����Ϣ
    			ScreensFramework.Show_Noti("Warning", "��ѡ����Ҫ�༭�Ĳ�Ʒ�ͺţ�");
			}
		}
		//@-��Ʒ�ͺű���
		else if(event.getSource()==Aodi_PudModel_Save_Button)
		{
			int i = Aodi_PudModel_Sel_ComboBox.getSelectionModel().getSelectedIndex();

			//@-��ѡ�б༭Ŀ��
			if(i>=0)
			{
				//@-��õ�ѹ����ֵ
				float temp1 = (Float.valueOf((Aodi_PudModel_VoltageLimit_TextField.getText())));
				//@-��õ�ѹ����ֵ��Χ
				float temp2 = (Float.valueOf((Aodi_PudModel_VoltageLimit_Range_TextField.getText())));

				//@-��õ�������ֵ
				float temp3 = (Float.valueOf((Aodi_PudModel_CurrentLimit_TextField.getText())));
				//@-��õ�������ֵ��Χ
				float temp4 = (Float.valueOf((Aodi_PudModel_CurrentLimit_Range_TextField.getText())));

				//@-����¶�1����ֵ
				float temp5 = (Float.valueOf((Aodi_PudModel_Temperature1Limit_TextField.getText())));
				//@-����¶�1����ֵ��Χ
				float temp6 = (Float.valueOf((Aodi_PudModel_Temperature1Limit_Range_TextField.getText())));
				//@-����¶�2����ֵ
				float temp7 = (Float.valueOf((Aodi_PudModel_Temperature2Limit_TextField.getText())));
				//@-����¶�2����ֵ��Χ
				float temp8 = (Float.valueOf((Aodi_PudModel_Temperature2Limit_Range_TextField.getText())));
				//@-����¶�3����ֵ
				float temp9 = (Float.valueOf((Aodi_PudModel_Temperature3Limit_TextField.getText())));
				//@-����¶�3����ֵ��Χ
				float temp10 = (Float.valueOf((Aodi_PudModel_Temperature3Limit_Range_TextField.getText())));

				//@-���µ�ѹ����ֵ
				ScreensFramework.AODI_PubModel.AODI_PudModel[i].Set_PudModel_VoltageLimit(temp1);
				//@-���µ�ѹ����ֵ��Χ
				ScreensFramework.AODI_PubModel.AODI_PudModel[i].Set_PudModel_VoltageLimit_Range(temp2);

				//@-���µ�������ֵ
				ScreensFramework.AODI_PubModel.AODI_PudModel[i].Set_PudModel_CurrentLimit(temp3);
				//@-���µ�������ֵ��Χ
				ScreensFramework.AODI_PubModel.AODI_PudModel[i].Set_PudModel_CurrentLimit_Range(temp4);

				//@-�����¶�1����ֵ
				ScreensFramework.AODI_PubModel.AODI_PudModel[i].Set_PudModel_Temperature1Limit(temp5);
				//@-�����¶�1����ֵ��Χ
				ScreensFramework.AODI_PubModel.AODI_PudModel[i].Set_PudModel_Temperature1Limit_Range(temp6);
				//@-�����¶�2����ֵ
				ScreensFramework.AODI_PubModel.AODI_PudModel[i].Set_PudModel_Temperature2Limit(temp7);
				//@-�����¶�2����ֵ��Χ
				ScreensFramework.AODI_PubModel.AODI_PudModel[i].Set_PudModel_Temperature2Limit_Range(temp8);
				//@-�����¶�3����ֵ
				ScreensFramework.AODI_PubModel.AODI_PudModel[i].Set_PudModel_Temperature3Limit(temp9);
				//@-�����¶�3����ֵ��Χ
				ScreensFramework.AODI_PubModel.AODI_PudModel[i].Set_PudModel_Temperature3Limit_Range(temp10);

				//@�����Ʒ�ͺ������ļ�
				ScreensFramework.AODI_PubModel.Save_Config();

    			//@-Noti�����Ϣ
    			ScreensFramework.Show_Noti("Success", "��Ʒ�ͺű�����ϣ�");

			}
			//@-û��ѡ��ѡ��Ŀ��
			else
			{
    			//@-Noti�����Ϣ
    			ScreensFramework.Show_Noti("Warning", "�޷������Ʒ�ͺţ�");
			}
		}
		//@-�½���Ʒ�ͺ�
		else if(event.getSource()==Aodi_PudModel_New_Button)
		{
			//@-��ò�Ʒ�ͺ���
			String temp = new String(Aodi_PudModel_New_TextField.getText());

			boolean rename_flag = false;
			//@-��Ʒ�ͺ����ж�
			if(temp.isEmpty()==false)
			{
				//@-��Ʒ�ͺ����ų��Ѵ��ڵĿ���
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

				//@-����rename��־�ж��Ƿ��½��ͺ�
				if(rename_flag==false)
				{
					//@-�ͺ�������1
					ScreensFramework.AODI_PubModel.PudModel_Num = ScreensFramework.AODI_PubModel.PudModel_Num + 1;

					//@-���������ͺ���
					ScreensFramework.AODI_PubModel.AODI_PudModel[ScreensFramework.AODI_PubModel.PudModel_Num-1].Set_PudModel_Name(temp);

					//@-���������ͺ�-��ѹ����ֵ
					ScreensFramework.AODI_PubModel.AODI_PudModel[ScreensFramework.AODI_PubModel.PudModel_Num-1].Set_PudModel_VoltageLimit(29);
					//@-���������ͺ�-��ѹ����ֵ��Χ
					ScreensFramework.AODI_PubModel.AODI_PudModel[ScreensFramework.AODI_PubModel.PudModel_Num-1].Set_PudModel_VoltageLimit_Range(1);

					//@-���������ͺ�-��������ֵ
					ScreensFramework.AODI_PubModel.AODI_PudModel[ScreensFramework.AODI_PubModel.PudModel_Num-1].Set_PudModel_CurrentLimit((float)14.5);
					//@-���������ͺ�-��������ֵ��Χ
					ScreensFramework.AODI_PubModel.AODI_PudModel[ScreensFramework.AODI_PubModel.PudModel_Num-1].Set_PudModel_CurrentLimit_Range((float)0.5);

					//@-���������ͺ�-�¶�1����ֵ
					ScreensFramework.AODI_PubModel.AODI_PudModel[ScreensFramework.AODI_PubModel.PudModel_Num-1].Set_PudModel_Temperature1Limit((float)60);
					//@-���������ͺ�-�¶�1����ֵ��Χ
					ScreensFramework.AODI_PubModel.AODI_PudModel[ScreensFramework.AODI_PubModel.PudModel_Num-1].Set_PudModel_Temperature1Limit_Range((float)10);
					//@-���������ͺ�-�¶�2����ֵ
					ScreensFramework.AODI_PubModel.AODI_PudModel[ScreensFramework.AODI_PubModel.PudModel_Num-1].Set_PudModel_Temperature2Limit((float)60);
					//@-���������ͺ�-�¶�2����ֵ��Χ
					ScreensFramework.AODI_PubModel.AODI_PudModel[ScreensFramework.AODI_PubModel.PudModel_Num-1].Set_PudModel_Temperature2Limit_Range((float)10);
					//@-���������ͺ�-�¶�3����ֵ
					ScreensFramework.AODI_PubModel.AODI_PudModel[ScreensFramework.AODI_PubModel.PudModel_Num-1].Set_PudModel_Temperature3Limit((float)70);
					//@-���������ͺ�-�¶�3����ֵ��Χ
					ScreensFramework.AODI_PubModel.AODI_PudModel[ScreensFramework.AODI_PubModel.PudModel_Num-1].Set_PudModel_Temperature3Limit_Range((float)10);

					//@�����Ʒ�ͺ������ļ�
					ScreensFramework.AODI_PubModel.Save_Config();

					//@-�����ͺ���ѡ���
					Aodi_PudModel_Sel_ComboBox.getItems().add(""+ScreensFramework.AODI_PubModel.AODI_PudModel[ScreensFramework.AODI_PubModel.PudModel_Num-1].Get_PudModel_Name());
					Test_PudModel_Sel_ComboBox.getItems().add(""+ScreensFramework.AODI_PubModel.AODI_PudModel[ScreensFramework.AODI_PubModel.PudModel_Num-1].Get_PudModel_Name());

	    			//@-Noti�����Ϣ
	    			ScreensFramework.Show_Noti("Success", "�²�Ʒ�ͺŴ����ɹ�,��༭������");

	    			//@-�½��ͺ������
	    			Aodi_PudModel_New_TextField.setText("");
				}
				else if(rename_flag==true)
				{
	    			//@-Noti�����Ϣ
	    			ScreensFramework.Show_Noti("Warning", "�ò�Ʒ�ͺ��Ѵ��ڣ�");
				}

			}
			else
			{
    			//@-Noti�����Ϣ
    			ScreensFramework.Show_Noti("Warning", "��Ʒ�ͺ�������Ϊ�գ�");
			}

		}
		//@-��ʱ�л�-Сʱ
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
		//@-��ʱ�л�-����
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
		//@-�����Ϣ��������
		else if(event.getSource()==Aodi_InfoClean_Button)
		{
			Aodi_InfoPut_TextArea.clear();
			QC_DisplayTimer.Info_Num = 0;
		}
		//@-���ݲɼ�ģʽѡ��
		else if(event.getSource()==Aodi_DataSample_Mode_ComboBox)
		{
			Aodi_DataSample_Mode = Aodi_DataSample_Mode_ComboBox.getSelectionModel().getSelectedIndex();
			//System.out.println("m:"+Aodi_DataSample_Mode);
		}
		//@-Ԥ������ģʽѡ��
		else if(event.getSource()==Aodi_AlarmMode_ComboBox)
		{
			Aodi_AlarmPro_Mode = Aodi_AlarmMode_ComboBox.getSelectionModel().getSelectedIndex();
		}
		//@-����Ӧ������ť
		else if(event.getSource()==Aodi_AlarmPro_Button)
		{
			AlarmPro();
		}

    }

    /**���̴���-Node
     *
     * @param event
     */
	private void Button_Pro_Node(ActionEvent event)
	{
		//@1-Node1���ڵ����顱��ť
		if(event.getSource()==Test_Target1_Button1)
		{
			Node_View(RunLevel2_GroupNum,1);
		}
		//@2-Node2���ڵ����顱��ť
		else if(event.getSource()==Test_Target2_Button1)
		{
			Node_View(RunLevel2_GroupNum,2);
		}
		//@3-Node3���ڵ����顱��ť
		else if(event.getSource()==Test_Target3_Button1)
		{
			Node_View(RunLevel2_GroupNum,3);
		}
		//@4-Node4���ڵ����顱��ť
		else if(event.getSource()==Test_Target4_Button1)
		{
			Node_View(RunLevel2_GroupNum,4);
		}
		//@5-Node5���ڵ����顱��ť
		else if(event.getSource()==Test_Target5_Button1)
		{
			Node_View(RunLevel2_GroupNum,5);
		}
		//@6-Node6���ڵ����顱��ť
		else if(event.getSource()==Test_Target6_Button1)
		{
			Node_View(RunLevel2_GroupNum,6);
		}
		//@7-Node7���ڵ����顱��ť
		else if(event.getSource()==Test_Target7_Button1)
		{
			Node_View(RunLevel2_GroupNum,7);
		}
		//@8-Node8���ڵ����顱��ť
		else if(event.getSource()==Test_Target8_Button1)
		{
			Node_View(RunLevel2_GroupNum,8);
		}
		//@9-Node9���ڵ����顱��ť
		else if(event.getSource()==Test_Target9_Button1)
		{
			Node_View(RunLevel2_GroupNum,9);
		}
		//@10-Node10���ڵ����顱��ť
		else if(event.getSource()==Test_Target10_Button1)
		{
			Node_View(RunLevel2_GroupNum,10);
		}
 //-------------------------------------------------------------------------------------
		//@11-����Node�����ء���ť
		else if((event.getSource()==Test_Target1_Button2)||(event.getSource()==Test_Target2_Button2)
			  ||(event.getSource()==Test_Target3_Button2)||(event.getSource()==Test_Target4_Button2)
			  ||(event.getSource()==Test_Target5_Button2)||(event.getSource()==Test_Target6_Button2)
			  ||(event.getSource()==Test_Target7_Button2)||(event.getSource()==Test_Target8_Button2)
			  ||(event.getSource()==Test_Target9_Button2)||(event.getSource()==Test_Target10_Button2))
		{
			//@-�ж�Group������Node��ʱֵ��������ֵ����Group��ʱֵ
			int time=0,time_max=0;
			int unconfig_count=0;
			for(int i=0;i<10;i++)
			{
				//@-�ڵ�������
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
			//@-Group�д��������õĽڵ�
			if(unconfig_count!=10)
			ScreensFramework.DZFZ_Group[RunLevel2_GroupNum].Set_Group_Timing(time_max);

			//@-Node----->Group
			Group_Node_Switch(2,0);
		}
//----------------------------------------------------------------------------------------
    	//@-Tab1-"���¼��"��ť
    	else if(event.getSource()==Test_Tab1_InputName_Button)
    	{
    		//@-�����ϻ���Ʒ���¼��ʱ������δ�����ϻ�
    		if((ScreensFramework.DZFZ_Group[RunLevel2_GroupNum].Get_Group_TimerRunFlag()==0))
    		{
	    		//@-����ڵ��������
	    		if(Tab1_InputName_Button_Flag==0)
	    		{
	    			//@-�л���ť����
	    			Tab1_InputName_Button_Flag=1;
	    			//@-����ڵ��������
	    			for(int Index=1; Index<=10; Index++)
	    			{
	    				//@-�ڵ����������
	    		    	String TargetName = new String("Test_Tab1_Node"+Index+"_Name_TextField");
	    		    	Map_Tab1_NodeName_TextField.get(TargetName).setText("");
	    		    	Map_Tab1_NodeName_TextField.get(TargetName).setDisable(false);

	    		    	//@-�ڵ��������-�ڵ�δ����
    		    		ScreensFramework.DZFZ_Group[RunLevel2_GroupNum].Group_DZFZ[(Index-1)].Set_Pud_Test_ConfigStatus(0);
        		    	ScreensFramework.DZFZ_Group[RunLevel2_GroupNum].Group_DZFZ[(Index-1)].Set_Pud_Test_Status(0);
	    			}
	    			//@-�ͺ�ѡ��-����
	    			Test_PudModel_Sel_ComboBox.setDisable(false);
	    			//@-�л���ť��
	    			Test_Tab1_InputName_Button.setText("ȷ��");
	    			//@-�л����潹��
	    			Tab1_InputName_Index = 1;
	    			Test_Tab1_Node1_Name_TextField.requestFocus();
	    		}
	    		//@-�ڵ������������ȷ��
	    		else if(Tab1_InputName_Button_Flag==1)
	    		{
	    			//@-�ϻ���Ʒ��Ż���
					String[] Str_Buff = new String[10];
					//@-�ϻ���Ʒ���������־
					boolean  SameName_Flag = false;
					//@-�ϻ���Ʒ��¼���ŵĸ���
					int   Node_Count=0;
					//@-�ϻ���Ʒ�ͺ��Ƿ�ѡ���־
					boolean PudModel_Flag = false;

	    			//@-10���ڵ����Ƿ�������
	    			for(int Index=1; Index<=10; Index++)
	    			{
	    		    	String TargetName = new String("Test_Tab1_Node"+Index+"_Name_TextField");

	    		    	//@-�жϽڵ����Ƿ�������
	    		    	if((Map_Tab1_NodeName_TextField.get(TargetName).getText()).equals("")&&((Map_Tab1_NodeName_TextField.get(TargetName).getLength())==0))
	    		    	{

	    		    	}
	    		    	else
	    		    	{
	    		    		//@-�ж���ʱbuff�еı���Ƿ�������
	    		    		for(int i=0; i<10; i++)
	    		    		{
			    				if(Map_Tab1_NodeName_TextField.get(TargetName).getText().equals(Str_Buff[i])==true)
			    				{
			    					SameName_Flag = true;
			    				}
	    		    		}

		    		    	//@-��ʱ�洢�ڵ���
		    		    	Str_Buff[(Index-1)] = new String(Map_Tab1_NodeName_TextField.get(TargetName).getText());
	    		    	}
	    			}

	    			//@-�жϲ�Ʒ�ͺ��Ƿ�ѡ��
	    			if(Test_PudModel_Sel_ComboBox.getSelectionModel().getSelectedIndex()>=0)
	    			{
	    				PudModel_Flag = true;
	    			}
	    			else
	    			{
	    				PudModel_Flag = false;
	    			}

	    			//System.out.println("f:"+SameName_Flag);

	    			//@-���ݱ�־�ж��Ƿ�ִ�д洢���Ҳ�Ʒ�ͺ���ѡ��
	    			if((SameName_Flag==false)&&(PudModel_Flag==true))
	    			{
		    			//@-����10���ڵ��ż����ݱ�����ݸ��½ڵ�״̬
		    			for(int Index=1; Index<=10; Index++)
		    			{
		    		    	String TargetName = new String("Test_Tab1_Node"+Index+"_Name_TextField");
		    		    	//@-����ڵ���
		    		    	ScreensFramework.DZFZ_Group[RunLevel2_GroupNum].Group_DZFZ[(Index-1)].Set_Pud_ID(Map_Tab1_NodeName_TextField.get(TargetName).getText());

		    		    	if(ScreensFramework.DZFZ_Group[RunLevel2_GroupNum].Group_DZFZ[(Index-1)].Get_Pud_ID().equals("")==false)
		    		    	{
			    		    	//@-���½ڵ�״̬-�ڵ�������
		    		    		ScreensFramework.DZFZ_Group[RunLevel2_GroupNum].Group_DZFZ[(Index-1)].Set_Pud_Test_ConfigStatus(1);
			    		    	//ScreensFramework.DZFZ_Group[RunLevel2_GroupNum].Group_DZFZ[(Index-1)].Set_Pud_Test_Status(1);
			    		    	//@-�ѽڵ����ø�������
			    		    	Node_Count = Node_Count + 1;
			    		    	//System.out.println("set");
		    		    	}
		    		    	else if(ScreensFramework.DZFZ_Group[RunLevel2_GroupNum].Group_DZFZ[(Index-1)].Get_Pud_ID().equals("")==true)
		    		    	{
		        		    	//@-���½ڵ�״̬-�ڵ�δ����
		    		    		ScreensFramework.DZFZ_Group[RunLevel2_GroupNum].Group_DZFZ[(Index-1)].Set_Pud_Test_ConfigStatus(0);
		        		    	ScreensFramework.DZFZ_Group[RunLevel2_GroupNum].Group_DZFZ[(Index-1)].Set_Pud_Test_Status(0);
		        		    	//System.out.println("none");
		    		    	}
		    			}
		    			//@-����ڵ����ø�������
		    			ScreensFramework.DZFZ_Group[RunLevel2_GroupNum].Set_Group_DZFZ_Count(Node_Count);

		    			//@-��ȡ��Ʒ�ͺ�
		    			int temp = Test_PudModel_Sel_ComboBox.getSelectionModel().getSelectedIndex();
		    			//@-�����Ʒ�ͺ�-Group
		    			ScreensFramework.DZFZ_Group[RunLevel2_GroupNum].Set_Group_Test_PudModel(RunLevel2_GroupNum,ScreensFramework.AODI_PubModel.AODI_PudModel[temp].Get_PudModel_Name());

		    			//@-�л���ť����
		    			Tab1_InputName_Button_Flag=0;
		    			//@-������ڵ��������
		    			for(int Index=1; Index<=10; Index++)
		    			{
		    		    	String TargetName = new String("Test_Tab1_Node"+Index+"_Name_TextField");
		    		    	Map_Tab1_NodeName_TextField.get(TargetName).setDisable(true);
		    			}
		    			//@-�ͺ�ѡ��-������
		    			Test_PudModel_Sel_ComboBox.setDisable(true);
		    			//@-�л���ť��
		    			Test_Tab1_InputName_Button.setText("¼��");
		    			//@-�ϻ�����������
		    			for(int Index=1; Index<=10; Index++)
		    			{
		    				String TargetName = new String("Test_Target"+Index+"_Name_Label");
		    		    	//@��ýڵ�DZFZ����
		    		    	Map_Name.get(TargetName).setText(ScreensFramework.DZFZ_Group[RunLevel2_GroupNum].Group_DZFZ[Index-1].Get_Pud_ID());
		    			}

		    			//@DX-���Ͳ�Ʒ�ͺ�-�ڵ��ز������ñ���
		    			//QC_DisplayTimer.CAN_NodeConfig_Flag[RunLevel2_GroupNum] = true;


	    			}
	    			//@-����Ľڵ���������
	    			else if(SameName_Flag==true)
	    			{
	    				//@-Noti�����Ϣ
	    				ScreensFramework.Show_Noti("Error", "�ϻ���Ʒ�����������");
	    			}
	    			//@-��Ʒ�ͺ�δѡ��
	    			else if(PudModel_Flag==false)
	    			{
	    				//@-Noti�����Ϣ
	    				ScreensFramework.Show_Noti("Error", "�ϻ���Ʒ�ͺ�δѡ��");
	    			}

	    		}
    		}
    		//@-�����ϻ���Ʒ���¼��ʱ���������ڽ����ϻ������ܽ��б��¼��
    		else
    		{
    			//@-Noti�����Ϣ
    			ScreensFramework.Show_Noti("Warning", "Group"+(RunLevel2_GroupNum+1)+"���ڽ����ϻ�,����¼���ţ�");
    		}
    	}
//----------------------------------------------------------------------------------------
		//@21-Node1����ʱʱ��+��-����ť
		else if((event.getSource()==Test_Target1_RunTime_Sub_Button)||(event.getSource()==Test_Target1_RunTime_Add_Button))
		{
			//@-��ʱ-
			if(event.getSource()==Test_Target1_RunTime_Sub_Button)
			{
				Timer_RunTime_Pro(1,false);
			}
			//@-��ʱ+
			else if(event.getSource()==Test_Target1_RunTime_Add_Button)
			{
				Timer_RunTime_Pro(1,true);
			}
		}
		//@22-Node2����ʱʱ��+��-����ť
		else if((event.getSource()==Test_Target2_RunTime_Sub_Button)||(event.getSource()==Test_Target2_RunTime_Add_Button))
		{
			//@-��ʱ-
			if(event.getSource()==Test_Target2_RunTime_Sub_Button)
			{
				Timer_RunTime_Pro(2,false);
			}
			//@-��ʱ+
			else if(event.getSource()==Test_Target2_RunTime_Add_Button)
			{
				Timer_RunTime_Pro(2,true);
			}
		}
		//@23-Node3����ʱʱ��+��-����ť
		else if((event.getSource()==Test_Target3_RunTime_Sub_Button)||(event.getSource()==Test_Target3_RunTime_Add_Button))
		{
			//@-��ʱ-
			if(event.getSource()==Test_Target3_RunTime_Sub_Button)
			{
				Timer_RunTime_Pro(3,false);
			}
			//@-��ʱ+
			else if(event.getSource()==Test_Target3_RunTime_Add_Button)
			{
				Timer_RunTime_Pro(3,true);
			}
		}
		//@24-Node4����ʱʱ��+��-����ť
		else if((event.getSource()==Test_Target4_RunTime_Sub_Button)||(event.getSource()==Test_Target4_RunTime_Add_Button))
		{
			//@-��ʱ-
			if(event.getSource()==Test_Target4_RunTime_Sub_Button)
			{
				Timer_RunTime_Pro(4,false);
			}
			//@-��ʱ+
			else if(event.getSource()==Test_Target4_RunTime_Add_Button)
			{
				Timer_RunTime_Pro(4,true);
			}
		}
		//@25-Node5����ʱʱ��+��-����ť
		else if((event.getSource()==Test_Target5_RunTime_Sub_Button)||(event.getSource()==Test_Target5_RunTime_Add_Button))
		{
			//@-��ʱ-
			if(event.getSource()==Test_Target5_RunTime_Sub_Button)
			{
				Timer_RunTime_Pro(5,false);
			}
			//@-��ʱ+
			else if(event.getSource()==Test_Target5_RunTime_Add_Button)
			{
				Timer_RunTime_Pro(5,true);
			}
		}
		//@26-Node6����ʱʱ��+��-����ť
		else if((event.getSource()==Test_Target6_RunTime_Sub_Button)||(event.getSource()==Test_Target6_RunTime_Add_Button))
		{
			//@-��ʱ-
			if(event.getSource()==Test_Target6_RunTime_Sub_Button)
			{
				Timer_RunTime_Pro(6,false);
			}
			//@-��ʱ+
			else if(event.getSource()==Test_Target6_RunTime_Add_Button)
			{
				Timer_RunTime_Pro(6,true);
			}
		}
		//@27-Node7����ʱʱ��+��-����ť
		else if((event.getSource()==Test_Target7_RunTime_Sub_Button)||(event.getSource()==Test_Target7_RunTime_Add_Button))
		{
			//@-��ʱ-
			if(event.getSource()==Test_Target7_RunTime_Sub_Button)
			{
				Timer_RunTime_Pro(7,false);
			}
			//@-��ʱ+
			else if(event.getSource()==Test_Target7_RunTime_Add_Button)
			{
				Timer_RunTime_Pro(7,true);
			}
		}
		//@28-Node8����ʱʱ��+��-����ť
		else if((event.getSource()==Test_Target8_RunTime_Sub_Button)||(event.getSource()==Test_Target8_RunTime_Add_Button))
		{
			//@-��ʱ-
			if(event.getSource()==Test_Target8_RunTime_Sub_Button)
			{
				Timer_RunTime_Pro(8,false);
			}
			//@-��ʱ+
			else if(event.getSource()==Test_Target8_RunTime_Add_Button)
			{
				Timer_RunTime_Pro(8,true);
			}
		}
		//@29-Node9����ʱʱ��+��-����ť
		else if((event.getSource()==Test_Target9_RunTime_Sub_Button)||(event.getSource()==Test_Target9_RunTime_Add_Button))
		{
			//@-��ʱ-
			if(event.getSource()==Test_Target9_RunTime_Sub_Button)
			{
				Timer_RunTime_Pro(9,false);
			}
			//@-��ʱ+
			else if(event.getSource()==Test_Target9_RunTime_Add_Button)
			{
				Timer_RunTime_Pro(9,true);
			}
		}
		//@30-Node10����ʱʱ��+��-����ť
		else if((event.getSource()==Test_Target10_RunTime_Sub_Button)||(event.getSource()==Test_Target10_RunTime_Add_Button))
		{
			//@-��ʱ-
			if(event.getSource()==Test_Target10_RunTime_Sub_Button)
			{
				Timer_RunTime_Pro(10,false);
			}
			//@-��ʱ+
			else if(event.getSource()==Test_Target10_RunTime_Add_Button)
			{
				Timer_RunTime_Pro(10,true);
			}
		}

	}

	/**�鿴�ڵ�����
	 *
	 * @param Group
	 * @param Node
	 */
	private void Node_View(int Group, int Node)
	{
		//@-�жϽڵ��Ƿ�������
		if((ScreensFramework.DZFZ_Group[Group].Group_DZFZ[Node-1].Get_Pud_Test_Status())!=0)
		{
			//@-�ڵ����鸳Group��Node
			View_Group = Group;
			View_Node  = Node;

			if(NodeView_Controller.Init_Run_Flag==true)
			{
				//@-�ڵ��������ݼ�¼����ʼ��
				NodeView_Controller.Init_Flag = true;
			}

			//@-�л����ڵ�����ҳ��
			ScreensFramework.PageChange.set("node_view");
		}
		else
		{
			//@-Noti�����Ϣ
			ScreensFramework.Show_Noti("Warning", "Group"+(Group+1)+"��Node"+Node+"Ϊ��,���ܲ鿴���飡");
		}
	}


    /**���̴���-Group
     *
     * @param event
     */
	private void Button_Pro_Group(ActionEvent event)
	{
		//@1-Group1����ʼ�ϻ�����ť
		if(event.getSource()==Test_Target1_Button1)
		{
			Group_TimerStart(0);
		}
		//@2-Group2����ʼ�ϻ�����ť
		else if(event.getSource()==Test_Target2_Button1)
		{
			Group_TimerStart(1);
		}
		//@3-Group3����ʼ�ϻ�����ť
		else if(event.getSource()==Test_Target3_Button1)
		{
			Group_TimerStart(2);
		}
		//@4-Group4����ʼ�ϻ�����ť
		else if(event.getSource()==Test_Target4_Button1)
		{
			Group_TimerStart(3);
		}
		//@5-Group5����ʼ�ϻ�����ť
		else if(event.getSource()==Test_Target5_Button1)
		{
			Group_TimerStart(4);
		}
		//@6-Group6����ʼ�ϻ�����ť
		else if(event.getSource()==Test_Target6_Button1)
		{
			Group_TimerStart(5);
		}
		//@7-Group7����ʼ�ϻ�����ť
		else if(event.getSource()==Test_Target7_Button1)
		{
			Group_TimerStart(6);
		}
		//@8-Group8����ʼ�ϻ�����ť
		else if(event.getSource()==Test_Target8_Button1)
		{
			Group_TimerStart(7);
		}
		//@9-Group9����ʼ�ϻ�����ť
		else if(event.getSource()==Test_Target9_Button1)
		{
			Group_TimerStart(8);
		}
		//@10-Group10����ʼ�ϻ�����ť
		else if(event.getSource()==Test_Target10_Button1)
		{
			Group_TimerStart(9);
		}
 //-------------------------------------------------------------------------------------
		//@11-Group1���༭�顱��ť
		else if(event.getSource()==Test_Target1_Button2)
		{
			//@-����������Node���Group��Ϣ
			RunLevel2_GroupNum = 0;
			//@-Group----->Node
			Group_Node_Switch(1,RunLevel2_GroupNum);
		}
		//@12-Group2���༭�顱��ť
		else if(event.getSource()==Test_Target2_Button2)
		{
			//@-����������Node���Group��Ϣ
			RunLevel2_GroupNum = 1;
			//@-Group----->Node
			Group_Node_Switch(1,RunLevel2_GroupNum);
		}
		//@13-Group3���༭�顱��ť
		else if(event.getSource()==Test_Target3_Button2)
		{
			//@-����������Node���Group��Ϣ
			RunLevel2_GroupNum = 2;
			//@-Group----->Node
			Group_Node_Switch(1,RunLevel2_GroupNum);
		}
		//@14-Group4���༭�顱��ť
		else if(event.getSource()==Test_Target4_Button2)
		{
			//@-����������Node���Group��Ϣ
			RunLevel2_GroupNum = 3;
			//@-Group----->Node
			Group_Node_Switch(1,RunLevel2_GroupNum);
		}
		//@15-Group5���༭�顱��ť
		else if(event.getSource()==Test_Target5_Button2)
		{
			//@-����������Node���Group��Ϣ
			RunLevel2_GroupNum = 4;
			//@-Group----->Node
			Group_Node_Switch(1,RunLevel2_GroupNum);
		}
		//@16-Group6���༭�顱��ť
		else if(event.getSource()==Test_Target6_Button2)
		{
			//@-����������Node���Group��Ϣ
			RunLevel2_GroupNum = 5;
			//@-Group----->Node
			Group_Node_Switch(1,RunLevel2_GroupNum);
		}
		//@17-Group7���༭�顱��ť
		else if(event.getSource()==Test_Target7_Button2)
		{
			//@-����������Node���Group��Ϣ
			RunLevel2_GroupNum = 6;
			//@-Group----->Node
			Group_Node_Switch(1,RunLevel2_GroupNum);
		}
		//@18-Group8���༭�顱��ť
		else if(event.getSource()==Test_Target8_Button2)
		{
			//@-����������Node���Group��Ϣ
			RunLevel2_GroupNum = 7;
			//@-Group----->Node
			Group_Node_Switch(1,RunLevel2_GroupNum);
		}
		//@19-Group8���༭�顱��ť
		else if(event.getSource()==Test_Target9_Button2)
		{
			//@-����������Node���Group��Ϣ
			RunLevel2_GroupNum = 8;
			//@-Group----->Node
			Group_Node_Switch(1,RunLevel2_GroupNum);
		}
		//@20-Group9���༭�顱��ť
		else if(event.getSource()==Test_Target10_Button2)
		{
			//@-����������Node���Group��Ϣ
			RunLevel2_GroupNum = 9;
			//@-Group----->Node
			Group_Node_Switch(1,RunLevel2_GroupNum);
		}
//-------------------------------------------------------------------------------------
		//@21-Group1����ʱʱ��+��-����ť
		else if((event.getSource()==Test_Target1_RunTime_Sub_Button)||(event.getSource()==Test_Target1_RunTime_Add_Button))
		{
			//@-��ʱ-
			if(event.getSource()==Test_Target1_RunTime_Sub_Button)
			{
				Timer_RunTime_Pro(1,false);
			}
			//@-��ʱ+
			else if(event.getSource()==Test_Target1_RunTime_Add_Button)
			{
				Timer_RunTime_Pro(1,true);
			}
		}
		//@22-Group2����ʱʱ��+��-����ť
		else if((event.getSource()==Test_Target2_RunTime_Sub_Button)||(event.getSource()==Test_Target2_RunTime_Add_Button))
		{
			//@-��ʱ-
			if(event.getSource()==Test_Target2_RunTime_Sub_Button)
			{
				Timer_RunTime_Pro(2,false);
			}
			//@-��ʱ+
			else if(event.getSource()==Test_Target2_RunTime_Add_Button)
			{
				Timer_RunTime_Pro(2,true);
			}
		}
		//@23-Group3����ʱʱ��+��-����ť
		else if((event.getSource()==Test_Target3_RunTime_Sub_Button)||(event.getSource()==Test_Target3_RunTime_Add_Button))
		{
			//@-��ʱ-
			if(event.getSource()==Test_Target3_RunTime_Sub_Button)
			{
				Timer_RunTime_Pro(3,false);
			}
			//@-��ʱ+
			else if(event.getSource()==Test_Target3_RunTime_Add_Button)
			{
				Timer_RunTime_Pro(3,true);
			}
		}
		//@24-Group4����ʱʱ��+��-����ť
		else if((event.getSource()==Test_Target4_RunTime_Sub_Button)||(event.getSource()==Test_Target4_RunTime_Add_Button))
		{
			//@-��ʱ-
			if(event.getSource()==Test_Target4_RunTime_Sub_Button)
			{
				Timer_RunTime_Pro(4,false);
			}
			//@-��ʱ+
			else if(event.getSource()==Test_Target4_RunTime_Add_Button)
			{
				Timer_RunTime_Pro(4,true);
			}
		}
		//@25-Group5����ʱʱ��+��-����ť
		else if((event.getSource()==Test_Target5_RunTime_Sub_Button)||(event.getSource()==Test_Target5_RunTime_Add_Button))
		{
			//@-��ʱ-
			if(event.getSource()==Test_Target5_RunTime_Sub_Button)
			{
				Timer_RunTime_Pro(5,false);
			}
			//@-��ʱ+
			else if(event.getSource()==Test_Target5_RunTime_Add_Button)
			{
				Timer_RunTime_Pro(5,true);
			}
		}
		//@26-Group6����ʱʱ��+��-����ť
		else if((event.getSource()==Test_Target6_RunTime_Sub_Button)||(event.getSource()==Test_Target6_RunTime_Add_Button))
		{
			//@-��ʱ-
			if(event.getSource()==Test_Target6_RunTime_Sub_Button)
			{
				Timer_RunTime_Pro(6,false);
			}
			//@-��ʱ+
			else if(event.getSource()==Test_Target6_RunTime_Add_Button)
			{
				Timer_RunTime_Pro(6,true);
			}
		}
		//@27-Group7����ʱʱ��+��-����ť
		else if((event.getSource()==Test_Target7_RunTime_Sub_Button)||(event.getSource()==Test_Target7_RunTime_Add_Button))
		{
			//@-��ʱ-
			if(event.getSource()==Test_Target7_RunTime_Sub_Button)
			{
				Timer_RunTime_Pro(7,false);
			}
			//@-��ʱ+
			else if(event.getSource()==Test_Target7_RunTime_Add_Button)
			{
				Timer_RunTime_Pro(7,true);
			}
		}
		//@28-Group8����ʱʱ��+��-����ť
		else if((event.getSource()==Test_Target8_RunTime_Sub_Button)||(event.getSource()==Test_Target8_RunTime_Add_Button))
		{
			//@-��ʱ-
			if(event.getSource()==Test_Target8_RunTime_Sub_Button)
			{
				Timer_RunTime_Pro(8,false);
			}
			//@-��ʱ+
			else if(event.getSource()==Test_Target8_RunTime_Add_Button)
			{
				Timer_RunTime_Pro(8,true);
			}
		}
		//@29-Group9����ʱʱ��+��-����ť
		else if((event.getSource()==Test_Target9_RunTime_Sub_Button)||(event.getSource()==Test_Target9_RunTime_Add_Button))
		{
			//@-��ʱ-
			if(event.getSource()==Test_Target9_RunTime_Sub_Button)
			{
				Timer_RunTime_Pro(9,false);
			}
			//@-��ʱ+
			else if(event.getSource()==Test_Target9_RunTime_Add_Button)
			{
				Timer_RunTime_Pro(9,true);
			}
		}
		//@30-Group10����ʱʱ��+��-����ť
		else if((event.getSource()==Test_Target10_RunTime_Sub_Button)||(event.getSource()==Test_Target10_RunTime_Add_Button))
		{
			//@-��ʱ-
			if(event.getSource()==Test_Target10_RunTime_Sub_Button)
			{
				Timer_RunTime_Pro(10,false);
			}
			//@-��ʱ+
			else if(event.getSource()==Test_Target10_RunTime_Add_Button)
			{
				Timer_RunTime_Pro(10,true);
			}
		}

	}

	/**��ʱ����ʱʱ�䴦��
	 *
	 * @param Target
	 * @param Dir  false:Sub   true:Add
	 */
	private void Timer_RunTime_Pro(int Target, boolean Dir)
	{
		//@-Group��
		if(GroupRunLevel==1)
		{
			//@-Add
			if(Dir==true)
			{
				int time = 0;

				//@-�ж�ʱ�伶��
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

				//@-�޸�Group������Nodeֵ
				for(int i=0;i<10;i++)
				ScreensFramework.DZFZ_Group[Target-1].Group_DZFZ[i].Set_Pud_Timing(time);
			}
			//@-Sub
			else if(Dir==false)
			{
				//@-�ж�ʱ�伶��
				if(TimerPro_Level==1)
				{
					int time = ScreensFramework.DZFZ_Group[Target-1].Get_Group_Timing();
					time = time -3600;
					if(time>=0)
					{
						ScreensFramework.DZFZ_Group[Target-1].Set_Group_Timing(time);
						//@-�޸�Group������Nodeֵ
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
						//@-�޸�Group������Nodeֵ
						for(int i=0;i<10;i++)
						ScreensFramework.DZFZ_Group[Target-1].Group_DZFZ[i].Set_Pud_Timing(time);
					}
				}


			}
		}
		//@-Node��
		else if(GroupRunLevel==2)
		{
			//@-Add
			if(Dir==true)
			{
				//@-�ж�ʱ�伶��
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
				//@-�ж�ʱ�伶��
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






	/**�ϻ���Ʒ������뽹���л�
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



    /**����Ӧ������
     *
     */
    private void AlarmPro()
    {
    	//@-��ù���Ӧ������Group
    	QC_DisplayTimer.CAN_AlarmPro_Group=Aodi_AlarmPro_Group_ComboBox.getSelectionModel().getSelectedIndex();
    	//@-��ù���Ӧ������Node
    	QC_DisplayTimer.CAN_AlarmPro_Node=Aodi_AlarmPro_Node_ComboBox.getSelectionModel().getSelectedIndex();

    	QC_DisplayTimer.CAN_Send_AlarmPro_Flag = true;
    }


    /**�����Ϣ���
     *
     * @param Info
     */
   synchronized private void Aodi_Info_Put(String Info)
    {
	   Aodi_InfoPut_TextArea.appendText("@"+QC_DisplayTimer.Time_Str1+"-"+Info);
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
