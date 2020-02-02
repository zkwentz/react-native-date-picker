
const expectDate = async (date) => {
  await expect(element(by.id('dateOutput'))).toHaveText(date)
  await reset()
}

const reset = () => element(by.id('reset')).tap()

const scrollWheel = async (index) => {
  await element(by.id('props/scroll')).tap()
  await element(by.id('wheelIndex')).replaceText(`${index}`)
  await element(by.id('scrollTimes')).replaceText("1")
  await element(by.id('doScroll')).tap()
}


const changeProp = name => async value => {
  await element(by.id('propName')).replaceText(name)
  await element(by.id('propValue')).replaceText(value)
  await element(by.id('changeProp')).tap()
}

const setLocale = changeProp("locale")
const setMinimumDate = changeProp("minimumDate")
const setMaximumDate = changeProp("maximumDate")

const setMode = changeProp("mode")

const scrollWheelWithIndexAndExpectDate = async (index, expectedDate) => {
  await scrollWheel(index)
  await expectDate(expectedDate)
}

describe('Wheel order', () => {


  describe('datetime', () => {

    before(async () => {
      await setMaximumDate("undefined")
      // await setMinimumDate("undefined")
      await setMode("datetime")
    })

    it('US', async () => {
      await setLocale("en-US")
      await scrollWheelWithIndexAndExpectDate(0, "2000-01-02 00:00:00")
      await scrollWheelWithIndexAndExpectDate(1, "2000-01-01 01:00:00")
      await scrollWheelWithIndexAndExpectDate(2, "2000-01-01 00:01:00")
      await scrollWheelWithIndexAndExpectDate(3, "2000-01-01 12:00:00")
    })

    it('Korean', async () => {
      await setLocale("ko-KR")
      await scrollWheelWithIndexAndExpectDate(0, "2000-01-02 00:00:00")
      await scrollWheelWithIndexAndExpectDate(1, "2000-01-01 12:00:00")
      await scrollWheelWithIndexAndExpectDate(2, "2000-01-01 01:00:00")
      await scrollWheelWithIndexAndExpectDate(3, "2000-01-01 00:01:00")
    })

  })


  describe('date', () => {

    before(async () => {
      await setMode("date")
    })

    it('US', async () => {
      await setLocale("en-US")
      await scrollWheelWithIndexAndExpectDate(0, "2000-02-01 00:00:00")
      await scrollWheelWithIndexAndExpectDate(1, "2000-01-02 00:00:00")
      await scrollWheelWithIndexAndExpectDate(2, "2001-01-01 00:00:00")
    })

    it('UK', async () => {
      await setLocale("en-GB")
      await scrollWheelWithIndexAndExpectDate(0, "2000-01-02 00:00:00")
      await scrollWheelWithIndexAndExpectDate(1, "2000-02-01 00:00:00")
      await scrollWheelWithIndexAndExpectDate(2, "2001-01-01 00:00:00")
    })

    it('Korean', async () => {

    })

  })

  describe('time', () => {

    it('US', async () => {

    })

    it('UK', async () => {

    })

    it('Korean', async () => {

    })

  })


})
