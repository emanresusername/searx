package my.will.be.done.searx.model

case class UrlTitle(
    url: String,
    title: String
)

case class Attribute(
    label: String,
    value: String
)

case class Infobox(
    engine: String,
    content: Option[String],
    infobox: String,
    imgSrc: Option[String],
    urls: List[UrlTitle],
    id: String,
    attributes: Option[List[Attribute]]
)

case class Osm(
    `type`: String,
    id: Long
)

sealed trait Geojson

object Geojson {
  case class Point(
      coordinates: List[Double]
  ) extends Geojson

  case class Polygon(
      coordinates: List[List[List[Double]]]
  ) extends Geojson
}

case class Result(
    engine: String,
    category: String,
    engines: List[String],
    title: String,
    url: String,
    positions: List[Int],
    parsedUrl: List[String],
    content: Option[String],
    prettyUrl: String,
    score: Double,
    publishedDate: Option[String],
    seed: Option[Int],
    magnetlink: Option[String],
    filesize: Option[Long],
    template: Option[String],
    pubdate: Option[String],
    torrentfile: Option[String],
    leech: Option[Int],
    imgSrc: Option[String],
    thumbnailSrc: Option[String],
    embedded: Option[String],
    thumbnail: Option[String],
    latitude: Option[Double],
    longitude: Option[Double],
    osm: Option[Osm],
    address: Option[String],
    geojson: Option[Geojson],
    boundingbox: Option[List[Double]]
)

case class Search(
    numberOfResults: Long,
    corrections: List[String],
    query: String,
    infoboxes: List[Infobox],
    suggestions: List[String],
    results: List[Result],
    answers: List[String],
    unresponsiveEngines: Option[List[List[String]]]
)
