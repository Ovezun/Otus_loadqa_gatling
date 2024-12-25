package otus

import java.text.SimpleDateFormat

import io.gatling.core.Predef._
import io.gatling.core.feeder.BatchableFeederBuilder
import io.gatling.http.Predef._
import io.gatling.recorder.internal.bouncycastle.util.test.TestRandomData

object Feeders {

  val users: BatchableFeederBuilder[String] = csv("user.csv").circular


}
