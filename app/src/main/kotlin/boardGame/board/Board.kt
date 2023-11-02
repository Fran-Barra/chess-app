package boardGame.board

import boardGame.piece.Piece

interface Board {
    /**this will move the piece to the destination, if the destination was related to a piece the piece will bo loss.*/
    fun movePiece(piece: Piece, destination: Vector): Board

    /**adds a piece in the position, if there was a piece it will be loss*/
    fun addPiece(piece: Piece, position: Vector): Board

    /**removes the piece from the board, letting the position empty*/
    fun removePiece(piece: Piece): Board

    /**Returns the piece in the position, if not found it returns null*/
    fun getPieceInPosition(position: Vector): Result<Piece>

    /**Returns true if the position exists and false if not*/
    fun positionExists(position: Vector): Boolean

    /**Returns all pieces and their position*/
    fun getPiecesAndPosition(): List<Pair<Piece, Vector>>

    fun getBoardAssList(): List<Pair<Vector, Piece?>>
}

class Vector (val x: Int, val y: Int){
    operator fun plus(other: Vector): Vector {
        return Vector(x + other.x, y + other.y);
    }

    operator fun minus(other: Vector): Vector {
        return Vector(x - other.x, y - other.y)
    }

    operator fun times(other: Vector): Int{
        return x*other.x + y*other.y
    }

    operator fun times(mul: Int): Vector{
        return Vector(x*mul, y*mul)
    }

    override fun equals(other: Any?): Boolean {
        if (other !is Vector) return false
        if (x != other.x) return false
        if (y != other.y) return false
        return true
    }

    override fun hashCode(): Int {
        var result = x
        result = 31 * result + y
        return result
    }
}