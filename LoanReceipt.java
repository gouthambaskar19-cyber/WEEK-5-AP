import java.util.Arrays;

public class LoanReceipt {
    private final String memberId;
    private final String[] bookIds;

    private static final String BOOK_ID_REGEX = "^BK-\\d{3}$";

    public LoanReceipt() {
        this.memberId = null;
        this.bookIds = new String[0];
    }

    public LoanReceipt(String memberId, String[] bookIds) {
        if (bookIds == null) {
            throw new IllegalArgumentException("construction rejected");
        }

        for (String id : bookIds) {
            if (id == null || !id.matches(BOOK_ID_REGEX)) {
                throw new IllegalArgumentException("construction rejected");
            }
        }

        this.memberId = memberId;
        // Defensive copy on input
        this.bookIds = Arrays.copyOf(bookIds, bookIds.length);
    }

    public String getMemberId() {
        return memberId;
    }

    public String[] getBookIds() {
        // Defensive copy on output
        return Arrays.copyOf(bookIds, bookIds.length);
    }

    // Wither pattern for immutable alteration
    public LoanReceipt withCorrectedBookId(int index, String newId) {
        if (index < 0 || index >= bookIds.length) {
            throw new IndexOutOfBoundsException("Invalid book ID index");
        }
        if (newId == null || !newId.matches(BOOK_ID_REGEX)) {
            throw new IllegalArgumentException("Invalid book ID format");
        }

        String[] updatedBookIds = Arrays.copyOf(this.bookIds, this.bookIds.length);
        updatedBookIds[index] = newId;
        return new LoanReceipt(this.memberId, updatedBookIds);
    }

    public static String processNightlyCirculation(LoanReceipt[] receipts) {
        if (receipts == null) {
            return "0 processed | 0 null skipped | 0 reference-only | 0 regular";
        }

        int processed = 0;
        int nullSkipped = 0;
        int referenceOnly = 0;
        int regular = 0;

        for (LoanReceipt receipt : receipts) {
            if (receipt == null) {
                nullSkipped++;
                continue;
            }

            processed++;
            if (receipt instanceof ReferenceOnlyLoanReceipt) {
                referenceOnly++;
            } else {
                regular++;
            }
        }

        return processed + " processed | " + nullSkipped + " null skipped | " + 
               referenceOnly + " reference-only | " + regular + " regular";
    }
}

class ReferenceOnlyLoanReceipt extends LoanReceipt {
    private final String roomNumber;

    public ReferenceOnlyLoanReceipt(String memberId, String[] bookIds, String roomNumber) {
        super();
        this.roomNumber = roomNumber;
    }

    public String getRoomNumber() {
        return roomNumber;
    }
}