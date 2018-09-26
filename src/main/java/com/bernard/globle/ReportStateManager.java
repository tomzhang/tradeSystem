package com.bernard.globle;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class ReportStateManager {
    public static Map<String, BigDecimal> totalAmountMap = new HashMap<>();
    public static Map<String, BigDecimal> totalFee = new HashMap<>();

    public static void addTotal(String pair, BigDecimal amount) {
        synchronized (totalAmountMap) {
            if (totalAmountMap.containsKey(pair)) {
                BigDecimal old = totalAmountMap.get(pair);
                totalAmountMap.put(pair, old.add(amount));
            } else {
                totalAmountMap.put(pair, amount);
            }
        }
    }

    public static void addFee(String pair, BigDecimal feeAmount) {
        synchronized (totalFee) {
            if (totalFee.containsKey(pair)) {
                BigDecimal old = totalFee.get(pair);
                totalFee.put(pair, old.add(feeAmount));
            } else {
                totalFee.put(pair, feeAmount);
            }
        }

    }

    public static BigDecimal getTotal(String pair) {
        synchronized (totalAmountMap) {
            if (totalAmountMap.containsKey(pair)) {
                BigDecimal total = totalAmountMap.get(pair);
                totalAmountMap.put(pair, new BigDecimal(0));
                return total;
            } else {
                return BigDecimal.ZERO;
            }
        }
    }

    public static BigDecimal getFee(String pair) {
        synchronized (totalFee) {
            if (totalFee.containsKey(pair)) {
                BigDecimal fee = totalFee.get(pair);
                totalFee.put(pair, new BigDecimal(0));
                return fee;
            } else {
                return new BigDecimal(0);
            }
        }
    }
}
