package org.jumpaku.core.curve.bezier

import io.vavr.API.*
import io.vavr.Tuple2
import io.vavr.collection.Array
import io.vavr.collection.Stream
import org.apache.commons.math3.util.CombinatoricsUtils
import org.apache.commons.math3.util.FastMath
import org.jumpaku.core.affine.*
import org.jumpaku.core.curve.Transformable
import org.jumpaku.core.curve.Differentiable
import org.jumpaku.core.curve.FuzzyCurve
import org.jumpaku.core.curve.Interval
import org.jumpaku.core.json.prettyGson
import org.jumpaku.core.util.component1
import org.jumpaku.core.util.component2


class Bezier constructor(val controlPoints: Array<Point>) : FuzzyCurve, Differentiable, Transformable {

    override val domain: Interval get() = Interval.ZERO_ONE

    override val derivative: BezierDerivative get() {
        val cp = controlPoints.map(Point::toCrisp)
        val vs = cp.zipWith(cp.tail(), { pre, post -> (post - pre)*degree.toDouble() })
        return  BezierDerivative(vs)
    }

    val degree: Int get() = controlPoints.size() - 1

    constructor(controlPoints: Iterable<Point>): this(Array.ofAll(controlPoints))

    constructor(vararg controlPoints: Point): this(Array(*controlPoints))

    override fun toString(): String = prettyGson.toJson(json())

    fun json(): BezierJson = BezierJson(this)

    override fun evaluate(t: Double): Point {
        require(t in domain) { "t($t) is out of domain($domain)" }

        var cps = controlPoints
        while (cps.size() > 1) {
            cps = decasteljau(t, cps)
        }

        return cps.head()
    }

    override fun differentiate(t: Double): Vector = derivative(t)

    override fun transform(a: Transform): Bezier = Bezier(controlPoints.map(a))

    fun restrict(i: Interval): Bezier = restrict(i.begin, i.end)

    fun restrict(begin: Double, end: Double): Bezier {
        require(Interval(begin, end) in domain) { "Interval([begin($begin), end($end)]) is out of domain($domain)" }

        return subdivide(end)._1().subdivide(begin / end)._2()
    }

    fun reverse(): Bezier = Bezier(controlPoints.reverse())

    fun elevate(): Bezier = Bezier(createElevatedControlPoints(controlPoints))

    fun reduce(): Bezier {
        require(degree >= 1) { "degree($degree) is too small" }

        return Bezier(createReducedControlPoints(controlPoints))
    }

    fun subdivide(t: Double): Tuple2<Bezier, Bezier> {
        require(t in domain) { "t($t) is out of domain($domain)" }

        return createSubdividedControlPoints(t, controlPoints).map(::Bezier, ::Bezier)
    }

    fun extend(t: Double): Bezier {
        require(t <= domain.begin || domain.end <= t) { "t($t) is in domain($domain)" }
        val controlPoints = createSubdividedControlPoints(t, controlPoints)
        return if(t <= domain.begin) {
            Bezier(controlPoints._2())
        }
        else {
            Bezier(controlPoints._1())
        }
    }

    companion object {

        fun basis(degree: Int, i: Int, t: Double): Double {
            val comb = CombinatoricsUtils::binomialCoefficientDouble
            return comb(degree, i) * FastMath.pow(t, i)*FastMath.pow(1 - t, degree - i)
        }

        fun <P : Divisible<P>> decasteljau(t: Double, cps: Array<P>): Array<P> {
            return cps.zipWith(cps.tail()) { p0, p1 -> p0.divide(t, p1) }
        }

        internal fun <P : Divisible<P>> createElevatedControlPoints(cp: Array<P>): Array<P> {
            val n = cp.size() - 1

            return Stream.rangeClosed(0, n + 1)
                    .map {
                        when(it) {
                            0 -> cp.head()
                            n + 1 -> cp.last()
                            else -> cp[it].divide(it / (n + 1).toDouble(), cp[it - 1])
                        }
                    }
                    .toArray()
        }

        internal fun <P : Divisible<P>> createSubdividedControlPoints(t: Double, cp: Array<P>): Tuple2<Array<P>, Array<P>> {
            var tmp = cp
            var first = List(tmp.head())
            var second = List(tmp.last())

            while (tmp.size() > 1) {
                tmp = decasteljau(t, tmp)
                first = first.prepend(tmp.head())
                second = second.prepend(tmp.last())
            }

            return Tuple(first.reverse().toArray(), second.toArray())
        }

        internal fun <P : Divisible<P>> createReducedControlPoints(cp: Array<P>): Array<P>  {
            val m = cp.size()
            val n = m - 1

            if (m == 2) {
                return Array.of(cp[0].divide(0.5, cp[1]))
            } else if(m % 2 != 0){
                val r = (m - 3) / 2

                return Stream.concat(
                        Stream.iterate(Tuple(cp.head(), 1),
                                { (qi, i) -> Tuple(cp[i].divide(i / (i - n).toDouble(), qi), i + 1) })
                                .take(r + 1),
                        Stream.iterate(Tuple(cp.last(), n - 2),
                                { (qi, i) -> Tuple(cp[i+1].divide((i + 1 - n)/(i + 1.0), qi), i - 1) })
                                .take(r + 1)
                                .reverse())
                        .map { it._1() }
                        .toArray()
            }
            else  {
                val r = (m - 2) / 2

                val first = Stream.iterate(Tuple(cp.head(), 1),
                        { (qi, i) -> Tuple(cp[i].divide(i / (i - n).toDouble(), qi), i + 1) })
                        .take(r)
                        .map { it._1() }

                val second = Stream.iterate(Tuple(cp.last(), n - 2),
                        { (qi, i) -> Tuple(cp[i+1].divide((i + 1 - n)/(i + 1.0), qi), i - 1) })
                        .take(r)
                        .map { it._1() }
                        .reverse()

                val pl = cp[r].divide(r / (r - n).toDouble(), first.last())
                val pr = cp[r + 1].divide((r + 1 - n) / (r + 1.0), second.head())

                return Stream.concat(first, Stream(pl.divide(0.5, pr)), second).toArray()
            }
        }
    }
}

data class BezierJson(private val controlPoints: List<PointJson>) {

    constructor(bezier: Bezier) : this(bezier.controlPoints.map(Point::json).toJavaList())

    fun bezier() = Bezier(controlPoints.map(PointJson::point))
}