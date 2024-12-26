package otus

import io.gatling.http.Predef._
import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder

object CommonScenarion {

  def apply(): ScenarioBuilder = new CommonScenarion().scn
}

class CommonScenarion {

    val scn: ScenarioBuilder = scenario("otus load gatling")
      .feed(Feeders.users)
      .feed(Feeders.passenger)
      .exec(Actions.getMain)
      //.exec(getCookieValue(CookieKey("MSO").saveAs("CookieMSO")))
      .exec(Actions.getUserSession)
      .exec(Actions.login)
      .exec(Actions.openFlightPage)
      .exec(Actions.buyTicketStep1)
      .exec(Actions.buyTicketStep2)
      .exec(Actions.buyTicketStep3)
}
