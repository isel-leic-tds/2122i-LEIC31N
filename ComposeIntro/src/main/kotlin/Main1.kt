// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.desktop.DesktopMaterialTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*

fun log(msg: String) = println("$msg: ${Thread.currentThread().name}")

fun main() = singleWindowApplication(
    title = "TDS Demo",
    state = WindowState(position = WindowPosition(Alignment.Center), width = 300.dp, height = 400.dp)
) {
    DesktopMaterialTheme {
        HelloList(listOf("ISEL","TDS","LEIC"))
    }
}

@Composable
fun HelloList(names: List<String>) {
    Column(
        modifier = Modifier.fillMaxSize().padding(20.dp).background(Color.LightGray),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        names.forEach {
            Hello(it)
        }
    }
}

@Composable
fun Hello(name: String) {
    Text(
        "Hello $name",
        color= Color.Magenta,
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.h4,
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .background(Color.Green)
    )
}
