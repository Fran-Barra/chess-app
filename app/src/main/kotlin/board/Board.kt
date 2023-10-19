package board

import piece.Piece
import vector.Vector

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
}