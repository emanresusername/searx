package my.will.be.done.searx.client

import monix.execution.Scheduler
import my.will.be.done.searx.model._
import io.circe.parser.decode
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.auto._
import scala.concurrent.Future
import fr.hmil.roshttp.HttpRequest
import fr.hmil.roshttp.body.{BodyPart, URLEncodedBody}

class Client(url: String)(implicit val scheduler: Scheduler) {
  implicit val config = Configuration.default.withSnakeCaseKeys.withDiscriminator("type")

  private[this] def seqParam(elems: Seq[String]): Option[String] = {
    elems match {
      case Nil ⇒ None
      case _ ⇒ Option(elems.mkString(","))
    }
  }

  def queryBody(query: Query): BodyPart = {
    URLEncodedBody(
      Seq(
        Option("q" → query.query),
        Option("format" → "json"),
        seqParam(query.categorys).map("categories" → _),
        seqParam(query.engines).map("engines" → _),
        query.language.map("lang" → _),
        query.pageNumber.map("pageno" → _.toString),
        query.timeRange.map("time_range" → _)
      ).flatten:_*
    )
  }

  def search(query: String): Future[Search] = {
    search(Query(query = query))
  }

  def search(query: Query): Future[Search] = {
    for {
      response ← HttpRequest(url).post(queryBody(query))
      search ← decode[Search](response.body).fold(Future.failed, Future.successful)
    } yield {
      search
    }
  }
}

object Client {
  def search(query: String, url: String = "https://searx.tk")(implicit scheduler: Scheduler): Future[Search] = {
    new Client(url).search(query)
  }
}
