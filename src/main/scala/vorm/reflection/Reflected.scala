package vorm.reflection

import vorm._
import extensions._

//  or InstanceReflection
class Reflected
  ( val instance : Any,
    val reflection : Reflection )
  {

    def propertyValues
      : Map[String, Any]
      = reflection.properties.view.unzip._1.zipBy(propertyValue).toMap

    def propertyValue
      ( name: String )
      : Any
      = instance
          .getClass
          .getMethods
          .find{ _.getName == name }
          .get 
          .invoke( instance )

    def methodResult
      ( name: String, 
        args: List[Any] = Nil ) 
      : Any
      = throw new NotImplementedError

  }