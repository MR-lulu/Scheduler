package application;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.ViewHelp;

/**
 * 
 * ClassName: Main 
 * @Description: �������
 * @author Mr_blue
 * @date 2017��6��27��
 */
public class Main extends Application{
	private Stage primaryStage;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		initRootlayout();
		primaryStage.setHeight(450);
		primaryStage.setWidth(850);
		primaryStage.setTitle("����������㷨");
		primaryStage.show();
	}
	
	//��ʼ������ҳ��
	private void initRootlayout(){
		Parent root = null;
		FXMLLoader loader = new FXMLLoader();
		try {
			loader.setLocation(ViewHelp.class.getResource(ViewHelp.managementView));
			root = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
	}
	
	public static void main(String[] args) {
		Application.launch(args);
	}
}
