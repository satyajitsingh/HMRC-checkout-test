package scala.com.control.movie

import shapeless.tag
import shapeless.tag.@@

object Movie {
  implicit class MovieOps(attribute: String) {
    val asMovieId: String @@ MovieId = tag[MovieId](attribute)

    val asError: String @@ Error = tag[Error](attribute)
  }
}

sealed trait MovieId

sealed trait Error
