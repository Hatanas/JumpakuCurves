package jumpaku.fsc.identify.reference

import io.vavr.API
import io.vavr.Tuple2
import io.vavr.Tuple3
import jumpaku.core.curve.Curve
import jumpaku.core.curve.Interval
import jumpaku.core.curve.arclength.ReparametrizedCurve
import jumpaku.core.curve.rationalbezier.ConicSection
import jumpaku.core.geom.*
import jumpaku.core.util.*
import org.apache.commons.math3.analysis.solvers.BrentSolver


class EllipticGenerator(val nSamples: Int = 25) : ReferenceGenerator {

    override fun <C: Curve> generate(fsc: ReparametrizedCurve<C>, t0: Double, t1: Double): Reference {
        val s = fsc.originalCurve
        val tf = computeEllipticFar(s, t0, t1, nSamples)
        val w = computeEllipticWeight(s, t0, t1, tf, s.domain, nSamples)
        val base = reparametrize(ConicSection(s(t0), s(tf), s(t1), w))
        val complement = reparametrize(base.originalCurve.complement())
        val domain = fsc.reparametrizer.run {
            ReferenceGenerator.ellipticDomain(toArcLengthRatio(t0), toArcLengthRatio(t1), base, complement)
        }
        return Reference(base.originalCurve, domain)
    }

    fun <C: Curve> generateScattered(fsc: ReparametrizedCurve<C>): Reference {
        val (t0, _, t1) = scatteredEllipticParams(fsc.originalCurve, nSamples)
        return generate(fsc, t0, t1)
    }

    fun <C: Curve> generateBeginEnd(fsc: ReparametrizedCurve<C>): Reference {
        val s = fsc.originalCurve
        val (t0, t1) = s.domain
        val tf = computeEllipticFar(s, t0, t1, nSamples)
        val w = computeEllipticWeight(s, t0, t1, tf, s.domain, nSamples)
        val base = ConicSection(s(t0), s(tf), s(t1), w)
        return Reference(base, Interval.ZERO_ONE)
    }

    companion object {
        /**
         * Computes parameters which maximizes triangle area of (fsc(t0), fsc(far), fsc(t1)).
         */
        fun scatteredEllipticParams(fsc: Curve, nSamples: Int): Tuple3<Double, Double, Double> {
            val ts = fsc.domain.sample(nSamples)
            return API.For(ts.take(nSamples / 3), ts.drop(2 * nSamples / 3))
                    .`yield` { t0, t1 ->
                        val tf = computeEllipticFar(fsc, t0, t1, nSamples)
                        API.Tuple(API.Tuple(t0, tf, t1), fsc(tf).area(fsc(t0), fsc(t1)))
                    }
                    .maxBy { (_, area) -> area }
                    .map { it._1() }.get()
        }

        /**
         * Computes a far point which bisects triangle area.
         * Far point on the fsc is a point such that line segment(f, m) bisects an area surrounded by an elliptic arc(fsc(t0), fsc(t1)) and a line segment(fsc(t0), fsc(t1)),
         * where f is far point, m is the middle point between fsc(t0) and fsc(t1).
         */
        fun computeEllipticFar(fsc: Curve, t0: Double, t1: Double, nSamples: Int): Double {
            val middle = fsc(t0).middle(fsc(t1))
            val ts = Interval(t0, t1).sample(nSamples)
            val ps = ts.map(fsc)
            val areas = ps.zipWithNext(middle::area).asVavr().scanLeft(0.0, Double::plus)
            val index = areas.lastIndexWhere { it < areas.last() / 2 }

            val t = BrentSolver(1.0e-6).solve(50, {
                val m = fsc(ts[index].divide(it, ts[index + 1]))
                val l = areas[index] + middle.area(ps[index], m)
                val r = areas.last() - areas[index + 1] + middle.area(ps[index + 1], m)
                l - r
            }, 0.0, 1.0)
            return ts[index].divide(t, ts[index + 1])
        }

        fun computeEllipticWeight(
                fsc: Curve, t0: Double, t1: Double, tf: Double, rangeSamples: Interval, nSamples: Int): Double {
            val begin = fsc(t0)
            val end = fsc(t1)
            val far = fsc(tf)

            val xy_xx = API.For(plane(begin, far, end).value(), line(begin, end).value(), rangeSamples.sample(nSamples))
                    .`yield` { plane: Plane, line: Line, tp: Double ->
                        val p = fsc(tp).projectTo(plane)
                        val a = far.projectTo(line(p, p + (end - begin)).orThrow())
                        val b = far.projectTo(line)
                        val t = (a - far).dot(b - far).tryDiv(b.distSquare(far)).value().orDefault(0.0)
                        val x = far.divide(t, begin.middle(end))
                        val dd = x.distSquare(p)
                        val ll = begin.distSquare(end) / 4
                        val yi = dd + t * t * ll - 2 * t * ll
                        val xi = ll * t * t - dd
                        val wi = 1.0//FastMath.exp(-fsc(tp).r)

                        Tuple2(wi * yi * xi, wi * xi * xi)
                    }.toArray()
            if (xy_xx.isEmpty) return 0.999

            return xy_xx.unzip { it }.let { (xy, xx) ->
                xy.sum().toDouble().divOrDefault(xx.sum().toDouble()) { 0.999 }
                        .coerceIn(-0.999, 0.999)
            }
        }
    }
}