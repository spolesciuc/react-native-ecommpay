export type Maybe<T> = T | null | undefined;

export enum EcommpayPaymentStatus {
  RESULT_SUCCESS = 0,
  RESULT_DECLINE = 100,
  RESULT_CANCELLED = 301,
  RESULT_FAILED = 501,
  INIT_FAILED = 601,
}

export interface EcommpayPaymentResponse {
  status: EcommpayPaymentStatus;
  errorMessage?: string;
}

export interface MerchantInfo {
  merchantName: string;
  merchantId: string;
}

export interface Parameters {
  allowedAuthMethods: string[];
  allowedCardNetworks: string[];
}

export interface Parameters2 {
  gateway: string;
  gatewayMerchantId: string;
}

export interface TokenizationSpecification {
  type: string;
  parameters: Parameters2;
}

export interface AllowedPaymentMethod {
  type: string;
  parameters: Parameters;
  tokenizationSpecification?: TokenizationSpecification;
}

export interface TransactionInfo {
  totalPriceStatus: string;
  totalPrice: string;
  currencyCode: string;
}

export interface PaymentDataRequest {
  apiVersion: number;
  apiVersionMinor: number;
  environment?: string;
  merchantInfo: MerchantInfo;
  allowedPaymentMethods: AllowedPaymentMethod[];
  transactionInfo: TransactionInfo;
}

export interface AndroidTheme {
  overlayColor?: Maybe<string>;
  statusBarColor?: Maybe<string>;
  modalBackgroundColor?: Maybe<string>;
  fullScreenBackgroundColor?: Maybe<string>;
  headingTextColor?: Maybe<string>;
  menuTextColor?: Maybe<string>;
  disableBackgroundColor?: Maybe<string>;
  wv_disableBackgroundColor?: Maybe<string>;
  fieldTextColor?: Maybe<string>;
  fieldPlaceholderTextColor?: Maybe<string>;
  fieldImageTintColor?: Maybe<string>;
  fieldBackgroundColor?: Maybe<string>;
  fieldUnderlineSelectedColor?: Maybe<string>;
  fieldUnderlineDefaultColor?: Maybe<string>;
  fieldUnderlineErrorColor?: Maybe<string>;
  fieldTitleColor?: Maybe<string>;
  navigationBarItemsColor?: Maybe<string>;
  wv_navigationBarItemsColor?: Maybe<string>;
  navigationBarColor?: Maybe<string>;
  wv_navigationBarColor?: Maybe<string>;
  selectorColor?: Maybe<string>;
  selectorBackgroundColor?: Maybe<string>;
  primaryTintColor?: Maybe<string>;
  wv_primaryTintColor?: Maybe<string>;
  secondaryTintColor?: Maybe<string>;
  actionButtonDisableBackgroundColor?: Maybe<string>;
  actionButtonDisableTextColor?: Maybe<string>;
  actionButtonTextColor?: Maybe<string>;
  afterEnteringInformationField?: Maybe<string>;
  lockImageBackground?: Maybe<string>;
  supportiveTextColor?: Maybe<string>;
  secureKeyboardTextColor?: Maybe<string>;
  backgroundRedirectViewColor?: Maybe<string>;
  wv_backgroundRedirectViewColor?: Maybe<string>;
  textPreloaderRedirectViewColor?: Maybe<string>;
  wv_textPreloaderRedirectViewColor?: Maybe<string>;
  loadingScreenAdditionalFieldsColor?: Maybe<string>;
  showShadow?: Maybe<boolean>;
  showLightSupportiveLogosOnPayment?: Maybe<boolean>;
  isShowNameAPS?: Maybe<boolean>;
  showLightAPSLogosOnSelection?: Maybe<boolean>;
  showLightAPSLogosOnPayment?: Maybe<boolean>;
  lightGooglePayMode?: Maybe<boolean>;
}

export interface iOSTheme {
  overlayColor?: Maybe<string>;
  backgroundColor?: Maybe<string>;
  headingTextColor?: Maybe<string>;
  menuTextColor?: Maybe<string>;
  fieldTextColor?: Maybe<string>;
  supportiveTextColor?: Maybe<string>;
  fieldPlaceholderTextColor?: Maybe<string>;
  fieldImageTintColor?: Maybe<string>;
  fieldTextCorrectColor?: Maybe<string>;
  fieldTextWrongColor?: Maybe<string>;
  fieldBackgroundColor?: Maybe<string>;
  selectorColor?: Maybe<string>;
  selectorBackgroundColor?: Maybe<string>;
  primaryTintColor?: Maybe<string>;
  secondaryTintColor?: Maybe<string>;
  lineColor?: Maybe<string>;
  actionButtonDisableBackgroundColor?: Maybe<string>;
  actionButtonDisableTextColor?: Maybe<string>;
  actionButtonTextColor?: Maybe<string>;
  searchInputViewPlaceholderColor?: Maybe<string>;
  bankItemTextColor?: Maybe<string>;
  actionButtonBackgroundColor?: Maybe<string>;
  loadingScreenBackground?: Maybe<string>;
  clarificationInfoTextColor?: Maybe<string>;
  clarificationHeaderTextColor?: Maybe<string>;
  clarificationInfoBackgroundColor?: Maybe<string>;
  clarificationInfoCircleColor?: Maybe<string>;
  clarificationPaymentInfoBackgroundColor?: Maybe<string>;
  clarificationInfoTintColor?: Maybe<string>;
  searchIconWrapViewColor?: Maybe<string>;
  searchViewIconColor?: Maybe<string>;
  showSearchViewBorder?: Maybe<boolean>;
  showShadow?: Maybe<boolean>;
  showDarkKeyboard?: Maybe<boolean>;
  showDarkNavigationBar?: Maybe<boolean>;
  showLightLogo?: Maybe<boolean>;
  apsLogoOnly?: Maybe<boolean>;
  showLightAPSLogos?: Maybe<boolean>;
}

export type Theme = AndroidTheme | iOSTheme;

export interface ECMPPaymentInfo {
  projectId: number;
  paymentId: Maybe<string>;
  paymentAmount: number;
  paymentCurrency: string;
  paymentDescription: Maybe<string>;
  customerId: Maybe<string>;
  regionCode: Maybe<string>;
}

export type CallbackResponseType = (response: EcommpayPaymentResponse) => void;

export enum RecurrentType {
  Regular = 'R',
  OneClick = 'C',
  Autopayment = 'U',
}

export enum RecurrentPeriod {
  Day = 'D',
  Week = 'W',
  Month = 'M',
  Quarter = 'Q',
  Year = 'Y',
}

export interface RecurrentInfoSchedule {
  date: string;
  amount: number;
}

export interface RecurrentInfo {
  type: RecurrentType;
  expiryDay?: Maybe<string>;
  expiryMonth?: Maybe<string>;
  expiryYear?: Maybe<string>;
  period?: Maybe<RecurrentPeriod>;
  time?: Maybe<string>;
  startDate?: Maybe<string>;
  scheduledPaymentID?: Maybe<string>;
  amount?: Maybe<number>;
}

export enum ActionType {
  Sale = 1,
  Auth = 2,
  Tokenize = 3,
  Verify = 4,
}
