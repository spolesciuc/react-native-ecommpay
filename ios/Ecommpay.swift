import AVFoundation
import Foundation
import UIKit
import ecommpaySDK

@objc(Ecommpay)
class Ecommpay: RCTEventEmitter {
  let ecompaySDK = EcommpaySDK()
  var paymentInfo: PaymentInfo?

  static let RESULT_SUCCESS = 0
  static let RESULT_DECLINE = 100
  static let RESULT_CANCELLED = 301
  static let RESULT_FAILED = 501
  static let INIT_FAILED = 601

  static let PAYMENT_INFO_DOES_NOT_EXIST = "PAYMENT_INFO_DOES_NOT_EXIST"
  static let PAYMENT_INFO_DOES_NOT_EXIST_DESCRIPTION =
    "Object PaymentInfo does not exist, execute initPayment method"

  @objc(createPayment:withResolver:withRejecter:)
  func createPayment(
    info: NSDictionary, resolve: @escaping RCTPromiseResolveBlock,
    reject: @escaping RCTPromiseRejectBlock
  ) {
    paymentInfo = PaymentInfoUtility.bind(info: info)
    resolve(true)
  }

  @objc(setTheme:isDark:withResolver:withRejecter:)
  func setTheme(
    options: NSDictionary, isDark: Bool, resolve: @escaping RCTPromiseResolveBlock,
    reject: @escaping RCTPromiseRejectBlock
  ) {
    let theme = ThemeUtility.bind(info: options, isDark: isDark)
    ecompaySDK.setTheme(theme: theme)
    resolve(true)
  }

  @objc(getParamsForSignature:withRejecter:)
  func getParamsForSignature(
    resolve: @escaping RCTPromiseResolveBlock, reject: @escaping RCTPromiseRejectBlock
  ) {
    if paymentInfo != nil {
      resolve(
        paymentInfo?.getParamsForSignature())
    } else {
      let error = NSError(
        domain: "", code: Ecommpay.INIT_FAILED,
        userInfo: [NSLocalizedDescriptionKey: Ecommpay.PAYMENT_INFO_DOES_NOT_EXIST_DESCRIPTION])
      reject(Ecommpay.PAYMENT_INFO_DOES_NOT_EXIST, error.userInfo.description, error)
    }
  }

  @objc(setForcePaymentMethod:withResolver:withRejecter:)
  func setForcePaymentMethod(
    method: String, resolve: @escaping RCTPromiseResolveBlock,
    reject: @escaping RCTPromiseRejectBlock
  ) {
    if paymentInfo != nil {
      paymentInfo?.setForcePaymentMethod(value: method)
      resolve(method)
    } else {
      let error = NSError(
        domain: "", code: Ecommpay.INIT_FAILED,
        userInfo: [NSLocalizedDescriptionKey: Ecommpay.PAYMENT_INFO_DOES_NOT_EXIST_DESCRIPTION])
      reject(Ecommpay.PAYMENT_INFO_DOES_NOT_EXIST, error.userInfo.description, error)
    }
  }

  @objc(setMerchantID:withResolver:withRejecter:)
  func setMerchantID(
    merchantID: String, resolve: @escaping RCTPromiseResolveBlock,
    reject: @escaping RCTPromiseRejectBlock
  ) {
    if paymentInfo != nil {
      paymentInfo?.setApplePayMerchantID(merchantID: merchantID)
      resolve(merchantID)
    } else {
      let error = NSError(
        domain: "", code: Ecommpay.INIT_FAILED,
        userInfo: [NSLocalizedDescriptionKey: Ecommpay.PAYMENT_INFO_DOES_NOT_EXIST_DESCRIPTION])
      reject(Ecommpay.PAYMENT_INFO_DOES_NOT_EXIST, error.userInfo.description, error)
    }
  }

