# Stage 1 test notes

Two reproducible runs on the base setting: one victory and one defeat. The game has no randomness, so the same input always gives the same result.

## How to replay

Build first (see the README), then feed an input file to the game from the repository root.

Bash, Git Bash or macOS/Linux:

```
java -cp out restaurantrush.ConsoleUI < docs/test-runs/victory.txt
java -cp out restaurantrush.ConsoleUI < docs/test-runs/defeat.txt
```

Windows PowerShell:

```
cmd /c "java -cp out restaurantrush.ConsoleUI < docs\test-runs\victory.txt"
cmd /c "java -cp out restaurantrush.ConsoleUI < docs\test-runs\defeat.txt"
```

You can also type the inputs below by hand. A prompt only appears when that staff member has something to do, so some turns have no Waiter or Chef input.

## Victory run

Input file: [`test-runs/victory.txt`](test-runs/victory.txt)

The Waiter always serves a READY dish first, then takes a seated customer's order, then seats the next customer. The Chef always prepares the open order.

| Turn | Waiter input | Chef input | What happens | Served | Departures | Revenue | Average |
|------|--------------|------------|--------------|--------|------------|---------|---------|
| 1 | `1` `1` | — | Customer 1 arrives and is seated | 0 | 0 | $0.00 | N/A |
| 2 | `2` `1` | `1` | Customer 1 orders Salad; the Chef finishes it (READY) | 0 | 0 | $0.00 | N/A |
| 3 | `3` `1` | — | Salad served | 1 | 0 | $0.00 | N/A |
| 4 | `1` `2` | — | Customer 2 arrives and is seated; customer 1 finishes eating (READY_TO_PAY) | 1 | 0 | $0.00 | N/A |
| 5 | `2` `2` | `2` | Customer 2 orders Burger (READY); customer 1 pays $9.00 at satisfaction 84 | 1 | 0 | $9.00 | 84.0 |
| 6 | `3` `2` | — | Burger served | 2 | 0 | $9.00 | 84.0 |
| 7 | `1` `3` | — | Customer 3 arrives and is seated; customer 2 is READY_TO_PAY | 2 | 0 | $9.00 | 84.0 |
| 8 | `2` `3` | `3` | Customer 3 orders Pasta (unit 1 of 2); customer 2 pays $15.00 at 84 | 2 | 0 | $24.00 | 84.0 |
| 9 | — | `3` | Pasta unit 2 of 2 (READY); the Waiter has nothing to do | 2 | 0 | $24.00 | 84.0 |
| 10 | `3` `3` | — | Pasta served; customer 4 arrives and waits | 3 | 0 | $24.00 | 84.0 |
| 11 | `1` `4` | — | Customer 4 is seated; customer 3 is READY_TO_PAY | 3 | 0 | $24.00 | 84.0 |
| 12 | `2` `4` | `4` | Customer 4 orders Salad (READY); customer 3 pays $18.00 at 76 | 3 | 0 | $42.00 | 81.3 |
| 13 | `3` `4` | — | Salad served; customer 5 arrives and waits | 4 | 0 | $42.00 | 81.3 |
| 14 | `1` `5` | — | Customer 5 is seated; customer 4 is READY_TO_PAY | 4 | 0 | $42.00 | 81.3 |
| 15 | `2` `5` | `5` | Customer 5 orders Burger (READY); customer 4 pays $9.00 at 76 | 4 | 0 | $51.00 | 80.0 |
| 16 | `3` `5` | — | Burger served; customer 6 arrives and waits | 5 | 0 | $51.00 | 80.0 |
| 17 | `1` `6` | — | Customer 6 is seated; customer 5 is READY_TO_PAY | 5 | 0 | $51.00 | 80.0 |
| 18 | `2` `6` | `6` | Customer 6 orders Pasta (unit 1 of 2); customer 5 pays $15.00 at 76 | 5 | 0 | $66.00 | 79.2 |

Final result:

```
You win!
Customers paid: 5
Customers served: 5
Unhappy departures: 0
Total revenue: $66.00
Average satisfaction: 79.2
Turns used: 18/18
```

All three targets are met: 5 paid (at least 4), $66.00 (at least $45.00) and an average of 79.2 (at least 60). Customer 6 arrives on turn 16 and cannot finish paying within 18 turns, as the brief expects.

## Defeat run

Input file: [`test-runs/defeat.txt`](test-runs/defeat.txt)

The Waiter chooses Wait (`0`) on every turn. No one is seated, so the Chef never has an order to prepare.

| Turns | What happens | Served | Departures | Revenue | Average |
|-------|--------------|--------|------------|---------|---------|
| 1–12 | Customers queue and lose 8 satisfaction per turn | 0 | 0 | $0.00 | N/A |
| 13 | Customer 1 (arrived turn 1) reaches 0 and leaves | 0 | 1 | $0.00 | N/A |
| 14–15 | Customers keep waiting | 0 | 1 | $0.00 | N/A |
| 16 | Customer 2 (arrived turn 4) reaches 0 and leaves | 0 | 2 | $0.00 | N/A |
| 17–18 | Customers keep waiting | 0 | 2 | $0.00 | N/A |

Final result:

```
You lose.
Customers paid: 0
Customers served: 0
Unhappy departures: 2
Total revenue: $0.00
Average satisfaction: N/A
Turns used: 18/18
```

No target is met: 0 paid, $0.00 revenue and no paid customers to average.
