package com.evgenltd.lt.component.query;

import com.evgenltd.lt.entity.Type;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Objects;

public class Comparison {

    public static boolean compare(final Value left, final Operator operator, final Value right) {
        if (operator.equals(Operator.E)) {
            return Objects.equals(left.getValue(), right.getValue());
        }

        if (operator.equals(Operator.N_E)) {
            return !Objects.equals(left.getValue(), right.getValue());
        }

        if (Arrays.asList(Operator.G, Operator.L, Operator.GE, Operator.LE).contains(operator)) {
            if (left.getType().equals(Type.NUMBER) && right.getType().equals(Type.NUMBER)) {
                final BigDecimal leftValue = new BigDecimal(left.getValue());
                final BigDecimal rightValue = new BigDecimal(right.getValue());
                if (operator.equals(Operator.G)) {
                    return leftValue.compareTo(rightValue) > 0;
                } else if (operator.equals(Operator.L)) {
                    return leftValue.compareTo(rightValue) < 0;
                } else if (operator.equals(Operator.GE)) {
                    return leftValue.compareTo(rightValue) >= 0;
                } else if (operator.equals(Operator.LE)) {
                    return leftValue.compareTo(rightValue) <= 0;
                }
            }
        }

        if (operator.equals(Operator.LIKE)) {
            if (left.getType().equals(Type.STRING) && right.getType().equals(Type.STRING)) {
                return left.getValue().matches(right.getValue());
            }
        }

        return false;
    }



}
