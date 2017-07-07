package misc;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

import model.PCB;

/**
 * ClassName: InitializionFormFile 
 * @Description: ���ļ��г�ʼ�����̶���
 * @author Mr_blue
 * @date 2017��6��28��
 */
public class InitializationFormFile {
	private static int pid=1000;
	private static int status=3;//0������1���У�2���

	public static LinkedList<PCB> getLinkedListFormFile(File file){
		LinkedList<PCB> list = new LinkedList<PCB>();
		
		try {
			Scanner scanner = new Scanner(file);
			scanner.nextLine();
			while (scanner.hasNext()) {
				PCB pcb = new PCB();
				pcb.setStatus(status);
				pcb.setPid(pid);
				pcb.setArriveTime(scanner.nextFloat());
				pcb.setNeedTime(scanner.nextFloat());
				pcb.setPriority(scanner.nextInt());
				pid+=1;
				list.add(pcb);
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return list;
	};
}
