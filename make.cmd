@echo off
rem clean
del *.class
del zelda.jar
rem compile
javac zelda.java
rem package
jar cvfm zelda.jar Manifest.txt *.class 
rem clean
del *.class
