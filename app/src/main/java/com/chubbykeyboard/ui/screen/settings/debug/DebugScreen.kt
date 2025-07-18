package com.chubbykeyboard.ui.screen.settings.debug

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun DebugScreen(innerPadding: PaddingValues,) {
    Column(modifier = Modifier.padding(innerPadding)) {
        val list = listOf(
            Pair("Text", KeyboardType.Text),
            Pair("Ascii", KeyboardType.Ascii),
            Pair("Number", KeyboardType.Number),
            Pair("Phone", KeyboardType.Phone),
            Pair("Uri", KeyboardType.Uri),
            Pair("Email", KeyboardType.Email),
            Pair("Password", KeyboardType.Password),
            Pair("NumberPassword", KeyboardType.NumberPassword),
            Pair("Decimal", KeyboardType.Decimal),
        )
        list.forEach { (label, type) ->
            val text = remember {
                mutableStateOf("")
            }

            TextField(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                value = text.value,
                label = { Text(label) },
                onValueChange = { text.value = it },
                keyboardOptions = KeyboardOptions(keyboardType = type, imeAction = ImeAction.Previous),
                keyboardActions = KeyboardActions(
                    onDone = {},
                    onGo = {} ,
                    onNext = {},
                    onPrevious = {},
                    onSearch = {},
                )
            )
        }
    }
}
