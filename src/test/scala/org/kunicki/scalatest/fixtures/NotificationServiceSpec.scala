package org.kunicki.scalatest.fixtures

import org.scalatest.FlatSpec

import scala.concurrent.ExecutionContext.Implicits.global

class NotificationServiceSpec extends FlatSpec with Goodies {

  it should "create a notification" in {
    // given
    val user = User("42", UserService.Email)
    val userService = new UserService
    val notificationRepository = new NotificationRepository
    val notificationService = new NotificationService("[TEST]", userService, notificationRepository)

    // when
    try {
      notificationService.notify(user.id, "Hello, Joker")

      // then
      eventually {
        notificationService.findByUserId(user.id).futureValue.value.message shouldBe "[TEST] Hello, Joker"
      }
    } finally {
      notificationRepository.shutdown()
    }
  }

  it should "do something else" in {
    // given
    val user = User("42", UserService.Email)
    val userService = new UserService
    val notificationRepository = new NotificationRepository
    val notificationService = new NotificationService("[TEST]", userService, notificationRepository)

    // when
    try {
      notificationService.doSomethingElse(user.id)

      // then
      succeed
    } finally {
      notificationRepository.shutdown()
    }
  }
}
