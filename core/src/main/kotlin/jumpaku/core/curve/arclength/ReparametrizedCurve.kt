package jumpaku.core.curve.arclength

import jumpaku.core.curve.Curve
import jumpaku.core.curve.Interval
import jumpaku.core.fuzzy.Grade
import jumpaku.core.geom.Point

class ReparametrizedCurve(val originalCurve: Curve, val reparametrizer: Reparametrizer): Curve {

    override fun evaluate(t: Double): Point {
        require(t in domain) { "t($t) is out of domain($domain)"}
        return originalCurve(reparametrizer.toOriginal(t))
    }

    override val domain: Interval = reparametrizer.range

    override val reparameterized: ReparametrizedCurve by lazy { reparametrize(1.0) }

    fun isPossible(other: ReparametrizedCurve, n: Int): Grade =
            evaluateAll(n).zipWith(other.evaluateAll(n), Point::isPossible).reduce(Grade::and)

    fun isNecessary(other: ReparametrizedCurve, n: Int): Grade =
            evaluateAll(n).zipWith(other.evaluateAll(n), Point::isNecessary).reduce(Grade::and)
}