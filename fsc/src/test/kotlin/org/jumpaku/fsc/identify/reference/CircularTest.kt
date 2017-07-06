package org.jumpaku.fsc.identify.reference

import com.github.salomonbrys.kotson.fromJson
import org.apache.commons.math3.util.FastMath
import org.assertj.core.api.Assertions.*
import org.jumpaku.core.affine.Point
import org.jumpaku.core.affine.pointAssertThat
import org.jumpaku.core.curve.Interval
import org.jumpaku.core.curve.bspline.BSplineJson
import org.jumpaku.core.curve.intervalAssertThat
import org.jumpaku.core.curve.rationalbezier.ConicSection
import org.jumpaku.core.curve.rationalbezier.conicSectionAssertThat
import org.jumpaku.core.fuzzy.Grade
import org.jumpaku.core.json.prettyGson
import org.jumpaku.fsc.identify.reference.Circular
import org.jumpaku.fsc.identify.reference.CircularJson
import org.junit.Test
import java.io.FileReader
import java.nio.file.Paths


class CircularTest {

    val R2 = FastMath.sqrt(2.0)

    @Test
    fun testProperties() {
        println("Properties")
        val cs = ConicSection(Point.xyr(0.0, 1.0, 1.0), Point.xyr(R2/2, R2/2, 2.0), Point.xyr(1.0, 0.0, 3.0), R2/2)
        val c = Circular(cs, Interval(-0.5, 1.5))
        conicSectionAssertThat(c.conicSection).isEqualConicSection(cs)
        intervalAssertThat(c.domain).isEqualToInterval(Interval(-0.5, 1.5))
    }

    @Test
    fun testFuzzyCurve() {
        println("FuzzyCurve")
        val cs = ConicSection(Point.xyr(0.0, 1.0, 1.0), Point.xyr(R2/2, R2/2, 2.0), Point.xyr(1.0, 0.0, 3.0), R2/2)
        val c = Circular(cs, Interval(-0.5, 1.5))
        pointAssertThat(c.fuzzyCurve(-0.5 )).isEqualToPoint(Point.xyr(-1/R2, -1/R2, 14+8*R2))
        pointAssertThat(c.fuzzyCurve(-0.25)).isEqualToPoint(Point.xyr((1-3*R2)/(10-3*R2), (9-3*R2)/(10-3*R2), (48+6*R2)/(10-3*R2)))
        pointAssertThat(c.fuzzyCurve( 0.0 )).isEqualToPoint(Point.xyr(0.0, 1.0, 1.0))
        pointAssertThat(c.fuzzyCurve( 0.25)).isEqualToPoint(Point.xyr((3*R2+1)/(3*R2+10), (3*R2+9)/(3*R2+10), (24+6*R2)/(10+3*R2)))
        pointAssertThat(c.fuzzyCurve( 0.5 )).isEqualToPoint(Point.xyr(1/R2, 1/R2, 2.0))
        pointAssertThat(c.fuzzyCurve( 0.75)).isEqualToPoint(Point.xyr((3*R2+9)/(3*R2+10), (3*R2+1)/(3*R2+10), (32+6*R2)/(10+3*R2)))
        pointAssertThat(c.fuzzyCurve( 1.0 )).isEqualToPoint(Point.xyr(1.0, 0.0, 3.0))
        pointAssertThat(c.fuzzyCurve( 1.25)).isEqualToPoint(Point.xyr((9-3*R2)/(10-3*R2), (1-3*R2)/(10-3*R2), (56+6*R2)/(10-3*R2)))
        pointAssertThat(c.fuzzyCurve( 1.5 )).isEqualToPoint(Point.xyr(-1/R2, -1/R2, 14+8*R2))
    }

    @Test
    fun testToString() {
        println("ToString")
        val cs = ConicSection(Point.xyr(0.0, 1.0, 1.0), Point.xyr(R2/2, R2/2, 2.0), Point.xyr(1.0, 0.0, 3.0), R2/2)
        val c = Circular(cs, Interval(-0.5, 1.5))
        circularAssertThat(prettyGson.fromJson<CircularJson>(c.toString()).circular()).isEqualToCircular(c)
    }

    @Test
    fun testIsValidFor() {
        println("IsValidFor")
        println("IsValidFor")
        val path = Paths.get("./src/test/resources/org/jumpaku/fsc/fsci/reference/")
        for (i in 0..9){
            val fsc = FileReader(path.resolve("Fsc$i.json").toFile()).use { prettyGson.fromJson<BSplineJson>(it).bSpline() }
            val arcLength = fsc.toArcLengthCurve()
            val t0 = arcLength.toOriginalParam(arcLength.arcLength()/5)
            val t1 = arcLength.toOriginalParam(arcLength.arcLength()*3/5)
            val ca = Circular.create(t0, t1, fsc)
            val ce = FileReader(path.resolve("Circular$i.json").toFile()).use { prettyGson.fromJson<CircularJson>(it).circular() }
            circularAssertThat(ca).isEqualToCircular(ce, 10.0)

            val cpa = ca.isValidFor(fsc)
            val cpe = FileReader(path.resolve("CircularGrade$i.json").toFile()).use { prettyGson.fromJson<Grade>(it).value }
            assertThat(cpa.value).isEqualTo(cpe)
        }
    }
}