package com.example

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.{OptionModule, TupleModule, DefaultScalaModule}

object Hello {
  case class Person(name: String, age: Int)
  case class Organization(name: String, persons: List[Person])

  def main(args: Array[String]): Unit = {
    val json = """{"name":"some organization","persons":[{"name":"Taro","age":22},{"name":"Hanako","age":18},{"name":"Saburo","age":25}]} """

    val organization =
      Organization(
        "some organization",
        List(Person("Taro", 22), Person("Hanako", 18), Person("Saburo", 25))
      )

    val mapper = new ObjectMapper
    mapper.registerModule(DefaultScalaModule)

    println(json)
    println(mapper.readValue(json, classOf[Organization]))

    println(mapper.writeValueAsString(organization))

    println("Hello, world!")
  }
}
