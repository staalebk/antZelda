call make.bat
python tools/playgame.py "java -jar MyBot.jar" "python tools/sample_bots/python/HunterBot.py" --fill --map_file tools/maps/maze_04p_01.map --log_dir ../../../../temp/game_logs --turns 1000 --player_seed 7 --verbose -e --turntime 500
rem -javaagent:F:\DevTools\profiler4j-1.0-beta2\agent.jar=waitconn=true,verbosity=1