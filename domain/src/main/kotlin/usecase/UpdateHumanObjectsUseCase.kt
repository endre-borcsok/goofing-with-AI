package usecase

import com.julia.AI
import com.julia.Action
import com.julia.Input
import model.`object`.*
import kotlin.math.abs

fun interface UpdateHumanObjectsUseCase {
    operator fun invoke(objects: List<ObjectModel>): List<ObjectModel>

    companion object {
        fun create(): UpdateHumanObjectsUseCase = AiUpdateHumanObjectsUseCase(
            ai = AI.create(),
        )
    }
}

private class AiUpdateHumanObjectsUseCase(
    private val ai: AI,
) : UpdateHumanObjectsUseCase{
    override fun invoke(objects: List<ObjectModel>): List<ObjectModel> =
        objects.filterIsInstance<ObjectModel.Human>().map { human ->
            objects
            .filter { model -> model.isVisible(human) }
            .map(::mapToInput)
            .flatten()
            .let(ai::makeDecision)
            .react(human)
        }.let { updateHumanObjects ->
            objects.filterNot { it is ObjectModel.Human }
                .plus(updateHumanObjects)
        }

    private fun ObjectModel.isVisible(human: ObjectModel.Human) =
        listOf(
            abs(position.x - human.position.x) <= human.visibilityRadius,
            abs(position.y - human.position.y) <= human.visibilityRadius,
            abs(position.z - human.position.z) <= human.visibilityRadius
        ).all { it }

    private fun mapToInput(model: ObjectModel) =
        listOf(
            model.position.x,
            model.position.y,
            model.position.z,
            model.size,
            model::class.qualifiedName?.toType() ?: 0f
        )

    private fun Action.react(human: ObjectModel.Human) =
        when (this) {
            Action.NO_ACTION -> human
            Action.MOVE_UP -> human.move(human.position.copy(y = human.position.y - human.size))
            Action.MOVE_DOWN -> human.move(human.position.copy(y = human.position.y + human.size))
            Action.MOVE_LEFT -> human.move(human.position.copy(x = human.position.x - human.size))
            Action.MOVE_RIGHT -> human.move(human.position.copy(x = human.position.x + human.size))
        }

    private fun ObjectModel.Human.move(position: Position) =
        when (this) {
            is MaleObjectModel -> this.copy(position = position)
        }

    private fun String.toType() = chars().sum().toFloat()
}