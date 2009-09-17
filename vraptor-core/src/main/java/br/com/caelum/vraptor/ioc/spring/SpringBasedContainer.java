/***
 *
 * Copyright (c) 2009 Caelum - www.caelum.com.br/opensource
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. Neither the name of the copyright holders nor the names of its
 *    contributors may be used to endorse or promote products derived from
 *    this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */
package br.com.caelum.vraptor.ioc.spring;

import javax.servlet.ServletContext;

import org.springframework.context.ApplicationContext;

import br.com.caelum.vraptor.ioc.AbstractComponentRegistry;
import br.com.caelum.vraptor.ioc.Container;

/**
 * @author Fabio Kung
 */
public class SpringBasedContainer extends AbstractComponentRegistry implements Container {

    private VRaptorApplicationContext applicationContext;

    public SpringBasedContainer(ApplicationContext parentContext, String... basePackages) {
		String[] packages = {"br.com.caelum.vraptor"};
        if (basePackages.length > 0) {
            packages = basePackages;
        }
        applicationContext = new VRaptorApplicationContext(this, packages);
        applicationContext.setParent(parentContext);
    }

    public void register(Class<?> requiredType, Class<?> componentType) {
        applicationContext.register(componentType);
    }

    public <T> T instanceFor(Class<T> type) {
        return applicationContext.getBean(type);
    }

    public void start(ServletContext context) {
        applicationContext.setServletContext(context);
        applicationContext.refresh();
        applicationContext.start();
    }

    public void stop() {
        applicationContext.stop();
        applicationContext.destroy();
        applicationContext = null;
    }

}
