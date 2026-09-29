// SPDX-License-Identifier: MIT
package dev.youniversal.demo.ui

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
import androidx.compose.ui.unit.dp
import dev.youniversal.demo.DemoIcons
import dev.youniversal.theme.YouniversalAvatar
import dev.youniversal.theme.YouniversalBackdrop
import dev.youniversal.theme.YouniversalBackdropStyle
import dev.youniversal.theme.YouniversalBadge
import dev.youniversal.theme.YouniversalButton
import dev.youniversal.theme.YouniversalCard
import dev.youniversal.theme.YouniversalDivider
import dev.youniversal.theme.YouniversalElevatedCard
import dev.youniversal.theme.YouniversalEmptyState
import dev.youniversal.theme.YouniversalHeroCard
import dev.youniversal.theme.YouniversalMetrics
import dev.youniversal.theme.YouniversalOutlinedCard
import dev.youniversal.theme.YouniversalSettingRow
import dev.youniversal.theme.YouniversalTextButton

/** Screen 3: containers, lists and the ambient backdrop. */
@Composable
internal fun SurfacesScreen(onMessage: (String) -> Unit) {
    DemoScreen {
        item {
            DemoSection(
                title = "Cards",
                description = "Tone and a hairline edge do the separating; shadows are reserved for things that float.",
            ) {
                YouniversalCard(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(YouniversalMetrics.SpacingLg)) {
                        Text("Filled card", style = MaterialTheme.typography.titleSmall)
                        Text(
                            text = "surfaceContainerLow with an outlineVariant edge.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                }
                YouniversalElevatedCard(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(YouniversalMetrics.SpacingLg)) {
                        Text("Elevated card", style = MaterialTheme.typography.titleSmall)
                        Text(
                            text = "For content that overlaps other content.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                }
                YouniversalOutlinedCard(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(YouniversalMetrics.SpacingLg)) {
                        Text("Outlined card", style = MaterialTheme.typography.titleSmall)
                        Text(
                            text = "No fill change — for dense or nested content.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                }
                YouniversalCard(
                    onClick = { onMessage("Tappable card") },
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Column(modifier = Modifier.padding(YouniversalMetrics.SpacingLg)) {
                        Text("Tappable card", style = MaterialTheme.typography.titleSmall)
                        Text(
                            text = "Ripple plus a small lift while enabled.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                }
            }
        }

        item {
            YouniversalHeroCard(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Hero surface",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                Text(
                    text =
                        "A gradient wash built from the theme's own primary and tertiary at 14% alpha, " +
                            "so it stays correct in every palette.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Row(horizontalArrangement = Arrangement.spacedBy(YouniversalMetrics.SpacingSm)) {
                    YouniversalButton(text = "Get started", onClick = { onMessage("Hero CTA") })
                    YouniversalTextButton(text = "Learn more", onClick = { onMessage("Hero secondary") })
                }
            }
        }

        item {
            DemoSection(
                title = "Lists",
                description = "Setting rows group inside a card; dividers use outlineVariant.",
            ) {
                YouniversalSettingRow(
                    title = "Ada Lovelace",
                    subtitle = "Design systems",
                    leadingIcon = DemoIcons.Overview,
                    trailing = { YouniversalBadge(text = "New") },
                )
                YouniversalDivider()
                YouniversalSettingRow(
                    title = "Grace Hopper",
                    subtitle = "Compilers",
                    leadingIcon = DemoIcons.Components,
                    trailing = { YouniversalBadge(text = "3", containerColor = MaterialTheme.colorScheme.secondaryContainer, contentColor = MaterialTheme.colorScheme.onSecondaryContainer) },
                )
                YouniversalDivider()
                YouniversalSettingRow(
                    title = "Alan Turing",
                    subtitle = "Tap to open",
                    leadingIcon = DemoIcons.Layers,
                    onClick = { onMessage("Row tapped") },
                    trailing = { YouniversalBadge(text = "Open") },
                )
                YouniversalDivider()
                Row(
                    modifier = Modifier.padding(YouniversalMetrics.SpacingLg),
                    horizontalArrangement = Arrangement.spacedBy(YouniversalMetrics.SpacingMd),
                ) {
                    YouniversalAvatar(initials = "AL")
                    YouniversalAvatar(initials = "GH", containerColor = MaterialTheme.colorScheme.secondaryContainer, contentColor = MaterialTheme.colorScheme.onSecondaryContainer)
                    YouniversalAvatar(icon = DemoIcons.About, containerColor = MaterialTheme.colorScheme.tertiaryContainer, contentColor = MaterialTheme.colorScheme.onTertiaryContainer)
                }
            }
        }

        item {
            DemoSection(
                title = "Backdrop",
                description = "The layer behind app content. YouniversalScaffold draws it for the whole screen.",
            ) {
                YouniversalBackdropStyle.entries.forEach { style ->
                    YouniversalBackdrop(
                        style = style,
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .height(84.dp)
                                .clip(MaterialTheme.shapes.medium),
                    ) {
                        Box(modifier = Modifier.padding(YouniversalMetrics.SpacingLg)) {
                            Text(
                                text = style.name,
                                style = MaterialTheme.typography.labelLarge,
                                color = MaterialTheme.colorScheme.onBackground,
                            )
                        }
                    }
                }
            }
        }

        item {
            YouniversalCard(modifier = Modifier.fillMaxWidth()) {
                YouniversalEmptyState(
                    title = "Nothing here yet",
                    description = "The empty state uses the same type scale and spacing as the rest of the system.",
                    icon = DemoIcons.Layers,
                    action = { YouniversalButton(text = "Add the first item", onClick = { onMessage("Added") }) },
                )
            }
        }
    }
}
