package jumpaku.core.affine


import com.github.salomonbrys.kotson.*
import com.google.gson.JsonElement
import io.vavr.Function1
import io.vavr.Tuple2
import io.vavr.Tuple3
import io.vavr.Tuple4
import io.vavr.collection.Array
import io.vavr.control.Option
import jumpaku.core.json.ToJson
import jumpaku.core.util.component1
import jumpaku.core.util.component2
import jumpaku.core.util.component3
import jumpaku.core.util.divOption
import org.apache.commons.math3.linear.MatrixUtils
import org.apache.commons.math3.linear.QRDecomposition
import org.apache.commons.math3.linear.RealMatrix
import org.apache.commons.math3.util.FastMath


/**
 * Transforms a crisp point by affine transformation.
 */
class Affine internal constructor(private val matrix: RealMatrix): Function1<Point, Point>, ToJson {

    operator fun invoke(p: Point): Point = apply(p)

    /**
     * @return transformed crisp point
     */
    override fun apply(p: Point): Point {
        val array = matrix.operate(doubleArrayOf(p.x, p.y, p.z, 1.0))
        return Point.xyz(array[0], array[1], array[2])
    }

    override fun toString(): String = toJsonString()

    override fun toJson(): JsonElement = jsonObject("matrix" to jsonArray(matrix.data.map { jsonArray(it.map { it.toJson() }) }))

    fun invert(): Option<Affine> {
        val solver = QRDecomposition(matrix).solver
        return Option.`when`(solver.isNonSingular) { Affine(solver.inverse) }
    }

    fun andThen(a: Affine) = Affine(a.matrix.multiply(matrix))

    fun andTransformAt(p: Point, a: Affine): Affine = andThen(transformationAt(p, a))

    fun andScale(x: Double, y: Double, z: Double): Affine = andThen(scaling(x, y, z))

    fun andScale(scale: Double): Affine = andScale(scale, scale, scale)

    fun andScaleAt(center: Point, x: Double, y: Double, z: Double): Affine = andTransformAt(center, scaling(x, y, z))

    fun andScaleAt(center: Point, scale: Double): Affine = andScaleAt(center, scale, scale, scale)

    fun andRotate(axis: Vector, radian: Double): Affine = andThen(rotation(axis, radian))

    fun andRotate(axisInitial: Point, axisTerminal: Point, radian: Double): Affine = andRotate(axisTerminal - axisInitial, radian)

    fun andRotate(from: Vector, to: Vector, radian: Double): Affine = from.cross(to).let {
        when {
            it.isZero() && from.dot(to) >= 0 -> identity
            else -> andRotate(it, radian)
        }
    }

    fun andRotate(from: Vector, to: Vector): Affine = andRotate(from, to, from.angle(to))

    fun andRotateAt(center: Point, axis: Vector, radian: Double): Affine = andTransformAt(center, identity.andRotate(axis, radian))

    fun andRotateAt(p: Point, from: Vector, to: Vector, radian: Double): Affine = andTransformAt(p, identity.andRotate(from, to, radian))

    fun andRotateAt(p: Point, from: Vector, to: Vector): Affine = andRotateAt(p, from, to, from.angle(to))

    fun andTranslate(v: Vector): Affine = andThen(translation(v))

    fun andTranslate(x: Double, y: Double, z: Double): Affine = andTranslate(Vector(x, y, z))
}

val JsonElement.affine: Affine get() {
    return Affine(MatrixUtils.createRealMatrix(
            this["matrix"].array.map { it.array.map { it.double }.toDoubleArray() }.toTypedArray()))
}

val identity = Affine(MatrixUtils.createRealIdentityMatrix(4))

fun translation(v: Vector): Affine {
    return Affine(MatrixUtils.createRealMatrix(arrayOf(
            doubleArrayOf(1.0, 0.0, 0.0, v.x),
            doubleArrayOf(0.0, 1.0, 0.0, v.y),
            doubleArrayOf(0.0, 0.0, 1.0, v.z),
            doubleArrayOf(0.0, 0.0, 0.0, 1.0)
    )))
}

