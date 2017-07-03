package controller;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Optional;

import algorithm.Scheduler;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import misc.InitializationFormFile;
import misc.InitializationInput;
import misc.InitializationRandom;
import misc.Regular_expression;
import misc.SimpleErrorAlert;
import misc.SimpleSuccessAlert;
import model.PCB;
import model.ResultModel;
/**
 * 
 * ClassName: Controller 
 * @Description: ���������࣬���ÿ�������ͬ��Ԫ��
 * @author Mr_blue
 * @date 2017��6��28��
 */
public class Controller {
	@FXML protected TableColumn<ResultModel, Integer> pidColumn;
	@FXML protected TableColumn<ResultModel, Float> startColumn;
	@FXML private TableColumn<ResultModel, String> statusColumn;
	@FXML protected TableColumn<ResultModel, Float> finishColumn;
	@FXML protected TableColumn<ResultModel, Float> turnColumn;
	@FXML protected TableColumn<ResultModel, Float> rturnColumn;
	@FXML protected TableColumn<ResultModel, Float> arriveColumn;
	@FXML protected Label num;

	@FXML protected TableView<ResultModel> tableView;
	protected ObservableList<ResultModel> resultData = FXCollections.observableArrayList();
	@FXML protected Button beginButton;
	@FXML protected Button initButton;
	@FXML protected Button initRButton;
	@FXML protected Button initIButton;
	protected Scheduler scheduler;
	protected boolean f = false;
	private static int pid = 1000;
	protected final String[] agrs= {"����","����","���","�ȴ�"};
	
