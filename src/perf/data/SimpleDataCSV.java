package perf.data;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;

/**
 * Created by Eric Yu on 2017/2/23.
 */
public class SimpleDataCSV extends JMeterCSV {

    protected List<String> headers;

    public List<String> getHeaders() {
        return headers;
    }

    public SimpleDataCSV(String dataFileName) {
        super(dataFileName);
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

        public String getKey() {
            return key;
        }
    }
}
