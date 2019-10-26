package org.kunicki.scalatest.examples

import org.kunicki.scalatest.BaseSpec
import org.scalatest.prop.TableDrivenPropertyChecks

object TestData {

  val Values = List(
    (2, 2, 4),
    (2, 0, 3),
    (2, 3, 5)
  )
}

class DataSets extends BaseSpec {

  it should "compute sum" in {
    TestData.Values.foreach { case (a, b, c) =>
      a + b shouldBe c
    }
  }
}

class SeparateTests extends BaseSpec {

  TestData.Values.foreach { case (a, b, c) =>
    it should s"compute $a+$b=$c" in {
      a + b shouldBe c
    }
  }
}

class DataTable extends BaseSpec with TableDrivenPropertyChecks {

  it should "compute sum with a table" in {
    val table = Table(
      ("a", "b", "c"),
      TestData.Values: _*
    )

    forAll(table) { case (a, b, c) =>
      a + b shouldBe c
    }
  }
}