[![](../../image/architectury_64.png)](../architectury/getting_started.md)
[![](../../image/fabric_64.png)](../fabric/getting_started.md)
[![](../../image/neoforge_64.png)](../neoforge/getting_started.md)

# Getting Started(Architectury)
**This tutorial assumes you are using Intellij(Highly Recommended)!**

## Setup:
### 1. Download & install [Intellij Community Edition](https://www.jetbrains.com/idea/download):
![Intellij download page with arrow pointing to Community Edition](../../image/intellij_download.png)

### 2. Install Minecraft & Architectury plugins:
#### Tip: `Ctrl` + `Alt` + `s` to open settings.
![Minecraft Development plugin page on Intellij Marketplace](../../image/intellij_minecraft_plugin.png)

![Architectury plugin page on Intellij Marketplace](../../image/intellij_architectury_plugin.png)

## 3. Create your mod project:
### Select the "Minecraft" wizard
![Intellij select wizard screen](../../image/intellij_select_wizard.png)
### Select Architectury as the Template
![Intellij create architectury project screen](../../image/intellij_architectury_create_project.png)

### Fill in your information:
- Name(ex. ExampleMod)
- Group ID Build System Properties -> Group ID(ex. `com.example` OR `com.github.yourusername`)
- Version Build System Properties -> Version(ex. `1.0.0` OR `1.0.0-alpha`)

### Hit `Create`:
![Intellij project create button](../../image/intellij_create_button.png)

## Importing ShieldLib:

### 1. Version
#### Add `shieldlib_version` to your `gradle.properties`. Find [Latest full release](https://github.com/StellarWind22/Shield-Lib/releases/latest)
```properties
shieldlib_version=[VERSION] (ex. 2.0.0-1.21.8)
```

### 2. Add to subprojects
#### Add modrinth maven repository in common, fabric, & neoforge `build.gradle`
```gradle
repositories {
    //other stuff here
    maven {url = "https://api.modrinth.com/maven"}
}
```

#### Add to dependencies using `modImplementation`

common & fabric:
```gradle
dependencies {
    //other stuff here
    modImplementation "maven.modrinth:shieldlib:${project.shieldlib_version}-fabric"
}
```

neoforge:
```gradle
dependencies {
    //other stuff here
    modImplementation "maven.modrinth:shieldlib:${project.shieldlib_version}-neoforge"
}
```

---

### Creating your first shield:
- [Vanilla Style(Tower Shield)](tower_shield.md)
- [Component Shield(No Banner Support)](component_shield.md)
- [New shield shape](custom_model.md)

---
[![](../../image/architectury_64.png)](../architectury/getting_started.md)
[![](../../image/fabric_64.png)](../fabric/getting_started.md)
[![](../../image/neoforge_64.png)](../neoforge/getting_started.md)