package org.jumpaku.core.curve.polyline

import com.github.salomonbrys.kotson.fromJson
import org.apache.commons.math3.util.FastMath
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.*
import org.jumpaku.core.affine.Point
import org.jumpaku.core.affine.Affine
import org.jumpaku.core.affine.Vector
import org.jumpaku.core.affine.pointAssertThat
import org.jumpaku.core.curve.Interval
import org.jumpaku.core.json.prettyGson
import org.junit.Test


class PolylineTest {
    @Test
    fun testProperties() {
        println("Properties")
        val p = Polyline(Point.xyr(-1.0, 1.0, 2.0), Point.xyr(1.0, 1.0, 1.0), Point.xyr(1.0, -3.0, 3.0), Point.xyzr(1.0, -3.0, 1.5, 2.0))
        pointAssertThat(p.points[0]).isEqualToPoint(Point.xyr(-1.0, 1.0, 2.0))
        pointAssertThat(p.points[1]).isEqualToPoint(Point.xyr( 1.0, 1.0, 1.0))
        pointAssertThat(p.points[2]).isEqualToPoint(Point.xyr( 1.0,-3.0, 3.0))
        pointAssertThat(p.points[3]).isEqualToPoint(Point.xyzr( 1.0,-3.0, 1.5, 2.0))
        assertThat(p.domain.begin).isCloseTo(0.0, withPrecision(1.0e-10))
        assertThat(p.domain.end)  .isCloseTo(7.5, withPrecision(1.0e-10))
    }

    @Test
    fun testToString() {
        println("ToString")
        val p = Polyline(Point.xyr(-1.0, 1.0, 2.0), Point.xyr(1.0, 1.0, 1.0), Point.xyr(1.0, -3.0, 3.0), Point.xyzr(1.0, -3.0, 1.5, 2.0))
        polylineAssertThat(prettyGson.fromJson<PolylineJson>(p.toString()).polyline()).isEqualToPolyline(p)
    }

    @Test
    fun testEvaluate() {
        println("Evaluate")
        val p = Polyline(Point.xyr(1.0, 1.0, 2.0), Point.xyr( -1.0, -1.0, 1.0), Point.xyzr(-1.0, -1.0, 1.0, 0.0))
        pointAssertThat(p.evaluate(0.0))                     .isEqualToPoint(Point.xyzr( 1.0, 1.0, 0.0, 2.0))
        pointAssertThat(p.evaluate(Math.sqrt(2.0)))          .isEqualToPoint(Point.xyzr( 0.0, 0.0, 0.0, 1.5))
        pointAssertThat(p.evaluate(2 * Math.sqrt(2.0)))      .isEqualToPoint(Point.xyzr(-1.0,-1.0, 0.0, 1.0))
        pointAssertThat(p.evaluate(2 * Math.sqrt(2.0) + 0.5)).isEqualToPoint(Point.xyzr(-1.0,-1.0, 0.5, 0.5))
        pointAssertThat(p.evaluate(2 * Math.sqrt(2.0) + 1))  .isEqualToPoint(Point.xyzr(-1.0,-1.0, 1.0, 0.0))
    }

    @Test
    fun testEvaluateAll() {
        println("EvaluateAll")
        val ps = Polyline(Point.xyr(-1.0, 1.0, 2.0), Point.xyr(1.0, 1.0, 1.0), Point.xyr(1.0, -3.0, 3.0), Point.xyzr(1.0, -3.0, 1.5, 2.0))
                .evaluateAll(6)
        assertThat(ps.size()).isEqualTo(6)
        pointAssertThat(ps[0]).isEqualToPoint(Point.xyzr(-1.0, 1.0, 0.0, 2.0 ))
        pointAssertThat(ps[1]).isEqualToPoint(Point.xyzr( 0.5, 1.0, 0.0, 1.25))
        pointAssertThat(ps[2]).isEqualToPoint(Point.xyzr( 1.0, 0.0, 0.0, 1.5 ))
        pointAssertThat(ps[3]).isEqualToPoint(Point.xyzr( 1.0,-1.5, 0.0, 2.25))
        pointAssertThat(ps[4]).isEqualToPoint(Point.xyzr( 1.0,-3.0, 0.0, 3.0 ))
        pointAssertThat(ps[5]).isEqualToPoint(Point.xyzr( 1.0,-3.0, 1.5, 2.0 ))
    }

