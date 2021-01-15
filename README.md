# Fabric Shield Lib
![logo](https://repository-images.githubusercontent.com/260845258/54b55080-8fdb-11ea-94b7-f04d017b0219)
Library for easily adding new shields, shield enchantments, and enabling you to enchant shields in general without worry of conflictions or having to write your own mixins.
- - - -

### Importing
this library is distributed via [jitpack.io](https://jitpack.io/#CrimsonDawn45/Fabric-Shield-Lib)

#### add this into build.gradle under dependencies
```gradle
modImplementation "com.github.CrimsonDawn45:Fabric-Shield-Lib:${project.fabric_shield_lib_version}-${project.minecraft_version}"
```

#### add this into build.gradle under **repositories -> allprojects** near the end of the file.
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

### Documentation?
head over to the [example mod repo](https://github.com/CrimsonDawn45/Fabric-Shield-Lib-Example-Mod) it's a template repo that shows how to implement custom shields, shield enchantments, and how the dependency should be setup in the build.gradle.
