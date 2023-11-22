package boardGame.piece

import java.util.UUID

interface Piece {
    fun getPieceType(): Int
    fun getPieceColor(): Int
    fun getPieceId(): UUID
}