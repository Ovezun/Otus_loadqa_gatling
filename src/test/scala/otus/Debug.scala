package otus

import io.gatling.http.Predef._
import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder

class Debug extends Simulation {

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
    .exec(Actions.getMain)

  val scn_load: ScenarioBuilder = scenario("load scenarion ")
    .feed(Feeders.users)
    .exec(Actions.getMain)
    .exec(Actions.getUserSession)
    .exec(Actions.login)
    .exec(Actions.openFlightPage)
    //.exec(Actions.buyTicketStep1)


  setUp(
  // покупка билета
    scn.inject(atOnceUsers(1))
      // Поиск максимума
     ,scn_load.inject(
      // Интенсивность на ступень
      incrementUsersPerSec((3).toInt)
        // Количество ступеней
        .times(10)
        // Длительность удержания
        .eachLevelLasting(100)
        // Длительность разгона
        .separatedByRampsLasting(20)
        // Старт нагрузки с
        .startingFrom(0),
    )
    //Стабильная нагрузка
//    scn_load.inject(
//      // Разгон
//      rampUsersPerSec(0) to 16.toInt during 30,
//      // Стабильный участок
//      constantUsersPerSec(16.toInt) during 3600,

//    )
    ).protocols(httpProtocol.proxy(Proxy("localhost", 8080)) )
    .assertions(global.failedRequests.percent.is(0))
    .assertions(global.responseTime.percentile3.lt(35000))
    .assertions(global.responseTime.max.lt(50000))





}
