#!/bin/bash
google-chrome --headless --screenshot --window-size=1200,300 ../../project/build/reports/jacoco/test/html/index.html
echo "Screenshot has been taken..."
