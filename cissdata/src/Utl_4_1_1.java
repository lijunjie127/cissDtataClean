import java.awt.Component;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;
//2016年8月31日09:57:24
//在myeclipse界面可以实现了
//打包成可运行jar包，也可以运行，修改了上个版本输出到文件是乱码的问题
//将中间输出全部特殊字符，输出到文件，解决了上个版本，特殊字符输出到命令窗口有问号的情况
//查重
public class Utl_4_1_1 {
	public static void main(String [] args) throws IOException {
		long time1 =System.currentTimeMillis();
		String filePath = null;//待处理数据文件路径
		String configureFilePath = null;//配置文件路径
		String _outPutPath = null;//文件输出路径开头
		String finalOutputPath = null;//最终的输出文件的路径
		String fileTitle_ = null;//接收要处理的厂商信息，输出文件名称的最前面部分
		String outPutPath = null;//文件输出路径
		BufferedReader br = null;//数据文件
		BufferedReader br_c = null;//配置文件
		BufferedReader br_o = null;//上一步输出的文件
		BufferedWriter writer = null; //输出到文件
		BufferedWriter writer0 = null; //输出到文件（备用）
		BufferedWriter writer1 = null; //输出到文件（备用）
//		PrintStream p = null;
		String str = "";
		Scanner in = new Scanner(System.in);
		while(true)
		{
			System.out.print("请在弹出窗口中选择待处理的数据文件....");
			filePath =  Utl_4_1_1.chooseFile();
			System.out.println("..完成！");
			
			System.out.print("请在弹出窗口中选择待配置文件....");
			configureFilePath = Utl_4_1_1.chooseFile();
			System.out.println("..完成！");
			
			System.out.print("请在弹出窗口中选择文件输出路径....");
			_outPutPath = Utl_4_1_1.chooseFilePath();
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
//		fileTitle_ = "1M数据2_";
		String outPath = _outPutPath;//请到这个目录下查看
		_outPutPath = _outPutPath + "\\" + fileTitle_ + "_";//补全outputpath路径
		finalOutputPath = _outPutPath;
//		_outPutPath = "E:\\ciss\\out\\1M数据2_";
//		outPutPath = "E:\\ciss\\out";
//		configureFilePath = "E:\\ciss\\configure.txt";
//		filePath = "C:\\Users\\Ciss\\Desktop\\1M数据2.txt";
//******************************************************************************************************分别找出所有字段的的特殊字符***************************************************
		br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath),"utf-8"));
		String [] tempa = br.readLine().split("\t");//读取文件第一行
	
		System.out.println("完整数据的目录结构如下：");
		for(int i = 0 ; i < tempa.length ; i ++)
		{
			System.out.println(i+1 + "." + tempa[i]);
		}
		System.out.println("");
//******************************************************************************************************分别找出所有字段的的特殊字符***********************************************************
		System.out.println("特殊字符处理，需要将可能会混淆的特殊字符（如:@和@、-和_、/和空格）放入配置文件" + configureFilePath + "中\n顶格写在对应行，字符之间不需要用其他字符隔开");
		System.out.println("请选择：\n1.配置文件已经配置完成，并且无需修改\n2.查看各个字段全部的特殊字符，核对或修改配置文件");
		int v_choose = in.nextInt();
		if(v_choose == 2)
		{
			outPutPath = _outPutPath + "所有字段的特殊字符.txt";
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outPutPath), "UTF-8"));
			System.out.print("正在将所有字段的特殊字符输出到" + outPutPath + "中\n....请耐心等待....");
			HashMap<Character,Integer> hsmap = new HashMap<Character,Integer>();
			for(int firstjishu = 0 ; firstjishu < tempa.length ; firstjishu ++)
			{
				if(tempa[firstjishu].equals("赛思编码"))
					continue;
				br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath),"utf-8"));
				hsmap = Utl_4_1_1.findOutSpecialChar(br, firstjishu);
//				System.out.println((firstjishu+1)+ tempa[firstjishu] + "列的全部特殊字符（除了字母、数字和汉字以外的其他字符）如下：");
				writer.write((firstjishu+1)+ tempa[firstjishu] + "列的全部特殊字符（除了字母、数字和汉字以外的其他字符）如下：\r\n");
				for (Map.Entry<Character, Integer> entry : hsmap.entrySet()) {
//		        	System.out.print(entry.getKey());
		        	writer.write(entry.getKey());
		        }
//				System.out.println("");
//				System.out.println("它们在该字段中出现的次数分别是：");
//				System.out.println(hsmap);
//				System.out.println("");
				writer.write("\r\n它们在该字段中出现的次数分别是：\r\n" + hsmap + "\r\n\r\n");
			}
