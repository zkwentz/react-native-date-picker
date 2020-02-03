
describe('Modes', () => {

  before(async () => {
    await device.reloadReactNative()
    await element(by.text('Advanced')).tap()
  })

  it('datetime', async () => {
    await element(by.id('mode/datetime')).tap()

    await expect(element(by.id('day'))).toBeVisible()
    await expect(element(by.id('minutes'))).toBeVisible()
    await expect(element(by.id('hour'))).toBeVisible()

    await expect(element(by.id('month'))).toNotExist()
    await expect(element(by.id('date'))).toNotExist()
    await expect(element(by.id('year'))).toNotExist()
  })

  it('date', async () => {
    await element(by.id('mode/date')).tap()

    await expect(element(by.id('month'))).toBeVisible()
    await expect(element(by.id('date'))).toBeVisible()
    await expect(element(by.id('year'))).toBeVisible()

    await expect(element(by.id('day'))).toNotExist()
    await expect(element(by.id('minutes'))).toNotExist()
    await expect(element(by.id('hour'))).toNotExist()
  })

  it('time', async () => {
    await element(by.id('mode/time')).tap()

    await expect(element(by.id('minutes'))).toBeVisible()
    await expect(element(by.id('hour'))).toBeVisible()

    await expect(element(by.id('day'))).toNotExist()
    await expect(element(by.id('month'))).toNotExist()
    await expect(element(by.id('date'))).toNotExist()
    await expect(element(by.id('year'))).toNotExist()
  })

})
