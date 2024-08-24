package model

import model.`object`.ObjectModel

data class WorldModel(
    val width: Float,
    val height: Float,
    val objects: List<ObjectModel>
): AppModel {
    companion object {
        val Default = WorldModel(
            width = 0f,
            height = 0f,
            objects = emptyList()
        )
    }
}