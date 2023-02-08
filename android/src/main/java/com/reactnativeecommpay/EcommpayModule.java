package com.reactnativeecommpay;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

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
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.wallet.IsReadyToPayRequest;
import com.google.android.gms.wallet.PaymentDataRequest;
import com.google.android.gms.wallet.PaymentsClient;
import com.google.android.gms.wallet.Wallet;
import com.google.android.gms.wallet.WalletConstants;
import com.reactnativeecommpay.utils.GPay;
import com.reactnativeecommpay.utils.RecurrentInfoUtility;
import com.reactnativeecommpay.utils.ThemeUtility;
import com.reactnativeecommpay.utils.PaymentInfoUtility;


import org.json.JSONObject;

import java.util.Objects;



@ReactModule(name = EcommpayModule.NAME)
public class EcommpayModule extends ReactContextBaseJavaModule {
  public static final String NAME = "Ecommpay";
  private static final String E_ACTIVITY_DOES_NOT_EXIST = "E_ACTIVITY_DOES_NOT_EXIST";
  private static final String PAYMENT_INFO_DOES_NOT_EXIST = "PAYMENT_INFO_DOES_NOT_EXIST";
  private static final String PAYMENT_INFO_DOES_NOT_EXIST_DESCRIPTION = "Object PaymentInfo does not exist, execute initPayment method";
  private static final String ACTION_DOES_NOT_EXIST = "ACTION_DOES_NOT_EXIST";
  private static final String NOT_READY_TO_PAY = "NOT_READY_TO_PAY";

  private final ReactApplicationContext reactContext;
  private static final int PAY_ACTIVITY_REQUEST = 888;
  ECMPPaymentInfo paymentInfo;
  ECMPTheme theme = ECMPTheme.getLightTheme();
  private static final String E_FAILED_TO_DETECT_IF_READY = "E_FAILED_TO_DETECT_IF_READY";

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
  public void setTheme(ReadableMap options, @Nullable Boolean isDark, Promise promise) {
    try {
      theme = ThemeUtility.bind(options, isDark);
      promise.resolve(true);
    } catch (Exception e) {
      promise.reject(e);
    }
  }



  private void isReadyToPay(Promise promise, String jsonPaymentDataRequest) {

    WritableMap params = Arguments.createMap();
    Activity currentActivity = getCurrentActivity();

    if(currentActivity == null) {
      sendEvent(reactContext, "onError", params);
      return;
    }

    PaymentsClient mPaymentsClient = Wallet.getPaymentsClient(currentActivity,
      new Wallet.WalletOptions
        .Builder()
        .setEnvironment(WalletConstants.ENVIRONMENT_PRODUCTION)
        .build());

    IsReadyToPayRequest request = IsReadyToPayRequest.fromJson(jsonPaymentDataRequest);
    Task<Boolean> task = mPaymentsClient.isReadyToPay(request);

    task.addOnCompleteListener(
      _task -> {
        try {

          boolean result =
            _task.getResult(ApiException.class);
          if (result) {
            promise.resolve(true);
          } else {
            params.putBoolean("initError", true);
            sendEvent(reactContext, "onError", params);
          }
        } catch (ApiException exception) {
          params.putBoolean("initError", true);
          sendEvent(reactContext, "onError", params);
        }
      });
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
      isReadyToPay(promise, jsonPaymentDataRequest);
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
  public void setLanguageCode(String value, Promise promise) {
    if (paymentInfo == null) {
      promise.reject(PAYMENT_INFO_DOES_NOT_EXIST, PAYMENT_INFO_DOES_NOT_EXIST_DESCRIPTION);
    }
    try {
      paymentInfo.setLanguageCode(value);
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

      WritableMap params = Arguments.createMap();
      params.putInt("status", -1);

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

  @ReactMethod
  public void checkGPayIsEnable(int environment, ReadableArray cardNetworks, final Promise promise) {
    final JSONObject isReadyToPayJson = GPay.getIsReadyToPayRequest(cardNetworks);
    if (isReadyToPayJson == null) {
      promise.reject(NOT_READY_TO_PAY, "not ready to pay");
    }
    IsReadyToPayRequest request = IsReadyToPayRequest.fromJson(isReadyToPayJson.toString());
    if (request == null) {
      promise.reject(NOT_READY_TO_PAY, "not ready to pay");
    }

    Activity activity = getCurrentActivity();

    if (activity == null) {
      promise.reject(E_ACTIVITY_DOES_NOT_EXIST, "Activity doesn't exist");
    }

    PaymentsClient mPaymentsClient =
      Wallet.getPaymentsClient(
        activity,
        new Wallet.WalletOptions.Builder()
          .setEnvironment(environment)
          .build());

    Task<Boolean> task = mPaymentsClient.isReadyToPay(request);
    task.addOnCompleteListener(
      new OnCompleteListener<Boolean>() {
        @Override
        public void onComplete(@NonNull Task<Boolean> task) {
          try {
            boolean result = task.getResult(ApiException.class);
            if (result) {
              promise.resolve(result);
            } else {
              promise.reject(NOT_READY_TO_PAY, "not ready to pay");
            }
          } catch (ApiException exception) {
            promise.reject(E_FAILED_TO_DETECT_IF_READY, exception.getMessage());
          }
        }
      });
  }
}
