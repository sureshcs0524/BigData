import org.apache.spark.sql.SparkSession
import org.apache.spark.SparkContext
import org.apache.spark.SparkConf

object TestDeckofcards {

  //val sc = new SparkContext()
  def main(args:Array[String]): Unit =
  {
    val session = SparkSession
      .builder()
      .appName("App Demo")
      .config("spark.master", "local")
      .getOrCreate();

    val sc = session.sparkContext
    val text = sc.textFile("file:/Users/daviddsouza/deckofcards.txt")
    val countblackqueen = text.filter (line => line.contains("BLACK"))
    println("Black Cards = "+countblackqueen.count())

  }

}
