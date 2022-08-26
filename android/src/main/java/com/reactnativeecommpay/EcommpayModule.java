package com.reactnativeecommpay;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ecommpay.sdk.ECMPActivity;
import com.ecommpay.sdk.ECMPPaymentInfo;
import com.ecommpay.sdk.ECMPRecurrentInfo;
import com.ecommpay.sdk.ECMPTheme;
import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.BaseActivityEventListener;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.facebook.react.bridge.Arguments;
import com.google.android.gms.wallet.PaymentDataRequest;
import com.reactnativeecommpay.utils.RecurrentInfoUtility;
import com.reactnativeecommpay.utils.ThemeUtility;
import com.reactnativeecommpay.utils.PaymentInfoUtility;


@ReactModule(name = EcommpayModule.NAME)
public class EcommpayModule extends ReactContextBaseJavaModule {
  public static final String NAME = "Ecommpay";
  private static final String E_ACTIVITY_DOES_NOT_EXIST = "E_ACTIVITY_DOES_NOT_EXIST";
  private static final String PAYMENT_INFO_DOES_NOT_EXIST = "PAYMENT_INFO_DOES_NOT_EXIST";
  private static final String PAYMENT_INFO_DOES_NOT_EXIST_DESCRIPTION = "Object PaymentInfo does not exist, execute initPayment method";
  private static final String ACTION_DOES_NOT_EXIST = "ACTION_DOES_NOT_EXIST";

  private final ReactApplicationContext reactContext;
  private static final int PAY_ACTIVITY_REQUEST = 888;
  ECMPPaymentInfo paymentInfo;
  ECMPTheme theme;

  public EcommpayModule(ReactApplicationContext reactContext) {
    super(reactContext);
    reactContext.addActivityEventListener(mActivityEventListener);
    this.reactContext = reactContext;
  }

  private void sendEvent(ReactContext reactContext, String eventName, @Nullable WritableMap params) {
    reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(eventName, params);
  }

  private final ActivityEventListener mActivityEventListener = new BaseActivityEventListener() {
    @Override
    public void onActivityResult(Activity activity, int requestCode, int resultCode, @Nullable Intent data) {
      super.onActivityResult(activity, requestCode, resultCode, data);
      WritableMap params = Arguments.createMap();
      params.putInt("status", resultCode);

      if (requestCode == PAY_ACTIVITY_REQUEST) {
        switch (resultCode) {
          case ECMPActivity.RESULT_SUCCESS: {
            sendEvent(reactContext, "onSuccess", params);
            break;
          }
          case ECMPActivity.RESULT_CANCELLED: {
            sendEvent(reactContext, "onCancel", params);
            break;
          }
          default: {
            if (data != null && data.hasExtra(ECMPActivity.DATA_INTENT_EXTRA_ERROR)) {
              String error = data.getStringExtra(ECMPActivity.DATA_INTENT_EXTRA_ERROR);
              params.putString("error", error);
            }
            sendEvent(reactContext, "onError", params);
            break;
          }
        }
      }
    }
  };

  @Override
  @NonNull
  public String getName() {
    return NAME;
  }

  @ReactMethod
  public void createPayment(ReadableMap info, Promise promise) {
    try {
      paymentInfo = PaymentInfoUtility.bind(info);
      promise.resolve(true);
    } catch (Exception e) {
      promise.reject(e);
    }
  }

  @ReactMethod
  public void setTheme(ReadableMap options, @Nullable Boolean isDark, Promise promise) {
    try {
      theme = ThemeUtility.bind(options, isDark);
      promise.resolve(true);
    } catch (Exception e) {
      promise.reject(e);
    }
  }

  @ReactMethod
  public void getParamsForSignature(Promise promise) {
    if (paymentInfo == null) {
      promise.reject(PAYMENT_INFO_DOES_NOT_EXIST, PAYMENT_INFO_DOES_NOT_EXIST_DESCRIPTION);
    }
    try {
      promise.resolve(paymentInfo.getParamsForSignature());
    } catch (Exception e) {
      promise.reject(e);
    }
  }

  @ReactMethod
  public void setForcePaymentMethod(String method, Promise promise) {
    if (paymentInfo == null) {
      promise.reject(PAYMENT_INFO_DOES_NOT_EXIST, PAYMENT_INFO_DOES_NOT_EXIST_DESCRIPTION);
    }
    try {
      paymentInfo.setForcePaymentMethod(method);
      promise.resolve(paymentInfo.getForcePaymentMethod());
    } catch (Exception e) {
      promise.reject(e);
    }
  }

