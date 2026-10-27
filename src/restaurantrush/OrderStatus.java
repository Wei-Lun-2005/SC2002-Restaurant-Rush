package restaurantrush;

/** Where an order is, from the kitchen to payment or cancellation. */
public enum OrderStatus {
    PREPARING,
    READY,
    SERVED,
    PAID,
    CANCELLED
}
