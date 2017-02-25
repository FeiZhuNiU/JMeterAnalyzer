package perf.data;

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
public class BasicJMeterCSV {

    protected List<CSVRecord> records;
    protected String fileName;
    protected CSVParser parser;
    protected List<String> headers;

    public List<CSVRecord> getRecords() {
        return records;
    }

    public CSVParser getParser() {
        return parser;
    }

    public List<String> getHeaders() {
        return headers;
    }

    public String getFileName() {
        return fileName;
    }

    public BasicJMeterCSV(String dataFileName) {
        try {
            parser = CSVParser.parse(new File(dataFileName), Charset.defaultCharset(), CSVFormat.DEFAULT.withHeader());
            records = parser.getRecords();
        } catch (IOException e) {
            System.out.println("there is an error when reading file!");
            e.printStackTrace();
        }
        this.fileName = dataFileName;
        this.headers = new ArrayList<>(parser.getHeaderMap().keySet());
    }

    public void close() {
        try {
            parser.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
