package Repositories.Implementation

import Models.Request.VideoGameRequestDTO
import Models.Response.VideoGameResponseDTO
import Repositories.Traits.VideoGameRepoTrait
import cats.effect.IO

import scala.collection.mutable.HashMap
import cats.implicits.toFunctorOps

import scala.util
import scala.util.Random


class VideoGameRepoImpl extends VideoGameRepoTrait {

  // Go back and refactor so VideoGameRequestDTO -> VideoGameEntity
  val IN_MEMORY_DATABASE: HashMap[String, VideoGameRequestDTO] = HashMap()

  override def addVideoGame(videoGame: VideoGameRequestDTO): IO[String] = IO {
    val videoGameId: String = Random.alphanumeric.take(8).foldLeft("")((result, c) => result + c)
    IN_MEMORY_DATABASE.put(videoGameId, videoGame)
    videoGameId
  }

  override def getVideoGame(id: String): IO[Option[VideoGameResponseDTO]] = IO {
    IN_MEMORY_DATABASE.get(id).map(game => VideoGameResponseDTO(id, title = game.title, console = game.console))
  }

  override def deleteVideoGame(id: String): IO[Either[String, Unit]] =
    for {
      removedVideoGame <- IO(IN_MEMORY_DATABASE.remove(id))
      result = removedVideoGame.toRight(s"Video Game with $id not found").void
    } yield result

  override def updateVideoGame(id: String, videoGame: VideoGameRequestDTO): IO[Either[String, Unit]] = {
    for {
      updatedVideoGame <- IO(IN_MEMORY_DATABASE.updateWith(id) {
        case Some(_) => Some(videoGame)
        case None => None
      })
      result = updatedVideoGame.toRight(s"Video Game with $id not found").void
    } yield result
  }

  override def getVideoGames(): IO[List[VideoGameResponseDTO]] = IO {
    IN_MEMORY_DATABASE.map((id, game) => VideoGameResponseDTO(id, title = game.title, console = game.console)).toList
  }
}
