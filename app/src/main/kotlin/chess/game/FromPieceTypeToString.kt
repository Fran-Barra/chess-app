package chess.game

import boardGame.game.PieceToString

val chessPieceToString = PieceToString(
    mapOf(
        Pair(0, "king"),
        Pair(1, "queen"),
        Pair(2, "bishop"),
        Pair(3, "knight"),
        Pair(4, "rook"),
        Pair(5, "pawn"),
        Pair(6, "archbishop"),
    ),
    "Ghost"
)
