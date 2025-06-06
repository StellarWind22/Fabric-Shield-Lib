[![](https://jitpack.io/v/CrimsonDawn45/Fabric-Shield-Lib.svg)](https://jitpack.io/#CrimsonDawn45/Fabric-Shield-Lib)

# Fabric Shield Lib
Library for easily adding new shields, shield enchantments, and enabling you to enchant shields in general without worry of conflictions.

## Importing
library is distributed via [jitpack.io](https://jitpack.io/#CrimsonDawn45/Fabric-Shield-Lib)

#### put this in gradle.properties
```properties
fabric_shield_lib_version=1.8.1-1.21.5
```

#### build.gradle under repositories just above dependencies
```gradle
maven { url = 'https://jitpack.io' }
```

if this mod is your only dependency it should look something like this.
```gradle
dependencies {
	minecraft "com.mojang:minecraft:${project.minecraft_version}"
	mappings "net.fabricmc:yarn:${project.yarn_mappings}:v2"
	modImplementation "net.fabricmc:fabric-loader:${project.loader_version}"
	modImplementation "net.fabricmc.fabric-api:fabric-api:${project.fabric_version}"
        
        //Fabric Shield Lib
	modImplementation "com.github.CrimsonDawn45:Fabric-Shield-Lib:v${project.fabric_shield_lib_version}"
}
```

#### **build.gradle** under dependencies
```gradle
modImplementation "com.github.CrimsonDawn45:Fabric-Shield-Lib:v${project.fabric_shield_lib_version}"
```

- - - -

## Documentation?
Tutorial available on [Fabric Wiki](https://fabricmc.net/wiki/tutorial:shield).

The [example mod repo](https://github.com/CrimsonDawn45/Fabric-Shield-Lib-Example-Mod) is a template repo you can use to quickly get started if your making a new mod. Although it isn't updated as frequently.