  @ReactMethod
  public void setMerchantID(String merchantID, Promise promise) {
    if (paymentInfo == null) {
      promise.reject(PAYMENT_INFO_DOES_NOT_EXIST, PAYMENT_INFO_DOES_NOT_EXIST_DESCRIPTION);
    }
    try {
      paymentInfo.setMerchantId(merchantID);
      promise.resolve(paymentInfo.getMerchantId());
    } catch (Exception e) {
      promise.reject(e);
    }
  }

  @ReactMethod
  public void setPaymentDataRequest(final String jsonPaymentDataRequest, Promise promise) {
    if (paymentInfo == null) {
      promise.reject(PAYMENT_INFO_DOES_NOT_EXIST, PAYMENT_INFO_DOES_NOT_EXIST_DESCRIPTION);
    }
    try {
      paymentInfo.setPaymentDataRequest(PaymentDataRequest.fromJson(jsonPaymentDataRequest));
      promise.resolve(jsonPaymentDataRequest);
    } catch (Exception e) {
      promise.reject(e);
    }
  }

  @ReactMethod
  public void addEcmpScreenDisplayMode(final String ecmpScreenDisplayMode, Promise promise) {
    if (paymentInfo == null) {
      promise.reject(PAYMENT_INFO_DOES_NOT_EXIST, PAYMENT_INFO_DOES_NOT_EXIST_DESCRIPTION);
    }
    try {
      paymentInfo.addEcmpScreenDisplayMode(ecmpScreenDisplayMode);
      promise.resolve(ecmpScreenDisplayMode);
    } catch (Exception e) {
      promise.reject(e);
    }
  }

  @ReactMethod
  public void setSignature(final String signature, Promise promise) {
    if (paymentInfo == null) {
      promise.reject(PAYMENT_INFO_DOES_NOT_EXIST, PAYMENT_INFO_DOES_NOT_EXIST_DESCRIPTION);
    }
    try {
      paymentInfo.setSignature(signature);
      promise.resolve(signature);
    } catch (Exception e) {
      promise.reject(e);
    }
  }

  @ReactMethod
  public void setRecurrent(ReadableMap recurrent, ReadableArray schedules, Promise promise) {
    if (paymentInfo == null) {
      promise.reject(PAYMENT_INFO_DOES_NOT_EXIST, PAYMENT_INFO_DOES_NOT_EXIST_DESCRIPTION);
    }
    try {
      ECMPRecurrentInfo recurrentInfo = RecurrentInfoUtility.bind(recurrent, schedules);
      paymentInfo.setRecurrent(recurrentInfo);
      promise.resolve(true);
    } catch (Exception e) {
      promise.reject(e);
    }
  }

  @ReactMethod
  public void setHideSavedWallets(Boolean value, Promise promise) {
    if (paymentInfo == null) {
      promise.reject(PAYMENT_INFO_DOES_NOT_EXIST, PAYMENT_INFO_DOES_NOT_EXIST_DESCRIPTION);
    }
    try {
      paymentInfo.setHideSavedWallets(value);
      promise.resolve(value);
    } catch (Exception e) {
      promise.reject(e);
    }
  }

  @ReactMethod
  public void setAction(final Integer action, Promise promise) {
    if (paymentInfo == null) {
      promise.reject(PAYMENT_INFO_DOES_NOT_EXIST, PAYMENT_INFO_DOES_NOT_EXIST_DESCRIPTION);
    }
    try {
      switch (action) {
        case 1: {
          paymentInfo.setAction(ECMPPaymentInfo.ActionType.Sale);
          break;
        }
        case 2: {
          paymentInfo.setAction(ECMPPaymentInfo.ActionType.Auth);
          break;
        }
        case 3: {
          paymentInfo.setAction(ECMPPaymentInfo.ActionType.Tokenize);
          break;
        }
        case 4: {
          paymentInfo.setAction(ECMPPaymentInfo.ActionType.Verify);
          break;
        }
        default: {
          promise.reject(ACTION_DOES_NOT_EXIST, "Action " + action + "doesn't exist");
        }
      }
      promise.resolve(action);
    } catch (Exception e) {
      promise.reject(e);
    }
  }

  @ReactMethod
  public void presentPayment(Promise promise) {
    if (paymentInfo == null) {
      promise.reject(PAYMENT_INFO_DOES_NOT_EXIST, PAYMENT_INFO_DOES_NOT_EXIST_DESCRIPTION);
    }
    try {
      Activity currentActivity = getCurrentActivity();
      if (currentActivity == null) {
        promise.reject(E_ACTIVITY_DOES_NOT_EXIST, "Activity doesn't exist");
        return;
      }
      currentActivity.startActivityForResult(ECMPActivity.buildIntent(getReactApplicationContext(), paymentInfo, theme), PAY_ACTIVITY_REQUEST);
      promise.resolve(true);
    } catch (Exception e) {
      promise.reject(e);
    }
  }
}
