# Restaurant Rush

SC2002 group assignment: a turn-based restaurant management game with a command-line interface, written in Java.

## Build and run

Requires Java 17 or later. From the repository root:

```
javac --release 17 -d out src/restaurantrush/*.java
java -cp out restaurantrush.ConsoleUI
```

Each turn, pick the Waiter's task and the id it applies to, then the order id for the Chef. Enter `0` to let either of them wait.

## Customer food choice

Customers choose their own dish; the manager never chooses it. The rule is fixed so every run can be reproduced: customers are numbered 1 to 6 in arrival order, and customer *n* orders menu item (*n* − 1) mod 3.

| Customer | 1 | 2 | 3 | 4 | 5 | 6 |
|----------|---|---|---|---|---|---|
| Orders | Salad | Burger | Pasta | Salad | Burger | Pasta |

## Stages

| Stage | Tag | Adds |
|-------|-----|------|
| 1 Basic operation | `stage-1` | Regular customers, menu, Waiter, Chef, Cashier, full turn sequence, both endings |
| 2 Customer behaviour | `stage-2` | VIP and Critic customers |
| 3 New role and menu policy | `stage-3` | Host, ComboMeal, Happy Hour |

### Stage 1 → Stage 2

_To be added: new files; existing files changed or deleted, with reasons; compare link._

### Stage 2 → Stage 3

_To be added: new files; existing files changed or deleted, with reasons; compare link._

## Video timestamps

_To be added._
