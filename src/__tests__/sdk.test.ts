import EcommpaySDK from '../sdk';

describe('EcommpaySDK', () => {
  it('bind', () => {
    const sdk = new EcommpaySDK();

    sdk.bind();

    // expect(getByTestId('icon')).toBeTruthy();
  });
});
