//
//  PaymentInfo.swift
//  Ecommpay
//
//  Created by Станислав Полещук on 22.07.2022.
//  Copyright © 2022 Facebook. All rights reserved.
//
import Foundation
import ecommpaySDK

public class PaymentInfoUtility {
  static func bind(info: NSDictionary) -> PaymentInfo {

    let projectId = info["projectId"] as? Int ?? 0
    let paymentId = info["paymentId"] as? String
    let paymentAmount = info["paymentAmount"] as? Int ?? 0
    let paymentCurrency = info["paymentCurrency"] as? String ?? "USD"
    let paymentDescription = info["paymentDescription"] as? String
    let customerId = info["customerId"] as? String
    let regionCode = info["regionCode"] as? String

    return PaymentInfo(
      projectID: projectId,
      paymentID: paymentId,
      paymentAmount: paymentAmount,
      paymentCurrency: paymentCurrency,
      paymentDescription: paymentDescription,
      customerID: customerId,
      regionCode: regionCode)
  }
}
