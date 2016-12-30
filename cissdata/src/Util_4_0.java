import java.awt.Component;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Map.Entry;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;
//2016年8月23日10:40:54

public class Util_4_0 {
	public static void main(String [] args) throws IOException {
		String filePath = null;//待处理数据文件路径
		String configureFilePath = null;//配置文件路径
		String fileTitle_ = null;//接收要处理的厂商信息，输出文件名称的最前面部分
		String _outPutPath = null;//文件输出路径开头
		String outPutPath = null;//文件输出路径
		BufferedReader br = null;//数据文件
		BufferedReader br_c = null;//配置文件
		String str = "";
		Scanner in = new Scanner(System.in);
		while(true)
		{
			System.out.print("请在弹出窗口中选择待处理的数据文件....");
			filePath =  Util_4_0.chooseFile();
			System.out.println("..完成！");
			
			System.out.print("请在弹出窗口中选择待配置文件....");
			configureFilePath = Util_4_0.chooseFile();
			System.out.println("..完成！");
			
			System.out.print("请在弹出窗口中选择文件输出路径....");
			_outPutPath = Util_4_0.chooseFilePath();
			System.out.println("..完成！\n");
			
			System.out.println("待处理数据文件：" + filePath);
			System.out.println("配置文件：" + configureFilePath);
			System.out.println("文件输出路径：" + _outPutPath);
			
			String stra_temphaha [] = filePath.split("\\\\");//默认读取文件目录是\\分隔的
			fileTitle_ = stra_temphaha[stra_temphaha.length-1];//要最后一段
			fileTitle_ = fileTitle_.substring(0, fileTitle_.length()-4);//去掉最后.txt四个字符，得到名称
			
			System.out.println("\n您的选择：\n1.数据文件、配置文件、结果的输出路径选择均正确，继续执行\n2.我要重新选择");
			int v = in.nextInt();
			if(v == 1)
			{
				break;
			}
		}
		
		//测试时的默认值
//		_outPutPath = "E:\\ciss\\out\\718_1002_1_";
//		configureFilePath = "E:\\ciss\\configure.txt";
//		filePath = "C:\Users\Ciss\Desktop\718_1002_1.txt";
//******************************************************************************************************分别找出所有字段的的特殊字符***********************************************************
		System.out.println("输出含有不合法特殊字符的数据，需要将合法的特殊字符放入配置文件" + configureFilePath + "中");
		System.out.println("请选择：\n1.我已将合法特殊字符写入到配置文件中了\n2.查看各个字段的特殊字符");
		int v_choose = in.nextInt();
		br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath),"utf-8"));
		String [] tempa = br.readLine().split("\t");//读取文件第一行
		if(v_choose == 2)
		{
			HashMap<Character,Integer> hsmap = new HashMap<Character,Integer>();
			for(int firstjishu = 0 ; firstjishu < tempa.length ; firstjishu ++)
			{
				if(tempa[firstjishu].equals("赛思编码"))
					continue;
				br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath),"utf-8"));
				hsmap = Util_4_0.findOutSpecialChar(br, firstjishu);
				System.out.println((firstjishu+1)+ tempa[firstjishu] + "列的全部特殊字符（除了字母、数字和汉字以外的其他字符）如下，请将合法的特殊字符输入到配置文件中的第" + (firstjishu+1) + "行");
				for (Map.Entry<Character, Integer> entry : hsmap.entrySet()) {
		        	System.out.print(entry.getKey());
		        }
				System.out.println("");
			}
			System.out.println("请按照顺序将合法的特殊字符复制粘贴并保存在配置文件" + configureFilePath + "中\n注：第一行为赛思编码的特殊字符，可以为空。\n完成后按任意键继续....");
			@SuppressWarnings("unused")
			Object o_loop_flag = in.next();
		}
		
