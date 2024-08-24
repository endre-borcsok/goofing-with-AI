package mapper

import AppState
import Tile
import WorldMap
import WorldObject
import androidx.compose.ui.unit.dp
import model.AppModel
import model.WorldModel
import model.`object`.*

fun AppModel.toAppState() =
    when (this) {
        is WorldModel -> toAppWorld()
    }

fun WorldModel.toAppWorld() =
    AppState.World(
        map = WorldMap(
            width = width,
            height = height,
            tileSize = 50.dp,
            objects = objects.map { obj ->
                WorldObject(
                    x = obj.position.x,
                    y = obj.position.y,
                    z = obj.position.z,
                    tile = when (obj) {
                        is GrassObjectModel -> Tile.Grass
                        is WaterObjectModel -> Tile.Water
                        is AppleObjectModel -> Tile.Apple
                        is MaleObjectModel -> Tile.Male
                        is EmptyObjectModel -> Tile.Empty
                    }
                )
            },
        ),
    )