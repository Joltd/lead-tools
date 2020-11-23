package com.evgenltd.lt.record;

import java.util.HashMap;
import java.util.Map;

public class AirtableRecord {
    private Map<String, Object> fields = new HashMap<>();

    public Map<String, Object> getFields() {
        return fields;
    }

    public void setFields(final Map<String, Object> fields) {
        this.fields = fields;
    }

    public Object get(final String field) {
        return fields.get(field);
    }

    public void set(final String field, final Object value) {
        fields.put(field, value);
    }

    public static class Existed extends AirtableRecord {
        private String id;

        public String getId() {
            return id;
        }

        public void setId(final String id) {
            this.id = id;
        }
    }
}
