package org.kunicki.scalatest.examples

import org.kunicki.scalatest.BaseSpec
import org.kunicki.scalatest.examples.Collections._
import org.scalatest.Inspectors._
import org.scalatest.{LoneElement, OptionValues}

object Collections {

  val one = List(1)
  val numbers = List(2, 4, 5, 6)
  val options = List(Some(1), None, Some(3))
}

class Emptiness extends BaseSpec {

  it should "check for emptiness with isEmpty" in {
    numbers.isEmpty shouldBe true
  }

  //region empty word
  it should "check for emptiness with empty" in {
    numbers shouldBe empty
  }
  //endregion
}

class Contains extends BaseSpec {

  it should "check for element with contains" in {
    numbers.contains(3) shouldBe true
  }

  //region contain word
  it should "check for element with contain" in {
    numbers should contain(3)
  }
  //endregion
}

class SingleElement extends BaseSpec with LoneElement {

  it should "check the only element with size and head" in {
    one should have size 1
    one.head shouldBe 1
  }

  //region loneElement
  it should "check the only element with loneElement" in {
    one.loneElement shouldBe 2
  }
  //endregion
}

class CompareToOther extends BaseSpec {

  it should "compare to another collection" in {
    numbers shouldBe Set(2, 4, 5, 6)
  }

  //region same elements
  it should "check elements" in {
    numbers should contain theSameElementsAs Set(2, 4, 5, 6)
  }
  //endregion

  //region in order
  it should "check elements in order" in {
    numbers should contain theSameElementsInOrderAs List(4, 2, 5, 6)
  }
  //endregion
}

class CheckAllElements extends BaseSpec with OptionValues {

  it should "check all elements with foreach" in {
    options.foreach(_.value shouldBe 1)
  }

  //region forAll
  it should "check all elements with forAll" in {
    forAll(options)(_.value shouldBe 1)
  }
  //endregion

  //region forEvery
  it should "check all elements with forEvery" in {
    forEvery(options)(_.value shouldBe 1)
  }
  //endregion
}
