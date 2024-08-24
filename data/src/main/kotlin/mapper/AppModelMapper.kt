package com.julia.data.mapper

import model.`object`.*

fun List<List<String>>.toWorldModel(
    layer: Int,
    modelSize: Float,
) = mapIndexed { rIndex, row ->
    row.mapIndexedNotNull { cIndex, col ->
        val position = Position(
            x = modelSize * cIndex,
            y = modelSize * rIndex,
            z = layer.toFloat()
        )
        when (col) {
            "G" -> GrassObjectModel.Default.copy(
                position = position,
                size = modelSize,
            )
            "W" -> WaterObjectModel.Default.copy(
                position = position,
                size = modelSize,
            )
            "A" -> AppleObjectModel.Default.copy(
                position = position,
                size = modelSize,
            )
            "M" -> MaleObjectModel.Default.copy(
                position = position,
                size = modelSize,
                visibilityRadius = modelSize * 2,
            )
            else -> null
        }
    }
}.flatten()
