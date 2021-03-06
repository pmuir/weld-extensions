/*
 * JBoss, Home of Professional Open Source
 * Copyright 2010, Red Hat, Inc., and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,  
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.weld.extensions.servicehandler;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 * Meta annotation that is used to specify an invocation handler for an
 * automatically implmented bean.
 * </p>
 * 
 * <p>
 * If the annotation that is annotated with this meta-annotation is applied to
 * an interface or abstract class then the container will automatically provide
 * a concrete implementation of the class/interface, and delegate all calls to
 * abstract methods through the handler class specified by this annotations.
 * </p>
 * 
 * <p>
 * The handler class must have a method with the following signature:
 * </p>
 * 
 * <pre>
 *  @AroundInvoke public Object aroundInvoke(final InvocationContext invocation) throws Exception
 * </pre>
 * 
 * <p>
 * This is the same as an intercepter class. This handler can be injected into
 * and use initializer methods, however @PreDestory methods are not available
 * </p>
 * 
 * <p>
 * The annotation should have:
 * </p>
 * 
 * <pre>
 * @Retention(RUNTIME)
 * @Target({ TYPE })
 * </pre>
 * 
 * @author Stuart Douglas <stuart.w.douglas@gmail.com>
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
@Documented
public @interface ServiceHandler
{
   Class<?> value();
}
