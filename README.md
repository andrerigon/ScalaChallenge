# ScalaChallenge [![Build Status](https://travis-ci.org/andrerigon/ScalaChallenge.svg?branch=master)](https://travis-ci.org/andrerigon/ScalaChallenge)

Simple App to process task networks

## Assumptions:

1. At least 1 link statement must be provided
2. Tasks statements are only for definition. They don't make sense without links. 
3. Link statements will be executed in order (there is no info about how to process sequential input links)
4. Delay information (tbb) becomes part of the input string for the next task

## Running tests:

make test

## Building a executable jar

make build

## Running the app:

make build run input=inputFile

or just:

make run input=inputFile











