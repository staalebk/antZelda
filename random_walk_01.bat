call make.bat
python tools/playgame.py "java -jar MyBot.jar" "python tools/sample_bots/python/HunterBot.py" "python tools/sample_bots/python/HunterBot.py" "python tools/sample_bots/python/HunterBot.py" --map_file tools/maps/symmetric_random_walk/random_walk_01.map --log_dir ../../../../temp/game_logs --turns 200 --player_seed 7 --verbose -e
