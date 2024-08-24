package model.`object`

data class AppleObjectModel(
    override val position: Position,
    override val size: Float,
) : ObjectModel.Plant {
    companion object {
        val Default = AppleObjectModel(
            position = Position.Default,
            size = 0f
        )
    }
}