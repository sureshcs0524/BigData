import org.apache.spark.sql.SparkSession
import org.apache.spark.SparkContext
import org.apache.spark.SparkConf

object TestDeckofCards {
  def main(args:Array[String]): Unit ={
    val sparkSession=SparkSession
      .builder()
      .appName(name ="App Demo")
      .config("spark.master", "local")
      .getOrCreate();
    val sc=sparkSession.sparkContext
    val text = sc.textFile("file:/Users/sureshcs/Documents/Hadoop/Data/deckofcards.txt")
    val countblackqueen = text.filter(line => line.contains("BLACK"))
    println("Black Cards "+countblackqueen.count())
  }
}