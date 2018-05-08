package jumpaku.core.test.curve.bezier

import io.vavr.API
import io.vavr.collection.Array
import jumpaku.core.affine.Point
import jumpaku.core.affine.Vector
import jumpaku.core.affine.identity
import jumpaku.core.curve.bezier.Bezier
import jumpaku.core.json.parseJson
import jumpaku.core.test.affine.shouldBePoint
import jumpaku.core.test.affine.shouldBeVector
import jumpaku.core.test.shouldBeCloseTo
import org.amshove.kluent.shouldBe
import org.apache.commons.math3.util.FastMath
import org.junit.Test

class BezierTest {

    private val bc = Bezier(Point.xyr(-2.0, 0.0, 1.0), Point.xyr(-1.0, 0.0, 2.0), Point.xy(0.0, 2.0), Point.xyr(1.0, 0.0, 2.0), Point.xyr(2.0, 0.0, 1.0))

    @Test
    fun testProperties() {
        println("Properties")
        bc.controlPoints[0].shouldBePoint(Point.xyr(-2.0, 0.0, 1.0))
        bc.controlPoints[1].shouldBePoint(Point.xyr(-1.0, 0.0, 2.0))
        bc.controlPoints[2].shouldBePoint(Point.xyr( 0.0, 2.0, 0.0))
        bc.controlPoints[3].shouldBePoint(Point.xyr( 1.0, 0.0, 2.0))
        bc.controlPoints[4].shouldBePoint(Point.xyr( 2.0, 0.0, 1.0))
        bc.controlPoints.size().shouldBe(5)
        bc.degree.shouldBe(4)
        bc.domain.begin.shouldBeCloseTo(0.0)
        bc.domain.end.shouldBeCloseTo(1.0)
    }

    @Test
    fun testToString() {
        println("ToString")
        bc.toString().parseJson().flatMap { Bezier.fromJson(it) }.get().shouldBeBezier(bc)
    }

    @Test
    fun testEvaluate() {
        println("Evaluate")
        bc.evaluate(0.0).shouldBePoint(Point.xyr(-2.0, 0.0      , 1.0        ))
        bc.evaluate(0.25).shouldBePoint(Point.xyr(-1.0, 27 / 64.0, 161.0 / 128))
        bc.evaluate(0.5).shouldBePoint(Point.xyr( 0.0, 0.75     , 9.0 / 8    ))
        bc.evaluate(0.75).shouldBePoint(Point.xyr( 1.0, 27 / 64.0, 161.0 / 128))
        bc.evaluate(1.0).shouldBePoint(Point.xyr( 2.0, 0.0      , 1.0        ))
    }

    @Test
    fun testDifferentiate() {
        val d = bc.derivative
        d.toBezier().shouldBeBezier(Bezier(Point.xy(4.0, 0.0), Point.xy(4.0, 8.0), Point.xy(4.0, -8.0), Point.xy(4.0, 0.0)))
        bc.differentiate(0.0).shouldBeVector(Vector(4.0, 0.0))
        bc.differentiate(0.25).shouldBeVector(Vector(4.0, 2.25))
        bc.differentiate(0.5).shouldBeVector(Vector(4.0, 0.0))
        bc.differentiate(0.75).shouldBeVector(Vector(4.0, -2.25))
        bc.differentiate(1.0).shouldBeVector(Vector(4.0, 0.0))
        d.evaluate(0.0).shouldBeVector(Vector(4.0, 0.0))
        d.evaluate(0.25).shouldBeVector(Vector(4.0, 2.25))
        d.evaluate(0.5).shouldBeVector(Vector(4.0, 0.0))
        d.evaluate(0.75).shouldBeVector(Vector(4.0, -2.25))
        d.evaluate(1.0).shouldBeVector(Vector(4.0, 0.0))
    }

    @Test
    fun testCrispTransform() {
        print("CrispTransform")
        val a = bc.transform(identity.andScale(2.0).andRotate(Vector(0.0, 0.0, 1.0), FastMath.PI/2).andTranslate(Vector(1.0, 1.0, 0.0)))
        val e = Bezier(Point.xy(1.0, -3.0), Point.xy(1.0, -1.0), Point.xy(-3.0, 1.0), Point.xy(1.0, 3.0), Point.xy(1.0, 5.0))
        a.shouldBeBezier(e)
    }

    @Test
    fun testToCrisp() {
        println("ToCrisp")
        bc.toCrisp().shouldBeBezier(
                Bezier(Point.xy(-2.0, 0.0), Point.xy(-1.0, 0.0), Point.xy(0.0, 2.0), Point.xy(1.0, 0.0), Point.xy(2.0, 0.0)))
    }

