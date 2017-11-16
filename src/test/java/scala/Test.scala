import scala.collection.mutable.ListBuffer

object xx {
  def main(args: Array[String]): Unit = {
    var xx = 1
    val yy = 2
    val arr = Array(11, 22, 33)
//    arr.foreach(print(_))
//
//    for (word <- arr) {
//      print(word)
//    }

    ListBuffer(arr).foreach(ints => print(ints))
  }
}