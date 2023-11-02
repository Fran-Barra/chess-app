package chess.winningConditionStrategy

import boardGame.board.Board
import boardGame.board.Vector
import boardGame.movement.MovementStrategy
import boardGame.movement.SpecialMovementController
import boardGame.piece.Piece
import boardGame.pieceEatingRuler.PieceEatingRuler
import boardGame.player.Player
import boardGame.winningConditionStrategy.WinningConditionStrategy
import boardGame.turnsController.TurnsController


//TODO: clean up code
class CheckmateWinningCondition: WinningConditionStrategy {
    override fun checkWinningConditions(board: Board, actualPlayer: Player, turnsController: TurnsController,
                                        pieceEatingRuler: PieceEatingRuler, pieceMovementStrategy: Map<Int, MovementStrategy>,
                                        specialMovementsController: SpecialMovementController
    ): Result<Boolean> {

        val pieceToCheck: Piece = findPieceToCheck()
        val pieces: List<Pair<Piece, Vector>> = board.getPiecesAndPosition()
        val boarPositionNotAvailable: Map<Vector, Boolean> =
            findNotAvailablePosition(pieceToCheck, pieces, buildMarkedBoard(board),
                board, pieceMovementStrategy, specialMovementsController, pieceEatingRuler, actualPlayer)

        return Result.success(isCheckMate(pieceToCheck, actualPlayer, boarPositionNotAvailable, board,
            pieceEatingRuler, pieceMovementStrategy, specialMovementsController))
    }

    private fun isCheckMate(piece: Piece, player: Player, markedBoard: Map<Vector, Boolean>,
                            board: Board,
                            pieceEatingRuler: PieceEatingRuler,
                            pieceMovementStrategy: Map<Int, MovementStrategy>,
                            specialMovementsController: SpecialMovementController
    ): Boolean {

        if (!isCheckPieceInCheck(markedBoard)) return false

        if (canCheckPieceMoveToNotCheck(piece, player, markedBoard, board, pieceEatingRuler, pieceMovementStrategy,
                specialMovementsController)) return false

        if (canAnotherPieceSaveTheCheckPieceFromCheck()) return false
        return true
    }

    private fun isCheckPieceInCheck(markedBoard: Map<Vector, Boolean>): Boolean{
       return markedBoard[findPiecePosition()] ?: false
    }

    //TODO("Consider special movements")
    private fun canCheckPieceMoveToNotCheck(piece: Piece, player: Player, markedBoard: Map<Vector, Boolean>,
                                            board: Board,
                                            pieceEatingRuler: PieceEatingRuler,
                                            pieceMovementStrategy: Map<Int, MovementStrategy>,
                                            specialMovementsController: SpecialMovementController
    ): Boolean{

        val strategy: MovementStrategy = pieceMovementStrategy[piece.getPieceType()] ?: return false

        for (position in markedBoard) {
            if (strategy.checkMovement(pieceEatingRuler, player, findPiecePosition(), position.key, board))
                return true
        }
        return false
    }

    private fun canAnotherPieceSaveTheCheckPieceFromCheck(): Boolean {
        TODO("Implement this shit")
    }

    private fun findNotAvailablePosition(pieceToCheck: Piece,
                                         pieces: List<Pair<Piece, Vector>>,
                                         markedBoard: Map<Vector, Boolean>,
                                         board: Board,
                                         pieceMovementStrategy: Map<Int, MovementStrategy>,
                                         specialMovementsController: SpecialMovementController,
                                         pieceEatingRuler: PieceEatingRuler,
                                         actualPlayer: Player
    ):
            Map<Vector, Boolean>{

        var markedBoarAux: MutableMap<Vector, Boolean> = markedBoard.toMutableMap()
        for (piece in pieces) {
            if (pieceToCheck == piece) continue
            markedBoarAux = pieceBlockPosition(piece, markedBoarAux, board, pieceMovementStrategy,
                specialMovementsController, pieceEatingRuler, actualPlayer)
        }
        return markedBoarAux
    }

    //TODO("consider special movements")
    //TODO("move pieces of the other player")
    private fun pieceBlockPosition(piecePair: Pair<Piece, Vector>,
                                   markedBoard: MutableMap<Vector, Boolean>,
                                   board: Board,
                                   pieceMovementStrategy: Map<Int, MovementStrategy>,
                                   specialMovementsController: SpecialMovementController,
                                   pieceEatingRuler: PieceEatingRuler,
                                   actualPlayer: Player
    ):
            MutableMap<Vector, Boolean> {

        val markedBoarAux: MutableMap<Vector, Boolean> = markedBoard;
        val piece: Piece = piecePair.first
        if (!actualPlayer.playerControlColor(piece.getPieceColor())) return markedBoarAux

        val movementStrategy: MovementStrategy = pieceMovementStrategy[piece.getPieceType()] ?: return markedBoarAux

        for (position in markedBoarAux) {
            val previousValue: Boolean? = markedBoarAux[position.key]
            if (previousValue == null) {
                markedBoarAux[position.key] = movementStrategy
                    .checkMovement(pieceEatingRuler, actualPlayer, piecePair.second, position.key, board)
            }else{
                if (previousValue) continue
                markedBoarAux[position.key] = movementStrategy
                    .checkMovement(pieceEatingRuler, actualPlayer, piecePair.second, position.key, board)
            }
        }

        return markedBoarAux

    }

    private fun buildMarkedBoard(board: Board) : Map<Vector, Boolean>{
        val boardList: List<Pair<Vector, Piece?>> = board.getBoardAssList()
        val mutableMap: MutableMap<Vector, Boolean> = mutableMapOf()
        boardList.forEach{(v, p) -> mutableMap[v] = (p != null)}
        return mutableMap
    }

    private fun findPieceToCheck(): Piece {
        TODO("Not implemented")
    }

    private fun findPiecePosition(): Vector {
        TODO("Implement method")
    }
}

