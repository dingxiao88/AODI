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

	//@1-主程序stage
    public static Stage Rec_Stage;

    public static Scene scene;

    private Rectangle2D bounds;

	//@2-主程序根root
    public static Group root = new Group();
	//@3-页面更改切换login->Main
	public static SimpleStringProperty PageChange = new SimpleStringProperty();

	//@6-主页面运行标志
	public static boolean Main_Falg=true;

	//@7-页面位置指示
	public static int  App_Page=1;    //0:login  1:main  2:node_view  3:data check  4:set  5:help

    public static int  Size_Swich=0;

	//@9-界面主调度器
    private ScreensController mainContainer;

    //@10-界面常量
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

    //@11-软件配置
    public static Config AODI_Config;

    //@11-产品型号
    public static PudModel AODI_PubModel;

    //@12-数据存储路径常量
	public static String MAIN_SaveData_Path = new String("/AODI_Save");
    //@13-今天数据存储路径
	public static String MAIN_SaveData_TodayPath;       //今天数据存储路径
    //@13-数据文件存储路径
	public static File MAIN_FileSave_Path;            //数据存储路径
    //@13-数据文件存储路径
	public static File MAIN_FileSave_TodayPath;       //今天数据存储路径
	//@14-存储文件文件对象列表
	public static File[] MAIN_SaveFileList;
	//@15-记录文件存储数量
	public static int MAIN_SaveFile_Num;


	//@16-提示窗
    private static Notification.Notifier Main_Noti;
    private static Notification Noti_Targe;

	//@4-主程序显示定时器
	public static QC_DisplayTimer QC_Displsy;

	//@6-网络连接对象
	public static Net_Main Net_Main_Connnect;

	//@-电子负载10个组
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

        //@-加载软件配置
        AODI_Config = new Config();

        //@-加载产品型号
        AODI_PubModel = new PudModel();

//    	System.out.println(""+ClassLoader.getSystemResourceAsStream(screen1ID));

    	Rec_Stage = new Stage(StageStyle.UNDECORATED);
    	Rec_Stage.setTitle("奥蒂电控老化测试V1.0");
    	//Rec_Stage.getIcons().add(new Image("/auros_icon_clipped_rev_1.png"));

        mainContainer = new ScreensController();

        mainContainer.loadScreen(screen1ID, screen1File);
        mainContainer.loadScreen(screen2ID, screen2File);
        //mainContainer.loadScreen(screen3ID, screen3File);
