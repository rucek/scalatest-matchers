package org.kunicki.scalatest.fixtures

import org.scalatest.OptionValues
import org.scalatest.concurrent.{Eventually, ScalaFutures}
import org.scalatest.matchers.should.Matchers

trait Goodies extends Matchers with Eventually with OptionValues with ScalaFutures
