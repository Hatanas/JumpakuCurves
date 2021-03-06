package jumpaku.fsc.test.snap.point

import jumpaku.core.geom.Point
import jumpaku.core.geom.Vector
import jumpaku.core.transform.Rotate
import jumpaku.core.test.geom.shouldEqualToPoint
import jumpaku.core.util.orDefault
import jumpaku.fsc.snap.Grid
import jumpaku.fsc.snap.point.MFGS
import org.junit.Test


class MFGSTest {

    val baseGrid = Grid(
            baseSpacing = 1.0,
            magnification = 4,
            origin = Point.xyz(0.0, 0.0, 0.0),
            rotation = Rotate(Vector.K, 0.0),
            baseFuzziness = 0.25)

    val snapper = MFGS(-1, 1)

    @Test
    fun testSnap0() {
        println("Snap0")
        val r = 1 / 32.0
        snapper.snap(baseGrid, Point.xr(0 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(0 / 32.0) }.shouldEqualToPoint(Point.x(0 / 32.0))
        snapper.snap(baseGrid, Point.xr(1 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(1 / 32.0) }.shouldEqualToPoint(Point.x(1 / 32.0))
        snapper.snap(baseGrid, Point.xr(2 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(2 / 32.0) }.shouldEqualToPoint(Point.x(2 / 32.0))
        snapper.snap(baseGrid, Point.xr(3 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(3 / 32.0) }.shouldEqualToPoint(Point.x(3 / 32.0))
        snapper.snap(baseGrid, Point.xr(4 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(4 / 32.0) }.shouldEqualToPoint(Point.x(4 / 32.0))
        snapper.snap(baseGrid, Point.xr(5 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(5 / 32.0) }.shouldEqualToPoint(Point.x(5 / 32.0))
        snapper.snap(baseGrid, Point.xr(6 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(6 / 32.0) }.shouldEqualToPoint(Point.x(6 / 32.0))
        snapper.snap(baseGrid, Point.xr(7 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(7 / 32.0) }.shouldEqualToPoint(Point.x(7 / 32.0))
        snapper.snap(baseGrid, Point.xr(8 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(8 / 32.0) }.shouldEqualToPoint(Point.x(8 / 32.0))
        snapper.snap(baseGrid, Point.xr(9 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(9 / 32.0) }.shouldEqualToPoint(Point.x(9 / 32.0))
        snapper.snap(baseGrid, Point.xr(10 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(10 / 32.0) }.shouldEqualToPoint(Point.x(10 / 32.0))
        snapper.snap(baseGrid, Point.xr(11 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(11 / 32.0) }.shouldEqualToPoint(Point.x(11 / 32.0))
        snapper.snap(baseGrid, Point.xr(12 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(12 / 32.0) }.shouldEqualToPoint(Point.x(12 / 32.0))
        snapper.snap(baseGrid, Point.xr(13 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(13 / 32.0) }.shouldEqualToPoint(Point.x(13 / 32.0))
        snapper.snap(baseGrid, Point.xr(14 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(14 / 32.0) }.shouldEqualToPoint(Point.x(14 / 32.0))
        snapper.snap(baseGrid, Point.xr(15 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(15 / 32.0) }.shouldEqualToPoint(Point.x(15 / 32.0))
        snapper.snap(baseGrid, Point.xr(16 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(16 / 32.0) }.shouldEqualToPoint(Point.x(16 / 32.0))
        snapper.snap(baseGrid, Point.xr(17 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(17 / 32.0) }.shouldEqualToPoint(Point.x(17 / 32.0))
        snapper.snap(baseGrid, Point.xr(18 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(18 / 32.0) }.shouldEqualToPoint(Point.x(18 / 32.0))
        snapper.snap(baseGrid, Point.xr(19 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(19 / 32.0) }.shouldEqualToPoint(Point.x(19 / 32.0))
        snapper.snap(baseGrid, Point.xr(20 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(20 / 32.0) }.shouldEqualToPoint(Point.x(20 / 32.0))
        snapper.snap(baseGrid, Point.xr(21 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(21 / 32.0) }.shouldEqualToPoint(Point.x(21 / 32.0))
        snapper.snap(baseGrid, Point.xr(22 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(22 / 32.0) }.shouldEqualToPoint(Point.x(22 / 32.0))
        snapper.snap(baseGrid, Point.xr(23 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(23 / 32.0) }.shouldEqualToPoint(Point.x(23 / 32.0))
        snapper.snap(baseGrid, Point.xr(24 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(24 / 32.0) }.shouldEqualToPoint(Point.x(24 / 32.0))
        snapper.snap(baseGrid, Point.xr(25 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(25 / 32.0) }.shouldEqualToPoint(Point.x(25 / 32.0))
        snapper.snap(baseGrid, Point.xr(26 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(26 / 32.0) }.shouldEqualToPoint(Point.x(26 / 32.0))
        snapper.snap(baseGrid, Point.xr(27 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(27 / 32.0) }.shouldEqualToPoint(Point.x(27 / 32.0))
        snapper.snap(baseGrid, Point.xr(28 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(28 / 32.0) }.shouldEqualToPoint(Point.x(28 / 32.0))
        snapper.snap(baseGrid, Point.xr(29 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(29 / 32.0) }.shouldEqualToPoint(Point.x(29 / 32.0))
        snapper.snap(baseGrid, Point.xr(30 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(30 / 32.0) }.shouldEqualToPoint(Point.x(30 / 32.0))
        snapper.snap(baseGrid, Point.xr(31 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(31 / 32.0) }.shouldEqualToPoint(Point.x(31 / 32.0))
        snapper.snap(baseGrid, Point.xr(32 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(32 / 32.0) }.shouldEqualToPoint(Point.x(32 / 32.0))
    }

    @Test
    fun testSnap1() {
        println("Snap1")
        val r = 1/16.0
        snapper.snap(baseGrid, Point.xr(0 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(0 / 32.0) }.shouldEqualToPoint(Point.x( 0/32.0))//
        snapper.snap(baseGrid, Point.xr(1 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(1 / 32.0) }.shouldEqualToPoint(Point.x( 1/32.0))
        snapper.snap(baseGrid, Point.xr(2 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(2 / 32.0) }.shouldEqualToPoint(Point.x( 2/32.0))
        snapper.snap(baseGrid, Point.xr(3 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(3 / 32.0) }.shouldEqualToPoint(Point.x( 3/32.0))
        snapper.snap(baseGrid, Point.xr(4 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(4 / 32.0) }.shouldEqualToPoint(Point.x( 4/32.0))//
        snapper.snap(baseGrid, Point.xr(5 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(5 / 32.0) }.shouldEqualToPoint(Point.x( 5/32.0))
        snapper.snap(baseGrid, Point.xr(6 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(6 / 32.0) }.shouldEqualToPoint(Point.x( 6/32.0))
        snapper.snap(baseGrid, Point.xr(7 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(7 / 32.0) }.shouldEqualToPoint(Point.x( 7/32.0))
        snapper.snap(baseGrid, Point.xr(8 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(8 / 32.0) }.shouldEqualToPoint(Point.x( 8/32.0))//
        snapper.snap(baseGrid, Point.xr(9 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(9 / 32.0) }.shouldEqualToPoint(Point.x( 9/32.0))
        snapper.snap(baseGrid, Point.xr(10 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(10 / 32.0) }.shouldEqualToPoint(Point.x(10/32.0))
        snapper.snap(baseGrid, Point.xr(10 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(10 / 32.0) }.shouldEqualToPoint(Point.x(10/32.0))
        snapper.snap(baseGrid, Point.xr(12 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(12 / 32.0) }.shouldEqualToPoint(Point.x(12/32.0))//
        snapper.snap(baseGrid, Point.xr(13 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(13 / 32.0) }.shouldEqualToPoint(Point.x(13/32.0))
        snapper.snap(baseGrid, Point.xr(14 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(14 / 32.0) }.shouldEqualToPoint(Point.x(14/32.0))
        snapper.snap(baseGrid, Point.xr(15 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(15 / 32.0) }.shouldEqualToPoint(Point.x(15/32.0))
        snapper.snap(baseGrid, Point.xr(16 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(16 / 32.0) }.shouldEqualToPoint(Point.x(16/32.0))//
        snapper.snap(baseGrid, Point.xr(17 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(17 / 32.0) }.shouldEqualToPoint(Point.x(17/32.0))
        snapper.snap(baseGrid, Point.xr(18 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(18 / 32.0) }.shouldEqualToPoint(Point.x(18/32.0))
        snapper.snap(baseGrid, Point.xr(19 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(19 / 32.0) }.shouldEqualToPoint(Point.x(19/32.0))
        snapper.snap(baseGrid, Point.xr(20 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(20 / 32.0) }.shouldEqualToPoint(Point.x(20/32.0))//
        snapper.snap(baseGrid, Point.xr(21 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(21 / 32.0) }.shouldEqualToPoint(Point.x(21/32.0))
        snapper.snap(baseGrid, Point.xr(22 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(22 / 32.0) }.shouldEqualToPoint(Point.x(22/32.0))
        snapper.snap(baseGrid, Point.xr(23 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(23 / 32.0) }.shouldEqualToPoint(Point.x(23/32.0))
        snapper.snap(baseGrid, Point.xr(24 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(24 / 32.0) }.shouldEqualToPoint(Point.x(24/32.0))//
        snapper.snap(baseGrid, Point.xr(25 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(25 / 32.0) }.shouldEqualToPoint(Point.x(25/32.0))
        snapper.snap(baseGrid, Point.xr(26 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(26 / 32.0) }.shouldEqualToPoint(Point.x(26/32.0))
        snapper.snap(baseGrid, Point.xr(27 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(27 / 32.0) }.shouldEqualToPoint(Point.x(27/32.0))
        snapper.snap(baseGrid, Point.xr(28 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(28 / 32.0) }.shouldEqualToPoint(Point.x(28/32.0))//
        snapper.snap(baseGrid, Point.xr(29 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(29 / 32.0) }.shouldEqualToPoint(Point.x(29/32.0))
        snapper.snap(baseGrid, Point.xr(30 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(30 / 32.0) }.shouldEqualToPoint(Point.x(30/32.0))
        snapper.snap(baseGrid, Point.xr(31 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(31 / 32.0) }.shouldEqualToPoint(Point.x(31/32.0))
        snapper.snap(baseGrid, Point.xr(32 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(32 / 32.0) }.shouldEqualToPoint(Point.x(32/32.0))//
    }

    @Test
    fun testSnap2() {
        println("Snap2")
        val r = 1/8.0
        snapper.snap(baseGrid, Point.xr(0 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(0 / 32.0) }.shouldEqualToPoint(Point.x( 0/32.0))//
        snapper.snap(baseGrid, Point.xr(1 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(1 / 32.0) }.shouldEqualToPoint(Point.x( 0/32.0))
        snapper.snap(baseGrid, Point.xr(2 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(2 / 32.0) }.shouldEqualToPoint(Point.x( 2/32.0))
        snapper.snap(baseGrid, Point.xr(3 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(3 / 32.0) }.shouldEqualToPoint(Point.x( 3/32.0))
        snapper.snap(baseGrid, Point.xr(4 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(4 / 32.0) }.shouldEqualToPoint(Point.x( 4/32.0))//
        snapper.snap(baseGrid, Point.xr(5 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(5 / 32.0) }.shouldEqualToPoint(Point.x( 5/32.0))
        snapper.snap(baseGrid, Point.xr(6 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(6 / 32.0) }.shouldEqualToPoint(Point.x( 6/32.0))
        snapper.snap(baseGrid, Point.xr(7 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(7 / 32.0) }.shouldEqualToPoint(Point.x( 8/32.0))
        snapper.snap(baseGrid, Point.xr(8 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(8 / 32.0) }.shouldEqualToPoint(Point.x( 8/32.0))//
        snapper.snap(baseGrid, Point.xr(9 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(9 / 32.0) }.shouldEqualToPoint(Point.x( 8/32.0))
        snapper.snap(baseGrid, Point.xr(10 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(10 / 32.0) }.shouldEqualToPoint(Point.x(10/32.0))
        snapper.snap(baseGrid, Point.xr(11 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(11 / 32.0) }.shouldEqualToPoint(Point.x(11/32.0))
        snapper.snap(baseGrid, Point.xr(12 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(12 / 32.0) }.shouldEqualToPoint(Point.x(12/32.0))//
        snapper.snap(baseGrid, Point.xr(13 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(13 / 32.0) }.shouldEqualToPoint(Point.x(13/32.0))
        snapper.snap(baseGrid, Point.xr(14 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(14 / 32.0) }.shouldEqualToPoint(Point.x(14/32.0))
        snapper.snap(baseGrid, Point.xr(15 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(15 / 32.0) }.shouldEqualToPoint(Point.x(16/32.0))
        snapper.snap(baseGrid, Point.xr(16 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(16 / 32.0) }.shouldEqualToPoint(Point.x(16/32.0))//
        snapper.snap(baseGrid, Point.xr(17 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(17 / 32.0) }.shouldEqualToPoint(Point.x(16/32.0))
        snapper.snap(baseGrid, Point.xr(18 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(18 / 32.0) }.shouldEqualToPoint(Point.x(18/32.0))
        snapper.snap(baseGrid, Point.xr(19 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(19 / 32.0) }.shouldEqualToPoint(Point.x(19/32.0))
        snapper.snap(baseGrid, Point.xr(20 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(20 / 32.0) }.shouldEqualToPoint(Point.x(20/32.0))//
        snapper.snap(baseGrid, Point.xr(21 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(21 / 32.0) }.shouldEqualToPoint(Point.x(21/32.0))
        snapper.snap(baseGrid, Point.xr(22 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(22 / 32.0) }.shouldEqualToPoint(Point.x(22/32.0))
        snapper.snap(baseGrid, Point.xr(23 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(23 / 32.0) }.shouldEqualToPoint(Point.x(24/32.0))
        snapper.snap(baseGrid, Point.xr(24 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(24 / 32.0) }.shouldEqualToPoint(Point.x(24/32.0))//
        snapper.snap(baseGrid, Point.xr(25 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(25 / 32.0) }.shouldEqualToPoint(Point.x(24/32.0))
        snapper.snap(baseGrid, Point.xr(26 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(26 / 32.0) }.shouldEqualToPoint(Point.x(26/32.0))
        snapper.snap(baseGrid, Point.xr(27 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(27 / 32.0) }.shouldEqualToPoint(Point.x(27/32.0))
        snapper.snap(baseGrid, Point.xr(28 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(28 / 32.0) }.shouldEqualToPoint(Point.x(28/32.0))//
        snapper.snap(baseGrid, Point.xr(29 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(29 / 32.0) }.shouldEqualToPoint(Point.x(29/32.0))
        snapper.snap(baseGrid, Point.xr(30 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(30 / 32.0) }.shouldEqualToPoint(Point.x(30/32.0))
        snapper.snap(baseGrid, Point.xr(31 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(31 / 32.0) }.shouldEqualToPoint(Point.x(32/32.0))
        snapper.snap(baseGrid, Point.xr(32 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(32 / 32.0) }.shouldEqualToPoint(Point.x(32/32.0))//
    }

    @Test
    fun testSnap3() {
        println("Snap3")
        val r = 1/4.0
        snapper.snap(baseGrid, Point.xr(0 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(0 / 32.0) }.shouldEqualToPoint(Point.x( 0/32.0))//
        snapper.snap(baseGrid, Point.xr(1 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(1 / 32.0) }.shouldEqualToPoint(Point.x( 0/32.0))
        snapper.snap(baseGrid, Point.xr(2 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(2 / 32.0) }.shouldEqualToPoint(Point.x( 0/32.0))
        snapper.snap(baseGrid, Point.xr(3 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(3 / 32.0) }.shouldEqualToPoint(Point.x( 0/32.0))
        snapper.snap(baseGrid, Point.xr(4 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(4 / 32.0) }.shouldEqualToPoint(Point.x( 4/32.0))//
        snapper.snap(baseGrid, Point.xr(5 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(5 / 32.0) }.shouldEqualToPoint(Point.x( 8/32.0))
        snapper.snap(baseGrid, Point.xr(6 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(6 / 32.0) }.shouldEqualToPoint(Point.x( 8/32.0))
        snapper.snap(baseGrid, Point.xr(7 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(7 / 32.0) }.shouldEqualToPoint(Point.x( 8/32.0))
        snapper.snap(baseGrid, Point.xr(8 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(8 / 32.0) }.shouldEqualToPoint(Point.x( 8/32.0))//
        snapper.snap(baseGrid, Point.xr(9 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(9 / 32.0) }.shouldEqualToPoint(Point.x( 8/32.0))
        snapper.snap(baseGrid, Point.xr(10 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(10 / 32.0) }.shouldEqualToPoint(Point.x( 8/32.0))
        snapper.snap(baseGrid, Point.xr(11 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(11 / 32.0) }.shouldEqualToPoint(Point.x( 8/32.0))
        snapper.snap(baseGrid, Point.xr(12 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(12 / 32.0) }.shouldEqualToPoint(Point.x(12/32.0))//
        snapper.snap(baseGrid, Point.xr(13 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(13 / 32.0) }.shouldEqualToPoint(Point.x(16/32.0))
        snapper.snap(baseGrid, Point.xr(14 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(14 / 32.0) }.shouldEqualToPoint(Point.x(16/32.0))
        snapper.snap(baseGrid, Point.xr(15 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(15 / 32.0) }.shouldEqualToPoint(Point.x(16/32.0))
        snapper.snap(baseGrid, Point.xr(16 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(16 / 32.0) }.shouldEqualToPoint(Point.x(16/32.0))//
        snapper.snap(baseGrid, Point.xr(17 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(17 / 32.0) }.shouldEqualToPoint(Point.x(16/32.0))
        snapper.snap(baseGrid, Point.xr(18 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(18 / 32.0) }.shouldEqualToPoint(Point.x(16/32.0))
        snapper.snap(baseGrid, Point.xr(19 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(19 / 32.0) }.shouldEqualToPoint(Point.x(16/32.0))
        snapper.snap(baseGrid, Point.xr(20 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(20 / 32.0) }.shouldEqualToPoint(Point.x(20/32.0))//
        snapper.snap(baseGrid, Point.xr(21 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(21 / 32.0) }.shouldEqualToPoint(Point.x(24/32.0))
        snapper.snap(baseGrid, Point.xr(22 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(22 / 32.0) }.shouldEqualToPoint(Point.x(24/32.0))
        snapper.snap(baseGrid, Point.xr(23 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(23 / 32.0) }.shouldEqualToPoint(Point.x(24/32.0))
        snapper.snap(baseGrid, Point.xr(24 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(24 / 32.0) }.shouldEqualToPoint(Point.x(24/32.0))//
        snapper.snap(baseGrid, Point.xr(25 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(25 / 32.0) }.shouldEqualToPoint(Point.x(24/32.0))
        snapper.snap(baseGrid, Point.xr(26 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(26 / 32.0) }.shouldEqualToPoint(Point.x(24/32.0))
        snapper.snap(baseGrid, Point.xr(27 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(27 / 32.0) }.shouldEqualToPoint(Point.x(24/32.0))
        snapper.snap(baseGrid, Point.xr(28 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(28 / 32.0) }.shouldEqualToPoint(Point.x(28/32.0))//
        snapper.snap(baseGrid, Point.xr(29 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(29 / 32.0) }.shouldEqualToPoint(Point.x(32/32.0))
        snapper.snap(baseGrid, Point.xr(30 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(30 / 32.0) }.shouldEqualToPoint(Point.x(32/32.0))
        snapper.snap(baseGrid, Point.xr(31 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(31 / 32.0) }.shouldEqualToPoint(Point.x(32/32.0))
        snapper.snap(baseGrid, Point.xr(32 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(32 / 32.0) }.shouldEqualToPoint(Point.x(32/32.0))//
    }

    @Test
    fun testSnap4() {
        println("Snap4")
        val r = 1/2.0
        snapper.snap(baseGrid, Point.xr(0 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(0 / 32.0) }.shouldEqualToPoint(Point.x( 0/32.0))//
        snapper.snap(baseGrid, Point.xr(1 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(1 / 32.0) }.shouldEqualToPoint(Point.x( 0/32.0))
        snapper.snap(baseGrid, Point.xr(2 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(2 / 32.0) }.shouldEqualToPoint(Point.x( 0/32.0))
        snapper.snap(baseGrid, Point.xr(3 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(3 / 32.0) }.shouldEqualToPoint(Point.x( 0/32.0))
        snapper.snap(baseGrid, Point.xr(4 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(4 / 32.0) }.shouldEqualToPoint(Point.x( 0/32.0))//
        snapper.snap(baseGrid, Point.xr(5 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(5 / 32.0) }.shouldEqualToPoint(Point.x( 8/32.0))
        snapper.snap(baseGrid, Point.xr(6 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(6 / 32.0) }.shouldEqualToPoint(Point.x( 8/32.0))
        snapper.snap(baseGrid, Point.xr(7 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(7 / 32.0) }.shouldEqualToPoint(Point.x( 8/32.0))
        snapper.snap(baseGrid, Point.xr(8 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(8 / 32.0) }.shouldEqualToPoint(Point.x( 8/32.0))//
        snapper.snap(baseGrid, Point.xr(9 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(9 / 32.0) }.shouldEqualToPoint(Point.x( 8/32.0))
        snapper.snap(baseGrid, Point.xr(10 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(10 / 32.0) }.shouldEqualToPoint(Point.x( 8/32.0))
        snapper.snap(baseGrid, Point.xr(11 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(11 / 32.0) }.shouldEqualToPoint(Point.x( 8/32.0))
        snapper.snap(baseGrid, Point.xr(12 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(12 / 32.0) }.shouldEqualToPoint(Point.x(16/32.0))//
        snapper.snap(baseGrid, Point.xr(13 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(13 / 32.0) }.shouldEqualToPoint(Point.x(16/32.0))
        snapper.snap(baseGrid, Point.xr(14 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(14 / 32.0) }.shouldEqualToPoint(Point.x(16/32.0))
        snapper.snap(baseGrid, Point.xr(15 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(15 / 32.0) }.shouldEqualToPoint(Point.x(16/32.0))
        snapper.snap(baseGrid, Point.xr(16 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(16 / 32.0) }.shouldEqualToPoint(Point.x(16/32.0))//
        snapper.snap(baseGrid, Point.xr(17 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(17 / 32.0) }.shouldEqualToPoint(Point.x(16/32.0))
        snapper.snap(baseGrid, Point.xr(18 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(18 / 32.0) }.shouldEqualToPoint(Point.x(16/32.0))
        snapper.snap(baseGrid, Point.xr(19 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(19 / 32.0) }.shouldEqualToPoint(Point.x(16/32.0))
        snapper.snap(baseGrid, Point.xr(20 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(20 / 32.0) }.shouldEqualToPoint(Point.x(24/32.0))//
        snapper.snap(baseGrid, Point.xr(21 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(21 / 32.0) }.shouldEqualToPoint(Point.x(24/32.0))
        snapper.snap(baseGrid, Point.xr(22 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(22 / 32.0) }.shouldEqualToPoint(Point.x(24/32.0))
        snapper.snap(baseGrid, Point.xr(23 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(23 / 32.0) }.shouldEqualToPoint(Point.x(24/32.0))
        snapper.snap(baseGrid, Point.xr(24 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(24 / 32.0) }.shouldEqualToPoint(Point.x(24/32.0))//
        snapper.snap(baseGrid, Point.xr(25 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(25 / 32.0) }.shouldEqualToPoint(Point.x(24/32.0))
        snapper.snap(baseGrid, Point.xr(26 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(26 / 32.0) }.shouldEqualToPoint(Point.x(24/32.0))
        snapper.snap(baseGrid, Point.xr(27 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(27 / 32.0) }.shouldEqualToPoint(Point.x(24/32.0))
        snapper.snap(baseGrid, Point.xr(28 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(28 / 32.0) }.shouldEqualToPoint(Point.x(32/32.0))//
        snapper.snap(baseGrid, Point.xr(29 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(29 / 32.0) }.shouldEqualToPoint(Point.x(32/32.0))
        snapper.snap(baseGrid, Point.xr(30 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(30 / 32.0) }.shouldEqualToPoint(Point.x(32/32.0))
        snapper.snap(baseGrid, Point.xr(31 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(31 / 32.0) }.shouldEqualToPoint(Point.x(32/32.0))
        snapper.snap(baseGrid, Point.xr(32 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(32 / 32.0) }.shouldEqualToPoint(Point.x(32/32.0))//
    }

    @Test
    fun testSnap5() {
        println("Snap5")
        val r = 1.0
        snapper.snap(baseGrid, Point.xr(0 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(0 / 32.0) }.shouldEqualToPoint(Point.x( 0/32.0))//
        snapper.snap(baseGrid, Point.xr(1 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(1 / 32.0) }.shouldEqualToPoint(Point.x( 0/32.0))
        snapper.snap(baseGrid, Point.xr(2 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(2 / 32.0) }.shouldEqualToPoint(Point.x( 0/32.0))
        snapper.snap(baseGrid, Point.xr(3 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(3 / 32.0) }.shouldEqualToPoint(Point.x( 0/32.0))
        snapper.snap(baseGrid, Point.xr(4 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(4 / 32.0) }.shouldEqualToPoint(Point.x( 0/32.0))//
        snapper.snap(baseGrid, Point.xr(5 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(5 / 32.0) }.shouldEqualToPoint(Point.x( 0/32.0))
        snapper.snap(baseGrid, Point.xr(6 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(6 / 32.0) }.shouldEqualToPoint(Point.x( 0/32.0))
        snapper.snap(baseGrid, Point.xr(7 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(7 / 32.0) }.shouldEqualToPoint(Point.x( 0/32.0))
        snapper.snap(baseGrid, Point.xr(8 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(8 / 32.0) }.shouldEqualToPoint(Point.x( 0/32.0))//
        snapper.snap(baseGrid, Point.xr(9 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(9 / 32.0) }.shouldEqualToPoint(Point.x( 0/32.0))
        snapper.snap(baseGrid, Point.xr(10 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(10 / 32.0) }.shouldEqualToPoint(Point.x( 0/32.0))
        snapper.snap(baseGrid, Point.xr(11 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(11 / 32.0) }.shouldEqualToPoint(Point.x( 0/32.0))
        snapper.snap(baseGrid, Point.xr(12 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(12 / 32.0) }.shouldEqualToPoint(Point.x( 0/32.0))//
        snapper.snap(baseGrid, Point.xr(13 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(13 / 32.0) }.shouldEqualToPoint(Point.x(16/32.0))
        snapper.snap(baseGrid, Point.xr(14 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(14 / 32.0) }.shouldEqualToPoint(Point.x(16/32.0))
        snapper.snap(baseGrid, Point.xr(15 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(15 / 32.0) }.shouldEqualToPoint(Point.x(16/32.0))
        snapper.snap(baseGrid, Point.xr(16 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(16 / 32.0) }.shouldEqualToPoint(Point.x(16/32.0))//
        snapper.snap(baseGrid, Point.xr(17 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(17 / 32.0) }.shouldEqualToPoint(Point.x(16/32.0))
        snapper.snap(baseGrid, Point.xr(18 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(18 / 32.0) }.shouldEqualToPoint(Point.x(16/32.0))
        snapper.snap(baseGrid, Point.xr(19 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(19 / 32.0) }.shouldEqualToPoint(Point.x(16/32.0))
        snapper.snap(baseGrid, Point.xr(20 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(20 / 32.0) }.shouldEqualToPoint(Point.x(32/32.0))//
        snapper.snap(baseGrid, Point.xr(21 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(21 / 32.0) }.shouldEqualToPoint(Point.x(32/32.0))
        snapper.snap(baseGrid, Point.xr(22 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(22 / 32.0) }.shouldEqualToPoint(Point.x(32/32.0))
        snapper.snap(baseGrid, Point.xr(23 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(23 / 32.0) }.shouldEqualToPoint(Point.x(32/32.0))
        snapper.snap(baseGrid, Point.xr(24 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(24 / 32.0) }.shouldEqualToPoint(Point.x(32/32.0))//
        snapper.snap(baseGrid, Point.xr(25 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(25 / 32.0) }.shouldEqualToPoint(Point.x(32/32.0))
        snapper.snap(baseGrid, Point.xr(26 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(26 / 32.0) }.shouldEqualToPoint(Point.x(32/32.0))
        snapper.snap(baseGrid, Point.xr(27 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(27 / 32.0) }.shouldEqualToPoint(Point.x(32/32.0))
        snapper.snap(baseGrid, Point.xr(28 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(28 / 32.0) }.shouldEqualToPoint(Point.x(32/32.0))//
        snapper.snap(baseGrid, Point.xr(29 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(29 / 32.0) }.shouldEqualToPoint(Point.x(32/32.0))
        snapper.snap(baseGrid, Point.xr(30 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(30 / 32.0) }.shouldEqualToPoint(Point.x(32/32.0))
        snapper.snap(baseGrid, Point.xr(31 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(31 / 32.0) }.shouldEqualToPoint(Point.x(32/32.0))
        snapper.snap(baseGrid, Point.xr(32 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(32 / 32.0) }.shouldEqualToPoint(Point.x(32/32.0))//
    }

    @Test
    fun testSnap6() {
        println("Snap6")
        val r = 2.0
        snapper.snap(baseGrid, Point.xr(0 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(0 / 32.0) }.shouldEqualToPoint(Point.x( 0/32.0))//
        snapper.snap(baseGrid, Point.xr(1 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(1 / 32.0) }.shouldEqualToPoint(Point.x( 0/32.0))
        snapper.snap(baseGrid, Point.xr(2 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(2 / 32.0) }.shouldEqualToPoint(Point.x( 0/32.0))
        snapper.snap(baseGrid, Point.xr(3 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(3 / 32.0) }.shouldEqualToPoint(Point.x( 0/32.0))
        snapper.snap(baseGrid, Point.xr(4 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(4 / 32.0) }.shouldEqualToPoint(Point.x( 0/32.0))//
        snapper.snap(baseGrid, Point.xr(5 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(5 / 32.0) }.shouldEqualToPoint(Point.x( 0/32.0))
        snapper.snap(baseGrid, Point.xr(6 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(6 / 32.0) }.shouldEqualToPoint(Point.x( 0/32.0))
        snapper.snap(baseGrid, Point.xr(7 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(7 / 32.0) }.shouldEqualToPoint(Point.x( 0/32.0))
        snapper.snap(baseGrid, Point.xr(8 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(8 / 32.0) }.shouldEqualToPoint(Point.x( 0/32.0))//
        snapper.snap(baseGrid, Point.xr(9 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(9 / 32.0) }.shouldEqualToPoint(Point.x( 0/32.0))
        snapper.snap(baseGrid, Point.xr(10 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(10 / 32.0) }.shouldEqualToPoint(Point.x( 0/32.0))
        snapper.snap(baseGrid, Point.xr(11 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(11 / 32.0) }.shouldEqualToPoint(Point.x( 0/32.0))
        snapper.snap(baseGrid, Point.xr(12 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(12 / 32.0) }.shouldEqualToPoint(Point.x( 0/32.0))//
        snapper.snap(baseGrid, Point.xr(13 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(13 / 32.0) }.shouldEqualToPoint(Point.x( 0/32.0))
        snapper.snap(baseGrid, Point.xr(14 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(14 / 32.0) }.shouldEqualToPoint(Point.x( 0/32.0))
        snapper.snap(baseGrid, Point.xr(15 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(15 / 32.0) }.shouldEqualToPoint(Point.x( 0/32.0))
        snapper.snap(baseGrid, Point.xr(16 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(16 / 32.0) }.shouldEqualToPoint(Point.x( 0/32.0))//
        snapper.snap(baseGrid, Point.xr(17 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(17 / 32.0) }.shouldEqualToPoint(Point.x(32/32.0))
        snapper.snap(baseGrid, Point.xr(18 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(18 / 32.0) }.shouldEqualToPoint(Point.x(32/32.0))
        snapper.snap(baseGrid, Point.xr(19 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(19 / 32.0) }.shouldEqualToPoint(Point.x(32/32.0))
        snapper.snap(baseGrid, Point.xr(20 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(20 / 32.0) }.shouldEqualToPoint(Point.x(32/32.0))//
        snapper.snap(baseGrid, Point.xr(21 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(21 / 32.0) }.shouldEqualToPoint(Point.x(32/32.0))
        snapper.snap(baseGrid, Point.xr(22 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(22 / 32.0) }.shouldEqualToPoint(Point.x(32/32.0))
        snapper.snap(baseGrid, Point.xr(23 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(23 / 32.0) }.shouldEqualToPoint(Point.x(32/32.0))
        snapper.snap(baseGrid, Point.xr(24 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(24 / 32.0) }.shouldEqualToPoint(Point.x(32/32.0))//
        snapper.snap(baseGrid, Point.xr(25 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(25 / 32.0) }.shouldEqualToPoint(Point.x(32/32.0))
        snapper.snap(baseGrid, Point.xr(26 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(26 / 32.0) }.shouldEqualToPoint(Point.x(32/32.0))
        snapper.snap(baseGrid, Point.xr(27 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(27 / 32.0) }.shouldEqualToPoint(Point.x(32/32.0))
        snapper.snap(baseGrid, Point.xr(28 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(28 / 32.0) }.shouldEqualToPoint(Point.x(32/32.0))//
        snapper.snap(baseGrid, Point.xr(29 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(29 / 32.0) }.shouldEqualToPoint(Point.x(32/32.0))
        snapper.snap(baseGrid, Point.xr(30 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(30 / 32.0) }.shouldEqualToPoint(Point.x(32/32.0))
        snapper.snap(baseGrid, Point.xr(31 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(31 / 32.0) }.shouldEqualToPoint(Point.x(32/32.0))
        snapper.snap(baseGrid, Point.xr(32 / 32.0, r)).map{ it.worldPoint(baseGrid) }.orDefault{ Point.x(32 / 32.0) }.shouldEqualToPoint(Point.x(32/32.0))//
    }
}