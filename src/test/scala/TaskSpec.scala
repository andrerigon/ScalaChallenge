import com.andre.rigon.{DelayTask, NoopTask, ReverseTask, EchoTask}
import org.scalatest.mock.MockitoSugar
import org.scalatest.{BeforeAndAfter, FlatSpec, Matchers}

class TaskSpec extends FlatSpec with Matchers with MockitoSugar with BeforeAndAfter {

  behavior of "EchoTask"

  it should "echo given inputs" in {
    val result = new EchoTask().process("foo bar")
    result should be("foofoo barbar")
  }

  it should "reverse given inputs" in {
    val result = new ReverseTask().process("foo bar")
    result should be("oof rab")
  }

  it should "delay given inputs" in {
    val result = new DelayTask().process("foo bar")
    result should be("tbb foo bar")
  }

  it should "do nothing for noop task" in {
    val result = new NoopTask().process("foo bar")
    result should be("foo bar")
  }
}
