package jumpaku.fsc.snap.conicsection

import jumpaku.core.geom.Point
import jumpaku.core.curve.rationalbezier.ConicSection

data class LinearFeaturePoints(val featurePoint0: Point, val featurePoint1: Point)

data class CircularFeaturePoints(val featurePoint0: Point, val featurePoint1: Point, val forNormal: Point)

data class EllipticFeaturePoints(val featurePoint0: Point, val featurePoint1: Point, val featurePoint2: Point)

interface FeaturePointsCombinator {

    fun linearCombinations(conicSection: ConicSection, isOpen: Boolean): List<LinearFeaturePoints>

    fun circularCombinations(conicSection: ConicSection, isOpen: Boolean): List<CircularFeaturePoints>

    fun ellipticCombinations(conicSection: ConicSection, isOpen: Boolean): List<EllipticFeaturePoints>
}