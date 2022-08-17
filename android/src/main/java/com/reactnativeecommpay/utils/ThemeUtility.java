package com.reactnativeecommpay.utils;

import android.graphics.Color;

import androidx.annotation.Nullable;

import com.ecommpay.sdk.ECMPTheme;
import com.facebook.react.bridge.ReadableMap;

public class ThemeUtility {

  public static ECMPTheme bind(ReadableMap options, @Nullable Boolean isDark) {
    ECMPTheme theme;

    theme = Boolean.TRUE.equals(isDark) ? ECMPTheme.getDarkTheme() : ECMPTheme.getLightTheme();

    if (options.hasKey("overlayColor")) {
      theme.overlayColor = Color.parseColor(options.getString("overlayColor"));
    }

    if (options.hasKey("statusBarColor")) {
      theme.statusBarColor = Color.parseColor(options.getString("statusBarColor"));
    }

    if (options.hasKey("fullScreenBackgroundColor")) {
      theme.fullScreenBackgroundColor = Color.parseColor(options.getString("fullScreenBackgroundColor"));
    }

    if (options.hasKey("modalBackgroundColor")) {
      theme.modalBackgroundColor = Color.parseColor(options.getString("modalBackgroundColor"));
    }

    if (options.hasKey("headingTextColor")) {
      theme.headingTextColor = Color.parseColor(options.getString("headingTextColor"));
    }

    if (options.hasKey("fieldImageTintColor")) {
      theme.fieldImageTintColor = Color.parseColor(options.getString("fieldImageTintColor"));
    }

    if (options.hasKey("fieldBackgroundColor")) {
      theme.fieldBackgroundColor = Color.parseColor(options.getString("fieldBackgroundColor"));
    }

    if (options.hasKey("fieldUnderlineSelectedColor")) {
      theme.fieldUnderlineSelectedColor = Color.parseColor(options.getString("fieldUnderlineSelectedColor"));
    }

    if (options.hasKey("fieldUnderlineDefaultColor")) {
      theme.fieldUnderlineDefaultColor = Color.parseColor(options.getString("fieldUnderlineDefaultColor"));
    }

    if (options.hasKey("fieldUnderlineErrorColor")) {
      theme.fieldUnderlineErrorColor = Color.parseColor(options.getString("fieldUnderlineErrorColor"));
    }

    if (options.hasKey("navigationBarItemsColor")) {
      theme.navigationBarItemsColor = Color.parseColor(options.getString("navigationBarItemsColor"));
    }

    if (options.hasKey("navigationBarColor")) {
      theme.navigationBarColor = Color.parseColor(options.getString("navigationBarColor"));
    }

    if (options.hasKey("primaryTintColor")) {
      theme.primaryTintColor = Color.parseColor(options.getString("primaryTintColor"));
    }

    if (options.hasKey("secondaryTintColor")) {
      theme.secondaryTintColor = Color.parseColor(options.getString("secondaryTintColor"));
    }

    if (options.hasKey("actionButtonDisableBackgroundColor")) {
      theme.actionButtonDisableBackgroundColor = Color.parseColor(options.getString("actionButtonDisableBackgroundColor"));
    }

    if (options.hasKey("actionButtonDisableTextColor")) {
      theme.actionButtonDisableTextColor = Color.parseColor(options.getString("actionButtonDisableTextColor"));
    }

    if (options.hasKey("actionButtonTextColor")) {
      theme.actionButtonTextColor = Color.parseColor(options.getString("actionButtonTextColor"));
    }

    if (options.hasKey("supportiveTextColor")) {
      theme.supportiveTextColor = Color.parseColor(options.getString("supportiveTextColor"));
    }

    if (options.hasKey("menuTextColor")) {
      theme.menuTextColor = Color.parseColor(options.getString("menuTextColor"));
    }

    if (options.hasKey("wv_disableBackgroundColor")) {
      theme.wv_disableBackgroundColor = Color.parseColor(options.getString("wv_disableBackgroundColor"));
    }

    if (options.hasKey("fieldTextColor")) {
      theme.supportiveTextColor = Color.parseColor(options.getString("fieldTextColor"));
    }

    if (options.hasKey("fieldPlaceholderTextColor")) {
      theme.fieldPlaceholderTextColor = Color.parseColor(options.getString("fieldPlaceholderTextColor"));
    }

    if (options.hasKey("fieldTitleColor")) {
      theme.fieldTitleColor = Color.parseColor(options.getString("fieldTitleColor"));
    }

    if (options.hasKey("wv_navigationBarItemsColor")) {
      theme.wv_navigationBarItemsColor = Color.parseColor(options.getString("wv_navigationBarItemsColor"));
    }

    if (options.hasKey("wv_navigationBarColor")) {
      theme.wv_navigationBarColor = Color.parseColor(options.getString("wv_navigationBarColor"));
    }

    if (options.hasKey("selectorColor")) {
      theme.selectorColor = Color.parseColor(options.getString("selectorColor"));
    }

    if (options.hasKey("selectorBackgroundColor")) {
      theme.selectorBackgroundColor = Color.parseColor(options.getString("selectorBackgroundColor"));
    }

    if (options.hasKey("wv_primaryTintColor")) {
      theme.wv_primaryTintColor = Color.parseColor(options.getString("wv_primaryTintColor"));
    }

    if (options.hasKey("afterEnteringInformationField")) {
      theme.afterEnteringInformationField = Color.parseColor(options.getString("afterEnteringInformationField"));
    }

    if (options.hasKey("lockImageBackground")) {
      theme.lockImageBackground = Color.parseColor(options.getString("lockImageBackground"));
    }

    if (options.hasKey("secureKeyboardTextColor")) {
      theme.secureKeyboardTextColor = Color.parseColor(options.getString("secureKeyboardTextColor"));
    }

    if (options.hasKey("backgroundRedirectViewColor")) {
      theme.backgroundRedirectViewColor = Color.parseColor(options.getString("backgroundRedirectViewColor"));
    }

    if (options.hasKey("wv_backgroundRedirectViewColor")) {
      theme.wv_backgroundRedirectViewColor = Color.parseColor(options.getString("wv_backgroundRedirectViewColor"));
    }

    if (options.hasKey("textPreloaderRedirectViewColor")) {
      theme.textPreloaderRedirectViewColor = Color.parseColor(options.getString("textPreloaderRedirectViewColor"));
    }

    if (options.hasKey("wv_textPreloaderRedirectViewColor")) {
      theme.wv_textPreloaderRedirectViewColor = Color.parseColor(options.getString("wv_textPreloaderRedirectViewColor"));
    }

    if (options.hasKey("loadingScreenAdditionalFieldsColor")) {
      theme.loadingScreenAdditionalFieldsColor = Color.parseColor(options.getString("loadingScreenAdditionalFieldsColor"));
    }

    if (options.hasKey("showShadow")) {
      theme.showShadow = options.getBoolean("showShadow");
    }

    if (options.hasKey("showLightSupportiveLogosOnPayment")) {
      theme.showLightSupportiveLogosOnPayment = options.getBoolean("showLightSupportiveLogosOnPayment");
    }

    if (options.hasKey("isShowNameAPS")) {
      theme.isShowNameAPS = options.getBoolean("isShowNameAPS");
    }

    if (options.hasKey("showLightAPSLogosOnSelection")) {
      theme.showLightAPSLogosOnSelection = options.getBoolean("showLightAPSLogosOnSelection");
    }

    if (options.hasKey("showLightAPSLogosOnPayment")) {
      theme.showLightAPSLogosOnPayment = options.getBoolean("showLightAPSLogosOnPayment");
    }

    if (options.hasKey("lightGooglePayMode")) {
      theme.lightGooglePayMode = options.getBoolean("lightGooglePayMode");
    }

    return theme;
  }
}

