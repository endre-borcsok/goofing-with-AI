import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.io.encoding.Base64

data class WorldMap(
    val width: Float,
    val height: Float,
    val tileSize: Dp,
    val objects: List<WorldObject>
) {
    companion object {
        val Default = WorldMap(
            width = 0f,
            height = 0f,
            objects = emptyList(),
            tileSize = Dp.Unspecified
        )
    }
}

data class WorldObject(
    val x: Float,
    val y: Float,
    val z: Float,
    val tile: Tile,
) {
    companion object {
        val Default = WorldObject(
            x = 0f,
            y = 0f,
            z = 0f,
            tile = Tile.Empty
        )
    }
}

@Composable
fun WorldMap(
    data: WorldMap,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .width(data.width.dp)
            .height(data.height.dp),
    ) {
        data.objects
            .map { it.z }
            .toSortedSet()
            .forEach { layer ->
                data.objects
                    .filter { it.z == layer }
                    .forEach { obj ->
                        obj.tile.render(
                            modifier = Modifier
                                .size(data.tileSize)
                                .offset(
                                    x = obj.x.dp,
                                    y = obj.y.dp
                                )
                    )
                }
        }
    }
}