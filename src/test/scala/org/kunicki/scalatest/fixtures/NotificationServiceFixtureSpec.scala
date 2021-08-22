package org.kunicki.scalatest.fixtures

import org.scalatest.Outcome
import org.scalatest.flatspec.FixtureAnyFlatSpec

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class NotificationServiceFixtureSpec extends FixtureAnyFlatSpec with Goodies {

  case class FixtureParam(userId: String, notificationService: NotificationService) {

    def findUserNotification: Future[Option[Notification]] =
      notificationService.findByUserId(userId)
  }

  override protected def withFixture(test: OneArgTest): Outcome = {
    val userService = new UserService
    val notificationRepository = new NotificationRepository
    val notificationService = new NotificationService("[TEST]", userService, notificationRepository)

    try {
      withFixture(test.toNoArgTest(FixtureParam("42", notificationService)))
    } finally {
      notificationRepository.shutdown()
    }
  }

  it should "create a notification" in { f =>
    // given
    import f._

    // when
    notificationService.notify(userId, "Hello, Joker")

    // then
    eventually {
      findUserNotification.futureValue.value.message shouldBe "[TEST] Hello, Joker"
    }
  }

  it should "do something else" in { implicit f =>
    // given

    // when
    doSomethingElse()

    // then
    succeed
  }

  private def doSomethingElse()(implicit f: FixtureParam): Future[Unit] =
    f.notificationService.doSomethingElse(f.userId)
}
