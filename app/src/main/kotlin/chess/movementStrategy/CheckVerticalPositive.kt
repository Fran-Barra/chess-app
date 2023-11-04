package chess.movementStrategy

import boardGame.board.Board
import boardGame.board.Vector
import boardGame.movement.MovementValidator
import boardGame.pieceEatingRuler.PieceEatingRuler
import boardGame.player.Player

object CheckVerticalPositive: MovementValidator {

    override fun validate(pieceEatingRuler: PieceEatingRuler, player: Player, actual: Vector,
                          destination: Vector, board: Board
    ): Boolean {
        if (player.getPlayerId() == 0) return destination.y < actual.y
        return destination.y > actual.y
    }
}