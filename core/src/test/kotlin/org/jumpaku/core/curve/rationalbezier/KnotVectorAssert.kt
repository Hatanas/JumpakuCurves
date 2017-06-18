package org.jumpaku.core.curve.rationalbezier

import org.assertj.core.api.AbstractAssert
import org.assertj.core.api.Assertions
import org.jumpaku.core.curve.KnotVector
import org.jumpaku.core.util.component1
import org.jumpaku.core.util.component2
import org.jumpaku.core.curve.knotAssertThat


fun knotVectorAssertThat(actual: KnotVector): KnotVectorAssert = KnotVectorAssert(actual)

class KnotVectorAssert(actual: KnotVector) : AbstractAssert<KnotVectorAssert, KnotVector>(actual, KnotVectorAssert::class.java) {
    fun isEqualToKnotVector(expected: KnotVector, eps: Double = 1.0e-10): KnotVectorAssert {
        isNotNull

        actual.knots.zip(expected.knots).forEachIndexed { index, (a, e) ->
            knotAssertThat(a).`as`("knot[%d]", index).isEqualToKnot(e)
        }

        return this
    }
}