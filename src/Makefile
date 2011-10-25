JAVAC=javac
JAVAC_ARGS=

JAR=jar
JAR_ARGS=cvfm


BOT_NAME=MyBot.jar

SOURCES=MyBot.java

CLASSES=$(SOURCES:%.java=%.class)

.PHONY: all clean

all: $(CLASSES) 
	$(JAR) $(JAR_ARGS) $(BOT_NAME) Manifest.txt *.class

%.class: %.java
	$(JAVAC) $(JAVAC_ARGS) $<

clean:
	rm -f *.class
	rm -f *.jar
