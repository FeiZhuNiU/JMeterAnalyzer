package perf.data;

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
public class SimpleDataCSV extends BasicCSV {

    protected List<String> headers;
    protected String fileName;
    protected CSVParser parser;

    public CSVParser getParser() {
        return parser;
    }
    public String getFileName() {
        return fileName;
    }
    public List<String> getHeaders() {
        return headers;
    }

    public SimpleDataCSV(String dataFileName) {
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

    /**
     * Created by Eric Yu on 2017/2/24.
     */
    public enum Header {
        TIMESTAMP("timeStamp"),
        ELAPSED("elapsed"),
        LABEL("label"),
        RESPONSE_CODE("responseCode"),
        RESPONSE_MESSAGE("responseMessage"),
        THREAD_NAME("threadName"),
        DATA_TYPE("dataType"),
        SUCCESS("success"),
        FAILURE_MESSAGE("failureMessage"),
        BYTES("bytes"),
        SENT_BYTES("sentBytes"),
        GRP_THREADS("grpThreads"),
        ALL_THREADS("allThreads"),
        LATENCY("Latency"),
        IDLE_TIME("IdleTime"),
        CONNECT("Connect");

        private String key;

        Header(String key) {
            this.key = key;
        }

//        public String getKey() {
//            return key;
//        }

        @Override
        public String toString() {
            return key;
        }
    }
    public void close() {
        try {
            parser.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
