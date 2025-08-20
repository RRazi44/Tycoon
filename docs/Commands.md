# Commands

> Domain entities that model Tycoon state (e.g., `Box`, `PlayerData`). Keep them simple data holders with minimal logic. Prefer immutability where possible, no Bukkit calls, no I/O, no scheduling. Enforce light invariants only; push behavior to managers.

- **Should contain:** fields, getters, small pure methods, validation of invariants.

- **Should NOT contain:** persistence, command handling, teleportation/world edits, async tasks.

---

## /Tycoon Start

**Description:**

First command used by a player when they join the server.  
This command generates a new box for the player and initializes a new Box and PlayerData.

**Usage**

```
/tycoon start
```

**Permission** `tycoon.command.start`

**Preconditions**

- The player must not already have a box.

**Example**

```
/tycoon start
```

> You already have a box.  
> Successfully created a box.

---

## /Tycoon Invite

**Description** Allows a player to invite another player to their box.

**Usage**

```
/tycoon invite <player>
```

**Permission** `tycoon.command.invite`

**Preconditions**

- The player must already have a box.
- The team must not be full.
- The invited player must not already have a box.

**Example**

```
/tycoon invite RRazi44
```

> The player has been invited to your box!  
> This player already has a box.

---

## /Tycoon List

**Description** Shows the list of players in a box.

**Usage**

```
/tycoon list <player>
```

**Permission** `tycoon.command.list`

**Preconditions**

- The player must already have a box.

**Example**

```
/tycoon list RRazi44
```

> Box members: RRazi44, Notch  
> This player doesn’t have a box.

---

## /Tycoon Info

**Description** Displays information about the player’s box.

**Usage**

```
/tycoon info
```

**Permission** `tycoon.command.info`

**Preconditions**

- The player must already have a box.

**Example**

```
/tycoon info
```

> Owner: RRazi44  
> Members: Notch, Steve  
> Box size: 100  
> Total money: 15T $  
> Earnings: 3B/s

> This player doesn’t have a box.

---

## /Tycoon Leave

**Description** Allows a player to leave their current box.

**Usage**

```
/tycoon leave
```

**Permission** `tycoon.command.leave`

**Preconditions**

- The player must already have a box.

**Example**

```
/tycoon leave
```

> You have left your box.  
> You don’t have a box.

---

## /Tycoon Kick

**Description** Allows the owner to kick a member from their box.

**Usage**

```
/tycoon kick <player>
```

**Permission** `tycoon.command.kick`

**Preconditions**

- The executor must be the owner of the box.
- The target must be a member of the box.

**Example**

```
/tycoon kick Steve
```

> Steve has been kicked from your box.  
> This player is not in your box.

---

## /Tycoon Disband

**Description**  
Disbands the player’s box and removes all its members.

**Usage**

```
/tycoon disband
```

**Permission**  
`tycoon.command.disband`

**Preconditions**

- The player must be the owner of the box.

**Example**

```
/tycoon disband
```

> Your box has been disbanded.  
> You are not the owner of a box.

---

## /Tycoon Accept

**Description**  
Accepts a pending invitation to join a box.

**Usage**

```
/tycoon accept <player>
```

**Permission**  
`tycoon.command.accept`

**Preconditions**

- The player must have a pending invitation.

**Example**

```
/tycoon accept Notch
```

> You have joined Notch’s box.  
> You don’t have an invitation from this player.

---

## /Tycoon Deny

**Description**  
Denies a pending invitation to join a box.

**Usage**

```
/tycoon deny <player>
```

**Permission**  
`tycoon.command.deny`

**Preconditions**

- The player must have a pending invitation.

**Example**

```
/tycoon deny Notch
```

> You denied the invitation from Notch.  
> You don’t have an invitation from this player.

---

## /Tycoon Promote

**Description**  
Promotes a member to a higher rank inside the box.

**Usage**

```
/tycoon promote <player>
```

**Permission**  
`tycoon.command.promote`

**Preconditions**

- The executor must be the owner of the box.
- The target must be a member of the box.

**Example**

```
/tycoon promote Steve
```

> Steve has been promoted.  
> This player is not in your box.

---

## /Tycoon Demote

**Description**  
Demotes a member to a lower rank inside the box.

**Usage**

```
/tycoon demote <player>
```

**Permission**  
`tycoon.command.demote`

**Preconditions**

- The executor must be the owner of the box.
- The target must be a member of the box.

**Example**

```
/tycoon demote Steve
```

> Steve has been demoted.  
> This player is not in your box.

---

## /Tycoon Transfer

**Description**  
Transfers the ownership of the box to another member.

**Usage**

```
/tycoon transfer <player>
```

**Permission**  
`tycoon.command.transfer`

**Preconditions**

- The executor must be the current owner.
- The target must be a member of the box.

**Example**

```
/tycoon transfer Notch
```

> Ownership has been transferred to Notch.  
> This player is not in your box.

---

## /Tycoon Top

**Description**  
Shows the ranking of the top boxes.

**Usage**

```
/tycoon top
```

**Permission**  
`tycoon.command.top`

**Preconditions**

- None.

