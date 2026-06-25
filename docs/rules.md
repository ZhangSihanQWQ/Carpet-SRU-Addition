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
- Suggested options: `-1`, `16`, `60`, `120`
- Categories: `feature`, `Allay`, `sru`

## allayThrowCooldownFix

Prevents items thrown by Allays from being picked up by any Allay for 3 seconds (60 ticks), avoiding immediate throw-and-pickup loops when `allayThrowCooldownTicks` is set too low.

- Type: `boolean`
- Default value: `false`
- Categories: `SRU`, `Allay`

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

## mcdrCommandAutoCompletion

Enables Tab completion for chat messages that start with `!!`, covering common MCDR and Prime Backup commands.

- Type: `boolean`
- Default value: `false`
- Categories: `feature`, `sru`

## limitTntRandomMomentum

Restricts the random horizontal momentum direction generated when TNT is primed.

Use `/limitTntRandomMomentum add <angle>` to add a restricted base angle, where `<angle>` is between `0` and `90`. Use `/limitTntRandomMomentum clear` to clear all restricted angles. Running `/limitTntRandomMomentum` without arguments displays the current angle list.

- Type: `boolean`
- Default value: `false`
- Categories: `feature`, `sru`, `TNT`

## visibleSpectators

Allows survival, creative, and adventure mode players to see players in spectator mode. Spectators are rendered as fully opaque player heads instead of translucent floating heads.

- Type: `boolean`
- Default value: `false`
- Categories: `feature`, `porting`

## Additional Commands

### `/srurecipe enable`

Enables some ~~not vanilla~~ recipes provided by this mod.
