# 🎮 Tycoon - Gameplay Scope

---

## Core Loop (MVP)

- Player runs `/tycoon start` → creates a Tycoon.
- Player runs `/tycoon collect` → retrieves stored resources.
- Player runs `/tycoon upgrade` → increases production & storage.
- Player can check status with `/tycoon info`.
- Tycoon **generates resources** every X seconds.

---

## Entities & Data (MVP)

### Box

- `id:` String

- `spawnBoxLocation` spawn location of the box

- `worldBorder` World seen when someone is the box

- `ownerUUID` The owner UUID

#### PlayerTycoon

- `owner`: UUID
- `tycoonLevel`: int (1, 2, ..., N)
- `generatorType`: id (e.g. `farm_wheat`)  
- `storageAmount`: int (items waiting to be collected)  
- `lastTick`: timestamp of last production  

#### GeneratorType (configured)

- `id`: string  
- `displayName`: string  
- `itemReward`: MATERIAL  
- `baseAmount`: int / tick  
- `baseInterval`: seconds  
- `upgrades[level]`: { cost: $, amount, interval }

---

## Rules (MVP)

- One Tycoon per player.  
- Offline production computed on join (capped at X minutes).  
- Storage cap per level (prevents infinite AFK).  
- No teams/multiplayer in MVP (solo only).  

---

## Commands (MVP)

- `/tycoon start` — create Tycoon.  
- `/tycoon collect` — collect stored resources.  
- `/tycoon upgrade` — upgrade if enough money.  
- `/tycoon info` — display Tycoon status.  

---

## Permissions

- `tycoon.start`  
- `tycoon.collect`  
- `tycoon.upgrade`  
- `tycoon.info`

---

## Configuration (MVP)

- `config.yml` → economy mode, offline cap, storage defaults.  
- `messages.yml` → all texts & colors.  
- `generators.yml` → at least one generator type.  

---

## Anti-scope (Not in MVP)

- No multiple Tycoons per player.  
- No teams/multiplayer.  
- No databases (YAML storage only).  
- No GUIs (commands only).  
- No cosmetics (particles, animations).  

---

## Post-MVP Extensions

- Teams & multiplayer Tycoons (shared progress, roles, invites).
- Advanced GUI menus.
- Multiple Tycoon types (farm, mine, factory).
- Quests & achievements.
- Leaderboards.
- Database support (MySQL/MongoDB).
- Public API (events, services).

---

## MVP Checklist

- [ ] `/tycoon start` → create Tycoon  
- [ ] `/tycoon collect` → collect resources  
- [ ] `/tycoon upgrade` → upgrade system  
- [ ] `/tycoon info` → display stats  
- [ ] Generator defined in `generators.yml`  
- [ ] Basic storage system (flat file)  
- [ ] Economy mode: internal (Vault later)  
- [ ] Offline production with cap
