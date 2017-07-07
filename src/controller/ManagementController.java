package controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import view.ViewHelp;

/**
 * 
 * ClassName: ManagementController 
 * @Description: 主界面Controller类，负责页面跳转
 * @author Mr_blue
 * @date 2017年6月28日
 */
public class ManagementController {
	@FXML private AnchorPane detailPane;
	@FXML private Button rrButton;
	@FXML private Button sjfButton;
	@FXML private Button psaButton;
	@FXML private Button mpsaButton;
	@FXML private Button fcfsButton;
	
	@FXML private void initialize(){
		//跳转至时间轮转调度页面
		loadPane(ViewHelp.rrView);
	}
	@FXML public void rrPress(){
		//跳转至时间轮转调度页面
		loadPane(ViewHelp.rrView);
	}
	@FXML public void sjfPress(){
		//跳转至短作业调度页面
		loadPane(ViewHelp.sjfView);
	}
	@FXML public void psaPress(){
		//跳转至动态优先级调度页面
		loadPane(ViewHelp.psaView);
	}
	@FXML public void mpsaPress(){
		//跳转多核动态优先级调度页面
		loadPane(ViewHelp.mpsaView);
	}
	@FXML public void fcfsPress(){
		//跳转至先来先服务调度页面
		loadPane(ViewHelp.fcfsView);
	}
	
	private void loadPane(String name){
		//清除之前加载的页面
		detailPane.getChildren().clear(); 
		
		//加载新页面
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(ViewHelp.class.getResource(name));
		try {
			detailPane.getChildren().add(loader.load());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

