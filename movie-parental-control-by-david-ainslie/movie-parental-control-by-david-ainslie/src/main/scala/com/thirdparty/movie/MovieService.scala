package com.thirdparty.movie

import scala.util.Try

trait MovieService {
  /**
    * For a given movie ID respond with the movie's rating which will be one of U, PG, 12, 15, 18
    * @param movieId String ID of movie
    * @return Try[String] where the contained String is the movie rating
    */
  def getParentalControlLevel(movieId: String): Try[String]
}