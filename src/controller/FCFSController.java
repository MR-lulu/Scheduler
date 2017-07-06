package controller;

import java.util.LinkedList;

import algorithm.FactoryFCFS;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import model.PCB;
import model.ResultModel;

/**
 * ClassName: FCFSController 
 * @Description: TODO
 * @author Mr_blue
 * @date 2017Äê7ÔÂ6ÈÕ
 */
public class FCFSController extends Controller{
	@FXML private TableColumn<ResultModel, Float> needColumn;
	@FXML private TableColumn<ResultModel, String> status1Column;
	
	@FXML private void initialize() {
		setUpTableView();
		needColumn.setCellValueFactory(cellData->cellData.getValue().needTimeProperty().asObject());
		needColumn.setCellFactory(new CellFloat());
		status1Column.setCellValueFactory(value->value.getValue().status1Property());
		status1Column.setCellFactory(new CellString());
		scheduler = new FactoryFCFS().cteate();
	}
	@Override
	protected void initList(LinkedList<PCB> linkedList) {
		for(PCB pcb:linkedList){
			resultData.add(new ResultModel(pcb,pcb.getPid(), pcb.getState(), pcb.getPriority(), 
					0, pcb.getArriveTime(),pcb.getNeedTime(), 0, 0, 0,0));
		}
	}
}
