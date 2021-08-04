# THIS LIBRARY IS NO LONGER BEING MAINTAINED BY CrimsonDawn45!
He has repeatedly burnt himself out on Minecrat trying to maintain this library. He originally enjoyed working on this project but maintaining it had become a chore, and had made him afraid of checking his own github account bc he knows people might see his activity on other things while he is not working on this.

So now I am maintaining the mod.

# Fabric Shield Lib
Library for easily adding new shields, shield enchantments, and enabling you to enchant shields in general without worry of conflictions or having to write your own mixins.

## Importing
library is distributed via [jitpack.io](https://jitpack.io/#CrimsonDawn45/Fabric-Shield-Lib)

#### gradle.properties
```properties
fabric_shield_lib_version=1.3.6
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
head over to the [example mod repo](https://github.com/CrimsonDawn45/Fabric-Shield-Lib-Example-Mod) it's a template repo that shows how to implement custom shields, shield enchantments, and how the dependency should be setup in the build.gradle.