//			System.out.println("请按照顺序将可能会混淆的特殊字符复制粘贴并保存在配置文件" + configureFilePath + "中的第1行，字符间不要用他字符连接（如：@@-_ /）");
			writer.write("请将可能会混淆的特殊字符复制粘贴并保存在配置文件" + configureFilePath + "中的第1行，字符间不要用他字符连接（如：@@-_ /）\r\n");
//			System.out.println("请将您要处理的字段的列数写在配置文件" + configureFilePath + "中的第2行，字符间用英文逗号隔开（如3,4则是选择了输出" +  tempa[2]+ "和" +  tempa[3]+ "同时相同的数据）");
			writer.write("请将您要处理的字段的列数写在配置文件" + configureFilePath + "中的第2行，字符间用英文逗号隔开（如3,4则是选择了输出" +  tempa[2]+ "和" +  tempa[3]+ "同时相同的数据）\r\n");
			writer.write("完成后请返回主程序，继续执行。。。");
			writer.close();
			br.close();
			
			System.out.println("完成\n请到" + outPutPath + "中查看所有字段的特殊字符，并按照提示，将特殊字符配置到配置文件" + configureFilePath + "中");
			System.out.println("注：在第一行可能混淆的特殊字符请不要输入#井号键\n完成后按任意键继续。。。。");
			@SuppressWarnings("unused")
			Object o_loop_flag = in.next();
		}
			
//********************************************************************************滤除部分字符拼接成的大字符串查重输出************************************************************************		
		{//主要方法
			br_c = new BufferedReader(new InputStreamReader(new FileInputStream(configureFilePath),"utf-8"));
			String [] stringSCArr1 = br_c.readLine().split("");//配置文件的第一行写相似的特殊字符
			String [] stringSCArr2 = br_c.readLine().split(",");//配置文件的第二行写选择的列数2,3,4,5,6
			int[] intArr = new int[stringSCArr2.length];//定义保存选择哪一列数据的整型数组2 3 4 5 6
			for(int i = 0; i < intArr.length; i++)
			{
				intArr[i] = Integer.parseInt(stringSCArr2[i]);//字符数组转换为整型数组
				finalOutputPath = finalOutputPath + intArr[i] + tempa[intArr[i]-1] + "_";
			}
			outPutPath = _outPutPath + "可能重复的数据(temp).txt";
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outPutPath), "UTF-8")); 
			br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath),"utf-8"));
			br.readLine();//跳过第一行
			System.out.println("正在对查询可能重复的数据");
			System.out.print("正在将结果输出到" + outPutPath + "中\n......请耐心等待！......");
			HashMap<String,StringBuilder> hsmap = new HashMap<String,StringBuilder>();//存储整个字段
			while ((str = br.readLine()) != null) 
			{
				String [] a = str.split("\t");
				if(a.length == tempa.length)
				{
					String orig = a[intArr[0]-1];//将第一个字段放入,intArr[0]是选择的第几列，需要在a数组中-1
					if(intArr.length > 1)//若intArr内只有选择了一列，则跳过次选项
					{
						for(int i = 1;i < intArr.length; i ++)
						{
							orig = orig + "##" + a[intArr[i]-1];//拼接字符串
						}
//						System.out.println("orig1:" + orig);
						//在这里滤除特殊字符
						for(int i = 0; i < stringSCArr1.length; i ++)
						{
							orig = orig.replace(stringSCArr1[i], "");
						}
//						System.out.println("orig2:" + orig);
					}
					if(hsmap.containsKey(orig))//统计整个字段
					 {
						StringBuilder val = hsmap.get(orig).append("##").append(str); 
						hsmap.put(orig, val);
					 }
					 else
					 {
						 hsmap.put(orig, new StringBuilder(str));//将赛思编码放进去
					 }
				}
			}
//			System.out.println(hsmap);
			Utl_4_1_1.sortOnValueRiseStringAndOutput(hsmap, writer);//按照数量（value）排序升序输出，输出到外部文件中
			writer.close();
			br.close();
			br_c.close();
			System.out.println("完成！\n");//输出到文件完成
		}//主要方法结束
