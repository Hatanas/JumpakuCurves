package jumpaku.core.test.curve.bspline

import io.vavr.collection.Array
import jumpaku.core.affine.Point
import jumpaku.core.affine.Vector
import jumpaku.core.affine.identity
import jumpaku.core.curve.Interval
import jumpaku.core.curve.Knot
import jumpaku.core.curve.KnotVector
import jumpaku.core.curve.arclength.ArcLengthReparametrized
import jumpaku.core.curve.bezier.Bezier
import jumpaku.core.curve.bspline.BSpline
import jumpaku.core.json.parseJson
import jumpaku.core.test.affine.shouldBePoint
import jumpaku.core.test.curve.bezier.shouldBeBezier
import jumpaku.core.test.curve.shouldBeInterval
import jumpaku.core.test.curve.shouldBeKnotVector
import jumpaku.core.test.shouldBeCloseTo
import jumpaku.core.util.component1
import jumpaku.core.util.component2
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldBeFalse
import org.apache.commons.math3.util.FastMath
import org.junit.Test


class BSplineTest {

    val clamped = BSpline(
            Array.of(
                    Point.xyr(-1.0, 0.0, 0.0),
                    Point.xyr(-1.0, 1.0, 1.0),
                    Point.xyr(0.0, 1.0, 2.0),
                    Point.xyr(0.0, 0.0, 1.0),
                    Point.xyr(1.0, 0.0, 0.0)),
            KnotVector.clamped(Interval(3.0, 4.0), 3, 9))

    val uniform = BSpline(
            Array.of(
                    Point.xy(-1.0, 0.0),
                    Point.xy(-1.0, 1.0),
                    Point.xy(0.0, 1.0),
                    Point.xy(0.0, 0.0),
                    Point.xy(1.0, 0.0)),
            KnotVector.uniform(Interval(3.0, 4.0), 2, 8))

    @Test
    fun testProperties() {
        println("Properties")
        clamped.controlPoints[0].shouldBePoint(Point.xyr(-1.0, 0.0, 0.0))
        clamped.controlPoints[1].shouldBePoint(Point.xyr(-1.0, 1.0, 1.0))
        clamped.controlPoints[2].shouldBePoint(Point.xyr(0.0, 1.0, 2.0))
        clamped.controlPoints[3].shouldBePoint(Point.xyr(0.0, 0.0, 1.0))
        clamped.controlPoints[4].shouldBePoint(Point.xyr(1.0, 0.0, 0.0))
        clamped.controlPoints.size().shouldBe(5)

        clamped.knotVector.shouldBeKnotVector(KnotVector.clamped(Interval(3.0, 4.0), 3, 9))

        clamped.domain.shouldBeInterval(Interval(3.0, 4.0))

        clamped.degree.shouldBe(3)
    }

    @Test
    fun testToString() {
        println("ToString")
        clamped.toString().parseJson().flatMap { BSpline.fromJson(it) }.get().shouldBeBSpline(clamped)
    }

    @Test
    fun testEvaluate() {
        println("Evaluate")
        clamped.evaluate(3.0).shouldBePoint(Point.xyr(-1.0, 0.0, 0.0))
        clamped.evaluate(3.25).shouldBePoint(Point.xyr(-23 / 32.0, 27 / 32.0, 9 / 8.0))
        clamped.evaluate(3.5).shouldBePoint(Point.xyr(-1 / 4.0, 3 / 4.0, 1.5))
        clamped.evaluate(3.75).shouldBePoint(Point.xyr(3 / 32.0, 9 / 32.0, 9 / 8.0))
        clamped.evaluate(4.0).shouldBePoint(Point.xyr(1.0, 0.0, 0.0))

        uniform.evaluate(3.0).shouldBePoint(Point.xy(-1.0, 0.5))
        uniform.evaluate(3 + 1 / 3.0).shouldBePoint(Point.xy(-0.5, 1.0))
        uniform.evaluate(3 + 2 / 3.0).shouldBePoint(Point.xy(0.0, 0.5))
        uniform.evaluate(4.0).shouldBePoint(Point.xy(0.5, 0.0))
    }

