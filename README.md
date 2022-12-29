# Capatible
This is a core library for all of my mods. If you just want to use one of my mods, then just download it off curseforge and put it into mods folder.

## Developers
### Dependency
```groovy
repositories {
    maven {
        url 'https://cdn.sussy.tech/repo'
    }
}

dependencies {
    implementation 'net.theairblow:capatible:1.0.0'
}
```
### Capabilities
#### Implementing
1) Make a class that implements `ICapability`
2) Implement all of the methods it requires
3) Add any properties to the class that you will need
4) Register it with `Capability` or `CapabilityRegistry`
5) Profit! Now their value would now be saved and read.
#### Reading
1) Acquire the `ICapabilityHolder` capability
2) Do `getCapability(ExampleImpl.class)`
3) Profit! Now you can do anything with it.
### Handle Mod
Call `Capatible.handleMod(MOD_ID)` at `preInit` for everything below to work.
### Capability
Add this annotation to an `ICapability` class to register it as a capability automagically.
### EventHandler
Add this annotation to a class to register it as an event handler automagically.
### Command
Add this annotation to a command class to register it as a server command automagically.