fun rotation(axis: Vector, radian: Double): Affine {
    require(!axis.isZero()) { "axis($axis) is zero" }
    val (x, y, z) = axis.normalize().get()
    val cos = FastMath.cos(radian)
    val sin = FastMath.sin(radian)
    return Affine(MatrixUtils.createRealMatrix(arrayOf(
            doubleArrayOf(x * x * (1 - cos) + cos, x * y * (1 - cos) - z * sin, z * x * (1 - cos) + y * sin, 0.0),
            doubleArrayOf(x * y * (1 - cos) + z * sin, y * y * (1 - cos) + cos, y * z * (1 - cos) - x * sin, 0.0),
            doubleArrayOf(z * x * (1 - cos) - y * sin, y * z * (1 - cos) + x * sin, z * z * (1 - cos) + cos, 0.0),
            doubleArrayOf(0.0, 0.0, 0.0, 1.0)
    )))
}

fun scaling(x: Double, y: Double, z: Double): Affine {
    return Affine(MatrixUtils.createRealDiagonalMatrix(
            doubleArrayOf(x, y, z, 1.0)))
}

fun transformationAt(p: Point, a: Affine): Affine {
    return translation(p.toVector().unaryMinus()).andThen(a).andTranslate(p.toVector())
}

fun calibrate(before: Tuple4<Point, Point, Point, Point>,
              after: Tuple4<Point, Point, Point, Point>): Option<Affine> {
    val at = MatrixUtils.createRealMatrix(arrayOf(
            doubleArrayOf(before._1().x, before._1().y, before._1().z, 1.0),
            doubleArrayOf(before._2().x, before._2().y, before._2().z, 1.0),
            doubleArrayOf(before._3().x, before._3().y, before._3().z, 1.0),
            doubleArrayOf(before._4().x, before._4().y, before._4().z, 1.0)))
    val bt = MatrixUtils.createRealMatrix(arrayOf(
            doubleArrayOf(after._1().x, after._1().y, after._1().z, 1.0),
            doubleArrayOf(after._2().x, after._2().y, after._2().z, 1.0),
            doubleArrayOf(after._3().x, after._3().y, after._3().z, 1.0),
            doubleArrayOf(after._4().x, after._4().y, after._4().z, 1.0)))
    val solver = QRDecomposition(at).solver
    return Option.`when`(solver.isNonSingular) { Affine(solver.solve(bt).transpose()) }
}

fun calibrateToPlane(before: Tuple3<Point, Point, Point>, after: Tuple3<Point, Point, Point>): Option<Affine> {
    val (a0, b0, c0) = before
    val n = a0.normal(b0, c0)
    val (a1, b1, c1) = after
    return n.flatMap { calibrate(Tuple4(a0, b0, c0, a0 + it), Tuple4(a1, b1, c1, a1)) }
}

fun similarity(ab: Tuple2<Point, Point>, cd: Tuple2<Point, Point>): Option<Affine> {
    val a = ab._2() - ab._1()
    val b = cd._2() - cd._1()
    val ac = cd._1() - ab._1()
    return b.length().divOption(a.length()).map { s ->
        identity.andRotateAt(ab._1(), a, b).andScaleAt(ab._1(), s).andTranslate(ac)
    }
}

fun similarityWithNormal(before: Tuple3<Point, Point, Vector>, after: Tuple3<Point, Point, Vector>): Option<Affine> {
    val (a0, b0, n0) = before
    val e0 = b0 - a0
    val e1 = e0.cross(n0)
    val e2 = e1.cross(e0)

    val (a1, b1, n1) = after
    val f0 = b1 - a1
    val f1 = f0.cross(n1)
    val f2 = f1.cross(f0)

    return calibrate(
            Tuple4(a0, a0 + e0, a0 + e1, a0 + e2),
            Tuple4(a1, a1 + f0, a1 + f1, a1 + f2))
}

