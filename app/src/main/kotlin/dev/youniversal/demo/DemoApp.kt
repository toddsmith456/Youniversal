// SPDX-License-Identifier: MIT
@file:OptIn(ExperimentalMaterial3Api::class)

package dev.youniversal.demo

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.vector.ImageVector
import dev.youniversal.demo.ui.AboutScreen
import dev.youniversal.demo.ui.ComponentsScreen
import dev.youniversal.demo.ui.OverviewScreen
import dev.youniversal.demo.ui.SurfacesScreen
import dev.youniversal.demo.ui.TypeColorScreen
import dev.youniversal.theme.YouniversalBottomBar
import dev.youniversal.theme.YouniversalNavigationItem
import dev.youniversal.theme.YouniversalScaffold
import dev.youniversal.theme.YouniversalSnackbarHost
import dev.youniversal.theme.YouniversalTheme
import dev.youniversal.theme.YouniversalThemeState
import dev.youniversal.theme.YouniversalTopBar
import kotlinx.coroutines.launch

/** The five destinations of the showcase. */
internal enum class DemoDestination(val title: String, val icon: ImageVector) {
    Overview("Overview", DemoIcons.Overview),
    Components("Components", DemoIcons.Components),
    Surfaces("Surfaces", DemoIcons.Layers),
    TypeColor("Type & Color", DemoIcons.Palette),
    About("About", DemoIcons.About),
}

/**
 * Root of the showcase: scaffold, navigation and the dialogs/snackbars the screens trigger.
 *
 * All state lives here so switching tabs never loses a control's value, and so the theme panel on
 * the Overview screen drives the same state object the activity themed itself with.
 */
@Composable
internal fun DemoApp(themeState: YouniversalThemeState) {
    var selectedTab by rememberSaveable { mutableIntStateOf(0) }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    fun navigate(index: Int) {
        selectedTab = index
    }

    fun showMessage(message: String) {
        scope.launch { snackbarHostState.showSnackbar(message) }
    }

    YouniversalScaffold(
        topBar = {
            YouniversalTopBar(
                title = DemoDestination.entries[selectedTab].title,
                actions = {
                    if (!YouniversalTheme.enabled) {
                        // The theme is switched off: say so, and offer the way back.
                        Text(
                            text = "Baseline Material 3",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(end = 16.dp),
                        )
                    }
                },
            )
        },
        bottomBar = {
            YouniversalBottomBar {
                DemoDestination.entries.forEachIndexed { index, destination ->
                    YouniversalNavigationItem(
                        selected = index == selectedTab,
                        onClick = { navigate(index) },
                        icon = destination.icon,
                        label = destination.title,
                    )
                }
            }
        },
        snackbarHost = { YouniversalSnackbarHost(hostState = snackbarHostState) },
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            when (selectedTab) {
                0 -> OverviewScreen(themeState = themeState, onNavigate = ::navigate)
                1 -> ComponentsScreen(onMessage = ::showMessage)
                2 -> SurfacesScreen(onMessage = ::showMessage)
                3 -> TypeColorScreen()
                else -> AboutScreen(themeState = themeState)
            }
        }
    }
}
