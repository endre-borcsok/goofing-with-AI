package usecase

import model.WorldModel

fun interface UpdateWorldUseCase {
    operator fun invoke(world: WorldModel): WorldModel

    companion object {
        fun create(): UpdateWorldUseCase = RunningUpdateWorldUseCase(
            updateHumanObjectsUseCase = UpdateHumanObjectsUseCase.create(),
            evaluateWorldUseCase = EvaluateWorldUseCase.create(),
        )
    }
}

private class RunningUpdateWorldUseCase(
    private val updateHumanObjectsUseCase: UpdateHumanObjectsUseCase,
    private val evaluateWorldUseCase: EvaluateWorldUseCase,
): UpdateWorldUseCase {
    override fun invoke(world: WorldModel) =
        world.copy(
            objects = evaluateWorldUseCase(
                world = world.copy(
                    objects = updateHumanObjectsUseCase(world.objects)
                )
            )
        )
}