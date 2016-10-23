import java.sql._

import org.scalatest.{Matchers, WordSpec}
import org.scalatest.mockito.MockitoSugar
import org.mockito.Mockito._

class CSSpec extends WordSpec with MockitoSugar with Matchers {
  trait Context {
    val name = "TheName"
    val cs = mock[CallableStatement]
    case class AnotherClass(field: String)
  }

  "The CS Macro (sets)" should {
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
  }

  "The CS Macro (sets with Option)" should {
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

  "The CS Macro (gets)" should {
    "Call getByte when reading a Byte" in new Context {
      val output: Byte = 5.toByte
      when(cs.getByte(name)).thenReturn(output)
      val result = CS.getParam[Byte](name, cs)
      verify(cs).getByte(name)
      result should equal(output)
    }

    "Call getString when reading a String" in new Context {
      val output: String = "AbCdEf"
      when(cs.getString(name)).thenReturn(output)
      val result = CS.getParam[String](name, cs)
      verify(cs).getString(name)
      result should equal(output)
    }

    "Call getBigDecimal when reading a java.match.BigDecimal" in new Context {
      val output: java.math.BigDecimal = new java.math.BigDecimal(12345)
      when(cs.getBigDecimal(name)).thenReturn(output)
      val result = CS.getParam[java.math.BigDecimal](name, cs)
      verify(cs).getBigDecimal(name)
      result should equal(output)
    }

    "Call getBigDecimal when reading a BigDecimal" in new Context {
      val output: BigDecimal = BigDecimal(12345)
      when(cs.getBigDecimal(name)).thenReturn(output.bigDecimal)
      val result = CS.getParam[BigDecimal](name, cs)
      verify(cs).getBigDecimal(name)
      result should equal(output)
    }

    "Call getShort when reading a Short" in new Context {
      val output: Short = 1234
      when(cs.getShort(name)).thenReturn(output)
      val result = CS.getParam[Short](name, cs)
      verify(cs).getShort(name)
      result should equal(output)
    }

    "Call getInt when reading a Int" in new Context {
      val output: Int = 12345
      when(cs.getInt(name)).thenReturn(output)
      val result = CS.getParam[Int](name, cs)
      verify(cs).getInt(name)
      result should equal(output)
    }

    "Call getLong when reading a Long" in new Context {
      val output: Long = 12345l
      when(cs.getLong(name)).thenReturn(output)
      val result = CS.getParam[Long](name, cs)
      verify(cs).getLong(name)
      result should equal(output)
    }

    "Call getDouble when reading a Double" in new Context {
      val output: Double = 12.34
      when(cs.getDouble(name)).thenReturn(output)
      val result = CS.getParam[Double](name, cs)
      verify(cs).getDouble(name)
      result should equal(output)
    }

    "Call getFloat when reading a Float" in new Context {
      val output: Float = 12.34f
      when(cs.getFloat(name)).thenReturn(output)
      val result = CS.getParam[Float](name, cs)
      verify(cs).getFloat(name)
      result should equal(output)
    }

    "Call getDate when reading a Date" in new Context {
      val output: Date = new Date(123123123123l)
      when(cs.getDate(name)).thenReturn(output)
      val result = CS.getParam[Date](name, cs)
      verify(cs).getDate(name)
      result should equal(output)
    }

    "Call getTime when reading a Time" in new Context {
      val output: Time = new Time(123123123123l)
      when(cs.getTime(name)).thenReturn(output)
      val result = CS.getParam[Time](name, cs)
      verify(cs).getTime(name)
      result should equal(output)
    }

    "Call getTimestamp when reading a Timestamp" in new Context {
      val output: Timestamp = new Timestamp(123123123123l)
      when(cs.getTimestamp(name)).thenReturn(output)
      val result = CS.getParam[Timestamp](name, cs)
      verify(cs).getTimestamp(name)
      result should equal(output)
    }

    "Call getBoolean when reading a Timestamp" in new Context {
      val output: Boolean = false
      when(cs.getBoolean(name)).thenReturn(output)
      val result = CS.getParam[Boolean](name, cs)
      verify(cs).getBoolean(name)
      result should equal(output)
    }
  }

  "The CS Macro (gets with Option)" should {
    "Call getByte when reading an Option[Byte], return Some if != 0" in new Context {
      val output: Byte = 5.toByte
      when(cs.getByte(name)).thenReturn(output)
      val result = CS.getParam[Option[Byte]](name, cs)
      verify(cs).getByte(name)
      result should equal(Some(output))
    }

    "Call getByte when reading an Option[Byte], return None if == 0" in new Context {
      val output: Byte = 0
      when(cs.getByte(name)).thenReturn(output)
      val result = CS.getParam[Option[Byte]](name, cs)
      verify(cs).getByte(name)
      result should equal(None)
    }

    "Call getString when reading an Option[String], return Some if != null" in new Context {
      val output: String = "A String"
      when(cs.getString(name)).thenReturn(output)
      val result = CS.getParam[Option[String]](name, cs)
      verify(cs).getString(name)
      result should equal(Some(output))
    }

    "Call getString when reading an Option[String], return None if == null" in new Context {
      val output: String = null
      when(cs.getString(name)).thenReturn(output)
      val result = CS.getParam[Option[String]](name, cs)
      verify(cs).getString(name)
      result should equal(None)
    }

    "Call getBigDecimal when reading an Option[java.math.BigDecimal], return Some if != null" in new Context {
      val output: java.math.BigDecimal = new java.math.BigDecimal(12345)
      when(cs.getBigDecimal(name)).thenReturn(output)
      val result = CS.getParam[Option[java.math.BigDecimal]](name, cs)
      verify(cs).getBigDecimal(name)
      result should equal(Some(output))
    }

    "Call getBigDecimal when reading an Option[java.math.BigDecimal], return None if == null" in new Context {
      val output: java.math.BigDecimal = null
      when(cs.getBigDecimal(name)).thenReturn(output)
      val result = CS.getParam[Option[java.math.BigDecimal]](name, cs)
      verify(cs).getBigDecimal(name)
      result should equal(None)
    }

    "Call getBigDecimal when reading an Option[BigDecimal], return Some if != null" in new Context {
      val output: BigDecimal = BigDecimal(12345)
      when(cs.getBigDecimal(name)).thenReturn(output.bigDecimal)
      val result = CS.getParam[Option[BigDecimal]](name, cs)
      verify(cs).getBigDecimal(name)
      result should equal(Some(output))
    }

    "Call getBigDecimal when reading an Option[BigDecimal], return None if == null" in new Context {
      when(cs.getBigDecimal(name)).thenReturn(null:java.math.BigDecimal)
      val result = CS.getParam[Option[BigDecimal]](name, cs)
      verify(cs).getBigDecimal(name)
      result should equal(None)
    }

    "Call getShort when reading an Option[Short], return Some if != 0" in new Context {
      val output: Short = 1234
      when(cs.getShort(name)).thenReturn(output)
      val result = CS.getParam[Option[Short]](name, cs)
      verify(cs).getShort(name)
      result should equal(Some(output))
    }

    "Call getShort when reading an Option[Short], return None if == 0" in new Context {
      val output: Short = 0
      when(cs.getShort(name)).thenReturn(output)
      val result = CS.getParam[Option[Short]](name, cs)
      verify(cs).getShort(name)
      result should equal(None)
    }

    "Call getInt when reading an Option[Int], return Some if != 0" in new Context {
      val output: Int = 1234
      when(cs.getInt(name)).thenReturn(output)
      val result = CS.getParam[Option[Int]](name, cs)
      verify(cs).getInt(name)
      result should equal(Some(output))
    }

    "Call getInt when reading an Option[Int], return None if == 0" in new Context {
      val output: Int = 0
      when(cs.getInt(name)).thenReturn(output)
      val result = CS.getParam[Option[Int]](name, cs)
      verify(cs).getInt(name)
      result should equal(None)
    }

    "Call getLong when reading an Option[Long], return Some if != 0" in new Context {
      val output: Long = 1234l
      when(cs.getLong(name)).thenReturn(output)
      val result = CS.getParam[Option[Long]](name, cs)
      verify(cs).getLong(name)
      result should equal(Some(output))
    }

    "Call getLong when reading an Option[Long], return None if == 0" in new Context {
      val output: Long = 0
      when(cs.getLong(name)).thenReturn(output)
      val result = CS.getParam[Option[Long]](name, cs)
      verify(cs).getLong(name)
      result should equal(None)
    }

    "Call getDouble when reading an Option[Double], return Some if != 0" in new Context {
      val output: Double = 1.23d
      when(cs.getDouble(name)).thenReturn(output)
      val result = CS.getParam[Option[Double]](name, cs)
      verify(cs).getDouble(name)
      result should equal(Some(output))
    }

    "Call getDouble when reading an Option[Double], return None if == 0" in new Context {
      val output: Double = 0.0d
      when(cs.getDouble(name)).thenReturn(output)
      val result = CS.getParam[Option[Double]](name, cs)
      verify(cs).getDouble(name)
      result should equal(None)
    }

    "Call getFloat when reading an Option[Float], return Some if != 0" in new Context {
      val output: Float = 1.23f
      when(cs.getFloat(name)).thenReturn(output)
      val result = CS.getParam[Option[Float]](name, cs)
      verify(cs).getFloat(name)
      result should equal(Some(output))
    }

    "Call getFloat when reading an Option[Float], return None if == 0" in new Context {
      val output: Float = 0.0f
      when(cs.getFloat(name)).thenReturn(output)
      val result = CS.getParam[Option[Float]](name, cs)
      verify(cs).getFloat(name)
      result should equal(None)
    }

    "Call getDate when reading an Option[Date], return Some if != null" in new Context {
      val output: Date = new Date(123123123123l)
      when(cs.getDate(name)).thenReturn(output)
      val result = CS.getParam[Option[Date]](name, cs)
      verify(cs).getDate(name)
      result should equal(Some(output))
    }

    "Call getDate when reading an Option[Date], return None if == null" in new Context {
      val output: Date = null
      when(cs.getDate(name)).thenReturn(output)
      val result = CS.getParam[Option[Date]](name, cs)
      verify(cs).getDate(name)
      result should equal(None)
    }

    "Call getTime when reading an Option[Time], return Some if != null" in new Context {
      val output: Time = new Time(123123123123l)
      when(cs.getTime(name)).thenReturn(output)
      val result = CS.getParam[Option[Time]](name, cs)
      verify(cs).getTime(name)
      result should equal(Some(output))
    }

    "Call getTime when reading an Option[Time], return None if == null" in new Context {
      val output: Time = null
      when(cs.getTime(name)).thenReturn(output)
      val result = CS.getParam[Option[Time]](name, cs)
      verify(cs).getTime(name)
      result should equal(None)
    }

    "Call getTimestamp when reading an Option[Timestamp], return Some if != null" in new Context {
      val output: Timestamp = new Timestamp(123123123123l)
      when(cs.getTimestamp(name)).thenReturn(output)
      val result = CS.getParam[Option[Timestamp]](name, cs)
      verify(cs).getTimestamp(name)
      result should equal(Some(output))
    }

    "Call getTimestamp when reading an Option[Timestamp], return None if == null" in new Context {
      val output: Timestamp = null
      when(cs.getTimestamp(name)).thenReturn(output)
      val result = CS.getParam[Option[Timestamp]](name, cs)
      verify(cs).getTimestamp(name)
      result should equal(None)
    }

    "Call getBoolean when reading an Option[Boolean], return Some if == true" in new Context {
      val output: Boolean = true
      when(cs.getBoolean(name)).thenReturn(output)
      val result = CS.getParam[Option[Boolean]](name, cs)
      verify(cs).getBoolean(name)
      result should equal(Some(output))
    }

    "Call getBoolean when reading an Option[Boolean], return None if == false" in new Context {
      val output: Boolean = false
      when(cs.getBoolean(name)).thenReturn(output)
      val result = CS.getParam[Option[Boolean]](name, cs)
      verify(cs).getBoolean(name)
      result should equal(None)
    }
  }
}
