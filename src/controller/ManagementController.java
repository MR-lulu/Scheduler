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
 * @Description: ������Controller�࣬����ҳ����ת
 * @author Mr_blue
 * @date 2017��6��28��
 */
public class ManagementController {
	@FXML private AnchorPane detailPane;
	@FXML private Button rrButton;
	@FXML private Button sjfButton;
	@FXML private Button psaButton;
	
	@FXML private void initialize(){
		loadPane(ViewHelp.rrView);
	}
	@FXML public void rrPress(){
		loadPane(ViewHelp.rrView);
	}
	@FXML public void sjfPress(){
		loadPane(ViewHelp.sjfView);
	}
	@FXML public void psaPress(){
		loadPane(ViewHelp.psaView);
	}
	
	private void loadPane(String name){
		//���֮ǰ���ص�ҳ��
		detailPane.getChildren().clear(); 
		
		//������ҳ��
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(ViewHelp.class.getResource(name));
		try {
			detailPane.getChildren().add(loader.load());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

