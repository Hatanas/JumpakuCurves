package jumpaku.examples

import javafx.application.Application
import javafx.scene.layout.Pane
import jumpaku.fxcomponents.colors.CudPalette
import jumpaku.fxcomponents.colors.fx
import jumpaku.fxcomponents.nodes.curve
import jumpaku.fxcomponents.nodes.fscUpdateControl
import jumpaku.fxcomponents.nodes.fuzzyCurve
import jumpaku.fxcomponents.nodes.onFscUpdated
import tornadofx.App
import tornadofx.View
import tornadofx.group
import tornadofx.pane


fun main(vararg args: String) = Application.launch(AppExample::class.java, *args)

class AppExample : App(ViewExample::class)

class ViewExample : View() {
    override val root: Pane = pane {
        val group = group {}
        fscUpdateControl {
            prefWidth = 1280.0
            prefHeight = 720.0
            onFscUpdated { e ->
                with(group) {
                    children.clear()
                    val s = e.fsc
                    curve(s) { stroke = CudPalette.RED.fx() }
                    fuzzyCurve(s) { stroke = CudPalette.BLUE.fx() }
                }
            }
        }
    }
}