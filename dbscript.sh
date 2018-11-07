#!/bin/sh

while (true) {

    UPSTREAM = $ {1: -'@{u}'}
    LOCAL = $(git rev - parse @)
    REMOTE = $(git rev - parse "$UPSTREAM")
    BASE = $(git merge - base @ "$UPSTREAM")

    if [$LOCAL = $REMOTE]; then
        echo "Up-to-date"
    elif[$LOCAL = $BASE]; then
        echo "Need to pull"
    elif[$REMOTE = $BASE]; then
        echo "Need to push"
    else
        echo "Diverged"
    fi

    sleep 5m
}

updateDB() {
    git pull
    pkill - 9 java
    cd Code / Java / DBServer / target
    nohup java - jar DBServer - 1.0 - SNAPSHOT - jar - with - dependencies.jar &
    cd ../../..
}