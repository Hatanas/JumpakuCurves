package jumpaku.fsc.test.generate

import com.github.salomonbrys.kotson.array
import io.vavr.collection.Array
import jumpaku.core.geom.ParamPoint
import jumpaku.core.curve.bspline.BSpline
import jumpaku.core.json.parseJson
import jumpaku.core.test.curve.bspline.shouldEqualToBSpline
import jumpaku.fsc.generate.DataPreparer
import jumpaku.fsc.generate.FscGenerator
import jumpaku.fsc.generate.LinearFuzzifier
import org.amshove.kluent.shouldBeGreaterThan
import org.junit.Test

class FscGeneratorTest {

    val urlString = "/jumpaku/fsc/test/generate/"
    fun resourceText(name: String): String = javaClass.getResource(urlString + name).readText()

    val generator = FscGenerator(
            degree = 3,
            knotSpan = 0.1,
            preparer = DataPreparer(0.1/3, 0.1, 0.1, 2),
            fuzzifier = LinearFuzzifier(0.004, 0.003))

    @Test
    fun testGenerate() {
        println("Generate")
        (0..2).forEach { i ->
            val data = Array.ofAll(resourceText("Data$i.json").parseJson().get().array.flatMap { ParamPoint.fromJson(it) })
            val e = resourceText("Fsc$i.json").parseJson().flatMap { BSpline.fromJson(it) }.get()
            val a = generator.generate(data)
            a.isPossible(e, 100).value.shouldBeGreaterThan(0.9)
        }
    }
}
