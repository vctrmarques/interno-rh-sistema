#!/bin/bash

# Clear cache is optional
rm -Rf node_modules/
rm -f package-lock.json
rm -Rf client/bower_components/
npm cache clean --force
# Installing application
npm install
npm install -g bower
npm install -g gulp
bower install
# Running application
if [ ! -z "$1" ]; then
  if [ "$1" = true ]; then
    gulp clean-tmp
    gulp build
    #gulp serve
  fi
fi
