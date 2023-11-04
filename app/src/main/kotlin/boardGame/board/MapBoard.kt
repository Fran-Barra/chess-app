package boardGame.board

import FailedOutcome
import Outcome
import SuccessfulOutcome
import boardGame.piece.Piece
import java.util.NoSuchElementException

class MapBoard(val board: Map<Vector, Piece?>): Board {

    override fun movePiece(piece: Piece, destination: Vector): Board {
        val updatedPieceMap = board.toMutableMap()

        val currentPosition = board.entries.find { it.value == piece }?.key?: return this

        updatedPieceMap[currentPosition] = null
        updatedPieceMap[destination] = piece

        return MapBoard(updatedPieceMap)
    }

    override fun addPiece(piece: Piece, position: Vector): Board {
        return MapBoard(board.toMutableMap().apply {if (containsKey(position)) put(position, piece)})
    }

    override fun removePiece(piece: Piece): Board {
        val updatedPieceMap = board.toMutableMap()
        val currentPosition: Vector? = updatedPieceMap.entries.find { it.value == piece }?.key

        if (currentPosition == null) return this
        updatedPieceMap[currentPosition] = null

        return MapBoard(updatedPieceMap)
    }

    override fun getPieceInPosition(position: Vector): Outcome<Piece> {
        return if (board.containsKey(position)) {
            val piece = board[position]
            if (piece != null) {SuccessfulOutcome(piece)}
            else {FailedOutcome("No boardGame.piece found in position $position")}
        } else {
            FailedOutcome("Key $position does not exist in the map")
        }
    }

    override fun positionExists(position: Vector): Boolean {
        return board.containsKey(position)
    }

    override fun getPiecesAndPosition(): List<Pair<Piece, Vector>> {
        val piecesAndPositions = mutableListOf<Pair<Piece, Vector>>()
        for ((vector, piece) in board)
            piece?.let { piecesAndPositions.add(it to vector) }
        return piecesAndPositions
    }

    override fun getBoardAssList(): List<Pair<Vector, Piece?>> {
        return  board.toList()
    }
}