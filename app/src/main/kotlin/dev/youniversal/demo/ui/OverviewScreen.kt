// SPDX-License-Identifier: MIT
package dev.youniversal.demo.ui

import android.os.Build
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.youniversal.theme.YouniversalBackgroundStyle
import dev.youniversal.theme.YouniversalButton
import dev.youniversal.theme.YouniversalCard
import dev.youniversal.theme.YouniversalHeroCard
import dev.youniversal.theme.YouniversalMetrics
import dev.youniversal.theme.YouniversalSettingRow
import dev.youniversal.theme.YouniversalTextButton
import dev.youniversal.theme.YouniversalTheme
import dev.youniversal.theme.YouniversalThemeState
import dev.youniversal.theme.youniversalCardBorder
import dev.youniversal.theme.youniversalContrastAgainst

/** Screen 1: what Youniversal is, the three background themes, and every theme control. */
@Composable
internal fun OverviewScreen(
    themeState: YouniversalThemeState,
    onNavigate: (Int) -> Unit,
) {
    DemoScreen {
        item { HeroCard() }

        item {
            Column(verticalArrangement = Arrangement.spacedBy(YouniversalMetrics.SpacingMd)) {
                Text(
                    text = "Background themes",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                )
                Row(horizontalArrangement = Arrangement.spacedBy(YouniversalMetrics.SpacingMd)) {
                    listOf(
                        YouniversalBackgroundStyle.Light,
                        YouniversalBackgroundStyle.Dark,
                        YouniversalBackgroundStyle.Cream,
                    ).forEach { style ->
                        StylePreviewCard(
                            style = style,
                            selected = YouniversalTheme.backgroundStyle == style,
                            onClick = { themeState.setBackgroundStyle(style) },
                            modifier = Modifier.weight(1f),
                        )
                    }
                }
                Text(
                    text =
                        "Each card is a nested YouniversalTheme rendering itself — the same call an " +
                            "app makes at its root.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }

        item { ThemePanel(state = themeState) }

        item { ActiveConfigurationCard(themeState = themeState) }

        item {
            Row(horizontalArrangement = Arrangement.spacedBy(YouniversalMetrics.SpacingMd)) {
                YouniversalButton(
                    text = "Components",
                    onClick = { onNavigate(1) },
                    modifier = Modifier.weight(1f),
                )
                YouniversalTextButton(
                    text = "Type & color",
                    onClick = { onNavigate(3) },
                    modifier = Modifier.weight(1f),
                )
            }
        }
    }
}

@Composable
private fun HeroCard() {
    YouniversalHeroCard(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Youniversal",
            style = MaterialTheme.typography.displaySmall,
            color = MaterialTheme.colorScheme.onSurface,
        )
        Text(
            text =
                "A drop-in Jetpack Compose design system: three background themes, full Material You " +
                    "support, and a rounded component set that can be the whole app — or an option " +
                    "the user switches off.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Text(
            text = "v1.0.0 · MIT licensed",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.primary,
        )
    }
}

/**
 * A miniature of each background theme.
 *
 * Implemented by nesting [YouniversalTheme] with `styleSystemBars = false`, so the preview does not
 * fight the host app over the status bar.
 */
@Composable
private fun StylePreviewCard(
    style: YouniversalBackgroundStyle,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val outerScheme = MaterialTheme.colorScheme
    YouniversalCard(
        onClick = onClick,
        modifier = modifier,
        border =
            if (selected) BorderStroke(2.dp, outerScheme.primary)
            else youniversalCardBorder(),
    ) {
        YouniversalTheme(
            backgroundStyle = style,
            dynamicColor = false,
            animateTransitions = false,
            styleSystemBars = false,
        ) {
            val scheme = MaterialTheme.colorScheme
            Column(
                modifier = Modifier.padding(YouniversalMetrics.SpacingMd),
                verticalArrangement = Arrangement.spacedBy(YouniversalMetrics.SpacingSm),
            ) {
                Text(
                    text = style.label,
                    style = MaterialTheme.typography.titleSmall,
                    color = scheme.onSurface,
                    maxLines = 1,
                )
                Bar(color = scheme.primary, weight = 1f)
                Bar(color = scheme.tertiary, weight = 0.7f)
                Bar(color = scheme.surfaceContainerHighest, weight = 0.85f)
            }
        }
    }
}

@Composable
private fun Bar(color: Color, weight: Float) {
    Box(
        modifier =
            Modifier
                .fillMaxWidth(weight)
                .height(7.dp)
                .clip(MaterialTheme.shapes.extraSmall)
                .background(color),
    )
}

/** Read-only summary of the active configuration, with contrast measured live. */
@Composable
private fun ActiveConfigurationCard(themeState: YouniversalThemeState) {
    val scheme = MaterialTheme.colorScheme
    val style = YouniversalTheme.backgroundStyle
    val dynamicSupported = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
    val seedActive = themeState.accentSeed != Color.Unspecified
    val paletteSource =
        when {
            !themeState.enabled -> "Baseline Material 3 (Youniversal is off)"
            seedActive -> "Generated from the accent seed"
            themeState.dynamicColor && dynamicSupported && style == YouniversalBackgroundStyle.Cream ->
                "Material You accents on the Cream neutrals"
            themeState.dynamicColor && dynamicSupported -> "Material You (wallpaper)"
            else -> "Youniversal brand palette"
        }

    YouniversalCard(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(YouniversalMetrics.SpacingXl)) {
            Text(
                text = "Active now",
                style = MaterialTheme.typography.titleMedium,
                color = scheme.onSurface,
            )
            YouniversalSettingRow(title = "Background", subtitle = style.label)
            YouniversalSettingRow(
                title = "Palette source",
                subtitle = paletteSource,
            )
            YouniversalSettingRow(
                title = "Body text contrast",
                subtitle =
                    "%.2f:1 against the background — WCAG AA needs 4.5:1"
                        .format(scheme.onSurface.youniversalContrastAgainst(scheme.background)),
            )
            YouniversalSettingRow(
                title = "Button label contrast",
                subtitle =
                    "%.2f:1 on the accent".format(
                        scheme.onPrimary.youniversalContrastAgainst(scheme.primary),
                    ),
            )
        }
    }
}
