package algorithm;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.ResultModel;

public abstract class Threads extends Thread {
protected int cpuid;
protected boolean suspended;
protected float nowtime;
protected int processnum;//cpu涓婄殑杩涚▼鏁扮洰 
public ObservableList<ResultModel> List;
public Threads(int cpuid,ObservableList<ResultModel> List)
{
	this.List = FXCollections.observableArrayList();
	this.List=List;
	this.cpuid=cpuid;
	this.suspended=true;//true涓虹┖闂诧紝false涓鸿繍琛�
	this.nowtime=0;
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


}
