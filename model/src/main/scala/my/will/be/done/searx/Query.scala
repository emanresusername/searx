package my.will.be.done.searx.model

case class Query(
  query: String,
  categorys: Seq[String] = Nil,
  engines: Seq[String] = Nil,
  language: Option[String] = None,
  pageNumber: Option[Int] = None,
  timeRange: Option[String] = None
)
