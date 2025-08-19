#!/bin/bash
set -ex
npm install -g appium@2.19.0
appium -v
appium driver install uiautomator2
appium --allow-insecure=adb_shell --allow-cors --log-level debug &>/dev/null &
