const { scrollWheel, expectDate } = require("../utils")

describe('scroll around', () => {

    before(async () => {
        await device.reloadReactNative()
        await element(by.text('Advanced')).tap()
    })

    it('Hour wheel should scroll all way around and switch AM/PM when passing 12', async () => {
        await scrollWheel(1, 5)
        await expectDate("2000-01-01 05:00:00")
        await scrollWheel(1, 5)
        await expectDate("2000-01-01 10:00:00")
        await scrollWheel(1, 5)
        await expectDate("2000-01-01 15:00:00")
        await scrollWheel(1, 5)
        await expectDate("2000-01-01 20:00:00")
        await scrollWheel(1, 5)
        await expectDate("2000-01-01 01:00:00")
    })

    it('Minute wheel should be possible to scroll all way around', async () => {
        await scrollWheel(2, 55)
        await expectDate("2000-01-01 00:55:00")
        await scrollWheel(2, 10)
        await expectDate("2000-01-01 00:05:00")
    })

    it('Day wheel should change year when passing new year', async () => {
        await scrollWheel(0, -1)
        await expectDate("1999-12-31 00:00:00")
        await scrollWheel(0, 1)
        await expectDate("2000-01-01 00:00:00")
    })

})