package otus

import  io.gatling.http.Predef._
import  io.gatling.core.Predef._

class Debug extends Simulation {

  setUp(

    CommonScenarion().inject(atOnceUsers(1))
      .protocols(httpProtocol.proxy(Proxy("localhost", 8080)) )

  )
}
