//
//  PaymentInfo.swift
//  Ecommpay
//
//  Created by Станислав Полещук on 22.07.2022.
//  Copyright © 2022 Facebook. All rights reserved.
//

import Foundation
import ecommpaySDK


public class ThemeUtility {
  static func bind(info: NSDictionary, isDark: Bool!) -> ecommpaySDK.ECPTheme {
    let theme = isDark ? ECPTheme.getDarkTheme() : ECPTheme.getLightTheme()
   
    if let overlayColor = info["overlayColor"] as? String {
      theme.overlayColor =  hexStringToUIColor(hex: overlayColor);
    }
    
    
    if let backgroundColor = info["backgroundColor"] as? String {
      theme.backgroundColor =  hexStringToUIColor(hex: backgroundColor);
    }
    
    
    if let headingTextColor = info["headingTextColor"] as? String {
      theme.headingTextColor =  hexStringToUIColor(hex: headingTextColor);
    }
    
    
    if let menuTextColor = info["menuTextColor"] as? String {
      theme.menuTextColor =  hexStringToUIColor(hex: menuTextColor);
    }
    
    
    if let fieldTextColor = info["fieldTextColor"] as? String {
      theme.fieldTextColor =  hexStringToUIColor(hex: fieldTextColor);
    }
    
    
    if let supportiveTextColor = info["supportiveTextColor"] as? String {
      theme.supportiveTextColor =  hexStringToUIColor(hex: supportiveTextColor);
    }
    
    
    if let fieldPlaceholderTextColor = info["fieldPlaceholderTextColor"] as? String {
      theme.fieldPlaceholderTextColor =  hexStringToUIColor(hex: fieldPlaceholderTextColor);
    }
    
    
    if let fieldImageTintColor = info["fieldImageTintColor"] as? String {
      theme.fieldImageTintColor =  hexStringToUIColor(hex: fieldImageTintColor);
    }
    
    
    if let fieldTextCorrectColor = info["fieldTextCorrectColor"] as? String {
      theme.fieldTextCorrectColor =  hexStringToUIColor(hex: fieldTextCorrectColor);
    }
    
    
    if let fieldTextWrongColor = info["fieldTextWrongColor"] as? String {
      theme.fieldTextWrongColor =  hexStringToUIColor(hex: fieldTextWrongColor);
    }
    
    
    if let fieldBackgroundColor = info["fieldBackgroundColor"] as? String {
      theme.fieldBackgroundColor =  hexStringToUIColor(hex: fieldBackgroundColor);
    }
    
    
    if let selectorColor = info["selectorColor"] as? String {
      theme.selectorColor =  hexStringToUIColor(hex: selectorColor);
    }
    
    
    if let selectorBackgroundColor = info["selectorBackgroundColor"] as? String {
      theme.selectorBackgroundColor =  hexStringToUIColor(hex: selectorBackgroundColor);
    }
    
    
    if let primaryTintColor = info["primaryTintColor"] as? String {
      theme.primaryTintColor =  hexStringToUIColor(hex: primaryTintColor);
    }
    
    
    if let secondaryTintColor = info["secondaryTintColor"] as? String {
      theme.secondaryTintColor =  hexStringToUIColor(hex: secondaryTintColor);
    }
    
    
    if let lineColor = info["lineColor"] as? String {
      theme.lineColor =  hexStringToUIColor(hex: lineColor);
    }
    
    
    if let actionButtonDisableBackgroundColor = info["actionButtonDisableBackgroundColor"] as? String {
      theme.actionButtonDisableBackgroundColor =  hexStringToUIColor(hex: actionButtonDisableBackgroundColor);
    }
    
    
    if let actionButtonDisableTextColor = info["actionButtonDisableTextColor"] as? String {
      theme.actionButtonDisableTextColor =  hexStringToUIColor(hex: actionButtonDisableTextColor);
    }
    
    
    if let actionButtonTextColor = info["actionButtonTextColor"] as? String {
      theme.actionButtonTextColor =  hexStringToUIColor(hex: actionButtonTextColor);
    }
    
    
    if let searchInputViewPlaceholderColor = info["searchInputViewPlaceholderColor"] as? String {
      theme.searchInputViewPlaceholderColor =  hexStringToUIColor(hex: searchInputViewPlaceholderColor);
    }
    
    
    if let bankItemTextColor = info["bankItemTextColor"] as? String {
      theme.bankItemTextColor =  hexStringToUIColor(hex: bankItemTextColor);
    }
    
    
    if let actionButtonBackgroundColor = info["actionButtonBackgroundColor"] as? String {
      theme.actionButtonBackgroundColor =  hexStringToUIColor(hex: actionButtonBackgroundColor);
    }
    
    
    if let loadingScreenBackground = info["loadingScreenBackground"] as? String {
      theme.loadingScreenBackground =  hexStringToUIColor(hex: loadingScreenBackground);
    }
    
    
    if let clarificationInfoTextColor = info["clarificationInfoTextColor"] as? String {
      theme.clarificationInfoTextColor =  hexStringToUIColor(hex: clarificationInfoTextColor);
    }
    
    
    if let clarificationHeaderTextColor = info["clarificationHeaderTextColor"] as? String {
      theme.clarificationHeaderTextColor =  hexStringToUIColor(hex: clarificationHeaderTextColor);
    }
    
    
    if let clarificationInfoBackgroundColor = info["clarificationInfoBackgroundColor"] as? String {
      theme.clarificationInfoBackgroundColor =  hexStringToUIColor(hex: clarificationInfoBackgroundColor);
    }
    
    
    if let clarificationInfoCircleColor = info["clarificationInfoCircleColor"] as? String {
      theme.clarificationInfoCircleColor =  hexStringToUIColor(hex: clarificationInfoCircleColor);
    }
    
    
    if let clarificationPaymentInfoBackgroundColor = info["clarificationPaymentInfoBackgroundColor"] as? String {
      theme.clarificationPaymentInfoBackgroundColor =  hexStringToUIColor(hex: clarificationPaymentInfoBackgroundColor);
    }
    
    
    if let clarificationInfoTintColor = info["clarificationInfoTintColor"] as? String {
      theme.clarificationInfoTintColor =  hexStringToUIColor(hex: clarificationInfoTintColor);
    }
    
    
    if let searchIconWrapViewColor = info["searchIconWrapViewColor"] as? String {
      theme.searchIconWrapViewColor =  hexStringToUIColor(hex: searchIconWrapViewColor);
    }
    
    
    if let searchViewIconColor = info["searchViewIconColor"] as? String {
      theme.searchViewIconColor =  hexStringToUIColor(hex: searchViewIconColor);
    }
    
    
    if let showSearchViewBorder = info["showSearchViewBorder"] as? Bool {
      theme.showSearchViewBorder =  showSearchViewBorder;
    }
    
    
    if let showShadow = info["showShadow"] as? Bool {
      theme.showShadow =  showShadow;
    }
    
    if let showSearchViewBorder = info["showSearchViewBorder"] as? Bool {
      theme.showSearchViewBorder =  showSearchViewBorder;
    }
    
    if let showDarkKeyboard = info["showDarkKeyboard"] as? Bool {
      theme.showDarkKeyboard =  showDarkKeyboard;
    }
    
    if let showDarkNavigationBar = info["showDarkNavigationBar"] as? Bool {
      theme.showDarkNavigationBar =  showDarkNavigationBar;
    }
    
    if let showLightLogo = info["showLightLogo"] as? Bool {
      theme.showLightLogo =  showLightLogo;
    }
    
    if let apsLogoOnly = info["apsLogoOnly"] as? Bool {
      theme.apsLogoOnly =  apsLogoOnly;
    }
    
    
    if let apsLogoOnly = info["apsLogoOnly"] as? Bool {
      theme.apsLogoOnly =  apsLogoOnly;
    }
      
    return theme
  }

  static func hexStringToUIColor(hex: String) -> UIColor {
    var cString: String = hex.trimmingCharacters(in: .whitespacesAndNewlines).uppercased()

    if cString.hasPrefix("#") {
      cString.remove(at: cString.startIndex)
    }

    if (cString.count) != 6 {
      return UIColor.gray
    }

    var rgbValue: UInt64 = 0
    Scanner(string: cString).scanHexInt64(&rgbValue)

    return UIColor(
      red: CGFloat((rgbValue & 0xFF0000) >> 16) / 255.0,
      green: CGFloat((rgbValue & 0x00FF00) >> 8) / 255.0,
      blue: CGFloat(rgbValue & 0x0000FF) / 255.0,
      alpha: CGFloat(1.0)
    )
  }
}