**Example**

```
/tycoon top
```

> #1 RRazi44 – 15T #2 Notch – 12T

---

## /Tycoon Help

**Description**  
Displays all available Tycoon commands.

**Usage**

```
/tycoon help
```

**Permission**  
`tycoon.command.help`

**Preconditions**

- None.

**Example**

```
/tycoon help
```

> /tycoon start – Create your box  
> /tycoon invite – Invite a friend

---

## /Tycoon Sethome

**Description**  
Sets the teleport location for the player’s box.

**Usage**

```
/tycoon sethome
```

**Permission**  
`tycoon.command.sethome`

**Preconditions**

- The player must already have a box.

**Example**

```
/tycoon sethome
```

> Your box home has been set.  
> You don’t have a box.

---

## /Tycoon Home

**Description**  
Teleports the player to their box home.

**Usage**

```
/tycoon home
```

**Permission**  
`tycoon.command.home`

**Preconditions**

- The player must already have a box.
- The box must have a home set.

**Example**

```
/tycoon home
```

> Teleporting to your box…  
> Your box does not have a home set.

---

## /Tycoon Upgrade

**Description**  
Upgrades the player’s box (size, earnings, etc.).

**Usage**

```
/tycoon upgrade
```

**Permission**  
`tycoon.command.upgrade`

**Preconditions**

- The player must already have a box.
- The player must have enough money.

**Example**

```
/tycoon upgrade
```

> Your box has been upgraded!  
> Not enough money.

---

## /Tycoon Settings

**Description**  
Opens a settings menu to configure the box.

**Usage**

```
/tycoon settings
```

**Permission**  
`tycoon.command.settings`

**Preconditions**

- The player must already have a box.

**Example**

```
/tycoon settings
```

> Opening box settings…

---

## /Tycoon Setspawn

**Description**  
Sets the spawn point inside the box.

**Usage**

```
/tycoon setspawn
```

**Permission**  
`tycoon.command.setspawn`

**Preconditions**

- The player must already have a box.

**Example**

```
/tycoon setspawn
```

> Your box spawn has been set.

---

## /Tycoon Admin Delete

**Description**  
Deletes a player’s box.

**Usage**

```
/tycoon admin delete <player>
```

**Permission**  
`tycoon.admin.delete`

**Preconditions**

- Executor must be an admin.
- Target player must have a box.

**Example**

```
/tycoon admin delete RRazi44
```

> RRazi44’s box has been deleted.  
> This player does not have a box.

---

## /Tycoon Admin Info

**Description**  
Displays information about another player’s box.

**Usage**

```
/tycoon admin info <player>
```

**Permission**  
`tycoon.admin.info`

**Preconditions**

- Executor must be an admin.

**Example**

```
/tycoon admin info Notch
```

> Owner: Notch  
> Members: Steve  
> Box size: 100  
> Total money: 20T $

---

## /Tycoon Admin List

**Description**  
Lists all existing boxes.

**Usage**

```
/tycoon admin list
```

**Permission**  
`tycoon.admin.list`

**Preconditions**

- Executor must be an admin.

**Example**

```
/tycoon admin list
```

> Box #1 – Owner: RRazi44 – Members: 3  
> Box #2 – Owner: Notch – Members: 5

---

## /Tycoon Admin Tp

**Description**  
Teleports an admin to a player’s box.

**Usage**

```
/tycoon admin tp <player>
```

**Permission**  
`tycoon.admin.tp`

**Preconditions**

- Executor must be an admin.
- Target player must have a box.

**Example**

```
/tycoon admin tp Steve
```

> Teleporting to Steve’s box…

---

## /Tycoon Admin Reload

**Description**  
Reloads the plugin configuration and messages.

**Usage**

```
/tycoon admin reload
```

**Permission**  
`tycoon.admin.reload`

**Preconditions**

- Executor must be an admin.

**Example**

```
/tycoon admin reload
```

> Tycoon configuration reloaded.

---

## /Tycoon Admin Setmoney

**Description**  
Sets the total money of a player’s box.

**Usage**

```
/tycoon admin setmoney <player> <amount>
```

**Permission**  
`tycoon.admin.setmoney`

**Preconditions**

- Executor must be an admin.
- Target player must have a box.

**Example**

```
/tycoon admin setmoney RRazi44 1000000
```

> Set RRazi44’s box money to 1,000,000.

---

## /Tycoon Admin Setlevel

**Description**  
Sets the level of a player’s box.

**Usage**

```
/tycoon admin setlevel <player> <level>
```

**Permission**  
`tycoon.admin.setlevel`

**Preconditions**

- Executor must be an admin.
- Target player must have a box.

**Example**

```
/tycoon admin setlevel Notch 5
```

> Set Notch’s box level to 5.

---

## /Tycoon Admin kickbox

**Description**

Kicks a player from their box

**Usage**

```
/tycoon admin kickbox <player>
```

**Permission**

``tycoon.admin.kickbox``

**Precondition**

- Executor must be an admin.

- Target player mush have a box.

**Example**

```
/tycoon admin kick Herobrine
```

> Kick Herobrine from their box

> This player does not have a box
