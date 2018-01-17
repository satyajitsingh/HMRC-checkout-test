package com.backwards.movie

import cats.data.Reader
import cats.syntax.either._
import shapeless.tag.@@
import com.backwards.movie.Movie._
import com.thirdparty.movie.{MovieService, TitleNotFoundException}

object ParentalControlService {
  type Result = String @@ Error Either MoviePermission

  type Watch = Reader[MovieService, Result]

  val nonExistingMovieError: String @@ MovieId => String @@ Error = { movieId =>
    s"Non existing movie: $movieId" asError
  }

  val systemError: Throwable => String @@ Error = { t =>
    s"Cannot watch movie due to system error: ${t.getMessage}" asError
  }

  val error: String @@ MovieId => Throwable => String @@ Error = movieId => {
    case _: TitleNotFoundException => nonExistingMovieError(movieId)
    case t => systemError(t)
  }

  def permission(ratingRestriction: Rating)(rating: String): Result = {
    def permission(ratingRestriction: Rating)(rating: Rating) = if (rating > ratingRestriction) Restricted else Permitted

    Rating(rating) map permission(ratingRestriction)
  }

  def watch(ratingRestriction: Rating, movieId: String @@ MovieId): Watch = Reader { movieService =>
    Either fromTry movieService.getParentalControlLevel(movieId) leftMap error(movieId) flatMap permission(ratingRestriction)
  }
}