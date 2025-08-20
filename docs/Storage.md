# Storage

Purpose: define how Tycoon persists and retrieves data, without binding the business logic to a specific database.

---

## Scope

- Persist and retrieve player state and box state.
- Support multiple storage backends that can be swapped without changing managers or commands.
- Guarantee reliability, integrity, and acceptable performance for a live server.

Out of scope:

- Bukkit API calls, command handling, business rules, or scheduling. Those belong to managers.

---

## Principles

- Replaceable backend: a common storage contract used by all managers.
- Non‑blocking: all disk or database operations off the main server thread.
- Atomicity: writes are performed in a way that prevents partial or corrupt files/rows.
- Versioned data: records include a schema version to support migrations.
- Minimal coupling: records are plain data; no references to game objects.
- Consistency: one player owns at most one box; membership cannot be duplicated.

---

## Records (conceptual model)

``Player data record``

- Player identifier (UUID as text).
- Owned box identifier or empty if none.
- Balance (total money).
- Earnings per second.
- Level or progression tier.
- Schema version.

``Box record``

- Box identifier (stable across ownership transfers).
- Owner identifier.
- Member identifiers (excluding owner).
- Size (for example, the side length of the protected area).
- Total money attributed to the box (if applicable).
- Earnings per second (if box‑scoped economy is used).
- Home location (world name and coordinates) or empty.
- Spawn location or empty.
- Schema version.

``Location value``

- World name.
- X, Y, Z coordinates.
- Yaw and pitch if needed.

Notes:

- Use stable identifiers (text UUIDs) for cross‑references.
- Do not embed game‑engine types in storage records.

---

## Storage responsibilities (what the backend must provide)

- Load a player record by identifier.
- Save a player record.
- Delete a player record.
- Load a box record by identifier.
- Save a box record.
- Delete a box record.
- Load all player records (maintenance and reports).
- Load all box records (leaderboards and scans).
- Close or dispose any underlying resources when the plugin stops.

Behavioral expectations

- If a record does not exist, return a neutral, empty, or default representation; let managers decide whether to create it.
- Operations must be safe to call concurrently from background tasks.
- Failures are surfaced with meaningful error messages for logging.

---

## Backends

YAML (MVP)

- Human‑readable files per player and per box.
- Suitable for small to medium servers and fast iteration.
- Must implement atomic write strategy to avoid corruption (for example, write‑then‑move); exact technique is left to the implementation.
- Keep periodic backups in a dated folder; configurable retention.

SQL (optional backend)

- Suitable for larger servers or multi‑instance setups.
- Tables for players, boxes, and a linking table for members.
- Use a connection pool and transactions for multi‑row updates.
- Add indexes for lookups by owner and by member.
- Migrations are applied when the stored schema version is lower than expected.

---

## Threading and performance

- All storage operations are executed outside the main server thread.
- Managers schedule saves periodically and on critical events (join, quit, disband, transfer).
- Reads are cached in memory by managers; storage is the source of truth on startup and shutdown.

---

## Atomicity, consistency, and durability

- Writes are atomic from the perspective of other readers.
- Box deletion removes all associated memberships and clears the owner reference from affected players.
- Ownership transfer updates the owner field but keeps the same box identifier.
- On shutdown, managers flush pending writes and then close the storage backend.

---

## Versioning and migrations

- Each record carries a schema version number.
- When loading, if the stored version is lower than the current version, the data is migrated in memory and saved back.
- A global storage version may be tracked in configuration to coordinate multi‑record migrations.
- Migrations must be idempotent and logged.

---

## Error handling and observability

- All failures are logged with context (operation, identifiers).
- Transient failures may be retried with a capped policy.
- A read‑only safety mode prevents overwriting data when a fatal load error occurs.
- Expose basic metrics in logs (number of records loaded, saved, skipped; time taken).

---

## Backups and recovery

- Automated periodic backups of the storage data directory or database dump, with configurable retention.
- Manual backup command may be provided for administrators.
- Recovery steps are documented: restore the latest backup, then start the server in read‑only safety mode to verify integrity.

---

## Security and privacy

- Do not store personal information beyond game identifiers and state.
- File and directory permissions should restrict write access to the server process.
- When using SQL, avoid logging credentials; store them in the server configuration securely.

---

## Acceptance criteria (definition of done)

- Startup loads all existing records without errors.
- Normal play (create, invite, leave, disband, transfer, upgrade) persists correctly across restarts.
- No storage operation blocks the main thread.
- Backups are created and old backups are pruned according to configuration.
- Migrations from the previous schema version complete successfully on first run.

---

## Implementation checklist

- Define a storage contract used by BoxManager and PlayerDataManager.
- Provide a YAML backend with atomic writes and backups.
- Provide optional SQL backend with pooling, transactions, and indexes.
- Ensure managers cache reads and batch writes on a scheduler.
- Add configuration for backup retention, autosave interval, and read‑only safety mode.
- Add logs and counters to observe storage behavior at runtime.
