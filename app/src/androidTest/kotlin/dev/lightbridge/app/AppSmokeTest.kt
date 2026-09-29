// SPDX-License-Identifier: MIT
package dev.lightbridge.app

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import org.junit.Rule
import org.junit.Test

class AppSmokeTest {
    @get:Rule val compose = createAndroidComposeRule<MainActivity>()
    @Test fun homeSendInboxAndSettingsAreReachable() {
        compose.onNodeWithText("A little light.\nA direct connection.").assertIsDisplayed()
        compose.onNodeWithText("Send", useUnmergedTree = true).performClick()
        compose.onNodeWithText("Choose a file").assertIsDisplayed()
        compose.onNodeWithText("Inbox", useUnmergedTree = true).performClick()
        compose.onNodeWithText("Your inbox").assertIsDisplayed()
        compose.onNodeWithText("Settings", useUnmergedTree = true).performClick()
        compose.onNodeWithText("Make it yours.").assertIsDisplayed()
    }
    @Test fun textCreatesRealQrAndFinishClearsSender() {
        compose.onNodeWithText("Send", useUnmergedTree = true).performClick()
        compose.onNodeWithText("Or send a text snippet").performTextInput("Hello over light")
        compose.onNodeWithText("Send text").performScrollTo().performClick()
        compose.waitUntil(15000) { compose.onAllNodesWithText("message.txt").fetchSemanticsNodes().isNotEmpty() }
        compose.onNodeWithText("message.txt").assertExists()
        compose.onNodeWithText("Finish").performScrollTo().performClick()
        compose.onNodeWithText("Choose a file").assertExists()
    }
}
