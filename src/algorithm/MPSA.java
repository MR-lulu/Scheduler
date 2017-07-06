package algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.PCB;
import model.ResultModel;

public class MPSA extends Scheduler{
	private int cpumun;
	private int end;
	private ArrayList<MPSAcpu> cpuinfo=null;
	public MPSA(int cpumun)
	{
		//this.List =new LinkedList<PCB>();
		//List = list;
		this.cpumun=cpumun;
		sequence=new ArrayList<String>();
		this.cpuinfo=new ArrayList<>();
	}
	
	public void sort(ObservableList<ResultModel> List) {
		for(int i=0;i<List.size();i++)
		{
			 for(int j=0;j<=i;j++)
			 {
				 if(List.get(i).getPcb().getArriveTime()<List.get(j).getPcb().getArriveTime())
				 {
					 PCB temp=new PCB();
					 //璧嬪�肩粰涓棿鍊�
					 
					 temp.setArriveTime(List.get(i).getPcb().getArriveTime());
					 temp.setFinishTime(List.get(i).getPcb().getFinishTime());
					 temp.setPid(List.get(i).getPcb().getPid());
					 temp.setNeedTime(List.get(i).getPcb().getNeedTime());
					 
					 temp.setStatus(List.get(i).getStatus());
					 temp.setPid(List.get(i).getPid());
					 temp.setStartTime(List.get(i).getStartTime());
					 temp.setFinishTime(List.get(i).getFinishTime());
					 
					 //----------------
					 List.set(i, List.get(j));
					 ResultModel tResultModel=new ResultModel();
					 tResultModel.setCupid(-1);
					 tResultModel.setPcb(temp);
						tResultModel.setNeedTime(temp.getNeedTime());
						tResultModel.setServerTime(temp.getServiceTime());
						tResultModel.setPriority(temp.getPriority());
						tResultModel.setStatus(3);
						tResultModel.setPid(temp.getPid());
						tResultModel.setStartTime(temp.getStartTime());
						tResultModel.setFinishTime(temp.getFinishTime());
					 //---------------
					 List.set(j, tResultModel);
				 }
			 }
		}
		
	}
	public void dynamicRun(ObservableList<ResultModel> List)
	{
		int nowtime=0;
		end=List.size();
		sort(List);
		
		//HashMap<MPSAcpu, Boolean> aMap=new HashMap<MPSA.MPSAcpu, Boolean>();
		//aMap.get(cpuinfo.get(0));
		for(int i=0;i<this.cpumun;i++){
			   //String a1 = new String();
			cpuinfo.add(new MPSAcpu(i,List));
			cpuinfo.get(i).start();
			}
		
		//for()
		while(this.end<=List.size())
		{
			  //out(List);
		setready(nowtime, List);
		//System.out.println(cpuinfo.get(0).processnum);
		//System.out.println(cpuinfo.get(1).processnum);
		//System.out.println(cpuinfo.get(2).processnum);
		//System.out.println(cpuinfo.get(3).processnum);
		//int cpuid= findCpu();
		//System.out.println("绯荤粺鏃堕棿"+nowtime);
		//allout(List, cpuid);
		
		try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
		nowtime++;
		}
	}
	public synchronized int findHigh(float nowtime,ObservableList<ResultModel> List,int cpuid)
	{
		int value=-1;
		float high=10000;
		/*if(this.List.get(i).getArriveTime()>nowtime)
		{
			return -1;
		}*/
		//ResultModel tResultModel=new ResultModel();
		for(int i1=0;i1<List.size();i1++)
		{
			//PCB tPcb=new PCB();
			System.out.println("1."+cpuid+" "+List.get(i1).getCupid());
				System.out.println("2."+List.get(i1).getStatus()+" "+0);
			if(List.get(i1).getStatus()==0&&List.get(i1).getCupid()==cpuid)
			{
				/*t1.setStartTime(this.List.get(value).getStartTime());
				t1.setArriveTime(this.List.get(value).getArriveTime());
				t1.setPid(this.List.get(value).getPid());
				t1.setNeedTime(this.List.get(value).getNeedTime());
				t1.setPriority(this.List.get(value).getPriority());
				t1.setStatus(0);
				this.List.set(i1, t1);*/
				
				if(high>List.get(i1).getPriority())
				{
					high=List.get(i1).getPriority();
					value=i1;
				}
				
			}
			//float min=this.List.get(i).getServiceTime();
		}
		
		return value;
	}
	public synchronized void setready(float nowtime,ObservableList<ResultModel> List)
	{
		//System.out.println("===========鏈夐槦鍒楀埌杈�=============");
		for(int i1=0;i1<List.size();i1++)
		{
			PCB tPcb=new PCB();
			ResultModel tResultModel=new ResultModel();
			if(List.get(i1).getPcb().getArriveTime()<=nowtime&&List.get(i1).getPcb().getStatus()==3)
			{
				//System.out.println("杩欓噷鐨勬椂闂翠负"+nowtime);
				tPcb.setStartTime(List.get(i1).getPcb().getStartTime());
				tPcb.setArriveTime(List.get(i1).getPcb().getArriveTime());
				tPcb.setPid(List.get(i1).getPcb().getPid());
				tPcb.setServiceTime(List.get(i1).getPcb().getServiceTime());
				tPcb.setNeedTime(List.get(i1).getPcb().getNeedTime());
				tPcb.setPriority(List.get(i1).getPcb().getPriority());
				tPcb.setFinishTime(List.get(i1).getPcb().getFinishTime());
				
				
				
				
				tResultModel.setPcb(tPcb);
				tResultModel.setCupid(-1);
				tResultModel.setNeedTime(tPcb.getNeedTime());
				tResultModel.setServerTime(tPcb.getServiceTime());
				tResultModel.setPriority(tPcb.getPriority());
				tResultModel.setStatus(0);
				tResultModel.setPid(List.get(i1).getPcb().getPid());
				tResultModel.setStartTime(tPcb.getStartTime());
				tResultModel.setArriveTime(tPcb.getArriveTime());
				tResultModel.setFinishTime(List.get(i1).getPcb().getFinishTime());
				tPcb.setStatus(0);
				int cpuid=findCpu(nowtime);
				
				tResultModel.setCupid(cpuid);
				List.set(i1, tResultModel);
				
				//System.out.println("鍚嶅瓧"+List.get(i1).getPid()+" "+"鐘舵��"+List.get(i1).getPcb().getStatus()+"浼樺厛绾�"+List.get(i1).getPcb().getPriority()
					//	+"鍒嗛厤cpu"+tResultModel.getCupid());
				
			    
			}
			
			//float min=this.List.get(i).getServiceTime();
		}
		System.out.println("==============================");
	}
	public synchronized void aging(ObservableList<ResultModel> List)
	{
		for(int i1=0;i1<List.size();i1++)
		{
			PCB tPcb=new PCB();
			ResultModel tResultModel=new ResultModel();
			if(List.get(i1).getPcb().getStatus()==0)
			{
				tPcb.setStartTime(List.get(i1).getStartTime());
				tPcb.setArriveTime(List.get(i1).getPcb().getArriveTime());
				tPcb.setPid(List.get(i1).getPcb().getPid());
				tPcb.setNeedTime(List.get(i1).getPcb().getNeedTime());
				tPcb.setServiceTime(List.get(i1).getPcb().getServiceTime());
				if(List.get(i1).getPcb().getPriority()-3<=0)
				{
					tPcb.setPriority(0);
				}
				else {
					tPcb.setPriority(List.get(i1).getPcb().getPriority()-3);
				}
				
				tPcb.setFinishTime(List.get(i1).getPcb().getFinishTime());
				tPcb.setStatus(0);
				tResultModel.setPcb(tPcb);
				tResultModel.setPid(tPcb.getPid());
				tResultModel.setNeedTime(tPcb.getNeedTime());
				tResultModel.setServerTime(tPcb.getServiceTime());
				tResultModel.setPriority(tPcb.getPriority());
				tResultModel.setStatus(0);
				tResultModel.setStartTime(tPcb.getStartTime());
				tResultModel.setArriveTime(tPcb.getArriveTime());
				tResultModel.setFinishTime(tPcb.getFinishTime());
				List.set(i1, tResultModel);
				System.out.println("鑰佸寲缁撴灉锛堬紙锛堬紙锛夛級锛夛級锛夛級");
				 System.out.println("鍚嶅瓧"+List.get(i1).getPid()+" "+i1);
			     System.out.println("浼樺厛绾�"+List.get(i1).getPcb().getPriority());
			     System.out.println("鍒拌揪鏃堕棿"+List.get(i1).getPcb().getArriveTime());
			     System.out.println("寮�濮嬫椂闂�"+List.get(i1).getStartTime());
			     System.out.println("鐘舵��"+List.get(i1).getPcb().getStatus());
			     System.out.println("闇�瑕佹椂闂�"+List.get(i1).getPcb().getNeedTime());
			     System.out.println("鏈嶅姟鏃堕棿"+List.get(i1).getPcb().getServiceTime());
			     System.out.println("瀹屾垚鏃堕棿"+List.get(i1).getFinishTime());
			
				
			}
			//float min=this.List.get(i).getServiceTime();
		}
	}
	public synchronized int findCpu(float nowtime) {
		int value=0;
		int max=100;
		//System.out.println("cpu鎬绘暟鐩�"+cpumun);
		for (int i =0; i <= this.cpumun-1; i++) {
			//System.out.println("璇pu杩涚▼鏁扮洰"+cpuinfo.get(i).processnum);
			if(cpuinfo.get(i).processnum<max)
			{
				max=cpuinfo.get(i).processnum;
				value=i;
				//break;
			}
		}
		//System.out.println("鏈�灏�"+max);
		if(max==0)
		{
			System.out.println("cpu"+value+"鍚姩");
			cpuinfo.get(value).resumecpu(nowtime);
		}
		cpuinfo.get(value).processnum++;
		return value;
	}
	
