public class AccessChecker {

    public static String classifyAccess(String fieldModifier, String accessorContext) {
        if (fieldModifier == null || accessorContext == null) {
            return "DENIED";
        }

        String mod = fieldModifier.toLowerCase();
        String ctx = accessorContext.toUpperCase();

        if ("public".equals(mod)) {
            return "ALLOWED";
        }

        if ("private".equals(mod)) {
            return "SAME_CLASS".equals(ctx) ? "ALLOWED" : "DENIED";
        }

        if ("default".equals(mod)) {
            return ("SAME_CLASS".equals(ctx) || "SAME_PACKAGE".equals(ctx)) ? "ALLOWED" : "DENIED";
        }

        if ("protected".equals(mod)) {
            switch (ctx) {
                case "SAME_CLASS":
                case "SAME_PACKAGE":
                case "SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE":
                    return "ALLOWED";
                case "DIFFERENT_PACKAGE":
                case "SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE":
                    return "DENIED";
                default:
                    return "DENIED";
            }
        }

        return "DENIED";
    }

    public static String describeContext(String accessorContext) {
        if (accessorContext == null || accessorContext.trim().isEmpty()) {
            return "";
        }

        String[] parts = accessorContext.split("_");
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < parts.length; i++) {
            if (parts[i].isEmpty()) continue;
            String word = parts[i].toLowerCase();
            sb.append(Character.toUpperCase(word.charAt(0)))
              .append(word.substring(1));

            if (i < parts.length - 1) {
                sb.append(" ");
            }
        }

        return sb.toString();
    }
}