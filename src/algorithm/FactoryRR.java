package algorithm;

import java.util.LinkedList;

import model.PCB;

/**
 * ClassName: FactoryRR 
 * @Description: ����RR�㷨
 * @author Mr_blue
 * @date 2017��6��28��
 */
public class FactoryRR implements Factory{

	@Override
	public Scheduler cteate() {
		LinkedList<PCB> list=new LinkedList<PCB>();
		return new RR(list);
	}
	
}