	public synchronized void out(ObservableList<ResultModel> List)
	{
		System.out.println("<<<<<<<<<<<<<<<鎵�鏈夎繘绋嬫儏鍐�>>>>>>>>>>>>>>>>>>>>");
		for(int i1=0;i1<List.size();i1++)
		{
		     System.out.println("鍚嶅瓧"+List.get(i1).getPid()+" "+i1);
		     System.out.println("浼樺厛绾�"+List.get(i1).getPriority());
		     System.out.println("鍒拌揪鏃堕棿"+List.get(i1).getPcb().getArriveTime());
		     System.out.println("寮�濮嬫椂闂�"+List.get(i1).getStartTime());
		     System.out.println("鐘舵��"+List.get(i1).getStatus());
		     System.out.println("闇�瑕佹椂闂�"+List.get(i1).getNeedTime());
		     System.out.println("鏈嶅姟鏃堕棿"+List.get(i1).getPcb().getServiceTime());
		     System.out.println("瀹屾垚鏃堕棿"+List.get(i1).getFinishTime());
		     System.out.println("鍒嗛厤cpu"+List.get(i1).getCupid());
		}
		System.out.println("<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>");
	}

	public class MPSAcpu extends Threads
	{
		public MPSAcpu(int cpuId,ObservableList<ResultModel> List) {
			super(cpuId, List);
			// TODO Auto-generated constructor stub
		}

