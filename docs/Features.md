# Features – Tycoon

> Vue d’ensemble de **toutes les fonctionnalités** du plugin, reliées à tes documents existants.  
> Chaque bloc indique les **commandes**, **classes**, **managers**, la **persistance** concernée et des **critères d’acceptation**.  
> Utilise les cases à cocher pour suivre l’avancement (implémentation, tests, doc).

- Docs liées : [Commands.md](./Commands.md) · [Class.md](./Class.md) · [Manager.md](./Manager.md) · [Storage.md](./Storage.md)

---

## F01 — Persistance & sauvegardes (SQLite V1)

**Backend** : SQLite (voir [Storage.md](./Storage.md))  
**Exigences**

- I/O hors thread principal ; autosave périodique ; `saveAll` à l’arrêt.
- Sauvegardes périodiques avec rétention configurée.
- Restauration documentée.

**Statut** : Implé [] · Tests [] · Doc [x]

---

## F02 — Création de box

**Commandes** : [/tycoon start](./Commands.md#tycoon-start)  
**Classes** : `Box`, `PlayerData` (voir [Class.md](./Class.md))  
**Managers** : `BoxManager`, `PlayerDataManager` (voir [Manager.md](./Manager.md))  
**Persistance** : `PlayerData` + `Box` (voir [Storage.md](./Storage.md))  
**Messages clés** : `box.already_have`, `box.created`

**Critères d’acceptation**

- Un joueur sans box peut créer **exactement une** box.
- Redémarrage du serveur → la box persiste.
- Message d’erreur clair si le joueur a déjà une box.

**Tests**

- Start → succès ; Start 2e fois → erreur.
- Restart serveur → la box existe toujours.

**Statut** : Implé [] · Tests [] · Doc [x]

---

## F03 — Invitations (inviter / accepter / refuser)

**Commandes** : [/tycoon invite](./Commands.md#tycoon-invite), [/tycoon accept](./Commands.md#tycoon-accept), [/tycoon-deny](./Commands.md#tycoon-deny)  
**Classes** : `Box`, `PlayerData`  
**Managers** : `InvitationManager`, `BoxManager`  
**Persistance** : pas obligatoire (les invitations peuvent être en mémoire avec expiration)  
**Messages clés** : `invite.sent`, `invite.no_invite`, `invite.accepted`, `invite.denied`, `invite.already_has`, `team.full`

**Critères d’acceptation**

- On ne peut inviter qu’un joueur **sans** box et non-membre.
- Accept/deny ne fonctionnent que si une invitation **valide** existe.
- Limite d’expiration configurable.

**Tests**

- Invite → Accept → devient membre.
- Invite → Deny → pas membre.
- Invite duplicata → message approprié.

**Statut** : Implé [] · Tests [] · Doc [x]

---

## F04 — Gestion des membres (leave / kick / list)

**Commandes** : [/tycoon leave](./Commands.md#tycoon-leave), [/tycoon kick](./Commands.md#tycoon-kick), [/tycoon list](./Commands.md#tycoon-list)  
**Classes** : `Box`  
**Managers** : `BoxManager`  
**Persistance** : mise à jour des membres

**Critères d’acceptation**

- `leave` retire correctement le joueur de la box (pas d’orphelins).
- `kick` seulement par l’owner ; impossible de kicker l’owner via cette commande.
- `list` reflète l’état exact après leave/kick.

**Tests**

- Membre quitte → liste mise à jour.
- Owner kick un membre → OK ; membre kick owner → refusé.

**Statut** : Implé [] · Tests [] · Doc [x]

---

## F05 — Informations

**Commandes** : [/tycoon info](./Commands.md#tycoon-info)  
**Données affichées** : Owner, Members, Box size, Total money, Earnings/s  
**Critères d’acceptation**

- Les infos proviennent de la source persistée/cachée la plus à jour.
- Messages d’erreur si le joueur n’a pas de box.

**Statut** : Implé [] · Tests [] · Doc [x]

---

## F06 — Propriété & rôles (promote / demote / transfer)

**Commandes** : [/tycoon promote](./Commands.md#tycoon-promote), [/tycoon demote](./Commands.md#tycoon-demote), [/tycoon transfer](./Commands.md#tycoon-transfer)  
**Managers** : `BoxManager`  
**Critères d’acceptation**

- Seul l’owner peut promouvoir/démote/transférer.
- `transfer` conserve le **même** `boxId`.
- Les rôles sont reflétés dans les préconditions des autres commandes.

**Statut** : Implé [] · Tests [] · Doc [x]

---

## F07 — Téléportation & points (sethome / home / setspawn)

**Commandes** : [/tycoon sethome](./Commands.md#tycoon-sethome), [/tycoon home](./Commands.md#tycoon-home), [/tycoon setspawn](./Commands.md#tycoon-setspawn)  
**Managers** : `HomeManager`  
**Critères d’acceptation**

- TP “safe” (chunk chargé, Y sûre).
- Refus si `home` non défini.

**Statut** : Implé [] · Tests [] · Doc [x]

---

## F08 — Économie & upgrades

**Commandes** : [/tycoon upgrade](./Commands.md#tycoon-upgrade)  
**Managers** : `PlayerDataManager`, `UpgradeManager`  
**Critères d’acceptation**

- Débit de la balance si upgrade acheté.
- Effets appliqués (taille/earnings) immédiatement et persistés.

**Statut** : Implé [] · Tests [] · Doc [x]

---

## F09 — Classement

**Commandes** : [/tycoon top](./Commands.md#tycoon-top)  
**Managers** : `TopManager`  
**Critères d’acceptation**

- Classement par argent (ou autre métrique) avec cache TTL.
- Performance acceptable même avec beaucoup de box.

**Statut** : Implé [] · Tests [] · Doc [x]

---

## F10 — Administration

**Commandes** : `/tycoon admin info|list|tp|delete|reload|setmoney|setlevel|kick` (voir [Commands.md](./Commands.md#tycoon-admin-delete))  
**Critères d’acceptation**

- Les commandes admin n’enfreignent pas les invariants (1 joueur = 0..1 box).
- `reload` recharge config/messages sans casser les tâches ni les caches.

**Statut** : Implé [] · Tests [] · Doc [x]

---

## F11 — Paramètres (settings)

**Commandes** : [/tycoon settings](./Commands.md#tycoon-settings)  
**Managers** : `SettingsManager`  
**Critères d’acceptation**

- Les toggles (ex. PVP) sont persistés et respectés par le gameplay associé.

**Statut** : Implé [] · Tests [] · Doc [x]

---

## F12 — Migration MySQL

**Objectif** : pouvoir passer à MySQL sans toucher au métier.  
**Pré-requis**

- Couche *Storage* abstraite ; commande d’admin de migration ; vérifications de comptage.  
  **Étapes**
- Test de connexion → migration dry-run → migration réelle → switch backend.

**Statut** : Implé [] · Tests [] · Doc [x]

---

## Annexes

**Invariants globaux**

- 1 joueur **possède au plus une** box.
- Un membre n’apparaît **qu’une seule fois** dans une box.
- `transfer` ne change pas le `boxId`.
- Aucun appel Bukkit dans la couche *Storage*.
