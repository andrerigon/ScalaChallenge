package com.andre.rigon

import com.andre.rigon.task._

import scala.collection.mutable
import scala.io.BufferedSource

object Parser {

  val task = "task (\\w+) (echo|delay|reverse|noop)".r
  val link = "link (\\w+) (\\w+)".r
  val process = "process (.+)".r

  def apply(source: BufferedSource = io.Source.stdin): Network = {
    var input: Option[String] = None
    val definedTasks = mutable.Map[String, Task]()
    val links = mutable.Set[Task]()

    readInput(source) foreach {
        case task(name, op) => definedTasks += name -> TaskParser(op)
        case link(t1, t2) => links += buildTask(t1, t2, definedTasks)
        case process(i) => input = Some(i)
        case x => throw new RuntimeException(s"<$x> task not found.")
    }

    if(links.isEmpty) {
      throw new RuntimeException("At least 1 <link> statement is required.")
    }

    Network(links.toList, input.getOrElse(throw new IllegalArgumentException("invalid input")))
  }

  def readInput(source: BufferedSource): Seq[String] = source.getLines().toList

  private def buildTask(t1: String, t2: String, definedTasks: mutable.Map[String, Task]) = {
    def isValid(s: String*) = s.foreach(s => if(!definedTasks.contains(s)) throw new RuntimeException(s"invlaid task <$s> to link"))
    isValid(t1, t2)

    new Link(definedTasks(t1), definedTasks(t2))
  }
}

object TaskParser {
  def apply(op: String): Task = op match {
    case "echo" => Echo
    case "delay" => Delay
    case "reverse" => Reverse
    case "noop" => Noop
  }
}