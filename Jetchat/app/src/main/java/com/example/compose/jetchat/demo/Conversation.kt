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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.compose.jetchat.R
import com.example.compose.jetchat.conversation.ConversationAppBar
import com.example.compose.jetchat.conversation.ConversationUiState
import com.example.compose.jetchat.conversation.Message
import com.example.compose.jetchat.conversation.UserInput
import com.example.compose.jetchat.data.OverrideColor
import com.example.compose.jetchat.data.exampleUiState
import com.example.compose.jetchat.theme.JetchatTheme
import dev.chrisbanes.accompanist.insets.navigationBarsWithImePadding
import dev.chrisbanes.accompanist.insets.statusBarsPadding

/**
 * Entry point for a conversation screen.
 */
@Composable
fun ConversationContent(
    uiState: ConversationUiState,
    modifier: Modifier = Modifier,
    onMessageSend: ((Message) -> Unit)? = null,
) {
    var overrideAccent by remember { mutableStateOf(OverrideColor.NONE) }

    JetchatTheme(overrideAccent = overrideAccent) {
        val authorMe = stringResource(R.string.author_me)
        val timeNow = stringResource(R.string.now)

        Surface(modifier = modifier) {
            Column(Modifier.fillMaxSize()) {
                // Channel name bar floats above the messages
                ConversationAppBar(
                    title = uiState.contactName,
                    contactPhoto = uiState.contactPhoto ?: 0,
                    // Use statusBarsPadding() to move the app bar content below the status bar
                    onAccentColorSelected = {
                        overrideAccent = it
                    },
                    modifier = Modifier.statusBarsPadding(),
                )

                Messages(
                    messages = uiState.messages,
                    modifier = Modifier.weight(1f),
                )

                UserInput(
                    onMessageSent = { content ->
                        onMessageSend?.invoke(Message(authorMe, isMe = true, content, timeNow))
                    },
                    // Use navigationBarsWithImePadding(), to move the input panel above both the
                    // navigation bar, and on-screen keyboard (IME)
                    modifier = Modifier.navigationBarsWithImePadding(),
                )
            }
        }
    }
}

@Preview
@Composable
fun ConversationPreview() {
    JetchatTheme {
        ConversationContent(uiState = exampleUiState)
    }
}

@Preview
@Composable
fun channelBarPrev() {
    JetchatTheme {
        ConversationAppBar(
            title = "Ali Connors",
            contactPhoto = R.drawable.ali,
            onAccentColorSelected = {},
        )
    }
}
