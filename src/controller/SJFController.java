package controller;



import java.util.LinkedList;

import algorithm.Factory;
import algorithm.FactorySRTF;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import misc.SimpleErrorAlert;
import model.PCB;
import model.ResultModel;

/**
 * 
 * ClassName: SJFController 
 * @Description: 控制SJF算法页面的操作
 * @author Mr_blue
 * @date 2017年6月28日
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
	@Override
	public void beginPress() {
		if (f) {
			tableView.setItems(resultData);
			num.setText(String.valueOf(resultData.size()));
			f = false;
			new Thread(()->{
				scheduler.dynamicRun(resultData);
				for(ResultModel model:resultData){
					System.out.println(model.getStatus());
				}
			}).start();
		} else {
			SimpleErrorAlert alert = new SimpleErrorAlert("错误", "未初始化", "请先完成初始化");
			alert.show();
		}
	}
	@Override
	protected void initList(LinkedList<PCB> linkedList) {
		for(PCB pcb:linkedList){
			scheduler.addProcess(pcb);
			resultData.add(new ResultModel(pcb,pcb.getPid(), pcb.getState(), pcb.getPriority(), 
					0, pcb.getArriveTime(),pcb.getNeedTime(), 0, 0, 0,0));
		}
	}
}