//******************************************************************************************************输出不完整数据记录***********************************************************		
		outPutPath =_outPutPath + "\\" + fileTitle_ + "_" + "不完整数据记录.txt";//例如C:\Users\Ciss\Documents\1M数据2_不完整数据记录.txt
		PrintStream p = new PrintStream(new FileOutputStream(new File(outPutPath)));
		br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath),"utf-8"));
//		String [] tempa = br.readLine().split("\t");//读取文件第一行
		int countAll = 0 , scount = 0;
		while ((str = br.readLine()) != null)
		{
			countAll ++;
			String [] a = str.split("\t");
			if(a.length != tempa.length)
			{
//				System.out.print("该条数据记录不完整，完整" + tempa.length + "个字段，该记录有" + a.length + "个字段:");
				p.print("该条数据记录不完整，完整" + tempa.length + "个字段，该记录有" + a.length + "个字段:");
//				System.out.println(str);
				p.println(str);
				scount ++;
			}
		}
		System.out.print("数据记录完整性检查结束，共" + countAll + "条数据，不完整数据记录" + scount + "条，完整数据记录" + (countAll - scount) + "条\n");
		p.println("共" + countAll + "条数据，以上是" + scount + "条不完整的数据记录");
		System.out.println("已将不完整数据记录输出到" + outPutPath + "中\n");
//*******************************************************************************************************输出不完整数据记录结束*************************************		
		System.out.println("完整数据的目录结构如下：");
		for(int i = 0 ; i < tempa.length ; i ++)
		{
			System.out.println(i+1 + "." + tempa[i]);
		}
		System.out.println("");
