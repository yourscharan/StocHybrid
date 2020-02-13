package utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelfileUtils {
	
	Workbook wb;
	//this constructor will be called automatically when we create an
	//object to this class so we r placing this code in constructor
	
	
   public  ExcelfileUtils() throws Exception{
	   
	   //to open the excel file in read mode
	   FileInputStream fis = new FileInputStream("D:\\nagjt\\myprograms\\StocAccHybrid_Maven\\testinput\\InputSheet.xlsx");
	   //we can write Xssfworkbook wb = new Xssfworkbook also
	   //but Workbook is the main interface and xssworkbook is the class present in that interface
	   //so we r writing like this
	   wb = new XSSFWorkbook(fis);
	   
	   
   }
   
   
   //to get row count(used rows in excel sheet)
   public int rowCount(String sheetname){
	  return wb.getSheet(sheetname).getLastRowNum();
   }
   

   //to get no.of cols(header cols)
   public int colCount(String sheetname){
	   
	  return wb.getSheet(sheetname).getRow(0).getLastCellNum();
	   
   }
   
   //to get the data from a particualr sheet,particular row and particular col
   
   public String getData(String sheetname,int row,int column){
	   
	   String data=" ";
	   if(wb.getSheet(sheetname).getRow(row).getCell(column).getCellType()==Cell.CELL_TYPE_NUMERIC){
		   
		   //wb.getSheet(sheetname).getRow(row).getCell(column).getNumericCellValue();  getnumericcellvalue will return a double value 
		   //so we r type casting into Int
		   
		   int celldata=(int)wb.getSheet(sheetname).getRow(row).getCell(column).getNumericCellValue();
		   data=String.valueOf(celldata);//type casting integer valur to string type and assigning to data variable
		   
	   }
	   else{
		   
		 data=  wb.getSheet(sheetname).getRow(row).getCell(column).getStringCellValue();
	   }
	   
	   return data;   
	   
	   //by using if-else we r getting a Single result of "String" type only bcoz
	   //excel sheet may contain data in the form of sTring and int format
	   
   }
   
 
   public void setData(String sheetname,int row,int column,String status) throws IOException{
	   
	   Sheet sh=wb.getSheet(sheetname);
	   Row rownum=sh.getRow(row);
	   Cell cell=rownum.createCell(column);
	   cell.setCellValue(status);
	   if(status.equalsIgnoreCase("Pass")){
			
			CellStyle style=wb.createCellStyle();
			Font font=wb.createFont();
			font.setColor(IndexedColors.GREEN.getIndex());
			font.setBold(true);
			style.setFont(font);
			rownum.getCell(column).setCellStyle(style);			
		} else if(status.equalsIgnoreCase("Fail")){
			
			CellStyle style=wb.createCellStyle();
			Font font=wb.createFont();
			font.setColor(IndexedColors.RED.getIndex());
			font.setBold(true);
			style.setFont(font);
			rownum.getCell(column).setCellStyle(style);
		} else if(status.equalsIgnoreCase("Not Completed")){
			
			CellStyle style=wb.createCellStyle();
			Font font=wb.createFont();
			font.setColor(IndexedColors.BLUE.getIndex());
			font.setBold(true);
			style.setFont(font);
			rownum.getCell(column).setCellStyle(style);
			
			
		}
		FileOutputStream fos=new FileOutputStream("D:\\nagjt\\myprograms\\StocAccHybrid_Maven\\testoutput\\hybrid.xlsx");
		//System.getProperty("user.dir")+"\\TestOutput\\HybridOutput.xlsx
		wb.write(fos);
		fos.close();
	   }
	   

}
