package model.`object`

data class Position(
    val x: Float,
    val y: Float,
    val z: Float,
) {
    companion object {
        val Default = Position(
            x = 0f,
            y = 0f,
            z = 0f,
        )
    }
}