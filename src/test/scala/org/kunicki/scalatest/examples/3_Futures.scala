package org.kunicki.scalatest.examples

import org.kunicki.scalatest.BaseSpec
import org.scalatest.concurrent.{IntegrationPatience, ScalaFutures}
import org.scalatest.flatspec.AsyncFlatSpec
import org.scalatest.matchers.should.Matchers

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}

class Futures extends BaseSpec with ScalaFutures with IntegrationPatience {

  def future: Future[Int] = Future {
    Thread.sleep(1000)
    1
  }(ExecutionContext.global)

  it should "check future value with Await" in {
    Await.result(future, Duration.Inf) shouldBe 1
  }

  //region future value
  it should "check future value with futureValue" in {
    future.futureValue shouldBe 1
  }
  //endregion
}

class FuturesWithAsync extends AsyncFlatSpec with Matchers with ScalaFutures {

  it should "check future value with AsyncFlatSpec" in {
    Future(1).map(_ shouldBe 1)
  }

  //region surprise
  it should "check future value with futureValue" in {
    Future(2)(ExecutionContext.global).futureValue shouldBe 2
  }
  //endregion
}