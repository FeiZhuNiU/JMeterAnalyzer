package perf.analyze;

import org.apache.commons.csv.CSVRecord;
import perf.data.BasicCSV;
import perf.data.SimpleDataHeader;

import java.util.List;

/**
 * Created by Eric Yu on 2017/2/24.
 */
public class SynthesisReport{
    private BasicCSV csv;
    public SynthesisReport(BasicCSV csv) {
        this.csv = csv;
    }

    public void filterByTime(long startSecond, long endSecond) {
        long startTimeStamp = Long.parseLong(csv.getRecords().get(0).get(SimpleDataHeader.TIMESTAMP.getKey()));
        long startOffset = startTimeStamp + startSecond * 1000;
        long endOffset = startTimeStamp + endSecond * 1000;
        int l = 0, r = csv.getRecords().size();
        int start, end;
        while (r > l + 1) {
            int mid = l + (r - l) / 2;
            long curTimeStamp = Long.parseLong(csv.getRecords().get(mid).get(SimpleDataHeader.TIMESTAMP.getKey()));
            if (curTimeStamp < startOffset) {
                l = mid;
            } else {
                r = mid;
            }
        }
        start = r;
        l = 0;
        r = csv.getRecords().size();
        while (r > l + 1) {
            int mid = l + (r - l) / 2;
            long curTimeStamp = Long.parseLong(csv.getRecords().get(mid).get(SimpleDataHeader.TIMESTAMP.getKey()));
            if (curTimeStamp < endOffset) {
                l = mid;
            } else {
                r = mid;
            }
        }
        end = l;
        csv.setRecords(csv.getRecords().subList(start, end));
    }
}
