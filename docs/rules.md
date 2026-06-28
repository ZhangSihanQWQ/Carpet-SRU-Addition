# Carpet Rules

[中文](rules_zh.md)

## allaySilentResonanceEnabled

Allows Allays to receive note block game events even when the note block is blocked by a block above.

- Type: `boolean`
- Default value: `false`
- Categories: `feature`, `Allay`, `sru`

## allayAiFreezeEnabled

Allows idle Allays to enter a deep sleep state when no visible player or liked note block is nearby, reducing AI activity until they need to respond again.

- Type: `boolean`
- Default value: `false`
- Categories: `optimization`, `Allay`, `sru`

## allayThrowCooldownTicks

Overrides the Allay item pickup cooldown after it throws an item.

Set it to `-1` to keep the vanilla behavior, which is 60 ticks.

- Type: `int`
- Default value: `-1`
- Suggested options: `-1`, `20`, `60`, `120`
- Categories: `feature`, `Allay`, `sru`

## allayThrowCooldownFix

Prevents items thrown by Allays from being picked up by any Allay for 3 seconds (60 ticks), avoiding immediate throw-and-pickup loops when `allayThrowCooldownTicks` is set too low.

- Type: `boolean`
- Default value: `false`
- Categories: `feature`, `Allay`, `sru`

## allayMaxHearingDistance

Overrides the maximum distance at which Allays can hear note blocks.

Set it to `-1` to keep the vanilla behavior, which is 16 blocks.

- Type: `int`
- Default value: `-1`
- Suggested options: `-1`, `0`, `4`, `16`, `32`
- Categories: `feature`, `Allay`, `sru`

## renewableDragonBreath

Makes dragon breath renewable.

When enabled, interacting with a dragon egg while holding dragon breath creates a collectible dragon breath cloud and removes the dragon egg.

- Type: `boolean`
- Default value: `false`
- Categories: `feature`, `sru`

## freezingTickNoStarve

Prevents Carpet fake players from taking starvation damage while game ticks are frozen with `/tick freeze`.

This only cancels starvation damage for Carpet fake players during tick freeze; real players and other damage types are unaffected.

- Type: `boolean`
- Default value: `false`
- Categories: `bugfix`, `sru`

## commandHat

Enables the `/hat` command. Ported from Vulpeus Carpet, originally from essential addons.

- Type: `String`
- Default value: `ops`
- Allowed options: `true`, `false`, `ops`, `0`, `1`, `2`, `3`, `4`
- Categories: `survival`, `command`, `sru`, `porting`

## commandSit

Enables the `/sit` command. Ported from Vulpeus Carpet, originally from PCA.

- Type: `String`
- Default value: `ops`
- Allowed options: `true`, `false`, `ops`, `0`, `1`, `2`, `3`, `4`
- Categories: `survival`, `command`, `sru`, `porting`

## commandSet

Enables the `/set <scale>` command, allowing players to change their own size using the vanilla `minecraft:scale` attribute.

The scale range is `0.0625` to `1.5`. 

- Type: `String`
- Default value: `ops`
- Allowed options: `true`, `false`, `ops`, `0`, `1`, `2`, `3`, `4`
- Categories: `survival`, `command`, `sru`

## limitTntRandomMomentum

Restricts the random horizontal momentum direction generated when TNT is primed.

Use `/limitTntRandomMomentum add <angle>` to add a restricted base angle, where `<angle>` is between `0` and `90`. Use `/limitTntRandomMomentum clear` to clear all restricted angles. Running `/limitTntRandomMomentum` without arguments displays the current angle list.

- Type: `boolean`
- Default value: `false`
- Categories: `feature`, `sru`, `TNT`

## visibleSpectators

Allows survival, creative, and adventure mode players to see players in spectator mode.

Ported from Vulpeus Carpet's `visibleSpectators` rule, which was originally ported from totos carpet tweaks.

- Type: `boolean`
- Default value: `false`
- Categories: `feature`, `porting`

## Additional Commands

### `/srurecipe enable`

Enables some ~~not vanilla~~ recipes provided by this mod.

### `/hat`

Equips one item from your main hand into your head slot. Totems of undying, non-empty shulker boxes, and replacing an item with Curse of Binding are blocked.

### `/sit`

Makes the executing player sit at their current position.

### `/set <scale>`

Sets the executing player's size. The accepted range is `0.0625` to `1.5`.

Aliases: `/small` = `/set 0.25`, `/smallpro` = `/set 0.0625`, `/big` = `/set 1`, `/bigpro` = `/set 1.5`.
