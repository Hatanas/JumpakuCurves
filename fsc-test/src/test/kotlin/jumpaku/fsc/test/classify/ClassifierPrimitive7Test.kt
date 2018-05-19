package jumpaku.fsc.test.classify

import jumpaku.core.curve.bspline.BSpline
import jumpaku.core.json.parseJson
import jumpaku.fsc.classify.ClassifierPrimitive7
import jumpaku.fsc.classify.ClassifyResult
import org.amshove.kluent.shouldBe
import org.junit.Test

class ClassifierPrimitive7Test {

    val urlString = "/jumpaku/fsc/test/classify/"
    fun resourceText(name: String): String = javaClass.getResource(urlString + name).readText()

    val classifier = ClassifierPrimitive7(nSamples = 25, nFmps = 15)

    @Test
    fun testClassify() {
        println("ClassifierPrimitive7.classify")
        for (i in (0..9)) {
            val s = resourceText("fsc$i.json").parseJson().flatMap { BSpline.fromJson(it) }.get()
            val e = resourceText("primitiveResult$i.json").parseJson().flatMap { ClassifyResult.fromJson(it) }.get()
            val a = classifier.classify(s)
            a.curveClass.shouldBe(e.curveClass)
        }


    }
}