//********************************************************************************可视化输出到文件************************************************************************
//		outPutPath = "C:\\Users\\Ciss\\Desktop\\out\\1M数据2_可能重复的数据(temp).txt";//临时写上的。。。。。。。。。。。。。。。。。。。。。。。。。。
//		finalOutputPath = "C:\\Users\\Ciss\\Desktop\\out\\aaa.txt";
		System.out.println("数据预处理完成！请选择：\n1.按照厂商是否全中文划分两个文件进行输出\n2.全部输出到一个文件里\n3.将上面的1和2都输出");
		int v = in.nextInt();
		if(v != 1)//选择的2或3，都需要完整输出
		{
			br_o = new BufferedReader(new InputStreamReader(new FileInputStream(outPutPath),"utf-8"));//读取上一步完成的文件,借用一下br_c
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(finalOutputPath + "可能重复的数据.txt"), "UTF-8")); 
			while ((str = br_o.readLine()) != null) 
			{
				String [] a_temp = str.split(" : ");
				writer.write(a_temp[0] + "\r\n");
				String [] aa = a_temp[1].split("##");
				for(int i = 0; i < aa.length; i ++)
				{
					writer.write(aa[i] + "\r\n");
				}
				writer.write("**********************************************************************************************\r\n");
			}
			br_o.close();
			writer.close();
		}
		if(v != 2)//选择的1或3，都需要分别输出
		{
			br_o = new BufferedReader(new InputStreamReader(new FileInputStream(outPutPath),"utf-8"));//读取上一步完成的文件
			String finalOutputPathAllChina = finalOutputPath + "可能重复的数据（全中文）.txt";
			String finalOutputPathAllOthers = finalOutputPath + "可能重复的数据（其他）.txt";
			writer0 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(finalOutputPathAllOthers), "UTF-8")); 
			writer1 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(finalOutputPathAllChina), "UTF-8")); //新建的输出全中文的
			while ((str = br_o.readLine()) != null) 
			{
//				System.out.println(str);
				String [] a_temp = str.split(" : ");
//				System.out.println(":" + a_temp[1]);//
//				p.println(a_temp[1]);
//				writer0.write(a_temp[0] + "\r\n");
				String [] aa = a_temp[1].split("##");
				StringBuilder strLastALast = new StringBuilder("");
				for(int i = 0; i < aa.length; i ++)
				{
					String [] lastA = aa[i].split("\t");
					strLastALast.append(lastA[lastA.length-1]);
				}
				str = strLastALast.toString().replaceAll("[\u4e00-\u9fa5]", "");//临时借用一下,将一组可能相同的数据的厂商全部连在一起
				if(str.length() > 0)//厂商有英文名或英文字符
				{
					for(int i = 0; i < aa.length; i ++)
					{
						writer0.write(aa[i] + "\r\n");//厂商名字是英文为主
					}
					writer0.write("**********************************************************************************************\r\n");
				}
				else
				{
					for(int i = 0; i < aa.length; i ++)
					{
						writer1.write(aa[i] + "\r\n");//厂商名字是全中文
					}
					writer1.write("**********************************************************************************************\r\n");
				}
			}
			writer0.close();
			writer1.close();
			br_o.close();
		}
//********************************************************************************可视化输出到文件************************************************************************
		long time2 =System.currentTimeMillis();
		System.out.println("用时" + (time2-time1)/1000 + "." + (time2-time1)%1000 + "秒，程序执行结束！请到" + outPath + "目录下查看输出结果");
		System.out.println("按任意键退出");;
		@SuppressWarnings("unused")
		Object ooo = in.next();
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
	public static HashMap<String, StringBuilder> sortOnValueRiseStringAndOutput(HashMap<String, StringBuilder> hsmap,BufferedWriter p) throws IOException
	{//key为字符串，value为该字符串所对应的赛思编码，这里按照value升序排序
		List<Map.Entry< String,StringBuilder>> list = new ArrayList<Map.Entry<String,StringBuilder>>(hsmap.entrySet());
		
		Collections. sort (
		     list , new Comparator<Map.Entry<String, StringBuilder>>() {
		           public int compare(Entry<String , StringBuilder> o1,Entry<String, StringBuilder> o2) {
		        	   int a1 = o1.getValue().toString().split("##").length;
		        	   int a2 = o2.getValue().toString().split("##").length;
		        	   return (a2 - a1);//降序排序
		          }
		     }
		);
		for(Map.Entry<String, StringBuilder> mapping:list ){
			 if(mapping.getValue().indexOf("##") != -1)
			 {
				 p.write(mapping.getKey()+ " : " +mapping.getValue());
				 p.write("\r\n");
			 }
		}
		return hsmap;
	}
	
	public static HashMap<Character,Integer> findOutSpecialChar(BufferedReader br,int v) throws IOException//处理哪个文件，第几列
	{//找出某字段的全部特殊字符
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
