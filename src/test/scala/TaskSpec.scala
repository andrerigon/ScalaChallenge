import com.andre.rigon.task._
import org.scalatest.mock.MockitoSugar
import org.scalatest.{BeforeAndAfter, FlatSpec, Matchers}

class TaskSpec extends FlatSpec with Matchers with MockitoSugar with BeforeAndAfter {

  behavior of "Tasks"

  it should "echo given inputs" in {
    Echo("foo bar") should be("foofoo barbar")
  }

  it should "reverse given inputs" in {
    Reverse("foo bar") should be("oof rab")
  }

  it should "delay given inputs" in {
    Delay("foo bar") should be("tbb foo bar")
  }

  it should "do nothing for noop task" in {
    Noop("foo bar") should be("foo bar")
  }

  it should "link two tasks" in {
    val result = new Link(Reverse, Delay)("foo bar")
    result should be("tbb oof rab")
  }
}
