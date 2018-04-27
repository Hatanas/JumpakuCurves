package jumpaku.core.test.curve

import jumpaku.core.curve.Interval
import jumpaku.core.curve.Knot
import jumpaku.core.curve.KnotVector
import jumpaku.core.json.parseJson
import org.assertj.core.api.Assertions.*
import jumpaku.core.util.component1
import jumpaku.core.util.component2
import org.junit.Test

class KnotVectorTest {

    val k = KnotVector.clamped(Interval(3.5, 5.0), 3, 10)

    @Test
    fun testProperties() {
        println("Properties")
        assertThat(k.knots.size()).isEqualTo(4)
        assertThat(k.knots[0].value).isEqualTo(3.5, withPrecision(1.0e-10))
        assertThat(k.knots[1].value).isEqualTo(4.0, withPrecision(1.0e-10))
        assertThat(k.knots[2].value).isEqualTo(4.5, withPrecision(1.0e-10))
        assertThat(k.knots[3].value).isEqualTo(5.0, withPrecision(1.0e-10))
        assertThat(k.knots[0].multiplicity).isEqualTo(4)
        assertThat(k.knots[1].multiplicity).isEqualTo(1)
        assertThat(k.knots[2].multiplicity).isEqualTo(1)
        assertThat(k.knots[3].multiplicity).isEqualTo(4)

        assertThat(k.degree).isEqualTo(3)
        intervalAssertThat(k.domain).isEqualToInterval(Interval(3.5, 5.0))
    }

    @Test
    fun testToString() {
        println("ToString")
        val k = KnotVector.clamped(Interval(3.5, 5.0), 3, 10)
        knotVectorAssertThat(k.toString().parseJson().flatMap { KnotVector.fromJson(it) }.get()).isEqualToKnotVector(k)
    }

    @Test
    fun testLastIndexUnder() {
        println("LastIndexUnder")
        assertThat(k.lastExtractedIndexUnder(3.5)).isEqualTo(3)
        assertThat(k.lastExtractedIndexUnder(3.7)).isEqualTo(3)
        assertThat(k.lastExtractedIndexUnder(4.0)).isEqualTo(4)
        assertThat(k.lastExtractedIndexUnder(4.1)).isEqualTo(4)
        assertThat(k.lastExtractedIndexUnder(4.5)).isEqualTo(5)
        assertThat(k.lastExtractedIndexUnder(4.6)).isEqualTo(5)
        assertThat(k.lastExtractedIndexUnder(5.0)).isEqualTo(5)
    }

    @Test
    fun testReverse() {
        println("Reverse")
        val a = k.reverse()
        val e = KnotVector.clamped(Interval(3.5, 5.0), 3, 10)
        knotVectorAssertThat(a).isEqualToKnotVector(e)
    }

    @Test
    fun testDerivativeKnotVector() {
        println("DerivativeKnotVector")
        val a = k.derivativeKnotVector()
        val e = KnotVector.clamped(Interval(3.5, 5.0), 2, 8)
        knotVectorAssertThat(a).isEqualToKnotVector(e)
    }

