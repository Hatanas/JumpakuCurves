package org.jumpaku.fsc.classify


import org.jumpaku.core.curve.bspline.BSpline

interface Classifier {

    fun classify(fsc: BSpline): Result
}
