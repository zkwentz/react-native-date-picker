const detox = require('detox')
const config = require('../package.json').detox
const adapter = require('detox/runners/mocha/adapter')
const { reset } = require("./utils")

before(async () => {
  await detox.init(config)
  await element(by.text('Advanced')).tap()
})

beforeEach(async function () {
  await adapter.beforeEach(this)
  await reset()
})

afterEach(async function () {
  await adapter.afterEach(this)
})

after(async () => {
  await detox.cleanup()
})
