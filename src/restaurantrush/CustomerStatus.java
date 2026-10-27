package restaurantrush;

/** Where a customer is in the visit, from arriving to paying or leaving. */
public enum CustomerStatus {
    WAITING,
    SEATED,
    ORDERED,
    EATING,
    READY_TO_PAY,
    PAID,
    LEFT
}
