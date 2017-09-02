package jumpaku.fsc.identify.reference

import com.github.salomonbrys.kotson.fromJson
import org.apache.commons.math3.util.FastMath
import jumpaku.core.affine.Point
import jumpaku.core.affine.pointAssertThat
import jumpaku.core.curve.Interval
import jumpaku.core.curve.intervalAssertThat
import jumpaku.core.curve.rationalbezier.ConicSection
import jumpaku.core.curve.rationalbezier.conicSectionAssertThat
import jumpaku.core.json.prettyGson
import org.junit.Test


class EllipticTest {

    val R2 = FastMath.sqrt(2.0)

    @Test
    fun testProperties() {
        println("Properties")
        val cs = ConicSection(Point.xyr(0.0, 1.0, 1.0), Point.xyr(R2, R2 / 2, 2.0), Point.xyr(2.0, 0.0, 3.0), R2 / 2)
        val e = Elliptic(cs, Interval(-0.5, 1.5))
        conicSectionAssertThat(e.conicSection).isEqualConicSection(cs)
        intervalAssertThat(e.domain).isEqualToInterval(Interval(-0.5, 1.5))
    }

    @Test
    fun testFuzzyCurve() {
        println("FuzzyCurve")
        val cs = ConicSection(Point.xyr(0.0, 1.0, 1.0), Point.xyr(R2, R2 / 2, 2.0), Point.xyr(2.0, 0.0, 3.0), R2 / 2)
        val e = Elliptic(cs, Interval(-0.5, 1.5))
        pointAssertThat(e.reference(-0.5)).isEqualToPoint(Point.xyr(-2*1/R2, -1/R2, 14+8*R2))
        pointAssertThat(e.reference(-0.25)).isEqualToPoint(Point.xyr(2*(1-3*R2)/(10-3*R2), (9-3*R2)/(10-3*R2), (48+6*R2)/(10-3*R2)))
        pointAssertThat(e.reference(0.0)).isEqualToPoint(Point.xyr(2*0.0, 1.0, 1.0))
        pointAssertThat(e.reference(0.25)).isEqualToPoint(Point.xyr(2*(3*R2+1)/(3*R2+10), (3*R2+9)/(3*R2+10), (24+6*R2)/(10+3*R2)))
        pointAssertThat(e.reference(0.5)).isEqualToPoint(Point.xyr(2*1/R2, 1/R2, 2.0))
        pointAssertThat(e.reference(0.75)).isEqualToPoint(Point.xyr(2*(3*R2+9)/(3*R2+10), (3*R2+1)/(3*R2+10), (32+6*R2)/(10+3*R2)))
        pointAssertThat(e.reference(1.0)).isEqualToPoint(Point.xyr(2*1.0, 0.0, 3.0))
        pointAssertThat(e.reference(1.25)).isEqualToPoint(Point.xyr(2*(9-3*R2)/(10-3*R2), (1-3*R2)/(10-3*R2), (56+6*R2)/(10-3*R2)))
        pointAssertThat(e.reference(1.5)).isEqualToPoint(Point.xyr(-2*1/R2, -1/R2, 14+8*R2))
    }

    @Test
    fun testToString() {
        println("ToString")
        val cs = ConicSection(Point.xyr(0.0, 1.0, 1.0), Point.xyr(R2, R2 / 2, 2.0), Point.xyr(2.0, 0.0, 3.0), R2 / 2)
        val e = Elliptic(cs, Interval(-0.5, 1.5))
        ellipticAssertThat(prettyGson.fromJson<EllipticJson>(e.toString()).elliptic()).isEqualToElliptic(e)
    }
}