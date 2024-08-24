package com.julia.data

import kotlinx.coroutines.flow.Flow
import model.AppModel

interface AppRepository {

    val appModel: Flow<AppModel>

    suspend fun updateApp(app: AppModel)

    suspend fun restartApp()

    companion object {
        fun create(): AppRepository = AppRepositoryImpl()
    }
}