    @Test
    fun testDifferentiate() {
        println("Differentiate")
        val e0 = BSpline(
                Array.of(Point.xy(0.0, 6.0), Point.xy(3.0, 0.0), Point.xy(0.0, -3.0), Point.xy(6.0, 0.0)),
                KnotVector.clamped(Interval(3.0, 4.0), 2, 7))
        clamped.derivative.toBSpline().shouldBeBSpline(e0)

        val e1 = BSpline(
                Array.of(Point.xy(0.0, 3.0), Point.xy(3.0, 0.0), Point.xy(0.0, -3.0), Point.xy(3.0, 0.0)),
                KnotVector.uniform(Interval(3.0, 4.0), 1, 6))
        uniform.derivative.toBSpline().shouldBeBSpline(e1)
    }

    @Test
    fun testTransform() {
        println("Transform")
        val a = clamped.transform(identity.andScale(2.0).andRotate(Vector(0.0, 0.0, 1.0), FastMath.PI/2).andTranslate(Vector(1.0, 1.0)))
        val e = BSpline(
                Array.of(Point.xy(1.0, -1.0), Point.xy(-1.0, -1.0), Point.xy(-1.0, 1.0), Point.xy(1.0, 1.0), Point.xy(1.0, 3.0)),
                KnotVector.clamped(Interval(3.0, 4.0), 3, 9))
        a.shouldBeBSpline(e)
    }

    @Test
    fun testToCrisp() {
        println("ToCrisp")
        val a = clamped.toCrisp()
        val e = BSpline(
                Array.of(Point.xy(-1.0, 0.0), Point.xy(-1.0, 1.0), Point.xy(0.0, 1.0), Point.xy(0.0, 0.0), Point.xy(1.0, 0.0)),
                KnotVector.clamped(Interval(3.0, 4.0), 3, 9))
        a.shouldBeBSpline(e)
    }

    @Test
    fun testRestrict() {
        println("Restrict")
        val e0 = BSpline(
                Array.of(Point.xyr(-0.25, 0.75, 1.5), Point.xyr(-0.125, 5 / 8.0, 1.5), Point.xyr(-1 / 16.0, 7 / 16.0, 11 / 8.0), Point.xyr(3 / 32.0, 9 / 32.0, 9 / 8.0)),
                KnotVector.clamped(Interval(3.5, 3.75), 3, 8))
        clamped.restrict(Interval(3.5, 3.75)).shouldBeBSpline(e0)

        val e1 = BSpline(
                Array.of(Point.xyr(-0.25, 0.75, 1.5), Point.xyr(-0.125, 5 / 8.0, 1.5), Point.xyr(-1 / 16.0, 7 / 16.0, 11 / 8.0), Point.xyr(3 / 32.0, 9 / 32.0, 9 / 8.0)),
                KnotVector.clamped(Interval(3.5, 3.75), 3, 8))
        clamped.restrict(3.5, 3.75).shouldBeBSpline(e1)

        clamped.restrict(3.0, 4.0).shouldBeBSpline(clamped)

        val e2 = BSpline(
                Array.of(Point.xy(-1.0, 0.5), Point.xy(-1.0, 1.0), Point.xy(0.0, 1.0), Point.xy(0.0, 0.0), Point.xy(0.5, 0.0)),
                KnotVector.clamped(Interval(3.0, 4.0), 2, 8))
        uniform.restrict(3.0, 4.0).shouldBeBSpline(e2)
    }

