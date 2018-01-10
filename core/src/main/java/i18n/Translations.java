package i18n;

import java.util.Locale;

public final class Translations {

    private Translations() {
    }

    public static final String StringsBundle = "speedith/core/i18n/strings";

    public static String i18n(String key) {
        return java.util.ResourceBundle.getBundle(StringsBundle).getString(key);
    }


    public static String i18n(String key, Object... args) {
        return String.format(java.util.ResourceBundle.getBundle(StringsBundle).getString(key), args);
    }

    public static String i18n(Locale locale, String key) {
        if (locale == null) {
            return i18n(key);
        } else {
            return java.util.ResourceBundle.getBundle(StringsBundle, locale).getString(key);
        }
    }

    public static String i18n(Locale locale, String key, Object... args) {
        if (locale == null) {
            return i18n(key, args);
        } else {
            return String.format(java.util.ResourceBundle.getBundle(StringsBundle, locale).getString(key), args);
        }
    }
}