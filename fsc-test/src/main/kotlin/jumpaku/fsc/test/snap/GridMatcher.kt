package jumpaku.fsc.test.snap

import jumpaku.core.test.affine.isCloseTo
import jumpaku.core.test.isCloseTo
import jumpaku.fsc.snap.Grid
import org.amshove.kluent.should

fun isCloseTo(actual: Grid, expected: Grid, error: Double): Boolean =
        isCloseTo(actual.spacing, expected.spacing, error) &&
                actual.magnification == expected.magnification &&
                isCloseTo(actual.origin, expected.origin, error) &&
                isCloseTo(actual.axis, expected.axis, error) &&
                isCloseTo(actual.radian, expected.radian, error) &&
                isCloseTo(actual.fuzziness, expected.fuzziness, error) &&
                actual.resolution == expected.resolution

fun Grid.shouldBeGrid(expected: Grid, error: Double = 1.0e-9) = this.should("$this should be $expected") {
    isCloseTo(this, expected, error)
}