import java.util.Scanner
case object Grade
{
  def main(args: Array[String]): Unit = {
    var percentage:Int=0
    var  input = new Scanner(System.in)
    println("Enter first number")
    percentage=input.nextInt()
    calculateGradeByPer(percentage)
  }
  def calculateGradeByPer(percentage: Int): Unit = {
    if (percentage < 70) {
      println("You are failed")
    }
    else if(percentage > 70 && percentage <= 80)
    {
      println("Your Grade C")
    }
    else if (percentage > 80 && percentage <= 90) {
      println("Your Grade B")
    }
    else if (percentage > 90 && percentage <= 100) {
      println("Your Grade A")
    }
    else if(percentage >100){
      println("Not a valid percentage, Hence not graded")
    }
  }
}
