package AODI_INFO;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Line;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;


/**
 * Login Controller.
 */
public class INFOController implements Initializable, ControlledScreen {

	//@1-������Ӧ�ó���ӿ�
	private ScreensController myController;
	
	@FXML
	private AnchorPane Root;
	
	//@1-����˳�
    @FXML
    private ImageView App_Exit;
    
    //@-ʱ��
    @FXML
    private Label System_Time;
    
	
	
	
	//@26-ͼƬ��Դ-LED��
	private Image Image_state_ok;
	//@26-ͼƬ��Դ-LED��
	private Image Image_state_error;
	//@26-ͼƬ��Դ-LED��
	private Image Image_state_warning;
	
//	private Socket client;
//	private int Send_flag=1;
//	String send_data=new String("DENG_LU|��ر������Զ�|820715|33ffde05474b363818581843||||#");
//	String send_data1=new String("POP|#");
//	byte[] data1 = send_data.getBytes();
//	byte[] data2 = send_data1.getBytes();
//	byte[] data3 = new byte[1024];
//	int flag=1;
//	Socket socket;
	
	//TCP���б�־
	public static boolean TCP_Run_Flag=false;
	//TCP��¼���������ݰ������л�
	public static boolean TCP_Send_Data=false;
	//TCP����״̬
	public static int TCP_State=0;  //1:������  2:ip��port������  3:�Ͽ�
	
    //��������
	public static SimpleStringProperty DataProperty_Main = new SimpleStringProperty();
	//change������
	private ChangeListener changelisten1;
	
	public static String  Main_Data_Sting=null;
	public static boolean Main_Data_flag=false;
	
	public static boolean Noti_Send_Flag=false;
	
	public static String Target_IP;
	public static int    Target_Port;
	public static String Target_DeviceID;
	public static String Target_CarNumber;
	public static String Target_Pwd;
	
	private boolean id_flag=true;
	

	/**��¼�����ʼ��
	 *
	 */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	
    	//@7-ͼƬ��Դ��ʼ��
		Image_state_ok = new Image(INFOController.class.getResourceAsStream("status_green.png"));
		Image_state_error = new Image(INFOController.class.getResourceAsStream("status_red.png"));
		Image_state_warning = new Image(INFOController.class.getResourceAsStream("status_yellow.png"));
    	

    	
    	//@1-����ͬ��
    	DataProperty_Main.addListener(changelisten1 = new ChangeListener(){
			@Override
			public void changed(ObservableValue arg0, Object oldval, Object newval) {
				// TODO Auto-generated method stub

				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						
						System_Time.setText(INFO_DisplayTimer.Time_Str);
						
					}
				});					

			}
    	});
    	
    	
    }
    

    
    /**����������
     * 
     * @param event
     */
//    @FXML
//    public void Button_Pro(ActionEvent event)
//    {
//    	//@1-��������
////    	if(event.getSource()==Button_Connect)
////    	{
////    		TCP_Connect();
////    	}
////    	//@2-����Text���
////    	else if(event.getSource()==Button_CleanText)
////    	{
////    		TextAre_RecText.setText("");
////    	}
//    	
//    }
    
   
    
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
