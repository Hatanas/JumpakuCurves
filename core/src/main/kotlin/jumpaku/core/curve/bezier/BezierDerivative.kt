package jumpaku.core.curve.bezier

import com.github.salomonbrys.kotson.array
import com.github.salomonbrys.kotson.get
import com.github.salomonbrys.kotson.jsonArray
import com.github.salomonbrys.kotson.jsonObject
import com.google.gson.JsonElement
import io.vavr.Tuple2
import jumpaku.core.curve.Derivative
import jumpaku.core.curve.Differentiable
import jumpaku.core.curve.Interval
import jumpaku.core.geom.Point
import jumpaku.core.geom.Vector
import jumpaku.core.json.ToJson
import jumpaku.core.util.Result
import jumpaku.core.util.result


class BezierDerivative(private val bezier: Bezier) : Derivative, Differentiable, ToJson {

    constructor(controlVectors: Iterable<Vector>): this(Bezier(controlVectors.map { Point(it) }))

    constructor(vararg controlVectors: Vector): this(controlVectors.asIterable())

    override val derivative: BezierDerivative get() = toBezier().derivative

    override val domain: Interval get() = toBezier().domain

    val controlVectors: List<Vector> get() = toBezier().controlPoints.map(Point::toVector)

    val degree: Int get() = toBezier().degree

    fun toBezier(): Bezier = Bezier(bezier.controlPoints.map { it.toCrisp() })

    override fun evaluate(t: Double): Vector = toBezier()(t).toVector()

    override fun differentiate(t: Double): Vector = toBezier().differentiate(t)

    override fun toString(): String = toJsonString()

    override fun toJson(): JsonElement =
            jsonObject("controlVectors" to jsonArray(controlVectors.map { it.toJson() }))

    fun restrict(i: Interval): BezierDerivative = BezierDerivative(toBezier().restrict(i))

    fun restrict(begin: Double, end: Double): BezierDerivative = BezierDerivative(toBezier().restrict(begin, end))

    fun reverse(): BezierDerivative = BezierDerivative(toBezier().reverse())

    fun elevate(): BezierDerivative = BezierDerivative(toBezier().elevate())

    fun reduce(): BezierDerivative = BezierDerivative(toBezier().reduce())

    fun subdivide(t: Double): Tuple2<BezierDerivative, BezierDerivative> = toBezier()
            .subdivide(t).map(::BezierDerivative, ::BezierDerivative)

    fun extend(t: Double): BezierDerivative = BezierDerivative(toBezier().extend(t))

    companion object {

        fun fromJson(json: JsonElement): Result<BezierDerivative> = result {
            BezierDerivative(json["controlVectors"].array.flatMap { Vector.fromJson(it).value() })
        }
    }
}