//********************************************************************************循环执行那3个方法************************************************************************		
		for(int v = 0; v < tempa.length; v ++)
		{
			if(tempa[v].equals("赛思编码"))
			{
				System.out.println("第" + (v+1) + "列：赛思编码，不做特殊处理。\n");
				continue;
			}
			
			{//执行第一个方法
				outPutPath =_outPutPath + "\\" + fileTitle_ + "_字段" + (v+1) + tempa[v] + "_" + "方法1统计并按数量升序排序.txt";
				p = new PrintStream(new FileOutputStream(new File(outPutPath)));
				br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath),"utf-8"));
				br.readLine();//跳过第一行
				System.out.println("正在对 " + tempa[v] + " 的数据进行统计并按数量升序排序");
				System.out.print("正在将结果输出到" + outPutPath + "中\n......请耐心等待！......");
				HashMap<String,Integer> hsmap = new HashMap<String,Integer>();//存储整个字段
				while ((str = br.readLine()) != null) 
				{
					String [] a = str.split("\t");
					if(a.length == tempa.length)
					{
						String orig = a[v];
						if(hsmap.containsKey(orig))//统计整个字段
						 {
							 int val = hsmap.get(orig)+1; 
							 hsmap.put(orig, val);
						 }
						 else
						 {
							 hsmap.put(orig, 1);
						 }
					}
				}
				Util_4_0.sortOnValueRiseAndOutput(hsmap, p);//按照数量（value）排序升序输出，输出到外部文件中
				System.out.println("完成！\n");//输出到文件完成
			}
			
			{//执行第二个方法
				outPutPath =_outPutPath + "\\" + fileTitle_ + "_字段" + (v+1) + tempa[v] + "_" + "方法2有不合法特殊字符的数据.txt";
				p = new PrintStream(new FileOutputStream(new File(outPutPath)));
				System.out.println("正在输出 " + tempa[v] + " 中带有不合法特殊字符的数据");
				System.out.print("正在将结果输出到" + outPutPath + "中\n......请耐心等待！......");
				br_c = new BufferedReader(new InputStreamReader(new FileInputStream(configureFilePath),"utf-8"));
				for(int i_temp = 0;i_temp < v ;i_temp ++)
					br_c.readLine();
				char[] charstrarr = br_c.readLine().toCharArray();
				String regex = "[a-zA-Z0-9\u4e00-\u9fa5]+";
//				for(Character s : charstrarr)
//				{
//					if("0".equals(s))//这里应该将s转换为字符串类型
//						System.out.println("选择了全选");
////						regex = "[A-Z0-9\u4e00-\u9fa5]+" ;
//				}
				br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath),"utf-8"));
				br.readLine();
				boolean hasSflag = false;//是否有含有非法特殊字符的数据
				while ((str = br.readLine()) != null) 
				{
					String [] a = str.split("\t");
					if(a.length == tempa.length)
					{
						String orig = a[v];
						String newValue = orig.replaceAll(regex, "");//去除所有的数字和字母
						
						for(Character s : charstrarr)
						{
							newValue = newValue.replace(String.valueOf(s), "");
						}
						if(newValue.length() < 1)
						{
							continue;
						}
						if(!newValue.matches(regex))
						{
//							System.out.println("赛思编码：" + a[0] + "； 原数据：" + orig + "； 不合法的特殊字符：" + newValue);
							p.println("赛思编码：" + a[0] + "； 原数据：" + orig + "； 不合法的特殊字符：" + newValue);
							hasSflag = true;
						}
					}
				}
				if(!hasSflag)//如果没有此类数据
				{
					p.println("0条数据\n");
				}

				System.out.println("完成！\n");//输出到文件完成
			}
			
			{//执行第三个方法
				System.out.println("注：这里的特殊字符指的是非（字母、数字、点和汉字）的字符");
				outPutPath =_outPutPath + "\\" + fileTitle_ + "_字段" + (v+1) + tempa[v] + "_" + "方法3去掉特殊字符后相同去掉前不同的数据.txt";
				p = new PrintStream(new FileOutputStream(new File(outPutPath)));
				br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath),"utf-8"));
				br.readLine();//跳过第一行
				System.out.print("正在将结果输出到" + outPutPath + "中\n......请耐心等待！......");
				HashMap<String,Integer> hsmap = new HashMap<String,Integer>();//存储整个字段
				while ((str = br.readLine()) != null) 
				{
					String [] a = str.split("\t");
					if(a.length == tempa.length)
					{
						String orig = a[v];
						if(hsmap.containsKey(orig))//统计整个字段
						 {
							 int val = hsmap.get(orig)+1; 
							 hsmap.put(orig, val);
						 }
						 else
						 {
							 hsmap.put(orig, 1);
						 }
					}
				}
				HashMap<String,ArrayList<String>> hash = new HashMap<String,ArrayList<String>> ();
				boolean hasDiffFlag = false; 
				int diff_i = 0;//记录去掉特殊字符前不同，去掉后相同的数据，数量
				for (Map.Entry<String, Integer> entry : hsmap.entrySet()) 
				{
					String orig = entry.getKey();
					String newValue = orig;
					newValue = orig.replaceAll("[^0-9a-zA-Z\\.\u4e00-\u9fa5]", ""); //去除字符串中非字母、数字、点和汉字的部分，汉字\u4e00-\u9fa5
					ArrayList<String> list = hash.get(newValue);//list和map建立联系
					if (list == null)
					{
						list = new ArrayList<String>();
						list.add(orig);
						hash.put(newValue, list);
					}
					else
					{
						boolean isDiff = false;
						for (String s : list)
						{
							if (!s.equals(orig))
							{
//								System.out.println("diff_" + ++diff_i + ":" + orig + "\t" + s);
								p.println("diff_" + ++diff_i + ":" + orig + "\t" + s);//这里diff_i自动加一，两次输出，只加一次就好
								isDiff = true;
								hasDiffFlag = true;
							}
						}
						
						if (isDiff)
						{
							list.add(orig);
						}
					}
				}
				if(hasDiffFlag)
				{
//					System.out.println("注：每个diff都代表一个或多个数据，这里将相同的结果只输出一次。\n");
					p.println("注：每个diff都代表一个或多个数据，这里将相同的结果只输出一次。\r\n");
				}
				else
				{
					System.out.println("没有找到此类数据。");
					p.println("没有找到此类数据。");
				}
				System.out.println("完成！\n");//输出到文件完成
			}//执行第3个方法结束
		}
