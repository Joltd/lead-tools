package com.evgenltd.lt.component.query;

import com.evgenltd.lt.entity.Type;

public class Value {
    private String value;
    private Type type;

    public Value(final String value, final Type type) {
        this.value = value;
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(final String value) {
        this.value = value;
    }

    public Type getType() {
        return type;
    }

    public void setType(final Type type) {
        this.type = type;
    }
}
