const { scrollWheel, expectDate, setMinuteInterval } = require("../utils")

const scrollMinuteWheel = () => scrollWheel(2, 1)

describe('minute interval', () => {

    it('1 minute (default)', async () => {
        await setMinuteInterval(1)
        await scrollMinuteWheel()
        await expectDate("2000-01-01 00:01:00")
    })

    it('5 minutes', async () => {
        await setMinuteInterval(5)
        await scrollMinuteWheel()
        await expectDate("2000-01-01 00:05:00")
    })

    it('15 minutes', async () => {
        await setMinuteInterval(15)
        await scrollMinuteWheel()
        await expectDate("2000-01-01 00:15:00")
        await scrollMinuteWheel()
        await expectDate("2000-01-01 00:30:00")
        await scrollMinuteWheel()
        await expectDate("2000-01-01 00:45:00")
    })

})