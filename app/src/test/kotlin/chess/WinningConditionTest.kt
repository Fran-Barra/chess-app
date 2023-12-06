package chess

import Move
import boardGame.board.Vector
import chess.game.gameFactory.BasicChessFactory
import org.junit.jupiter.api.Test
import performMovementsAndLastMovementMustWin

class WinningConditionTest {

    @Test
    fun testMateInThree() {
        performMovementsAndLastMovementMustWin(
            BasicChessFactory.getGame(),
            listOf(
                Move(Vector(6, 7), Vector(6, 6)), Move(Vector(5, 2), Vector(5, 4)),
                Move(Vector(7, 7), Vector(7, 5)), Move(Vector(4, 1), Vector(8, 5))
            )
        )
    }

    @Test
    fun testScholarMate() {
        performMovementsAndLastMovementMustWin(
            BasicChessFactory.getGame(),
            listOf(
                Move(Vector(5, 7), Vector(5, 5)), Move(Vector(5, 2), Vector(5, 4)),
                Move(Vector(4, 8), Vector(8, 4)), Move(Vector(2, 1), Vector(3, 3)),
                Move(Vector(6, 8), Vector(3, 5)), Move(Vector(7, 1), Vector(6, 3)),
                Move(Vector(8, 4), Vector(6, 2))
            )
        )
    }

    @Test
    fun checkButNotCheckmate() {
        performMovementsAndLastMovementMustWin(
            BasicChessFactory.getGame(),
            listOf(
                Move(Vector(5, 7), Vector(5, 6)), Move(Vector(4, 2), Vector(4, 3)),
                Move(Vector(6, 8), Vector(2, 4))
            ),
            false
        )
    }
}