import * as React from 'react';
import {
  Platform,
  StyleSheet,
  Text,
  TouchableOpacity,
  View,
} from 'react-native';
import Ecommpay, {
  ActionType,
  ECMPPaymentInfo,
  PaymentDataRequest,
  RecurrentType,
} from 'react-native-ecommpay';

export default function App() {
  const generatePaymentRequest = React.useCallback(
    (paymentAmount: number, paymentCurrency: string): PaymentDataRequest => {
      return {
        apiVersion: 2,
        apiVersionMinor: 0,
        merchantInfo: {
          merchantName: 'ECOMMPAY_MERCHANT_NAME',
          merchantId: 'ECOMMPAY_MERCHANT_ID_ANDROID',
        },
        allowedPaymentMethods: [
          {
            type: 'CARD',
            parameters: {
              allowedCardNetworks: ['VISA', 'MASTERCARD'],
              allowedAuthMethods: ['PAN_ONLY', 'CRYPTOGRAM_3DS'],
            },
            tokenizationSpecification: {
              type: 'PAYMENT_GATEWAY',
              parameters: {
                gateway: 'ecommpay',
                gatewayMerchantId: 'ecommpay',
              },
            },
          },
        ],
        transactionInfo: {
          totalPriceStatus: 'FINAL',
          totalPrice: paymentAmount.toString(),
          currencyCode: paymentCurrency,
        },
      };
    },
    []
  );

  const onPressCreatePayment = React.useCallback(() => {
    (async () => {
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

      await Ecommpay.createPayment(info);

      await Ecommpay.setAction(ActionType.Verify);

      await Ecommpay.setTheme({
        actionButtonBackgroundColor: '#fff',
        showShadow: true,
      });

      const signature = await Ecommpay.getParamsForSignature();
      console.log(signature, '@signature');

      await Ecommpay.setForcePaymentMethod('alipay');
      await Ecommpay.setMerchantID('com.merchant.test');

      await Ecommpay.setRecurrent(
        {
          type: RecurrentType.OneClick,
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

      await Ecommpay.setSignature('generated signature');

      await Ecommpay.addEcmpScreenDisplayMode('hide_decline_final_page');
      await Ecommpay.addEcmpScreenDisplayMode('hide_success_final_page');

      switch (Platform.OS) {
        case 'android': {
          await Ecommpay.setPaymentDataRequest(
            generatePaymentRequest(paymentAmount, paymentCurrency)
          );
          break;
        }
        case 'ios': {
          await Ecommpay.setApplePayDescription('description');
          break;
        }
      }

      console.log('Present payment form');

      await Ecommpay.presentPayment();
    })();
  }, [generatePaymentRequest]);

  return (
    <View style={styles.container}>
      <TouchableOpacity onPress={onPressCreatePayment}>
        <Text>createPayment</Text>
      </TouchableOpacity>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
  box: {
    width: 60,
    height: 60,
    marginVertical: 20,
  },
});