    @Test
    fun testReverse() {
        println("Reverse")
        val r = BSpline(
                Array.of(Point.xyr(-1.0, 0.0, 0.0), Point.xyr(-1.0, 1.0, 1.0), Point.xyr(0.0, 1.0, 2.0), Point.xyr(0.0, 0.0, 1.0), Point.xyr(1.0, 0.0, 0.0)),
                KnotVector.clamped(Interval(0.0, 2.0), 3, 9))
                .reverse()
        val e = BSpline(
                Array.of(Point.xyr(1.0, 0.0, 0.0), Point.xyr(0.0, 0.0, 1.0), Point.xyr(0.0, 1.0, 2.0), Point.xyr(-1.0, 1.0, 1.0), Point.xyr(-1.0, 0.0, 0.0)),
                KnotVector.clamped(Interval(0.0, 2.0), 3, 9))

        r.shouldBeBSpline(e)
    }

    @Test
    fun testToBeziers() {
        println("ToBeziers")
        val beziers0 = clamped.toBeziers()
        beziers0.size().shouldBe(2)
        beziers0[0].shouldBeBezier(Bezier(
                Point.xyr(-1.0, 0.0, 0.0), Point.xyr(-1.0, 1.0, 1.0), Point.xyr(-0.5, 1.0, 1.5), Point.xyr(-0.25, 0.75, 1.5)))
        beziers0[1].shouldBeBezier(Bezier(
                Point.xyr(-0.25, 0.75, 1.5), Point.xyr(0.0, 0.5, 1.5), Point.xyr(0.0, 0.0, 1.0), Point.xyr(1.0, 0.0, 0.0)))

        val beziers1 = uniform.toBeziers()
        beziers1.size().shouldBe(3)
        beziers1[0].shouldBeBezier(Bezier(
                Point.xy(-1.0, 0.5), Point.xy(-1.0, 1.0), Point.xy(-0.5, 1.0)))
        beziers1[1].shouldBeBezier(Bezier(
                Point.xy(-0.5, 1.0), Point.xy(0.0, 1.0), Point.xy(0.0, 0.5)))
        beziers1[2].shouldBeBezier(Bezier(
                Point.xy(0.0, 0.5), Point.xy(0.0, 0.0), Point.xy(0.5, 0.0)))
    }

    @Test
    fun testSubdivide() {
        println("Subdivide")
        val (s01, s02) = clamped.subdivide(3.0)
        s01.isDefined.shouldBeFalse()
        s02.get().shouldBeBSpline(clamped)

        val (s11, s12) = clamped.subdivide(3.5)
        s11.get().shouldBeBSpline(BSpline(
                Array.of(Point.xyr(-1.0, 0.0, 0.0), Point.xyr(-1.0, 1.0, 1.0), Point.xyr(-0.5, 1.0, 1.5), Point.xyr(-0.25, 0.75, 1.5)),
                KnotVector.clamped(Interval(3.0, 3.5), 3, 8)))
        s12.get().shouldBeBSpline(BSpline(
                Array.of(Point.xyr(-0.25, 0.75, 1.5), Point.xyr(0.0, 0.5, 1.5), Point.xyr(0.0, 0.0, 1.0), Point.xyr(1.0, 0.0, 0.0)),
                KnotVector.clamped(Interval(3.5, 4.0), 3, 8)))

        val (s21, s22) = clamped.subdivide(4.0)
        s21.get().shouldBeBSpline(clamped)
        s22.isDefined.shouldBeFalse()

        val (s31, s32) = uniform.subdivide(3.0)
        s31.isDefined.shouldBeFalse()
        s32.get().shouldBeBSpline(BSpline(
                Array.of(Point.xy(-1.0, 0.5), Point.xy(-1.0, 1.0), Point.xy(0.0, 1.0), Point.xy(0.0, 0.0), Point.xy(1.0, 0.0)),
                KnotVector(2, Knot(3.0, 3), Knot(10/3.0), Knot(11/3.0), Knot(4.0), Knot(13/3.0), Knot(14/3.0))))

        val (s41, s42) = uniform.subdivide(4.0)
        s41.get().shouldBeBSpline(BSpline(
                Array.of(Point.xy(-1.0, 0.0), Point.xy(-1.0, 1.0), Point.xy(0.0, 1.0), Point.xy(0.0, 0.0), Point.xy(0.5, 0.0)),
                KnotVector(2, Knot(7/3.0), Knot(8/3.0), Knot(3.0), Knot(10/3.0), Knot(11/3.0), Knot(4.0, 3))))
        s42.isDefined.shouldBeFalse()
    }