    @Test
    fun testSubdivide() {
        println("Subdivide")
        val (a00, a01) = k.subdivide(3.5)
        assertThat(a00.isDefined).isFalse()
        knotVectorAssertThat(a01.get()).isEqualToKnotVector(KnotVector.clamped(Interval(3.5, 5.0), 3, 10))

        val (a10, a11) = k.subdivide(3.7)
        knotVectorAssertThat(a10.get()).isEqualToKnotVector(KnotVector.clamped(Interval(3.5, 3.7), 3, 8))
        knotVectorAssertThat(a11.get()).isEqualToKnotVector(KnotVector(3,
                Knot(3.7, 4), Knot(4.0), Knot(4.5), Knot(5.0, 4)))

        val (a20, a21) = k.subdivide(4.0)
        knotVectorAssertThat(a20.get()).isEqualToKnotVector(KnotVector.clamped(Interval(3.5, 4.0), 3, 8))
        knotVectorAssertThat(a21.get()).isEqualToKnotVector(KnotVector.clamped(Interval(4.0, 5.0), 3, 9))

        val (a30, a31) = k.subdivide(4.2)
        knotVectorAssertThat(a30.get()).isEqualToKnotVector(KnotVector(3,
                Knot(3.5, 4), Knot(4.0), Knot(4.2, 4)))
        knotVectorAssertThat(a31.get()).isEqualToKnotVector(KnotVector(3,
                Knot(4.2, 4), Knot(4.5), Knot(5.0, 4)))

        val (a40, a41) = k.subdivide(4.5)
        knotVectorAssertThat(a40.get()).isEqualToKnotVector(KnotVector.clamped(Interval(3.5, 4.5), 3, 9))
        knotVectorAssertThat(a41.get()).isEqualToKnotVector(KnotVector.clamped(Interval(4.5, 5.0), 3, 8))

        val (a50, a51) = k.subdivide(4.6)
        knotVectorAssertThat(a50.get()).isEqualToKnotVector(KnotVector(3,
                Knot(3.5, 4), Knot(4.0), Knot(4.5), Knot(4.6, 4)))
        knotVectorAssertThat(a51.get()).isEqualToKnotVector(KnotVector.clamped(Interval(4.6, 5.0), 3, 8))

        val (a60, a61) = k.subdivide(5.0)
        knotVectorAssertThat(a60.get()).isEqualToKnotVector(KnotVector.clamped(Interval(3.5, 5.0), 3, 10))
        assertThat(a61.isDefined).isFalse()
    }

    @Test
    fun testInsertKnot() {
        println("InsertKnot")

        val k = KnotVector(3,
                Knot(2.5), Knot(3.0), Knot(3.5), Knot(4.0), Knot(4.5), Knot(5.0, 2), Knot(5.5))

        knotVectorAssertThat(k.insert(3.5, 0)).isEqualToKnotVector(k)
        knotVectorAssertThat(k.insert(3.5, 1)).isEqualToKnotVector(KnotVector(3,
                Knot(2.5), Knot(3.0), Knot(3.5, 2), Knot(4.0), Knot(4.5), Knot(5.0, 2), Knot(5.5)))
        knotVectorAssertThat(k.insert(3.5, 2)).isEqualToKnotVector(KnotVector(3,
                Knot(2.5), Knot(3.0), Knot(3.5, 3), Knot(4.0), Knot(4.5), Knot(5.0, 2), Knot(5.5)))

        knotVectorAssertThat(k.insert(4.1, 0)).isEqualToKnotVector(k)
        knotVectorAssertThat(k.insert(4.1, 1)).isEqualToKnotVector(KnotVector(3,
                Knot(2.5), Knot(3.0), Knot(3.5), Knot(4.0), Knot(4.1), Knot(4.5), Knot(5.0, 2), Knot(5.5)))
        knotVectorAssertThat(k.insert(4.1, 2)).isEqualToKnotVector(KnotVector(3,
                Knot(2.5), Knot(3.0), Knot(3.5), Knot(4.0), Knot(4.1, 2), Knot(4.5), Knot(5.0, 2), Knot(5.5)))
        knotVectorAssertThat(k.insert(4.1, 3)).isEqualToKnotVector(KnotVector(3,
                Knot(2.5), Knot(3.0), Knot(3.5), Knot(4.0), Knot(4.1, 3), Knot(4.5), Knot(5.0, 2), Knot(5.5)))

        knotVectorAssertThat(k.insert(4.5, 0)).isEqualToKnotVector(k)
        knotVectorAssertThat(k.insert(4.5, 1)).isEqualToKnotVector(KnotVector(3,
                Knot(2.5), Knot(3.0), Knot(3.5), Knot(4.0), Knot(4.5, 2), Knot(5.0, 2), Knot(5.5)))
        knotVectorAssertThat(k.insert(4.5, 2)).isEqualToKnotVector(KnotVector(3,
                Knot(2.5), Knot(3.0), Knot(3.5), Knot(4.0), Knot(4.5, 3), Knot(5.0, 2), Knot(5.5)))

        knotVectorAssertThat(k.insert(5.0, 0)).isEqualToKnotVector(k)
        knotVectorAssertThat(k.insert(5.0, 1)).isEqualToKnotVector(KnotVector(3,
                Knot(2.5), Knot(3.0), Knot(3.5), Knot(4.0), Knot(4.5), Knot(5.0, 3), Knot(5.5)))
    }
}