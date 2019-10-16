package org.kunicki.scalatest.fixtures

import org.scalatest.concurrent.{Eventually, ScalaFutures}
import org.scalatest.{Matchers, OptionValues}

trait Goodies extends Matchers with Eventually with OptionValues with ScalaFutures
