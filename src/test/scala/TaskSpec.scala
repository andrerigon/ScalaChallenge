import com.andre.rigon._
import org.scalatest.mock.MockitoSugar
import org.scalatest.{BeforeAndAfter, FlatSpec, Matchers}

class TaskSpec extends FlatSpec with Matchers with MockitoSugar with BeforeAndAfter {

  behavior of "EchoTask"

  it should "echo given inputs" in {
    val result = EchoTask.process("foo bar")
    result should be("foofoo barbar")
  }

  it should "reverse given inputs" in {
    val result = ReverseTask.process("foo bar")
    result should be("oof rab")
  }

  it should "delay given inputs" in {
    val result = DelayTask.process("foo bar")
    result should be("tbb foo bar")
  }

  it should "do nothing for noop task" in {
    val result = NoopTask.process("foo bar")
    result should be("foo bar")
  }

  it should "link two tasks" in {
    val result = new LinkTask(ReverseTask, DelayTask).process("foo bar")
    result should be("tbb oof rab")
  }
}
