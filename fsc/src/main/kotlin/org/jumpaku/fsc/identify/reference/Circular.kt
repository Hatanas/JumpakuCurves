package org.jumpaku.fsc.identify.reference

import io.vavr.API
import io.vavr.Tuple3
import org.apache.commons.math3.analysis.solvers.BrentSolver
import org.jumpaku.core.affine.Point
import org.jumpaku.core.curve.FuzzyCurve
import org.jumpaku.core.curve.Interval
import org.jumpaku.core.curve.IntervalJson
import org.jumpaku.core.curve.bspline.BSpline
import org.jumpaku.core.curve.rationalbezier.ConicSection
import org.jumpaku.core.curve.rationalbezier.ConicSectionJson
import org.jumpaku.core.fuzzy.Grade
import org.jumpaku.core.json.prettyGson
import org.jumpaku.core.util.component1
import org.jumpaku.core.util.component2
import org.jumpaku.core.util.component3


class Circular(val conicSection: ConicSection, val domain: Interval) : Reference {

    val reference: FuzzyCurve = object : FuzzyCurve {

        override val domain: Interval = this@Circular.domain

        override fun evaluate(t: Double): Point {
            require(t in domain) { "t($t) is out of domain($domain)" }
            return evaluateWithoutDomain(t, conicSection)
        }
    }

    override fun isValidFor(fsc: BSpline): Grade = reference.isPossible(fsc)


    override fun toString(): String = prettyGson.toJson(json())

    fun json(): CircularJson = CircularJson(this)

    companion object {

        private fun circularFar(t0: Double, t1: Double, fsc: FuzzyCurve): Double {
            val begin = fsc(t0)
            val end = fsc(t1)
            val relative = 1.0e-8
            val absolute = 1.0e-5
            return BrentSolver(relative, absolute).solve(50, {
                val f = fsc(it)
                f.distSquare(begin) - f.distSquare(end)
            }, t0, t1)
        }

        fun triangleAreaMaximizingParams(fsc: BSpline, nSamples: Int = 99): Tuple3<Double, Double, Double> {
            val ts = fsc.domain.sample(nSamples)
            return API.For(ts.take(nSamples/3), ts.drop(2*nSamples/3))
                    .yield({ t0, t1 ->
                        val tf = circularFar(t0, t1, fsc)
                        API.Tuple(API.Tuple(t0, tf, t1), fsc(tf).area(fsc(t0), fsc(t1)))
                    })
                    .maxBy { (_, area) -> area }
                    .map { it._1() }.get()
        }

        fun ofParams(t0: Double, t1: Double, fsc: FuzzyCurve): Circular {
            val tf = circularFar(t0, t1, fsc)
            val circular = ConicSection.shearedCircularArc(fsc(t0), fsc(tf), fsc(t1))
            val domain = createDomain(t0, t1, fsc.toArcLengthCurve(), circular)

            return Circular(circular, domain)
        }

        fun ofBeginEnd(fsc: BSpline): Circular {
            return ofParams(fsc.domain.begin, fsc.domain.end, fsc)
        }

        fun of(fsc: BSpline): Circular {
            val (t0, _, t1) = triangleAreaMaximizingParams(fsc)

            return ofParams(t0, t1, fsc)
        }
    }
}


data class CircularJson(val conicSection: ConicSectionJson, val domain: IntervalJson){

    constructor(circular: Circular) : this(circular.conicSection.json(), circular.domain.json())

    fun circular(): Circular = Circular(conicSection.conicSection(), domain.interval())
}