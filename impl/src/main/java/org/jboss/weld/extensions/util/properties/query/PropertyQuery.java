package org.jboss.weld.extensions.util.properties.query;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.jboss.weld.extensions.util.properties.Properties;
import org.jboss.weld.extensions.util.properties.Property;

/**
 * Queries a target class for properties that match certain criteria.  A
 * property may either be a private or public field, declared by the target 
 * class or inherited from a superclass, or a public method declared by the
 * target class or inherited from any of its superclasses.  For properties that
 * are exposed via a method, the property must be a JavaBean style property, i.e.
 * it must provide both an accessor and mutator method according to the JavaBean
 * specification.
 * 
 * This class is not thread-safe, however the result returned by the
 * getResultList() method is.
 * 
 * @author Shane Bryzak
 */
public class PropertyQuery<V>
{
   private final Class<?> targetClass;
   private final List<PropertyCriteria> criteria;
   
   PropertyQuery(Class<?> targetClass)
   {
      this.targetClass = targetClass;
      this.criteria = new ArrayList<PropertyCriteria>();
   }
   
   public PropertyQuery<V> addCriteria(PropertyCriteria criteria)
   {
      this.criteria.add(criteria);
      return this;
   }
   
   public Property<V> getFirstResult()
   {
      List<Property<V>> results = getResultList();      
      return results.isEmpty() ? null : results.get(0);      
   }
   
   public List<Property<V>> getResultList()
   {
      List<Property<V>> results = new ArrayList<Property<V>>();

      // First check public accessor methods (we ignore private methods)
      for (Method method : targetClass.getMethods())
      {
         if (!(method.getName().startsWith("is") || method.getName().startsWith("get"))) continue;         
         
         boolean match = true;
         for (PropertyCriteria c : criteria)
         {
            if (!c.methodMatches(method))
            {
               match = false;
               break;
            }
         }
         if (match) results.add(Properties.<V>createProperty(method));
      }         
      
      Class<?> cls = targetClass;      
      while (!cls.equals(Object.class))
      {
         // Now check declared fields
         for (Field field : cls.getDeclaredFields())
         {
            boolean match = true;
            for (PropertyCriteria c : criteria)
            {                     
               if (!c.fieldMatches(field))
               {
                  match = false;
                  break;
               }
            }
            Property<V> prop = Properties.<V>createProperty(field);
            
            if (match && !resultsContainsProperty(results, prop.getName())) results.add(prop);
         }
         
         cls = cls.getSuperclass();
      }
  
      
      return results;
   }
   
   private boolean resultsContainsProperty(List<Property<V>> results, String propertyName)
   {
      for (Property<V> p : results)
      {
         if (propertyName.equals(p.getName())) return true;
      }
      return false;
   }
}
