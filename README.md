# Fabric-Shield-Lib
Library for easily adding new shields, shield enchantments, and enabling you to enchant shields in general into the game without worry of conflictions or having to write your own mixins.

### Documentation?
head over to the [example mod repo](https://github.com/CrimsonDawn45/Fabric-Shield-Lib-Example-Mod) it's a template repo that you can just copy that shows how to implement custom shields, shield enchantments, and how the dependency should be setup in the build.gradle. **I still need to set up more documentation** to further explain the ShieldEvent system, but I need to get the core library fixed first.

- - - -

### Importing library
this library is distributed via [jitpack.io](https://jitpack.io/#CrimsonDawn45/Fabric-Shield-Lib)

#### add this line into build.gradle under dependencies
```gradle
modImplementation "com.github.CrimsonDawn45:Fabric-Shield-Lib:${project.fabric_shield_lib_version}-${project.minecraft_version}"
```

#### add this line into build.gradle under **repositories -> allprojects** near the end of the file.
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
