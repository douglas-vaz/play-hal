package play.api.hal

import play.api.libs.json.JsObject

case class HalLink(rel: String, href: String,
                   deprecation: Option[String] = None, name: Option[String] = None, profile: Option[String] = None,
                   title: Option[String] = None, hreflang: Option[String] = None, `type`: Option[String] = None,
                   linkAttr: JsObject = Defaults.emptyJson, templated: Boolean = false) {

  def withLinkAttributes(obj: JsObject) = this.copy(linkAttr = obj)
  def withDeprecation(url: String) = this.copy(deprecation = Some(url))
  def withName(name: String) = this.copy(name = Some(name))
  def withProfile(profile: String) = this.copy(profile = Some(profile))
  def withTitle(title: String) = this.copy(title = Some(title))
  def withHreflang(lang: String) = this.copy(hreflang = Some(lang))
  def withType(mediaType: String) = this.copy(`type` = Some(mediaType))
}

class HalLinks(val links: Vector[HalLink] = Vector.empty) {
  def ++(other: HalLinks) = {
    new HalLinks(this.links ++ other.links)
  }

  def include(other: HalLinks) = ++(other)

  def ++(link: HalLink) = new HalLinks(link +: this.links)

  def include(link: HalLink) = ++(link)

  override def equals(obj: scala.Any): Boolean = obj.asInstanceOf[HalLinks].links.map(links.contains(_)).reduce(_ && _)
}
