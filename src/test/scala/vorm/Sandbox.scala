package vorm

import api._
import jdbc._
import samples._
import extensions._

import com.codahale.logula.Logging
import org.apache.log4j.Level
import java.sql.DriverManager

object Sandbox extends App {

  Logging.configure { log =>
    log.level = Level.TRACE
    log.loggers("vorm.jdbc.ConnectionAdapter") = Level.TRACE
  }

  case class A ( a : Seq[Int] )

  val db = TestingInstance.h2( Entity[A]() )
  db.save(A( Seq() ))
  db.save(A( Seq(2, 9, 3) ))
  db.save(A( Seq(4) ))
  db.save(A( Seq() ))


  db.query[A]
    .filterEquals("a", Seq(2,9,3))
    .fetchAll()
    .map{_.id}.toSet
    .prettyString.trace()


//  db.connection.executeQuery(
//    Statement(
//      """
//      SELECT
//        a.id
//        FROM
//          a
//          AS a
//        LEFT JOIN
//          a$a
//          AS b
//          ON b.p$id = a.id
//        LEFT JOIN
//          a$a
//          AS c
//          ON c.p$id = a.id
//        GROUP BY a.id
//        HAVING COUNT(DISTINCT b.i) = 0 AND
//               COUNT(DISTINCT c.i) = 0
//      """
////      """
////      SELECT
////        a.p$id
////        FROM
////          a$a
////          AS a
////        WHERE ( a.i = 2 AND
////                a.v = 3 ) OR
////              ( ( a.i = 1 AND
////                  a.v = 9 ) OR
////                ( a.i = 0 AND
////                  a.v = 2 ) )
////        HAVING COUNT(DISTINCT a.i) = 3
////      """
//    )
//  ) .parseAndClose().prettyString.trace()


}