package controller;


import java.util.ArrayList;
import java.util.LinkedList;

import algorithm.FactoryMPSA;
import algorithm.FactoryPSA;
import algorithm.MPSA;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import model.PCB;
import model.ResultModel;
/**
 * ClassName: MPSAController 
 * @Description: TODO
 * @author Mr_blue
 * @date 2017年7月5日
 */
public class MPSAController extends Controller{
	@FXML private TableColumn<ResultModel, Integer> cpuColumn;
	@FXML private TableColumn<ResultModel, Integer> priorityColumn;
	@FXML private ComboBox<Integer> choose;
	@FXML private Label cpunum;

	@FXML private void initialize() {
		setUpTableView();
		cpuColumn.setCellValueFactory(cellData->cellData.getValue().cpuidProperty().asObject());
		cpuColumn.setCellFactory(new Cell());
		priorityColumn.setCellValueFactory(cellData->cellData.getValue().priorityProperty().asObject());
		priorityColumn.setCellFactory(new Cell());
		scheduler = new FactoryMPSA().cteate();
		choose.setItems(FXCollections.observableArrayList(1,2,3,4,5,6,7,8));
		choose.getSelectionModel().selectedItemProperty().addListener((ov,oldV,newV)->{
			MPSA mpsa = (MPSA)scheduler;
			mpsa.setCpumun(newV.intValue());
			num.setText(String.valueOf(newV.intValue()));
		});
	}

	@Override
	protected void initList(LinkedList<PCB> linkedList) {
		for(PCB pcb:linkedList){
			resultData.add(new ResultModel(pcb,pcb.getPid(), pcb.getState(), pcb.getPriority(), 
					0, pcb.getArriveTime(),pcb.getNeedTime(), 0, 0, 0,0));
		}
	}

	@Override
	public void proPress() {
		//super.proPress();
		Dialog<Void> dialog = new Dialog<Void>();
		dialog.setTitle("调度过程");
		dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK,ButtonType.CLOSE);
		dialog.getDialogPane().setPrefSize(1000, 800);
		
		final NumberAxis xAxis = new NumberAxis();//X轴
		final CategoryAxis yAxis = new CategoryAxis();//Y轴
		final LineChart<Number,String> lineChart = 
				new LineChart<Number,String>(xAxis,yAxis);
		
		lineChart.setTitle("算法调度过程");
		lineChart.setLegendVisible(false);
		
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));
		
		ComboBox<Integer> comboBox = new ComboBox<Integer>();
		MPSA mpsa = (MPSA)scheduler;
		ObservableList<Integer> observableList = FXCollections.observableArrayList();
		ArrayList<ArrayList<String>> arrayList= mpsa.getArrayList();
		for (int i = 0; i < arrayList.size(); i++) {
			observableList.add(i);
		}
		comboBox.setItems(observableList);
		comboBox.getSelectionModel().selectedIndexProperty().addListener((ov,oldV,newV)->{
			ArrayList<String> list = arrayList.get(newV.intValue());
			XYChart.Series series[] = new XYChart.Series[list.size()];	
			for (int i = 0; i < list.size(); i++) {
				series[i] = new XYChart.Series();
				if (list.get(i).equals("-1")) {
					continue;
				}
				for (int j = 0; j < 2; j++) {
					series[i].getData().add(new XYChart.Data(i+j, "进程"+list.get(i)));
				}
				lineChart.getData().add(series[i]);
			}
		});
		grid.add(comboBox, 1, 0);
		grid.add(new Label("选择cpu"), 0,0);
		
		BorderPane borderPane = new BorderPane();
		borderPane.setTop(grid);
		borderPane.setCenter(lineChart);
		dialog.getDialogPane().setContent(borderPane);
		dialog.showAndWait();
	}

}
