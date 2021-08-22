package org.kunicki.scalatest.fixtures

import org.scalatest.flatspec.AnyFlatSpec

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class NotificationServiceCustomMethodSpec extends AnyFlatSpec with Goodies {

  case class Fixture(userId: String, notificationService: NotificationService) {

    def findUserNotification: Future[Option[Notification]] =
      notificationService.findByUserId(userId)
  }

  def withNotificationService(prefix: String = "[TEST]")(test: Fixture => Unit): Unit = {
    val userService = new UserService
    val notificationRepository = new NotificationRepository
    val notificationService = new NotificationService(prefix, userService, notificationRepository)

    try {
      test(Fixture("42", notificationService))
    } finally {
      notificationRepository.shutdown()
    }
  }

  it should "create a notification" in withNotificationService("[FOO]") { f =>
    // given
    import f._

    // when
    notificationService.notify(userId, "Hello, Joker")

    // then
    eventually {
      f.findUserNotification.futureValue.value.message shouldBe "[FOO] Hello, Joker"
    }
  }

  it should "do something else" in withNotificationService() { f =>
    // given

    // when
    f.notificationService.doSomethingElse(f.userId)

    // then
    succeed
  }
}
