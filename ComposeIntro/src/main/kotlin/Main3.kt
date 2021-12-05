import androidx.compose.desktop.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.*

@Composable
fun NumberEdit(modifier: Modifier, init: Int =0, range: IntRange, onNumberChange: (Int)->Unit ) {
    Row(modifier = modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
        Button({ onNumberChange(init-1) }, enabled = init>range.first){ Text("-") }
        Text("$init", style = MaterialTheme.typography.h5, modifier = Modifier.padding(2.dp))
        Button({ onNumberChange(init+1) }, enabled = init<range.last){ Text("+") }
    }
}

fun main() = singleWindowApplication(
    title = "TDS Main3",
    state = WindowState(position = WindowPosition(Alignment.Center), width = Dp.Unspecified, height = Dp.Unspecified),
) {
    DesktopMaterialTheme {
        Column(Modifier/*.fillMaxWidth()*/.padding(5.dp), verticalArrangement = Arrangement.Center) {
            var value by remember { mutableStateOf(5) }
            Text("value = $value")
            Spacer(modifier = Modifier.height(10.dp))
            NumberEdit(Modifier.background(Color.LightGray), init = value, range = 1..10, { value = it })
        }
    }
}