package algorithm;


import java.util.ArrayList;
import java.util.LinkedList;

import javafx.collections.ObservableList;
import model.PCB;
import model.ResultModel;

public class SJF extends Scheduler{
	
	public SJF(LinkedList<PCB> list)
	{
		//this.List =new LinkedList<PCB>();
		List = list;
	}
	public void sort()//�Ե���ʱ���������
	{
		for(int i=0;i<this.List.size();i++)
		{
			 for(int j=0;j<=i;j++)
			 {
				 if(this.List.get(i).getArriveTime()<this.List.get(j).getArriveTime())
				 {
					 PCB temp=new PCB();
					 //��ֵ���м�ֵ
					 temp.setArriveTime(this.List.get(i).getArriveTime());
					 temp.setFinishTime(this.List.get(i).getFinishTime());
					 temp.setPid(this.List.get(i).getPid());
					 temp.setNeedTime(this.List.get(i).getNeedTime());
					 temp.setStartTime(this.List.get(i).getStartTime());
					 //----------------
					 this.List.set(i, this.List.get(j));
					 //---------------
					 this.List.set(j, temp);
				 }
			 }
		}
	}

	public int findMin(int i,float endtime)//Ѱ���ѵ����������
	{
		
		int value=i;
		float min=10000;
		if(this.List.get(i).getArriveTime()>endtime)
		{
			return -1;
		}
		for(int i1=0;i1<this.List.size();i1++)
		{
			if(this.List.get(i1).getArriveTime()<endtime&&this.List.get(i1).getStatus()!=2)
			{
				if(min>this.List.get(i1).getNeedTime())
				{
					min=this.List.get(i1).getNeedTime();
					value=i1;
				}
			}
			//float min=this.List.get(i).getServiceTime();
		}
		return value;
	}
	public ArrayList<ResultModel> runProcess()//����
	{
		PCB t=new PCB();
		float endtime=0;
		int value=0;
		 //��ֵ���м�ֵ
		 t.setArriveTime(this.List.get(0).getArriveTime());
		 t.setFinishTime(this.List.get(0).getNeedTime()+this.List.get(0).getArriveTime());
		 endtime=t.getFinishTime();
		 t.setPid(this.List.get(0).getPid());
		 t.setNeedTime(0);
		 t.setServiceTime(this.List.get(0).getNeedTime());
		 t.setStatus(2);//��ɵ���������Ϊ2��������Ϊ0�����е�Ϊ1
		 t.setStartTime(this.List.get(0).getArriveTime());
		this.List.set(0, t);
		for(int i=1;i<this.List.size();i++)
		{
			PCB t1=new PCB();
			value=findMin(i,endtime);
			System.out.println("����˳��"+value);
			if(value==-1)
			{
				t1.setArriveTime(this.List.get(i).getArriveTime());
				 t1.setFinishTime(endtime+(this.List.get(i).getArriveTime()-endtime)+this.List.get(i).getNeedTime());
				 endtime=endtime+(this.List.get(i).getArriveTime()-endtime)+this.List.get(i).getNeedTime();
				 t1.setPid(this.List.get(i).getPid());
				 t1.setNeedTime(0);
				 t1.setStartTime(endtime+(this.List.get(i).getArriveTime()-endtime));
				 t1.setServiceTime(this.List.get(i).getNeedTime());
				 t1.setStatus(2);
				 this.List.set(i, t1);
			}
			else if(value>=1)
			{
				t1.setArriveTime(this.List.get(value).getArriveTime());
				t1.setStartTime(endtime);
				 t1.setFinishTime(endtime+this.List.get(value).getNeedTime());
				 endtime=endtime+this.List.get(value).getNeedTime();
				 t1.setPid(this.List.get(value).getPid());
				 t1.setNeedTime(0);
				 
				 t1.setServiceTime(this.List.get(value).getNeedTime());
				 t1.setStatus(2);
				 this.List.set(value, t1);
				
			}
			
		}
		LinkedList<PCB> a=new LinkedList<PCB>();
		ArrayList<ResultModel> b=new ArrayList<ResultModel>();
		a=this.List;
		for(int i=0;i<a.size();i++)//�������ʱ����������
		{
			 for(int j=0;j<=i;j++)
			 {
				 if(a.get(i).getFinishTime()<a.get(j).getFinishTime())
				 {
					 PCB temp=new PCB();
					 //��ֵ���м�ֵ
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
	public void output()//���ڲ���������
	{
		
		for(int i1=0;i1<this.List.size();i1++)
		{
		     System.out.println("����"+this.List.get(i1).getPid()+" "+i1);
		     
		     System.out.println("����ʱ��"+this.List.get(i1).getArriveTime());
		     System.out.println("��Ҫʱ��"+this.List.get(i1).getNeedTime());
		     System.out.println("����ʱ��"+this.List.get(i1).getServiceTime());
		     System.out.println("���ʱ��"+this.List.get(i1).getFinishTime());
		}
	}
	@Override
	public void dynamicRun(ObservableList<ResultModel> List) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
}
