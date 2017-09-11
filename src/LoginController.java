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


import java.awt.Color;
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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

/**
 * Login Controller.
 */
public class LoginController implements Initializable, ControlledScreen {

	@FXML
	private ImageView BMS_Logo;
	
	
	
	//@1-传递主应用程序接口
	private ScreensController myController;
    //@25-记录指令数同步
	public static SimpleStringProperty RecCountProperty_Main = new SimpleStringProperty();
	
    //@34-change监听器
	private ChangeListener changelisten1;
	private Timeline timeline1;
	
    private Image BMS_Pic;
    

    
	/**登录界面初始化
	 *
	 */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	
    	BMS_Pic= new Image(LoginController.class.getResourceAsStream("BMS.png"));
    	BMS_Logo.setImage(BMS_Pic);
    	
    	//@6-记录指令数同步
    	RecCountProperty_Main.addListener(changelisten1=new ChangeListener(){
			@Override
			public void changed(ObservableValue arg0, Object oldval, Object newval) {
				// TODO Auto-generated method stub
				
				Thread t1 = new Thread(new Runnable() {
					@Override
					public void run() {
						
							Platform.runLater(new Runnable() {
								@Override
								public void run() {
									BackPIC_Animation();
								}
							});					
					}
				});
				t1.setName("Login_Show");
				t1.setDaemon(true);
				t1.start();
			}
    	});
    }
    
    
    /**
     * 
     */
	public void BackPIC_Animation() 
	{

    	timeline1 = new Timeline();
		timeline1.setCycleCount(1);
		timeline1.setAutoReverse(false);
		
		timeline1.getKeyFrames().addAll(
				new KeyFrame(Duration.ZERO,
						new KeyValue(BMS_Logo.opacityProperty(), 0)						
				),
				
				new KeyFrame(new Duration(2000),
						new KeyValue(BMS_Logo.opacityProperty(), 1, Interpolator.EASE_BOTH)
				)
		);
		
		timeline1.setOnFinished(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				//@启动主界面
				AODI_DisplayTimer.lock_start_main=true;
			}
	    });

        timeline1.play();
	}
    
    
	@Override
	public void setScreenParent(ScreensController screenPage) {
		// TODO Auto-generated method stub
		myController = screenPage;
	}
    
}
