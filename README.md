# ♕ BYU CS 240 Chess

This project demonstrates mastery of proper software design, client/server architecture, networking using HTTP and WebSocket, database persistence, unit testing, serialization, and security.

## 10k Architecture Overview

The application implements a multiplayer chess server and a command line chess client.

[![Sequence Diagram](10k-architecture.png)](https://sequencediagram.org/index.html#initialData=C4S2BsFMAIGEAtIGckCh0AcCGAnUBjEbAO2DnBElIEZVs8RCSzYKrgAmO3AorU6AGVIOAG4jUAEyzAsAIyxIYAERnzFkdKgrFIuaKlaUa0ALQA+ISPE4AXNABWAexDFoAcywBbTcLEizS1VZBSVbbVc9HGgnADNYiN19QzZSDkCrfztHFzdPH1Q-Gwzg9TDEqJj4iuSjdmoMopF7LywAaxgvJ3FC6wCLaFLQyHCdSriEseSm6NMBurT7AFcMaWAYOSdcSRTjTka+7NaO6C6emZK1YdHI-Qma6N6ss3nU4Gpl1ZkNrZwdhfeByy9hwyBA7mIT2KAyGGhuSWi9wuc0sAI49nyMG6ElQQA)

## Modules

The application has three modules.

- **Client**: The command line program used to play a game of chess over the network.
- **Server**: The command line program that listens for network requests from the client and manages users and games.
- **Shared**: Code that is used by both the client and the server. This includes the rules of chess and tracking the state of a game.

## Starter Code

As you create your chess application you will move through specific phases of development. This starts with implementing the moves of chess and finishes with sending game moves over the network between your client and server. You will start each phase by copying course provided [starter-code](starter-code/) for that phase into the source code of the project. Do not copy a phases' starter code before you are ready to begin work on that phase.

## IntelliJ Support

Open the project directory in IntelliJ in order to develop, run, and debug your code using an IDE.

## Maven Support

You can use the following commands to build, test, package, and run your code.

| Command                    | Description                                     |
| -------------------------- | ----------------------------------------------- |
| `mvn compile`              | Builds the code                                 |
| `mvn package`              | Run the tests and build an Uber jar file        |
| `mvn package -DskipTests`  | Build an Uber jar file                          |
| `mvn install`              | Installs the packages into the local repository |
| `mvn test`                 | Run all the tests                               |
| `mvn -pl shared test`      | Run all the shared tests                        |
| `mvn -pl client exec:java` | Build and run the client `Main`                 |
| `mvn -pl server exec:java` | Build and run the server `Main`                 |

These commands are configured by the `pom.xml` (Project Object Model) files. There is a POM file in the root of the project, and one in each of the modules. The root POM defines any global dependencies and references the module POM files.

## Running the program using Java

Once you have compiled your project into an uber jar, you can execute it with the following command.

```sh
java -jar client/target/client-jar-with-dependencies.jar

♕ 240 Chess Client: chess.ChessPiece@7852e922
```

## Server API Diagram
([https://sequencediagram.org/index.html#initialData=C4S2BsFMAIGEAtIGckCh0AcCGAnUBjEbAO2DnBElIEZVs8RCSzYKrgAmO3AorU6AGVIOAG4jUAEyzAsAIyxIYAERnzFkdKgrFIuaKlaUa0ALQA+ISPE4AXNABWAexDFoAcywBbTcLEizS1VZBSVbbVc9HGgnADNYiN19QzZSDkCrfztHFzdPH1Q-Gwzg9TDEqJj4iuSjdmoMopF7LywAaxgvJ3FC6wCLaFLQyHCdSriEseSm6NMBurT7AFcMaWAYOSdcSRTjTka+7NaO6C6emZK1YdHI-Qma6N6ss3nU4Gpl1ZkNrZwdhfeByy9hwyBA7mIT2KAyGGhuSWi9wuc0sAI49nyMG6ElQQA](https://sequencediagram.org/index.html?presentationMode=readOnly#initialData=IYYwLg9gTgBAwgGwJYFMB2YBQAHYUxIhK4YwDKKUAbpTngUSWDABLBoAmCtu+hx7ZhWqEUdPo0EwAIsDDAAgiBAoAzqswc5wAEbBVKGBx2ZM6MFACeq3ETQBzGAAYAdAE5M9qBACu2GADEaMBUljAASij2SKoWckgQaIEA7gAWSGBiiKikALQAfOSUNFAAXDAA2gAKAPJkACoAujAA9D4GUAA6aADeAETtlMEAtih9pX0wfQA0U7jqydAc45MzUyjDwEgIK1MAvpjCJTAFrOxclOX9g1AjYxNTs33zqotQyw9rfRtbO58HbE43FgpyOonKUCiMUyUAAFJForFKJEAI4+NRgACUh2KohOhVk8iUKnU5XsKDAAFUOrCbndsYTFMo1Kp8UYdKUAGJITgwamURkwHRhOnAUaYRnElknUG4lTlNA+BAIHEiFRsyXM0kgSFyFD8uE3RkM7RS9Rs4ylBQcDh8jqM1VUPGnTUk1SlHUoPUKHxgVKw4C+1LGiWmrWs06W622n1+h1g9W5U6Ai5lCJQpFQSKqJVYFPAmWFI6XGDXDp3SblVZPQN++oQADW6ErU32jsohfgyHM5QATE4nN0y0MxWMYFXHlNa6l6020C3Vgd0BxTF5fP4AtB2OSYAAZCDRJIBNIZLLdvJF4ol6p1JqtAzqBJoIei0azF5vDgHYsgwr5ks9K+KDvvorxLAC5wFrKaooOUCAHjysL7oeqLorE2IJoYLphm6ZIUgatLlqOJpEuGFoctyvIGoKwowEBoakW6naYeU9GYRqOEsh6uqZLG-rTiGrrSpGHLRjAfHxnKWHJpBlzITy2a5pg-4gtBJRXAMRGjAuk59NOs7NuOrZ9N+V6qYU2Q9jA-aDr0mkjtpRnVlOQYGfOTltsuq7eH4gReCg6B7gevjMMe6SZJglkXkU1DXtIACiu7xfU8XNC0D6qE+3T6Y26Dtr+ZxAiWOVzspskFSxMDwfYIVIcFvqoRiGFSRxjFceSYB8QGrm5WgJFMkxIlcjytoCdoDEDcJangmN8iOs6BKcaSHAoNwvFBt1cbaP1ZoRoUlrSKtFKGLNwDzYmMlFWUyEhYpCB5uVzFmaUPSmbF5ldjkYClDZQ5Lpw3nroEkK2ru0IwAA4qOrJhaekXnswanXhDyVpfYo7ZT1pU-myKnlCVeUqWyLHILEUOjKoSHQuTaiNeh51YYtbWkh1XUE31E27eRw28qdnNkUml4waUfPsdhzPuqTYA05TO0C-tFEjVV4MQAAZjA6MU-zg3TfKUswGrGvQwznZ42DZPQ3dD1XU970vX0mtqOMlQuK7jRvccgufT2v12Y7qjOxUrsuO7ZgA54PkbtgPhQNg3DwDxhg0yk4Vnl9xPPTeDRoxjwRY+gQ6OwAcqOHsdpdqYi-nz69MXpdlTbuuwZ6eo07CcCJzTdNYib4uTSzFJs9XcuDQrPOjUGDpCeaXssaLLV97t3Fepkbc9HXox7LMSAcCPwljx3K+GH6hiO73FfAqUh+t6OVsN6mtvqf0jsAJLSM7ACMvYAMwACxlx9KK31fbP1HG-T+P9-5hxXBHIGARLCrXgskGAAApCAPJIajkCDoBAoAGzw3TkjMo1RKR3haI7TGdZepDjjsABBUA4AQHglAWYr9pAANxuVKuVC5w0NwfQxhzDWFgPYffKCQsnTygAFboLQG3NBPIu4oDRE1XuTN+7ulZhtdme8Z5j0ohPLac1p4Ribtw4M40xbqKXh1NubDdF7XZOPI24oTGP3BGfKxMglruj8FoVeo5YS0IEUw6AwjRhvwcdzMSJ8YBoBQMg4JtAxYX0uAouRt81BKSJrPZ6r18qdiAT9Acf1oGA18gELwdCuxelgMAbAcdCDxESCnOGQCM520qAlJKKU0rGAKaksoYjy4SPBCAbgeBYTNRgq1DRHoJlwiiUNRAtSjDaD0AYYZH0fwvQ4V7IpID-oriAA))
