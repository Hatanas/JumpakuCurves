package jumpaku.core.test.curve.polyline

import jumpaku.core.curve.polyline.Polyline
import jumpaku.core.test.affine.pointAssertThat
import jumpaku.core.util.component1
import jumpaku.core.util.component2
import org.assertj.core.api.AbstractAssert

fun polylineAssertThat(actual: Polyline): PolylineAssert = PolylineAssert(actual)
class PolylineAssert(actual: Polyline) : AbstractAssert<PolylineAssert, Polyline>(actual, PolylineAssert::class.java) {

    fun isEqualToPolyline(expected: Polyline, eps: Double = 1.0e-10): PolylineAssert {
        isNotNull

        if (actual.points.size() != expected.points.size()){
            failWithMessage("Expected polyline size to be <%s> but was <%s>", expected.points.size(), actual.points.size())
            return this
        }

        actual.points.zip(expected.points)
                .forEachIndexed {
                    i, (a, e) -> pointAssertThat(a).`as`("polyline.points[%d]", i).isEqualToPoint(e, eps)
                }

        return this
    }
}