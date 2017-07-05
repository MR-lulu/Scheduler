package controller;



import java.util.LinkedList;

import algorithm.Factory;
import algorithm.FactorySRTF;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import misc.SimpleErrorAlert;
import model.PCB;
import model.ResultModel;

/**
 * 
 * ClassName: SJFController 
 * @Description: ����SJF�㷨ҳ��Ĳ���
 * @author Mr_blue
 * @date 2017��6��28��
 */
public class SJFController extends Controller {
	@FXML private TableColumn<ResultModel, Float> needColumn;
	@FXML 
	private void initialize() {
		setUpTableView();
		needColumn.setCellValueFactory(cellData->cellData.getValue().needTimeProperty().asObject());
		needColumn.setCellFactory(new CellFloat());
		Factory factory = new FactorySRTF();
		scheduler = factory.cteate();
	}
}
