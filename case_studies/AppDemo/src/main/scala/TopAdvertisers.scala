import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

object TopAdvertisers {
  def main(args:Array[String]): Unit = {
    val spark: SparkSession = SparkSession.builder()
      .master("local[3]")
      .appName("ads")
      .getOrCreate()
    val sc = spark.sparkContext

    val dfclicks = spark.read.option("multiline",true).json("src/main/resources/clicks.json")
    val dfimpr = spark.read.option("multiline",true).json("src/main/resources/impressions.json")

    dfclicks.printSchema()
    dfclicks.show(false)

    dfimpr.printSchema()
    dfimpr.show(false)

    val records = dfimpr.join(dfclicks).where(dfimpr("id") === dfclicks("impression_id"))
    records.printSchema()
    records.show(false)

    val revenuesumperappandcountry = records.groupBy("app_id","country_code")
      .agg(
            sum("revenue").as("sum_revenue")
      )
    revenuesumperappandcountry.show(false)
    revenuesumperappandcountry.coalesce(1).write.json("src/main/resources/revenuesumperappandcountry.json")
  }
}
