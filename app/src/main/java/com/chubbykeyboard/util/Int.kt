package com.chubbykeyboard.util

import kotlin.math.absoluteValue
import kotlin.math.sign

/** Divides this value by the other value, ceiling the result to an integer that is closer to positive infinity. */
fun Int.ceilDiv(other: Int): Int {
    return this.floorDiv(other) + this.rem(other).sign.absoluteValue
}