    @Test
    fun testInsertKnot() {
        println("InsertKnot")
        val b0 = clamped.insertKnot(3.25)
        val e0 = BSpline(
                Array.of(Point.xyr(-1.0, 0.0, 0.0), Point.xyr(-1.0, 0.5, 0.5), Point.xyr(-0.75, 1.0, 1.25), Point.xyr(0.0, 0.75, 1.75), Point.xyr(0.0, 0.0, 1.0), Point.xyr(1.0, 0.0, 0.0)),
                KnotVector(3, Knot(3.0, 4), Knot(3.25), Knot(3.5), Knot(4.0, 4)))
        b0.shouldBeBSpline(e0)

        val b1 = clamped.insertKnot(3.5, 2)
        val e1 = BSpline(
                Array.of(Point.xyr(-1.0, 0.0, 0.0), Point.xyr(-1.0, 1.0, 1.0), Point.xyr(-0.5, 1.0, 1.5), Point.xyr(-0.25, 0.75, 1.5), Point.xyr(0.0, 0.5, 1.5), Point.xyr(0.0, 0.0, 1.0), Point.xyr(1.0, 0.0, 0.0)),
                KnotVector(3, Knot(3.0, 4), Knot(3.5, 3), Knot(4.0, 4)))
        b1.shouldBeBSpline(e1)

        val b2 = clamped.insertKnot(3.5, 3)
        val e2 = BSpline(
                Array.of(Point.xyr(-1.0, 0.0, 0.0), Point.xyr(-1.0, 1.0, 1.0), Point.xyr(-0.5, 1.0, 1.5), Point.xyr(-0.25, 0.75, 1.5), Point.xyr(-0.25, 0.75, 1.5), Point.xyr(0.0, 0.5, 1.5), Point.xyr(0.0, 0.0, 1.0), Point.xyr(1.0, 0.0, 0.0)),
                KnotVector(3, Knot(3.0, 4), Knot(3.5, 4), Knot(4.0, 4)))
        b2.shouldBeBSpline(e2)
    }

    @Test
    fun testRemoveKnot() {
        println("RemoveKnot")
        val clamped = clamped.toCrisp()

        for (times in 0..4) {
            val c = clamped.insertKnot(3.25, times).removeKnot(3.25, times)
            c.shouldBeBSpline(clamped)
        }
        for (times in 0..3) {
            val c = clamped.insertKnot(3.5, times).removeKnot(3.5, times)
            c.shouldBeBSpline(clamped)
        }
        for (times in 0..4) {
            val c = clamped.insertKnot(3.75, times).removeKnot(3.75, times)
            c.shouldBeBSpline(clamped)
        }

        for (times in 0..2) {
            val u = uniform.insertKnot(3.0, times).removeKnot(3.0, times)
            u.shouldBeBSpline(uniform)
        }
        for (times in 0..2) {
            val u = uniform.insertKnot(4.0, times).removeKnot(4.0, times)
            u.shouldBeBSpline(uniform)
        }
    }

    @Test
    fun testToArcLengthCurve() {
        println("ToArcLengthCurve")
        val b = BSpline(Array.of(
                Point.xyr(0.0, 0.0, 0.0),
                Point.xyr(0.0, 600.0, 1.0),
                Point.xyr(300.0, 600.0, 2.0),
                Point.xyr(300.0, 0.0, 1.0),
                Point.xyr(600.0, 0.0, 0.0)),
                KnotVector.clamped(Interval(3.0, 4.0), 3, 9))
        val a = ArcLengthReparametrized(b, 1000).arcLength()
        b.reparametrizeArcLength().arcLength().shouldBeCloseTo(a, 0.1)
    }

