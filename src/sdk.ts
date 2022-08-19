import { Maybe } from './types';

class EcommpaySDK {
  private currentInstanceId: Maybe<string> = null;

  constructor() {
    this.currentInstanceId = EcommpaySDK.generateInstanceId();
  }

  private static generateInstanceId() {
    return String(+Date.now());
  }

  public bind() {
    console.log(this.currentInstanceId, '@curr');
  }
}

export default EcommpaySDK;
