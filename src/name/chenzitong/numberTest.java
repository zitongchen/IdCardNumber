package name.chenzitong;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.junit.Test;

public class numberTest {
	

	public static int  idCarTest() throws FileNotFoundException, IOException{
		Random random=new Random();
		POIFSFileSystem fs=new POIFSFileSystem(new FileInputStream(".\\source\\idcar.xls"));
		HSSFWorkbook wb=new HSSFWorkbook(fs);
		HSSFSheet sheet=wb.getSheetAt(0);
		
		HSSFRow row=sheet.getRow(random.nextInt(sheet.getLastRowNum()));
		
		HSSFCell cell=row.getCell(0);
		
		int number=(int)cell.getNumericCellValue();
		return number;
		
	}
}
