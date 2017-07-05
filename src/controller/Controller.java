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
import javafx.util.Callback;
import misc.CreateDialog;
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
 * @Description: 控制器父类，设置控制器共同的元素
 * @author Mr_blue
 * @date 2017年6月28日
 */
public class Controller {
	@FXML protected TableColumn<ResultModel, Integer> pidColumn;
	@FXML protected TableColumn<ResultModel, Float> startColumn;
	@FXML protected TableColumn<ResultModel, String> statusColumn;
	@FXML protected TableColumn<ResultModel, Float> finishColumn;
	@FXML protected TableColumn<ResultModel, Float> turnColumn;
	@FXML protected TableColumn<ResultModel, Float> rturnColumn;
	@FXML protected TableColumn<ResultModel, Float> arriveColumn;
	@FXML protected Label num;

	@FXML protected TableView<ResultModel> tableView;
	protected ObservableList<ResultModel> resultData = FXCollections.observableArrayList();
	@FXML protected Button beginButton;
	@FXML protected Button processButton;
	@FXML protected Button initButton;
	@FXML protected Button initRButton;
	@FXML protected Button initIButton;
	protected Scheduler scheduler;
	protected boolean f = false;
	private static int pid = 1000;

	protected void setUpTableView(){
		//设置tableView表格内所填值的元素
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
		pidColumn.setCellFactory(cell);
		arriveColumn.setCellFactory(tableCell);
		startColumn.setCellFactory(tableCell);
		statusColumn.setCellFactory(new CellString());
		finishColumn.setCellFactory(tableCell);
		turnColumn.setCellFactory(tableCell);
		rturnColumn.setCellFactory(tableCell);		
	}
	protected void initList(LinkedList<PCB> linkedList) {
		for(PCB pcb:linkedList){
			scheduler.addProcess(pcb);
			resultData.add(new ResultModel(pcb,pcb.getPid(), pcb.getState(), pcb.getPriority(), 
					0, pcb.getArriveTime(),pcb.getNeedTime(), 0, 0, 0,0));
		}
	}
	@FXML
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
	@FXML 
	public void initPress(){
		//scheduler.clearList();
		LinkedList<PCB> linkedList = InitializationFormFile.getLinkedListFormFile();
		initList(linkedList);
		SimpleSuccessAlert alert = new SimpleSuccessAlert("提示", "初始化完成", "");
		alert.show();
		f = true;
	}
	@FXML 
	public void initRPress(){
		//scheduler.clearList();
		TextInputDialog dialog = new TextInputDialog("6");
		dialog.setTitle("输入");
		dialog.setHeaderText("随机初始化");
		dialog.setContentText("请输入初始化的进程数:");

		// Traditional way to get the response value.
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent() && !result.get().equals("")
				&& Regular_expression.isNumeric(result.get())){
			int num = Integer.valueOf(result.get());
			LinkedList<PCB> linkedList = InitializationRandom.getLinkedListrandom(num);
			initList(linkedList);
			SimpleSuccessAlert alert = new SimpleSuccessAlert("提示", "初始化完成", "");
			alert.show();
			f = true;
		} else {
			SimpleErrorAlert alert = new SimpleErrorAlert("错误", "初始化失败", "请重新初始化");
			alert.show();
		}
		/*	The Java 8 way to get the response value (with lambda expression).
		result.ifPresent(name -> System.out.println("Your name: " + name));
		 */
	}
	@FXML
	public void initIPress(){
		/*Dialog<LinkedList<PCB>> dialog = new Dialog<LinkedList<PCB>>();

		dialog.setTitle("输入");
		dialog.setHeaderText("请输入进程的到达时间，需要时间和优先级");
		ButtonType nextButtonType = new ButtonType("下一步",ButtonData.NEXT_FORWARD);
		ButtonType finishButtonType = new ButtonType("完成",ButtonData.FINISH);
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
		grid.add(new Label("到达时间:"), 0, 1);
		grid.add(arriveTime, 1, 1);
		grid.add(new Label("需要时间:"), 0, 2);
		grid.add(needTime, 1, 2);
		grid.add(new Label("优先级:"), 0, 3);
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
					SimpleErrorAlert alert = new SimpleErrorAlert("错误", "输入格式错误", "请重新输入");
					alert.show();
				} 
			} if (value == finishButtonType) {
				dialog.hide();
				dialog.close();
				return InitializationInput.getLinkedListFormFile();
			}
			return null;
		});*/
		Optional<LinkedList<PCB>> optional = new CreateDialog(true).showAndWait();
		optional.ifPresent(result->{
			initList(result);
			f = true;
		});
	}
	@FXML public void proPress(){
		Dialog<Void> dialog = new Dialog<Void>();
		dialog.setTitle("调度过程");
		dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK,ButtonType.CLOSE);
		dialog.getDialogPane().setPrefSize(1000, 800);

		final NumberAxis xAxis = new NumberAxis();
		final CategoryAxis yAxis = new CategoryAxis();
		final LineChart<Number,String> lineChart = 
				new LineChart<Number,String>(xAxis,yAxis);

		lineChart.setTitle("算法调度过程");
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
				series[i].getData().add(new XYChart.Data(i+j, "进程"+list.get(i)));
			}
			lineChart.getData().add(series[i]);
		}
		dialog.showAndWait();
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