import org.mockito.Mockito._
import org.scalatest.mock.MockitoSugar
import org.scalatest.{BeforeAndAfter, FlatSpec, Matchers}

import scala.collection.JavaConverters._

class FirstSpec extends FlatSpec with Matchers with MockitoSugar with BeforeAndAfter {

  before {
    println("before foo")
  }

  behavior of "#foo"

  it should "bring balance to the force" in {
    println("foo bar")
  }

}
