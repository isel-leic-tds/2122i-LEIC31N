import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import kotlinx.coroutines.delay
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

val startTime = System.currentTimeMillis()

@OptIn(ExperimentalTime::class)
fun log(txt: String) {
    val time = Duration.milliseconds(System.currentTimeMillis()-startTime)
    val thread = Thread.currentThread().name
    println("$time $thread $txt")
}

@Composable
@Preview
fun App() {
    log("App composed")
    var counter by remember { mutableStateOf(0) }
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Counter example")
        Content(counter, { counter++ })
    }
}

//data class Counter(var current: MutableState<Int> = mutableStateOf(0))

@Composable
private fun Content(value: Int, inc: ()->Unit) {
    log("Content composed")
    Row(
        modifier = Modifier.padding(10.dp).fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text("Counter= ${value}")
        Button(onClick = inc) {
            Text("Increment")
        }
        if (value<10)
            AutoButton("Start","Stop",inc)
    }
}

@Composable
fun AutoButton(on: String, off: String, progress: ()->Unit = {} ) {
    log("Auto Button composed")
    //val scope = rememberCoroutineScope()
    var started by remember{ mutableStateOf(false) }
    LaunchedEffect(true) {
        while (true) {
            delay(1000)
            if (started) progress()
        }
    }
    Button(onClick = {
        started = !started
 /*       if (started) scope.launch {
            while(started) {
                delay(1000)
                progress()
            }
        }*/
    } ) {
        Text( if (started) off else on )
    }
}

fun main() {
    log("start app")
    application(exitProcessOnExit = false) {
        MaterialTheme {
            val state = WindowState(width = 350.dp, height = Dp.Unspecified)
            Window(onCloseRequest = ::exitApplication, state = state) {
                App()
            }
        }
    }
    log("end app")
}
