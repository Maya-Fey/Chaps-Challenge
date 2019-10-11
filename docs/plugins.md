The plugin system is built around the fact that every object on the maze implements **Persistable** and has an associated **ChapsFactory**. These classes and factories are put together in a **RootFactory** which can instantiate any type from a corresponding **JsonObject**. Because the RootFactory is just a list of factories, adding more factories to it is trivial and allows for infinite extensibility.

Factory addition is mediated by a class called **ExternalCodeLoader** which parses JAR files and uses reflection to find all the pairs of concrete maze classes and their factories. These pairs are then stored in **ExternalChapsClass** which can be loaded into RootFactory whenever they're needed. Every time a level is loaded, RootFactory is cleared to prevent conflicts between plugins.

These external jars are then grouped together with JSON representing the game state, and zipped up to become plugins.

You can create plugins with the level editor:

nz.ac.vuw.ecs.swen225.a3.plugin.LevelBuilder