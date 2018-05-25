package jumpaku.core.curve.polyline

import com.github.salomonbrys.kotson.array
import com.github.salomonbrys.kotson.get
import com.github.salomonbrys.kotson.jsonArray
import com.github.salomonbrys.kotson.jsonObject
import com.google.gson.JsonElement
import io.vavr.API.*
import io.vavr.Tuple2
import io.vavr.collection.Array
import io.vavr.collection.Stream
import io.vavr.control.Option
import io.vavr.control.Try
import jumpaku.core.curve.ParamPoint
import jumpaku.core.geom.Point
import jumpaku.core.transform.Transform
import jumpaku.core.curve.Curve
import jumpaku.core.curve.Interval
import jumpaku.core.curve.arclength.ArcLengthReparameterized
import jumpaku.core.curve.chordalParametrize
import jumpaku.core.json.ToJson
import org.apache.commons.math3.util.Precision


/**
 * Polyline parametrized by arc-arcLength.
 */
class Polyline (private val paramPoints: Array<ParamPoint>) : Curve, ToJson {

    val points: Array<Point> = paramPoints.map(ParamPoint::point)

    private val parameters: Array<Double> = paramPoints.map(ParamPoint::param)

    init {
        require(points.size() == parameters.size()){ "points.size() != parameters.size()" }
    }

    override val domain: Interval = Interval(parameters.head(), parameters.last())

    constructor(points: Iterable<Point>) : this(chordalParametrize(Array.ofAll(points)))

    constructor(vararg points: Point) : this(Array(*points))

    override fun toString(): String = toJsonString()

    override fun toJson(): JsonElement = jsonObject("points" to jsonArray(points.map { it.toJson() }))

    override fun evaluate(t: Double): Point {
        require(t in domain) { "t=$t is out of $domain" }

        val i = parameters.search(t)
        return if(i >= 0) points[i] else evaluateInSpan(t, -2 - i)
    }

    override fun evaluateAll(n: Int): Array<Point> {
        if (points.isSingleValued) {
            return Stream.fill(n,  { points.head() }).toArray()
        }

        val evaluated = ArrayList<Point>(n)
        val ts = domain.sample(n).subSequence(1, n-1)
        var index = 0
        for (t in ts) {
            index = parameters.indexWhere({ t < it }, index)
            evaluated += evaluateInSpan(t, index-1)
        }

        return Stream.ofAll(evaluated).prepend(points.head()).append(points.last()).toArray()
    }

    private fun evaluateInSpan(t: Double, index: Int): Point = points[index].divide(
                (t - parameters[index]) / (parameters[index+1] - parameters[index]), points[index+1])

    fun transform(a: Transform): Polyline = Polyline(points.map(a::invoke))

    override fun toCrisp(): Polyline = Polyline(paramPoints.map { it.copy(point = it.point.toCrisp()) })

    fun reverse(): Polyline = Polyline(points.reverse().zipWith(parameters.map { domain.end + domain.begin - it }.reverse(), ::ParamPoint))

    fun restrict(i: Interval): Polyline = restrict(i.begin, i.end)

    fun restrict(begin: Double, end: Double): Polyline = subdivide(begin)._2().subdivide(end-begin)._1()

    fun subdivide(t: Double): Tuple2<Polyline, Polyline> {
        require(t in domain) { "t($t) is out of domain($domain)" }

        val index = parameters.search(t)
        return when{
            index >= 0 ->
                Tuple(Polyline(paramPoints.take(index + 1)),
                        Polyline(paramPoints.drop(index).map { it.copy(param = it.param - t) }))
            Precision.equals(parameters[-2-index], t, 1.0e-10) ->
                Tuple(Polyline(paramPoints.take(-1 - index)),
                        Polyline(paramPoints.drop(-2 - index).map { it.copy(param = it.param - t) }))
            Precision.equals(parameters[-1-index], t, 1.0e-10) ->
                Tuple(Polyline(paramPoints.take(-index)),
                        Polyline(paramPoints.drop(-1 - index).map { it.copy(param = it.param - t) }))
            else -> {
                val p = evaluate(t)
                Tuple(Polyline(paramPoints.take(-1 - index).append(ParamPoint(p, t))),
                        Polyline(paramPoints.drop(-1 - index).prepend(ParamPoint(p, t)).map { it.copy(param = it.param - t) }))
            }
        }
    }

    override val reparameterized: ArcLengthReparameterized by lazy{
        ArcLengthReparameterized(this, parameters)
    }

    companion object {

        fun fromJson(json: JsonElement): Option<Polyline> =
                Try.ofSupplier { Polyline(json["points"].array.flatMap { Point.fromJson(it) }) } .toOption()
    }
}
