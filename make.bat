@echo off
cd src
rem clean
del *.class
del Zelda.jar
rem compile
javac Zelda.java
rem package
jar cvfm Zelda.jar Manifest.txt *.class 
rem clean
del *.class
move Zelda.jar ../
cd..