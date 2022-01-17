import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {
    log("start")
    runBlocking {
        repeat(3) {
            launch {
                log("before delay")
                print(">")
                delay(2000)
                log("after delay")
                print(".")
            }
        }
        log("after repeat")
    }
    log("end")
}