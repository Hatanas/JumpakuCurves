package jumpaku.fsc.snap

import org.assertj.core.api.AbstractAssert
import org.assertj.core.api.Assertions

fun gridCoordinateAssertThat(actual: GridPoint): GridCoordinateAssert = GridCoordinateAssert(actual)
class GridCoordinateAssert(actual: GridPoint) : AbstractAssert<GridCoordinateAssert, GridPoint>(actual, GridCoordinateAssert::class.java) {

    fun isEqualToGridCoordinate(expected: GridPoint, eps: Double = 1.0e-10): GridCoordinateAssert {
        isNotNull

        Assertions.assertThat(actual.x).isEqualTo(expected.x)
        Assertions.assertThat(actual.y).isEqualTo(expected.y)
        Assertions.assertThat(actual.z).isEqualTo(expected.z)
        gridAssertThat(actual.grid).isEqualToGrid(expected.grid, eps)

        return this
    }
}