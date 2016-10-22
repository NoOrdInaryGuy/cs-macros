import java.sql._

import org.scalatest.WordSpec
import org.scalatest.mockito.MockitoSugar
import org.mockito.Mockito._

class CSSpec extends WordSpec with MockitoSugar {
  trait Context {
    val name = "TheName"
    val cs = mock[CallableStatement]
    case class AnotherClass(field: String)
  }

  "The CS Macro" should {
    "Call setByte when given a Byte" in new Context {
      val input: Byte = 5.toByte
      CS.setParam(name, input, cs)
      verify(cs).setByte(name, input)
    }

    "Call setString when given a String" in new Context {
      val input: String = "Some words"
      CS.setParam(name, input, cs)
      verify(cs).setString(name, input)
    }

    "Call setBigDecimal when given a Java BigDecimal" in new Context {
      val input: java.math.BigDecimal = new java.math.BigDecimal(12345)
      CS.setParam(name, input, cs)
      verify(cs).setBigDecimal(name, input)
    }

    "Call setBigDecimal with the inner Java BigDecimal when given a Scala BigDecimal" in new Context {
      val input: BigDecimal = BigDecimal(12345)
      CS.setParam(name, input, cs)
      verify(cs).setBigDecimal(name, input.bigDecimal)
    }

    "Call setShort when given a Short" in new Context {
      val input: Short = 123.toShort
      CS.setParam(name, input, cs)
      verify(cs).setShort(name, input)
    }

    "Call setInt when given a Int" in new Context {
      val input: Int = 1212
      CS.setParam(name, input, cs)
      verify(cs).setInt(name, input)
    }

    "Call setLong when given a Long" in new Context {
      val input: Long = 1213l
      CS.setParam(name, input, cs)
      verify(cs).setLong(name, input)
    }

    "Call setDouble when given a Double" in new Context {
      val input: Double = 12.2d
      CS.setParam(name, input, cs)
      verify(cs).setDouble(name, input)
    }

    "Call setFloat when given a Float" in new Context {
      val input: Float = 12.2f
      CS.setParam(name, input, cs)
      verify(cs).setFloat(name, input)
    }

    "Call setDate when given a Date" in new Context {
      val input: Date = new Date(123123123123l)
      CS.setParam(name, input, cs)
      verify(cs).setDate(name, input)
    }

    "Call setTime when given a Time" in new Context {
      val input: Time = new Time(123123123123l)
      CS.setParam(name, input, cs)
      verify(cs).setTime(name, input)
    }

    "Call setTimestamp when given a Timestamp" in new Context {
      val input: Timestamp = new Timestamp(123123123123l)
      CS.setParam(name, input, cs)
      verify(cs).setTimestamp(name, input)
    }

    "Call setBoolean when given a Boolean" in new Context {
      val input: Boolean = false
      CS.setParam(name, input, cs)
      verify(cs).setBoolean(name, input)
    }

    "Call setObject when given another type" in new Context {
      val input: AnotherClass = AnotherClass("Abcd")
      CS.setParam(name, input, cs)
      verify(cs).setObject(name, input)
    }

    "Call setByte when given a Some[Byte]" in new Context {
      val wrapped: Byte = 5.toByte
      val input: Option[Byte] = Some(wrapped)
      CS.setParam(name, input, cs)
      verify(cs).setByte(name, wrapped)
    }

    "Call setNull with the correct SQL type when given a None[Byte]" in new Context {
      val input: Option[Byte] = None
      CS.setParam(name, input, cs)
      verify(cs).setNull(name, Types.TINYINT)
    }

    "Call setString when given a Some[String]" in new Context {
      val wrapped: String = "Some words"
      val input: Option[String] = Some(wrapped)
      CS.setParam(name, input, cs)
      verify(cs).setString(name, wrapped)
    }

    "Call setNull with the correct SQL type when given a None[String]" in new Context {
      val input: Option[String] = None
      CS.setParam(name, input, cs)
      verify(cs).setNull(name, Types.VARCHAR)
    }

    "Call setBigDecimal when given a Some[java.math.BigDecimal]" in new Context {
      val wrapped: java.math.BigDecimal = new java.math.BigDecimal(12345)
      val input: Option[java.math.BigDecimal] = Some(wrapped)
      CS.setParam(name, input, cs)
      verify(cs).setBigDecimal(name, wrapped)
    }

    "Call setNull with the correct SQL type when given a None[java.math.BigDecimal]" in new Context {
      val input: Option[java.math.BigDecimal] = None
      CS.setParam(name, input, cs)
      verify(cs).setNull(name, Types.DECIMAL)
    }

    "Call setBigDecimal with the inner Java BigDecimal when given a Some[BigDecimal]" in new Context {
      val wrapped: BigDecimal = BigDecimal(12345)
      val input = Some(wrapped)
      CS.setParam(name, input, cs)
      verify(cs).setBigDecimal(name, wrapped.bigDecimal)
    }

    "Call setNull with the correct SQL type when given a None[BigDecimal]" in new Context {
      val input: Option[BigDecimal] = None
      CS.setParam(name, input, cs)
      verify(cs).setNull(name, Types.DECIMAL)
    }

    "Call setShort when given a Some[Short]" in new Context {
      val wrapped: Short = 123.toShort
      val input = Some(wrapped)
      CS.setParam(name, input, cs)
      verify(cs).setShort(name, wrapped)
    }

    "Call setNull with the correct SQL type when given a None[Short]" in new Context {
      val input: Option[Short] = None
      CS.setParam(name, input, cs)
      verify(cs).setNull(name, Types.SMALLINT)
    }


    "Call setInt when given a Some[Int]" in new Context {
      val wrapped = 1234
      val input: Option[Int] = Some(wrapped)
      CS.setParam(name, input, cs)
      verify(cs).setInt(name, wrapped)
    }

    "Call setNull with the correct SQL type when given a None[Int]" in new Context {
      val input: Option[Int] = None
      CS.setParam(name, input, cs)
      verify(cs).setNull(name, Types.INTEGER)
    }

    "Call setLong when given a Some[Long]" in new Context {
      val wrapped: Long = 1213l
      val input: Option[Long] = Some(wrapped)
      CS.setParam(name, input, cs)
      verify(cs).setLong(name, wrapped)
    }

    "Call setNull with the correct SQL type when given a None[Long]" in new Context {
      val input: Option[Long] = None
      CS.setParam(name, input, cs)
      verify(cs).setNull(name, Types.BIGINT)
    }

    "Call setDouble when given a Some[Double]" in new Context {
      val wrapped: Double = 12.2d
      val input: Option[Double] = Some(wrapped)
      CS.setParam(name, input, cs)
      verify(cs).setDouble(name, wrapped)
    }

    "Call setNull with the correct SQL type when given a None[Double]" in new Context {
      val input: Option[Double] = None
      CS.setParam(name, input, cs)
      verify(cs).setNull(name, Types.DOUBLE)
    }

    "Call setFloat when given a Some[Float]" in new Context {
      val wrapped: Float = 12.2f
      val input: Option[Float] = Some(wrapped)
      CS.setParam(name, input, cs)
      verify(cs).setFloat(name, wrapped)
    }

    "Call setNull with the correct SQL type when given a None[Float]" in new Context {
      val input: Option[Float] = None
      CS.setParam(name, input, cs)
      verify(cs).setNull(name, Types.FLOAT)
    }

    "Call setDate when given a Some[Date]" in new Context {
      val wrapped: Date = new Date(123123123123l)
      val input: Option[Date] = Some(wrapped)
      CS.setParam(name, input, cs)
      verify(cs).setDate(name, wrapped)
    }

    "Call setNull with the correct SQL type when given a None[Date]" in new Context {
      val input: Option[Date] = None
      CS.setParam(name, input, cs)
      verify(cs).setNull(name, Types.DATE)
    }

    "Call setTime when given a Some[Time]" in new Context {
      val wrapped: Time = new Time(123123123123l)
      val input: Option[Time] = Some(wrapped)
      CS.setParam(name, input, cs)
      verify(cs).setTime(name, wrapped)
    }

    "Call setTimestamp when given a Some[Timestamp]" in new Context {
      val wrapped: Timestamp = new Timestamp(123123123123l)
      val input: Option[Timestamp] = Some(wrapped)
      CS.setParam(name, input, cs)
      verify(cs).setTimestamp(name, wrapped)
    }

    "Call setBoolean when given a Some[Boolean]" in new Context {
      val wrapped: Boolean = false
      val input: Option[Boolean] = Some(wrapped)
      CS.setParam(name, input, cs)
      verify(cs).setBoolean(name, wrapped)
    }

    "Call setNull with the correct SQL type when given a None[Boolean]" in new Context {
      val input: Option[Boolean] = None
      CS.setParam(name, input, cs)
      verify(cs).setNull(name, Types.BOOLEAN)
    }

    "Call setObject when given a Some[another type]" in new Context {
      val wrapped: AnotherClass = AnotherClass("Abcd")
      val input: Option[AnotherClass] = Some(wrapped)
      CS.setParam(name, input, cs)
      verify(cs).setObject(name, wrapped)
    }

    "Call setNull with the correct SQL type when given a None[another type]" in new Context {
      val input: Option[AnotherClass] = None
      CS.setParam(name, input, cs)
      verify(cs).setNull(name, Types.OTHER)
    }
  }
}
