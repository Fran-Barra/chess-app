package checkers.game

import boardGame.game.PieceToString


val checkersIdToString = PieceToString(
    mapOf(
        Pair(0, "queen"),
        Pair(1, "pawn")
    ),
    "Pawn"
)