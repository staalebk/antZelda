SRC_DIR=src
BOT_NAME=MyBot.jar

.PHONY: all clean

all: 
	make -C $(SRC_DIR) all
	mv $(SRC_DIR)/$(BOT_NAME) .

clean:
	make -C $(SRC_DIR) clean
	rm -f *.jar
