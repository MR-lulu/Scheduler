package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * 
 * ClassName: RRController 
 * @Description: ����RR�㷨ҳ��Ĳ���
 * @author Mr_blue
 * @date 2017��6��28��
 */
public class RRController extends Controller{
	@FXML private Button endButton;
	
	@FXML private void initialize(){
		setUpTableView();
		//�����Ԫ��
		
	}
	@FXML public void endPress(){
		int index = tableView.getSelectionModel().getSelectedIndex();
		if(index != -1){
			resultData.remove(index);
		}
	}
}