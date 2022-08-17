# react-native-ecommpay


The plugin for Ecommpay SDK is a wrapper for the native Android and iOS SDKs and supports the following native SDK versions:

* iOS: 1.15.0
* Android: 1.15.0

## Installation

```sh
npm install react-native-ecommpay
```
or
```sh
yarn add react-native-ecommpay
```

## Usage

```ts
import {
  addEcmpScreenDisplayMode,
  createPayment,
  ECMPPaymentInfo,
  getParamsForSignature,
  PaymentDataRequest,
  presentPayment,
  RecurrentType,
  setApplePayDescription,
  setForcePaymentMethod,
  setMerchantID,
  setPaymentDataRequest,
  setRecurrent,
  setSignature,
  setTheme,
} from 'react-native-ecommpay';

// ...

const paymentAmount = 100;
const paymentCurrency = 'USD';

const info: ECMPPaymentInfo = {
  projectId: 1,
  paymentId: '1',
  paymentAmount: paymentAmount,
  paymentCurrency: paymentCurrency,
  customerId: '1',
  paymentDescription: 'test',
  regionCode: null,
};

await createPayment(info);

await setTheme({
  actionButtonBackgroundColor: '#fff',
  showShadow: true,
});

const signature = await getParamsForSignature();
console.log(signature, '@signature');

await setForcePaymentMethod('alipay');
await setMerchantID('com.merchant.test');

await setRecurrent(
  {
    type: RecurrentType.Regular,
    amount: 1000,
    scheduledPaymentID: '100',
  },
  [
    {
      amount: 1500,
      date: '12-08-2021',
    },
  ]
);

await setSignature('generated signature');

await addEcmpScreenDisplayMode('hide_decline_final_page');
await addEcmpScreenDisplayMode('hide_success_final_page');

switch (Platform.OS) {
  case 'android': {
    await setPaymentDataRequest(
      generatePaymentRequest(paymentAmount, paymentCurrency)
    );
    break;
  }
  case 'ios': {
    await setApplePayDescription('description');
    break;
  }
}

console.log('Present payment form');

await presentPayment();
```


## Importing libraries

**Importing libraries in your project**


#### iOS

1. Install cocoa pods: ```cd ios && pod install```


2. Add key NSCameraUsageDescription with value `permission is needed in order to scan card` to the Info.plist file.


3. If your iOS app does not use user location information, add the NSLocationWhenInUseUsageDescription key with the `fraud prevention` value in the Info.plist file.

    The ECommPay libraries code does not request user location, if the request is not initiated by the host app, but App Store requires that the NSLocationWhenInUseUsageDescription key value is not empty.

    If your iOS app requests user location information, you can skip this step.


4.  If the iOS app does not have permission to save data on the mobile device, add Privacy - Photo Library Usage Description and Privacy - Photo Library Additions Usage Description keys with values to the Info.plist file. The values specified are shown to the customer in the permission request message.


#### Android

1. Open your application module (build.gradle);
2. In the `android {}` section, add the following compilation parameters:

```
compileOptions {
    sourceCompatibility JavaVersion.VERSION_1_8
    targetCompatibility JavaVersion.VERSION_1_8
}
```

3. In the `dependencies {}` section, add the following dependencies:

```
//Ecommapy SDK
implementation project(':reactnativeecommpay')

//Retrofit
implementation 'com.squareup.retrofit2:retrofit:2.3.0'
implementation 'com.squareup.okhttp3:logging-interceptor:3.10.0'
implementation 'com.squareup.retrofit2:converter-gson:2.3.0'

//Android
implementation 'io.card:android-sdk:5.5.1'
implementation 'androidx.appcompat:appcompat:1.0.0'
implementation 'androidx.legacy:legacy-support-v4:1.0.0'
implementation 'androidx.recyclerview:recyclerview:1.0.0'
implementation 'com.google.code.gson:gson:2.8.4'
implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlin_version"
implementation 'androidx.lifecycle:lifecycle-viewmodel:2.0.0'
implementation 'androidx.lifecycle:lifecycle-extensions:2.0.0'
implementation 'com.google.android.gms:play-services-wallet:18.1.3'
kapt 'androidx.lifecycle:lifecycle-compiler:2.0.0'
```
4. Add the following permissions in the `AndroidManifest.xml` file:
```
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
```

## Contributing

See the [contributing guide](CONTRIBUTING.md) to learn how to contribute to the repository and the development workflow.

## License

MIT
