package algorithm;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.ResultModel;

public abstract class Threads extends Thread {
protected int cpuid;
protected boolean suspended;
protected float nowtime;
protected int processnum;//cpu�ϵĽ�����Ŀ 
public ObservableList<ResultModel> List;
protected ArrayList<String> cpusequence=new ArrayList<String>();
public Threads(int cpuid,ObservableList<ResultModel> List)
{
	this.List = FXCollections.observableArrayList();
	this.List=List;
	this.cpuid=cpuid;
	this.suspended=true;//trueΪ���У�falseΪ����
	this.nowtime=0;
	this.cpusequence=new ArrayList<String>();
}

public void getProcess(ObservableList<ResultModel> List){}
public void agingProcess(ObservableList<ResultModel> List){}
public void findHigh(ObservableList<ResultModel> List){}


public int getCpuId() {
	return cpuid;
}



public void setSuspended(boolean suspended) {
	this.suspended = suspended;
}
public synchronized void resumecpu(float nowtime2){
    suspended = false;
    this.nowtime=nowtime2;
    notify();
}

public ArrayList<String> getCpusequence() {
	return cpusequence;
}

public void setCpusequence(ArrayList<String> cpusequence) {
	this.cpusequence = cpusequence;
}


}
