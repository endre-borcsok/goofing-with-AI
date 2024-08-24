package model.`object`

data class MaleObjectModel(
    override val position: Position,
    override val size: Float,
    override val visibilityRadius: Float
) : ObjectModel.Human {
    companion object {
        val Default = MaleObjectModel(
            position = Position.Default,
            size = 0f,
            visibilityRadius = 0f,
        )
    }
}