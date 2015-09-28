package com.tylip.adapters.scopus

import com.ning.http.client.AsyncHttpClientConfig
import play.api.Play.current
import play.api.libs.json.JsResult
import play.api.libs.ws._
import play.api.libs.ws.ning.{NingWSClient, NingAsyncHttpClientConfigBuilder}
import play.libs.Json
import scala.concurrent.Future
/**
 * @author Sophie
 * date 04.08.2015.
 */

case class WSExample(url: String) extends  App{
//  lazy val url =  "http://api.elsevier.com/content/article/doi/" + doi;
  lazy val devId ="65970e52d980ceb8d3478777b2839b1d";
  lazy val config = new NingAsyncHttpClientConfigBuilder().build()
  lazy val builder = new AsyncHttpClientConfig.Builder(config)
  lazy val ws: WSClient = new NingWSClient(builder.build())
  lazy val holder: WSRequestHolder = ws.url(url)

  lazy val complexHolder: WSRequestHolder =
    holder
      .withHeaders("Accept" -> "application/json")
      .withHeaders("X-ELS-APIKey" -> devId)
      .withRequestTimeout(10000)
  def futureResponse: Future[WSResponse] = complexHolder.get()
}
