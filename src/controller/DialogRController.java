package controller;
/**
 * ClassName: DialogRController 
 * @Description: TODO
 * @author Mr_blue
 * @date 2017Äê6ÔÂ29ÈÕ
 */
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import misc.CreateDialog;

public class DialogRController {
	@FXML private Button ackButton;
	@FXML private Button cancelButton;
	@FXML private TextField input;
	private CreateDialog dialog;
	
	@FXML 
	public void ackPress(){
		dialog.close();
	}
	@FXML
	public void cancelPress(){
		dialog.close();
	}
	@FXML public int getPNum(){
		return Integer.valueOf(input.getText());
	}
	public void setDialog(CreateDialog dialog){
		this.dialog = dialog;
	}
}