		public void run() {
	        super.run();
	        while(true)
	        {
	        	setreadyInCpu(cpuid, nowtime, List);
	        	try {
	                 Thread.sleep(1000);
	                 synchronized(this) {
	                     while(suspended) {
	                        wait();
	                     }
	                 }
	             } catch (InterruptedException e) {
	                 System.out.println("Thread " +  getCpuId() + " interrupted.");
	                 e.printStackTrace();
	             }
	        	//setready(nowtime, List);
	        	//int ret=cpuwork(nowtime, List);
	        	 
	        	 
	        	 System.out.println("cpu " +  getCpuId() + " 鍦ㄦ墽琛�");
	        	 int value=MPSA.this.findHigh(nowtime,List,this.cpuid);
	        	 System.out.println("cpu " +  getCpuId() + " 鎵ц杩涚▼"+value);
	        	 if(work(value, cpuid, nowtime, List)==-1)
	        	 {
	        		 setSuspended(true);
	        	 }
	        	 
	         nowtime++;
	        // System.out.println("褰撳墠cpu鏍稿績鍙�"+getCpuId()+"褰撳墠鏃堕棿"+nowtime);
	        }
	    }
	}
public synchronized void setreadyInCpu(int cpuid,float nowtime,ObservableList<ResultModel> List)
{
	for(int i1=0;i1<List.size();i1++)
	{
		PCB tPcb=new PCB();
		ResultModel tResultModel=new ResultModel();
		if(List.get(i1).getPcb().getArriveTime()<=nowtime&&List.get(i1).getPcb().getStatus()==1&&List.get(i1).getCupid()==cpuid)
		{
			tPcb.setStartTime(List.get(i1).getPcb().getStartTime());
			tPcb.setArriveTime(List.get(i1).getPcb().getArriveTime());
			tPcb.setPid(List.get(i1).getPcb().getPid());
			tPcb.setServiceTime(List.get(i1).getPcb().getServiceTime());
			tPcb.setNeedTime(List.get(i1).getPcb().getNeedTime());
			tPcb.setPriority(List.get(i1).getPcb().getPriority());
			tPcb.setFinishTime(List.get(i1).getPcb().getFinishTime());
			
			tPcb.setStatus(0);
			tResultModel.setPcb(tPcb);
			tResultModel.setNeedTime(tPcb.getNeedTime());
			tResultModel.setServerTime(tPcb.getServiceTime());
			tResultModel.setPriority(tPcb.getPriority());
			tResultModel.setCupid(List.get(i1).getCupid());
			tResultModel.setStatus(0);
			tResultModel.setPid(List.get(i1).getPcb().getPid());
			tResultModel.setStartTime(tPcb.getStartTime());
			tResultModel.setArriveTime(tPcb.getArriveTime());
			tResultModel.setFinishTime(List.get(i1).getPcb().getFinishTime());
			List.set(i1, tResultModel);
			
			//System.out.println("鍚嶅瓧"+List.get(i1).getPid()+" "+i1);
			//System.out.println("鐘舵��"+List.get(i1).getPcb().getStatus());
		   // System.out.println("浼樺厛绾�"+List.get(i1).getPcb().getPriority());
		}
		
		//float min=this.List.get(i).getServiceTime();
	}
}
public synchronized int work(int value,int cpuid,float nowtime,ObservableList<ResultModel> List) {
	if(value>=0)
	 {
	 ResultModel tResultModel=new ResultModel();
		PCB tPcb=new PCB(); 
		if(List.get(value).getStartTime()==0&&List.get(value).getStartTime()!=List.get(value).getPcb().getArriveTime())//濡傛灉鏄涓�娆¤繍琛岃缃叾杩愯鏃堕棿
	{
		System.out.println("绗�"+List.get(value).getPid()+"涓紑濮�----------");
		tPcb.setStartTime(nowtime);
	}
	else {
		tPcb.setStartTime(List.get(value).getStartTime());
		tResultModel.setStartTime(List.get(value).getStartTime());
	}
		
	
	
	
	tPcb.setArriveTime(List.get(value).getPcb().getArriveTime());
	tPcb.setPid(List.get(value).getPid());
	tPcb.setNeedTime(List.get(value).getPcb().getNeedTime()-1);
	tPcb.setServiceTime(List.get(value).getPcb().getServiceTime()+1);
	
	tPcb.setPriority(List.get(value).getPcb().getPriority()+3);
	tPcb.setStatus(1);
	
	tResultModel.setPid(tPcb.getPid());
	tResultModel.setStartTime(tPcb.getStartTime());
	tResultModel.setNeedTime(tPcb.getNeedTime());
	tResultModel.setServerTime(tPcb.getServiceTime());
	tResultModel.setArriveTime(tPcb.getArriveTime());
	tResultModel.setPriority(tPcb.getPriority());
	tResultModel.setCupid(List.get(value).getCupid());
	tResultModel.setStatus(1);
	if(tPcb.getNeedTime()==0)//濡傛灉浠诲姟缁撴潫璁剧疆鍏剁粨鏉熸椂闂村拰璁剧疆鐘舵�佷负缁撴潫
	{
		tPcb.setFinishTime(nowtime+1);
		tResultModel.setFinishTime(nowtime+1);
		tPcb.setStatus(2);
		tResultModel.setStatus(2);
		tResultModel.setTurnaroundTime(tPcb.getFinishTime()-tPcb.getArriveTime());
		tResultModel.setRturnaroundTime((tPcb.getFinishTime()-tPcb.getArriveTime())/tPcb.getServiceTime());
		System.out.println("绗�"+value+"涓粨鏉�");
		this.end++;
	}
	tResultModel.setPcb(tPcb);
	List.set(value,tResultModel);
	System.out.println("鍚嶅瓧"+List.get(value).getPid()+" "+"浼樺厛绾�"+List.get(value).getPriority()+" "+"鍒拌揪鏃堕棿"+List.get(value).getArriveTime()
			 +" "+"寮�濮嬫椂闂�"+List.get(value).getStartTime()
			 +" "+"闇�瑕佹椂闂�"+List.get(value).getNeedTime()
			 +" "+"鏈嶅姟鏃堕棿"+List.get(value).getServerTime()
			 +" "+"瀹屾垚鏃堕棿"+List.get(value).getFinishTime()
			 +" "+"鐘舵��"+List.get(value).getStatus()
			 +" "+"褰撳墠鍒嗛厤cpu"+List.get(value).getCupid());
	return 1;
	 }
	 else {
		//setSuspended(true);
		 return -1;
	}

	
}
	@Override
	public ArrayList<ResultModel> runProcess() {
		// TODO Auto-generated method stub
		return null;
	}
}
