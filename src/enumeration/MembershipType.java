package enumeration;

public enum MembershipType {
    BASIC(1),
    PREMIUM(3),
    VIP(12);

    private final int durationInMonths;

    MembershipType(int durationInMonths) {
        this.durationInMonths = durationInMonths;
    }

    public int getDurationInMonths() {
        return durationInMonths;
    }
}
