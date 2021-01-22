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

@file:Suppress("UNUSED_PARAMETER")

package com.example.compose.jetchat.conversation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredWidthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
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
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Message(
    msg: Message,
    isUserMe: Boolean,
    modifier: Modifier = Modifier,
) {
    Box(modifier) {
        Surface(
            color = when {
                isUserMe -> MaterialTheme.colors.surface
                else -> MaterialTheme.colors.secondary
            },
            shape = MaterialTheme.shapes.medium,
            elevation = 1.dp,
            modifier = Modifier
                .align(if (isUserMe) Alignment.CenterStart else Alignment.CenterEnd)
                .preferredWidthIn(max = 280.dp)
        ) {
            var showActions by remember { mutableStateOf(false) }
            Column(
                modifier = Modifier.clickable {
                    showActions = !showActions
                }
            ) {
                Text(
                    text = msg.content,
                    modifier = Modifier.padding(8.dp)
                )
                AnimatedVisibility(visible = showActions) {
                    MessageActions(isUserMe)
                }
            }
        }
    }
}

@Composable
fun Messages(
    messages: List<Message>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        reverseLayout = true,
        modifier = modifier.fillMaxWidth()
    ) {
        items(messages) { message ->
            Message(
                msg = message,
                isUserMe = message.isMe,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp)
            )
        }
    }
}

@Composable
fun MessageActions(isUserMe: Boolean) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (isUserMe) Arrangement.Start else Arrangement.End
    ) {
        IconButton(onClick = { /*TODO*/ }) {
            Icon(Icons.Outlined.ContentCopy)
        }
        IconButton(onClick = { /*TODO*/ }) {
            Icon(Icons.Outlined.Share)
        }
        IconButton(onClick = { /*TODO*/ }) {
            Icon(Icons.Outlined.InsertEmoticon)
        }
    }
}
