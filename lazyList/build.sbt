name := "lazyList"

version := "0.1"

scalaVersion := "2.13.6"

resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/releases"
libraryDependencies += "com.storm-enroute" %% "scalameter" % "0.21"

testFrameworks += new TestFramework("org.scalameter.ScalaMeterFramework")

parallelExecution := false