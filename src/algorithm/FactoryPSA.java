package algorithm;

import java.util.LinkedList;

import model.PCB;

/**
 * ClassName: FactoryPSA 
 * @Description: ����PSA�㷨
 * @author Mr_blue
 * @date 2017��6��28��
 */
public class FactoryPSA implements Factory{

	@Override
	public Scheduler cteate() {
		LinkedList<PCB> list=new LinkedList<PCB>();
		PSA psa = new PSA(list);
		return psa;
	}
	
}
