import boardGame.board.Vector
import boardGame.game.Game
import boardGame.game.MovementFailed
import boardGame.game.MovementSuccessful
import boardGame.game.PlayerWon
import org.junit.jupiter.api.Assertions

data class Move(val from: Vector, val too: Vector)

fun performMovementsAndLastMovementMustWin(game: Game, movements: List<Move>, mustWin: Boolean = true) {
    var newGame = game
    for (movement in movements) {
        val player = when (val outcome = newGame.getActualPlayer()) {
            is SuccessfulOutcome -> outcome.data
            is FailedOutcome -> {assert(false) {"No valid player found to perform movement"}; return}
        }

        newGame = when (val outcome = newGame.makeMovement(player, movement.from, movement.too)) {
            is MovementFailed -> {assert(false) {"Movement $movement failed: ${outcome.message}"};return}
            is MovementSuccessful -> outcome.newGameState
            is PlayerWon -> {managePlayerWon(mustWin, movement, movements); return}
        }
    }
}

private fun managePlayerWon(mustWin: Boolean, movement: Move, movements: List<Move>) {
    if (!mustWin) {assert(false) {"Movement $movement should not win"}; return}
    if (movements.last() == movement) {assert(true)}
    else {assert(false) {"Movement $movement won, but was not last"}}
}

fun testMovement(game: Game, piecePos: Vector, validPos: List<Vector>){
    val actualPlayer = when (val outcome = game.getActualPlayer()) {
        is SuccessfulOutcome -> outcome.data
        is FailedOutcome -> throw Exception("This test need a game with an actual plaer")
    }
    val validatedMovements: MutableList<Vector> = mutableListOf()
    for ((pos: Vector, _) in game.getBoard().getBoardAssList()) {
        when (game.makeMovement(actualPlayer, piecePos, pos)) {
            is MovementFailed -> {}
            is MovementSuccessful -> validatedMovements.add(pos)
            is PlayerWon -> validatedMovements.add(pos)
        }
    }

    val extras = validatedMovements - validPos
    val missing = validPos - validatedMovements
    Assertions.assertEquals(0, extras.size) {"Extra positions validated: $extras"}
    Assertions.assertEquals(0, missing.size) {"Missing positions: $missing"}
}