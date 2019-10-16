package org.kunicki.scalatest.fixtures

import org.scalatest.FlatSpec

import scala.concurrent.ExecutionContext.Implicits.global

class NotificationServiceSpec extends FlatSpec with Goodies {

  it should "create a notification" in {
    // given
    val user = User("42", UserService.Email)
    val message = "Hello, Piter!"
    val userService = new UserService
    val notificationRepository = new NotificationRepository
    val notificationService = new NotificationService(userService, notificationRepository)

    // when
    try {
      notificationService.notify(user.id, message).futureValue

      // then
      eventually {
        notificationRepository.findByUser(user).futureValue.value.message shouldBe message
      }
    } finally {
      notificationRepository.shutdown()
    }
  }

  it should "create another notification" in {
    // given
    val user = User("42", UserService.Email)
    val message = "Hello, Piter!"
    val userService = new UserService
    val notificationRepository = new NotificationRepository
    val notificationService = new NotificationService(userService, notificationRepository)

    // when
    try {
      notificationService.notify(user.id, message)

      // then
      eventually {
        notificationRepository.findByUser(user).futureValue.value.message shouldBe message
      }
    } finally {
      notificationRepository.shutdown()
    }
  }
}
