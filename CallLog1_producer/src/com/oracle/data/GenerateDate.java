package com.oracle.data;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;



public class GenerateDate implements Runnable{

	static List<String> callNumbers = null;//��ź���ļ���
	static HashMap<String,String> contact = null;//����������map
	static int index1;//�������1
	static int index2;//�������2
	static String callInfo;//��������Ϣ
	static String calledInfo;//��������Ϣ
	static String allInfo;//��������Ϣ+��������Ϣ
	static String callTime;//ͨ����ʼʱ��
	static String holdTime;//ͨ��ʱ��
	static StringBuffer buffer;//ƴ���ַ���
	
	@Override
	public void run() {
		while(true){
			BufferedWriter bw;
			//��ȡ�����к��룬����
			allInfo = getCallAndCalled();
			//��ȡͨ��ʱ��
			callTime = getCallTime("2018/01/01","2018/08/14");
			//��ȡͨ��ʱ��
			holdTime = getHoldTime();
			buffer = new StringBuffer();
			buffer.append(allInfo+",");
			buffer.append(callTime+",");
			buffer.append(holdTime);
			try {
				bw = new BufferedWriter(
						new OutputStreamWriter(
								new BufferedOutputStream(
										new FileOutputStream(new File("/home/bduser/datas/a.txt"),true)
										)
								,"UTF-8")
						);
				bw.write(buffer.toString()+"\n");
//				bw.newLine();
				bw.flush();
				Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}
	
	
	public static void main(String[] args) throws ParseException, IOException {
		//���õ绰����ļ���
		setNumbers();
		//����map������������Ķ�Ӧ��ϵ
		setContacts();
		new Thread(new GenerateDate()).start();
		
//		for(int i=0;i<50;i++){
//			System.out.println("************"+i);
//			//��ȡ�����к��룬����
//			allInfo = getCallAndCalled();
//			//��ȡͨ��ʱ��
//			callTime = getCallTime("2018/01/01","2018/08/14");
//			//��ȡͨ��ʱ��
//			holdTime = getHoldTime();
//			buffer = new StringBuffer();
//			buffer.append(allInfo+",");
//			buffer.append(callTime+",");
//			buffer.append(holdTime);
//			System.out.println(buffer.toString());
//			
//			
//			BufferedWriter bw = new BufferedWriter(
//					new OutputStreamWriter(
//							new BufferedOutputStream(
//									new FileOutputStream(new File("G:/test/b.txt"),true)
//									)
//							,"UTF-8")
//					);
//			bw.write(buffer.toString()+"\n");
//			bw.newLine();
//			bw.flush();
//		}
	}


	private static String getHoldTime() {
		int holdTime = (int)(Math.random()*7200);
		DecimalFormat format = new DecimalFormat("0000");
		return format.format(holdTime);
	}

	private static String getCallAndCalled() {
		
//		public Food(Snake snake) {
//			boolean flag;//�ж�ʳ��������
//			do {
//				flag=false;
//				foodx = (int)(Math.random()*30)*20;
//				foody = (int)(Math.random()*30)*20;
//				for (int i = 0; i < snake.getLeng(); i++) {
//					if(snake.getX()[i]==foodx&&snake.getY()[i]==foody){
//						flag=true;
//					}
//				}
//			} while (flag);
//		}
		
		index1 = (int)(Math.random()*50);
		index2 = (int)(Math.random()*50);
		callInfo = null;
		calledInfo = null;
		if(index1==index2){
//			System.out.println("��ͬ��");
			getCallAndCalled();
		}
//		System.out.println(index1);
//		System.out.println(callNumbers.get(index1)+":"+contact.get(callNumbers.get(index1)));
		callInfo = callNumbers.get(index1) + "," + contact.get(callNumbers.get(index1));
//		System.out.println(index2);
//		System.out.println(callNumbers.get(index2)+":"+contact.get(callNumbers.get(index2)));
		calledInfo = callNumbers.get(index2) + "," + contact.get(callNumbers.get(index2));
		return callInfo + "," + calledInfo;
		
	}

	private static String getCallTime(String begin,String end) {
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
//		System.out.println("��ǰʱ��:"+format.format(new Date()));

		Date beginDate = null;
		Date endDate = null;
		
		try {
			beginDate = format.parse(begin);
			endDate = format.parse(end);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long beginTime = beginDate.getTime();
		long endTime = endDate.getTime();
//		System.out.println("��ʼʱ��"+beginTime);
//		System.out.println("����ʱ��"+endTime);
		
//		System.out.println("***********");
		long callTime = beginTime + (long) ((endTime - beginTime) * Math.random());
//		System.out.println("ͨ��ʱ��" + callTime);
		SimpleDateFormat format2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		return format2.format(new Date(callTime));
//		System.out.println("ͨ��ʱ��" + format2.format(new Date(callTime)));	
		
		
	}

	private static void setNumbers() {
		callNumbers = new ArrayList<String>();
		callNumbers.add("15981479062");
		callNumbers.add("15316341413");
		callNumbers.add("14314258091");
		callNumbers.add("13904361845");
		callNumbers.add("15919677639");
		callNumbers.add("15078413749");
		callNumbers.add("17053018400");
		callNumbers.add("16468595837");
		callNumbers.add("13166378867");
		callNumbers.add("13293095206");
		callNumbers.add("14573171378");
		callNumbers.add("16623116314");
		callNumbers.add("17821858084");
		callNumbers.add("13951118474");
		callNumbers.add("14997080261");
		callNumbers.add("15089653745");
		callNumbers.add("13818189989");
		callNumbers.add("13851619605");
		callNumbers.add("13487226992");
		callNumbers.add("16231879059");
		callNumbers.add("15226572441");
		callNumbers.add("16090585997");
		callNumbers.add("16007362382");
		callNumbers.add("18856521012");
		callNumbers.add("15744215596");
		callNumbers.add("17108606082");
		callNumbers.add("18386038643");
		callNumbers.add("17104409094");
		callNumbers.add("16057563899");
		callNumbers.add("16355214218");
		callNumbers.add("13091075826");
		callNumbers.add("15206273865");
		callNumbers.add("13813310663");
		callNumbers.add("13950894030");
		callNumbers.add("15775138038");
		callNumbers.add("18058990235");
		callNumbers.add("18351784674");
		callNumbers.add("18336790768");
		callNumbers.add("15412930519");
		callNumbers.add("15182955507");
		callNumbers.add("15891344571");
		callNumbers.add("13655002941");
		callNumbers.add("18664015893");
		callNumbers.add("16806624375");
		callNumbers.add("18113380181");
		callNumbers.add("14106885810");
		callNumbers.add("14604235595");
		callNumbers.add("15787655905");
		callNumbers.add("18186717979");
		callNumbers.add("14382184011");		
	}
	
	private static void setContacts() {
		contact = new HashMap<String,String>();
		contact.put("15981479062","�ܴ���");
		contact.put("15316341413","�ܶ���");
		contact.put("14314258091","������");
		contact.put("13904361845","������");
		contact.put("15919677639","������");
		contact.put("15078413749","������");
		contact.put("17053018400","������");
		contact.put("16468595837","�ܰ���");
		contact.put("13166378867","����");
		contact.put("13293095206","��С��");
		contact.put("14573171378","�½��");
		contact.put("16623116314","̸���");
		contact.put("17821858084","�ܾ���");
		contact.put("13951118474","֣����");
		contact.put("14997080261","����");
		contact.put("15089653745","�����");
		contact.put("13818189989","֣�ҽ�");
		contact.put("13851619605","֣��");
		contact.put("13487226992","����");
		contact.put("16231879059","������");
		contact.put("15226572441","������");
		contact.put("16090585997","����");
		contact.put("16007362382","�´��");
		contact.put("18856521012","�¶���");
		contact.put("15744215596","������");
		contact.put("17108606082","���ı�");
		contact.put("18386038643","�����");
		contact.put("17104409094","����");
		contact.put("16057563899","�����");
		contact.put("16355214218","������");
		contact.put("13091075826","���߱�");
		contact.put("15206273865","����");
		contact.put("13813310663","������");
		contact.put("13950894030","�����");
		contact.put("15775138038","������");
		contact.put("18058990235","��ٻٻ");
		contact.put("18351784674","���ҽ�");
		contact.put("18336790768","����¶");
		contact.put("15412930519","��Ԫɺ");
		contact.put("15182955507","�ܺ���");
		contact.put("15891344571","������");
		contact.put("13655002941","��ٻ");
		contact.put("18664015893","�ȹ���");
		contact.put("16806624375","����");
		contact.put("18113380181","����");
		contact.put("14106885810","�ײӲ�");
		contact.put("14604235595","��ӽ");
		contact.put("15787655905","������");
		contact.put("18186717979","�����");
		contact.put("14382184011","������");		
	}
	//������߼�
//	for (HashMap<String, String> hashMap : contacts) {
//	Set<String> set = hashMap.keySet();
//	for(Iterator<String> it = set.iterator();it.hasNext();){
//		String key = it.next();
//		System.out.println(key+contact.get(key));
//	}
//}
	
}
