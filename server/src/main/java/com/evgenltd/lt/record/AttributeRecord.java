package com.evgenltd.lt.record;

import com.evgenltd.lt.entity.Attribute;
import com.evgenltd.lt.entity.Type;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public record AttributeRecord(Long id, String name, Type type, Boolean readonly, String link, List<AttributeColorRecord> colors) {

    public static AttributeRecord from(final Attribute attribute) {
        return new AttributeRecord(
                attribute.getId(),
                attribute.getName(),
                attribute.getType(),
                attribute.getReadonly(),
                attribute.getLink(),
                attribute.getColors()
                        .stream()
                        .map(AttributeColorRecord::from)
                        .collect(Collectors.toList())
        );
    }

    public static AttributeRecord fromSimple(final Attribute attribute) {
        return new AttributeRecord(
                attribute.getId(),
                attribute.getName(),
                attribute.getType(),
                attribute.getReadonly(),
                attribute.getLink(),
                new ArrayList<>()
        );
    }

    public Attribute toEntity() {
        final Attribute attribute = new Attribute();
        attribute.setId(id());
        attribute.setName(name());
        attribute.setType(type());
        attribute.setReadonly(readonly());
        attribute.setLink(link());
        colors().stream()
                .map(AttributeColorRecord::toEntity)
                .peek(attributeColor -> attributeColor.setAttribute(attribute))
                .forEach(attributeColor -> attribute.getColors().add(attributeColor));
        return attribute;
    }

}
