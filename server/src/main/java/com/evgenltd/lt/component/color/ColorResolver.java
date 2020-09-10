package com.evgenltd.lt.component.color;

import com.evgenltd.lt.component.Utils;
import com.evgenltd.lt.component.query.ParseException;
import com.evgenltd.lt.component.query.QueryBuilder;
import com.evgenltd.lt.entity.Attribute;
import com.evgenltd.lt.entity.AttributeColor;
import com.evgenltd.lt.entity.TicketAttribute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ColorResolver {

    private static final Logger log = LoggerFactory.getLogger(ColorResolver.class);

    public static String resolve(final TicketAttribute ticketAttribute) {
        final Attribute attribute = ticketAttribute.getAttribute();
        final String value = ticketAttribute.getValue();
        for (final AttributeColor attributeColor : attribute.getColors()) {
            if (Utils.isBlank(attributeColor.getCondition())) {
                return attributeColor.getColor();
            }
            try {
                final boolean isMatched = QueryBuilder.from(attributeColor.getCondition(), value, attribute.getType()).match();
                if (isMatched) {
                    return attributeColor.getColor();
                }
            } catch (final ParseException e) {
                log.error("Query failed", e);
            }
        }
        return null;
    }

}
