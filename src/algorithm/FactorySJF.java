package algorithm;
import java.util.LinkedList;

import model.PCB;

/**
 * 
 * ClassName: FactorySJF 
 * @Description: ����SJF�㷨
 * @author Mr_blue
 * @date 2017��6��28��
 */
public class FactorySJF implements Factory{
	@Override
	public SJF cteate() {
		// �������� ����SJF�㷨
		LinkedList<PCB> list=new LinkedList<PCB>();
		SJF sjf=new SJF(list);
		return sjf;
	}
}
