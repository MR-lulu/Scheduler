package misc;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Optional;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.PCB;
import view.ViewHelp;

/**
 * ClassName: CreateDialog 
 * @Description: ���������ʼ���Ի���
 * @author Mr_blue
 * @date 2017��6��29��
 */
public class CreateDialog {
	private static int pid = 1000;
	private Dialog<LinkedList<PCB>> dialog;  
	
	public CreateDialog(boolean isDisable){
		dialog = new Dialog<LinkedList<PCB>>();

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
		priority.setDisable(isDisable);
		
		grid.add(new Label("pid:"), 0, 0);
		grid.add(pidLabel, 1, 0);
		grid.add(new Label("����ʱ��:"), 0, 1);
		grid.add(arriveTime, 1, 1);
		grid.add(new Label("��Ҫʱ��:"), 0, 2);
		grid.add(needTime, 1, 2);
		grid.add(new Label("���ȼ�:"), 0, 3);
		grid.add(priority, 1, 3);

		//���öԻ����ڵĲ���
		dialog.getDialogPane().setContent(grid);
		
		//�Ի����ڵĵ���¼�
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
					return null; //������ֵΪnullʱshowAndWait()���᷵�ؽ��
				} else {
					SimpleErrorAlert alert = new SimpleErrorAlert("����", "�����ʽ����", "����������");
					alert.show();
				} 
			} if (value == finishButtonType) {
				dialog.hide();
				dialog.close();
				return InitializationInput.getLinkedListFormFile(); //�����Ѿ���ʼ����PCB����
			}
			return null;
		});
	}
	//��ʾ�Ի��򲢵ȴ����ؽ��
	public Optional<LinkedList<PCB>> showAndWait(){
		return dialog.showAndWait();
	}
}
