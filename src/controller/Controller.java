package controller;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Optional;

import algorithm.Scheduler;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.Worker.State;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
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
import javafx.stage.FileChooser;
import javafx.util.Callback;
import misc.CreateDialog;
import misc.InitializationFormFile;
import misc.InitializationInput;
import misc.InitializationRandom;
import misc.Regular_expression;
import misc.ScheduleThread;
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
	//����е�ÿһ��
	@FXML protected TableColumn<ResultModel, Integer> pidColumn;
	@FXML protected TableColumn<ResultModel, Float> startColumn;
	@FXML protected TableColumn<ResultModel, String> statusColumn;
	@FXML protected TableColumn<ResultModel, Float> finishColumn;
	@FXML protected TableColumn<ResultModel, Float> turnColumn;
	@FXML protected TableColumn<ResultModel, Float> rturnColumn;
	@FXML protected TableColumn<ResultModel, Float> arriveColumn;
	@FXML protected Label num;

	@FXML protected TableView<ResultModel> tableView;
	protected ObservableList<ResultModel> resultData = FXCollections.observableArrayList();//����е�ֵ
	@FXML protected Button beginButton;  //��ʼ��ť
	@FXML protected Button processButton; //���ȹ��̰�ť
	@FXML protected Button initButton; // �ļ���ʼ����ť
	@FXML protected Button initRButton; //�����ʼ����ť
	@FXML protected Button initIButton; //�����ʼ����ť
	protected Scheduler scheduler;  
	protected boolean f = false;
	private static int pid = 1000;

	protected void setUpTableView(){
		//����tableView���������ֵ��Ԫ�أ�ʹ��java8��lambda���ʽ�������
		pidColumn.setCellValueFactory(CellData->CellData.getValue().pidProperty().asObject());
		arriveColumn.setCellValueFactory(CellData->CellData.getValue().arriverTimeProperty().asObject());
		//statusColumn.setCellValueFactory(CellData->CellData.getValue().statusProperty().asObject());
		statusColumn.setCellValueFactory(CellData->new SimpleObjectProperty<>(CellData.getValue().getStatusString()));
		startColumn.setCellValueFactory(CellData->CellData.getValue().startTimeProperty().asObject());
		finishColumn.setCellValueFactory(CellData->CellData.getValue().finishTimeProperty().asObject());
		turnColumn.setCellValueFactory(CellData->CellData.getValue().turnaroundTimeProperty().asObject());
		rturnColumn.setCellValueFactory(CellData->CellData.getValue().rturnaroundTimeProperty().asObject());

		Cell cell = new Cell();
		CellFloat tableCell = new CellFloat();
		//���ñ����ʽ
		pidColumn.setCellFactory(cell);
		arriveColumn.setCellFactory(tableCell);
		startColumn.setCellFactory(tableCell);
		statusColumn.setCellFactory(new CellString());
		finishColumn.setCellFactory(tableCell);
		turnColumn.setCellFactory(tableCell);
		rturnColumn.setCellFactory(tableCell);		
	}
	//��ʼ�����̶���
	protected void initList(LinkedList<PCB> linkedList) {
		for(PCB pcb:linkedList){
			scheduler.addProcess(pcb);
			resultData.add(new ResultModel(pcb,pcb.getPid(), pcb.getState(), pcb.getPriority(), 
					0, pcb.getArriveTime(),pcb.getNeedTime(), 0, 0, 0,0));
		}
	}
	@FXML
	public void beginPress() {
		//f = true ���Ѿ���ʼ��
		if (f) {
			tableView.setItems(resultData); //���ñ���ֵ
			num.setText(String.valueOf(resultData.size())); //���ý���������Label
			f = false;
			/**
			 * ��ͨJava�߳�(�ػ��߳�)
			 */
			/*new Thread(()->{
				scheduler.dynamicRun(resultData);
			}).start();*/
			/**
			 * Javafx�߳�
			 */
			new ScheduleThread(new Task<Void>() {
				@Override
				protected Void call() throws Exception {
					scheduler.dynamicRun(resultData);
					return null;
				}
			}).run(new Callback<Void, Void>() {
				//�ص�����
				@Override
				public Void call(Void param) {
					proPress();
					return null;
				}
			});
		} else {
			SimpleErrorAlert alert = new SimpleErrorAlert("����", "δ��ʼ��", "������ɳ�ʼ��");
			alert.show();
		}
	}
	@FXML 
	public void initPress(){
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("ѡ���ʼ���ļ�,��ʽΪTXT");
		File file = fileChooser.showOpenDialog(null);
		if (file!=null) {
			//�ļ���ʼ��
			LinkedList<PCB> linkedList = InitializationFormFile.getLinkedListFormFile(file);
			initList(linkedList);
			SimpleSuccessAlert alert = new SimpleSuccessAlert("��ʾ", "��ʼ�����", "");
			alert.show();
			f = true;
		} else {
			f = false;
		}
		
	}
	@FXML 
	public void initRPress(){
		//�����ʼ���Ի���
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
		//�����ʼ��
		Optional<LinkedList<PCB>> optional = new CreateDialog(true).showAndWait();
		optional.ifPresent(result->{
			initList(result);
			f = true;
		});
	}
	@FXML public void proPress(){
		//���ȹ��̶Ի���
		Dialog<Void> dialog = new Dialog<Void>();
		dialog.setTitle("���ȹ���");
		dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK,ButtonType.CLOSE);
		dialog.getDialogPane().setPrefSize(1000, 800);

		final NumberAxis xAxis = new NumberAxis();//X��
		final CategoryAxis yAxis = new CategoryAxis();//Y��
		final LineChart<Number,String> lineChart = 
				new LineChart<Number,String>(xAxis,yAxis);

		lineChart.setTitle("�㷨���ȹ���");
		dialog.getDialogPane().setContent(lineChart);
		lineChart.setLegendVisible(false);
		
		ArrayList<String> list = scheduler.getList();
	/*	ArrayList<String> list = new ArrayList<String>();
		list.add("1000");
		list.add("1000");
		list.add("1001");
		list.add("1002");
		list.add("1001");*/
		XYChart.Series series[] = new XYChart.Series[list.size()];
		
		for (int i = 0; i < list.size(); i++) {
			series[i] = new XYChart.Series();
			if (list.get(i).equals("-1")) {
				continue;
			}
			for (int j = 0; j < 2; j++) {
				series[i].getData().add(new XYChart.Data(i+j, "����"+list.get(i)));
			}
			lineChart.getData().add(series[i]);
		}
		dialog.showAndWait();
	}
}
/*
 * Cell��CellFloat��CellString�������ڸı������ʾ��ʽ
 */
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
					setAlignment(Pos.CENTER); //�����ס
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
					setAlignment(Pos.CENTER); //�����ס
				}
			}

		};
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
					//��ͬ״̬�����ɫ��ͬ
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