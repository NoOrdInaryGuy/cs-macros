import java.sql.CallableStatement
import java.sql.Time
import java.sql.Timestamp
import java.sql.Date
import java.sql.Types

import scala.language.experimental.macros
import scala.reflect.macros.blackbox

object CS {
  def setParam[T](name: String, value: T, cs: CallableStatement): Unit = macro MacrosImpl.setParam_impl[T]
  def getParam[T](name: String, cs: CallableStatement): T = macro MacrosImpl.getParam_impl[T]
}

object MacrosImpl {
  private val unknownTypeError = "Must explicitly specify supported generic type on call to getParam[Type](...)"

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

  def getParam_impl[T: c.WeakTypeTag](c: blackbox.Context)(name: c.Tree, cs: c.Tree): c.Tree = {
    import c.universe._

    def doGetParam(name: c.Tree, cs: c.Tree, tpe: c.Type): c.Tree = {
      tpe match {
        case t if t =:= typeOf[Byte] =>
          q"$cs.getByte($name)"
        case t if t =:= typeOf[String] =>
          q"$cs.getString($name)"
        case t if t =:= typeOf[java.math.BigDecimal] =>
          q"$cs.getBigDecimal($name)"
        case t if t =:= typeOf[BigDecimal] =>
          q"val nullableResult = $cs.getBigDecimal($name); Option(nullableResult).map(BigDecimal(_)).getOrElse(null)"
        case t if t =:= typeOf[Short] =>
          q"$cs.getShort($name)"
        case t if t =:= typeOf[Int] =>
          q"$cs.getInt($name)"
        case t if t =:= typeOf[Long] =>
          q"$cs.getLong($name)"
        case t if t =:= typeOf[Double] =>
          q"$cs.getDouble($name)"
        case t if t =:= typeOf[Float] =>
          q"$cs.getFloat($name)"
        case t if t =:= typeOf[Date] =>
          q"$cs.getDate($name)"
        case t if t =:= typeOf[Time] =>
          q"$cs.getTime($name)"
        case t if t =:= typeOf[Timestamp] =>
          q"$cs.getTimestamp($name)"
        case t if t =:= typeOf[Boolean] =>
          q"$cs.getBoolean($name)"
        case _ =>
          c.abort(c.enclosingPosition, unknownTypeError)
      }
    }

    def optionNoneIfZero(tree: c.Tree): c.Tree = {
      q"val result = $tree; if (result == 0) None else Some(result)"
    }

    def optionNoneIfFalse(tree: c.Tree): c.Tree = {
      q"val result = $tree; if (!result) None else Some(result)"
    }

    def optionNoneIfNull(tree: c.Tree): c.Tree = {
      q"Option($tree)"
    }

    def doGetNullableParam(name: c.Tree, cs: c.Tree, tpe: c.Type): c.Tree = {
      val getResult = doGetParam(name, cs, tpe)
      tpe match {
        case t if t =:= typeOf[Byte] =>
          optionNoneIfZero(getResult)
        case t if t =:= typeOf[String] =>
          optionNoneIfNull(getResult)
        case t if t =:= typeOf[java.math.BigDecimal] =>
          optionNoneIfNull(getResult)
        case t if t =:= typeOf[BigDecimal] =>
          val bdGetResult = doGetParam(name, cs, typeOf[java.math.BigDecimal])
          q"Option($bdGetResult).map(BigDecimal(_))"
        case t if t =:= typeOf[Short] =>
          optionNoneIfZero(getResult)
        case t if t =:= typeOf[Int] =>
          optionNoneIfZero(getResult)
        case t if t =:= typeOf[Long] =>
          optionNoneIfZero(getResult)
        case t if t =:= typeOf[Double] =>
          optionNoneIfZero(getResult)
        case t if t =:= typeOf[Float] =>
          optionNoneIfZero(getResult)
        case t if t =:= typeOf[Date] =>
          optionNoneIfNull(getResult)
        case t if t =:= typeOf[Time] =>
          optionNoneIfNull(getResult)
        case t if t =:= typeOf[Timestamp] =>
          optionNoneIfNull(getResult)
        case t if t =:= typeOf[Boolean] =>
          optionNoneIfFalse(getResult)
        case _ =>
          c.abort(c.enclosingPosition, unknownTypeError)
      }
    }

    val tt = weakTypeOf[T]

    tt.baseType(typeOf[Option[_]].typeSymbol) match {
      case TypeRef(_, _, targ :: Nil) =>
        doGetNullableParam(name, cs, targ)
      case NoType =>
        doGetParam(name, cs, tt)
    }
  }
}
