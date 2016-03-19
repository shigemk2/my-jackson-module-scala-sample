import com.example.Hello.{Organization, Person}
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import org.scalatest._

class HelloSpec extends FunSpec with Matchers {
  describe("JacksonModule") {
    it("parse json as Case Class") {
      val json =
        """|{"name":"some organization",
        | "persons":
        |  [{"name":"Taro","age":22},
        |   {"name":"Hanako","age":18},
        |   {"name":"Saburo","age":25}]}""".stripMargin

      val mapper = new ObjectMapper
      mapper.registerModule(DefaultScalaModule)

      mapper.readValue(json, classOf[Organization]) should be(Organization("some organization",
        List(Person("Taro", 22),
          Person("Hanako", 18),
          Person("Saburo", 25))))
    }
  }

  it("parse json use Scala Collection") {
    val json =
      """|{"name":"some organization",
        | "persons":
        |  [{"name":"Taro","age":22},
        |   {"name":"Hanako","age":18},
        |   {"name":"Saburo","age":25}]}""".stripMargin

    val mapper = new ObjectMapper
    mapper.registerModule(DefaultScalaModule)

    mapper.readValue(json, classOf[Map[_, _]]) should be {
      Map("name" -> "some organization",
        "persons" -> List(
          Map("name" -> "Taro", "age" -> 22), Map("name" -> "Hanako", "age" -> 18), Map("name" -> "Saburo", "age" -> 25)
        )
      )
    }
  }

  it("produce json from Scala Collection") {
    val organization =
      Map("name" -> "some organization",
        "persons" -> List(
          Map("name" -> "Taro", "age" -> 22), Map("name" -> "Hanako", "age" -> 18), Map("name" -> "Saburo", "age" -> 25)
        )
      )

    val mapper = new ObjectMapper
    mapper.registerModule(DefaultScalaModule)

    mapper.writeValueAsString(organization) should be {
      """{"name":"some organization","persons":[{"name":"Taro","age":22},{"name":"Hanako","age":18},{"name":"Saburo","age":25}]}"""
    }
  }
}
