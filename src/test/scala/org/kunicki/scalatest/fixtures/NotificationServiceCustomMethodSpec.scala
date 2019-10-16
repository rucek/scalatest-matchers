package org.kunicki.scalatest.fixtures

import org.scalatest.FlatSpec

import scala.concurrent.ExecutionContext.Implicits.global

class NotificationServiceCustomMethodSpec extends FlatSpec with Goodies {

  type Fixture = (User, String, NotificationService, NotificationRepository)

  def testNotificationService()(test: Fixture => Unit): Unit = {
    val user = User("42", UserService.Email)
    val message = "Hello, Piter!"
    val userService = new UserService
    val notificationRepository = new NotificationRepository
    val notificationService = new NotificationService(userService, notificationRepository)

    try {
      test((user, message, notificationService, notificationRepository))
    } finally {
      notificationRepository.shutdown()
    }
  }

  it should "create a notification" in testNotificationService() { fixture =>
    // given
    val (user, message, notificationService, notificationRepository) = fixture

    // when
    notificationService.notify(user.id, message).futureValue

    // then
    eventually {
      notificationRepository.findByUser(user).futureValue.value.message shouldBe message
    }
  }
}
