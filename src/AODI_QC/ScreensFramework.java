package AODI_QC;
/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License"). You
 * may not use this file except in compliance with the License. You can
 * obtain a copy of the License at
 * https://glassfish.dev.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */





import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;

/**
 *
 * @author DX
 */
public class ScreensFramework extends Application {

	//@1-������stage
    public static Stage Rec_Stage;

    public static Scene scene;

    private Rectangle2D bounds;

	//@2-�������root
    public static Group root = new Group();
	//@3-ҳ������л�login->Main
	public static SimpleStringProperty PageChange = new SimpleStringProperty();

	//@6-��ҳ�����б�־
	public static boolean Main_Falg=true;

	//@7-ҳ��λ��ָʾ
	public static int  App_Page=1;    //0:login  1:main  2:node_view  3:data check  4:set  5:help

    public static int  Size_Swich=0;

	//@9-������������
    private ScreensController mainContainer;

    //@10-���泣��
    public static String screen1ID = "main";
    public static String screen1File = "QC_PI2.fxml";
    public static String screen2ID = "node_view";
    public static String screen2File = "QC_NodeView.fxml";
    public static String screen3ID = "curve";
    public static String screen3File = "Servo_Curve.fxml";
    public static String screen4ID = "checkdata";
    public static String screen4File = "CheckData.fxml";
    public static String screen5ID = "set";
    public static String screen5File = "Set.fxml";
    public static String screen6ID = "help";
    public static String screen6File = "Help.fxml";

    //@11-�������
    public static Config AODI_Config;

    //@11-��Ʒ�ͺ�
    public static PudModel AODI_PubModel;

    //@12-���ݴ洢·������
	public static String MAIN_SaveData_Path = new String("/AODI_Save");
    //@13-�������ݴ洢·��
	public static String MAIN_SaveData_TodayPath;       //�������ݴ洢·��
    //@13-�����ļ��洢·��
	public static File MAIN_FileSave_Path;            //���ݴ洢·��
    //@13-�����ļ��洢·��
	public static File MAIN_FileSave_TodayPath;       //�������ݴ洢·��
	//@14-�洢�ļ��ļ������б�
	public static File[] MAIN_SaveFileList;
	//@15-��¼�ļ��洢����
	public static int MAIN_SaveFile_Num;


	//@16-��ʾ��
    private static Notification.Notifier Main_Noti;
    private static Notification Noti_Targe;

	//@4-��������ʾ��ʱ��
	public static QC_DisplayTimer QC_Displsy;

	//@6-�������Ӷ���
	public static Net_Main Net_Main_Connnect;

	//@-���Ӹ���10����
	public static AODI_DZFZ_Group[] DZFZ_Group = new AODI_DZFZ_Group[10];


    public static KeyCode Key_Code;
    public static int Key_Count=0;

    private double initX;
    private double initY;


    public static String Debug_String;


