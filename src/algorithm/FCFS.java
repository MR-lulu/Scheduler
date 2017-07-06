package algorithm;

import java.util.ArrayList;
import java.util.LinkedList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import misc.InitializationFormFile;
import model.PCB;
import model.ResultModel;

/**
 * ClassName: FCFS 
 * @Description: TODO
 * @author Mr_blue
 * @date 2017年7月6日
 */
public class FCFS extends Scheduler{
	private ObservableList<ResultModel> readyList;
	private int arrived;
	private int nowtime;
	public FCFS(){
		readyList = FXCollections.observableArrayList();
		sequence=new ArrayList<String>();
	}
	@Override
	public void dynamicRun(ObservableList<ResultModel> List) {
		nowtime = 0;
		int end =0;
		arrived = 0;
		sort(List);
		while(end<List.size()){
			setReady(List);
			if (!readyList.isEmpty()) {
				float needTime = readyList.get(0).getNeedTime();
				if (needTime>0) {
					System.out.println("运行:"+readyList.get(0).getPid());
					if (readyList.get(0).getServerTime()==0) {
						readyList.get(0).setStartTime(nowtime);
						readyList.get(0).setServerTime(1);
					} else {
						float time = readyList.get(0).getServerTime();
						readyList.get(0).setServerTime(++time);
					}
					readyList.get(0).setStatus(1);
					sequence.add(String.valueOf(readyList.get(0).getPid()));
					readyList.get(0).setNeedTime(--needTime);
					nowtime++;
				} else {
					readyList.get(0).setStatus(2);
					readyList.get(0).setFinishTime(nowtime);
					float btime = readyList.get(0).getStartTime();
					float ftime = readyList.get(0).getFinishTime();
					float rtime = ftime-btime;
					readyList.get(0).setTurnaroundTime(rtime);
					readyList.get(0).setRturnaroundTime(rtime/readyList.get(0).getServerTime());
					end++;
					System.out.println("完成:"+readyList.get(0).getPid());
					readyList.remove(0);
				}
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	private void setReady(ObservableList<ResultModel> List){
		boolean f = false;
		for(ResultModel model:List){
			if (model.getArriveTime()==nowtime) {
				f = true;
				arrived++;
				model.setStatus(0);
				System.out.println("到达:"+model.getPid());
				readyList.add(model);
			}
		}
		if (!f && arrived<List.size()&&readyList.isEmpty()) {
			sequence.add("-1");
			System.out.println("空闲");
			nowtime++;
		}
	}
	public void sort(ObservableList<ResultModel> List) {
		for (int i = 1; i < List.size(); i++)
        {
			if (List.get(i-1).getArriveTime()>List.get(i).getArriveTime()) {
				 ResultModel temp= List.get(i);
				 int j = i;
				 while (j > 0 && List.get(j-1).getArriveTime() > temp.getArriveTime()){
					 List.set(j, List.get(j-1));
					 j--;
				 }
				 List.set(j, temp);
			}
        }
	}
	/*public static void main(String[] args) {
		FCFS fcfs = new FCFS();
		ObservableList<ResultModel> resultData = FXCollections.observableArrayList();
		LinkedList<PCB> linkedList = InitializationFormFile.getLinkedListFormFile();
		for(PCB pcb:linkedList){
			resultData.add(new ResultModel(pcb,pcb.getPid(), pcb.getState(), pcb.getPriority(), 
					0, pcb.getArriveTime(),pcb.getNeedTime(), 0, 0, 0,0));
		}
		fcfs.sort(resultData);
		fcfs.dynamicRun(resultData);
		System.out.println("---------------");
		for(String list: fcfs.getList()){
			System.out.println(list);
		}
	}*/
}
