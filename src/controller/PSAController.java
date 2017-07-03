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
 * @Description: 控制PSA算法页面的操作
 * @author Mr_blue
 * @date 2017年6月28日
 */
public class PSAController extends Controller{
	//private PSA psa;
	@FXML private TableColumn<ResultModel, String> statusColumn;
	@FXML private TableColumn<ResultModel, Integer> priorityColumn;
	@FXML 
	private void initialize() {
		setUpTableView();
		statusColumn.setCellValueFactory(cellData->new SimpleObjectProperty<>(agrs[cellData.getValue().getStatus()]));
		statusColumn.setCellFactory(new CellString());
		priorityColumn.setCellValueFactory(cellData->cellData.getValue().priorityProperty().asObject());
		priorityColumn.setCellFactory(new Cell());
		scheduler = new FactoryPSA().cteate();
		//psa = (PSA) scheduler;
	}
	//重载开始调度按钮的操作方法
	@Override
	public void beginPress() {
		if (f) {
			tableView.setItems(resultData);
			num.setText(String.valueOf(resultData.size()));
			f = false;
			new Thread(()->{
				scheduler.dynamicRun(resultData);
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
class CellString implements Callback<TableColumn<ResultModel,String>, TableCell<ResultModel,String>>{
	@Override
	public TableCell<ResultModel, String> call(TableColumn<ResultModel, String> param) {
		// TODO Auto-generated method stub
		return new TableCell<ResultModel, String>(){
			@Override
			protected void updateItem(String item, boolean empty) {
				// TODO Auto-generated method stub
				super.updateItem(item, empty);
				if (empty) {
					setText(null);
					setGraphic(null);
				} else {
					//setTextAlignment(TextAlignment.RIGHT);
					if (item.equals("等待")) {
						this.getTableRow().setStyle("-fx-background-color: red");
					} else if (item.equals("完成")) {
						this.getTableRow().setStyle("-fx-background-color: CHARTREUSE");
					} else if (item.equals("运行")) {
						this.getTableRow().setStyle("-fx-background-color: DODGERBLUE");
						//this.getTableRow().setTextFill(Color.BLUE);
					} else if (item.equals("就绪")) {
						this.getTableRow().setStyle("-fx-background-color: DARKORANGE");
						//this.getTableRow().setTextFill(Color.DARKGOLDENROD);
						
					}
					setText(String.valueOf(item));
					setAlignment(Pos.CENTER);
				}
			}

		};
	}
}