package com.andre.rigon

trait Task {

  def process(input: String) = join(exec(split(input)))

  def exec(input: Seq[String]): Seq[String] = input

  private def split(input: String) = input.split(" ").toSeq

  private def join(input: Seq[String]) = input.mkString(" ")
}

object EchoTask extends Task {
  override def exec(input: Seq[String]): Seq[String] = input.map(s => s + s)
}

object ReverseTask extends Task {
  override def exec(input: Seq[String]): Seq[String] = input.map(_.reverse)
}

object DelayTask extends Task {
  override def process(input: String) = s"tbb $input"
}

object NoopTask extends Task

class LinkTask(t1: Task, t2: Task) extends Task {
  override def process(input: String) = t2.process(t1.process(input))
}