package com

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.chubbykeyboard.ui.theme.ChubbyKeyboardTheme

//TODO ACTIVITY FOR TESTING, TO BE REMOVED
@SuppressLint("RestrictedApi")
class TestActivity : ComponentActivity() {

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            ChubbyKeyboardTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                ) { innerPadding ->
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
                            ))
                        }
                    }
                }
            }
        }
    }
}