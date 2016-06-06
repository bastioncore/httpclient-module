package io.bastioncore.modules.httpclient

import akka.actor.ActorRef
import akka.actor.ActorSelection
import akka.actor.ActorSystem
import akka.actor.Props
import io.bastioncore.core.Configuration
import org.junit.Test

/**
 *
 */
class HttpClientModuleTests {

    @Test
    void processTickTest(){
        ActorSystem actorSystem = ActorSystem.create()
        ActorRef ref = actorSystem.actorOf(Props.create(HttpClientScheduledEntry))
        Configuration configuration = new Configuration([configuration:[:]])
        configuration.configuration['delay'] = '1 second'
        configuration.configuration['interval'] = '3 seconds'
        configuration.configuration['method'] = 'get'
        configuration.configuration['url'] = 'http://www.google.com'
        configuration.configuration['headers'] = ['Accept':'text/html']
        ref.tell(configuration,null)
        Thread.sleep(1000)
        actorSystem.terminate()
    }

    @Test
    void htmlToDataConverterTest(){
        def data = new HtmlToDataConverter().convert('<html><body><div class="foobar">aa</div></body></html>')
         data.body.getElementsByTagName('div').each{
             assert it.getAttribute('class')=='foobar'
         }
    }
}
