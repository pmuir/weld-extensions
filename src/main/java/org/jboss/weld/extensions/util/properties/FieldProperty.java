/**
 * 
 */
package org.jboss.weld.extensions.util.properties;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Type;

/**
 * A bean property based on the value contained in a field
 * 
 * @author Pete Muir
 * @author Shane Bryzak
 *
 */
class FieldProperty implements Property
{
   private static String buildGetFieldValueErrorMessage(Field field, Object obj)
   {
      return String.format("Exception reading [%s] field from object [%s].", field.getName(), obj);
   }
   
   private static String buildSetFieldValueErrorMessage(Field field, Object obj, Object value)
   {
      return String.format("Exception setting [%s] field on object [%s] to value [%s]", field.getName(), obj, value);
   }
   
   private final Field field;

   public FieldProperty(Field field)
   {
      this.field = field;
   }
   
   public String getName()
   {
      return field.getName();
   }
   
   public Type getBaseType()
   {
      return field.getGenericType();
   }
   
   public <A extends Annotation> A getAnnotation(Class<A> annotationClass)
   {
      return field.getAnnotation(annotationClass);
   }
   
   public Class<?> getPropertyClass()
   {
      return (Class<?>) field.getType();
   }
   
   public Object getValue(Object instance)
   {
      field.setAccessible(true);
      try
      {
         return getPropertyClass().cast(field.get(instance));
      }
      catch (IllegalAccessException e)
      {
         throw new RuntimeException(buildGetFieldValueErrorMessage(field, instance), e);
      }
      catch (NullPointerException ex)
      {
         NullPointerException ex2 = new NullPointerException(buildGetFieldValueErrorMessage(field, instance));
         ex2.initCause(ex.getCause());
         throw ex2;
      }
   }
   
   public void setValue(Object instance, Object value) 
   {
      field.setAccessible(true);
      try
      {
         field.set(instance, value);
      }
      catch (IllegalAccessException e)
      {
         throw new RuntimeException(buildSetFieldValueErrorMessage(field, instance, value), e);
      }
      catch (NullPointerException ex)
      {
         NullPointerException ex2 = new NullPointerException(buildSetFieldValueErrorMessage(field, instance, value));
         ex2.initCause(ex.getCause());
         throw ex2;
      }
   }
   
}