  @objc(setApplePayDescription:withResolver:withRejecter:)
  func setApplePayDescription(
    description: String, resolve: @escaping RCTPromiseResolveBlock,
    reject: @escaping RCTPromiseRejectBlock
  ) {
    if paymentInfo != nil {
      paymentInfo?.setApplePayDescription(description: description)
      resolve(description)
    } else {
      let error = NSError(
        domain: "", code: Ecommpay.INIT_FAILED,
        userInfo: [NSLocalizedDescriptionKey: Ecommpay.PAYMENT_INFO_DOES_NOT_EXIST_DESCRIPTION])
      reject(Ecommpay.PAYMENT_INFO_DOES_NOT_EXIST, error.userInfo.description, error)
    }
  }

  @objc(setSignature:withResolver:withRejecter:)
  func setSignature(
    signature: String, resolve: @escaping RCTPromiseResolveBlock,
    reject: @escaping RCTPromiseRejectBlock
  ) {
    if paymentInfo != nil {
      paymentInfo?.setSignature(value: signature)
      resolve(signature)
    } else {
      let error = NSError(
        domain: "", code: Ecommpay.INIT_FAILED,
        userInfo: [NSLocalizedDescriptionKey: Ecommpay.PAYMENT_INFO_DOES_NOT_EXIST_DESCRIPTION])
      reject(Ecommpay.PAYMENT_INFO_DOES_NOT_EXIST, error.userInfo.description, error)
    }
  }

  @objc(setRecurrent:withSchedules:withResolver:withRejecter:)
  func setRecurrent(
    recurrent: NSDictionary, schedules: NSArray, resolve: @escaping RCTPromiseResolveBlock,
    reject: @escaping RCTPromiseRejectBlock
  ) {

    let recurrentInfo = RecurrentInfoUtility.bind(recurrent: recurrent, schedules: schedules)

    if paymentInfo != nil {
      paymentInfo?.setRecurrent(recurrent: recurrentInfo)
      resolve(true)
    } else {
      let error = NSError(
        domain: "", code: Ecommpay.INIT_FAILED,
        userInfo: [NSLocalizedDescriptionKey: Ecommpay.PAYMENT_INFO_DOES_NOT_EXIST_DESCRIPTION])
      reject(Ecommpay.PAYMENT_INFO_DOES_NOT_EXIST, error.userInfo.description, error)
    }

  }

  @objc(presentPayment:withRejecter:)
  func presentPayment(
    resolve: @escaping RCTPromiseResolveBlock,
    reject: @escaping RCTPromiseRejectBlock
  ) {
    if paymentInfo != nil {
      let payment = paymentInfo!

      DispatchQueue.main.async(execute: {
        let rootViewController = (UIApplication.shared.delegate?.window??.rootViewController)!

        self.ecompaySDK.presentPayment(at: rootViewController, paymentInfo: payment) { (result) in
          print("ecommpaySDK finished with status \(result.status.rawValue)")

          let status = result.status
          switch result.status.rawValue {
          case Ecommpay.RESULT_SUCCESS:
            do {
              self.sendEvent(withName: "onSuccess", body: ["status": status.rawValue])
              break
            }
          case Ecommpay.RESULT_CANCELLED:
            do {
              print("Cancelled")
              print(status.rawValue)

              self.sendEvent(withName: "onCancel", body: ["status": status.rawValue])
              break
            }
          default:
            do {
              print("Failed")
              var errorMessage = ""
              if let error = result.error {  // в случае ошибки
                print(error)
                errorMessage = error.localizedDescription
              }
              self.sendEvent(
                withName: "onError",
                body: ["errorMessage": errorMessage, "status": status.rawValue])
            }
          }
        }
      })
    } else {
      let error = NSError(
        domain: "", code: Ecommpay.INIT_FAILED,
        userInfo: [NSLocalizedDescriptionKey: Ecommpay.PAYMENT_INFO_DOES_NOT_EXIST_DESCRIPTION])
      reject(Ecommpay.PAYMENT_INFO_DOES_NOT_EXIST, error.userInfo.description, error)
    }
  }

  override func supportedEvents() -> [String]! {
    return ["onSuccess", "onError", "onCancel"]
  }

  override func constantsToExport() -> [AnyHashable: Any]! {
    return ["initialCount": 100]
  }

  override static func requiresMainQueueSetup() -> Bool {
    return true
  }
}
