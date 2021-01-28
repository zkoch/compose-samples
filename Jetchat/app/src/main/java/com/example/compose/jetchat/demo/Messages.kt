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

import android.icu.text.RelativeDateTimeFormatter
import android.icu.text.RelativeDateTimeFormatter.RelativeDateTimeUnit
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredSize
import androidx.compose.foundation.layout.preferredWidthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.AmbientContentAlpha
import androidx.compose.material.ContentAlpha
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Providers
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.compose.jetchat.conversation.Message
import com.example.compose.jetchat.conversation.daysSinceEpoch
import com.example.compose.jetchat.data.demoMessages
import com.example.compose.jetchat.theme.JetchatTheme
import java.time.Instant
import kotlinx.coroutines.launch

@Preview
@Composable
fun PreviewMessages_LightTheme() {
    JetchatTheme(isDarkTheme = false) {
        Surface {
            Messages(demoMessages())
        }
    }
}

@Preview
@Composable
fun PreviewMessages_DarkTheme() {
    JetchatTheme(isDarkTheme = true) {
        Surface {
            Messages(demoMessages())
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Messages(
    messages: List<Message>,
    modifier: Modifier = Modifier
) {
    Box {
        val listState = rememberLazyListState()

        LazyColumn(
            state = listState,
            verticalArrangement = Arrangement.spacedBy(4.dp),
            contentPadding = PaddingValues(16.dp),
            reverseLayout = true,
            modifier = modifier
        ) {

//        items(messages) { message ->
//            MessageItem(
//                message = message,
//                modifier = Modifier.fillParentMaxWidth()
//            )
//        }

            val messagesPerDay = messages.groupBy { it.dateTime.daysSinceEpoch() }

            messagesPerDay.forEach { (daysSinceEpoch, messagesForDay) ->
                items(messagesForDay) { message ->
                    MessageItem(
                        message = message,
                        modifier = Modifier.fillParentMaxWidth()
                    )
                }

                stickyHeader {
                    DaySeparator(daysSinceEpoch, Modifier.fillParentMaxWidth())
                }
            }
        }

        val showButton = listState.firstVisibleItemIndex > 0

        val scope = rememberCoroutineScope()

        AnimatedVisibility(
            visible = showButton,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            ScrollToBottomButton(
                onClick = {
                    scope.launch {
                        listState.snapToItemIndex(0)
                    }
                },
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

/**
 * Small wrapper around [Message] which floats it left/right based on the sender,
 * and adds some spacing.
 */
@Composable
fun MessageItem(
    message: Message,
    modifier: Modifier = Modifier,
) {
    Box(modifier) {
        Message(
            message = message,
            isFromMe = message.isMe,
            modifier = Modifier
                .preferredWidthIn(max = 280.dp)
                .align(if (message.isMe) Alignment.CenterEnd else Alignment.CenterStart)
        )
    }
}

@Composable
fun DaySeparator(
    daysSinceEpoch: Long,
    modifier: Modifier = Modifier,
) {
    Surface(
        border = BorderStroke(
            width = Dp.Hairline,
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.1f)
        ),
        shape = MaterialTheme.shapes.small,
        modifier = modifier
            .wrapContentWidth()
            .padding(vertical = 4.dp)
    ) {
        Providers(AmbientContentAlpha provides ContentAlpha.medium) {
            val nowSinceEpoch = Instant.now().daysSinceEpoch()
            val formatter = RelativeDateTimeFormatter.getInstance()

            Text(
                text = formatter.format(
                    (daysSinceEpoch - nowSinceEpoch).toDouble(),
                    RelativeDateTimeUnit.DAY
                ).toUpperCase(),
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            )
        }
    }
}

@Composable
fun ScrollToBottomButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    FloatingActionButton(
        onClick = onClick,
        backgroundColor = MaterialTheme.colors.surface,
        modifier = modifier.preferredSize(40.dp),
    ) {
        Icon(
            imageVector = Icons.Default.ArrowDownward,
            contentDescription = "Scroll to bottom"
        )
    }
}
