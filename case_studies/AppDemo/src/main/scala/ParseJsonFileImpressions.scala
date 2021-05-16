import net.liftweb.json.DefaultFormats
import scala.io.Source
import net.liftweb.json._

case class Impressions(
                        app_id: Option[Int],
                        advertiser_id: Option[Int],
                        country_code: String,
                        id: Option[String]
                 )

object ParseJsonFileImpressions extends App {
  implicit val formats: DefaultFormats.type = DefaultFormats
  val jsonString = Source.fromResource("impressions.json")

  val json = parse(jsonString.mkString)

  val elements = json.children
  for (impr <- elements) {
    val m = impr.extract[Impressions]
    println(s"Impressions: ${m.app_id}, ${m.advertiser_id}, ${m.country_code}, ${m.id}")
  }

}
