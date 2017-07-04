package controller;

import java.util.LinkedList;

import algorithm.FactoryPSA;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import misc.SimpleErrorAlert;
import model.PCB;
import model.ResultModel;
/**
 * 
 * ClassName: PSAController 
 * @Description: ����PSA�㷨ҳ��Ĳ���
 * @author Mr_blue
 * @date 2017��6��28��
 */
public class PSAController extends Controller{
	@FXML private TableColumn<ResultModel, Integer> priorityColumn;
	@FXML 
	private void initialize() {
		setUpTableView();
		
		priorityColumn.setCellValueFactory(cellData->cellData.getValue().priorityProperty().asObject());
		priorityColumn.setCellFactory(new Cell());
		scheduler = new FactoryPSA().cteate();
	}
}
