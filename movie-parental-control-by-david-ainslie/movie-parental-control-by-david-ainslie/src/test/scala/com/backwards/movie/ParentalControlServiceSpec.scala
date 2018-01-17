package com.backwards.movie

import scala.util.{Failure, Success}
import shapeless.tag.@@
import org.scalacheck.Gen
import org.scalacheck.Prop.forAll
import org.specs2.ScalaCheck
import org.specs2.mock.Mockito
import org.specs2.mutable.Specification
import com.backwards.movie.Movie._
import com.thirdparty.movie.{MovieService, TitleNotFoundException}

class ParentalControlServiceSpec extends Specification with Mockito with ScalaCheck {
  import ParentalControlService._

  "Parent control service" should {
    "permit client to watch a given 18 rated movie" in {
      val movieId: String @@ MovieId = "movieId" asMovieId

      val movieService = mock[MovieService]
      movieService.getParentalControlLevel(movieId) returns Success(`18`.toString)

      watch(`18`, movieId)(movieService) must be equalTo Right(Permitted)
    }

    "not permit client to watch a given 18 rated movie" in {
      val movieId: String @@ MovieId = "movieId" asMovieId

      val movieService = mock[MovieService]
      movieService.getParentalControlLevel(movieId) returns Success(`18`.toString)

      watch(`15`, movieId)(movieService) must be equalTo Right(Restricted)
    }

    "make a decision on whether client is permitted to watch a given movie" in {
      val ratingGen = Gen.oneOf(Rating.ratings)

      val movieGen = Gen.oneOf("MU" -> `U`, "MPG" -> `PG`, "M12" -> `12`, "M15" -> `15`, "M18" -> `18`)

      forAll(ratingGen) { ratingRestriction =>
        forAll(movieGen) { case (movieId, movieRating) =>
          val movieService = mock[MovieService]
          movieService.getParentalControlLevel(movieId) returns Success(movieRating.toString)

          val Right(result) = watch(ratingRestriction, movieId asMovieId)(movieService)

          if (movieRating > ratingRestriction) result == Restricted
          else result == Permitted
        }
      }
    }

    "handle a non existing movie" in {
      val movieId: String @@ MovieId = "movieId" asMovieId

      val movieService = mock[MovieService]
      movieService.getParentalControlLevel(movieId) returns Failure(new TitleNotFoundException)

      watch(`18`, movieId)(movieService) must be equalTo Left(nonExistingMovieError(movieId))
    }

    "encounter a technical failure" in {
      val movieId: String @@ MovieId = "movieId" asMovieId

      val movieService = mock[MovieService]
      val exception = new Exception()
      movieService.getParentalControlLevel(movieId) returns Failure(exception)

      watch(`18`, movieId)(movieService) must be equalTo Left(systemError(exception))
    }
  }
}