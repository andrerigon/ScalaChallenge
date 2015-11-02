package com.andre.rigon

import com.andre.rigon.task.Task

case class Network(tasks: List[Task], input: String) {

  def process = tasks.foldLeft(input)((i, task) => task(i))
}
