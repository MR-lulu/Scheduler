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
	@FXML public void proPress(){
		Dialog<Void> dialog = new Dialog<Void>();
		dialog.setTitle("调度过程");
		dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK,ButtonType.CLOSE);
		dialog.getDialogPane().setPrefSize(800, 700);
		
		final NumberAxis xAxis = new NumberAxis();
        final CategoryAxis yAxis = new CategoryAxis();
        final LineChart<Number,String> lineChart = 
                new LineChart<Number,String>(xAxis,yAxis);
        
        lineChart.setTitle("算法调度过程");
        dialog.getDialogPane().setContent(lineChart);
        XYChart.Series series[] = new XYChart.Series[10];
        
        for (int i = 0; i < 10; i++) {
			series[i] = new XYChart.Series();
			 for (int j = 0; j < 2; j++) {
					series[i].getData().add(new XYChart.Data(i+j, "进程"+i));	
			}
			lineChart.getData().add(series[i]);
		}
		dialog.showAndWait();
	}
}