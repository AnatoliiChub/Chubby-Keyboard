package com.chubbykeyboard.ui.view.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chubbykeyboard.R
import com.chubbykeyboard.domain.Settings

@Composable
fun RangedSetting(
    settings: Settings.RangedSettings,
    onChanged: (Float) -> Unit
) {
    with(settings) {
        val updatedValue = remember {
            mutableFloatStateOf(value.toFloat())
        }
        val isEnabled = remember {
            mutableStateOf(value > 0)
        }
        Column(modifier = Modifier.fillMaxSize()) {

            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    modifier = Modifier.padding(vertical = 16.dp),
                    text = title,
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.weight(1f))
                Switch(checked = isEnabled.value, onCheckedChange = {
                    isEnabled.value = it
                    onChanged(if (it) updatedValue.floatValue else 0f)
                })

            }

            Text(
                modifier = Modifier.padding(vertical = 8.dp),
                text = description,
                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = if (isEnabled.value) 1f else 0.5f),
                fontSize = 14.sp
            )
            if (isEnabled.value) {
                Slider(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    value = updatedValue.floatValue,
                    onValueChange = { updatedValue.floatValue = it },
                    valueRange = range.first.toFloat()..range.last.toFloat(),
                            onValueChangeFinished = { onChanged(updatedValue.floatValue) }
                )
                Text(
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .fillMaxWidth(),
                    text = stringResource(id = R.string.settings_debounce_value, updatedValue.floatValue.toInt()),
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}