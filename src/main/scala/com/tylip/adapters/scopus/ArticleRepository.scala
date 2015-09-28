//package com.tylip.adapters.scopus
//
///**
// * @author Sophie
// */
//object ArticleRepository  {
//
//
//  def main(args : Array[String]) = {
//    try {
//      val article: Article = getArticleByDOI(args.mkString)
//      //println(article.coredata1.dc_title)
//    }catch {
//      case e: Exception => println(e.getMessage);
//    }
//  }
//
//  def getArticleByDOI(doi: String): Article ={
//    try {
//      var strJson = Main.getArticle(doi)
//      strJson = Main.preprocessingJsonString(strJson)
//      println(strJson)
//      Main.convertToArticle(strJson);
//    }catch {
//      case e: Exception => throw new IllegalAccessException("Error retrieving article");
//    }
//  }
//}
