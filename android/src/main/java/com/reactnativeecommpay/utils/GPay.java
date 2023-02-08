package com.reactnativeecommpay.utils;

import android.util.Log;

import com.facebook.react.bridge.ReadableArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GPay {

    /**
     * Create a Google Pay API base request object with properties used in all requests
     *
     * @return Google Pay API base request object
     * @throws JSONException
     */
    private static JSONObject getBaseRequest() throws JSONException {
        return new JSONObject()
                .put("apiVersion", 2)
                .put("apiVersionMinor", 0);
    }


    /**
     * Card authentication methods supported by your app and your gateway
     *
     * @return allowed card authentication methods
     * @see <a
     * href="https://developers.google.com/pay/api/android/reference/object#CardParameters">CardParameters</a>
     */
    private static JSONArray getAllowedCardAuthMethods() {
        return new JSONArray()
                //.put("CRYPTOGRAM_3DS")
                .put("PAN_ONLY");
    }

    /**
     * Card networks supported by your app and your gateway
     *
     * @return allowed card networks
     * @see <a
     * href="https://developers.google.com/pay/api/android/reference/object#CardParameters">CardParameters</a>
     */
    private static JSONArray getAllowedCardNetworks(ReadableArray cardNetworks) {

        JSONArray jsonArray = new JSONArray();

        for (Object value : cardNetworks.toArrayList()) {
            jsonArray.put(value.toString());
        }

        return jsonArray;
    }

    /**
     * Describe your app's support for the CARD payment method
     *
     * <p>The provided properties are applicable to both an IsReadyToPayRequest and a
     * PaymentDataRequest
     *
     * @return a CARD PaymentMethod object describing accepted cards
     * @throws JSONException
     * @see <a
     * href="https://developers.google.com/pay/api/android/reference/object#PaymentMethod">PaymentMethod</a>
     */
    private static JSONObject getBaseCardPaymentMethod(ReadableArray cardNetworks) throws JSONException {
        JSONObject cardPaymentMethod = new JSONObject();
        cardPaymentMethod.put("type", "CARD");
        cardPaymentMethod.put(
                "parameters",
                new JSONObject()
                        .put("allowedAuthMethods", GPay.getAllowedCardAuthMethods())
                        .put("allowedCardNetworks",  GPay.getAllowedCardNetworks(cardNetworks)));

        return cardPaymentMethod;
    }

    /**
     * An object describing accepted forms of payment by your app, used to determine a viewer's
     * readiness to pay
     *
     * @return API version and payment methods supported by the app
     * @see <a
     * href="https://developers.google.com/pay/api/android/reference/object#IsReadyToPayRequest">IsReadyToPayRequest</a>
     */
    public static JSONObject getIsReadyToPayRequest(ReadableArray cardNetworks) {
        try {
            JSONObject isReadyToPayRequest = GPay.getBaseRequest();
            isReadyToPayRequest.put(
                    "allowedPaymentMethods", new JSONArray().put(getBaseCardPaymentMethod(cardNetworks)));
            return isReadyToPayRequest;
        } catch (JSONException e) {
            Log.e("getIsReadyToPayRequest", e.toString());
            return null;
        }
    }
}
