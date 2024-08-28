import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.julia.data.AppRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import mapper.toAppState
import model.AppModel
import model.WorldModel
import model.`object`.ObjectModel
import usecase.UpdateWorldUseCase
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.lang.Thread.sleep
import java.net.Socket

const val serverLaunchCommand = "./ai_launch.sh"

val ioScope = CoroutineScope(Dispatchers.IO)
val appRepository = AppRepository.create()
val updateWorldUseCase = UpdateWorldUseCase.create()

val appModel = appRepository.appModel.stateIn(
    scope = ioScope,
    started = SharingStarted.Eagerly,
    initialValue = WorldModel.Default
)

fun main() = application {
    ioScope.startServer()
    ioScope.launch { loopApp() }
    Window(::exitApplication) {
        App(appModel.collectAsState().value.toAppState())
    }
}

private suspend fun loopApp() {
    while (true) {
        delay(1000)
        update(appModel.value).let { app ->
            if (app.over()) {
                appRepository.restartApp()
            } else {
                appRepository.updateApp(app)
            }
        }
    }
}

private fun update(appModel: AppModel) =
    when (appModel) {
        is WorldModel -> updateWorldUseCase(appModel)
    }

private fun WorldModel.over() =
    objects.none { it is ObjectModel.Human }

private fun CoroutineScope.startServer() {
    val process = Runtime.getRuntime().exec(serverLaunchCommand)
    println(process.info())
    while (true) {
        try {
            Socket("127.0.0.1", 9999)
            break
        } catch (e: Exception) {
            println(e)
            sleep(1000)
        }
    }
    launch {
        val reader = BufferedReader(InputStreamReader(process.inputStream))
        for (line in reader.lines()) {
            println(line)
        }
    }
}