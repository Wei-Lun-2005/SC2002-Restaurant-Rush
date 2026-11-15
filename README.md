# Restaurant Rush

A turn-based restaurant management game with a command-line interface, written in Java. SC2002/SC2302 group project by lab group TAIS2, group 5.

- **Repository:** https://github.com/Wei-Lun-2005/SC2002-Restaurant-Rush
- **Stage tags:** [`stage-1`](https://github.com/Wei-Lun-2005/SC2002-Restaurant-Rush/tree/stage-1), [`stage-2`](https://github.com/Wei-Lun-2005/SC2002-Restaurant-Rush/tree/stage-2), [`stage-3`](https://github.com/Wei-Lun-2005/SC2002-Restaurant-Rush/tree/stage-3)
- **Stage comparisons:** [stage-1...stage-2](https://github.com/Wei-Lun-2005/SC2002-Restaurant-Rush/compare/stage-1...stage-2), [stage-2...stage-3](https://github.com/Wei-Lun-2005/SC2002-Restaurant-Rush/compare/stage-2...stage-3)
- **Diagrams:** [class diagram](docs/uml/class-diagram.png), [sequence diagram](docs/uml/sequence-diagram.png)
- **Test runs:** [docs/test-notes.md](docs/test-notes.md)
- **Video timestamps:** [see below](#video-timestamps)

## Contents

1. [Build and run](#build-and-run)
2. [How to play](#how-to-play)
3. [Game rules](#game-rules)
4. [Project structure](#project-structure)
5. [Diagrams](#diagrams)
6. [Test runs](#test-runs)
7. [Stages](#stages)
8. [Video timestamps](#video-timestamps)

## Build and run

Requires Java 17 or later. From the repository root:

```
javac --release 17 -d out src/restaurantrush/*.java
java -cp out restaurantrush.ConsoleUI
```

To run an earlier stage, check out its tag and build again, for example `git checkout stage-1`. Use `git checkout main` to return to the latest version.

## How to play

You are the manager of a restaurant with 2 tables, a Host, a Waiter, a Chef and a Cashier. Each staff member can act at most once per turn, so every turn you decide who is seated, served and cooked for first.

Each turn the prompts come in this order, and a prompt only appears when it applies:

1. **Happy Hour:** `y` or `n`, until it has been used.
2. **Host:** the id of a waiting customer to seat, or `0` to skip.
3. **Waiter:** a task and the id it applies to, or `0` to wait.
4. **Chef:** the id of the order to work on, or `0` to wait.

The Cashier acts on its own. The game ends after turn 18. You win if at least 4 customers have paid, the revenue is at least $45.00 and the average satisfaction of the paying customers is at least 60.

## Game rules

### Customers

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
| Orders | Salad | Burger and Pasta (combo) | Pasta | Pasta | Burger | Pasta and Salad (combo) |
| Price | $9.00 | $29.70, VIP pays $26.73 | $18.00 | $18.00 | $15.00 | $24.30 |

### Host

The Host is a fourth staff member. Each turn the Host can seat at most one waiting customer, in addition to the Waiter, with the same checks: the customer must be waiting and a table must be free. The Host acts before the Waiter, so the Waiter can take the order of a customer the Host has just seated.

### Happy Hour

Happy Hour can be started once per game, at the start of a turn before any staff member acts. It lasts that turn and the next. The start of each turn shows whether it is available, active (with the turns left) or ended. While it is active:

- orders taken get 20% off;
- every customer still waiting for a table or for food (WAITING, SEATED or ORDERED) recovers 15 satisfaction at the start of each active turn, up to 100.

Orders taken before Happy Hour keep their price, and customers who have already left are not affected.

### Prices

An order's price is locked when the Waiter takes it, and the Cashier later charges exactly that price. The UI shows it, for example `Customer 4 ordered Pasta (order 4, $14.40).`

1. Start from the dish's price. A combo costs 90% of its two items, rounded to the nearest cent.
2. Apply the customer's own discount (VIP: 10% off).
3. If Happy Hour is active, take 20% off that, rounded to the nearest cent.

For example, a VIP's Burger during Happy Hour costs $15.00 → $13.50 → $10.80. In this game the VIP orders Burger and Pasta: $29.70 → $26.73, or $21.38 during Happy Hour.

## Project structure

```
src/restaurantrush/    Java source (21 classes in Stage 3)
docs/uml/              class diagram and sequence diagram (.drawio source and .png)
docs/test-runs/        input files for the victory and defeat runs of each stage
docs/test-notes.md     expected result of every run, turn by turn
CONTRIBUTING.md        team workflow: branches, pull requests and coding rules
```

## Diagrams

Each diagram has its `.drawio` source next to the `.png`.

| Diagram | Describes | Link |
|---------|-----------|------|
| Class diagram | Stage 3 (final design) | [docs/uml/class-diagram.png](docs/uml/class-diagram.png) |
| Class diagram | Stage 2 | [class-diagram.png at `stage-2`](https://github.com/Wei-Lun-2005/SC2002-Restaurant-Rush/blob/stage-2/docs/uml/class-diagram.png) |
| Class diagram | Stage 1 | [class-diagram.png at `stage-1`](https://github.com/Wei-Lun-2005/SC2002-Restaurant-Rush/blob/stage-1/docs/uml/class-diagram.png) |
| Sequence diagram | Stage 3: the Critic's visit with Happy Hour | [docs/uml/sequence-diagram.png](docs/uml/sequence-diagram.png) |

## Test runs

The game has no randomness, so the same input always gives the same result. Replay a run from the repository root after building:

```
java -cp out restaurantrush.ConsoleUI < docs/test-runs/stage3-victory.txt
```

In Windows PowerShell, use `cmd /c "java -cp out restaurantrush.ConsoleUI < docs\test-runs\stage3-victory.txt"`. The Stage 1 and Stage 2 runs were recorded at their tags, so check out the tag first.

| Stage | Run | Input file | Result |
|-------|-----|------------|--------|
| 3 | Victory | `docs/test-runs/stage3-victory.txt` | 5 paid, $83.13 revenue, average satisfaction 85.6 |
| 3 | Defeat | `docs/test-runs/stage3-defeat.txt` | 0 paid, $0.00 revenue, 2 unhappy departures |
| 2 | Victory | `docs/test-runs/stage2-victory.txt` | 5 paid, $73.50 revenue, average satisfaction 75.6 |
| 2 | Defeat | `docs/test-runs/stage2-defeat.txt` | 0 paid, $0.00 revenue, 2 unhappy departures |
| 1 | Victory | `docs/test-runs/victory.txt` | 5 paid, $66.00 revenue, average satisfaction 79.2 |
| 1 | Defeat | `docs/test-runs/defeat.txt` | 0 paid, $0.00 revenue, 2 unhappy departures |

[docs/test-notes.md](docs/test-notes.md) lists the inputs and the expected state after every turn.

## Stages

| Stage | Tag | Adds |
|-------|-----|------|
| 1 Basic operation | [`stage-1`](https://github.com/Wei-Lun-2005/SC2002-Restaurant-Rush/tree/stage-1) | Regular customers, menu, Waiter, Chef, Cashier, full turn sequence, both endings |
| 2 Customer behaviour | [`stage-2`](https://github.com/Wei-Lun-2005/SC2002-Restaurant-Rush/tree/stage-2) | VIP and Critic customers |
| 3 New role and menu policy | [`stage-3`](https://github.com/Wei-Lun-2005/SC2002-Restaurant-Rush/tree/stage-3) | Host, ComboMeal, Happy Hour |

### Stage 1 → Stage 2

**Compare:** [stage-1...stage-2](https://github.com/Wei-Lun-2005/SC2002-Restaurant-Rush/compare/stage-1...stage-2)

**New files**

- `VIPCustomer.java`: overrides `getWaitPenalty()` (5) and `calculateBill()` (10% off).
- `CriticCustomer.java`: overrides `getWaitPenalty()` (12) and `chooseItem()` (always Pasta).
- `docs/test-runs/stage2-victory.txt` and `stage2-defeat.txt`: Stage 2 test runs with a VIP and a Critic.

**Changed files**

- `GameController.java`: `createArrival()` creates a VIP on turn 4 and a Critic on turn 10 (new constants `VIP_ARRIVAL_TURN` and `CRITIC_ARRIVAL_TURN`). This is the only place that names the concrete customer classes, so something here has to change to create the new types. The turn sequence itself is unchanged.
- `Customer.java`: adds abstract `getTypeName()`, so the UI can label each customer without checking its type.
- `RegularCustomer.java`: implements `getTypeName()`, since it is now abstract in `Customer`.
- `ConsoleUI.java`: prints the type label in the queue and table lines.
- `docs/uml/class-diagram.drawio` and `.png`: adds the two new classes and `getTypeName()`.
- `docs/test-notes.md`: adds the Stage 2 runs.

**Deleted files:** none.

**Unchanged:** `Restaurant`, `Table`, `Order`, `MenuItem`, the staff classes and the turn sequence. `GameController.endTurn()` already calls `loseSatisfaction()`, which uses `getWaitPenalty()`; `takeOrder()` already calls `chooseItem()`; and `Cashier` already calls `calculateBill()`. The new behaviour comes from the subclasses overriding these methods, with no type checks anywhere.

### Stage 2 → Stage 3

**Compare:** [stage-2...stage-3](https://github.com/Wei-Lun-2005/SC2002-Restaurant-Rush/compare/stage-2...stage-3)

**New files**

- `Dish.java`: interface with `getName()`, `getPriceCents()` and `getPrepUnits()`. An order must be able to hold either a single menu item or a combo. `MenuItem` is an enum and cannot extend a class, so an interface is the way for both to share one type.
- `ComboMeal.java`: a `Dish` made of two existing `MenuItem`s. Its prep units are the sum of both; its price is 90% of their total, rounded to the nearest cent.
- `HappyHour.java`: the once-per-game policy: whether it can start, the turns left, the 20% discount and the 15 recovery.
- `Host.java`: a `Staff` subclass with `seatCustomer()`, so it gets the one-action-per-turn rule from `Staff`.
- `docs/test-runs/stage3-victory.txt` and `stage3-defeat.txt`: Stage 3 test runs.

**Changed files**

- `MenuItem.java`: `implements Dish`. Its existing methods already match the interface, so nothing else changes.
- `Order.java`: the item is a `Dish` instead of a `MenuItem` (field, constructor and `getItem()`), so an order can hold a combo. `Order` and the UI only call `getName()` and `getPrepUnits()` on the item, so the Chef and the UI handle a combo with no type checks.
- `Customer.java`: `chooseItem()` returns a `Dish` and applies the combo rule (see [Food choice](#food-choice)). Adds `recoverSatisfaction()` and `MAX_SATISFACTION` for Happy Hour; recovery uses the same status check as `loseSatisfaction()`, so only customers still waiting for a table or food recover.
- `CriticCustomer.java`: `chooseItem()` returns `Dish` to match `Customer`. The Critic still always orders Pasta.
- `Restaurant.java`: creates and owns the Host (`host`, `getHost()`), like the other staff.
- `GameController.java`:
  - owns a `HappyHour`. `activateHappyHour()` starts it (once, and only before any staff member acts), `startTurn()` gives the recovery while it is active and `endTurn()` counts it down.
  - `hostSeatCustomer()` runs the Host's action, and `startTurn()` resets the Host with the other staff.
  - `takeOrder()` locks the price with `lockPrice()`: the customer's own discount first, then Happy Hour.
  - `customersInRestaurant()` is the list `endTurn()` already built, moved into a method so `recoverWaitingCustomers()` can use it too.
- `Cashier.java`: charges `order.getPriceCents()` instead of calling `calculateBill()`. Discounts are now applied once, when the order is taken; calling `calculateBill()` again would give the VIP 10% off twice.
- `ConsoleUI.java`: Happy Hour and Host prompts at the start of each turn, a Happy Hour status line, the Host's tasks in the task list, and the locked price when an order is taken.
- `Waiter.java`: three error messages now end with a full stop, to match the Host's. No change in behaviour.
- `docs/uml/class-diagram.drawio` and `.png`: adds `Dish`, `ComboMeal`, `HappyHour` and `Host`, and the changes above.
- `docs/test-notes.md`: adds the Stage 3 runs. The Stage 1 and Stage 2 runs are now replayed at their tags.

**Deleted files:** none.

**Unchanged:** `Staff`, `Chef`, `Table`, `VIPCustomer`, `RegularCustomer`, `ActionResult`, `CustomerStatus` and `OrderStatus`. The Host reuses `Staff`'s one-action-per-turn rule, and `VIPCustomer.calculateBill()` is still the VIP discount, now applied when the order is taken instead of at payment. Each turn now starts with the optional Happy Hour and the Host; the Waiter, Chef and end-of-turn steps are as before.

## Video timestamps

Times are in the group video, `SC2302_TAIS2_G5_Video.mp4` (16:02). The gameplay is the Stage 3 victory run, then the Stage 3 defeat run replayed from its input file.

| # | Segment | Time |
|---|---------|------|
| 1 | The game launching from the submitted project | 1:56 |
| 2 | Meaningful manager choices, including a decision where staff are constrained | 2:03 (the manager's choices each turn, from turn 1); 4:37 (turn 13: the Host seats customer 5 so that the Waiter can serve the Critic's Pasta) |
| 3 | A dish going READY → SERVED → payment | 4:16 (turn 12: the Critic's Pasta is READY); 4:37 (turn 13: SERVED); 4:59 (turn 15: the Critic pays) |
| 4 | A VIP or Critic behaviour | 2:44 (turn 4: the VIP orders a combo at 10% off); 3:39 (turn 10: the Critic arrives); 3:55 (turn 11: the Critic orders Pasta) |
| 5 | A Stage 3 feature | 3:39 (turn 10: Happy Hour starts and the Host seats the Critic); 3:55 (turn 11: the price is locked at $14.40, 20% off); 6:06 (the same run with and without Happy Hour) |
| 6 | One victory and one defeat | 5:26 (victory); 5:42 (defeat run, replayed from its input file); 5:51 (defeat result) |
| 7 | The stage tag comparisons and the diagrams | 6:34 (final class diagram); 10:19 (class diagrams of all three stages); 10:46 (Stage 1 → Stage 2); 11:28 (Stage 2 → Stage 3); 13:04 (sequence diagram) |

Parts of the video:

| Time | Part |
|------|------|
| 0:00 | Game overview and the three stages |
| 1:50 | Gameplay demonstration and key outcomes |
| 6:34 | Class diagrams and evidence of stage changes |
| 12:12 | Selected use case and sequence diagram |
| 14:20 | Individual contributions and key learning |
