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
	//���ؿ�ʼ���Ȱ�ť�Ĳ�������
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
			SimpleErrorAlert alert = new SimpleErrorAlert("����", "δ��ʼ��", "������ɳ�ʼ��");
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
					if (item.equals("�ȴ�")) {
						this.getTableRow().setStyle("-fx-background-color: red");
					} else if (item.equals("���")) {
						this.getTableRow().setStyle("-fx-background-color: CHARTREUSE");
					} else if (item.equals("����")) {
						this.getTableRow().setStyle("-fx-background-color: DODGERBLUE");
						//this.getTableRow().setTextFill(Color.BLUE);
					} else if (item.equals("����")) {
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