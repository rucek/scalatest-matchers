package org.kunicki.scalatest.fixtures

import scala.collection.mutable
import scala.concurrent.{ExecutionContext, Future}

case class User(id: String, email: String)

case class Notification(user: User, message: String)

class NotificationRepository {

  // don't try this at home
  private [this] val notifications: mutable.Map[User, Notification] = mutable.Map.empty

  def create(notification: Notification): Future[Unit] = {
    notifications += notification.user -> notification
    Future.successful(())
  }

  def findByUser(user: User): Future[Option[Notification]] = Future.successful(notifications.get(user))

  def shutdown(): Unit = println("Shutting down")
}

class UserService {

  def findById(id: String): Future[User] = Future.successful(User(id, UserService.Email))
}

object UserService {
  val Email = "test@example.com"
}

class NotificationService(messagePrefix: String, userService: UserService, notificationRepository: NotificationRepository) {

  def notify(userId: String, message: String)(implicit ec: ExecutionContext): Future[Unit] =
    for {
      user <- userService.findById(userId)
      notification <- notificationRepository.create(Notification(user, s"$messagePrefix $message"))
    } yield notification

  def findByUserId(userId: String)(implicit ec: ExecutionContext): Future[Option[Notification]] =
    for {
      user <- userService.findById(userId)
      notification <- notificationRepository.findByUser(user)
    } yield notification

  def doSomethingElse(userId: String): Future[Unit] = Future.successful(())
}
