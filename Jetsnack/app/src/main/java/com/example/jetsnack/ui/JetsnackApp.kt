/*
 * Copyright 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.jetsnack.ui

import androidx.activity.OnBackPressedDispatcher
import androidx.compose.Composable
import androidx.compose.remember
import androidx.ui.animation.Crossfade
import androidx.ui.savedinstancestate.rememberSavedInstanceState
import com.example.jetsnack.ui.home.Home
import com.example.jetsnack.ui.snackdetail.SnackDetail
import com.example.jetsnack.ui.theme.JetsnackTheme
import com.example.jetsnack.ui.utils.Navigator
import com.example.jetsnack.ui.utils.ProvideInsets

@Composable
fun JetsnackApp(backDispatcher: OnBackPressedDispatcher) {
    val navigator: Navigator<Destination> = rememberSavedInstanceState(
        saver = Navigator.saver<Destination>(backDispatcher)
    ) {
        Navigator(Destination.Home, backDispatcher)
    }
    val actions = remember(navigator) { Actions(navigator) }
    ProvideInsets {
        JetsnackTheme {
            Crossfade(navigator.current) { destination ->
                when (destination) {
                    Destination.Home -> Home(actions.selectSnack)
                    is Destination.SnackDetail -> SnackDetail(
                        snackId = destination.snackId,
                        upPress = actions.upPress
                    )
                }
            }
        }
    }
}
