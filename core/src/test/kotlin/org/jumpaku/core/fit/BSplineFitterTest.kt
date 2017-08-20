package org.jumpaku.core.fit

import io.vavr.API
import org.jumpaku.core.affine.Point
import org.jumpaku.core.curve.Interval
import org.jumpaku.core.curve.KnotVector
import org.jumpaku.core.curve.ParamPoint
import org.jumpaku.core.curve.bspline.BSpline
import org.jumpaku.core.curve.bspline.bSplineAssertThat
import org.junit.Test


class BSplineFitterTest {

    @Test
    fun testFit() {
        println("Fit")
        val b = BSpline(
                API.Array(Point.xy(-1.0, 0.0), Point.xy(-1.0, 1.0), Point.xy(0.0, 1.0), Point.xy(0.0, 0.0), Point.xy(1.0, 0.0)),
                KnotVector.clampedUniform(Interval(1.0, 1.7), 3, 9))
        val data = b.domain.sample(10).map { ParamPoint(b(it), it) }
        val f = BSplineFitter(b.degree, b.knotVector).fit(data)
        bSplineAssertThat(f).isEqualToBSpline(b)
    }

}