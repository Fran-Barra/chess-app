package chess.movementStrategy.gameMovementsFactory

import boardGame.board.Board
import boardGame.movement.*
import boardGame.movement.movementManager.*
import chess.movementStrategy.movementPerformer.CastlingPerformer
import chess.movementStrategy.movementPerformer.EmptyPerformer
import chess.movementStrategy.movementStrategyFactory.*
import chess.movementStrategy.updateMovementManagerOverEvent.OnPieceTypeMovedLossMovement
import chess.movementStrategy.validators.CastlingValidator
import chess.movementStrategy.validators.RookCastlingValidator

class BasicChessMovements(private val board: Board): GameMovementsFactory {
    override fun getMovementsManager(): MovementManager {
        return CombinedMovementManagers(
            mapOf(
                Pair(MovementManagerType.Id_Piece_Type, buildMovementPieceType()),
                Pair(MovementManagerType.Id_Piece, buildMovementSpecificPiece())
            ),
        StandardMovementManagerClassifier())
    }

    private fun buildMovementPieceType(): IdMovementManager {
        val movementStrategies: MutableMap<Int, List<Movement>> = mutableMapOf()
        movementStrategies[0] = listOf(
            Movement(KingMovementStrategy.getMovementStrategy(), FromTooMovementPerformer)
        )
        movementStrategies[1] = listOf(
            Movement(QueenMovementStrategy.getMovementStrategy(), FromTooMovementPerformer)
        )
        movementStrategies[2] = listOf(
            Movement(BishopMovementStrategy.getMovementStrategy(), FromTooMovementPerformer)
        )
        movementStrategies[3] = listOf(
            Movement(KnightMovementStrategy.getMovementStrategy(), FromTooMovementPerformer)
        )
        movementStrategies[4] = listOf(
            Movement(RookMovementStrategy.getMovementStrategy(), FromTooMovementPerformer)
        )
        movementStrategies[5] = listOf(
            Movement(PawnMovementStrategy.getMovementStrategy(), FromTooMovementPerformer)
        )

        return IdMovementManager(movementStrategies, GetPieceTypeId())
    }

    private fun buildMovementSpecificPiece(): MovementManager {
        val map = mutableMapOf<Int, List<Movement>>()
        addPawnStartingMovement(map)
        addCastling(map)
        return IdMovementManager(map, GetPieceId())
    }

    private fun addPawnStartingMovement(map: MutableMap<Int, List<Movement>>) {
        for ((piece, _) in board.getPiecesAndPosition()) {
            if (piece.getPieceType() != 5) continue
            val movements = map[piece.getPieceId()]?: listOf()
            map[piece.getPieceId()] = movements +
                    listOf(Movement(PawnStartingMovement2.getMovementStrategy(), FromTooMovementPerformer))
        }
    }

    private fun addCastling(map: MutableMap<Int, List<Movement>>){
        for ((piece, _) in board.getPiecesAndPosition()) {
            if (piece.getPieceType() == 0) {
                val movements = map[piece.getPieceId()] ?: listOf()
                map[piece.getPieceId()] = movements + listOf(Movement(CastlingValidator, CastlingPerformer))
            } else if (piece.getPieceType() == 4) {
                val movements = map[piece.getPieceId()] ?: listOf()
                map[piece.getPieceId()] = movements + listOf(Movement(RookCastlingValidator, EmptyPerformer))
            }
        }
    }

    override fun getMovementsManagerController(): MovementManagerController {
        return BaseMovementManagerController(listOf(
            OnPieceTypeMovedLossMovement(5, Movement(PawnStartingMovement2.getMovementStrategy(), FromTooMovementPerformer)),
            OnPieceTypeMovedLossMovement(4, Movement(RookCastlingValidator, EmptyPerformer)),
            OnPieceTypeMovedLossMovement(0, Movement(CastlingValidator, CastlingPerformer))
        ))
    }
}