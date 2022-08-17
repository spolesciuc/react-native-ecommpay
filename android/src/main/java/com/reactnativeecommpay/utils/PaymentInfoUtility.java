package com.reactnativeecommpay.utils;

import com.ecommpay.sdk.ECMPPaymentInfo;
import com.facebook.react.bridge.ReadableMap;


public class PaymentInfoUtility {
  public static ECMPPaymentInfo bind(ReadableMap info) throws Exception {
    int projectId = info.hasKey("projectId") ? info.getInt("projectId") : 0;
    String paymentId = info.hasKey("paymentId") ? info.getString("paymentId") : null;
    long paymentAmount = info.hasKey("paymentAmount") ? TypeUtility.convertToLong(info.getDouble("paymentAmount")) : 0;
    String paymentCurrency = info.hasKey("paymentCurrency") ? info.getString("paymentCurrency") : null;
    String paymentDescription = info.hasKey("paymentDescription") ? info.getString("paymentDescription") : null;
    String customerId = info.hasKey("customerId") ? info.getString("customerId") : null;
    String regionCode = info.hasKey("regionCode") ? info.getString("regionCode") : null;


    if (TypeUtility.stringIsNotEmpty(paymentId) && TypeUtility.stringIsNotEmpty(paymentCurrency) &&
      TypeUtility.stringIsNotEmpty(paymentDescription) && TypeUtility.stringIsNotEmpty(customerId) &&
      TypeUtility.stringIsNotEmpty(regionCode)) {
      return new ECMPPaymentInfo(
        projectId,
        paymentId,
        paymentAmount,
        paymentCurrency,
        paymentDescription,
        customerId,
        regionCode);
    }

    if (TypeUtility.stringIsNotEmpty(paymentId) && TypeUtility.stringIsNotEmpty(paymentCurrency)) {
      return new ECMPPaymentInfo(
        projectId,
        paymentId,
        paymentAmount,
        paymentCurrency);
    }

    if (TypeUtility.stringIsNotEmpty(paymentCurrency)) {
      return new ECMPPaymentInfo(
        projectId,
        paymentAmount,
        paymentCurrency);
    }

    throw new Exception("Invalid payment info");
  }
}

