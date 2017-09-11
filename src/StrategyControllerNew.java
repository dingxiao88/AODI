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

	//----------充电电压------------
	//@1-充电电压-起始电压
	@FXML
	private TextField Charging_VoltageStart;
	//@2-充电电压-终止电压
	@FXML
	private TextField Charging_VoltageEnd;
	//@3-充电电压-终止电压延时时间
	@FXML
	private TextField Charging_VoltageEnd_Delay;
	//@4-充电电压-步进电压
	@FXML
	private TextField Charging_VoltageStep;
    //@5-充电电压-区域电压1
	@FXML
	private TextField Charging_VoltageArea1;
    //@6-充电电压-区域电压1-low
	@FXML
	private TextField Charging_VoltageArea1_Low;
    //@7-充电电压-区域电压1-hight
	@FXML
	private TextField Charging_VoltageArea1_High;
    //@8-充电电压-区域电压2
	@FXML
	private TextField Charging_VoltageArea2;
    //@9-充电电压-区域电压2-low
	@FXML
	private TextField Charging_VoltageArea2_Low;
    //@10-充电电压-区域电压2-hight
	@FXML
	private TextField Charging_VoltageArea2_High;
    //@11-充电电压-区域电压3
	@FXML
	private TextField Charging_VoltageArea3;
    //@12-充电电压-区域电压3-low
	@FXML
	private TextField Charging_VoltageArea3_Low;
    //@13-充电电压-区域电压3-hight
	@FXML
	private TextField Charging_VoltageArea3_High;
    //@14-充电电压-区域电压4
	@FXML
	private TextField Charging_VoltageArea4;
    //@15-充电电压-区域电压4-low
	@FXML
	private TextField Charging_VoltageArea4_Low;
    //@16-充电电压-区域电压4-hight
	@FXML
	private TextField Charging_VoltageArea4_High;
	
	//----------充电电流------------
	//@4-充电电流-预充电流
	@FXML
	private TextField Charging_Current1;
    //@5-充电电流-预充电流-low
	@FXML
	private TextField Charging_Current1_Low;
    //@6-充电电流-预充电流-hight
	@FXML
	private TextField Charging_Current1_High;
    //@7-充电电流-恒电流1
	@FXML
	private TextField Charging_Current2;
    //@8-充电电流-恒电流1-low
	@FXML
	private TextField Charging_Current2_Low;
    //@9-充电电流-恒电流1-hight
	@FXML
	private TextField Charging_Current2_High;
    //@10-充电电流-恒电流2
	@FXML
	private TextField Charging_Current3;
    //@11-充电电流-恒电流2-low
	@FXML
	private TextField Charging_Current3_Low;
    //@12-充电电流-恒电流2-hight
	@FXML
	private TextField Charging_Current3_High;
    //@13-充电电流-浮充电流
	@FXML
	private TextField Charging_Current4;
    //@14-充电电流-浮充电流-low
	@FXML
	private TextField Charging_Current4_Low;
    //@15-充电电流-浮充电流-hight
	@FXML
	private TextField Charging_Current4_High;
	
	//----------策略准则------------
	//@-策略准则-准则判断1
	@FXML
	private CheckBox Strategy_VoltageArea1;
	//@-策略准则-准则判断2
	@FXML
	private CheckBox Strategy_VoltageArea2;
	//@-策略准则-准则判断3
	@FXML
	private CheckBox Strategy_VoltageArea3;
	//@-策略准则-准则判断4
	@FXML
	private CheckBox Strategy_VoltageArea4;
	
    //@9-策略准则-文件名
    @FXML
    private TextField   Strategy_SaveFileName;
    //@24-策略准则-保存执行按钮
    @FXML
    private Button     Strategy_Save_Button;
    //@24-策略准则-取消按钮
    @FXML
    private Button     Strategy_Cancel_Button;
	
    
