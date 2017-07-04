package algorithm;

import java.util.ArrayList;
import java.util.LinkedList;

import javafx.collections.ObservableList;
import model.PCB;
import model.ResultModel;

public abstract class Scheduler {
	protected ArrayList<String> sequence;
	protected LinkedList<PCB> list;
	public void addProcess(PCB pcb){
		this.list.add(pcb);
	}
	public void deleteProcess(PCB pcb){
		for(int i1=0;i1<this.list.size();i1++)
		{
			if(pcb.getPid()==this.list.get(i1).getPid())
				this.list.remove(i1);
		}
	}
	public void clearList()
	{
		this.list.clear();
	}
	public ArrayList<ResultModel> runProcess(){
		return null;
	};
	public abstract void dynamicRun(ObservableList<ResultModel> List);
	public ArrayList<String> getList()
	{
		return this.sequence;
	}
}


