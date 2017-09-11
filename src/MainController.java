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
import java.util.ResourceBundle;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadowBuilder;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineBuilder;
import javafx.scene.transform.Rotate;

/**
 * Login Controller.
 */
public class MainController implements Initializable, ControlledScreen {

	@FXML
	private ImageView Meter_BackupImage;

	@FXML
	private Label Meter_DisplayUnit;

	@FXML
	private Label Meter_DisplayValue;


	@FXML
	private ChoiceBox Serise_Port;

	@FXML
	private ChoiceBox Serise_Bound;

	@FXML
	private Button Serise_Open;

	@FXML
	private Button Serise_Close;


	@FXML
	private TextField Serise_Receive_Bytes;

	@FXML
	private TextArea Serise_Receive_datas;

	@FXML
	private ImageView Switch1;

	@FXML
	private ImageView Switch2;


	private static Rotate PointRotation;
	public static DoubleProperty meter_value = new SimpleDoubleProperty(0.0);  //仪表值
	private static LineBuilder Pointer1_Build;
	public static Line Meter_Pointer;


	//串口
	public static Serise Serise_Main;    //串口对象

    //@25-记录指令数同步
	public static SimpleStringProperty RecCountProperty_Main = new SimpleStringProperty();
    //@34-change监听器
	private ChangeListener changelisten1;



	//@1-传递主应用程序接口
	private ScreensController myController;


	private int Recv_Count_C=0;
	private int Rec_Angel=0;


	private Image Switch1_on;
	private Image Switch1_off;
	private Image Switch2_on;
	private Image Switch2_off;

	private boolean Switch1_Flag=false;
	private boolean Switch2_Flag=false;



	/**登录界面初始化
	 *
	 */
    @SuppressWarnings("unchecked")
	@Override
    public void initialize(URL location, ResourceBundle resources) {



	    Meter_DisplayUnit.setText("A");
	    Meter_DisplayValue.setText("0.0");

	    Serise_Port.getItems().clear();
	    Serise_Port.getItems().addAll("COM1", "COM2", "COM3", "COM4", "COM5");
	    Serise_Port.getSelectionModel().selectFirst();

	    Serise_Bound.getItems().clear();
	    Serise_Bound.getItems().addAll("9600", "19200", "115200");
	    Serise_Bound.getSelectionModel().selectFirst();


    	//@6-记录指令数同步
    	RecCountProperty_Main.addListener(changelisten1=new ChangeListener(){
			@Override
			public void changed(ObservableValue arg0, Object oldval, Object newval) {
				// TODO Auto-generated method stub

				Serise_Receive_Bytes.setText(newval.toString());

				Recv_Count_C=Recv_Count_C+1;
				Rec_Angel=Rec_Angel+1;

				//@-接收数据框清空
				if(Recv_Count_C>600)
				{
					Recv_Count_C=0;
					Serise_Receive_datas.setText("");
				}
				//@-角度控制
				if(Rec_Angel>280)
				{
					Rec_Angel=0;
				}
				meter_value.setValue(Rec_Angel);
				Meter_DisplayValue.setText(""+Rec_Angel);
				//@-打印接受到的数据
//				String kk = new String(Serise.tempBuffer[0]+"-"+Serise.tempBuffer[1]+"-"+Serise.tempBuffer[2]+"-"+
//						               Serise.tempBuffer[3]+"-"+Serise.tempBuffer[4]+"-"+Serise.tempBuffer[5]+"-"+
//						               Serise.tempBuffer[6]+"-"+Serise.tempBuffer[7]+"-"+Serise.tempBuffer[8]+"-"+
//						               Serise.tempBuffer[9]+"-"+Serise.tempBuffer[10]+"-"+Serise.tempBuffer[11]+"-"+
//						               Serise.tempBuffer[12]+"-"+Serise.tempBuffer[13]+"-"+Serise.tempBuffer[14]+"-"+
//						               Serise.tempBuffer[15]+"-"+Serise.tempBuffer[16]+"-"+Serise.tempBuffer[17]+"-"+
//						               Serise.tempBuffer[18]+"-"+Serise.tempBuffer[19]+"-"+Serise.tempBuffer[20]+"-"+
//						               Serise.tempBuffer[21]+"-"+Serise.tempBuffer[22]+"-"+Serise.tempBuffer[23]+"-");
				String kk = new String(Serise.tempBuffer[0]+"-"+Serise.tempBuffer[1]+"-"+Serise.tempBuffer[2]+"-"+
			               Serise.tempBuffer[3]+"-"+Serise.tempBuffer[4]+"-"+Serise.tempBuffer[5]+"-"+
			               Serise.tempBuffer[6]+"-"+Serise.tempBuffer[7]+"-"+Serise.tempBuffer[8]+"-"+
			               Serise.tempBuffer[9]+"-"+Serise.tempBuffer[10]+"-"+Serise.tempBuffer[11]+"-"+
			               Serise.tempBuffer[12]+"-"+Serise.tempBuffer[13]+"-"+Serise.tempBuffer[14]+"-"+
			               Serise.tempBuffer[15]+"-"+Serise.tempBuffer[16]);

				Serise_Receive_datas.appendText(kk);
				Serise_Receive_datas.appendText("\n");

//            	if((AODI_DisplayTimer.Recv_HEX)<0)
//            	Serise_Receive_datas.appendText("0x"+Integer.toHexString((AODI_DisplayTimer.Recv_HEX)+256));
//            	else
//            	Serise_Receive_datas.appendText("0x"+Integer.toHexString(AODI_DisplayTimer.Recv_HEX));
//
//            	Serise_Receive_datas.appendText("-");
			}
    	});


    	//@7-图片资源初始化
    	Switch1_on = new Image(MainController.class.getResourceAsStream("switch1_on.png"));
    	Switch1_off = new Image(MainController.class.getResourceAsStream("switch1_off.png"));
    	Switch2_on = new Image(MainController.class.getResourceAsStream("switch2_on.png"));
    	Switch2_off = new Image(MainController.class.getResourceAsStream("switch2_off.png"));


    	Serise_Receive_datas.setStyle("-fx-background-color:rgb(141, 193, 0)");

    }

