package io.bastioncore.modules.httpclient

import groovy.transform.CompileStatic
import io.bastioncore.core.Configuration
import io.bastioncore.core.components.AbstractSink
import io.bastioncore.core.messages.DefaultMessage
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component
/**
 *
 */
@Component
@Scope('prototype')
@CompileStatic
class HttpClientSink extends AbstractSink {

    HttpBox httpBox
    String groovy
    void onReceive(def message){
        super.onReceive(message)
        if(message instanceof Configuration){
            httpBox = new HttpBox(configuration)
            groovy = ((Map)configuration.configuration).groovy
        }
    }

    @Override
    DefaultMessage process(DefaultMessage defaultMessage) {
        httpBox.request defaultMessage, {
            debug('performing request to '+httpBox.url)
        }
        return null
    }
}
