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

package com.example.compose.jetchat.conversation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.compose.jetchat.data.exampleUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ConversationViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(exampleUiState)
    val uiState = _uiState.asStateFlow()

    fun sendMessage(message: Message) {
        addMessage(message)

        viewModelScope.launch {
            delay(2000)
            addMessage(REPLY)
        }
    }

    private fun addMessage(message: Message) {
        val newMessages = ArrayList(_uiState.value.messages)
        newMessages.add(0, message)
        _uiState.tryEmit(_uiState.value.copy(messages = newMessages))
    }

    companion object {
        private val REPLY = Message(
            author = "Ali Connors",
            isMe = false,
            content = "Nice!",
            timestamp = "Just now"
        )
    }
}
