package algorithm;

import java.util.ArrayList;
import java.util.LinkedList;

import javafx.collections.ObservableList;
import model.PCB;
import model.ResultModel;

public class RR extends Scheduler {
	private LinkedList aLinkedList=new LinkedList();
public RR(LinkedList<PCB> list)
{
	//this.List =new LinkedList<PCB>();
	List = list;
	sequence=new ArrayList<String>();
}
@Override

public void dynamicRun(ObservableList<ResultModel> List)
{
	sort(List);
	int nowtime=0;
	int end=List.size();
	int num=0;
	
	while(end>0)
	{
		setready(nowtime, List);
		retready(nowtime, List);
		for(int i=0;i<aLinkedList.size();i++)
		{
			
			num=setready(nowtime, List);
			retready(nowtime, List);
			ResultModel tResultModel=new ResultModel();
			PCB tPcb=new PCB();
			//out(List);
			out(List);
			System.out.println(i);
			//out(List);
			int value=0;
			
			for(int i1=0;i1<aLinkedList.size();i1++)
			{
				System.out.println("队列"+aLinkedList.get(i1));
			}
			System.out.println("当前就绪队列数目"+num+"循环位数"+i+"当前时间"+nowtime+"当前运行");
			boolean v=true;
			
			for(int i2=0;i2<3&&v;i2++)
			{
				
				setready(nowtime, List);
				value=(int) aLinkedList.get(0);
			if(List.get(value).getStartTime()==0&&List.get(value).getStartTime()!=List.get(value).getPcb().getArriveTime())//如果是第一次运行设置其运行时间
			{
				System.out.println("第"+List.get(value).getPid()+"个开始----------");
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
			tPcb.setPriority(List.get(value).getPcb().getPriority());
			tPcb.setStatus(1);
			
			tResultModel.setPid(tPcb.getPid());
			tResultModel.setStartTime(tPcb.getStartTime());
			tResultModel.setNeedTime(tPcb.getNeedTime());
			tResultModel.setServerTime(tPcb.getServiceTime());
			tResultModel.setArriveTime(tPcb.getArriveTime());
			tResultModel.setPriority(tPcb.getPriority());
			tResultModel.setStatus(1);
			tResultModel.setArriveTime(tPcb.getArriveTime());
			if(tPcb.getNeedTime()==0)//如果任务结束设置其结束时间和设置状态为结束
			{
				tPcb.setFinishTime(nowtime+1);
				tResultModel.setFinishTime(nowtime+1);
				tPcb.setStatus(2);
				tResultModel.setStatus(2);
				tResultModel.setTurnaroundTime(tPcb.getFinishTime()-tPcb.getArriveTime());
				tResultModel.setRturnaroundTime((tPcb.getFinishTime()-tPcb.getArriveTime())/tPcb.getServiceTime());
				//System.out.println("第"+value+"个结束");
				aLinkedList.remove(0);end--;
				//System.out.println("队列0是  "+aLinkedList.get(0));
				//break;
				v=false;
			}
			tResultModel.setPcb(tPcb);
			List.set(value,tResultModel);
			nowtime++;
			//sequence.add(String.valueOf(List.get(0).getPid()));	
			 try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			 System.out.println(0);
			 System.out.println("名字"+List.get(value).getPid()+" "+"优先级"+List.get(value).getPcb().getPriority()+" "+"到达时间"+List.get(value).getPcb().getArriveTime()
					 +" "+"开始时间"+List.get(value).getStartTime()
					 +" "+"需要时间"+List.get(value).getNeedTime()
					 +" "+"服务时间"+List.get(value).getServerTime()
					 +" "+"完成时间"+List.get(value).getFinishTime()
					 +" "+"状态"+List.get(value).getPcb().getStatus());
		    // System.out.println("优先级"+List.get(i).getPcb().getPriority());
		     //System.out.println("到达时间"+List.get(i).getPcb().getArriveTime());
		     //System.out.println("开始时间"+List.get(i).getStartTime());
		     //System.out.println("需要时间"+List.get(i).getPcb().getNeedTime());
		     //System.out.println("服务时间"+List.get(i).getPcb().getServiceTime());
		     //System.out.println("完成时间"+List.get(i).getFinishTime());
		     //System.out.println("状态"+List.get(i).getPcb().getStatus());
		
	
			}
			sequence.add(String.valueOf(List.get(value).getPid()));
			if(aLinkedList.size()>=1&&v)
			{
				System.out.println("置换"+aLinkedList.size());
			aLinkedList.addLast(aLinkedList.getFirst());
			aLinkedList.removeFirst();
			}
			
		}
	}
}
public int setready(float nowtime,ObservableList<ResultModel> List)
{
	//System.out.println("===========当前就绪队列=============");
	int num=0;
	for(int i1=0;i1<List.size();i1++)
	{
		PCB tPcb=new PCB();
		ResultModel tResultModel=new ResultModel();
		//System.out.println("比较1   "+List.get(i1).getPcb().getArriveTime()+" "+nowtime);
		//System.out.println("比较2   "+List.get(i1).getPcb().getStatus()+" "+3);
		if(List.get(i1).getPcb().getArriveTime()<=nowtime&&(List.get(i1).getPcb().getStatus()==3))
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
			tResultModel.setStatus(0);
			tResultModel.setPid(List.get(i1).getPcb().getPid());
			tResultModel.setStartTime(List.get(i1).getStartTime());
			tResultModel.setArriveTime(tPcb.getArriveTime());
			tResultModel.setFinishTime(List.get(i1).getPcb().getFinishTime());
			List.set(i1, tResultModel);
			aLinkedList.add(i1);
			num++;
			//System.out.println("名字"+List.get(i1).getPid()+" "+i1);
			//System.out.println("状态"+List.get(i1).getPcb().getStatus());
		   // System.out.println("优先级"+List.get(i1).getPcb().getPriority());
		    
		}
		
		//float min=this.List.get(i).getServiceTime();
	}
	System.out.println("==============================");
	return num;
}
public void retready(float nowtime,ObservableList<ResultModel> List) {
	//System.out.println("===========当前就绪队列=============");
		//int num=0;
		for(int i1=0;i1<List.size();i1++)
		{
			PCB tPcb=new PCB();
			ResultModel tResultModel=new ResultModel();
			//System.out.println("比较1   "+List.get(i1).getPcb().getArriveTime()+" "+nowtime);
			//System.out.println("比较2   "+List.get(i1).getPcb().getStatus()+" "+3);
			if(List.get(i1).getPcb().getArriveTime()<=nowtime&&(List.get(i1).getPcb().getStatus()==1))
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
				tResultModel.setStatus(0);
				tResultModel.setPid(List.get(i1).getPcb().getPid());
				tResultModel.setStartTime(List.get(i1).getStartTime());
				tResultModel.setArriveTime(tPcb.getArriveTime());
				tResultModel.setFinishTime(List.get(i1).getPcb().getFinishTime());
				List.set(i1, tResultModel);
				//aLinkedList.add(i1);
				//num++;
				//System.out.println("名字"+List.get(i1).getPid()+" "+i1);
				//System.out.println("状态"+List.get(i1).getPcb().getStatus());
			   // System.out.println("优先级"+List.get(i1).getPcb().getPriority());
			    
			}
			
			//float min=this.List.get(i).getServiceTime();
		}
		System.out.println("==============================");
		//return num;
}
public void sort(ObservableList<ResultModel> List) {
	for(int i=0;i<List.size();i++)
	{
		 for(int j=0;j<=i;j++)
		 {
			 if(List.get(i).getPcb().getArriveTime()<List.get(j).getPcb().getArriveTime())
			 {
				 PCB temp=new PCB();
				 //赋值给中间值
				 
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
public void out(ObservableList<ResultModel> List)
{
	
	System.out.println("<<<<<<<<<<<<<<<所有进程情况>>>>>>>>>>>>>>>>>>>>");
	for(int i1=0;i1<List.size();i1++)
	{
		 System.out.println("名字"+List.get(i1).getPid()+" "+"优先级"+List.get(i1).getPriority()+" "+"到达时间"+List.get(i1).getArriveTime()
				 +" "+"开始时间"+List.get(i1).getStartTime()
				 +" "+"需要时间"+List.get(i1).getNeedTime()
				 +" "+"服务时间"+List.get(i1).getServerTime()
				 +" "+"完成时间"+List.get(i1).getFinishTime()
				 +" "+"状态"+List.get(i1).getPcb().getStatus());
	}
	System.out.println("<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>");
}
	
}
