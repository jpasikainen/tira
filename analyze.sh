#!/bin/bash
cd project/
gradle run --args="--depth 1 --auto --analyze" -q > results1.csv &
sleep 5 &
gradle run --args="--depth 2 --auto --analyze" -q > results2.csv &
sleep 5 &
gradle run --args="--depth 3 --auto --analyze" -q > results3.csv &
sleep 5 &
gradle run --args="--depth 4 --auto --analyze" -q > results4.csv &
sleep 5 &
gradle run --args="--depth 5 --auto --analyze" -q > results5.csv &
sleep 5 &
gradle run --args="--depth 6 --auto --analyze" -q > results6.csv &
sleep 5 &
gradle run --args="--depth 7 --auto --analyze" -q > results7.csv