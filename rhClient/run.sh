#!/bin/bash

fuser -k tcp/3000
gulp clean-tmp
gulp serve
