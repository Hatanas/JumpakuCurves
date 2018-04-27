package jumpaku.fsc.test.snap.conicsection

import jumpaku.core.affine.Point
import jumpaku.core.affine.Vector
import jumpaku.core.curve.bspline.BSpline
import jumpaku.core.curve.rationalbezier.ConicSection
import jumpaku.core.json.parseJson
import jumpaku.fsc.classify.ClassifyResult
import jumpaku.fsc.snap.Grid
import jumpaku.fsc.snap.conicsection.ConicSectionSnapResult
import jumpaku.fsc.snap.conicsection.ConicSectionSnapper
import jumpaku.fsc.snap.conicsection.ConjugateCombinator
import jumpaku.fsc.snap.point.PointSnapper
import org.junit.Test
import java.nio.file.Paths

class ConicSectionSnapperTest {

    val path = Paths.get("./src/test/resources/jumpaku/fsc/test/snap/conicsection/")

    val w = 1280.0

    val h = 720.0

    val baseGrid = Grid(
            spacing = 50.0,
            magnification = 2,
            origin = Point.xy(w/2, h/2),
            axis = Vector.K,
            radian = 0.0,
            fuzziness = 20.0)

    val conicSectionSnapper = ConicSectionSnapper(
            PointSnapper(
                    baseGrid = baseGrid,
                    minResolution = -5,
                    maxResolution = 5),
            ConjugateCombinator())

    @Test
    fun testSnap() {
        println("Snap")
        for (i in 0..4) {
            val cs = path.resolve("ConicSection$i.json").parseJson().flatMap { ConicSection.fromJson(it) }.get()
            val curveClass = path.resolve("ClassifyResult$i.json").parseJson().flatMap { ClassifyResult.fromJson(it) }.get().curveClass
            val fsc = path.resolve("Fsc$i.json").parseJson().flatMap { BSpline.fromJson(it) }.get()
            val e = path.resolve("ConicSectionSnapResult$i.json").parseJson().flatMap { ConicSectionSnapResult.fromJson(it) }.get()
            val a = conicSectionSnapper.snap(cs, curveClass) { candidate ->
                candidate.snappedConicSection.isPossible(fsc, n = 15)
            }
            conicSectionSnapResultAssertThat(a).`as`("$i").isEqualToConicSectionSnapResult(e)
        }
    }
}