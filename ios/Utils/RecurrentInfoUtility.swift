//
//  PaymentInfo.swift
//  Ecommpay
//
//  Created by Станислав Полещук on 22.07.2022.
//  Copyright © 2022 Facebook. All rights reserved.
//
import Foundation
import ecommpaySDK

public class RecurrentInfoUtility {
  static func bind(recurrent: NSDictionary, schedules: NSArray) -> RecurrentInfo {

    var type = RecurrentType.OneClick

    let typeStr = recurrent["type"] as? String

    switch typeStr {
    case "R":
      do {
        type = RecurrentType.Regular
        break
      }
    case "C":
      do {
        type = RecurrentType.OneClick
        break
      }
    case "U":
      do {
        type = RecurrentType.Autopayment
        break
      }
    default:
      do {
        type = RecurrentType.Autopayment
      }
    }

    let expiryDay = recurrent["expiryDay"] as? String ?? nil
    let expiryMonth = recurrent["expiryMonth"] as? String ?? nil
    let expiryYear = recurrent["expiryYear"] as? String ?? nil
    let period = recurrent["period"] as? ecommpaySDK.RecurrentPeriod ?? nil
    let time = recurrent["time"] as? String ?? nil
    let startDate = recurrent["startDate"] as? String ?? nil
    let scheduledPaymentID = recurrent["scheduledPaymentID"] as? String ?? nil
    let amount = recurrent["amount"] as? Int ?? nil

    let recurrentInfo: RecurrentInfo

    if expiryDay != nil && expiryMonth != nil && expiryYear != nil && period != nil && time != nil
      && startDate != nil && scheduledPaymentID != nil
    {
      recurrentInfo = RecurrentInfo(
        type: type,
        expiryDay: expiryDay!,
        expiryMonth: expiryMonth!,
        expiryYear: expiryYear!,
        period: period!,
        time: time!,
        startDate: startDate!,
        scheduledPaymentID: scheduledPaymentID!
      )
    } else {
      recurrentInfo = RecurrentInfo(
        type: type
      )
    }

    if amount != nil {
      recurrentInfo.setAmount(amount: amount!)
    }

    if schedules.count > 0 {
      var arrSchedule = [ecommpaySDK.RecurrentInfoSchedule]()

      schedules.forEach { (x) in
        let shedule = x as AnyObject

        print(shedule)
        let date = shedule["date"]
        let amount = shedule["amount"]

        if date != nil && amount != nil {
          arrSchedule.append(RecurrentInfoSchedule(date: date as! String, amount: amount as! Int))
        }
      }

      recurrentInfo.setSchedule(schedule: arrSchedule)
    }

    return recurrentInfo
  }
}
