package Repositories.Traits

import Models.Request.VideoGameRequestDTO
import Models.Response.VideoGameResponseDTO
import cats.effect.IO

trait VideoGameRepoTrait {
  def addVideoGame(videoGame: VideoGameRequestDTO): IO[String]

  def getVideoGame(id: String): IO[Option[VideoGameResponseDTO]]

  def deleteVideoGame(id: String): IO[Either[String, Unit]]

  def updateVideoGame(id: String, videoGame: VideoGameRequestDTO): IO[Either[String, Unit]]

  def getVideoGames(): IO[List[VideoGameResponseDTO]]
}
