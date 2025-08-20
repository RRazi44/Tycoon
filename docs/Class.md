# Class PlayerData

## Description

Represents all data related to a player in the Tycoon

## Responsibilities

- Store persistent player data (box, money, stats).

- Handle earnings and balance changes.

- Provide accessors to retrieve and modify data.

## Fields

- `UUID uuid` - Unique identifier of the player.

- `Box box` - The player's box.

- `Rank rank` - The player's rank on the box.

- `long balance` - Player's money amount.

- `long earnings` - Money generated per second.

- `int level` - Player's level or rank in the Tycoon

## Methods

- `getBalance(): long` - Returns the current balance.

- `addBalance(): void` - Adds money to the player.

- `getEarnings(): long` - Returns earnings per second.

- `upgradeLevel(): void` - Increase player's level.

- `hasBox(): boolean` - Checks if the player has a box.

## Relations

- Used by: Box, Commands

- Depend on : Bukkit Player (via UUID).

---

# Class Box

## Description

Reprensents a Box entity owned by a player or team.

## Responsibilities

- Store the owner and members of the box.

- Manage box size and upgrades.

- Provide teleportation and spawn point.

- Store box economy (money, earnings).

## Fields

- `UUID id` – Unique identifier of the box.
- `UUID owner` – UUID of the owner.
- `Set<UUID> members` – List of members.
- `Location spawnLocation` – Spawn point of the box.
- `int size` – Size of the box.
- `long totalMoney` – Total accumulated money.
- `long earnings` – Money earned per second.

## Key Methods

- `addMember(UUID player): void` – Adds a player to the box.
- `removeMember(UUID player): void` – Removes a player from the box.
- `upgradeSize(): void` – Increases the box size.
- `getEarnings(): long` – Returns earnings per second.
- `getTotalMoney(): long` – Returns total money.

## Relations

- Used by: PlayerData, Commands.
- Depends on: Bukkit API (Location, WorldBorder).
