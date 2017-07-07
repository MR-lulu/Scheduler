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
 * @Description: �����ȷ����㷨
 * @author Mr_blue
 * @date 2017��7��6��
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
			//ģ������CPU
			if (!readyList.isEmpty()) {
				float needTime = readyList.get(0).getNeedTime();
				//��Ҫʱ�䲻Ϊ0�������о������еĵ�һ������
				if (needTime>0) {
					System.out.println("����:"+readyList.get(0).getPid());
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
				//��Ҫʱ��Ϊ0���������ɣ��Ƴ���������
				} else {
					readyList.get(0).setStatus(2);
					readyList.get(0).setFinishTime(nowtime);
					float btime = readyList.get(0).getStartTime();
					float ftime = readyList.get(0).getFinishTime();
					float rtime = ftime-btime;
					readyList.get(0).setTurnaroundTime(rtime);
					readyList.get(0).setRturnaroundTime(rtime/readyList.get(0).getServerTime());
					end++;
					System.out.println("���:"+readyList.get(0).getPid());
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
		//������Ľ��̼����������
		for(ResultModel model:List){
			if (model.getArriveTime()==nowtime) {
				f = true;
				arrived++;
				model.setStatus(0);
				System.out.println("����:"+model.getPid());
				readyList.add(model);
			}
		}
		//û�н��̵��ﵫ���н���δ���ȣ���cpu����
		if (!f && arrived<List.size()&&readyList.isEmpty()) {
			sequence.add("-1");
			System.out.println("����");
			nowtime++;
		}
	}
	//������ʱ���������
	private void sort(ObservableList<ResultModel> List) {
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
