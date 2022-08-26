import {
  ActionType,
  CallbackResponseType,
  ECMPPaymentInfo,
  EcommpayPaymentResponse,
  PaymentDataRequest,
  RecurrentInfo,
  RecurrentInfoSchedule,
  Theme,
} from './types';
import { Platform } from 'react-native';
import EcommpayNative, { EcommpayModuleEvt } from './module';

export function createPayment(info: ECMPPaymentInfo): Promise<boolean> {
  return EcommpayNative.createPayment(info);
}

export function setTheme(
  theme: Theme,
  isDark: boolean = false
): Promise<boolean> {
  return EcommpayNative.setTheme(theme, isDark);
}

export function setRecurrent(
  recurrent: RecurrentInfo,
  schedules: Array<RecurrentInfoSchedule> = []
): Promise<boolean> {
  return EcommpayNative.setRecurrent(recurrent, schedules);
}

export function getParamsForSignature(): Promise<string> {
  return EcommpayNative.getParamsForSignature();
}

export function setForcePaymentMethod(method: string): Promise<string> {
  return EcommpayNative.setForcePaymentMethod(method);
}

export function setMerchantID(merchantID: string): Promise<string> {
  return EcommpayNative.setMerchantID(merchantID);
}

export function setPaymentDataRequest(
  paymentDataRequest: PaymentDataRequest
): Promise<PaymentDataRequest> {
  switch (Platform.OS) {
    case 'android': {
      const jsonDataRequest = JSON.stringify(paymentDataRequest);
      return EcommpayNative.setPaymentDataRequest(jsonDataRequest);
    }
    default: {
      throw new Error(
        'The method is only available for devices with Android OS'
      );
    }
  }
}

export function addEcmpScreenDisplayMode(
  ecmpScreenDisplayMode: string
): Promise<string> {
  return EcommpayNative.addEcmpScreenDisplayMode(ecmpScreenDisplayMode);
}

export function setSignature(signature: string): Promise<string> {
  return EcommpayNative.setSignature(signature);
}

export function setAction(action: ActionType): Promise<string> {
  return EcommpayNative.setAction(+action);
}

export function setHideSavedWallets(value: boolean): Promise<boolean> {
  return EcommpayNative.setHideSavedWallets(value);
}

export function setLanguageCode(value: string): Promise<string> {
  return EcommpayNative.setLanguageCode(value);
}

export function presentPayment(
  onSuccess?: CallbackResponseType,
  onError?: CallbackResponseType,
  onCancel?: CallbackResponseType
): Promise<string> {
  callbackReceiver(onSuccess, onError, onCancel);
  return EcommpayNative.presentPayment();
}

export function setApplePayDescription(
  description: string
): Promise<PaymentDataRequest> {
  switch (Platform.OS) {
    case 'ios': {
      return EcommpayNative.setApplePayDescription(description);
    }
    default: {
      throw new Error('The method is only available for devices with iOS');
    }
  }
}

function clearListeners() {
  EcommpayModuleEvt.removeAllListeners('onSuccess');
  EcommpayModuleEvt.removeAllListeners('onError');
  EcommpayModuleEvt.removeAllListeners('onCancel');
}

function callbackReceiver(
  onSuccess?: CallbackResponseType,
  onError?: CallbackResponseType,
  onCancel?: CallbackResponseType
) {
  EcommpayModuleEvt.addListener(
    'onSuccess',
    (data: EcommpayPaymentResponse) => {
      if (__DEV__) {
        console.log('EcommpayModuleEvt.addListener("onSuccess")', data);
      }
      clearListeners();
      onSuccess && onSuccess(data);
    }
  );

  EcommpayModuleEvt.addListener('onError', (data: EcommpayPaymentResponse) => {
    if (__DEV__) {
      console.log('EcommpayModuleEvt.addListener("onError")', data);
    }
    clearListeners();
    onError && onError(data);
  });

  EcommpayModuleEvt.addListener('onCancel', (data: EcommpayPaymentResponse) => {
    if (__DEV__) {
      console.log('EcommpayModuleEvt.addListener("onCancel")', data);
    }
    clearListeners();
    onCancel && onCancel(data);
  });
}
