import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 写入Excel文件
 */
public class PoiDemo02 {
    @Test
    public void fun02() throws IOException {
        //创建工作簿
        XSSFWorkbook workbook = new XSSFWorkbook();
        //创建工作表
        XSSFSheet sheet = workbook.createSheet("学生名单");
        //创建行
        XSSFRow row = sheet.createRow(0);
        row.createCell(0).setCellValue("姓名");
        row.createCell(1).setCellValue("年龄");
        row.createCell(2).setCellValue("性别");

        XSSFRow row2 = sheet.createRow(1);
        row2.createCell(0).setCellValue("张三");
        row2.createCell(1).setCellValue("15");
        row2.createCell(2).setCellValue("男");

        XSSFRow row3 = sheet.createRow(2);
        row3.createCell(0).setCellValue("李四");
        row3.createCell(1).setCellValue("16");
        row3.createCell(2).setCellValue("女");

        workbook.write(new FileOutputStream("C:\\Users\\Administrator\\Desktop\\demo02.xlsx"));

    }
}
