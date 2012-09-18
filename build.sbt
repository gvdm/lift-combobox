name := "combobox"

liftVersion <<= liftVersion ?? "2.5-SNAPSHOT"

version <<= liftVersion apply { _ + "-0.1" }

organization := "net.liftmodules"
 
scalaVersion := "2.9.2"

crossScalaVersions := Seq("2.9.2", "2.9.1-1", "2.9.1")

scalacOptions ++= Seq("-unchecked", "-deprecation")

resolvers ++= Seq(
    "Scala-Tools" at "https://oss.sonatype.org/content/repositories/snapshots/"
)

libraryDependencies <++= liftVersion { v =>
  "net.liftweb" %% "lift-webkit" % v % "compile->default" :: Nil
}    