//        mainContainer.loadScreen(screen4ID, screen4File);
//        mainContainer.loadScreen(screen5ID, screen5File);
//        mainContainer.loadScreen(screen6ID, screen6File);


        //@1-页面更改挂载更改监听器
    	PageChange.addListener(new ChangeListener<Object>(){
			@Override
			public void changed(ObservableValue<?> arg0, Object arg1, Object newval) {
				// TODO Auto-generated method stub

				//切换到主界面
				if(newval.toString().equals(new String("main")))
				{
					Platform.runLater(new Runnable() {
						@Override
						public void run() {

							if(Main_Falg==false)
							Main_Falg=true;

							//切换到主界面
							mainContainer.setScreen(screen1ID);
							App_Page=1;

//					        Rec_Stage.setX(bounds.getMinX() + bounds.getWidth() / 2 - 1024 / 2);
//					        Rec_Stage.setY(bounds.getMinY() + bounds.getHeight() / 2 - 768 / 2);
//							Rec_Stage.setWidth(1024);
//							Rec_Stage.setHeight(800);

						}
					});
				}
				//切换到曲线
				else if(newval.toString().equals(new String("node_view")))
				{
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							//切换到记录文件查看页面
							mainContainer.setScreen(screen2ID);
							App_Page=2;

//							Rec_Stage.setWidth(1280);
//							Rec_Stage.setHeight(720);
//					        Rec_Stage.setX(bounds.getMinX() + bounds.getWidth() / 2 - 1280 / 2);
//					        Rec_Stage.setY(bounds.getMinY() + bounds.getHeight() / 2 - 720 / 2);
						}
					});
				}
				//切换到记录数据查看页面
				else if(newval.toString().equals(new String("checkdata")))
				{
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							//切换到记录数据查看页面
							mainContainer.setScreen(screen4ID);
							App_Page=3;
							//CheckDataController.ReadFileProperty.set("read");
						}
					});
				}
			}});

        mainContainer.setScreen(screen1ID);
        App_Page=1;

        //@-加载电子负载初始数据
        AODI_DZFZ_Init();

        //@-开机检测记录文件文件目录
        Check_File_Dir();

        root = new Group();
        root.getChildren().addAll(mainContainer);

		//@9-增加背景移动监听器
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

        //@7-挂载键盘监听
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

		//@8-启动定时器-20ms
         QC_Displsy = new QC_DisplayTimer(20);
    }


    /**键盘处理
     *
     * @param keyEvent
     */
    private static void KeyBoard_Pro(KeyEvent keyEvent)
    {
    	Key_Code = keyEvent.getCode();

    	Key_Count = Key_Count+1;

   	    //System.out.println("Key:"+Key_Code +  "mk:"+KeyBoard.My_KeyBoard_Num1);

   		//@-主界面键盘相应map
   		if(App_Page==1)
   		{
			//Tab1-"节点编号录入"按钮功能为已激活10个节点编号输入框-焦点已切换至Map_Tab1_NodeName_TextField上-等待回车录入
			if(QC_Controller.Tab1_InputName_Button_Flag==1)
			{
				//@-回车键
				if(Key_Code ==  KeyCode.ENTER)
				{
					//System.out.println("key");
					//@-输入老化产品编号切换焦点
					QC_Controller.DataProperty_Main.setValue("NodeName_Focus");
				}
			}
   		}

   		//@-节点详情
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
    		DZFZ_Group[Group] = new AODI_DZFZ_Group("老化组"+(Group+1));
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

    	//@2-Noti标号
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

    	//@3-建立Noti
    	Noti_Targe =  new Notification(Noti_Title, Show_Info, Noti_Image);

    	//@4-显示Noti
		Main_Noti = Notification.Notifier.INSTANCE;
		Main_Noti.notify(Noti_Targe);

    }


    /**检测记录文件文件目录
     *
     */
    public static void Check_File_Dir()
    {
		//@-获取系统时间
    	Calendar local_time = Calendar.getInstance();

    	//@-记录数据目录
    	MAIN_FileSave_Path = new File(MAIN_SaveData_Path);

    	//@-目录是否存在
    	if(MAIN_FileSave_Path.isDirectory())
    	{
    		//@-是否存在今天的目录
    		MAIN_FileSave_TodayPath = new File(MAIN_SaveData_Path+"/"+local_time.get(Calendar.YEAR)+"-"+(local_time.get(Calendar.MONTH)+1)+"-"+local_time.get(Calendar.DATE)+"/");

    		if((MAIN_FileSave_TodayPath.isDirectory())==false)
    		{
        		//@-创建目录
    			MAIN_FileSave_TodayPath.mkdirs();

    			//@-今天数据存储路径
    			MAIN_SaveData_TodayPath = new String(MAIN_SaveData_Path+"/"+local_time.get(Calendar.YEAR)+"-"+(local_time.get(Calendar.MONTH)+1)+"-"+local_time.get(Calendar.DATE)+"/");
    		}
    		else
    		{
    			//@-今天数据存储路径
    			MAIN_SaveData_TodayPath = new String(MAIN_SaveData_Path+"/"+local_time.get(Calendar.YEAR)+"-"+(local_time.get(Calendar.MONTH)+1)+"-"+local_time.get(Calendar.DATE)+"/");
    		}

    	}
    	else
    	{
    		//@-创建目录
    	    MAIN_FileSave_Path.mkdirs();
    	}
    }


    /**数据文件加载
     *
     */
    public static boolean File_Chooser()
    {
    	FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("杭州实业投资集团人员调动数据加载");
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


	/**程序退出
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