//********************************************************************************循环执行那3个方法结束************************************************************************	
		System.out.println("程序执行结束！请到" + _outPutPath + "中查看输出结果");
		System.exit(0);
	}
	
	
	
	
		
	
	//以下为普通方法
	public static String chooseFilePath()//选择文件目录
	{
		JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);//设置只能选择目录
		chooser.setDialogTitle("请选择文件...");
		chooser.setApproveButtonText("确定");
		int returnVal = chooser.showOpenDialog(chooser);
		if(returnVal == JFileChooser.APPROVE_OPTION) 
		{
			String selectPath =chooser.getSelectedFile().getPath() ;
//			System.out.println ( "你选择的文件输出目录是：" + selectPath );
			return selectPath;
		}
		else
		{
			return "选择目录不合法";
		}
	}
	
	public static String chooseFile()//选择文件
	{
		String filePath = null;
		int result_JFC = 0;
		JFileChooser fileChooser = new JFileChooser();
		FileSystemView fsv = FileSystemView.getFileSystemView();  //注意了，这里重要的一句
//		System.out.println("请在小窗口中选择待处理的数据文件，当前默认为桌面路径：" + fsv.getHomeDirectory());//得到桌面路径
		fileChooser.setCurrentDirectory(fsv.getHomeDirectory());
		fileChooser.setDialogTitle("请选择文件...");
		fileChooser.setApproveButtonText("确定");
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		Component chatFrame = null;
		result_JFC = fileChooser.showOpenDialog(chatFrame);//弹出选择文件的界面
		if (JFileChooser.APPROVE_OPTION == result_JFC) 
		{
			filePath = fileChooser.getSelectedFile().getPath();
		}
		return filePath;
	}
	
	public static HashMap<String, Integer> sortOnValueRiseAndOutput(HashMap<String, Integer> hsmap,PrintStream p)
	{//key为字符串，value为该字符串出现的次数，这里按照value升序排序
		List<Map.Entry< String,Integer>> list = new ArrayList<Map.Entry<String, Integer>>(hsmap.entrySet());
		
		Collections. sort (
		     list , new Comparator<Map.Entry<String, Integer >>() {//升序排序
		           public int compare(Entry<String , Integer > o1,Entry<String, Integer > o2) {
		               return o1.getValue().compareTo(o2.getValue());
		          }
		     }
		);
		for(Map.Entry<String, Integer> mapping:list ){
//			     System.out.println(mapping.getKey()+ " : " +mapping.getValue());
		     p.println(mapping.getKey()+ " : " +mapping.getValue());
		}
		return hsmap;
	}
	
	public static HashMap<Character,Integer> findOutSpecialChar(BufferedReader br,int v) throws IOException//处理哪个文件，第几列
	{
		String str = "";
		String [] tempa = br.readLine().split("\t");
		HashMap<Character,Integer> hsmap = new HashMap<Character,Integer>();//存储特殊字符
		while ((str = br.readLine()) != null) 
		{
			String [] a = str.split("\t");
			
			if(a.length == tempa.length)
			{
				String orig = a[v];
				String regex = "[a-zA-Z0-9\u4e00-\u9fa5]+";//字母数字汉字
				String newValue = orig.replaceAll(regex, "");
				for (int i = 0; i < newValue.length(); i++) 
				{
					 char c = newValue.charAt(i);
					 if(hsmap.containsKey(c))
					 {
						 int val = hsmap.get(c)+1; 
						 hsmap.put(c, val);
					 }
					 else
					 {
						 hsmap.put(c, 1);
					 }
				}
			}
		}
		return hsmap;
	}
}
