package org.kunicki.scalatest.fixtures

import org.scalatest.{Outcome, fixture}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class NotificationServiceFixtureSpec extends fixture.FlatSpec with Goodies {

  case class FixtureParam(user: User, message: String, notificationService: NotificationService, notificationRepository: NotificationRepository) {

    def findUserNotification: Future[Option[Notification]] = notificationRepository.findByUser(user)
  }

  override protected def withFixture(test: OneArgTest): Outcome = {
    val user = User("42", UserService.Email)
    val message = "Hello, Piter!"
    val userService = new UserService
    val notificationRepository = new NotificationRepository
    val notificationService = new NotificationService(userService, notificationRepository)

    val fixtureParam = FixtureParam(user, message, notificationService, notificationRepository)

    try {
      super.withFixture(test.toNoArgTest(fixtureParam))
    } finally {
      notificationRepository.shutdown()
    }
  }

  it should "create a notification" in { implicit f =>
    // given

    // when
    notify()

    // then
    eventually {
      f.findUserNotification.futureValue.value.message shouldBe f.message
    }
  }

  private def notify()(implicit f: FixtureParam): Unit = f.notificationService.notify(f.user.id, f.message).futureValue
}
