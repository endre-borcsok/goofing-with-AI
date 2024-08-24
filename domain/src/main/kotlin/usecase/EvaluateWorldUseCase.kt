package usecase

import model.WorldModel
import model.`object`.EmptyObjectModel
import model.`object`.ObjectModel
import model.`object`.WaterObjectModel

fun interface EvaluateWorldUseCase {
    operator fun invoke(world: WorldModel): List<ObjectModel>

    companion object {
        fun create(): EvaluateWorldUseCase = EvaluateWorldUseCaseImpl()
    }
}

private class EvaluateWorldUseCaseImpl : EvaluateWorldUseCase {
    override fun invoke(world: WorldModel) =
        world.objects.filterNot { obj -> obj.dead(world) }

    private fun ObjectModel.dead(world: WorldModel) =
        listOf(
            leftMap(world),
            sunk(world)
        ).any { it }

    private fun ObjectModel.leftMap(world: WorldModel) =
        position.x < 0
                || position.x >= world.height
                || position.y < 0
                || position.y >= world.width

    private fun ObjectModel.sunk(world: WorldModel) =
        this !is WaterObjectModel && world.objects.filterIsInstance<WaterObjectModel>()
            .filter { it.position.x == position.x }
            .filter { it.position.y == position.y }
            .any { it.position.z == position.z }
}