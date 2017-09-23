package my.will.be.done.searx.client

import monix.execution.Scheduler
import my.will.be.done.searx.model.Search
import io.circe.parser.decode
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.auto._
import scala.concurrent.Future
import fr.hmil.roshttp.HttpRequest
import fr.hmil.roshttp.body.URLEncodedBody

class Client(url: String)(implicit val scheduler: Scheduler) {
  implicit val config = Configuration.default.withSnakeCaseKeys.withDiscriminator("type")

  private[this] def seqParam(elems: Seq[String]): Option[String] = {
    elems match {
      case Nil ⇒ None
      case _ ⇒ Option(elems.mkString(","))
    }
  }

  def search(
    query: String,
    categorys: Seq[String] = Nil,
    engines: Seq[String] = Nil,
    language: Option[String] = None,
    pageNumber: Option[Int] = None,
    timeRange: Option[String] = None
  ): Future[Search] = {
    val body = URLEncodedBody(Seq(
      Option("q" → query),
      Option("format" → "json"),
      seqParam(categorys).map("categories" → _),
      seqParam(engines).map("engines" → _),
      language.map("lang" → _),
      pageNumber.map("pageno" → _.toString),
      timeRange.map("time_range" → _)
    ).flatten:_*)

    for {
      response ← HttpRequest(url).post(body)
      search ← decode[Search](response.body).fold(Future.failed, Future.successful)
    } yield {
      search
    }
  }
}
