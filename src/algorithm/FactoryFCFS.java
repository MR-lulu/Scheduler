package algorithm;
/**
 * ClassName: FactoryFCFS 
 * @Description: TODO
 * @author Mr_blue
 * @date 2017Äê7ÔÂ6ÈÕ
 */
public class FactoryFCFS implements Factory{
	@Override
	public Scheduler cteate() {
		FCFS fcfs = new FCFS();
		return fcfs;
	}
	
}
