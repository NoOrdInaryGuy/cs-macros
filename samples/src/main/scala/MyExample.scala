import java.sql.Time
import java.sql.Timestamp
import java.sql.Date

import com.mysql.jdbc.CallableStatement

class MyExample {
  val cs = new CallableStatement(null, null)

  case class MyType(name: String, age: Int)

  val noneInt: Option[Int] = None
  val someInt: Option[Int] = Some(12345)

  CS.setParamOpt("aNoneStringParam", Option[String](null), cs)
  CS.setParamOpt("aSomeStringParam", Some("Yo"), cs)
  CS.setParamOpt("aNoneIntParam", noneInt, cs)
  CS.setParamOpt("aSomeIntParam", someInt, cs)

  CS.setParam("aByteParam", 123.toByte, cs)
  CS.setParam("aStringParam", "hello", cs)
  CS.setParam("aBigDecimalParam", BigDecimal(123.4), cs)
  CS.setParam("aShortParam", 123.toShort, cs)
  CS.setParam("anIntParam", 123, cs)
  CS.setParam("aLongParam", 123L, cs)
  CS.setParam("aDoubleParam", 123.4, cs)
  CS.setParam("aFloatParam", 123.4f, cs)
  CS.setParam("aDateParam", new Date(System.currentTimeMillis()), cs)
  CS.setParam("aTimeParam", new Time(System.currentTimeMillis()), cs)
  CS.setParam("aTimestampParam", new Timestamp(System.currentTimeMillis()), cs)
  CS.setParam("aBooleanParam", false, cs)
//  CS.setParam("anotherParam", MyType("Neil", 99), cs)

}
