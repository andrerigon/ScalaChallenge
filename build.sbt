import java.io.File

import sbtrelease.{ReleaseStep, SbtCompat}
import spray.revolver.RevolverPlugin._
import sbtrelease.ReleaseStateTransformations._

name := """fyber"""

fork in run := true


resolvers ++= Seq("Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots")

resolvers += "Mesosphere Public Repository" at "http://downloads.mesosphere.io/maven"

resolvers += Resolver.mavenLocal

scalaVersion := "2.11.7"

// test

libraryDependencies ++= Seq(
  "org.mockito" % "mockito-core" % "1.9.5" % "test",
  "org.scalatest" % "scalatest_2.11" % "2.2.1" % "test"
)

scalacOptions in ThisBuild ++= Seq(
  "-target:jvm-1.8",
  "-encoding", "UTF-8",
  "-deprecation", // warning and location for usages of deprecated APIs
  "-feature", // warning and location for usages of features that should be imported explicitly
  "-unchecked", // additional warnings where generated code depends on assumptions
  "-Ywarn-adapted-args", // Warn if an argument list is modified to match the receiver
  "-Ywarn-inaccessible",
  "-Ywarn-dead-code",
  "-language:implicitConversions",
  "-language:reflectiveCalls"
)

assemblyJarName in assembly := s"${name.value}.jar"

parallelExecution in Test := true

coverageMinimum in Test := 90

coverageFailOnMinimum := true

addCommandAlias("test", "testQuick")

addCommandAlias("cov", "; clean; coverage; test")

testOptions in Test += Tests.Argument("-oI")

Revolver.settings

lazy val generatePropertiesTask = Def.task {
  val file = (resourceManaged in Compile).value / "build.properties"
  var contents = s"name=${name.value}"
  contents += s"\nversion=${version.value}"
  contents += s"\nbuild=${version.value}"
  contents += "\nscm_repository=https://github.com/andrerigon/?"
  contents += s"\nbuild_branch_name=" + Process("git rev-parse --abbrev-ref HEAD").lines.head.split("/").last
  contents += "\nbuild_revision=" + Process("git rev-parse HEAD").lines.head
  contents += "\nbuild_last_few_commits=" + s"${Process("git log --oneline -n 5").lines.map(_.split(" ").tail.mkString(" ")).mkString("\\n")}"
  IO.write(file, contents)
  Seq(file)
}

resourceGenerators in Compile += generatePropertiesTask.taskValue

mappings in(Compile, packageSrc) ++= {
  val allGeneratedFiles = ((resourceManaged in Compile).value ** "*") filter {
    _.isFile
  }
  allGeneratedFiles.get pair relativeTo((resourceManaged in Compile).value)
}

test in assembly := {}

releaseSettings

publishTo := Some(Resolver.file("Local repo", file("~/.m2/repository")))

val mavenLocal =  "Local Maven" at Path.userHome.asFile.toURI.toURL + ".m2/repository"
