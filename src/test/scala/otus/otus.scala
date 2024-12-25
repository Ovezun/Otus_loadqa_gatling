
import  io.gatling.http.Predef._
import  io.gatling.core.Predef._

 package object otus {

  //урл сайта
  val httpProtocol = http
    .baseUrl ("http://webtours.load-test.ru:1080")

  // получаем куку

}
