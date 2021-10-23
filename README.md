[![](https://jitpack.io/v/CrimsonDawn45/Fabric-Shield-Lib.svg)](https://jitpack.io/#CrimsonDawn45/Fabric-Shield-Lib)

# Fabric Shield Lib
Library for easily adding new shields, shield enchantments, and enabling you to enchant shields in general without worry of conflictions.

## Importing
library is distributed via [jitpack.io](https://jitpack.io/#CrimsonDawn45/Fabric-Shield-Lib)

#### gradle.properties
```properties
fabric_shield_lib_version=1.4.4
```

#### **build.gradle** under dependencies
```gradle
modImplementation "com.github.CrimsonDawn45:Fabric-Shield-Lib:${project.fabric_shield_lib_version}-${project.minecraft_version}"
```

#### **build.gradle** under repositories near the end of the file.
```gradle
maven { url 'https://jitpack.io' }
```

if this mod is your only dependency it should look something like this.
```gradle
repositories {
    allprojects {
        repositories {
            maven { url 'https://jitpack.io' }
	}
    }
}
```

- - - -

## Documentation?
Tutorial available on on [Fabric Wiki](https://fabricmc.net/wiki/tutorial:shield).

The [example mod repo](https://github.com/CrimsonDawn45/Fabric-Shield-Lib-Example-Mod) is a template repo you can use to quickly get started if your making a new mod.
