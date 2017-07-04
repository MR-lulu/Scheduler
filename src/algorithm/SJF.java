package algorithm;


import java.util.ArrayList;
import java.util.LinkedList;

import javafx.collections.ObservableList;
import model.PCB;
import model.ResultModel;

public class SJF extends Scheduler{
	
	public SJF(LinkedList<PCB> list)
	{
		//this.list =new Linkedlist<PCB>();
		this.list = list;
	}
	public void sort()//对到达时间进行排序
	{
		for(int i=0;i<this.list.size();i++)
		{
			 for(int j=0;j<=i;j++)
			 {
				 if(this.list.get(i).getArriveTime()<this.list.get(j).getArriveTime())
				 {
					 PCB temp=new PCB();
					 //赋值给中间值
					 temp.setArriveTime(this.list.get(i).getArriveTime());
					 temp.setFinishTime(this.list.get(i).getFinishTime());
					 temp.setPid(this.list.get(i).getPid());
					 temp.setNeedTime(this.list.get(i).getNeedTime());
					 temp.setStartTime(this.list.get(i).getStartTime());
					 //----------------
					 this.list.set(i, this.list.get(j));
					 //---------------
					 this.list.set(j, temp);
				 }
			 }
		}
	}

	public int findMin(int i,float endtime)//寻找已到达的任务中
	{
		
		int value=i;
		float min=10000;
		if(this.list.get(i).getArriveTime()>endtime)
		{
			return -1;
		}
		for(int i1=0;i1<this.list.size();i1++)
		{
			if(this.list.get(i1).getArriveTime()<endtime&&this.list.get(i1).getStatus()!=2)
			{
				if(min>this.list.get(i1).getNeedTime())
				{
					min=this.list.get(i1).getNeedTime();
					value=i1;
				}
			}
			//float min=this.list.get(i).getServiceTime();
		}
		return value;
	}
	public ArrayList<ResultModel> runProcess()//运行
	{
		PCB t=new PCB();
		float endtime=0;
		int value=0;
		 //赋值给中间值
		 t.setArriveTime(this.list.get(0).getArriveTime());
		 t.setFinishTime(this.list.get(0).getNeedTime()+this.list.get(0).getArriveTime());
		 endtime=t.getFinishTime();
		 t.setPid(this.list.get(0).getPid());
		 t.setNeedTime(0);
		 t.setServiceTime(this.list.get(0).getNeedTime());
		 t.setStatus(2);//完成的任务设置为2，就绪的为0，运行的为1
		 t.setStartTime(this.list.get(0).getArriveTime());
		this.list.set(0, t);
		for(int i=1;i<this.list.size();i++)
		{
			PCB t1=new PCB();
			value=findMin(i,endtime);
			System.out.println("运行顺序"+value);
			if(value==-1)
			{
				t1.setArriveTime(this.list.get(i).getArriveTime());
				 t1.setFinishTime(endtime+(this.list.get(i).getArriveTime()-endtime)+this.list.get(i).getNeedTime());
				 endtime=endtime+(this.list.get(i).getArriveTime()-endtime)+this.list.get(i).getNeedTime();
				 t1.setPid(this.list.get(i).getPid());
				 t1.setNeedTime(0);
				 t1.setStartTime(endtime+(this.list.get(i).getArriveTime()-endtime));
				 t1.setServiceTime(this.list.get(i).getNeedTime());
				 t1.setStatus(2);
				 this.list.set(i, t1);
			}
			else if(value>=1)
			{
				t1.setArriveTime(this.list.get(value).getArriveTime());
				t1.setStartTime(endtime);
				 t1.setFinishTime(endtime+this.list.get(value).getNeedTime());
				 endtime=endtime+this.list.get(value).getNeedTime();
				 t1.setPid(this.list.get(value).getPid());
				 t1.setNeedTime(0);
				 
				 t1.setServiceTime(this.list.get(value).getNeedTime());
				 t1.setStatus(2);
				 this.list.set(value, t1);
				
			}
			
		}
		LinkedList<PCB> a=new LinkedList<PCB>();
		ArrayList<ResultModel> b=new ArrayList<ResultModel>();
		a=this.list;
		for(int i=0;i<a.size();i++)//按照完成时间重新排序
		{
			 for(int j=0;j<=i;j++)
			 {
				 if(a.get(i).getFinishTime()<a.get(j).getFinishTime())
				 {
					 PCB temp=new PCB();
					 //赋值给中间值
					 temp.setArriveTime(a.get(i).getArriveTime());
					 temp.setFinishTime(a.get(i).getFinishTime());
					 temp.setPid(a.get(i).getPid());
					 temp.setServiceTime(a.get(i).getServiceTime());
					 temp.setNeedTime(a.get(i).getNeedTime());
					 temp.setStartTime(a.get(i).getStartTime());
					 //----------------
					 a.set(i,a.get(j));
					 //---------------
					 a.set(j, temp);
				 }
			 }
		}
		for(int i=0;i<a.size();i++)
		{
			ResultModel t2=new ResultModel();
			t2.setPcb(a.get(i));
			t2.setPid(a.get(i).getPid());
			t2.setStartTime(a.get(i).getStartTime());
			t2.setFinishTime(a.get(i).getFinishTime());
			t2.setTurnaroundTime(a.get(i).getFinishTime()-a.get(i).getArriveTime());
			t2.setRturnaroundTime((a.get(i).getFinishTime()-a.get(i).getArriveTime())/a.get(i).getServiceTime());
			b.add(t2);
		}
		return b;
		
	}
	public void output()//用于测试输出结果
	{
		
		for(int i1=0;i1<this.list.size();i1++)
		{
		     System.out.println("名字"+this.list.get(i1).getPid()+" "+i1);
		     
		     System.out.println("到达时间"+this.list.get(i1).getArriveTime());
		     System.out.println("需要时间"+this.list.get(i1).getNeedTime());
		     System.out.println("服务时间"+this.list.get(i1).getServiceTime());
		     System.out.println("完成时间"+this.list.get(i1).getFinishTime());
		}
	}
	@Override
	public void dynamicRun(ObservableList<ResultModel> List) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
}
