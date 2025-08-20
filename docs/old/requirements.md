# 📘 Tycoon - Requirements / Roadmap

## 1. Project Overview

- [x] Project setup (Maven, plugin.yml, GitHub repo)
- [ ] Define gameplay scope (tycoon mechanics, economy, upgrades)
- [ ] Document configuration structure (`config.yml`, `messages.yml`, `tycoons.yml`)

---

## 2. Core Features

- [x] `/tycoon start` command to create a base
- [ ] Save & load player data
- [ ] Resource generators
  - [ ] Define generators in `tycoons.yml`
  - [ ] Basic production (items every X seconds)
  - [ ] Upgrade system (levels, costs, production speed)
- [ ] Economy
  - [ ] Internal balance system
  - [ ] Optional Vault support

---

## 3. Configuration

- [ ] Global config (`config.yml`)
- [ ] Messages & translations (`messages.yml`)
- [ ] Tycoon definitions (`tycoons.yml`)
- [ ] Custom items (`items.yml`)

---

## 4. Storage

- [ ] Local storage (YAML/JSON)
- [ ] Database support
  - [ ] MySQL
  - [ ] MongoDB

---

## 5. Additional Features

- [ ] Permissions (`tycoon.start`, `tycoon.admin`)
- [ ] GUI menus for upgrades/shop
- [ ] Leaderboards (richest players, highest level)
- [ ] Public developer API

---

## 6. Roadmap (Milestones)

- [x] Project setup
- [ ] Basic tycoon creation (`/tycoon start`)
- [ ] Resource generator system
- [ ] Economy system (internal + Vault)
- [ ] Advanced YAML configuration
- [ ] Database storage
- [ ] GUI menus
- [ ] API release
