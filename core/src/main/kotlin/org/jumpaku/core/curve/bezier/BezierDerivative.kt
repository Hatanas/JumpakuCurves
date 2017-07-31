package org.jumpaku.core.curve.bezier

import io.vavr.Tuple2
import io.vavr.collection.Array
import org.jumpaku.core.affine.Point
import org.jumpaku.core.affine.Vector
import org.jumpaku.core.curve.Derivative
import org.jumpaku.core.curve.Differentiable
import org.jumpaku.core.curve.Interval
import org.jumpaku.core.json.prettyGson


class BezierDerivative(val asBezier: Bezier) : Derivative, Differentiable {

    override val derivative: BezierDerivative get() = asBezier.derivative

    override val domain: Interval get() = asBezier.domain

    val controlVectors: Array<Vector> get() = asBezier.controlPoints.map(Point::vector)

    val degree: Int get() = asBezier.degree

    constructor(controlVectors: Array<Vector>): this(Bezier(controlVectors.map { Point(it) }))

    constructor(controlVectors: Iterable<Vector>): this(Array.ofAll(controlVectors))

    constructor(vararg controlVectors: Vector): this(controlVectors.asIterable())

    override fun evaluate(t: Double): Vector = asBezier(t).vector

    override fun differentiate(t: Double): Vector = asBezier.differentiate(t)

    override fun toString(): String = prettyGson.toJson(json())

    fun json(): BezierDerivativeJson = BezierDerivativeJson(this)

    fun restrict(i: Interval): BezierDerivative = BezierDerivative(asBezier.restrict(i))

    fun restrict(begin: Double, end: Double): BezierDerivative = BezierDerivative(asBezier.restrict(begin, end))

    fun reverse(): BezierDerivative = BezierDerivative(asBezier.reverse())

    fun elevate(): BezierDerivative = BezierDerivative(asBezier.elevate())

    fun reduce(): BezierDerivative = BezierDerivative(asBezier.reduce())

    fun subdivide(t: Double): Tuple2<BezierDerivative, BezierDerivative> = asBezier
            .subdivide(t).map(::BezierDerivative, ::BezierDerivative)

    fun extend(t: Double): BezierDerivative = BezierDerivative(asBezier.extend(t))
}

data class BezierDerivativeJson(private val controlVectors: List<Vector>){

    constructor(bezierDerivative: BezierDerivative) : this(bezierDerivative.controlVectors.toJavaList())

    fun bezierDerivative(): BezierDerivative = BezierDerivative(controlVectors)
}