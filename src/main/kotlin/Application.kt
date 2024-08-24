import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

sealed interface AppState {
    data class World(
        val map: WorldMap
    ) : AppState {
        companion object {
            val Default = World(
                map = WorldMap.Default
            )
        }
    }
}

@Composable
fun App(
    appState: AppState,
) {
    MaterialTheme {
        when (appState) {
            is AppState.World -> WorldMap(
                data = appState.map,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}