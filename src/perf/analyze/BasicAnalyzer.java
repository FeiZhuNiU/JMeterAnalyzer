package perf.analyze;

import org.apache.commons.csv.CSVRecord;
import org.apache.commons.math3.stat.descriptive.moment.Mean;
import perf.data.BasicCSV;
import perf.data.SimpleDataHeader;

import java.util.List;

/**
 * Created by 麟 on 2017/2/24.
 */
public class BasicAnalyzer {
    protected BasicCSV csv;
    protected List<CSVRecord> records;

    public BasicAnalyzer(BasicCSV csv) {
        this.csv = csv;
        this.records = csv.getRecords();
    }
    /**
     * make sure that vals are nubmers
     *
     * @param records
     * @param header
     * @return
     */
    public double avg(List<CSVRecord> records, String header) {
        double[] data = new double[records.size()];
        for (int i = 0; i < records.size(); ++i) {
            data[i] = Double.parseDouble(records.get(i).get(header));
        }
        Mean mean = new Mean();
        return mean.evaluate(data, 0, data.length);
    }

    public double avg(List<CSVRecord> records, SimpleDataHeader header) {
        return avg(records, header.getKey());
    }

    public double avg(SimpleDataHeader header) {
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
