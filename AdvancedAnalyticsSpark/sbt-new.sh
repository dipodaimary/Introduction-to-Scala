#!/bin/sh
read -p "Enter Project Name: "  projectname
mkdir -p "$projectname"/src/main/java
mkdir -p "$projectname"/src/main/resources
mkdir -p "$projectname"/src/main/scala
mkdir -p "$projectname"/src/test/java
mkdir -p "$projectname"/src/test/resources
mkdir -p "$projectname"/src/test/scala
mkdir -p "$projectname"/lib 
mkdir -p "$projectname"/project
mkdir -p "$projectname"/target
# create an initial build.sbt file
echo 'name := ''"'"$projectname"'"
version := "1.0"
scalaVersion := "2.10.0"' > "$projectname"/build.sbt
