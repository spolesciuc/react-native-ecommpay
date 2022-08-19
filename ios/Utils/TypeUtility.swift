//
//  ColorUtility.swift
//  Ecommpay
//
//  Created by Станислав Полещук on 22.07.2022.
//  Copyright © 2022 Facebook. All rights reserved.
//

import Foundation

public class TypeUtility {
  static func isObjectNotNil(object: AnyObject!) -> Bool {
    if let _: AnyObject = object {
      return true
    }
    return false
  }
}
