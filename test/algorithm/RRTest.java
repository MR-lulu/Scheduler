package algorithm;

import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import misc.InitializationFormFile;
import model.PCB;
import model.ResultModel;

public class RRTest {

	@Test
	public void test() {
		String [] myList = {"1000","1001","1002","1003","1000","1004","1003","1000","1004"}; // 打印所有数组元素 
		boolean retVal;
		Factory factory = new FactoryRR();
		Scheduler scheduler = factory.cteate();
		LinkedList<PCB> linkedList = InitializationFormFile.getLinkedListFormFile();
		ObservableList<ResultModel> resultData = FXCollections.observableArrayList();
		for(PCB pcb:linkedList){
			scheduler.addProcess(pcb);
			resultData.add(new ResultModel(pcb,pcb.getPid(), pcb.getState(), pcb.getPriority(), 
					0, pcb.getArriveTime(),pcb.getNeedTime(), 0, 0, 0,0));
		}
		scheduler.dynamicRun(resultData);
		String[] str2 = scheduler.getList().toArray(new String[myList.length]);
		assertArrayEquals(myList,str2);
		for (int i = 0 ; i < myList.length ; i++) {
			System.out.println(myList[i]); 
			} 
		} 
	}


