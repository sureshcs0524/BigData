import java.util.Scanner
case object TestCalculator
{
  def main(args:Array[String]): Unit =
  {
    var result:Int=0
    var x:Int=0
    var y:Int=0
    var operator:String= ""
    var  input = new Scanner(System.in)
    println("Chose the operator")
    operator=input.next().charAt(0).toString
    println("Enter first number")
    x=input.nextInt()
    println("Enter Second number")
    y=input.nextInt()
    //println("Result", result)
    operator match{
      case "+" =>  result = x + y
      case "-" =>  result = x - y
      case "/" =>  result = x / y
      case "*" => result = x * y
      case _ => "other"
    }
    println("result ", result)
  }
}
