package com.andre.rigon

trait Task {

  def process(input: String) = join(exec(split(input)))

  def exec(input: Seq[String]): Seq[String]

  private def split(input: String) = input.split(" ").toSeq

  private def join(input: Seq[String]) = input.mkString(" ")
}

class EchoTask extends Task {
  override def exec(input: Seq[String]): Seq[String] = input.map(s => s + s)
}

class ReverseTask extends Task {
  override def exec(input: Seq[String]): Seq[String] = input.map(_.reverse)
}

class DelayTask extends NoopTask {
  override def process(input: String) = s"tbb $input"
}

class NoopTask extends Task {
  override def exec(input: Seq[String]): Seq[String] = input
}