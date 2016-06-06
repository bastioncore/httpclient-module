package io.bastioncore.modules.httpclient

import io.bastioncore.core.Configuration
import io.bastioncore.core.components.AbstractScheduledEntry
import io.bastioncore.core.messages.DefaultMessage
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component
/**
 *
 */
@Component
@Scope('prototype')
class HttpClientScheduledEntry extends AbstractScheduledEntry {

    HttpBox httpBox

    void onReceive(def message){
        super.onReceive(message)
        if(message instanceof Configuration)
            httpBox = new HttpBox(configuration)
    }
    @Override
    void processTick() {
        httpBox.request null, { message ->
            debug('performing request to '+httpBox.url)
            self().tell(message,self())
        }
    }

    @Override
    DefaultMessage process(DefaultMessage defaultMessage) {
        return defaultMessage
    }

    @Override
    void schedule() {
        schedule(configuration.configuration.delay,configuration.configuration.interval)
    }
}
