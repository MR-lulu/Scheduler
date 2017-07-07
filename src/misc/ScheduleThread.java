package misc;

import java.util.Optional;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.ButtonType;
import javafx.util.Callback;

/**
 * ClassName: ScheduleThread 
 * @Description: �㷨�����̣߳��ػ��̣߳������ڵ������е����㷨
 * @author Mr_blue
 * @date 2017��7��5��
 */
public class ScheduleThread {
	private Service<Void> thread;
	private Callback<Void, Void> callback; //�ص�����
	
	public ScheduleThread(Task<Void> task){
		thread = new Service<Void>() {
			@Override
			protected Task<Void> createTask() {
				return task;       //�����߳�����
			}
		};    //�����߳����֮����ʾ��ʾ��
		thread.setOnSucceeded(value->{
			SimpleSuccessAlert alert = new SimpleSuccessAlert("���", "�������","�Ƿ�鿴���ȹ���");
			Optional<ButtonType> optional = alert.showAndWait();
			optional.ifPresent(result->{
				if (result==ButtonType.OK) {
					callback.call(null);
				}
			});
		});
	}
	public void run(Callback<Void, Void> callback){
		this.callback = callback;
		thread.start();
	}
}
