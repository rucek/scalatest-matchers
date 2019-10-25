package org.kunicki.scalatest.fixtures

import org.scalatest.FlatSpec

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class NotificationServiceCustomMethodSpec extends FlatSpec with Goodies {

  case class Fixture(user: User, notificationService: NotificationService) {

    def findUserNotification: Future[Option[Notification]] =
      notificationService.findByUserId(user.id)
  }

  def withNotificationService(prefix: String = "[TEST]")(test: Fixture => Unit): Unit = {
    val user = User("42", UserService.Email)
    val userService = new UserService
    val notificationRepository = new NotificationRepository
    val notificationService = new NotificationService(prefix, userService, notificationRepository)

    try {
      test(Fixture(user, notificationService))
    } finally {
      notificationRepository.shutdown()
    }
  }

  it should "create a notification" in withNotificationService("[FOO]") { f =>
    // given
    import f._

    // when
    notificationService.notify(user.id, "Hello, Joker")

    // then
    eventually {
      f.findUserNotification.futureValue.value.message shouldBe "[FOO] Hello, Joker"
    }
  }

  it should "do something else" in withNotificationService() { f =>
    // given

    // when
    f.notificationService.doSomethingElse(f.user.id)

    // then
    succeed
  }
}
