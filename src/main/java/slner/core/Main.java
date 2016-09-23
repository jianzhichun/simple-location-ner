package slner.core;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class Main {
	public static void main(String[] args) {
		SimpleLocationRecognizer simpleLocationRecognizer = SimpleLocationRecognizer.getInstance();
		try {
			long t = System.currentTimeMillis();
			Workbook readbook = Workbook.getWorkbook(new File("./src/main/resources/客户发货地址.xls"));
			WritableWorkbook writebook = Workbook.createWorkbook(new File("./src/main/resources/客户发货地址output.xls"));
            Sheet readsheet = readbook.getSheet(0);
            WritableSheet writesheet = writebook.createSheet("output", 0);
            writesheet.addCell(new Label(0, 0, readsheet.getCell(0, 0).getContents()));
            writesheet.addCell(new Label(1, 0, "province"));
            writesheet.addCell(new Label(2, 0, "city"));
            writesheet.addCell(new Label(3, 0, "area"));
            int rownum = readsheet.getRows();
            
            for(int i = 1; i<rownum;i++){
            	Cell cell1 = readsheet.getCell(0, i);
                String str = cell1.getContents();
                writesheet.addCell(new Label(0, i, str));
                Map<String,List<String>> rs = simpleLocationRecognizer.recognizeLocationFormat(str);
                for(Entry<String, List<String>> entry:rs.entrySet()){
                	try{
	                	switch(entry.getKey()){
	                	case "province":
							writesheet.addCell(new Label(1, i, StringUtils.join(entry.getValue(),"|")));
	                		break;
	                	case "city":
							writesheet.addCell(new Label(2, i, StringUtils.join(entry.getValue(),"|")));
	                		break;
	                	case "area":
							writesheet.addCell(new Label(3, i, StringUtils.join(entry.getValue(),"|")));
	                		break;
	                	}
                	}catch(Exception e){
                		throw new RuntimeException(e);
                	}
                }
            }
            
            writebook.write();
            writebook.close();
            readbook.close();
            System.out.println(System.currentTimeMillis()-t);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
}
