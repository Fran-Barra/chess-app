package connection

import edu.austral.dissis.chess.gui.GameView
import edu.austral.dissis.chess.gui.MoveResult
import javafx.application.Application
import javafx.application.Platform
import javafx.scene.Scene
import javafx.stage.Stage

class BoardApplication: Application() {
    private lateinit var stage: Stage
    private lateinit var gameView: GameView

    override fun start(primaryStage: Stage) {
        primaryStage.title = "Board Game"
        primaryStage.show()
        stage = primaryStage

        ClientGame(this)
    }

    public fun updateUI(gameView: GameView){
        this.gameView = gameView
        Platform.runLater {
            stage.scene = Scene(gameView)
            stage.show()
        }
    }

    fun handleMoveResult(result: MoveResult) {
        gameView.handleMoveResult(result)
    }
}