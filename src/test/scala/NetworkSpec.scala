import com.andre.rigon.Network
import com.andre.rigon.task._
import org.scalatest.mock.MockitoSugar
import org.scalatest.{BeforeAndAfter, FlatSpec, Matchers}

class NetworkSpec extends FlatSpec with Matchers with MockitoSugar with BeforeAndAfter {

  behavior of "Network"

  it should "process network" in {
    val network = Network(
      List(
        Link(Reverse, Delay),
        Link(Noop, Echo)
      ),
      "foo bar"
    )

    network.process should be("tbbtbb oofoof rabrab")
  }
}