package me.arkteek.worsetagram.common.utilities

import java.util.concurrent.TimeUnit

fun getTimeDifference(postTimestamp: Long): String {
  val currentTimeMillis = System.currentTimeMillis()
  val diffMillis = currentTimeMillis - postTimestamp

  val minutes = TimeUnit.MILLISECONDS.toMinutes(diffMillis)
  val hours = TimeUnit.MILLISECONDS.toHours(diffMillis)
  val days = TimeUnit.MILLISECONDS.toDays(diffMillis)

  return when {
    days > 31 -> {
      val months = days / 30
      if (months == 1L) "$months month ago" else "$months months ago"
    }
    days > 0 -> if (days == 1L) "$days day ago" else "$days days ago"
    hours > 0 -> if (hours == 1L) "$hours hour ago" else "$hours hours ago"
    minutes > 0 -> if (minutes == 1L) "$minutes minute ago" else "$minutes minutes ago"
    else -> "Just now"
  }
}
