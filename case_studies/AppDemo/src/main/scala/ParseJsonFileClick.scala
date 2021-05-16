import net.liftweb.json.DefaultFormats
import scala.io.Source
import net.liftweb.json._

case class Clicks(
                   impression_id: String,
                   revenue: Double
                 )

object ParseJsonFileClick extends App {
  implicit val formats: DefaultFormats.type = DefaultFormats
  val jsonString = Source.fromResource("clicks.json")

  val json = parse(jsonString.mkString)

  val elements = json.children
  for (clk <- elements) {
    val m = clk.extract[Clicks]
    println(s"Clicks: ${m.impression_id}, ${m.revenue}")
  }

}
