package org.kunicki.scalatest.fixtures

import org.scalatest.{Outcome, fixture}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class NotificationServiceFixtureSpec extends fixture.FlatSpec with Goodies {

  case class FixtureParam(user: User, notificationService: NotificationService) {

    def findUserNotification: Future[Option[Notification]] =
      notificationService.findByUserId(user.id)
  }

  override protected def withFixture(test: OneArgTest): Outcome = {
    val user = User("42", UserService.Email)
    val userService = new UserService
    val notificationRepository = new NotificationRepository
    val notificationService = new NotificationService("[TEST]", userService, notificationRepository)

    try {
      withFixture(test.toNoArgTest(FixtureParam(user, notificationService)))
    } finally {
      notificationRepository.shutdown()
    }
  }

  it should "create a notification" in { f =>
    // given
    import f._

    // when
    notificationService.notify(user.id, "Hello, Joker")

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
    f.notificationService.doSomethingElse(f.user.id)
}
