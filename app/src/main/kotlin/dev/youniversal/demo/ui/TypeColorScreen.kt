// SPDX-License-Identifier: MIT
package dev.youniversal.demo.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import dev.youniversal.theme.YouniversalBadge
import dev.youniversal.theme.YouniversalColorSwatch
import dev.youniversal.theme.YouniversalMetrics
import dev.youniversal.theme.YouniversalPalettePreview
import dev.youniversal.theme.YouniversalSettingRow
import dev.youniversal.theme.YouniversalTheme
import dev.youniversal.theme.youniversalContrastAgainst

/** Screen 4: the type scale, every color role, and the contrast the palette actually delivers. */
@Composable
internal fun TypeColorScreen() {
    val scheme = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography
    val extended = YouniversalTheme.extendedColors

    val typeScale: List<Pair<String, TextStyle>> =
        listOf(
            "displayLarge" to typography.displayLarge,
            "displayMedium" to typography.displayMedium,
            "displaySmall" to typography.displaySmall,
            "headlineLarge" to typography.headlineLarge,
            "headlineMedium" to typography.headlineMedium,
            "headlineSmall" to typography.headlineSmall,
            "titleLarge" to typography.titleLarge,
            "titleMedium" to typography.titleMedium,
            "titleSmall" to typography.titleSmall,
            "bodyLarge" to typography.bodyLarge,
            "bodyMedium" to typography.bodyMedium,
            "bodySmall" to typography.bodySmall,
            "labelLarge" to typography.labelLarge,
            "labelMedium" to typography.labelMedium,
            "labelSmall" to typography.labelSmall,
        )

    val contrastPairs: List<Triple<String, androidx.compose.ui.graphics.Color, androidx.compose.ui.graphics.Color>> =
        listOf(
            Triple("Body text", scheme.onSurface, scheme.background),
            Triple("Text on card", scheme.onSurface, scheme.surfaceContainerLow),
            Triple("Supporting text", scheme.onSurfaceVariant, scheme.surfaceVariant),
            Triple("Button label", scheme.onPrimary, scheme.primary),
            Triple("Tonal label", scheme.onSecondaryContainer, scheme.secondaryContainer),
            Triple("Error text", scheme.onError, scheme.error),
            Triple("Success text", extended.onSuccessContainer, extended.successContainer),
            Triple("Warning text", extended.onWarningContainer, extended.warningContainer),
            Triple("Info text", extended.onInfoContainer, extended.infoContainer),
        )

    DemoScreen {
        item {
            DemoSection(
                title = "Type scale",
                description = "Fifteen steps, retuned: tighter tracking on display sizes, longer line height for body.",
            ) {
                typeScale.forEach { (name, style) ->
                    Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                        Text(
                            text = name,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.width(104.dp),
                        )
                        Text(
                            text = "Youniversal",
                            style = style,
                            color = MaterialTheme.colorScheme.onSurface,
                            maxLines = 1,
                        )
                    }
                }
            }
        }

        item {
            DemoSection(
                title = "Color roles",
                description = "Every role of the palette in use right now, with its hex value.",
            ) {
                YouniversalPalettePreview(modifier = Modifier.fillMaxWidth())
            }
        }

        item {
            DemoSection(
                title = "Extended roles",
                description = "Semantic colors Material 3 leaves out, provided through YouniversalTheme.extendedColors.",
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(YouniversalMetrics.SpacingMd)) {
                    YouniversalColorSwatch(
                        name = "success",
                        color = extended.successContainer,
                        modifier = Modifier.weight(1f),
                    )
                    YouniversalColorSwatch(
                        name = "warning",
                        color = extended.warningContainer,
                        modifier = Modifier.weight(1f),
                    )
                    YouniversalColorSwatch(
                        name = "info",
                        color = extended.infoContainer,
                        modifier = Modifier.weight(1f),
                    )
                }
                Row(horizontalArrangement = Arrangement.spacedBy(YouniversalMetrics.SpacingMd)) {
                    YouniversalColorSwatch(name = "glow", color = extended.glow, modifier = Modifier.weight(1f))
                    YouniversalColorSwatch(
                        name = "shimmer",
                        color = extended.shimmerBase,
                        modifier = Modifier.weight(1f),
                    )
                    YouniversalColorSwatch(
                        name = "backdrop",
                        color = extended.backdropGlowA,
                        modifier = Modifier.weight(1f),
                    )
                }
            }
        }

        item {
            DemoSection(
                title = "Contrast",
                description = "Measured live from the active palette with the WCAG 2.1 relative-luminance formula.",
            ) {
                contrastPairs.forEach { (label, foreground, background) ->
                    val ratio = foreground.youniversalContrastAgainst(background)
                    YouniversalSettingRow(
                        title = label,
                        subtitle = "%.2f:1".format(ratio),
                        trailing = {
                            YouniversalBadge(
                                text = if (ratio >= 4.5f) "AA" else "Low",
                                containerColor =
                                    if (ratio >= 4.5f) extended.successContainer
                                    else MaterialTheme.colorScheme.errorContainer,
                                contentColor =
                                    if (ratio >= 4.5f) extended.onSuccessContainer
                                    else MaterialTheme.colorScheme.onErrorContainer,
                            )
                        },
                    )
                }
            }
        }

        item {
            Column {
                Text(
                    text = "Shapes scale with the corner radius preference; every role above is " +
                        "generated by tools/palette_lab.py and verified against WCAG AA.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}
