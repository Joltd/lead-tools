package com.evgenltd.lt.record;

import java.util.ArrayList;
import java.util.List;

public final class AirtableBody {

    public static final class Request {
        private List<AirtableRecord> records = new ArrayList<>();
        private boolean typecast = true;

        public List<AirtableRecord> getRecords() {
            return records;
        }

        public void setRecords(final List<AirtableRecord> records) {
            this.records = records;
        }

        public boolean isTypecast() {
            return typecast;
        }

        public void setTypecast(final boolean typecast) {
            this.typecast = typecast;
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
