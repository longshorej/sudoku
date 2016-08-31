name := "sudoku"

maintainer := "Jason Longshore <longshorej@gmail.com>"

version := "1.0"

organization := "info.longshore"

enablePlugins(JavaAppPackaging)

scalaVersion := "2.11.8"

scalacOptions ++= Vector(
  "-deprecation",
  "-encoding", "UTF-8",
  "-feature",
  "-language:existentials",
  "-language:higherKinds",
  "-language:implicitConversions",
  "-unchecked",
  "-Xfatal-warnings",
  "-Xlint",
  "-Yno-adapted-args",
  "-Yrangepos",
  "-Ywarn-dead-code",
  "-Ywarn-numeric-widen",
  "-Ywarn-value-discard",
  "-Xfuture",
  "-Ywarn-unused-import",
  "-Ywarn-unused"
)

// mainClass in Compile := Some("info.longshore.sudoku.gui.SudokuApplication")

mainClass in Compile := Some("info.longshore.sudoku.domain.SudokuPuzzle")
