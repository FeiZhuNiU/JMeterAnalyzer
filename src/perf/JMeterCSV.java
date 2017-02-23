package perf;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.Assert;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;

/**
 * Created by Eric Yu on 2017/2/23.
 */
public class JMeterCSV {
    private List<CSVRecord> records;
    private String fileName;
    private CSVParser parser;

    public JMeterCSV(String dataFileName) {
        try {
            parser = CSVParser.parse(new File(dataFileName), Charset.defaultCharset(), CSVFormat.DEFAULT.withHeader());
            records = parser.getRecords();
        } catch (IOException e) {
            System.out.println("there is an error when reading file!");
            e.printStackTrace();
        }
        this.fileName = dataFileName;
    }

    public void writeToXls(String xlsFileName){
        rawData2Xls(fileName, xlsFileName);
    }

    private static void rawData2Xls(String rawDataFileName, String xlsFileName) {
        Assert.assertNotNull(rawDataFileName);
        Assert.assertNotNull(xlsFileName);
        try {
            CSVParser parser = CSVParser.parse(new File(rawDataFileName), Charset.defaultCharset(), CSVFormat.DEFAULT.withHeader());
            List<CSVRecord> records = parser.getRecords();

            parser.getHeaderMap().entrySet().forEach(stringIntegerEntry -> System.out.println(stringIntegerEntry.getKey()));
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet1 = workbook.createSheet("50");
            /**
             * add head row
             */
            sheet1.createRow(0);
            final int[] col_cnt = {0};
            parser.getHeaderMap().entrySet().forEach(stringIntegerEntry -> {
                HSSFCell cell = sheet1.getRow(0).createCell(col_cnt[0]++);
                cell.setCellValue(stringIntegerEntry.getKey());
            });
            /**
             * add data rows
             */
            final int[] row_cnt = {1};
            records.forEach(record -> {
                col_cnt[0] = 0;
                HSSFRow row = sheet1.createRow(row_cnt[0]++);
                parser.getHeaderMap().entrySet().forEach(stringIntegerEntry -> {
                    HSSFCell cell = row.createCell(col_cnt[0]++);
                    cell.setCellValue(record.get(stringIntegerEntry.getKey()));
                });
            });

            File file = new File(xlsFileName);
            workbook.write(file);
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<String> getHeaders(){
        return new ArrayList<>(parser.getHeaderMap().keySet());
    }
//    public Map<String, Long> getAvgResponseTime(String labelName, List<CSVRecord> csvRecords){
//        Map<String, Long>
//        records.forEach(record->{
//
//        });
//
//    }

    public void close(){
        try {
            parser.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
