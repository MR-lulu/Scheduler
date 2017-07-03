package misc;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import view.ViewHelp;

/**
 * ClassName: CreateDialog 
 * @Description: 创建对话框
 * @author Mr_blue
 * @date 2017年6月29日
 */
public class CreateDialog {
	private FXMLLoader loader;
	private Stage stage;
	
	public CreateDialog(String title,String name) {
		stage = new Stage();
		Pane pane = null;
		loader = new FXMLLoader();
		try {
			loader.setLocation(ViewHelp.class.getResource(name));
			pane = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		stage.setScene(new Scene(pane));
		stage.setTitle(title);
		stage.showAndWait();
	}
	public Object getController(){
		return loader.getController();
	}
	public void close(){
		stage.close();
	}
}
