package org.jboss.weld.extensions.bean.generic;

import javax.enterprise.util.AnnotationLiteral;

/**
 * The annotation literal for {@link GenericProduct} 
 * 
 * @author pmuir
 * 
 * @see GenericProduct
 *
 */
public class GenericProductLiteral extends AnnotationLiteral<GenericProduct> implements GenericProduct 
{
   
   public static final GenericProduct INSTANCE = new GenericProductLiteral();

   private static final long serialVersionUID = 2768505716290514234L;
   
   private GenericProductLiteral() {}

}
