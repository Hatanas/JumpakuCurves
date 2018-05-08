package jumpaku.fsc.oldtest.classify

import org.assertj.core.api.Assertions.*
import jumpaku.core.fuzzy.Grade
import jumpaku.core.json.parseJson
import jumpaku.fsc.classify.ClassifyResult
import jumpaku.fsc.classify.CurveClass
import jumpaku.fsc.oldtest.classify.classifyResultAssertThat
import org.junit.Test


class ClassifyResultTest {

    val s = arrayOf(
            CurveClass.Point to Grade(0.3),
            CurveClass.LineSegment to Grade(0.7),
            CurveClass.Circle to Grade(0.4),
            CurveClass.CircularArc to Grade(0.0),
            CurveClass.Ellipse to Grade(0.9),
            CurveClass.EllipticArc to Grade(0.3),
            CurveClass.ClosedFreeCurve to Grade(0.5),
            CurveClass.OpenFreeCurve to Grade(0.8))

    val r = ClassifyResult(*s)

    @Test
    fun testProperties() {
        println("Properties")
        assertThat(r.curveClass).isEqualTo(CurveClass.Ellipse)
        assertThat(r.grade.value).isEqualTo(0.9, withPrecision(1.0e-10))
    }

    @Test
    fun testToString() {
        println("ToString")
        val a = r.toString().parseJson().flatMap { ClassifyResult.fromJson(it) }.get()
        classifyResultAssertThat(a).isEqualToClassifyResult(r)
    }
}