package perf.model;

import perf.io.XlsWriter;

import java.util.List;

/**
 * Created by Eric Yu on 2017/2/27.
 */
public class ReportCSV{
    protected List<String> headers;
    protected List<List<String>> data;

    public ReportCSV(List<String> headers, List<List<String>> data) {
        this.headers = headers;
        this.data = data;
    }

    public List<String> getHeaders() {
        return headers;
    }

    public List<List<String>> getData() {
        return data;
    }

    public void writeXls(String fileName, String sheetName) {
        XlsWriter.writeXls(headers, data, fileName, sheetName);
    }

}
