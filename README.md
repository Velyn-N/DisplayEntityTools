# DisplayEntityTools
Small-footprint Paper Plugin for easier handling of Minecraft's Display-Entities

> [!CAUTION]
> This Project has been archived due to other Projects using up time. I might reactivate this Repository when I have time to work on it.

## Short Feature List

- No Dependencies outside of Java and Paper itself
- Modern Paper-Plugin Architecture (no Bukkit/Spigot Support!)
- Pure Vanilla Minecraft Mechanics
- Full Tab-Completion for all Commands

## What is DisplayEntityTools?

The main use of DisplayEntityTools is the abstraction of the Vanilla Commands to create and manipulate DisplayEntities.

An Excerpt taken from the [Minecraft Wiki](https://minecraft.fandom.com/wiki/Display):
> Display entities are entities useful for map or data pack creators to display various things.
> There are Block Display, Item Display and Text Display, which are used to display blocks, items and texts respectively.
> They can only be created with the `/summon` or `/execute summon` command.

DisplayEntityTools provides a Command Palette to manipulate every feature of DisplayEntities as well as Item-Based Tools
to change Positioning by simply Clicking instead of typing out Commands.
> !!! Developers Notice: While in Alpha/Beta Releases, not every Property might be editable!
> If I missed one, please tell me in the [Issue Tracker](https://github.com/Velyn-N/DisplayEntityTools/issues).

Since DisplayEntityTools only abstracts Features already included in Vanilla Minecraft
you can uninstall the Plugin at any time and still keep the DisplayEntities including all of their features.<br/>
This also means that DisplayEntityTools does not aim to provide any additional functionality to DisplayEntities.

## Server Requirements

- Paper 1.20.1+ (1.19.4 introduced DisplayEntities, so it might be supported too (untested))
- Java 17+

## Usage Guide

DisplayEntityTools provides exactly one Command: `/displayentitytools`! You can configure additional Aliases for the Command.
The Alias of `/det` is preconfigured and will be used in the following paragraphs for readability.

All Functions have their respective SubCommands. Some of them can only be applied to certain types of DisplayEntities
(e.g. a TextDisplay can not get its Block changed) and will tell the User so if used incorrectly.
Playing around with the Commands will be the best option to get to know the Features.

Before manipulating an Entity you need to select one. Therefore go near it and run `/det select {RADIUS]`.
A Message will tell you which Entity you about your newly selected DisplayEntity.

Using the `/det get-tool [TOOLNAME]` Command you can obtain special Items that will manipulate the
DisplayEntities Position upon Clicking. You can change the Tools Intensity using a GUI after running `/det settings`.

## Configuration Reference

If a Plugin Update introduces a new Config Option,
it will automatically be added to your config.yml File with its default value set.<br/>
Most Properties should be self-explanatory, otherwise you will see a description here:

```yaml
debug: false # Debug Option for Development and Debugging purposes.
command-aliases: # Aliases for the one and only /displayentitytools Command
  - det
```
