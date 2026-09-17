// SPDX-License-Identifier: MIT
package dev.youniversal.demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dev.youniversal.theme.YouniversalTheme
import dev.youniversal.theme.rememberYouniversalThemeState

/**
 * The Youniversal showcase.
 *
 * The activity does almost nothing: it goes edge to edge, creates the persisted theme state, and
 * hands the tree to [YouniversalTheme]. Everything else — colors, type, shape, system bars — comes
 * from the theme.
 */
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val themeState = rememberYouniversalThemeState()

            // Full-app usage: one call themes the whole activity.
            YouniversalTheme(state = themeState) {
                DemoApp(themeState = themeState)
            }
        }
    }
}
