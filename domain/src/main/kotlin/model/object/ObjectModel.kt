package model.`object`

sealed interface ObjectModel {
    val position: Position
    val size: Float

    sealed interface Land : ObjectModel
    sealed interface Plant : ObjectModel

    sealed interface Human : ObjectModel {
        val visibilityRadius: Float
    }
}