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


import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/**
 * Login Controller.
 */
public class StrategyControllerNew implements Initializable, ControlledScreen {

	//----------����ѹ------------
	//@1-����ѹ-��ʼ��ѹ
	@FXML
	private TextField Charging_VoltageStart;
	//@2-����ѹ-��ֹ��ѹ
	@FXML
	private TextField Charging_VoltageEnd;
	//@3-����ѹ-��ֹ��ѹ��ʱʱ��
	@FXML
	private TextField Charging_VoltageEnd_Delay;
	//@4-����ѹ-������ѹ
	@FXML
	private TextField Charging_VoltageStep;
    //@5-����ѹ-�����ѹ1
	@FXML
	private TextField Charging_VoltageArea1;
    //@6-����ѹ-�����ѹ1-low
	@FXML
	private TextField Charging_VoltageArea1_Low;
    //@7-����ѹ-�����ѹ1-hight
	@FXML
	private TextField Charging_VoltageArea1_High;
    //@8-����ѹ-�����ѹ2
	@FXML
	private TextField Charging_VoltageArea2;
    //@9-����ѹ-�����ѹ2-low
	@FXML
	private TextField Charging_VoltageArea2_Low;
    //@10-����ѹ-�����ѹ2-hight
	@FXML
	private TextField Charging_VoltageArea2_High;
    //@11-����ѹ-�����ѹ3
	@FXML
	private TextField Charging_VoltageArea3;
    //@12-����ѹ-�����ѹ3-low
	@FXML
	private TextField Charging_VoltageArea3_Low;
    //@13-����ѹ-�����ѹ3-hight
	@FXML
	private TextField Charging_VoltageArea3_High;
    //@14-����ѹ-�����ѹ4
	@FXML
	private TextField Charging_VoltageArea4;
    //@15-����ѹ-�����ѹ4-low
	@FXML
	private TextField Charging_VoltageArea4_Low;
    //@16-����ѹ-�����ѹ4-hight
	@FXML
	private TextField Charging_VoltageArea4_High;
	
	//----------������------------
	//@4-������-Ԥ�����
	@FXML
	private TextField Charging_Current1;
    //@5-������-Ԥ�����-low
	@FXML
	private TextField Charging_Current1_Low;
    //@6-������-Ԥ�����-hight
	@FXML
	private TextField Charging_Current1_High;
    //@7-������-�����1
	@FXML
	private TextField Charging_Current2;
    //@8-������-�����1-low
	@FXML
	private TextField Charging_Current2_Low;
    //@9-������-�����1-hight
	@FXML
	private TextField Charging_Current2_High;
    //@10-������-�����2
	@FXML
	private TextField Charging_Current3;
    //@11-������-�����2-low
	@FXML
	private TextField Charging_Current3_Low;
    //@12-������-�����2-hight
	@FXML
	private TextField Charging_Current3_High;
    //@13-������-�������
	@FXML
	private TextField Charging_Current4;
    //@14-������-�������-low
	@FXML
	private TextField Charging_Current4_Low;
    //@15-������-�������-hight
	@FXML
	private TextField Charging_Current4_High;
	
	//----------����׼��------------
	//@-����׼��-׼���ж�1
	@FXML
	private CheckBox Strategy_VoltageArea1;
	//@-����׼��-׼���ж�2
	@FXML
	private CheckBox Strategy_VoltageArea2;
	//@-����׼��-׼���ж�3
	@FXML
	private CheckBox Strategy_VoltageArea3;
	//@-����׼��-׼���ж�4
	@FXML
	private CheckBox Strategy_VoltageArea4;
	
    //@9-����׼��-�ļ���
    @FXML
    private TextField   Strategy_SaveFileName;
    //@24-����׼��-����ִ�а�ť
    @FXML
    private Button     Strategy_Save_Button;
    //@24-����׼��-ȡ����ť
    @FXML
    private Button     Strategy_Cancel_Button;
	
    
//-----------------------------------------------------------------------
	//@1-������Ӧ�ó���ӿ�
	private ScreensController myController;
	//@-����׼���ļ���
	public static String Strategy_FileName;
    //@-�����ļ�ͬ��
	public static SimpleStringProperty StrategyFile = new SimpleStringProperty();

	
	//@8-����׼��-��ʼ��ѹ
	public static float  Temp_VoltageStart;
	//@9-����׼��-��ֹ��ѹ
	public static float  Temp_VoltageEnd;
	//@9-����׼��-��ֹ��ѹ��ʱ
	public static float  Temp_VoltageEnd_Delay;
	//@10-����׼��-������ѹ
	public static float  Temp_VoltageStep;
	
