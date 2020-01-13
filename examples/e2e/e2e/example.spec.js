describe("Example", () => {
  beforeEach(async () => {
    await device.reloadReactNative();
  });

  it("should show today", async () => {
    await element(by.text("Advanced")).tap();
    await expect(element(by.text("mode"))).toBeVisible();
  });
});
