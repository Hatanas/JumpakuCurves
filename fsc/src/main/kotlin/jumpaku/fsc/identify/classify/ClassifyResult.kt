package jumpaku.fsc.identify.classify

import io.vavr.collection.HashMap
import io.vavr.collection.Map
import io.vavr.collection.Set
import jumpaku.core.fuzzy.Grade
import jumpaku.core.util.component1
import jumpaku.core.util.component2


class ClassifyResult(val grades: Map<CurveClass, Grade>){

    constructor(vararg pairs: Pair<CurveClass, Grade>) : this(HashMap.ofAll(mutableMapOf(*pairs)))

    init {
        require(grades.nonEmpty()) { "empty grades" }
    }

    val curveClass: CurveClass = grades.maxBy { (_, m) -> m } .map { it._1() }.get()

    val grade: Grade = grades.values().max().get()

    val curveClasses: Set<CurveClass> = grades.keySet()
}