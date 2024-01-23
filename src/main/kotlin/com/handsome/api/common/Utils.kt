package com.handsome.api.common

import java.time.OffsetDateTime

private var fixedDateTime: OffsetDateTime? = null

fun currentDateTime(): OffsetDateTime = when (fixedDateTime) {
    null -> OffsetDateTime.now()
    else -> fixedDateTime!!
}

fun useFixedCurrentDateTime(dateTime: OffsetDateTime) {
    fixedDateTime = fixedDateTime
}