    @Override
    public void start(Stage primaryStage) {

    	if(Size_Swich==0)
    	screen1File = new String("QC_PI2.fxml");
    	else if(Size_Swich==1)
    	screen1File = new String("QC_PI2.fxml");

        //@-�����������
        AODI_Config = new Config();

        //@-���ز�Ʒ�ͺ�
        AODI_PubModel = new PudModel();

//    	System.out.println(""+ClassLoader.getSystemResourceAsStream(screen1ID));

    	Rec_Stage = new Stage(StageStyle.UNDECORATED);
    	Rec_Stage.setTitle("�µٵ���ϻ�����V1.0");
    	//Rec_Stage.getIcons().add(new Image("/auros_icon_clipped_rev_1.png"));

        mainContainer = new ScreensController();

        mainContainer.loadScreen(screen1ID, screen1File);
        mainContainer.loadScreen(screen2ID, screen2File);
        //mainContainer.loadScreen(screen3ID, screen3File);
//        mainContainer.loadScreen(screen4ID, screen4File);
//        mainContainer.loadScreen(screen5ID, screen5File);
//        mainContainer.loadScreen(screen6ID, screen6File);


        //@1-ҳ����Ĺ��ظ��ļ�����
    	PageChange.addListener(new ChangeListener<Object>(){
			@Override
			public void changed(ObservableValue<?> arg0, Object arg1, Object newval) {
				// TODO Auto-generated method stub

				//�л���������
				if(newval.toString().equals(new String("main")))
				{
					Platform.runLater(new Runnable() {
						@Override
						public void run() {

							if(Main_Falg==false)
							Main_Falg=true;

							//�л���������
							mainContainer.setScreen(screen1ID);
							App_Page=1;

//					        Rec_Stage.setX(bounds.getMinX() + bounds.getWidth() / 2 - 1024 / 2);
//					        Rec_Stage.setY(bounds.getMinY() + bounds.getHeight() / 2 - 768 / 2);
//							Rec_Stage.setWidth(1024);
//							Rec_Stage.setHeight(800);

						}
					});
				}
				//�л�������
				else if(newval.toString().equals(new String("node_view")))
				{
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							//�л�����¼�ļ��鿴ҳ��
							mainContainer.setScreen(screen2ID);
							App_Page=2;

//							Rec_Stage.setWidth(1280);
//							Rec_Stage.setHeight(720);
//					        Rec_Stage.setX(bounds.getMinX() + bounds.getWidth() / 2 - 1280 / 2);
//					        Rec_Stage.setY(bounds.getMinY() + bounds.getHeight() / 2 - 720 / 2);
						}
					});
				}
				//�л�����¼���ݲ鿴ҳ��
				else if(newval.toString().equals(new String("checkdata")))
				{
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							//�л�����¼���ݲ鿴ҳ��
							mainContainer.setScreen(screen4ID);
							App_Page=3;
							//CheckDataController.ReadFileProperty.set("read");
						}
					});
				}
			}});

        mainContainer.setScreen(screen1ID);
        App_Page=1;

        //@-���ص��Ӹ��س�ʼ����
        AODI_DZFZ_Init();

        //@-��������¼�ļ��ļ�Ŀ¼
        Check_File_Dir();

        root = new Group();
        root.getChildren().addAll(mainContainer);

		//@9-���ӱ����ƶ�������
        root.setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
    			Stage primaryStage = Rec_Stage;
                initX = me.getScreenX() - primaryStage.getX();
                initY = me.getScreenY() - primaryStage.getY();
            }
        });
        //when screen is dragged, translate it accordingly
        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
            	Stage primaryStage = Rec_Stage;
            	primaryStage.setX(me.getScreenX() - initX);
            	primaryStage.setY(me.getScreenY() - initY);
            }
        });

        scene = new Scene(root);

        //@7-���ؼ��̼���
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
       	 public void handle(KeyEvent keyEvent)
       	 {
       		KeyBoard_Pro(keyEvent);
       	 }
       });


        Rec_Stage.setScene(scene);
        bounds = Screen.getPrimary().getBounds();

        if(Size_Swich==0)
        {
	        Rec_Stage.setX(bounds.getMinX() + bounds.getWidth() / 2 - 1024 / 2);
	        Rec_Stage.setY(bounds.getMinY() + bounds.getHeight() / 2 - 800 / 2);
        }
        else if(Size_Swich==1)
        {
	        Rec_Stage.setX(bounds.getMinX() + bounds.getWidth() / 2 - 1024 / 2);
	        Rec_Stage.setY(bounds.getMinY() + bounds.getHeight() / 2 - 768 / 2);
        }

        Rec_Stage.show();

		//@8-������ʱ��-20ms
         QC_Displsy = new QC_DisplayTimer(20);
    }


    /**���̴���
     *
     * @param keyEvent
     */
    private static void KeyBoard_Pro(KeyEvent keyEvent)
    {
    	Key_Code = keyEvent.getCode();

    	Key_Count = Key_Count+1;

   	    //System.out.println("Key:"+Key_Code +  "mk:"+KeyBoard.My_KeyBoard_Num1);

   		//@-�����������Ӧmap
   		if(App_Page==1)
   		{
			//Tab1-"�ڵ���¼��"��ť����Ϊ�Ѽ���10���ڵ��������-�������л���Map_Tab1_NodeName_TextField��-�ȴ��س�¼��
			if(QC_Controller.Tab1_InputName_Button_Flag==1)
			{
				//@-�س���
				if(Key_Code ==  KeyCode.ENTER)
				{
					//System.out.println("key");
					//@-�����ϻ���Ʒ����л�����
					QC_Controller.DataProperty_Main.setValue("NodeName_Focus");
				}
			}
   		}

   		//@-�ڵ�����
   		else if(App_Page==2)
   		{

   		}

    }


    /**
     *
     */
    private void AODI_DZFZ_Init()
    {
    	for(int Group=0;Group<10;Group++)
    	{
    		DZFZ_Group[Group] = new AODI_DZFZ_Group("�ϻ���"+(Group+1));
    	}
    }



    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }


    /**
     *
     * @param Show_Mode
     * @param Show_Info
     */
    public static void Show_Noti(String Show_Mode, String Show_Info)
    {
    	String Noti_Title=null;
    	Image  Noti_Image=null;

    	//@2-Noti���
    	switch(Show_Mode)
    	{
    		case "Info":
	        		Noti_Title = new String("Info");
	        		Noti_Image = new Image(ScreensFramework.class.getResourceAsStream("info.png"));
	        		break;
    		case "Error":
	        		Noti_Title = new String("Error");
	        		Noti_Image = new Image(ScreensFramework.class.getResourceAsStream("error.png"));
	        		break;
    		case "Warning":
	        		Noti_Title = new String("Warning");
	        		Noti_Image = new Image(ScreensFramework.class.getResourceAsStream("warning.png"));
	        		break;
    		case "Success":
	        		Noti_Title = new String("Success");
	        		Noti_Image = new Image(ScreensFramework.class.getResourceAsStream("success.png"));
	        		break;

	        default:break;

    	}

    	//@3-����Noti
    	Noti_Targe =  new Notification(Noti_Title, Show_Info, Noti_Image);

    	//@4-��ʾNoti
		Main_Noti = Notification.Notifier.INSTANCE;
		Main_Noti.notify(Noti_Targe);

    }


    /**����¼�ļ��ļ�Ŀ¼
     *
     */
    public static void Check_File_Dir()
    {
		//@-��ȡϵͳʱ��
    	Calendar local_time = Calendar.getInstance();

    	//@-��¼����Ŀ¼
    	MAIN_FileSave_Path = new File(MAIN_SaveData_Path);

    	//@-Ŀ¼�Ƿ����
    	if(MAIN_FileSave_Path.isDirectory())
    	{
    		//@-�Ƿ���ڽ����Ŀ¼
    		MAIN_FileSave_TodayPath = new File(MAIN_SaveData_Path+"/"+local_time.get(Calendar.YEAR)+"-"+(local_time.get(Calendar.MONTH)+1)+"-"+local_time.get(Calendar.DATE)+"/");

    		if((MAIN_FileSave_TodayPath.isDirectory())==false)
    		{
        		//@-����Ŀ¼
    			MAIN_FileSave_TodayPath.mkdirs();

    			//@-�������ݴ洢·��
    			MAIN_SaveData_TodayPath = new String(MAIN_SaveData_Path+"/"+local_time.get(Calendar.YEAR)+"-"+(local_time.get(Calendar.MONTH)+1)+"-"+local_time.get(Calendar.DATE)+"/");
    		}
    		else
    		{
    			//@-�������ݴ洢·��
    			MAIN_SaveData_TodayPath = new String(MAIN_SaveData_Path+"/"+local_time.get(Calendar.YEAR)+"-"+(local_time.get(Calendar.MONTH)+1)+"-"+local_time.get(Calendar.DATE)+"/");
    		}

    	}
    	else
    	{
    		//@-����Ŀ¼
    	    MAIN_FileSave_Path.mkdirs();
    	}
    }


    /**�����ļ�����
     *
     */
    public static boolean File_Chooser()
    {
    	FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("����ʵҵͶ�ʼ�����Ա�������ݼ���");
        fileChooser.setInitialDirectory(
            new File("./")
        );
        fileChooser.getExtensionFilters().addAll(
            //new FileChooser.ExtensionFilter("All Images", "*.*"),
            //new FileChooser.ExtensionFilter("JPG", "*.jpg"),
            new FileChooser.ExtensionFilter("syjt", "*.syjt")
        );

        File file = fileChooser.showOpenDialog(Rec_Stage);
        if (file != null)
        {
//        	ExcelController.DataLoadFile_Path=file.getAbsolutePath();
//        	ExcelController.DataLoadFile_Name=file.getName();
        	return true;
        }
        else
        {
        	return false;
        }
    }


	/**�����˳�
	 *
	 */
	public static void cleanAndQuit() {
		//Time_Displsy.task_dis.cancel();
//		if(Net_Main_Connnect.isOpen==true)
//		{
//			Net_Main_Connnect.close();
//		}
		Rec_Stage.close();
		Platform.exit();
		System.exit(0);
	}
}
