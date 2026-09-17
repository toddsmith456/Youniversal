// SPDX-License-Identifier: MIT
package dev.youniversal.demo.ui

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dev.youniversal.demo.DemoIcons
import dev.youniversal.theme.YouniversalAlertDialog
import dev.youniversal.theme.YouniversalAssistChip
import dev.youniversal.theme.YouniversalButton
import dev.youniversal.theme.YouniversalCheckbox
import dev.youniversal.theme.YouniversalDivider
import dev.youniversal.theme.YouniversalElevatedButton
import dev.youniversal.theme.YouniversalExtendedFab
import dev.youniversal.theme.YouniversalFab
import dev.youniversal.theme.YouniversalFilterChip
import dev.youniversal.theme.YouniversalMetrics
import dev.youniversal.theme.YouniversalOutlinedButton
import dev.youniversal.theme.YouniversalOutlinedTextField
import dev.youniversal.theme.YouniversalProgressBar
import dev.youniversal.theme.YouniversalRadioButton
import dev.youniversal.theme.YouniversalSegmentedControl
import dev.youniversal.theme.YouniversalSettingRow
import dev.youniversal.theme.YouniversalSlider
import dev.youniversal.theme.YouniversalSuggestionChip
import dev.youniversal.theme.YouniversalSwitch
import dev.youniversal.theme.YouniversalTextButton
import dev.youniversal.theme.YouniversalTextField
import dev.youniversal.theme.YouniversalTonalButton
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/** Screen 2: every interactive component, wired to state so each one actually works. */
@Composable
internal fun ComponentsScreen(onMessage: (String) -> Unit) {
    var filledText by rememberSaveable { mutableStateOf("") }
    var outlinedText by rememberSaveable { mutableStateOf("Cream") }
    var switchOn by rememberSaveable { mutableStateOf(true) }
    var agreed by rememberSaveable { mutableStateOf(true) }
    var radioIndex by rememberSaveable { mutableIntStateOf(0) }
    var sliderValue by rememberSaveable { mutableFloatStateOf(0.42f) }
    var loading by rememberSaveable { mutableStateOf(false) }
    var dialogOpen by rememberSaveable { mutableStateOf(false) }
    var selectedFilters by rememberSaveable { mutableStateOf(setOf("Rounded")) }
    var segmented by rememberSaveable { mutableStateOf("Day") }

    val scope = rememberCoroutineScope()
    val radioOptions = listOf("Auto", "System", "Manual")
    val filterOptions = listOf("Rounded", "Material You", "Cream")

    DemoScreen {
        item {
            DemoSection(
                title = "Buttons",
                description = "Five Material 3 variants as pills, all driven by the active palette.",
            ) {
                YouniversalButton(
                    text = "Primary",
                    onClick = { onMessage("Primary tapped") },
                    modifier = Modifier.fillMaxWidth(),
                )
                Row(horizontalArrangement = Arrangement.spacedBy(YouniversalMetrics.SpacingMd)) {
                    YouniversalTonalButton(
                        text = "Tonal",
                        onClick = { onMessage("Tonal tapped") },
                        modifier = Modifier.weight(1f),
                    )
                    YouniversalElevatedButton(
                        text = "Elevated",
                        onClick = { onMessage("Elevated tapped") },
                        modifier = Modifier.weight(1f),
                    )
                }
                Row(horizontalArrangement = Arrangement.spacedBy(YouniversalMetrics.SpacingMd)) {
                    YouniversalOutlinedButton(
                        text = "Outlined",
                        onClick = { onMessage("Outlined tapped") },
                        modifier = Modifier.weight(1f),
                    )
                    YouniversalTextButton(
                        text = "Text",
                        onClick = { onMessage("Text tapped") },
                        modifier = Modifier.weight(1f),
                    )
                }
                YouniversalButton(
                    text = "With icon",
                    onClick = { onMessage("Icon button tapped") },
                    leadingIcon = DemoIcons.Check,
                )
                YouniversalButton(text = "Disabled", onClick = {}, enabled = false)
            }
        }

        item {
            DemoSection(
                title = "Async state",
                description = "The label swaps for a spinner without the button changing size.",
            ) {
                YouniversalButton(
                    text = if (loading) "Saving" else "Save changes",
                    onClick = {
                        loading = true
                        scope.launch {
                            delay(1_400)
                            loading = false
                            onMessage("Changes saved")
                        }
                    },
                    loading = loading,
                    modifier = Modifier.fillMaxWidth(),
                )
                YouniversalProgressBar(progress = { sliderValue })
                DemoCaption("Determinate progress, rounded to match the buttons.")
            }
        }

        item {
            DemoSection(
                title = "Text fields",
                description = "Filled fields drop the underline; outlined fields use a hairline that strengthens on focus.",
            ) {
                YouniversalTextField(
                    value = filledText,
                    onValueChange = { filledText = it },
                    label = "Project name",
                    placeholder = "Untitled",
                    leadingIcon = DemoIcons.Sliders,
                    supportingText = "Filled, ${filledText.length} characters",
                    modifier = Modifier.fillMaxWidth(),
                )
                YouniversalOutlinedTextField(
                    value = outlinedText,
                    onValueChange = { outlinedText = it },
                    label = "Theme name",
                    placeholder = "Youniversal",
                    supportingText = "Outlined",
                    modifier = Modifier.fillMaxWidth(),
                )
                YouniversalTextField(
                    value = "Read only",
                    onValueChange = {},
                    label = "Locked field",
                    readOnly = true,
                    isError = true,
                    supportingText = "Error state",
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }

        item {
            DemoSection(
                title = "Chips",
                description = "Assist, filter and suggestion chips share the pill shape.",
            ) {
                Row(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(YouniversalMetrics.SpacingSm),
                ) {
                    filterOptions.forEach { option ->
                        YouniversalFilterChip(
                            text = option,
                            selected = option in selectedFilters,
                            onClick = {
                                selectedFilters =
                                    if (option in selectedFilters) selectedFilters - option
                                    else selectedFilters + option
                            },
                        )
                    }
                    YouniversalAssistChip(text = "Assist", onClick = { onMessage("Assist chip") })
                    YouniversalSuggestionChip(text = "Suggestion", onClick = { onMessage("Suggestion chip") })
                }
                DemoCaption("Selected: ${selectedFilters.sorted().joinToString(", ").ifEmpty { "none" }}")
            }
        }

        item {
            DemoSection(
                title = "Selection controls",
                description = "Switch, checkbox, radio and slider, all tinted from the theme.",
            ) {
                YouniversalSettingRow(
                    title = "Ambient backdrop",
                    subtitle = if (switchOn) "Aurora glows are drawn" else "Flat background",
                    trailing = { YouniversalSwitch(checked = switchOn, onCheckedChange = { switchOn = it }) },
                )
                YouniversalSettingRow(
                    title = "I understand this is a demo",
                    trailing = { YouniversalCheckbox(checked = agreed, onCheckedChange = { agreed = it }) },
                )
                YouniversalDivider()
                radioOptions.forEachIndexed { index, option ->
                    YouniversalSettingRow(
                        title = option,
                        onClick = { radioIndex = index },
                        leadingIcon = if (radioIndex == index) DemoIcons.Check else null,
                        trailing = {
                            YouniversalRadioButton(selected = radioIndex == index, onClick = { radioIndex = index })
                        },
                    )
                }
                YouniversalSettingRow(
                    title = "Slider",
                    subtitle = "%.0f%%".format(sliderValue * 100f),
                )
                YouniversalSlider(value = sliderValue, onValueChange = { sliderValue = it })
            }
        }

        item {
            DemoSection(
                title = "Segmented control",
                description = "One track, one sliding indicator — not a row of outlined buttons.",
            ) {
                YouniversalSegmentedControl(
                    options = listOf("Day", "Week", "Month", "Year"),
                    selected = segmented,
                    onOptionSelected = { segmented = it },
                    modifier = Modifier.fillMaxWidth(),
                )
                YouniversalSegmentedControl(
                    options = listOf("On", "Off"),
                    selected = "On",
                    onOptionSelected = { onMessage("Segmented: $it") },
                    enabled = false,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }

        item {
            DemoSection(
                title = "Dialogs and floating actions",
                description = "Rounded dialogs and floating buttons use the theme's surfaces.",
            ) {
                YouniversalButton(
                    text = "Show dialog",
                    onClick = { dialogOpen = true },
                    modifier = Modifier.fillMaxWidth(),
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(YouniversalMetrics.SpacingMd),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    YouniversalFab(
                        icon = DemoIcons.Check,
                        contentDescription = "Confirm",
                        onClick = { onMessage("FAB tapped") },
                    )
                    YouniversalExtendedFab(
                        text = "Compose",
                        icon = DemoIcons.Overview,
                        onClick = { onMessage("Extended FAB tapped") },
                    )
                }
            }
        }

        item {
            DemoSection(title = "Type in context") {
                Text(
                    text = "Body copy in the active theme",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                Text(
                    text = "Supporting text sits one step down in the scale and uses onSurfaceVariant.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }

    if (dialogOpen) {
        YouniversalAlertDialog(
            title = "Apply this theme?",
            text = "The choice is stored on this device and applied the next time the app starts.",
            confirmText = "Apply",
            onConfirm = {
                dialogOpen = false
                onMessage("Theme applied")
            },
            dismissText = "Cancel",
            onDismissRequest = { dialogOpen = false },
            icon = DemoIcons.Mark,
        )
    }
}
