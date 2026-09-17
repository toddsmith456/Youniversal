// SPDX-License-Identifier: MIT
package dev.youniversal.demo.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.youniversal.theme.YouniversalCard
import dev.youniversal.theme.YouniversalMetrics

/**
 * Shared scaffolding for the showcase screens: a scrollable screen with even spacing, and titled
 * cards that group each feature.
 */

@Composable
public fun DemoScreen(
    modifier: Modifier = Modifier,
    content: LazyListScope.() -> Unit,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding =
            PaddingValues(
                start = YouniversalMetrics.ScreenPadding,
                end = YouniversalMetrics.ScreenPadding,
                top = YouniversalMetrics.SpacingLg,
                bottom = YouniversalMetrics.SpacingXxl,
            ),
        verticalArrangement = Arrangement.spacedBy(YouniversalMetrics.SpacingLg),
        content = content,
    )
}

/** A feature group: a titled card holding the examples. */
@Composable
public fun DemoSection(
    title: String,
    modifier: Modifier = Modifier,
    description: String? = null,
    content: @Composable ColumnScope.() -> Unit,
) {
    YouniversalCard(modifier = modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(YouniversalMetrics.SpacingXl),
            verticalArrangement = Arrangement.spacedBy(YouniversalMetrics.SpacingMd),
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )
            if (description != null) {
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
            content()
        }
    }
}

/** A short caption under a group of examples. */
@Composable
public fun DemoCaption(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        style = MaterialTheme.typography.labelSmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = modifier,
    )
}
