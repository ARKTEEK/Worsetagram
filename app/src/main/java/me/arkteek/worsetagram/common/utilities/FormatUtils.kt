package me.arkteek.worsetagram.common.utilities

import java.util.concurrent.TimeUnit

fun getTimeDifference(postTimestamp: Long): String {
  val currentTimeMillis = System.currentTimeMillis()
  val diffMillis = currentTimeMillis - postTimestamp

  val seconds = TimeUnit.MILLISECONDS.toSeconds(diffMillis)
  val minutes = TimeUnit.MILLISECONDS.toMinutes(diffMillis)
  val hours = TimeUnit.MILLISECONDS.toHours(diffMillis)
  val days = TimeUnit.MILLISECONDS.toDays(diffMillis)

  return when {
    days > 31 -> {
      val months = days / 30
      "$months month(s) ago"
    }
    days > 0 -> "$days day(s) ago"
    hours > 0 -> "$hours hour(s) ago"
    minutes > 0 -> "$minutes minute(s) ago"
    else -> "Just now"
  }
}
