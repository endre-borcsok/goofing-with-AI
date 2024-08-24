package com.julia.data

import com.julia.data.mapper.toWorldModel
import kotlinx.coroutines.flow.MutableStateFlow
import model.AppModel
import model.WorldModel

internal class AppRepositoryImpl : AppRepository {

    private val _appModel = MutableStateFlow<AppModel>(buildWorld())

    override val appModel = _appModel

    override suspend fun updateApp(app: AppModel) {
        _appModel.value = app
    }

    override suspend fun restartApp() {
        _appModel.value = buildWorld()
    }

    private fun buildWorld() =
        WorldModel(
            width = 500f,
            height = 500f,
            objects = buildList {
                addAll(
                    ground.toWorldModel(
                        layer = 1,
                        modelSize = 500f / ground.size
                    )
                )
                addAll(
                    terrain
                        .addRandom("M")
                        .toWorldModel(
                            layer = 2,
                            modelSize = 500f / ground.size
                        )
                )
            }
        )
}

private val ground = listOf(
    listOf("G","G","G","G","G","G","G","G","G","G"),
    listOf("G","G","G","G","G","G","G","G","G","G"),
    listOf("G","G","G","G","G","G","G","G","G","G"),
    listOf("G","G","G","G","G","G","G","G","G","G"),
    listOf("G","G","G","G","G","G","G","G","G","G"),
    listOf("G","G","G","G","G","G","G","G","G","G"),
    listOf("G","G","G","G","G","G","G","G","G","G"),
    listOf("G","G","G","G","G","G","G","G","G","G"),
    listOf("G","G","G","G","G","G","G","G","G","G"),
    listOf("G","G","G","G","G","G","G","G","G","G"),
)


private val terrain = listOf(
    listOf("E","E","E","E","E","W","E","E","E","E",),
    listOf("E","E","E","E","E","W","E","E","E","E",),
    listOf("E","E","E","E","W","W","E","E","A","E",),
    listOf("E","E","E","E","W","E","E","E","E","E",),
    listOf("E","E","E","W","W","E","E","E","E","E",),
    listOf("E","E","E","W","W","E","E","E","E","E",),
    listOf("E","E","E","E","W","W","E","E","E","E",),
    listOf("E","E","E","E","E","W","E","E","E","E",),
    listOf("E","E","E","E","W","W","E","E","E","E",),
    listOf("E","E","E","E","E","E","E","E","E","E",),
)

private fun List<List<String>>.addRandom(item: String) =
    toMutableList().let { mutable ->
        val threshold = (0..size * this[0].size).random()
        mutable.forEachIndexed { rIndex, row ->
            row.forEachIndexed { cIndex, string ->
                if (string == "E" && threshold <= rIndex * cIndex) {
                    mutable[rIndex] = row.toMutableList().apply { set(cIndex, item) }
                    return@let mutable.toList()
                }
            }
        }
        mutable
    }