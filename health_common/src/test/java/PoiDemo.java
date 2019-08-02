import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

/**
 * 读取excel文件
 */
public class PoiDemo {
    @Test
    public void fun01() throws Exception {
        //工作簿
        XSSFWorkbook workbook = new XSSFWorkbook("C:\\Users\\Administrator\\Desktop\\demo.xlsx");
        //工作表
        XSSFSheet sheet = workbook.getSheetAt(0);
        //遍历表,获取行
        for (Row row : sheet) {
            //遍历行,获取列
            for (Cell cell : row) {
                System.out.println(cell);
            }
            System.out.println("------------------------------");
        }
        workbook.close();
    }
}
