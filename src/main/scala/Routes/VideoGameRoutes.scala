package Routes

import Models.Request.VideoGameRequestDTO
import Repositories.Implementation.VideoGameRepoImpl
import cats.effect.IO
import io.circe.Json
import org.http4s.HttpRoutes
import org.http4s.dsl.io.*
import org.http4s.implicits.*
import org.http4s.ember.server.*
import io.circe.generic.auto.*
import org.http4s.circe.CirceEntityCodec.*
import cats.effect.unsafe.implicits.global

object VideoGameRoutes {

  def routes(): HttpRoutes[IO] = {
    val videoGameRepo = new VideoGameRepoImpl

    HttpRoutes.of[IO] {
      case _@GET -> Root / "games" =>
        videoGameRepo.getVideoGames().flatMap(games => Ok(games))

      case _@GET -> Root / "games" / id =>
        videoGameRepo.getVideoGame(id) flatMap {
          case None => Ok("Video Game was not found")
          case Some(game) => Ok(game)
        }

      case req@POST -> Root / "games" =>
        req.decode[VideoGameRequestDTO] { game =>
          videoGameRepo.addVideoGame(game) flatMap (id =>
            Created(Json.obj(("id", Json.fromString(id))))
            )
        }

      case req@PUT -> Root / "games" / id =>
        req.decode[VideoGameRequestDTO] {
          game =>
            videoGameRepo.updateVideoGame(id, game) flatMap {
              case Left(message) => NotFound(message)
              case Right(_) => Ok()
            }
        }

      case _@DELETE -> Root / "games" / id =>
        videoGameRepo.deleteVideoGame(id) flatMap {
          case Left(message) => NotFound(message)
          case Right(_) => Ok()
        }
    }
  }
}
