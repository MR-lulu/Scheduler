package algorithm;
/**
 * ClassName: FactoryMPSA 
 * @Description: TODO
 * @author Mr_blue
 * @date 2017��7��6��
 */
public class FactoryMPSA implements Factory{
	@Override
	public Scheduler cteate() {
		// TODO Auto-generated method stub
		return new MPSA(2);
	}
}
