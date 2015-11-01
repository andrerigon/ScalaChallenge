package com.andre.rigon

import scala.collection.mutable
import scala.io.BufferedSource

object Parser {

  val taskPattern = "task (\\w+) (echo|delay|reverse|noop)".r
  val linkPattern = "link (\\w+) (\\w+)".r

  def apply(source: BufferedSource = io.Source.stdin): Seq[Task] = {

    val definedTasks = mutable.Map[String, Task]()

    val tasks = readInput(source) map {
        case taskPattern(name, op) => definedTasks += name -> TaskParser(op); None
        case linkPattern(t1, t2) => buildTask(t1, t2, definedTasks)
        case x => throw new RuntimeException(s"<$x> task not found.")
    }
    tasks.flatten
  }

  private def buildTask(t1: String, t2: String, definedTasks: mutable.Map[String, Task]) = {
    def isValid(s: String*) = s.foreach(s => if(!definedTasks.contains(s)) throw new RuntimeException(s"invlaid task <$s> to link"))
    isValid(t1, t2)
    if(!definedTasks.contains(t2)) throw new RuntimeException(s"Invalid task <$t2> to link")

    Some(new LinkTask(definedTasks(t1), definedTasks(t2)))
  }


  def readInput(source: BufferedSource): Seq[String] = source.getLines().toList
}

object TaskParser {
  def apply(op: String): Task = op match {
    case "echo" => EchoTask
    case "delay" => DelayTask
    case "reverse" => ReverseTask
    case "noop" => NoopTask
  }
}

object A extends App {
  val task = "task (\\w+) (echo|bla)".r

  val task(name, typee) = "task echo bla"

  println(s"name: $name")
  println(s"typee: $typee")
}
