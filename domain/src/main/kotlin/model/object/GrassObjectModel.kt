package model.`object`

data class GrassObjectModel(
    override val position: Position,
    override val size: Float,
) : ObjectModel.Land {
    companion object {
        val Default = GrassObjectModel(
            position = Position.Default,
            size = 0f
        )
    }
}