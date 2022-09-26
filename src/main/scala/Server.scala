import Repositories.Implementation.VideoGameRepoImpl
import Repositories.Traits.VideoGameRepoTrait
import Routes.VideoGameRoutes
import cats.effect.{ExitCode, IO, IOApp}
import com.comcast.ip4s.*
import org.http4s.ember.server.EmberServerBuilder
import org.http4s.server.Router

object Server extends IOApp {

  val httpRoutes = Router[IO](
    "/" -> VideoGameRoutes.routes()
  ).orNotFound

  override def run(args: List[String]): IO[ExitCode] =
    EmberServerBuilder
      .default[IO]
      .withHost(ipv4"0.0.0.0")
      .withPort(port"8080")
      .withHttpApp(httpRoutes)
      .build
      .use(_ => IO.never)
      .as(ExitCode.Success)
}