	//@10-����׼��-�����ѹ1
	public static float  Temp_VoltageArea1;
	//@10-����׼��-�����ѹ1-Low
	public static float  Temp_VoltageArea1_Low;
	//@10-����׼��-�����ѹ1-Hight
	public static float  Temp_VoltageArea1_High;
	//@10-����׼��-�����ѹ2
	public static float  Temp_VoltageArea2;
	//@10-����׼��-�����ѹ2-Low
	public static float  Temp_VoltageArea2_Low;
	//@10-����׼��-�����ѹ2-Hight
	public static float  Temp_VoltageArea2_High;
	//@10-����׼��-�����ѹ3
	public static float  Temp_VoltageArea3;
	//@10-����׼��-�����ѹ3-Low
	public static float  Temp_VoltageArea3_Low;
	//@10-����׼��-�����ѹ3-Hight
	public static float  Temp_VoltageArea3_High;
	//@10-����׼��-�����ѹ4
	public static float  Temp_VoltageArea4;
	//@10-����׼��-�����ѹ4-Low
	public static float  Temp_VoltageArea4_Low;
	//@10-����׼��-�����ѹ4-Hight
	public static float  Temp_VoltageArea4_High;
	
	//@10-����׼��-Ԥ�����
	public static float  Temp_Current1;
	//@10-����׼��-Ԥ�����-Low
	public static float  Temp_Current1_Low;
	//@10-����׼��-Ԥ�����-Hight
	public static float  Temp_Current1_High;
	//@10-����׼��-�����1
	public static float  Temp_Current2;
	//@10-����׼��-�����1-Low
	public static float  Temp_Current2_Low;
	//@10-����׼��-�����1-Hight
	public static float  Temp_Current2_High;
	//@10-����׼��-�����2
	public static float  Temp_Current3;
	//@10-����׼��-�����2-Low
	public static float  Temp_Current3_Low;
	//@10-����׼��-�����2-Hight
	public static float  Temp_Current3_High;
	//@10-����׼��-�������
	public static float  Temp_Current4;
	//@10-����׼��-�������-Low
	public static float  Temp_Current4_Low;
	//@10-����׼��-�������-Hight
	public static float  Temp_Current4_High;
	
	
	//@-���������ѹ1ѡ���־
	public static boolean VoltageArea1_Sel_Flag=true;
	//@-���������ѹ2ѡ���־
	public static boolean VoltageArea2_Sel_Flag=false;
	//@-���������ѹ3ѡ���־
	public static boolean VoltageArea3_Sel_Flag=false;
	//@-���������ѹ4ѡ���־
	public static boolean VoltageArea4_Sel_Flag=false;
    
    //@34-change������
	private ChangeListener changelisten1;
	//@-�������óɹ���־
	public static boolean StrategySet_Flag=false;
    