	protected void setUpTableView(){
		//����tableView���������ֵ��Ԫ��
		pidColumn.setCellValueFactory(CellData->CellData.getValue().pidProperty().asObject());
		arriveColumn.setCellValueFactory(CellData->CellData.getValue().arriverTimeProperty().asObject());
		statusColumn.setCellValueFactory(cellData->new SimpleObjectProperty<>(agrs[cellData.getValue().getStatus()]));
		statusColumn.setCellFactory(new CellString());
		startColumn.setCellValueFactory(CellData->CellData.getValue().startTimeProperty().asObject());
		finishColumn.setCellValueFactory(CellData->CellData.getValue().finishTimeProperty().asObject());
		turnColumn.setCellValueFactory(CellData->CellData.getValue().turnaroundTimeProperty().asObject());
		rturnColumn.setCellValueFactory(CellData->CellData.getValue().rturnaroundTimeProperty().asObject());
		
		Cell cell = new Cell();
		CellFloat tableCell = new CellFloat(); 
		pidColumn.setCellFactory(cell);
		arriveColumn.setCellFactory(tableCell);
		startColumn.setCellFactory(tableCell);
		finishColumn.setCellFactory(tableCell);
		turnColumn.setCellFactory(tableCell);
		rturnColumn.setCellFactory(tableCell);		
	}
	protected void initList(LinkedList<PCB> linkedList) {
		for(PCB pcb : linkedList){
			scheduler.addProcess(pcb);
		}
	}
	@FXML
	public void beginPress() {
		if (f) {
			resultData.clear(); //��ս��������
			ArrayList<ResultModel> list = new ArrayList<ResultModel>(); //�������

			list = scheduler.runProcess(); 

			resultData.addAll(list);
			tableView.setItems(resultData);
			num.setText(String.valueOf(resultData.size()));
		} else {
			SimpleErrorAlert alert = new SimpleErrorAlert("����", "δ��ʼ��", "������ɳ�ʼ��");
			alert.show();
		}
	}
	@FXML 
	public void initPress(){
		//scheduler.clearList();
		LinkedList<PCB> linkedList = InitializationFormFile.getLinkedListFormFile();
		initList(linkedList);
		SimpleSuccessAlert alert = new SimpleSuccessAlert("��ʾ", "��ʼ�����", "");
		alert.show();
		f = true;
	}
	@FXML 
	public void initRPress(){
		//scheduler.clearList();
		TextInputDialog dialog = new TextInputDialog("6");
		dialog.setTitle("����");
		dialog.setHeaderText("�����ʼ��");
		dialog.setContentText("�������ʼ���Ľ�����:");

		// Traditional way to get the response value.
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent() && !result.get().equals("")
				&& Regular_expression.isNumeric(result.get())){
			int num = Integer.valueOf(result.get());
			LinkedList<PCB> linkedList = InitializationRandom.getLinkedListrandom(num);
			initList(linkedList);
			SimpleSuccessAlert alert = new SimpleSuccessAlert("��ʾ", "��ʼ�����", "");
			alert.show();
			f = true;
		} else {
			SimpleErrorAlert alert = new SimpleErrorAlert("����", "��ʼ��ʧ��", "�����³�ʼ��");
			alert.show();
		}
		/*	The Java 8 way to get the response value (with lambda expression).
		result.ifPresent(name -> System.out.println("Your name: " + name));
		 */
	}
	@FXML
	public void initIPress(){
		Dialog<LinkedList<PCB>> dialog = new Dialog<LinkedList<PCB>>();
	
		dialog.setTitle("����");
		dialog.setHeaderText("��������̵ĵ���ʱ�䣬��Ҫʱ������ȼ�");
		ButtonType nextButtonType = new ButtonType("��һ��",ButtonData.NEXT_FORWARD);
		ButtonType finishButtonType = new ButtonType("���",ButtonData.FINISH);
		dialog.getDialogPane().getButtonTypes().addAll(nextButtonType,finishButtonType);

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		TextField arriveTime = new TextField();
		TextField needTime = new TextField();
		TextField priority = new TextField("0");
		Label pidLabel = new Label("1000");
		
		grid.add(new Label("pid:"), 0, 0);
		grid.add(pidLabel, 1, 0);
		grid.add(new Label("����ʱ��:"), 0, 1);
		grid.add(arriveTime, 1, 1);
		grid.add(new Label("��Ҫʱ��:"), 0, 2);
		grid.add(needTime, 1, 2);
		grid.add(new Label("���ȼ�:"), 0, 3);
		grid.add(priority, 1, 3);


		dialog.getDialogPane().setContent(grid);

		dialog.setResultConverter(value->{
			if (value == nextButtonType) {
				if (Regular_expression.isNumeric(arriveTime.getText()) 
						&& Regular_expression.isNumeric(needTime.getText())) {
				InitializationInput.setInput(arriveTime.getText(), needTime.getText(),priority.getText());
				arriveTime.setText("");
				needTime.setText("");
				priority.setText("0");
				pidLabel.setText(String.valueOf(pid));
				pid++;
				return null;
			} else {
				SimpleErrorAlert alert = new SimpleErrorAlert("����", "�����ʽ����", "����������");
				alert.show();
			} 
			} if (value == finishButtonType) {
				dialog.hide();
				dialog.close();
				return InitializationInput.getLinkedListFormFile();
			}
			return null;
		});
		Optional<LinkedList<PCB>> optional = dialog.showAndWait();
		optional.ifPresent(result->{
			initList(result);
			f = true;
		});
	}
}
class Cell implements Callback<TableColumn<ResultModel,Integer>, TableCell<ResultModel,Integer>>{
	@Override
	public TableCell<ResultModel, Integer> call(TableColumn<ResultModel, Integer> param) {
		// TODO Auto-generated method stub
		return new TableCell<ResultModel, Integer>(){
			@Override
			protected void updateItem(Integer item, boolean empty) {
				// TODO Auto-generated method stub
				super.updateItem(item, empty);
				if (empty) {
					setText(null);
					setGraphic(null);
				} else {
					//setTextAlignment(TextAlignment.RIGHT);
					setText(String.valueOf(item));
					setAlignment(Pos.CENTER);
				}
			}

		};
	}
}
class CellFloat implements Callback<TableColumn<ResultModel,Float>, TableCell<ResultModel,Float>>{
	@Override
	public TableCell<ResultModel, Float> call(TableColumn<ResultModel, Float> param) {
		// TODO Auto-generated method stub
		return new TableCell<ResultModel, Float>(){
			@Override
			protected void updateItem(Float item, boolean empty) {
				// TODO Auto-generated method stub
				super.updateItem(item, empty);
				if (empty) {
					setText(null);
					setGraphic(null);
				} else {
					//setTextAlignment(TextAlignment.RIGHT);
					setText(String.valueOf(item));
					setAlignment(Pos.CENTER);
				}
			}

		};
	}
}