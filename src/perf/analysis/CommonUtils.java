package perf.analysis;

import org.apache.commons.csv.CSVRecord;
import org.apache.commons.math3.stat.descriptive.moment.Mean;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;
import org.apache.commons.math3.stat.descriptive.rank.Max;
import org.apache.commons.math3.stat.descriptive.rank.Min;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Eric Yu on 2017/2/26.
 * yulin.jay@gmail.com
 */
public class CommonUtils {
    /**
     * make sure that vals are numbers
     *
     * @param records
     * @param header
     * @return
     */
    private static double[] getValueArray(List<CSVRecord> records, String header) {
        assert records != null;
        double[] data = new double[records.size()];
        for (int i = 0; i < records.size(); ++i) {
            data[i] = Double.parseDouble(records.get(i).get(header));
        }
        return data;
    }

    public static double avg(List<CSVRecord> records, String header) {
        double[] data = getValueArray(records, header);
        Mean mean = new Mean();
        return mean.evaluate(data, 0, data.length);
    }

    public static double getMinVal(List<CSVRecord> records, String header) {
        double[] data = getValueArray(records, header);
        Min min = new Min();
        return min.evaluate(data);
    }

    public static double getMaxVal(List<CSVRecord> records, String header) {
        double[] data = getValueArray(records, header);
        Max max = new Max();
        return max.evaluate(data);
    }

    public static double getSpecifiedPositionVal(List<CSVRecord> records, String header, int percent) {
        double[] data = getValueArray(records, header);
        Arrays.sort(data);
        return data[data.length * percent / 100];
    }

    public static double getStandardDeviation(List<CSVRecord> records, String header){
        double[] data = getValueArray(records, header);
        StandardDeviation sd = new StandardDeviation();
        return sd.evaluate(data);
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

    public static List<String> csvRecordtoList(CSVRecord record){
        List<String> ret = new ArrayList<>();
        Iterator<String> iterator = record.iterator();
        while(iterator.hasNext()){
            ret.add(iterator.next());
        }
        return ret;
    }
}
