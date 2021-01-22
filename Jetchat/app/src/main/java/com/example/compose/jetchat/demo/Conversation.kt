/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.compose.jetchat.demo

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.viewModel
import com.example.compose.jetchat.conversation.ConversationAppBar
import com.example.compose.jetchat.conversation.ConversationUiState
import com.example.compose.jetchat.conversation.ConversationViewModel
import com.example.compose.jetchat.conversation.UserInput
import com.example.compose.jetchat.data.OverrideColor
import com.example.compose.jetchat.data.exampleUiState
import com.example.compose.jetchat.theme.JetchatTheme
import dev.chrisbanes.accompanist.insets.navigationBarsWithImePadding
import dev.chrisbanes.accompanist.insets.statusBarsPadding

@Composable
fun Conversation() {
    var overrideAccent by remember { mutableStateOf(OverrideColor.NONE) }

    JetchatTheme(overrideAccent = overrideAccent) {
        val viewModel: ConversationViewModel = viewModel()

        val uiState by viewModel.uiState.collectAsState()

        ConversationContent(
            uiState = uiState,
            onMessageSent = { message ->
                viewModel.sendMessage(message)
            },
            onOverrideColorChanged = { color ->
                overrideAccent = color
            }
        )
    }
}

@Composable
private fun ConversationContent(
    uiState: ConversationUiState,
    onOverrideColorChanged: (OverrideColor) -> Unit,
    onMessageSent: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(modifier = modifier) {
        Column {
            ConversationAppBar(
                title = uiState.contactName,
                contactPhoto = uiState.contactPhoto ?: 0,
                onAccentColorSelected = onOverrideColorChanged,
                // Use statusBarsPadding() to move the app bar content below the status bar
                modifier = Modifier.statusBarsPadding(),
            )

            Messages(
                messages = uiState.messages,
                modifier = Modifier.weight(1f),
            )

            UserInput(
                onMessageSent = onMessageSent,
                // Use navigationBarsWithImePadding(), to move the input panel above both the
                // navigation bar, and on-screen keyboard (IME)
                modifier = Modifier.navigationBarsWithImePadding(),
            )
        }
    }
}

@Preview
@Composable
fun ConversationPreview() {
    JetchatTheme {
        ConversationContent(
            uiState = exampleUiState,
            onMessageSent = {},
            onOverrideColorChanged = {},
        )
    }
}
