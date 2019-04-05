package org.kunicki.scalatest.examples

import org.kunicki.scalatest.BaseSpec
import org.scalatest.{EitherValues, OptionValues}
import OptionAndEithers._

object OptionAndEithers {

  val option: Option[Int] = None
  val either: Either[String, Int] = Left("Error")
}

class OptionIsDefined extends BaseSpec {

  it should "check if an Option is defined with isDefined" in {
    option.isDefined shouldBe true
  }

  //region defined word
  it should "check if an Option is not empty with defined" in {
    option shouldBe defined
  }
  //endregion
}

class OptionHasValue extends BaseSpec with OptionValues {

  it should "check Option value with get" in {
    option.get shouldBe 1
  }

  //region option value
  it should "check Option value with value" in {
    option.value shouldBe 1
  }
  //endregion

  //region comparing to Some
  it should "check Option value by comparing to Some" in {
    option shouldBe Some(1)
  }
  //endregion
}

class EitherHasValue extends BaseSpec with EitherValues {

  it should "check if an Either is right with get" in {
    either.right.get shouldBe 1
  }

  //region either value
  it should "check if an Either is right with value" in {
    either.right.value shouldBe 1
  }
  //endregion

  //region comparing to Right
  it should "check if an Either is right by comparing to Right" in {
    either shouldBe Right(1)
  }
  //endregion
}
