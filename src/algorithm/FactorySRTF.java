package algorithm;

import java.util.LinkedList;

import model.PCB;

/**
 * ClassName: FactorySRTF 
 * @Description: TODO
 * @author Mr_blue
 * @date 2017��6��30��
 */
public class FactorySRTF implements Factory{

	@Override
	public SRTF cteate() {
		LinkedList<PCB> list=new LinkedList<PCB>();
		SRTF srtf = new SRTF(list);
		return srtf;
	}
	
}
