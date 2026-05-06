# Carpet-SRU-Addition

[![English](https://img.shields.io/badge/Language-English-blue)](README.md) [![中文](https://img.shields.io/badge/Language-中文-red)](README_zh.md)

A Carpet Mod extension that adds various useful features for Minecraft 1.21.1, including enhancements for Allays, renewable dragon breath, and TNT momentum control.

## Features Overview

This mod extension provides the following Carpet rules:

- **Allay Enhancements**: Fine-tune how Allays behave, including silent resonance, throw cooldown, and hearing distance
- **Dragon Breath**: Make dragon breath renewable through interactions
- **TNT Control**: Limit random momentum directions for precise TNT placement
- **Recipe Toggle**: Enable/disable specific SRU recipes
- **MCDR Integration**: Enable command auto-completion for MCDR commands

## Carpet Rules

### Allay Rules

| Rule Name | Command | Type | Description | Default |
|-----------|---------|------|-------------|---------|
| `allaySilentResonanceEnabled` | `/carpet allaySilentResonanceEnabled` | boolean | Allays receive note block events even when blocked by blocks above | `false` |
| `allayThrowCooldownTicks` | `/carpet allayThrowCooldownTicks <ticks>` | int | Custom cooldown ticks for Allay item pickup after throwing. Set to -1 for vanilla (60 ticks) | `-1` |
| `allayMaxHearingDistance` | `/carpet allayMaxHearingDistance <distance>` | int | Maximum distance Allays can hear note blocks. Set to -1 for vanilla (16 blocks) | `-1` |

### Dragon Breath Rules

| Rule Name | Command | Type | Description | Default |
|-----------|---------|------|-------------|---------|
| `renewableDragonBreath` | `/carpet renewableDragonBreath` | boolean | Make dragon breath clouds renewable by interacting with dragon egg and glass bottle | `false` |

### TNT Rules

| Rule Name | Command | Type | Description | Default |
|-----------|---------|------|-------------|---------|
| `limitTntRandomMomentum` | `/carpet limitTntRandomMomentum [add\|clear\|query]` | string | Restrict TNT horizontal momentum in specified yaw angle ranges. Stores base angles (0-90°) as comma-separated list | `""` |

**TNT Command Details:**
- `query` / no argument: Display all restricted angles and their effective ranges
- `add <0-90>`: Add a base angle restriction
- `clear <0-90>`: Remove a base angle restriction  
- `clear all`: Remove all restrictions

For example:
```
/carpet limitTntRandomMomentum add 0      # Restrict cardinal directions
/carpet limitTntRandomMomentum add 45     # Restrict diagonal directions
/carpet limitTntRandomMomentum            # Query current restrictions
/carpet limitTntRandomMomentum clear all  # Clear all restrictions
```

### Other Rules

| Rule Name | Command | Type | Description | Default |
|-----------|---------|------|-------------|---------|
| `mcdrCommandAutoCompletion` | `/carpet mcdrCommandAutoCompletion` | boolean | Enable Tab completion for !!-prefixed MCDR/Prime Backup commands in chat | `false` |

## Rule Categories

Rules are organized by category for easy filtering in the Carpet settings menu:
- **allay**: Allay-related features
- **sru**: General SRU Addition features
- **TNT**: TNT-related features

## Installation

1. Install [Fabric Loader](https://fabricmc.net/use) for Minecraft 1.21.1
2. Install [Carpet Mod](https://www.curseforge.com/minecraft/mods/carpet) (version compatible with 1.21.1)
3. Download the latest `carpet-sru-addition-*.jar` from [Releases](../../releases)
4. Place the jar file in your `mods` folder
5. Start your server/game

## Setup & Development

For setup instructions, please see the [Fabric Documentation page](https://docs.fabricmc.net/develop/getting-started/creating-a-project#setting-up) related to the IDE that you are using.

### Building from Source

```bash
git clone <repository-url>
cd Carpet-SRU-Addition
./gradlew build
```

The compiled jar will be in `build/libs/`

## Documentation

- [Complete Rules Guide](LIMIT_TNT_RANDOM_MOMENTUM_GUIDE.md) - Detailed documentation for TNT momentum limiting
- [Examples & Troubleshooting](EXAMPLES_AND_TROUBLESHOOTING.md) - Practical examples and solutions
- [Quick Reference](QUICK_REFERENCE.md) - Quick command reference
- [Implementation Details](IMPLEMENTATION_SUMMARY.md) - Technical implementation information

## Language Support

This mod provides translations in:
- 🇬🇧 English
- 🇨🇳 Chinese (Simplified)

Switch languages: [中文](README_zh.md)

## License

This project is available under the CC0 license. Feel free to learn from it and incorporate it in your own projects.

## Credits

- Built on [Carpet Mod](https://github.com/gnembon/fabric-carpet) by gnembon
- Uses [Fabric API](https://github.com/FabricMC/fabric-api)
- Compatible with Minecraft 1.21.1
