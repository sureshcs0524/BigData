import ParseJsonFileImpressions.json
import net.liftweb.json.DefaultFormats
import net.liftweb.json.JsonAST._
import scala.io.Source
import java.io._
import net.liftweb.json._
import net.liftweb.json.JsonDSL._
import net.liftweb.json.Serialization.write
import scala.collection.mutable.ListBuffer

case class ImprMatrics(
                        app_id: Option[Int],
                        country_code: String,
                        count: Int
                      )


object GetMatrics extends App {
  implicit val formats: DefaultFormats.type = DefaultFormats
  val jsonString = Source.fromResource("impressions.json")
  val json = parse(jsonString.mkString)
  val elements = json.children
  elements.groupBy(e => (e.extract[Impressions].app_id, e.extract[Impressions].country_code))
    .foreach(e => println(s"Impressions per app_id and country: ${e._1}, ${e._2.map(i => i.children)}"))

  elements.groupBy(e => (e.extract[Impressions].app_id, e.extract[Impressions].country_code))
    .foreach(e => println(s"Count of Impressions per app_id and country: ${e._1}, ${e._2.map(i => i.children).count(z => true)}"))

  val gdata = elements.groupBy(e => (e.extract[Impressions].app_id, e.extract[Impressions].country_code))
  gdata.keySet.toList.foreach(k => println(k._1, k._2))

  var jdata = new ListBuffer[String]()
  for ((k, v) <- gdata) {
    val outjson = ImprMatrics(k._1, k._2, v.count(z => true))
    val jsonString = write(outjson)
    jdata += jsonString
  }
  println(render(jdata))

  val file = "src/main/resources/result.json"
  val writer = new BufferedWriter(new FileWriter(file))
  jdata.toList.foreach(writer.write)
  writer.flush()
  writer.close()
}

