package org.jumpaku.fitting

import io.vavr.API
import org.jumpaku.affine.Point
import org.jumpaku.curve.Knot
import org.jumpaku.curve.bspline.BSpline
import org.jumpaku.curve.bspline.bSplineAssertThat
import org.junit.Test


class BSplineFittingTest {

    @Test
    fun testFit() {
        println("Fit")
        val b = BSpline(
                API.Array(Point.xy(-1.0, 0.0), Point.xy(-1.0, 1.0), Point.xy(0.0, 1.0), Point.xy(0.0, 0.0), Point.xy(1.0, 0.0)),
                Knot.clampedUniformKnots(3, 9))
        val data = b.domain.sample(10).map { TimeSeriesPoint(b(it), it) }
        val f = BSplineFitting(b.degree, b.knots).fit(data)
        bSplineAssertThat(f).isEqualToBSpline(b)
    }

}