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

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;

/**
 *
 * @author Jack Ding
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
	//@4-主程序显示定时器
	private static AODI_DisplayTimer Time_Displsy;
	//@6-主页面运行标志
	public static boolean Main_Falg=true;

	//@7-页面位置指示
	public static int  App_Page=0;    //0:login  1:main  2:Strategy  3:data check  4:set  5:help


	//@9-界面主调度器
    private ScreensController mainContainer;

    //@10-界面常量
    public static String screen1ID = "login";
    public static String screen1File = "AODI_Login.fxml";
    public static String screen2ID = "main";
    public static String screen2File = "AODI_BMS_Main.fxml";
    public static String screen3ID = "strategy";
    public static String screen3File = "AODI_StrategyNew.fxml";
    public static String screen4ID = "checkdata";
    public static String screen4File = "CheckData.fxml";
    public static String screen5ID = "set";
    public static String screen5File = "Set.fxml";
    public static String screen6ID = "help";
    public static String screen6File = "Help.fxml";

    //@11-Config配置器
    public static AODI_Config Main_Config;

    //@12-数据存储路径常量
	public static String MAIN_SaveData_Path = new String("/home/pi/Rec/Save");
    //@13-数据文件存储路径
	public static File MAIN_FileSave_Path;            //数据存储路径
	//@14-存储文件文件对象列表
	public static File[] MAIN_SaveFileList;
	//@15-记录文件存储数量
	public static int MAIN_SaveFile_Num;


	//@16-提示窗
    private static Notification.Notifier Main_Noti;
    private static Notification Noti_Targe;



    @Override
    public void start(Stage primaryStage) {

    	Rec_Stage = new Stage(StageStyle.UNDECORATED);
    	Rec_Stage.setTitle("AODI-电子负载控制系统");
    	Rec_Stage.getIcons().add(new Image("/BMS.png"));
    	Rec_Stage.setResizable(false);

        mainContainer = new ScreensController();

        mainContainer.loadScreen(screen1ID, screen1File);
        mainContainer.loadScreen(screen2ID, screen2File);
        mainContainer.loadScreen(screen3ID, screen3File);
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
							//切换到主界面
							mainContainer.setScreen(screen2ID);
							App_Page=1;

//							Rec_Stage.setWidth(1024);
//							Rec_Stage.setHeight(768);
					        Rec_Stage.setX(bounds.getMinX() + bounds.getWidth() / 2 - 1024 / 2);
					        Rec_Stage.setY(bounds.getMinY() + bounds.getHeight() / 2 - 768 / 2);

					        //@-策略配置成功标志
					        if(StrategyControllerNew.StrategySet_Flag==true)
					        {
					        	LoginController1.StrategyProperty_Main.setValue("Set");
					        }
					        else if(StrategyControllerNew.StrategySet_Flag==false)
					        {
					        	LoginController1.StrategyProperty_Main.setValue("Cancel");
					        }


						}
					});
				}
				//切换到策略配置页面
				else if(newval.toString().equals(new String("strategy")))
				{
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							//切换策略配置页面
							mainContainer.setScreen(screen3ID);
							App_Page=2;
							if(LoginController1.StrategyLoadFile_Mode==1)
							{
								StrategyControllerNew.StrategyFile.setValue("New");
							}
							else if(LoginController1.StrategyLoadFile_Mode==2)
							{
								StrategyControllerNew.StrategyFile.setValue("Load");
							}
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
				//切换到参数配置页面
				else if(newval.toString().equals(new String("set")))
				{
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							//切换到参数配置界面
							mainContainer.setScreen(screen5ID);
							App_Page=4;
						}
					});
				}
				//切换到帮助页面
				else if(newval.toString().equals(new String("help")))
				{
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							//切换到帮助界面
							mainContainer.setScreen(screen6ID);
							App_Page=5;
						}
					});
				}
			}});

        mainContainer.setScreen(screen1ID);
        App_Page=0;

        //@-加载存储配置项
        Main_Config = new AODI_Config();

        //@-开机检测记录文件数量
        //Check_File_Num();

        root = new Group();
        root.getChildren().addAll(mainContainer);
        scene = new Scene(root);
        Rec_Stage.setScene(scene);
        bounds = Screen.getPrimary().getBounds();
        Rec_Stage.setX(bounds.getMinX() + bounds.getWidth() / 2 - 1024 / 2);
        Rec_Stage.setY(bounds.getMinY() + bounds.getHeight() / 2 - 768 / 2);

        Rec_Stage.show();

		//@8-启动定时器-200ms
	    Time_Displsy = new AODI_DisplayTimer(50);
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


    /**策略文件加载
     *
     */
    public static boolean File_Chooser()
    {
    	FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("电子负载策略文件加载");
        fileChooser.setInitialDirectory(
            new File("./")
        );
        fileChooser.getExtensionFilters().addAll(
            //new FileChooser.ExtensionFilter("All Images", "*.*"),
            //new FileChooser.ExtensionFilter("JPG", "*.jpg"),
            new FileChooser.ExtensionFilter("aodi", "*.aodi")
        );

        File file = fileChooser.showOpenDialog(Rec_Stage);
        if (file != null)
        {
        	LoginController1.StrategyLoadFile_Path=file.getAbsolutePath();
        	LoginController1.StrategyLoadFile_Name=file.getName();
        	return true;
        }
        else
        {
        	return false;
        }
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

    	//@1-Noti标号
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
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				Main_Noti = Notification.Notifier.INSTANCE;
				Main_Noti.notify(Noti_Targe);
			}
		});
    }

    /**
     *
     */
    public static void Check_File_Num()
    {
    	MAIN_FileSave_Path = new File(MAIN_SaveData_Path);
    	if(MAIN_FileSave_Path.isDirectory())
    	{
	   	 //获取文件列表
    	 MAIN_SaveFileList = MAIN_FileSave_Path.listFiles();
	   	 //获取文件数量
    	 MAIN_SaveFile_Num=MAIN_SaveFileList.length;
    	}
    }


	/**程序退出
	 *
	 */
	public static void cleanAndQuit() {
		Time_Displsy.task_dis.cancel();
//		if(Net_Main_Connnect.isOpen==true)
//		{
//			Net_Main_Connnect.close();
//		}
		Rec_Stage.close();
		Platform.exit();
		System.exit(0);
	}
}
