package org.jboss.weld.extensions.bean.generic;

import java.lang.annotation.Annotation;

import javax.enterprise.util.AnnotationLiteral;

public class GenericLiteral extends AnnotationLiteral<Generic> implements Generic
{

   private static final long serialVersionUID = -1931707390692943775L;

   private final Class<? extends Annotation> value;
   
   public GenericLiteral(Class<? extends Annotation> value)
   {
      this.value = value;
   }

   public Class<? extends Annotation> value()
   {
      return value;
   }

}