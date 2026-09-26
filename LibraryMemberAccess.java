import java.util.LinkedHashMap;
import java.util.Map;

class LibraryMemberAccess {

    public static String classifyAccess(String fieldModifier, String accessorContext) {
        if (fieldModifier == null || accessorContext == null) {
            return "DENIED";
        }

        switch (fieldModifier.toLowerCase()) {
            case "public":
                return "ALLOWED";
            case "private":
                return "SAME_CLASS".equalsIgnoreCase(accessorContext) ? "ALLOWED" : "DENIED";
            case "default":
                return ("SAME_CLASS".equalsIgnoreCase(accessorContext) || 
                        "SAME_PACKAGE".equalsIgnoreCase(accessorContext)) ? "ALLOWED" : "DENIED";
            case "protected":
                return ("SAME_CLASS".equalsIgnoreCase(accessorContext) || 
                        "SAME_PACKAGE".equalsIgnoreCase(accessorContext)) ? "ALLOWED" : "DENIED";
            default:
                return "DENIED";
        }
    }

    public static String summarizeByModifier(String[][] attempts) {
        if (attempts == null) {
            return "private: 0 allowed / 0 denied | default: 0 allowed / 0 denied | protected: 0 allowed / 0 denied | public: 0 allowed / 0 denied";
        }

        Map<String, int[]> counts = new LinkedHashMap<>();
        counts.put("private", new int[]{0, 0});
        counts.put("default", new int[]{0, 0});
        counts.put("protected", new int[]{0, 0});
        counts.put("public", new int[]{0, 0});

        for (String[] attempt : attempts) {
            if (attempt != null && attempt.length >= 2) {
                String mod = attempt[0].toLowerCase();
                String ctx = attempt[1];
                if (counts.containsKey(mod)) {
                    String res = classifyAccess(mod, ctx);
                    if ("ALLOWED".equals(res)) {
                        counts.get(mod)[0]++;
                    } else {
                        counts.get(mod)[1]++;
                    }
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        int index = 0;
        for (Map.Entry<String, int[]> entry : counts.entrySet()) {
            sb.append(entry.getKey()).append(": ")
              .append(entry.getValue()[0]).append(" allowed / ")
              .append(entry.getValue()[1]).append(" denied");
            if (index < counts.size() - 1) {
                sb.append(" | ");
            }
            index++;
        }

        return sb.toString();
    }
}

class LibraryMember {
    private String membershipId;
    protected String branchCode;
    double finesOwed; // default/package-private
    public String displayName;

    // Constructor enforcing membershipId length rules
    public LibraryMember(String membershipId, String branchCode, double finesOwed, String displayName) {
        if (!isValidMembershipId(membershipId)) {
            throw new IllegalArgumentException("construction rejected");
        }
        this.membershipId = membershipId.trim();
        this.branchCode = branchCode;
        this.finesOwed = finesOwed;
        this.displayName = displayName;
    }

    private static boolean isValidMembershipId(String id) {
        if (id == null) return false;
        String trimmed = id.trim();
        return !trimmed.isEmpty() && trimmed.length() >= 4;
    }

    public String getMembershipId() {
        return membershipId;
    }
}