describe('Example', () => {
  beforeEach(async () => {
    await device.reloadReactNative()
  })

  it('first', async () => {
    await element(by.text('Advanced')).tap()
    await expect(element(by.text('mode'))).toBeVisible()
  })

})
