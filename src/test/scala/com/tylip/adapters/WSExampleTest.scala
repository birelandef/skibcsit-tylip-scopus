package com.tylip.adapters

import com.tylip.adapters.scopus.{Main, WSExample}
import org.specs2.mutable.Specification

import scala.concurrent.Await
import scala.concurrent.duration._

/**
 * @author Sophie
 * date 17.08.2015.
 */
class WSExampleTest extends Specification {
  "WSExample" should {
    "do it" in {
      val doi = "10.1016/j.aap.2009.06.027"
      lazy val url = "http://api.elsevier.com/content/article/doi/" + doi;
      val wsMock: WSExample = WSExample(url)
      val future = wsMock.futureResponse
      val result = Await.result(future, 30.seconds)
      println(s":::: result = $result")
      result.status mustEqual (200)
    }

    "make author" in {
      val resultAuthor = Main.getAuthor("6603727683")
            println(s":::: resultAuthor = $resultAuthor")
            resultAuthor.toString
            //TODO переписать через запрос
      resultAuthor.coreDataAuthor mustNotEqual(null)
    }

    "make article" in {
      val resultArticle = Main.getArticle("10.1016/j.aap.2009.06.027")
      println(s":::: resultArticle = $resultArticle")
      resultArticle.toString
      //TODO переписать через getArticleForTest
      resultArticle.coredata1 mustNotEqual (null)
    }

  }
}
