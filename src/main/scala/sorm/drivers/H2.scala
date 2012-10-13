package sorm.drivers

import sext._
import sorm.core.Driver
import sorm.jdbc.JdbcConnection
import sorm.ddl.Table

class H2 (url:String, user:String, password:String)
  extends Driver
  with StdQuery
  with StdSqlRendering
  with StdDropAllTables
  with StdAbstractSqlToSql
  with StdNow
  with StdModify
  with StdDropTables
  with StdQuote
  with StdTransaction
  with StdCreateTable
{
  val connection = JdbcConnection(url, user, password)
  override protected def tableDdl(t: Table) : String
    = {
      val Table(name, columns, primaryKey, uniqueKeys, indexes, foreingKeys) = t
      val statements =
        columns.map(columnDdl) ++:
        primaryKey.$(primaryKeyDdl) +:
        uniqueKeys.map(uniqueKeyDdl) ++:
        foreingKeys.map(foreingKeyDdl).toStream
      "CREATE TABLE " + quote(name) +
      ( "\n( " + statements.mkString(",\n").indent(2).trim + " )" ).indent(2)
    }
}