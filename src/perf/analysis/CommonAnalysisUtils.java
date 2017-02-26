package perf.analysis;

import org.apache.commons.csv.CSVRecord;
import org.apache.commons.math3.stat.descriptive.moment.Mean;
import perf.data.SimpleDataCSV;

import java.util.List;

/**
 * Created by Eric Yu on 2017/2/26.
 * yulin.jay@gmail.com
 */
public class CommonAnalysisUtils {
    /**
     * make sure that vals are nubmers
     *
     * @param records
     * @param header
     * @return
     */
    public static double avg(List<CSVRecord> records, String header) {
        double[] data = new double[records.size()];
        for (int i = 0; i < records.size(); ++i) {
            data[i] = Double.parseDouble(records.get(i).get(header));
        }
        Mean mean = new Mean();
        return mean.evaluate(data, 0, data.length);
    }

    public static double avg(List<CSVRecord> records, SimpleDataCSV.Header header) {
        return avg(records, header.toString());
    }

    public static int count(List<CSVRecord> records, String header, String val) {
        int ret = 0;
        for (CSVRecord record : records) {
            if (record.get(header).equals(val)) {
                ret++;
            }
        }
        return ret;
    }
}
