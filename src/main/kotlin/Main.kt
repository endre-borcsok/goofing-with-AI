import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.julia.data.AppRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import mapper.toAppState
import model.AppModel
import model.WorldModel
import model.`object`.ObjectModel
import usecase.UpdateWorldUseCase
import java.io.BufferedReader
import java.io.InputStreamReader

const val serverLaunchCommand = "./ai_launch.sh"

val ioScope = CoroutineScope(Dispatchers.IO)
val appRepository = AppRepository.create()
val updateWorldUseCase = UpdateWorldUseCase.create()

fun main() = application {
    ioScope.startServer()
    Window(::exitApplication) {
        val appModel = appRepository.appModel.collectAsState(WorldModel.Default)
        LaunchedEffect(Unit) { loopApp(appModel.value) }
        App(appModel.value.toAppState())
    }
}

private suspend fun loopApp(appModel: AppModel) {
    while (true) {
        delay(500)
        update(appModel).let { app ->
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
    val reader = BufferedReader(InputStreamReader(process.inputStream))
    launch {
        for (line in reader.lines()) {
            println(line)
        }
    }
}