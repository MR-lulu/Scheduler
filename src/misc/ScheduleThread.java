package misc;

import java.util.Optional;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.ButtonType;
import javafx.util.Callback;

/**
 * ClassName: ScheduleThread 
 * @Description: 算法调度线程（守护线程），用于单独运行调度算法
 * @author Mr_blue
 * @date 2017年7月5日
 */
public class ScheduleThread {
	private Service<Void> thread;
	private Callback<Void, Void> callback; //回调方法
	
	public ScheduleThread(Task<Void> task){
		thread = new Service<Void>() {
			@Override
			protected Task<Void> createTask() {
				return task;       //创建线程任务
			}
		};    //调度线程完成之后显示提示框
		thread.setOnSucceeded(value->{
			SimpleSuccessAlert alert = new SimpleSuccessAlert("完成", "调度完成","是否查看调度过程");
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
