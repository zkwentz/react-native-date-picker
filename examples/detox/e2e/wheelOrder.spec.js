
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


describe('Wheel order', () => {

  describe('datetime', async () => {

    it('US', async () => {
      await scrollWheel(0)
      await expectDate("2000-01-02 00:00:00")

      await scrollWheel(1)
      await expectDate("2000-01-01 01:00:00")

      await scrollWheel(2)
      await expectDate("2000-01-01 00:01:00")

      await scrollWheel(3)
      await expectDate("2000-01-01 12:00:00")
    })

    it('Korean', async () => {
      await scrollWheel(0)
      await expectDate("2000-01-02 00:00:00")

      await scrollWheel(1)
      await expectDate("2000-01-01 12:00:00")

      await scrollWheel(2)
      await expectDate("2000-01-01 01:00:00")

      await scrollWheel(3)
      await expectDate("2000-01-01 00:01:00")
    })

  })


  describe('date', () => {

    it('US', () => {

    })

    it('UK', () => {

    })

    it('Korean', () => {

    })

  })

  describe('time', () => {

    it('US', () => {

    })

    it('UK', () => {

    })

    it('Korean', () => {

    })

  })


})
