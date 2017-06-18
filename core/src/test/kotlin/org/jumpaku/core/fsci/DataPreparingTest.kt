package org.jumpaku.core.fsci

import io.vavr.API
import org.assertj.core.api.Assertions.assertThat
import org.jumpaku.core.affine.Point
import org.jumpaku.core.affine.TimeSeriesPoint
import org.jumpaku.core.affine.timeSeriesDataAssertThat
import org.jumpaku.core.curve.Interval
import org.jumpaku.core.curve.KnotVector
import org.jumpaku.core.curve.bspline.BSpline
import org.jumpaku.core.curve.bspline.bSplineAssertThat
import org.jumpaku.core.fitting.BSplineFitting
import org.junit.Test


class DataPreparingTest {



    @Test
    fun testPrepare() {
        println("Prepare")
        val knots = KnotVector.clampedUniform(2, 8)
        val b = BSpline(API.Array<Point>(
                Point.xy(-2.0, 0.0), Point.xy(-1.0, 0.0), Point.xy(0.0, 2.0), Point.xy(1.0, 0.0), Point.xy(2.0, 0.0)),
                knots)
        val data = Interval(0.5, 2.5).sample(100).map { TimeSeriesPoint(b(it), it) }
        val a = BSplineFitting(2, knots).fit(DataPreparing(0.1, 0.5, 0.5, 2).prepare(data))
        bSplineAssertThat(a).isEqualToBSpline(b, 0.2)

        val b2 = BSpline(API.Array<Point>(
                Point.xy(1.0, 3.0), Point.xy(2.0, 0.0), Point.xy(3.0, 5.0), Point.xy(4.0, 3.0), Point.xy(5.0, 3.0)),
                KnotVector.clampedUniform(2, 8))
        val data2 = Interval(0.2, 2.8).sample(50).map { TimeSeriesPoint(b2(it), it) }
        val a2 = BSplineFitting(2, KnotVector.clampedUniform(2, 8)).fit(DataPreparing(0.1, 0.2, 0.2, 2).prepare(data2))
        val e2 = BSpline(API.Array(
                Point.xy(1.1157219672319155, 2.7493678060976845),
                Point.xy(1.9591584061231399, 0.09817360222120309),
                Point.xy(3.010446626771964, 4.961079201399634),
                Point.xy(4.0078822134901674, 3.0246311832085775),
                Point.xy(4.953430481558565, 2.9928530991891427)),
                knots)

        bSplineAssertThat(a2).isEqualToBSpline(e2, 0.1)
    }

    @Test
    fun testFill() {
        println("Fill")
        val data = API.Array(
                TimeSeriesPoint(Point.xy(1.0, -2.0), 10.0),
                TimeSeriesPoint(Point.xy(1.5, -3.0), 15.0),
                TimeSeriesPoint(Point.xy(2.5, -5.0), 25.0))
        val a = DataPreparing.fill(data, 2.0)

        assertThat(a.size()).isEqualTo(9)
        timeSeriesDataAssertThat(a[0]).isEqualToTimeSeriesData(TimeSeriesPoint(Point.xy(1.0, -2.0), 10.0))
        timeSeriesDataAssertThat(a[1]).isEqualToTimeSeriesData(TimeSeriesPoint(Point.xy(1+0.5/3.0, -2-1/3.0), 10+5/3.0))
        timeSeriesDataAssertThat(a[2]).isEqualToTimeSeriesData(TimeSeriesPoint(Point.xy(1+1/3.0, -2-2/3.0), 10+10/3.0))
        timeSeriesDataAssertThat(a[3]).isEqualToTimeSeriesData(TimeSeriesPoint(Point.xy(1.5, -3.0), 15.0))
        timeSeriesDataAssertThat(a[4]).isEqualToTimeSeriesData(TimeSeriesPoint(Point.xy(1.7, -3.4), 17.0))
        timeSeriesDataAssertThat(a[5]).isEqualToTimeSeriesData(TimeSeriesPoint(Point.xy(1.9, -3.8), 19.0))
        timeSeriesDataAssertThat(a[6]).isEqualToTimeSeriesData(TimeSeriesPoint(Point.xy(2.1, -4.2), 21.0))
        timeSeriesDataAssertThat(a[7]).isEqualToTimeSeriesData(TimeSeriesPoint(Point.xy(2.3, -4.6), 23.0))
        timeSeriesDataAssertThat(a[8]).isEqualToTimeSeriesData(TimeSeriesPoint(Point.xy(2.5, -5.0), 25.0))
    }

    @Test
    fun testExtendFront() {
        println("ExtendFront")
        val knots = KnotVector.clampedUniform(2, 8)
        val b = BSpline(API.Array<Point>(Point.xy(-2.0, 0.0), Point.xy(-1.0, 0.0), Point.xy(0.0, 2.0), Point.xy(1.0, 0.0), Point.xy(2.0, 0.0)), knots)
        val data = Interval(0.5, 3.0).sample(100).map { TimeSeriesPoint(b(it), it) }
        val subdivided = BSplineFitting(2, knots).fit(DataPreparing.extendFront(data, 0.5)).subdivide(2.0)
        bSplineAssertThat(subdivided[0]).isEqualToBSpline(b.subdivide(2.0)[0], 0.2)
        bSplineAssertThat(subdivided[1]).isEqualToBSpline(b.subdivide(2.0)[1], 0.01)
    }

    @Test
    fun testExtendBack() {
        println("ExtendBack")
        val knots = KnotVector.clampedUniform(2, 8)
        val b = BSpline(API.Array<Point>(Point.xy(-2.0, 0.0), Point.xy(-1.0, 0.0), Point.xy(0.0, 2.0), Point.xy(1.0, 0.0), Point.xy(2.0, 0.0)), knots)
        val data = Interval(0.0, 2.5).sample(100).map { TimeSeriesPoint(b(it), it) }
        val subdivided = BSplineFitting(2, knots).fit(DataPreparing.extendBack(data, 0.5)).subdivide(1.0)
        bSplineAssertThat(subdivided[0]).isEqualToBSpline(b.subdivide(1.0)[0], 0.01)
        bSplineAssertThat(subdivided[1]).isEqualToBSpline(b.subdivide(1.0)[1], 0.2)
    }
}