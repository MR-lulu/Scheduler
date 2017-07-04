package algorithm;

import java.util.ArrayList;
import java.util.LinkedList;

import javafx.collections.ObservableList;
import model.PCB;
import model.ResultModel;

public class RR extends Scheduler {
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
		for(int i=0;i<List.size();i++)
		{
			ResultModel tResultModel=new ResultModel();
			PCB tPcb=new PCB();
			out(List);
			num=setready(nowtime, List);
			System.out.println("��ǰ����������Ŀ"+num+"ѭ��λ��"+i+"��ǰʱ��"+nowtime);
			out(List);
			
			if(num>0)
			{
			if(List.get(i).getStatus()==0)
			{
				if(List.get(i).getStartTime()==0&&List.get(i).getStartTime()!=List.get(i).getPcb().getArriveTime())//����ǵ�һ����������������ʱ��
				{
					System.out.println("��"+List.get(i).getPid()+"����ʼ----------");
					tPcb.setStartTime(nowtime);
				}
				else {
					tPcb.setStartTime(List.get(i).getStartTime());
					tResultModel.setStartTime(List.get(i).getStartTime());
				}			
				tPcb.setArriveTime(List.get(i).getPcb().getArriveTime());
				tPcb.setPid(List.get(i).getPid());
				tPcb.setNeedTime(List.get(i).getPcb().getNeedTime()-1);
				tPcb.setServiceTime(List.get(i).getPcb().getServiceTime()+1);
				
				tPcb.setPriority(List.get(i).getPcb().getPriority());
				tPcb.setStatus(1);
				
				tResultModel.setPid(tPcb.getPid());
				tResultModel.setStartTime(tPcb.getStartTime());
				tResultModel.setNeedTime(tPcb.getNeedTime());
				tResultModel.setServerTime(tPcb.getServiceTime());
				tResultModel.setArriveTime(tPcb.getArriveTime());
				tResultModel.setPriority(tPcb.getPriority());
				tResultModel.setStatus(1);
				tResultModel.setArriveTime(tPcb.getArriveTime());
				if(tPcb.getNeedTime()==0)//�������������������ʱ�������״̬Ϊ����
				{
					tPcb.setFinishTime(nowtime+1);
					tResultModel.setFinishTime(nowtime+1);
					tPcb.setStatus(2);
					tResultModel.setStatus(2);
					tResultModel.setTurnaroundTime(tPcb.getFinishTime()-tPcb.getArriveTime());
					tResultModel.setRturnaroundTime((tPcb.getFinishTime()-tPcb.getArriveTime())/tPcb.getServiceTime());
					//System.out.println("��"+value+"������");
					end--;
				}
				tResultModel.setPcb(tPcb);
				List.set(i,tResultModel);
				nowtime++;
				sequence.add(String.valueOf(List.get(i).getPid()));	
				 try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
				 System.out.println(i);
				 System.out.println("����"+List.get(i).getPid()+" "+"���ȼ�"+List.get(i).getPcb().getPriority()+" "+"����ʱ��"+List.get(i).getPcb().getArriveTime()
						 +" "+"��ʼʱ��"+List.get(i).getStartTime()
						 +" "+"��Ҫʱ��"+List.get(i).getPcb().getNeedTime()
						 +" "+"����ʱ��"+List.get(i).getPcb().getServiceTime()
						 +" "+"���ʱ��"+List.get(i).getFinishTime()
						 +" "+"״̬"+List.get(i).getPcb().getStatus());
			    // System.out.println("���ȼ�"+List.get(i).getPcb().getPriority());
			     //System.out.println("����ʱ��"+List.get(i).getPcb().getArriveTime());
			     //System.out.println("��ʼʱ��"+List.get(i).getStartTime());
			     //System.out.println("��Ҫʱ��"+List.get(i).getPcb().getNeedTime());
			     //System.out.println("����ʱ��"+List.get(i).getPcb().getServiceTime());
			     //System.out.println("���ʱ��"+List.get(i).getFinishTime());
			     //System.out.println("״̬"+List.get(i).getPcb().getStatus());
			}
			else {
				continue;
			}
		}
			else if (num==0) {
				sequence.add("-1");	
				nowtime++;
				 try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
				 break;
			}
	}
	}
}
public int setready(float nowtime,ObservableList<ResultModel> List)
{
	System.out.println("===========��ǰ��������=============");
	int num=0;
	for(int i1=0;i1<List.size();i1++)
	{
		PCB tPcb=new PCB();
		ResultModel tResultModel=new ResultModel();
		if(List.get(i1).getPcb().getArriveTime()<=nowtime&&(List.get(i1).getPcb().getStatus()!=2))
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
			num++;
			System.out.println("����"+List.get(i1).getPid()+" "+i1);
			System.out.println("״̬"+List.get(i1).getPcb().getStatus());
		    System.out.println("���ȼ�"+List.get(i1).getPcb().getPriority());
		    
		}
		
		//float min=this.List.get(i).getServiceTime();
	}
	System.out.println("==============================");
	return num;
}
public void sort(ObservableList<ResultModel> List) {
	for(int i=0;i<List.size();i++)
	{
		 for(int j=0;j<=i;j++)
		 {
			 if(List.get(i).getPcb().getArriveTime()<=List.get(j).getPcb().getArriveTime())
			 {
				 PCB temp=new PCB();
				 //��ֵ���м�ֵ
				 
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
					tResultModel.setArriveTime(temp.getArriveTime());
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
	System.out.println("<<<<<<<<<<<<<<<���н������>>>>>>>>>>>>>>>>>>>>");
	for(int i1=0;i1<List.size();i1++)
	{
		 System.out.println("����"+List.get(i1).getPid()+" "+"���ȼ�"+List.get(i1).getPriority()+" "+"����ʱ��"+List.get(i1).getArriveTime()
				 +" "+"��ʼʱ��"+List.get(i1).getStartTime()
				 +" "+"��Ҫʱ��"+List.get(i1).getNeedTime()
				 +" "+"����ʱ��"+List.get(i1).getServerTime()
				 +" "+"���ʱ��"+List.get(i1).getFinishTime()
				 +" "+"״̬"+List.get(i1).getPcb().getStatus());
	}
	System.out.println("<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>");
}
	
}