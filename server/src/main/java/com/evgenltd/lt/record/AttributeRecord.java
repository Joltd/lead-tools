package com.evgenltd.lt.record;

import com.evgenltd.lt.entity.Attribute;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public record AttributeRecord(Long id, String name, Attribute.Type type, Boolean readonly) {

    public static AttributeRecord from(final Attribute attribute) {
        return new AttributeRecord(
                attribute.getId(),
                attribute.getName(),
                attribute.getType(),
                attribute.getReadonly()
        );
    }

    public Attribute toEntity() {
        final Attribute attribute = new Attribute();
        attribute.setId(id());
        attribute.setName(name());
        attribute.setType(type());
        attribute.setReadonly(readonly());
        return attribute;
    }

}
