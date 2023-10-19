import board.Board
import vector.Vector


class App(private val game: Game) {
    fun startGame(){
        var origin: Vector
        var destination: Vector
        var resultOfMovement: Result<Game>
        while (true){
            origin = getVectorInputFromPlayer("from")
            destination = getVectorInputFromPlayer("destination")
            resultOfMovement = game.makeMovement(game.actualPlayer, origin, destination)
            if (resultOfMovement.isFailure)
                println(resultOfMovement.exceptionOrNull())
        }
    }

    private fun getVectorInputFromPlayer(position: String): Vector{
        while (true){
            println("Input position to move $position in (number): <x>, <y>")
            val vectorR: Result<Vector> = getVector(readln())
            if (vectorR.isFailure)
                println(vectorR.exceptionOrNull()!!)
            else
                return vectorR.getOrNull()!!
        }
    }

    private fun getVector(vector: String): Result<Vector>{
        if (!vector.contains(","))
            return Result.failure(Exception("Expected \",\" to separate <x> and <y>: <x>, <y>"))

        val numbers: List<String> = vector.split(",")
        val x: Int
        val y: Int
        try{
            x = numbers[0].toInt();
            y = numbers[1].toInt()
        }catch (err: NumberFormatException){
            return Result.failure(Exception("x and y should be numbers"))
        }
        return Result.success(Vector(x, y))
    }
}