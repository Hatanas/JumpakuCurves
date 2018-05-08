package jumpaku.core.test.curve.rationalbezier

import jumpaku.core.curve.rationalbezier.RationalBezier
import jumpaku.core.test.affine.isCloseTo
import org.amshove.kluent.should

fun isCloseTo(actual: RationalBezier, expected: RationalBezier, error: Double = 1.0e-9): Boolean =
        (actual.weightedControlPoints.size() == expected.weightedControlPoints.size()) &&
                actual.weightedControlPoints.zipWith(expected.weightedControlPoints) { a, e -> isCloseTo(a, e, error) }.all { it }

fun RationalBezier.shouldBeRationalBezier(expected: RationalBezier, error: Double = 1.0e-9) = this.should("$this should be $expected") {
    isCloseTo(this, expected, error)
}
