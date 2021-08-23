package org.kunicki.scalatest.fixtures

import org.scalatest.flatspec.AnyFlatSpec

import scala.concurrent.ExecutionContext.Implicits.global

class NotificationServiceSpec extends AnyFlatSpec with Goodies {

  it should "create a notification" in {
    // given
    val userId = "42"
    val userService = new UserService
    val notificationRepository = new NotificationRepository
    val notificationService = new NotificationService("[TEST]", userService, notificationRepository)

    // when
    try {
      notificationService.notify(userId, "Hello, world")

      // then
      eventually {
        notificationService.findByUserId(userId).futureValue.value.message shouldBe "[TEST] Hello, world"
      }
    } finally {
      notificationRepository.shutdown()
    }
  }

  it should "do something else" in {
    // given
    val userService = new UserService
    val notificationRepository = new NotificationRepository
    val notificationService = new NotificationService("[TEST]", userService, notificationRepository)

    // when
    try {
      notificationService.doSomethingElse("42")

      // then
      succeed
    } finally {
      notificationRepository.shutdown()
    }
  }
}
