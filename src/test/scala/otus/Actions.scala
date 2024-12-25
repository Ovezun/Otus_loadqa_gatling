package otus

import java.text.SimpleDateFormat
import java.util.Calendar

import io.gatling.http.Predef._
import io.gatling.core.Predef._


object Actions {

    //val httpConf = http.proxy("proxy.server.address",8888)

    val getMain = http("open main page")
      .get("/cgi-bin/welcome.pl")

    val getUserSession = http("get userSession")
      .get("/cgi-bin/nav.pl?in=home")
      .header("Cookie", "MSO=SID&1735106830")
      .check( css("input[name=userSession]", "value").saveAs("userSession") )

    val params = Map ("content-type"-> "application/x-www-form-urlencoded", "Cookie"-> "MSO=SID&1735106830")
    val login = http("login -  #{userSession}")
      .post("/cgi-bin/login.pl")
      .headers(params)
      //.header("content-type", "application/x-www-form-urlencoded")
//      .header("Cookie", "MSO=SID&1735106830")
      .formParam("userSession","#{userSession}" )
//      .formParam("username", "#{username}")
//      .formParam("password", "#{password}")
      .formParam("username", "vezun2024")
      .formParam("password", "vezun")
      .check(status.is(200) )
      .check(regex("""login.pl""").exists)

    val openFlightPage = http("Open Flight Page")
      .get("/cgi-bin/reservations.pl")
      .header("Cookie", "MSO=SID&1735106830")
      .check(regex("""Find Flight""").exists)
      .check(css("select[name=depart] > option").findRandom.saveAs("cityFrom"))
      .check(css("select[name=arrive] > option").findRandom.saveAs("cityTo"))

    val formatDate = new SimpleDateFormat("MM/dd/YYYY")
    val instance = Calendar.getInstance()
   // println(instance)
    instance.add(Calendar.DAY_OF_MONTH, 1)
    val dateFrom = formatDate.format(instance.getTime())
    println(dateFrom)
    val instance2 = Calendar.getInstance()
    // println(instance)
    instance2.add(Calendar.DAY_OF_MONTH, 3)
    val dateTo = formatDate.format(instance2.getTime())
    println(dateTo)


    val payload = Map(
        "advanceDiscount" -> 0,
        "depart" -> "#{cityFrom}",
        "departDate" -> dateFrom,
        "arrive" -> "#{cityTo}",
        "returnDate" -> dateTo,
        "numPassengers" -> 1,
        "seatPref" -> "None",
        "seatType" -> "Coach",
        "findFlights.x" -> 64,
        "findFlights.y" -> 5
    )
    println(payload)
//    payload + (".cgifields" -> "roundtrip")
//    payload + (".cgifields" -> "seatType")
//    payload + (".cgifields" -> "seatPref")
//    println(payload)

    val buyTicketStep1 = http("nexStep - from #{cityFrom} - to #{cityTo} " )
      .post("/cgi-bin/reservations.pl")
      .header("Cookie", "MSO=SID&1735106830")
      .formParamMap(payload)
      .formParam(".cgifields", "roundtrip")
      .formParam(".cgifields", "seatType")
      .formParam(".cgifields" ,"seatPref")
      .check(regex("""Flight departing from """).exists)
     println(buyTicketStep1)






}
