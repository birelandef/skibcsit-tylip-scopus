package com.tylip.adapters.scopus

//import java.io.InputStream
//import java.net.{URL, URLConnection}

import java.io.InputStream
import java.net.{URL, URLConnection}

import play.api.libs.json._

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._


/**
 * @author Sophie
 */

object Main {
  val devId ="65970e52d980ceb8d3478777b2839b1d";

  implicit  val linkFormat = Json.reads[Link]
  implicit  val subjectFormat = Json.format[Subject]
  implicit  val creatorFormat = Json.reads[Creator]

  implicit  val coreDataArticle1Format = Json.reads[CoreDataArticle1]
  implicit  val coreDataArticle2Format = Json.reads[CoreDataArticle2]
  implicit  val articleFormat = Json.reads[Article]

  implicit  val affiliationFormat = Json.reads[Affiliation]
  implicit  val coreDataAuthorFormat = Json.reads[CoreDataAuthor]
  implicit  val futhorFormat = Json.reads[Author]

  def getArticleForTests(doi: String): String = {
    val queryString = "http://api.elsevier.com/content/article/DOI:" + doi + "?APIKey=" + devId + "&httpAccept=application/json&view=META_ABS"
    val connection: URLConnection  = new URL(queryString).openConnection();
    connection.setRequestProperty("Accept-Charset", "utf-8");
    val response: InputStream = connection.getInputStream;
    var data: Integer = response.read();
    var content: Char = 'd';
    var answer: StringBuilder = new StringBuilder();
    while(data != -1) {
      content = data.toChar;
      answer.+=(content);
      data = response.read();
    }
    response.close();
    return answer.toString();
  }

/*
  * Get entity Article with DOI
 */
  def getArticle(doi: String): Article ={
    lazy val url =  "http://api.elsevier.com/content/article/doi/" + doi;
    lazy val wsMock: WSExample = WSExample(url)
    var wsJson = (Await.result(wsMock.futureResponse, 30.seconds)).json
    //TODO можно ли как-нибудь без лишнего преобразования в String?
    wsJson = Json parse preprocessingJsonString(wsJson.toString())
    val lat1 = wsJson \ "full-text-retrieval-response"
    val coreData1  = (lat1 \ "coredata").asOpt[CoreDataArticle1]
    val coreData2  = (lat1 \ "coredata").asOpt[CoreDataArticle2]
    // TODO если в списке только один элемент, то возникает JsError
    val listSubjects  = (lat1 \ "coredata"\ "dcterms_subject").asOpt[List[Subject]]
    //      val listLinks = Json.fromJson[List[Link]](lat1 \ "link")
    val article = new Article(coreData1.get,coreData2.get, listSubjects.get/*,  listLinks.get*/)
    article
  }

  def getAuthor(author_id: String): Author = {
    lazy val url =  "http://api.elsevier.com/content/author/author_id/" + author_id;
    lazy val wsMock: WSExample = WSExample(url)
    var wsJson = (Await.result(wsMock.futureResponse, 30.seconds)).json
    //TODO можно ли как-нибудь без лишнего преобразования в String?
    wsJson = Json parse preprocessingJsonString(wsJson.toString())
    val lat1 = wsJson \"author-retrieval-response"
    println(lat1)
    val coreDataAuthor = (lat1 \ "coredata").asOpt[CoreDataAuthor]
    val affilfiation = (lat1 \ "affiliation-current").asOpt[Affiliation]
    val author = new Author(coreDataAuthor.get, affilfiation.get)
    author
  }

/*
  * Function for transform source json to view for reading with implicit formatters;
  * For all entities
 */
  def preprocessingJsonString(initialString: String): String = {
    initialString.replaceAll("prism:", "prism_").replaceAll("dc:", "dc_").replace("dcterms:", "dcterms_").replace("@rel", "rel").replace("@href","href").replace("scopus-id", "scopus2id").replace("scopus-eid","scopus2eid")
  }
}
