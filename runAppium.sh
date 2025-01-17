#!/bin/bash
set -ex
npm install -g appium@2.11.2
appium -v
appium driver install uiautomator2
appium &>/dev/null &
