package io.bastioncore.modules.httpclient

import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.Method
import io.bastioncore.core.Configuration
import io.bastioncore.core.messages.DefaultMessage
import io.bastioncore.core.scripting.BGroovy

class HttpBox {
    String url
    String method
    def headers
    def body
    String groovy

    public HttpBox(){
        headers = [:]
    }

    public HttpBox(Configuration configuration) {
        url = configuration.configuration.url
        headers = configuration.configuration.headers
        body = configuration.configuration.body
        method = configuration.configuration.method
        groovy = configuration.configuration.groovy
    }

    public void request(DefaultMessage defaultMessage,Closure callback){
        Method methodObject
        switch(method){
            case 'get': methodObject = Method.GET; break
            case 'post': methodObject = Method.POST; break
            case 'put': methodObject = Method.PUT; break
            case 'patch': methodObject = Method.PATCH; break
            case 'delete': methodObject = Method.DELETE; break
        }
        if (groovy) {
            Script script = BGroovy.instance.parse(groovy)
            Binding binding = new Binding()
            binding.setVariable('content', defaultMessage.content)
            binding.setVariable('context', defaultMessage.context)
            binding.setVariable('url', url)
            binding.setVariable('headers', headers)
            binding.setVariable('body', body)
            script.setBinding(binding)
            script.run()
            url = binding.getVariable('url')
            headers = binding.getVariable('headers')
            body = binding.getVariable('body')
        }
        HTTPBuilder builder = new HTTPBuilder(url)
        builder.setHeaders(headers)

        builder.request(methodObject, ContentType.TEXT) { request ->
            response.success = { resp, reader ->
                DefaultMessage message = new DefaultMessage(reader.getText())
                message.context.put('headers',resp.headers)
                message.context.put('status_code',resp.status)
                callback(message)
            }
        }
    }
}
