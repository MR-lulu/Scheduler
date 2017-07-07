package controller;


import java.util.LinkedList;

import algorithm.FactoryMPSA;
import algorithm.FactoryPSA;
import javafx.fxml.FXML;
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
	
	@FXML private void initialize() {
		setUpTableView();
		cpuColumn.setCellValueFactory(cellData->cellData.getValue().cpuidProperty().asObject());
		cpuColumn.setCellFactory(new Cell());
		priorityColumn.setCellValueFactory(cellData->cellData.getValue().priorityProperty().asObject());
		priorityColumn.setCellFactory(new Cell());
		scheduler = new FactoryMPSA().cteate();
	}

	@Override
	protected void initList(LinkedList<PCB> linkedList) {
		for(PCB pcb:linkedList){
			resultData.add(new ResultModel(pcb,pcb.getPid(), pcb.getState(), pcb.getPriority(), 
					0, pcb.getArriveTime(),pcb.getNeedTime(), 0, 0, 0,0));
		}
	}
	
}
