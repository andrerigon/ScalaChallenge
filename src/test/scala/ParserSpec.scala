import java.io.ByteArrayInputStream

import com.andre.rigon._
import org.scalatest.mock.MockitoSugar
import org.scalatest.{BeforeAndAfter, FlatSpec, Matchers}

import scala.io.BufferedSource

class ParserSpec extends FlatSpec with Matchers with MockitoSugar with BeforeAndAfter {

  behavior of "parser"

  it should "read input" in {
    val data = List("you", "were", "the", "chosen", "one")
    System.setIn(toStream(data))
    val result = Parser.readInput(io.Source.stdin).toList
    result should be(data)
  }

  it should "parse correct task definitions" in {
    val tasks = List( "reverse", "delay", "echo", "noop").map(TaskParser.apply)

    tasks should be(List(
      ReverseTask,
      DelayTask,
      EchoTask,
      NoopTask
    ))
  }

  it should "throw error if incorrect task definition" in {
    val data = List(
      "task reverse reverse",
      "task delay foo"
    )
    intercept[RuntimeException] {
      Parser(new BufferedSource(toStream(data)))
    }
  }

  it should "throw error if a link statement doesnt have a corresponding task" in {
    val data = List(
      "task reverse reverse",
      "link reverse foo"
    )
    intercept[RuntimeException] {
      Parser(new BufferedSource(toStream(data)))
    }
  }

  it should "parse link task definitions" in {
     val data = List(
       "task reverse reverse",
       "task delay delay",
       "task echo echo",
       "task noop noop",
       "link reverse delay",
       "link echo noop"
     )

     val tasks = Parser(new BufferedSource(toStream(data)))

     tasks should be(List(
       new LinkTask(ReverseTask, DelayTask),
       new LinkTask(EchoTask, NoopTask)
     ))
   }

  private def toStream(lines: Seq[String]) = new ByteArrayInputStream(lines.mkString("\n").getBytes)
}
