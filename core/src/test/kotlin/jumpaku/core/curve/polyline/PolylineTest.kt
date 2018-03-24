package jumpaku.core.curve.polyline

import jumpaku.core.affine.*
import org.apache.commons.math3.util.FastMath
import org.assertj.core.api.Assertions.*
import jumpaku.core.curve.Interval
import jumpaku.core.json.parseJson
import jumpaku.core.util.component1
import jumpaku.core.util.component2
import org.assertj.core.api.AbstractAssert
import org.junit.Test

fun polylineAssertThat(actual: Polyline): PolylineAssert = PolylineAssert(actual)

class PolylineAssert(actual: Polyline) : AbstractAssert<PolylineAssert, Polyline>(actual, PolylineAssert::class.java) {

    fun isEqualToPolyline(expected: Polyline, eps: Double = 1.0e-10): PolylineAssert {
        isNotNull

        if (actual.points.size() != expected.points.size()){
            failWithMessage("Expected polyline size to be <%s> but was <%s>", expected.points.size(), actual.points.size())
            return this
        }

        actual.points.zip(expected.points)
                .forEachIndexed {
                    i, (a, e) -> pointAssertThat(a).`as`("polyline.points[%d]", i).isEqualToPoint(e, eps)
                }

        return this
    }
}

class PolylineTest {

    private val pl = Polyline(Point.xyr(-1.0, 1.0, 2.0), Point.xyr(1.0, 1.0, 1.0), Point.xyr(1.0, -3.0, 3.0), Point.xyzr(1.0, -3.0, 1.5, 2.0))

    @Test
    fun testProperties() {
        println("Properties")
        val p = pl
        pointAssertThat(p.points[0]).isEqualToPoint(Point.xyr(-1.0, 1.0, 2.0))
        pointAssertThat(p.points[1]).isEqualToPoint(Point.xyr( 1.0, 1.0, 1.0))
        pointAssertThat(p.points[2]).isEqualToPoint(Point.xyr( 1.0,-3.0, 3.0))
        pointAssertThat(p.points[3]).isEqualToPoint(Point.xyzr( 1.0,-3.0, 1.5, 2.0))
        assertThat(p.domain.begin).isEqualTo(0.0, withPrecision(1.0e-10))
        assertThat(p.domain.end)  .isEqualTo(7.5, withPrecision(1.0e-10))
    }

    @Test
    fun testToString() {
        println("ToString")
        val p = pl
        polylineAssertThat(p.toString().parseJson().flatMap { Polyline.fromJson(it) }.get()).isEqualToPolyline(p)
    }

    @Test
    fun testEvaluate() {
        println("Evaluate")
        val p = Polyline(Point.xyr(1.0, 1.0, 2.0), Point.xyr(-1.0, -1.0, 1.0), Point.xyzr(-1.0, -1.0, 1.0, 0.0))
        pointAssertThat(p.evaluate(0.0)).isEqualToPoint(Point.xyzr( 1.0, 1.0, 0.0, 2.0))
        pointAssertThat(p.evaluate(Math.sqrt(2.0))).isEqualToPoint(Point.xyzr( 0.0, 0.0, 0.0, 1.5))
        pointAssertThat(p.evaluate(2 * Math.sqrt(2.0))).isEqualToPoint(Point.xyzr(-1.0,-1.0, 0.0, 1.0))
        pointAssertThat(p.evaluate(2 * Math.sqrt(2.0) + 0.5)).isEqualToPoint(Point.xyzr(-1.0,-1.0, 0.5, 0.5))
        pointAssertThat(p.evaluate(2 * Math.sqrt(2.0) + 1)).isEqualToPoint(Point.xyzr(-1.0,-1.0, 1.0, 0.0))
    }

    @Test
    fun testEvaluateAll() {
        println("EvaluateAll")
        val ps = pl.evaluateAll(6)
        assertThat(ps.size()).isEqualTo(6)
        pointAssertThat(ps[0]).isEqualToPoint(Point.xyzr(-1.0, 1.0, 0.0, 2.0 ))
        pointAssertThat(ps[1]).isEqualToPoint(Point.xyzr( 0.5, 1.0, 0.0, 1.25))
        pointAssertThat(ps[2]).isEqualToPoint(Point.xyzr( 1.0, 0.0, 0.0, 1.5 ))
        pointAssertThat(ps[3]).isEqualToPoint(Point.xyzr( 1.0,-1.5, 0.0, 2.25))
        pointAssertThat(ps[4]).isEqualToPoint(Point.xyzr( 1.0,-3.0, 0.0, 3.0 ))
        pointAssertThat(ps[5]).isEqualToPoint(Point.xyzr( 1.0,-3.0, 1.5, 2.0 ))
    }

    @Test
    fun testTransform() {
        println("Transform")
        val b = pl
        val a = b.transform(identity.andScale(2.0).andRotate(Vector(0.0, 0.0, 1.0), FastMath.PI/2).andTranslate(Vector(1.0, 1.0)))
        val e = Polyline(Point.xy(-1.0, -1.0), Point.xy(-1.0, 3.0), Point.xy(7.0, 3.0), Point.xyz(7.0, 3.0, 3.0))
        polylineAssertThat(a).isEqualToPolyline(e)
    }

    @Test
    fun testToCrisp() {
        println("ToCrisp")
        val p = pl.toCrisp()
        polylineAssertThat(p).isEqualToPolyline(
                Polyline(Point.xy(-1.0, 1.0), Point.xy(1.0, 1.0), Point.xy(1.0, -3.0), Point.xyz(1.0, -3.0, 1.5)))

    }

    @Test
    fun testReverse() {
        println("Reverse")
        val p = pl.reverse()
        polylineAssertThat(p).isEqualToPolyline(
                Polyline(Point.xyzr(1.0, -3.0, 1.5, 2.0), Point.xyr(1.0, -3.0, 3.0), Point.xyr(1.0, 1.0, 1.0), Point.xyr(-1.0, 1.0, 2.0)))
    }

    @Test
    fun testRestrict() {
        println("Restrict")
        val r0 = pl.restrict(3.0, 4.5)
        polylineAssertThat(r0).isEqualToPolyline(Polyline(Point.xyr(1.0, 0.0, 1.5), Point.xyr(1.0, -1.5, 2.25)))

        val r1 = pl.restrict(Interval(3.0, 4.5))
        polylineAssertThat(r1).isEqualToPolyline(Polyline(Point.xyr(1.0, 0.0, 1.5), Point.xyr(1.0, -1.5, 2.25)))
    }

    @Test
    fun testSubdivide() {
        println("Subdivide")
        val ps = pl.subdivide(4.5)
        polylineAssertThat(ps._1()).isEqualToPolyline(Polyline(Point.xyr(-1.0, 1.0, 2.0), Point.xyr(1.0, 1.0, 1.0), Point.xyr(1.0, -1.5, 2.25)))
        polylineAssertThat(ps._2()).isEqualToPolyline(Polyline(Point.xyr(1.0, -1.5, 2.25), Point.xyr(1.0, -3.0, 3.0), Point.xyzr(1.0, -3.0, 1.5, 2.0)))
    }

    @Test
    fun testToArcLengthCurve() {
        println("ToArcLengthCurve")
        val l = Polyline(
                Point.xyr(-100.0, 100.0, 2.0),
                Point.xyr(100.0, 100.0, 1.0),
                Point.xyr(100.0, -300.0, 3.0),
                Point.xyzr(100.0, -300.0, 150.0, 2.0))
                .reparametrizeArcLength().arcLength()
        assertThat(l).isEqualTo(750.0, withPrecision(0.1))
    }
}