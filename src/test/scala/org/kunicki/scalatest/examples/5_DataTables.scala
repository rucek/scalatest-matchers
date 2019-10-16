package org.kunicki.scalatest.examples

import org.kunicki.scalatest.BaseSpec
import org.scalatest.prop.TableDrivenPropertyChecks

class DataSets extends BaseSpec {

  private val data = List(
    (2, 2, 4),
    (2, 0, 3),
    (2, 3, 5)
  )

  it should "compute sum" in {
    data.foreach { case (a, b, c) =>
      a + b shouldBe c
    }
  }
}

class SeparateTests extends BaseSpec {

  private val data = List(
    (2, 2, 4),
    (2, 0, 3),
    (2, 3, 5)
  )

  data.foreach { case (a, b, c) =>
    it should s"compute $a + $b = $c" in {
      a + b shouldBe c
    }
  }
}

class DataTable extends BaseSpec with TableDrivenPropertyChecks {

  private val dataTable = Table(
    ("a", "b", "c"),
    (2, 2, 4),
    (2, 0, 3),
    (2, 3, 5)
  )

  it should "compute sum with a table" in {
    forAll(dataTable) { (a, b, c) =>
      a + b shouldBe c
    }
  }
}
