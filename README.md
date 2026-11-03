# Restaurant Rush

SC2002 group assignment: a turn-based restaurant management game with a command-line interface, written in Java.

## Build and run

Requires Java 17 or later. From the repository root:

```
javac --release 17 -d out src/restaurantrush/*.java
java -cp out restaurantrush.ConsoleUI
```

Each turn, pick the Waiter's task and the id it applies to, then the order id for the Chef. Enter `0` to let either of them wait.

Reproducible victory and defeat runs are in [docs/test-notes.md](docs/test-notes.md).

## Customers

Six customers arrive on turns 1, 4, 7, 10, 13 and 16 and are numbered 1 to 6 in arrival order. Customer 2 is a VIP and customer 4 is a Critic; the rest are Regular. The UI shows each customer's type next to its number.

| Type | Loses per waiting turn | Bill |
|------|------------------------|------|
| Regular | 8 | Full price |
| VIP | 5 | 10% off |
| Critic | 12 | Full price |

### Food choice

Customers choose their own dish; the manager never chooses it. The rule is fixed so every run can be reproduced:

- Customer *n*'s usual item is menu item (*n* − 1) mod 3: Salad, Burger, Pasta, Salad, Burger, Pasta.
- Odd-numbered customers (1, 3, 5) order their usual item on its own.
- Even-numbered customers (2, 4, 6) order a combo of their usual item and the next item on the menu. A combo takes the prep units of both items and costs 90% of their combined price, rounded to the nearest cent.
- The Critic overrides this and always orders Pasta, the most expensive dish but also the slowest (2 Chef turns), so a Critic who already loses 12 per turn has to be seated and cooked for early.

| Customer | 1 | 2 | 3 | 4 | 5 | 6 |
|----------|---|---|---|---|---|---|
| Type | Regular | VIP | Regular | Critic | Regular | Regular |
| Orders | Salad | Burger + Pasta combo | Pasta | Pasta | Burger | Pasta + Salad combo |
| Price | $9.00 | $29.70, VIP pays $26.73 | $18.00 | $18.00 | $15.00 | $24.30 |

## Stages

| Stage | Tag | Adds |
|-------|-----|------|
| 1 Basic operation | `stage-1` | Regular customers, menu, Waiter, Chef, Cashier, full turn sequence, both endings |
| 2 Customer behaviour | `stage-2` | VIP and Critic customers |
| 3 New role and menu policy | `stage-3` | Host, ComboMeal, Happy Hour |

### Stage 1 → Stage 2

Compare: [stage-1...stage-2](https://github.com/Wei-Lun-2005/SC2002-Restaurant-Rush/compare/stage-1...stage-2)

New files:

- `VIPCustomer.java`: overrides `getWaitPenalty()` (5) and `calculateBill()` (10% off).
- `CriticCustomer.java`: overrides `getWaitPenalty()` (12) and `chooseItem()` (always Pasta).
- `docs/test-runs/stage2-victory.txt` and `stage2-defeat.txt`: Stage 2 test runs with a VIP and a Critic.

Changed files:

- `GameController.java`: `createArrival()` creates a VIP on turn 4 and a Critic on turn 10 (new constants `VIP_ARRIVAL_TURN` and `CRITIC_ARRIVAL_TURN`). This is the only place that names the concrete customer classes, so something here has to change to create the new types. The turn sequence itself is unchanged.
- `Customer.java`: adds abstract `getTypeName()`, so the UI can label each customer without checking its type.
- `RegularCustomer.java`: implements `getTypeName()`, since it is now abstract in `Customer`.
- `ConsoleUI.java`: prints the type label in the queue and table lines.
- `docs/uml/class-diagram.drawio` and `.png`: adds the two new classes and `getTypeName()`.
- `docs/test-notes.md`: adds the Stage 2 runs.

No files were deleted.

Unchanged: `Restaurant`, `Table`, `Order`, `MenuItem`, the staff classes and the turn sequence. `GameController.endTurn()` already calls `loseSatisfaction()`, which uses `getWaitPenalty()`; `takeOrder()` already calls `chooseItem()`; and `Cashier` already calls `calculateBill()`. The new behaviour comes from the subclasses overriding these methods, with no type checks anywhere.

### Stage 2 → Stage 3

_To be added: new files; existing files changed or deleted, with reasons; compare link._

## Video timestamps

_To be added._
