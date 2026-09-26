import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LibraryMemberProfile {
    private String membershipId;
    private String name;
    private boolean premiumMember;
    private String hashedSecurityAnswer;
    private boolean isIdSet = false;

    // Public no-arg constructor
    public LibraryMemberProfile() {
        this(null, null);
    }

    // Name-only constructor
    public LibraryMemberProfile(String name) {
        this(null, name);
    }

    // Primary constructor chaining via this(...)
    public LibraryMemberProfile(String membershipId, String name) {
        if (membershipId != null) {
            setMembershipId(membershipId);
        }
        this.name = name;
        this.premiumMember = false;
    }

    public String getMembershipId() {
        return this.membershipId;
    }

    // Write-once property setter
    public void setMembershipId(String id) {
        if (!this.isIdSet && id != null) {
            this.membershipId = id;
            this.isIdSet = true;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPremiumMember() {
        return premiumMember;
    }

    public void setPremiumMember(boolean premiumMember) {
        this.premiumMember = premiumMember;
    }

    // Write-only property setter with one-way hashing
    public void setSecurityAnswer(String answer) {
        if (answer != null && !answer.trim().isEmpty()) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                byte[] hash = md.digest(answer.trim().getBytes());
                StringBuilder hex = new StringBuilder();
                for (byte b : hash) {
                    hex.append(String.format("%02x", b));
                }
                this.hashedSecurityAnswer = hex.toString();
            } catch (NoSuchAlgorithmException e) {
                this.hashedSecurityAnswer = String.valueOf(answer.hashCode());
            }
        }
    }
}