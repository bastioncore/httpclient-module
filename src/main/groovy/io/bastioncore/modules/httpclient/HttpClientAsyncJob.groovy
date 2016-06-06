package io.bastioncore.modules.httpclient

import groovy.transform.CompileStatic
import io.bastioncore.core.Configuration
import io.bastioncore.core.components.AbstractAsyncJob
import io.bastioncore.core.messages.DefaultMessage
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component

/**
 *
 */
@Component
@Scope('prototype')
@CompileStatic
class HttpClientAsyncJob extends AbstractAsyncJob {

    HttpBox box

    void onReceive(def message){
        super.onReceive(message)
        if(message instanceof Configuration && configuration.child)
            box = new HttpBox(configuration)
    }
    @Override
    void run(DefaultMessage defaultMessage) {
        box.request defaultMessage, { debug('request performed') }
    }
}
