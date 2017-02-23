package perf.out;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

/**
 * Created by Eric Yu on 2017/2/24.
 */

public class XlsWriter {


    public static void writeXls(List<String> headers, List<List<String>> records, String dstFileName, String sheetName) {
        assert headers != null;
        assert records != null;

        try {
            File file = new File(dstFileName);
            HSSFWorkbook workbook;
            if (file.exists()) {
                workbook = new HSSFWorkbook(new FileInputStream(file));
            } else {
                workbook = new HSSFWorkbook();
            }
            HSSFSheet sheet = workbook.createSheet(sheetName);
            // create rows
            int records_num = records.size();
            for(int i = 0; i <= records_num ; ++i){
                sheet.createRow(i);
            }
            /**
             * write head row
             */
            int col = 0, row = 0;
            for (String header : headers) {
                HSSFCell cell = sheet.getRow(0).createCell(col++);
                cell.setCellValue(header);
            }
            /**
             * write originData rows
             */
            for (List<String> record : records) {
                col = 0;
                HSSFRow cur_row = sheet.getRow(records.indexOf(record) + 1);
                for (String str : record) {
                    HSSFCell cell = cur_row.createCell(col++);
                    cell.setCellValue(str);
                }
            }
            workbook.write(file);
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
