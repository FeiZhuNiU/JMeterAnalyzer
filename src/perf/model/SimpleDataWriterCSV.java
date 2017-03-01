package perf.model;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eric Yu on 2017/2/23.
 */
public class SimpleDataWriterCSV extends BasicCSV {

    protected List<String> headers;
    protected String fileName;

    public String getFileName() {
        return fileName;
    }

    public List<String> getHeaders() {
        return headers;
    }

    public SimpleDataWriterCSV(String dataFileName) {
        try {
            CSVParser parser = CSVParser.parse(new File(dataFileName), Charset.defaultCharset(), CSVFormat.DEFAULT.withHeader());
            this.records = parser.getRecords();
            this.fileName = new File(dataFileName).getName();
            this.headers = new ArrayList<>(parser.getHeaderMap().keySet());
            parser.close();
        } catch (IOException e) {
            System.out.println("there is an error when reading file!");
            e.printStackTrace();
        }
    }

}
