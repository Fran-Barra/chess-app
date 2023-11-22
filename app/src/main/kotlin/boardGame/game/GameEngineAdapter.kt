package boardGame.game

import edu.austral.dissis.chess.gui.*

//TODO: refactor this and make my own GameEngine / gameAdapter
class GameEngineAdapter(private var game: Game,
    private val pieceTypeToString: GetPieceFromTypeInStringFormat
): GameEngine {
    override fun init(): InitialState = getInitialState(game, pieceTypeToString)

    override fun applyMove(move: Move): MoveResult {
        return fromGameResultToMoveResult(
            game.makeMovement(
                game.getActualPlayer(), fromPosToVector(move.from), fromPosToVector(move.to)
            )
        )
    }

    private fun fromGameResultToMoveResult(gameResult: GameMovementResult): MoveResult {
        return when (gameResult) {
            is PlayerWon -> GameOver(fromPlayerToPlayerColor(gameResult.player))
            is MovementFailed -> InvalidMove(gameResult.message)
            is MovementSuccessful -> {
                game = gameResult.newGameState
                NewGameState(fromGameToListPieces(game, pieceTypeToString), fromPlayerToPlayerColor(game.getActualPlayer()))
            }
        }
    }
}