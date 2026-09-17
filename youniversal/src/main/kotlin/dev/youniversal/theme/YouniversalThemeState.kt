// SPDX-License-Identifier: MIT
package dev.youniversal.theme

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily

/**
 * Holds every Youniversal setting, persists it to `SharedPreferences`, and exposes each value as
 * Compose state so a settings screen re-themes the app the moment a control changes.
 *
 * ```
 * val themeState = rememberYouniversalThemeState()
 * YouniversalTheme(state = themeState) {
 *     Switch(checked = themeState.enabled, onCheckedChange = themeState::setEnabled)
 * }
 * ```
 */
@Stable
public class YouniversalThemeState internal constructor(context: Context) {

    private val preferences: SharedPreferences =
        context.applicationContext.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

    /** Master switch. When false the host app's own theme renders the content. */
    public var enabled: Boolean by mutableStateOf(preferences.getBoolean(KEY_ENABLED, true))
        private set

    /** The background theme. Defaults to following the system. */
    public var backgroundStyle: YouniversalBackgroundStyle by mutableStateOf(
        readEnum(KEY_STYLE, YouniversalBackgroundStyle.Auto),
    )
        private set

    /** Material You / wallpaper color. Requires Android 12+. */
    public var dynamicColor: Boolean by mutableStateOf(preferences.getBoolean(KEY_DYNAMIC, true))
        private set

    /** Default or high contrast. */
    public var contrast: YouniversalContrast by mutableStateOf(
        readEnum(KEY_CONTRAST, YouniversalContrast.Default),
    )
        private set

    /** Custom accent seed, or [Color.Unspecified] to use the Youniversal accent. */
    public var accentSeed: Color by mutableStateOf(readSeed())
        private set

    /** Corner radius multiplier, 0f–2f. */
    public var cornerScale: Float by mutableStateOf(
        preferences.getFloat(KEY_CORNERS, 1f).coerceIn(0f, 2f),
    )
        private set

    /** Type scale multiplier, 0.7f–2f. */
    public var fontScale: Float by mutableStateOf(
        preferences.getFloat(KEY_FONT_SCALE, 1f).coerceIn(0.7f, 2f),
    )
        private set

    /** Morph colors when the theme changes. */
    public var animateTransitions: Boolean by mutableStateOf(
        preferences.getBoolean(KEY_ANIMATE, true),
    )
        private set

    /**
     * Typeface override. Not persisted — a font is an object, not a preference — so restore it at
     * startup with [setFontFamily] if your app ships one.
     */
    public var fontFamily: FontFamily? by mutableStateOf(null)
        private set

    public fun setEnabled(value: Boolean) {
        enabled = value
        preferences.edit().putBoolean(KEY_ENABLED, value).apply()
    }

    public fun setBackgroundStyle(value: YouniversalBackgroundStyle) {
        backgroundStyle = value
        preferences.edit().putString(KEY_STYLE, value.name).apply()
    }

    public fun setDynamicColor(value: Boolean) {
        dynamicColor = value
        preferences.edit().putBoolean(KEY_DYNAMIC, value).apply()
    }

    public fun setContrast(value: YouniversalContrast) {
        contrast = value
        preferences.edit().putString(KEY_CONTRAST, value.name).apply()
    }

    /** Pass [Color.Unspecified] to clear the seed and return to the Youniversal accent. */
    public fun setAccentSeed(value: Color) {
        accentSeed = value
        val editor = preferences.edit()
        if (value == Color.Unspecified) {
            editor.remove(KEY_SEED)
        } else {
            editor.putLong(KEY_SEED, value.toArgb().toLong() and 0xFFFFFFFFL)
        }
        editor.apply()
    }

    public fun setCornerScale(value: Float) {
        cornerScale = value.coerceIn(0f, 2f)
        preferences.edit().putFloat(KEY_CORNERS, cornerScale).apply()
    }

    public fun setFontScale(value: Float) {
        fontScale = value.coerceIn(0.7f, 2f)
        preferences.edit().putFloat(KEY_FONT_SCALE, fontScale).apply()
    }

    public fun setAnimateTransitions(value: Boolean) {
        animateTransitions = value
        preferences.edit().putBoolean(KEY_ANIMATE, value).apply()
    }

    public fun setFontFamily(value: FontFamily?) {
        fontFamily = value
    }

    /** Returns every persisted setting to its default. [fontFamily] is cleared too. */
    public fun reset() {
        preferences.edit().clear().apply()
        enabled = true
        backgroundStyle = YouniversalBackgroundStyle.Auto
        dynamicColor = true
        contrast = YouniversalContrast.Default
        accentSeed = Color.Unspecified
        cornerScale = 1f
        fontScale = 1f
        animateTransitions = true
        fontFamily = null
    }

    private fun readSeed(): Color {
        if (!preferences.contains(KEY_SEED)) return Color.Unspecified
        return Color(preferences.getLong(KEY_SEED, 0L))
    }

    private inline fun <reified T : Enum<T>> readEnum(key: String, default: T): T {
        val name = preferences.getString(key, null) ?: return default
        return enumValues<T>().firstOrNull { it.name == name } ?: default
    }

    private companion object {
        const val PREFERENCES_NAME = "youniversal_theme"
        const val KEY_ENABLED = "enabled"
        const val KEY_STYLE = "background_style"
        const val KEY_DYNAMIC = "dynamic_color"
        const val KEY_CONTRAST = "contrast"
        const val KEY_SEED = "accent_seed"
        const val KEY_CORNERS = "corner_scale"
        const val KEY_FONT_SCALE = "font_scale"
        const val KEY_ANIMATE = "animate_transitions"
    }
}

/** Creates (and remembers) a [YouniversalThemeState] backed by `SharedPreferences`. */
@Composable
public fun rememberYouniversalThemeState(
    context: Context = LocalContext.current,
): YouniversalThemeState = remember(context.applicationContext) { YouniversalThemeState(context) }
