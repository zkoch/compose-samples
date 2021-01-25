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

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredWidthIn
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ContentCopy
import androidx.compose.material.icons.outlined.InsertEmoticon
import androidx.compose.material.icons.outlined.Share
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.jetchat.conversation.Message
import com.example.compose.jetchat.data.demoMessages
import com.example.compose.jetchat.theme.JetchatTheme

@Preview
@Composable
fun PreviewMessage_Me() {
    JetchatTheme {
        Message(msg = demoMessages[0], isUserMe = true)
    }
}

@Preview
@Composable
fun PreviewMessage_Other() {
    JetchatTheme {
        Message(msg = demoMessages[1], isUserMe = false)
    }
}

@Composable
fun Message(
    msg: Message,
    isUserMe: Boolean,
    modifier: Modifier = Modifier,
) {
    Box(modifier) {
        Card(
            backgroundColor = when {
                isUserMe -> MaterialTheme.colors.surface
                else -> MaterialTheme.colors.secondary
            },
            modifier = Modifier
                .align(if (isUserMe) Alignment.CenterStart else Alignment.CenterEnd)
                .preferredWidthIn(max = 280.dp)
        ) {
            var showActions by remember { mutableStateOf(false) }
            Column(
                horizontalAlignment = if (isUserMe) Alignment.Start else Alignment.End,
                modifier = Modifier
                    .clickable { showActions = !showActions }
                    .padding(12.dp)
            ) {
                Text(text = msg.content)

                AnimatedVisibility(visible = showActions) {
                    MessageActions()
                }
            }
        }
    }
}

@Composable
fun MessageActions() {
    Row {
        IconButton(onClick = { /*TODO*/ }) {
            Icon(Icons.Outlined.ContentCopy, contentDescription = "Copy message")
        }
        IconButton(onClick = { /*TODO*/ }) {
            Icon(Icons.Outlined.Share, contentDescription = "Share")
        }
        IconButton(onClick = { /*TODO*/ }) {
            Icon(Icons.Outlined.InsertEmoticon, contentDescription = "Insert emoji")
        }
    }
}
