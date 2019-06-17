// A full working example on using ZIO bracket

import scalaz.zio.{App, Task, UIO}
import java.io.{File, FileInputStream}
import java.nio.charset.StandardCharsets

object Main extends App {

  // run my bracket
  def run(args: List[String]) =
    mybracket.orDie.const(0) 

  def closeStream(is: FileInputStream) =
    UIO(is.close())

  def convertBytes(is: FileInputStream) =
    Task.effect(println(new String(is.readAllBytes(), StandardCharsets.UTF_8))) // Java 11+, since is.realAllBytes() won't compile on Java8

  // mybracket is just a value. Won't execute anything here until interpreted
  val mybracket: Task[Unit] = for {
    file   <- Task(new File("/tmp/hello"))
    string <- Task(new FileInputStream(file)).bracket(closeStream)(convertBytes)
  } yield string           
}
