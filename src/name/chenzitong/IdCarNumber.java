package name.chenzitong;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Random;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.junit.Test;

public class IdCarNumber {
	
	
	/**
	 * @return String content
	 * @throws BiffException
	 * @throws IOException
	 * 此方法返回全国的地区码，通过EXCEL文件获取对应含省，市，县编号
	 * 身份证的地方编码是有固定规则的，无法通过随机合并获取6位的地区码，
	 * 所以通过读取EXCEL文件内的数据得到地区码
	 * jar包：jxl-2.6.9.jar
	 */
	private static Random random=new Random();
	private String provinceCityCounty() throws BiffException, IOException{
		
		//创建File实例
		File file = new File(".\\source\\idcar.xls");  
		
		InputStream in = new FileInputStream(file);  
		//在Java中操作EXCEL先创建一个Workbook实例
		Workbook workbook = Workbook.getWorkbook(in); 
		
		//获取第一张Sheet表  
		Sheet sheet = workbook.getSheet(0);  
		/* 
		我们既可能通过Sheet的名称来访问它，也可以通过下标来访问它。如果通过下标来访问的话，要注意的一点是下标从0开始，就像数组一样。  
		我们可以通过指定行和列得到指定的单元格Cell对象  
		*/
		
		//获取第一列第N行的数据对象,通过sheet.getRows()获取表格的行数
		Cell cell = sheet.getCell(0, random.nextInt(sheet.getRows()));  
		  
		 //然后再取每一个Cell中的值  
		String content = cell.getContents(); 
		in.close();
		return content;
}
	
	/**
	 * @return String 
	 * 通过随机数的形式生成身份证中的年月日
	 */
	private String birthday(){
		int year,month,day = 0;
		
		StringBuffer stringBuffer=new StringBuffer();
		//通过getInstance类方法获取Calendar实例
		Calendar calendar=Calendar.getInstance();
		//获取当前日期年份的int格式
		int YEAR=calendar.get(Calendar.YEAR);
		//假设身份证号码的年从1900年开始计算
		year=random.nextInt(YEAR-1900)+1900;
		
		//通过随机数获取月份，随机数因为包含头（0），不包含未所以输入的随机数值是13
		month=random.nextInt(12)+1;
		
		//处理闰年
		if(month==2&&year/4==0){
			day=random.nextInt(29)+1;
		}else if(month==2){
			day=random.nextInt(28)+1;
		}else if(month==4||month==6||month==9||month==11){
			day=random.nextInt(30)+1;
		}else{
			day=random.nextInt(31)+1;
		}
		
		stringBuffer.append(String.valueOf(year));
		//规格化月份
		if(month<10){
			stringBuffer.append("0");
		}
		
		stringBuffer.append(String.valueOf(month));
		//规格化日
		if(day<10){
			stringBuffer.append("0");
		}
		
		String birthday=stringBuffer.append(String.valueOf(day)).toString();
		
		return birthday;
	}
	
	
	/**
	 * @return randomNumber
	 * 身份证中倒数第四位到第二位为随机分配，所以可以使用随机分配
	 */
	private String randomNumber(){
		int number;
		
		StringBuffer stringBuffer=new StringBuffer();
		number=random.nextInt(1000);
		//格式化随机数
		if(number<10){
			stringBuffer.append("00");
		}else if(10<number&&number<100){
			stringBuffer.append("0");
		}
		String randomNumber=stringBuffer.append(String.valueOf(number)).toString();
		return randomNumber;
	}
	
	/**
	 * 	1、将前面的身份证号码17位数分别乘以不同的系数。从第一位到第十七位的系数分别为：7 9 10 5 8 4 2 1 6 3 7 9 10 5 8 4 2 ；
		2、将这17位数字和系数相乘的结果相加；
		3、用加出来和除以11，看余数是多少；
		4、余数只可能有0 1 2 3 4 5 6 7 8 9 10这11个数字。其分别对应的最后一位身份证的号码为1 0 X 9 8 7 6 5 4 3 2；
	 * @param frontNumber
	 * @return
	 */
	//最后一位校验位的计算
	private String checkNumber(String frontNumber){
		int[] number=new int[17]; 
		int[] array={7,9,10,5,8,4,2,1,6,3,7,9,10,5,8,4,2}; //1-17位的系数集合
		String[] check={"1","0","X","9","8","7","6","5","4","3","2"};//余数对应的最后以为号码的集合
		int sum=0,y;
		for(int i=0;i<17;i++){
			number[i]=Integer.valueOf(String.valueOf(frontNumber.charAt(i)));//利用Integer来转型，再次利用string来转型
			sum+=number[i]*array[i];
		}
		y=sum%11;//获得余数
		frontNumber=frontNumber+check[y];//拼接身份证号码
		
		return frontNumber;
	}

	public String carNumber() throws BiffException, IOException{
	
		StringBuffer stringBuffer=new StringBuffer();
		stringBuffer.append(String.valueOf(new numberTest().idCarTest()));
		stringBuffer.append(birthday());
		stringBuffer.append(randomNumber());
		String idCarNumber=checkNumber(stringBuffer.toString());
		return idCarNumber;
	}
	
	@Test
	public void carNumberTest() throws BiffException, IOException{
		for(int i=0;i<10;i++){
			System.out.println(carNumber());
		}
	}
	
	
}