    @Test
    fun testRestrict() {
        println("Restrict")
        bc.restrict(0.25, 0.5).shouldBeBezier(Bezier(
                Point.xyr(-1.0, 27 / 64.0, 161 / 128.0), Point.xyr(-3 / 4.0, 9 / 16.0, 39 / 32.0), Point.xyr(-1 / 2.0, 11 / 16.0, 37 / 32.0), Point.xyr(-1 / 4.0, 3 / 4.0, 9 / 8.0), Point.xyr(0.0, 3 / 4.0, 9 / 8.0)))
    }

    @Test
    fun testReverse() {
        println("Reverse")
        bc.reverse().shouldBeBezier(Bezier(
                Point.xyr(2.0, 0.0, 1.0), Point.xyr(1.0, 0.0, 2.0), Point.xy(0.0, 2.0), Point.xyr(-1.0, 0.0, 2.0), Point.xyr(-2.0, 0.0, 1.0)))
    }

    @Test
    fun testElevate() {
        println("Elevate")
        val instance = Bezier(Point.xr(-1.0, 0.0), Point.xr(0.0, 2.0), Point.xr(1.0, 0.0))
        val expected = Bezier(Point.xr(-1.0, 0.0), Point.xr(-1 / 3.0, 4 / 3.0), Point.xr(1 / 3.0, 4 / 3.0), Point.xr(1.0, 0.0))
        instance.elevate().shouldBeBezier(expected)
    }

    @Test
    fun testReduce() {
        println("Reduce")
        val b1 = Bezier(Point.xyr(-1.0, 2.0, 2.0), Point.xyr(1.0, 1.0, 1.0))
                .reduce()
        val e1 = Bezier(Point.xyr(0.0, 1.5, 1.5))
        b1.shouldBeBezier(e1)

        val b2 = Bezier(Point.xyr(-1.0, 0.0, 0.0), Point.xyr(0.0, 2.0, 2.0), Point.xyr(1.0, 0.0, 0.0))
                .reduce()
        val e2 = Bezier(Point.xyr(-1.0, 0.0, 0.0), Point.xyr(1.0, 0.0, 0.0))
        b2.shouldBeBezier(e2)

        val b3 = Bezier(Point.xyr(-1.0, 0.0, 0.0), Point.xyr(-1 / 3.0, 4 / 3.0, 4 / 3.0), Point.xyr(1 / 3.0, 4 / 3.0, 4 / 3.0), Point.xyr(1.0, 0.0, 0.0)).reduce()
        val e3 = Bezier(Point.xyr(-1.0, 0.0, 0.0), Point.xyr(0.0, 2.0, 2.0), Point.xyr(1.0, 0.0, 0.0))
        b3.shouldBeBezier(e3)

        val b4 = Bezier(Point.xyr(-1.0, 0.0, 0.0), Point.xyr(-0.5, 1.0, 1.0), Point.xyr(0.0, 4 / 3.0, 4 / 3.0), Point.xyr(0.5, 1.0, 1.0), Point.xyr(1.0, 0.0, 0.0))
                .reduce()
        val e4 = Bezier(Point.xyr(-1.0, 0.0, 0.0), Point.xyr(-1 / 3.0, 4 / 3.0, 4 / 3.0), Point.xyr(1 / 3.0, 4 / 3.0, 4 / 3.0), Point.xyr(1.0, 0.0, 0.0))
        b4.shouldBeBezier(e4)

        val b5 = Bezier(Point.xyr(-1.0, 0.0, 0.0), Point.xyr(-0.6, 0.8, 0.8), Point.xyr(-0.3, 1.2, 1.2), Point.xyr(0.3, 1.2, 1.2), Point.xyr(0.6, 0.8, 0.8), Point.xyr(1.0, 0.0, 0.0))
                .reduce()
        val e5 = Bezier(Point.xyr(-1.0, 0.0, 0.0), Point.xyr(-0.5, 1.0, 1.0), Point.xyr(0.0, 4 / 3.0, 8 / 3.0), Point.xyr(0.5, 1.0, 1.0), Point.xyr(1.0, 0.0, 0.0))
        b5.shouldBeBezier(e5)
    }

    @Test
    fun testSubdivide() {
        println("Subdivide")
        val bs = Bezier(
                Point.xyr(-2.0, 0.0, 1.0), Point.xyr(-1.0, 0.0, 2.0), Point.xy(0.0, 2.0), Point.xyr(1.0, 0.0, 2.0), Point.xyr(2.0, 0.0, 1.0))
                .subdivide(0.25)
        bs._1().shouldBeBezier(Bezier(
                Point.xyr(-2.0, 0.0, 1.0), Point.xyr(-7 / 4.0, 0.0, 5 / 4.0), Point.xyr(-3 / 2.0, 1 / 8.0, 21 / 16.0), Point.xyr(-5 / 4.0, 9 / 32.0, 83 / 64.0), Point.xyr(-1.0, 27 / 64.0, 161 / 128.0)))
        bs._2().shouldBeBezier(Bezier(
                Point.xyr(-1.0, 27 / 64.0, 322 / 256.0), Point.xyr(-1 / 4.0, 27 / 32.0, 73 / 64.0), Point.xyr(1 / 2.0, 9 / 8.0, 13 / 16.0), Point.xyr(5 / 4.0, 0.0, 7 / 4.0), Point.xyr(2.0, 0.0, 1.0)))
    }