//-----------------------------------------------------------------------
	//@1-传递主应用程序接口
	private ScreensController myController;
	//@-策略准则文件名
	public static String Strategy_FileName;
    //@-策略文件同步
	public static SimpleStringProperty StrategyFile = new SimpleStringProperty();

	
	//@8-策略准则-起始电压
	public static float  Temp_VoltageStart;
	//@9-策略准则-终止电压
	public static float  Temp_VoltageEnd;
	//@9-策略准则-终止电压延时
	public static float  Temp_VoltageEnd_Delay;
	//@10-策略准则-步进电压
	public static float  Temp_VoltageStep;
	
	//@10-策略准则-区域电压1
	public static float  Temp_VoltageArea1;
	//@10-策略准则-区域电压1-Low
	public static float  Temp_VoltageArea1_Low;
	//@10-策略准则-区域电压1-Hight
	public static float  Temp_VoltageArea1_High;
	//@10-策略准则-区域电压2
	public static float  Temp_VoltageArea2;
	//@10-策略准则-区域电压2-Low
	public static float  Temp_VoltageArea2_Low;
	//@10-策略准则-区域电压2-Hight
	public static float  Temp_VoltageArea2_High;
	//@10-策略准则-区域电压3
	public static float  Temp_VoltageArea3;
	//@10-策略准则-区域电压3-Low
	public static float  Temp_VoltageArea3_Low;
	//@10-策略准则-区域电压3-Hight
	public static float  Temp_VoltageArea3_High;
	//@10-策略准则-区域电压4
	public static float  Temp_VoltageArea4;
	//@10-策略准则-区域电压4-Low
	public static float  Temp_VoltageArea4_Low;
	//@10-策略准则-区域电压4-Hight
	public static float  Temp_VoltageArea4_High;
	
	//@10-策略准则-预充电流
	public static float  Temp_Current1;
	//@10-策略准则-预充电流-Low
	public static float  Temp_Current1_Low;
	//@10-策略准则-预充电流-Hight
	public static float  Temp_Current1_High;
	//@10-策略准则-恒电流1
	public static float  Temp_Current2;
	//@10-策略准则-恒电流1-Low
	public static float  Temp_Current2_Low;
	//@10-策略准则-恒电流1-Hight
	public static float  Temp_Current2_High;
	//@10-策略准则-恒电流2
	public static float  Temp_Current3;
	//@10-策略准则-恒电流2-Low
	public static float  Temp_Current3_Low;
	//@10-策略准则-恒电流2-Hight
	public static float  Temp_Current3_High;
	//@10-策略准则-浮充电流
	public static float  Temp_Current4;
	//@10-策略准则-浮充电流-Low
	public static float  Temp_Current4_Low;
	//@10-策略准则-浮充电流-Hight
	public static float  Temp_Current4_High;
	
	
	//@-策略区域电压1选择标志
	public static boolean VoltageArea1_Sel_Flag=true;
	//@-策略区域电压2选择标志
	public static boolean VoltageArea2_Sel_Flag=false;
	//@-策略区域电压3选择标志
	public static boolean VoltageArea3_Sel_Flag=false;
	//@-策略区域电压4选择标志
	public static boolean VoltageArea4_Sel_Flag=false;
    
    //@34-change监听器
	private ChangeListener changelisten1;
	//@-策略配置成功标志
	public static boolean StrategySet_Flag=false;
    
	/**登录界面初始化
	 *
	 */
	@Override
    public void initialize(URL location, ResourceBundle resources) {
    	
    	//@1-数据过滤器        
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
        
		//@2-策略文件同步
		StrategyFile.addListener(changelisten1=new ChangeListener(){
			@Override
			public void changed(ObservableValue arg0, Object oldval, Object newval) {
				// TODO Auto-generated method stub	
				if(newval.toString().equals(new String("New")))          //新建策略文件
				{
					//@-策略配置成功标志复位
					StrategySet_Flag=false;
					//@-设置新建策略文件名
					Strategy_SaveFileName.setEditable(true);
					Strategy_SaveFileName.setDisable(false);
					Strategy_SaveFileName.setText("New.aodi");
					//@-加载策略数据
					Strategy_Load(1);
				}
				else if(newval.toString().equals(new String("Load")))    //加载策略文件
				{
					//@-策略配置成功标志复位
					StrategySet_Flag=false;
					//@-设置策略文件名
					Strategy_SaveFileName.setEditable(false);
					Strategy_SaveFileName.setDisable(true);
					Strategy_SaveFileName.setText(LoginController1.StrategyLoadFile_Name);
					//@-加载策略文件数据
					ScreensFramework.Main_Config.Load_Config(LoginController1.StrategyLoadFile_Path);
					//@-加载策略数据
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
    	//@-新建策略文件
    	if(mode==1)
    	{
    		//@-显示加载策略数据-起始电压
    		Charging_VoltageStart.setText("50");
			//@-显示加载策略数据-终止电压
    		Charging_VoltageEnd.setText("80");
			//@-显示加载策略数据-终止电压延时
    		Charging_VoltageEnd.setText("10");
			//@-显示加载策略数据-步进电压
    		Charging_VoltageStep.setText("0.1");
    		
			//@-显示加载策略数据-区域电压1
    		Charging_VoltageArea1.setText("60");
			//@-显示加载策略数据-区域电压1-Low
    		Charging_VoltageArea1_Low.setText("0");
			//@-显示加载策略数据-区域电压1-High
    		Charging_VoltageArea1_High.setText("0");
			//@-显示加载策略数据-区域电压2
    		Charging_VoltageArea2.setText("70");
			//@-显示加载策略数据-区域电压2-Low
    		Charging_VoltageArea2_Low.setText("0");
			//@-显示加载策略数据-区域电压2-High
    		Charging_VoltageArea2_High.setText("0");
			//@-显示加载策略数据-区域电压3
    		Charging_VoltageArea3.setText("75");
			//@-显示加载策略数据-区域电压3-Low
    		Charging_VoltageArea3_Low.setText("0");
			//@-显示加载策略数据-区域电压3-High
    		Charging_VoltageArea3_High.setText("0");
			//@-显示加载策略数据-区域电压4
    		Charging_VoltageArea4.setText("80");
			//@-显示加载策略数据-区域电压4-Low
    		Charging_VoltageArea4_Low.setText("0");
			//@-显示加载策略数据-区域电压4-High
    		Charging_VoltageArea4_High.setText("0");
    		
			//@-显示加载策略数据-预充电流
    		Charging_Current1.setText("2");
			//@-显示加载策略数据-预充电流-Low
    		Charging_Current1_Low.setText("0");
			//@-显示加载策略数据-预充电流-High
    		Charging_Current1_High.setText("0");
			//@-显示加载策略数据-恒电流1
    		Charging_Current2.setText("16");
			//@-显示加载策略数据-恒电流1-Low
    		Charging_Current2_Low.setText("0");
			//@-显示加载策略数据-恒电流1-High
    		Charging_Current2_High.setText("0");
			//@-显示加载策略数据-恒电流2
    		Charging_Current3.setText("11");
			//@-显示加载策略数据-恒电流2-Low
    		Charging_Current3_Low.setText("0");
			//@-显示加载策略数据-恒电流2-High
    		Charging_Current3_High.setText("0");
			//@-显示加载策略数据-浮充电流
    		Charging_Current4.setText("2");
			//@-显示加载策略数据-浮充电流-Low
    		Charging_Current4_Low.setText("0");
			//@-显示加载策略数据-浮充电流-High
    		Charging_Current4_High.setText("0");
    		
			
			//@-加载策略数据-区域电压1选择标志
    		VoltageArea1_Sel_Flag=true;
			//@-加载策略数据-区域电压2选择标志
    		VoltageArea2_Sel_Flag=false;
			//@-加载策略数据-区域电压3选择标志
    		VoltageArea3_Sel_Flag=false;
			//@-加载策略数据-区域电压4选择标志
    		VoltageArea4_Sel_Flag=false;
			
			//@-策略阶段重载
    		Strategy_VoltageArea1.setSelected(VoltageArea1_Sel_Flag);
    		Strategy_VoltageArea2.setSelected(VoltageArea2_Sel_Flag);
    		Strategy_VoltageArea3.setSelected(VoltageArea3_Sel_Flag);
    		Strategy_VoltageArea4.setSelected(VoltageArea4_Sel_Flag);
    	}
    	//@-加载策略文件
    	else if(mode==2)
    	{
    		//@-显示加载策略数据-起始电压
    		Charging_VoltageStart.setText(""+ScreensFramework.Main_Config.Def_VoltageStart);
			//@-显示加载策略数据-终止电压
    		Charging_VoltageEnd.setText(""+ScreensFramework.Main_Config.Def_VoltageEnd);
			//@-显示加载策略数据-终止电压延时
    		Charging_VoltageEnd_Delay.setText(""+ScreensFramework.Main_Config.Def_VoltageEnd_Delay);
			//@-显示加载策略数据-步进电压
    		Charging_VoltageStep.setText(""+ScreensFramework.Main_Config.Def_VoltageStep);
    		
			//@-显示加载策略数据-区域电压1
    		Charging_VoltageArea1.setText(""+ScreensFramework.Main_Config.Def_VoltageArea1);
			//@-显示加载策略数据-区域电压1-Low
    		Charging_VoltageArea1_Low.setText(""+ScreensFramework.Main_Config.Def_VoltageArea1_Low);
			//@-显示加载策略数据-区域电压1-High
    		Charging_VoltageArea1_High.setText(""+ScreensFramework.Main_Config.Def_VoltageArea1_High);
			//@-显示加载策略数据-区域电压2
    		Charging_VoltageArea2.setText(""+ScreensFramework.Main_Config.Def_VoltageArea2);
			//@-显示加载策略数据-区域电压2-Low
    		Charging_VoltageArea2_Low.setText(""+ScreensFramework.Main_Config.Def_VoltageArea2_Low);
			//@-显示加载策略数据-区域电压2-High
    		Charging_VoltageArea2_High.setText(""+ScreensFramework.Main_Config.Def_VoltageArea2_High);
			//@-显示加载策略数据-区域电压3
    		Charging_VoltageArea3.setText(""+ScreensFramework.Main_Config.Def_VoltageArea3);
			//@-显示加载策略数据-区域电压3-Low
    		Charging_VoltageArea3_Low.setText(""+ScreensFramework.Main_Config.Def_VoltageArea3_Low);
			//@-显示加载策略数据-区域电压3-High
    		Charging_VoltageArea3_High.setText(""+ScreensFramework.Main_Config.Def_VoltageArea3_High);
			//@-显示加载策略数据-区域电压4
    		Charging_VoltageArea4.setText(""+ScreensFramework.Main_Config.Def_VoltageArea4);
			//@-显示加载策略数据-区域电压4-Low
    		Charging_VoltageArea4_Low.setText(""+ScreensFramework.Main_Config.Def_VoltageArea4_Low);
			//@-显示加载策略数据-区域电压4-High
    		Charging_VoltageArea4_High.setText(""+ScreensFramework.Main_Config.Def_VoltageArea4_High);
    		
			//@-显示加载策略数据-预充电流
    		Charging_Current1.setText(""+ScreensFramework.Main_Config.Def_Current1);
			//@-显示加载策略数据-预充电流-Low
    		Charging_Current1_Low.setText(""+ScreensFramework.Main_Config.Def_Current1_Low);
			//@-显示加载策略数据-预充电流-High
    		Charging_Current1_High.setText(""+ScreensFramework.Main_Config.Def_Current1_High);
			//@-显示加载策略数据-恒电流1
    		Charging_Current2.setText(""+ScreensFramework.Main_Config.Def_Current2);
			//@-显示加载策略数据-恒电流1-Low
    		Charging_Current2_Low.setText(""+ScreensFramework.Main_Config.Def_Current2_Low);
			//@-显示加载策略数据-恒电流1-High
    		Charging_Current2_High.setText(""+ScreensFramework.Main_Config.Def_Current2_High);
			//@-显示加载策略数据-恒电流2
    		Charging_Current3.setText(""+ScreensFramework.Main_Config.Def_Current3);
			//@-显示加载策略数据-恒电流2-Low
    		Charging_Current3_Low.setText(""+ScreensFramework.Main_Config.Def_Current3_Low);
			//@-显示加载策略数据-恒电流2-High
    		Charging_Current3_High.setText(""+ScreensFramework.Main_Config.Def_Current3_High);
			//@-显示加载策略数据-浮充电流
    		Charging_Current4.setText(""+ScreensFramework.Main_Config.Def_Current4);
			//@-显示加载策略数据-浮充电流-Low
    		Charging_Current4_Low.setText(""+ScreensFramework.Main_Config.Def_Current4_Low);
			//@-显示加载策略数据-浮充电流-High
    		Charging_Current4_High.setText(""+ScreensFramework.Main_Config.Def_Current4_High);
    		
    		
			//@-加载策略数据-Stage1选择标志
    		VoltageArea1_Sel_Flag=ScreensFramework.Main_Config.Def_VoltageArea1_Sel;
			//@-加载策略数据-Stage2选择标志
    		VoltageArea2_Sel_Flag=ScreensFramework.Main_Config.Def_VoltageArea2_Sel;
			//@-加载策略数据-Stage3选择标志
    		VoltageArea3_Sel_Flag=ScreensFramework.Main_Config.Def_VoltageArea3_Sel;
			//@-加载策略数据-Stage4选择标志
    		VoltageArea4_Sel_Flag=ScreensFramework.Main_Config.Def_VoltageArea4_Sel;
			
			//@-策略阶段重载
    		Strategy_VoltageArea1.setSelected(VoltageArea1_Sel_Flag);
    		Strategy_VoltageArea2.setSelected(VoltageArea2_Sel_Flag);
    		Strategy_VoltageArea3.setSelected(VoltageArea3_Sel_Flag);
    		Strategy_VoltageArea4.setSelected(VoltageArea4_Sel_Flag);
    		
    		//System.out.println("data:"+ScreensFramework.Main_Config.Def_VoltageEnd);
    		
    	}
    }
    
    /**阶段及范围效果显示
     * 
     */
	private void StageRange_Display_Animation(Rectangle Rect) 
	{
		//@-策略阶段效果显示Timeline
		Timeline Stage_Timeline;
		//@-策略效果执行对象
		Rectangle target;
		
		//@-策略对象
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
    
	
    /**阶段步进效果显示
     * 
     */
	private void StageStep_Display_Animation(Line line) 
	{
		//@-策略阶段步进效果显示Timeline
		Timeline Line_Timeline;
		//@-策略效果执行对象
		Line target;
		
		//@-策略对象
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
                if (Text.getText().length() > 5) {
                    String s = Text.getText().substring(0, 5);
                    Text.setText(s);
                }
            }
        });   	
    }

    /**按键监听器
     * 
     * @param event
     */
    @FXML
    public void Button_Pro(ActionEvent event)
    {
    	int check_result;

    	//@2-保存执行按钮
    	if((event.getSource()==Strategy_Save_Button))
    	{
    		//@-策略保存-策略文件名
    		Strategy_FileName = Strategy_SaveFileName.getText();
    		
    		//@-策略保存-策略准则-起始电压
    		Temp_VoltageStart =  Float.parseFloat(Charging_VoltageStart.getText());
    		//@-策略保存-策略准则-终止电压
    		Temp_VoltageEnd =  Float.parseFloat(Charging_VoltageEnd.getText());
    		//@-策略保存-策略准则-终止电压延时
    		Temp_VoltageEnd_Delay =  Float.parseFloat(Charging_VoltageEnd_Delay.getText());
    		//@-策略保存-策略准则-步进电压
    		Temp_VoltageStep =  Float.parseFloat(Charging_VoltageStep.getText());
    		
    		//@-策略保存-策略准则-区域电压1
    		Temp_VoltageArea1 =  Float.parseFloat(Charging_VoltageArea1.getText());
    		//@-策略保存-策略准则-区域电压1-Low
    		Temp_VoltageArea1_Low =  Float.parseFloat(Charging_VoltageArea1_Low.getText());
    		//@-策略保存-策略准则-区域电压1-High
    		Temp_VoltageArea1_High =  Float.parseFloat(Charging_VoltageArea1_High.getText());
    		//@-策略保存-策略准则-区域电压2
    		Temp_VoltageArea2 =  Float.parseFloat(Charging_VoltageArea2.getText());
    		//@-策略保存-策略准则-区域电压2-Low
    		Temp_VoltageArea2_Low =  Float.parseFloat(Charging_VoltageArea2_Low.getText());
    		//@-策略保存-策略准则-区域电压2-High
    		Temp_VoltageArea2_High =  Float.parseFloat(Charging_VoltageArea2_High.getText());
    		//@-策略保存-策略准则-区域电压3
    		Temp_VoltageArea3 =  Float.parseFloat(Charging_VoltageArea3.getText());
    		//@-策略保存-策略准则-区域电压3-Low
    		Temp_VoltageArea3_Low =  Float.parseFloat(Charging_VoltageArea3_Low.getText());
    		//@-策略保存-策略准则-区域电压3-High
    		Temp_VoltageArea3_High =  Float.parseFloat(Charging_VoltageArea3_High.getText());
    		//@-策略保存-策略准则-区域电压4
    		Temp_VoltageArea4 =  Float.parseFloat(Charging_VoltageArea4.getText());
    		//@-策略保存-策略准则-区域电压4-Low
    		Temp_VoltageArea4_Low =  Float.parseFloat(Charging_VoltageArea4_Low.getText());
    		//@-策略保存-策略准则-区域电压4-High
    		Temp_VoltageArea4_High =  Float.parseFloat(Charging_VoltageArea4_High.getText());
    		
    		//@-策略保存-策略准则-预充电流
    		Temp_Current1 =  Float.parseFloat(Charging_Current1.getText());
    		//@-策略保存-策略准则-预充电流-Low
    		Temp_Current1_Low =  Float.parseFloat(Charging_Current1_Low.getText());
    		//@-策略保存-策略准则-预充电流-High
    		Temp_Current1_High =  Float.parseFloat(Charging_Current1_High.getText());
    		//@-策略保存-策略准则-恒电流1
    		Temp_Current2 =  Float.parseFloat(Charging_Current2.getText());
    		//@-策略保存-策略准则-恒电流1-Low
    		Temp_Current2_Low =  Float.parseFloat(Charging_Current2_Low.getText());
    		//@-策略保存-策略准则-恒电流1-High
    		Temp_Current2_High =  Float.parseFloat(Charging_Current2_High.getText());
    		//@-策略保存-策略准则-恒电流2
    		Temp_Current3 =  Float.parseFloat(Charging_Current3.getText());
    		//@-策略保存-策略准则-恒电流2-Low
    		Temp_Current3_Low =  Float.parseFloat(Charging_Current3_Low.getText());
    		//@-策略保存-策略准则-恒电流2-High
    		Temp_Current3_High =  Float.parseFloat(Charging_Current3_High.getText());
    		//@-策略保存-策略准则-浮充电流
    		Temp_Current4 =  Float.parseFloat(Charging_Current4.getText());
    		//@-策略保存-策略准则-浮充电流-Low
    		Temp_Current4_Low =  Float.parseFloat(Charging_Current4_Low.getText());
    		//@-策略保存-策略准则-浮充电流-High
    		Temp_Current4_High =  Float.parseFloat(Charging_Current4_High.getText());
    		
    		//@-策略保存-策略准则-区域电压1选择标志
    		VoltageArea1_Sel_Flag = Strategy_VoltageArea1.isSelected();
    		//@-策略保存-策略准则-区域电压2选择标志
    		VoltageArea2_Sel_Flag = Strategy_VoltageArea2.isSelected();
    		//@-策略保存-策略准则-区域电压3选择标志
    		VoltageArea3_Sel_Flag = Strategy_VoltageArea3.isSelected();
    		//@-策略保存-策略准则-区域电压4选择标志
    		VoltageArea4_Sel_Flag = Strategy_VoltageArea4.isSelected();
    		
    		//@-检测数据是否符合策略逻辑
    		check_result=Check_Data();
    		
    		if(check_result==0)
    		{
	    		//@-新建策略文件
	    		if(LoginController1.StrategyLoadFile_Mode==1)
	    		{
		    		int SaveResult = ScreensFramework.Main_Config.CreateSave_Config(2,"./"+Strategy_FileName,false);
		    		if(SaveResult==2)
		    		{
	    				//@-策略数据重载
	    				ScreensFramework.Main_Config.Load_Config("./"+Strategy_FileName);
		    					    		
			    		//@-策略配置成功标志置位
			    		StrategySet_Flag=true;
			    		
			    		ScreensFramework.PageChange.set("main");
		    		}
		    		else if(SaveResult==1)
		    		{
		    			ScreensFramework.Show_Noti("Warning", "策略文件:"+Strategy_FileName+"已存在!");
		    		}
		    		else if(SaveResult==3)
		    		{
		    			ScreensFramework.Show_Noti("Error", "策略文件名:"+Strategy_FileName+"出错!");
		    		}
	    		}
	    		//@-加载策略文件
	    		else if(LoginController1.StrategyLoadFile_Mode==2)
	    		{
	    			
	    			int SaveResult = ScreensFramework.Main_Config.CreateSave_Config(2,LoginController1.StrategyLoadFile_Path,true);
		    		
	    			if(SaveResult!=3)
	    			{
	    				//@-策略数据重载
	    				ScreensFramework.Main_Config.Load_Config(LoginController1.StrategyLoadFile_Path);
	    						    		
			    		//@-策略配置成功标志置位
			    		StrategySet_Flag=true;
			    		
			    		ScreensFramework.PageChange.set("main");	
	    			}
	    			else if(SaveResult==3)
	    			{
	    				ScreensFramework.Show_Noti("Error", "策略文件:"+Strategy_FileName+"保存出错!");
	    			}
	    		}
    		}
    	}
    	
    	//@2-取消按钮
    	else if((event.getSource()==Strategy_Cancel_Button))
    	{    		
    		//@-策略配置成功标志复位
    		StrategySet_Flag=false;
    		
    		ScreensFramework.PageChange.set("main");
    	}
    	
    }
    
    /*输入数据检测
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
    	    	
    	
    	//@-区域电压1选定
    	if(VoltageArea1_Sel_Flag==true)
    	{
	    	//@-判断区域电压1是否满足要求
	    	area_start = Temp_VoltageArea1-Temp_VoltageArea1_Low;
	    	area_end =   Temp_VoltageArea1+Temp_VoltageArea1_High;
	    	area=area_end-area_start;
	    	sel_num=sel_num+1;
	    	if(new Float(area-Temp_VoltageStep).compareTo(new Float(0.1))>0)
	    	{
		    	if(new Float(area_start).compareTo(new Float(area_end))<0)
		    	{
		    		//@-区域电压1起始值大于充电电压起始值
		    		if(new Float(area_start).compareTo(new Float(Temp_VoltageStart))>=0)
		    		{
		    			ok_num = (byte)(ok_num|0x01);
		    		}
		    		else
		    		{
		    			error_num=1;
		    			ScreensFramework.Show_Noti("Warning", "区域电压1小于电压起始值!");
		        		return (error_num);
		    		}
		    		
		    		//@-区域电压1终止值小于充电电压起始值
		    		if(new Float(area_end).compareTo(new Float(Temp_VoltageEnd))<=0)
		    		{
		    			ok_num = (byte)(ok_num|0x02);
		    		}
		    		else
		    		{
		    			error_num=2;
		    			ScreensFramework.Show_Noti("Warning", "区域电压1大于电压终止值!");
		        		return (error_num);
		    		}
		    	}
	    	}
	    	else
	    	{
	    		error_num=3;
    			ScreensFramework.Show_Noti("Warning", "区域电压1检测范围需大于步进值0.1V!");
        		return (error_num);
	    	}
    	}
    	
    	//@-区域电压2选定
    	if(VoltageArea2_Sel_Flag==true)
    	{
	    	//@-判断区域电压2是否满足要求
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
		    		
		    		//@-区域电压2起始值大于前一区域电压终止值
		    		if(new Float(area_start).compareTo(new Float(area_temp))>=0)
		    		{
		    			ok_num = (byte)(ok_num|0x04);
		    		}
		    		else
		    		{
		    			error_num=4;
		    			ScreensFramework.Show_Noti("Warning", "区域电压2小于前一区域电压终止值!");
		        		return (error_num);
		    		}
		    		
		    		//@-区域电压2终止值小于充电电压起始值
		    		if(new Float(area_end).compareTo(new Float(Temp_VoltageEnd))<=0)
		    		{
		    			ok_num = (byte)(ok_num|0x08);
		    		}
		    		else
		    		{
		    			error_num=5;
		    			ScreensFramework.Show_Noti("Warning", "区域电压2大于电压终止值!");
		        		return (error_num);
		    		}
		    	}
	    	}
	    	else
	    	{
	    		error_num=6;
    			ScreensFramework.Show_Noti("Warning", "区域电压2检测范围需大于步进值0.1V!");
        		return (error_num);
	    	}
    	}
    	
    	//@-区域电压3选定
    	if(VoltageArea3_Sel_Flag==true)
    	{
	    	//@-判断区域电压3是否满足要求
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
		    		
		    		//@-区域电压3起始值大于前一区域电压终止值
		    		if(new Float(area_start).compareTo(new Float(area_temp))>=0)
		    		{
		    			ok_num = (byte)(ok_num|0x10);
		    		}
		    		else
		    		{
		    			error_num=7;
		    			ScreensFramework.Show_Noti("Warning", "区域电压3小于前一区域电压终止值!");
		        		return (error_num);
		    		}
		    		
		    		//@-区域电压3终止值小于充电电压起始值
		    		if(new Float(area_end).compareTo(new Float(Temp_VoltageEnd))<=0)
		    		{
		    			ok_num = (byte)(ok_num|0x20);
		    		}
		    		else
		    		{
		    			error_num=8;
		    			ScreensFramework.Show_Noti("Warning", "区域电压3大于电压终止值!");
		        		return (error_num);
		    		}
		    	}
	    	}
	    	else
	    	{
	    		error_num=9;
    			ScreensFramework.Show_Noti("Warning", "区域电压3检测范围需大于步进值0.1V!");
        		return (error_num);
	    	}
    	}
    	
    	//@-区域电压4选定
    	if(VoltageArea4_Sel_Flag==true)
    	{
	    	//@-判断区域电压4是否满足要求
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
	
		    		//@-区域电压3起始值大于前一区域电压终止值
		    		if(new Float(area_start).compareTo(new Float(area_temp))>=0)
		    		{
		    			ok_num = (byte)(ok_num|0x40);
		    		}
		    		else
		    		{
		    			error_num=10;
		    			ScreensFramework.Show_Noti("Warning", "区域电压4小于前一区域电压终止值!");
		        		return (error_num);
		    		}
		    		
		    		//@-区域电压4终止值小于充电电压起始值
		    		if(new Float(area_end).compareTo(new Float(Temp_VoltageEnd))<=0)
		    		{
		    			ok_num = (byte)(ok_num|0x80);
		    		}
		    		else
		    		{
		    			error_num=11;
		    			ScreensFramework.Show_Noti("Warning", "区域电压4大于电压终止值!");
		        		return (error_num);
		    		}
		    	}
	    	}
	    	else
	    	{
	    		error_num=12;
    			ScreensFramework.Show_Noti("Warning", "区域电压4检测范围需大于步进值0.1V!");
        		return (error_num);
	    	}
    	}
    	
    	if(sel_num==0)
    	{
    		error_num=13;
    		ScreensFramework.Show_Noti("Warning", "没有区域电压被选中!");
    	}
    	
    	return (error_num);
    }
    
	@Override
	public void setScreenParent(ScreensController screenPage) {
		// TODO Auto-generated method stub
		myController = screenPage;
	}
    
}
