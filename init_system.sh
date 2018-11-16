#!/usr/bin/env bash

brew cask install caskroom/versions/java8
brew install kafka
brew install cassandra
brew install apache-spark

brew services start zookeeper
brew services start kafka
brew services start cassandra
