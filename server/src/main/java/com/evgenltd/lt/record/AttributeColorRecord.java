package com.evgenltd.lt.record;

import com.evgenltd.lt.entity.AttributeColor;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public record AttributeColorRecord(Long id, String color, String condition) {

    public static AttributeColorRecord from(final AttributeColor attributeColor) {
        return new AttributeColorRecord(
                attributeColor.getId(),
                attributeColor.getColor(),
                attributeColor.getCondition()
        );
    }

    public AttributeColor toEntity() {
        final AttributeColor attributeColor = new AttributeColor();
        attributeColor.setId(id());
        attributeColor.setColor(color());
        attributeColor.setCondition(condition());
        return attributeColor;
    }

}
