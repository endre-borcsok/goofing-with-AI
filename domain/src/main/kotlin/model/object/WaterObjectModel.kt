package model.`object`

data class WaterObjectModel(
    override val position: Position,
    override val size: Float,
) : ObjectModel.Land {
    companion object {
        val Default = WaterObjectModel(
            position = Position.Default,
            size = 0f,
        )
    }
}