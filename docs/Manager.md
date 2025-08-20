# Managers

> Central services that coordinate data, persistence, and game logic for Tycoon. Keep *entities* (Box, PlayerData) dumb; put logic here.

---

## Manager: BoxManager

**Description**  
Creates, finds, and deletes boxes. Manages membership and ownership.

**Responsibilities**

- Create/disband boxes.
- Query by owner/member.
- Add/remove members; transfer ownership.
- Validate constraints (one box per player, team size).

**Data**

- `Map<UUID, Box> byOwner`
- `Map<UUID, UUID> boxByMember` *(member -> ownerBoxId or boxId)*
- `int maxMembersPerBox` (from config)

**Key Methods**

- `Optional<Box> getByOwner(UUID owner)`
- `Optional<Box> getByMember(UUID player)`
- `Box createFor(UUID owner)`
- `void disband(UUID owner)`
- `boolean addMember(UUID owner, UUID target)`
- `boolean removeMember(UUID owner, UUID target)`
- `boolean transferOwnership(UUID currentOwner, UUID newOwner)`
- `Collection<Box> all()`

**Notes**

- Enforce: player cannot own two boxes; member cannot be in two boxes.
- Fire custom events (e.g., `BoxCreateEvent`, `BoxDisbandEvent`).

---

## Manager: PlayerDataManager

**Description**  
Loads, caches, and saves `PlayerData`. Provides money/earnings helpers.

**Responsibilities**

- Lifecycle: load on join, save on quit.
- Get/update balance, earnings, level.
- Periodic tick to add earnings to balance.

**Data**

- `Map<UUID, PlayerData> cache`
- `StorageAdapter storage` *(YAML/SQL)*
- `long tickIntervalMs`

**Key Methods**

- `PlayerData get(UUID player)` *(loads if missing)*
- `void save(UUID player)` / `void saveAll()`
- `void addBalance(UUID player, long amount)`
- `boolean trySpend(UUID player, long amount)`
- `void setEarnings(UUID player, long perSecond)`
- `void upgradeLevel(UUID player)`

**Notes**

- Run periodic task with Bukkit scheduler; avoid blocking I/O on main thread.

---

## Manager: InvitationManager

**Description**  
Handles invite -> accept/deny flow with expiration.

**Responsibilities**

- Send, list, revoke invitations.
- Validate target eligibility (no existing box, not already member).
- Expire invitations automatically.

**Data**

- `Map<UUID, PendingInvite>` *(target -> invite)*
- `duration expiry` (from config)

**Key Methods**

- `boolean invite(UUID owner, UUID target)`
- `boolean accept(UUID target)`
- `boolean deny(UUID target)`
- `Optional<PendingInvite> get(UUID target)`

**Notes**

- One active invite per target (or keep `List<PendingInvite>` if multi-source allowed).

---

## Manager: HomeManager

**Description**  
Stores/teleports to box home (sethome/home/setspawn).

**Responsibilities**

- Set/get box spawn & home.
- Safe teleport (chunk loaded, safe Y).

**Data**

- In `Box` or separate map: `Location home`, `Location spawn`

**Key Methods**

- `boolean setHome(UUID owner, Location loc)`
- `Optional<Location> getHome(UUID owner)`
- `boolean setSpawn(UUID owner, Location loc)`
- `Optional<Location> getSpawn(UUID owner)`
- `void teleportHome(Player p)`

**Notes**

- Validate world and border; fallback if null/unsafe.

---

## Manager: UpgradeManager

**Description**  
Applies upgrades to boxes (size, earnings, etc.).

**Responsibilities**

- Read upgrade costs/values from config.
- Check affordability; apply effects.

**Data**

- `UpgradeTree upgrades` *(parsed from config)*

**Key Methods**

- `boolean canUpgrade(UUID owner, UpgradeType type)`
- `UpgradeResult apply(UUID owner, UpgradeType type)`

**Notes**

- Coordinate with `PlayerDataManager.trySpend(...)` for payments.

---

## Manager: TopManager

**Description**  
Computes leaderboards for `/tycoon top`.

**Responsibilities**

- Rank by total money / earnings / level.
- Cache results to avoid recomputation each command.

**Data**

- `List<TopEntry> cached`
- `long lastUpdateMillis`
- `long ttlMillis` *(from config)*

**Key Methods**

- `List<TopEntry> topByMoney(int limit)`
- `List<TopEntry> topByEarnings(int limit)`
- `List<TopEntry> topByLevel(int limit)`

**Notes**

- Rebuild periodically or on save; avoid sorting large maps every call.

---

## Manager: SettingsManager

**Description**  
Holds per-box toggles and preferences (used by `/tycoon settings`).

**Responsibilities**

- Read/write settings (e.g., PVP in box, build permissions, invites).

**Data**

- In `Box.settings` or map: `BoxSettings { pvpEnabled, buildMembersOnly, ... }`

**Key Methods**

- `BoxSettings get(UUID owner)`
- `void set(UUID owner, Consumer<BoxSettings> edit)`

**Notes**

- Expose read-only view for UI; mutate via commands/menus only.

---

## Manager: ConfigManager

**Description**  
Central access to plugin configuration and messages.

**Responsibilities**

- Load `config.yml`, `messages.yml`.
- Provide typed getters and message formatting.

**Key Methods**

- `int getMaxMembers()`
- `Duration getInviteExpiry()`
- `String msg(String key, Object... args)`
- `reload()`

**Notes**

- Keep message keys consistent in `messages.yml`.


