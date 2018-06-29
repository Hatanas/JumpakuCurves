package jumpaku.core.curve

import io.vavr.collection.Array
import io.vavr.collection.Stream
import jumpaku.core.curve.arclength.ReparametrizedCurve
import jumpaku.core.curve.arclength.Reparametrizer
import jumpaku.core.curve.arclength.repeatBisect
import jumpaku.core.fuzzy.Grade
import jumpaku.core.geom.Point
import jumpaku.core.geom.line
import jumpaku.core.util.component1
import jumpaku.core.util.component2
import jumpaku.core.util.component3

interface Curve : (Double)->Point {

    val domain: Interval

    /**
     * @param t
     * @return
     * @throws IllegalArgumentException t !in domain
     */
    fun evaluate(t: Double): Point

    override operator fun invoke(t: Double): Point {
        require(t in domain) { "t($t) is out of domain($domain)" }
        return evaluate(t)
    }

    fun evaluateAll(n: Int): Array<Point> = domain.sample(n).map(this::evaluate)

    fun evaluateAll(delta: Double): Array<Point> = domain.sample(delta).map(this::evaluate)

    fun sample(n: Int): Array<ParamPoint> = domain.sample(n).map { ParamPoint(this(it), it) }

    fun sample(delta: Double): Array<ParamPoint> = domain.sample(delta).map { ParamPoint(this(it), it) }


    fun toCrisp(): Curve = object : Curve {
        override val domain: Interval = this@Curve.domain
        override fun evaluate(t: Double): Point = this@Curve.evaluate(t).toCrisp()
    }
}