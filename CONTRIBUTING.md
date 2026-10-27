# Working on this repo

## Setup

- Java 17 or newer (the code must compile with `--release 17`).
- Build and run from the repo root:

```
javac --release 17 -d out src/restaurantrush/*.java
java -cp out restaurantrush.ConsoleUI
```

## Branches

- `main` is always runnable. Do not push to it directly.
- Make one branch per task from the latest `main`:

```
git switch main
git pull
git switch -c stage1/<task>
```

- Branch names (lowercase, words joined with hyphens):

| Format | Use for | Example |
|---|---|---|
| `stage1/<task>` | Stage 1 work | `stage1/customer-order` |
| `stage2/<task>` | Stage 2 work | `stage2/vip-critic` |
| `stage3/<task>` | Stage 3 work | `stage3/happy-hour` |
| `docs/<topic>` | diagrams, README, report files | `docs/uml-stage2` |

### Stage 1 branches

| Branch | Classes |
|---|---|
| `stage1/game-controller` | `GameController` |
| `stage1/console-ui` | `ConsoleUI`, `ActionResult` |
| `stage1/customer-restaurant` | `Customer`, `RegularCustomer`, `CustomerStatus`, `Restaurant` |
| `stage1/order-menu-table` | `MenuItem`, `Order`, `OrderStatus`, `Table` |
| `stage1/staff` | `Staff`, `Waiter`, `Chef`, `Cashier` |

- Before opening a pull request, update your branch with `main`:

```
git switch main
git pull
git switch stage1/<task>
git merge main
```

## Commits

- Small commits with a short message in the imperative: `Add Order status changes`, `Fix table not freed on departure`.
- Only commit source, docs and diagrams. No `out/`, IDE folders or `.class` files.

## Pull requests

- Open a pull request into `main` and fill in the template.
- One other member reviews and merges it. Delete the branch after merging.
- The project must compile and start before it is merged.

## Tags

- Tag only when every pull request for the stage is merged and the game runs from a clean clone:

```
git switch main
git pull
git tag stage-1
git push origin stage-1
```

- Update `docs/uml/class-diagram.drawio` and `.png` to match the code before tagging.

## Coding rules

- Class, field and method names follow `docs/uml/class-diagram.png`. If you need to change one, tell the group and update the diagram in the same pull request.
- All fields are `private`. State changes only through methods such as `markServed()` or `seat()`, never public setters.
- List getters return a read-only view (`Collections.unmodifiableList(...)`).
- Money is `int` cents everywhere. Only `ConsoleUI.formatDollars()` turns it into `$0.00`.
- An order's price is fixed when the order is created.
- Invalid choices return `ActionResult.fail("reason")` and do not call `markActed()`, so the player can choose again.
- Game rules belong in `GameController` and the domain classes, never in `ConsoleUI`.
- Waiter actions fail once the Chef has acted this turn.
- No `instanceof` checks on customer types. Different behaviour comes from overridden methods.
- Constants are `private static final` and named in capitals.
- Naming: staff actions are verb + noun (`seatCustomer`, `takeOrder`). Status changes are `mark...()`. Yes/no questions start with `is` or `can`.
