package gg.saki.zaiko.utils;

import org.bukkit.ChatColor;
import org.jetbrains.annotations.Contract;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.bukkit.ChatColor.COLOR_CHAR;

public class StringUtil {

    private static final Pattern hexPattern = Pattern.compile("&#([A-Fa-f0-9]{6})");

    private StringUtil() {
        throw new UnsupportedOperationException("This class cannot be instantiated");
    }

    /**
     * Returns if the given string is null or empty.
     *
     * @param s the string to check
     * @return true if the string is null <b>or</b> empty
     */
    public static boolean isNullOrEmpty(String s) {
        return s == null || s.isEmpty();
    }

    /**
     * Returns if <b>any</b> of the given strings are null or empty.
     *
     * @param strings the strings to check
     * @return true if any of the strings are null <b>or</b> empty
     */
    @Contract(value = "null -> true")
    public static boolean isNullOrEmpty(String... strings) {
        return Arrays.stream(strings).anyMatch(StringUtil::isNullOrEmpty);
    }

    public static String translate(String message) {
        Matcher matcher = hexPattern.matcher(message);
        StringBuilder buffer = new StringBuilder(message.length() + 4 * 8);
        while (matcher.find())
        {
            String group = matcher.group(1);
            matcher.appendReplacement(buffer, COLOR_CHAR + "x"
                    + COLOR_CHAR + group.charAt(0) + COLOR_CHAR + group.charAt(1)
                    + COLOR_CHAR + group.charAt(2) + COLOR_CHAR + group.charAt(3)
                    + COLOR_CHAR + group.charAt(4) + COLOR_CHAR + group.charAt(5)
            );
        }
        return ChatColor.translateAlternateColorCodes('&', matcher.appendTail(buffer).toString());
    }
}
