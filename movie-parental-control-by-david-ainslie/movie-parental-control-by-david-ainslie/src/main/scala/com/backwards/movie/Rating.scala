package com.backwards.movie

import shapeless.tag.@@
import com.backwards.movie.Movie._

sealed trait Rating extends Ordered[Rating] {
  import Rating.ratings.indexOf

  def compare(that: Rating): Int = indexOf(this) compareTo indexOf(that)
}

object Rating {
  val ratings = Vector(U, PG, `12`, `15`, `18`)

  def apply(rating: String): String @@ Error Either Rating =
    ratings.find(_.toString == rating) toRight s"Invalid rating: $rating".asError
}

case object `U` extends Rating

case object `PG` extends Rating

case object `12` extends Rating

case object `15` extends Rating

case object `18` extends Rating