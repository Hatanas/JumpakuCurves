package jumpaku.fsc.test.classify.reference

import com.github.salomonbrys.kotson.array
import io.vavr.collection.Array
import jumpaku.core.affine.Point
import jumpaku.core.curve.bspline.BSpline
import jumpaku.core.json.parseJson
import jumpaku.core.test.affine.pointAssertThat
import jumpaku.core.util.component1
import jumpaku.core.util.component2
import jumpaku.fsc.classify.reference.Elliptic
import org.junit.Test
import java.nio.file.Path
import java.nio.file.Paths


class EllipticTest {

    val path: Path = Paths.get("./src/test/resources/jumpaku/fsc/test/classify/reference")

    @Test
    fun testReference() {
        println("Reference")
        val s = path.resolve("ellipticFsc.json").parseJson().flatMap { BSpline.fromJson(it) }.get()
        val eps = Array.ofAll(path.resolve("ellipticReference.json").parseJson().get().array.flatMap { Point.fromJson(it) })
        val rps = Elliptic.of(s, nSamples = 25).reference.evaluateAll(eps.size())
        rps.zip(eps).forEach { (r, e) -> pointAssertThat(r).isEqualToPoint(e) }
    }
}