/*
 * Copyright 2020 The Android Open Source Project
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

package com.example.compose.jetchat.conversation

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredHeight
import androidx.compose.foundation.layout.preferredSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.AmbientContentAlpha
import androidx.compose.material.ContentAlpha
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Brush
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Providers
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.unit.dp
import com.example.compose.jetchat.components.JetchatAppBar
import com.example.compose.jetchat.data.OverrideColor

@Composable
fun ConversationAppBar(
    title: String,
    @DrawableRes contactPhoto: Int,
    onAccentColorSelected: (OverrideColor) -> Unit,
    modifier: Modifier = Modifier
) {
    val showMenu = remember { mutableStateOf(false) }

    JetchatAppBar(
        modifier = modifier,
        iconDrawable = contactPhoto,
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.subtitle1
            )
        },
        actions = {
            Providers(AmbientContentAlpha provides ContentAlpha.medium) {
                // Search icon
                Icon(
                    imageVector = Icons.Outlined.Search,
                    modifier = Modifier
                        .clickable(onClick = {}) // TODO: Show not implemented dialog.
                        .padding(horizontal = 12.dp, vertical = 16.dp)
                        .preferredHeight(24.dp)
                )

                DropdownMenu(
                    toggle = {
                        Icon(
                            imageVector = Icons.Outlined.Brush,
                            modifier = Modifier
                                .clickable { showMenu.value = true }
                                .padding(horizontal = 12.dp, vertical = 16.dp)
                                .preferredHeight(24.dp)
                        )
                    },
                    expanded = showMenu.value,
                    onDismissRequest = { showMenu.value = false }
                ) {
                    OverrideColor.values().forEach { color ->
                        DropdownMenuItem(
                            onClick = {
                                onAccentColorSelected(color)
                                // close the menu
                                showMenu.value = false
                            }
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Spacer(
                                    Modifier
                                        .padding(end = 8.dp)
                                        .preferredSize(16.dp)
                                        .clip(CircleShape)
                                        .paint(ColorPainter(color.color))
                                )
                                Text(text = color.title)
                            }
                        }
                    }
                }
            }
        }
    )
}
