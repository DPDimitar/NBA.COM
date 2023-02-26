# Scraping NBA Player 3PA data (JAVA)

## Short summary

This program is written in Java built with maven build tool and its purpose is scraping NBA player 3PA per regular season per 40mins. Program should receive one argument the player name!

### Important
Tests and also other part of the codes are based on the nba.com website so because of future changes part of this code(including Tests) maybe won't work.

## To run this example:

1. Make sure you have Java, the JDK, and Maven installed
2. Clone the repo

## Running our Code

`mvn clean install`

`mvn exec:java`

For changing the argument go to the pom.xml file under build.plugins.plugin.configuration.arguments and change the first argument

### For running the tests execute

`mvn test`

`java -jar target/mvn-example-1.0-SNAPSHOT.jar`