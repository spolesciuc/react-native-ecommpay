package com.reactnativeecommpay.utils;

import com.ecommpay.sdk.ECMPRecurrentInfo;
import com.ecommpay.sdk.ECMPRecurrentInfoSchedule;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;


public class RecurrentInfoUtility {
  public static ECMPRecurrentInfo bind(ReadableMap recurrent, ReadableArray schedules) {
    String type = recurrent.hasKey("type") ? recurrent.getString("type") : "R";
    String expiryDay = recurrent.hasKey("expiryDay") ? recurrent.getString("expiryDay") : null;
    String expiryMonth = recurrent.hasKey("expiryMonth") ? recurrent.getString("expiryMonth") : null;
    String expiryYear = recurrent.hasKey("expiryYear") ? recurrent.getString("expiryYear") : null;
    String period = recurrent.hasKey("period") ? recurrent.getString("period") : null;
    String time = recurrent.hasKey("time") ? recurrent.getString("time") : null;
    String startDate = recurrent.hasKey("startDate") ? recurrent.getString("startDate") : null;
    String scheduledPaymentID = recurrent.hasKey("scheduledPaymentID") ? recurrent.getString("scheduledPaymentID") : null;

    ECMPRecurrentInfo recurrentInfo;

    if (TypeUtility.stringIsNotEmpty(expiryDay) && TypeUtility.stringIsNotEmpty(expiryMonth) &&
      TypeUtility.stringIsNotEmpty(expiryYear) && TypeUtility.stringIsNotEmpty(period) &&
      TypeUtility.stringIsNotEmpty(time) && TypeUtility.stringIsNotEmpty(startDate) &&
      TypeUtility.stringIsNotEmpty(scheduledPaymentID)
    ) {
      recurrentInfo = new ECMPRecurrentInfo(
        type,
        expiryDay,
        expiryMonth,
        expiryYear,
        period,
        time,
        startDate,
        scheduledPaymentID
      );
    } else {
      recurrentInfo = new ECMPRecurrentInfo(
        type
      );
    }

    if (recurrent.hasKey("amount")) {
      recurrentInfo.setAmount(TypeUtility.convertToLong(recurrent.getDouble("amount")));
    }

    if (schedules.size() > 0) {
      ECMPRecurrentInfoSchedule[] ecmpRecurrentInfoSchedules = new ECMPRecurrentInfoSchedule[schedules.size()];
      for (int i = 0; i < schedules.size(); i++) {
        ReadableMap map = schedules.getMap(i);

        if (map == null) {
          continue;
        }

        String date = map.hasKey("date") ? map.getString("date") : null;
        long amount = map.hasKey("amount") ? TypeUtility.convertToLong(map.getDouble("amount")) : -1;

        if (TypeUtility.stringIsEmpty(date) || amount == -1) {
          continue;
        }

        ecmpRecurrentInfoSchedules[i] = new ECMPRecurrentInfoSchedule(date, amount);
      }

      if (ecmpRecurrentInfoSchedules.length > 0) {
        recurrentInfo.setSchedule(ecmpRecurrentInfoSchedules);
      }
    }

    return recurrentInfo;
  }
}

