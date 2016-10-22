import java.sql.CallableStatement
import java.sql.Time
import java.sql.Timestamp
import java.sql.Date
import java.sql.Types

import scala.language.experimental.macros
import scala.reflect.macros.blackbox

object CS {
  def setParam[T](name: String, value: T, cs: CallableStatement): Unit = macro MacrosImpl.setParam_impl[T]
}

object MacrosImpl {
  def setParam_impl[T: c.WeakTypeTag](c: blackbox.Context)(name: c.Tree, value: c.Tree, cs: c.Tree): c.Tree = {
    import c.universe._

    def doSetParam(name: c.Tree, value: c.Tree, cs: c.Tree, tpe: c.Type): c.Tree = {
      tpe match {
        case t if t =:= typeOf[Byte] =>
          q"$cs.setByte($name, $value)"
        case t if t =:= typeOf[String] =>
          q"$cs.setString($name, $value)"
        case t if t =:= typeOf[java.math.BigDecimal] =>
          q"$cs.setBigDecimal($name, $value)"
        case t if t =:= typeOf[BigDecimal] =>
          q"$cs.setBigDecimal($name, $value.bigDecimal)"
        case t if t =:= typeOf[Short] =>
          q"$cs.setShort($name, $value)"
        case t if t =:= typeOf[Int] =>
          q"$cs.setInt($name, $value)"
        case t if t =:= typeOf[Long] =>
          q"$cs.setLong($name, $value)"
        case t if t =:= typeOf[Double] =>
          q"$cs.setDouble($name, $value)"
        case t if t =:= typeOf[Float] =>
          q"$cs.setFloat($name, $value)"
        case t if t =:= typeOf[Date] =>
          q"$cs.setDate($name, $value)"
        case t if t =:= typeOf[Time] =>
          q"$cs.setTime($name, $value)"
        case t if t =:= typeOf[Timestamp] =>
          q"$cs.setTimestamp($name, $value)"
        case t if t =:= typeOf[Boolean] =>
          q"$cs.setBoolean($name, $value)"
        case _ =>
          q"$cs.setObject($name, $value)"
      }
    }

    def doSetNullParam(name: c.Tree, cs: c.Tree, tpe: c.Type): c.Tree = {
      tpe match {
        case t if t =:= typeOf[Byte] =>
          q"$cs.setNull($name, ${Types.TINYINT})"
        case t if t =:= typeOf[String] =>
          q"$cs.setNull($name, ${Types.VARCHAR})"
        case t if t =:= typeOf[java.math.BigDecimal] =>
          q"$cs.setNull($name, ${Types.DECIMAL})"
        case t if t =:= typeOf[BigDecimal] =>
          q"$cs.setNull($name, ${Types.DECIMAL})"
        case t if t =:= typeOf[Short] =>
          q"$cs.setNull($name, ${Types.SMALLINT})"
        case t if t =:= typeOf[Int] =>
          q"$cs.setNull($name, ${Types.INTEGER})"
        case t if t =:= typeOf[Long] =>
          q"$cs.setNull($name, ${Types.BIGINT})"
        case t if t =:= typeOf[Double] =>
          q"$cs.setNull($name, ${Types.DOUBLE})"
        case t if t =:= typeOf[Float] =>
          q"$cs.setNull($name, ${Types.FLOAT})"
        case t if t =:= typeOf[Date] =>
          q"$cs.setNull($name, ${Types.DATE})"
        case t if t =:= typeOf[Time] =>
          q"$cs.setNull($name, ${Types.TIME})"
        case t if t =:= typeOf[Timestamp] =>
          q"$cs.setNull($name, ${Types.TIMESTAMP})"
        case t if t =:= typeOf[Boolean] =>
          q"$cs.setNull($name, ${Types.BOOLEAN})"
        case _ =>
          q"$cs.setNull($name, ${Types.OTHER})"
      }
    }

    val tt = weakTypeOf[T]

    tt.baseType(typeOf[Option[_]].typeSymbol) match {
      case TypeRef(_, _, targ :: Nil) =>
        val t = doSetParam(name, q"$value.get", cs, targ)
        val tNo = doSetNullParam(name, cs, targ)
        q"if ($value.isDefined) $t else $tNo"
      case NoType =>
        doSetParam(name, value, cs, tt)
    }
  }
}
