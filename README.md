# ThorinCompanies
A Minecraft plugin for Paper that introduces companies into the game.

## Run instructions
1. Clone the repository
2. Open the project directory with IntelliJ
3. Have sure you have the "Minecraft Development" plugin for IntelliJ
4. Make sure you have the "Kotlin" plugin for IntelliJ so that you get syntax highlighting and analysis.
5. IntelliJ may need to be updated to the latest version
6. It should now be possible to generate a JAR file for the plugin using Maven, all inside IntelliJ.

## Local Development Environment
A local development environment for this plugin should include the following:
1. A Minecraft server running a matching version of Paper.
2. A local MySQL database, Laragon is a simple software for handling this.

## Database
The plugin is designed exclusively for use with MySQL as this is considered a more flexible and long-term stable solution. 
Support for local database storage can always be added in the future.

## Why Kotlin?
While plain Java is typically used for Minecraft Plugins, this plugin utilises 
Kotlin in order to achieve more concise and readable code. While the inclusion of the Kotlin standard library 
introduced overhead, this is considered highly justified considering the increased development speed and code 
maintainability in the long run.