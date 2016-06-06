package io.bastioncore.modules.httpclient

import groovy.transform.CompileStatic
import io.bastioncore.core.Configuration
import io.bastioncore.core.components.AbstractJob
import io.bastioncore.core.messages.DefaultMessage
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component

/**
 *
 */
@Component
@Scope('prototype')
@CompileStatic
class HttpClientJob extends AbstractJob {

    HttpBox box

    void onReceive(def message){
        super.onReceive(message)
        if (message instanceof Configuration)
            box = new HttpBox(configuration)

    }
    @Override
    void run(DefaultMessage defaultMessage) {
        box.request defaultMessage, { debug('request performed')}
    }
}
