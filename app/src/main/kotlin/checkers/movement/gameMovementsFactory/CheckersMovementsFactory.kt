package checkers.movement.gameMovementsFactory

import boardGame.movement.*
import checkers.movement.performer.JumpAndEatPerformer
import checkers.movement.validator.composedValidators.CheckerMovement
import checkers.movement.validator.composedValidators.EatCheckerMovement
import checkers.movement.validator.composedValidators.EatNormalMovement
import checkers.movement.validator.composedValidators.NormalPieceMovement

object CheckersMovementsFactory: GameMovementsFactory {
    override fun getMovementsManager(): MovementManager {
        val movements: Map<Int, List<Movement>> = mutableMapOf(
            Pair(0, listOf(
                Movement(CheckerMovement.getMovementStrategy(), FromTooMovementPerformer),
                Movement(EatCheckerMovement.getMovementStrategy(), JumpAndEatPerformer)
            )),
            Pair(1, listOf(
                Movement(NormalPieceMovement.getMovementStrategy(), PerformMovementAndPromoteIfPossible(FromTooMovementPerformer)),
                Movement(EatNormalMovement.getMovementStrategy(), PerformMovementAndPromoteIfPossible(JumpAndEatPerformer))
            ))
        )

        return PieceTypeMovementManager(movements)
    }

    override fun getMovementsManagerController(): MovementManagerController {
        return BaseMovementManagerController(listOf())
    }
}