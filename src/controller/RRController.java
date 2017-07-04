package controller;

import com.sun.org.apache.bcel.internal.generic.NEW;

import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;

/**
 * 
 * ClassName: RRController 
 * @Description: 控制RR算法页面的操作
 * @author Mr_blue
 * @date 2017年6月28日
 */
public class RRController extends Controller{
	@FXML private Button processButton;
	
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