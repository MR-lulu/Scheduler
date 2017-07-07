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
				System.out.println("闃熷垪"+aLinkedList.get(i1));
			}
			System.out.println("褰撳墠灏辩华闃熷垪鏁扮洰"+num+"寰幆浣嶆暟"+i+"褰撳墠鏃堕棿"+nowtime+"褰撳墠杩愯");
			boolean v=true;
			
			for(int i2=0;i2<3&&v;i2++)
			{
				
				setready(nowtime, List);
				value=(int) aLinkedList.get(0);
			if(List.get(value).getStartTime()==0&&List.get(value).getStartTime()!=List.get(value).getPcb().getArriveTime())//濡傛灉鏄涓�娆¤繍琛岃缃叾杩愯鏃堕棿
			{
				System.out.println("绗�"+List.get(value).getPid()+"涓紑濮�----------");
				tPcb.setStartTime(nowtime);
			}
			else {
				tPcb.setStartTime(List.get(value).getStartTime());
				tResultModel.setStartTime(List.get(value).getStartTime());
			}
			sequence.add(String.valueOf(List.get(value).getPid()));
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
			if(tPcb.getNeedTime()==0)//濡傛灉浠诲姟缁撴潫璁剧疆鍏剁粨鏉熸椂闂村拰璁剧疆鐘舵�佷负缁撴潫
			{
				tPcb.setFinishTime(nowtime+1);
				tResultModel.setFinishTime(nowtime+1);
				tPcb.setStatus(2);
				tResultModel.setStatus(2);
				tResultModel.setTurnaroundTime(tPcb.getFinishTime()-tPcb.getArriveTime());
				tResultModel.setRturnaroundTime((tPcb.getFinishTime()-tPcb.getArriveTime())/tPcb.getServiceTime());
				//System.out.println("绗�"+value+"涓粨鏉�");
				aLinkedList.remove(0);end--;
				//System.out.println("闃熷垪0鏄�  "+aLinkedList.get(0));
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
			 System.out.println("鍚嶅瓧"+List.get(value).getPid()+" "+"浼樺厛绾�"+List.get(value).getPcb().getPriority()+" "+"鍒拌揪鏃堕棿"+List.get(value).getPcb().getArriveTime()
					 +" "+"寮�濮嬫椂闂�"+List.get(value).getStartTime()
					 +" "+"闇�瑕佹椂闂�"+List.get(value).getNeedTime()
					 +" "+"鏈嶅姟鏃堕棿"+List.get(value).getServerTime()
					 +" "+"瀹屾垚鏃堕棿"+List.get(value).getFinishTime()
					 +" "+"鐘舵��"+List.get(value).getPcb().getStatus());
		    // System.out.println("浼樺厛绾�"+List.get(i).getPcb().getPriority());
		     //System.out.println("鍒拌揪鏃堕棿"+List.get(i).getPcb().getArriveTime());
		     //System.out.println("寮�濮嬫椂闂�"+List.get(i).getStartTime());
		     //System.out.println("闇�瑕佹椂闂�"+List.get(i).getPcb().getNeedTime());
		     //System.out.println("鏈嶅姟鏃堕棿"+List.get(i).getPcb().getServiceTime());
		     //System.out.println("瀹屾垚鏃堕棿"+List.get(i).getFinishTime());
		     //System.out.println("鐘舵��"+List.get(i).getPcb().getStatus());
		
	
			}
			
			if(aLinkedList.size()>=1&&v)
			{
				System.out.println("缃崲"+aLinkedList.size());
			aLinkedList.addLast(aLinkedList.getFirst());
			aLinkedList.removeFirst();
			}
			
		}
		if(aLinkedList.size()==0)
		{ try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		nowtime++;
			
		}
	}
}
public int setready(float nowtime,ObservableList<ResultModel> List)
{
	//System.out.println("===========褰撳墠灏辩华闃熷垪=============");
	int num=0;
	for(int i1=0;i1<List.size();i1++)
	{
		PCB tPcb=new PCB();
		ResultModel tResultModel=new ResultModel();
		//System.out.println("姣旇緝1   "+List.get(i1).getPcb().getArriveTime()+" "+nowtime);
		//System.out.println("姣旇緝2   "+List.get(i1).getPcb().getStatus()+" "+3);
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
			//System.out.println("鍚嶅瓧"+List.get(i1).getPid()+" "+i1);
			//System.out.println("鐘舵��"+List.get(i1).getPcb().getStatus());
		   // System.out.println("浼樺厛绾�"+List.get(i1).getPcb().getPriority());
		    
		}
		
		//float min=this.List.get(i).getServiceTime();
	}
	System.out.println("==============================");
	return num;
}
public void retready(float nowtime,ObservableList<ResultModel> List) {
	//System.out.println("===========褰撳墠灏辩华闃熷垪=============");
		//int num=0;
		for(int i1=0;i1<List.size();i1++)
		{
			PCB tPcb=new PCB();
			ResultModel tResultModel=new ResultModel();
			//System.out.println("姣旇緝1   "+List.get(i1).getPcb().getArriveTime()+" "+nowtime);
			//System.out.println("姣旇緝2   "+List.get(i1).getPcb().getStatus()+" "+3);
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
				//System.out.println("鍚嶅瓧"+List.get(i1).getPid()+" "+i1);
				//System.out.println("鐘舵��"+List.get(i1).getPcb().getStatus());
			   // System.out.println("浼樺厛绾�"+List.get(i1).getPcb().getPriority());
			    
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
	
	System.out.println("<<<<<<<<<<<<<<<鎵�鏈夎繘绋嬫儏鍐�>>>>>>>>>>>>>>>>>>>>");
	for(int i1=0;i1<List.size();i1++)
	{
		 System.out.println("鍚嶅瓧"+List.get(i1).getPid()+" "+"浼樺厛绾�"+List.get(i1).getPriority()+" "+"鍒拌揪鏃堕棿"+List.get(i1).getArriveTime()
				 +" "+"寮�濮嬫椂闂�"+List.get(i1).getStartTime()
				 +" "+"闇�瑕佹椂闂�"+List.get(i1).getNeedTime()
				 +" "+"鏈嶅姟鏃堕棿"+List.get(i1).getServerTime()
				 +" "+"瀹屾垚鏃堕棿"+List.get(i1).getFinishTime()
				 +" "+"鐘舵��"+List.get(i1).getPcb().getStatus());
	}
	System.out.println("<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>");
}
	
}
