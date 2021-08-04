# AC-1.7.3
Decompiled AdventureCraft rewrite for 1.7.3.

## Setup
Run the following command, replacing "idea" with the command your specific ide if you are not using intelliJ:

Note: If you do this using cmd in the projects folder do "gradlew", not "./gradlew".

```
./gradlew idea
```

NOTE: if you want sources (recommended), instead run

```
./gradlew rebuildLVT genSources idea
```

Once your mod is complete and ready for use it can be compiled and reobfuscated with:

```
./gradlew idea
```

## Java Dependency
Make sure to use Java 8 because the Minecraft Version this runs on doesn't support anything higher,
if you encounter problems with the download of gradle files, switch to a higher jdk and switch back after that.