    /**
     *
     */
    public static void add_point()
    {
    	//@3-指针变化的动作
		PointRotation = new Rotate(0, 0, 0);
		PointRotation.angleProperty().bind(new DoubleBinding() {
			{
				bind(meter_value);
			}

			@Override
			protected double computeValue() {
    					double zeroOne = meter_value.get();
    					return -140+(zeroOne*1);
			}
		});

		//@4-指针
		Pointer1_Build = LineBuilder.create();
		Pointer1_Build.startX(0);
		Pointer1_Build.startY(15);
		Pointer1_Build.endX(0);
		Pointer1_Build.endY(-80);
		Pointer1_Build.stroke(
			new LinearGradient(0, 1, 0, 0, true,
			CycleMethod.NO_CYCLE,
			new Stop(0.33, Color.TRANSPARENT), new Stop(0.34,
					Color.web("#7e7e7e")), new Stop(1, Color.web("#c0171b")))
			);
		Pointer1_Build.effect(
			DropShadowBuilder.create().radius(5)
			.color(new Color(0, 0, 0, 0.3)).build()
			);
		Pointer1_Build.strokeWidth(3);
		Pointer1_Build.translateX(273);
		Pointer1_Build.translateY(203);
		Pointer1_Build.transforms(PointRotation);
	    Meter_Pointer = Pointer1_Build.build();

    }


    /**
     *
     * @param event
     */
    @FXML
    private void Button_Open_Handle(ActionEvent event)
    {
    	try {
			Serise_Main = new Serise("COM1", 9600, "None");
		} catch (NoSuchPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }


    /**
     *
     * @param event
     */
    @FXML
    private void Button_Close_Handle(ActionEvent event)
    {
    	Serise_Main.close();
    }


    /**
     *
     * @param event
     */
    @FXML
    private void Switch_Action(MouseEvent event)
    {
        if(event.getEventType()==MouseEvent.MOUSE_CLICKED)
        {
        	if(event.getSource()==Switch1)
        	{
        		if(Switch1_Flag==false)
        		Switch1_Flag=true;
        		else if(Switch1_Flag==true)
        		Switch1_Flag=false;

        		if(Switch1_Flag==true)
        		Switch1.setImage(Switch1_on);
        		else if(Switch1_Flag==false)
        		Switch1.setImage(Switch1_off);

        	}
        	else if(event.getSource()==Switch2)
        	{
        		if(Switch2_Flag==false)
        		Switch2_Flag=true;
        		else if(Switch2_Flag==true)
        		Switch2_Flag=false;

        		if(Switch2_Flag==true)
        		Switch2.setImage(Switch2_on);
        		else if(Switch2_Flag==false)
        		Switch2.setImage(Switch2_off);
        	}
        }
    }


	@Override
	public void setScreenParent(ScreensController screenPage) {
		// TODO Auto-generated method stub
		myController = screenPage;
	}

}
