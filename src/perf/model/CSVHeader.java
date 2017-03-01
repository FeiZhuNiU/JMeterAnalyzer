package perf.model;

/**
 * Created by Eric Yu on 2017/2/24.
 */
public enum CSVHeader {
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

    CSVHeader(String key) {
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
