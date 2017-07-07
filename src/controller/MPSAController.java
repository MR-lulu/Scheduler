package controller;


import java.util.LinkedList;

import algorithm.FactoryMPSA;
import algorithm.FactoryPSA;
import algorithm.MPSA;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import model.PCB;
import model.ResultModel;
/**
 * ClassName: MPSAController 
 * @Description: TODO
 * @author Mr_blue
 * @date 2017Äê7ÔÂ5ÈÕ
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
	
}
