import java.io.ByteArrayInputStream

import com.andre.rigon._
import com.andre.rigon.task._
import org.scalatest.mock.MockitoSugar
import org.scalatest.{BeforeAndAfter, FlatSpec, Matchers}

import scala.io.BufferedSource

class ParserSpec extends FlatSpec with Matchers with MockitoSugar with BeforeAndAfter {

  behavior of "parser"

  it should "read input" in {
    val data = List("you", "were", "the", "chosen", "one")
    val result = Parser.readInput(new BufferedSource(toStream(data))).toList
    result should be(data)
  }

  it should "parse correct task definitions" in {
    val tasks = List("reverse", "delay", "echo", "noop").map(TaskParser.apply)

    tasks should be(List(
      Reverse,
      Delay,
      Echo,
      Noop
    ))
  }

  it should "throw error if incorrect task definition" in {
    val data = List(
      "task reverse reverse",
      "task delay foo",
      "process a"
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

  it should "throw error if a process statement is not present" in {
    val data = List(
      "task reverse reverse",
      "task delay delay",
      "link reverse delay"
    )
    intercept[RuntimeException] {
      Parser(new BufferedSource(toStream(data)))
    }
  }

  it should "throw error if a link statement is not present" in {
    val data = List(
      "task reverse reverse",
      "task delay delay",
      "process foo bar"
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
       "link echo noop",
       "process foo bar"
     )

     val tasks = Parser(new BufferedSource(toStream(data))).tasks

     tasks should be(List(
       new Link(Reverse, Delay),
       new Link(Echo, Noop)
     ))
   }

  it should "parse network" in {
    val data = List(
      "task reverse reverse",
      "task delay delay",
      "link reverse delay",
      "process foo bar"
    )

    val network = Parser(new BufferedSource(toStream(data)))

    network.tasks should be(List(
      new Link(Reverse, Delay)
    ))

    network.input should be("foo bar")
  }

  private def toStream(lines: Seq[String]) = new ByteArrayInputStream(lines.mkString("\n").getBytes)
}
