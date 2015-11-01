package com.andre.rigon.task

trait Task {

  def process(input: String) = join(exec(split(input)))

  protected def exec(input: Seq[String]): Seq[String] = input

  private def split(input: String) = input.split(" ").toSeq

  private def join(input: Seq[String]) = input.mkString(" ")
}

object Echo extends Task {
  override def exec(input: Seq[String]): Seq[String] = input.map(s => s + s)
}

object Reverse extends Task {
  override def exec(input: Seq[String]): Seq[String] = input.map(_.reverse)
}

object Delay extends Task {
  override def process(input: String) = s"tbb $input"
}

object Noop extends Task

case class Link(t1: Task, t2: Task) extends Task {
  override def process(input: String) = t2.process(t1.process(input))
}