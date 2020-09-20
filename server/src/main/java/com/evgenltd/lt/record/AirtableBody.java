package com.evgenltd.lt.record;

import java.util.ArrayList;
import java.util.List;

public final class AirtableBody {

    public static final class Request {
        private List<AirtableRecord> records = new ArrayList<>();

        public List<AirtableRecord> getRecords() {
            return records;
        }

        public void setRecords(final List<AirtableRecord> records) {
            this.records = records;
        }

    }

    public static final class Response {
        private List<AirtableRecord.Existed> records = new ArrayList<>();
        private String offset;

        public List<AirtableRecord.Existed> getRecords() {
            return records;
        }

        public void setRecords(final List<AirtableRecord.Existed> records) {
            this.records = records;
        }

        public String getOffset() {
            return offset;
        }

        public void setOffset(final String offset) {
            this.offset = offset;
        }
    }

}
