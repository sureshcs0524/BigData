import ParseJsonFileImpressions.json
import net.liftweb.json.DefaultFormats
import net.liftweb.json.JsonAST._
import scala.io.Source
import java.io._
import net.liftweb.json._
import net.liftweb.json.JsonDSL._
import net.liftweb.json.Serialization.write
import scala.collection.mutable.ListBuffer
import scala.collection.mutable.Map

case class ImprMatricsFull(
                            app_id: Option[Int],
                            country_code: String,
                            impressions: Int,
                            clicks: Int,
                            revenue: Double
                          )

object GetMatricsFull extends App {
  implicit val formats: DefaultFormats.type = DefaultFormats
  val impressionitr = Source.fromResource("impressions.json")
  val imprjson = parse(impressionitr.mkString)
  val imprelements = imprjson.children

  val clicksitr = Source.fromResource("clicks.json")
  val clicksjson = parse(clicksitr.mkString)
  val clkelements = clicksjson.children


  println(clkelements.count(z => true))
  var jdata1 = new ListBuffer[(Int, String, Int, String, String, Double)]()


  val gdata = imprelements.groupBy(e => (e.extract[Impressions].app_id, e.extract[Impressions].country_code))

  for ((k, v) <- gdata) {
    //println(k._1.getOrElse(0), k._2, v.toList.map(i => i.extract[Impressions].id.getOrElse(0)).count(c => true))
    for (impr <- imprelements) {
      val m = impr.extract[Impressions]
      for (clks <- clkelements) {
        val c = clks.extract[Clicks]
        if (m.id.nonEmpty) {
          if (clks.extract[Clicks].impression_id.contains(m.id.get)) {
            val fdata = (k._1.getOrElse(0), k._2, v.toList.map(i => i.extract[Impressions].id.getOrElse()).count(c => true), m.id.get, c.impression_id, c.revenue)
            jdata1 += fdata

          }
        }
      }
    }
  }

  val file = "src/main/resources/resultnew.json"
  val writer = new BufferedWriter(new FileWriter(file))
  val jdata2 = jdata1.groupBy(e => (e._1, e._2, e._3, e._4))
  var jdata3 = new ListBuffer[String]()
  for (i <- jdata2) {
    val outjson = ImprMatricsFull(Some(i._1._1), i._1._2, i._1._3, i._2.count(c => true), i._2.map(s => s._6).sum)
    val jsonString = write(outjson)
    jdata3 += jsonString
  }


  println(render(jdata3))
  jdata3.toList.foreach(writer.write)
  writer.flush()
  writer.close()
}

