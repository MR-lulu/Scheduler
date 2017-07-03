package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * 
 * ClassName: RRController 
 * @Description: 控制RR算法页面的操作
 * @author Mr_blue
 * @date 2017年6月28日
 */
public class RRController extends Controller{
	@FXML private Button endButton;
	
	@FXML private void initialize(){
		setUpTableView();
		//填充表格元素
		
	}
	@FXML public void endPress(){
		int index = tableView.getSelectionModel().getSelectedIndex();
		if(index != -1){
			resultData.remove(index);
		}
	}
}