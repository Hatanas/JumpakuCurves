package jumpaku.fsc.classify.reference

import com.github.salomonbrys.kotson.array
import io.vavr.collection.Array
import jumpaku.core.affine.point
import jumpaku.core.affine.pointAssertThat
import jumpaku.core.curve.bspline.bSpline
import jumpaku.core.json.parseToJson
import jumpaku.core.util.component1
import jumpaku.core.util.component2
import org.junit.Test
import java.nio.file.Path
import java.nio.file.Paths


class CircularTest {

    val path: Path = Paths.get("./src/test/resources/jumpaku/fsc/classify/reference")

    @Test
    fun testReference() {
        println("Reference")
        val s = path.resolve("circularFsc.json").toFile().readText().parseToJson().get().bSpline
        val eps = Array.ofAll(path.resolve("circularReference.json").toFile().readText().parseToJson().get().array.map { it.point })
        val rps = Circular.of(s, nSamples = 99).reference.evaluateAll(eps.size())
        rps.zip(eps).forEach { (r, e) -> pointAssertThat(r).isEqualToPoint(e) }
    }
}