    @Test
    fun testExtend() {
        println("Extend")
        val extendBack = Bezier(
                Point.xyr(-2.0, 0.0, 1.0), Point.xyr(-7 / 4.0, 0.0, 5 / 4.0), Point.xyr(-3 / 2.0, 1 / 8.0, 21 / 16.0), Point.xyr(-5 / 4.0, 9 / 32.0, 83 / 64.0), Point.xyr(-1.0, 27 / 64.0, 161 / 128.0))
                .extend(4.0)
        extendBack.shouldBeBezier(Bezier(
                Point.xyr(-2.0, 0.0, 1.0), Point.xyr(-1.0, 0.0, 8.0), Point.xyr(0.0, 2.0, 60.0), Point.xyr(1.0, 0.0, 434.0), Point.xyr(2.0, 0.0, 3073.0)))

        val extendFront = Bezier(
                Point.xyr(-1.0, 27 / 64.0, 322 / 256.0), Point.xyr(-1 / 4.0, 27 / 32.0, 73 / 64.0), Point.xyr(1 / 2.0, 9 / 8.0, 13 / 16.0), Point.xyr(5 / 4.0, 0.0, 7 / 4.0), Point.xyr(2.0, 0.0, 1.0))
                .extend(-1/3.0)
        extendFront.shouldBeBezier(Bezier(
                Point.xyr(-2.0, 0.0, 721 / 81.0), Point.xyr(-1.0, 0.0, 134 / 27.0), Point.xyr(0.0, 2.0, 28 / 9.0), Point.xyr(1.0, 0.0, 8 / 3.0), Point.xyr(2.0, 0.0, 1.0)))

    }

    @Test
    fun testToArcLengthCurve() {
        println("ToArcLengthCurve")
        val l = Bezier(Point.xy(0.0, 0.0), Point.xy(50.0, 0.0), Point.xy(100.0, 100.0)).reparametrizeArcLength().arcLength()
        l.shouldBeCloseTo(7394.71429/50, 0.1)
    }

    @Test
    fun testDecasteljau(){
        println("Decasteljau")
        val result = Bezier.decasteljau(0.25,
                Array.of(Point.xyzr(1.0, 0.0, -2.0, 1.0), Point.xyzr(0.0, 3.0, 4.0, 0.0), Point.xyzr(-1.0, -1.0, 0.0, 2.0)))
        result.size().shouldBe(result.size())
        result.get(0).shouldBePoint(Point.xyzr(0.75, 0.75, -0.5, 0.75))
        result.get(1).shouldBePoint(Point.xyzr(-0.25, 2.0, 3.0, 0.5))
    }

    @Test
    fun test_Basis(){
        print("Basis")
        Bezier.basis(2, 0, 0.0 ).shouldBeCloseTo(1.0)
        Bezier.basis(2, 1, 0.0 ).shouldBeCloseTo(0.0)
        Bezier.basis(2, 2, 0.0 ).shouldBeCloseTo(0.0)

        Bezier.basis(2, 0, 0.25).shouldBeCloseTo(9 / 16.0)
        Bezier.basis(2, 1, 0.25).shouldBeCloseTo(6 / 16.0)
        Bezier.basis(2, 2, 0.25).shouldBeCloseTo(1 / 16.0)

        Bezier.basis(2, 0, 0.5 ).shouldBeCloseTo(0.25)
        Bezier.basis(2, 1, 0.5 ).shouldBeCloseTo(0.5)
        Bezier.basis(2, 2, 0.5 ).shouldBeCloseTo(0.25)

        Bezier.basis(2, 0, 0.75).shouldBeCloseTo(1 / 16.0)
        Bezier.basis(2, 1, 0.75).shouldBeCloseTo(6 / 16.0)
        Bezier.basis(2, 2, 0.75).shouldBeCloseTo(9 / 16.0)

        Bezier.basis(2, 0, 1.0 ).shouldBeCloseTo(0.0)
        Bezier.basis(2, 1, 1.0 ).shouldBeCloseTo(0.0)
        Bezier.basis(2, 2, 1.0 ).shouldBeCloseTo(1.0)
    }
}