package jumpaku.core.fuzzy

import com.github.salomonbrys.kotson.double
import com.google.gson.JsonPrimitive
import io.vavr.control.Option
import io.vavr.control.Try
import jumpaku.core.json.ToJson


data class Grade(val value: Double) : Comparable<Grade>, ToJson {

    init {
        require(value.isFinite() && value in 0.0..1.0) { "value($value) is out of [0.0, 1.0]." }
    }

    constructor(booleanValue: Boolean): this(if (booleanValue) 1.0 else 0.0)

    constructor(intValue: Int): this(intValue.toDouble())

    override fun compareTo(other: Grade): Int = value.compareTo(other.value)

    infix fun and(g: Grade): Grade = minOf(this, g)

    infix fun or(g: Grade): Grade = maxOf(this, g)

    operator fun not(): Grade = Grade(1.0 - value)

    override fun toString(): String = value.toString()

    override fun toJson(): JsonPrimitive = JsonPrimitive(value)

    companion object {

        val TRUE = Grade(1.0)

        val FALSE = Grade(0.0)

        fun clamped(value: Double): Grade = Grade(value.coerceIn(0.0, 1.0))

        fun fromJson(json: JsonPrimitive): Option<Grade> = Try.ofSupplier { Grade(json.double) }.toOption()
    }
}

