package boardGame.piece


interface Piece {
    fun getPieceType(): Int
    fun getPieceColor(): Int
    fun getPieceId(): Int
}