    @Test
    fun testCrispTransform() {
        println("CrispTransform")
        val b = Polyline(Point.xyr(-1.0, 1.0, 2.0), Point.xyr(1.0, 1.0, 1.0), Point.xyr(1.0, -3.0, 3.0), Point.xyzr(1.0, -3.0, 1.5, 2.0))
        val a = b.transform(Affine.ID.scale(2.0).rotate(Vector(0.0, 0.0, 1.0), FastMath.PI/2).translate(Vector(1.0, 1.0)))
        val e = Polyline(Point.xy(-1.0, -1.0), Point.xy(-1.0, 3.0), Point.xy(7.0, 3.0), Point.xyz(7.0, 3.0, 3.0))
        polylineAssertThat(a).isEqualToPolyline(e)
    }

    @Test
    fun testReverse() {
        println("Reverse")
        val p = Polyline(Point.xyr(-1.0, 1.0, 2.0), Point.xyr(1.0, 1.0, 1.0), Point.xyr(1.0, -3.0, 3.0), Point.xyzr(1.0, -3.0, 1.5, 2.0))
                .reverse()
        polylineAssertThat(p).isEqualToPolyline(
                Polyline(Point.xyzr(1.0, -3.0, 1.5, 2.0), Point.xyr(1.0, -3.0, 3.0), Point.xyr(1.0, 1.0, 1.0), Point.xyr(-1.0, 1.0, 2.0)))
    }

    @Test
    fun testRestrict() {
        println("Restrict")
        val r0 = Polyline(Point.xyr(-1.0, 1.0, 2.0), Point.xyr(1.0, 1.0, 1.0), Point.xyr(1.0, -3.0, 3.0), Point.xyzr(1.0, -3.0, 1.5, 2.0))
                .restrict(3.0, 4.5)
        polylineAssertThat(r0).isEqualToPolyline(Polyline(Point.xyr(1.0, 0.0, 1.5), Point.xyr(1.0,-1.5, 2.25)))

        val r1 = Polyline(Point.xyr(-1.0, 1.0, 2.0), Point.xyr(1.0, 1.0, 1.0), Point.xyr(1.0, -3.0, 3.0), Point.xyzr(1.0, -3.0, 1.5, 2.0))
                .restrict(Interval(3.0, 4.5))
        polylineAssertThat(r1).isEqualToPolyline(Polyline(Point.xyr(1.0, 0.0, 1.5), Point.xyr(1.0,-1.5, 2.25)))
    }

    @Test
    fun testSubdivide() {
        println("Subdivide")
        val ps = Polyline(Point.xyr(-1.0, 1.0, 2.0), Point.xyr(1.0, 1.0, 1.0), Point.xyr(1.0, -3.0, 3.0), Point.xyzr(1.0, -3.0, 1.5, 2.0))
                .subdivide(4.5)
        polylineAssertThat(ps._1()).isEqualToPolyline(Polyline(Point.xyr(-1.0, 1.0, 2.0 ), Point.xyr(1.0,  1.0, 1.0), Point.xyr( 1.0,-1.5, 2.25)))
        polylineAssertThat(ps._2()).isEqualToPolyline(Polyline(Point.xyr( 1.0,-1.5, 2.25), Point.xyr(1.0, -3.0, 3.0), Point.xyzr(1.0, -3.0, 1.5, 2.0)))
    }

    @Test
    fun testToArcLengthCurve() {
        println("ToArcLengthCurve")
        Assertions.fail("ToArcLengthCurve")

    }
}