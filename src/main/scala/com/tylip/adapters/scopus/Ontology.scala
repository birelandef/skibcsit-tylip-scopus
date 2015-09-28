package com.tylip.adapters.scopus

case class Article(
      coredata1: CoreDataArticle1,
      coredata2: CoreDataArticle2,
      dcterms_subject: List[Subject]//,
//      link: List[Link]
      //originalText: String
)

case class CoreDataArticle1(
                            prism_url: String,
                            dc_identifier: String,
                            eid: String,
                            prism_doi: String,
                            pii: String,
                            dc_title: String,
                            prism_publicationName: String,
                            prism_aggregationType: String ,
                            prism_issn :String,
                            prism_volume: String,
                            prism_issueIdentifier: String,
                            prism_startingPage: String,
                            prism_endingPage: String,
                            prism_pageRange: String
)

case class CoreDataArticle2(
           prism_number: String,
           dc_format: String,
           prism_coverDate: String,
           prism_coverDisplayDate: String,
           prism_copyright: String,
           prism_publisher: String,
           dc_creator: List[Creator] ,
           dc_description: String,
           openaccess: Option[String],
                           //TODO как избежать null?
           openaccessArticle: Option[Boolean],
           openaccessType: Option[String],
           openArchiveArticle: Option[Boolean],
           openaccessSponsorName: Option[String],
           openaccessSponsorType: Option[String],
           openaccessUserLicense: Option[String]
)


case class Creator(
  $ :String
)

case class Subject(
  $ :String
)

case class Link(
  rel: String,
  href: String
)


case class Author(
                 coreDataAuthor: CoreDataAuthor,
                 affiliationCurrent: Affiliation//,
//                 affiliationHistory: List[Affiliation],
//                 subjectAreas: List[SubjectArea],
//                 authorProfile: AuthorProfile,
//                 h_index: String,
//                 coauthor_count: String
)
case class CoreDataAuthor(
                prism2url:String,
                dc2identifier: String,
                document_count:String,
                cited_by_count:String,
                citation_count:String,
                //TODO зачем это?
                //authClaim:@associations:0,
                link: List[Link]
)

case class Affiliation(
    id:String,
    href:String
)
  //TODO унифицировать с другими сущностями
case class SubjectArea(
                      abbrev:String,
                      code: String,
                      name: String //this is replace "$" in JSON, in "subject-area"
)
  //TODO раскрыть подполя
case class AuthorProfile(
status: String,
date_created:DateCreated,
preferred_name: Name,
name_variant: List[Name],
classificationgroup:{  }, // Что это и зачем?
publication_range: PublicationRange,
journal_history:{  },
affiliation_current:{  },
affiliation_history:{  }

)
case class DateCreated(
                      day:String,
                      month:String,
                      year:String
)
case class Name(
          initials:String,
          indexed_name: String,
          surname: String,
          given_name: String
)
case class PublicationRange(
                           end: String,
                           start: String
)


case class AffiliationRetrieval(
  coredata: CoreDataAffiliation,
  affiliation_name: String,
  name_variants: AffiliationName,
  address: String,
  city: String,
  country: String//,
  //TODO обязательно?
//  institution_profile:
)
case class CoreDataAffiliation(
  prism_url: String,
  dc_identifier:String,
  author_count:String,
  document_count: String,
  link: List[Link]
)

case class AffiliationName(
    name_variant: String
)