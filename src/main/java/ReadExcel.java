import com.google.common.collect.Lists;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.google.common.collect.Lists;

import java.io.*;
import java.util.*;

public class ReadExcel {
    private String vNo;
    private String ilKod;
    public String getvNo() {
        return vNo;
    }

    public void setvNo(String vNo) {
        this.vNo = vNo;
    }

    public String getIlKod() {
        return ilKod;
    }

    public void setIlKod(String ilKod) {
        this.ilKod = ilKod;
    }


    public List<String> readExcel(String path) throws IOException {

        File excelFile = new File(path);
        FileInputStream fis = new FileInputStream(excelFile);

        List<String> vNoList = Lists.newArrayList();

        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        XSSFSheet sheet = workbook.getSheetAt(0);
        // we iterate on rows
        Iterator<Row> rowIt = sheet.iterator();

        /*if (rowIt.hasNext()) {
            rowIt.next();
        }*/
        while (rowIt.hasNext()) {
            Row row = rowIt.next();

            // iterate on cells for the current row
            Iterator<Cell> cellIterator = row.cellIterator();

            while (cellIterator.hasNext()) {
                Cell cell1 = cellIterator.next();
                cell1.setCellType(CellType.STRING);


                if (cell1 == null) {
                    continue;
                }else if(cell1.getStringCellValue().length() != 10){
                    continue;
                }
                else{
                    vNoList.add(cell1.getStringCellValue());
                }
            }
        }



        //workbook.close();
        fis.close();

        return vNoList;
    }


}