	/**��¼�����ʼ��
	 *
	 */
	@Override
    public void initialize(URL location, ResourceBundle resources) {
    	
    	//@1-���ݹ�����        
        TextField_CharFilter(Charging_VoltageStart);
        TextField_CharFilter(Charging_VoltageEnd);
        TextField_CharFilter(Charging_VoltageEnd_Delay);
        TextField_CharFilter(Charging_VoltageStep);
        TextField_CharFilter(Charging_VoltageArea1);
        TextField_CharFilter(Charging_VoltageArea1_Low);
        TextField_CharFilter(Charging_VoltageArea1_High);
        TextField_CharFilter(Charging_VoltageArea2);
        TextField_CharFilter(Charging_VoltageArea2_Low);
        TextField_CharFilter(Charging_VoltageArea2_High);
        TextField_CharFilter(Charging_VoltageArea3);
        TextField_CharFilter(Charging_VoltageArea3_Low);
        TextField_CharFilter(Charging_VoltageArea3_High);
        TextField_CharFilter(Charging_VoltageArea4);
        TextField_CharFilter(Charging_VoltageArea4_Low);
        TextField_CharFilter(Charging_VoltageArea4_High);
        
        TextField_CharFilter(Charging_Current1);
        TextField_CharFilter(Charging_Current1_Low);
        TextField_CharFilter(Charging_Current1_High);
        TextField_CharFilter(Charging_Current2);
        TextField_CharFilter(Charging_Current2_Low);
        TextField_CharFilter(Charging_Current2_High);
        TextField_CharFilter(Charging_Current3);
        TextField_CharFilter(Charging_Current3_Low);
        TextField_CharFilter(Charging_Current3_High);
        TextField_CharFilter(Charging_Current4);
        TextField_CharFilter(Charging_Current4_Low);
        TextField_CharFilter(Charging_Current4_High);
        
		//@2-�����ļ�ͬ��
		StrategyFile.addListener(changelisten1=new ChangeListener(){
			@Override
			public void changed(ObservableValue arg0, Object oldval, Object newval) {
				// TODO Auto-generated method stub	
				if(newval.toString().equals(new String("New")))          //�½������ļ�
				{
					//@-�������óɹ���־��λ
					StrategySet_Flag=false;
					//@-�����½������ļ���
					Strategy_SaveFileName.setEditable(true);
					Strategy_SaveFileName.setDisable(false);
					Strategy_SaveFileName.setText("New.aodi");
					//@-���ز�������
					Strategy_Load(1);
				}
				else if(newval.toString().equals(new String("Load")))    //���ز����ļ�
				{
					//@-�������óɹ���־��λ
					StrategySet_Flag=false;
					//@-���ò����ļ���
					Strategy_SaveFileName.setEditable(false);
					Strategy_SaveFileName.setDisable(true);
					Strategy_SaveFileName.setText(LoginController1.StrategyLoadFile_Name);
					//@-���ز����ļ�����
					ScreensFramework.Main_Config.Load_Config(LoginController1.StrategyLoadFile_Path);
					//@-���ز�������
					Strategy_Load(2);
				}
				
				StrategyFile.setValue("None");
			}
    	});	
    }
    
    
	/**
	 * 
	 * @param mode
	 */
    private void Strategy_Load(int mode)
    {
    	//@-�½������ļ�
    	if(mode==1)
    	{
    		//@-��ʾ���ز�������-��ʼ��ѹ
    		Charging_VoltageStart.setText("50");
			//@-��ʾ���ز�������-��ֹ��ѹ
    		Charging_VoltageEnd.setText("80");
			//@-��ʾ���ز�������-��ֹ��ѹ��ʱ
    		Charging_VoltageEnd.setText("10");
			//@-��ʾ���ز�������-������ѹ
    		Charging_VoltageStep.setText("0.1");
    		
			//@-��ʾ���ز�������-�����ѹ1
    		Charging_VoltageArea1.setText("60");
			//@-��ʾ���ز�������-�����ѹ1-Low
    		Charging_VoltageArea1_Low.setText("0");
			//@-��ʾ���ز�������-�����ѹ1-High
    		Charging_VoltageArea1_High.setText("0");
			//@-��ʾ���ز�������-�����ѹ2
    		Charging_VoltageArea2.setText("70");
			//@-��ʾ���ز�������-�����ѹ2-Low
    		Charging_VoltageArea2_Low.setText("0");
			//@-��ʾ���ز�������-�����ѹ2-High
    		Charging_VoltageArea2_High.setText("0");
			//@-��ʾ���ز�������-�����ѹ3
    		Charging_VoltageArea3.setText("75");
			//@-��ʾ���ز�������-�����ѹ3-Low
    		Charging_VoltageArea3_Low.setText("0");
			//@-��ʾ���ز�������-�����ѹ3-High
    		Charging_VoltageArea3_High.setText("0");
			//@-��ʾ���ز�������-�����ѹ4
    		Charging_VoltageArea4.setText("80");
			//@-��ʾ���ز�������-�����ѹ4-Low
    		Charging_VoltageArea4_Low.setText("0");
			//@-��ʾ���ز�������-�����ѹ4-High
    		Charging_VoltageArea4_High.setText("0");
    		
			//@-��ʾ���ز�������-Ԥ�����
    		Charging_Current1.setText("2");
			//@-��ʾ���ز�������-Ԥ�����-Low
    		Charging_Current1_Low.setText("0");
			//@-��ʾ���ز�������-Ԥ�����-High
    		Charging_Current1_High.setText("0");
			//@-��ʾ���ز�������-�����1
    		Charging_Current2.setText("16");
			//@-��ʾ���ز�������-�����1-Low
    		Charging_Current2_Low.setText("0");
			//@-��ʾ���ز�������-�����1-High
    		Charging_Current2_High.setText("0");
			//@-��ʾ���ز�������-�����2
    		Charging_Current3.setText("11");
			//@-��ʾ���ز�������-�����2-Low
    		Charging_Current3_Low.setText("0");
			//@-��ʾ���ز�������-�����2-High
    		Charging_Current3_High.setText("0");
			//@-��ʾ���ز�������-�������
    		Charging_Current4.setText("2");
			//@-��ʾ���ز�������-�������-Low
    		Charging_Current4_Low.setText("0");
			//@-��ʾ���ز�������-�������-High
    		Charging_Current4_High.setText("0");
    		
			
			//@-���ز�������-�����ѹ1ѡ���־
    		VoltageArea1_Sel_Flag=true;
			//@-���ز�������-�����ѹ2ѡ���־
    		VoltageArea2_Sel_Flag=false;
			//@-���ز�������-�����ѹ3ѡ���־
    		VoltageArea3_Sel_Flag=false;
			//@-���ز�������-�����ѹ4ѡ���־
    		VoltageArea4_Sel_Flag=false;
			
			//@-���Խ׶�����
    		Strategy_VoltageArea1.setSelected(VoltageArea1_Sel_Flag);
    		Strategy_VoltageArea2.setSelected(VoltageArea2_Sel_Flag);
    		Strategy_VoltageArea3.setSelected(VoltageArea3_Sel_Flag);
    		Strategy_VoltageArea4.setSelected(VoltageArea4_Sel_Flag);
    	}
    	//@-���ز����ļ�
    	else if(mode==2)
    	{
    		//@-��ʾ���ز�������-��ʼ��ѹ
    		Charging_VoltageStart.setText(""+ScreensFramework.Main_Config.Def_VoltageStart);
			//@-��ʾ���ز�������-��ֹ��ѹ
    		Charging_VoltageEnd.setText(""+ScreensFramework.Main_Config.Def_VoltageEnd);
			//@-��ʾ���ز�������-��ֹ��ѹ��ʱ
    		Charging_VoltageEnd_Delay.setText(""+ScreensFramework.Main_Config.Def_VoltageEnd_Delay);
			//@-��ʾ���ز�������-������ѹ
    		Charging_VoltageStep.setText(""+ScreensFramework.Main_Config.Def_VoltageStep);
    		
			//@-��ʾ���ز�������-�����ѹ1
    		Charging_VoltageArea1.setText(""+ScreensFramework.Main_Config.Def_VoltageArea1);
			//@-��ʾ���ز�������-�����ѹ1-Low
    		Charging_VoltageArea1_Low.setText(""+ScreensFramework.Main_Config.Def_VoltageArea1_Low);
			//@-��ʾ���ز�������-�����ѹ1-High
    		Charging_VoltageArea1_High.setText(""+ScreensFramework.Main_Config.Def_VoltageArea1_High);
			//@-��ʾ���ز�������-�����ѹ2
    		Charging_VoltageArea2.setText(""+ScreensFramework.Main_Config.Def_VoltageArea2);
			//@-��ʾ���ز�������-�����ѹ2-Low
    		Charging_VoltageArea2_Low.setText(""+ScreensFramework.Main_Config.Def_VoltageArea2_Low);
			//@-��ʾ���ز�������-�����ѹ2-High
    		Charging_VoltageArea2_High.setText(""+ScreensFramework.Main_Config.Def_VoltageArea2_High);
			//@-��ʾ���ز�������-�����ѹ3
    		Charging_VoltageArea3.setText(""+ScreensFramework.Main_Config.Def_VoltageArea3);
			//@-��ʾ���ز�������-�����ѹ3-Low
    		Charging_VoltageArea3_Low.setText(""+ScreensFramework.Main_Config.Def_VoltageArea3_Low);
			//@-��ʾ���ز�������-�����ѹ3-High
    		Charging_VoltageArea3_High.setText(""+ScreensFramework.Main_Config.Def_VoltageArea3_High);
			//@-��ʾ���ز�������-�����ѹ4
    		Charging_VoltageArea4.setText(""+ScreensFramework.Main_Config.Def_VoltageArea4);
			//@-��ʾ���ز�������-�����ѹ4-Low
    		Charging_VoltageArea4_Low.setText(""+ScreensFramework.Main_Config.Def_VoltageArea4_Low);
			//@-��ʾ���ز�������-�����ѹ4-High
    		Charging_VoltageArea4_High.setText(""+ScreensFramework.Main_Config.Def_VoltageArea4_High);
    		
			//@-��ʾ���ز�������-Ԥ�����
    		Charging_Current1.setText(""+ScreensFramework.Main_Config.Def_Current1);
			//@-��ʾ���ز�������-Ԥ�����-Low
    		Charging_Current1_Low.setText(""+ScreensFramework.Main_Config.Def_Current1_Low);
			//@-��ʾ���ز�������-Ԥ�����-High
    		Charging_Current1_High.setText(""+ScreensFramework.Main_Config.Def_Current1_High);
			//@-��ʾ���ز�������-�����1
    		Charging_Current2.setText(""+ScreensFramework.Main_Config.Def_Current2);
			//@-��ʾ���ز�������-�����1-Low
    		Charging_Current2_Low.setText(""+ScreensFramework.Main_Config.Def_Current2_Low);
			//@-��ʾ���ز�������-�����1-High
    		Charging_Current2_High.setText(""+ScreensFramework.Main_Config.Def_Current2_High);
			//@-��ʾ���ز�������-�����2
    		Charging_Current3.setText(""+ScreensFramework.Main_Config.Def_Current3);
			//@-��ʾ���ز�������-�����2-Low
    		Charging_Current3_Low.setText(""+ScreensFramework.Main_Config.Def_Current3_Low);
			//@-��ʾ���ز�������-�����2-High
    		Charging_Current3_High.setText(""+ScreensFramework.Main_Config.Def_Current3_High);
			//@-��ʾ���ز�������-�������
    		Charging_Current4.setText(""+ScreensFramework.Main_Config.Def_Current4);
			//@-��ʾ���ز�������-�������-Low
    		Charging_Current4_Low.setText(""+ScreensFramework.Main_Config.Def_Current4_Low);
			//@-��ʾ���ز�������-�������-High
    		Charging_Current4_High.setText(""+ScreensFramework.Main_Config.Def_Current4_High);
    		
    		
			//@-���ز�������-Stage1ѡ���־
    		VoltageArea1_Sel_Flag=ScreensFramework.Main_Config.Def_VoltageArea1_Sel;
			//@-���ز�������-Stage2ѡ���־
    		VoltageArea2_Sel_Flag=ScreensFramework.Main_Config.Def_VoltageArea2_Sel;
			//@-���ز�������-Stage3ѡ���־
    		VoltageArea3_Sel_Flag=ScreensFramework.Main_Config.Def_VoltageArea3_Sel;
			//@-���ز�������-Stage4ѡ���־
    		VoltageArea4_Sel_Flag=ScreensFramework.Main_Config.Def_VoltageArea4_Sel;
			
			//@-���Խ׶�����
    		Strategy_VoltageArea1.setSelected(VoltageArea1_Sel_Flag);
    		Strategy_VoltageArea2.setSelected(VoltageArea2_Sel_Flag);
    		Strategy_VoltageArea3.setSelected(VoltageArea3_Sel_Flag);
    		Strategy_VoltageArea4.setSelected(VoltageArea4_Sel_Flag);
    		
    		//System.out.println("data:"+ScreensFramework.Main_Config.Def_VoltageEnd);
    		
    	}
    }
    
