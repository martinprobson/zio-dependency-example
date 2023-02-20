package net.martinprobson.example

import zio.test.*

object ExampleSpec extends ZIOSpecDefault:

  def spec = suite("clock")(
    test("foo") {
      assertTrue(true)
    },
    test("bar") {
      assertTrue(true)
    },
    test("foo bar") {
      assertTrue(2 == 2)
    }
  )
end ExampleSpec
