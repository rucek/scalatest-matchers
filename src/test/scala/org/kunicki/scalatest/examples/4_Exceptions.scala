package org.kunicki.scalatest.examples

import org.kunicki.scalatest.BaseSpec
import Exceptions._

object Exceptions {

  case class SomeException(error: String) extends Exception(error)

  def misbehave = throw SomeException("""¯\_(ツ)_/¯""")
}

class ExceptionWasThrown extends BaseSpec {

  it should "check for exceptions with a flag" in {
    var exceptionCaught = false

    try {
      misbehave
    } catch {
      case _: SomeException => exceptionCaught = true
    }

    exceptionCaught shouldBe true
  }

  //region assertThrows
  it should "check for exceptions with assertThrows" in {
    assertThrows[SomeException](misbehave)
  }
  //endregion

  //region thrownBy
  it should "check for exceptions with thrownBy" in {
    a [SomeException] should be thrownBy misbehave
  }
  //endregion

  //region noException
  it should "check for exceptions with noException" in {
    noException should be thrownBy misbehave
  }
  //endregion
}

class ExceptionHasMessage extends BaseSpec {

  it should "get the exception with intercept" in {
    val thrown = intercept[SomeException](misbehave)
    thrown.getMessage shouldBe "foo"
  }

  //region thrownBy
  it should "get the exception with thrownBy" in {
    val thrown = the [SomeException] thrownBy misbehave
    thrown.getMessage shouldBe "foo"
  }
  //endregion

  //region have message
  it should "check the exception message with thrownBy and have" in {
    the [SomeException] thrownBy misbehave should have message "foo"
  }
  //endregion
}