    /**�׶μ���ΧЧ����ʾ
     * 
     */
	private void StageRange_Display_Animation(Rectangle Rect) 
	{
		//@-���Խ׶�Ч����ʾTimeline
		Timeline Stage_Timeline;
		//@-����Ч��ִ�ж���
		Rectangle target;
		
		//@-���Զ���
		target=Rect;

		Stage_Timeline = new Timeline();
		Stage_Timeline.setCycleCount(1);
		Stage_Timeline.setAutoReverse(false);
		
		Stage_Timeline.getKeyFrames().addAll(
				new KeyFrame(Duration.ZERO,
						new KeyValue(target.opacityProperty(), 0.3)						
				),
				
				new KeyFrame(new Duration(500),
						new KeyValue(target.opacityProperty(), 0, Interpolator.EASE_BOTH)
				)
		);
		
		Stage_Timeline.play();
	}
    
	
    /**�׶β���Ч����ʾ
     * 
     */
	private void StageStep_Display_Animation(Line line) 
	{
		//@-���Խ׶β���Ч����ʾTimeline
		Timeline Line_Timeline;
		//@-����Ч��ִ�ж���
		Line target;
		
		//@-���Զ���
		target=line;

		Line_Timeline = new Timeline();
		Line_Timeline.setCycleCount(1);
		Line_Timeline.setAutoReverse(false);
	
		Line_Timeline.getKeyFrames().addAll(
				new KeyFrame(Duration.ZERO,
						new KeyValue(target.strokeProperty(), (Color.RED))						
				),
				
				new KeyFrame(new Duration(500),
						new KeyValue(target.strokeProperty(), (Color.WHITE), Interpolator.EASE_BOTH)
				)
		);
		
		Line_Timeline.play();
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
                if (Text.getText().length() > 5) {
                    String s = Text.getText().substring(0, 5);
                    Text.setText(s);
                }
            }
        });   	
    }

    /**����������
     * 
     * @param event
     */
    @FXML
    public void Button_Pro(ActionEvent event)
    {
    	int check_result;

    	//@2-����ִ�а�ť
    	if((event.getSource()==Strategy_Save_Button))
    	{
    		//@-���Ա���-�����ļ���
    		Strategy_FileName = Strategy_SaveFileName.getText();
    		
    		//@-���Ա���-����׼��-��ʼ��ѹ
    		Temp_VoltageStart =  Float.parseFloat(Charging_VoltageStart.getText());
    		//@-���Ա���-����׼��-��ֹ��ѹ
    		Temp_VoltageEnd =  Float.parseFloat(Charging_VoltageEnd.getText());
    		//@-���Ա���-����׼��-��ֹ��ѹ��ʱ
    		Temp_VoltageEnd_Delay =  Float.parseFloat(Charging_VoltageEnd_Delay.getText());
    		//@-���Ա���-����׼��-������ѹ
    		Temp_VoltageStep =  Float.parseFloat(Charging_VoltageStep.getText());
    		
    		//@-���Ա���-����׼��-�����ѹ1
    		Temp_VoltageArea1 =  Float.parseFloat(Charging_VoltageArea1.getText());
    		//@-���Ա���-����׼��-�����ѹ1-Low
    		Temp_VoltageArea1_Low =  Float.parseFloat(Charging_VoltageArea1_Low.getText());
    		//@-���Ա���-����׼��-�����ѹ1-High
    		Temp_VoltageArea1_High =  Float.parseFloat(Charging_VoltageArea1_High.getText());
    		//@-���Ա���-����׼��-�����ѹ2
    		Temp_VoltageArea2 =  Float.parseFloat(Charging_VoltageArea2.getText());
    		//@-���Ա���-����׼��-�����ѹ2-Low
    		Temp_VoltageArea2_Low =  Float.parseFloat(Charging_VoltageArea2_Low.getText());
    		//@-���Ա���-����׼��-�����ѹ2-High
    		Temp_VoltageArea2_High =  Float.parseFloat(Charging_VoltageArea2_High.getText());
    		//@-���Ա���-����׼��-�����ѹ3
    		Temp_VoltageArea3 =  Float.parseFloat(Charging_VoltageArea3.getText());
    		//@-���Ա���-����׼��-�����ѹ3-Low
    		Temp_VoltageArea3_Low =  Float.parseFloat(Charging_VoltageArea3_Low.getText());
    		//@-���Ա���-����׼��-�����ѹ3-High
    		Temp_VoltageArea3_High =  Float.parseFloat(Charging_VoltageArea3_High.getText());
    		//@-���Ա���-����׼��-�����ѹ4
    		Temp_VoltageArea4 =  Float.parseFloat(Charging_VoltageArea4.getText());
    		//@-���Ա���-����׼��-�����ѹ4-Low
    		Temp_VoltageArea4_Low =  Float.parseFloat(Charging_VoltageArea4_Low.getText());
    		//@-���Ա���-����׼��-�����ѹ4-High
    		Temp_VoltageArea4_High =  Float.parseFloat(Charging_VoltageArea4_High.getText());
    		
    		//@-���Ա���-����׼��-Ԥ�����
    		Temp_Current1 =  Float.parseFloat(Charging_Current1.getText());
    		//@-���Ա���-����׼��-Ԥ�����-Low
    		Temp_Current1_Low =  Float.parseFloat(Charging_Current1_Low.getText());
    		//@-���Ա���-����׼��-Ԥ�����-High
    		Temp_Current1_High =  Float.parseFloat(Charging_Current1_High.getText());
    		//@-���Ա���-����׼��-�����1
    		Temp_Current2 =  Float.parseFloat(Charging_Current2.getText());
    		//@-���Ա���-����׼��-�����1-Low
    		Temp_Current2_Low =  Float.parseFloat(Charging_Current2_Low.getText());
    		//@-���Ա���-����׼��-�����1-High
    		Temp_Current2_High =  Float.parseFloat(Charging_Current2_High.getText());
    		//@-���Ա���-����׼��-�����2
    		Temp_Current3 =  Float.parseFloat(Charging_Current3.getText());
    		//@-���Ա���-����׼��-�����2-Low
    		Temp_Current3_Low =  Float.parseFloat(Charging_Current3_Low.getText());
    		//@-���Ա���-����׼��-�����2-High
    		Temp_Current3_High =  Float.parseFloat(Charging_Current3_High.getText());
    		//@-���Ա���-����׼��-�������
    		Temp_Current4 =  Float.parseFloat(Charging_Current4.getText());
    		//@-���Ա���-����׼��-�������-Low
    		Temp_Current4_Low =  Float.parseFloat(Charging_Current4_Low.getText());
    		//@-���Ա���-����׼��-�������-High
    		Temp_Current4_High =  Float.parseFloat(Charging_Current4_High.getText());
    		
    		//@-���Ա���-����׼��-�����ѹ1ѡ���־
    		VoltageArea1_Sel_Flag = Strategy_VoltageArea1.isSelected();
    		//@-���Ա���-����׼��-�����ѹ2ѡ���־
    		VoltageArea2_Sel_Flag = Strategy_VoltageArea2.isSelected();
    		//@-���Ա���-����׼��-�����ѹ3ѡ���־
    		VoltageArea3_Sel_Flag = Strategy_VoltageArea3.isSelected();
    		//@-���Ա���-����׼��-�����ѹ4ѡ���־
    		VoltageArea4_Sel_Flag = Strategy_VoltageArea4.isSelected();
    		
    		//@-��������Ƿ���ϲ����߼�
    		check_result=Check_Data();
    		
    		if(check_result==0)
    		{
	    		//@-�½������ļ�
	    		if(LoginController1.StrategyLoadFile_Mode==1)
	    		{
		    		int SaveResult = ScreensFramework.Main_Config.CreateSave_Config(2,"./"+Strategy_FileName,false);
		    		if(SaveResult==2)
		    		{
	    				//@-������������
	    				ScreensFramework.Main_Config.Load_Config("./"+Strategy_FileName);
		    					    		
			    		//@-�������óɹ���־��λ
			    		StrategySet_Flag=true;
			    		
			    		ScreensFramework.PageChange.set("main");
		    		}
		    		else if(SaveResult==1)
		    		{
		    			ScreensFramework.Show_Noti("Warning", "�����ļ�:"+Strategy_FileName+"�Ѵ���!");
		    		}
		    		else if(SaveResult==3)
		    		{
		    			ScreensFramework.Show_Noti("Error", "�����ļ���:"+Strategy_FileName+"����!");
		    		}
	    		}
	    		//@-���ز����ļ�
	    		else if(LoginController1.StrategyLoadFile_Mode==2)
	    		{
	    			
	    			int SaveResult = ScreensFramework.Main_Config.CreateSave_Config(2,LoginController1.StrategyLoadFile_Path,true);
		    		
	    			if(SaveResult!=3)
	    			{
	    				//@-������������
	    				ScreensFramework.Main_Config.Load_Config(LoginController1.StrategyLoadFile_Path);
	    						    		
			    		//@-�������óɹ���־��λ
			    		StrategySet_Flag=true;
			    		
			    		ScreensFramework.PageChange.set("main");	
	    			}
	    			else if(SaveResult==3)
	    			{
	    				ScreensFramework.Show_Noti("Error", "�����ļ�:"+Strategy_FileName+"�������!");
	    			}
	    		}
    		}
    	}
    	
    	//@2-ȡ����ť
    	else if((event.getSource()==Strategy_Cancel_Button))
    	{    		
    		//@-�������óɹ���־��λ
    		StrategySet_Flag=false;
    		
    		ScreensFramework.PageChange.set("main");
    	}
    	
    }
    
    /*�������ݼ��
     * 
     * @return
     */
    private int Check_Data()
    {
    	float area_start;
    	float area_end;
    	float area_temp;
    	float area;
    	int error_num=0;
    	byte ok_num=0x00;
    	int sel_num=0;
    	    	
    	
    	//@-�����ѹ1ѡ��
    	if(VoltageArea1_Sel_Flag==true)
    	{
	    	//@-�ж������ѹ1�Ƿ�����Ҫ��
	    	area_start = Temp_VoltageArea1-Temp_VoltageArea1_Low;
	    	area_end =   Temp_VoltageArea1+Temp_VoltageArea1_High;
	    	area=area_end-area_start;
	    	sel_num=sel_num+1;
	    	if(new Float(area-Temp_VoltageStep).compareTo(new Float(0.1))>0)
	    	{
		    	if(new Float(area_start).compareTo(new Float(area_end))<0)
		    	{
		    		//@-�����ѹ1��ʼֵ���ڳ���ѹ��ʼֵ
		    		if(new Float(area_start).compareTo(new Float(Temp_VoltageStart))>=0)
		    		{
		    			ok_num = (byte)(ok_num|0x01);
		    		}
		    		else
		    		{
		    			error_num=1;
		    			ScreensFramework.Show_Noti("Warning", "�����ѹ1С�ڵ�ѹ��ʼֵ!");
		        		return (error_num);
		    		}
		    		
		    		//@-�����ѹ1��ֵֹС�ڳ���ѹ��ʼֵ
		    		if(new Float(area_end).compareTo(new Float(Temp_VoltageEnd))<=0)
		    		{
		    			ok_num = (byte)(ok_num|0x02);
		    		}
		    		else
		    		{
		    			error_num=2;
		    			ScreensFramework.Show_Noti("Warning", "�����ѹ1���ڵ�ѹ��ֵֹ!");
		        		return (error_num);
		    		}
		    	}
	    	}
	    	else
	    	{
	    		error_num=3;
    			ScreensFramework.Show_Noti("Warning", "�����ѹ1��ⷶΧ����ڲ���ֵ0.1V!");
        		return (error_num);
	    	}
    	}
    	
    	//@-�����ѹ2ѡ��
    	if(VoltageArea2_Sel_Flag==true)
    	{
	    	//@-�ж������ѹ2�Ƿ�����Ҫ��
	    	area_start = Temp_VoltageArea2-Temp_VoltageArea2_Low;
	    	area_end =   Temp_VoltageArea2+Temp_VoltageArea2_High;
	    	area=area_end-area_start;
	    	sel_num=sel_num+1;
	    	if(new Float(area-Temp_VoltageStep).compareTo(new Float(0.1))>0)
	    	{
		    	if(new Float(area_start).compareTo(new Float(area_end))<0)
		    	{
		    		
		    		if(VoltageArea1_Sel_Flag==true)
		    		area_temp=Temp_VoltageArea1+Temp_VoltageArea1_High;
		    		else
		    		area_temp=Temp_VoltageStart;
		    		
		    		//@-�����ѹ2��ʼֵ����ǰһ�����ѹ��ֵֹ
		    		if(new Float(area_start).compareTo(new Float(area_temp))>=0)
		    		{
		    			ok_num = (byte)(ok_num|0x04);
		    		}
		    		else
		    		{
		    			error_num=4;
		    			ScreensFramework.Show_Noti("Warning", "�����ѹ2С��ǰһ�����ѹ��ֵֹ!");
		        		return (error_num);
		    		}
		    		
		    		//@-�����ѹ2��ֵֹС�ڳ���ѹ��ʼֵ
		    		if(new Float(area_end).compareTo(new Float(Temp_VoltageEnd))<=0)
		    		{
		    			ok_num = (byte)(ok_num|0x08);
		    		}
		    		else
		    		{
		    			error_num=5;
		    			ScreensFramework.Show_Noti("Warning", "�����ѹ2���ڵ�ѹ��ֵֹ!");
		        		return (error_num);
		    		}
		    	}
	    	}
	    	else
	    	{
	    		error_num=6;
    			ScreensFramework.Show_Noti("Warning", "�����ѹ2��ⷶΧ����ڲ���ֵ0.1V!");
        		return (error_num);
	    	}
    	}
    	
    	//@-�����ѹ3ѡ��
    	if(VoltageArea3_Sel_Flag==true)
    	{
	    	//@-�ж������ѹ3�Ƿ�����Ҫ��
	    	area_start = Temp_VoltageArea3-Temp_VoltageArea3_Low;
	    	area_end =   Temp_VoltageArea3+Temp_VoltageArea3_High;
	    	area=area_end-area_start;
	    	sel_num=sel_num+1;
	    	if(new Float(area-Temp_VoltageStep).compareTo(new Float(0.1))>0)
	    	{
		    	if(new Float(area_start).compareTo(new Float(area_end))<0)
		    	{
		    		if(VoltageArea2_Sel_Flag==true)
		    		area_temp=Temp_VoltageArea2+Temp_VoltageArea2_High;
		    		else
		    		{
			    		if(VoltageArea1_Sel_Flag==true)
			    		area_temp=Temp_VoltageArea1+Temp_VoltageArea1_High;
			    		else
			    		area_temp=Temp_VoltageStart;
		    		}
		    		
		    		//@-�����ѹ3��ʼֵ����ǰһ�����ѹ��ֵֹ
		    		if(new Float(area_start).compareTo(new Float(area_temp))>=0)
		    		{
		    			ok_num = (byte)(ok_num|0x10);
		    		}
		    		else
		    		{
		    			error_num=7;
		    			ScreensFramework.Show_Noti("Warning", "�����ѹ3С��ǰһ�����ѹ��ֵֹ!");
		        		return (error_num);
		    		}
		    		
		    		//@-�����ѹ3��ֵֹС�ڳ���ѹ��ʼֵ
		    		if(new Float(area_end).compareTo(new Float(Temp_VoltageEnd))<=0)
		    		{
		    			ok_num = (byte)(ok_num|0x20);
		    		}
		    		else
		    		{
		    			error_num=8;
		    			ScreensFramework.Show_Noti("Warning", "�����ѹ3���ڵ�ѹ��ֵֹ!");
		        		return (error_num);
		    		}
		    	}
	    	}
	    	else
	    	{
	    		error_num=9;
    			ScreensFramework.Show_Noti("Warning", "�����ѹ3��ⷶΧ����ڲ���ֵ0.1V!");
        		return (error_num);
	    	}
    	}
    	
    	//@-�����ѹ4ѡ��
    	if(VoltageArea4_Sel_Flag==true)
    	{
	    	//@-�ж������ѹ4�Ƿ�����Ҫ��
	    	area_start = Temp_VoltageArea4-Temp_VoltageArea4_Low;
	    	area_end =   Temp_VoltageArea4+Temp_VoltageArea4_High;
	    	area=area_end-area_start;
	    	sel_num=sel_num+1;
	    	if(new Float(area-Temp_VoltageStep).compareTo(new Float(0.1))>0)
	    	{
		    	if(new Float(area_start).compareTo(new Float(area_end))<0)
		    	{
		    		if(VoltageArea3_Sel_Flag==true)
		    		area_temp=Temp_VoltageArea3+Temp_VoltageArea3_High;
		    		else
		    		{
			    		if(VoltageArea2_Sel_Flag==true)
			    		area_temp=Temp_VoltageArea2+Temp_VoltageArea2_High;
			    		else
			    		{
				    		if(VoltageArea1_Sel_Flag==true)
				    		area_temp=Temp_VoltageArea1+Temp_VoltageArea1_High;
				    		else
				    		area_temp=Temp_VoltageStart;
			    		}
		    		}
	
		    		//@-�����ѹ3��ʼֵ����ǰһ�����ѹ��ֵֹ
		    		if(new Float(area_start).compareTo(new Float(area_temp))>=0)
		    		{
		    			ok_num = (byte)(ok_num|0x40);
		    		}
		    		else
		    		{
		    			error_num=10;
		    			ScreensFramework.Show_Noti("Warning", "�����ѹ4С��ǰһ�����ѹ��ֵֹ!");
		        		return (error_num);
		    		}
		    		
		    		//@-�����ѹ4��ֵֹС�ڳ���ѹ��ʼֵ
		    		if(new Float(area_end).compareTo(new Float(Temp_VoltageEnd))<=0)
		    		{
		    			ok_num = (byte)(ok_num|0x80);
		    		}
		    		else
		    		{
		    			error_num=11;
		    			ScreensFramework.Show_Noti("Warning", "�����ѹ4���ڵ�ѹ��ֵֹ!");
		        		return (error_num);
		    		}
		    	}
	    	}
	    	else
	    	{
	    		error_num=12;
    			ScreensFramework.Show_Noti("Warning", "�����ѹ4��ⷶΧ����ڲ���ֵ0.1V!");
        		return (error_num);
	    	}
    	}
    	
    	if(sel_num==0)
    	{
    		error_num=13;
    		ScreensFramework.Show_Noti("Warning", "û�������ѹ��ѡ��!");
    	}
    	
    	return (error_num);
    }
    
	@Override
	public void setScreenParent(ScreensController screenPage) {
		// TODO Auto-generated method stub
		myController = screenPage;
	}
    
}
