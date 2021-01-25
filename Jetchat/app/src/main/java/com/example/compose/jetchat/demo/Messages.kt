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

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredWidthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.jetchat.conversation.Message
import com.example.compose.jetchat.data.demoMessages
import com.example.compose.jetchat.theme.JetchatTheme

@Preview
@Composable
fun PreviewMessages() {
    JetchatTheme(isDarkTheme = false) {
        Messages(demoMessages)
    }
}

@Preview
@Composable
fun PreviewMessages_DarkTheme() {
    JetchatTheme(isDarkTheme = true) {
        Messages(demoMessages)
    }
}

@Composable
fun Messages(
    messages: List<Message>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        reverseLayout = true,
        modifier = modifier
    ) {
        items(messages) { message ->
            Box(Modifier.fillParentMaxWidth()) {
                Message(
                    message = message,
                    isFromMe = message.isMe,
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 4.dp)
                        .preferredWidthIn(max = 280.dp)
                        .align(if (message.isMe) Alignment.CenterEnd else Alignment.CenterStart)
                )
            }
        }
    }
}
