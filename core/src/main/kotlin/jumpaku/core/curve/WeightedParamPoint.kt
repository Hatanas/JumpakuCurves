package jumpaku.core.curve

import com.github.salomonbrys.kotson.double
import com.github.salomonbrys.kotson.get
import com.github.salomonbrys.kotson.jsonObject
import com.github.salomonbrys.kotson.toJson
import com.google.gson.JsonElement
import jumpaku.core.geom.Point
import jumpaku.core.json.ToJson
import jumpaku.core.util.Result
import jumpaku.core.util.result

fun ParamPoint.weighted(weight: Double = 1.0): WeightedParamPoint = WeightedParamPoint(this, weight)

data class WeightedParamPoint(val paramPoint: ParamPoint, val weight: Double = 1.0): ToJson {

    constructor(point: Point, param: Double, weight: Double = 1.0) : this(ParamPoint(point, param), weight)

    val point: Point = paramPoint.point

    val param: Double = paramPoint.param

    override fun toString(): String = toJsonString()

    override fun toJson(): JsonElement = jsonObject("paramPoint" to paramPoint.toJson(), "weight" to weight.toJson())

    companion object {

        fun fromJson(json: JsonElement): Result<WeightedParamPoint> = result {
            WeightedParamPoint(ParamPoint.fromJson(json["paramPoint"]).orThrow(), json["weight"].double)
        }
    }
}
