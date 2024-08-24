package model.`object`

data class EmptyObjectModel(
    override val position: Position,
    override val size: Float,
) : ObjectModel.Land {
    companion object {
        val Default = EmptyObjectModel(
            position = Position.Default,
            size = 0f
        )
    }
}