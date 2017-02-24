package perf;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.math3.stat.descriptive.moment.Mean;
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
public class BasicCSV {

    protected List<CSVRecord> records;
    protected String fileName;
    protected CSVParser parser;

    public List<CSVRecord> getRecords() {
        return records;
    }

    public String getFileName() {
        return fileName;
    }

    public BasicCSV(String dataFileName) {
        try {
            parser = CSVParser.parse(new File(dataFileName), Charset.defaultCharset(), CSVFormat.DEFAULT.withHeader());
            records = parser.getRecords();
        } catch (IOException e) {
            System.out.println("there is an error when reading file!");
            e.printStackTrace();
        }
        this.fileName = dataFileName;
    }

    public void filterByTime(long startSecond, long endSecond) {
        long startTimeStamp = Long.parseLong(records.get(0).get(JMeterCSVHeader.TIMESTAMP.getKey()));
        long startOffset = startTimeStamp + startSecond * 1000;
        long endOffset = startTimeStamp + endSecond * 1000;
        int l = 0, r = records.size();
        int start, end;
        while (r > l+1) {
            int mid = l + (r - l) / 2;
            long curTimeStamp = Long.parseLong(records.get(mid).get(JMeterCSVHeader.TIMESTAMP.getKey()));
            if (curTimeStamp < startOffset){
                l = mid;
            }else{
                r = mid;
            }
        }
        start = r;
        l = 0;
        r = records.size();
        while (r > l+1) {
            int mid = l + (r - l) / 2;
            long curTimeStamp = Long.parseLong(records.get(mid).get(JMeterCSVHeader.TIMESTAMP.getKey()));
            if (curTimeStamp < endOffset){
                l = mid;
            }else{
                r = mid;
            }
        }
        end = l;
        records = records.subList(start,end);
    }

    public void writeToXls(String xlsFileName, String sheetName) {
        rawData2Xls(fileName, xlsFileName, sheetName);
    }

    public void writeToXls(String xlsFileName) {
        rawData2Xls(fileName, xlsFileName, "sheet1");
    }

    private void rawData2Xls(String rawDataFileName, String xlsFileName, String sheetName) {
        Assert.assertNotNull(rawDataFileName);
        Assert.assertNotNull(xlsFileName);
        try {
            parser.getHeaderMap().entrySet().forEach(stringIntegerEntry -> System.out.println(stringIntegerEntry.getKey()));
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet1 = workbook.createSheet(sheetName);
            /**
             * write head row
             */
            sheet1.createRow(0);
            final int[] col_cnt = {0};
            parser.getHeaderMap().entrySet().forEach(stringIntegerEntry -> {
                HSSFCell cell = sheet1.getRow(0).createCell(col_cnt[0]++);
                cell.setCellValue(stringIntegerEntry.getKey());
            });
            /**
             * write data rows
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

    public List<String> getHeaders() {
        return new ArrayList<>(parser.getHeaderMap().keySet());
    }

    public List<CSVRecord> getRecordsByHeader(String header, String value) {
        List<CSVRecord> ret = new ArrayList<>();
        records.forEach(record -> {
            if (record.get(header).equals(value)) {
                ret.add(record);
            }
        });
        return ret;
    }

    public List<CSVRecord> getRecordsByHeader(JMeterCSVHeader header, String value) {
        return getRecordsByHeader(header.getKey(), value);
    }

    public void close() {
        try {
            parser.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public double avg(List<CSVRecord> records, String header) {
        double[] data = new double[records.size()];
        for (int i = 0; i < records.size(); ++i) {
            data[i] = Double.parseDouble(records.get(i).get(header));
        }
        Mean mean = new Mean();
        return mean.evaluate(data, 0, data.length);
    }

    public double avg(List<CSVRecord> records, JMeterCSVHeader header) {
        return avg(records, header.getKey());
    }

    public double avg(JMeterCSVHeader header) {
        return avg(records, header.getKey());
    }

    public int count(List<CSVRecord> records, String header, String val) {
        int ret = 0;
        for (CSVRecord record : records) {
            if (record.get(header).equals(val)) {
                ret++;
            }
        }
        return ret;
    }

    public int count(String header, String val) {
        return count(records, header, val);
    }


}
