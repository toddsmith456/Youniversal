// SPDX-License-Identifier: MIT
package dev.youniversal.demo.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import dev.youniversal.demo.DemoIcons
import dev.youniversal.theme.YouniversalButton
import dev.youniversal.theme.YouniversalCard
import dev.youniversal.theme.YouniversalHeroCard
import dev.youniversal.theme.YouniversalMetrics
import dev.youniversal.theme.YouniversalSettingRow
import dev.youniversal.theme.YouniversalThemeState

private const val Version = "1.0.0"

private val IntegrationSnippet =
    """
    // 1. Youniversal as the whole app
    setContent {
        YouniversalTheme(
            backgroundStyle = YouniversalBackgroundStyle.Cream,
            dynamicColor = true,
        ) {
            App()
        }
    }

    // 2. Youniversal as an option you can toggle
    val theme = rememberYouniversalThemeState()

    YouniversalTheme(state = theme) {
        // state.enabled == false -> the fallback
        // Material 3 theme renders your app instead
        Settings(
            checked = theme.enabled,
            onCheckedChange = theme::setEnabled,
        )
        App()
    }
    """.trimIndent()

private const val LicenseText =
    "MIT License\n\n" +
        "Copyright (c) 2026 Todd Smith and the Youniversal contributors\n\n" +
        "Permission is hereby granted, free of charge, to any person obtaining a copy of this " +
        "software and associated documentation files (the \"Software\"), to deal in the Software " +
        "without restriction, including without limitation the rights to use, copy, modify, merge, " +
        "publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons " +
        "to whom the Software is furnished to do so, subject to the following conditions:\n\n" +
        "The above copyright notice and this permission notice shall be included in all copies or " +
        "substantial portions of the Software.\n\n" +
        "THE SOFTWARE IS PROVIDED \"AS IS\", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, " +
        "INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR " +
        "PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE " +
        "FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR " +
        "OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER " +
        "DEALINGS IN THE SOFTWARE."

/** Screen 5: what the template is, how to adopt it, and the license. */
@Composable
internal fun AboutScreen(themeState: YouniversalThemeState) {
    DemoScreen {
        item {
            YouniversalHeroCard(modifier = Modifier.fillMaxWidth()) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(YouniversalMetrics.SpacingMd),
                ) {
                    Box(
                        modifier =
                            Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primary),
                        contentAlignment = Alignment.Center,
                    ) {
                        androidx.compose.material3.Icon(
                            imageVector = DemoIcons.Mark,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier.size(26.dp),
                        )
                    }
                    Column {
                        Text(
                            text = "Youniversal",
                            style = MaterialTheme.typography.headlineSmall,
                            color = MaterialTheme.colorScheme.onSurface,
                        )
                        Text(
                            text = "Version $Version · MIT licensed",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                }
                Text(
                    text =
                        "A Jetpack Compose design system written in Kotlin. Drop it in as the whole " +
                            "UI, or let users switch it on and off — when it is off, your own theme " +
                            "takes over and nothing Youniversal-branded is applied.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }

        item {
            DemoSection(title = "What is in the box") {
                FeatureRow("Three background themes", "Light, Dark and Cream — an aged-paper palette that is a little darker than a fresh page.")
                FeatureRow("Full Material You", "Wallpaper colors on Android 12+, and a seed generator so a brand accent works everywhere.")
                FeatureRow("48 tuned color roles", "Every Material 3 role is set, including the fixed roles, and verified against WCAG AA.")
                FeatureRow("Extended roles", "Success, warning and info, plus backdrop glows and shimmer colors.")
                FeatureRow("Rounded component set", "Cards, buttons, fields, chips, a sliding segmented control, lists, bars, dialogs.")
                FeatureRow("Motion built in", "Theme changes morph every color role instead of snapping.")
                FeatureRow("Accessible by default", "High-contrast mode, live contrast readouts, 48dp targets, semantic controls.")
            }
        }

        item {
            DemoSection(
                title = "Adopting it",
                description = "Add the :youniversal module (or the published artifact) and wrap your content.",
            ) {
                CodeBlock(text = IntegrationSnippet)
            }
        }

        item {
            DemoSection(title = "Theme state") {
                YouniversalSettingRow(
                    title = "Youniversal",
                    subtitle = if (themeState.enabled) "On" else "Off",
                    trailing = { YouniversalButton(text = "Toggle", onClick = { themeState.setEnabled(!themeState.enabled) }) },
                )
                YouniversalSettingRow(
                    title = "Background",
                    subtitle = themeState.backgroundStyle.label,
                )
                YouniversalSettingRow(
                    title = "Material You",
                    subtitle = if (themeState.dynamicColor) "Requested" else "Off",
                )
                YouniversalSettingRow(
                    title = "Restore defaults",
                    subtitle = "Clears the saved preferences on this device",
                    trailing = { YouniversalButton(text = "Reset", onClick = themeState::reset) },
                )
            }
        }

        item {
            DemoSection(title = "License") {
                Text(
                    text = LicenseText,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}

@Composable
private fun FeatureRow(title: String, description: String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSurface,
        )
        Text(
            text = description,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@Composable
private fun CodeBlock(text: String) {
    YouniversalCard(
        modifier = Modifier.fillMaxWidth(),
        containerColor = MaterialTheme.colorScheme.surfaceContainerHighest,
    ) {
        Text(
            text = text,
            style =
                MaterialTheme.typography.bodySmall.copy(
                    fontFamily = FontFamily.Monospace,
                    lineHeight = MaterialTheme.typography.bodySmall.lineHeight,
                ),
            color = MaterialTheme.colorScheme.onSurface,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(YouniversalMetrics.SpacingLg),
        )
    }
}
