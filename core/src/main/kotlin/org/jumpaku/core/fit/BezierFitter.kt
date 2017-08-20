package org.jumpaku.core.fit

import io.vavr.API.Tuple
import io.vavr.collection.Array
import org.apache.commons.math3.linear.DiagonalMatrix
import org.apache.commons.math3.linear.MatrixUtils
import org.apache.commons.math3.linear.QRDecomposition
import org.jumpaku.core.affine.Point
import org.jumpaku.core.curve.bezier.Bezier
import org.jumpaku.core.util.component1
import org.jumpaku.core.util.component2
import org.jumpaku.core.util.component3


class BezierFitter(val degree: Int) : Fitter<Bezier> {

    fun basis(i: Int, t: Double): Double = Bezier.basis(degree, i, t)

    override fun fit(data: Array<WeightedParamPoint>): Bezier {
        require(data.nonEmpty()) { "empty data" }

        val (ds, ts, ws) = data.unzip3 { (pt, w) -> Tuple(pt.point, pt.param, w) }

        val distinct = data.distinctBy(WeightedParamPoint::param)
        if(distinct.size() <= degree){
            return BezierFitter(degree - 1).fit(distinct).elevate()
        }

        val d = ds.map { doubleArrayOf(it.x, it.y, it.z) }
                .toJavaArray(DoubleArray::class.java)
                .let(MatrixUtils::createRealMatrix)
        val b = ts.map { t -> (0..degree).map { basis(it, t) } }
                .map(List<Double>::toDoubleArray)
                .toJavaArray(DoubleArray::class.java)
                .let(MatrixUtils::createRealMatrix)
        val w = DiagonalMatrix(ws.toJavaArray(Double::class.java).toDoubleArray())
        val p = QRDecomposition(b.transpose().multiply(w).multiply(b)).solver
                .solve(b.transpose().multiply(w).multiply(d))
                .run { this.data.map { Point.xyz(it[0], it[1], it[2]) } }

        return Bezier(p)
    }
}