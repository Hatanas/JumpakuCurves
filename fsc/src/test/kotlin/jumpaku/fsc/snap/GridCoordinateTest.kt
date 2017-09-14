package jumpaku.fsc.snap

import jumpaku.core.affine.Point
import jumpaku.core.affine.Vector
import jumpaku.core.affine.pointAssertThat
import jumpaku.core.affine.rotation
import jumpaku.core.json.parseToJson
import org.apache.commons.math3.util.FastMath
import org.assertj.core.api.AbstractAssert
import org.assertj.core.api.Assertions.*
import org.junit.Test

class GridCoordinateTest {

    val baseGrid = BaseGrid(
            baseGridSpacing = 4.0,
            magnification = 4,
            origin = Point.xyz(4.0, 4.0, 0.0),
            rotation = rotation(Vector(0.0, 0.0, 1.0), FastMath.PI/2),
            fuzziness = 2.0)

    val higherGrid = DerivedGrid(baseGrid, 1)

    val lowerGrid = DerivedGrid(baseGrid, -1)

    val baseGridCoordinate = GridCoordinate(4, -3, 0, baseGrid)
    val higherGridCoordinate = GridCoordinate(4, -3, 0, higherGrid)
    val lowerGridCoordinate = GridCoordinate(4, -3, 0, lowerGrid)

    @Test
    fun testProperties() {
        println("Properties")
        assertThat(baseGridCoordinate.x).isEqualTo(4)
        assertThat(baseGridCoordinate.y).isEqualTo(-3)
        assertThat(baseGridCoordinate.z).isEqualTo(0)
        gridAssertThat(baseGridCoordinate.grid).isEqualToGrid(baseGrid)
    }

    @Test
    fun testToString() {
        println("ToString")
        val j = baseGridCoordinate.toString().parseToJson().get().gridCoordinate
        gridCoordinateAssertThat(j).isEqualToGridCoordinate(baseGridCoordinate)
    }

    @Test
    fun testGetPoint() {
        println("GetPoint")
        pointAssertThat(baseGridCoordinate.point).isEqualToPoint(Point.xy(16.0, 20.0))
        pointAssertThat(higherGridCoordinate.point).isEqualToPoint(Point.xy(7.0, 8.0))
        pointAssertThat(lowerGridCoordinate.point).isEqualToPoint(Point.xy(52.0, 68.0))
    }
}


fun gridCoordinateAssertThat(actual: GridCoordinate): GridCoordinateAssert = GridCoordinateAssert(actual)

class GridCoordinateAssert(actual: GridCoordinate) : AbstractAssert<GridCoordinateAssert, GridCoordinate>(actual, GridCoordinateAssert::class.java) {

    fun isEqualToGridCoordinate(expected: GridCoordinate, eps: Double = 1.0e-10): GridCoordinateAssert {
        isNotNull

        assertThat(actual.x).isEqualTo(expected.x)
        assertThat(actual.y).isEqualTo(expected.y)
        assertThat(actual.z).isEqualTo(expected.z)
        gridAssertThat(actual.grid).isEqualToGrid(expected.grid, eps)

        return this
    }
}