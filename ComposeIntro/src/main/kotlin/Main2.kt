// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.desktop.DesktopMaterialTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*

fun main() = singleWindowApplication(
    title = "TDS Demo",
    state = WindowState(position = WindowPosition(Alignment.Center), width = 300.dp, height = 400.dp)
) {
    DesktopMaterialTheme {
        EditText("Init") { txt -> println("Final=$txt")  }
    }
}

@Composable
fun EditText(text: String, onOk: (String)->Unit) {
    var edit by remember {  mutableStateOf(text) }
    Column {
        TextField(
            edit,
            onValueChange = {
                edit = it
            }
        )
        Button(onClick = { onOk(edit) }) { Text("OK") }
    }
}

