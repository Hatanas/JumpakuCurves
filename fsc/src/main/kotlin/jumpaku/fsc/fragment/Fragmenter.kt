package jumpaku.fsc.fragment

import io.vavr.Tuple3
import io.vavr.collection.Array
import jumpaku.core.curve.Interval
import jumpaku.core.curve.bspline.BSpline
import jumpaku.core.fuzzy.Grade
import jumpaku.core.util.*


class Fragmenter(
        val threshold: FragmentThreshold = FragmentThreshold(0.4, 0.6),
        val n: Int = 4,
        minStayTime: Double = 0.1
) {
    val samplingSpan: Double

    init {
        require(n > 0) { "k should be k > 0" }
        samplingSpan = minStayTime / n
    }

    fun fragment(fsc: BSpline): List<Fragment> {
        val chunks = fsc.domain.sample(samplingSpan)
                .windowed(n)
                .map { chunk(fsc, Interval(it.first(), it.last()), n) }
        val states = chunks.asVavr()
                .map { it.state(threshold) }
                .fold(Array.of(Fragmenter.State.STAY)) { l, n -> l.append(l.last().transit(n)) }
                .tail()
        val initial = Array.of(Tuple3(chunks.first().interval.begin, chunks.first().interval.end, states.first()))
        return chunks.zip(states).fold(initial) { prev, (nChunk, nState) ->
            val (pBegin, _, pState) = prev.last()
            when {
                (pState != nState) -> prev.append(Tuple3(nChunk.interval.begin, nChunk.interval.end, nState))
                else -> prev.update(prev.lastIndex, Tuple3(pBegin, nChunk.interval.end, pState))
            }
        }.map { (begin, end, state) ->
            when (state!!) {  // 型推論がうまくいかない
                State.MOVE -> Fragment(Interval(begin, end), Fragment.Type.Move)
                State.STAY -> Fragment(Interval(begin, end), Fragment.Type.Stay)
            }
        }.asKt()
    }

    private enum class State {
        STAY {
            override fun transit(next: Chunk.State): Fragmenter.State = when (next) {
                Chunk.State.STAY, Chunk.State.UNKNOWN -> Fragmenter.State.STAY
                Chunk.State.MOVE -> Fragmenter.State.MOVE
            }
        },
        MOVE {
            override fun transit(next: Chunk.State): Fragmenter.State = when (next) {
                Chunk.State.STAY -> Fragmenter.State.STAY
                Chunk.State.MOVE, Chunk.State.UNKNOWN -> Fragmenter.State.MOVE
            }
        };

        abstract fun transit(next: Chunk.State): Fragmenter.State
    }
}

data class FragmentThreshold(val necessity: Grade, val possibility: Grade) {

    constructor(necessity: Double, possibility: Double) : this(Grade(necessity), Grade(possibility))
}