    @Test
    fun testClamp() {
        println("Clamp")
        val c = clamped
        c.clamp().shouldBeBSpline(c)

        val u = BSpline(
                Array.of(
                        Point.xy(-1.0, 0.0),
                        Point.xy(-1.0, 1.0),
                        Point.xy(0.0, 1.0),
                        Point.xy(0.0, 0.0),
                        Point.xy(1.0, 0.0)),
                KnotVector.uniform(Interval(3.0, 4.0), 2, 8))
        val e = BSpline(
                Array.of(
                        Point.xy(-1.0, 0.5),
                        Point.xy(-1.0, 1.0),
                        Point.xy(0.0, 1.0),
                        Point.xy(0.0, 0.0),
                        Point.xy(0.5, 0.0)),
                KnotVector.clamped(Interval(3.0, 4.0), 2, 8))
        u.clamp().shouldBeBSpline(e)
    }

    @Test
    fun testClose() {
        println("Close")
        val ac = clamped
        val ec = BSpline(
                Array.of(Point.xyr(0.0, 0.0, 0.0), Point.xyr(-1.0, 1.0, 1.0), Point.xyr(0.0, 1.0, 2.0), Point.xyr(0.0, 0.0, 1.0), Point.xyr(0.0, 0.0, 0.0)),
                KnotVector.clamped(Interval(3.0, 4.0), 3, 9))
        ac.close().shouldBeBSpline(ec)

        val au = BSpline(
                Array.of(Point.xyr(-1.0, 0.0, 0.0), Point.xyr(-1.0, 1.0, 1.0), Point.xyr(0.0, 1.0, 2.0), Point.xyr(0.0, 0.0, 1.0), Point.xyr(1.0, 0.0, 0.0)),
                KnotVector.uniform(Interval(0.0, 2.0), 2, 8))
        val eu = BSpline(
                Array.of(Point.xyr(-0.25, 0.25, 0.5), Point.xyr(-1.0, 1.0, 1.0), Point.xyr(0.0, 1.0, 2.0), Point.xyr(0.0, 0.0, 1.0), Point.xyr(-0.25, 0.25, 0.5)),
                KnotVector.clamped(Interval(0.0, 2.0), 2, 8))
        au.close().shouldBeBSpline(eu)
    }

    @Test
    fun test_Basis() {
        println("Basis")
        val knots = KnotVector.clamped(Interval(0.0, 2.0), 2, 7)

        BSpline.basis(0.0, 0, knots).shouldBeCloseTo(1.0)
        BSpline.basis(0.0, 1, knots).shouldBeCloseTo(0.0)
        BSpline.basis(0.0, 2, knots).shouldBeCloseTo(0.0)
        BSpline.basis(0.0, 3, knots).shouldBeCloseTo(0.0)

        BSpline.basis(0.5, 0, knots).shouldBeCloseTo(0.25)
        BSpline.basis(0.5, 1, knots).shouldBeCloseTo(5 / 8.0)
        BSpline.basis(0.5, 2, knots).shouldBeCloseTo(0.125)
        BSpline.basis(0.5, 3, knots).shouldBeCloseTo(0.0)

        BSpline.basis(1.0, 0, knots).shouldBeCloseTo(0.0)
        BSpline.basis(1.0, 1, knots).shouldBeCloseTo(0.5)
        BSpline.basis(1.0, 2, knots).shouldBeCloseTo(0.5)
        BSpline.basis(1.0, 3, knots).shouldBeCloseTo(0.0)

        BSpline.basis(1.5, 0, knots).shouldBeCloseTo(0.0)
        BSpline.basis(1.5, 1, knots).shouldBeCloseTo(0.125)
        BSpline.basis(1.5, 2, knots).shouldBeCloseTo(5 / 8.0)
        BSpline.basis(1.5, 3, knots).shouldBeCloseTo(0.25)

        BSpline.basis(2.0, 0, knots).shouldBeCloseTo(0.0)
        BSpline.basis(2.0, 1, knots).shouldBeCloseTo(0.0)
        BSpline.basis(2.0, 2, knots).shouldBeCloseTo(0.0)
        BSpline.basis(2.0, 3, knots).shouldBeCloseTo(1.0)
    }
}